/*
 * Decompiled with CFR 0.145.
 */
package android.view.inspector;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class IntFlagMapping {
    private final List<Flag> mFlags = new ArrayList<Flag>();

    public void add(int n, int n2, String string2) {
        this.mFlags.add(new Flag(n, n2, string2));
    }

    public Set<String> get(int n) {
        HashSet<String> hashSet = new HashSet<String>(this.mFlags.size());
        for (Flag flag : this.mFlags) {
            if (!flag.isEnabledFor(n)) continue;
            hashSet.add(flag.mName);
        }
        return Collections.unmodifiableSet(hashSet);
    }

    private static final class Flag {
        private final int mMask;
        private final String mName;
        private final int mTarget;

        private Flag(int n, int n2, String string2) {
            this.mTarget = n2;
            this.mMask = n;
            this.mName = Objects.requireNonNull(string2);
        }

        private boolean isEnabledFor(int n) {
            boolean bl = (this.mMask & n) == this.mTarget;
            return bl;
        }
    }

}

