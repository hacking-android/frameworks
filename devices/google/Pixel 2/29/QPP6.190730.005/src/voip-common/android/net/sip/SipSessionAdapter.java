/*
 * Decompiled with CFR 0.145.
 */
package android.net.sip;

import android.net.sip.ISipSession;
import android.net.sip.ISipSessionListener;
import android.net.sip.SipProfile;

public class SipSessionAdapter
extends ISipSessionListener.Stub {
    @Override
    public void onCallBusy(ISipSession iSipSession) {
    }

    @Override
    public void onCallChangeFailed(ISipSession iSipSession, int n, String string) {
    }

    @Override
    public void onCallEnded(ISipSession iSipSession) {
    }

    @Override
    public void onCallEstablished(ISipSession iSipSession, String string) {
    }

    @Override
    public void onCallTransferring(ISipSession iSipSession, String string) {
    }

    @Override
    public void onCalling(ISipSession iSipSession) {
    }

    @Override
    public void onError(ISipSession iSipSession, int n, String string) {
    }

    @Override
    public void onRegistering(ISipSession iSipSession) {
    }

    @Override
    public void onRegistrationDone(ISipSession iSipSession, int n) {
    }

    @Override
    public void onRegistrationFailed(ISipSession iSipSession, int n, String string) {
    }

    @Override
    public void onRegistrationTimeout(ISipSession iSipSession) {
    }

    @Override
    public void onRinging(ISipSession iSipSession, SipProfile sipProfile, String string) {
    }

    @Override
    public void onRingingBack(ISipSession iSipSession) {
    }
}

