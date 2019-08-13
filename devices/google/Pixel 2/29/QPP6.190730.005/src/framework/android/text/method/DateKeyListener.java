/*
 * Decompiled with CFR 0.145.
 */
package android.text.method;

import android.text.method.NumberKeyListener;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.util.ArrayUtils;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Locale;

public class DateKeyListener
extends NumberKeyListener {
    @Deprecated
    public static final char[] CHARACTERS;
    private static final String[] SKELETONS;
    private static final String SYMBOLS_TO_IGNORE = "yMLd";
    @GuardedBy(value={"sLock"})
    private static final HashMap<Locale, DateKeyListener> sInstanceCache;
    private static final Object sLock;
    private final char[] mCharacters;
    private final boolean mNeedsAdvancedInput;

    static {
        SKELETONS = new String[]{"yMd", "yM", "Md"};
        CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '/', '-', '.'};
        sLock = new Object();
        sInstanceCache = new HashMap();
    }

    @Deprecated
    public DateKeyListener() {
        this(null);
    }

    public DateKeyListener(Locale locale) {
        LinkedHashSet<Character> linkedHashSet = new LinkedHashSet<Character>();
        boolean bl = NumberKeyListener.addDigits(linkedHashSet, locale) && NumberKeyListener.addFormatCharsFromSkeletons(linkedHashSet, locale, SKELETONS, SYMBOLS_TO_IGNORE);
        if (bl) {
            this.mCharacters = NumberKeyListener.collectionToArray(linkedHashSet);
            this.mNeedsAdvancedInput = true ^ ArrayUtils.containsAll(CHARACTERS, this.mCharacters);
        } else {
            this.mCharacters = CHARACTERS;
            this.mNeedsAdvancedInput = false;
        }
    }

    @Deprecated
    public static DateKeyListener getInstance() {
        return DateKeyListener.getInstance(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DateKeyListener getInstance(Locale locale) {
        Object object = sLock;
        synchronized (object) {
            DateKeyListener dateKeyListener;
            DateKeyListener dateKeyListener2 = dateKeyListener = sInstanceCache.get(locale);
            if (dateKeyListener == null) {
                dateKeyListener2 = new DateKeyListener(locale);
                sInstanceCache.put(locale, dateKeyListener2);
            }
            return dateKeyListener2;
        }
    }

    @Override
    protected char[] getAcceptedChars() {
        return this.mCharacters;
    }

    @Override
    public int getInputType() {
        if (this.mNeedsAdvancedInput) {
            return 1;
        }
        return 20;
    }
}

