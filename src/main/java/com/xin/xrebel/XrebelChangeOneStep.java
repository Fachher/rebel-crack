package com.xin.xrebel;

import com.xin.common.NoChangeClass;

import java.io.File;
import java.net.URL;
import java.net.URLDecoder;

import static com.xin.common.CrackPrint.scanAndChangeClass;


public class XrebelChangeOneStep {

    public static void main(String[] args) throws Exception {

        final URL resource = ClassLoader.getSystemClassLoader().getResource("xrebel/xrebel.jar");
        File sourcePath = new File(URLDecoder.decode(resource.getFile(), "utf-8"));

        final File destFile = new File("G:\\workspaces\\idea\\维护\\instant-invoke\\instant-invoke-plugin\\resources\\advance\\xrebel\\xrebel.jar");
        destFile.delete();
        scanAndChangeClass(sourcePath
                , destFile
                , jarOutputStream -> {
                }, cls -> {
                    try {
                        if (ChangeClass_lr.validate(cls)) {
                            return ChangeClass_lr.crackLicenseValidate(cls);
                        }
                        if (ChangeClass_n.validate(cls)) {
                            return ChangeClass_n.crackContextValidate(cls);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return NoChangeClass.changeToNoChange(cls);
                });
        return;
    }

}
