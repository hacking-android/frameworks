/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.format.ObjectFormat;
import android.graphics.Bitmap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;

public class SerializedFrame
extends Frame {
    private static final int INITIAL_CAPACITY = 64;
    private DirectByteOutputStream mByteOutputStream;
    private ObjectOutputStream mObjectOut;

    SerializedFrame(FrameFormat object, FrameManager frameManager) {
        super((FrameFormat)object, frameManager);
        this.setReusable(false);
        try {
            this.mByteOutputStream = object = new DirectByteOutputStream(64);
            this.mObjectOut = object = new ObjectOutputStream(this.mByteOutputStream);
            this.mByteOutputStream.markHeaderEnd();
            return;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Could not create serialization streams for SerializedFrame!", iOException);
        }
    }

    private final Object deserializeObjectValue() {
        try {
            Object object = this.mByteOutputStream.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream((InputStream)object);
            object = objectInputStream.readObject();
            return object;
        }
        catch (ClassNotFoundException classNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unable to deserialize object of unknown class in ");
            stringBuilder.append(this);
            stringBuilder.append("!");
            throw new RuntimeException(stringBuilder.toString(), classNotFoundException);
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not deserialize object in ");
            stringBuilder.append(this);
            stringBuilder.append("!");
            throw new RuntimeException(stringBuilder.toString(), iOException);
        }
    }

    private final void serializeObjectValue(Object object) {
        try {
            this.mByteOutputStream.reset();
            this.mObjectOut.writeObject(object);
            this.mObjectOut.flush();
            this.mObjectOut.close();
            return;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not serialize object ");
            stringBuilder.append(object);
            stringBuilder.append(" in ");
            stringBuilder.append(this);
            stringBuilder.append("!");
            throw new RuntimeException(stringBuilder.toString(), iOException);
        }
    }

    static SerializedFrame wrapObject(Object object, FrameManager object2) {
        object2 = new SerializedFrame(ObjectFormat.fromObject(object, 1), (FrameManager)object2);
        ((Frame)object2).setObjectValue(object);
        return object2;
    }

    @Override
    public Bitmap getBitmap() {
        Object object = this.deserializeObjectValue();
        object = object instanceof Bitmap ? (Bitmap)object : null;
        return object;
    }

    @Override
    public ByteBuffer getData() {
        Object object = this.deserializeObjectValue();
        object = object instanceof ByteBuffer ? (ByteBuffer)object : null;
        return object;
    }

    @Override
    public float[] getFloats() {
        Object object = this.deserializeObjectValue();
        object = object instanceof float[] ? (float[])object : null;
        return object;
    }

    @Override
    public int[] getInts() {
        Object object = this.deserializeObjectValue();
        object = object instanceof int[] ? (int[])object : null;
        return object;
    }

    @Override
    public Object getObjectValue() {
        return this.deserializeObjectValue();
    }

    @Override
    protected boolean hasNativeAllocation() {
        return false;
    }

    @Override
    protected void releaseNativeAllocation() {
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.assertFrameMutable();
        this.setGenericObjectValue(bitmap);
    }

    @Override
    public void setData(ByteBuffer byteBuffer, int n, int n2) {
        this.assertFrameMutable();
        this.setGenericObjectValue(ByteBuffer.wrap(byteBuffer.array(), n, n2));
    }

    @Override
    public void setFloats(float[] arrf) {
        this.assertFrameMutable();
        this.setGenericObjectValue(arrf);
    }

    @Override
    protected void setGenericObjectValue(Object object) {
        this.serializeObjectValue(object);
    }

    @Override
    public void setInts(int[] arrn) {
        this.assertFrameMutable();
        this.setGenericObjectValue(arrn);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SerializedFrame (");
        stringBuilder.append(this.getFormat());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    private class DirectByteInputStream
    extends InputStream {
        private byte[] mBuffer;
        private int mPos = 0;
        private int mSize;

        public DirectByteInputStream(byte[] arrby, int n) {
            this.mBuffer = arrby;
            this.mSize = n;
        }

        @Override
        public final int available() {
            return this.mSize - this.mPos;
        }

        @Override
        public final int read() {
            int n = this.mPos;
            if (n < this.mSize) {
                byte[] arrby = this.mBuffer;
                this.mPos = n + 1;
                n = arrby[n] & 255;
            } else {
                n = -1;
            }
            return n;
        }

        @Override
        public final int read(byte[] arrby, int n, int n2) {
            int n3 = this.mPos;
            int n4 = this.mSize;
            if (n3 >= n4) {
                return -1;
            }
            int n5 = n2;
            if (n3 + n2 > n4) {
                n5 = n4 - n3;
            }
            System.arraycopy(this.mBuffer, this.mPos, arrby, n, n5);
            this.mPos += n5;
            return n5;
        }

        @Override
        public final long skip(long l) {
            int n = this.mPos;
            long l2 = n;
            int n2 = this.mSize;
            long l3 = l;
            if (l2 + l > (long)n2) {
                l3 = n2 - n;
            }
            if (l3 < 0L) {
                return 0L;
            }
            this.mPos = (int)((long)this.mPos + l3);
            return l3;
        }
    }

    private class DirectByteOutputStream
    extends OutputStream {
        private byte[] mBuffer = null;
        private int mDataOffset = 0;
        private int mOffset = 0;

        public DirectByteOutputStream(int n) {
            this.mBuffer = new byte[n];
        }

        private final void ensureFit(int n) {
            int n2 = this.mOffset;
            byte[] arrby = this.mBuffer;
            if (n2 + n > arrby.length) {
                byte[] arrby2 = this.mBuffer;
                this.mBuffer = new byte[Math.max(n2 + n, arrby.length * 2)];
                System.arraycopy(arrby2, 0, this.mBuffer, 0, this.mOffset);
            }
        }

        public byte[] getByteArray() {
            return this.mBuffer;
        }

        public final DirectByteInputStream getInputStream() {
            return new DirectByteInputStream(this.mBuffer, this.mOffset);
        }

        public final int getSize() {
            return this.mOffset;
        }

        public final void markHeaderEnd() {
            this.mDataOffset = this.mOffset;
        }

        public final void reset() {
            this.mOffset = this.mDataOffset;
        }

        @Override
        public final void write(int n) {
            this.ensureFit(1);
            byte[] arrby = this.mBuffer;
            int n2 = this.mOffset;
            this.mOffset = n2 + 1;
            arrby[n2] = (byte)n;
        }

        @Override
        public final void write(byte[] arrby) {
            this.write(arrby, 0, arrby.length);
        }

        @Override
        public final void write(byte[] arrby, int n, int n2) {
            this.ensureFit(n2);
            System.arraycopy(arrby, n, this.mBuffer, this.mOffset, n2);
            this.mOffset += n2;
        }
    }

}

