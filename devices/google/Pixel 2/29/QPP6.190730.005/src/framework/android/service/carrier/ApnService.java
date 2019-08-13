/*
 * Decompiled with CFR 0.145.
 */
package android.service.carrier;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.android.internal.telephony.IApnSourceService;
import java.util.List;

@SystemApi
public abstract class ApnService
extends Service {
    private static final String LOG_TAG = "ApnService";
    private final IApnSourceService.Stub mBinder = new IApnSourceService.Stub(){

        @Override
        public ContentValues[] getApns(int n) {
            try {
                ContentValues[] arrcontentValues = ApnService.this.onRestoreApns(n);
                arrcontentValues = arrcontentValues.toArray(new ContentValues[arrcontentValues.size()]);
                return arrcontentValues;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error in getApns for subId=");
                stringBuilder.append(n);
                stringBuilder.append(": ");
                stringBuilder.append(exception.getMessage());
                Log.e(ApnService.LOG_TAG, stringBuilder.toString(), exception);
                return null;
            }
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public abstract List<ContentValues> onRestoreApns(int var1);

}

