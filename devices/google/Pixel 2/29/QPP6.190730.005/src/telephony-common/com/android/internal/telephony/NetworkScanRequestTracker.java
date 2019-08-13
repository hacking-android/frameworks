/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.AsyncResult
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Message
 *  android.os.Messenger
 *  android.os.Parcelable
 *  android.os.RemoteException
 *  android.telephony.CellIdentity
 *  android.telephony.CellInfo
 *  android.telephony.LocationAccessPolicy
 *  android.telephony.LocationAccessPolicy$LocationPermissionQuery
 *  android.telephony.LocationAccessPolicy$LocationPermissionQuery$Builder
 *  android.telephony.LocationAccessPolicy$LocationPermissionResult
 *  android.telephony.NetworkScanRequest
 *  android.telephony.RadioAccessSpecifier
 *  android.telephony.SubscriptionInfo
 *  android.util.Log
 *  com.android.internal.telephony.-$
 *  com.android.internal.telephony.-$$Lambda
 *  com.android.internal.telephony.-$$Lambda$NetworkScanRequestTracker
 *  com.android.internal.telephony.-$$Lambda$NetworkScanRequestTracker$ElkGiXq_pSMxogeu8FScyf5E2jg
 *  com.android.internal.telephony.-$$Lambda$OXXtpNvVeJw7E7y9hLioSYgFy9A
 *  com.android.internal.telephony.-$$Lambda$seyL25CSW2NInOydsTbSDrNW6pM
 *  com.android.internal.telephony.NetworkScanResult
 *  com.android.internal.util.FunctionalUtils
 *  com.android.internal.util.FunctionalUtils$ThrowingSupplier
 */
package com.android.internal.telephony;

import android.content.Context;
import android.os.AsyncResult;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.RemoteException;
import android.telephony.CellIdentity;
import android.telephony.CellInfo;
import android.telephony.LocationAccessPolicy;
import android.telephony.NetworkScanRequest;
import android.telephony.RadioAccessSpecifier;
import android.telephony.SubscriptionInfo;
import android.util.Log;
import com.android.internal.telephony.-$;
import com.android.internal.telephony.CommandException;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.NetworkScanResult;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.ServiceStateTracker;
import com.android.internal.telephony.SubscriptionController;
import com.android.internal.telephony._$$Lambda$NetworkScanRequestTracker$3p0zlHLjJ9t4MD0JtdOXxkna_rc;
import com.android.internal.telephony._$$Lambda$NetworkScanRequestTracker$ElkGiXq_pSMxogeu8FScyf5E2jg;
import com.android.internal.telephony._$$Lambda$NetworkScanRequestTracker$kZrc_pK3C_d6BRM_xQpUxJvEcU4;
import com.android.internal.telephony._$$Lambda$OXXtpNvVeJw7E7y9hLioSYgFy9A;
import com.android.internal.telephony._$$Lambda$seyL25CSW2NInOydsTbSDrNW6pM;
import com.android.internal.util.FunctionalUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class NetworkScanRequestTracker {
    private static final int CMD_INTERRUPT_NETWORK_SCAN = 6;
    private static final int CMD_START_NETWORK_SCAN = 1;
    private static final int CMD_STOP_NETWORK_SCAN = 4;
    private static final int EVENT_INTERRUPT_NETWORK_SCAN_DONE = 7;
    private static final int EVENT_RECEIVE_NETWORK_SCAN_RESULT = 3;
    private static final int EVENT_START_NETWORK_SCAN_DONE = 2;
    private static final int EVENT_STOP_NETWORK_SCAN_DONE = 5;
    private static final String TAG = "ScanRequestTracker";
    private final Handler mHandler = new Handler(){

        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    break;
                }
                case 7: {
                    NetworkScanRequestTracker.this.mScheduler.interruptScanDone((AsyncResult)message.obj);
                    break;
                }
                case 6: {
                    NetworkScanRequestTracker.this.mScheduler.doInterruptScan(message.arg1);
                    break;
                }
                case 5: {
                    NetworkScanRequestTracker.this.mScheduler.stopScanDone((AsyncResult)message.obj);
                    break;
                }
                case 4: {
                    NetworkScanRequestTracker.this.mScheduler.doStopScan(message.arg1);
                    break;
                }
                case 3: {
                    NetworkScanRequestTracker.this.mScheduler.receiveResult((AsyncResult)message.obj);
                    break;
                }
                case 2: {
                    NetworkScanRequestTracker.this.mScheduler.startScanDone((AsyncResult)message.obj);
                    break;
                }
                case 1: {
                    NetworkScanRequestTracker.this.mScheduler.doStartScan((NetworkScanRequestInfo)message.obj);
                }
            }
        }
    };
    private final AtomicInteger mNextNetworkScanRequestId = new AtomicInteger(1);
    private final NetworkScanRequestScheduler mScheduler = new NetworkScanRequestScheduler();

    private static boolean doesCellInfoCorrespondToKnownMccMnc(CellInfo cellInfo, Collection<String> collection) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(cellInfo.getCellIdentity().getMccString());
        stringBuilder.append(cellInfo.getCellIdentity().getMncString());
        return collection.contains(stringBuilder.toString());
    }

    private static Stream<String> getAllowableMccMncsFromSubscriptionInfo(SubscriptionInfo subscriptionInfo) {
        Stream<String> stream = Stream.of(subscriptionInfo.getEhplmns(), subscriptionInfo.getHplmns()).flatMap(_$$Lambda$seyL25CSW2NInOydsTbSDrNW6pM.INSTANCE);
        Stream<String> stream2 = stream;
        if (subscriptionInfo.getMccString() != null) {
            stream2 = stream;
            if (subscriptionInfo.getMncString() != null) {
                stream2 = new StringBuilder();
                ((StringBuilder)((Object)stream2)).append(subscriptionInfo.getMccString());
                ((StringBuilder)((Object)stream2)).append(subscriptionInfo.getMncString());
                stream2 = Stream.concat(stream, Stream.of(((StringBuilder)((Object)stream2)).toString()));
            }
        }
        return stream2;
    }

    public static Set<String> getAllowedMccMncsForLocationRestrictedScan(Context context) {
        return (Set)Binder.withCleanCallingIdentity((FunctionalUtils.ThrowingSupplier)new _$$Lambda$NetworkScanRequestTracker$kZrc_pK3C_d6BRM_xQpUxJvEcU4(context));
    }

    private void interruptNetworkScan(int n) {
        this.mHandler.obtainMessage(6, n, 0).sendToTarget();
    }

    private boolean isValidScan(NetworkScanRequestInfo networkScanRequestInfo) {
        if (networkScanRequestInfo.mRequest != null && networkScanRequestInfo.mRequest.getSpecifiers() != null) {
            if (networkScanRequestInfo.mRequest.getSpecifiers().length > 8) {
                return false;
            }
            for (RadioAccessSpecifier radioAccessSpecifier : networkScanRequestInfo.mRequest.getSpecifiers()) {
                if (radioAccessSpecifier.getRadioAccessNetwork() != 1 && radioAccessSpecifier.getRadioAccessNetwork() != 2 && radioAccessSpecifier.getRadioAccessNetwork() != 3) {
                    return false;
                }
                if (radioAccessSpecifier.getBands() != null && radioAccessSpecifier.getBands().length > 8) {
                    return false;
                }
                if (radioAccessSpecifier.getChannels() == null || radioAccessSpecifier.getChannels().length <= 32) continue;
                return false;
            }
            if (networkScanRequestInfo.mRequest.getSearchPeriodicity() >= 5 && networkScanRequestInfo.mRequest.getSearchPeriodicity() <= 300) {
                if (networkScanRequestInfo.mRequest.getMaxSearchTime() >= 60 && networkScanRequestInfo.mRequest.getMaxSearchTime() <= 3600) {
                    if (networkScanRequestInfo.mRequest.getIncrementalResultsPeriodicity() >= 1 && networkScanRequestInfo.mRequest.getIncrementalResultsPeriodicity() <= 10) {
                        if (networkScanRequestInfo.mRequest.getSearchPeriodicity() <= networkScanRequestInfo.mRequest.getMaxSearchTime() && networkScanRequestInfo.mRequest.getIncrementalResultsPeriodicity() <= networkScanRequestInfo.mRequest.getMaxSearchTime()) {
                            return networkScanRequestInfo.mRequest.getPlmns() == null || networkScanRequestInfo.mRequest.getPlmns().size() <= 20;
                        }
                        return false;
                    }
                    return false;
                }
                return false;
            }
            return false;
        }
        return false;
    }

    public static /* synthetic */ Stream lambda$ElkGiXq_pSMxogeu8FScyf5E2jg(SubscriptionInfo subscriptionInfo) {
        return NetworkScanRequestTracker.getAllowableMccMncsFromSubscriptionInfo(subscriptionInfo);
    }

    static /* synthetic */ Set lambda$getAllowedMccMncsForLocationRestrictedScan$0(Context context) throws Exception {
        return SubscriptionController.getInstance().getAvailableSubscriptionInfoList(context.getOpPackageName()).stream().flatMap(_$$Lambda$NetworkScanRequestTracker$ElkGiXq_pSMxogeu8FScyf5E2jg.INSTANCE).collect(Collectors.toSet());
    }

    static /* synthetic */ boolean lambda$notifyMessenger$1(Set set, CellInfo cellInfo) {
        return NetworkScanRequestTracker.doesCellInfoCorrespondToKnownMccMnc(cellInfo, set);
    }

    private void logEmptyResultOrException(AsyncResult asyncResult) {
        if (asyncResult.result == null) {
            Log.e((String)TAG, (String)"NetworkScanResult: Empty result");
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NetworkScanResult: Exception: ");
            stringBuilder.append(asyncResult.exception);
            Log.e((String)TAG, (String)stringBuilder.toString());
        }
    }

    private void notifyMessenger(NetworkScanRequestInfo object, int n, int n2, List<CellInfo> arrcellInfo) {
        Messenger messenger = ((NetworkScanRequestInfo)object).mMessenger;
        Message message = Message.obtain();
        message.what = n;
        message.arg1 = n2;
        message.arg2 = ((NetworkScanRequestInfo)object).mScanId;
        if (arrcellInfo != null) {
            List<Object> list = arrcellInfo;
            if (n == 4) {
                object = NetworkScanRequestTracker.getAllowedMccMncsForLocationRestrictedScan(((NetworkScanRequestInfo)object).mPhone.getContext());
                list = arrcellInfo.stream().map(_$$Lambda$OXXtpNvVeJw7E7y9hLioSYgFy9A.INSTANCE).filter(new _$$Lambda$NetworkScanRequestTracker$3p0zlHLjJ9t4MD0JtdOXxkna_rc((Set)object)).collect(Collectors.toList());
            }
            arrcellInfo = list.toArray((T[])new CellInfo[list.size()]);
            object = new Bundle();
            object.putParcelableArray("scanResult", (Parcelable[])arrcellInfo);
            message.setData((Bundle)object);
        } else {
            message.obj = null;
        }
        try {
            messenger.send(message);
        }
        catch (RemoteException remoteException) {
            arrcellInfo = new StringBuilder();
            arrcellInfo.append("Exception in notifyMessenger: ");
            arrcellInfo.append((Object)remoteException);
            Log.e((String)TAG, (String)arrcellInfo.toString());
        }
    }

    public int startNetworkScan(NetworkScanRequest object, Messenger messenger, IBinder iBinder, Phone phone, int n, int n2, String string) {
        int n3 = this.mNextNetworkScanRequestId.getAndIncrement();
        object = new NetworkScanRequestInfo((NetworkScanRequest)object, messenger, iBinder, n3, phone, n, n2, string);
        this.mHandler.obtainMessage(1, object).sendToTarget();
        return n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void stopNetworkScan(int n, int n2) {
        NetworkScanRequestScheduler networkScanRequestScheduler = this.mScheduler;
        synchronized (networkScanRequestScheduler) {
            if (this.mScheduler.mLiveRequestInfo != null && n == this.mScheduler.mLiveRequestInfo.mScanId && n2 == this.mScheduler.mLiveRequestInfo.mUid || this.mScheduler.mPendingRequestInfo != null && n == this.mScheduler.mPendingRequestInfo.mScanId && n2 == this.mScheduler.mPendingRequestInfo.mUid) {
                this.mHandler.obtainMessage(4, n, 0).sendToTarget();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Scan with id: ");
            stringBuilder.append(n);
            stringBuilder.append(" does not exist!");
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
    }

    class NetworkScanRequestInfo
    implements IBinder.DeathRecipient {
        private final IBinder mBinder;
        private final String mCallingPackage;
        private boolean mIsBinderDead;
        private final Messenger mMessenger;
        private final Phone mPhone;
        private final int mPid;
        private final NetworkScanRequest mRequest;
        private final int mScanId;
        private final int mUid;

        NetworkScanRequestInfo(NetworkScanRequest networkScanRequest, Messenger messenger, IBinder iBinder, int n, Phone phone, int n2, int n3, String string) {
            this.mRequest = networkScanRequest;
            this.mMessenger = messenger;
            this.mBinder = iBinder;
            this.mScanId = n;
            this.mPhone = phone;
            this.mUid = n2;
            this.mPid = n3;
            this.mCallingPackage = string;
            this.mIsBinderDead = false;
            try {
                this.mBinder.linkToDeath((IBinder.DeathRecipient)this, 0);
            }
            catch (RemoteException remoteException) {
                this.binderDied();
            }
        }

        public void binderDied() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PhoneInterfaceManager NetworkScanRequestInfo binderDied(");
            stringBuilder.append((Object)this.mRequest);
            stringBuilder.append(", ");
            stringBuilder.append((Object)this.mBinder);
            stringBuilder.append(")");
            Log.e((String)NetworkScanRequestTracker.TAG, (String)stringBuilder.toString());
            this.setIsBinderDead(true);
            NetworkScanRequestTracker.this.interruptNetworkScan(this.mScanId);
        }

        boolean getIsBinderDead() {
            synchronized (this) {
                boolean bl = this.mIsBinderDead;
                return bl;
            }
        }

        NetworkScanRequest getRequest() {
            return this.mRequest;
        }

        void setIsBinderDead(boolean bl) {
            synchronized (this) {
                this.mIsBinderDead = bl;
                return;
            }
        }

        void unlinkDeathRecipient() {
            IBinder iBinder = this.mBinder;
            if (iBinder != null) {
                iBinder.unlinkToDeath((IBinder.DeathRecipient)this, 0);
            }
        }
    }

    private class NetworkScanRequestScheduler {
        private NetworkScanRequestInfo mLiveRequestInfo;
        private NetworkScanRequestInfo mPendingRequestInfo;

        private NetworkScanRequestScheduler() {
        }

        private boolean cacheScan(NetworkScanRequestInfo networkScanRequestInfo) {
            return false;
        }

        private int commandExceptionErrorToScanError(CommandException.Error error) {
            switch (error) {
                default: {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("commandExceptionErrorToScanError: Unexpected CommandExceptionError ");
                    stringBuilder.append((Object)error);
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)stringBuilder.toString());
                    return 10000;
                }
                case DEVICE_IN_USE: {
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"commandExceptionErrorToScanError: DEVICE_IN_USE");
                    return 3;
                }
                case INVALID_ARGUMENTS: {
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"commandExceptionErrorToScanError: INVALID_ARGUMENTS");
                    return 2;
                }
                case OPERATION_NOT_ALLOWED: {
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"commandExceptionErrorToScanError: OPERATION_NOT_ALLOWED");
                    return 1;
                }
                case MODEM_ERR: {
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"commandExceptionErrorToScanError: MODEM_ERR");
                    return 1;
                }
                case INTERNAL_ERR: {
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"commandExceptionErrorToScanError: INTERNAL_ERR");
                    return 1;
                }
                case NO_MEMORY: {
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"commandExceptionErrorToScanError: NO_MEMORY");
                    return 1;
                }
                case REQUEST_NOT_SUPPORTED: {
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"commandExceptionErrorToScanError: REQUEST_NOT_SUPPORTED");
                    return 4;
                }
                case RADIO_NOT_AVAILABLE: 
            }
            Log.e((String)NetworkScanRequestTracker.TAG, (String)"commandExceptionErrorToScanError: RADIO_NOT_AVAILABLE");
            return 1;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void deleteScanAndMayNotify(NetworkScanRequestInfo networkScanRequestInfo, int n, boolean bl) {
            synchronized (this) {
                if (this.mLiveRequestInfo != null && networkScanRequestInfo.mScanId == this.mLiveRequestInfo.mScanId) {
                    void var3_3;
                    if (var3_3 != false) {
                        void var2_2;
                        if (var2_2 == false) {
                            NetworkScanRequestTracker.this.notifyMessenger(networkScanRequestInfo, 3, (int)var2_2, null);
                        } else {
                            NetworkScanRequestTracker.this.notifyMessenger(networkScanRequestInfo, 2, (int)var2_2, null);
                        }
                    }
                    this.mLiveRequestInfo = null;
                    if (this.mPendingRequestInfo != null) {
                        this.startNewScan(this.mPendingRequestInfo);
                        this.mPendingRequestInfo = null;
                    }
                }
                return;
            }
        }

        private void doInterruptScan(int n) {
            synchronized (this) {
                if (this.mLiveRequestInfo != null && n == this.mLiveRequestInfo.mScanId) {
                    this.mLiveRequestInfo.mPhone.stopNetworkScan(NetworkScanRequestTracker.this.mHandler.obtainMessage(7, (Object)this.mLiveRequestInfo));
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("doInterruptScan: scan ");
                    stringBuilder.append(n);
                    stringBuilder.append(" does not exist!");
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)stringBuilder.toString());
                }
                return;
            }
        }

        private void doStartScan(NetworkScanRequestInfo networkScanRequestInfo) {
            if (networkScanRequestInfo == null) {
                Log.e((String)NetworkScanRequestTracker.TAG, (String)"CMD_START_NETWORK_SCAN: nsri is null");
                return;
            }
            if (!NetworkScanRequestTracker.this.isValidScan(networkScanRequestInfo)) {
                NetworkScanRequestTracker.this.notifyMessenger(networkScanRequestInfo, 2, 2, null);
                return;
            }
            if (networkScanRequestInfo.getIsBinderDead()) {
                Log.e((String)NetworkScanRequestTracker.TAG, (String)"CMD_START_NETWORK_SCAN: Binder has died");
                return;
            }
            if (!(this.startNewScan(networkScanRequestInfo) || this.interruptLiveScan(networkScanRequestInfo) || this.cacheScan(networkScanRequestInfo))) {
                NetworkScanRequestTracker.this.notifyMessenger(networkScanRequestInfo, 2, 3, null);
            }
        }

        private void doStopScan(int n) {
            synchronized (this) {
                if (this.mLiveRequestInfo != null && n == this.mLiveRequestInfo.mScanId) {
                    this.mLiveRequestInfo.mPhone.stopNetworkScan(NetworkScanRequestTracker.this.mHandler.obtainMessage(5, (Object)this.mLiveRequestInfo));
                } else if (this.mPendingRequestInfo != null && n == this.mPendingRequestInfo.mScanId) {
                    NetworkScanRequestTracker.this.notifyMessenger(this.mPendingRequestInfo, 3, 0, null);
                    this.mPendingRequestInfo = null;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("stopScan: scan ");
                    stringBuilder.append(n);
                    stringBuilder.append(" does not exist!");
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)stringBuilder.toString());
                }
                return;
            }
        }

        private boolean interruptLiveScan(NetworkScanRequestInfo networkScanRequestInfo) {
            synchronized (this) {
                if (this.mLiveRequestInfo != null && this.mPendingRequestInfo == null && networkScanRequestInfo.mUid == 1001 && this.mLiveRequestInfo.mUid != 1001) {
                    this.doInterruptScan(this.mLiveRequestInfo.mScanId);
                    this.mPendingRequestInfo = networkScanRequestInfo;
                    NetworkScanRequestTracker.this.notifyMessenger(this.mLiveRequestInfo, 2, 10002, null);
                    return true;
                }
                return false;
            }
        }

        private void interruptScanDone(AsyncResult object) {
            object = (NetworkScanRequestInfo)object.userObj;
            if (object == null) {
                Log.e((String)NetworkScanRequestTracker.TAG, (String)"EVENT_INTERRUPT_NETWORK_SCAN_DONE: nsri is null");
                return;
            }
            NetworkScanRequestInfo.access$1200((NetworkScanRequestInfo)object).mCi.unregisterForNetworkScanResult(NetworkScanRequestTracker.this.mHandler);
            this.deleteScanAndMayNotify((NetworkScanRequestInfo)object, 0, false);
        }

        private void receiveResult(AsyncResult asyncResult) {
            NetworkScanRequestInfo networkScanRequestInfo = (NetworkScanRequestInfo)asyncResult.userObj;
            if (networkScanRequestInfo == null) {
                Log.e((String)NetworkScanRequestTracker.TAG, (String)"EVENT_RECEIVE_NETWORK_SCAN_RESULT: nsri is null");
                return;
            }
            LocationAccessPolicy.LocationPermissionQuery locationPermissionQuery = new LocationAccessPolicy.LocationPermissionQuery.Builder().setCallingPackage(networkScanRequestInfo.mCallingPackage).setCallingPid(networkScanRequestInfo.mPid).setCallingUid(networkScanRequestInfo.mUid).setMinSdkVersionForFine(29).setMethod("NetworkScanTracker#onResult").build();
            if (asyncResult.exception == null && asyncResult.result != null) {
                asyncResult = (NetworkScanResult)asyncResult.result;
                int n = LocationAccessPolicy.checkLocationPermission((Context)networkScanRequestInfo.mPhone.getContext(), (LocationAccessPolicy.LocationPermissionQuery)locationPermissionQuery) == LocationAccessPolicy.LocationPermissionResult.ALLOWED ? 1 : 0;
                n = n != 0 ? 1 : 4;
                if (asyncResult.scanError == 0) {
                    if (networkScanRequestInfo.mPhone.getServiceStateTracker() != null) {
                        networkScanRequestInfo.mPhone.getServiceStateTracker().updateOperatorNameForCellInfo(asyncResult.networkInfos);
                    }
                    NetworkScanRequestTracker.this.notifyMessenger(networkScanRequestInfo, n, this.rilErrorToScanError(asyncResult.scanError), asyncResult.networkInfos);
                    if (asyncResult.scanStatus == 2) {
                        this.deleteScanAndMayNotify(networkScanRequestInfo, 0, true);
                        NetworkScanRequestInfo.access$1200((NetworkScanRequestInfo)networkScanRequestInfo).mCi.unregisterForNetworkScanResult(NetworkScanRequestTracker.this.mHandler);
                    }
                } else {
                    if (asyncResult.networkInfos != null) {
                        NetworkScanRequestTracker.this.notifyMessenger(networkScanRequestInfo, n, this.rilErrorToScanError(asyncResult.scanError), asyncResult.networkInfos);
                    }
                    this.deleteScanAndMayNotify(networkScanRequestInfo, this.rilErrorToScanError(asyncResult.scanError), true);
                    NetworkScanRequestInfo.access$1200((NetworkScanRequestInfo)networkScanRequestInfo).mCi.unregisterForNetworkScanResult(NetworkScanRequestTracker.this.mHandler);
                }
            } else {
                NetworkScanRequestTracker.this.logEmptyResultOrException(asyncResult);
                this.deleteScanAndMayNotify(networkScanRequestInfo, 10000, true);
                NetworkScanRequestInfo.access$1200((NetworkScanRequestInfo)networkScanRequestInfo).mCi.unregisterForNetworkScanResult(NetworkScanRequestTracker.this.mHandler);
            }
        }

        private int rilErrorToScanError(int n) {
            if (n != 0) {
                if (n != 1) {
                    if (n != 6) {
                        if (n != 40) {
                            if (n != 44) {
                                if (n != 54) {
                                    if (n != 64) {
                                        if (n != 37) {
                                            if (n != 38) {
                                                StringBuilder stringBuilder = new StringBuilder();
                                                stringBuilder.append("rilErrorToScanError: Unexpected RadioError ");
                                                stringBuilder.append(n);
                                                Log.e((String)NetworkScanRequestTracker.TAG, (String)stringBuilder.toString());
                                                return 10000;
                                            }
                                            Log.e((String)NetworkScanRequestTracker.TAG, (String)"rilErrorToScanError: INTERNAL_ERR");
                                            return 1;
                                        }
                                        Log.e((String)NetworkScanRequestTracker.TAG, (String)"rilErrorToScanError: NO_MEMORY");
                                        return 1;
                                    }
                                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"rilErrorToScanError: DEVICE_IN_USE");
                                    return 3;
                                }
                                Log.e((String)NetworkScanRequestTracker.TAG, (String)"rilErrorToScanError: OPERATION_NOT_ALLOWED");
                                return 1;
                            }
                            Log.e((String)NetworkScanRequestTracker.TAG, (String)"rilErrorToScanError: INVALID_ARGUMENTS");
                            return 2;
                        }
                        Log.e((String)NetworkScanRequestTracker.TAG, (String)"rilErrorToScanError: MODEM_ERR");
                        return 1;
                    }
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"rilErrorToScanError: REQUEST_NOT_SUPPORTED");
                    return 4;
                }
                Log.e((String)NetworkScanRequestTracker.TAG, (String)"rilErrorToScanError: RADIO_NOT_AVAILABLE");
                return 1;
            }
            return 0;
        }

        private boolean startNewScan(NetworkScanRequestInfo networkScanRequestInfo) {
            synchronized (this) {
                if (this.mLiveRequestInfo == null) {
                    this.mLiveRequestInfo = networkScanRequestInfo;
                    networkScanRequestInfo.mPhone.startNetworkScan(networkScanRequestInfo.getRequest(), NetworkScanRequestTracker.this.mHandler.obtainMessage(2, (Object)networkScanRequestInfo));
                    return true;
                }
                return false;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void startScanDone(AsyncResult asyncResult) {
            synchronized (this) {
                NetworkScanRequestInfo networkScanRequestInfo = (NetworkScanRequestInfo)asyncResult.userObj;
                if (networkScanRequestInfo == null) {
                    Log.e((String)NetworkScanRequestTracker.TAG, (String)"EVENT_START_NETWORK_SCAN_DONE: nsri is null");
                    return;
                }
                if (this.mLiveRequestInfo != null && networkScanRequestInfo.mScanId == this.mLiveRequestInfo.mScanId) {
                    if (asyncResult.exception == null && asyncResult.result != null) {
                        NetworkScanRequestInfo.access$1200((NetworkScanRequestInfo)networkScanRequestInfo).mCi.registerForNetworkScanResult(NetworkScanRequestTracker.this.mHandler, 3, networkScanRequestInfo);
                    } else {
                        NetworkScanRequestTracker.this.logEmptyResultOrException(asyncResult);
                        if (asyncResult.exception != null) {
                            this.deleteScanAndMayNotify(networkScanRequestInfo, this.commandExceptionErrorToScanError(((CommandException)asyncResult.exception).getCommandError()), true);
                        } else {
                            Log.wtf((String)NetworkScanRequestTracker.TAG, (String)"EVENT_START_NETWORK_SCAN_DONE: ar.exception can not be null!");
                        }
                    }
                    return;
                }
                Log.e((String)NetworkScanRequestTracker.TAG, (String)"EVENT_START_NETWORK_SCAN_DONE: nsri does not match mLiveRequestInfo");
                return;
            }
        }

        private void stopScanDone(AsyncResult asyncResult) {
            NetworkScanRequestInfo networkScanRequestInfo = (NetworkScanRequestInfo)asyncResult.userObj;
            if (networkScanRequestInfo == null) {
                Log.e((String)NetworkScanRequestTracker.TAG, (String)"EVENT_STOP_NETWORK_SCAN_DONE: nsri is null");
                return;
            }
            if (asyncResult.exception == null && asyncResult.result != null) {
                this.deleteScanAndMayNotify(networkScanRequestInfo, 0, true);
            } else {
                NetworkScanRequestTracker.this.logEmptyResultOrException(asyncResult);
                if (asyncResult.exception != null) {
                    this.deleteScanAndMayNotify(networkScanRequestInfo, this.commandExceptionErrorToScanError(((CommandException)asyncResult.exception).getCommandError()), true);
                } else {
                    Log.wtf((String)NetworkScanRequestTracker.TAG, (String)"EVENT_STOP_NETWORK_SCAN_DONE: ar.exception can not be null!");
                }
            }
            NetworkScanRequestInfo.access$1200((NetworkScanRequestInfo)networkScanRequestInfo).mCi.unregisterForNetworkScanResult(NetworkScanRequestTracker.this.mHandler);
        }
    }

}

