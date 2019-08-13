/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.dta;

import android.content.Context;
import android.nfc.INfcDta;
import android.nfc.NfcAdapter;
import android.os.RemoteException;
import android.util.Log;
import java.util.HashMap;

public final class NfcDta {
    private static final String TAG = "NfcDta";
    private static HashMap<Context, NfcDta> sNfcDtas = new HashMap();
    private static INfcDta sService;
    private final Context mContext;

    private NfcDta(Context context, INfcDta iNfcDta) {
        this.mContext = context.getApplicationContext();
        sService = iNfcDta;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static NfcDta getInstance(NfcAdapter object) {
        synchronized (NfcDta.class) {
            Throwable throwable2;
            if (object != null) {
                block7 : {
                    NfcDta nfcDta;
                    block8 : {
                        try {
                            NfcDta nfcDta2;
                            Context context = ((NfcAdapter)object).getContext();
                            if (context == null) break block7;
                            nfcDta = nfcDta2 = sNfcDtas.get(context);
                            if (nfcDta2 != null) break block8;
                            if ((object = ((NfcAdapter)object).getNfcDtaInterface()) == null) {
                                Log.e(TAG, "This device does not implement the INfcDta interface.");
                                object = new UnsupportedOperationException();
                                throw object;
                            }
                            nfcDta = new NfcDta(context, (INfcDta)object);
                            sNfcDtas.put(context, nfcDta);
                        }
                        catch (Throwable throwable2) {}
                    }
                    return nfcDta;
                }
                Log.e(TAG, "NfcAdapter context is null.");
                object = new UnsupportedOperationException();
                throw object;
            } else {
                object = new NullPointerException("NfcAdapter is null");
                throw object;
            }
            throw throwable2;
        }
    }

    public boolean disableClient() {
        try {
            sService.disableClient();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean disableDta() {
        try {
            sService.disableDta();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean disableServer() {
        try {
            sService.disableServer();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean enableClient(String string2, int n, int n2, int n3) {
        try {
            boolean bl = sService.enableClient(string2, n, n2, n3);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean enableDta() {
        try {
            sService.enableDta();
            return true;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean enableServer(String string2, int n, int n2, int n3, int n4) {
        try {
            boolean bl = sService.enableServer(string2, n, n2, n3, n4);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }

    public boolean registerMessageService(String string2) {
        try {
            boolean bl = sService.registerMessageService(string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            return false;
        }
    }
}

