/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw.core;

import android.filterfw.core.FieldPort;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Program;
import java.lang.reflect.Field;

public class ProgramPort
extends FieldPort {
    protected String mVarName;

    public ProgramPort(Filter filter, String string2, String string3, Field field, boolean bl) {
        super(filter, string2, field, bl);
        this.mVarName = string3;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Program ");
        stringBuilder.append(super.toString());
        return stringBuilder.toString();
    }

    @Override
    public void transfer(FilterContext object) {
        synchronized (this) {
            boolean bl = this.mValueWaiting;
            if (bl && (object = this.mField.get(this.mFilter)) != null) {
                ((Program)object).setHostValue(this.mVarName, this.mValue);
                this.mValueWaiting = false;
            }
            return;
        }
    }
}

