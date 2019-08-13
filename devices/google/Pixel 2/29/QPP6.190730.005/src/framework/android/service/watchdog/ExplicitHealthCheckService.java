/*
 * Decompiled with CFR 0.145.
 */
package android.service.watchdog;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.watchdog.IExplicitHealthCheckService;
import android.service.watchdog._$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$5Rv9E4_Jc0y0GMGqI_g_82qtYpg;
import android.service.watchdog._$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$EnMyVE8D3ameNypg4gr2IMP7BCo;
import android.service.watchdog._$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$Gn8La3kwBvbjLET_Nqtstvz2RZg;
import android.service.watchdog._$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$pC_jIpmynGf4FhRLSRCGbJwUkGE;
import android.service.watchdog._$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$yycuCTr7mDJWrqK_xpXb1sTmkyA;
import android.service.watchdog._$$Lambda$ExplicitHealthCheckService$ulagkAZ2bM7_LW9T7PSTxSLQfBE;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@SystemApi
public abstract class ExplicitHealthCheckService
extends Service {
    public static final String BIND_PERMISSION = "android.permission.BIND_EXPLICIT_HEALTH_CHECK_SERVICE";
    public static final String EXTRA_HEALTH_CHECK_PASSED_PACKAGE = "android.service.watchdog.extra.health_check_passed_package";
    public static final String EXTRA_REQUESTED_PACKAGES = "android.service.watchdog.extra.requested_packages";
    public static final String EXTRA_SUPPORTED_PACKAGES = "android.service.watchdog.extra.supported_packages";
    public static final String SERVICE_INTERFACE = "android.service.watchdog.ExplicitHealthCheckService";
    private static final String TAG = "ExplicitHealthCheckService";
    private RemoteCallback mCallback;
    private final Handler mHandler = new Handler(Looper.getMainLooper(), null, true);
    private final ExplicitHealthCheckServiceWrapper mWrapper = new ExplicitHealthCheckServiceWrapper();

    public /* synthetic */ void lambda$notifyHealthCheckPassed$0$ExplicitHealthCheckService(String string2) {
        if (this.mCallback != null) {
            Objects.requireNonNull(string2, "Package passing explicit health check must be non-null");
            Bundle bundle = new Bundle();
            bundle.putString(EXTRA_HEALTH_CHECK_PASSED_PACKAGE, string2);
            this.mCallback.sendResult(bundle);
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("System missed explicit health check result for ");
            stringBuilder.append(string2);
            Log.wtf(TAG, stringBuilder.toString());
        }
    }

    public final void notifyHealthCheckPassed(String string2) {
        this.mHandler.post(new _$$Lambda$ExplicitHealthCheckService$ulagkAZ2bM7_LW9T7PSTxSLQfBE(this, string2));
    }

    @Override
    public final IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    public abstract void onCancelHealthCheck(String var1);

    public abstract List<String> onGetRequestedPackages();

    public abstract List<PackageConfig> onGetSupportedPackages();

    public abstract void onRequestHealthCheck(String var1);

    private class ExplicitHealthCheckServiceWrapper
    extends IExplicitHealthCheckService.Stub {
        private ExplicitHealthCheckServiceWrapper() {
        }

        @Override
        public void cancel(String string2) throws RemoteException {
            ExplicitHealthCheckService.this.mHandler.post(new _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$pC_jIpmynGf4FhRLSRCGbJwUkGE(this, string2));
        }

        @Override
        public void getRequestedPackages(RemoteCallback remoteCallback) throws RemoteException {
            ExplicitHealthCheckService.this.mHandler.post(new _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$yycuCTr7mDJWrqK_xpXb1sTmkyA(this, remoteCallback));
        }

        @Override
        public void getSupportedPackages(RemoteCallback remoteCallback) throws RemoteException {
            ExplicitHealthCheckService.this.mHandler.post(new _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$5Rv9E4_Jc0y0GMGqI_g_82qtYpg(this, remoteCallback));
        }

        public /* synthetic */ void lambda$cancel$2$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper(String string2) {
            ExplicitHealthCheckService.this.onCancelHealthCheck(string2);
        }

        public /* synthetic */ void lambda$getRequestedPackages$4$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper(RemoteCallback remoteCallback) {
            List<String> list = ExplicitHealthCheckService.this.onGetRequestedPackages();
            Objects.requireNonNull(list, "Requested  package list must be non-null");
            Bundle bundle = new Bundle();
            bundle.putStringArrayList(ExplicitHealthCheckService.EXTRA_REQUESTED_PACKAGES, new ArrayList<String>(list));
            remoteCallback.sendResult(bundle);
        }

        public /* synthetic */ void lambda$getSupportedPackages$3$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper(RemoteCallback remoteCallback) {
            List<PackageConfig> list = ExplicitHealthCheckService.this.onGetSupportedPackages();
            Objects.requireNonNull(list, "Supported package list must be non-null");
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(ExplicitHealthCheckService.EXTRA_SUPPORTED_PACKAGES, new ArrayList<PackageConfig>(list));
            remoteCallback.sendResult(bundle);
        }

        public /* synthetic */ void lambda$request$1$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper(String string2) {
            ExplicitHealthCheckService.this.onRequestHealthCheck(string2);
        }

        public /* synthetic */ void lambda$setCallback$0$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper(RemoteCallback remoteCallback) {
            ExplicitHealthCheckService.this.mCallback = remoteCallback;
        }

        @Override
        public void request(String string2) throws RemoteException {
            ExplicitHealthCheckService.this.mHandler.post(new _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$Gn8La3kwBvbjLET_Nqtstvz2RZg(this, string2));
        }

        @Override
        public void setCallback(RemoteCallback remoteCallback) throws RemoteException {
            ExplicitHealthCheckService.this.mHandler.post(new _$$Lambda$ExplicitHealthCheckService$ExplicitHealthCheckServiceWrapper$EnMyVE8D3ameNypg4gr2IMP7BCo(this, remoteCallback));
        }
    }

    @SystemApi
    public static final class PackageConfig
    implements Parcelable {
        public static final Parcelable.Creator<PackageConfig> CREATOR;
        private static final long DEFAULT_HEALTH_CHECK_TIMEOUT_MILLIS;
        private final long mHealthCheckTimeoutMillis;
        private final String mPackageName;

        static {
            DEFAULT_HEALTH_CHECK_TIMEOUT_MILLIS = TimeUnit.HOURS.toMillis(1L);
            CREATOR = new Parcelable.Creator<PackageConfig>(){

                @Override
                public PackageConfig createFromParcel(Parcel parcel) {
                    return new PackageConfig(parcel);
                }

                public PackageConfig[] newArray(int n) {
                    return new PackageConfig[n];
                }
            };
        }

        private PackageConfig(Parcel parcel) {
            this.mPackageName = parcel.readString();
            this.mHealthCheckTimeoutMillis = parcel.readLong();
        }

        public PackageConfig(String string2, long l) {
            this.mPackageName = Preconditions.checkNotNull(string2);
            this.mHealthCheckTimeoutMillis = l == 0L ? DEFAULT_HEALTH_CHECK_TIMEOUT_MILLIS : Preconditions.checkArgumentNonnegative(l);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (object == this) {
                return true;
            }
            if (!(object instanceof PackageConfig)) {
                return false;
            }
            if (!Objects.equals(((PackageConfig)(object = (PackageConfig)object)).getHealthCheckTimeoutMillis(), this.mHealthCheckTimeoutMillis) || !Objects.equals(((PackageConfig)object).getPackageName(), this.mPackageName)) {
                bl = false;
            }
            return bl;
        }

        public long getHealthCheckTimeoutMillis() {
            return this.mHealthCheckTimeoutMillis;
        }

        public String getPackageName() {
            return this.mPackageName;
        }

        public int hashCode() {
            return Objects.hash(this.mPackageName, this.mHealthCheckTimeoutMillis);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("PackageConfig{");
            stringBuilder.append(this.mPackageName);
            stringBuilder.append(", ");
            stringBuilder.append(this.mHealthCheckTimeoutMillis);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mPackageName);
            parcel.writeLong(this.mHealthCheckTimeoutMillis);
        }

    }

}

