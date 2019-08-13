/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.text;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.format.ObjectFormat;
import android.util.Log;

public class StringLogger
extends Filter {
    public StringLogger(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext filterContext) {
        Log.i("StringLogger", this.pullInput("string").getObjectValue().toString());
    }

    @Override
    public void setupPorts() {
        this.addMaskedInputPort("string", ObjectFormat.fromClass(Object.class, 1));
    }
}

