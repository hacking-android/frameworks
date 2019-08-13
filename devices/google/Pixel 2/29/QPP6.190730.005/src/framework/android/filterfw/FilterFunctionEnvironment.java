/*
 * Decompiled with CFR 0.145.
 */
package android.filterfw;

import android.filterfw.MffEnvironment;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterFactory;
import android.filterfw.core.FilterFunction;
import android.filterfw.core.FrameManager;

public class FilterFunctionEnvironment
extends MffEnvironment {
    public FilterFunctionEnvironment() {
        super(null);
    }

    public FilterFunctionEnvironment(FrameManager frameManager) {
        super(frameManager);
    }

    public FilterFunction createFunction(Class object, Object ... arrobject) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("FilterFunction(");
        charSequence.append(((Class)object).getSimpleName());
        charSequence.append(")");
        charSequence = charSequence.toString();
        object = FilterFactory.sharedFactory().createFilterByClass((Class)object, (String)charSequence);
        ((Filter)object).initWithAssignmentList(arrobject);
        return new FilterFunction(this.getContext(), (Filter)object);
    }
}

