/*
 * Decompiled with CFR 0.145.
 */
package android.content;

import android.accounts.Account;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;
import java.util.Set;

public class PeriodicSync
implements Parcelable {
    public static final Parcelable.Creator<PeriodicSync> CREATOR = new Parcelable.Creator<PeriodicSync>(){

        @Override
        public PeriodicSync createFromParcel(Parcel parcel) {
            return new PeriodicSync(parcel);
        }

        public PeriodicSync[] newArray(int n) {
            return new PeriodicSync[n];
        }
    };
    public final Account account;
    public final String authority;
    public final Bundle extras;
    public final long flexTime;
    public final long period;

    public PeriodicSync(Account account, String string2, Bundle bundle, long l) {
        this.account = account;
        this.authority = string2;
        this.extras = bundle == null ? new Bundle() : new Bundle(bundle);
        this.period = l;
        this.flexTime = 0L;
    }

    public PeriodicSync(Account account, String string2, Bundle bundle, long l, long l2) {
        this.account = account;
        this.authority = string2;
        this.extras = new Bundle(bundle);
        this.period = l;
        this.flexTime = l2;
    }

    public PeriodicSync(PeriodicSync periodicSync) {
        this.account = periodicSync.account;
        this.authority = periodicSync.authority;
        this.extras = new Bundle(periodicSync.extras);
        this.period = periodicSync.period;
        this.flexTime = periodicSync.flexTime;
    }

    private PeriodicSync(Parcel parcel) {
        this.account = (Account)parcel.readParcelable(null);
        this.authority = parcel.readString();
        this.extras = parcel.readBundle();
        this.period = parcel.readLong();
        this.flexTime = parcel.readLong();
    }

    public static boolean syncExtrasEquals(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        if (bundle.isEmpty()) {
            return true;
        }
        for (String string2 : bundle.keySet()) {
            if (!bundle2.containsKey(string2)) {
                return false;
            }
            if (Objects.equals(bundle.get(string2), bundle2.get(string2))) continue;
            return false;
        }
        return true;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof PeriodicSync)) {
            return false;
        }
        object = (PeriodicSync)object;
        if (!(this.account.equals(((PeriodicSync)object).account) && this.authority.equals(((PeriodicSync)object).authority) && this.period == ((PeriodicSync)object).period && PeriodicSync.syncExtrasEquals(this.extras, ((PeriodicSync)object).extras))) {
            bl = false;
        }
        return bl;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("account: ");
        stringBuilder.append(this.account);
        stringBuilder.append(", authority: ");
        stringBuilder.append(this.authority);
        stringBuilder.append(". period: ");
        stringBuilder.append(this.period);
        stringBuilder.append("s , flex: ");
        stringBuilder.append(this.flexTime);
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.account, n);
        parcel.writeString(this.authority);
        parcel.writeBundle(this.extras);
        parcel.writeLong(this.period);
        parcel.writeLong(this.flexTime);
    }

}

