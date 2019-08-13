/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.text;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;
import android.filterfw.core.FrameFormat;
import android.filterfw.core.FrameManager;
import android.filterfw.format.ObjectFormat;
import java.util.Locale;

public class ToUpperCase
extends Filter {
    private FrameFormat mOutputFormat;

    public ToUpperCase(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext object) {
        String string2 = (String)this.pullInput("mixedcase").getObjectValue();
        object = ((FilterContext)object).getFrameManager().newFrame(this.mOutputFormat);
        ((Frame)object).setObjectValue(string2.toUpperCase(Locale.getDefault()));
        this.pushOutput("uppercase", (Frame)object);
    }

    @Override
    public void setupPorts() {
        this.mOutputFormat = ObjectFormat.fromClass(String.class, 1);
        this.addMaskedInputPort("mixedcase", this.mOutputFormat);
        this.addOutputPort("uppercase", this.mOutputFormat);
    }
}

