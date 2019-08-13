/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.DeadObjectException
 *  android.telephony.Rlog
 */
package com.android.server.sip;

import android.net.sip.ISipSession;
import android.net.sip.ISipSessionListener;
import android.net.sip.SipProfile;
import android.os.DeadObjectException;
import android.telephony.Rlog;

class SipSessionListenerProxy
extends ISipSessionListener.Stub {
    private static final String TAG = "SipSessionListnerProxy";
    private ISipSessionListener mListener;

    SipSessionListenerProxy() {
    }

    private void handle(Throwable throwable, String string) {
        if (throwable instanceof DeadObjectException) {
            this.mListener = null;
        } else if (this.mListener != null) {
            this.loge(string, throwable);
        }
    }

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string, Throwable throwable) {
        Rlog.e((String)TAG, (String)string, (Throwable)throwable);
    }

    private void proxy(Runnable runnable) {
        new Thread(runnable, "SipSessionCallbackThread").start();
    }

    public ISipSessionListener getListener() {
        return this.mListener;
    }

    @Override
    public void onCallBusy(final ISipSession iSipSession) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onCallBusy(iSipSession);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onCallBusy()");
                }
            }
        });
    }

    @Override
    public void onCallChangeFailed(final ISipSession iSipSession, final int n, final String string) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onCallChangeFailed(iSipSession, n, string);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onCallChangeFailed()");
                }
            }
        });
    }

    @Override
    public void onCallEnded(final ISipSession iSipSession) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onCallEnded(iSipSession);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onCallEnded()");
                }
            }
        });
    }

    @Override
    public void onCallEstablished(final ISipSession iSipSession, final String string) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onCallEstablished(iSipSession, string);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onCallEstablished()");
                }
            }
        });
    }

    @Override
    public void onCallTransferring(final ISipSession iSipSession, final String string) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onCallTransferring(iSipSession, string);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onCallTransferring()");
                }
            }
        });
    }

    @Override
    public void onCalling(final ISipSession iSipSession) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onCalling(iSipSession);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onCalling()");
                }
            }
        });
    }

    @Override
    public void onError(final ISipSession iSipSession, final int n, final String string) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onError(iSipSession, n, string);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onError()");
                }
            }
        });
    }

    @Override
    public void onRegistering(final ISipSession iSipSession) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onRegistering(iSipSession);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onRegistering()");
                }
            }
        });
    }

    @Override
    public void onRegistrationDone(final ISipSession iSipSession, final int n) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onRegistrationDone(iSipSession, n);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onRegistrationDone()");
                }
            }
        });
    }

    @Override
    public void onRegistrationFailed(final ISipSession iSipSession, final int n, final String string) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onRegistrationFailed(iSipSession, n, string);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onRegistrationFailed()");
                }
            }
        });
    }

    @Override
    public void onRegistrationTimeout(final ISipSession iSipSession) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onRegistrationTimeout(iSipSession);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onRegistrationTimeout()");
                }
            }
        });
    }

    @Override
    public void onRinging(final ISipSession iSipSession, final SipProfile sipProfile, final String string) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onRinging(iSipSession, sipProfile, string);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onRinging()");
                }
            }
        });
    }

    @Override
    public void onRingingBack(final ISipSession iSipSession) {
        if (this.mListener == null) {
            return;
        }
        this.proxy(new Runnable(){

            @Override
            public void run() {
                try {
                    SipSessionListenerProxy.this.mListener.onRingingBack(iSipSession);
                }
                catch (Throwable throwable) {
                    SipSessionListenerProxy.this.handle(throwable, "onRingingBack()");
                }
            }
        });
    }

    public void setListener(ISipSessionListener iSipSessionListener) {
        this.mListener = iSipSessionListener;
    }

}

