/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.pm.ServiceInfo
 *  android.os.Binder
 *  android.os.Handler
 *  android.os.Message
 *  android.os.RemoteException
 *  android.service.carrier.ICarrierMessagingCallback
 *  android.service.carrier.ICarrierMessagingCallback$Stub
 *  android.service.carrier.ICarrierMessagingService
 *  android.service.carrier.MessagePdu
 *  android.telephony.Rlog
 *  android.util.LocalLog
 *  com.android.internal.annotations.VisibleForTesting
 */
package com.android.internal.telephony;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.service.carrier.ICarrierMessagingCallback;
import android.service.carrier.ICarrierMessagingService;
import android.service.carrier.MessagePdu;
import android.telephony.CarrierMessagingServiceManager;
import android.telephony.Rlog;
import android.util.LocalLog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.telephony.CarrierSmsUtils;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.uicc.UiccCard;
import com.android.internal.telephony.uicc.UiccController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CarrierServicesSmsFilter {
    protected static final boolean DBG = true;
    public static final int EVENT_ON_FILTER_COMPLETE_NOT_CALLED = 1;
    public static final int FILTER_COMPLETE_TIMEOUT_MS = 600000;
    private final CallbackTimeoutHandler mCallbackTimeoutHandler;
    private final CarrierServicesSmsFilterCallbackInterface mCarrierServicesSmsFilterCallback;
    private final Context mContext;
    private final int mDestPort;
    private FilterAggregator mFilterAggregator;
    private final LocalLog mLocalLog;
    private final String mLogTag;
    private final String mPduFormat;
    private final byte[][] mPdus;
    private final Phone mPhone;

    @VisibleForTesting
    public CarrierServicesSmsFilter(Context context, Phone phone, byte[][] arrby, int n, String string, CarrierServicesSmsFilterCallbackInterface carrierServicesSmsFilterCallbackInterface, String string2, LocalLog localLog) {
        this.mContext = context;
        this.mPhone = phone;
        this.mPdus = arrby;
        this.mDestPort = n;
        this.mPduFormat = string;
        this.mCarrierServicesSmsFilterCallback = carrierServicesSmsFilterCallbackInterface;
        this.mLogTag = string2;
        this.mCallbackTimeoutHandler = new CallbackTimeoutHandler();
        this.mLocalLog = localLog;
    }

    private void filterWithPackage(String string, FilterAggregator filterAggregator) {
        CarrierSmsFilter carrierSmsFilter = new CarrierSmsFilter(this.mPdus, this.mDestPort, this.mPduFormat);
        CarrierSmsFilterCallback carrierSmsFilterCallback = new CarrierSmsFilterCallback(filterAggregator, carrierSmsFilter);
        filterAggregator.addToCallbacks(carrierSmsFilterCallback);
        carrierSmsFilter.filterSms(string, carrierSmsFilterCallback);
    }

    private Optional<String> getCarrierAppPackageForFiltering() {
        List<String> list = null;
        Object object = UiccController.getInstance().getUiccCard(this.mPhone.getPhoneId());
        if (object != null) {
            list = ((UiccCard)object).getCarrierPackageNamesForIntent(this.mContext.getPackageManager(), new Intent("android.service.carrier.CarrierMessagingService"));
        } else {
            Rlog.e((String)this.mLogTag, (String)"UiccCard not initialized.");
        }
        if (list != null && list.size() == 1) {
            this.log("Found carrier package.");
            return Optional.of(list.get(0));
        }
        List<String> list2 = this.getSystemAppForIntent(new Intent("android.service.carrier.CarrierMessagingService"));
        if (list2 != null && list2.size() == 1) {
            this.log("Found system package.");
            return Optional.of(list2.get(0));
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Unable to find carrier package: ");
        ((StringBuilder)object).append(list);
        ((StringBuilder)object).append(", nor systemPackages: ");
        ((StringBuilder)object).append(list2);
        this.logv(((StringBuilder)object).toString());
        return Optional.empty();
    }

    private List<String> getSystemAppForIntent(Intent object) {
        ArrayList<String> arrayList = new ArrayList<String>();
        PackageManager packageManager = this.mContext.getPackageManager();
        for (Object object2 : packageManager.queryIntentServices((Intent)object, 0)) {
            CharSequence charSequence;
            if (((ResolveInfo)object2).serviceInfo == null) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Can't get service information from ");
                ((StringBuilder)charSequence).append(object2);
                this.loge(((StringBuilder)charSequence).toString());
                continue;
            }
            charSequence = object2.serviceInfo.packageName;
            if (packageManager.checkPermission("android.permission.CARRIER_FILTER_SMS", (String)charSequence) != 0) continue;
            arrayList.add((String)charSequence);
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("getSystemAppForIntent: added package ");
            ((StringBuilder)object2).append((String)charSequence);
            this.log(((StringBuilder)object2).toString());
        }
        return arrayList;
    }

    private void log(String string) {
        Rlog.d((String)this.mLogTag, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)this.mLogTag, (String)string);
    }

    private void logv(String string) {
        Rlog.e((String)this.mLogTag, (String)string);
    }

    @VisibleForTesting
    public boolean filter() {
        Object object = this.getCarrierAppPackageForFiltering();
        Object object2 = new ArrayList<Object>();
        if (((Optional)object).isPresent()) {
            object2.add(((Optional)object).get());
        }
        if ((object = CarrierSmsUtils.getCarrierImsPackageForIntent(this.mContext, this.mPhone, new Intent("android.service.carrier.CarrierMessagingService"))) != null) {
            object2.add(object);
        }
        if (this.mFilterAggregator == null) {
            int n = object2.size();
            if (n > 0) {
                this.mFilterAggregator = new FilterAggregator(n);
                object = this.mCallbackTimeoutHandler;
                object.sendMessageDelayed(object.obtainMessage(1), 600000L);
                object2 = object2.iterator();
                while (object2.hasNext()) {
                    this.filterWithPackage((String)object2.next(), this.mFilterAggregator);
                }
                return true;
            }
            return false;
        }
        this.loge("Cannot reuse the same CarrierServiceSmsFilter object for filtering.");
        throw new RuntimeException("Cannot reuse the same CarrierServiceSmsFilter object for filtering.");
    }

    protected final class CallbackTimeoutHandler
    extends Handler {
        private static final boolean DBG = true;

        protected CallbackTimeoutHandler() {
        }

        private void handleFilterCallbacksTimeout() {
            for (CarrierSmsFilterCallback carrierSmsFilterCallback : CarrierServicesSmsFilter.this.mFilterAggregator.mCallbacks) {
                CarrierServicesSmsFilter.this.log("handleFilterCallbacksTimeout: calling onFilterComplete");
                carrierSmsFilterCallback.onFilterComplete(0);
            }
        }

        public void handleMessage(Message message) {
            CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("CallbackTimeoutHandler handleMessage(");
            stringBuilder.append(message.what);
            stringBuilder.append(")");
            carrierServicesSmsFilter.log(stringBuilder.toString());
            if (message.what == 1) {
                CarrierServicesSmsFilter.this.mLocalLog.log("CarrierServicesSmsFilter: onFilterComplete timeout: not called before 600000 milliseconds.");
                this.handleFilterCallbacksTimeout();
            }
        }
    }

    @VisibleForTesting
    public static interface CarrierServicesSmsFilterCallbackInterface {
        public void onFilterComplete(int var1);
    }

    private final class CarrierSmsFilter
    extends CarrierMessagingServiceManager {
        private final int mDestPort;
        private final byte[][] mPdus;
        private volatile CarrierSmsFilterCallback mSmsFilterCallback;
        private final String mSmsFormat;

        CarrierSmsFilter(byte[][] arrby, int n, String string) {
            this.mPdus = arrby;
            this.mDestPort = n;
            this.mSmsFormat = string;
        }

        void filterSms(String string, CarrierSmsFilterCallback carrierSmsFilterCallback) {
            this.mSmsFilterCallback = carrierSmsFilterCallback;
            if (!this.bindToCarrierMessagingService(CarrierServicesSmsFilter.this.mContext, string)) {
                CarrierServicesSmsFilter.this.loge("bindService() for carrier messaging service failed");
                carrierSmsFilterCallback.onFilterComplete(0);
            } else {
                CarrierServicesSmsFilter.this.logv("bindService() for carrier messaging service succeeded");
            }
        }

        @Override
        protected void onServiceReady(ICarrierMessagingService iCarrierMessagingService) {
            try {
                CarrierServicesSmsFilter.this.log("onServiceReady: calling filterSms");
                MessagePdu messagePdu = new MessagePdu(Arrays.asList(this.mPdus));
                iCarrierMessagingService.filterSms(messagePdu, this.mSmsFormat, this.mDestPort, CarrierServicesSmsFilter.this.mPhone.getSubId(), (ICarrierMessagingCallback)this.mSmsFilterCallback);
            }
            catch (RemoteException remoteException) {
                CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Exception filtering the SMS: ");
                stringBuilder.append((Object)remoteException);
                carrierServicesSmsFilter.loge(stringBuilder.toString());
                this.mSmsFilterCallback.onFilterComplete(0);
            }
        }
    }

    private final class CarrierSmsFilterCallback
    extends ICarrierMessagingCallback.Stub {
        private final CarrierMessagingServiceManager mCarrierMessagingServiceManager;
        private final FilterAggregator mFilterAggregator;
        private boolean mIsOnFilterCompleteCalled;

        CarrierSmsFilterCallback(FilterAggregator filterAggregator, CarrierMessagingServiceManager carrierMessagingServiceManager) {
            this.mFilterAggregator = filterAggregator;
            this.mCarrierMessagingServiceManager = carrierMessagingServiceManager;
            this.mIsOnFilterCompleteCalled = false;
        }

        public void onDownloadMmsComplete(int n) {
            CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected onDownloadMmsComplete call with result: ");
            stringBuilder.append(n);
            carrierServicesSmsFilter.loge(stringBuilder.toString());
        }

        public void onFilterComplete(int n) {
            CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("onFilterComplete called with result: ");
            stringBuilder.append(n);
            carrierServicesSmsFilter.log(stringBuilder.toString());
            if (!this.mIsOnFilterCompleteCalled) {
                this.mIsOnFilterCompleteCalled = true;
                this.mCarrierMessagingServiceManager.disposeConnection(CarrierServicesSmsFilter.this.mContext);
                this.mFilterAggregator.onFilterComplete(n);
            }
        }

        public void onSendMmsComplete(int n, byte[] object) {
            CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected onSendMmsComplete call with result: ");
            ((StringBuilder)object).append(n);
            carrierServicesSmsFilter.loge(((StringBuilder)object).toString());
        }

        public void onSendMultipartSmsComplete(int n, int[] object) {
            CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected onSendMultipartSmsComplete call with result: ");
            ((StringBuilder)object).append(n);
            carrierServicesSmsFilter.loge(((StringBuilder)object).toString());
        }

        public void onSendSmsComplete(int n, int n2) {
            CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unexpected onSendSmsComplete call with result: ");
            stringBuilder.append(n);
            carrierServicesSmsFilter.loge(stringBuilder.toString());
        }
    }

    private final class FilterAggregator {
        private final Set<CarrierSmsFilterCallback> mCallbacks;
        private final Object mFilterLock = new Object();
        private int mFilterResult;
        private int mNumPendingFilters;

        FilterAggregator(int n) {
            this.mNumPendingFilters = n;
            this.mCallbacks = new HashSet<CarrierSmsFilterCallback>();
            this.mFilterResult = 0;
        }

        private void addToCallbacks(CarrierSmsFilterCallback carrierSmsFilterCallback) {
            this.mCallbacks.add(carrierSmsFilterCallback);
        }

        private void combine(int n) {
            this.mFilterResult |= n;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        void onFilterComplete(int n) {
            Object object = this.mFilterLock;
            synchronized (object) {
                block7 : {
                    block6 : {
                        --this.mNumPendingFilters;
                        this.combine(n);
                        if (this.mNumPendingFilters != 0) break block6;
                        long l = Binder.clearCallingIdentity();
                        CarrierServicesSmsFilter.this.mCarrierServicesSmsFilterCallback.onFilterComplete(this.mFilterResult);
                        CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("onFilterComplete: called successfully with result = ");
                        stringBuilder.append(n);
                        carrierServicesSmsFilter.log(stringBuilder.toString());
                        CarrierServicesSmsFilter.this.mCallbackTimeoutHandler.removeMessages(1);
                        break block7;
                        finally {
                            Binder.restoreCallingIdentity((long)l);
                        }
                    }
                    CarrierServicesSmsFilter carrierServicesSmsFilter = CarrierServicesSmsFilter.this;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("onFilterComplete: waiting for pending filters ");
                    stringBuilder.append(this.mNumPendingFilters);
                    carrierServicesSmsFilter.log(stringBuilder.toString());
                }
                return;
            }
        }
    }

}

