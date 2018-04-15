from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt
from user.DBModels import *
import json
from django.http import JsonResponse

def index(request):
    return HttpResponse("Hello, world. You're at the user.")

#POST
#always a dict[success] = True/False, check first

#handled by FB?
# make sure both longer than 4 characters, no special character
#3 parameters (username, password, name)
@csrf_exempt
def create(request):
    json_data = json.loads(request.body.decode())
    output = User.create(json_data['username'], json_data['password'], json_data['name'])

    if output['success'] == True:
        return JsonResponse({'userId':output['uId'], 'authToken':output['authToken']})
    else:
        return JsonResponse()


#2 parameters (username, password)
#returning dictionary
@csrf_exempt
def login(request):
    json_data = json.loads(request.body.decode())
    output = User.login(json_data['username'], json_data['password'])
    if output['success'] == True:
        return JsonResponse(output)

#3 parameters (uID, AuthToken, password)
@csrf_exempt
def update(request):

    json_data = json.loads(request.body.decode())
    if person = User.check(json_data['uId'], json_data['authToken']):
        return JsonResponse(User.update(json_data['name'], json_data['password']))
    else:
    #if true, call udpate, which returns same as login
        return JsonResponse(problem)

@csrf_exempt
def status(request):
    #call updateStatus
    return HttpResponse("status + lat/long + people in user")
