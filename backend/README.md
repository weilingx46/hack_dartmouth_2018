## Overview
All text in bodies of POST requests need to be sent as x-www-form-urlencoded

Hosted at: http://ec2-18-188-214-59.us-east-2.compute.amazonaws.com/

### Users
I've created a couple of default users and trips in the database you are free to use as needed:

<br/>
name: 'dummytest'

Username: 'dummy'

Password: 'notsodumb'

Lat: 40.0001

Long: 14.07

uId: '5ad304f17f15006b0bb2a630'

indicator: None

<br />
name: 'spongebob'

username: 'spongy'

password: 'squarepants'

lat: 40.0101

long: 14.10

uId: '5ad306b37f15006b6d1c2f12'

indicator: 'OK'

<br />
name: 'test'

username: 'test1'

password: 'test2'

lat: 40.1101

long: 14.13

uId: '5ad307447f15006b6d1c2f13'

### Trips

<br/>
name: 'best trip ever'

password: 'password'

users: ['5ad304f17f15006b0bb2a630','5ad306b37f15006b6d1c2f12'] (so dummy and spongebob)

destination: [38.50, 14.90]

tId: '5ad3083a7f15006b8053ddcc'

<br/>
name: 'another test trip'

password: 'password'

users: ['5ad304f17f15006b0bb2a630','5ad306b37f15006b6d1c2f12', '5ad307447f15006b6d1c2f13'] (so dummy and spongebob and test)

destination: [35.50, 20.90]

tId: '5ad308817f15006b8053ddcd'


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


Login to an existing user account with username and password. You'll want to save the uId and authToken fields on successful login, so
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
      'long':240124.24444,
      'indicator':'Restroom break'/'OK'/'Food stop'
    }

Response:

    {
      'success':True,
      'uName':'edward',
      'uId':'12j49jsoz02',
      'lat':40.54957,
      'long':240124.24444,
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
<summary><b>POST /user/addTrip/</b></summary>
<br>
Route to post to add a trip to a user in the database. Expects a username, authToken, tripId
they wish to be added to, and the trip's password.

Request:

    {
      'username':'weilingx96',
      'tName':'Florida Road Trip',
      'tPassword','thisgrouprocks'
    }


Response:

    {
        'success':True,
        'tId':'12210klfsd0',
        'tName':'best trip ever',
        'tPeople': [
            'sdnf983421',
            '124su1248g9',
            'klj983jk20',
            '124019204124',
        ],
        'tDest': [2104.222,22.111104]
    }

</details>

<details>
<summary><b>POST /user/deleteTrip/</b></summary>
<br>
Route to post to delete a trip from a user in the database. Expects a userId, authToken,
and the name of the trip they wish to be deleted from.

Request:

    {
      'username':'weilingx96',
      'tName':'Florida Road Trip',
    }

Response:

{
    'success':True,
    'tId':'12210klfsd0',
    'tName':'best trip ever',
    'tPeople': [
        'klj983jk20',
        '124019204124',
    ],
    'tDest': [2104.222,22.111104]
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

To create a new trip in the database. Can optionally add people to the room immediately. You'll want to save the tripId (tId) for use on future requests.

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

    {
        'success':True,
        'tId':'12210klfsd0',
        'tName':'best trip ever',
        'tPeople': [
            '124su1248g9',
            'klj983jk20',
            '124019204124',
        ],
        'tDest': [2104.222,22.111104]
    }
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

    {
        'success':True,
        'tId':'12210klfsd0',
        'tName':'best trip ever',
        'tPeople': [
            '124su1248g9',
            'klj983jk20',
            '124019204124',
            '12l12rjk1j20',
            '124109klsss',
            '122109kfsls'
        ],
        'tDest': [2104.222,22.111104]
    }
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

     {
        'success':True,
        'tId':'12210klfsd0',
        'tName':'best trip ever',
        'tPeople': [
            '124su1248g9',
            'klj983jk20',
            '124019204124'
        ],
        'tDest': [2104.222,22.111104]
    }
</details>


<details>
<summary><b>POST /trip/destination/</b></summary>
<br>

To change the destination of a current trip

Request:

    {
      'tId': '124fs9d0ss0z',
      'tDest':[120.124,124214.11]
    }

Response:

    {
        'success':True,
        'tId':'12210klfsd0',
        'tName':'best trip ever',
        'tPeople': [
            '124su1248g9',
            'klj983jk20',
            '124019204124'
        ],
        'tDest': [120.124,124214.11]
    }

</details>
