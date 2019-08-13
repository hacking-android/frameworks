/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.telecom.-$
 *  android.telecom.-$$Lambda
 *  android.telecom.-$$Lambda$qa4s1Fm2YuohEunaJUJcmJXDXG0
 */
package android.telecom;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.telecom.-$;
import android.telecom.Logging.EventManager;
import android.telecom.Logging.Session;
import android.telecom.Logging.SessionManager;
import android.telecom._$$Lambda$qa4s1Fm2YuohEunaJUJcmJXDXG0;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.util.IndentingPrintWriter;
import java.util.IllegalFormatException;
import java.util.Locale;

public class Log {
    public static boolean DEBUG = false;
    public static boolean ERROR = false;
    private static final int EVENTS_TO_CACHE = 10;
    private static final int EVENTS_TO_CACHE_DEBUG = 20;
    private static final long EXTENDED_LOGGING_DURATION_MILLIS = 1800000L;
    private static final boolean FORCE_LOGGING = false;
    public static boolean INFO;
    private static final int NUM_DIALABLE_DIGITS_TO_LOG;
    @VisibleForTesting
    public static String TAG;
    private static final boolean USER_BUILD;
    public static boolean VERBOSE;
    public static boolean WARN;
    private static EventManager sEventManager;
    private static boolean sIsUserExtendedLoggingEnabled;
    private static SessionManager sSessionManager;
    private static final Object sSingletonSync;
    private static long sUserExtendedLoggingStopTime;

    static {
        int n = Build.IS_USER ? 0 : 2;
        NUM_DIALABLE_DIGITS_TO_LOG = n;
        TAG = "TelecomFramework";
        DEBUG = Log.isLoggable(3);
        INFO = Log.isLoggable(4);
        VERBOSE = Log.isLoggable(2);
        WARN = Log.isLoggable(5);
        ERROR = Log.isLoggable(6);
        USER_BUILD = Build.IS_USER;
        sSingletonSync = new Object();
        sIsUserExtendedLoggingEnabled = false;
        sUserExtendedLoggingStopTime = 0L;
    }

    private Log() {
    }

    public static void addEvent(EventManager.Loggable loggable, String string2) {
        Log.getEventManager().event(loggable, string2, null);
    }

    public static void addEvent(EventManager.Loggable loggable, String string2, Object object) {
        Log.getEventManager().event(loggable, string2, object);
    }

    public static void addEvent(EventManager.Loggable loggable, String string2, String string3, Object ... arrobject) {
        Log.getEventManager().event(loggable, string2, string3, arrobject);
    }

    public static void addRequestResponsePair(EventManager.TimedEventPair timedEventPair) {
        Log.getEventManager().addRequestResponsePair(timedEventPair);
    }

    private static String buildMessage(String string2, String charSequence, Object ... object) {
        CharSequence charSequence2;
        String string3 = Log.getSessionId();
        if (TextUtils.isEmpty(string3)) {
            string3 = "";
        } else {
            charSequence2 = new StringBuilder();
            charSequence2.append(": ");
            charSequence2.append(string3);
            string3 = charSequence2.toString();
        }
        if (object != null) {
            try {
                if (((Object[])object).length != 0) {
                    charSequence2 = String.format(Locale.US, charSequence, (Object[])object);
                    charSequence = charSequence2;
                }
            }
            catch (IllegalFormatException illegalFormatException) {
                Log.e(TAG, (Throwable)illegalFormatException, "Log: IllegalFormatException: formatString='%s' numArgs=%d", charSequence, ((Object[])object).length);
                object = new StringBuilder();
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append(" (An error occurred while formatting the message.)");
                charSequence = ((StringBuilder)object).toString();
            }
        }
        return String.format(Locale.US, "%s: %s%s", string2, charSequence, string3);
    }

    public static void cancelSubsession(Session session) {
        Log.getSessionManager().cancelSubsession(session);
    }

    public static void continueSession(Session session, String string2) {
        Log.getSessionManager().continueSession(session, string2);
    }

    public static Session createSubsession() {
        return Log.getSessionManager().createSubsession();
    }

    public static void d(Object object, String string2, Object ... arrobject) {
        if (sIsUserExtendedLoggingEnabled) {
            Log.maybeDisableLogging();
            Slog.i(TAG, Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject));
        } else if (DEBUG) {
            Slog.d(TAG, Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject));
        }
    }

    public static void d(String string2, String string3, Object ... arrobject) {
        if (sIsUserExtendedLoggingEnabled) {
            Log.maybeDisableLogging();
            Slog.i(TAG, Log.buildMessage(string2, string3, arrobject));
        } else if (DEBUG) {
            Slog.d(TAG, Log.buildMessage(string2, string3, arrobject));
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void dumpEvents(IndentingPrintWriter indentingPrintWriter) {
        Object object = sSingletonSync;
        synchronized (object) {
            if (sEventManager != null) {
                Log.getEventManager().dumpEvents(indentingPrintWriter);
            } else {
                indentingPrintWriter.println("No Historical Events Logged.");
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void dumpEventsTimeline(IndentingPrintWriter indentingPrintWriter) {
        Object object = sSingletonSync;
        synchronized (object) {
            if (sEventManager != null) {
                Log.getEventManager().dumpEventsTimeline(indentingPrintWriter);
            } else {
                indentingPrintWriter.println("No Historical Events Logged.");
            }
            return;
        }
    }

    public static void e(Object object, Throwable throwable, String string2, Object ... arrobject) {
        if (ERROR) {
            Slog.e(TAG, Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject), throwable);
        }
    }

    public static void e(String string2, Throwable throwable, String string3, Object ... arrobject) {
        if (ERROR) {
            Slog.e(TAG, Log.buildMessage(string2, string3, arrobject), throwable);
        }
    }

    public static void endSession() {
        Log.getSessionManager().endSession();
    }

    private static int getDialableCount(String arrc) {
        int n = 0;
        arrc = arrc.toCharArray();
        int n2 = arrc.length;
        for (int i = 0; i < n2; ++i) {
            int n3 = n;
            if (PhoneNumberUtils.isDialable(arrc[i])) {
                n3 = n + 1;
            }
            n = n3;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static EventManager getEventManager() {
        if (sEventManager != null) return sEventManager;
        Object object = sSingletonSync;
        synchronized (object) {
            if (sEventManager != null) return sEventManager;
            EventManager eventManager = new EventManager((SessionManager.ISessionIdQueryHandler)_$$Lambda$qa4s1Fm2YuohEunaJUJcmJXDXG0.INSTANCE);
            sEventManager = eventManager;
            return sEventManager;
        }
    }

    public static Session.Info getExternalSession() {
        return Log.getSessionManager().getExternalSession();
    }

    private static String getPrefixFromObject(Object object) {
        object = object == null ? "<null>" : object.getClass().getSimpleName();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static String getSessionId() {
        Object object = sSingletonSync;
        synchronized (object) {
            if (sSessionManager == null) return "";
            return Log.getSessionManager().getSessionId();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @VisibleForTesting
    public static SessionManager getSessionManager() {
        if (sSessionManager != null) return sSessionManager;
        Object object = sSingletonSync;
        synchronized (object) {
            if (sSessionManager != null) return sSessionManager;
            SessionManager sessionManager = new SessionManager();
            sSessionManager = sessionManager;
            return sSessionManager;
        }
    }

    public static void i(Object object, String string2, Object ... arrobject) {
        if (INFO) {
            Slog.i(TAG, Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject));
        }
    }

    @UnsupportedAppUsage
    public static void i(String string2, String string3, Object ... arrobject) {
        if (INFO) {
            Slog.i(TAG, Log.buildMessage(string2, string3, arrobject));
        }
    }

    public static boolean isLoggable(int n) {
        return android.util.Log.isLoggable(TAG, n);
    }

    private static void maybeDisableLogging() {
        if (!sIsUserExtendedLoggingEnabled) {
            return;
        }
        if (sUserExtendedLoggingStopTime < System.currentTimeMillis()) {
            sUserExtendedLoggingStopTime = 0L;
            sIsUserExtendedLoggingEnabled = false;
        }
    }

    private static void obfuscatePhoneNumber(StringBuilder stringBuilder, String string2) {
        int n = Log.getDialableCount(string2) - NUM_DIALABLE_DIGITS_TO_LOG;
        for (int i = 0; i < string2.length(); ++i) {
            char c = string2.charAt(i);
            boolean bl = PhoneNumberUtils.isDialable(c);
            int n2 = n;
            if (bl) {
                n2 = n - 1;
            }
            Object object = bl && n2 >= 0 ? "*" : Character.valueOf(c);
            stringBuilder.append(object);
            n = n2;
        }
    }

    public static String pii(Object object) {
        if (object != null && !VERBOSE) {
            return "***";
        }
        return String.valueOf(object);
    }

    public static String piiHandle(Object object) {
        if (object != null && !VERBOSE) {
            StringBuilder stringBuilder = new StringBuilder();
            if (object instanceof Uri) {
                Object object2 = (Uri)object;
                String string2 = ((Uri)object2).getScheme();
                if (!TextUtils.isEmpty(string2)) {
                    stringBuilder.append(string2);
                    stringBuilder.append(":");
                }
                object2 = ((Uri)object2).getSchemeSpecificPart();
                if ("tel".equals(string2)) {
                    Log.obfuscatePhoneNumber(stringBuilder, (String)object2);
                } else if ("sip".equals(string2)) {
                    for (int i = 0; i < ((String)object2).length(); ++i) {
                        int n;
                        int n2 = n = ((String)object2).charAt(i);
                        if (n != 64) {
                            n2 = n;
                            if (n != 46) {
                                n2 = n = 42;
                            }
                        }
                        stringBuilder.append((char)n2);
                    }
                } else {
                    stringBuilder.append(Log.pii(object));
                }
            } else if (object instanceof String) {
                Log.obfuscatePhoneNumber(stringBuilder, (String)object);
            }
            return stringBuilder.toString();
        }
        return String.valueOf(object);
    }

    public static void registerEventListener(EventManager.EventListener eventListener) {
        Log.getEventManager().registerEventListener(eventListener);
    }

    public static void registerSessionListener(SessionManager.ISessionListener iSessionListener) {
        Log.getSessionManager().registerSessionListener(iSessionListener);
    }

    public static void setIsExtendedLoggingEnabled(boolean bl) {
        if (sIsUserExtendedLoggingEnabled == bl) {
            return;
        }
        EventManager eventManager = sEventManager;
        if (eventManager != null) {
            int n = bl ? 20 : 10;
            eventManager.changeEventCacheSize(n);
        }
        sUserExtendedLoggingStopTime = (sIsUserExtendedLoggingEnabled = bl) ? System.currentTimeMillis() + 1800000L : 0L;
    }

    public static void setSessionContext(Context context) {
        Log.getSessionManager().setContext(context);
    }

    public static void setTag(String string2) {
        TAG = string2;
        DEBUG = Log.isLoggable(3);
        INFO = Log.isLoggable(4);
        VERBOSE = Log.isLoggable(2);
        WARN = Log.isLoggable(5);
        ERROR = Log.isLoggable(6);
    }

    public static void startSession(Session.Info info, String string2) {
        Log.getSessionManager().startSession(info, string2, null);
    }

    public static void startSession(Session.Info info, String string2, String string3) {
        Log.getSessionManager().startSession(info, string2, string3);
    }

    public static void startSession(String string2) {
        Log.getSessionManager().startSession(string2, null);
    }

    public static void startSession(String string2, String string3) {
        Log.getSessionManager().startSession(string2, string3);
    }

    public static void v(Object object, String string2, Object ... arrobject) {
        if (sIsUserExtendedLoggingEnabled) {
            Log.maybeDisableLogging();
            Slog.i(TAG, Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject));
        } else if (VERBOSE) {
            Slog.v(TAG, Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject));
        }
    }

    public static void v(String string2, String string3, Object ... arrobject) {
        if (sIsUserExtendedLoggingEnabled) {
            Log.maybeDisableLogging();
            Slog.i(TAG, Log.buildMessage(string2, string3, arrobject));
        } else if (VERBOSE) {
            Slog.v(TAG, Log.buildMessage(string2, string3, arrobject));
        }
    }

    public static void w(Object object, String string2, Object ... arrobject) {
        if (WARN) {
            Slog.w(TAG, Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject));
        }
    }

    @UnsupportedAppUsage
    public static void w(String string2, String string3, Object ... arrobject) {
        if (WARN) {
            Slog.w(TAG, Log.buildMessage(string2, string3, arrobject));
        }
    }

    public static void wtf(Object object, String string2, Object ... arrobject) {
        object = Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject);
        Slog.wtf(TAG, (String)object, new IllegalStateException((String)object));
    }

    public static void wtf(Object object, Throwable throwable, String string2, Object ... arrobject) {
        Slog.wtf(TAG, Log.buildMessage(Log.getPrefixFromObject(object), string2, arrobject), throwable);
    }

    public static void wtf(String string2, String string3, Object ... arrobject) {
        string2 = Log.buildMessage(string2, string3, arrobject);
        Slog.wtf(TAG, string2, new IllegalStateException(string2));
    }

    public static void wtf(String string2, Throwable throwable, String string3, Object ... arrobject) {
        Slog.wtf(TAG, Log.buildMessage(string2, string3, arrobject), throwable);
    }
}

