package com.github.joostvdg.cmd;

import io.kubernetes.client.openapi.ApiClient;
import io.kubernetes.client.openapi.ApiException;
import io.kubernetes.client.openapi.Configuration;
import io.kubernetes.client.openapi.apis.CoreV1Api;
import io.kubernetes.client.openapi.models.V1Pod;
import io.kubernetes.client.openapi.models.V1PodList;
import io.kubernetes.client.util.Config;
import picocli.CommandLine;

import java.io.IOException;

@CommandLine.Command(name = "list-po", description = "...",
        mixinStandardHelpOptions = true)
public class ListPodsDemo  implements Runnable {

    public static void main(String[] args) {
        CommandLine.run(new ListPodsDemo(), args);
    }

    @Override
    public void run() {
        try {
            ApiClient client = Config.defaultClient();
            Configuration.setDefaultApiClient(client);

            CoreV1Api api = new CoreV1Api();
            V1PodList list = null;
            list = api.listPodForAllNamespaces(null, null, null, null, null, null, null, null, null);

            for (V1Pod item : list.getItems()) {
                System.out.println(item.getMetadata().getName());
            }
        } catch (ApiException | IOException e) {
            e.printStackTrace();
        }
    }

}
