package com.xin.xrebel;

import org.objectweb.asm.AnnotationVisitor;
import org.objectweb.asm.Attribute;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Handle;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.Modifier;

import static org.objectweb.asm.ClassWriter.COMPUTE_MAXS;
import static org.objectweb.asm.Opcodes.ASM4;
import static org.objectweb.asm.Opcodes.POP;

/**
 * @author linxixin@cvte.com
 */
public class ChangeClass_n {


    public static final String LR_CLASS = "com.zeroturnaround.xrebel.bootstrap.n";

    public static boolean validate(byte[] classByte) {

        return new ClassReader(classByte).getClassName().equals(LR_CLASS);
    }

    public static byte[] crackContextValidate(byte[] classByte) throws Exception {
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
        public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
            MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);

            // 当是sayName方法是做对应的修改
            if (name.equals("a") && Modifier.isPublic(access)) {
                MethodVisitor newMv = new ChangeMethodAdapter(mv);
                return newMv;
            } else {
                return mv;
            }
        }

        private class ChangeMethodAdapter extends MethodVisitor {
            boolean isOk = false;
            int     time = 0;

            public ChangeMethodAdapter(MethodVisitor mv) {
                super(ASM4);
                this.mv = mv;
            }


            @Override
            public void visitAttribute(Attribute attr) {
                if (isOk)
                    super.visitAttribute(attr);
            }

            @Override
            public void visitCode() {
                if (isOk)
                    super.visitCode();
            }

            @Override
            public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
                if (isOk)
                    super.visitFrame(type, nLocal, local, nStack, stack);
            }

            @Override
            public void visitInsn(int opcode) {
                if (opcode == POP) {
                    time++;
                    if (time == 2) {
                        isOk = true;
                        super.visitCode();
//                        super.visitInsn(opcode);
                        return;
                    }
                }
                if (isOk)
                    super.visitInsn(opcode);
            }

            @Override
            public void visitIntInsn(int opcode, int operand) {
                if (isOk)
                    super.visitIntInsn(opcode, operand);
            }

            @Override
            public void visitVarInsn(int opcode, int var) {
                if (isOk)
                    super.visitVarInsn(opcode, var);
            }

            @Override
            public void visitTypeInsn(int opcode, String type) {
                if (isOk)
                    super.visitTypeInsn(opcode, type);
            }

            @Override
            public void visitFieldInsn(int opcode, String owner, String name, String desc) {
                if (isOk)
                    super.visitFieldInsn(opcode, owner, name, desc);
            }



            @Override
            public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
                if (isOk)
                    super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
            }

            @Override
            public void visitJumpInsn(int opcode, Label label) {
                if (isOk)
                    super.visitJumpInsn(opcode, label);
            }

            @Override
            public void visitLabel(Label label) {
                if (isOk)
                    super.visitLabel(label);
            }

            @Override
            public void visitLdcInsn(Object cst) {
                if (isOk)
                    super.visitLdcInsn(cst);
            }

            @Override
            public void visitIincInsn(int var, int increment) {
                if (isOk)
                    super.visitIincInsn(var, increment);
            }

            @Override
            public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
                if (isOk)
                    super.visitTableSwitchInsn(min, max, dflt, labels);
            }


            @Override
            public void visitMethodInsn(int opcode, String owner, String name, String desc) {
                if (isOk)
                    super.visitMethodInsn(opcode, owner, name, desc);
            }


            @Override
            public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
                if (isOk)
                    super.visitLookupSwitchInsn(dflt, keys, labels);
            }

            @Override
            public void visitMultiANewArrayInsn(String desc, int dims) {
                if (isOk)
                    super.visitMultiANewArrayInsn(desc, dims);
            }


            @Override
            public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
                if (isOk)
                    super.visitTryCatchBlock(start, end, handler, type);
            }


            @Override
            public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
                if (isOk)
                    super.visitLocalVariable(name, desc, signature, start, end, index);
            }

            @Override
            public void visitLineNumber(int line, Label start) {
                if (isOk)
                    super.visitLineNumber(line, start);
            }

            @Override
            public void visitMaxs(int maxStack, int maxLocals) {
                if (isOk)
                    super.visitMaxs(maxStack, maxLocals);
            }

            @Override
            public void visitEnd() {
                if (isOk)
                    super.visitEnd();
            }

            @Override
            public AnnotationVisitor visitAnnotationDefault() {
                return super.visitAnnotationDefault();
            }

            @Override
            public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
                return super.visitAnnotation(desc, visible);
            }

            @Override
            public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
                return super.visitParameterAnnotation(parameter, desc, visible);
            }

        }
    }


}
