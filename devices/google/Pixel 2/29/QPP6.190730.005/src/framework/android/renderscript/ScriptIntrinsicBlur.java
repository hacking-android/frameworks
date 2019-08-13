/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.Allocation;
import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsic;
import android.renderscript.Type;

public final class ScriptIntrinsicBlur
extends ScriptIntrinsic {
    private Allocation mInput;
    private final float[] mValues = new float[9];

    private ScriptIntrinsicBlur(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static ScriptIntrinsicBlur create(RenderScript object, Element element) {
        if (!element.isCompatible(Element.U8_4((RenderScript)object)) && !element.isCompatible(Element.U8((RenderScript)object))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        object = new ScriptIntrinsicBlur(((RenderScript)object).nScriptIntrinsicCreate(5, element.getID((RenderScript)object)), (RenderScript)object);
        ((ScriptIntrinsicBlur)object).setRadius(5.0f);
        return object;
    }

    public void forEach(Allocation allocation) {
        if (allocation.getType().getY() != 0) {
            this.forEach(0, (Allocation)null, allocation, null);
            return;
        }
        throw new RSIllegalArgumentException("Output is a 1D Allocation");
    }

    public void forEach(Allocation allocation, Script.LaunchOptions launchOptions) {
        if (allocation.getType().getY() != 0) {
            this.forEach(0, (Allocation)null, allocation, null, launchOptions);
            return;
        }
        throw new RSIllegalArgumentException("Output is a 1D Allocation");
    }

    public Script.FieldID getFieldID_Input() {
        return this.createFieldID(1, null);
    }

    public Script.KernelID getKernelID() {
        return this.createKernelID(0, 2, null, null);
    }

    public void setInput(Allocation allocation) {
        if (allocation.getType().getY() != 0) {
            Element element = allocation.getElement();
            if (!element.isCompatible(Element.U8_4(this.mRS)) && !element.isCompatible(Element.U8(this.mRS))) {
                throw new RSIllegalArgumentException("Unsupported element type.");
            }
            this.mInput = allocation;
            this.setVar(1, allocation);
            return;
        }
        throw new RSIllegalArgumentException("Input set to a 1D Allocation");
    }

    public void setRadius(float f) {
        if (!(f <= 0.0f) && !(f > 25.0f)) {
            this.setVar(0, f);
            return;
        }
        throw new RSIllegalArgumentException("Radius out of range (0 < r <= 25).");
    }
}

