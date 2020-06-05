package com.github.joostvdg.model;

public class Network {

    private String service;
    private String ingress;
    private String hostname;
    private String path;
    private boolean tls;

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getIngress() {
        return ingress;
    }

    public void setIngress(String ingress) {
        this.ingress = ingress;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public boolean isTls() {
        return tls;
    }

    public void setTls(boolean tls) {
        this.tls = tls;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "{" +
                "service='" + service + '\'' +
                ", hostname='" + hostname + '\'' +
                ", path='" + path + '\'' +
                ", tls=" + tls +
                '}';
    }
}
