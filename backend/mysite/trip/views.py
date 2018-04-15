from django.shortcuts import render
from django.http import HttpResponse

def index(request):
    return HttpResponse("Hello, world. You're at the trip center.")

#POST

#take 3 arguments (squad name, squad password, list members adding)
def create(request):
    #Trip.create(tName, tPassword, tPeople, tDest)
    return HttpResponse("trip ID")

#3 arguments (id, password, member name)
def add (request):
    #trip.addPeople(tPeople)
    return HttpResponse("trip ID")

#3 arguments (squad name, member name)
def delete(request):
    #trip.delete
    return HttpResponse("Squad ID")

#uncertain how many arguments
def destination (request):
    #trip.changeDest(tDest)
    return HttpResponse("Long/Lat Object")

#GET
def status(request):
    return HttpResponse("list of people + status")
