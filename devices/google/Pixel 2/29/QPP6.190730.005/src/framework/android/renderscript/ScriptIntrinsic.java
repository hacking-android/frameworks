/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.Script;

public abstract class ScriptIntrinsic
extends Script {
    ScriptIntrinsic(long l, RenderScript renderScript) {
        super(l, renderScript);
        if (l != 0L) {
            return;
        }
        throw new RSRuntimeException("Loading of ScriptIntrinsic failed.");
    }
}

