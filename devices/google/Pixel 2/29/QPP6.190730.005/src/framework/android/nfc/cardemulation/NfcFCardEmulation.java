/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.cardemulation;

import android.app.Activity;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.Context;
import android.nfc.INfcFCardEmulation;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.NfcFServiceInfo;
import android.os.RemoteException;
import android.util.Log;
import java.util.HashMap;
import java.util.List;

public final class NfcFCardEmulation {
    static final String TAG = "NfcFCardEmulation";
    static HashMap<Context, NfcFCardEmulation> sCardEmus;
    static boolean sIsInitialized;
    static INfcFCardEmulation sService;
    final Context mContext;

    static {
        sIsInitialized = false;
        sCardEmus = new HashMap();
    }

    private NfcFCardEmulation(Context context, INfcFCardEmulation iNfcFCardEmulation) {
        this.mContext = context.getApplicationContext();
        sService = iNfcFCardEmulation;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static NfcFCardEmulation getInstance(NfcAdapter object) {
        synchronized (NfcFCardEmulation.class) {
            Throwable throwable2;
            if (object != null) {
                block13 : {
                    Object object2;
                    block14 : {
                        try {
                            Context context = ((NfcAdapter)object).getContext();
                            if (context == null) break block13;
                            if (!sIsInitialized) {
                                object2 = ActivityThread.getPackageManager();
                                if (object2 == null) {
                                    Log.e(TAG, "Cannot get PackageManager");
                                    object = new UnsupportedOperationException();
                                    throw object;
                                }
                                try {
                                    boolean bl = object2.hasSystemFeature("android.hardware.nfc.hcef", 0);
                                    if (!bl) {
                                        Log.e(TAG, "This device does not support NFC-F card emulation");
                                        object = new UnsupportedOperationException();
                                        throw object;
                                    }
                                    sIsInitialized = true;
                                }
                                catch (RemoteException remoteException) {
                                    Log.e(TAG, "PackageManager query failed.");
                                    UnsupportedOperationException unsupportedOperationException = new UnsupportedOperationException();
                                    throw unsupportedOperationException;
                                }
                            }
                            NfcFCardEmulation nfcFCardEmulation = sCardEmus.get(context);
                            object2 = nfcFCardEmulation;
                            if (nfcFCardEmulation != null) break block14;
                            if ((object = ((NfcAdapter)object).getNfcFCardEmulationService()) == null) {
                                Log.e(TAG, "This device does not implement the INfcFCardEmulation interface.");
                                object = new UnsupportedOperationException();
                                throw object;
                            }
                            object2 = new NfcFCardEmulation(context, (INfcFCardEmulation)object);
                            sCardEmus.put(context, (NfcFCardEmulation)object2);
                        }
                        catch (Throwable throwable2) {}
                    }
                    return object2;
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

    public static boolean isValidNfcid2(String string2) {
        if (string2 == null) {
            return false;
        }
        if (string2.length() != 16) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NFCID2 ");
            stringBuilder.append(string2);
            stringBuilder.append(" is not a valid NFCID2.");
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        if (!string2.toUpperCase().startsWith("02FE")) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NFCID2 ");
            stringBuilder.append(string2);
            stringBuilder.append(" is not a valid NFCID2.");
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        try {
            Long.parseLong(string2, 16);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("NFCID2 ");
            stringBuilder.append(string2);
            stringBuilder.append(" is not a valid NFCID2.");
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
    }

    public static boolean isValidSystemCode(String string2) {
        if (string2 == null) {
            return false;
        }
        if (string2.length() != 4) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("System Code ");
            stringBuilder.append(string2);
            stringBuilder.append(" is not a valid System Code.");
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        if (string2.startsWith("4") && !string2.toUpperCase().endsWith("FF")) {
            try {
                Integer.parseInt(string2, 16);
                return true;
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("System Code ");
                stringBuilder.append(string2);
                stringBuilder.append(" is not a valid System Code.");
                Log.e(TAG, stringBuilder.toString());
                return false;
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("System Code ");
        stringBuilder.append(string2);
        stringBuilder.append(" is not a valid System Code.");
        Log.e(TAG, stringBuilder.toString());
        return false;
    }

    public boolean disableService(Activity activity) throws RuntimeException {
        if (activity != null) {
            if (activity.isResumed()) {
                try {
                    boolean bl = sService.disableNfcFForegroundService();
                    return bl;
                }
                catch (RemoteException remoteException) {
                    this.recoverService();
                    INfcFCardEmulation iNfcFCardEmulation = sService;
                    if (iNfcFCardEmulation == null) {
                        Log.e(TAG, "Failed to recover CardEmulationService.");
                        return false;
                    }
                    try {
                        boolean bl = iNfcFCardEmulation.disableNfcFForegroundService();
                        return bl;
                    }
                    catch (RemoteException remoteException2) {
                        Log.e(TAG, "Failed to reach CardEmulationService.");
                        remoteException2.rethrowAsRuntimeException();
                        return false;
                    }
                }
            }
            throw new IllegalArgumentException("Activity must be resumed.");
        }
        throw new NullPointerException("activity is null");
    }

    public boolean enableService(Activity activity, ComponentName componentName) throws RuntimeException {
        if (activity != null && componentName != null) {
            if (activity.isResumed()) {
                try {
                    boolean bl = sService.enableNfcFForegroundService(componentName);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    this.recoverService();
                    INfcFCardEmulation iNfcFCardEmulation = sService;
                    if (iNfcFCardEmulation == null) {
                        Log.e(TAG, "Failed to recover CardEmulationService.");
                        return false;
                    }
                    try {
                        boolean bl = iNfcFCardEmulation.enableNfcFForegroundService(componentName);
                        return bl;
                    }
                    catch (RemoteException remoteException2) {
                        Log.e(TAG, "Failed to reach CardEmulationService.");
                        remoteException2.rethrowAsRuntimeException();
                        return false;
                    }
                }
            }
            throw new IllegalArgumentException("Activity must be resumed.");
        }
        throw new NullPointerException("activity or service is null");
    }

    public int getMaxNumOfRegisterableSystemCodes() {
        try {
            int n = sService.getMaxNumOfRegisterableSystemCodes();
            return n;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcFCardEmulation iNfcFCardEmulation = sService;
            if (iNfcFCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return -1;
            }
            try {
                int n = iNfcFCardEmulation.getMaxNumOfRegisterableSystemCodes();
                return n;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return -1;
            }
        }
    }

    public List<NfcFServiceInfo> getNfcFServices() {
        try {
            List<NfcFServiceInfo> list = sService.getNfcFServices(this.mContext.getUserId());
            return list;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            Object object = sService;
            if (object == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return null;
            }
            try {
                object = object.getNfcFServices(this.mContext.getUserId());
                return object;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return null;
            }
        }
    }

    public String getNfcid2ForService(ComponentName object) throws RuntimeException {
        if (object != null) {
            try {
                String string2 = sService.getNfcid2ForService(this.mContext.getUserId(), (ComponentName)object);
                return string2;
            }
            catch (RemoteException remoteException) {
                this.recoverService();
                INfcFCardEmulation iNfcFCardEmulation = sService;
                if (iNfcFCardEmulation == null) {
                    Log.e(TAG, "Failed to recover CardEmulationService.");
                    return null;
                }
                try {
                    object = iNfcFCardEmulation.getNfcid2ForService(this.mContext.getUserId(), (ComponentName)object);
                    return object;
                }
                catch (RemoteException remoteException2) {
                    Log.e(TAG, "Failed to reach CardEmulationService.");
                    remoteException2.rethrowAsRuntimeException();
                    return null;
                }
            }
        }
        throw new NullPointerException("service is null");
    }

    public String getSystemCodeForService(ComponentName object) throws RuntimeException {
        if (object != null) {
            try {
                String string2 = sService.getSystemCodeForService(this.mContext.getUserId(), (ComponentName)object);
                return string2;
            }
            catch (RemoteException remoteException) {
                this.recoverService();
                INfcFCardEmulation iNfcFCardEmulation = sService;
                if (iNfcFCardEmulation == null) {
                    Log.e(TAG, "Failed to recover CardEmulationService.");
                    return null;
                }
                try {
                    object = iNfcFCardEmulation.getSystemCodeForService(this.mContext.getUserId(), (ComponentName)object);
                    return object;
                }
                catch (RemoteException remoteException2) {
                    Log.e(TAG, "Failed to reach CardEmulationService.");
                    remoteException2.rethrowAsRuntimeException();
                    return null;
                }
            }
        }
        throw new NullPointerException("service is null");
    }

    void recoverService() {
        sService = NfcAdapter.getDefaultAdapter(this.mContext).getNfcFCardEmulationService();
    }

    public boolean registerSystemCodeForService(ComponentName componentName, String string2) throws RuntimeException {
        if (componentName != null && string2 != null) {
            try {
                boolean bl = sService.registerSystemCodeForService(this.mContext.getUserId(), componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                this.recoverService();
                INfcFCardEmulation iNfcFCardEmulation = sService;
                if (iNfcFCardEmulation == null) {
                    Log.e(TAG, "Failed to recover CardEmulationService.");
                    return false;
                }
                try {
                    boolean bl = iNfcFCardEmulation.registerSystemCodeForService(this.mContext.getUserId(), componentName, string2);
                    return bl;
                }
                catch (RemoteException remoteException2) {
                    Log.e(TAG, "Failed to reach CardEmulationService.");
                    remoteException2.rethrowAsRuntimeException();
                    return false;
                }
            }
        }
        throw new NullPointerException("service or systemCode is null");
    }

    public boolean setNfcid2ForService(ComponentName componentName, String string2) throws RuntimeException {
        if (componentName != null && string2 != null) {
            try {
                boolean bl = sService.setNfcid2ForService(this.mContext.getUserId(), componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException) {
                this.recoverService();
                INfcFCardEmulation iNfcFCardEmulation = sService;
                if (iNfcFCardEmulation == null) {
                    Log.e(TAG, "Failed to recover CardEmulationService.");
                    return false;
                }
                try {
                    boolean bl = iNfcFCardEmulation.setNfcid2ForService(this.mContext.getUserId(), componentName, string2);
                    return bl;
                }
                catch (RemoteException remoteException2) {
                    Log.e(TAG, "Failed to reach CardEmulationService.");
                    remoteException2.rethrowAsRuntimeException();
                    return false;
                }
            }
        }
        throw new NullPointerException("service or nfcid2 is null");
    }

    public boolean unregisterSystemCodeForService(ComponentName componentName) throws RuntimeException {
        if (componentName != null) {
            try {
                boolean bl = sService.removeSystemCodeForService(this.mContext.getUserId(), componentName);
                return bl;
            }
            catch (RemoteException remoteException) {
                this.recoverService();
                INfcFCardEmulation iNfcFCardEmulation = sService;
                if (iNfcFCardEmulation == null) {
                    Log.e(TAG, "Failed to recover CardEmulationService.");
                    return false;
                }
                try {
                    boolean bl = iNfcFCardEmulation.removeSystemCodeForService(this.mContext.getUserId(), componentName);
                    return bl;
                }
                catch (RemoteException remoteException2) {
                    Log.e(TAG, "Failed to reach CardEmulationService.");
                    remoteException2.rethrowAsRuntimeException();
                    return false;
                }
            }
        }
        throw new NullPointerException("service is null");
    }
}

