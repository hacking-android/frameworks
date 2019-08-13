/*
 * Decompiled with CFR 0.145.
 */
package android.service.notification;

import android.content.Context;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

public final class Condition
implements Parcelable {
    public static final Parcelable.Creator<Condition> CREATOR = new Parcelable.Creator<Condition>(){

        @Override
        public Condition createFromParcel(Parcel parcel) {
            return new Condition(parcel);
        }

        public Condition[] newArray(int n) {
            return new Condition[n];
        }
    };
    public static final int FLAG_RELEVANT_ALWAYS = 2;
    public static final int FLAG_RELEVANT_NOW = 1;
    public static final String SCHEME = "condition";
    public static final int STATE_ERROR = 3;
    public static final int STATE_FALSE = 0;
    public static final int STATE_TRUE = 1;
    public static final int STATE_UNKNOWN = 2;
    public final int flags;
    public final int icon;
    public final Uri id;
    public final String line1;
    public final String line2;
    public final int state;
    public final String summary;

    public Condition(Uri uri, String string2, int n) {
        this(uri, string2, "", "", -1, n, 2);
    }

    public Condition(Uri object, String string2, String string3, String string4, int n, int n2, int n3) {
        if (object != null) {
            if (string2 != null) {
                if (Condition.isValidState(n2)) {
                    this.id = object;
                    this.summary = string2;
                    this.line1 = string3;
                    this.line2 = string4;
                    this.icon = n;
                    this.state = n2;
                    this.flags = n3;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("state is invalid: ");
                ((StringBuilder)object).append(n2);
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            throw new IllegalArgumentException("summary is required");
        }
        throw new IllegalArgumentException("id is required");
    }

    public Condition(Parcel parcel) {
        this((Uri)parcel.readParcelable(Condition.class.getClassLoader()), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt());
    }

    public static boolean isValidId(Uri uri, String string2) {
        boolean bl = uri != null && SCHEME.equals(uri.getScheme()) && string2.equals(uri.getAuthority());
        return bl;
    }

    private static boolean isValidState(int n) {
        boolean bl = n >= 0 && n <= 3;
        return bl;
    }

    public static Uri.Builder newId(Context context) {
        return new Uri.Builder().scheme(SCHEME).authority(context.getPackageName());
    }

    public static String relevanceToString(int n) {
        boolean bl = false;
        boolean bl2 = (n & 1) != 0;
        if ((n & 2) != 0) {
            bl = true;
        }
        if (!bl2 && !bl) {
            return "NONE";
        }
        if (bl2 && bl) {
            return "NOW, ALWAYS";
        }
        String string2 = bl2 ? "NOW" : "ALWAYS";
        return string2;
    }

    public static String stateToString(int n) {
        if (n == 0) {
            return "STATE_FALSE";
        }
        if (n == 1) {
            return "STATE_TRUE";
        }
        if (n == 2) {
            return "STATE_UNKNOWN";
        }
        if (n == 3) {
            return "STATE_ERROR";
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("state is invalid: ");
        stringBuilder.append(n);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Condition copy() {
        Parcel parcel = Parcel.obtain();
        try {
            this.writeToParcel(parcel, 0);
            parcel.setDataPosition(0);
            Condition condition = new Condition(parcel);
            return condition;
        }
        finally {
            parcel.recycle();
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Condition)) {
            return false;
        }
        boolean bl = true;
        if (object == this) {
            return true;
        }
        object = (Condition)object;
        if (!(Objects.equals(((Condition)object).id, this.id) && Objects.equals(((Condition)object).summary, this.summary) && Objects.equals(((Condition)object).line1, this.line1) && Objects.equals(((Condition)object).line2, this.line2) && ((Condition)object).icon == this.icon && ((Condition)object).state == this.state && ((Condition)object).flags == this.flags)) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return Objects.hash(this.id, this.summary, this.line1, this.line2, this.icon, this.state, this.flags);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(Condition.class.getSimpleName());
        stringBuilder.append('[');
        stringBuilder.append("state=");
        stringBuilder.append(Condition.stateToString(this.state));
        stringBuilder.append(",id=");
        stringBuilder.append(this.id);
        stringBuilder.append(",summary=");
        stringBuilder.append(this.summary);
        stringBuilder.append(",line1=");
        stringBuilder.append(this.line1);
        stringBuilder.append(",line2=");
        stringBuilder.append(this.line2);
        stringBuilder.append(",icon=");
        stringBuilder.append(this.icon);
        stringBuilder.append(",flags=");
        stringBuilder.append(this.flags);
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.id, 0);
        parcel.writeString(this.summary);
        parcel.writeString(this.line1);
        parcel.writeString(this.line2);
        parcel.writeInt(this.icon);
        parcel.writeInt(this.state);
        parcel.writeInt(this.flags);
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        l = protoOutputStream.start(l);
        protoOutputStream.write(1138166333441L, this.id.toString());
        protoOutputStream.write(1138166333442L, this.summary);
        protoOutputStream.write(1138166333443L, this.line1);
        protoOutputStream.write(1138166333444L, this.line2);
        protoOutputStream.write(1120986464261L, this.icon);
        protoOutputStream.write(1159641169926L, this.state);
        protoOutputStream.write(1120986464263L, this.flags);
        protoOutputStream.end(l);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface State {
    }

}

