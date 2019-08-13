/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class PosixFilePermissions {
    private PosixFilePermissions() {
    }

    public static FileAttribute<Set<PosixFilePermission>> asFileAttribute(final Set<PosixFilePermission> set) {
        set = new HashSet<PosixFilePermission>(set);
        Iterator<PosixFilePermission> iterator = set.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() != null) continue;
            throw new NullPointerException();
        }
        return new FileAttribute<Set<PosixFilePermission>>(){

            @Override
            public String name() {
                return "posix:permissions";
            }

            @Override
            public Set<PosixFilePermission> value() {
                return Collections.unmodifiableSet(set);
            }
        };
    }

    public static Set<PosixFilePermission> fromString(String string) {
        if (string.length() == 9) {
            EnumSet<PosixFilePermission> enumSet = EnumSet.noneOf(PosixFilePermission.class);
            if (PosixFilePermissions.isR(string.charAt(0))) {
                enumSet.add(PosixFilePermission.OWNER_READ);
            }
            if (PosixFilePermissions.isW(string.charAt(1))) {
                enumSet.add(PosixFilePermission.OWNER_WRITE);
            }
            if (PosixFilePermissions.isX(string.charAt(2))) {
                enumSet.add(PosixFilePermission.OWNER_EXECUTE);
            }
            if (PosixFilePermissions.isR(string.charAt(3))) {
                enumSet.add(PosixFilePermission.GROUP_READ);
            }
            if (PosixFilePermissions.isW(string.charAt(4))) {
                enumSet.add(PosixFilePermission.GROUP_WRITE);
            }
            if (PosixFilePermissions.isX(string.charAt(5))) {
                enumSet.add(PosixFilePermission.GROUP_EXECUTE);
            }
            if (PosixFilePermissions.isR(string.charAt(6))) {
                enumSet.add(PosixFilePermission.OTHERS_READ);
            }
            if (PosixFilePermissions.isW(string.charAt(7))) {
                enumSet.add(PosixFilePermission.OTHERS_WRITE);
            }
            if (PosixFilePermissions.isX(string.charAt(8))) {
                enumSet.add(PosixFilePermission.OTHERS_EXECUTE);
            }
            return enumSet;
        }
        throw new IllegalArgumentException("Invalid mode");
    }

    private static boolean isR(char c) {
        return PosixFilePermissions.isSet(c, 'r');
    }

    private static boolean isSet(char c, char c2) {
        if (c == c2) {
            return true;
        }
        if (c == '-') {
            return false;
        }
        throw new IllegalArgumentException("Invalid mode");
    }

    private static boolean isW(char c) {
        return PosixFilePermissions.isSet(c, 'w');
    }

    private static boolean isX(char c) {
        return PosixFilePermissions.isSet(c, 'x');
    }

    public static String toString(Set<PosixFilePermission> set) {
        StringBuilder stringBuilder = new StringBuilder(9);
        PosixFilePermissions.writeBits(stringBuilder, set.contains((Object)PosixFilePermission.OWNER_READ), set.contains((Object)PosixFilePermission.OWNER_WRITE), set.contains((Object)PosixFilePermission.OWNER_EXECUTE));
        PosixFilePermissions.writeBits(stringBuilder, set.contains((Object)PosixFilePermission.GROUP_READ), set.contains((Object)PosixFilePermission.GROUP_WRITE), set.contains((Object)PosixFilePermission.GROUP_EXECUTE));
        PosixFilePermissions.writeBits(stringBuilder, set.contains((Object)PosixFilePermission.OTHERS_READ), set.contains((Object)PosixFilePermission.OTHERS_WRITE), set.contains((Object)PosixFilePermission.OTHERS_EXECUTE));
        return stringBuilder.toString();
    }

    private static void writeBits(StringBuilder stringBuilder, boolean bl, boolean bl2, boolean bl3) {
        if (bl) {
            stringBuilder.append('r');
        } else {
            stringBuilder.append('-');
        }
        if (bl2) {
            stringBuilder.append('w');
        } else {
            stringBuilder.append('-');
        }
        if (bl3) {
            stringBuilder.append('x');
        } else {
            stringBuilder.append('-');
        }
    }

}

