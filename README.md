# func_javatest

OpenFaaS Java8 Template with multistage build

## Purpose

This template is going to be used by CODESIGN2 for OpenFaaS projects using Java to provide ad-hoc or 
highly scalable serverless functions. The design of all OpenFaaS functions is to receive data into 
`STDIN` via pipe, and return results from `STDOUT`.

This template provides the following.

* support for gradle build system
* support for maven build system
* resolve dependencies during builder stage of docker build
* run tests during builder stage of Docker build
* use official openJDK builds of docker on alpine

## Goals

The goal is to allow existing and new Java code to be authored and used as a serverless function 
for OpenFaaS.

## Usage

### Getting the template

```
git clone https://github.com/CODESIGN2/func_javatest func_${name_of_your_function}
cd func_${name_of_your_function}
```

### building the template

Building requires a docker environment, which is an OpenFaaS dependency. It should be noted that 
a working OpenFaaS install is a pre-requisite for usage, but only docker for building

```
bash ./build.sh
```

For a guide on setting up OpenFaaS check out [official docs](https://github.com/openfaas/faas/tree/master/docs)

### Using the function

After successfully building the image, having installed OpenFaaS, you can easily deploy a 
function with just the following line (from within the repo)

```
docker stack deploy func --compose-file ./javatest.yml 
```

This defaults to a service name of `func_javatest` in OpenFaaS which is because of the func 
prefix to the docker stack command. The YAML file is documented at OpenFaaS, but is very humanly 
readable as-is. OpenFaaS also provides a CLI tool to make administering much easier.

### Updating the function

Releasing a new version is as simple as building and running the command from [Using the function]

It should be noted this is also not the way the OpenFaaS CLI manages updating functions. 
For now it seemed simpler to test with as few moving parts as possible. This has also not 
been tested with FaaS-Netes for Kubernetes (still uses Docker so should be fine)

### Using existing code

By removing the Handler folder you can clone your own Docker Maven project (master branch), 
or switch to gradle branch and replace the Handler folder there. If you have different output 
paths, or do not package a Fat-Jar (Jar with dependencies bundled), you may have additional 
work to complete to ensure the Dockerfile works without issue. This has been tested with a 
vanilla maven project `mvn archetype:generate -DgroupId=com.openfaas.cli -DartifactId=Handler` 
using defaults with a custom package name and a single dependency to demonstrate dependency 
resolution during build-step.

## Design choices

This keeps the build dependency resolution, basic-testing as part of the Docker image 
generation using multi-stage builds, with best-practices outlined in the docker-library 
OpenJDK build.

For the resulting runtime the default code also runs without privileges as part of OpenFaaS 
design for function templates. If it's required to maintain builds with feature toggles, 
because the build is separate to the Handler folder, it should be trivial to branch and add 
build specific environment variables to branches and build multiple branches in parallel 
using one code-base in the Handler (git submodule) to test and deploy diverse features in 
a single code-base.

## Notes

OpenFaaS May diverge from this Java template. Due care and attention should be taken to look 
at what OpenFaaS is doing, and follow their best practices to ensure compatibility with the 
OpenFaaS serverless platform. This is an incubator project, feel free to raise issues to 
document, suggest and PR's to improve. 

There is no warranty implied or otherwise, use at your own risk yadda yadda. Support can 
only be guaranteed to current CODESIGN2 customers with active paid-up accounts using a 
development support plan.
