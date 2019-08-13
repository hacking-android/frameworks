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

public final class ScriptIntrinsicResize
extends ScriptIntrinsic {
    private Allocation mInput;

    private ScriptIntrinsicResize(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    public static ScriptIntrinsicResize create(RenderScript renderScript) {
        return new ScriptIntrinsicResize(renderScript.nScriptIntrinsicCreate(12, 0L), renderScript);
    }

    public void forEach_bicubic(Allocation allocation) {
        if (allocation != this.mInput) {
            this.forEach_bicubic(allocation, null);
            return;
        }
        throw new RSIllegalArgumentException("Output cannot be same as Input.");
    }

    public void forEach_bicubic(Allocation allocation, Script.LaunchOptions launchOptions) {
        this.forEach(0, (Allocation)null, allocation, null, launchOptions);
    }

    public Script.FieldID getFieldID_Input() {
        return this.createFieldID(0, null);
    }

    public Script.KernelID getKernelID_bicubic() {
        return this.createKernelID(0, 2, null, null);
    }

    public void setInput(Allocation allocation) {
        Element element = allocation.getElement();
        if (!(element.isCompatible(Element.U8(this.mRS)) || element.isCompatible(Element.U8_2(this.mRS)) || element.isCompatible(Element.U8_3(this.mRS)) || element.isCompatible(Element.U8_4(this.mRS)) || element.isCompatible(Element.F32(this.mRS)) || element.isCompatible(Element.F32_2(this.mRS)) || element.isCompatible(Element.F32_3(this.mRS)) || element.isCompatible(Element.F32_4(this.mRS)))) {
            throw new RSIllegalArgumentException("Unsupported element type.");
        }
        this.mInput = allocation;
        this.setVar(0, allocation);
    }
}

