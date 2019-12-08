
Software Design  
===============  
  
## Table of Contents  
* [Purpose](#purpose)  
* [High level architecture](#high-level-architecture)  
  * [Architecture pattern](#Architecture-pattern)  
  * [Application type](#Application-type)  
  * [Persistence](#Persistence)  
* [Project file structure](#Project-file-structure)  
* [Layers](#Layers)  
  * [Presentation and API layers](#Presentation-and-API-layers)  
  * [Presentation layer](#Presentation-layer)  
  * [API layer](#api-layer)  
  * [Business layer](#business-layer)  
  * [Persistence layer](#persistence-layer)  
  
  
## Purpose  
The purpose of this document is to provide a detailed description of the design of the software.  
  
## High level architecture  
### Architecture pattern  
The [multitier architecture pattern](https://en.wikipedia.org/wiki/Multitier_architecture) is used in this project.    
  
### Application type  
Single WAR Jakarta EE 8 web application.  
  
### Persistence  
The application uses an SQL database with geospatial feature set through JPA; standard files for binary data.  
  
## Project file structure  
The project is organised into the [standard maven directory layout](https://maven.apache.org/guides/introduction/introduction-to-the-standard-directory-layout.html).  
  
## Layers  
The application comprises of the following layers:  
  
* Presentation and API layer  
* Business layer  
* Persistence layer  
  
### Presentation and API layers  
Since the presentation layer and the API layer has similar functionality they  
are on the same level in the hierarchy. The main purpose of these layers is to expose  
the application's features to the users.  
  
### Presentation layer  
The presentation layer includes the [JSF](https://en.wikipedia.org/wiki/JavaServer_Faces) UI implementation    
using [Primefaces](https://www.primefaces.org/#primefaces) and [Omnifaces](http://omnifaces.org/).  
It consists of the necessary Facelets (xhtml files), backing beans, filters, converters   
and other UI related classes.   
  
#### Packages  
* `net.potm.web.jsf`  
  
Classes that belong to the Presentation layer should be placed in the `net.potm.web.jsf` package and organized into  
sub-packages considering their purpose (like user_profile or activation) and not their technical function (e.g.: controllers in a .controller package).    
  
#### Interaction with other layers  
- The presentation layer may call any public method in the Business layer.  
- The presentation layer must not call any method in the API layer.  
- The presentation layer must not call any method in the Persistence layer.   
  
### Api layer  
The api layer is implemented using [JAX-RS](https://en.wikipedia.org/wiki/Java_API_for_RESTful_Web_Services).  
  
#### Package  
* `net.potm.web.rest`  
  
Classes that belong to the API layer should be placed in the `net.potm.web.rest` package and organized into  
sub-packages considering their purpose (like user_profile or activation) and not their technical function.  
  
#### Interaction with other layers  
- The API layer may call any public method in the Presentation layer.  
- The API layer must not call any method in the API layer.  
- The API layer must not call any method in the Persistence layer.  
  
### Business layer  
Business logic is implemented in local EJB session beans.  
  
#### Packages  
* `net.potm.business.api` - interfaces  
* `net.potm.business.impl` - implementation  
  
#### Interaction with other layers  
- The Business layer may call any public method in the Persistence layer.  
- The Business layer must not call any method in the API layer.  
- The Business layer must not call any method in the Presentation layer.  
  
### Persistence layer  
The persistence layer consists of  
* Models that represent database tables  
* Services that perform actions on the database using the models as input and/or output.  
  
#### Package  
* `net.potm.persistence`  
  
#### Interaction with other layers  
- The Persistence layer must not call any method in the Business layer.  
- The Persistence layer must not call any method in the API layer.  
- The Persistence layer must not call any method in the Presentation layer.