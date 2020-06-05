package com.github.joostvdg.model;

public class ClientMaster {

    private String name;
    private String namespace;
    private String status;
    private String type;
    private Storage storage;
    private Network network;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "ClientMaster{" +
                "name='" + name + '\'' +
                ", namespace='" + namespace + '\'' + "\n" +
                ", status='" + status + '\'' + "\n" +
                ", type='" + type + '\'' + "\n" +
                ", storage=" + storage + "\n" +
                ", network=" + network + "\n" +
                '}';
    }
}
