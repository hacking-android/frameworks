/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.icu.text.Collator
 *  android.icu.text.RuleBasedCollator
 *  libcore.icu.ICU
 */
package java.text;

import java.text.CollationKey;
import java.text.RuleBasedCollator;
import java.util.Comparator;
import java.util.Locale;
import libcore.icu.ICU;

public abstract class Collator
implements Comparator<Object>,
Cloneable {
    public static final int CANONICAL_DECOMPOSITION = 1;
    public static final int FULL_DECOMPOSITION = 2;
    public static final int IDENTICAL = 3;
    public static final int NO_DECOMPOSITION = 0;
    public static final int PRIMARY = 0;
    public static final int SECONDARY = 1;
    public static final int TERTIARY = 2;
    android.icu.text.Collator icuColl;

    protected Collator() {
        this.icuColl = android.icu.text.RuleBasedCollator.getInstance((Locale)Locale.getDefault());
    }

    Collator(android.icu.text.Collator collator) {
        this.icuColl = collator;
    }

    private int decompositionMode_ICU_Java(int n) {
        int n2 = n;
        n = n != 16 ? (n != 17 ? n2 : 1) : 0;
        return n;
    }

    private int decompositionMode_Java_ICU(int n) {
        if (n != 0) {
            if (n == 1) {
                return 17;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Bad mode: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        return 16;
    }

    public static Locale[] getAvailableLocales() {
        synchronized (Collator.class) {
            Locale[] arrlocale = ICU.getAvailableCollatorLocales();
            return arrlocale;
        }
    }

    public static Collator getInstance() {
        synchronized (Collator.class) {
            Collator collator = Collator.getInstance(Locale.getDefault());
            return collator;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Collator getInstance(Locale object) {
        synchronized (Collator.class) {
            if (object != null) {
                return new RuleBasedCollator((android.icu.text.RuleBasedCollator)android.icu.text.Collator.getInstance((Locale)object));
            }
            try {
                object = new NullPointerException("locale == null");
                throw object;
            }
            catch (Throwable throwable) {}
            throw throwable;
        }
    }

    public Object clone() {
        try {
            Collator collator = (Collator)super.clone();
            collator.icuColl = (android.icu.text.Collator)this.icuColl.clone();
            return collator;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new AssertionError(cloneNotSupportedException);
        }
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((String)object, (String)object2);
    }

    @Override
    public abstract int compare(String var1, String var2);

    @Override
    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        Collator collator = (Collator)object;
        object = this.icuColl;
        if (object == null) {
            if (collator.icuColl != null) {
                bl = false;
            }
        } else {
            bl = object.equals((Object)collator.icuColl);
        }
        return bl;
    }

    public boolean equals(String string, String string2) {
        boolean bl = this.compare(string, string2) == 0;
        return bl;
    }

    public abstract CollationKey getCollationKey(String var1);

    public int getDecomposition() {
        synchronized (this) {
            int n = this.decompositionMode_ICU_Java(this.icuColl.getDecomposition());
            return n;
        }
    }

    public int getStrength() {
        synchronized (this) {
            int n;
            block3 : {
                n = this.icuColl.getStrength();
                if (n != 15) break block3;
                n = 3;
            }
            return n;
        }
    }

    public abstract int hashCode();

    public void setDecomposition(int n) {
        synchronized (this) {
            this.icuColl.setDecomposition(this.decompositionMode_Java_ICU(n));
            return;
        }
    }

    public void setStrength(int n) {
        synchronized (this) {
            int n2 = n;
            if (n == 3) {
                n2 = 15;
            }
            this.icuColl.setStrength(n2);
            return;
        }
    }
}

