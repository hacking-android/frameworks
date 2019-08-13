/*
 * Decompiled with CFR 0.145.
 */
package android.nfc.cardemulation;

import android.app.Activity;
import android.app.ActivityThread;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.nfc.INfcCardEmulation;
import android.nfc.NfcAdapter;
import android.nfc.cardemulation.AidGroup;
import android.nfc.cardemulation.ApduServiceInfo;
import android.os.RemoteException;
import android.provider.Settings;
import android.util.Log;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CardEmulation {
    public static final String ACTION_CHANGE_DEFAULT = "android.nfc.cardemulation.action.ACTION_CHANGE_DEFAULT";
    private static final Pattern AID_PATTERN = Pattern.compile("[0-9A-Fa-f]{10,32}\\*?\\#?");
    public static final String CATEGORY_OTHER = "other";
    public static final String CATEGORY_PAYMENT = "payment";
    public static final String EXTRA_CATEGORY = "category";
    public static final String EXTRA_SERVICE_COMPONENT = "component";
    public static final int SELECTION_MODE_ALWAYS_ASK = 1;
    public static final int SELECTION_MODE_ASK_IF_CONFLICT = 2;
    public static final int SELECTION_MODE_PREFER_DEFAULT = 0;
    static final String TAG = "CardEmulation";
    static HashMap<Context, CardEmulation> sCardEmus;
    static boolean sIsInitialized;
    static INfcCardEmulation sService;
    final Context mContext;

    static {
        sIsInitialized = false;
        sCardEmus = new HashMap();
    }

    private CardEmulation(Context context, INfcCardEmulation iNfcCardEmulation) {
        this.mContext = context.getApplicationContext();
        sService = iNfcCardEmulation;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static CardEmulation getInstance(NfcAdapter object) {
        synchronized (CardEmulation.class) {
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
                                    boolean bl = object2.hasSystemFeature("android.hardware.nfc.hce", 0);
                                    if (!bl) {
                                        Log.e(TAG, "This device does not support card emulation");
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
                            CardEmulation cardEmulation = sCardEmus.get(context);
                            object2 = cardEmulation;
                            if (cardEmulation != null) break block14;
                            if ((object = ((NfcAdapter)object).getCardEmulationService()) == null) {
                                Log.e(TAG, "This device does not implement the INfcCardEmulation interface.");
                                object = new UnsupportedOperationException();
                                throw object;
                            }
                            object2 = new CardEmulation(context, (INfcCardEmulation)object);
                            sCardEmus.put(context, (CardEmulation)object2);
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

    public static boolean isValidAid(String string2) {
        if (string2 == null) {
            return false;
        }
        if ((string2.endsWith("*") || string2.endsWith("#")) && string2.length() % 2 == 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AID ");
            stringBuilder.append(string2);
            stringBuilder.append(" is not a valid AID.");
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        if (!string2.endsWith("*") && !string2.endsWith("#") && string2.length() % 2 != 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AID ");
            stringBuilder.append(string2);
            stringBuilder.append(" is not a valid AID.");
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        if (!AID_PATTERN.matcher(string2).matches()) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("AID ");
            stringBuilder.append(string2);
            stringBuilder.append(" is not a valid AID.");
            Log.e(TAG, stringBuilder.toString());
            return false;
        }
        return true;
    }

    public boolean categoryAllowsForegroundPreference(String string2) {
        boolean bl = CATEGORY_PAYMENT.equals(string2);
        boolean bl2 = true;
        if (bl) {
            bl = false;
            try {
                int n = Settings.Secure.getInt(this.mContext.getContentResolver(), "nfc_payment_foreground");
                if (n == 0) {
                    bl2 = false;
                }
            }
            catch (Settings.SettingNotFoundException settingNotFoundException) {
                bl2 = bl;
            }
            return bl2;
        }
        return true;
    }

    public List<String> getAidsForService(ComponentName object, String object2) {
        List<String> list;
        block7 : {
            AidGroup aidGroup;
            Object var3_4 = null;
            list = null;
            try {
                aidGroup = sService.getAidGroupForService(this.mContext.getUserId(), (ComponentName)object, (String)object2);
                if (aidGroup == null) break block7;
            }
            catch (RemoteException remoteException) {
                block8 : {
                    this.recoverService();
                    INfcCardEmulation iNfcCardEmulation = sService;
                    if (iNfcCardEmulation == null) {
                        Log.e(TAG, "Failed to recover CardEmulationService.");
                        return null;
                    }
                    try {
                        object2 = iNfcCardEmulation.getAidGroupForService(this.mContext.getUserId(), (ComponentName)object, (String)object2);
                        object = var3_4;
                        if (object2 == null) break block8;
                    }
                    catch (RemoteException remoteException2) {
                        Log.e(TAG, "Failed to recover CardEmulationService.");
                        return null;
                    }
                    object = ((AidGroup)object2).getAids();
                }
                return object;
            }
            list = aidGroup.getAids();
        }
        return list;
    }

    public int getSelectionModeForCategory(String string2) {
        if (CATEGORY_PAYMENT.equals(string2)) {
            return Settings.Secure.getString(this.mContext.getContentResolver(), "nfc_payment_default_component") == null;
        }
        return 2;
    }

    public List<ApduServiceInfo> getServices(String object) {
        try {
            List<ApduServiceInfo> list = sService.getServices(this.mContext.getUserId(), (String)object);
            return list;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return null;
            }
            try {
                object = iNfcCardEmulation.getServices(this.mContext.getUserId(), (String)object);
                return object;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return null;
            }
        }
    }

    public boolean isDefaultServiceForAid(ComponentName componentName, String string2) {
        try {
            boolean bl = sService.isDefaultServiceForAid(this.mContext.getUserId(), componentName, string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
            try {
                boolean bl = iNfcCardEmulation.isDefaultServiceForAid(this.mContext.getUserId(), componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return false;
            }
        }
    }

    public boolean isDefaultServiceForCategory(ComponentName componentName, String string2) {
        try {
            boolean bl = sService.isDefaultServiceForCategory(this.mContext.getUserId(), componentName, string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
            try {
                boolean bl = iNfcCardEmulation.isDefaultServiceForCategory(this.mContext.getUserId(), componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
        }
    }

    void recoverService() {
        sService = NfcAdapter.getDefaultAdapter(this.mContext).getCardEmulationService();
    }

    public boolean registerAidsForService(ComponentName componentName, String object, List<String> list) {
        object = new AidGroup(list, (String)object);
        try {
            boolean bl = sService.registerAidGroupForService(this.mContext.getUserId(), componentName, (AidGroup)object);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
            try {
                boolean bl = iNfcCardEmulation.registerAidGroupForService(this.mContext.getUserId(), componentName, (AidGroup)object);
                return bl;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return false;
            }
        }
    }

    public boolean removeAidsForService(ComponentName componentName, String string2) {
        try {
            boolean bl = sService.removeAidGroupForService(this.mContext.getUserId(), componentName, string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
            try {
                boolean bl = iNfcCardEmulation.removeAidGroupForService(this.mContext.getUserId(), componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return false;
            }
        }
    }

    public boolean setDefaultForNextTap(ComponentName componentName) {
        try {
            boolean bl = sService.setDefaultForNextTap(this.mContext.getUserId(), componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
            try {
                boolean bl = iNfcCardEmulation.setDefaultForNextTap(this.mContext.getUserId(), componentName);
                return bl;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return false;
            }
        }
    }

    public boolean setDefaultServiceForCategory(ComponentName componentName, String string2) {
        try {
            boolean bl = sService.setDefaultServiceForCategory(this.mContext.getUserId(), componentName, string2);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
            try {
                boolean bl = iNfcCardEmulation.setDefaultServiceForCategory(this.mContext.getUserId(), componentName, string2);
                return bl;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return false;
            }
        }
    }

    public boolean setOffHostForService(ComponentName componentName, String string2) {
        Object object = NfcAdapter.getDefaultAdapter(this.mContext);
        if (object != null && string2 != null) {
            object = ((NfcAdapter)object).getSupportedOffHostSecureElements();
            if (string2.startsWith("eSE") && !object.contains("eSE") || string2.startsWith("SIM") && !object.contains("SIM")) {
                return false;
            }
            if (!string2.startsWith("eSE") && !string2.startsWith("SIM")) {
                return false;
            }
            if (string2.equals("eSE")) {
                object = "eSE1";
            } else {
                object = string2;
                if (string2.equals("SIM")) {
                    object = "SIM1";
                }
            }
            try {
                boolean bl = sService.setOffHostForService(this.mContext.getUserId(), componentName, (String)object);
                return bl;
            }
            catch (RemoteException remoteException) {
                this.recoverService();
                INfcCardEmulation iNfcCardEmulation = sService;
                if (iNfcCardEmulation == null) {
                    Log.e(TAG, "Failed to recover CardEmulationService.");
                    return false;
                }
                try {
                    boolean bl = iNfcCardEmulation.setOffHostForService(this.mContext.getUserId(), componentName, (String)object);
                    return bl;
                }
                catch (RemoteException remoteException2) {
                    Log.e(TAG, "Failed to reach CardEmulationService.");
                    return false;
                }
            }
        }
        return false;
    }

    public boolean setPreferredService(Activity activity, ComponentName componentName) {
        if (activity != null && componentName != null) {
            if (activity.isResumed()) {
                try {
                    boolean bl = sService.setPreferredService(componentName);
                    return bl;
                }
                catch (RemoteException remoteException) {
                    this.recoverService();
                    INfcCardEmulation iNfcCardEmulation = sService;
                    if (iNfcCardEmulation == null) {
                        Log.e(TAG, "Failed to recover CardEmulationService.");
                        return false;
                    }
                    try {
                        boolean bl = iNfcCardEmulation.setPreferredService(componentName);
                        return bl;
                    }
                    catch (RemoteException remoteException2) {
                        Log.e(TAG, "Failed to reach CardEmulationService.");
                        return false;
                    }
                }
            }
            throw new IllegalArgumentException("Activity must be resumed.");
        }
        throw new NullPointerException("activity or service or category is null");
    }

    public boolean supportsAidPrefixRegistration() {
        try {
            boolean bl = sService.supportsAidPrefixRegistration();
            return bl;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
            try {
                boolean bl = iNfcCardEmulation.supportsAidPrefixRegistration();
                return bl;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return false;
            }
        }
    }

    public boolean unsetOffHostForService(ComponentName componentName) {
        if (NfcAdapter.getDefaultAdapter(this.mContext) == null) {
            return false;
        }
        try {
            boolean bl = sService.unsetOffHostForService(this.mContext.getUserId(), componentName);
            return bl;
        }
        catch (RemoteException remoteException) {
            this.recoverService();
            INfcCardEmulation iNfcCardEmulation = sService;
            if (iNfcCardEmulation == null) {
                Log.e(TAG, "Failed to recover CardEmulationService.");
                return false;
            }
            try {
                boolean bl = iNfcCardEmulation.unsetOffHostForService(this.mContext.getUserId(), componentName);
                return bl;
            }
            catch (RemoteException remoteException2) {
                Log.e(TAG, "Failed to reach CardEmulationService.");
                return false;
            }
        }
    }

    public boolean unsetPreferredService(Activity activity) {
        if (activity != null) {
            if (activity.isResumed()) {
                try {
                    boolean bl = sService.unsetPreferredService();
                    return bl;
                }
                catch (RemoteException remoteException) {
                    this.recoverService();
                    INfcCardEmulation iNfcCardEmulation = sService;
                    if (iNfcCardEmulation == null) {
                        Log.e(TAG, "Failed to recover CardEmulationService.");
                        return false;
                    }
                    try {
                        boolean bl = iNfcCardEmulation.unsetPreferredService();
                        return bl;
                    }
                    catch (RemoteException remoteException2) {
                        Log.e(TAG, "Failed to reach CardEmulationService.");
                        return false;
                    }
                }
            }
            throw new IllegalArgumentException("Activity must be resumed.");
        }
        throw new NullPointerException("activity is null");
    }
}

