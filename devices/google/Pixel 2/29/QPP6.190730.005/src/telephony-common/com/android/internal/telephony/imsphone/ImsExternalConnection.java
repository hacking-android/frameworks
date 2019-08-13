/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.content.Context
 *  android.net.Uri
 *  android.telephony.PhoneNumberUtils
 */
package com.android.internal.telephony.imsphone;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.Uri;
import android.telephony.PhoneNumberUtils;
import com.android.internal.telephony.Call;
import com.android.internal.telephony.CallStateException;
import com.android.internal.telephony.Connection;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.UUSInfo;
import com.android.internal.telephony.imsphone.ImsExternalCall;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ImsExternalConnection
extends Connection {
    private static final String CONFERENCE_PREFIX = "conf";
    private ImsExternalCall mCall;
    private int mCallId;
    private final Context mContext;
    private boolean mIsPullable;
    private final Set<Listener> mListeners = Collections.newSetFromMap(new ConcurrentHashMap(8, 0.9f, 1));
    private Uri mOriginalAddress;

    protected ImsExternalConnection(Phone phone, int n, Uri uri, boolean bl) {
        super(phone.getPhoneType());
        this.mContext = phone.getContext();
        this.mCall = new ImsExternalCall(phone, this);
        this.mCallId = n;
        this.setExternalConnectionAddress(uri);
        this.mNumberPresentation = 1;
        this.mIsPullable = bl;
        this.rebuildCapabilities();
        this.setActive();
    }

    @UnsupportedAppUsage
    private void rebuildCapabilities() {
        int n = 16;
        if (this.mIsPullable) {
            n = 16 | 32;
        }
        this.setConnectionCapabilities(n);
    }

    public void addListener(Listener listener) {
        this.mListeners.add(listener);
    }

    @Override
    public void cancelPostDial() {
    }

    @Override
    public void deflect(String string) throws CallStateException {
        throw new CallStateException("Deflect is not supported for external calls");
    }

    @Override
    public Call getCall() {
        return this.mCall;
    }

    public int getCallId() {
        return this.mCallId;
    }

    @Override
    public long getDisconnectTime() {
        return 0L;
    }

    @Override
    public long getHoldDurationMillis() {
        return 0L;
    }

    @Override
    public int getNumberPresentation() {
        return this.mNumberPresentation;
    }

    @Override
    public int getPreciseDisconnectCause() {
        return 0;
    }

    @Override
    public UUSInfo getUUSInfo() {
        return null;
    }

    @Override
    public String getVendorDisconnectCause() {
        return null;
    }

    @Override
    public void hangup() throws CallStateException {
    }

    @Override
    public boolean isMultiparty() {
        return false;
    }

    @Override
    public void proceedAfterWaitChar() {
    }

    @Override
    public void proceedAfterWildChar(String string) {
    }

    @Override
    public void pullExternalCall() {
        Iterator<Listener> iterator = this.mListeners.iterator();
        while (iterator.hasNext()) {
            iterator.next().onPullExternalCall(this);
        }
    }

    public void removeListener(Listener listener) {
        this.mListeners.remove(listener);
    }

    @Override
    public void separate() throws CallStateException {
    }

    @UnsupportedAppUsage
    public void setActive() {
        ImsExternalCall imsExternalCall = this.mCall;
        if (imsExternalCall == null) {
            return;
        }
        imsExternalCall.setActive();
    }

    public void setExternalConnectionAddress(Uri uri) {
        this.mOriginalAddress = uri;
        if ("sip".equals(uri.getScheme()) && uri.getSchemeSpecificPart().startsWith(CONFERENCE_PREFIX)) {
            this.mCnapName = this.mContext.getString(17039668);
            this.mCnapNamePresentation = 1;
            this.mAddress = "";
            this.mNumberPresentation = 2;
            return;
        }
        this.mAddress = PhoneNumberUtils.convertSipUriToTelUri((Uri)uri).getSchemeSpecificPart();
    }

    public void setIsPullable(boolean bl) {
        this.mIsPullable = bl;
        this.rebuildCapabilities();
    }

    public void setTerminated() {
        ImsExternalCall imsExternalCall = this.mCall;
        if (imsExternalCall == null) {
            return;
        }
        imsExternalCall.setTerminated();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("[ImsExternalConnection dialogCallId:");
        stringBuilder.append(this.mCallId);
        stringBuilder.append(" state:");
        if (this.mCall.getState() == Call.State.ACTIVE) {
            stringBuilder.append("Active");
        } else if (this.mCall.getState() == Call.State.DISCONNECTED) {
            stringBuilder.append("Disconnected");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static interface Listener {
        public void onPullExternalCall(ImsExternalConnection var1);
    }

}

