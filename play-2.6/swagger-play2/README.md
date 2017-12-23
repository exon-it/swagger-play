[![Build Status](https://travis-ci.org/rayyildiz/swagger-play.svg?branch=master)](https://travis-ci.org/rayyildiz/swagger-play)

# Swagger Play2 Module

## Overview
This is a module to support the play2 framework from [playframework](http://www.playframework.org).  It is written in scala but can be used with either java or scala-based play2 applications.

## Version History

* `by.exonit:swagger-play2-play26` supports play 2.6 and swagger 2.0.

Usage
-----

You can depend on pre-built libraries in maven central by adding the following dependency:

```
libraryDependencies ++= Seq(
  "by.exonit" %% "swagger-play2-play26" % "1.0.0-SNAPSHOT"
)
```

Or you can build from source.

```
cd play-2.6/swagger-play2

sbt publishLocal
```

### Adding Swagger to your Play2 app

There are just a couple steps to integrate your Play2 app with swagger.

1\. Add the Swagger module to your `application.conf`
 
```
play.modules.enabled += "play.modules.swagger.SwaggerModule"
```
 
2\. Add the resource listing to your routes file (you can read more about the resource listing [here](https://github.com/swagger-api/swagger-core/wiki/Resource-Listing))

```
GET     /swagger.json           controllers.ApiHelpController.getResources
```

You can generate Swagger resource listing for specific path, which is useful for versioned APIs:
```
# API v1 routes are defined in another routing config
->         /api/v1/                         api_v1.Routes

GET        /api/v1/swagger.json             controllers.ApiHelpController.getResource(path = "/api/v1")
```

3\. Annotate your REST endpoints with Swagger annotations. This allows the Swagger framework to create the [api-declaration](https://github.com/swagger-api/swagger-core/wiki/API-Declaration) automatically!

In your controller for, say your "pet" resource:

```scala
  @ApiResponses(Array(
    new ApiResponse(code = 400, message = "Invalid ID supplied"),
    new ApiResponse(code = 404, message = "Pet not found")))
  def getPetById(
    @ApiParam(value = "ID of the pet to fetch") id: String) = Action {
    implicit request =>
      petData.getPetbyId(getLong(0, 100000, 0, id)) match {
        case Some(pet) => JsonResponse(pet)
        case _ => JsonResponse(new value.ApiResponse(404, "Pet not found"), 404)
      }
  }

```

What this does is the following:

* Tells swagger that the methods in this controller should be described under the `/api-docs/pet` path

* The Routes file tells swagger that this API listens to `/{id}`

* Describes the operation as a `GET` with the documentation `Find pet by Id` with more detailed notes `Returns a pet ....`

* Takes the param `id`, which is a datatype `string` and a `path` param

* Returns error codes 400 and 404, with the messages provided

In the routes file, you then wire this api as follows:

```
GET     /pet/:id                 controllers.PetApiController.getPetById(id)
```

This will "attach" the /api-docs/pet api to the swagger resource listing, and the method to the `getPetById` method above

Please note that the minimum configuration needed to have a route/controller be exposed in swagger declaration is to have an `Api` annotation at class level.

#### The ApiParam annotation

Swagger for play has two types of `ApiParam`s--they are `ApiParam` and `ApiImplicitParam`.  The distinction is that some
paramaters (variables) are passed to the method implicitly by the framework.  ALL body parameters need to be described
with `ApiImplicitParam` annotations.  If they are `queryParam`s or `pathParam`s, you can use `ApiParam` annotations.


# application.conf - config options
```
api.version (String) - version of API | default: empty, will be replaced with "beta" if not set
swagger.api.basepath (String) - base url | default: "http://localhost:9000"
swagger.filter (String) - classname of swagger filter | default: empty
swagger.api.info = {
  contact : (String) - Contact Information | default : empty,
  description : (String) - Description | default : empty,
  title : (String) - Title | default : empty,
  termsOfService : (String) - Terms Of Service | default : empty,
  license : (String) - Terms Of Service | default : empty,
  licenseUrl : (String) - Terms Of Service | default : empty
}
```

## Note on Dependency Injection
This plugin works by default if your application uses Runtime dependency injection.

Nevertheless, a helper is provided `SwaggerApplicationLoader` to ease the use of this plugin with Compile Time Dependency Injection. 


## License

```
Copyright 2017 Exon IT
Copyright 2017 SmartBear Software

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at [apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
