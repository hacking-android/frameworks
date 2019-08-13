/*
 * Decompiled with CFR 0.145.
 */
package android.view.textservice;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.view.textservice.SpellCheckerInfo;
import android.view.textservice.SpellCheckerSession;
import android.view.textservice.SpellCheckerSubtype;
import com.android.internal.textservice.ISpellCheckerSessionListener;
import com.android.internal.textservice.ITextServicesManager;
import com.android.internal.textservice.ITextServicesSessionListener;
import java.util.Locale;

public final class TextServicesManager {
    private static final boolean DBG = false;
    private static final String TAG = TextServicesManager.class.getSimpleName();
    @Deprecated
    private static TextServicesManager sInstance;
    private final ITextServicesManager mService = ITextServicesManager.Stub.asInterface(ServiceManager.getServiceOrThrow("textservices"));
    private final int mUserId;

    private TextServicesManager(int n) throws ServiceManager.ServiceNotFoundException {
        this.mUserId = n;
    }

    public static TextServicesManager createInstance(Context context) throws ServiceManager.ServiceNotFoundException {
        return new TextServicesManager(context.getUserId());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public static TextServicesManager getInstance() {
        synchronized (TextServicesManager.class) {
            TextServicesManager textServicesManager = sInstance;
            if (textServicesManager != null) return sInstance;
            try {
                sInstance = textServicesManager = new TextServicesManager(UserHandle.myUserId());
                return sInstance;
            }
            catch (ServiceManager.ServiceNotFoundException serviceNotFoundException) {
                IllegalStateException illegalStateException = new IllegalStateException(serviceNotFoundException);
                throw illegalStateException;
            }
        }
    }

    private static String parseLanguageFromLocaleString(String string2) {
        int n = string2.indexOf(95);
        if (n < 0) {
            return string2;
        }
        return string2.substring(0, n);
    }

    void finishSpellCheckerService(ISpellCheckerSessionListener iSpellCheckerSessionListener) {
        try {
            this.mService.finishSpellCheckerService(this.mUserId, iSpellCheckerSessionListener);
            return;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public SpellCheckerInfo getCurrentSpellChecker() {
        try {
            SpellCheckerInfo spellCheckerInfo = this.mService.getCurrentSpellChecker(this.mUserId, null);
            return spellCheckerInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public SpellCheckerSubtype getCurrentSpellCheckerSubtype(boolean bl) {
        try {
            SpellCheckerSubtype spellCheckerSubtype = this.mService.getCurrentSpellCheckerSubtype(this.mUserId, bl);
            return spellCheckerSubtype;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public SpellCheckerInfo[] getEnabledSpellCheckers() {
        try {
            SpellCheckerInfo[] arrspellCheckerInfo = this.mService.getEnabledSpellCheckers(this.mUserId);
            return arrspellCheckerInfo;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    @UnsupportedAppUsage
    public boolean isSpellCheckerEnabled() {
        try {
            boolean bl = this.mService.isSpellCheckerEnabled(this.mUserId);
            return bl;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SpellCheckerSession newSpellCheckerSession(Bundle bundle, Locale object, SpellCheckerSession.SpellCheckerSessionListener spellCheckerSessionListener, boolean bl) {
        SpellCheckerInfo spellCheckerInfo;
        Object object2;
        block15 : {
            Object object3;
            int n;
            String string2;
            if (spellCheckerSessionListener == null) throw new NullPointerException();
            if (!bl) {
                if (object == null) throw new IllegalArgumentException("Locale should not be null if you don't refer settings.");
            }
            if (bl && !this.isSpellCheckerEnabled()) {
                return null;
            }
            try {
                spellCheckerInfo = this.mService.getCurrentSpellChecker(this.mUserId, null);
                if (spellCheckerInfo == null) {
                    return null;
                }
                object3 = null;
                if (bl) {
                    object3 = this.getCurrentSpellCheckerSubtype(true);
                    if (object3 == null) {
                        return null;
                    }
                    object2 = object3;
                    if (object != null) {
                        object2 = TextServicesManager.parseLanguageFromLocaleString(((SpellCheckerSubtype)object3).getLocale());
                        if (((String)object2).length() < 2) return null;
                        if (!((Locale)object).getLanguage().equals(object2)) {
                            return null;
                        }
                        object2 = object3;
                    }
                    break block15;
                }
                string2 = ((Locale)object).toString();
                n = 0;
            }
            catch (RemoteException remoteException) {
                return null;
            }
            do {
                object2 = object3;
                if (n >= spellCheckerInfo.getSubtypeCount()) break;
                object2 = spellCheckerInfo.getSubtypeAt(n);
                Object object4 = ((SpellCheckerSubtype)object2).getLocale();
                String string3 = TextServicesManager.parseLanguageFromLocaleString((String)object4);
                if (((String)object4).equals(string2)) break;
                object4 = object3;
                if (string3.length() >= 2) {
                    object4 = object3;
                    if (((Locale)object).getLanguage().equals(string3)) {
                        object4 = object2;
                    }
                }
                ++n;
                object3 = object4;
            } while (true);
        }
        if (object2 == null) {
            return null;
        }
        object = new SpellCheckerSession(spellCheckerInfo, this, spellCheckerSessionListener);
        try {
            this.mService.getSpellCheckerService(this.mUserId, spellCheckerInfo.getId(), ((SpellCheckerSubtype)object2).getLocale(), ((SpellCheckerSession)object).getTextServicesSessionListener(), ((SpellCheckerSession)object).getSpellCheckerSessionListener(), bundle);
            return object;
        }
        catch (RemoteException remoteException) {
            throw remoteException.rethrowFromSystemServer();
        }
    }
}

