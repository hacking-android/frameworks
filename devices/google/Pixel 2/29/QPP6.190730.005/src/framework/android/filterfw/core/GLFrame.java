/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GLEnvironment;
import android.filterfw.core.GLFrameTimer;
import android.filterfw.core.NativeFrame;
import android.filterfw.core.SimpleFrame;
import android.filterfw.core.StopWatchMap;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.opengl.GLES20;
import java.nio.ByteBuffer;

public class GLFrame
extends Frame {
    public static final int EXISTING_FBO_BINDING = 101;
    public static final int EXISTING_TEXTURE_BINDING = 100;
    public static final int EXTERNAL_TEXTURE = 104;
    public static final int NEW_FBO_BINDING = 103;
    public static final int NEW_TEXTURE_BINDING = 102;
    private int glFrameId = -1;
    private GLEnvironment mGLEnvironment;
    private boolean mOwnsTexture = true;

    static {
        System.loadLibrary("filterfw");
    }

    GLFrame(FrameFormat frameFormat, FrameManager frameManager) {
        super(frameFormat, frameManager);
    }

    GLFrame(FrameFormat frameFormat, FrameManager frameManager, int n, long l) {
        super(frameFormat, frameManager, n, l);
    }

    private void assertGLEnvValid() {
        if (!this.mGLEnvironment.isContextActive()) {
            if (GLEnvironment.isAnyContextActive()) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Attempting to access ");
                stringBuilder.append(this);
                stringBuilder.append(" with foreign GL context active!");
                throw new RuntimeException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Attempting to access ");
            stringBuilder.append(this);
            stringBuilder.append(" with no GL context  active!");
            throw new RuntimeException(stringBuilder.toString());
        }
    }

    private native boolean generateNativeMipMap();

    private native boolean getNativeBitmap(Bitmap var1);

    private native byte[] getNativeData();

    private native int getNativeFboId();

    private native float[] getNativeFloats();

    private native int[] getNativeInts();

    private native int getNativeTextureId();

    private void initNew(boolean bl) {
        block7 : {
            block6 : {
                block5 : {
                    if (!bl) break block5;
                    if (!this.nativeAllocateExternal(this.mGLEnvironment)) {
                        throw new RuntimeException("Could not allocate external GL frame!");
                    }
                    break block6;
                }
                if (!this.nativeAllocate(this.mGLEnvironment, this.getFormat().getWidth(), this.getFormat().getHeight())) break block7;
            }
            return;
        }
        throw new RuntimeException("Could not allocate GL frame!");
    }

    private void initWithFbo(int n) {
        int n2;
        int n3 = this.getFormat().getWidth();
        if (this.nativeAllocateWithFbo(this.mGLEnvironment, n, n3, n2 = this.getFormat().getHeight())) {
            return;
        }
        throw new RuntimeException("Could not allocate FBO backed GL frame!");
    }

    private void initWithTexture(int n) {
        int n2;
        int n3 = this.getFormat().getWidth();
        if (this.nativeAllocateWithTexture(this.mGLEnvironment, n, n3, n2 = this.getFormat().getHeight())) {
            this.mOwnsTexture = false;
            this.markReadOnly();
            return;
        }
        throw new RuntimeException("Could not allocate texture backed GL frame!");
    }

    private native boolean nativeAllocate(GLEnvironment var1, int var2, int var3);

    private native boolean nativeAllocateExternal(GLEnvironment var1);

    private native boolean nativeAllocateWithFbo(GLEnvironment var1, int var2, int var3, int var4);

    private native boolean nativeAllocateWithTexture(GLEnvironment var1, int var2, int var3, int var4);

    private native boolean nativeCopyFromGL(GLFrame var1);

    private native boolean nativeCopyFromNative(NativeFrame var1);

    private native boolean nativeDeallocate();

    private native boolean nativeDetachTexFromFbo();

    private native boolean nativeFocus();

    private native boolean nativeReattachTexToFbo();

    private native boolean nativeResetParams();

    private native boolean setNativeBitmap(Bitmap var1, int var2);

    private native boolean setNativeData(byte[] var1, int var2, int var3);

    private native boolean setNativeFloats(float[] var1);

    private native boolean setNativeInts(int[] var1);

    private native boolean setNativeTextureParam(int var1, int var2);

    private native boolean setNativeViewport(int var1, int var2, int var3, int var4);

    void flushGPU(String string2) {
        StopWatchMap stopWatchMap = GLFrameTimer.get();
        if (stopWatchMap.LOG_MFF_RUNNING_TIMES) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("glFinish ");
            stringBuilder.append(string2);
            stopWatchMap.start(stringBuilder.toString());
            GLES20.glFinish();
            stringBuilder = new StringBuilder();
            stringBuilder.append("glFinish ");
            stringBuilder.append(string2);
            stopWatchMap.stop(stringBuilder.toString());
        }
    }

    public void focus() {
        if (this.nativeFocus()) {
            return;
        }
        throw new RuntimeException("Could not focus on GLFrame for drawing!");
    }

    @UnsupportedAppUsage
    public void generateMipMap() {
        this.assertFrameMutable();
        this.assertGLEnvValid();
        if (this.generateNativeMipMap()) {
            return;
        }
        throw new RuntimeException("Could not generate mip-map for GL frame!");
    }

    @Override
    public Bitmap getBitmap() {
        this.assertGLEnvValid();
        this.flushGPU("getBitmap");
        Bitmap bitmap = Bitmap.createBitmap(this.getFormat().getWidth(), this.getFormat().getHeight(), Bitmap.Config.ARGB_8888);
        if (this.getNativeBitmap(bitmap)) {
            return bitmap;
        }
        throw new RuntimeException("Could not get bitmap data from GL frame!");
    }

    @Override
    public ByteBuffer getData() {
        this.assertGLEnvValid();
        this.flushGPU("getData");
        return ByteBuffer.wrap(this.getNativeData());
    }

    public int getFboId() {
        return this.getNativeFboId();
    }

    @Override
    public float[] getFloats() {
        this.assertGLEnvValid();
        this.flushGPU("getFloats");
        return this.getNativeFloats();
    }

    public GLEnvironment getGLEnvironment() {
        return this.mGLEnvironment;
    }

    @Override
    public int[] getInts() {
        this.assertGLEnvValid();
        this.flushGPU("getInts");
        return this.getNativeInts();
    }

    @Override
    public Object getObjectValue() {
        this.assertGLEnvValid();
        return ByteBuffer.wrap(this.getNativeData());
    }

    @UnsupportedAppUsage
    public int getTextureId() {
        return this.getNativeTextureId();
    }

    @Override
    protected boolean hasNativeAllocation() {
        synchronized (this) {
            int n = this.glFrameId;
            boolean bl = n != -1;
            return bl;
        }
    }

    void init(GLEnvironment object) {
        block2 : {
            block3 : {
                block4 : {
                    int n;
                    block11 : {
                        boolean bl;
                        block6 : {
                            block10 : {
                                block9 : {
                                    block8 : {
                                        block7 : {
                                            block5 : {
                                                FrameFormat frameFormat = this.getFormat();
                                                this.mGLEnvironment = object;
                                                if (frameFormat.getBytesPerSample() != 4) break block2;
                                                if (frameFormat.getDimensionCount() != 2) break block3;
                                                if (this.getFormat().getSize() < 0) break block4;
                                                n = this.getBindingType();
                                                bl = true;
                                                if (n != 0) break block5;
                                                this.initNew(false);
                                                break block6;
                                            }
                                            if (n != 104) break block7;
                                            this.initNew(true);
                                            bl = false;
                                            break block6;
                                        }
                                        if (n != 100) break block8;
                                        this.initWithTexture((int)this.getBindingId());
                                        break block6;
                                    }
                                    if (n != 101) break block9;
                                    this.initWithFbo((int)this.getBindingId());
                                    break block6;
                                }
                                if (n != 102) break block10;
                                this.initWithTexture((int)this.getBindingId());
                                break block6;
                            }
                            if (n != 103) break block11;
                            this.initWithFbo((int)this.getBindingId());
                        }
                        this.setReusable(bl);
                        return;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Attempting to create GL frame with unknown binding type ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append("!");
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                throw new IllegalArgumentException("Initializing GL frame with zero size!");
            }
            throw new IllegalArgumentException("GL frames must be 2-dimensional!");
        }
        throw new IllegalArgumentException("GL frames must have 4 bytes per sample!");
    }

    @Override
    protected void onFrameFetch() {
        if (!this.mOwnsTexture) {
            this.nativeReattachTexToFbo();
        }
    }

    @Override
    protected void onFrameStore() {
        if (!this.mOwnsTexture) {
            this.nativeDetachTexFromFbo();
        }
    }

    @Override
    protected void releaseNativeAllocation() {
        synchronized (this) {
            this.nativeDeallocate();
            this.glFrameId = -1;
            return;
        }
    }

    @Override
    protected void reset(FrameFormat frameFormat) {
        if (this.nativeResetParams()) {
            super.reset(frameFormat);
            return;
        }
        throw new RuntimeException("Could not reset GLFrame texture parameters!");
    }

    @UnsupportedAppUsage
    @Override
    public void setBitmap(Bitmap bitmap) {
        this.assertFrameMutable();
        this.assertGLEnvValid();
        if (this.getFormat().getWidth() == bitmap.getWidth() && this.getFormat().getHeight() == bitmap.getHeight()) {
            if (this.setNativeBitmap(bitmap = GLFrame.convertBitmapToRGBA(bitmap), bitmap.getByteCount())) {
                return;
            }
            throw new RuntimeException("Could not set GL frame bitmap data!");
        }
        throw new RuntimeException("Bitmap dimensions do not match GL frame dimensions!");
    }

    @Override
    public void setData(ByteBuffer arrby, int n, int n2) {
        this.assertFrameMutable();
        this.assertGLEnvValid();
        arrby = arrby.array();
        if (this.getFormat().getSize() == arrby.length) {
            if (this.setNativeData(arrby, n, n2)) {
                return;
            }
            throw new RuntimeException("Could not set GL frame data!");
        }
        throw new RuntimeException("Data size in setData does not match GL frame size!");
    }

    @Override
    public void setDataFromFrame(Frame frame) {
        this.assertGLEnvValid();
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
        stringBuilder.append(" to smaller GL frame of size ");
        stringBuilder.append(this.getFormat().getSize());
        stringBuilder.append("!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public void setFloats(float[] arrf) {
        this.assertFrameMutable();
        this.assertGLEnvValid();
        if (this.setNativeFloats(arrf)) {
            return;
        }
        throw new RuntimeException("Could not set int values for GL frame!");
    }

    @Override
    public void setInts(int[] arrn) {
        this.assertFrameMutable();
        this.assertGLEnvValid();
        if (this.setNativeInts(arrn)) {
            return;
        }
        throw new RuntimeException("Could not set int values for GL frame!");
    }

    @UnsupportedAppUsage
    public void setTextureParameter(int n, int n2) {
        this.assertFrameMutable();
        this.assertGLEnvValid();
        if (this.setNativeTextureParam(n, n2)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Could not set texture value ");
        stringBuilder.append(n);
        stringBuilder.append(" = ");
        stringBuilder.append(n2);
        stringBuilder.append(" for GLFrame!");
        throw new RuntimeException(stringBuilder.toString());
    }

    public void setViewport(int n, int n2, int n3, int n4) {
        this.assertFrameMutable();
        this.setNativeViewport(n, n2, n3, n4);
    }

    public void setViewport(Rect rect) {
        this.assertFrameMutable();
        this.setNativeViewport(rect.left, rect.top, rect.right - rect.left, rect.bottom - rect.top);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("GLFrame id: ");
        stringBuilder.append(this.glFrameId);
        stringBuilder.append(" (");
        stringBuilder.append(this.getFormat());
        stringBuilder.append(") with texture ID ");
        stringBuilder.append(this.getTextureId());
        stringBuilder.append(", FBO ID ");
        stringBuilder.append(this.getFboId());
        return stringBuilder.toString();
    }
}

