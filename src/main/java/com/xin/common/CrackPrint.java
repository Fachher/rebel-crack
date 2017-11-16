package com.xin.common;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;

/**
 * @author linxixin@cvte.com
 */
public class CrackPrint {

    public interface AddOtherInJarFunction {
        void AddOtherInJar(JarOutputStream jarOutputStream);
    }

    public interface ModifiClassFunction {
        byte[] ModifiClass(byte[] classByte);
    }

    public static void scanAndChangeClass(File sourceFile, File destFile, AddOtherInJarFunction addOtherInJar, ModifiClassFunction modifiClassFunction) throws Exception {

        try ( JarInputStream jarIn = new JarInputStream(new FileInputStream(sourceFile)); FileOutputStream fos = new FileOutputStream(destFile)) {

            JarOutputStream jarOutputStream;
            if (jarIn.getManifest() != null) {
                jarOutputStream = new JarOutputStream(fos, jarIn.getManifest());
            } else {
                jarOutputStream = new JarOutputStream(fos);
            }

            addOtherInJar.AddOtherInJar(jarOutputStream);

            JarEntry jarEntry;
            while ((jarEntry = jarIn.getNextJarEntry()) != null) {
                byte[] oldClassByte = IOUtils.toByteArray(jarIn);
                try {
                    if (jarEntry.getName().endsWith(".class") && !jarEntry.getName().startsWith("META-INF/")) {

                        final byte[] newBytes = modifiClassFunction.ModifiClass(oldClassByte);
                        final JarEntry newJarEntry = new JarEntry(jarEntry);
                        newJarEntry.setSize(newBytes.length);
                        newJarEntry.setCompressedSize(-1);
                        jarOutputStream.putNextEntry(newJarEntry);
                        jarOutputStream.write(newBytes);
                        jarOutputStream.flush();
                        jarOutputStream.closeEntry();
                        continue;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                }

                //不是class且有异常的类就直接copy了
                jarEntry.setSize(oldClassByte.length);
                jarEntry.setCompressedSize(-1);
                jarOutputStream.putNextEntry(jarEntry);
                jarOutputStream.write(oldClassByte);
                jarOutputStream.flush();
                jarOutputStream.closeEntry();
            }
        }
    }

}
