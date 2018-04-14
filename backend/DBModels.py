from mongoengine import *

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
  uPassword = StringField(max_length=30)
  trippees = ListField(EmbeddedDocumentField(Friend))
  trips = ListField(EmbeddedDocumentField(Trip))



