/*
 * Decompiled with CFR 0.145.
 */
package libcore.icu;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import libcore.util.BasicLruCache;

public final class TimeZoneNames {
    public static final int LONG_NAME = 1;
    public static final int LONG_NAME_DST = 3;
    public static final int NAME_COUNT = 5;
    public static final int OLSON_NAME = 0;
    public static final int SHORT_NAME = 2;
    public static final int SHORT_NAME_DST = 4;
    private static final Comparator<String[]> ZONE_STRINGS_COMPARATOR;
    private static final String[] availableTimeZoneIds;
    private static final ZoneStringsCache cachedZoneStrings;

    static {
        availableTimeZoneIds = TimeZone.getAvailableIDs();
        cachedZoneStrings = new ZoneStringsCache();
        ZONE_STRINGS_COMPARATOR = new Comparator<String[]>(){

            @Override
            public int compare(String[] arrstring, String[] arrstring2) {
                return arrstring[0].compareTo(arrstring2[0]);
            }
        };
    }

    private TimeZoneNames() {
    }

    private static native void fillZoneStrings(String var0, String[][] var1);

    public static String getDisplayName(String[][] arrstring, String string, boolean bl, int n) {
        Comparator<String[]> comparator = ZONE_STRINGS_COMPARATOR;
        int n2 = Arrays.binarySearch(arrstring, new String[]{string}, comparator);
        if (n2 >= 0) {
            arrstring = arrstring[n2];
            if (bl) {
                arrstring = n == 1 ? arrstring[3] : arrstring[4];
                return arrstring;
            }
            arrstring = n == 1 ? arrstring[1] : arrstring[2];
            return arrstring;
        }
        return null;
    }

    public static String[][] getZoneStrings(Locale locale) {
        Locale locale2 = locale;
        if (locale == null) {
            locale2 = Locale.getDefault();
        }
        return (String[][])cachedZoneStrings.get(locale2);
    }

    public static class ZoneStringsCache
    extends BasicLruCache<Locale, String[][]> {
        public ZoneStringsCache() {
            super(5);
        }

        private void addOffsetStrings(String[][] arrstring) {
            for (int i = 0; i < arrstring.length; ++i) {
                TimeZone timeZone = null;
                for (int j = 1; j < 5; ++j) {
                    TimeZone timeZone2;
                    int n;
                    block7 : {
                        int n2;
                        block6 : {
                            if (arrstring[i][j] != null) continue;
                            timeZone2 = timeZone;
                            if (timeZone == null) {
                                timeZone2 = TimeZone.getTimeZone(arrstring[i][0]);
                            }
                            n2 = timeZone2.getRawOffset();
                            if (j == 3) break block6;
                            n = n2;
                            if (j != 4) break block7;
                        }
                        n = n2 + timeZone2.getDSTSavings();
                    }
                    arrstring[i][j] = TimeZone.createGmtOffsetString((boolean)true, (boolean)true, (int)n);
                    timeZone = timeZone2;
                }
            }
        }

        private void internStrings(String[][] arrstring) {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            for (int i = 0; i < arrstring.length; ++i) {
                for (int j = 1; j < 5; ++j) {
                    String string = arrstring[i][j];
                    String string2 = (String)hashMap.get(string);
                    if (string2 == null) {
                        hashMap.put(string, string);
                        continue;
                    }
                    arrstring[i][j] = string2;
                }
            }
        }

        @Override
        protected String[][] create(Locale locale) {
            long l = System.nanoTime();
            String[][] arrstring = new String[availableTimeZoneIds.length][5];
            for (int i = 0; i < availableTimeZoneIds.length; ++i) {
                arrstring[i][0] = availableTimeZoneIds[i];
            }
            long l2 = System.nanoTime();
            TimeZoneNames.fillZoneStrings(locale.toLanguageTag(), arrstring);
            long l3 = System.nanoTime();
            this.addOffsetStrings(arrstring);
            this.internStrings(arrstring);
            long l4 = System.nanoTime();
            l2 = TimeUnit.NANOSECONDS.toMillis(l3 - l2);
            l4 = TimeUnit.NANOSECONDS.toMillis(l4 - l);
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Loaded time zone names for \"");
            stringBuilder.append(locale);
            stringBuilder.append("\" in ");
            stringBuilder.append(l4);
            stringBuilder.append("ms (");
            stringBuilder.append(l2);
            stringBuilder.append("ms in ICU)");
            System.logI((String)stringBuilder.toString());
            return arrstring;
        }
    }

}

