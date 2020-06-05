# cloudbees-kubernetes-probe

CLI for probing CloudBees resources in Kubernetes

## How To Use

* have JDK 11+ installed
* have Maven 3.6.x+ installed
* run `mvn package` 
* run `java -jar target/cloudbees-kubernetes-probe-0.1.jar sts`

## Labels

### StatefulSets

* OC: `com.cloudbees.cje.type=cjoc` 
* Masters: `com.cloudbees.cje.type=master` 
* Team Masters: ` com.cloudbees.cje.type=master` 

* cjoc  
    * `app.kubernetes.io/component=cjoc`
    * `app.kubernetes.io/instance=cloudbees-ci`
    * `app.kubernetes.io/name=cloudbees-core`
    * `com.cloudbees.cje.tenant=cjoc`
    * `com.cloudbees.cje.type=cjoc`
* mm1
    * `com.cloudbees.cje.tenant=mm1`
    * `com.cloudbees.cje.type=master`
    * `com.cloudbees.pse.tenant=mm1`
    * `com.cloudbees.pse.type=master`
    * `tenant=mm1`
    * `type=master`
* teams-cat 
    * `com.cloudbees.cje.tenant=teams-cat`
    * `com.cloudbees.cje.type=master`
    * `com.cloudbees.pse.tenant=teams-cat`
    * `com.cloudbees.pse.type=master`
    * `tenant=teams-cat`
    * `type=master`

Main info from StatefulSet

```json
{
    "metadata": {
        "name": "cjoc",
        "namespace": "cloudbees-ci"
    }
}
```

### Pods

* `statefulset.kubernetes.io/pod-name=teams-cat-0`

## Resources To List

* StatefulSet
* Pod
* Ingress
* Service
* PVC
* PV 