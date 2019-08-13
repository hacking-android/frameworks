/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.MutableFrameFormat;
import android.filterfw.format.ObjectFormat;
import android.graphics.Bitmap;
import java.nio.ByteBuffer;

public class SimpleFrame
extends Frame {
    private Object mObject;

    SimpleFrame(FrameFormat frameFormat, FrameManager frameManager) {
        super(frameFormat, frameManager);
        this.initWithFormat(frameFormat);
        this.setReusable(false);
    }

    private void initWithFormat(FrameFormat frameFormat) {
        int n = frameFormat.getLength();
        int n2 = frameFormat.getBaseType();
        this.mObject = n2 != 2 ? (n2 != 3 ? (n2 != 4 ? (n2 != 5 ? (n2 != 6 ? null : new double[n]) : new float[n]) : new int[n]) : new short[n]) : new byte[n];
    }

    private void setFormatObjectClass(Class class_) {
        MutableFrameFormat mutableFrameFormat = this.getFormat().mutableCopy();
        mutableFrameFormat.setObjectClass(class_);
        this.setFormat(mutableFrameFormat);
    }

    static SimpleFrame wrapObject(Object object, FrameManager object2) {
        object2 = new SimpleFrame(ObjectFormat.fromObject(object, 1), (FrameManager)object2);
        ((Frame)object2).setObjectValue(object);
        return object2;
    }

    @Override
    public Bitmap getBitmap() {
        Object object = this.mObject;
        object = object instanceof Bitmap ? (Bitmap)object : null;
        return object;
    }

    @Override
    public ByteBuffer getData() {
        Object object = this.mObject;
        object = object instanceof ByteBuffer ? (ByteBuffer)object : null;
        return object;
    }

    @Override
    public float[] getFloats() {
        Object object = this.mObject;
        object = object instanceof float[] ? (float[])object : null;
        return object;
    }

    @Override
    public int[] getInts() {
        Object object = this.mObject;
        object = object instanceof int[] ? (int[])object : null;
        return object;
    }

    @Override
    public Object getObjectValue() {
        return this.mObject;
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
        FrameFormat frameFormat;
        block4 : {
            block3 : {
                block2 : {
                    frameFormat = this.getFormat();
                    if (frameFormat.getObjectClass() != null) break block2;
                    this.setFormatObjectClass(object.getClass());
                    break block3;
                }
                if (!frameFormat.getObjectClass().isAssignableFrom(object.getClass())) break block4;
            }
            this.mObject = object;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Attempting to set object value of type '");
        stringBuilder.append(object.getClass());
        stringBuilder.append("' on SimpleFrame of type '");
        stringBuilder.append(frameFormat.getObjectClass());
        stringBuilder.append("'!");
        throw new RuntimeException(stringBuilder.toString());
    }

    @Override
    public void setInts(int[] arrn) {
        this.assertFrameMutable();
        this.setGenericObjectValue(arrn);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SimpleFrame (");
        stringBuilder.append(this.getFormat());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}

