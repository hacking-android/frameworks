/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.graphics.Bitmap;
import java.nio.ByteBuffer;

public class VertexFrame
extends Frame {
    private int vertexFrameId = -1;

    static {
        System.loadLibrary("filterfw");
    }

    VertexFrame(FrameFormat frameFormat, FrameManager frameManager) {
        super(frameFormat, frameManager);
        if (this.getFormat().getSize() > 0) {
            if (this.nativeAllocate(this.getFormat().getSize())) {
                return;
            }
            throw new RuntimeException("Could not allocate vertex frame!");
        }
        throw new IllegalArgumentException("Initializing vertex frame with zero size!");
    }

    private native int getNativeVboId();

    private native boolean nativeAllocate(int var1);

    private native boolean nativeDeallocate();

    private native boolean setNativeData(byte[] var1, int var2, int var3);

    private native boolean setNativeFloats(float[] var1);

    private native boolean setNativeInts(int[] var1);

    @Override
    public Bitmap getBitmap() {
        throw new RuntimeException("Vertex frames do not support reading data!");
    }

    @Override
    public ByteBuffer getData() {
        throw new RuntimeException("Vertex frames do not support reading data!");
    }

    @Override
    public float[] getFloats() {
        throw new RuntimeException("Vertex frames do not support reading data!");
    }

    @Override
    public int[] getInts() {
        throw new RuntimeException("Vertex frames do not support reading data!");
    }

    @Override
    public Object getObjectValue() {
        throw new RuntimeException("Vertex frames do not support reading data!");
    }

    public int getVboId() {
        return this.getNativeVboId();
    }

    @Override
    protected boolean hasNativeAllocation() {
        synchronized (this) {
            int n = this.vertexFrameId;
            boolean bl = n != -1;
            return bl;
        }
    }

    @Override
    protected void releaseNativeAllocation() {
        synchronized (this) {
            this.nativeDeallocate();
            this.vertexFrameId = -1;
            return;
        }
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        throw new RuntimeException("Unsupported: Cannot set vertex frame bitmap value!");
    }

    @Override
    public void setData(ByteBuffer arrby, int n, int n2) {
        this.assertFrameMutable();
        arrby = arrby.array();
        if (this.getFormat().getSize() == arrby.length) {
            if (this.setNativeData(arrby, n, n2)) {
                return;
            }
            throw new RuntimeException("Could not set vertex frame data!");
        }
        throw new RuntimeException("Data size in setData does not match vertex frame size!");
    }

    @Override
    public void setDataFromFrame(Frame frame) {
        super.setDataFromFrame(frame);
    }

    @Override
    public void setFloats(float[] arrf) {
        this.assertFrameMutable();
        if (this.setNativeFloats(arrf)) {
            return;
        }
        throw new RuntimeException("Could not set int values for vertex frame!");
    }

    @Override
    public void setInts(int[] arrn) {
        this.assertFrameMutable();
        if (this.setNativeInts(arrn)) {
            return;
        }
        throw new RuntimeException("Could not set int values for vertex frame!");
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VertexFrame (");
        stringBuilder.append(this.getFormat());
        stringBuilder.append(") with VBO ID ");
        stringBuilder.append(this.getVboId());
        return stringBuilder.toString();
    }
}

