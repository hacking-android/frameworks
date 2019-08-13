/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLFrame;
import android.filterfw.core.NativeBuffer;
import android.filterfw.core.SimpleFrame;
import android.graphics.Bitmap;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class NativeFrame
extends Frame {
    private int nativeFrameId = -1;

    static {
        System.loadLibrary("filterfw");
    }

    NativeFrame(FrameFormat frameFormat, FrameManager frameManager) {
        super(frameFormat, frameManager);
        int n = frameFormat.getSize();
        this.nativeAllocate(n);
        boolean bl = n != 0;
        this.setReusable(bl);
    }

    private native boolean getNativeBitmap(Bitmap var1, int var2, int var3);

    private native boolean getNativeBuffer(NativeBuffer var1);

    private native int getNativeCapacity();

    private native byte[] getNativeData(int var1);

    private native float[] getNativeFloats(int var1);

    private native int[] getNativeInts(int var1);

    private native boolean nativeAllocate(int var1);

    private native boolean nativeCopyFromGL(GLFrame var1);

    private native boolean nativeCopyFromNative(NativeFrame var1);

    private native boolean nativeDeallocate();

    private static native int nativeFloatSize();

    private static native int nativeIntSize();

    private native boolean setNativeBitmap(Bitmap var1, int var2, int var3);

    private native boolean setNativeData(byte[] var1, int var2, int var3);

    private native boolean setNativeFloats(float[] var1);

    private native boolean setNativeInts(int[] var1);

    @Override
    public Bitmap getBitmap() {
        if (this.getFormat().getNumberOfDimensions() == 2) {
            Bitmap bitmap = Bitmap.createBitmap(this.getFormat().getWidth(), this.getFormat().getHeight(), Bitmap.Config.ARGB_8888);
            if (this.getNativeBitmap(bitmap, bitmap.getByteCount(), this.getFormat().getBytesPerSample())) {
                return bitmap;
            }
            throw new RuntimeException("Could not get bitmap data from native frame!");
        }
        throw new RuntimeException("Attempting to get Bitmap for non 2-dimensional native frame!");
    }

    @Override
    public int getCapacity() {
        return this.getNativeCapacity();
    }

    @Override
    public ByteBuffer getData() {
        Object object = this.getNativeData(this.getFormat().getSize());
        object = object == null ? null : ByteBuffer.wrap(object);
        return object;
    }

    @Override
    public float[] getFloats() {
        return this.getNativeFloats(this.getFormat().getSize());
    }

    @Override
    public int[] getInts() {
        return this.getNativeInts(this.getFormat().getSize());
    }

    @Override
    public Object getObjectValue() {
        if (this.getFormat().getBaseType() != 8) {
            return this.getData();
        }
        Class class_ = this.getFormat().getObjectClass();
        if (class_ != null) {
            if (NativeBuffer.class.isAssignableFrom(class_)) {
                block5 : {
                    try {
                        NativeBuffer nativeBuffer = (NativeBuffer)class_.newInstance();
                        if (!this.getNativeBuffer(nativeBuffer)) break block5;
                        nativeBuffer.attachToFrame(this);
                        return nativeBuffer;
                    }
                    catch (Exception exception) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Could not instantiate new structure instance of type '");
                        stringBuilder.append(class_);
                        stringBuilder.append("'!");
                        throw new RuntimeException(stringBuilder.toString());
                    }
                }
                throw new RuntimeException("Could not get the native structured data for frame!");
            }
            throw new RuntimeException("NativeFrame object class must be a subclass of NativeBuffer!");
        }
        throw new RuntimeException("Attempting to get object data from frame that does not specify a structure object class!");
    }

    @Override
    protected boolean hasNativeAllocation() {
        synchronized (this) {
            int n = this.nativeFrameId;
            boolean bl = n != -1;
            return bl;
        }
    }

    @Override
    protected void releaseNativeAllocation() {
        synchronized (this) {
            this.nativeDeallocate();
            this.nativeFrameId = -1;
            return;
        }
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        this.assertFrameMutable();
        if (this.getFormat().getNumberOfDimensions() == 2) {
            if (this.getFormat().getWidth() == bitmap.getWidth() && this.getFormat().getHeight() == bitmap.getHeight()) {
                if (this.setNativeBitmap(bitmap = NativeFrame.convertBitmapToRGBA(bitmap), bitmap.getByteCount(), this.getFormat().getBytesPerSample())) {
                    return;
                }
                throw new RuntimeException("Could not set native frame bitmap data!");
            }
            throw new RuntimeException("Bitmap dimensions do not match native frame dimensions!");
        }
        throw new RuntimeException("Attempting to set Bitmap for non 2-dimensional native frame!");
    }

    @Override
    public void setData(ByteBuffer object, int n, int n2) {
        this.assertFrameMutable();
        Object object2 = ((ByteBuffer)object).array();
        if (n2 + n <= ((Buffer)object).limit()) {
            if (this.getFormat().getSize() == n2) {
                if (this.setNativeData((byte[])object2, n, n2)) {
                    return;
                }
                throw new RuntimeException("Could not set native frame data!");
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Data size in setData does not match native frame size: Frame size is ");
            ((StringBuilder)object).append(this.getFormat().getSize());
            ((StringBuilder)object).append(" bytes, but ");
            ((StringBuilder)object).append(n2);
            ((StringBuilder)object).append(" bytes given!");
            throw new RuntimeException(((StringBuilder)object).toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Offset and length exceed buffer size in native setData: ");
        ((StringBuilder)object2).append(n2 + n);
        ((StringBuilder)object2).append(" bytes given, but only ");
        ((StringBuilder)object2).append(((Buffer)object).limit());
        ((StringBuilder)object2).append(" bytes available!");
        throw new RuntimeException(((StringBuilder)object2).toString());
    }

    @Override
    public void setDataFromFrame(Frame frame) {
        if (this.getFormat().getSize() >= frame.getFormat().getSize()) {
            if (frame instanceof NativeFrame) {
                this.nativeCopyFromNative((NativeFrame)frame);
            } else if (frame instanceof GLFrame) {
                this.nativeCopyFromGL((GLFrame)frame);
            } else if (frame instanceof SimpleFrame) {
                this.setObjectValue(frame.getObjectValue());
            } else {
                super.setDataFromFrame(frame);
            }
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to assign frame of size ");
        stringBuilder.append(frame.getFormat().getSize());
        stringBuilder.append(" to smaller native frame of size ");
        stringBuilder.append(this.getFormat().getSize());
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public void setFloats(float[] arrf) {
        this.assertFrameMutable();
        if (arrf.length * NativeFrame.nativeFloatSize() <= this.getFormat().getSize()) {
            if (this.setNativeFloats(arrf)) {
                return;
            }
            throw new RuntimeException("Could not set int values for native frame!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NativeFrame cannot hold ");
        stringBuilder.append(arrf.length);
        stringBuilder.append(" floats. (Can only hold ");
        stringBuilder.append(this.getFormat().getSize() / NativeFrame.nativeFloatSize());
        stringBuilder.append(" floats).");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public void setInts(int[] arrn) {
        this.assertFrameMutable();
        if (arrn.length * NativeFrame.nativeIntSize() <= this.getFormat().getSize()) {
            if (this.setNativeInts(arrn)) {
                return;
            }
            throw new RuntimeException("Could not set int values for native frame!");
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NativeFrame cannot hold ");
        stringBuilder.append(arrn.length);
        stringBuilder.append(" integers. (Can only hold ");
        stringBuilder.append(this.getFormat().getSize() / NativeFrame.nativeIntSize());
        stringBuilder.append(" integers).");
        throw new RuntimeException(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("NativeFrame id: ");
        stringBuilder.append(this.nativeFrameId);
        stringBuilder.append(" (");
        stringBuilder.append(this.getFormat());
        stringBuilder.append(") of size ");
        stringBuilder.append(this.getCapacity());
        return stringBuilder.toString();
    }
}

