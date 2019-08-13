/*
 * Decompiled with CFR 0.145.
 */
package android.os;

import android.annotation.SystemApi;
import android.annotation.UnsupportedAppUsage;
import android.os.Binder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Process;
import java.io.PrintWriter;

public final class UserHandle
implements Parcelable {
    @UnsupportedAppUsage
    public static final int AID_APP_END = 19999;
    @UnsupportedAppUsage
    public static final int AID_APP_START = 10000;
    @UnsupportedAppUsage
    public static final int AID_CACHE_GID_START = 20000;
    @UnsupportedAppUsage
    public static final int AID_ROOT = 0;
    @UnsupportedAppUsage
    public static final int AID_SHARED_GID_START = 50000;
    @SystemApi
    public static final UserHandle ALL = new UserHandle(-1);
    public static final Parcelable.Creator<UserHandle> CREATOR;
    @SystemApi
    public static final UserHandle CURRENT;
    @UnsupportedAppUsage
    public static final UserHandle CURRENT_OR_SELF;
    @UnsupportedAppUsage
    public static final int ERR_GID = -1;
    @UnsupportedAppUsage
    public static final boolean MU_ENABLED = true;
    @Deprecated
    @UnsupportedAppUsage
    public static final UserHandle OWNER;
    @UnsupportedAppUsage
    public static final int PER_USER_RANGE = 100000;
    @SystemApi
    public static final UserHandle SYSTEM;
    @UnsupportedAppUsage
    public static final int USER_ALL = -1;
    @UnsupportedAppUsage
    public static final int USER_CURRENT = -2;
    @UnsupportedAppUsage
    public static final int USER_CURRENT_OR_SELF = -3;
    @UnsupportedAppUsage
    public static final int USER_NULL = -10000;
    @Deprecated
    @UnsupportedAppUsage
    public static final int USER_OWNER = 0;
    @UnsupportedAppUsage
    public static final int USER_SERIAL_SYSTEM = 0;
    @UnsupportedAppUsage
    public static final int USER_SYSTEM = 0;
    @UnsupportedAppUsage
    final int mHandle;

    static {
        CURRENT = new UserHandle(-2);
        CURRENT_OR_SELF = new UserHandle(-3);
        OWNER = new UserHandle(0);
        SYSTEM = new UserHandle(0);
        CREATOR = new Parcelable.Creator<UserHandle>(){

            @Override
            public UserHandle createFromParcel(Parcel parcel) {
                return new UserHandle(parcel);
            }

            public UserHandle[] newArray(int n) {
                return new UserHandle[n];
            }
        };
    }

    @UnsupportedAppUsage
    public UserHandle(int n) {
        this.mHandle = n;
    }

    public UserHandle(Parcel parcel) {
        this.mHandle = parcel.readInt();
    }

    public static String formatUid(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        UserHandle.formatUid(stringBuilder, n);
        return stringBuilder.toString();
    }

    public static void formatUid(PrintWriter printWriter, int n) {
        if (n < 10000) {
            printWriter.print(n);
        } else {
            printWriter.print('u');
            printWriter.print(UserHandle.getUserId(n));
            n = UserHandle.getAppId(n);
            if (UserHandle.isIsolated(n)) {
                if (n > 99000) {
                    printWriter.print('i');
                    printWriter.print(n - 99000);
                } else {
                    printWriter.print("ai");
                    printWriter.print(n - 90000);
                }
            } else if (n >= 10000) {
                printWriter.print('a');
                printWriter.print(n - 10000);
            } else {
                printWriter.print('s');
                printWriter.print(n);
            }
        }
    }

    public static void formatUid(StringBuilder stringBuilder, int n) {
        if (n < 10000) {
            stringBuilder.append(n);
        } else {
            stringBuilder.append('u');
            stringBuilder.append(UserHandle.getUserId(n));
            n = UserHandle.getAppId(n);
            if (UserHandle.isIsolated(n)) {
                if (n > 99000) {
                    stringBuilder.append('i');
                    stringBuilder.append(n - 99000);
                } else {
                    stringBuilder.append("ai");
                    stringBuilder.append(n - 90000);
                }
            } else if (n >= 10000) {
                stringBuilder.append('a');
                stringBuilder.append(n - 10000);
            } else {
                stringBuilder.append('s');
                stringBuilder.append(n);
            }
        }
    }

    @SystemApi
    public static int getAppId(int n) {
        return n % 100000;
    }

    @UnsupportedAppUsage
    public static int getAppIdFromSharedAppGid(int n) {
        if ((n = UserHandle.getAppId(n) + 10000 - 50000) >= 0 && n < 50000) {
            return n;
        }
        return -1;
    }

    public static int getCacheAppGid(int n) {
        return UserHandle.getCacheAppGid(UserHandle.getUserId(n), UserHandle.getAppId(n));
    }

    public static int getCacheAppGid(int n, int n2) {
        if (n2 >= 10000 && n2 <= 19999) {
            return UserHandle.getUid(n, n2 - 10000 + 20000);
        }
        return -1;
    }

    public static int getCallingAppId() {
        return UserHandle.getAppId(Binder.getCallingUid());
    }

    @UnsupportedAppUsage
    public static int getCallingUserId() {
        return UserHandle.getUserId(Binder.getCallingUid());
    }

    public static int getSharedAppGid(int n) {
        return UserHandle.getSharedAppGid(UserHandle.getUserId(n), UserHandle.getAppId(n));
    }

    public static int getSharedAppGid(int n, int n2) {
        if (n2 >= 10000 && n2 <= 19999) {
            return n2 - 10000 + 50000;
        }
        if (n2 >= 0 && n2 <= 10000) {
            return n2;
        }
        return -1;
    }

    @UnsupportedAppUsage
    public static int getUid(int n, int n2) {
        return n * 100000 + n2 % 100000;
    }

    public static int getUserGid(int n) {
        return UserHandle.getUid(n, 9997);
    }

    public static UserHandle getUserHandleForUid(int n) {
        return UserHandle.of(UserHandle.getUserId(n));
    }

    @UnsupportedAppUsage
    public static int getUserId(int n) {
        return n / 100000;
    }

    public static boolean isApp(int n) {
        boolean bl = false;
        if (n > 0) {
            n = UserHandle.getAppId(n);
            boolean bl2 = bl;
            if (n >= 10000) {
                bl2 = bl;
                if (n <= 19999) {
                    bl2 = true;
                }
            }
            return bl2;
        }
        return false;
    }

    public static boolean isCore(int n) {
        boolean bl = false;
        if (n >= 0) {
            if (UserHandle.getAppId(n) < 10000) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    @UnsupportedAppUsage
    public static boolean isIsolated(int n) {
        if (n > 0) {
            return Process.isIsolated(n);
        }
        return false;
    }

    @UnsupportedAppUsage
    public static boolean isSameApp(int n, int n2) {
        boolean bl = UserHandle.getAppId(n) == UserHandle.getAppId(n2);
        return bl;
    }

    public static boolean isSameUser(int n, int n2) {
        boolean bl = UserHandle.getUserId(n) == UserHandle.getUserId(n2);
        return bl;
    }

    @SystemApi
    public static int myUserId() {
        return UserHandle.getUserId(Process.myUid());
    }

    @SystemApi
    public static UserHandle of(int n) {
        UserHandle userHandle = n == 0 ? SYSTEM : new UserHandle(n);
        return userHandle;
    }

    public static int parseUserArg(String string2) {
        int n;
        if ("all".equals(string2)) {
            n = -1;
        } else if (!"current".equals(string2) && !"cur".equals(string2)) {
            try {
                n = Integer.parseInt(string2);
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Bad user number: ");
                stringBuilder.append(string2);
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        } else {
            n = -2;
        }
        return n;
    }

    public static UserHandle readFromParcel(Parcel object) {
        int n = ((Parcel)object).readInt();
        object = n != -10000 ? new UserHandle(n) : null;
        return object;
    }

    public static void writeToParcel(UserHandle userHandle, Parcel parcel) {
        if (userHandle != null) {
            userHandle.writeToParcel(parcel, 0);
        } else {
            parcel.writeInt(-10000);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = false;
        if (object != null) {
            try {
                object = (UserHandle)object;
                int n = this.mHandle;
                int n2 = ((UserHandle)object).mHandle;
                if (n == n2) {
                    bl = true;
                }
                return bl;
            }
            catch (ClassCastException classCastException) {
            }
        }
        return false;
    }

    @SystemApi
    public int getIdentifier() {
        return this.mHandle;
    }

    public int hashCode() {
        return this.mHandle;
    }

    @SystemApi
    @Deprecated
    public boolean isOwner() {
        return this.equals(OWNER);
    }

    @SystemApi
    public boolean isSystem() {
        return this.equals(SYSTEM);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UserHandle{");
        stringBuilder.append(this.mHandle);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mHandle);
    }

}

