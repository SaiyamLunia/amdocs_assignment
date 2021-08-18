# Amdocs Java Coding Exercise

Architecture- The project is designed using Spring Boot.

The application has handlers for authenticating the service using Basic-Auth(Username and password is provided below).
Access Info -

	context-path - /assignment	
	resource-path - /user
	Port  - 9090
	Username - ADMIN
	Password - adminpass
	

The Controller class provides the four basic HTTP Operations.

 
#### Access Info -

(1)POST -
	http://localhost:9090/assignment/user
	
Headers
	Content-Type:application/json
	
Body -
{
    "username": "Jill",
    "password": "pass1234"
}
	
(2)GET -
	http://localhost:9090/assignment/user/{username}
	
Headers
	Content-Type:application/json

	
(3)PUT - 
	http://localhost:9090/assignment/user/{username}
	
Headers
	Content-Type:application/json
	Authorization:Basic dXNlcjpwYXNz
	
Body -
{
    "username": "Jill",
    "password": "pchange"
}

(4)DELETE - 
	http://localhost:9090/assignment/user/{username}
	
Headers
	Content-Type:application/json


