/*
 * Decompiled with CFR 0.145.
 */
package android.net.sip;

public interface SipRegistrationListener {
    public void onRegistering(String var1);

    public void onRegistrationDone(String var1, long var2);

    public void onRegistrationFailed(String var1, int var2, String var3);
}

