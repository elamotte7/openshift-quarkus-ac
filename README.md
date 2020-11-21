# openshift-quarkus-ac project

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: https://quarkus.io/ .

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:
```
./mvnw quarkus:dev
```

## Packaging and running the application

The application can be packaged using `./mvnw package`.
It produces the `openshift-quarkus-ac-1.0-SNAPSHOT-runner.jar` file in the `/target` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/lib` directory.

The application is now runnable using `java -jar target/openshift-quarkus-ac-1.0-SNAPSHOT-runner.jar`.

## Creating a native executable

You can create a native executable using: `./mvnw package -Pnative`.

Or, if you don't have GraalVM installed, you can run the native executable build in a container using: `./mvnw package -Pnative -Dquarkus.native.container-build=true`.

You can then execute your native executable with: `./target/openshift-quarkus-ac-1.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult https://quarkus.io/guides/building-native-image.

## Deploy to openshift

### Prerequisite

Have an Openshift installed and set up.

configure you docker registry, go to application.properties and modify with your specific properties

````
# Registry
%openshift.quarkus.container-image.registry=${K8S_REGISTRY_URL:<your-docker-repository>}
%openshift.quarkus.container-image.username=${K8S_REGISTRY_USERNAME:<your-username-docker-repository>}
%openshift.quarkus.container-image.password=${K8S_REGISTRY_PASSWORD:<your-password-docker-repository>}
%openshift.quarkus.openshift.image-pull-secrets=${K8S_REGISTRY_PASSWORD:<your-password-docker-repository>}
````

crc oc-env

Create the project

````shell script
$ oc new-project quarkus-apero-code --description="Example of quarkus app deploy automatically in a openshift cluster" --display-name="quarkus-apero-code"
````

Login to openshift

````shell script
$ oc login -u developer -p developer https://api.crc.testing:6443
````

Then launch

````shell script
$ ./mvnw clean package -Dquarkus.kubernetes.deploy=true -Dquarkus.profile=openshift
````

Check if everithing is well deployed

````shell script
$ oc get is -n quarkus-apero-code
$ oc get pods -n quarkus-apero-code
$ oc get svc -n quarkus-apero-code
````

Test it

````shell script
$ oc expose svc/openshift-quarkus-ac -n quarkus-apero-code
$ oc get routes -n quarkus-apero-code
$ curl http://<route>/openshift-quarkus-ac
````
