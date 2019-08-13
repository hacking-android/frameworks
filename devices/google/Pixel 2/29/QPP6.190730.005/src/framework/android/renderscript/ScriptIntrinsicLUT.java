/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.Allocation;
import android.renderscript.BaseObj;
import android.renderscript.Element;
import android.renderscript.FieldPacker;
import android.renderscript.Matrix4f;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import android.renderscript.ScriptIntrinsic;

public final class ScriptIntrinsicLUT
extends ScriptIntrinsic {
    private final byte[] mCache = new byte[1024];
    private boolean mDirty = true;
    private final Matrix4f mMatrix = new Matrix4f();
    private Allocation mTables;

    private ScriptIntrinsicLUT(long l, RenderScript arrby) {
        super(l, (RenderScript)arrby);
        this.mTables = Allocation.createSized((RenderScript)arrby, Element.U8((RenderScript)arrby), 1024);
        for (int i = 0; i < 256; ++i) {
            arrby = this.mCache;
            arrby[i] = (byte)i;
            arrby[i + 256] = (byte)i;
            arrby[i + 512] = (byte)i;
            arrby[i + 768] = (byte)i;
        }
        this.setVar(0, this.mTables);
    }

    public static ScriptIntrinsicLUT create(RenderScript renderScript, Element element) {
        return new ScriptIntrinsicLUT(renderScript.nScriptIntrinsicCreate(3, element.getID(renderScript)), renderScript);
    }

    private void validate(int n, int n2) {
        if (n >= 0 && n <= 255) {
            if (n2 >= 0 && n2 <= 255) {
                return;
            }
            throw new RSIllegalArgumentException("Value out of range (0-255).");
        }
        throw new RSIllegalArgumentException("Index out of range (0-255).");
    }

    @Override
    public void destroy() {
        this.mTables.destroy();
        super.destroy();
    }

    public void forEach(Allocation allocation, Allocation allocation2) {
        this.forEach(allocation, allocation2, null);
    }

    public void forEach(Allocation allocation, Allocation allocation2, Script.LaunchOptions launchOptions) {
        if (this.mDirty) {
            this.mDirty = false;
            this.mTables.copyFromUnchecked(this.mCache);
        }
        this.forEach(0, allocation, allocation2, null, launchOptions);
    }

    public Script.KernelID getKernelID() {
        return this.createKernelID(0, 3, null, null);
    }

    public void setAlpha(int n, int n2) {
        this.validate(n, n2);
        this.mCache[n + 768] = (byte)n2;
        this.mDirty = true;
    }

    public void setBlue(int n, int n2) {
        this.validate(n, n2);
        this.mCache[n + 512] = (byte)n2;
        this.mDirty = true;
    }

    public void setGreen(int n, int n2) {
        this.validate(n, n2);
        this.mCache[n + 256] = (byte)n2;
        this.mDirty = true;
    }

    public void setRed(int n, int n2) {
        this.validate(n, n2);
        this.mCache[n] = (byte)n2;
        this.mDirty = true;
    }
}

