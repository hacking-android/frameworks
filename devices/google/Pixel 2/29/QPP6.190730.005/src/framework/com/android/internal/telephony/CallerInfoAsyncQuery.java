/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony;

import android.app.ActivityManager;
import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.ContactsContract;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.text.TextUtils;
import com.android.internal.telephony.CallerInfo;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CallerInfoAsyncQuery {
    private static final boolean DBG = false;
    private static final boolean ENABLE_UNKNOWN_NUMBER_GEO_DESCRIPTION = true;
    private static final int EVENT_ADD_LISTENER = 2;
    private static final int EVENT_EMERGENCY_NUMBER = 4;
    private static final int EVENT_END_OF_QUEUE = 3;
    private static final int EVENT_GET_GEO_DESCRIPTION = 6;
    private static final int EVENT_NEW_QUERY = 1;
    private static final int EVENT_VOICEMAIL_NUMBER = 5;
    private static final String LOG_TAG = "CallerInfoAsyncQuery";
    private CallerInfoAsyncQueryHandler mHandler;

    private CallerInfoAsyncQuery() {
    }

    private void allocate(Context context, Uri uri) {
        if (context != null && uri != null) {
            this.mHandler = new CallerInfoAsyncQueryHandler(context);
            this.mHandler.mQueryUri = uri;
            return;
        }
        throw new QueryPoolException("Bad context or query uri.");
    }

    static ContentResolver getCurrentProfileContentResolver(Context context) {
        int n = ActivityManager.getCurrentUser();
        if (UserManager.get(context).getUserHandle() != n) {
            try {
                Object object = context.getPackageName();
                UserHandle userHandle = new UserHandle(n);
                object = context.createPackageContextAsUser((String)object, 0, userHandle).getContentResolver();
                return object;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Rlog.e(LOG_TAG, "Can't find self package", nameNotFoundException);
            }
        }
        return context.getContentResolver();
    }

    private void release() {
        this.mHandler.mContext = null;
        this.mHandler.mQueryUri = null;
        this.mHandler.mCallerInfo = null;
        this.mHandler = null;
    }

    private static String sanitizeUriToString(Uri object) {
        if (object != null) {
            int n = ((String)(object = ((Uri)object).toString())).lastIndexOf(47);
            if (n > 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(((String)object).substring(0, n));
                stringBuilder.append("/xxxxxxx");
                return stringBuilder.toString();
            }
            return object;
        }
        return "";
    }

    public static CallerInfoAsyncQuery startQuery(int n, Context object, Uri uri, OnQueryCompleteListener onQueryCompleteListener, Object object2) {
        CallerInfoAsyncQuery callerInfoAsyncQuery = new CallerInfoAsyncQuery();
        callerInfoAsyncQuery.allocate((Context)object, uri);
        object = new CookieWrapper();
        ((CookieWrapper)object).listener = onQueryCompleteListener;
        ((CookieWrapper)object).cookie = object2;
        ((CookieWrapper)object).event = 1;
        callerInfoAsyncQuery.mHandler.startQuery(n, object, uri, null, null, null, null);
        return callerInfoAsyncQuery;
    }

    public static CallerInfoAsyncQuery startQuery(int n, Context context, String string2, OnQueryCompleteListener onQueryCompleteListener, Object object) {
        return CallerInfoAsyncQuery.startQuery(n, context, string2, onQueryCompleteListener, object, SubscriptionManager.getDefaultSubscriptionId());
    }

    public static CallerInfoAsyncQuery startQuery(int n, Context context, String string2, OnQueryCompleteListener onQueryCompleteListener, Object object, int n2) {
        Uri uri = ContactsContract.PhoneLookup.ENTERPRISE_CONTENT_FILTER_URI.buildUpon().appendPath(string2).appendQueryParameter("sip", String.valueOf(PhoneNumberUtils.isUriNumber(string2))).build();
        CallerInfoAsyncQuery callerInfoAsyncQuery = new CallerInfoAsyncQuery();
        callerInfoAsyncQuery.allocate(context, uri);
        CookieWrapper cookieWrapper = new CookieWrapper();
        cookieWrapper.listener = onQueryCompleteListener;
        cookieWrapper.cookie = object;
        cookieWrapper.number = string2;
        cookieWrapper.subId = n2;
        cookieWrapper.event = PhoneNumberUtils.isLocalEmergencyNumber(context, string2) ? 4 : (PhoneNumberUtils.isVoiceMailNumber(context, n2, string2) ? 5 : 1);
        callerInfoAsyncQuery.mHandler.startQuery(n, cookieWrapper, uri, null, null, null, null);
        return callerInfoAsyncQuery;
    }

    public void addQueryListener(int n, OnQueryCompleteListener onQueryCompleteListener, Object object) {
        CookieWrapper cookieWrapper = new CookieWrapper();
        cookieWrapper.listener = onQueryCompleteListener;
        cookieWrapper.cookie = object;
        cookieWrapper.event = 2;
        this.mHandler.startQuery(n, cookieWrapper, null, null, null, null, null);
    }

    private class CallerInfoAsyncQueryHandler
    extends AsyncQueryHandler {
        private CallerInfo mCallerInfo;
        private Context mContext;
        private List<Runnable> mPendingListenerCallbacks;
        private Uri mQueryUri;

        private CallerInfoAsyncQueryHandler(Context context) {
            super(CallerInfoAsyncQuery.getCurrentProfileContentResolver(context));
            this.mPendingListenerCallbacks = new ArrayList<Runnable>();
            this.mContext = context;
        }

        @Override
        protected Handler createHandler(Looper looper) {
            return new CallerInfoWorkerHandler(looper);
        }

        @Override
        protected void onQueryComplete(int n, Object iterator, Cursor cursor) {
            Object object = new StringBuilder();
            ((StringBuilder)object).append("##### onQueryComplete() #####   query complete for token: ");
            ((StringBuilder)object).append(n);
            Rlog.d(CallerInfoAsyncQuery.LOG_TAG, ((StringBuilder)object).toString());
            iterator = (CookieWrapper)((Object)iterator);
            if (iterator == null) {
                Rlog.i(CallerInfoAsyncQuery.LOG_TAG, "Cookie is null, ignoring onQueryComplete() request.");
                if (cursor != null) {
                    cursor.close();
                }
                return;
            }
            if (((CookieWrapper)iterator).event == 3) {
                iterator = this.mPendingListenerCallbacks.iterator();
                while (iterator.hasNext()) {
                    iterator.next().run();
                }
                this.mPendingListenerCallbacks.clear();
                CallerInfoAsyncQuery.this.release();
                if (cursor != null) {
                    cursor.close();
                }
                return;
            }
            if (((CookieWrapper)iterator).event == 6) {
                object = this.mCallerInfo;
                if (object != null) {
                    ((CallerInfo)object).geoDescription = ((CookieWrapper)iterator).geoDescription;
                }
                object = new CookieWrapper();
                ((CookieWrapper)object).event = 3;
                this.startQuery(n, object, null, null, null, null, null);
            }
            if (this.mCallerInfo == null) {
                if (this.mContext != null && this.mQueryUri != null) {
                    if (((CookieWrapper)iterator).event == 4) {
                        this.mCallerInfo = new CallerInfo().markAsEmergency(this.mContext);
                    } else if (((CookieWrapper)iterator).event == 5) {
                        this.mCallerInfo = new CallerInfo().markAsVoiceMail(((CookieWrapper)iterator).subId);
                    } else {
                        this.mCallerInfo = CallerInfo.getCallerInfo(this.mContext, this.mQueryUri, cursor);
                        object = CallerInfo.doSecondaryLookupIfNecessary(this.mContext, ((CookieWrapper)iterator).number, this.mCallerInfo);
                        if (object != this.mCallerInfo) {
                            this.mCallerInfo = object;
                        }
                        if (!TextUtils.isEmpty(((CookieWrapper)iterator).number)) {
                            this.mCallerInfo.phoneNumber = PhoneNumberUtils.formatNumber(((CookieWrapper)iterator).number, this.mCallerInfo.normalizedNumber, CallerInfo.getCurrentCountryIso(this.mContext));
                        }
                        if (TextUtils.isEmpty(this.mCallerInfo.name)) {
                            ((CookieWrapper)iterator).event = 6;
                            this.startQuery(n, iterator, null, null, null, null, null);
                            return;
                        }
                    }
                    object = new CookieWrapper();
                    ((CookieWrapper)object).event = 3;
                    this.startQuery(n, object, null, null, null, null, null);
                } else {
                    throw new QueryPoolException("Bad context or query uri, or CallerInfoAsyncQuery already released.");
                }
            }
            if (((CookieWrapper)iterator).listener != null) {
                this.mPendingListenerCallbacks.add(new Runnable((CookieWrapper)((Object)iterator), n){
                    final /* synthetic */ CookieWrapper val$cw;
                    final /* synthetic */ int val$token;
                    {
                        this.val$cw = cookieWrapper;
                        this.val$token = n;
                    }

                    @Override
                    public void run() {
                        this.val$cw.listener.onQueryComplete(this.val$token, this.val$cw.cookie, CallerInfoAsyncQueryHandler.this.mCallerInfo);
                    }
                });
            } else {
                Rlog.w(CallerInfoAsyncQuery.LOG_TAG, "There is no listener to notify for this query.");
            }
            if (cursor != null) {
                cursor.close();
            }
        }

        protected class CallerInfoWorkerHandler
        extends AsyncQueryHandler.WorkerHandler {
            public CallerInfoWorkerHandler(Looper looper) {
                super(looper);
            }

            private void handleGeoDescription(Message message) {
                AsyncQueryHandler.WorkerArgs workerArgs = (AsyncQueryHandler.WorkerArgs)message.obj;
                Object object = (CookieWrapper)workerArgs.cookie;
                if (!TextUtils.isEmpty(((CookieWrapper)object).number) && ((CookieWrapper)object).cookie != null && CallerInfoAsyncQueryHandler.this.mContext != null) {
                    SystemClock.elapsedRealtime();
                    ((CookieWrapper)object).geoDescription = CallerInfo.getGeoDescription(CallerInfoAsyncQueryHandler.this.mContext, ((CookieWrapper)object).number);
                    SystemClock.elapsedRealtime();
                }
                object = workerArgs.handler.obtainMessage(message.what);
                ((Message)object).obj = workerArgs;
                ((Message)object).arg1 = message.arg1;
                ((Message)object).sendToTarget();
            }

            @Override
            public void handleMessage(Message message) {
                Object object = (AsyncQueryHandler.WorkerArgs)message.obj;
                CookieWrapper cookieWrapper = (CookieWrapper)((AsyncQueryHandler.WorkerArgs)object).cookie;
                if (cookieWrapper == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unexpected command (CookieWrapper is null): ");
                    ((StringBuilder)object).append(message.what);
                    ((StringBuilder)object).append(" ignored by CallerInfoWorkerHandler, passing onto parent.");
                    Rlog.i(CallerInfoAsyncQuery.LOG_TAG, ((StringBuilder)object).toString());
                    super.handleMessage(message);
                } else {
                    Object object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Processing event: ");
                    ((StringBuilder)object2).append(cookieWrapper.event);
                    ((StringBuilder)object2).append(" token (arg1): ");
                    ((StringBuilder)object2).append(message.arg1);
                    ((StringBuilder)object2).append(" command: ");
                    ((StringBuilder)object2).append(message.what);
                    ((StringBuilder)object2).append(" query URI: ");
                    ((StringBuilder)object2).append(CallerInfoAsyncQuery.sanitizeUriToString(((AsyncQueryHandler.WorkerArgs)object).uri));
                    Rlog.d(CallerInfoAsyncQuery.LOG_TAG, ((StringBuilder)object2).toString());
                    switch (cookieWrapper.event) {
                        default: {
                            break;
                        }
                        case 6: {
                            this.handleGeoDescription(message);
                            break;
                        }
                        case 2: 
                        case 3: 
                        case 4: 
                        case 5: {
                            object2 = ((AsyncQueryHandler.WorkerArgs)object).handler.obtainMessage(message.what);
                            ((Message)object2).obj = object;
                            ((Message)object2).arg1 = message.arg1;
                            ((Message)object2).sendToTarget();
                            break;
                        }
                        case 1: {
                            super.handleMessage(message);
                        }
                    }
                }
            }
        }

    }

    private static final class CookieWrapper {
        public Object cookie;
        public int event;
        public String geoDescription;
        public OnQueryCompleteListener listener;
        public String number;
        public int subId;

        private CookieWrapper() {
        }
    }

    public static interface OnQueryCompleteListener {
        public void onQueryComplete(int var1, Object var2, CallerInfo var3);
    }

    public static class QueryPoolException
    extends SQLException {
        public QueryPoolException(String string2) {
            super(string2);
        }
    }

}

