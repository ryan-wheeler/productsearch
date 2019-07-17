# ProductSearch

ProductSearch is a restful web service that supports searching for and filtering products.

## Starting the application

IDE - import the project as a Gradle project, and then select the 'booRun' target from the Gradle tool panel

Terminal -
         - mac/linux : navigate to the source directory and call './gradlew bootrun'
         - windows : navigate to the source directory and call 'gradlew.bat bootrun'
         
After starting the application, the api can be exercised using the Swagger page at:
http://localhost:8080/swagger-ui.html


## Note
Tests in the 'integration' folder will not be run as part of the build. (They can be run manually.)