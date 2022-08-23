_Tires & Tune-Ups_ is a slowly growing automobile service company, with a few service locations around town. 
Each service shop offers a set of available services, though not every service is available at every location.
The company offers a self-service portal, where customers can view and book appointments online.
A customer can specify a shop location, a start time, and a list of services they'd like done.
When booking the appointment, the system validates if the requested services are available at the desired service shop.

The online portal is powered by several applications, but we need your help to improve the web service that manages appointments.

## This Application's Purpose
* Allows customers to retrieve their upcoming appointments
* Allows customers to create an appointment
* Allows employees to complete an appointment

## Call To Action:
This Appointment web service is a REST based service.
We need your help to resolve the issues submitted below. Verifying solutions with tests is important (unit, integration, or both).
All changes must be backwards compatible to not affect consumers. Feel free to add any Maven dependencies you need.
You won't be able to start the application, but the included integration tests provide great runtime feedback for debugging.
The included automated tests are expected to pass even after the below issues are resolved.

### Specific Issues:
* Current Reported Issues:
  * Customers are sometimes able to create appointments with services that aren't available at the desired shop location.
  * Need an endpoint to edit an existing appointments start time. This should only be allowed for appointments that aren't completed.
* Other observations:
  * IT Support has trouble debugging production problems
  * Appointment booking sometimes returns 201 when an appointment failed to create
  * Unreliable network causes frequent failed requests to Shop Web Service
