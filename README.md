 ## Spring Boot REST Application ##
 
 This is a Spring Boot Application with a full-fledged REST API service exposed with Swagger 2.0 UI.
 
 __*The this application is hosted on Microsoft Azure Cloud.**__
 
 Click the following link to access the  __[DEMO](http://appdemos.org)__ 
 
Default 2 users in the system and corresponding details for Basic in-memory login

| User          | Password      |
| ------------- |:-------------:|
| admin         | *password*    |
| user          | *password*    |

## Technology Stack ##
 * Spring Boot
 * Spring Rest
 * Swagger 2.0
 * Docker Container
 * Open JDK 8
 * Maven 3
 
 # Build Instructions
 If you need to run the project on Eclipse or IntelliJ following steps would help for a quick setup
 
 1. Open pom.xml with Intelij or Eclipse
 2. Run Main.java
 3. Open browser on http://localhost:8080/swagger-ui.html
 
 # Docker Run Command
 If you require to directly run the service in docker environment then execute the following command to automatically pull and deploy locally from public docker cloud repository.
 
 `docker run -d -p 8080:8080 shouriendoc/springapp:1.0-SNAPSHOT`
 
 # Application Guide
 
 * The landing page for this application is the Swagger based 2.0 UI. ![Landing Page](https://i.imgur.com/nzR3fVz.png, "Landing Page")
 
 * Two Rest resources exposed to the user for invoking API's. The corresponding documentation detailing the request/response and the ability to invoke the apis is also provided by Swagger seamlessly. ![API Listing](https://i.imgur.com/sJR7d0i.png, "Rest Resource Listing")
 
 * Following is an example to invoke the REST API below. This is the REST API Search's the passed list of strings in the provided text and return the counts respectively. ![Invoke API](https://i.imgur.com/KdVImga.png, "Rest API to file word count") 
 
* The REST response of the API as provided by Swagger. ![REST Response](https://i.imgur.com/WYTAQYk.png, "Rest API response to file word count")
