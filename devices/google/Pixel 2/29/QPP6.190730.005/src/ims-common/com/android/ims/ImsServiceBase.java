/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.os.Message
 *  android.telephony.ims.ImsCallProfile
 *  com.android.ims.internal.IImsCallSession
 *  com.android.ims.internal.IImsCallSessionListener
 *  com.android.ims.internal.IImsConfig
 *  com.android.ims.internal.IImsEcbm
 *  com.android.ims.internal.IImsMultiEndpoint
 *  com.android.ims.internal.IImsRegistrationListener
 *  com.android.ims.internal.IImsService
 *  com.android.ims.internal.IImsService$Stub
 *  com.android.ims.internal.IImsUt
 */
package com.android.ims;

import android.app.PendingIntent;
import android.os.Message;
import android.telephony.ims.ImsCallProfile;
import com.android.ims.internal.IImsCallSession;
import com.android.ims.internal.IImsCallSessionListener;
import com.android.ims.internal.IImsConfig;
import com.android.ims.internal.IImsEcbm;
import com.android.ims.internal.IImsMultiEndpoint;
import com.android.ims.internal.IImsRegistrationListener;
import com.android.ims.internal.IImsService;
import com.android.ims.internal.IImsUt;

public abstract class ImsServiceBase {
    private ImsServiceBinder mBinder;

    public ImsServiceBinder getBinder() {
        if (this.mBinder == null) {
            this.mBinder = new ImsServiceBinder();
        }
        return this.mBinder;
    }

    protected void onAddRegistrationListener(int n, int n2, IImsRegistrationListener iImsRegistrationListener) {
    }

    protected void onClose(int n) {
    }

    protected ImsCallProfile onCreateCallProfile(int n, int n2, int n3) {
        return null;
    }

    protected IImsCallSession onCreateCallSession(int n, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) {
        return null;
    }

    protected IImsConfig onGetConfigInterface(int n) {
        return null;
    }

    protected IImsEcbm onGetEcbmInterface(int n) {
        return null;
    }

    protected IImsMultiEndpoint onGetMultiEndpointInterface(int n) {
        return null;
    }

    protected IImsCallSession onGetPendingCallSession(int n, String string) {
        return null;
    }

    protected IImsUt onGetUtInterface(int n) {
        return null;
    }

    protected boolean onIsConnected(int n, int n2, int n3) {
        return false;
    }

    protected boolean onIsOpened(int n) {
        return false;
    }

    protected int onOpen(int n, int n2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) {
        return 0;
    }

    protected void onSetRegistrationListener(int n, IImsRegistrationListener iImsRegistrationListener) {
    }

    protected void onSetUiTTYMode(int n, int n2, Message message) {
    }

    protected void onTurnOffIms(int n) {
    }

    protected void onTurnOnIms(int n) {
    }

    private final class ImsServiceBinder
    extends IImsService.Stub {
        private ImsServiceBinder() {
        }

        public void addRegistrationListener(int n, int n2, IImsRegistrationListener iImsRegistrationListener) {
            ImsServiceBase.this.onAddRegistrationListener(n, n2, iImsRegistrationListener);
        }

        public void close(int n) {
            ImsServiceBase.this.onClose(n);
        }

        public ImsCallProfile createCallProfile(int n, int n2, int n3) {
            return ImsServiceBase.this.onCreateCallProfile(n, n2, n3);
        }

        public IImsCallSession createCallSession(int n, ImsCallProfile imsCallProfile, IImsCallSessionListener iImsCallSessionListener) {
            return ImsServiceBase.this.onCreateCallSession(n, imsCallProfile, iImsCallSessionListener);
        }

        public IImsConfig getConfigInterface(int n) {
            return ImsServiceBase.this.onGetConfigInterface(n);
        }

        public IImsEcbm getEcbmInterface(int n) {
            return ImsServiceBase.this.onGetEcbmInterface(n);
        }

        public IImsMultiEndpoint getMultiEndpointInterface(int n) {
            return ImsServiceBase.this.onGetMultiEndpointInterface(n);
        }

        public IImsCallSession getPendingCallSession(int n, String string) {
            return ImsServiceBase.this.onGetPendingCallSession(n, string);
        }

        public IImsUt getUtInterface(int n) {
            return ImsServiceBase.this.onGetUtInterface(n);
        }

        public boolean isConnected(int n, int n2, int n3) {
            return ImsServiceBase.this.onIsConnected(n, n2, n3);
        }

        public boolean isOpened(int n) {
            return ImsServiceBase.this.onIsOpened(n);
        }

        public int open(int n, int n2, PendingIntent pendingIntent, IImsRegistrationListener iImsRegistrationListener) {
            return ImsServiceBase.this.onOpen(n, n2, pendingIntent, iImsRegistrationListener);
        }

        public void setRegistrationListener(int n, IImsRegistrationListener iImsRegistrationListener) {
            ImsServiceBase.this.onSetRegistrationListener(n, iImsRegistrationListener);
        }

        public void setUiTTYMode(int n, int n2, Message message) {
            ImsServiceBase.this.onSetUiTTYMode(n, n2, message);
        }

        public void turnOffIms(int n) {
            ImsServiceBase.this.onTurnOffIms(n);
        }

        public void turnOnIms(int n) {
            ImsServiceBase.this.onTurnOnIms(n);
        }
    }

}

