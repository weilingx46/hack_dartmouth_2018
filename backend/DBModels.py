from mongoengine import *
import time
import uuid
import bcrypt

connect("Trips")

class Trip(EmbeddedDocument):
  tPeople = ListField()
  tId = StringField(max_length=20)
  tPassword = StringField(max_length=20)
  tName = StringField(max_length=40)
  tDest = ListField()
  
class Friend(EmbeddedDocument):
  fName = StringField(max_length=30)
  fId = StringField(max_length=20)

class User(Document):
  uName = StringField(max_length=30)
  uId = StringField(max_length=20)
  uPassword = StringField()
  uUsername = StringField(max_length=20)
  
  friends = ListField(EmbeddedDocumentField(Friend))
  trips = ListField(EmbeddedDocumentField(Trip))

  lastLogin = LongField()
  authToken = StringField()

  # create a new user, pass in desired username, password, and name
  @staticmethod
  def create(username, password, name):

    if len(username) < 4 or len(password) < 4:
      return {"success":False, "message":"Username or password too short!"}
    
    if User.objects(uUsername = username):
      return {"success":False, "message":"User already exists!"}

    password = bcrypt.hashpw(password.encode(), bcrypt.gensalt())
    newUser = User(uUsername = username, uPassword = password, uName = name)

    authToken = str(uuid.uuid4().hex)
    newUser.authToken = authToken
    newUser.lastLogin = time.time()

    newUser.save()
    return {"success":True, "uId":str(newUser.id), "authToken":authToken}


  # login with an existing username and password
  @staticmethod
  def login(username, password):
    matches = User.objects(uUsername = username)
    if not matches:
      return {"success":False, "message":"User doesn't exist!"}

    usr = matches[0]
    if bcrypt.hashpw(password.encode(), usr.uPassword.encode()) != usr.uPassword.encode():
      return {"success":False, "message":"Incorrect Password!"}

    usr.lastLogin = time.time()
    usr.save()
    
    return {"success":True, "uName":usr.uUsername, "uId":str(usr.id), "authToken":usr.authToken, "friends":usr.friends, "trips":usr.trips}

  # return user object if validated, otherwise give error object
  @staticmethod
  def check(uId, AuthToken):
    try:
      int(uId, 16)
    except ValueError:
      return {"success":False, "message": "Invalid uId!"}

    matches = User.objects(id=uId)
    if not matches:
      return {"success":False, "message":"No such user with ID"}

    usr = matches[0]
    if AuthToken == usr.authToken:
      return usr
    else:
      return {"success":False, "message":"Bad AuthToken"}

  def update(self, name, password):
    if name:
      self.uName = name
    if password:
      self.password = password
    self.save()
    return {"success":True}

