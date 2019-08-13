/*
 * Decompiled with CFR 0.145.
 */
package android.app;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

public final class AuthenticationRequiredException
extends SecurityException
implements Parcelable {
    public static final Parcelable.Creator<AuthenticationRequiredException> CREATOR = new Parcelable.Creator<AuthenticationRequiredException>(){

        @Override
        public AuthenticationRequiredException createFromParcel(Parcel parcel) {
            return new AuthenticationRequiredException(parcel);
        }

        public AuthenticationRequiredException[] newArray(int n) {
            return new AuthenticationRequiredException[n];
        }
    };
    private static final String TAG = "AuthenticationRequiredException";
    private final PendingIntent mUserAction;

    public AuthenticationRequiredException(Parcel parcel) {
        this(new SecurityException(parcel.readString()), PendingIntent.CREATOR.createFromParcel(parcel));
    }

    public AuthenticationRequiredException(Throwable throwable, PendingIntent pendingIntent) {
        super(throwable.getMessage());
        this.mUserAction = Preconditions.checkNotNull(pendingIntent);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public PendingIntent getUserAction() {
        return this.mUserAction;
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.getMessage());
        this.mUserAction.writeToParcel(parcel, n);
    }

}

