/*
 * Decompiled with CFR 0.145.
 */
package android.app.admin;

import android.annotation.UnsupportedAppUsage;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.util.EventLog;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.Objects;

public class SecurityLog {
    public static final int LEVEL_ERROR = 3;
    public static final int LEVEL_INFO = 1;
    public static final int LEVEL_WARNING = 2;
    private static final String PROPERTY_LOGGING_ENABLED = "persist.logd.security";
    public static final int TAG_ADB_SHELL_CMD = 210002;
    public static final int TAG_ADB_SHELL_INTERACTIVE = 210001;
    public static final int TAG_APP_PROCESS_START = 210005;
    public static final int TAG_CERT_AUTHORITY_INSTALLED = 210029;
    public static final int TAG_CERT_AUTHORITY_REMOVED = 210030;
    public static final int TAG_CERT_VALIDATION_FAILURE = 210033;
    public static final int TAG_CRYPTO_SELF_TEST_COMPLETED = 210031;
    public static final int TAG_KEYGUARD_DISABLED_FEATURES_SET = 210021;
    public static final int TAG_KEYGUARD_DISMISSED = 210006;
    public static final int TAG_KEYGUARD_DISMISS_AUTH_ATTEMPT = 210007;
    public static final int TAG_KEYGUARD_SECURED = 210008;
    public static final int TAG_KEY_DESTRUCTION = 210026;
    public static final int TAG_KEY_GENERATED = 210024;
    public static final int TAG_KEY_IMPORT = 210025;
    public static final int TAG_KEY_INTEGRITY_VIOLATION = 210032;
    public static final int TAG_LOGGING_STARTED = 210011;
    public static final int TAG_LOGGING_STOPPED = 210012;
    public static final int TAG_LOG_BUFFER_SIZE_CRITICAL = 210015;
    public static final int TAG_MAX_PASSWORD_ATTEMPTS_SET = 210020;
    public static final int TAG_MAX_SCREEN_LOCK_TIMEOUT_SET = 210019;
    public static final int TAG_MEDIA_MOUNT = 210013;
    public static final int TAG_MEDIA_UNMOUNT = 210014;
    public static final int TAG_OS_SHUTDOWN = 210010;
    public static final int TAG_OS_STARTUP = 210009;
    public static final int TAG_PASSWORD_COMPLEXITY_SET = 210017;
    public static final int TAG_PASSWORD_EXPIRATION_SET = 210016;
    public static final int TAG_PASSWORD_HISTORY_LENGTH_SET = 210018;
    public static final int TAG_REMOTE_LOCK = 210022;
    public static final int TAG_SYNC_RECV_FILE = 210003;
    public static final int TAG_SYNC_SEND_FILE = 210004;
    public static final int TAG_USER_RESTRICTION_ADDED = 210027;
    public static final int TAG_USER_RESTRICTION_REMOVED = 210028;
    public static final int TAG_WIPE_FAILURE = 210023;

    public static boolean getLoggingEnabledProperty() {
        return SystemProperties.getBoolean(PROPERTY_LOGGING_ENABLED, false);
    }

    public static native boolean isLoggingEnabled();

    public static native void readEvents(Collection<SecurityEvent> var0) throws IOException;

    public static native void readEventsOnWrapping(long var0, Collection<SecurityEvent> var2) throws IOException;

    public static native void readEventsSince(long var0, Collection<SecurityEvent> var2) throws IOException;

    public static native void readPreviousEvents(Collection<SecurityEvent> var0) throws IOException;

    public static void setLoggingEnabledProperty(boolean bl) {
        String string2 = bl ? "true" : "false";
        SystemProperties.set("persist.logd.security", string2);
    }

    public static native int writeEvent(int var0, String var1);

    public static native int writeEvent(int var0, Object ... var1);

    public static final class SecurityEvent
    implements Parcelable {
        public static final Parcelable.Creator<SecurityEvent> CREATOR = new Parcelable.Creator<SecurityEvent>(){

            @Override
            public SecurityEvent createFromParcel(Parcel parcel) {
                return new SecurityEvent(parcel);
            }

            public SecurityEvent[] newArray(int n) {
                return new SecurityEvent[n];
            }
        };
        private EventLog.Event mEvent;
        private long mId;

        public SecurityEvent(long l, byte[] arrby) {
            this.mId = l;
            this.mEvent = EventLog.Event.fromBytes(arrby);
        }

        SecurityEvent(Parcel parcel) {
            this(parcel.readLong(), parcel.createByteArray());
        }

        @UnsupportedAppUsage
        SecurityEvent(byte[] arrby) {
            this(0L, arrby);
        }

        private boolean getSuccess() {
            Object[] arrobject = this.getData();
            boolean bl = false;
            if (arrobject != null && arrobject instanceof Object[]) {
                arrobject = arrobject;
                boolean bl2 = bl;
                if (arrobject.length >= 1) {
                    bl2 = bl;
                    if (arrobject[0] instanceof Integer) {
                        bl2 = bl;
                        if ((Integer)arrobject[0] != 0) {
                            bl2 = true;
                        }
                    }
                }
                return bl2;
            }
            return false;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (SecurityEvent)object;
                if (!this.mEvent.equals(((SecurityEvent)object).mEvent) || this.mId != ((SecurityEvent)object).mId) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public boolean eventEquals(SecurityEvent securityEvent) {
            boolean bl = securityEvent != null && this.mEvent.equals(securityEvent.mEvent);
            return bl;
        }

        public Object getData() {
            return this.mEvent.getData();
        }

        public long getId() {
            return this.mId;
        }

        public int getLogLevel() {
            int n = this.mEvent.getTag();
            int n2 = 2;
            int n3 = 3;
            switch (n) {
                default: {
                    return 1;
                }
                case 210033: {
                    return 2;
                }
                case 210030: 
                case 210031: {
                    n2 = n3;
                    if (this.getSuccess()) {
                        n2 = 1;
                    }
                    return n2;
                }
                case 210015: 
                case 210023: 
                case 210032: {
                    return 3;
                }
                case 210007: 
                case 210024: 
                case 210025: 
                case 210026: 
                case 210029: {
                    if (this.getSuccess()) {
                        n2 = 1;
                    }
                    return n2;
                }
                case 210001: 
                case 210002: 
                case 210003: 
                case 210004: 
                case 210005: 
                case 210006: 
                case 210008: 
                case 210009: 
                case 210010: 
                case 210011: 
                case 210012: 
                case 210013: 
                case 210014: 
                case 210016: 
                case 210017: 
                case 210018: 
                case 210019: 
                case 210020: 
                case 210027: 
                case 210028: 
            }
            return 1;
        }

        public int getTag() {
            return this.mEvent.getTag();
        }

        public long getTimeNanos() {
            return this.mEvent.getTimeNanos();
        }

        public int hashCode() {
            return Objects.hash(this.mEvent, this.mId);
        }

        public void setId(long l) {
            this.mId = l;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.mId);
            parcel.writeByteArray(this.mEvent.getBytes());
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SecurityLogLevel {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface SecurityLogTag {
    }

}

