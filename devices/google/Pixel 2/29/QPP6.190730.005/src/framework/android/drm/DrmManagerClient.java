/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.drm;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.drm.DrmConvertedStatus;
import android.drm.DrmErrorEvent;
import android.drm.DrmEvent;
import android.drm.DrmInfo;
import android.drm.DrmInfoEvent;
import android.drm.DrmInfoRequest;
import android.drm.DrmInfoStatus;
import android.drm.DrmRights;
import android.drm.DrmStore;
import android.drm.DrmSupportInfo;
import android.drm.DrmUtils;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import dalvik.system.CloseGuard;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class DrmManagerClient
implements AutoCloseable {
    private static final int ACTION_PROCESS_DRM_INFO = 1002;
    private static final int ACTION_REMOVE_ALL_RIGHTS = 1001;
    public static final int ERROR_NONE = 0;
    public static final int ERROR_UNKNOWN = -2000;
    public static final int INVALID_SESSION = -1;
    private static final String TAG = "DrmManagerClient";
    private final CloseGuard mCloseGuard = CloseGuard.get();
    private final AtomicBoolean mClosed = new AtomicBoolean();
    private Context mContext;
    private EventHandler mEventHandler;
    HandlerThread mEventThread;
    private InfoHandler mInfoHandler;
    HandlerThread mInfoThread;
    private long mNativeContext;
    private OnErrorListener mOnErrorListener;
    private OnEventListener mOnEventListener;
    private OnInfoListener mOnInfoListener;
    private int mUniqueId;

    static {
        System.loadLibrary("drmframework_jni");
    }

    public DrmManagerClient(Context context) {
        this.mContext = context;
        this.createEventThreads();
        this.mUniqueId = this._initialize();
        this.mCloseGuard.open("release");
    }

    private native DrmInfo _acquireDrmInfo(int var1, DrmInfoRequest var2);

    private native boolean _canHandle(int var1, String var2, String var3);

    private native int _checkRightsStatus(int var1, String var2, int var3);

    private native DrmConvertedStatus _closeConvertSession(int var1, int var2);

    private native DrmConvertedStatus _convertData(int var1, int var2, byte[] var3);

    private native DrmSupportInfo[] _getAllSupportInfo(int var1);

    private native ContentValues _getConstraints(int var1, String var2, int var3);

    private native int _getDrmObjectType(int var1, String var2, String var3);

    private native ContentValues _getMetadata(int var1, String var2);

    private native String _getOriginalMimeType(int var1, String var2, FileDescriptor var3);

    private native int _initialize();

    private native void _installDrmEngine(int var1, String var2);

    private native int _openConvertSession(int var1, String var2);

    private native DrmInfoStatus _processDrmInfo(int var1, DrmInfo var2);

    private native void _release(int var1);

    private native int _removeAllRights(int var1);

    private native int _removeRights(int var1, String var2);

    private native int _saveRights(int var1, DrmRights var2, String var3, String var4);

    private native void _setListeners(int var1, Object var2);

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private String convertUriToPath(Uri object) {
        Object object2;
        Throwable throwable22222;
        block6 : {
            Object object3;
            block5 : {
                object2 = null;
                if (object == null) return object2;
                object2 = ((Uri)object).getScheme();
                if (object2 == null) return ((Uri)object).getPath();
                if (((String)object2).equals("")) return ((Uri)object).getPath();
                if (((String)object2).equals("file")) return ((Uri)object).getPath();
                if (((String)object2).equals("http")) return ((Uri)object).toString();
                if (((String)object2).equals("https")) return ((Uri)object).toString();
                if (!((String)object2).equals("content")) throw new IllegalArgumentException("Given Uri scheme is not supported");
                object3 = null;
                object2 = null;
                object = this.mContext.getContentResolver().query((Uri)object, new String[]{"_data"}, null, null, null);
                if (object == null) break block5;
                object2 = object;
                object3 = object;
                if (object.getCount() == 0) break block5;
                object2 = object;
                object3 = object;
                if (!object.moveToFirst()) break block5;
                object2 = object;
                object3 = object;
                String string2 = object.getString(object.getColumnIndexOrThrow("_data"));
                object2 = string2;
                object.close();
                return object2;
            }
            object2 = object;
            object3 = object;
            try {
                object2 = object;
                object3 = object;
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Given Uri could not be found in media store");
                object2 = object;
                object3 = object;
                throw illegalArgumentException;
            }
            catch (Throwable throwable22222) {
                break block6;
            }
            catch (SQLiteException sQLiteException) {
                object2 = object3;
                object2 = object3;
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException("Given Uri is not formatted in a way so that it can be found in media store.");
                object2 = object3;
                throw illegalArgumentException;
            }
        }
        if (object2 == null) throw throwable22222;
        object2.close();
        throw throwable22222;
    }

    private void createEventThreads() {
        if (this.mEventHandler == null && this.mInfoHandler == null) {
            this.mInfoThread = new HandlerThread("DrmManagerClient.InfoHandler");
            this.mInfoThread.start();
            this.mInfoHandler = new InfoHandler(this.mInfoThread.getLooper());
            this.mEventThread = new HandlerThread("DrmManagerClient.EventHandler");
            this.mEventThread.start();
            this.mEventHandler = new EventHandler(this.mEventThread.getLooper());
        }
    }

    private void createListeners() {
        this._setListeners(this.mUniqueId, new WeakReference<DrmManagerClient>(this));
    }

    private int getErrorType(int n) {
        int n2 = -1;
        n = n != 1 && n != 2 && n != 3 ? n2 : 2006;
        return n;
    }

    private int getEventType(int n) {
        int n2 = -1;
        n = n != 1 && n != 2 && n != 3 ? n2 : 1002;
        return n;
    }

    public static void notify(Object object, int n, int n2, String object2) {
        InfoHandler infoHandler;
        if ((object = (DrmManagerClient)((WeakReference)object).get()) != null && (infoHandler = ((DrmManagerClient)object).mInfoHandler) != null) {
            object2 = infoHandler.obtainMessage(1, n, n2, object2);
            ((DrmManagerClient)object).mInfoHandler.sendMessage((Message)object2);
        }
    }

    public DrmInfo acquireDrmInfo(DrmInfoRequest drmInfoRequest) {
        if (drmInfoRequest != null && drmInfoRequest.isValid()) {
            return this._acquireDrmInfo(this.mUniqueId, drmInfoRequest);
        }
        throw new IllegalArgumentException("Given drmInfoRequest is invalid/null");
    }

    public int acquireRights(DrmInfoRequest object) {
        if ((object = this.acquireDrmInfo((DrmInfoRequest)object)) == null) {
            return -2000;
        }
        return this.processDrmInfo((DrmInfo)object);
    }

    public boolean canHandle(Uri uri, String string2) {
        if (uri != null && Uri.EMPTY != uri || string2 != null && !string2.equals("")) {
            return this.canHandle(this.convertUriToPath(uri), string2);
        }
        throw new IllegalArgumentException("Uri or the mimetype should be non null");
    }

    public boolean canHandle(String string2, String string3) {
        if (string2 != null && !string2.equals("") || string3 != null && !string3.equals("")) {
            return this._canHandle(this.mUniqueId, string2, string3);
        }
        throw new IllegalArgumentException("Path or the mimetype should be non null");
    }

    public int checkRightsStatus(Uri uri) {
        if (uri != null && Uri.EMPTY != uri) {
            return this.checkRightsStatus(this.convertUriToPath(uri));
        }
        throw new IllegalArgumentException("Given uri is not valid");
    }

    public int checkRightsStatus(Uri uri, int n) {
        if (uri != null && Uri.EMPTY != uri) {
            return this.checkRightsStatus(this.convertUriToPath(uri), n);
        }
        throw new IllegalArgumentException("Given uri is not valid");
    }

    public int checkRightsStatus(String string2) {
        return this.checkRightsStatus(string2, 0);
    }

    public int checkRightsStatus(String string2, int n) {
        if (string2 != null && !string2.equals("") && DrmStore.Action.isValid(n)) {
            return this._checkRightsStatus(this.mUniqueId, string2, n);
        }
        throw new IllegalArgumentException("Given path or action is not valid");
    }

    @Override
    public void close() {
        this.mCloseGuard.close();
        if (this.mClosed.compareAndSet(false, true)) {
            if (this.mEventHandler != null) {
                this.mEventThread.quit();
                this.mEventThread = null;
            }
            if (this.mInfoHandler != null) {
                this.mInfoThread.quit();
                this.mInfoThread = null;
            }
            this.mEventHandler = null;
            this.mInfoHandler = null;
            this.mOnEventListener = null;
            this.mOnInfoListener = null;
            this.mOnErrorListener = null;
            this._release(this.mUniqueId);
        }
    }

    public DrmConvertedStatus closeConvertSession(int n) {
        return this._closeConvertSession(this.mUniqueId, n);
    }

    public DrmConvertedStatus convertData(int n, byte[] arrby) {
        if (arrby != null && arrby.length > 0) {
            return this._convertData(this.mUniqueId, n, arrby);
        }
        throw new IllegalArgumentException("Given inputData should be non null");
    }

    protected void finalize() throws Throwable {
        try {
            if (this.mCloseGuard != null) {
                this.mCloseGuard.warnIfOpen();
            }
            this.close();
            return;
        }
        finally {
            super.finalize();
        }
    }

    public String[] getAvailableDrmEngines() {
        DrmSupportInfo[] arrdrmSupportInfo = this._getAllSupportInfo(this.mUniqueId);
        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < arrdrmSupportInfo.length; ++i) {
            arrayList.add(arrdrmSupportInfo[i].getDescriprition());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public ContentValues getConstraints(Uri uri, int n) {
        if (uri != null && Uri.EMPTY != uri) {
            return this.getConstraints(this.convertUriToPath(uri), n);
        }
        throw new IllegalArgumentException("Uri should be non null");
    }

    public ContentValues getConstraints(String string2, int n) {
        if (string2 != null && !string2.equals("") && DrmStore.Action.isValid(n)) {
            return this._getConstraints(this.mUniqueId, string2, n);
        }
        throw new IllegalArgumentException("Given usage or path is invalid/null");
    }

    public int getDrmObjectType(Uri object, String string2) {
        if (object != null && Uri.EMPTY != object || string2 != null && !string2.equals("")) {
            String string3 = "";
            try {
                object = this.convertUriToPath((Uri)object);
            }
            catch (Exception exception) {
                Log.w("DrmManagerClient", "Given Uri could not be found in media store");
                object = string3;
            }
            return this.getDrmObjectType((String)object, string2);
        }
        throw new IllegalArgumentException("Uri or the mimetype should be non null");
    }

    public int getDrmObjectType(String string2, String string3) {
        if (string2 != null && !string2.equals("") || string3 != null && !string3.equals("")) {
            return this._getDrmObjectType(this.mUniqueId, string2, string3);
        }
        throw new IllegalArgumentException("Path or the mimetype should be non null");
    }

    public ContentValues getMetadata(Uri uri) {
        if (uri != null && Uri.EMPTY != uri) {
            return this.getMetadata(this.convertUriToPath(uri));
        }
        throw new IllegalArgumentException("Uri should be non null");
    }

    public ContentValues getMetadata(String string2) {
        if (string2 != null && !string2.equals("")) {
            return this._getMetadata(this.mUniqueId, string2);
        }
        throw new IllegalArgumentException("Given path is invalid/null");
    }

    public String getOriginalMimeType(Uri uri) {
        if (uri != null && Uri.EMPTY != uri) {
            return this.getOriginalMimeType(this.convertUriToPath(uri));
        }
        throw new IllegalArgumentException("Given uri is not valid");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String getOriginalMimeType(String object) {
        if (object == null) throw new IllegalArgumentException("Given path should be non null");
        if (((String)object).equals("")) throw new IllegalArgumentException("Given path should be non null");
        Object var2_5 = null;
        FileInputStream fileInputStream = null;
        Object var4_7 = null;
        FileInputStream fileInputStream2 = null;
        FileInputStream fileInputStream3 = null;
        FileInputStream fileInputStream4 = null;
        FileDescriptor fileDescriptor = null;
        Object object2 = fileInputStream2;
        FileInputStream fileInputStream5 = fileInputStream3;
        object2 = fileInputStream2;
        fileInputStream5 = fileInputStream3;
        File file = new File((String)object);
        object2 = fileInputStream2;
        fileInputStream5 = fileInputStream3;
        if (file.exists()) {
            object2 = fileInputStream2;
            fileInputStream5 = fileInputStream3;
            object2 = fileInputStream2;
            fileInputStream5 = fileInputStream3;
            fileInputStream4 = new FileInputStream(file);
            object2 = fileInputStream4;
            fileInputStream5 = fileInputStream4;
            fileDescriptor = fileInputStream4.getFD();
        }
        object2 = fileInputStream4;
        fileInputStream5 = fileInputStream4;
        object2 = object = this._getOriginalMimeType(this.mUniqueId, (String)object, fileDescriptor);
        if (fileInputStream4 == null) return object2;
        object2 = object;
        fileInputStream4.close();
        return object;
        catch (Throwable throwable) {
            if (object2 == null) throw throwable;
            try {
                ((FileInputStream)object2).close();
                throw throwable;
            }
            catch (IOException iOException) {
                throw throwable;
            }
        }
        catch (IOException iOException) {
            object2 = fileInputStream;
            if (fileInputStream5 == null) return object2;
            object2 = var2_5;
            try {
                fileInputStream5.close();
                object = var4_7;
                return object;
            }
            catch (IOException iOException2) {
                object = object2;
            }
        }
        return object;
    }

    public void installDrmEngine(String string2) {
        if (string2 != null && !string2.equals("")) {
            this._installDrmEngine(this.mUniqueId, string2);
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Given engineFilePath: ");
        stringBuilder.append(string2);
        stringBuilder.append("is not valid");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int openConvertSession(String string2) {
        if (string2 != null && !string2.equals("")) {
            return this._openConvertSession(this.mUniqueId, string2);
        }
        throw new IllegalArgumentException("Path or the mimeType should be non null");
    }

    public int processDrmInfo(DrmInfo object) {
        if (object != null && ((DrmInfo)object).isValid()) {
            int n = -2000;
            EventHandler eventHandler = this.mEventHandler;
            if (eventHandler != null) {
                n = this.mEventHandler.sendMessage((Message)(object = eventHandler.obtainMessage(1002, object))) ? 0 : -2000;
            }
            return n;
        }
        throw new IllegalArgumentException("Given drmInfo is invalid/null");
    }

    @Deprecated
    public void release() {
        this.close();
    }

    public int removeAllRights() {
        int n = -2000;
        Object object = this.mEventHandler;
        if (object != null) {
            n = this.mEventHandler.sendMessage((Message)(object = ((Handler)object).obtainMessage(1001))) ? 0 : -2000;
        }
        return n;
    }

    public int removeRights(Uri uri) {
        if (uri != null && Uri.EMPTY != uri) {
            return this.removeRights(this.convertUriToPath(uri));
        }
        throw new IllegalArgumentException("Given uri is not valid");
    }

    public int removeRights(String string2) {
        if (string2 != null && !string2.equals("")) {
            return this._removeRights(this.mUniqueId, string2);
        }
        throw new IllegalArgumentException("Given path should be non null");
    }

    public int saveRights(DrmRights drmRights, String string2, String string3) throws IOException {
        if (drmRights != null && drmRights.isValid()) {
            if (string2 != null && !string2.equals("")) {
                DrmUtils.writeToFile(string2, drmRights.getData());
            }
            return this._saveRights(this.mUniqueId, drmRights, string2, string3);
        }
        throw new IllegalArgumentException("Given drmRights or contentPath is not valid");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnErrorListener(OnErrorListener onErrorListener) {
        synchronized (this) {
            this.mOnErrorListener = onErrorListener;
            if (onErrorListener != null) {
                this.createListeners();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnEventListener(OnEventListener onEventListener) {
        synchronized (this) {
            this.mOnEventListener = onEventListener;
            if (onEventListener != null) {
                this.createListeners();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setOnInfoListener(OnInfoListener onInfoListener) {
        synchronized (this) {
            this.mOnInfoListener = onInfoListener;
            if (onInfoListener != null) {
                this.createListeners();
            }
            return;
        }
    }

    private class EventHandler
    extends Handler {
        public EventHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message object) {
            Object var2_2 = null;
            Object object2 = null;
            HashMap<String, Object> hashMap = new HashMap<String, Object>();
            int n = ((Message)object).what;
            if (n != 1001) {
                if (n != 1002) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unknown message type ");
                    ((StringBuilder)object2).append(((Message)object).what);
                    Log.e(DrmManagerClient.TAG, ((StringBuilder)object2).toString());
                    return;
                }
                object = (DrmInfo)((Message)object).obj;
                Object object3 = DrmManagerClient.this;
                object3 = ((DrmManagerClient)object3)._processDrmInfo(((DrmManagerClient)object3).mUniqueId, (DrmInfo)object);
                hashMap.put("drm_info_status_object", object3);
                hashMap.put("drm_info_object", object);
                if (object3 != null && 1 == ((DrmInfoStatus)object3).statusCode) {
                    object = new DrmEvent(DrmManagerClient.this.mUniqueId, DrmManagerClient.this.getEventType(((DrmInfoStatus)object3).infoType), null, hashMap);
                } else {
                    n = object3 != null ? ((DrmInfoStatus)object3).infoType : ((DrmInfo)object).getInfoType();
                    object2 = new DrmErrorEvent(DrmManagerClient.this.mUniqueId, DrmManagerClient.this.getErrorType(n), null, hashMap);
                    object = var2_2;
                }
            } else {
                object = DrmManagerClient.this;
                if (((DrmManagerClient)object)._removeAllRights(((DrmManagerClient)object).mUniqueId) == 0) {
                    object = new DrmEvent(DrmManagerClient.this.mUniqueId, 1001, null);
                } else {
                    object2 = new DrmErrorEvent(DrmManagerClient.this.mUniqueId, 2007, null);
                    object = var2_2;
                }
            }
            if (DrmManagerClient.this.mOnEventListener != null && object != null) {
                DrmManagerClient.this.mOnEventListener.onEvent(DrmManagerClient.this, (DrmEvent)object);
            }
            if (DrmManagerClient.this.mOnErrorListener != null && object2 != null) {
                DrmManagerClient.this.mOnErrorListener.onError(DrmManagerClient.this, (DrmErrorEvent)object2);
            }
        }
    }

    private class InfoHandler
    extends Handler {
        public static final int INFO_EVENT_TYPE = 1;

        public InfoHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message object) {
            Object var2_2 = null;
            Object object2 = null;
            if (((Message)object).what != 1) {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Unknown message type ");
                ((StringBuilder)object2).append(((Message)object).what);
                Log.e(DrmManagerClient.TAG, ((StringBuilder)object2).toString());
                return;
            }
            int n = ((Message)object).arg1;
            int n2 = ((Message)object).arg2;
            object = ((Message)object).obj.toString();
            switch (n2) {
                default: {
                    object2 = new DrmErrorEvent(n, n2, (String)object);
                    object = var2_2;
                    break;
                }
                case 2: {
                    try {
                        DrmUtils.removeFile((String)object);
                    }
                    catch (IOException iOException) {
                        iOException.printStackTrace();
                    }
                    object = new DrmInfoEvent(n, n2, (String)object);
                    break;
                }
                case 1: 
                case 3: 
                case 4: 
                case 5: 
                case 6: {
                    object = new DrmInfoEvent(n, n2, (String)object);
                }
            }
            if (DrmManagerClient.this.mOnInfoListener != null && object != null) {
                DrmManagerClient.this.mOnInfoListener.onInfo(DrmManagerClient.this, (DrmInfoEvent)object);
            }
            if (DrmManagerClient.this.mOnErrorListener != null && object2 != null) {
                DrmManagerClient.this.mOnErrorListener.onError(DrmManagerClient.this, (DrmErrorEvent)object2);
            }
        }
    }

    public static interface OnErrorListener {
        public void onError(DrmManagerClient var1, DrmErrorEvent var2);
    }

    public static interface OnEventListener {
        public void onEvent(DrmManagerClient var1, DrmEvent var2);
    }

    public static interface OnInfoListener {
        public void onInfo(DrmManagerClient var1, DrmInfoEvent var2);
    }

}

