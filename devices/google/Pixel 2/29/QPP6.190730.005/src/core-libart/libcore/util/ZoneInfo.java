/*
 * Decompiled with CFR 0.145.
 */
package libcore.util;

import dalvik.annotation.compat.UnsupportedAppUsage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import libcore.io.BufferIterator;

public final class ZoneInfo
extends TimeZone {
    private static final int[] LEAP;
    private static final long MILLISECONDS_PER_400_YEARS = 12622780800000L;
    private static final long MILLISECONDS_PER_DAY = 86400000L;
    private static final int[] NORMAL;
    private static final long UNIX_OFFSET = 62167219200000L;
    static final long serialVersionUID = -4598738130123921552L;
    private int mDstSavings;
    private final int mEarliestRawOffset;
    private final byte[] mIsDsts;
    private final int[] mOffsets;
    private int mRawOffset;
    @UnsupportedAppUsage
    private final long[] mTransitions;
    private final byte[] mTypes;
    private final boolean mUseDst;

    static {
        NORMAL = new int[]{0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334};
        LEAP = new int[]{0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335};
    }

    private ZoneInfo(String arrby, long[] object, byte[] arrby2, int[] arrn, byte[] arrby3, long l) {
        block15 : {
            block18 : {
                int n;
                int n2;
                int n3;
                block17 : {
                    block16 : {
                        if (arrn.length == 0) break block15;
                        this.mTransitions = object;
                        this.mTypes = arrby2;
                        this.mIsDsts = arrby3;
                        this.setID((String)arrby);
                        n3 = -1;
                        n2 = -1;
                        for (n = this.mTransitions.length - 1; (n3 == -1 || n2 == -1) && n >= 0; --n) {
                            int n4 = this.mTypes[n] & 255;
                            int n5 = n3;
                            if (n3 == -1) {
                                n5 = n3;
                                if (this.mIsDsts[n4] == 0) {
                                    n5 = n;
                                }
                            }
                            int n6 = n2;
                            if (n2 == -1) {
                                n6 = n2;
                                if (this.mIsDsts[n4] != 0) {
                                    n6 = n;
                                }
                            }
                            n3 = n5;
                            n2 = n6;
                        }
                        if (this.mTransitions.length != 0) break block16;
                        this.mRawOffset = arrn[0];
                        break block17;
                    }
                    if (n3 == -1) break block18;
                    this.mRawOffset = arrn[this.mTypes[n3] & 255];
                }
                n = n2;
                if (n2 != -1) {
                    n = n2;
                    if (this.mTransitions[n2] < ZoneInfo.roundUpMillisToSeconds(l)) {
                        n = -1;
                    }
                }
                if (n == -1) {
                    this.mDstSavings = 0;
                    this.mUseDst = false;
                } else {
                    arrby = this.mTypes;
                    n2 = arrn[arrby[n3] & 255];
                    this.mDstSavings = (arrn[arrby[n] & 255] - n2) * 1000;
                    this.mUseDst = true;
                }
                n3 = -1;
                n = 0;
                do {
                    arrby = this.mIsDsts;
                    n2 = n3;
                    if (n >= arrby.length) break;
                    if (arrby[n] == 0) {
                        n2 = n;
                        break;
                    }
                    ++n;
                } while (true);
                n = n2 != -1 ? arrn[n2] : this.mRawOffset;
                this.mOffsets = arrn;
                for (n2 = 0; n2 < (arrby = this.mOffsets).length; ++n2) {
                    arrby[n2] = arrby[n2] - this.mRawOffset;
                }
                this.mRawOffset *= 1000;
                this.mEarliestRawOffset = n * 1000;
                return;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("ZoneInfo requires at least one non-DST transition to be provided for each timezone that has at least one transition but could not find one for '");
            ((StringBuilder)object).append((String)arrby);
            ((StringBuilder)object).append("'");
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("ZoneInfo requires at least one offset to be provided for each timezone but could not find one for '");
        ((StringBuilder)object).append((String)arrby);
        ((StringBuilder)object).append("'");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    private static int checked32BitAdd(long l, int n) throws CheckedArithmeticException {
        if ((l = (long)n + l) == (long)((int)l)) {
            return (int)l;
        }
        throw new CheckedArithmeticException();
    }

    private static int checked32BitSubtract(long l, int n) throws CheckedArithmeticException {
        if ((l -= (long)n) == (long)((int)l)) {
            return (int)l;
        }
        throw new CheckedArithmeticException();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (!this.mUseDst && this.mDstSavings != 0) {
            this.mDstSavings = 0;
        }
    }

    public static ZoneInfo readTimeZone(String string, BufferIterator object, long l) throws IOException {
        int n = ((BufferIterator)object).readInt();
        if (n == 1415211366) {
            ((BufferIterator)object).skip(28);
            int n2 = ((BufferIterator)object).readInt();
            int n3 = 2000;
            if (n2 >= 0 && n2 <= 2000) {
                int n4 = ((BufferIterator)object).readInt();
                if (n4 >= 1) {
                    if (n4 <= 256) {
                        ((BufferIterator)object).skip(4);
                        int[] arrn = new int[n2];
                        ((BufferIterator)object).readIntArray(arrn, 0, arrn.length);
                        long[] arrl = new long[n2];
                        for (n = 0; n < n2; ++n) {
                            arrl[n] = arrn[n];
                            if (n <= 0 || arrl[n] > arrl[n - 1]) continue;
                            object = new StringBuilder();
                            ((StringBuilder)object).append(string);
                            ((StringBuilder)object).append(" transition at ");
                            ((StringBuilder)object).append(n);
                            ((StringBuilder)object).append(" is not sorted correctly, is ");
                            ((StringBuilder)object).append(arrl[n]);
                            ((StringBuilder)object).append(", previous is ");
                            ((StringBuilder)object).append(arrl[n - 1]);
                            throw new IOException(((StringBuilder)object).toString());
                        }
                        arrn = new byte[n2];
                        ((BufferIterator)object).readByteArray((byte[])arrn, 0, arrn.length);
                        for (n = 0; n < arrn.length; ++n) {
                            n2 = arrn[n] & 255;
                            if (n2 < n4) {
                                continue;
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append(string);
                            ((StringBuilder)object).append(" type at ");
                            ((StringBuilder)object).append(n);
                            ((StringBuilder)object).append(" is not < ");
                            ((StringBuilder)object).append(n4);
                            ((StringBuilder)object).append(", is ");
                            ((StringBuilder)object).append(n2);
                            throw new IOException(((StringBuilder)object).toString());
                        }
                        int[] arrn2 = new int[n4];
                        byte[] arrby = new byte[n4];
                        n2 = 0;
                        n = n3;
                        for (n3 = n2; n3 < n4; ++n3) {
                            arrn2[n3] = ((BufferIterator)object).readInt();
                            n2 = ((BufferIterator)object).readByte();
                            if (n2 != 0 && n2 != 1) {
                                object = new StringBuilder();
                                ((StringBuilder)object).append(string);
                                ((StringBuilder)object).append(" dst at ");
                                ((StringBuilder)object).append(n3);
                                ((StringBuilder)object).append(" is not 0 or 1, is ");
                                ((StringBuilder)object).append(n2);
                                throw new IOException(((StringBuilder)object).toString());
                            }
                            arrby[n3] = (byte)n2;
                            ((BufferIterator)object).skip(1);
                        }
                        return new ZoneInfo(string, arrl, (byte[])arrn, arrn2, arrby, l);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Timezone with id ");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append(" has too many types=");
                    ((StringBuilder)object).append(n4);
                    throw new IOException(((StringBuilder)object).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("ZoneInfo requires at least one type to be provided for each timezone but could not find one for '");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append("'");
                throw new IOException(((StringBuilder)object).toString());
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Timezone id=");
            ((StringBuilder)object).append(string);
            ((StringBuilder)object).append(" has an invalid number of transitions=");
            ((StringBuilder)object).append(n2);
            throw new IOException(((StringBuilder)object).toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Timezone id=");
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append(" has an invalid header=");
        ((StringBuilder)object).append(n);
        throw new IOException(((StringBuilder)object).toString());
    }

    static long roundDownMillisToSeconds(long l) {
        if (l < 0L) {
            return (l - 999L) / 1000L;
        }
        return l / 1000L;
    }

    static long roundUpMillisToSeconds(long l) {
        if (l > 0L) {
            return (999L + l) / 1000L;
        }
        return l / 1000L;
    }

    private static int saturated32BitAdd(long l, int n) {
        if ((l = (long)n + l) > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (l < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int)l;
    }

    @Override
    public Object clone() {
        return super.clone();
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof ZoneInfo;
        boolean bl2 = false;
        if (!bl) {
            return false;
        }
        object = (ZoneInfo)object;
        bl = bl2;
        if (this.getID().equals(((TimeZone)object).getID())) {
            bl = bl2;
            if (this.hasSameRules((TimeZone)object)) {
                bl = true;
            }
        }
        return bl;
    }

    int findOffsetIndexForTimeInMilliseconds(long l) {
        return this.findOffsetIndexForTimeInSeconds(ZoneInfo.roundDownMillisToSeconds(l));
    }

    int findOffsetIndexForTimeInSeconds(long l) {
        int n = this.findTransitionIndex(l);
        if (n < 0) {
            return -1;
        }
        return this.mTypes[n] & 255;
    }

    public int findTransitionIndex(long l) {
        int n;
        int n2 = n = Arrays.binarySearch(this.mTransitions, l);
        if (n < 0) {
            n2 = --n;
            if (n < 0) {
                return -1;
            }
        }
        return n2;
    }

    @Override
    public int getDSTSavings() {
        return this.mDstSavings;
    }

    @Override
    public int getOffset(int n, int n2, int n3, int n4, int n5, int n6) {
        long l;
        long l2 = n2 / 400;
        n = n2 % 400;
        l2 = l = l2 * 12622780800000L + (long)n * 31536000000L + (long)((n + 3) / 4) * 86400000L;
        if (n > 0) {
            l2 = l - (long)((n - 1) / 100) * 86400000L;
        }
        n = n != 0 && (n % 4 != 0 || n % 100 == 0) ? 0 : 1;
        int[] arrn = n != 0 ? LEAP : NORMAL;
        return this.getOffset(l2 + (long)arrn[n3] * 86400000L + (long)(n4 - 1) * 86400000L + (long)n6 - (long)this.mRawOffset - 62167219200000L);
    }

    @Override
    public int getOffset(long l) {
        int n = this.findOffsetIndexForTimeInMilliseconds(l);
        if (n == -1) {
            return this.mEarliestRawOffset;
        }
        return this.mRawOffset + this.mOffsets[n] * 1000;
    }

    public int getOffsetsByUtcTime(long l, int[] arrn) {
        int n;
        int n2;
        int n3 = this.findTransitionIndex(ZoneInfo.roundDownMillisToSeconds(l));
        if (n3 == -1) {
            n3 = this.mEarliestRawOffset;
            n2 = 0;
            n = n3;
        } else {
            n2 = this.mTypes[n3] & 255;
            n = this.mRawOffset + this.mOffsets[n2] * 1000;
            if (this.mIsDsts[n2] == 0) {
                n3 = n;
                n2 = 0;
            } else {
                block6 : {
                    int n4;
                    n2 = -1;
                    do {
                        int n5 = n3 - 1;
                        n3 = n2;
                        if (n5 < 0) break block6;
                        n4 = this.mTypes[n5] & 255;
                        n3 = n5;
                    } while (this.mIsDsts[n4] != 0);
                    n3 = this.mRawOffset + this.mOffsets[n4] * 1000;
                }
                if (n3 == -1) {
                    n3 = this.mEarliestRawOffset;
                }
                n2 = n - n3;
            }
        }
        arrn[0] = n3;
        arrn[1] = n2;
        return n;
    }

    @Override
    public int getRawOffset() {
        return this.mRawOffset;
    }

    @Override
    public boolean hasSameRules(TimeZone timeZone) {
        boolean bl = timeZone instanceof ZoneInfo;
        boolean bl2 = false;
        boolean bl3 = false;
        if (!bl) {
            return false;
        }
        timeZone = (ZoneInfo)timeZone;
        bl = this.mUseDst;
        if (bl != ((ZoneInfo)timeZone).mUseDst) {
            return false;
        }
        if (!bl) {
            if (this.mRawOffset == ((ZoneInfo)timeZone).mRawOffset) {
                bl3 = true;
            }
            return bl3;
        }
        bl3 = this.mRawOffset == ((ZoneInfo)timeZone).mRawOffset && Arrays.equals(this.mOffsets, ((ZoneInfo)timeZone).mOffsets) && Arrays.equals(this.mIsDsts, ((ZoneInfo)timeZone).mIsDsts) && Arrays.equals(this.mTypes, ((ZoneInfo)timeZone).mTypes) && Arrays.equals(this.mTransitions, ((ZoneInfo)timeZone).mTransitions) ? true : bl2;
        return bl3;
    }

    public int hashCode() {
        int n = this.getID().hashCode();
        int n2 = Arrays.hashCode(this.mOffsets);
        int n3 = Arrays.hashCode(this.mIsDsts);
        int n4 = this.mRawOffset;
        int n5 = Arrays.hashCode(this.mTransitions);
        int n6 = Arrays.hashCode(this.mTypes);
        int n7 = this.mUseDst ? 1231 : 1237;
        return ((((((1 * 31 + n) * 31 + n2) * 31 + n3) * 31 + n4) * 31 + n5) * 31 + n6) * 31 + n7;
    }

    @Override
    public boolean inDaylightTime(Date date) {
        int n = this.findOffsetIndexForTimeInMilliseconds(date.getTime());
        boolean bl = false;
        if (n == -1) {
            return false;
        }
        if (this.mIsDsts[n] == 1) {
            bl = true;
        }
        return bl;
    }

    @Override
    public void setRawOffset(int n) {
        this.mRawOffset = n;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getName());
        stringBuilder.append("[id=\"");
        stringBuilder.append(this.getID());
        stringBuilder.append("\",mRawOffset=");
        stringBuilder.append(this.mRawOffset);
        stringBuilder.append(",mEarliestRawOffset=");
        stringBuilder.append(this.mEarliestRawOffset);
        stringBuilder.append(",mUseDst=");
        stringBuilder.append(this.mUseDst);
        stringBuilder.append(",mDstSavings=");
        stringBuilder.append(this.mDstSavings);
        stringBuilder.append(",transitions=");
        stringBuilder.append(this.mTransitions.length);
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public boolean useDaylightTime() {
        return this.mUseDst;
    }

    private static class CheckedArithmeticException
    extends Exception {
        private CheckedArithmeticException() {
        }
    }

    static class OffsetInterval {
        private final int endWallTimeSeconds;
        private final int isDst;
        private final int startWallTimeSeconds;
        private final int totalOffsetSeconds;

        private OffsetInterval(int n, int n2, int n3, int n4) {
            this.startWallTimeSeconds = n;
            this.endWallTimeSeconds = n2;
            this.isDst = n3;
            this.totalOffsetSeconds = n4;
        }

        public static OffsetInterval create(ZoneInfo zoneInfo, int n) {
            if (n >= -1 && n < zoneInfo.mTransitions.length) {
                if (n == -1) {
                    n = zoneInfo.mEarliestRawOffset / 1000;
                    int n2 = ZoneInfo.saturated32BitAdd(zoneInfo.mTransitions[0], n);
                    if (Integer.MIN_VALUE == n2) {
                        return null;
                    }
                    return new OffsetInterval(Integer.MIN_VALUE, n2, 0, n);
                }
                int n3 = zoneInfo.mRawOffset / 1000;
                int n4 = zoneInfo.mTypes[n] & 255;
                int n5 = zoneInfo.mOffsets[n4] + n3;
                n3 = n == zoneInfo.mTransitions.length - 1 ? Integer.MAX_VALUE : ZoneInfo.saturated32BitAdd(zoneInfo.mTransitions[n + 1], n5);
                n4 = zoneInfo.mIsDsts[n4];
                n = ZoneInfo.saturated32BitAdd(zoneInfo.mTransitions[n], n5);
                if (n == n3) {
                    return null;
                }
                return new OffsetInterval(n, n3, n4, n5);
            }
            return null;
        }

        public boolean containsWallTime(long l) {
            boolean bl = l >= (long)this.startWallTimeSeconds && l < (long)this.endWallTimeSeconds;
            return bl;
        }

        public long getEndWallTimeSeconds() {
            return this.endWallTimeSeconds;
        }

        public int getIsDst() {
            return this.isDst;
        }

        public long getStartWallTimeSeconds() {
            return this.startWallTimeSeconds;
        }

        public int getTotalOffsetSeconds() {
            return this.totalOffsetSeconds;
        }
    }

    public static class WallTime {
        private final GregorianCalendar calendar = new GregorianCalendar(0, 0, 0, 0, 0, 0);
        private int gmtOffsetSeconds;
        private int hour;
        private int isDst;
        private int minute;
        private int month;
        private int monthDay;
        private int second;
        private int weekDay;
        private int year;
        private int yearDay;

        public WallTime() {
            this.calendar.setTimeZone(TimeZone.getTimeZone("UTC"));
        }

        private void copyFieldsFromCalendar() {
            this.year = this.calendar.get(1);
            this.month = this.calendar.get(2);
            this.monthDay = this.calendar.get(5);
            this.hour = this.calendar.get(11);
            this.minute = this.calendar.get(12);
            this.second = this.calendar.get(13);
            this.weekDay = this.calendar.get(7) - 1;
            this.yearDay = this.calendar.get(6) - 1;
        }

        private void copyFieldsToCalendar() {
            this.calendar.set(1, this.year);
            this.calendar.set(2, this.month);
            this.calendar.set(5, this.monthDay);
            this.calendar.set(11, this.hour);
            this.calendar.set(12, this.minute);
            this.calendar.set(13, this.second);
            this.calendar.set(14, 0);
        }

        private Integer doWallTimeSearch(ZoneInfo zoneInfo, int n, int n2, boolean bl) throws CheckedArithmeticException {
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            do {
                int n6 = (n3 + 1) / 2;
                int n7 = 1;
                int n8 = 1;
                int n9 = 1;
                if (n3 % 2 == 1) {
                    n6 *= -1;
                }
                if (n6 > 0 && n4 != 0) {
                    n7 = n4;
                } else if (n6 < 0 && n5 != 0) {
                    n7 = n4;
                } else {
                    int n10 = n + n6;
                    OffsetInterval offsetInterval = OffsetInterval.create(zoneInfo, n10);
                    if (offsetInterval == null) {
                        n7 = n6 > 0 ? 1 : 0;
                        n6 = n6 < 0 ? n9 : 0;
                        n7 |= n4;
                        n5 |= n6;
                    } else {
                        Integer n11;
                        if (bl) {
                            if (offsetInterval.containsWallTime(n2) && (this.isDst == -1 || offsetInterval.getIsDst() == this.isDst)) {
                                n = offsetInterval.getTotalOffsetSeconds();
                                n2 = ZoneInfo.checked32BitSubtract(n2, n);
                                this.copyFieldsFromCalendar();
                                this.isDst = offsetInterval.getIsDst();
                                this.gmtOffsetSeconds = n;
                                return n2;
                            }
                        } else if (this.isDst != offsetInterval.getIsDst() && (n11 = this.tryOffsetAdjustments(zoneInfo, n2, offsetInterval, n10, this.isDst)) != null) {
                            return n11;
                        }
                        if (n6 > 0) {
                            n6 = offsetInterval.getEndWallTimeSeconds() - (long)n2 > 86400L ? n7 : 0;
                            n7 = n4;
                            if (n6 != 0) {
                                n7 = 1;
                            }
                        } else {
                            n7 = n4;
                            if (n6 < 0) {
                                n6 = (long)n2 - offsetInterval.getStartWallTimeSeconds() >= 86400L ? n8 : 0;
                                n7 = n4;
                                if (n6 != 0) {
                                    n5 = 1;
                                    n7 = n4;
                                }
                            }
                        }
                    }
                }
                if (n7 != 0 && n5 != 0) {
                    return null;
                }
                ++n3;
                n4 = n7;
            } while (true);
        }

        private static int[] getOffsetsOfType(ZoneInfo arrn, int n, int n2) {
            int[] arrn2 = new int[((ZoneInfo)arrn).mOffsets.length + 1];
            boolean[] arrbl = new boolean[((ZoneInfo)arrn).mOffsets.length];
            int n3 = 0;
            int n4 = 0;
            boolean bl = false;
            boolean bl2 = false;
            do {
                boolean bl3;
                int n5;
                boolean bl4;
                n4 = n5 = n4 * -1;
                if (n5 >= 0) {
                    n4 = n5 + 1;
                }
                n5 = n + n4;
                if (n4 < 0 && n5 < -1) {
                    bl3 = true;
                    n5 = n3;
                    bl4 = bl;
                } else if (n4 > 0 && n5 >= ((ZoneInfo)arrn).mTypes.length) {
                    bl4 = true;
                    n5 = n3;
                    bl3 = bl2;
                } else if (n5 == -1) {
                    n5 = n3;
                    bl4 = bl;
                    bl3 = bl2;
                    if (n2 == 0) {
                        arrn2[n3] = 0;
                        n5 = n3 + 1;
                        bl4 = bl;
                        bl3 = bl2;
                    }
                } else {
                    int n6 = ((ZoneInfo)arrn).mTypes[n5] & 255;
                    n5 = n3;
                    bl4 = bl;
                    bl3 = bl2;
                    if (!arrbl[n6]) {
                        n5 = n3;
                        if (((ZoneInfo)arrn).mIsDsts[n6] == n2) {
                            arrn2[n3] = ((ZoneInfo)arrn).mOffsets[n6];
                            n5 = n3 + 1;
                        }
                        arrbl[n6] = true;
                        bl3 = bl2;
                        bl4 = bl;
                    }
                }
                if (bl4 && bl3) {
                    arrn = new int[n5];
                    System.arraycopy(arrn2, 0, arrn, 0, n5);
                    return arrn;
                }
                n3 = n5;
                bl = bl4;
                bl2 = bl3;
            } while (true);
        }

        private Integer tryOffsetAdjustments(ZoneInfo zoneInfo, int n, OffsetInterval offsetInterval, int n2, int n3) throws CheckedArithmeticException {
            int[] arrn = WallTime.getOffsetsOfType(zoneInfo, n2, n3);
            for (n2 = 0; n2 < arrn.length; ++n2) {
                int n4 = zoneInfo.mRawOffset / 1000;
                int n5 = arrn[n2];
                n3 = offsetInterval.getTotalOffsetSeconds();
                n5 = ZoneInfo.checked32BitAdd(n, n3 - (n5 + n4));
                if (!offsetInterval.containsWallTime(n5)) continue;
                n = ZoneInfo.checked32BitSubtract(n5, n3);
                this.calendar.setTimeInMillis((long)n5 * 1000L);
                this.copyFieldsFromCalendar();
                this.isDst = offsetInterval.getIsDst();
                this.gmtOffsetSeconds = n3;
                return n;
            }
            return null;
        }

        public int getGmtOffset() {
            return this.gmtOffsetSeconds;
        }

        public int getHour() {
            return this.hour;
        }

        public int getIsDst() {
            return this.isDst;
        }

        public int getMinute() {
            return this.minute;
        }

        public int getMonth() {
            return this.month;
        }

        public int getMonthDay() {
            return this.monthDay;
        }

        public int getSecond() {
            return this.second;
        }

        public int getWeekDay() {
            return this.weekDay;
        }

        public int getYear() {
            return this.year;
        }

        public int getYearDay() {
            return this.yearDay;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void localtime(int n, ZoneInfo zoneInfo) {
            try {
                int n2;
                int n3 = zoneInfo.mRawOffset / 1000;
                if (zoneInfo.mTransitions.length == 0) {
                    n2 = 0;
                } else {
                    n2 = zoneInfo.findOffsetIndexForTimeInSeconds(n);
                    if (n2 == -1) {
                        n3 = zoneInfo.mEarliestRawOffset / 1000;
                        n2 = 0;
                    } else {
                        n3 += zoneInfo.mOffsets[n2];
                        n2 = zoneInfo.mIsDsts[n2];
                    }
                }
                n = ZoneInfo.checked32BitAdd(n, n3);
                this.calendar.setTimeInMillis((long)n * 1000L);
                this.copyFieldsFromCalendar();
                this.isDst = n2;
                this.gmtOffsetSeconds = n3;
                return;
            }
            catch (CheckedArithmeticException checkedArithmeticException) {
                // empty catch block
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int mktime(ZoneInfo serializable) {
            Integer n;
            block16 : {
                int n2;
                Integer n3;
                int n4;
                block14 : {
                    block15 : {
                        int n5;
                        block12 : {
                            int n6;
                            block13 : {
                                n4 = this.isDst;
                                n5 = -1;
                                if (n4 > 0) {
                                    this.isDst = 1;
                                    n4 = 1;
                                } else if (n4 < 0) {
                                    this.isDst = -1;
                                    n4 = -1;
                                } else {
                                    n4 = 0;
                                }
                                this.isDst = n4;
                                this.copyFieldsToCalendar();
                                long l = this.calendar.getTimeInMillis() / 1000L;
                                if (Integer.MIN_VALUE > l) return -1;
                                if (l > Integer.MAX_VALUE) return -1;
                                n4 = (int)l;
                                try {
                                    n6 = ((ZoneInfo)serializable).mRawOffset / 1000;
                                    n2 = ZoneInfo.checked32BitSubtract(n4, n6);
                                    if (((ZoneInfo)serializable).mTransitions.length != 0) break block12;
                                    if (this.isDst <= 0) break block13;
                                    return -1;
                                }
                                catch (CheckedArithmeticException checkedArithmeticException) {
                                    return -1;
                                }
                            }
                            this.copyFieldsFromCalendar();
                            this.isDst = 0;
                            this.gmtOffsetSeconds = n6;
                            return n2;
                        }
                        n2 = ((ZoneInfo)serializable).findTransitionIndex(n2);
                        if (this.isDst >= 0) break block14;
                        if ((serializable = this.doWallTimeSearch((ZoneInfo)serializable, n2, n4, true)) != null) break block15;
                        return n5;
                    }
                    return (Integer)serializable;
                }
                n = n3 = this.doWallTimeSearch((ZoneInfo)serializable, n2, n4, true);
                if (n3 != null) break block16;
                n = this.doWallTimeSearch((ZoneInfo)serializable, n2, n4, false);
            }
            serializable = n;
            if (n != null) return (Integer)serializable;
            serializable = Integer.valueOf(-1);
            return (Integer)serializable;
        }

        public void setGmtOffset(int n) {
            this.gmtOffsetSeconds = n;
        }

        public void setHour(int n) {
            this.hour = n;
        }

        public void setIsDst(int n) {
            this.isDst = n;
        }

        public void setMinute(int n) {
            this.minute = n;
        }

        public void setMonth(int n) {
            this.month = n;
        }

        public void setMonthDay(int n) {
            this.monthDay = n;
        }

        public void setSecond(int n) {
            this.second = n;
        }

        public void setWeekDay(int n) {
            this.weekDay = n;
        }

        public void setYear(int n) {
            this.year = n;
        }

        public void setYearDay(int n) {
            this.yearDay = n;
        }
    }

}

