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

public class DateTimeKeyListener
extends NumberKeyListener {
    public static final char[] CHARACTERS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'm', 'p', ':', '/', '-', ' '};
    private static final String SKELETON_12HOUR = "yMdhms";
    private static final String SKELETON_24HOUR = "yMdHms";
    private static final String SYMBOLS_TO_IGNORE = "yMLdahHKkms";
    @GuardedBy(value={"sLock"})
    private static final HashMap<Locale, DateTimeKeyListener> sInstanceCache;
    private static final Object sLock;
    private final char[] mCharacters;
    private final boolean mNeedsAdvancedInput;

    static {
        sLock = new Object();
        sInstanceCache = new HashMap();
    }

    @Deprecated
    public DateTimeKeyListener() {
        this(null);
    }

    public DateTimeKeyListener(Locale locale) {
        LinkedHashSet<Character> linkedHashSet = new LinkedHashSet<Character>();
        boolean bl = NumberKeyListener.addDigits(linkedHashSet, locale) && NumberKeyListener.addAmPmChars(linkedHashSet, locale) && NumberKeyListener.addFormatCharsFromSkeleton(linkedHashSet, locale, SKELETON_12HOUR, SYMBOLS_TO_IGNORE) && NumberKeyListener.addFormatCharsFromSkeleton(linkedHashSet, locale, SKELETON_24HOUR, SYMBOLS_TO_IGNORE);
        if (bl) {
            this.mCharacters = NumberKeyListener.collectionToArray(linkedHashSet);
            this.mNeedsAdvancedInput = locale != null && "en".equals(locale.getLanguage()) ? false : true ^ ArrayUtils.containsAll(CHARACTERS, this.mCharacters);
        } else {
            this.mCharacters = CHARACTERS;
            this.mNeedsAdvancedInput = false;
        }
    }

    @Deprecated
    public static DateTimeKeyListener getInstance() {
        return DateTimeKeyListener.getInstance(null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DateTimeKeyListener getInstance(Locale locale) {
        Object object = sLock;
        synchronized (object) {
            DateTimeKeyListener dateTimeKeyListener;
            DateTimeKeyListener dateTimeKeyListener2 = dateTimeKeyListener = sInstanceCache.get(locale);
            if (dateTimeKeyListener == null) {
                dateTimeKeyListener2 = new DateTimeKeyListener(locale);
                sInstanceCache.put(locale, dateTimeKeyListener2);
            }
            return dateTimeKeyListener2;
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
        return 4;
    }
}

