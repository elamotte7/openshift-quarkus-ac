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

To keep it as simple as possible, we will use Code Ready Container as Openshift cluster. 

````shell script
$ crc start
````

````shell script
$ crc console
````

````shell script
$ crc oc-env
````

Login to openshift

````shell script
$ oc login -u developer -p developer https://api.crc.testing:6443
````

Create the project

````shell script
$ oc new-project quarkus-apero-code --description="Example of quarkus app deploy automatically in a openshift cluster" --display-name="quarkus-apero-code"
````

Then launch

````shell script
$ ./mvnw clean package -Dquarkus.profile=os -Dquarkus.kubernetes.deploy=true
````

the console output

````properties
[INFO] [io.quarkus.container.image.openshift.deployment.OpenshiftProcessor] Push successful
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Deploying to openshift server: https://api.lab.ocp.lan:6443/ in namespace: quarkus-apero-code.
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Applied: Service openshift-quarkus-ac.
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Applied: ImageStream openjdk-11.
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Applied: ImageStream openshift-quarkus-ac.
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Applied: BuildConfig openshift-quarkus-ac.
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Applied: DeploymentConfig openshift-quarkus-ac.
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] Applied: Route openshift-quarkus-ac.
[INFO] [io.quarkus.kubernetes.deployment.KubernetesDeployer] The deployed application can be accessed at: http://openshift-quarkus-ac-quarkus-apero-code.apps.lab.ocp.lan
[INFO] [io.quarkus.deployment.QuarkusAugmentor] Quarkus augmentation completed in 85106ms
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
````

Check if everything is well deployed

````shell script
$ oc get is -n quarkus-apero-code
$ oc get pods -n quarkus-apero-code
$ oc get svc -n quarkus-apero-code
````

Test it

Clic on the link at the end of the log of the build

# Keycloak

Get the token for api

````shell script
curl --insecure -X POST https://keycloak-keycloak.apps.lab.ocp.lan/auth/realms/quarkus/protocol/openid-connect/token \
--user backend-service:secret \
-H 'content-type: application/x-www-form-urlencoded' \
-d 'username=admin&password=admin&grant_type=password' | jq --raw-output '.access_token'
````

Test the api

````shell script
curl -v -X GET \
  http://openshift-quarkus-ac-quarkus-apero-code.apps.lab.ocp.lan/api/users/me \
  -H "Authorization: Bearer "<access_token>
````