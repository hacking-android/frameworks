/*
 * Decompiled with CFR 0.145.
 */
package android.os.health;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.util.Arrays;

public class HealthKeys {
    public static final int BASE_PACKAGE = 40000;
    public static final int BASE_PID = 20000;
    public static final int BASE_PROCESS = 30000;
    public static final int BASE_SERVICE = 50000;
    public static final int BASE_UID = 10000;
    public static final int TYPE_COUNT = 5;
    public static final int TYPE_MEASUREMENT = 1;
    public static final int TYPE_MEASUREMENTS = 4;
    public static final int TYPE_STATS = 2;
    public static final int TYPE_TIMER = 0;
    public static final int TYPE_TIMERS = 3;
    public static final int UNKNOWN_KEY = 0;

    @Retention(value=RetentionPolicy.RUNTIME)
    @Target(value={ElementType.FIELD})
    public static @interface Constant {
        public int type();
    }

    public static class Constants {
        private final String mDataType;
        private final int[][] mKeys = new int[5][];

        public Constants(Class annotatedElement) {
            int n;
            this.mDataType = ((Class)annotatedElement).getSimpleName();
            Field[] arrfield = ((Class)annotatedElement).getDeclaredFields();
            int n2 = arrfield.length;
            SortedIntArray[] arrsortedIntArray = new SortedIntArray[this.mKeys.length];
            for (n = 0; n < arrsortedIntArray.length; ++n) {
                arrsortedIntArray[n] = new SortedIntArray(n2);
            }
            for (n = 0; n < n2; ++n) {
                annotatedElement = arrfield[n];
                Object object = ((Field)annotatedElement).getAnnotation(Constant.class);
                if (object == null) continue;
                int n3 = object.type();
                if (n3 < arrsortedIntArray.length) {
                    try {
                        arrsortedIntArray[n3].addValue(((Field)annotatedElement).getInt(null));
                        continue;
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Can't read constant value type=");
                        ((StringBuilder)object).append(n3);
                        ((StringBuilder)object).append(" field=");
                        ((StringBuilder)object).append(annotatedElement);
                        throw new RuntimeException(((StringBuilder)object).toString(), illegalAccessException);
                    }
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown Constant type ");
                ((StringBuilder)object).append(n3);
                ((StringBuilder)object).append(" on ");
                ((StringBuilder)object).append(annotatedElement);
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            for (n = 0; n < arrsortedIntArray.length; ++n) {
                this.mKeys[n] = arrsortedIntArray[n].getArray();
            }
        }

        public String getDataType() {
            return this.mDataType;
        }

        public int getIndex(int n, int n2) {
            int n3 = Arrays.binarySearch(this.mKeys[n], n2);
            if (n3 >= 0) {
                return n3;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unknown Constant ");
            stringBuilder.append(n2);
            stringBuilder.append(" (of type ");
            stringBuilder.append(n);
            stringBuilder.append(" )");
            throw new RuntimeException(stringBuilder.toString());
        }

        public int[] getKeys(int n) {
            return this.mKeys[n];
        }

        public int getSize(int n) {
            return this.mKeys[n].length;
        }
    }

    private static class SortedIntArray {
        int[] mArray;
        int mCount;

        SortedIntArray(int n) {
            this.mArray = new int[n];
        }

        void addValue(int n) {
            int[] arrn = this.mArray;
            int n2 = this.mCount;
            this.mCount = n2 + 1;
            arrn[n2] = n;
        }

        int[] getArray() {
            int n = this.mCount;
            int[] arrn = this.mArray;
            if (n == arrn.length) {
                Arrays.sort(arrn);
                return this.mArray;
            }
            int[] arrn2 = new int[n];
            System.arraycopy(arrn, 0, arrn2, 0, n);
            Arrays.sort(arrn2);
            return arrn2;
        }
    }

}

