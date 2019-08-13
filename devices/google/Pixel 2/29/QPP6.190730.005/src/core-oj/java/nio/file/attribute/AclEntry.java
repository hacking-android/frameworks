/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.nio.file.attribute.AclEntryFlag;
import java.nio.file.attribute.AclEntryPermission;
import java.nio.file.attribute.AclEntryType;
import java.nio.file.attribute.UserPrincipal;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class AclEntry {
    private final Set<AclEntryFlag> flags;
    private volatile int hash;
    private final Set<AclEntryPermission> perms;
    private final AclEntryType type;
    private final UserPrincipal who;

    private AclEntry(AclEntryType aclEntryType, UserPrincipal userPrincipal, Set<AclEntryPermission> set, Set<AclEntryFlag> set2) {
        this.type = aclEntryType;
        this.who = userPrincipal;
        this.perms = set;
        this.flags = set2;
    }

    private static int hash(int n, Object object) {
        return n * 127 + object.hashCode();
    }

    public static Builder newBuilder() {
        return new Builder(null, null, Collections.emptySet(), Collections.emptySet());
    }

    public static Builder newBuilder(AclEntry aclEntry) {
        return new Builder(aclEntry.type, aclEntry.who, aclEntry.perms, aclEntry.flags);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object != null && object instanceof AclEntry) {
            object = (AclEntry)object;
            if (this.type != ((AclEntry)object).type) {
                return false;
            }
            if (!this.who.equals(((AclEntry)object).who)) {
                return false;
            }
            if (!this.perms.equals(((AclEntry)object).perms)) {
                return false;
            }
            return this.flags.equals(((AclEntry)object).flags);
        }
        return false;
    }

    public Set<AclEntryFlag> flags() {
        return new HashSet<AclEntryFlag>(this.flags);
    }

    public int hashCode() {
        if (this.hash != 0) {
            return this.hash;
        }
        this.hash = AclEntry.hash(AclEntry.hash(AclEntry.hash(this.type.hashCode(), this.who), this.perms), this.flags);
        return this.hash;
    }

    public Set<AclEntryPermission> permissions() {
        return new HashSet<AclEntryPermission>(this.perms);
    }

    public UserPrincipal principal() {
        return this.who;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.who.getName());
        stringBuilder.append(':');
        Iterator<Enum> iterator = this.perms.iterator();
        while (iterator.hasNext()) {
            stringBuilder.append(iterator.next().name());
            stringBuilder.append('/');
        }
        stringBuilder.setLength(stringBuilder.length() - 1);
        stringBuilder.append(':');
        if (!this.flags.isEmpty()) {
            iterator = this.flags.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(((AclEntryFlag)iterator.next()).name());
                stringBuilder.append('/');
            }
            stringBuilder.setLength(stringBuilder.length() - 1);
            stringBuilder.append(':');
        }
        stringBuilder.append(this.type.name());
        return stringBuilder.toString();
    }

    public AclEntryType type() {
        return this.type;
    }

    public static final class Builder {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Set<AclEntryFlag> flags;
        private Set<AclEntryPermission> perms;
        private AclEntryType type;
        private UserPrincipal who;

        private Builder(AclEntryType aclEntryType, UserPrincipal userPrincipal, Set<AclEntryPermission> set, Set<AclEntryFlag> set2) {
            this.type = aclEntryType;
            this.who = userPrincipal;
            this.perms = set;
            this.flags = set2;
        }

        private static void checkSet(Set<?> object, Class<?> class_) {
            object = object.iterator();
            while (object.hasNext()) {
                Object e = object.next();
                if (e != null) {
                    class_.cast(e);
                    continue;
                }
                throw new NullPointerException();
            }
        }

        public AclEntry build() {
            AclEntryType aclEntryType = this.type;
            if (aclEntryType != null) {
                UserPrincipal userPrincipal = this.who;
                if (userPrincipal != null) {
                    return new AclEntry(aclEntryType, userPrincipal, this.perms, this.flags);
                }
                throw new IllegalStateException("Missing who component");
            }
            throw new IllegalStateException("Missing type component");
        }

        public Builder setFlags(Set<AclEntryFlag> set) {
            if (set.isEmpty()) {
                set = Collections.emptySet();
            } else {
                set = EnumSet.copyOf(set);
                Builder.checkSet(set, AclEntryFlag.class);
            }
            this.flags = set;
            return this;
        }

        public Builder setFlags(AclEntryFlag ... arraclEntryFlag) {
            EnumSet<AclEntryFlag> enumSet = EnumSet.noneOf(AclEntryFlag.class);
            for (AclEntryFlag aclEntryFlag : arraclEntryFlag) {
                if (aclEntryFlag != null) {
                    enumSet.add(aclEntryFlag);
                    continue;
                }
                throw new NullPointerException();
            }
            this.flags = enumSet;
            return this;
        }

        public Builder setPermissions(Set<AclEntryPermission> set) {
            if (set.isEmpty()) {
                set = Collections.emptySet();
            } else {
                set = EnumSet.copyOf(set);
                Builder.checkSet(set, AclEntryPermission.class);
            }
            this.perms = set;
            return this;
        }

        public Builder setPermissions(AclEntryPermission ... arraclEntryPermission) {
            EnumSet<AclEntryPermission> enumSet = EnumSet.noneOf(AclEntryPermission.class);
            for (AclEntryPermission aclEntryPermission : arraclEntryPermission) {
                if (aclEntryPermission != null) {
                    enumSet.add(aclEntryPermission);
                    continue;
                }
                throw new NullPointerException();
            }
            this.perms = enumSet;
            return this;
        }

        public Builder setPrincipal(UserPrincipal userPrincipal) {
            if (userPrincipal != null) {
                this.who = userPrincipal;
                return this;
            }
            throw new NullPointerException();
        }

        public Builder setType(AclEntryType aclEntryType) {
            if (aclEntryType != null) {
                this.type = aclEntryType;
                return this;
            }
            throw new NullPointerException();
        }
    }

}

