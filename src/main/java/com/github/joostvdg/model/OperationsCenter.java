package com.github.joostvdg.model;

public class OperationsCenter {

    private String namespace;
    private String status;
    private String version;
    private Storage storage;
    private Network network;

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Storage getStorage() {
        return storage;
    }

    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "OperationsCenter{" +
                "namespace='" + namespace + '\'' +
                ", status=" + status + "\n" +
                ", version=" + version + "\n" +
                ", storage=" + storage + "\n" +
                ", network=" + network + "\n" +
                '}';
    }
}
