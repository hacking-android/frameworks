/*
 * Decompiled with CFR 0.145.
 */
package android.renderscript;

import android.content.res.Resources;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import android.renderscript.Script;
import java.io.IOException;
import java.io.InputStream;

public class ScriptC
extends Script {
    private static final String TAG = "ScriptC";

    protected ScriptC(int n, RenderScript renderScript) {
        super(n, renderScript);
    }

    protected ScriptC(long l, RenderScript renderScript) {
        super(l, renderScript);
    }

    protected ScriptC(RenderScript renderScript, Resources resources, int n) {
        super(0L, renderScript);
        long l = ScriptC.internalCreate(renderScript, resources, n);
        if (l != 0L) {
            this.setID(l);
            return;
        }
        throw new RSRuntimeException("Loading of ScriptC script failed.");
    }

    protected ScriptC(RenderScript renderScript, String string2, byte[] arrby, byte[] arrby2) {
        super(0L, renderScript);
        long l = RenderScript.sPointerSize == 4 ? ScriptC.internalStringCreate(renderScript, string2, arrby) : ScriptC.internalStringCreate(renderScript, string2, arrby2);
        if (l != 0L) {
            this.setID(l);
            return;
        }
        throw new RSRuntimeException("Loading of ScriptC script failed.");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private static long internalCreate(RenderScript var0, Resources var1_5, int var2_6) {
        // MONITORENTER : android.renderscript.ScriptC.class
        var3_6 = var1_4.openRawResource((int)var2_5);
        try {
            var4_7 = new byte[1024];
            var5_8 = 0;
            ** GOTO lbl11
        }
        catch (Throwable var0_1) {
            try {
                var3_6.close();
                throw var0_1;
            }
            catch (IOException var0_2) {
                var0_3 = new Resources.NotFoundException();
                throw var0_3;
            }
lbl11: // 1 sources:
            do {
                var6_9 = var4_7.length - var5_8;
                var7_10 = var4_7;
                var8_11 = var6_9;
                if (var6_9 == 0) {
                    var7_10 = new byte[var4_7.length * 2];
                    System.arraycopy(var4_7, 0, var7_10, 0, var4_7.length);
                    var8_11 = var7_10.length - var5_8;
                }
                if ((var8_11 = var3_6.read(var7_10, var5_8, var8_11)) <= 0) {
                    var3_6.close();
                    var9_12 = var0.nScriptCCreate(var1_4.getResourceEntryName((int)var2_5), RenderScript.getCachePath(), var7_10, var5_8);
                    return var9_12;
                }
                var5_8 += var8_11;
                var4_7 = var7_10;
            } while (true);
        }
    }

    private static long internalStringCreate(RenderScript renderScript, String string2, byte[] arrby) {
        synchronized (ScriptC.class) {
            long l = renderScript.nScriptCCreate(string2, RenderScript.getCachePath(), arrby, arrby.length);
            return l;
        }
    }
}

