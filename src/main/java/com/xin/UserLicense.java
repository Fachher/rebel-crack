package com.xin;

import java.io.Serializable;

/**
 * @author linxixin@cvte.com
 */
public class UserLicense implements Serializable{
    private byte[] license;
    private byte[] signature;

    public void setLicense(byte[] license) {
        this.license = license;
    }

    public byte[] getLicense() {
        return license;
    }

    public void setSignature(byte[] signature) {
        this.signature = signature;
    }

    public byte[] getSignature() {
        return signature;
    }
}
