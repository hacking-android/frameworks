/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.FieldPort;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import java.lang.reflect.Field;

public class FinalPort
extends FieldPort {
    public FinalPort(Filter filter, String string2, Field field, boolean bl) {
        super(filter, string2, field, bl);
    }

    @Override
    protected void setFieldFrame(Frame object, boolean bl) {
        synchronized (this) {
            this.assertPortIsOpen();
            this.checkFrameType((Frame)object, bl);
            if (this.mFilter.getStatus() == 0) {
                super.setFieldFrame((Frame)object, bl);
                super.transfer(null);
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Attempting to modify ");
            ((StringBuilder)object).append(this);
            ((StringBuilder)object).append("!");
            RuntimeException runtimeException = new RuntimeException(((StringBuilder)object).toString());
            throw runtimeException;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("final ");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }
}

