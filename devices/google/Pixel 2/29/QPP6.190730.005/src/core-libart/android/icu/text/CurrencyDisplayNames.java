/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.CurrencyData;
import android.icu.util.ULocale;
import java.util.Locale;
import java.util.Map;

public abstract class CurrencyDisplayNames {
    @Deprecated
    protected CurrencyDisplayNames() {
    }

    public static CurrencyDisplayNames getInstance(ULocale uLocale) {
        return CurrencyData.provider.getInstance(uLocale, true);
    }

    public static CurrencyDisplayNames getInstance(ULocale uLocale, boolean bl) {
        return CurrencyData.provider.getInstance(uLocale, bl ^ true);
    }

    public static CurrencyDisplayNames getInstance(Locale locale) {
        return CurrencyDisplayNames.getInstance(locale, false);
    }

    public static CurrencyDisplayNames getInstance(Locale locale, boolean bl) {
        return CurrencyDisplayNames.getInstance(ULocale.forLocale(locale), bl);
    }

    @Deprecated
    public static boolean hasData() {
        return CurrencyData.provider.hasData();
    }

    public abstract String getName(String var1);

    public abstract String getNarrowSymbol(String var1);

    public abstract String getPluralName(String var1, String var2);

    public abstract String getSymbol(String var1);

    public abstract ULocale getULocale();

    public abstract Map<String, String> nameMap();

    public abstract Map<String, String> symbolMap();
}

