All text in bodies of POST requests need to be sent as x-www-form-urlencoded

Hosted at: http://ec2-18-188-214-59.us-east-2.compute.amazonaws.com/

### Routes
<details>
<summary><b>POST /user/create/</b></summary>
<br>
Route to post with certain parameters to create a user in the database. Expects username, password, and name fields, such that username
and password fields are strings no shorter than 4 characters. 
Request:

    {
      'username':'weilingx96',
      'password':'testpassword',
      'name','weiling'
    }
    
Response:

    {
      'userId':'sdnf983421',
      'authToken':'fnwef032rkn23'
    }
    
OR
    
    {
      'error':'Password too short!'
    }
    
</details>

<details>
<summary><b>POST /user/login/</b></summary>
<br>


Login to an existing user account with username and password. You'll want to save the uId and authToken fields on succesful login, so
that you can pass it back to the backend on certain function calls.

Request:
  
    {
      'username':'edwardstestaccount',
      'password':'Iamverysmart'
    }

Response:

    {
      'success':True,
      'uName':'edwardstestaccount',
      'uId':'2k4m1889sk29',
      'authToken':'21krm291rkm',
      'friends': [
        FriendObject,
        FriendObject
      ],
      'trips': [
        '124l124fs'
      ]
    
    }
    
OR if you fail to login, something like 

    {
      'success': False,
      'message': 'Incorrect Password!'
    }

</details>
<details>
<summary><b>POST /user/update/</b></summary>
<br>

Not all of these fields have to be present, only fields which you want updated per request. However, authToken and uId MUST BE PRESENT.
 
Request:
  
    {
      'authToken':'124812jks9124',
      'uId':'124kj912k41',
      'name':'edward',
      'password':'newpassword',
      'lat':40.54957,
      'longitude':240124.24444,
      'indicator':'Restroom break'/'OK'/'Food stop'
    }

Response:

    {
      'success':True,
      'uName':'edward',
      'uId':'12j49jsoz02',
      'lat':40.54957,
      'longitude':240124.24444,
      'indicator':'Restroom break',
      'trips':[
        ('Boston day trip','12rsdfk23k520'
      ],
      'friends': [
        ['Myself','49124k,'],
        ['Me','124kljlfksd']
      ]
    }

</details>

<details>
<summary><b>GET /trip/</b></summary>
<br>

Get a compact version of all the current trips, in ordered lists of (tripname, tripID)

Request:
    
    (None)

Response:

    {
      trips: [
        ['bestTrip','21k9f12k'],
        ['nextBestTrip','2kr9ldj47hhh'],
        ['yougetit','l1k91k2']
      ]
    }

</details>

<details>
<summary><b>GET /trip/status/</b></summary>
<br>

Get the status of everyone in the trip (meant to be used when actively driving)

Request:
    
    (None)

Response:

    {
      'success':'True',
      'tId':'1241249ks0',
      'tName':'Boston road trip',
      'tPeople': [
        {
            'uName':'edward',
            'uId':'12412f9sf',
            'lat':14.1111,
            'long':214091.1,
            'indicator':'OK',
            'trips':['1240912l','1240129412']
            'friends':['12421k40','12fks0212']
        },
        {
            'uName':'Nathan',
            'uId':'824229sf',
            'lat':155.89,
            'long':21421.1,
            'indicator':'OK',
            'trips':['1240912l','12404f412']
            'friends':['1gk21k40','12fs212']
        }
      ], 
      'tDest': [1240.0001,12.244412]
    }

</details>


<details>
<summary><b>POST /trip/create/</b></summary>
<br>

To create a new trip in the database. Can optionally add people to the room immediately.

Request:
  
    {
      'tName':'best trip ever',
      'tPassword':'roomPassword',
      'tPeople':[
        '124su1248g9',
        'klj983jk20',
        '124019204124',
      ],
      'tDest': [2104.222,22.111104]
    }

Response:

</details>


<details>
<summary><b>POST /trip/add/</b></summary>
<br>

To add more people to an existing trip.

Request:
  
    {
      'tId':'sdfk2912kl1',
      'people': [
        '12l12rjk1j20',
        '124109klsss',
        '122109kfsls'
      ]
    }

Response:

</details>


<details>
<summary><b>POST /trip/delete/</b></summary>
<br>

To delete people from an existing trip.

Request:
  
    {
      'tId':'sdfk2912kl1',
      'people': [
        '12l12rjk1j20',
        '124109klsss',
        '122109kfsls'
      ]
    }

Response:

</details>


<details>
<summary><b>POST /trip/delete/</b></summary>
<br>

To change the destination of a current trip

Request:
  
    {
      'tId':'sdfk2912kl1',
      'people': [
        '12l12rjk1j20',
        '124109klsss',
        '122109kfsls'
      ]
    }

Response:

</details>


