/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.annotation.UnsupportedAppUsage
 *  android.os.AsyncResult
 *  android.os.Handler
 *  android.os.Message
 *  android.util.SparseArray
 */
package com.android.internal.telephony.uicc;

import android.annotation.UnsupportedAppUsage;
import android.os.AsyncResult;
import android.os.Handler;
import android.os.Message;
import android.util.SparseArray;
import com.android.internal.telephony.gsm.UsimPhoneBookManager;
import com.android.internal.telephony.uicc.AdnRecord;
import com.android.internal.telephony.uicc.AdnRecordLoader;
import com.android.internal.telephony.uicc.IccConstants;
import com.android.internal.telephony.uicc.IccFileHandler;
import java.util.ArrayList;
import java.util.Iterator;

public class AdnRecordCache
extends Handler
implements IccConstants {
    static final int EVENT_LOAD_ALL_ADN_LIKE_DONE = 1;
    static final int EVENT_UPDATE_ADN_DONE = 2;
    SparseArray<ArrayList<AdnRecord>> mAdnLikeFiles = new SparseArray();
    @UnsupportedAppUsage
    SparseArray<ArrayList<Message>> mAdnLikeWaiters = new SparseArray();
    @UnsupportedAppUsage
    private IccFileHandler mFh;
    @UnsupportedAppUsage
    SparseArray<Message> mUserWriteResponse = new SparseArray();
    @UnsupportedAppUsage
    private UsimPhoneBookManager mUsimPhoneBookManager;

    AdnRecordCache(IccFileHandler iccFileHandler) {
        this.mFh = iccFileHandler;
        this.mUsimPhoneBookManager = new UsimPhoneBookManager(this.mFh, this);
    }

    private void clearUserWriters() {
        int n = this.mUserWriteResponse.size();
        for (int i = 0; i < n; ++i) {
            this.sendErrorResponse((Message)this.mUserWriteResponse.valueAt(i), "AdnCace reset");
        }
        this.mUserWriteResponse.clear();
    }

    private void clearWaiters() {
        int n = this.mAdnLikeWaiters.size();
        for (int i = 0; i < n; ++i) {
            this.notifyWaiters((ArrayList)this.mAdnLikeWaiters.valueAt(i), new AsyncResult(null, null, (Throwable)new RuntimeException("AdnCache reset")));
        }
        this.mAdnLikeWaiters.clear();
    }

    private void notifyWaiters(ArrayList<Message> arrayList, AsyncResult asyncResult) {
        if (arrayList == null) {
            return;
        }
        int n = arrayList.size();
        for (int i = 0; i < n; ++i) {
            Message message = arrayList.get(i);
            AsyncResult.forMessage((Message)message, (Object)asyncResult.result, (Throwable)asyncResult.exception);
            message.sendToTarget();
        }
    }

    @UnsupportedAppUsage
    private void sendErrorResponse(Message message, String object) {
        if (message != null) {
            AsyncResult.forMessage((Message)message).exception = object = new RuntimeException((String)object);
            message.sendToTarget();
        }
    }

    @UnsupportedAppUsage
    public int extensionEfForEf(int n) {
        if (n != 20272) {
            if (n != 28480) {
                if (n != 28489) {
                    if (n != 28615) {
                        if (n != 28474) {
                            if (n != 28475) {
                                return -1;
                            }
                            return 28491;
                        }
                        return 28490;
                    }
                    return 28616;
                }
                return 28492;
            }
            return 28490;
        }
        return 0;
    }

    @UnsupportedAppUsage
    public ArrayList<AdnRecord> getRecordsIfLoaded(int n) {
        return (ArrayList)this.mAdnLikeFiles.get(n);
    }

    public void handleMessage(Message object) {
        int n = object.what;
        if (n != 1) {
            if (n == 2) {
                AsyncResult asyncResult = (AsyncResult)object.obj;
                int n2 = object.arg1;
                n = object.arg2;
                object = (AdnRecord)asyncResult.userObj;
                if (asyncResult.exception == null) {
                    ((ArrayList)this.mAdnLikeFiles.get(n2)).set(n - 1, object);
                    this.mUsimPhoneBookManager.invalidateCache();
                }
                object = (Message)this.mUserWriteResponse.get(n2);
                this.mUserWriteResponse.delete(n2);
                if (object != null) {
                    AsyncResult.forMessage((Message)object, null, (Throwable)asyncResult.exception);
                    object.sendToTarget();
                }
            }
        } else {
            AsyncResult asyncResult = (AsyncResult)object.obj;
            n = object.arg1;
            object = (ArrayList)this.mAdnLikeWaiters.get(n);
            this.mAdnLikeWaiters.delete(n);
            if (asyncResult.exception == null) {
                this.mAdnLikeFiles.put(n, (Object)((ArrayList)asyncResult.result));
            }
            this.notifyWaiters((ArrayList<Message>)object, asyncResult);
        }
    }

    public void requestLoadAllAdnLike(int n, int n2, Message message) {
        Object object = n == 20272 ? this.mUsimPhoneBookManager.loadEfFilesFromUsim() : this.getRecordsIfLoaded(n);
        if (object != null) {
            if (message != null) {
                AsyncResult.forMessage((Message)message).result = object;
                message.sendToTarget();
            }
            return;
        }
        object = (AsyncResult)this.mAdnLikeWaiters.get(n);
        if (object != null) {
            object.add(message);
            return;
        }
        object = new ArrayList();
        object.add(message);
        this.mAdnLikeWaiters.put(n, object);
        if (n2 < 0) {
            if (message != null) {
                object = AsyncResult.forMessage((Message)message);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("EF is not known ADN-like EF:0x");
                stringBuilder.append(Integer.toHexString(n).toUpperCase());
                object.exception = new RuntimeException(stringBuilder.toString());
                message.sendToTarget();
            }
            return;
        }
        new AdnRecordLoader(this.mFh).loadAllFromEF(n, n2, this.obtainMessage(1, n, 0));
    }

    @UnsupportedAppUsage
    public void reset() {
        this.mAdnLikeFiles.clear();
        this.mUsimPhoneBookManager.reset();
        this.clearWaiters();
        this.clearUserWriters();
    }

    @UnsupportedAppUsage
    public void updateAdnByIndex(int n, AdnRecord object, int n2, String string, Message message) {
        int n3 = this.extensionEfForEf(n);
        if (n3 < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("EF is not known ADN-like EF:0x");
            ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
            this.sendErrorResponse(message, ((StringBuilder)object).toString());
            return;
        }
        if ((Message)this.mUserWriteResponse.get(n) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Have pending update for EF:0x");
            ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
            this.sendErrorResponse(message, ((StringBuilder)object).toString());
            return;
        }
        this.mUserWriteResponse.put(n, (Object)message);
        new AdnRecordLoader(this.mFh).updateEF((AdnRecord)object, n, n3, n2, string, this.obtainMessage(2, n, n2, object));
    }

    public void updateAdnBySearch(int n, AdnRecord object, AdnRecord object2, String string, Message message) {
        int n2 = n;
        int n3 = this.extensionEfForEf(n);
        if (n3 < 0) {
            object = new StringBuilder();
            ((StringBuilder)object).append("EF is not known ADN-like EF:0x");
            ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
            this.sendErrorResponse(message, ((StringBuilder)object).toString());
            return;
        }
        ArrayList<AdnRecord> arrayList = n2 == 20272 ? this.mUsimPhoneBookManager.loadEfFilesFromUsim() : this.getRecordsIfLoaded(n);
        if (arrayList == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Adn list not exist for EF:0x");
            ((StringBuilder)object).append(Integer.toHexString(n).toUpperCase());
            this.sendErrorResponse(message, ((StringBuilder)object).toString());
            return;
        }
        int n4 = -1;
        Iterator<AdnRecord> iterator = arrayList.iterator();
        int n5 = 1;
        do {
            n = n4;
            if (!iterator.hasNext()) break;
            if (((AdnRecord)object).isEqual(iterator.next())) {
                n = n5;
                break;
            }
            ++n5;
        } while (true);
        if (n == -1) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Adn record don't exist for ");
            ((StringBuilder)object2).append(object);
            this.sendErrorResponse(message, ((StringBuilder)object2).toString());
            return;
        }
        if (n2 == 20272) {
            object = arrayList.get(n - 1);
            n2 = ((AdnRecord)object).mEfid;
            n5 = ((AdnRecord)object).mExtRecord;
            n = ((AdnRecord)object).mRecordNumber;
            ((AdnRecord)object2).mEfid = n2;
            ((AdnRecord)object2).mExtRecord = n5;
            ((AdnRecord)object2).mRecordNumber = n;
        } else {
            n5 = n3;
        }
        if ((Message)this.mUserWriteResponse.get(n2) != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Have pending update for EF:0x");
            ((StringBuilder)object).append(Integer.toHexString(n2).toUpperCase());
            this.sendErrorResponse(message, ((StringBuilder)object).toString());
            return;
        }
        this.mUserWriteResponse.put(n2, (Object)message);
        new AdnRecordLoader(this.mFh).updateEF((AdnRecord)object2, n2, n5, n, string, this.obtainMessage(2, n2, n, object2));
    }
}

