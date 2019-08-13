/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.util.EventLog;

public class SecurityLogTags {
    public static final int SECURITY_ADB_SHELL_COMMAND = 210002;
    public static final int SECURITY_ADB_SHELL_INTERACTIVE = 210001;
    public static final int SECURITY_ADB_SYNC_RECV = 210003;
    public static final int SECURITY_ADB_SYNC_SEND = 210004;
    public static final int SECURITY_APP_PROCESS_START = 210005;
    public static final int SECURITY_CERT_AUTHORITY_INSTALLED = 210029;
    public static final int SECURITY_CERT_AUTHORITY_REMOVED = 210030;
    public static final int SECURITY_CERT_VALIDATION_FAILURE = 210033;
    public static final int SECURITY_CRYPTO_SELF_TEST_COMPLETED = 210031;
    public static final int SECURITY_KEYGUARD_DISABLED_FEATURES_SET = 210021;
    public static final int SECURITY_KEYGUARD_DISMISSED = 210006;
    public static final int SECURITY_KEYGUARD_DISMISS_AUTH_ATTEMPT = 210007;
    public static final int SECURITY_KEYGUARD_SECURED = 210008;
    public static final int SECURITY_KEY_DESTROYED = 210026;
    public static final int SECURITY_KEY_GENERATED = 210024;
    public static final int SECURITY_KEY_IMPORTED = 210025;
    public static final int SECURITY_KEY_INTEGRITY_VIOLATION = 210032;
    public static final int SECURITY_LOGGING_STARTED = 210011;
    public static final int SECURITY_LOGGING_STOPPED = 210012;
    public static final int SECURITY_LOG_BUFFER_SIZE_CRITICAL = 210015;
    public static final int SECURITY_MAX_PASSWORD_ATTEMPTS_SET = 210020;
    public static final int SECURITY_MAX_SCREEN_LOCK_TIMEOUT_SET = 210019;
    public static final int SECURITY_MEDIA_MOUNTED = 210013;
    public static final int SECURITY_MEDIA_UNMOUNTED = 210014;
    public static final int SECURITY_OS_SHUTDOWN = 210010;
    public static final int SECURITY_OS_STARTUP = 210009;
    public static final int SECURITY_PASSWORD_COMPLEXITY_SET = 210017;
    public static final int SECURITY_PASSWORD_EXPIRATION_SET = 210016;
    public static final int SECURITY_PASSWORD_HISTORY_LENGTH_SET = 210018;
    public static final int SECURITY_REMOTE_LOCK = 210022;
    public static final int SECURITY_USER_RESTRICTION_ADDED = 210027;
    public static final int SECURITY_USER_RESTRICTION_REMOVED = 210028;
    public static final int SECURITY_WIPE_FAILED = 210023;

    private SecurityLogTags() {
    }

    public static void writeSecurityAdbShellCommand(String string2) {
        EventLog.writeEvent(210002, string2);
    }

    public static void writeSecurityAdbShellInteractive() {
        EventLog.writeEvent(210001, new Object[0]);
    }

    public static void writeSecurityAdbSyncRecv(String string2) {
        EventLog.writeEvent(210003, string2);
    }

    public static void writeSecurityAdbSyncSend(String string2) {
        EventLog.writeEvent(210004, string2);
    }

    public static void writeSecurityAppProcessStart(String string2, long l, int n, int n2, String string3, String string4) {
        EventLog.writeEvent(210005, string2, l, n, n2, string3, string4);
    }

    public static void writeSecurityCertAuthorityInstalled(int n, String string2) {
        EventLog.writeEvent(210029, n, string2);
    }

    public static void writeSecurityCertAuthorityRemoved(int n, String string2) {
        EventLog.writeEvent(210030, n, string2);
    }

    public static void writeSecurityCertValidationFailure(String string2) {
        EventLog.writeEvent(210033, string2);
    }

    public static void writeSecurityCryptoSelfTestCompleted(int n) {
        EventLog.writeEvent(210031, n);
    }

    public static void writeSecurityKeyDestroyed(int n, String string2, int n2) {
        EventLog.writeEvent(210026, n, string2, n2);
    }

    public static void writeSecurityKeyGenerated(int n, String string2, int n2) {
        EventLog.writeEvent(210024, n, string2, n2);
    }

    public static void writeSecurityKeyImported(int n, String string2, int n2) {
        EventLog.writeEvent(210025, n, string2, n2);
    }

    public static void writeSecurityKeyIntegrityViolation(String string2, int n) {
        EventLog.writeEvent(210032, string2, n);
    }

    public static void writeSecurityKeyguardDisabledFeaturesSet(String string2, int n, int n2, int n3) {
        EventLog.writeEvent(210021, string2, n, n2, n3);
    }

    public static void writeSecurityKeyguardDismissAuthAttempt(int n, int n2) {
        EventLog.writeEvent(210007, n, n2);
    }

    public static void writeSecurityKeyguardDismissed() {
        EventLog.writeEvent(210006, new Object[0]);
    }

    public static void writeSecurityKeyguardSecured() {
        EventLog.writeEvent(210008, new Object[0]);
    }

    public static void writeSecurityLogBufferSizeCritical() {
        EventLog.writeEvent(210015, new Object[0]);
    }

    public static void writeSecurityLoggingStarted() {
        EventLog.writeEvent(210011, new Object[0]);
    }

    public static void writeSecurityLoggingStopped() {
        EventLog.writeEvent(210012, new Object[0]);
    }

    public static void writeSecurityMaxPasswordAttemptsSet(String string2, int n, int n2, int n3) {
        EventLog.writeEvent(210020, string2, n, n2, n3);
    }

    public static void writeSecurityMaxScreenLockTimeoutSet(String string2, int n, int n2, long l) {
        EventLog.writeEvent(210019, string2, n, n2, l);
    }

    public static void writeSecurityMediaMounted(String string2, String string3) {
        EventLog.writeEvent(210013, string2, string3);
    }

    public static void writeSecurityMediaUnmounted(String string2, String string3) {
        EventLog.writeEvent(210014, string2, string3);
    }

    public static void writeSecurityOsShutdown() {
        EventLog.writeEvent(210010, new Object[0]);
    }

    public static void writeSecurityOsStartup(String string2, String string3) {
        EventLog.writeEvent(210009, string2, string3);
    }

    public static void writeSecurityPasswordComplexitySet(String string2, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, int n9, int n10) {
        EventLog.writeEvent(210017, string2, n, n2, n3, n4, n5, n6, n7, n8, n9, n10);
    }

    public static void writeSecurityPasswordExpirationSet(String string2, int n, int n2, long l) {
        EventLog.writeEvent(210016, string2, n, n2, l);
    }

    public static void writeSecurityPasswordHistoryLengthSet(String string2, int n, int n2, int n3) {
        EventLog.writeEvent(210018, string2, n, n2, n3);
    }

    public static void writeSecurityRemoteLock(String string2, int n, int n2) {
        EventLog.writeEvent(210022, string2, n, n2);
    }

    public static void writeSecurityUserRestrictionAdded(String string2, int n, String string3) {
        EventLog.writeEvent(210027, string2, n, string3);
    }

    public static void writeSecurityUserRestrictionRemoved(String string2, int n, String string3) {
        EventLog.writeEvent(210028, string2, n, string3);
    }

    public static void writeSecurityWipeFailed(String string2, int n) {
        EventLog.writeEvent(210023, string2, n);
    }
}

