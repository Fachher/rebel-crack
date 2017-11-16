package com.xin;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;


/**
 * @author linxixin@cvte.com
 */
public class CreateLicFile {
    public static void main(String[] args) throws IOException,  ClassNotFoundException {
        String userHome = System.getProperty("user.home");
//
//        final JRebelLicensingCLI jRebelLicensingCLI = new JRebelLicensingCLI();
//        final LicensingStatus licensingStatus =
//                jRebelLicensingCLI.activateLicenseFile(
//                        new File("C:\\Users\\xin\\.xrebel\\xrebel.lic"));
        HashMap<String, Object> crackMsg = new HashMap<>();
        crackMsg.put("Comment", "xxin");
        crackMsg.put("commercial", "false");
        crackMsg.put("Organization", "anonymous-user");
        crackMsg.put("limited", "false");
        crackMsg.put("enterprise", "false");
        crackMsg.put("Product", "XREBEL");
        crackMsg.put("validFrom", new Date());
        crackMsg.put("version", "1.x");
        crackMsg.put("Name", "xxin");
        crackMsg.put("Email", "497668869@qq.com");
        crackMsg.put("OrderId", "1234567");
        crackMsg.put("uid", "uidddddddddd");
        crackMsg.put("Seats", "1111");
        crackMsg.put("ZeroId", "ZeroIdZeroIdZeroId");
        crackMsg.put("validDays", -1);
        crackMsg.put("Type", "evaluation");
        crackMsg.put("noBanner", "true");
        crackMsg.put("validUntil", new Date(System.currentTimeMillis() + 365L * 24 * 60 * 60 * 1000));
        crackMsg.put("override", "true");

        UserLicense userLicense = new UserLicense();
        userLicense.setLicense(objectToByte(crackMsg));
        userLicense.setSignature(objectToByte(crackMsg));

        System.out.println(new String(Base64.getEncoder().encode(objectToByte(userLicense))));
    }

    private static byte[] objectToByte(Serializable serializable) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(serializable);
            objectOutputStream.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (IOException v0) {
            return null;
        }
    }

}
