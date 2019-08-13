/*
 * Decompiled with CFR 0.145.
 */
package android.media.effect;

import android.annotation.UnsupportedAppUsage;
import android.filterfw.core.Filter;
import android.filterfw.core.FilterContext;
import android.filterfw.core.FilterFactory;
import android.filterfw.core.FilterFunction;
import android.filterfw.core.Frame;
import android.media.effect.EffectContext;
import android.media.effect.FilterEffect;

public class SingleFilterEffect
extends FilterEffect {
    protected FilterFunction mFunction;
    protected String mInputName;
    protected String mOutputName;

    @UnsupportedAppUsage
    public SingleFilterEffect(EffectContext object, String string2, Class class_, String string3, String string4, Object ... arrobject) {
        super((EffectContext)object, string2);
        this.mInputName = string3;
        this.mOutputName = string4;
        object = class_.getSimpleName();
        object = FilterFactory.sharedFactory().createFilterByClass(class_, (String)object);
        ((Filter)object).initWithAssignmentList(arrobject);
        this.mFunction = new FilterFunction(this.getFilterContext(), (Filter)object);
    }

    @Override
    public void apply(int n, int n2, int n3, int n4) {
        this.beginGLEffect();
        Frame frame = this.frameFromTexture(n, n2, n3);
        Frame frame2 = this.frameFromTexture(n4, n2, n3);
        Frame frame3 = this.mFunction.executeWithArgList(this.mInputName, frame);
        frame2.setDataFromFrame(frame3);
        frame.release();
        frame2.release();
        frame3.release();
        this.endGLEffect();
    }

    @Override
    public void release() {
        this.mFunction.tearDown();
        this.mFunction = null;
    }

    @Override
    public void setParameter(String string2, Object object) {
        this.mFunction.setInputValue(string2, object);
    }
}

