/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.content.Context;
import android.net.Uri;
import android.os.IBinder;
import android.os.IIncidentAuthListener;
import android.os.IIncidentCompanion;
import android.os.IIncidentManager;
import android.os.IncidentReportArgs;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os._$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ;
import android.os._$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U;
import android.os._$$Lambda$IncidentManager$mfBTEJgu7VPkoPMTQdf1KC7oi5g;
import android.os._$$Lambda$IncidentManager$yGukxCMuLDmoRlrh5jGUmq5BOTk;
import android.util.Slog;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

@SystemApi
public class IncidentManager {
    public static final int FLAG_CONFIRMATION_DIALOG = 1;
    public static final int PRIVACY_POLICY_AUTO = 200;
    public static final int PRIVACY_POLICY_EXPLICIT = 100;
    public static final int PRIVACY_POLICY_LOCAL = 0;
    private static final String TAG = "IncidentManager";
    public static final String URI_AUTHORITY = "android.os.IncidentManager";
    public static final String URI_PARAM_CALLING_PACKAGE = "pkg";
    public static final String URI_PARAM_FLAGS = "flags";
    public static final String URI_PARAM_ID = "id";
    public static final String URI_PARAM_RECEIVER_CLASS = "receiver";
    public static final String URI_PARAM_REPORT_ID = "r";
    public static final String URI_PARAM_TIMESTAMP = "t";
    public static final String URI_PATH = "/pending";
    public static final String URI_SCHEME = "content";
    private IIncidentCompanion mCompanionService;
    private final Context mContext;
    private IIncidentManager mIncidentService;
    private Object mLock = new Object();

    public IncidentManager(Context context) {
        this.mContext = context;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private IIncidentCompanion getCompanionServiceLocked() throws RemoteException {
        Object object = this.mCompanionService;
        if (object != null) {
            return object;
        }
        synchronized (this) {
            if (this.mCompanionService != null) {
                return this.mCompanionService;
            }
            this.mCompanionService = IIncidentCompanion.Stub.asInterface(ServiceManager.getService("incidentcompanion"));
            if (this.mCompanionService == null) return this.mCompanionService;
            object = this.mCompanionService.asBinder();
            _$$Lambda$IncidentManager$mfBTEJgu7VPkoPMTQdf1KC7oi5g _$$Lambda$IncidentManager$mfBTEJgu7VPkoPMTQdf1KC7oi5g = new _$$Lambda$IncidentManager$mfBTEJgu7VPkoPMTQdf1KC7oi5g(this);
            object.linkToDeath(_$$Lambda$IncidentManager$mfBTEJgu7VPkoPMTQdf1KC7oi5g, 0);
            return this.mCompanionService;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private IIncidentManager getIIncidentManagerLocked() throws RemoteException {
        Object object = this.mIncidentService;
        if (object != null) {
            return object;
        }
        object = this.mLock;
        synchronized (object) {
            if (this.mIncidentService != null) {
                return this.mIncidentService;
            }
            this.mIncidentService = IIncidentManager.Stub.asInterface(ServiceManager.getService("incident"));
            if (this.mIncidentService == null) return this.mIncidentService;
            IBinder iBinder = this.mIncidentService.asBinder();
            _$$Lambda$IncidentManager$yGukxCMuLDmoRlrh5jGUmq5BOTk _$$Lambda$IncidentManager$yGukxCMuLDmoRlrh5jGUmq5BOTk = new _$$Lambda$IncidentManager$yGukxCMuLDmoRlrh5jGUmq5BOTk(this);
            iBinder.linkToDeath(_$$Lambda$IncidentManager$yGukxCMuLDmoRlrh5jGUmq5BOTk, 0);
            return this.mIncidentService;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void reportIncidentInternal(IncidentReportArgs incidentReportArgs) {
        try {
            IIncidentManager iIncidentManager = this.getIIncidentManagerLocked();
            if (iIncidentManager == null) {
                Slog.e(TAG, "reportIncident can't find incident binder service");
                return;
            }
            iIncidentManager.reportIncident(incidentReportArgs);
            return;
        }
        catch (RemoteException remoteException) {
            Slog.e(TAG, "reportIncident failed", remoteException);
        }
    }

    public void approveReport(Uri uri) {
        try {
            this.getCompanionServiceLocked().approveReport(uri.toString());
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public void cancelAuthorization(AuthListener authListener) {
        try {
            this.getCompanionServiceLocked().cancelAuthorization(authListener.mBinder);
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void deleteIncidentReports(Uri uri) {
        if (uri == null) {
            try {
                this.getCompanionServiceLocked().deleteAllIncidentReports(this.mContext.getPackageName());
                return;
            }
            catch (RemoteException remoteException) {
                throw new RuntimeException("System server or incidentd going down", remoteException);
            }
        }
        String string2 = uri.getQueryParameter(URI_PARAM_CALLING_PACKAGE);
        if (string2 != null) {
            CharSequence charSequence = uri.getQueryParameter(URI_PARAM_RECEIVER_CLASS);
            if (charSequence != null) {
                String string3 = uri.getQueryParameter(URI_PARAM_REPORT_ID);
                if (string3 != null) {
                    try {
                        this.getCompanionServiceLocked().deleteIncidentReports(string2, (String)charSequence, string3);
                        return;
                    }
                    catch (RemoteException remoteException) {
                        throw new RuntimeException("System server or incidentd going down", remoteException);
                    }
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Invalid URI: No r parameter. ");
                ((StringBuilder)charSequence).append(uri);
                throw new RuntimeException(((StringBuilder)charSequence).toString());
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid URI: No receiver parameter. ");
            ((StringBuilder)charSequence).append(uri);
            throw new RuntimeException(((StringBuilder)charSequence).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid URI: No pkg parameter. ");
        stringBuilder.append(uri);
        throw new RuntimeException(stringBuilder.toString());
    }

    public void denyReport(Uri uri) {
        try {
            this.getCompanionServiceLocked().denyReport(uri.toString());
            return;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public IncidentReport getIncidentReport(Uri parcelable) {
        String string2 = parcelable.getQueryParameter(URI_PARAM_REPORT_ID);
        if (string2 == null) {
            return null;
        }
        String string3 = parcelable.getQueryParameter(URI_PARAM_CALLING_PACKAGE);
        if (string3 != null) {
            CharSequence charSequence = parcelable.getQueryParameter(URI_PARAM_RECEIVER_CLASS);
            if (charSequence != null) {
                try {
                    parcelable = this.getCompanionServiceLocked().getIncidentReport(string3, (String)charSequence, string2);
                    return parcelable;
                }
                catch (RemoteException remoteException) {
                    throw new RuntimeException("System server or incidentd going down", remoteException);
                }
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid URI: No receiver parameter. ");
            ((StringBuilder)charSequence).append(parcelable);
            throw new RuntimeException(((StringBuilder)charSequence).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid URI: No pkg parameter. ");
        stringBuilder.append(parcelable);
        throw new RuntimeException(stringBuilder.toString());
    }

    public List<Uri> getIncidentReportList(String object) {
        int n;
        ArrayList<Uri> arrayList;
        try {
            object = this.getCompanionServiceLocked().getIncidentReportList(this.mContext.getPackageName(), (String)object);
            n = object.size();
            arrayList = new ArrayList<Uri>(n);
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException("System server or incidentd going down", remoteException);
        }
        for (int i = 0; i < n; ++i) {
            arrayList.add(Uri.parse((String)object.get(i)));
        }
        return arrayList;
    }

    public List<PendingReport> getPendingReports() {
        List<String> list;
        int n;
        ArrayList<PendingReport> arrayList;
        try {
            list = this.getCompanionServiceLocked().getPendingReports();
            n = list.size();
            arrayList = new ArrayList<PendingReport>(n);
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
        for (int i = 0; i < n; ++i) {
            arrayList.add(new PendingReport(Uri.parse(list.get(i))));
        }
        return arrayList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$getCompanionServiceLocked$1$IncidentManager() {
        Object object = this.mLock;
        synchronized (object) {
            this.mCompanionService = null;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public /* synthetic */ void lambda$getIIncidentManagerLocked$0$IncidentManager() {
        Object object = this.mLock;
        synchronized (object) {
            this.mIncidentService = null;
            return;
        }
    }

    public void reportIncident(IncidentReportArgs incidentReportArgs) {
        this.reportIncidentInternal(incidentReportArgs);
    }

    public void requestAuthorization(int n, String string2, int n2, AuthListener authListener) {
        this.requestAuthorization(n, string2, n2, this.mContext.getMainExecutor(), authListener);
    }

    public void requestAuthorization(int n, String object, int n2, Executor executor, AuthListener authListener) {
        try {
            if (authListener.mExecutor == null) {
                authListener.mExecutor = executor;
                this.getCompanionServiceLocked().authorizeReport(n, (String)object, null, null, n2, authListener.mBinder);
                return;
            }
            object = new RuntimeException("Do not reuse AuthListener objects when calling requestAuthorization");
            throw object;
        }
        catch (RemoteException remoteException) {
            throw new RuntimeException(remoteException);
        }
    }

    public static class AuthListener {
        IIncidentAuthListener.Stub mBinder = new IIncidentAuthListener.Stub(){

            public /* synthetic */ void lambda$onReportApproved$0$IncidentManager$AuthListener$1() {
                AuthListener.this.onReportApproved();
            }

            public /* synthetic */ void lambda$onReportDenied$1$IncidentManager$AuthListener$1() {
                AuthListener.this.onReportDenied();
            }

            @Override
            public void onReportApproved() {
                if (AuthListener.this.mExecutor != null) {
                    AuthListener.this.mExecutor.execute(new _$$Lambda$IncidentManager$AuthListener$1$lPkHJjJYlkckZZgbwSfNFtF2x_U(this));
                } else {
                    AuthListener.this.onReportApproved();
                }
            }

            @Override
            public void onReportDenied() {
                if (AuthListener.this.mExecutor != null) {
                    AuthListener.this.mExecutor.execute(new _$$Lambda$IncidentManager$AuthListener$1$VoPbrfU3RKoeruCLRzIQ8yeLsyQ(this));
                } else {
                    AuthListener.this.onReportDenied();
                }
            }
        };
        Executor mExecutor;

        public void onReportApproved() {
        }

        public void onReportDenied() {
        }

    }

    @SystemApi
    public static class IncidentReport
    implements Parcelable,
    Closeable {
        public static final Parcelable.Creator<IncidentReport> CREATOR = new Parcelable.Creator(){

            public IncidentReport createFromParcel(Parcel parcel) {
                return new IncidentReport(parcel);
            }

            public IncidentReport[] newArray(int n) {
                return new IncidentReport[n];
            }
        };
        private ParcelFileDescriptor mFileDescriptor;
        private final int mPrivacyPolicy;
        private final long mTimestampNs;

        public IncidentReport(Parcel parcel) {
            this.mTimestampNs = parcel.readLong();
            this.mPrivacyPolicy = parcel.readInt();
            this.mFileDescriptor = parcel.readInt() != 0 ? ParcelFileDescriptor.CREATOR.createFromParcel(parcel) : null;
        }

        @Override
        public void close() {
            try {
                if (this.mFileDescriptor != null) {
                    this.mFileDescriptor.close();
                    this.mFileDescriptor = null;
                }
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }

        @Override
        public int describeContents() {
            int n = this.mFileDescriptor != null ? 1 : 0;
            return n;
        }

        public InputStream getInputStream() throws IOException {
            ParcelFileDescriptor parcelFileDescriptor = this.mFileDescriptor;
            if (parcelFileDescriptor == null) {
                return null;
            }
            return new ParcelFileDescriptor.AutoCloseInputStream(parcelFileDescriptor);
        }

        public long getPrivacyPolicy() {
            return this.mPrivacyPolicy;
        }

        public long getTimestamp() {
            return this.mTimestampNs / 1000000L;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.mTimestampNs);
            parcel.writeInt(this.mPrivacyPolicy);
            if (this.mFileDescriptor != null) {
                parcel.writeInt(1);
                this.mFileDescriptor.writeToParcel(parcel, n);
            } else {
                parcel.writeInt(0);
            }
        }

    }

    @SystemApi
    public static class PendingReport {
        private final int mFlags;
        private final String mRequestingPackage;
        private final long mTimestamp;
        private final Uri mUri;

        public PendingReport(Uri uri) {
            int n;
            try {}
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Invalid URI: No flags parameter. ");
                stringBuilder.append(uri);
                throw new RuntimeException(stringBuilder.toString());
            }
            this.mFlags = n = Integer.parseInt(uri.getQueryParameter(IncidentManager.URI_PARAM_FLAGS));
            CharSequence charSequence = uri.getQueryParameter(IncidentManager.URI_PARAM_CALLING_PACKAGE);
            if (charSequence != null) {
                long l;
                this.mRequestingPackage = charSequence;
                try {}
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid URI: No t parameter. ");
                    stringBuilder.append(uri);
                    throw new RuntimeException(stringBuilder.toString());
                }
                this.mTimestamp = l = Long.parseLong(uri.getQueryParameter(IncidentManager.URI_PARAM_TIMESTAMP));
                this.mUri = uri;
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Invalid URI: No pkg parameter. ");
            ((StringBuilder)charSequence).append(uri);
            throw new RuntimeException(((StringBuilder)charSequence).toString());
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!(object instanceof PendingReport)) {
                return false;
            }
            object = (PendingReport)object;
            if (!this.mUri.equals(((PendingReport)object).mUri) || this.mFlags != ((PendingReport)object).mFlags || !this.mRequestingPackage.equals(((PendingReport)object).mRequestingPackage) || this.mTimestamp != ((PendingReport)object).mTimestamp) {
                bl = false;
            }
            return bl;
        }

        public int getFlags() {
            return this.mFlags;
        }

        public String getRequestingPackage() {
            return this.mRequestingPackage;
        }

        public long getTimestamp() {
            return this.mTimestamp;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PendingReport(");
            stringBuilder.append(this.getUri().toString());
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface PrivacyPolicy {
    }

}

