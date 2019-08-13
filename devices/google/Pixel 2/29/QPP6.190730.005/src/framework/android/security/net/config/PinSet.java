/*
 * Decompiled with CFR 0.145.
 */
package android.security.net.config;

import android.security.net.config.Pin;
import android.util.ArraySet;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

public final class PinSet {
    public static final PinSet EMPTY_PINSET = new PinSet(Collections.<Pin>emptySet(), Long.MAX_VALUE);
    public final long expirationTime;
    public final Set<Pin> pins;

    public PinSet(Set<Pin> set, long l) {
        if (set != null) {
            this.pins = set;
            this.expirationTime = l;
            return;
        }
        throw new NullPointerException("pins must not be null");
    }

    Set<String> getPinAlgorithms() {
        ArraySet<String> arraySet = new ArraySet<String>();
        Iterator<Pin> iterator = this.pins.iterator();
        while (iterator.hasNext()) {
            arraySet.add(iterator.next().digestAlgorithm);
        }
        return arraySet;
    }
}

