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

public final class ScriptIntrinsic3DLUT
extends ScriptIntrinsic {
    private Element mElement;
    private Allocation mLUT;

    private ScriptIntrinsic3DLUT(long l, RenderScript renderScript, Element element) {
        super(l, renderScript);
        this.mElement = element;
    }

    public static ScriptIntrinsic3DLUT create(RenderScript renderScript, Element element) {
        long l = renderScript.nScriptIntrinsicCreate(8, element.getID(renderScript));
        if (element.isCompatible(Element.U8_4(renderScript))) {
            return new ScriptIntrinsic3DLUT(l, renderScript, element);
        }
        throw new RSIllegalArgumentException("Element must be compatible with uchar4.");
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        this.forEach(allocation, allocation2, null);
    }

    public void forEach(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        this.forEach(0, allocation, allocation2, null, launchOptions);
    }

    public Script.KernelID getKernelID() {
        return this.createKernelID(0, 3, null, null);
    }

    public void setLUT(Allocation allocation) {
        Type type = allocation.getType();
        if (type.getZ() != 0) {
            if (type.getElement().isCompatible(this.mElement)) {
                this.mLUT = allocation;
                this.setVar(0, this.mLUT);
                return;
            }
            throw new RSIllegalArgumentException("LUT element type must match.");
        }
        throw new RSIllegalArgumentException("LUT must be 3d.");
    }
}

