from mongoengine import *
import time
import uuid
import bcrypt
import sys

connect("Trips")

class Trip(Document):
  
  tPeople = ListField()
  tId = StringField(max_length=20)
  tPassword = StringField()
  tName = StringField(max_length=40)
  tDest = ListField()

  @staticmethod
  def create(tName, tPassword, tPeople, tDest):
    matches = Trip.objects(tName=tName)
    if matches:
      return {'success':False, 'message':'Trip with name already exists!'}

    tPassword = bcrypt.hashpw(tPassword.encode(),bcrypt.gensalt())
    newTrip = Trip(tName = tName, tPassword = tPassword, tDest = tDest, tPeople = tPeople)
    newTrip.save()
      
    return newTrip.objectify()

  @staticmethod
  def check(tId):
    matches = Trip.objects(id = tId)
    if not matches:
      return 

    return matches[0]

  @staticmethod
  def getAll():
    return list(map(lambda x: [x.tName, str(x.id)], Trip.objects()))

  
  def status():
    return self.objectify()
  
  
  def addPeople(self, morePeople):
    self.tPeople = self.tPeople + morePeople
    self.save()

    return self.objectify()
    
  def deletePeople(self, deletePeople):
    self.tPeople = [x for x in self.tPeople if x not in deletePeople]
    self.save()

    return self.objectify()

  def changeName(self, name):
    self.tName = name
    self.save()

    return self.objectify()

  def changeDest(self, tDest):
    if not tDest or len(tDest) != 2:
      return {'success':False, "message":"Malformed destination!"}

    self.tDest = tDest
    self.save()

    return self.objectify()

  def objectify(self):

    people = []
    for i in self.tPeople:
      if i:
        m = User.objects(id = i)
        if m:
          people.append(m[0].objectify())
        
    return {'success':True,'tId':str(self.id),'tName': self.tName ,'tPeople': people, 'tDest':self.tDest}
    
class Friend(EmbeddedDocument):
  fName = StringField(max_length=30)
  fId = StringField(max_length=20)

  def objectify(self):
    return {'success':True, 'name':self.fName, 'id':self.fId }
  
class User(Document):

  uName = StringField(max_length=30)
  uId = StringField(max_length=20)
  uPassword = StringField()
  uUsername = StringField(max_length=20)
  
  friends = ListField(EmbeddedDocumentField(Friend))
  trips = ListField()

  lastLogin = LongField()
  authToken = StringField()

  latitude = FloatField()
  longitude = FloatField()
  indicator = IntField()

  indicators = ["Short Break","Restroom","Grabbing Something To Eat","Sleep"]
  
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
      return 

    matches = User.objects(id=uId)
    if not matches:
      return 

    usr = matches[0]

    now = time.time()
    if not usr.authToken:
      return 
    elif (now - usr.lastLogin) > 60*30:
      usr.authToken = None
      usr.save()
      return 
    elif AuthToken == usr.authToken:
      return usr
    else:
      return 

  # update name password
  def update(self, name, password, latitude, longitude, indicator):
    if name:
      self.uName = name
    if password:
      self.password = password
    if latitude:
      self.latitude = latitude
    if longitude:
      self.longitude = longitude
    if indicator:
      self.indicator = indicator
    self.save()

    return self.objectify()

  # serialize into dictionary object
  def objectify(self):
    trips = []
    for i in self.trips:
      match = Trip.objects(id = i)
      if match:
        trips.append((match[0].tName, match[0].tId))

    friends = []  
    for f in self.friends:
      friends.append(f.objectify())
      
    return {'uName':self.uName, 'uId':str(self.id), 'lat':self.latitude, 'long':self.longitude, 'indicator':self.indicator, 'trips':trips, 'friends':friends }


if __name__ == "__main__":
  restore = []

  try:
    print("==== Creating account for 'henry' ====== ")
    a = User.create('account1','password1','henry')
    print("User.create returned: " + str(a))
    a = User.objects(id=a['uId'])
    restore.append(a[0])

    print()
    print("==== Creating account for 'jill' ===== ")
    a = User.create('account2','password2','jill')
    print("User.create returned: " + str(a))
    a = User.objects(id=a['uId'])
    restore.append(a[0])


    print()

    print("==== Creating account for 'jack' ==== ")
    a = User.create('account3','password3', 'jack')
    print("User.create returned: " + str(a))
    a = User.objects(id = a['uId'])
    restore.append(a[0])

    print()
    print("==== Logging in as jill ==== ")
    a = User.login('account2','password2')
    print("User.login returned: " + str(a))

    print()
    print("==== Logging in as Jack ==== ")
    a = User.login('account3', 'password3')
    print("User.login returned: " + str(a))


    print()
    print("=== Checking Jack authorization ===")
    a = User.check(a['uId'],a['authToken'])
    print("User.check returned " + str(a))

    print()
    print("=== Updating jill's account username ==== ")
    restore[2].update('jillian',None)
    print("Results after updating name: " + str(restore[2].objectify()))

    print()
    print("=== Creating empty trip ==== ")
    a = Trip.create("trip1","password1",[],[])
    print("Trip.create returned: " + str(a))
    a = Trip.objects(id = a['tId'])[0]
    restore.append(a)

    print()
    print("=== Creating cool trip ==== ")
    a = Trip.create("cooltrip","hardtoguess",[str(restore[1].id),str(restore[2].id)],[60,60])
    print("Trip.create returned: " + str(a))
    sys.stdout.flush()
    a = Trip.objects(id = a['tId'])[0]
    restore.append(a)

    print()
    print("Trip.getAll returns: " + str(Trip.getAll()))
  
  finally:
    for o in restore:
      o.delete()

    print()
    print("==== Deleted test objects from database! ====")
