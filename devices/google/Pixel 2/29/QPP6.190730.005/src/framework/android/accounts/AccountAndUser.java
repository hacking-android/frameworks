/*
 * Decompiled with CFR 0.145.
 */
package android.accounts;

import android.accounts.Account;
import android.annotation.UnsupportedAppUsage;

public class AccountAndUser {
    @UnsupportedAppUsage
    public Account account;
    @UnsupportedAppUsage
    public int userId;

    @UnsupportedAppUsage
    public AccountAndUser(Account account, int n) {
        this.account = account;
        this.userId = n;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof AccountAndUser)) {
            return false;
        }
        object = (AccountAndUser)object;
        if (!this.account.equals(((AccountAndUser)object).account) || this.userId != ((AccountAndUser)object).userId) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        return this.account.hashCode() + this.userId;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.account.toString());
        stringBuilder.append(" u");
        stringBuilder.append(this.userId);
        return stringBuilder.toString();
    }
}

