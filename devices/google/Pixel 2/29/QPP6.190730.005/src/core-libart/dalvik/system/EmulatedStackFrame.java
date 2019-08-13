/*
 * Decompiled with CFR 0.145.
 */
package dalvik.system;

import java.io.Serializable;
import java.lang.invoke.MethodType;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class EmulatedStackFrame {
    private final MethodType callsiteType;
    private final Object[] references;
    private final byte[] stackFrame;
    private final MethodType type;

    private EmulatedStackFrame(MethodType methodType, MethodType methodType2, Object[] arrobject, byte[] arrby) {
        this.type = methodType;
        this.callsiteType = methodType2;
        this.references = arrobject;
        this.stackFrame = arrby;
    }

    public static EmulatedStackFrame create(MethodType methodType) {
        Class<?> class_2;
        int n = 0;
        int n2 = 0;
        for (Class<?> class_2 : methodType.ptypes()) {
            if (!class_2.isPrimitive()) {
                ++n;
                continue;
            }
            n2 += EmulatedStackFrame.getSize(class_2);
        }
        class_2 = methodType.rtype();
        if (!class_2.isPrimitive()) {
            ++n;
        } else {
            n2 += EmulatedStackFrame.getSize(class_2);
        }
        return new EmulatedStackFrame(methodType, methodType, new Object[n], new byte[n2]);
    }

    public static int getSize(Class<?> class_) {
        if (class_.isPrimitive()) {
            if (EmulatedStackFrame.is64BitPrimitive(class_)) {
                return 8;
            }
            return 4;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("type.isPrimitive() == false: ");
        stringBuilder.append(class_);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private static boolean is64BitPrimitive(Class<?> class_) {
        boolean bl = class_ == Double.TYPE || class_ == Long.TYPE;
        return bl;
    }

    public void copyRangeTo(EmulatedStackFrame emulatedStackFrame, Range range, int n, int n2) {
        if (range.numReferences > 0) {
            System.arraycopy(this.references, range.referencesStart, emulatedStackFrame.references, n, range.numReferences);
        }
        if (range.numBytes > 0) {
            System.arraycopy((byte[])this.stackFrame, (int)range.stackFrameStart, (byte[])emulatedStackFrame.stackFrame, (int)n2, (int)range.numBytes);
        }
    }

    public void copyReturnValueTo(EmulatedStackFrame arrobject) {
        Object[] arrobject2 = this.type.returnType();
        if (!arrobject2.isPrimitive()) {
            arrobject2 = arrobject.references;
            int n = arrobject2.length;
            arrobject = this.references;
            arrobject2[n - 1] = arrobject[arrobject.length - 1];
        } else if (!EmulatedStackFrame.is64BitPrimitive(arrobject2)) {
            arrobject2 = this.stackFrame;
            int n = arrobject2.length;
            arrobject = arrobject.stackFrame;
            System.arraycopy((byte[])arrobject2, (int)(n - 4), (byte[])arrobject, (int)(arrobject.length - 4), (int)4);
        } else {
            arrobject2 = this.stackFrame;
            int n = arrobject2.length;
            arrobject = arrobject.stackFrame;
            System.arraycopy((byte[])arrobject2, (int)(n - 8), (byte[])arrobject, (int)(arrobject.length - 8), (int)8);
        }
    }

    public final MethodType getCallsiteType() {
        return this.callsiteType;
    }

    public final MethodType getMethodType() {
        return this.type;
    }

    public <T> T getReference(int n, Class<T> class_) {
        if (class_ == this.type.ptypes()[n]) {
            return (T)this.references[n];
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Argument: ");
        stringBuilder.append(n);
        stringBuilder.append(" is of type ");
        stringBuilder.append(this.type.ptypes()[n]);
        stringBuilder.append(" expected ");
        stringBuilder.append(class_);
        stringBuilder.append("");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public void setReference(int n, Object object) {
        Class<?>[] arrclass = this.type.ptypes();
        if (n >= 0 && n < arrclass.length) {
            if (object != null && !arrclass[n].isInstance(object)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("reference is not of type: ");
                ((StringBuilder)object).append(this.type.ptypes()[n]);
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
            this.references[n] = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid index: ");
        ((StringBuilder)object).append(n);
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void setReturnValueTo(Object object) {
        Object[] arrobject = this.type.returnType();
        if (!arrobject.isPrimitive()) {
            if (object != null && !arrobject.isInstance(object)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("reference is not of type ");
                ((StringBuilder)object).append(arrobject);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            arrobject = this.references;
            arrobject[arrobject.length - 1] = object;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("return type is not a reference type: ");
        ((StringBuilder)object).append(arrobject);
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    public static final class Range {
        public final int numBytes;
        public final int numReferences;
        public final int referencesStart;
        public final int stackFrameStart;

        private Range(int n, int n2, int n3, int n4) {
            this.referencesStart = n;
            this.numReferences = n2;
            this.stackFrameStart = n3;
            this.numBytes = n4;
        }

        public static Range all(MethodType methodType) {
            return Range.of(methodType, 0, methodType.parameterCount());
        }

        public static Range of(MethodType arrclass, int n, int n2) {
            int n3;
            Class<?> class_;
            arrclass = arrclass.ptypes();
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            int n7 = 0;
            for (n3 = 0; n3 < n; ++n3) {
                class_ = arrclass[n3];
                if (!class_.isPrimitive()) {
                    ++n4;
                    continue;
                }
                n6 += EmulatedStackFrame.getSize(class_);
            }
            n3 = n7;
            while (n < n2) {
                class_ = arrclass[n];
                if (!class_.isPrimitive()) {
                    ++n5;
                } else {
                    n3 += EmulatedStackFrame.getSize(class_);
                }
                ++n;
            }
            return new Range(n4, n5, n6, n3);
        }
    }

    public static class StackFrameAccessor {
        private static final int RETURN_VALUE_IDX = -2;
        protected int argumentIdx = 0;
        protected EmulatedStackFrame frame;
        protected ByteBuffer frameBuf = null;
        private int numArgs = 0;
        protected int referencesOffset = 0;

        protected StackFrameAccessor() {
        }

        public static void copyNext(StackFrameReader stackFrameReader, StackFrameWriter stackFrameWriter, Class<?> class_) {
            if (!class_.isPrimitive()) {
                stackFrameWriter.putNextReference(stackFrameReader.nextReference(class_), class_);
            } else if (class_ == Boolean.TYPE) {
                stackFrameWriter.putNextBoolean(stackFrameReader.nextBoolean());
            } else if (class_ == Byte.TYPE) {
                stackFrameWriter.putNextByte(stackFrameReader.nextByte());
            } else if (class_ == Character.TYPE) {
                stackFrameWriter.putNextChar(stackFrameReader.nextChar());
            } else if (class_ == Short.TYPE) {
                stackFrameWriter.putNextShort(stackFrameReader.nextShort());
            } else if (class_ == Integer.TYPE) {
                stackFrameWriter.putNextInt(stackFrameReader.nextInt());
            } else if (class_ == Long.TYPE) {
                stackFrameWriter.putNextLong(stackFrameReader.nextLong());
            } else if (class_ == Float.TYPE) {
                stackFrameWriter.putNextFloat(stackFrameReader.nextFloat());
            } else if (class_ == Double.TYPE) {
                stackFrameWriter.putNextDouble(stackFrameReader.nextDouble());
            }
        }

        public StackFrameAccessor attach(EmulatedStackFrame emulatedStackFrame) {
            return this.attach(emulatedStackFrame, 0, 0, 0);
        }

        public StackFrameAccessor attach(EmulatedStackFrame emulatedStackFrame, int n, int n2, int n3) {
            this.frame = emulatedStackFrame;
            this.frameBuf = ByteBuffer.wrap(this.frame.stackFrame).order(ByteOrder.LITTLE_ENDIAN);
            this.numArgs = this.frame.type.ptypes().length;
            if (n3 != 0) {
                this.frameBuf.position(n3);
            }
            this.referencesOffset = n2;
            this.argumentIdx = n;
            return this;
        }

        protected void checkType(Class<?> serializable) {
            int n = this.argumentIdx;
            if (n < this.numArgs && n != -1) {
                Class<?> class_ = n == -2 ? this.frame.type.rtype() : this.frame.type.ptypes()[this.argumentIdx];
                if (class_ == serializable) {
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Incorrect type: ");
                stringBuilder.append(serializable);
                stringBuilder.append(", expected: ");
                stringBuilder.append(class_);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Invalid argument index: ");
            ((StringBuilder)serializable).append(this.argumentIdx);
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }

        public void makeReturnValueAccessor() {
            Class<?> class_ = this.frame.type.rtype();
            this.argumentIdx = -2;
            if (class_.isPrimitive()) {
                ByteBuffer byteBuffer = this.frameBuf;
                byteBuffer.position(byteBuffer.capacity() - EmulatedStackFrame.getSize(class_));
            } else {
                this.referencesOffset = this.frame.references.length - 1;
            }
        }
    }

    public static class StackFrameReader
    extends StackFrameAccessor {
        public boolean nextBoolean() {
            this.checkType(Boolean.TYPE);
            int n = this.argumentIdx;
            boolean bl = true;
            this.argumentIdx = n + 1;
            if (this.frameBuf.getInt() == 0) {
                bl = false;
            }
            return bl;
        }

        public byte nextByte() {
            this.checkType(Byte.TYPE);
            ++this.argumentIdx;
            return (byte)this.frameBuf.getInt();
        }

        public char nextChar() {
            this.checkType(Character.TYPE);
            ++this.argumentIdx;
            return (char)this.frameBuf.getInt();
        }

        public double nextDouble() {
            this.checkType(Double.TYPE);
            ++this.argumentIdx;
            return this.frameBuf.getDouble();
        }

        public float nextFloat() {
            this.checkType(Float.TYPE);
            ++this.argumentIdx;
            return this.frameBuf.getFloat();
        }

        public int nextInt() {
            this.checkType(Integer.TYPE);
            ++this.argumentIdx;
            return this.frameBuf.getInt();
        }

        public long nextLong() {
            this.checkType(Long.TYPE);
            ++this.argumentIdx;
            return this.frameBuf.getLong();
        }

        public <T> T nextReference(Class<T> arrobject) {
            this.checkType((Class<?>)arrobject);
            ++this.argumentIdx;
            arrobject = this.frame.references;
            int n = this.referencesOffset;
            this.referencesOffset = n + 1;
            return (T)arrobject[n];
        }

        public short nextShort() {
            this.checkType(Short.TYPE);
            ++this.argumentIdx;
            return (short)this.frameBuf.getInt();
        }
    }

    public static class StackFrameWriter
    extends StackFrameAccessor {
        public void putNextBoolean(boolean bl) {
            this.checkType(Boolean.TYPE);
            ++this.argumentIdx;
            this.frameBuf.putInt((int)bl);
        }

        public void putNextByte(byte by) {
            this.checkType(Byte.TYPE);
            ++this.argumentIdx;
            this.frameBuf.putInt(by);
        }

        public void putNextChar(char c) {
            this.checkType(Character.TYPE);
            ++this.argumentIdx;
            this.frameBuf.putInt(c);
        }

        public void putNextDouble(double d) {
            this.checkType(Double.TYPE);
            ++this.argumentIdx;
            this.frameBuf.putDouble(d);
        }

        public void putNextFloat(float f) {
            this.checkType(Float.TYPE);
            ++this.argumentIdx;
            this.frameBuf.putFloat(f);
        }

        public void putNextInt(int n) {
            this.checkType(Integer.TYPE);
            ++this.argumentIdx;
            this.frameBuf.putInt(n);
        }

        public void putNextLong(long l) {
            this.checkType(Long.TYPE);
            ++this.argumentIdx;
            this.frameBuf.putLong(l);
        }

        public void putNextReference(Object object, Class<?> arrobject) {
            this.checkType((Class<?>)arrobject);
            ++this.argumentIdx;
            arrobject = this.frame.references;
            int n = this.referencesOffset;
            this.referencesOffset = n + 1;
            arrobject[n] = object;
        }

        public void putNextShort(short s) {
            this.checkType(Short.TYPE);
            ++this.argumentIdx;
            this.frameBuf.putInt(s);
        }
    }

}

