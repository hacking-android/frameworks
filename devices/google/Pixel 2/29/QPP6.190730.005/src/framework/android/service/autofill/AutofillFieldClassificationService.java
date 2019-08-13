/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.service.autofill.-$
 *  android.service.autofill.-$$Lambda
 *  android.service.autofill.-$$Lambda$AutofillFieldClassificationService
 *  android.service.autofill.-$$Lambda$AutofillFieldClassificationService$AutofillFieldClassificationServiceWrapper
 *  android.service.autofill.-$$Lambda$AutofillFieldClassificationService$AutofillFieldClassificationServiceWrapper$mUalgFt87R5lup2LhB9vW49Xixs
 */
package android.service.autofill;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteCallback;
import android.os.RemoteException;
import android.service.autofill.-$;
import android.service.autofill.IAutofillFieldClassificationService;
import android.service.autofill._$$Lambda$AutofillFieldClassificationService$AutofillFieldClassificationServiceWrapper$mUalgFt87R5lup2LhB9vW49Xixs;
import android.util.Log;
import android.view.autofill.AutofillValue;
import com.android.internal.util.function.pooled.PooledLambda;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SystemApi
public abstract class AutofillFieldClassificationService
extends Service {
    public static final String EXTRA_SCORES = "scores";
    public static final String REQUIRED_ALGORITHM_EDIT_DISTANCE = "EDIT_DISTANCE";
    public static final String REQUIRED_ALGORITHM_EXACT_MATCH = "EXACT_MATCH";
    public static final String SERVICE_INTERFACE = "android.service.autofill.AutofillFieldClassificationService";
    public static final String SERVICE_META_DATA_KEY_AVAILABLE_ALGORITHMS = "android.autofill.field_classification.available_algorithms";
    public static final String SERVICE_META_DATA_KEY_DEFAULT_ALGORITHM = "android.autofill.field_classification.default_algorithm";
    private static final String TAG = "AutofillFieldClassificationService";
    private final Handler mHandler = new Handler(Looper.getMainLooper(), null, true);
    private AutofillFieldClassificationServiceWrapper mWrapper;

    private void calculateScores(RemoteCallback remoteCallback, List<AutofillValue> arrf, String[] arrstring, String[] arrstring2, String string2, Bundle bundle, Map map, Map map2) {
        Bundle bundle2 = new Bundle();
        if ((arrf = this.onCalculateScores((List<AutofillValue>)arrf, Arrays.asList(arrstring), Arrays.asList(arrstring2), string2, bundle, map, map2)) != null) {
            bundle2.putParcelable(EXTRA_SCORES, new Scores(arrf));
        }
        remoteCallback.sendResult(bundle2);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mWrapper;
    }

    @SystemApi
    public float[][] onCalculateScores(List<AutofillValue> object, List<String> list, List<String> list2, String string2, Bundle bundle, Map map, Map map2) {
        object = new StringBuilder();
        ((StringBuilder)object).append("service implementation (");
        ((StringBuilder)object).append(this.getClass());
        ((StringBuilder)object).append(" does not implement onCalculateScore()");
        Log.e(TAG, ((StringBuilder)object).toString());
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mWrapper = new AutofillFieldClassificationServiceWrapper();
    }

    @SystemApi
    @Deprecated
    public float[][] onGetScores(String charSequence, Bundle bundle, List<AutofillValue> list, List<String> list2) {
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("service implementation (");
        ((StringBuilder)charSequence).append(this.getClass());
        ((StringBuilder)charSequence).append(" does not implement onGetScores()");
        Log.e(TAG, ((StringBuilder)charSequence).toString());
        return null;
    }

    private final class AutofillFieldClassificationServiceWrapper
    extends IAutofillFieldClassificationService.Stub {
        private AutofillFieldClassificationServiceWrapper() {
        }

        static /* synthetic */ void lambda$calculateScores$0(Object object, RemoteCallback remoteCallback, List list, String[] arrstring, String[] arrstring2, String string2, Bundle bundle, Map map, Map map2) {
            ((AutofillFieldClassificationService)object).calculateScores(remoteCallback, list, arrstring, arrstring2, string2, bundle, map, map2);
        }

        @Override
        public void calculateScores(RemoteCallback remoteCallback, List<AutofillValue> list, String[] arrstring, String[] arrstring2, String string2, Bundle bundle, Map map, Map map2) throws RemoteException {
            AutofillFieldClassificationService.this.mHandler.sendMessage(PooledLambda.obtainMessage(_$$Lambda$AutofillFieldClassificationService$AutofillFieldClassificationServiceWrapper$mUalgFt87R5lup2LhB9vW49Xixs.INSTANCE, AutofillFieldClassificationService.this, remoteCallback, list, arrstring, arrstring2, string2, bundle, map, map2));
        }
    }

    public static final class Scores
    implements Parcelable {
        public static final Parcelable.Creator<Scores> CREATOR = new Parcelable.Creator<Scores>(){

            @Override
            public Scores createFromParcel(Parcel parcel) {
                return new Scores(parcel);
            }

            public Scores[] newArray(int n) {
                return new Scores[n];
            }
        };
        public final float[][] scores;

        private Scores(Parcel parcel) {
            int n = parcel.readInt();
            int n2 = parcel.readInt();
            this.scores = new float[n][n2];
            for (int i = 0; i < n; ++i) {
                for (int j = 0; j < n2; ++j) {
                    this.scores[i][j] = parcel.readFloat();
                }
            }
        }

        private Scores(float[][] arrf) {
            this.scores = arrf;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public String toString() {
            Object object = this.scores;
            int n = ((float[][])object).length;
            int n2 = 0;
            if (n > 0) {
                n2 = object[0].length;
            }
            object = new StringBuilder("Scores [");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append("x");
            ((StringBuilder)object).append(n2);
            object = ((StringBuilder)object).append("] ");
            for (n2 = 0; n2 < n; ++n2) {
                ((StringBuilder)object).append(n2);
                ((StringBuilder)object).append(": ");
                ((StringBuilder)object).append(Arrays.toString(this.scores[n2]));
                ((StringBuilder)object).append(' ');
            }
            return ((StringBuilder)object).toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            float[][] arrf = this.scores;
            int n2 = arrf.length;
            int n3 = arrf[0].length;
            parcel.writeInt(n2);
            parcel.writeInt(n3);
            for (n = 0; n < n2; ++n) {
                for (int i = 0; i < n3; ++i) {
                    parcel.writeFloat(this.scores[n][i]);
                }
            }
        }

    }

}

