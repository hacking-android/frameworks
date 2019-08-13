/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.FileUtils;
import android.util.Log;
import android.util.Pair;
import android.util.Range;
import android.util.Rational;
import android.util.Size;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Vector;

class Utils {
    private static final String TAG = "Utils";

    Utils() {
    }

    static Range<Integer> alignRange(Range<Integer> range, int n) {
        return range.intersect(Utils.divUp(range.getLower(), n) * n, range.getUpper() / n * n);
    }

    public static <T extends Comparable<? super T>> int binarySearchDistinctRanges(Range<T>[] arrrange, T t) {
        return Arrays.binarySearch(arrrange, Range.create(t, t), new Comparator<Range<T>>(){

            @Override
            public int compare(Range<T> range, Range<T> range2) {
                if (range.getUpper().compareTo(range2.getLower()) < 0) {
                    return -1;
                }
                return range.getLower().compareTo(range2.getUpper()) > 0;
            }
        });
    }

    static int divUp(int n, int n2) {
        return (n + n2 - 1) / n2;
    }

    static long divUp(long l, long l2) {
        return (l + l2 - 1L) / l2;
    }

    static Range<Integer> factorRange(Range<Integer> range, int n) {
        if (n == 1) {
            return range;
        }
        return Range.create(Utils.divUp(range.getLower(), n), range.getUpper() / n);
    }

    static Range<Long> factorRange(Range<Long> range, long l) {
        if (l == 1L) {
            return range;
        }
        return Range.create(Utils.divUp(range.getLower(), l), range.getUpper() / l);
    }

    static int gcd(int n, int n2) {
        if (n == 0 && n2 == 0) {
            return 1;
        }
        int n3 = n2;
        if (n2 < 0) {
            n3 = -n2;
        }
        n2 = n;
        int n4 = n3;
        if (n < 0) {
            n2 = -n;
            n4 = n3;
        }
        do {
            n = n4;
            if (n2 == 0) break;
            n4 = n2;
            n2 = n % n2;
        } while (true);
        return n;
    }

    static String getFileDisplayNameFromUri(Context object, Uri object2) {
        String string2 = ((Uri)object2).getScheme();
        if ("file".equals(string2)) {
            return ((Uri)object2).getLastPathSegment();
        }
        if ("content".equals(string2)) {
            if ((object = ((Context)object).getContentResolver().query((Uri)object2, new String[]{"_display_name"}, null, null, null)) != null) {
                try {
                    if (object.getCount() != 0) {
                        object.moveToFirst();
                        object2 = object.getString(object.getColumnIndex("_display_name"));
                        object.close();
                        return object2;
                    }
                }
                catch (Throwable throwable) {
                    try {
                        throw throwable;
                    }
                    catch (Throwable throwable2) {
                        try {
                            object.close();
                        }
                        catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                        throw throwable2;
                    }
                }
            }
            if (object != null) {
                object.close();
            }
        }
        return ((Uri)object2).toString();
    }

    public static File getUniqueExternalFile(Context object, String charSequence, String string2, String string3) {
        object = Environment.getExternalStoragePublicDirectory((String)charSequence);
        ((File)object).mkdirs();
        try {
            object = FileUtils.buildUniqueFile((File)object, string3, string2);
            return object;
        }
        catch (FileNotFoundException fileNotFoundException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("Unable to get a unique file name: ");
            ((StringBuilder)charSequence).append(fileNotFoundException);
            Log.e(TAG, ((StringBuilder)charSequence).toString());
            return null;
        }
    }

    static Range<Integer> intRangeFor(double d) {
        return Range.create((int)d, (int)Math.ceil(d));
    }

    public static <T extends Comparable<? super T>> Range<T>[] intersectSortedDistinctRanges(Range<T>[] arrrange, Range<T>[] arrrange2) {
        int n = 0;
        Vector<Range<T>> vector = new Vector<Range<T>>();
        for (Range<T> range : arrrange2) {
            int n2 = n;
            do {
                n = n2;
                if (n2 >= arrrange.length) break;
                n = n2;
                if (arrrange[n2].getUpper().compareTo(range.getLower()) >= 0) break;
                ++n2;
            } while (true);
            while (n < arrrange.length && arrrange[n].getUpper().compareTo(range.getUpper()) < 0) {
                vector.add(range.intersect(arrrange[n]));
                ++n;
            }
            if (n == arrrange.length) break;
            if (arrrange[n].getLower().compareTo(range.getUpper()) > 0) continue;
            vector.add(range.intersect(arrrange[n]));
        }
        return vector.toArray(new Range[vector.size()]);
    }

    private static long lcm(int n, int n2) {
        if (n != 0 && n2 != 0) {
            return (long)n * (long)n2 / (long)Utils.gcd(n, n2);
        }
        throw new IllegalArgumentException("lce is not defined for zero arguments");
    }

    static Range<Long> longRangeFor(double d) {
        return Range.create((long)d, (long)Math.ceil(d));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Range<Integer> parseIntRange(Object object, Range<Integer> range) {
        try {
            String string2 = (String)object;
            int n = string2.indexOf(45);
            if (n >= 0) {
                return Range.create(Integer.parseInt(string2.substring(0, n), 10), Integer.parseInt(string2.substring(n + 1), 10));
            }
            n = Integer.parseInt(string2);
            return Range.create(n, n);
        }
        catch (IllegalArgumentException illegalArgumentException) {
        }
        catch (NullPointerException nullPointerException) {
            return range;
        }
        catch (NumberFormatException numberFormatException) {
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("could not parse integer range '");
        stringBuilder.append(object);
        stringBuilder.append("'");
        Log.w(TAG, stringBuilder.toString());
        return range;
    }

    static int parseIntSafely(Object object, int n) {
        if (object == null) {
            return n;
        }
        try {
            int n2 = Integer.parseInt((String)object);
            return n2;
        }
        catch (NullPointerException nullPointerException) {
            return n;
        }
        catch (NumberFormatException numberFormatException) {
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("could not parse integer '");
        stringBuilder.append(object);
        stringBuilder.append("'");
        Log.w(TAG, stringBuilder.toString());
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Range<Long> parseLongRange(Object object, Range<Long> range) {
        try {
            String string2 = (String)object;
            int n = string2.indexOf(45);
            if (n >= 0) {
                return Range.create(Long.parseLong(string2.substring(0, n), 10), Long.parseLong(string2.substring(n + 1), 10));
            }
            long l = Long.parseLong(string2);
            return Range.create(l, l);
        }
        catch (IllegalArgumentException illegalArgumentException) {
        }
        catch (NullPointerException nullPointerException) {
            return range;
        }
        catch (NumberFormatException numberFormatException) {
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("could not parse long range '");
        stringBuilder.append(object);
        stringBuilder.append("'");
        Log.w(TAG, stringBuilder.toString());
        return range;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Range<Rational> parseRationalRange(Object object, Range<Rational> range) {
        try {
            Range<Object> range2 = (String)object;
            int n = ((String)((Object)range2)).indexOf(45);
            if (n >= 0) {
                return Range.create(Rational.parseRational(((String)((Object)range2)).substring(0, n)), Rational.parseRational(((String)((Object)range2)).substring(n + 1)));
            }
            range2 = Rational.parseRational((String)((Object)range2));
            return Range.create(range2, range2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
        }
        catch (NullPointerException nullPointerException) {
            return range;
        }
        catch (NumberFormatException numberFormatException) {
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("could not parse rational range '");
        stringBuilder.append(object);
        stringBuilder.append("'");
        Log.w(TAG, stringBuilder.toString());
        return range;
    }

    static Size parseSize(Object object, Size size) {
        try {
            Size size2 = Size.parseSize((String)object);
            return size2;
        }
        catch (NullPointerException nullPointerException) {
            return size;
        }
        catch (NumberFormatException numberFormatException) {
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("could not parse size '");
        stringBuilder.append(object);
        stringBuilder.append("'");
        Log.w(TAG, stringBuilder.toString());
        return size;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Pair<Size, Size> parseSizeRange(Object object) {
        try {
            Pair<Object, Object> pair = (String)object;
            int n = ((String)((Object)pair)).indexOf(45);
            if (n >= 0) {
                return Pair.create(Size.parseSize(((String)((Object)pair)).substring(0, n)), Size.parseSize(((String)((Object)pair)).substring(n + 1)));
            }
            pair = Size.parseSize((String)((Object)pair));
            return Pair.create(pair, pair);
        }
        catch (IllegalArgumentException illegalArgumentException) {
        }
        catch (NullPointerException nullPointerException) {
            return null;
        }
        catch (NumberFormatException numberFormatException) {
        }
        catch (ClassCastException classCastException) {
            // empty catch block
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("could not parse size range '");
        stringBuilder.append(object);
        stringBuilder.append("'");
        Log.w(TAG, stringBuilder.toString());
        return null;
    }

    static Range<Rational> scaleRange(Range<Rational> range, int n, int n2) {
        if (n == n2) {
            return range;
        }
        return Range.create(Utils.scaleRatio(range.getLower(), n, n2), Utils.scaleRatio(range.getUpper(), n, n2));
    }

    private static Rational scaleRatio(Rational rational, int n, int n2) {
        int n3 = Utils.gcd(n, n2);
        return new Rational((int)((double)rational.getNumerator() * (double)(n /= n3)), (int)((double)rational.getDenominator() * (double)(n2 /= n3)));
    }

    public static <T extends Comparable<? super T>> void sortDistinctRanges(Range<T>[] arrrange) {
        Arrays.sort(arrrange, new Comparator<Range<T>>(){

            @Override
            public int compare(Range<T> range, Range<T> range2) {
                if (range.getUpper().compareTo(range2.getLower()) < 0) {
                    return -1;
                }
                if (range.getLower().compareTo(range2.getUpper()) > 0) {
                    return 1;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("sample rate ranges must be distinct (");
                stringBuilder.append(range);
                stringBuilder.append(" and ");
                stringBuilder.append(range2);
                stringBuilder.append(")");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
        });
    }

}

