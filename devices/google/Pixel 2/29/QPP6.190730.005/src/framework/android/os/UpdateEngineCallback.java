/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;

@SystemApi
public abstract class UpdateEngineCallback {
    public abstract void onPayloadApplicationComplete(int var1);

    public abstract void onStatusUpdate(int var1, float var2);
}

