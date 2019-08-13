/*
 * Decompiled with CFR 0.145.
 */
package android.filterpacks.base;

import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.Frame;

public class NullFilter
extends Filter {
    public NullFilter(String string2) {
        super(string2);
    }

    @Override
    public void process(FilterContext filterContext) {
        this.pullInput("frame");
    }

    @Override
    public void setupPorts() {
        this.addInputPort("frame");
    }
}

