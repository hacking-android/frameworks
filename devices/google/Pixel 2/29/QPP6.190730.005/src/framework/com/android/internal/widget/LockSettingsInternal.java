/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.widget;

import com.android.internal.widget.LockPatternUtils;

public abstract class LockSettingsInternal {
    public abstract long addEscrowToken(byte[] var1, int var2, LockPatternUtils.EscrowTokenStateChangeCallback var3);

    public abstract boolean isEscrowTokenActive(long var1, int var3);

    public abstract boolean removeEscrowToken(long var1, int var3);

    public abstract boolean setLockCredentialWithToken(byte[] var1, int var2, long var3, byte[] var5, int var6, int var7);

    public abstract boolean unlockUserWithToken(long var1, byte[] var3, int var4);
}

