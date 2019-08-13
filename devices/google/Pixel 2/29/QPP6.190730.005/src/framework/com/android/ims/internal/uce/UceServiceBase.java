/*
 * Decompiled with CFR 0.145.
 */
package com.android.ims.internal.uce;

import com.android.ims.internal.uce.common.UceLong;
import com.android.ims.internal.uce.options.IOptionsListener;
import com.android.ims.internal.uce.options.IOptionsService;
import com.android.ims.internal.uce.presence.IPresenceListener;
import com.android.ims.internal.uce.presence.IPresenceService;
import com.android.ims.internal.uce.uceservice.IUceListener;
import com.android.ims.internal.uce.uceservice.IUceService;

public abstract class UceServiceBase {
    private UceServiceBinder mBinder;

    public UceServiceBinder getBinder() {
        if (this.mBinder == null) {
            this.mBinder = new UceServiceBinder();
        }
        return this.mBinder;
    }

    protected int onCreateOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) {
        return 0;
    }

    protected int onCreateOptionsService(IOptionsListener iOptionsListener, UceLong uceLong, String string2) {
        return 0;
    }

    protected int onCreatePresService(IPresenceListener iPresenceListener, UceLong uceLong) {
        return 0;
    }

    protected int onCreatePresService(IPresenceListener iPresenceListener, UceLong uceLong, String string2) {
        return 0;
    }

    protected void onDestroyOptionsService(int n) {
    }

    protected void onDestroyPresService(int n) {
    }

    protected IOptionsService onGetOptionsService() {
        return null;
    }

    protected IOptionsService onGetOptionsService(String string2) {
        return null;
    }

    protected IPresenceService onGetPresenceService() {
        return null;
    }

    protected IPresenceService onGetPresenceService(String string2) {
        return null;
    }

    protected boolean onGetServiceStatus() {
        return false;
    }

    protected boolean onIsServiceStarted() {
        return false;
    }

    protected boolean onServiceStart(IUceListener iUceListener) {
        return false;
    }

    protected boolean onStopService() {
        return false;
    }

    private final class UceServiceBinder
    extends IUceService.Stub {
        private UceServiceBinder() {
        }

        @Override
        public int createOptionsService(IOptionsListener iOptionsListener, UceLong uceLong) {
            return UceServiceBase.this.onCreateOptionsService(iOptionsListener, uceLong);
        }

        @Override
        public int createOptionsServiceForSubscription(IOptionsListener iOptionsListener, UceLong uceLong, String string2) {
            return UceServiceBase.this.onCreateOptionsService(iOptionsListener, uceLong, string2);
        }

        @Override
        public int createPresenceService(IPresenceListener iPresenceListener, UceLong uceLong) {
            return UceServiceBase.this.onCreatePresService(iPresenceListener, uceLong);
        }

        @Override
        public int createPresenceServiceForSubscription(IPresenceListener iPresenceListener, UceLong uceLong, String string2) {
            return UceServiceBase.this.onCreatePresService(iPresenceListener, uceLong, string2);
        }

        @Override
        public void destroyOptionsService(int n) {
            UceServiceBase.this.onDestroyOptionsService(n);
        }

        @Override
        public void destroyPresenceService(int n) {
            UceServiceBase.this.onDestroyPresService(n);
        }

        @Override
        public IOptionsService getOptionsService() {
            return UceServiceBase.this.onGetOptionsService();
        }

        @Override
        public IOptionsService getOptionsServiceForSubscription(String string2) {
            return UceServiceBase.this.onGetOptionsService(string2);
        }

        @Override
        public IPresenceService getPresenceService() {
            return UceServiceBase.this.onGetPresenceService();
        }

        @Override
        public IPresenceService getPresenceServiceForSubscription(String string2) {
            return UceServiceBase.this.onGetPresenceService(string2);
        }

        @Override
        public boolean getServiceStatus() {
            return UceServiceBase.this.onGetServiceStatus();
        }

        @Override
        public boolean isServiceStarted() {
            return UceServiceBase.this.onIsServiceStarted();
        }

        @Override
        public boolean startService(IUceListener iUceListener) {
            return UceServiceBase.this.onServiceStart(iUceListener);
        }

        @Override
        public boolean stopService() {
            return UceServiceBase.this.onStopService();
        }
    }

}

