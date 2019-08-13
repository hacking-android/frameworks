/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.net.LinkProperties
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.telephony.Rlog
 *  android.telephony.SubscriptionManager
 *  android.telephony.data.DataCallResponse
 *  android.telephony.data.DataProfile
 *  android.telephony.data.DataService
 *  android.telephony.data.DataService$DataServiceProvider
 *  android.telephony.data.DataServiceCallback
 */
package com.android.internal.telephony.dataconnection;

import android.net.LinkProperties;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.data.DataCallResponse;
import android.telephony.data.DataProfile;
import android.telephony.data.DataService;
import android.telephony.data.DataServiceCallback;
import com.android.internal.telephony.CommandsInterface;
import com.android.internal.telephony.Phone;
import com.android.internal.telephony.PhoneFactory;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CellularDataService
extends DataService {
    private static final int DATA_CALL_LIST_CHANGED = 6;
    private static final boolean DBG = false;
    private static final int DEACTIVATE_DATA_ALL_COMPLETE = 2;
    private static final int REQUEST_DATA_CALL_LIST_COMPLETE = 5;
    private static final int SETUP_DATA_CALL_COMPLETE = 1;
    private static final int SET_DATA_PROFILE_COMPLETE = 4;
    private static final int SET_INITIAL_ATTACH_APN_COMPLETE = 3;
    private static final String TAG = CellularDataService.class.getSimpleName();

    private void log(String string) {
        Rlog.d((String)TAG, (String)string);
    }

    private void loge(String string) {
        Rlog.e((String)TAG, (String)string);
    }

    public DataService.DataServiceProvider onCreateDataServiceProvider(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cellular data service created for slot ");
        stringBuilder.append(n);
        this.log(stringBuilder.toString());
        if (!SubscriptionManager.isValidSlotIndex((int)n)) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("Tried to cellular data service with invalid slotId ");
            stringBuilder.append(n);
            this.loge(stringBuilder.toString());
            return null;
        }
        return new CellularDataServiceProvider(n);
    }

    private class CellularDataServiceProvider
    extends DataService.DataServiceProvider {
        private final Map<Message, DataServiceCallback> mCallbackMap;
        private final Handler mHandler;
        private final HandlerThread mHandlerThread;
        private final Looper mLooper;
        private final Phone mPhone;

        private CellularDataServiceProvider(int n) {
            super((DataService)CellularDataService.this, n);
            this.mCallbackMap = new HashMap<Message, DataServiceCallback>();
            this.mPhone = PhoneFactory.getPhone(this.getSlotIndex());
            this.mHandlerThread = new HandlerThread(CellularDataService.class.getSimpleName());
            this.mHandlerThread.start();
            this.mLooper = this.mHandlerThread.getLooper();
            this.mHandler = new Handler(this.mLooper){

                public void handleMessage(Message object) {
                    Object object2 = (DataServiceCallback)CellularDataServiceProvider.this.mCallbackMap.remove(object);
                    Object object3 = (AsyncResult)object.obj;
                    int n = object.what;
                    int n2 = 4;
                    switch (n) {
                        default: {
                            object3 = CellularDataService.this;
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Unexpected event: ");
                            ((StringBuilder)object2).append(object.what);
                            ((CellularDataService)((Object)object3)).loge(((StringBuilder)object2).toString());
                            return;
                        }
                        case 6: {
                            CellularDataServiceProvider.this.notifyDataCallListChanged((List)object3.result);
                            break;
                        }
                        case 5: {
                            if (object3.exception == null) {
                                n2 = 0;
                            }
                            object = object3.exception != null ? null : (List)object3.result;
                            object2.onRequestDataCallListComplete(n2, (List)object);
                            break;
                        }
                        case 4: {
                            if (object3.exception == null) {
                                n2 = 0;
                            }
                            object2.onSetDataProfileComplete(n2);
                            break;
                        }
                        case 3: {
                            if (object3.exception == null) {
                                n2 = 0;
                            }
                            object2.onSetInitialAttachApnComplete(n2);
                            break;
                        }
                        case 2: {
                            if (object3.exception == null) {
                                n2 = 0;
                            }
                            object2.onDeactivateDataCallComplete(n2);
                            break;
                        }
                        case 1: {
                            object = (DataCallResponse)object3.result;
                            if (object3.exception == null) {
                                n2 = 0;
                            }
                            object2.onSetupDataCallComplete(n2, (DataCallResponse)object);
                        }
                    }
                }
            };
            this.mPhone.mCi.registerForDataCallListChanged(this.mHandler, 6, null);
        }

        public void close() {
            this.mPhone.mCi.unregisterForDataCallListChanged(this.mHandler);
            this.mHandlerThread.quit();
        }

        public void deactivateDataCall(int n, int n2, DataServiceCallback dataServiceCallback) {
            Message message = null;
            if (dataServiceCallback != null) {
                message = Message.obtain((Handler)this.mHandler, (int)2);
                this.mCallbackMap.put(message, dataServiceCallback);
            }
            this.mPhone.mCi.deactivateDataCall(n, n2, message);
        }

        public void requestDataCallList(DataServiceCallback dataServiceCallback) {
            Message message = null;
            if (dataServiceCallback != null) {
                message = Message.obtain((Handler)this.mHandler, (int)5);
                this.mCallbackMap.put(message, dataServiceCallback);
            }
            this.mPhone.mCi.getDataCallList(message);
        }

        public void setDataProfile(List<DataProfile> list, boolean bl, DataServiceCallback dataServiceCallback) {
            Message message = null;
            if (dataServiceCallback != null) {
                message = Message.obtain((Handler)this.mHandler, (int)4);
                this.mCallbackMap.put(message, dataServiceCallback);
            }
            this.mPhone.mCi.setDataProfile(list.toArray((T[])new DataProfile[list.size()]), bl, message);
        }

        public void setInitialAttachApn(DataProfile dataProfile, boolean bl, DataServiceCallback dataServiceCallback) {
            Message message = null;
            if (dataServiceCallback != null) {
                message = Message.obtain((Handler)this.mHandler, (int)3);
                this.mCallbackMap.put(message, dataServiceCallback);
            }
            this.mPhone.mCi.setInitialAttachApn(dataProfile, bl, message);
        }

        public void setupDataCall(int n, DataProfile dataProfile, boolean bl, boolean bl2, int n2, LinkProperties linkProperties, DataServiceCallback dataServiceCallback) {
            Message message = null;
            if (dataServiceCallback != null) {
                message = Message.obtain((Handler)this.mHandler, (int)1);
                this.mCallbackMap.put(message, dataServiceCallback);
            }
            this.mPhone.mCi.setupDataCall(n, dataProfile, bl, bl2, n2, linkProperties, message);
        }

    }

}

