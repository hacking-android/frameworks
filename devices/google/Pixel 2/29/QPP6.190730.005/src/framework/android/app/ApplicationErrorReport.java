/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.Printer;
import com.android.internal.util.FastPrintWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

public class ApplicationErrorReport
implements Parcelable {
    public static final Parcelable.Creator<ApplicationErrorReport> CREATOR = new Parcelable.Creator<ApplicationErrorReport>(){

        @Override
        public ApplicationErrorReport createFromParcel(Parcel parcel) {
            return new ApplicationErrorReport(parcel);
        }

        public ApplicationErrorReport[] newArray(int n) {
            return new ApplicationErrorReport[n];
        }
    };
    static final String DEFAULT_ERROR_RECEIVER_PROPERTY = "ro.error.receiver.default";
    static final String SYSTEM_APPS_ERROR_RECEIVER_PROPERTY = "ro.error.receiver.system.apps";
    public static final int TYPE_ANR = 2;
    public static final int TYPE_BATTERY = 3;
    public static final int TYPE_CRASH = 1;
    public static final int TYPE_NONE = 0;
    public static final int TYPE_RUNNING_SERVICE = 5;
    public AnrInfo anrInfo;
    public BatteryInfo batteryInfo;
    public CrashInfo crashInfo;
    public String installerPackageName;
    public String packageName;
    public String processName;
    public RunningServiceInfo runningServiceInfo;
    public boolean systemApp;
    public long time;
    public int type;

    public ApplicationErrorReport() {
    }

    ApplicationErrorReport(Parcel parcel) {
        this.readFromParcel(parcel);
    }

    public static ComponentName getErrorReportReceiver(Context object, String string2, int n) {
        if (Settings.Global.getInt(((Context)object).getContentResolver(), "send_action_app_error", 0) == 0) {
            return null;
        }
        PackageManager packageManager = ((Context)object).getPackageManager();
        object = null;
        try {
            String string3 = packageManager.getInstallerPackageName(string2);
            object = string3;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            // empty catch block
        }
        if (object != null && (object = ApplicationErrorReport.getErrorReportReceiver(packageManager, string2, (String)object)) != null) {
            return object;
        }
        if ((n & 1) != 0 && (object = ApplicationErrorReport.getErrorReportReceiver(packageManager, string2, SystemProperties.get(SYSTEM_APPS_ERROR_RECEIVER_PROPERTY))) != null) {
            return object;
        }
        return ApplicationErrorReport.getErrorReportReceiver(packageManager, string2, SystemProperties.get(DEFAULT_ERROR_RECEIVER_PROPERTY));
    }

    static ComponentName getErrorReportReceiver(PackageManager object, String object2, String string2) {
        if (string2 != null && string2.length() != 0) {
            if (string2.equals(object2)) {
                return null;
            }
            object2 = new Intent("android.intent.action.APP_ERROR");
            ((Intent)object2).setPackage(string2);
            object = ((PackageManager)object).resolveActivity((Intent)object2, 0);
            if (object != null && ((ResolveInfo)object).activityInfo != null) {
                return new ComponentName(string2, object.activityInfo.name);
            }
            return null;
        }
        return null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void dump(Printer printer, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("type: ");
        stringBuilder.append(this.type);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("packageName: ");
        stringBuilder.append(this.packageName);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("installerPackageName: ");
        stringBuilder.append(this.installerPackageName);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("processName: ");
        stringBuilder.append(this.processName);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("time: ");
        stringBuilder.append(this.time);
        printer.println(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append("systemApp: ");
        stringBuilder.append(this.systemApp);
        printer.println(stringBuilder.toString());
        int n = this.type;
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 5) {
                        this.runningServiceInfo.dump(printer, string2);
                    }
                } else {
                    this.batteryInfo.dump(printer, string2);
                }
            } else {
                this.anrInfo.dump(printer, string2);
            }
        } else {
            this.crashInfo.dump(printer, string2);
        }
    }

    public void readFromParcel(Parcel object) {
        this.type = ((Parcel)object).readInt();
        this.packageName = ((Parcel)object).readString();
        this.installerPackageName = ((Parcel)object).readString();
        this.processName = ((Parcel)object).readString();
        this.time = ((Parcel)object).readLong();
        int n = ((Parcel)object).readInt();
        boolean bl = false;
        boolean bl2 = n == 1;
        this.systemApp = bl2;
        if (((Parcel)object).readInt() == 1) {
            bl = true;
        }
        if ((n = this.type) != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n == 5) {
                        this.batteryInfo = null;
                        this.anrInfo = null;
                        this.crashInfo = null;
                        this.runningServiceInfo = new RunningServiceInfo((Parcel)object);
                    }
                } else {
                    this.batteryInfo = new BatteryInfo((Parcel)object);
                    this.anrInfo = null;
                    this.crashInfo = null;
                    this.runningServiceInfo = null;
                }
            } else {
                this.anrInfo = new AnrInfo((Parcel)object);
                this.crashInfo = null;
                this.batteryInfo = null;
                this.runningServiceInfo = null;
            }
        } else {
            object = bl ? new CrashInfo((Parcel)object) : null;
            this.crashInfo = object;
            this.anrInfo = null;
            this.batteryInfo = null;
            this.runningServiceInfo = null;
        }
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.type);
        parcel.writeString(this.packageName);
        parcel.writeString(this.installerPackageName);
        parcel.writeString(this.processName);
        parcel.writeLong(this.time);
        parcel.writeInt((int)this.systemApp);
        int n2 = this.crashInfo != null ? 1 : 0;
        parcel.writeInt(n2);
        n2 = this.type;
        if (n2 != 1) {
            if (n2 != 2) {
                if (n2 != 3) {
                    if (n2 == 5) {
                        this.runningServiceInfo.writeToParcel(parcel, n);
                    }
                } else {
                    this.batteryInfo.writeToParcel(parcel, n);
                }
            } else {
                this.anrInfo.writeToParcel(parcel, n);
            }
        } else {
            CrashInfo crashInfo = this.crashInfo;
            if (crashInfo != null) {
                crashInfo.writeToParcel(parcel, n);
            }
        }
    }

    public static class AnrInfo {
        public String activity;
        public String cause;
        public String info;

        public AnrInfo() {
        }

        public AnrInfo(Parcel parcel) {
            this.activity = parcel.readString();
            this.cause = parcel.readString();
            this.info = parcel.readString();
        }

        public void dump(Printer printer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("activity: ");
            stringBuilder.append(this.activity);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("cause: ");
            stringBuilder.append(this.cause);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("info: ");
            stringBuilder.append(this.info);
            printer.println(stringBuilder.toString());
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.activity);
            parcel.writeString(this.cause);
            parcel.writeString(this.info);
        }
    }

    public static class BatteryInfo {
        public String checkinDetails;
        public long durationMicros;
        public String usageDetails;
        public int usagePercent;

        public BatteryInfo() {
        }

        public BatteryInfo(Parcel parcel) {
            this.usagePercent = parcel.readInt();
            this.durationMicros = parcel.readLong();
            this.usageDetails = parcel.readString();
            this.checkinDetails = parcel.readString();
        }

        public void dump(Printer printer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("usagePercent: ");
            stringBuilder.append(this.usagePercent);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("durationMicros: ");
            stringBuilder.append(this.durationMicros);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("usageDetails: ");
            stringBuilder.append(this.usageDetails);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("checkinDetails: ");
            stringBuilder.append(this.checkinDetails);
            printer.println(stringBuilder.toString());
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeInt(this.usagePercent);
            parcel.writeLong(this.durationMicros);
            parcel.writeString(this.usageDetails);
            parcel.writeString(this.checkinDetails);
        }
    }

    public static class CrashInfo {
        public String crashTag;
        public String exceptionClassName;
        public String exceptionMessage;
        public String stackTrace;
        public String throwClassName;
        public String throwFileName;
        public int throwLineNumber;
        public String throwMethodName;

        public CrashInfo() {
        }

        public CrashInfo(Parcel parcel) {
            this.exceptionClassName = parcel.readString();
            this.exceptionMessage = parcel.readString();
            this.throwFileName = parcel.readString();
            this.throwClassName = parcel.readString();
            this.throwMethodName = parcel.readString();
            this.throwLineNumber = parcel.readInt();
            this.stackTrace = parcel.readString();
            this.crashTag = parcel.readString();
        }

        public CrashInfo(Throwable serializable) {
            Object object = new StringWriter();
            Object object2 = new FastPrintWriter((Writer)object, false, 256);
            ((Throwable)serializable).printStackTrace((PrintWriter)object2);
            ((PrintWriter)object2).flush();
            this.stackTrace = this.sanitizeString(((StringWriter)object).toString());
            this.exceptionMessage = ((Throwable)serializable).getMessage();
            object = serializable;
            while (((Throwable)serializable).getCause() != null) {
                serializable = ((Throwable)serializable).getCause();
                object2 = object;
                if (((Throwable)serializable).getStackTrace() != null) {
                    object2 = object;
                    if (((Throwable)serializable).getStackTrace().length > 0) {
                        object2 = serializable;
                    }
                }
                if ((object = ((Throwable)serializable).getMessage()) != null && ((String)object).length() > 0) {
                    this.exceptionMessage = object;
                }
                object = object2;
            }
            this.exceptionClassName = object.getClass().getName();
            if (((Throwable)object).getStackTrace().length > 0) {
                serializable = ((Throwable)object).getStackTrace()[0];
                this.throwFileName = ((StackTraceElement)serializable).getFileName();
                this.throwClassName = ((StackTraceElement)serializable).getClassName();
                this.throwMethodName = ((StackTraceElement)serializable).getMethodName();
                this.throwLineNumber = ((StackTraceElement)serializable).getLineNumber();
            } else {
                this.throwFileName = "unknown";
                this.throwClassName = "unknown";
                this.throwMethodName = "unknown";
                this.throwLineNumber = 0;
            }
            this.exceptionMessage = this.sanitizeString(this.exceptionMessage);
        }

        private String sanitizeString(String string2) {
            int n = 10240 + 10240;
            if (string2 != null && string2.length() > n) {
                CharSequence charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("\n[TRUNCATED ");
                ((StringBuilder)charSequence).append(string2.length() - n);
                ((StringBuilder)charSequence).append(" CHARS]\n");
                charSequence = ((StringBuilder)charSequence).toString();
                StringBuilder stringBuilder = new StringBuilder(((String)charSequence).length() + n);
                stringBuilder.append(string2.substring(0, 10240));
                stringBuilder.append((String)charSequence);
                stringBuilder.append(string2.substring(string2.length() - 10240));
                return stringBuilder.toString();
            }
            return string2;
        }

        public void appendStackTrace(String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.stackTrace);
            stringBuilder.append(string2);
            this.stackTrace = this.sanitizeString(stringBuilder.toString());
        }

        public void dump(Printer printer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("exceptionClassName: ");
            stringBuilder.append(this.exceptionClassName);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("exceptionMessage: ");
            stringBuilder.append(this.exceptionMessage);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("throwFileName: ");
            stringBuilder.append(this.throwFileName);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("throwClassName: ");
            stringBuilder.append(this.throwClassName);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("throwMethodName: ");
            stringBuilder.append(this.throwMethodName);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("throwLineNumber: ");
            stringBuilder.append(this.throwLineNumber);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("stackTrace: ");
            stringBuilder.append(this.stackTrace);
            printer.println(stringBuilder.toString());
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.dataPosition();
            parcel.writeString(this.exceptionClassName);
            parcel.writeString(this.exceptionMessage);
            parcel.writeString(this.throwFileName);
            parcel.writeString(this.throwClassName);
            parcel.writeString(this.throwMethodName);
            parcel.writeInt(this.throwLineNumber);
            parcel.writeString(this.stackTrace);
            parcel.writeString(this.crashTag);
            parcel.dataPosition();
        }
    }

    public static class ParcelableCrashInfo
    extends CrashInfo
    implements Parcelable {
        public static final Parcelable.Creator<ParcelableCrashInfo> CREATOR = new Parcelable.Creator<ParcelableCrashInfo>(){

            @Override
            public ParcelableCrashInfo createFromParcel(Parcel parcel) {
                return new ParcelableCrashInfo(parcel);
            }

            public ParcelableCrashInfo[] newArray(int n) {
                return new ParcelableCrashInfo[n];
            }
        };

        public ParcelableCrashInfo() {
        }

        public ParcelableCrashInfo(Parcel parcel) {
            super(parcel);
        }

        public ParcelableCrashInfo(Throwable throwable) {
            super(throwable);
        }

        @Override
        public int describeContents() {
            return 0;
        }

    }

    public static class RunningServiceInfo {
        public long durationMillis;
        public String serviceDetails;

        public RunningServiceInfo() {
        }

        public RunningServiceInfo(Parcel parcel) {
            this.durationMillis = parcel.readLong();
            this.serviceDetails = parcel.readString();
        }

        public void dump(Printer printer, String string2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("durationMillis: ");
            stringBuilder.append(this.durationMillis);
            printer.println(stringBuilder.toString());
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append("serviceDetails: ");
            stringBuilder.append(this.serviceDetails);
            printer.println(stringBuilder.toString());
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeLong(this.durationMillis);
            parcel.writeString(this.serviceDetails);
        }
    }

}

