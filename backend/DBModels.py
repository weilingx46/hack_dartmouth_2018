from mongoengine import *
import secrets
import time

class Trip(EmbeddedDocument):
  memberStatuses = ListField()
  tId = StringField(max_length=20)
  tPassword = StringField(max_length=20)

class Friend(EmbeddedDocument):
  fName = StringField(max_length=30)
  fId = StringField(max_length=20)

class User(Document):
  uName = StringField(max_length=30)
  uId = StringField(max_length=20)

  trippees = ListField(EmbeddedDocumentField(Friend))
  trips = ListField(EmbeddedDocumentField(Trip))

  lastLogin = LongField()
  authToken = StringField(max_length=20)


def generateAccessToken():
  secret = secrets.token_hex(nbytes=16)

def login(uId,uName):
  now = time.time()

  results = User.objects(uId=uId)

  if not results:
    newUser = User(uId)
    newUser.lastLogin = time.time()
  else:
    user = results[0]
    print(user)


# check auth token - if over time limit, delete
# current auth token and force re-sign in
def validateAuthToken(uId, authToken):
  
  results = User.objects(uId=uId)

  if not results:
    return None

  user = results[0]
  print(user)

  now = time.time()




