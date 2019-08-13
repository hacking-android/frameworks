/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.InputPort;
import java.lang.reflect.Field;

public class FieldPort
extends InputPort {
    protected Field mField;
    protected boolean mHasFrame;
    protected Object mValue;
    protected boolean mValueWaiting = false;

    public FieldPort(Filter filter, String string2, Field field, boolean bl) {
        super(filter, string2);
        this.mField = field;
        this.mHasFrame = bl;
    }

    @Override
    public boolean acceptsFrame() {
        synchronized (this) {
            boolean bl = this.mValueWaiting;
            return bl ^ true;
        }
    }

    @Override
    public void clear() {
    }

    @Override
    public Object getTarget() {
        try {
            Object object = this.mField.get(this.mFilter);
            return object;
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
    }

    @Override
    public boolean hasFrame() {
        synchronized (this) {
            boolean bl = this.mHasFrame;
            return bl;
        }
    }

    @Override
    public Frame pullFrame() {
        synchronized (this) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot pull frame on ");
            stringBuilder.append(this);
            stringBuilder.append("!");
            RuntimeException runtimeException = new RuntimeException(stringBuilder.toString());
            throw runtimeException;
        }
    }

    @Override
    public void pushFrame(Frame frame) {
        this.setFieldFrame(frame, false);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void setFieldFrame(Frame object, boolean bl) {
        synchronized (this) {
            void var2_2;
            this.assertPortIsOpen();
            this.checkFrameType((Frame)object, (boolean)var2_2);
            object = ((Frame)object).getObjectValue();
            if (object == null && this.mValue != null || !object.equals(this.mValue)) {
                this.mValue = object;
                this.mValueWaiting = true;
            }
            this.mHasFrame = true;
            return;
        }
    }

    @Override
    public void setFrame(Frame frame) {
        this.setFieldFrame(frame, true);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("field ");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }

    @Override
    public void transfer(FilterContext filterContext) {
        synchronized (this) {
            block10 : {
                boolean bl = this.mValueWaiting;
                if (!bl) break block10;
                try {
                    this.mField.set(this.mFilter, this.mValue);
                    this.mValueWaiting = false;
                    if (filterContext != null) {
                        this.mFilter.notifyFieldPortValueUpdated(this.mName, filterContext);
                    }
                }
                catch (IllegalAccessException illegalAccessException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Access to field '");
                    stringBuilder.append(this.mField.getName());
                    stringBuilder.append("' was denied!");
                    RuntimeException runtimeException = new RuntimeException(stringBuilder.toString());
                    throw runtimeException;
                }
            }
            return;
            finally {
            }
        }
    }
}

