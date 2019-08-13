/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.face;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.UserInfo;
import android.content.res.Resources;
import android.hardware.biometrics.BiometricAuthenticator;
import android.hardware.biometrics.BiometricFaceConstants;
import android.hardware.biometrics.CryptoObject;
import android.hardware.biometrics.IBiometricServiceLockoutResetCallback;
import android.hardware.face.Face;
import android.hardware.face.IFaceService;
import android.hardware.face.IFaceServiceReceiver;
import android.hardware.face._$$Lambda$FaceManager$2$IVmrd2VOH7JdDdb7PFFlL5bjZ5w;
import android.os.Binder;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.Handler;
import android.os.IBinder;
import android.os.IRemoteCallback;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.Trace;
import android.os.UserHandle;
import android.util.Log;
import android.util.Slog;
import com.android.internal.os.SomeArgs;
import java.util.List;

public class FaceManager
implements BiometricAuthenticator,
BiometricFaceConstants {
    private static final boolean DEBUG = true;
    private static final int MSG_ACQUIRED = 101;
    private static final int MSG_AUTHENTICATION_FAILED = 103;
    private static final int MSG_AUTHENTICATION_SUCCEEDED = 102;
    private static final int MSG_ENROLL_RESULT = 100;
    private static final int MSG_ERROR = 104;
    private static final int MSG_GET_FEATURE_COMPLETED = 106;
    private static final int MSG_REMOVED = 105;
    private static final int MSG_SET_FEATURE_COMPLETED = 107;
    private static final String TAG = "FaceManager";
    private AuthenticationCallback mAuthenticationCallback;
    private final Context mContext;
    private CryptoObject mCryptoObject;
    private EnrollmentCallback mEnrollmentCallback;
    private GetFeatureCallback mGetFeatureCallback;
    private Handler mHandler;
    private RemovalCallback mRemovalCallback;
    private Face mRemovalFace;
    private IFaceService mService;
    private IFaceServiceReceiver mServiceReceiver = new IFaceServiceReceiver.Stub(){

        @Override
        public void onAcquired(long l, int n, int n2) {
            FaceManager.this.mHandler.obtainMessage(101, n, n2, l).sendToTarget();
        }

        @Override
        public void onAuthenticationFailed(long l) {
            FaceManager.this.mHandler.obtainMessage(103).sendToTarget();
        }

        @Override
        public void onAuthenticationSucceeded(long l, Face face, int n) {
            FaceManager.this.mHandler.obtainMessage(102, n, 0, face).sendToTarget();
        }

        @Override
        public void onEnrollResult(long l, int n, int n2) {
            FaceManager.this.mHandler.obtainMessage(100, n2, 0, new Face(null, n, l)).sendToTarget();
        }

        @Override
        public void onEnumerated(long l, int n, int n2) {
        }

        @Override
        public void onError(long l, int n, int n2) {
            FaceManager.this.mHandler.obtainMessage(104, n, n2, l).sendToTarget();
        }

        @Override
        public void onFeatureGet(boolean bl, int n, boolean bl2) {
            SomeArgs someArgs = SomeArgs.obtain();
            someArgs.arg1 = bl;
            someArgs.argi1 = n;
            someArgs.arg2 = bl2;
            FaceManager.this.mHandler.obtainMessage(106, someArgs).sendToTarget();
        }

        @Override
        public void onFeatureSet(boolean bl, int n) {
            FaceManager.this.mHandler.obtainMessage(107, n, 0, bl).sendToTarget();
        }

        @Override
        public void onRemoved(long l, int n, int n2) {
            FaceManager.this.mHandler.obtainMessage(105, n2, 0, new Face(null, n, l)).sendToTarget();
        }
    };
    private SetFeatureCallback mSetFeatureCallback;
    private IBinder mToken = new Binder();

    public FaceManager(Context context, IFaceService iFaceService) {
        this.mContext = context;
        this.mService = iFaceService;
        if (this.mService == null) {
            Slog.v(TAG, "FaceAuthenticationManagerService was null");
        }
        this.mHandler = new MyHandler(context);
    }

    private void cancelAuthentication(CryptoObject object) {
        object = this.mService;
        if (object != null) {
            try {
                object.cancelAuthentication(this.mToken, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    private void cancelEnrollment() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.cancelEnrollment(this.mToken);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public static String getAcquiredString(Context arrstring, int n, int n2) {
        switch (n) {
            default: {
                break;
            }
            case 22: {
                arrstring = arrstring.getResources().getStringArray(17236083);
                if (n2 >= arrstring.length) break;
                return arrstring[n2];
            }
            case 21: {
                return arrstring.getString(17039961);
            }
            case 20: {
                return null;
            }
            case 19: {
                return arrstring.getString(17039956);
            }
            case 18: {
                return arrstring.getString(17039960);
            }
            case 17: {
                return arrstring.getString(17039962);
            }
            case 16: {
                return arrstring.getString(17039957);
            }
            case 15: {
                return arrstring.getString(17039973);
            }
            case 14: {
                return arrstring.getString(17039966);
            }
            case 13: {
                return arrstring.getString(17039959);
            }
            case 12: {
                return arrstring.getString(17039971);
            }
            case 11: {
                return arrstring.getString(17039955);
            }
            case 10: {
                return arrstring.getString(17039958);
            }
            case 9: {
                return arrstring.getString(17039969);
            }
            case 8: {
                return arrstring.getString(17039972);
            }
            case 7: {
                return arrstring.getString(17039970);
            }
            case 6: {
                return arrstring.getString(17039968);
            }
            case 5: {
                return arrstring.getString(17039967);
            }
            case 4: {
                return arrstring.getString(17039964);
            }
            case 3: {
                return arrstring.getString(17039965);
            }
            case 2: {
                return arrstring.getString(17039963);
            }
            case 1: {
                return arrstring.getString(17039954);
            }
            case 0: {
                return null;
            }
        }
        arrstring = new StringBuilder();
        arrstring.append("Invalid acquired message: ");
        arrstring.append(n);
        arrstring.append(", ");
        arrstring.append(n2);
        Slog.w(TAG, arrstring.toString());
        return null;
    }

    private int getCurrentUserId() {
        try {
            int n = ActivityManager.getService().getCurrentUser().id;
            return n;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    public static String getErrorString(Context arrstring, int n, int n2) {
        switch (n) {
            default: {
                break;
            }
            case 12: {
                return arrstring.getString(17039978);
            }
            case 11: {
                return arrstring.getString(17039982);
            }
            case 10: {
                return arrstring.getString(17039985);
            }
            case 9: {
                return arrstring.getString(17039980);
            }
            case 8: {
                arrstring = arrstring.getResources().getStringArray(17236084);
                if (n2 >= arrstring.length) break;
                return arrstring[n2];
            }
            case 7: {
                return arrstring.getString(17039979);
            }
            case 5: {
                return arrstring.getString(17039976);
            }
            case 4: {
                return arrstring.getString(17039981);
            }
            case 3: {
                return arrstring.getString(17039983);
            }
            case 2: {
                return arrstring.getString(17039984);
            }
            case 1: {
                return arrstring.getString(17039977);
            }
        }
        arrstring = new StringBuilder();
        arrstring.append("Invalid error message: ");
        arrstring.append(n);
        arrstring.append(", ");
        arrstring.append(n2);
        Slog.w(TAG, arrstring.toString());
        return null;
    }

    public static int getMappedAcquiredInfo(int n, int n2) {
        if (n != 22) {
            switch (n) {
                default: {
                    return 0;
                }
                case 10: 
                case 11: 
                case 12: 
                case 13: {
                    return 2;
                }
                case 4: 
                case 5: 
                case 6: 
                case 7: 
                case 8: 
                case 9: {
                    return 1;
                }
                case 1: 
                case 2: 
                case 3: {
                    return 2;
                }
                case 0: 
            }
            return 0;
        }
        return n2 + 1000;
    }

    private void sendAcquiredResult(long l, int n, int n2) {
        Object object;
        Object object2 = this.mAuthenticationCallback;
        if (object2 != null) {
            ((AuthenticationCallback)object2).onAuthenticationAcquired(n);
        }
        object2 = FaceManager.getAcquiredString(this.mContext, n, n2);
        if (n == 22) {
            n = n2 + 1000;
        }
        if ((object = this.mEnrollmentCallback) != null) {
            ((EnrollmentCallback)object).onEnrollmentHelp(n, (CharSequence)object2);
        } else {
            object = this.mAuthenticationCallback;
            if (object != null && object2 != null) {
                ((AuthenticationCallback)object).onAuthenticationHelp(n, (CharSequence)object2);
            }
        }
    }

    private void sendAuthenticatedFailed() {
        AuthenticationCallback authenticationCallback = this.mAuthenticationCallback;
        if (authenticationCallback != null) {
            authenticationCallback.onAuthenticationFailed();
        }
    }

    private void sendAuthenticatedSucceeded(Face object, int n) {
        if (this.mAuthenticationCallback != null) {
            object = new AuthenticationResult(this.mCryptoObject, (Face)object, n);
            this.mAuthenticationCallback.onAuthenticationSucceeded((AuthenticationResult)object);
        }
    }

    private void sendEnrollResult(Face object, int n) {
        object = this.mEnrollmentCallback;
        if (object != null) {
            ((EnrollmentCallback)object).onEnrollmentProgress(n);
        }
    }

    private void sendErrorResult(long l, int n, int n2) {
        int n3 = n == 8 ? n2 + 1000 : n;
        Object object = this.mEnrollmentCallback;
        if (object != null) {
            ((EnrollmentCallback)object).onEnrollmentError(n3, FaceManager.getErrorString(this.mContext, n, n2));
        } else {
            object = this.mAuthenticationCallback;
            if (object != null) {
                ((AuthenticationCallback)object).onAuthenticationError(n3, FaceManager.getErrorString(this.mContext, n, n2));
            } else {
                object = this.mRemovalCallback;
                if (object != null) {
                    ((RemovalCallback)object).onRemovalError(this.mRemovalFace, n3, FaceManager.getErrorString(this.mContext, n, n2));
                }
            }
        }
    }

    private void sendGetFeatureCompleted(boolean bl, int n, boolean bl2) {
        GetFeatureCallback getFeatureCallback = this.mGetFeatureCallback;
        if (getFeatureCallback == null) {
            return;
        }
        getFeatureCallback.onCompleted(bl, n, bl2);
    }

    private void sendRemovedResult(Face face, int n) {
        RemovalCallback removalCallback = this.mRemovalCallback;
        if (removalCallback == null) {
            return;
        }
        if (face == null) {
            Log.e(TAG, "Received MSG_REMOVED, but face is null");
            return;
        }
        removalCallback.onRemovalSucceeded(face, n);
    }

    private void sendSetFeatureCompleted(boolean bl, int n) {
        SetFeatureCallback setFeatureCallback = this.mSetFeatureCallback;
        if (setFeatureCallback == null) {
            return;
        }
        setFeatureCallback.onCompleted(bl, n);
    }

    private void useHandler(Handler handler) {
        if (handler != null) {
            this.mHandler = new MyHandler(handler.getLooper());
        } else if (this.mHandler.getLooper() != this.mContext.getMainLooper()) {
            this.mHandler = new MyHandler(this.mContext.getMainLooper());
        }
    }

    public void addLockoutResetCallback(final LockoutResetCallback lockoutResetCallback) {
        if (this.mService != null) {
            try {
                final PowerManager powerManager = this.mContext.getSystemService(PowerManager.class);
                IFaceService iFaceService = this.mService;
                IBiometricServiceLockoutResetCallback.Stub stub = new IBiometricServiceLockoutResetCallback.Stub(){

                    static /* synthetic */ void lambda$onLockoutReset$0(LockoutResetCallback lockoutResetCallback2, PowerManager.WakeLock wakeLock) {
                        try {
                            lockoutResetCallback2.onLockoutReset();
                            return;
                        }
                        finally {
                            wakeLock.release();
                        }
                    }

                    @Override
                    public void onLockoutReset(long l, IRemoteCallback iRemoteCallback) throws RemoteException {
                        try {
                            PowerManager.WakeLock wakeLock = powerManager.newWakeLock(1, "faceLockoutResetCallback");
                            wakeLock.acquire();
                            Handler handler = FaceManager.this.mHandler;
                            LockoutResetCallback lockoutResetCallback2 = lockoutResetCallback;
                            _$$Lambda$FaceManager$2$IVmrd2VOH7JdDdb7PFFlL5bjZ5w _$$Lambda$FaceManager$2$IVmrd2VOH7JdDdb7PFFlL5bjZ5w = new _$$Lambda$FaceManager$2$IVmrd2VOH7JdDdb7PFFlL5bjZ5w(lockoutResetCallback2, wakeLock);
                            handler.post(_$$Lambda$FaceManager$2$IVmrd2VOH7JdDdb7PFFlL5bjZ5w);
                            return;
                        }
                        finally {
                            iRemoteCallback.sendResult(null);
                        }
                    }
                };
                iFaceService.addLockoutResetCallback(stub);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        } else {
            Log.w(TAG, "addLockoutResetCallback(): Service not connected!");
        }
    }

    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int n, AuthenticationCallback authenticationCallback, Handler handler) {
        this.authenticate(cryptoObject, cancellationSignal, n, authenticationCallback, handler, this.mContext.getUserId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void authenticate(CryptoObject cryptoObject, CancellationSignal cancellationSignal, int n, AuthenticationCallback authenticationCallback, Handler handler, int n2) {
        if (authenticationCallback == null) throw new IllegalArgumentException("Must supply an authentication callback");
        if (cancellationSignal != null) {
            if (cancellationSignal.isCanceled()) {
                Log.w(TAG, "authentication already canceled");
                return;
            }
            cancellationSignal.setOnCancelListener(new OnAuthenticationCancelListener(cryptoObject));
        }
        if (this.mService == null) return;
        try {
            try {
                this.useHandler(handler);
                this.mAuthenticationCallback = authenticationCallback;
                this.mCryptoObject = cryptoObject;
                long l = cryptoObject != null ? cryptoObject.getOpId() : 0L;
                Trace.beginSection("FaceManager#authenticate");
                this.mService.authenticate(this.mToken, l, n2, this.mServiceReceiver, n, this.mContext.getOpPackageName());
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, "Remote exception while authenticating: ", remoteException);
                authenticationCallback.onAuthenticationError(1, FaceManager.getErrorString(this.mContext, 1, 0));
            }
        }
        catch (Throwable throwable2) {}
        Trace.endSection();
        return;
        Trace.endSection();
        throw throwable2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void enroll(byte[] arrby, CancellationSignal cancellationSignal, EnrollmentCallback enrollmentCallback, int[] arrn) {
        if (enrollmentCallback == null) throw new IllegalArgumentException("Must supply an enrollment callback");
        if (cancellationSignal != null) {
            if (cancellationSignal.isCanceled()) {
                Log.w(TAG, "enrollment already canceled");
                return;
            }
            cancellationSignal.setOnCancelListener(new OnEnrollCancelListener());
        }
        if (this.mService == null) return;
        try {
            try {
                this.mEnrollmentCallback = enrollmentCallback;
                Trace.beginSection("FaceManager#enroll");
                this.mService.enroll(this.mToken, arrby, this.mServiceReceiver, this.mContext.getOpPackageName(), arrn);
            }
            catch (RemoteException remoteException) {
                Log.w(TAG, "Remote exception in enroll: ", remoteException);
                enrollmentCallback.onEnrollmentError(1, FaceManager.getErrorString(this.mContext, 1, 0));
            }
        }
        catch (Throwable throwable2) {}
        Trace.endSection();
        return;
        Trace.endSection();
        throw throwable2;
    }

    public long generateChallenge() {
        long l = 0L;
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                l = iFaceService.generateChallenge(this.mToken);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return l;
    }

    public long getAuthenticatorId() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                long l = iFaceService.getAuthenticatorId(this.mContext.getOpPackageName());
                return l;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "getAuthenticatorId(): Service not connected!");
        return 0L;
    }

    public List<Face> getEnrolledFaces() {
        return this.getEnrolledFaces(UserHandle.myUserId());
    }

    public List<Face> getEnrolledFaces(int n) {
        Object object = this.mService;
        if (object != null) {
            try {
                object = object.getEnrolledFaces(n, this.mContext.getOpPackageName());
                return object;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return null;
    }

    public void getFeature(int n, GetFeatureCallback getFeatureCallback) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                this.mGetFeatureCallback = getFeatureCallback;
                iFaceService.getFeature(n, this.mServiceReceiver);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    @Override
    public boolean hasEnrolledTemplates() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                boolean bl = iFaceService.hasEnrolledFaces(UserHandle.myUserId(), this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @Override
    public boolean hasEnrolledTemplates(int n) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                boolean bl = iFaceService.hasEnrolledFaces(n, this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return false;
    }

    @Override
    public boolean isHardwareDetected() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                boolean bl = iFaceService.isHardwareDetected(0L, this.mContext.getOpPackageName());
                return bl;
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        Log.w(TAG, "isFaceHardwareDetected(): Service not connected!");
        return false;
    }

    public void remove(Face face, int n, RemovalCallback removalCallback) {
        block3 : {
            IFaceService iFaceService = this.mService;
            if (iFaceService != null) {
                try {
                    this.mRemovalCallback = removalCallback;
                    this.mRemovalFace = face;
                    iFaceService.remove(this.mToken, face.getBiometricId(), n, this.mServiceReceiver);
                }
                catch (RemoteException remoteException) {
                    Log.w(TAG, "Remote exception in remove: ", remoteException);
                    if (removalCallback == null) break block3;
                    removalCallback.onRemovalError(face, 1, FaceManager.getErrorString(this.mContext, 1, 0));
                }
            }
        }
    }

    public int revokeChallenge() {
        int n = 0;
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                n = iFaceService.revokeChallenge(this.mToken);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
        return n;
    }

    @Override
    public void setActiveUser(int n) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.setActiveUser(n);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void setFeature(int n, boolean bl, byte[] arrby, SetFeatureCallback setFeatureCallback) {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                this.mSetFeatureCallback = setFeatureCallback;
                iFaceService.setFeature(n, bl, arrby, this.mServiceReceiver);
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public void userActivity() {
        IFaceService iFaceService = this.mService;
        if (iFaceService != null) {
            try {
                iFaceService.userActivity();
            }
            catch (RemoteException remoteException) {
                throw remoteException.rethrowFromSystemServer();
            }
        }
    }

    public static abstract class AuthenticationCallback
    extends BiometricAuthenticator.AuthenticationCallback {
        @Override
        public void onAuthenticationAcquired(int n) {
        }

        @Override
        public void onAuthenticationError(int n, CharSequence charSequence) {
        }

        @Override
        public void onAuthenticationFailed() {
        }

        @Override
        public void onAuthenticationHelp(int n, CharSequence charSequence) {
        }

        public void onAuthenticationSucceeded(AuthenticationResult authenticationResult) {
        }
    }

    public static class AuthenticationResult {
        private CryptoObject mCryptoObject;
        private Face mFace;
        private int mUserId;

        public AuthenticationResult(CryptoObject cryptoObject, Face face, int n) {
            this.mCryptoObject = cryptoObject;
            this.mFace = face;
            this.mUserId = n;
        }

        public CryptoObject getCryptoObject() {
            return this.mCryptoObject;
        }

        public Face getFace() {
            return this.mFace;
        }

        public int getUserId() {
            return this.mUserId;
        }
    }

    public static abstract class EnrollmentCallback {
        public void onEnrollmentError(int n, CharSequence charSequence) {
        }

        public void onEnrollmentHelp(int n, CharSequence charSequence) {
        }

        public void onEnrollmentProgress(int n) {
        }
    }

    public static abstract class GetFeatureCallback {
        public abstract void onCompleted(boolean var1, int var2, boolean var3);
    }

    public static abstract class LockoutResetCallback {
        public void onLockoutReset() {
        }
    }

    private class MyHandler
    extends Handler {
        private MyHandler(Context context) {
            super(context.getMainLooper());
        }

        private MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message object) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("FaceManager#handleMessage: ");
            stringBuilder.append(Integer.toString(((Message)object).what));
            Trace.beginSection(stringBuilder.toString());
            switch (((Message)object).what) {
                default: {
                    stringBuilder = new StringBuilder();
                    stringBuilder.append("Unknown message: ");
                    stringBuilder.append(((Message)object).what);
                    Log.w(FaceManager.TAG, stringBuilder.toString());
                    break;
                }
                case 107: {
                    FaceManager.this.sendSetFeatureCompleted((Boolean)((Message)object).obj, ((Message)object).arg1);
                    break;
                }
                case 106: {
                    object = (SomeArgs)((Message)object).obj;
                    FaceManager.this.sendGetFeatureCompleted((Boolean)((SomeArgs)object).arg1, ((SomeArgs)object).argi1, (Boolean)((SomeArgs)object).arg2);
                    ((SomeArgs)object).recycle();
                    break;
                }
                case 105: {
                    FaceManager.this.sendRemovedResult((Face)((Message)object).obj, ((Message)object).arg1);
                    break;
                }
                case 104: {
                    FaceManager.this.sendErrorResult((Long)((Message)object).obj, ((Message)object).arg1, ((Message)object).arg2);
                    break;
                }
                case 103: {
                    FaceManager.this.sendAuthenticatedFailed();
                    break;
                }
                case 102: {
                    FaceManager.this.sendAuthenticatedSucceeded((Face)((Message)object).obj, ((Message)object).arg1);
                    break;
                }
                case 101: {
                    FaceManager.this.sendAcquiredResult((Long)((Message)object).obj, ((Message)object).arg1, ((Message)object).arg2);
                    break;
                }
                case 100: {
                    FaceManager.this.sendEnrollResult((Face)((Message)object).obj, ((Message)object).arg1);
                }
            }
            Trace.endSection();
        }
    }

    private class OnAuthenticationCancelListener
    implements CancellationSignal.OnCancelListener {
        private CryptoObject mCrypto;

        OnAuthenticationCancelListener(CryptoObject cryptoObject) {
            this.mCrypto = cryptoObject;
        }

        @Override
        public void onCancel() {
            FaceManager.this.cancelAuthentication(this.mCrypto);
        }
    }

    private class OnEnrollCancelListener
    implements CancellationSignal.OnCancelListener {
        private OnEnrollCancelListener() {
        }

        @Override
        public void onCancel() {
            FaceManager.this.cancelEnrollment();
        }
    }

    public static abstract class RemovalCallback {
        public void onRemovalError(Face face, int n, CharSequence charSequence) {
        }

        public void onRemovalSucceeded(Face face, int n) {
        }
    }

    public static abstract class SetFeatureCallback {
        public abstract void onCompleted(boolean var1, int var2);
    }

}

