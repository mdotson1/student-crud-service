to compile and package war file:  
mvn package

to run locally (set PORT environment variable or will default to 8080):  
foreman start

CURL POST syntax:  
curl -X POST -H "Content-Type: application/json" -d '{"firstName":"Michael","lastName":"Dotson"}' http://student-crud-service.herokuapp.com/rest/students

CURL GET syntax:  
curl http://student-crud-service.herokuapp.com/rest/students

CURL PUT syntax:
curl -X PUT -H "Content-Type: application/json" -d '{"firstName":"Helena","lastName":"Dotson"}' localhost:5000/rest/students/{id}

CURL DELETE syntax:
curl -X DELETE localhost:5000/rest/students

App URL:  
http://student-crud-service.herokuapp.com/rest/students

bitbucket URL:  
https://bitbucket.org/loyolachicagocs_healthcare/student-crud-service

OKV URL:  
http://api.openkeyval.org/{id}
