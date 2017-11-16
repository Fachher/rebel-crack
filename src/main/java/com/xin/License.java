package com.xin;

import java.util.List;

/**
 * @author linxixin@cvte.com
 */
public class License {

    public final String       id;
    public final String       serverRandomness;
    public final String       clientRandomness;
    public final String       guid;
    public final String       offline;
    public final String       validFrom;
    public final String       validUntil;
    public final String       seatPoolType;
    public final String       signature;
    public final String       company;
    public final String       orderId;
    public final List<String> zeroIds;
    public final boolean      migrationNeeded;

    public License(String id, String serverRandomness, String clientRandomness, String guid, String offline, String validFrom, String validUntil, String seatPoolType, String signature, String company, String orderId, List<String> zeroIds, boolean migrationNeeded) {
        this.id = id;
        this.serverRandomness = serverRandomness;
        this.clientRandomness = clientRandomness;
        this.guid = guid;
        this.offline = offline;
        this.validFrom = validFrom;
        this.validUntil = validUntil;
        this.seatPoolType = seatPoolType;
        this.signature = signature;
        this.company = company;
        this.orderId = orderId;
        this.zeroIds = zeroIds;
        this.migrationNeeded = migrationNeeded;
    }

    public String getId() {
        return id;
    }

    public String getServerRandomness() {
        return serverRandomness;
    }

    public String getClientRandomness() {
        return clientRandomness;
    }

    public String getGuid() {
        return guid;
    }

    public String getOffline() {
        return offline;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public String getValidUntil() {
        return validUntil;
    }

    public String getSeatPoolType() {
        return seatPoolType;
    }

    public String getSignature() {
        return signature;
    }

    public String getCompany() {
        return company;
    }

    public String getOrderId() {
        return orderId;
    }

    public List<String> getZeroIds() {
        return zeroIds;
    }

    public boolean isMigrationNeeded() {
        return migrationNeeded;
    }
}
