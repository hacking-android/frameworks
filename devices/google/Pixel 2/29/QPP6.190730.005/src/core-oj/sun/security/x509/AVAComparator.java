/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.util.Comparator;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AVA;

class AVAComparator
implements Comparator<AVA> {
    private static final Comparator<AVA> INSTANCE = new AVAComparator();

    private AVAComparator() {
    }

    static Comparator<AVA> getInstance() {
        return INSTANCE;
    }

    @Override
    public int compare(AVA arrn, AVA arrn2) {
        int n;
        boolean bl = arrn.hasRFC2253Keyword();
        boolean bl2 = arrn2.hasRFC2253Keyword();
        if (bl) {
            if (bl2) {
                return arrn.toRFC2253CanonicalString().compareTo(arrn2.toRFC2253CanonicalString());
            }
            return -1;
        }
        if (bl2) {
            return 1;
        }
        arrn = arrn.getObjectIdentifier().toIntArray();
        arrn2 = arrn2.getObjectIdentifier().toIntArray();
        int n2 = arrn.length > arrn2.length ? arrn2.length : arrn.length;
        for (n = 0; n < n2 && arrn[n] == arrn2[n]; ++n) {
        }
        n2 = n == n2 ? arrn.length - arrn2.length : arrn[n] - arrn2[n];
        return n2;
    }
}

