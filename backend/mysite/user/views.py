from django.shortcuts import render
from django.http import HttpResponse

def index(request):
    return HttpResponse("Hello, world. You're at the user.")

#POST
#always a dict[success] = True/False, check first

#handled by FB?
# make sure both longer than 4 characters, no special character
#3 parameters (username, password, name)
def create(request):
    #parse request object
    #call User.create(user, pass, name)
        #if None, output error
    #check if dict[0] == True
    #return dict {Success=True, uID, authToken}
    return HttpResponse("return uID + authToken from createUsers")

#2 parameters (username, password)
def login(request):
    #User.login(user, pass)
    #send to ed
    return HttpResponse("object {uName, uID, authToken, friends[], trips[] }")

#3 parameters (uID, AuthToken, password)
def update(request):
    #call Trip.check(uID)
    #if false, handle error

    #if true, call udpate, which returns same as login
    return HttpResponse("same as login")

def status(request):
    #call updateStatus
    return HttpResponse("status + lat/long + people in user")
