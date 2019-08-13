/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.text;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.core.GenerateFieldPort;
import android.filterfw.format.ObjectFormat;

public class StringSource
extends Filter {
    private FrameFormat mOutputFormat;
    @GenerateFieldPort(name="stringValue")
    private String mString;

    public StringSource(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext object) {
        object = ((FilterContext)object).getFrameManager().newFrame(this.mOutputFormat);
        ((Frame)object).setObjectValue(this.mString);
        ((Frame)object).setTimestamp(-1L);
        this.pushOutput("string", (Frame)object);
        this.closeOutputPort("string");
    }

    @Override
    public void setupPorts() {
        this.mOutputFormat = ObjectFormat.fromClass(String.class, 1);
        this.addOutputPort("string", this.mOutputFormat);
    }
}

