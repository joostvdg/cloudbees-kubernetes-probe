package com.github.joostvdg.cmd;

import com.github.joostvdg.model.ClientMaster;
import com.github.joostvdg.model.Network;
import com.github.joostvdg.model.OperationsCenter;
import com.github.joostvdg.model.Storage;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.AppsV1Api;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.apis.ExtensionsApi;
import io.kubernetes.client.openapi.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.openapi.models.*;
import io.kubernetes.client.util.Config;
import picocli.CommandLine;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@CommandLine.Command(name = "sts", description = "...",
        mixinStandardHelpOptions = true)
public class ListCBSts  implements Runnable {

    public static void main(String[] args) {
        CommandLine.run(new ListPodsDemo(), args);
    }


    public Network findNetwork(String name, String namespace) {
        Network network = new Network();

        String fieldSelector = "metadata.name=" + name;
        CoreV1Api api = new CoreV1Api();
        V1ServiceList serviceList;

        try {
            serviceList = api.listNamespacedService(namespace, null,null,null,fieldSelector,null,null,null,null,null);
            if (!serviceList.getItems().isEmpty()) {
                // we expect one and only one entry
                var service = serviceList.getItems().get(0);
                String serviceInfo = service.getSpec().getClusterIP();
                network.setService(serviceInfo);
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return network;
        }

        ExtensionsV1beta1IngressList ingressList;
        ExtensionsV1beta1Api extensionsApi = new ExtensionsV1beta1Api();
        try {
            ingressList = extensionsApi.listNamespacedIngress(namespace, null, null, null, fieldSelector, null, null, null, null, null);
            if (!ingressList.getItems().isEmpty()) {
                // we expect one and only one entry
                var ingress = ingressList.getItems().get(0);
                String hostname = ingress.getSpec().getRules().get(0).getHost();
                String path = ingress.getSpec().getRules().get(0).getHttp().getPaths().get(0).getPath();
                network.setHostname(hostname);
                network.setPath(path);
                boolean tls = false;
                if (ingress.getSpec().getTls() != null && !ingress.getSpec().getTls().isEmpty()) {
                    tls = true;
                }
                network.setTls(tls);
            }
        } catch (ApiException e) {
            e.printStackTrace();
            return network;
        }


        return network;
    }

    public Storage findStorage(String label) {
        Storage storage = new Storage();

        CoreV1Api api = new CoreV1Api();
        V1PersistentVolumeClaimList list;
        try {
            list = api.listPersistentVolumeClaimForAllNamespaces(null, null, null, label, null, null, null, null, null);
        } catch (ApiException e) {
            e.printStackTrace();
            return storage;
        }

        // we expect one and only one entry!
        if (!list.getItems().isEmpty() ) {
            var pvc = list.getItems().get(0);
            String persistentVolumeSize = calculateVolumeSize(pvc.getStatus().getCapacity());
            String persistentVolumeClaim = pvc.getMetadata().getName();
            String persistentVolume = pvc.getSpec().getVolumeName();
            String storageClass = pvc.getSpec().getStorageClassName();
            storage.setPersistentVolume(persistentVolume);
            storage.setPersistentVolumeClaim(persistentVolumeClaim);
            storage.setPersistentVolumeSize(persistentVolumeSize);
            storage.setStorageClass(storageClass);
        }
        return storage;
    }

    private String calculateVolumeSize(Map<String, Quantity> capacity) {
        String volumeSize = "";

        if (capacity.containsKey("storage")) {
            var storageCapacity = capacity.get("storage");
            if (storageCapacity.getFormat().equals(Quantity.Format.BINARY_SI)) {
                BigDecimal diviser = new BigDecimal("1024");
                int sizeInGb = storageCapacity.getNumber().divide(diviser).divide(diviser).divide(diviser).intValue();
                volumeSize = "" + sizeInGb + "Gi";
            }
        }

        return volumeSize;
    }


    public String parseVersionFromImage(String imageName) {
        String version = "";
        if (imageName.contains(":")) {
            int colonIndex = imageName.lastIndexOf(":");
            version = imageName.substring(colonIndex + 1);
        }

        return version;
    }

    private static final String CONTAINER_NAME = "jenkins";
    public String parseImageFromContainer(List<V1Container> containers) {
        for (V1Container container : containers) {
            if (CONTAINER_NAME.equals(container.getName())) {
                return parseVersionFromImage(container.getImage());
            }
        }
        return "";
    }

    public List<OperationsCenter> findOperationsCenters() {

        AppsV1Api api = new AppsV1Api();
        V1StatefulSetList list = null;
        String pretty = null;
        Boolean allowWatchBookmarks = null;
        String _continue = null;
        String fieldSelector = null;
        String labelSelector = "com.cloudbees.cje.tenant=cjoc";
        Integer limit = null;
        String resourceVersion = null;
        Integer timeoutSeconds = null;
        Boolean watch = null;

        try {
            list = api.listStatefulSetForAllNamespaces(allowWatchBookmarks, _continue, fieldSelector, labelSelector, limit,pretty, resourceVersion, timeoutSeconds, watch);
        } catch (ApiException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        List<OperationsCenter> operationsCenters = new ArrayList<>();
        for (V1StatefulSet sts : list.getItems() ) {
            String status = "" + sts.getStatus().getReadyReplicas() + "/" + sts.getStatus().getCurrentReplicas() ;
            String namespace = sts.getMetadata().getNamespace();
            OperationsCenter operationsCenter = new OperationsCenter();
            operationsCenter.setStatus(status);
            operationsCenter.setNamespace(namespace);
            var storage = findStorage("app=cjoc");
            operationsCenter.setStorage(storage);
            var network = findNetwork("cjoc", namespace);
            operationsCenter.setNetwork(network);
            String version = parseImageFromContainer(sts.getSpec().getTemplate().getSpec().getContainers());
            operationsCenter.setVersion(version);
            operationsCenters.add(operationsCenter);
        }

        return operationsCenters;
    }

    public List<ClientMaster> findClientMasters() {
        AppsV1Api api = new AppsV1Api();
        V1StatefulSetList list = null;
        String pretty = null;
        Boolean allowWatchBookmarks = null;
        String _continue = null;
        String fieldSelector = null;
        String labelSelector = "com.cloudbees.cje.type=master";
        Integer limit = null;
        String resourceVersion = null;
        Integer timeoutSeconds = null;
        Boolean watch = null;

        try {
            list = api.listStatefulSetForAllNamespaces(allowWatchBookmarks, _continue, fieldSelector, labelSelector, limit,pretty, resourceVersion, timeoutSeconds, watch);
        } catch (ApiException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }

        List<ClientMaster> clientMasters = new ArrayList<>();
        for (V1StatefulSet sts : list.getItems() ) {
            String name = "" + sts.getMetadata().getName();
            String status = "" + sts.getStatus().getReadyReplicas() + "/" + sts.getStatus().getCurrentReplicas() ;
            String namespace = sts.getMetadata().getNamespace();
            ClientMaster clientMaster = new ClientMaster();
            clientMaster.setName(name);
            clientMaster.setStatus(status);
            clientMaster.setNamespace(namespace);
            var storage = findStorage("com.cloudbees.cje.tenant=" + name);
            clientMaster.setStorage(storage);
            var network = findNetwork(name, namespace);
            clientMaster.setNetwork(network);
            String type = "Managed Master";
            if (sts.getMetadata().getName().startsWith("teams-")) {
                type = "Team Master";
            }
            String version = parseImageFromContainer(sts.getSpec().getTemplate().getSpec().getContainers());
            clientMaster.setVersion(version);
            clientMaster.setType(type);
            clientMasters.add(clientMaster);
        }

        return clientMasters;
    }

    @Override
    public void run() {
        try {
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);

            var operationsCenters = findOperationsCenters() ;
            var clientMasters = findClientMasters();

            operationsCenters.forEach(System.out::println);
            System.out.println("-----------------");
            clientMasters.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
