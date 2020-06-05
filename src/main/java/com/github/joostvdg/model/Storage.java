package com.github.joostvdg.model;

public class Storage {

    private String persistentVolumeClaim;
    private String persistentVolume;
    private String persistentVolumeSize;
    private String storageClass;

    public String getPersistentVolumeClaim() {
        return persistentVolumeClaim;
    }

    public void setPersistentVolumeClaim(String persistentVolumeClaim) {
        this.persistentVolumeClaim = persistentVolumeClaim;
    }

    public String getPersistentVolume() {
        return persistentVolume;
    }

    public void setPersistentVolume(String persistentVolume) {
        this.persistentVolume = persistentVolume;
    }

    public String getPersistentVolumeSize() {
        return persistentVolumeSize;
    }

    public void setPersistentVolumeSize(String persistentVolumeSize) {
        this.persistentVolumeSize = persistentVolumeSize;
    }

    public String getStorageClass() {
        return storageClass;
    }

    public void setStorageClass(String storageClass) {
        this.storageClass = storageClass;
    }

    @Override
    public String toString() {
        return "{" +
                "persistentVolumeClaim='" + persistentVolumeClaim + '\'' +
                ", persistentVolume='" + persistentVolume + '\'' +
                ", persistentVolumeSize='" + persistentVolumeSize  + '\'' +
                ", storageClass='" + storageClass + '\'' +
                '}';
    }
}
