from django.shortcuts import render
from django.http import HttpResponse
from django.views.decorators.csrf import csrf_exempt


def index(request):
    return HttpResponse("Hello, world. You're at the trip center.")
#POST

#take 3 arguments (squad name, squad password, list members adding)
@csrf_exempt
def create(request):
    #Trip.create(tName, tPassword, tPeople, tDest)
    json_data = json.loads(request.body.decode())
    output = Trip.Create(json_data['tName'], json_data['tPassword'], json_data['tPeople'], json_data['tDest'])
    if output['success']!= False:
        return JsonResponse(output)
    else:
        return JsonResponse({'error':'create_error'})

#3 arguments (id, password, member name)
@csrf_exempt
def add (request):
    json_data = json.loads(request.body.decode())
    trip = Trip.check(json_data['tId'])
    output = trip.addPeople(json_data['people'])
    #trip.addPeople(tPeople)
    if output['success'] == True:
        return JsonResponse(output)
    else:
        return JsonResponse({'error':'add_error'})

#3 arguments (squad name, member name)
@csrf_exempt
def delete(request):
    json_data = json.loads(request.body.decode())
    trip = Trip.check(json_data['tId'])
    output = trip.deletePeople(json_data['people'])
    #trip.addPeople(tPeople)
    if output['success'] == True:
        return JsonResponse(output)
    #trip.delete
    else:
        return JsonResponse({'error':'delete_error'})

#uncertain how many arguments
@csrf_exempt
def destination (request):
    json_data = json.loads(request.body.decode())
    trip = Trip.check(json_data['tId'])
    output = trip.changeDest(json_data['tDest'])
    #trip.changeDest(tDest)
    if output['success'] == True:
        return JsonResponse(output)
    else:
        return JsonResponse({'error':"destination_error"})

#GET
@csrf_exempt
def status(request):
    return HttpResponse("list of people + status")
