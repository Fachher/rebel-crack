package com.xin.xrebel;

import com.xin.common.NoChangeClass;
import jdk.internal.org.objectweb.asm.ClassReader;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.jar.JarEntry;

import static com.xin.common.AddPrintParamClass.changeToAddPrintParamClass;
import static com.xin.common.CrackPrint.scanAndChangeClass;


public class XrebelChange {

    public static void main(String[] args) throws Exception {
        final File destFile = new File("C:\\Users\\xin\\.IntelliJIdea2017.2\\system\\plugins-sandbox3\\plugins\\instant-invoke-plugin\\classes\\advance\\xrebel.jar");
        destFile.delete();
        scanAndChangeClass(new File("G:\\workspaces\\idea\\维护\\rebel-crack\\src\\main\\resources\\xrebel\\xrebel.jar")
                , destFile
                , jarOutputStream -> {
                    try {
                        jarOutputStream.putNextEntry(new JarEntry("Mylog.class"));
                        jarOutputStream.write(IOUtils.toByteArray(new FileInputStream("G:\\workspaces\\idea\\维护\\rebel-crack\\target\\classes\\Mylog.class")));
                        jarOutputStream.closeEntry();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }, classByte -> {
                    try {
//                        if (cls.getName().startsWith("com.zeroturnaround.xrebel.bundled.com.fasterxml")
//                                || cls.getName().startsWith("com.zeroturnaround.xrebel.bundled.javassist")
//                                || cls.getName().startsWith("com.zeroturnaround.xrebel.bundled.com.google")
//                                ) {
//                            return NoChangeClass.changeToNoChange(cls);
//                        }
                        if (ChangeClass_lr.validate(classByte)) {
                            return ChangeClass_lr.crackLicenseValidate(classByte);
                        }
                        if (ChangeClass_n.validate(classByte)) {
                            return ChangeClass_n.crackContextValidate(classByte);
                        }
                        ClassReader classReader = new ClassReader(classByte);
                        if ((classReader.getClassName().startsWith("com.zeroturnaround.xrebel.licensing")
                                || classReader.getClassName().equals("com.zeroturnaround.xrebel")
                                || classReader.getClassName().startsWith("com.zeroturnaround.xrebel.lD"))
                                || classReader.getClassName().equals("com.zeroturnaround.xrebel.ll")
                                || classReader.getClassName().equals("com.zeroturnaround.xrebel.fs")
                                ) {
                            return changeToAddPrintParamClass(classByte);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return NoChangeClass.changeToNoChange(classByte);
                });
        return;
    }

}
