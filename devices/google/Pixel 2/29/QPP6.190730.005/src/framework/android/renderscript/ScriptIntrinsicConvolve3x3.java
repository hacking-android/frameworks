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

public final class ScriptIntrinsicConvolve3x3
extends ScriptIntrinsic {
    private Allocation mInput;
    private final float[] mValues = new float[9];

    private ScriptIntrinsicConvolve3x3(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static ScriptIntrinsicConvolve3x3 create(RenderScript object, Element element) {
        if (!(element.isCompatible(Element.U8((RenderScript)object)) || element.isCompatible(Element.U8_2((RenderScript)object)) || element.isCompatible(Element.U8_3((RenderScript)object)) || element.isCompatible(Element.U8_4((RenderScript)object)) || element.isCompatible(Element.F32((RenderScript)object)) || element.isCompatible(Element.F32_2((RenderScript)object)) || element.isCompatible(Element.F32_3((RenderScript)object)) || element.isCompatible(Element.F32_4((RenderScript)object)))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        object = new ScriptIntrinsicConvolve3x3(((RenderScript)object).nScriptIntrinsicCreate(1, element.getID((RenderScript)object)), (RenderScript)object);
        ((ScriptIntrinsicConvolve3x3)object).setCoefficients(new float[]{0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f});
        return object;
    }

    public void forEach(Allocation allocation) {
        this.forEach(0, (Allocation)null, allocation, null);
    }

    public void forEach(Allocation allocation, Script.LaunchOptions launchOptions) {
        this.forEach(0, (Allocation)null, allocation, null, launchOptions);
    }

    public Script.FieldID getFieldID_Input() {
        return this.createFieldID(1, null);
    }

    public Script.KernelID getKernelID() {
        return this.createKernelID(0, 2, null, null);
    }

    public void setCoefficients(float[] arrf) {
        float[] arrf2;
        FieldPacker fieldPacker = new FieldPacker(36);
        for (int i = 0; i < (arrf2 = this.mValues).length; ++i) {
            arrf2[i] = arrf[i];
            fieldPacker.addF32(arrf2[i]);
        }
        this.setVar(0, fieldPacker);
    }

    public void setInput(Allocation allocation) {
        this.mInput = allocation;
        this.setVar(1, allocation);
    }
}

