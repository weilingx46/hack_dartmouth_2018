from mongoengine import *

class User(Document):
  uName = StringField(max_length=30)
  uId = StringField(max_length=20)
  uPassword = StringField(max_length=30)
  trippees = ListField(EmbeddedDocumentField(Friend))
  trips = ListField(EmbeddedDocumentField(Trip))

class Friend(EmbeddedDocument):
  fName = StringField(max_length=30)
  fId = StringField(max_length=20)

class Trip(EmbeddedDocument):
  memberStatuses = ListField()
  tId = StringField(max_length=20)
  tPassword = StringField(max_length=20)
  

poll = Poll.objects(question__contains="What").first()
choice = Choice(choice_text="I'm at DjangoCon.fi", votes=23)
poll.choices.append(choice)
poll.save()
