/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.internal;

import com.android.i18n.phonenumbers.Phonemetadata;
import com.android.i18n.phonenumbers.internal.MatcherApi;
import com.android.i18n.phonenumbers.internal.RegexCache;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class RegexBasedMatcher
implements MatcherApi {
    private final RegexCache regexCache = new RegexCache(100);

    private RegexBasedMatcher() {
    }

    public static MatcherApi create() {
        return new RegexBasedMatcher();
    }

    private static boolean match(CharSequence object, Pattern pattern, boolean bl) {
        block1 : {
            if (!((Matcher)(object = pattern.matcher((CharSequence)object))).lookingAt()) {
                return false;
            }
            if (!((Matcher)object).matches()) break block1;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean matchNationalNumber(CharSequence charSequence, Phonemetadata.PhoneNumberDesc object, boolean bl) {
        if (((String)(object = ((Phonemetadata.PhoneNumberDesc)object).getNationalNumberPattern())).length() == 0) {
            return false;
        }
        return RegexBasedMatcher.match(charSequence, this.regexCache.getPatternForRegex((String)object), bl);
    }
}

