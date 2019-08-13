/*
 * Decompiled with CFR 0.145.
 */
package android.text;

import android.graphics.BaseCanvas;
import android.graphics.Paint;

public interface GraphicsOperations
extends CharSequence {
    public void drawText(BaseCanvas var1, int var2, int var3, float var4, float var5, Paint var6);

    public void drawTextRun(BaseCanvas var1, int var2, int var3, int var4, int var5, float var6, float var7, boolean var8, Paint var9);

    public float getTextRunAdvances(int var1, int var2, int var3, int var4, boolean var5, float[] var6, int var7, Paint var8);

    public int getTextRunCursor(int var1, int var2, boolean var3, int var4, int var5, Paint var6);

    public int getTextWidths(int var1, int var2, float[] var3, Paint var4);

    public float measureText(int var1, int var2, Paint var3);
}

