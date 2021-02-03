package com.lumata.visume.config.msal;

import java.util.Date;

class StateData {
    private String nonce;
    private Date expirationDate;

    StateData(String nonce, Date expirationDate) {
        this.nonce = nonce;
        this.expirationDate = expirationDate;
    }

    String getNonce() {
        return nonce;
    }

    Date getExpirationDate() {
        return expirationDate;
    }
}