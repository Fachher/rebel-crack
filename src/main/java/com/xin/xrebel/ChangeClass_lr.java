package com.xin.xrebel;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.ICONST_1;
import static org.objectweb.asm.Opcodes.IRETURN;

/**
 * @author linxixin@cvte.com
 */
public class ChangeClass_lr {


    public static final String LR_CLASS = "com.zeroturnaround.xrebel.lr";

    public static boolean validate(byte[] classByte) {
        ClassReader classReader = new ClassReader(classByte);

        return classReader.getClassName().equals(LR_CLASS);
    }

    public static byte[] crackLicenseValidate(byte[] classByte) throws Exception {
        ClassReader classReader = new ClassReader(classByte);
        ClassWriter classWriter = new ClassWriter(classReader, COMPUTE_MAXS);
        classReader.accept(new ChangeClassAdapter(classWriter), ClassReader.EXPAND_FRAMES);

        return classWriter.toByteArray();
    }


    private static class ChangeClassAdapter extends ClassVisitor {

        public ChangeClassAdapter(ClassVisitor cv) {
            super(ASM4);
            this.cv = cv;
        }

        @Override
        public FieldVisitor visitField(int access, String name, String desc, String signature, Object value) {
            return super.visitField(access, name, desc, signature, value);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String desc,
                                         String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

            // 当是sayName方法是做对应的修改
            if (name.equals("a") && desc.contains("(Lcom/zeroturnaround/xrebel/sdk/UserLicense;)Z")) {
                MethodVisitor newMv = new ChangeMethodAdapter(mv);
                return newMv;
            } else {
                return mv;
            }
        }

        // 定义一个自己的方法访问类
        private class ChangeMethodAdapter extends MethodVisitor {
            public boolean arraylength = false;
            public boolean aaload      = false;

            public ChangeMethodAdapter(MethodVisitor mv) {
                super(ASM4, mv);
            }

            @Override
            public void visitCode() {
                super.visitCode();
                super.visitInsn(ICONST_1);
                super.visitInsn(IRETURN);
                super.visitEnd();
            }


            @Override
            public void visitInsn(int opcode) {
            }

            @Override
            public void visitIntInsn(int opcode, int operand) {
            }

            @Override
            public void visitVarInsn(int opcode, int var) {
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
            }

            @Override
            public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
            }

            @Override
            public void visitJumpInsn(int opcode, Label label) {
            }


            @Override
            public void visitLocalVariable(String s, String s1, String s2, Label label, Label label1, int i) {

            }

            @Override
            public void visitMethodInsn(int i, String s, String s1, String s2) {
            }

            @Override
            public void visitLdcInsn(Object cst) {
            }

            @Override
            public void visitIincInsn(int var, int increment) {
            }

            @Override
            public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
            }

            @Override
            public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
            }

            @Override
            public void visitMultiANewArrayInsn(String desc, int dims) {
            }


            @Override
            public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
            }

            @Override
            public void visitLineNumber(int line, Label start) {
            }

            @Override
            public void visitEnd() {
            }
        }
    }

}
