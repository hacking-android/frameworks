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

public final class ScriptIntrinsicConvolve5x5
extends ScriptIntrinsic {
    private Allocation mInput;
    private final float[] mValues = new float[25];

    private ScriptIntrinsicConvolve5x5(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static ScriptIntrinsicConvolve5x5 create(RenderScript renderScript, Element element) {
        if (!(element.isCompatible(Element.U8(renderScript)) || element.isCompatible(Element.U8_2(renderScript)) || element.isCompatible(Element.U8_3(renderScript)) || element.isCompatible(Element.U8_4(renderScript)) || element.isCompatible(Element.F32(renderScript)) || element.isCompatible(Element.F32_2(renderScript)) || element.isCompatible(Element.F32_3(renderScript)) || element.isCompatible(Element.F32_4(renderScript)))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        return new ScriptIntrinsicConvolve5x5(renderScript.nScriptIntrinsicCreate(4, element.getID(renderScript)), renderScript);
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
        FieldPacker fieldPacker = new FieldPacker(100);
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

