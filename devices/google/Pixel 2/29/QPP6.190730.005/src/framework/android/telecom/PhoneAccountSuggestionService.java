/*
 * Decompiled with CFR 0.145.
 */
package android.telecom;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.telecom.Log;
import android.telecom.PhoneAccountSuggestion;
import com.android.internal.telecom.IPhoneAccountSuggestionCallback;
import com.android.internal.telecom.IPhoneAccountSuggestionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SystemApi
public class PhoneAccountSuggestionService
extends Service {
    public static final String SERVICE_INTERFACE = "android.telecom.PhoneAccountSuggestionService";
    private final Map<String, IPhoneAccountSuggestionCallback> mCallbackMap = new HashMap<String, IPhoneAccountSuggestionCallback>();
    private IPhoneAccountSuggestionService mInterface = new IPhoneAccountSuggestionService.Stub(){

        @Override
        public void onAccountSuggestionRequest(IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback, String string2) {
            PhoneAccountSuggestionService.this.mCallbackMap.put(string2, iPhoneAccountSuggestionCallback);
            PhoneAccountSuggestionService.this.onAccountSuggestionRequest(string2);
        }
    };

    public void onAccountSuggestionRequest(String string2) {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this.mInterface.asBinder();
    }

    public final void suggestPhoneAccounts(String string2, List<PhoneAccountSuggestion> list) {
        IPhoneAccountSuggestionCallback iPhoneAccountSuggestionCallback = this.mCallbackMap.remove(string2);
        if (iPhoneAccountSuggestionCallback == null) {
            Log.w(this, "No suggestions requested for the number %s", Log.pii(string2));
            return;
        }
        try {
            iPhoneAccountSuggestionCallback.suggestPhoneAccounts(string2, list);
        }
        catch (RemoteException remoteException) {
            Log.w(this, "Remote exception calling suggestPhoneAccounts", new Object[0]);
        }
    }

}

