package com.codepath.apps.learnfitness.models;

/**
 * Created by spandhare on 3/22/16.
 */
public class ClientRegistration {
    String status;

    public ClientRegistration(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
