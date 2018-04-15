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
    return HttpResponse("trip ID")

#3 arguments (id, password, member name)
@csrf_exempt
def add (request):
    #trip.addPeople(tPeople)
    return HttpResponse("trip ID")

#3 arguments (squad name, member name)
@csrf_exempt
def delete(request):
    #trip.delete
    return HttpResponse("Squad ID")

#uncertain how many arguments
@csrf_exempt
def destination (request):
    #trip.changeDest(tDest)
    return HttpResponse("Long/Lat Object")

#GET
@csrf_exempt
def status(request):
    return HttpResponse("list of people + status")
