/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CalType;
import android.icu.impl.Grego;
import android.icu.impl.ICUResourceBundle;
import android.icu.util.ICUException;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceBundleIterator;
import java.util.Arrays;

public class EraRules {
    private static final int DAY_MASK = 255;
    private static final int MAX_ENCODED_START_YEAR = 32767;
    public static final int MIN_ENCODED_START = EraRules.encodeDate(-32768, 1, 1);
    private static final int MIN_ENCODED_START_YEAR = -32768;
    private static final int MONTH_MASK = 65280;
    private static final int YEAR_MASK = -65536;
    private int currentEra;
    private int numEras;
    private int[] startDates;

    private EraRules(int[] arrn, int n) {
        this.startDates = arrn;
        this.numEras = n;
        this.initCurrentEra();
    }

    private static int compareEncodedDateWithYMD(int n, int n2, int n3, int n4) {
        if (n2 < -32768) {
            if (n == MIN_ENCODED_START) {
                if (n2 <= Integer.MIN_VALUE && n3 <= 1 && n4 <= 1) {
                    return 0;
                }
                return -1;
            }
            return 1;
        }
        if (n2 > 32767) {
            return -1;
        }
        if (n < (n2 = EraRules.encodeDate(n2, n3, n4))) {
            return -1;
        }
        return n != n2;
    }

    private static int[] decodeDate(int n, int[] arrn) {
        int n2;
        int n3;
        if (n == MIN_ENCODED_START) {
            n = Integer.MIN_VALUE;
            n2 = 1;
            n3 = 1;
        } else {
            int n4 = (-65536 & n) >> 16;
            n2 = (65280 & n) >> 8;
            n3 = n & 255;
            n = n4;
        }
        if (arrn != null && arrn.length >= 3) {
            arrn[0] = n;
            arrn[1] = n2;
            arrn[2] = n3;
            return arrn;
        }
        return new int[]{n, n2, n3};
    }

    private static int encodeDate(int n, int n2, int n3) {
        return n << 16 | n2 << 8 | n3;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static EraRules getInstance(CalType object, boolean bl) {
        UResourceBundle uResourceBundle = UResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        UResourceBundle uResourceBundle2 = uResourceBundle.get("calendarData");
        UResourceBundle uResourceBundle3 = uResourceBundle2.get(((CalType)((Object)object)).getId());
        UResourceBundle uResourceBundle4 = uResourceBundle3.get("eras");
        int n = uResourceBundle4.getSize();
        int[] arrn = new int[n];
        Object object2 = uResourceBundle4.getIterator();
        int n2 = Integer.MAX_VALUE;
        do {
            int n3;
            int n4;
            boolean bl2;
            String string;
            Object object3;
            block21 : {
                block20 : {
                    block19 : {
                        if (!((UResourceBundleIterator)object2).hasNext()) break block20;
                        object3 = ((UResourceBundleIterator)object2).next();
                        string = ((UResourceBundle)object3).getKey();
                        try {
                            n4 = Integer.parseInt(string);
                            if (n4 < 0 || n4 >= n) break block19;
                        }
                        catch (NumberFormatException numberFormatException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invald era rule key:");
                            stringBuilder.append(string);
                            stringBuilder.append(" in era rule data for ");
                            stringBuilder.append(((CalType)((Object)object)).getId());
                            throw new ICUException(stringBuilder.toString());
                        }
                        if (EraRules.isSet(arrn[n4])) {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Dupulicated era rule for rule key:");
                            ((StringBuilder)object2).append(string);
                            ((StringBuilder)object2).append(" in era rule data for ");
                            ((StringBuilder)object2).append(((CalType)((Object)object)).getId());
                            throw new ICUException(((StringBuilder)object2).toString());
                        }
                        n3 = 1;
                        bl2 = false;
                        object3 = ((UResourceBundle)object3).getIterator();
                        break block21;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Era rule key:");
                    ((StringBuilder)object2).append(string);
                    ((StringBuilder)object2).append(" in era rule data for ");
                    ((StringBuilder)object2).append(((CalType)((Object)object)).getId());
                    ((StringBuilder)object2).append(" must be in range [0, ");
                    ((StringBuilder)object2).append(n - 1);
                    ((StringBuilder)object2).append("]");
                    throw new ICUException(((StringBuilder)object2).toString());
                }
                if (n2 < Integer.MAX_VALUE && !bl) {
                    return new EraRules(arrn, n2);
                }
                return new EraRules(arrn, n);
            }
            while (((UResourceBundleIterator)object3).hasNext()) {
                int n5;
                int[] arrn2 = ((UResourceBundleIterator)object3).next();
                String string2 = arrn2.getKey();
                if (string2.equals("start")) {
                    if ((arrn2 = arrn2.getIntVector()).length == 3 && EraRules.isValidRuleStartDate(arrn2[0], arrn2[1], arrn2[2])) {
                        arrn[n4] = EraRules.encodeDate(arrn2[0], arrn2[1], arrn2[2]);
                        continue;
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Invalid era rule date data:");
                    ((StringBuilder)object2).append(Arrays.toString(arrn2));
                    ((StringBuilder)object2).append(" in era rule data for ");
                    ((StringBuilder)object2).append(((CalType)((Object)object)).getId());
                    throw new ICUException(((StringBuilder)object2).toString());
                }
                if (string2.equals("named")) {
                    n5 = n3;
                    if (arrn2.getString().equals("false")) {
                        n5 = 0;
                    }
                } else {
                    n5 = n3;
                    if (string2.equals("end")) {
                        bl2 = true;
                        continue;
                    }
                }
                n3 = n5;
            }
            if (!EraRules.isSet(arrn[n4])) {
                if (!bl2) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Missing era start/end rule date for key:");
                    ((StringBuilder)object2).append(string);
                    ((StringBuilder)object2).append(" in era rule data for ");
                    ((StringBuilder)object2).append(((CalType)((Object)object)).getId());
                    throw new ICUException(((StringBuilder)object2).toString());
                }
                if (n4 != 0) {
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Era data for ");
                    ((StringBuilder)object2).append(string);
                    ((StringBuilder)object2).append(" in era rule data for ");
                    ((StringBuilder)object2).append(((CalType)((Object)object)).getId());
                    ((StringBuilder)object2).append(" has only end rule.");
                    throw new ICUException(((StringBuilder)object2).toString());
                }
                arrn[n4] = MIN_ENCODED_START;
            }
            if (n3 != 0) {
                if (n4 >= n2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Non-tentative era(");
                    ((StringBuilder)object).append(n4);
                    ((StringBuilder)object).append(") must be placed before the first tentative era");
                    throw new ICUException(((StringBuilder)object).toString());
                }
                n3 = n2;
            } else {
                n3 = n2;
                if (n4 < n2) {
                    n3 = n4;
                }
            }
            n2 = n3;
        } while (true);
    }

    private void initCurrentEra() {
        int n;
        int[] arrn = Grego.timeToFields(System.currentTimeMillis(), null);
        int n2 = EraRules.encodeDate(arrn[0], arrn[1] + 1, arrn[2]);
        for (n = this.numEras - 1; n > 0 && n2 < this.startDates[n]; --n) {
        }
        this.currentEra = n;
    }

    private static boolean isSet(int n) {
        boolean bl = n != 0;
        return bl;
    }

    private static boolean isValidRuleStartDate(int n, int n2, int n3) {
        boolean bl = true;
        if (n < -32768 || n > 32767 || n2 < 1 || n2 > 12 || n3 < 1 || n3 > 31) {
            bl = false;
        }
        return bl;
    }

    public int getCurrentEraIndex() {
        return this.currentEra;
    }

    public int getEraIndex(int n, int n2, int n3) {
        if (n2 >= 1 && n2 <= 12 && n3 >= 1 && n3 <= 31) {
            int n4 = this.numEras;
            int n5 = EraRules.compareEncodedDateWithYMD(this.startDates[this.getCurrentEraIndex()], n, n2, n3) <= 0 ? this.getCurrentEraIndex() : 0;
            while (n5 < n4 - 1) {
                int n6 = (n5 + n4) / 2;
                if (EraRules.compareEncodedDateWithYMD(this.startDates[n6], n, n2, n3) <= 0) {
                    n5 = n6;
                    continue;
                }
                n4 = n6;
            }
            return n5;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Illegal date - year:");
        stringBuilder.append(n);
        stringBuilder.append("month:");
        stringBuilder.append(n2);
        stringBuilder.append("day:");
        stringBuilder.append(n3);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public int getNumberOfEras() {
        return this.numEras;
    }

    public int[] getStartDate(int n, int[] arrn) {
        if (n >= 0 && n < this.numEras) {
            return EraRules.decodeDate(this.startDates[n], arrn);
        }
        throw new IllegalArgumentException("eraIdx is out of range");
    }

    public int getStartYear(int n) {
        if (n >= 0 && n < this.numEras) {
            return EraRules.decodeDate(this.startDates[n], null)[0];
        }
        throw new IllegalArgumentException("eraIdx is out of range");
    }
}

