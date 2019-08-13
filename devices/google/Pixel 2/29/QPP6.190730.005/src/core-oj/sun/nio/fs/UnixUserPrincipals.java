/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.UserPrincipal;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.security.Permission;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.Util;

class UnixUserPrincipals {
    static final User SPECIAL_EVERYONE;
    static final User SPECIAL_GROUP;
    static final User SPECIAL_OWNER;

    static {
        SPECIAL_OWNER = UnixUserPrincipals.createSpecial("OWNER@");
        SPECIAL_GROUP = UnixUserPrincipals.createSpecial("GROUP@");
        SPECIAL_EVERYONE = UnixUserPrincipals.createSpecial("EVERYONE@");
    }

    UnixUserPrincipals() {
    }

    private static User createSpecial(String string) {
        return new User(-1, string);
    }

    static Group fromGid(int n) {
        String string;
        try {
            string = Util.toString(UnixNativeDispatcher.getgrgid(n));
        }
        catch (UnixException unixException) {
            string = Integer.toString(n);
        }
        return new Group(n, string);
    }

    static User fromUid(int n) {
        String string;
        try {
            string = Util.toString(UnixNativeDispatcher.getpwuid(n));
        }
        catch (UnixException unixException) {
            string = Integer.toString(n);
        }
        return new User(n, string);
    }

    static GroupPrincipal lookupGroup(String string) throws IOException {
        return new Group(UnixUserPrincipals.lookupName(string, true), string);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static int lookupName(String var0, boolean var1_1) throws IOException {
        block5 : {
            var2_2 = System.getSecurityManager();
            if (var2_2 != null) {
                var2_2.checkPermission(new RuntimePermission("lookupUserInformation"));
            }
            if (!var1_1) ** GOTO lbl8
            var3_4 = UnixNativeDispatcher.getgrnam(var0);
            break block5;
lbl8: // 1 sources:
            var3_4 = UnixNativeDispatcher.getpwnam(var0);
        }
        var4_5 = var3_4;
        if (var3_4 != -1) return var4_5;
        try {
            return Integer.parseInt(var0);
        }
        catch (NumberFormatException var2_3) {
            throw new UserPrincipalNotFoundException(var0);
        }
        catch (UnixException var5_6) {
            var2_2 = new StringBuilder();
            var2_2.append(var0);
            var2_2.append(": ");
            var2_2.append(var5_6.errorString());
            throw new IOException(var2_2.toString());
        }
    }

    static UserPrincipal lookupUser(String string) throws IOException {
        if (string.equals(SPECIAL_OWNER.getName())) {
            return SPECIAL_OWNER;
        }
        if (string.equals(SPECIAL_GROUP.getName())) {
            return SPECIAL_GROUP;
        }
        if (string.equals(SPECIAL_EVERYONE.getName())) {
            return SPECIAL_EVERYONE;
        }
        return new User(UnixUserPrincipals.lookupName(string, false), string);
    }

    static class Group
    extends User
    implements GroupPrincipal {
        Group(int n, String string) {
            super(n, true, string);
        }
    }

    static class User
    implements UserPrincipal {
        private final int id;
        private final boolean isGroup;
        private final String name;

        User(int n, String string) {
            this(n, false, string);
        }

        private User(int n, boolean bl, String string) {
            this.id = n;
            this.isGroup = bl;
            this.name = string;
        }

        @Override
        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof User)) {
                return false;
            }
            object = (User)object;
            int n = this.id;
            int n2 = ((User)object).id;
            if (n == n2 && this.isGroup == ((User)object).isGroup) {
                if (n == -1 && n2 == -1) {
                    return this.name.equals(((User)object).name);
                }
                return true;
            }
            return false;
        }

        @Override
        public String getName() {
            return this.name;
        }

        int gid() {
            if (this.isGroup) {
                return this.id;
            }
            throw new AssertionError();
        }

        @Override
        public int hashCode() {
            int n = this.id;
            if (n == -1) {
                n = this.name.hashCode();
            }
            return n;
        }

        boolean isSpecial() {
            boolean bl = this.id == -1;
            return bl;
        }

        @Override
        public String toString() {
            return this.name;
        }

        int uid() {
            if (!this.isGroup) {
                return this.id;
            }
            throw new AssertionError();
        }
    }

}

