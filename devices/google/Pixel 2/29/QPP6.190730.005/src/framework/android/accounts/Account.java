/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.IAccountManager;
import android.annotation.UnsupportedAppUsage;
import android.os.BadParcelableException;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import com.android.internal.annotations.GuardedBy;
import java.util.Set;

public class Account
implements Parcelable {
    public static final Parcelable.Creator<Account> CREATOR;
    @UnsupportedAppUsage
    private static final String TAG = "Account";
    @GuardedBy(value={"sAccessedAccounts"})
    private static final Set<Account> sAccessedAccounts;
    @UnsupportedAppUsage
    private final String accessId;
    private String mSafeName;
    public final String name;
    public final String type;

    static {
        sAccessedAccounts = new ArraySet<Account>();
        CREATOR = new Parcelable.Creator<Account>(){

            @Override
            public Account createFromParcel(Parcel parcel) {
                return new Account(parcel);
            }

            public Account[] newArray(int n) {
                return new Account[n];
            }
        };
    }

    public Account(Account account, String string2) {
        this(account.name, account.type, string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Account(Parcel set) {
        this.name = ((Parcel)((Object)set)).readString();
        this.type = ((Parcel)((Object)set)).readString();
        if (TextUtils.isEmpty(this.name)) {
            set = new StringBuilder();
            ((StringBuilder)((Object)set)).append("the name must not be empty: ");
            ((StringBuilder)((Object)set)).append(this.name);
            throw new BadParcelableException(((StringBuilder)((Object)set)).toString());
        }
        if (TextUtils.isEmpty(this.type)) {
            set = new StringBuilder();
            ((StringBuilder)((Object)set)).append("the type must not be empty: ");
            ((StringBuilder)((Object)set)).append(this.type);
            throw new BadParcelableException(((StringBuilder)((Object)set)).toString());
        }
        this.accessId = ((Parcel)((Object)set)).readString();
        if (this.accessId != null) {
            set = sAccessedAccounts;
            synchronized (set) {
                boolean bl = sAccessedAccounts.add(this);
                if (bl) {
                    try {
                        IAccountManager.Stub.asInterface(ServiceManager.getService("account")).onAccountAccessed(this.accessId);
                    }
                    catch (RemoteException remoteException) {
                        Log.e(TAG, "Error noting account access", remoteException);
                    }
                }
            }
        }
    }

    public Account(String string2, String string3) {
        this(string2, string3, null);
    }

    public Account(String charSequence, String charSequence2, String string2) {
        if (!TextUtils.isEmpty(charSequence)) {
            if (!TextUtils.isEmpty(charSequence2)) {
                this.name = charSequence;
                this.type = charSequence2;
                this.accessId = string2;
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("the type must not be empty: ");
            ((StringBuilder)charSequence).append((String)charSequence2);
            throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append("the name must not be empty: ");
        ((StringBuilder)charSequence2).append((String)charSequence);
        throw new IllegalArgumentException(((StringBuilder)charSequence2).toString());
    }

    public static String toSafeName(String string2, char c) {
        StringBuilder stringBuilder = new StringBuilder(64);
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            char c2 = string2.charAt(i);
            if (Character.isLetterOrDigit(c2)) {
                stringBuilder.append(c);
                continue;
            }
            stringBuilder.append(c2);
        }
        return stringBuilder.toString();
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
        if (!(object instanceof Account)) {
            return false;
        }
        object = (Account)object;
        if (!this.name.equals(((Account)object).name) || !this.type.equals(((Account)object).type)) {
            bl = false;
        }
        return bl;
    }

    public String getAccessId() {
        return this.accessId;
    }

    public int hashCode() {
        return (17 * 31 + this.name.hashCode()) * 31 + this.type.hashCode();
    }

    public String toSafeString() {
        if (this.mSafeName == null) {
            this.mSafeName = Account.toSafeName(this.name, 'x');
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Account {name=");
        stringBuilder.append(this.mSafeName);
        stringBuilder.append(", type=");
        stringBuilder.append(this.type);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Account {name=");
        stringBuilder.append(this.name);
        stringBuilder.append(", type=");
        stringBuilder.append(this.type);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.name);
        parcel.writeString(this.type);
        parcel.writeString(this.accessId);
    }

}

