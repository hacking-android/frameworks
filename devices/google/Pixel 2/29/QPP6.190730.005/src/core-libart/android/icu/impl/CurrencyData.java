/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.text.CurrencyDisplayNames;
import android.icu.util.ULocale;
import dalvik.annotation.compat.UnsupportedAppUsage;
import java.util.Collections;
import java.util.Map;

public class CurrencyData {
    public static final CurrencyDisplayInfoProvider provider;

    static {
        CurrencyDisplayInfoProvider currencyDisplayInfoProvider;
        try {
            currencyDisplayInfoProvider = (CurrencyDisplayInfoProvider)Class.forName("android.icu.impl.ICUCurrencyDisplayInfoProvider").newInstance();
        }
        catch (Throwable throwable) {
            currencyDisplayInfoProvider = new CurrencyDisplayInfoProvider(){

                @Override
                public CurrencyDisplayInfo getInstance(ULocale uLocale, boolean bl) {
                    return DefaultInfo.getWithFallback(bl);
                }

                @Override
                public boolean hasData() {
                    return false;
                }
            };
        }
        provider = currencyDisplayInfoProvider;
    }

    @UnsupportedAppUsage
    private CurrencyData() {
    }

    public static abstract class CurrencyDisplayInfo
    extends CurrencyDisplayNames {
        public abstract CurrencyFormatInfo getFormatInfo(String var1);

        public abstract CurrencySpacingInfo getSpacingInfo();

        public abstract Map<String, String> getUnitPatterns();
    }

    public static interface CurrencyDisplayInfoProvider {
        public CurrencyDisplayInfo getInstance(ULocale var1, boolean var2);

        public boolean hasData();
    }

    public static final class CurrencyFormatInfo {
        public final String currencyPattern;
        public final String isoCode;
        public final String monetaryDecimalSeparator;
        public final String monetaryGroupingSeparator;

        public CurrencyFormatInfo(String string, String string2, String string3, String string4) {
            this.isoCode = string;
            this.currencyPattern = string2;
            this.monetaryDecimalSeparator = string3;
            this.monetaryGroupingSeparator = string4;
        }
    }

    public static final class CurrencySpacingInfo {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public static final CurrencySpacingInfo DEFAULT = new CurrencySpacingInfo("[:letter:]", "[:digit:]", " ", "[:letter:]", "[:digit:]", " ");
        private static final String DEFAULT_CTX_MATCH = "[:digit:]";
        private static final String DEFAULT_CUR_MATCH = "[:letter:]";
        private static final String DEFAULT_INSERT = " ";
        public boolean hasAfterCurrency = false;
        public boolean hasBeforeCurrency = false;
        private final String[][] symbols = new String[SpacingType.COUNT.ordinal()][SpacingPattern.COUNT.ordinal()];

        public CurrencySpacingInfo() {
        }

        public CurrencySpacingInfo(String ... arrstring) {
            int n = 0;
            for (int i = 0; i < SpacingType.COUNT.ordinal(); ++i) {
                for (int j = 0; j < SpacingPattern.COUNT.ordinal(); ++j) {
                    this.symbols[i][j] = arrstring[n];
                    ++n;
                }
            }
        }

        public String[] getAfterSymbols() {
            return this.symbols[SpacingType.AFTER.ordinal()];
        }

        public String[] getBeforeSymbols() {
            return this.symbols[SpacingType.BEFORE.ordinal()];
        }

        public void setSymbolIfNull(SpacingType arrstring, SpacingPattern spacingPattern, String string) {
            int n = arrstring.ordinal();
            arrstring = this.symbols;
            int n2 = spacingPattern.ordinal();
            if (arrstring[n][n2] == null) {
                arrstring[n][n2] = string;
            }
        }

        public static enum SpacingPattern {
            CURRENCY_MATCH(0),
            SURROUNDING_MATCH(1),
            INSERT_BETWEEN(2),
            COUNT;
            
            static final /* synthetic */ boolean $assertionsDisabled = false;

            private SpacingPattern() {
            }

            private SpacingPattern(int n2) {
            }
        }

        public static enum SpacingType {
            BEFORE,
            AFTER,
            COUNT;
            
        }

    }

    public static class DefaultInfo
    extends CurrencyDisplayInfo {
        private static final CurrencyDisplayInfo FALLBACK_INSTANCE = new DefaultInfo(true);
        private static final CurrencyDisplayInfo NO_FALLBACK_INSTANCE = new DefaultInfo(false);
        private final boolean fallback;

        private DefaultInfo(boolean bl) {
            this.fallback = bl;
        }

        public static final CurrencyDisplayInfo getWithFallback(boolean bl) {
            CurrencyDisplayInfo currencyDisplayInfo = bl ? FALLBACK_INSTANCE : NO_FALLBACK_INSTANCE;
            return currencyDisplayInfo;
        }

        @Override
        public CurrencyFormatInfo getFormatInfo(String string) {
            return null;
        }

        @Override
        public String getName(String string) {
            if (!this.fallback) {
                string = null;
            }
            return string;
        }

        @Override
        public String getNarrowSymbol(String string) {
            if (!this.fallback) {
                string = null;
            }
            return string;
        }

        @Override
        public String getPluralName(String string, String string2) {
            if (!this.fallback) {
                string = null;
            }
            return string;
        }

        @Override
        public CurrencySpacingInfo getSpacingInfo() {
            CurrencySpacingInfo currencySpacingInfo = this.fallback ? CurrencySpacingInfo.DEFAULT : null;
            return currencySpacingInfo;
        }

        @Override
        public String getSymbol(String string) {
            if (!this.fallback) {
                string = null;
            }
            return string;
        }

        @Override
        public ULocale getULocale() {
            return ULocale.ROOT;
        }

        @Override
        public Map<String, String> getUnitPatterns() {
            if (this.fallback) {
                return Collections.emptyMap();
            }
            return null;
        }

        @Override
        public Map<String, String> nameMap() {
            return Collections.emptyMap();
        }

        @Override
        public Map<String, String> symbolMap() {
            return Collections.emptyMap();
        }
    }

}

