# cloudbees-kubernetes-probe

CLI for probing CloudBees resources in Kubernetes

## How To Use

* have JDK 11+ installed
* have Maven 3.6.x+ installed
* run `mvn package` 
* run `java -jar target/cloudbees-kubernetes-probe-0.1.jar sts`

### Result

It should print a table like this:

```sh

           │ Main                                               │ Network                                                 │ Storage
           ├──────────────┬────────┬───────────┬────────────────┼────────────────┬──────────────────┬─────────────┬───────┼──────────────────────────┬──────────────────────────────────────────┬──────┬──────────
 Name      │ namespace    │ status │ version   │ type           │ service        │ hostname         │ path        │ tls   │ pvc                      │ pv                                       │ size │ class
───────────┼──────────────┼────────┼───────────┼────────────────┼────────────────┼──────────────────┼─────────────┼───────┼──────────────────────────┼──────────────────────────────────────────┼──────┼──────────
      cjoc │ cloudbees-ci │    1/1 │ 2.222.4.3 │           CJOC │  10.110.134.94 │ 127.0.0.1.nip.io │       /cjoc │ false │      jenkins-home-cjoc-0 │ pvc-ff7475eb-1a80-44c8-8abf-660ffcc7e37a │ 20Gi │ hostpath
 teams-cat │ cloudbees-ci │    1/1 │ 2.222.4.3 │    Team Master │  10.106.147.20 │ 127.0.0.1.nip.io │ /teams-cat/ │ false │ jenkins-home-teams-cat-0 │ pvc-0a05200f-94df-4771-8470-d476402eeb0e │ 50Gi │ hostpath
       mm2 │          mm2 │    1/1 │ 2.222.4.3 │ Managed Master │ 10.111.175.205 │ 127.0.0.1.nip.io │       /mm2/ │ false │       jenkins-home-mm2-0 │ pvc-ec0eb687-b1b5-4638-b733-ddbe8ad9712d │ 50Gi │ hostpath
```

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