# SFTP File Poller using Fuse Integration Services (FIS)

This example demonstrates how to consume a file from a remote SFTP server and copy it to either a local directory or persistent volume (PV).

## Prerequisites

The following are required to run this project:

1. JDK 1.8 or newer
2. Maven 3.3 or newer
3. An SFTP server

### Building


1. Update the `support\sample-settings.xml` file with your local maven respository path (located on line 3)

2. The example can be built with

    mvn -s support/sample-settings.xml clean install
    
Additionally, please ensure the following has been configured:

(1) Update `/src/main/resources/application.properties` with the necessary credentials for the external SFTP server

### Running the example in SpringBoot

The example can be run as a standalone SpringBoot instance by executing:

	mvn -s support/settings.xml spring-boot:run

### Running the example in Docker

To build to the project:

    mvn -s support/settings.xml clean package docker:build   
        
    docker run -t example/fis-ftp-mft
     
Copy a file over to your SFTP server to test the flow.

After you've finished running the docker example, be sure to tidy up:

     docker ps (to obtain the container ID)
     docker stop <container ID>
     docker rm <container ID>
     
### Viewing the Fuse Management Console (HawtIO)

Once the application has started up successfully, in any web browser, go to `localhost:8080/hawtio/index.html` and select the Camel tab. You should see your Camel Routes running.

### Running the example on OpenShift

1. Login to OpenShift via the oc CLI using your credentials.
2. Create a new project e.g. `oc create project fis-ftp-mft`
3. Deploy the project to OpenShift

To deploy to the project:
```
    mvn -s support/settings.xml fabric8:deploy
```