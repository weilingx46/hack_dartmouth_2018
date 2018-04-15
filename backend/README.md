All text in bodies of POST requests need to be sent as x-www-form-urlencoded

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

Not all of these fields have to be present, only fields which you want updated per request.
 
Request:
  
    {
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
