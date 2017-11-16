package com.xin.common;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.INVOKESTATIC;

/**
 * @author linxixin@cvte.com
 */
public class AddPrintParamClass {


    public static byte[] changeToAddPrintParamClass(byte[] classByte) throws Exception {
        ClassReader classReader = new ClassReader(classByte);
        ClassWriter classWriter = new ClassWriter(classReader, COMPUTE_MAXS | COMPUTE_MAXS);
        MethodClassAdapter methodClassAdapter = new MethodClassAdapter(ASM4, classWriter,classReader.getClassName());

        classReader.accept(methodClassAdapter, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray();
    }

    /**
     * @author linxixin@cvte.com
     */


    public static class MethodClassAdapter extends ClassVisitor {

        private final String className;

        public MethodClassAdapter(int api, ClassVisitor cv, String name) {
            super(api, cv);
            this.className = name;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            return super.visitField(access, name, desc, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                                         String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
            //toString 和 初始化方法不打印以免发生循环递归
            if (name.contains("toString") || name.contains("<init>")||name.equals("hashCode")) {
                return mv;
            }
            return new ChangeMethodAdapter(className,mv, access, name, desc, signature, exceptions);
        }

        // 定义一个自己的方法访问类
        class ChangeMethodAdapter extends MethodVisitor {

            private final String className;
            private String name;
            private int      access;
            private String   desc;
            private String   signature;
            private String[] exceptions;


            public ChangeMethodAdapter(String className,MethodVisitor mv, int access, String name, String desc, String signature, String[] exceptions) {
                super(ASM4, mv);
                this.access = access;
                this.name = name;
                this.desc = desc;
                this.signature = signature;
                this.exceptions = exceptions;
                this.className = className;
            }

            @Override
            public void visitCode() {
                super.visitCode();
                final Type argumentTypes = Type.getMethodType(desc);
                final String arguments = Arrays.stream(argumentTypes.getArgumentTypes()).map(Type::getClassName).collect(Collectors.joining(",", "(", ")"));

                String methodAccess = "";

                if (Modifier.isPublic(access)) {
                    methodAccess += "public ";
                }

                if (Modifier.isPrivate(access)) {
                    methodAccess += "private ";
                }

                if (Modifier.isProtected(access)) {
                    methodAccess += "protected ";
                }
                if (Modifier.isStatic(access)) {
                    methodAccess += "static ";
                }
                if (Modifier.isSynchronized(access)) {
                    methodAccess += "synchronized ";
                }
                if(className.equals("")) {
                    System.out.println("asdfasdfasdf");
                }
                mv.visitLdcInsn(className+" "+methodAccess   + Type.getMethodType(desc).getReturnType().getClassName() + " " + name  + arguments);
                int i = 0;
                int count = 1;

                if (!Modifier.isStatic(access)) {
                    i++;
                }
                for (Type type : argumentTypes.getArgumentTypes()) {
                    i = load(mv, type, i);
                    count++;
                }

                invoke(mv, count);
            }

            private void invoke(MethodVisitor mv, int count) {
                String desc = "Ljava/lang/Object;";
                List<String> descs = new ArrayList<>();

                for (int i = 0; i < count; i++) {
                    descs.add(desc);
                }
                String methodDesc = descs.stream().collect(Collectors.joining("", "(", ")V"));

                mv.visitMethodInsn(INVOKESTATIC, "Mylog", "printt", methodDesc, false);
            }
        }


        public static int load(MethodVisitor mv, Type type, int i) {
//            if (type == null) {
//                mv.visitInsn(ACONST_NULL);
//                i++;
//                return i;
//            }
            switch (type.toString()) {
                case "I":
                    mv.visitVarInsn(Opcodes.ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                    i++;
                    break;
                case "J":
                    mv.visitVarInsn(Opcodes.LLOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                    i += 2;
                    break;
                case "D":
                    mv.visitVarInsn(Opcodes.DLOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                    i += 2;
                    break;
                case "F":
                    mv.visitVarInsn(Opcodes.FLOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                    i++;
                    break;
                case "B":
                    mv.visitVarInsn(Opcodes.ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                    i += 1;
                    break;
                case "C":
                    mv.visitVarInsn(Opcodes.ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                    i += 1;
                    break;
                case "S":
                    mv.visitVarInsn(Opcodes.ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                    i += 1;
                    break;
                case "Z":
                    mv.visitVarInsn(Opcodes.ILOAD, i);
                    mv.visitMethodInsn(INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                    i += 1;
                    break;
                default:
                    mv.visitVarInsn(Opcodes.ALOAD, i);
                    i++;
                    break;
            }
            return i;
        }
    }
}
