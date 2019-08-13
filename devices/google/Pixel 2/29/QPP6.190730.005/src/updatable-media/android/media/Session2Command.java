/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package android.media;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import java.util.Objects;

public final class Session2Command
implements Parcelable {
    public static final int COMMAND_CODE_CUSTOM = 0;
    public static final Parcelable.Creator<Session2Command> CREATOR = new Parcelable.Creator<Session2Command>(){

        public Session2Command createFromParcel(Parcel parcel) {
            return new Session2Command(parcel);
        }

        public Session2Command[] newArray(int n) {
            return new Session2Command[n];
        }
    };
    private final int mCommandCode;
    private final String mCustomAction;
    private final Bundle mCustomExtras;

    public Session2Command(int n) {
        if (n != 0) {
            this.mCommandCode = n;
            this.mCustomAction = null;
            this.mCustomExtras = null;
            return;
        }
        throw new IllegalArgumentException("commandCode shouldn't be COMMAND_CODE_CUSTOM");
    }

    Session2Command(Parcel parcel) {
        this.mCommandCode = parcel.readInt();
        this.mCustomAction = parcel.readString();
        this.mCustomExtras = parcel.readBundle();
    }

    public Session2Command(String string, Bundle bundle) {
        if (string != null) {
            this.mCommandCode = 0;
            this.mCustomAction = string;
            this.mCustomExtras = bundle;
            return;
        }
        throw new IllegalArgumentException("action shouldn't be null");
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl;
        block1 : {
            boolean bl2 = object instanceof Session2Command;
            bl = false;
            if (!bl2) {
                return false;
            }
            object = (Session2Command)object;
            if (this.mCommandCode != ((Session2Command)object).mCommandCode || !TextUtils.equals((CharSequence)this.mCustomAction, (CharSequence)((Session2Command)object).mCustomAction)) break block1;
            bl = true;
        }
        return bl;
    }

    public int getCommandCode() {
        return this.mCommandCode;
    }

    public String getCustomAction() {
        return this.mCustomAction;
    }

    public Bundle getCustomExtras() {
        return this.mCustomExtras;
    }

    public int hashCode() {
        return Objects.hash(this.mCustomAction, this.mCommandCode);
    }

    public void writeToParcel(Parcel parcel, int n) {
        if (parcel != null) {
            parcel.writeInt(this.mCommandCode);
            parcel.writeString(this.mCustomAction);
            parcel.writeBundle(this.mCustomExtras);
            return;
        }
        throw new IllegalArgumentException("parcel shouldn't be null");
    }

    public static final class Result {
        public static final int RESULT_ERROR_UNKNOWN_ERROR = -1;
        public static final int RESULT_INFO_SKIPPED = 1;
        public static final int RESULT_SUCCESS = 0;
        private final int mResultCode;
        private final Bundle mResultData;

        public Result(int n, Bundle bundle) {
            this.mResultCode = n;
            this.mResultData = bundle;
        }

        public int getResultCode() {
            return this.mResultCode;
        }

        public Bundle getResultData() {
            return this.mResultData;
        }
    }

}

