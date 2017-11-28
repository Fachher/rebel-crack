package com.xin.jrebel;

import com.xin.common.AddPrintParamClass;
import com.xin.common.NoChangeClass;
import jdk.internal.org.objectweb.asm.ClassReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.xin.common.CrackPrint.scanAndChangeClass;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @author linxixin@cvte.com
 */
public class JrebelChange {

    static void deleteFile(File file) {
        if(!file.exists()){
            return ;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File waitToDelete : files) {
                    deleteFile(waitToDelete);
                }
            }
        }

        if (!file.delete()) {
            throw new RuntimeException("删除文件失败 " + file);
        }
    }

    public interface FilterAndHandle {
        void handle(File sourceFile, File targetFile);
    }

    static void copyFile(File sourceFile, String sourceStartDir, String destStartDir, FilterAndHandle filterAndHandle) throws IOException {
        if (sourceFile.isDirectory()) {
            File[] files = sourceFile.listFiles();
            if (files != null) {
                for (File waitToCopy : files) {
                    copyFile(waitToCopy, sourceStartDir, destStartDir, filterAndHandle);
                }
            }
        } else {
            if (sourceFile.isFile()) {
                new File(sourceFile.getParentFile().getPath().replace(sourceStartDir, destStartDir)).mkdirs();
            }

            Path target = Paths.get(sourceFile.getPath().replace(sourceStartDir, destStartDir));

            deleteOldVersionJar(sourceFile, target);
            Files.copy(Paths.get(sourceFile.getPath()), target, REPLACE_EXISTING);
            filterAndHandle.handle(sourceFile, target.toFile());
        }
    }

    /**
     * 删除旧版本的jar包, jar包版本一般有以下规律
     * jr-ide-common-7.1.2.jar
     * 因此每次匹配要匹配 "-"之前的字符串, 如果判断成功则删除旧版本jar
     *
     * @param sourceFile
     * @param target
     */
    private static void deleteOldVersionJar(File sourceFile, Path target) {
        File[] selectFile = target.toFile().getParentFile().listFiles((dir, name) -> {
            if (name.equals(sourceFile.getName())) {
                return true;
            }
            if (!sourceFile.getName().contains("-")) {
                return false;
            }
            String jarName = sourceFile.getName().substring(0, sourceFile.getName().lastIndexOf("-"));

            if (name.endsWith(".jar") && name.contains("-")) {
                String jarNameDesc = name.substring(0, name.lastIndexOf("-"));
                if (jarName.equals(jarNameDesc)) {
                    return true;
                }
            }
            return false;
        });
        if (selectFile.length == 1) {
            if (!selectFile[0].delete()) {
                throw new RuntimeException("旧版本jar包无法删除 " + selectFile[0]);
            }
        } else if (selectFile.length <= 0) {
            System.err.println("一个jar包搜出不等于一个要删除jar包" + sourceFile + "   " + Arrays.stream(selectFile).map(File::getName).collect(Collectors.joining(",")));
        } else {
            throw new RuntimeException("一个jar包搜出不等于一个要删除jar包" + sourceFile + "  " + selectFile.length + "  " + Arrays.stream(selectFile).map(File::getName).collect(Collectors.joining(",")));
        }
    }

    public static void main(String[] args) throws Exception {

        final URL resource = ClassLoader.getSystemClassLoader().getResource("jrebel6");

        String pathname = "G:\\workspaces\\idea\\维护\\instant-invoke\\instant-invoke-plugin";
        File dest = new File(pathname+"\\lib");
        String destStartDir = pathname +"\\resources\\advance\\";

        File sourcePath = new File(URLDecoder.decode(resource.getFile(), "utf-8"));

        File descPath = dest;

        copyFile(sourcePath, sourcePath.getPath(), descPath.getPath(), (sourceFile, targetFile) -> {

            if (sourceFile.getName().startsWith("jrebel.jar")) {
                try {
                    scanAndChangeClass(sourceFile
                            , targetFile
                            , jarOutputStream -> {
                            }, cls -> {
                                try {
                                    if (ChangeClass_Sign.validate(cls)) {
                                        return ChangeClass_Sign.crackContextValidate(cls);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return NoChangeClass.changeToNoChange(cls);
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (sourceFile.getName().startsWith("jrebel-config-client")) {
                try {
                    scanAndChangeClass(sourceFile
                            , targetFile
                            , jarOutputStream -> {
                            }, classByte -> {
                                try {
                                    if (ChangeClass_RSADigestSigner.validate(classByte)) {
                                        return ChangeClass_RSADigestSigner.crackContextValidate(classByte);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return NoChangeClass.changeToNoChange(classByte);
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


            if (sourceFile.getName().startsWith("jr-ide-common")) {
                try {
                    scanAndChangeClass(sourceFile
                            , targetFile
                            , jarOutputStream -> {
                            }, classByte -> {
                                try {
                                    if (ChangeClass_LicenseNotifier.validate(classByte)) {
                                        return ChangeClass_LicenseNotifier.crackContextValidate(classByte);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return NoChangeClass.changeToNoChange(classByte);
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }



            if (sourceFile.getName().startsWith("httpclient")) {
                try {
                    scanAndChangeClass(sourceFile
                            , targetFile
                            , jarOutputStream -> {
                            }, classByte -> {
                                try {
                                    ClassReader classReader = new ClassReader(classByte);
                                    String className = classReader.getClassName().replace("/", ".");
                                    if (className.equals("org.apache.http.impl.client.CloseableHttpClient")
                                            ||className.equals("org.apache.http.impl.client.ContentEncodingHttpClient")
                                            ||className.equals("org.apache.http.impl.client.MinimalHttpClient")
                                            ||className.equals("org.apache.http.impl.client.DefaultHttpClient")
                                            ||className.equals("org.apache.http.impl.client.SystemDefaultHttpClient")
                                            ||className.startsWith("org.apache.http.impl.client")
                                            ||className.equals("org.apache.http.impl.client.InternalHttpClient")
                                            ) {
                                        return AddPrintParamClass.changeToAddPrintParamClass(classByte);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                return NoChangeClass.changeToNoChange(classByte);
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


        File jrebel6 = new File(dest, "jrebel6");
        copyFile(jrebel6, dest.getPath(), destStartDir, (sourceFile, targetFile) -> {});
        deleteFile(jrebel6);
        deleteFile(new File(dest, "jrebel"));

    }
}
