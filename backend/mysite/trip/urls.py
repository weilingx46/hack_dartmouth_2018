from django.conf.urls import url, include
from django.urls import path

from . import views

urlpatterns = [
    path('', views.index),
    path('create', views.create),
    path('add', views.add),
    path('delete', views.delete),
    path('destination', views.destination),
    path('status', views.status),
]
