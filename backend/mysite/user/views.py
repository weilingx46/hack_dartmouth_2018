from django.shortcuts import render
from django.http import HttpResponse

def index(request):
    return HttpResponse("Hello, world. You're at the user.")

#POST

#handled by FB?
#3 parameters (username, password, name)
def create(request):
    return HttpResponse("trips + name")

#2 parameters (username, password)
def login(request):
    return HttpResponse("unique user id")

#2 parameters (password, ppl in car)
def update(request):
    return HttpResponse("trips + name")

def status(request):
    return HttpResponse("status + lat/long + people in user")
