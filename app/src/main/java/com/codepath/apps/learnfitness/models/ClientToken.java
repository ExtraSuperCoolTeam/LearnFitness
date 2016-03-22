package com.codepath.apps.learnfitness.models;

/**
 * Created by spandhare on 3/22/16.
 */
public class ClientToken {
    String clientId;
    String token;

    public ClientToken(String clientId, String token) {
        this.clientId = clientId;
        this.token = token;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
