/*
 * Decompiled with CFR 0.145.
 */
package java.nio.file.attribute;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class FileTime
implements Comparable<FileTime> {
    private static final long DAYS_PER_10000_YEARS = 3652425L;
    private static final long HOURS_PER_DAY = 24L;
    private static final long MAX_SECOND = 31556889864403199L;
    private static final long MICROS_PER_SECOND = 1000000L;
    private static final long MILLIS_PER_SECOND = 1000L;
    private static final long MINUTES_PER_HOUR = 60L;
    private static final long MIN_SECOND = -31557014167219200L;
    private static final int NANOS_PER_MICRO = 1000;
    private static final int NANOS_PER_MILLI = 1000000;
    private static final long NANOS_PER_SECOND = 1000000000L;
    private static final long SECONDS_0000_TO_1970 = 62167219200L;
    private static final long SECONDS_PER_10000_YEARS = 315569520000L;
    private static final long SECONDS_PER_DAY = 86400L;
    private static final long SECONDS_PER_HOUR = 3600L;
    private static final long SECONDS_PER_MINUTE = 60L;
    private Instant instant;
    private final TimeUnit unit;
    private final long value;
    private String valueAsString;

    private FileTime(long l, TimeUnit timeUnit, Instant instant) {
        this.value = l;
        this.unit = timeUnit;
        this.instant = instant;
    }

    private StringBuilder append(StringBuilder stringBuilder, int n, int n2) {
        while (n > 0) {
            stringBuilder.append((char)(n2 / n + 48));
            n2 %= n;
            n /= 10;
        }
        return stringBuilder;
    }

    public static FileTime from(long l, TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit, "unit");
        return new FileTime(l, timeUnit, null);
    }

    public static FileTime from(Instant instant) {
        Objects.requireNonNull(instant, "instant");
        return new FileTime(0L, null, instant);
    }

    public static FileTime fromMillis(long l) {
        return new FileTime(l, TimeUnit.MILLISECONDS, null);
    }

    private static long scale(long l, long l2, long l3) {
        if (l > l3) {
            return Long.MAX_VALUE;
        }
        if (l < -l3) {
            return Long.MIN_VALUE;
        }
        return l * l2;
    }

    private long toDays() {
        TimeUnit timeUnit = this.unit;
        if (timeUnit != null) {
            return timeUnit.toDays(this.value);
        }
        return TimeUnit.SECONDS.toDays(this.toInstant().getEpochSecond());
    }

    private long toExcessNanos(long l) {
        TimeUnit timeUnit = this.unit;
        if (timeUnit != null) {
            return timeUnit.toNanos(this.value - timeUnit.convert(l, TimeUnit.DAYS));
        }
        return TimeUnit.SECONDS.toNanos(this.toInstant().getEpochSecond() - TimeUnit.DAYS.toSeconds(l));
    }

    @Override
    public int compareTo(FileTime fileTime) {
        TimeUnit timeUnit = this.unit;
        if (timeUnit != null && timeUnit == fileTime.unit) {
            return Long.compare(this.value, fileTime.value);
        }
        long l = this.toInstant().getEpochSecond();
        int n = Long.compare(l, fileTime.toInstant().getEpochSecond());
        if (n != 0) {
            return n;
        }
        n = Long.compare(this.toInstant().getNano(), fileTime.toInstant().getNano());
        if (n != 0) {
            return n;
        }
        if (l != 31556889864403199L && l != -31557014167219200L) {
            return 0;
        }
        long l2 = this.toDays();
        if (l2 == (l = fileTime.toDays())) {
            return Long.compare(this.toExcessNanos(l2), fileTime.toExcessNanos(l));
        }
        return Long.compare(l2, l);
    }

    public boolean equals(Object object) {
        boolean bl;
        boolean bl2 = object instanceof FileTime;
        boolean bl3 = bl = false;
        if (bl2) {
            bl3 = bl;
            if (this.compareTo((FileTime)object) == 0) {
                bl3 = true;
            }
        }
        return bl3;
    }

    public int hashCode() {
        return this.toInstant().hashCode();
    }

    public long to(TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit, "unit");
        TimeUnit timeUnit2 = this.unit;
        if (timeUnit2 != null) {
            return timeUnit.convert(this.value, timeUnit2);
        }
        long l = timeUnit.convert(this.instant.getEpochSecond(), TimeUnit.SECONDS);
        long l2 = Long.MIN_VALUE;
        if (l != Long.MIN_VALUE && l != Long.MAX_VALUE) {
            long l3 = timeUnit.convert(this.instant.getNano(), TimeUnit.NANOSECONDS);
            long l4 = l + l3;
            if (((l ^ l4) & (l3 ^ l4)) < 0L) {
                if (l >= 0L) {
                    l2 = Long.MAX_VALUE;
                }
                return l2;
            }
            return l4;
        }
        return l;
    }

    public Instant toInstant() {
        if (this.instant == null) {
            long l;
            int n = 0;
            switch (this.unit) {
                default: {
                    throw new AssertionError((Object)"Unit not handled");
                }
                case NANOSECONDS: {
                    l = Math.floorDiv(this.value, 1000000000L);
                    n = (int)Math.floorMod(this.value, 1000000000L);
                    break;
                }
                case MICROSECONDS: {
                    l = Math.floorDiv(this.value, 1000000L);
                    n = (int)Math.floorMod(this.value, 1000000L) * 1000;
                    break;
                }
                case MILLISECONDS: {
                    l = Math.floorDiv(this.value, 1000L);
                    n = (int)Math.floorMod(this.value, 1000L) * 1000000;
                    break;
                }
                case SECONDS: {
                    l = this.value;
                    break;
                }
                case MINUTES: {
                    l = FileTime.scale(this.value, 60L, 0x222222222222222L);
                    break;
                }
                case HOURS: {
                    l = FileTime.scale(this.value, 3600L, 2562047788015215L);
                    break;
                }
                case DAYS: {
                    l = FileTime.scale(this.value, 86400L, 106751991167300L);
                }
            }
            this.instant = l <= -31557014167219200L ? Instant.MIN : (l >= 31556889864403199L ? Instant.MAX : Instant.ofEpochSecond(l, n));
        }
        return this.instant;
    }

    public long toMillis() {
        TimeUnit timeUnit = this.unit;
        if (timeUnit != null) {
            return timeUnit.toMillis(this.value);
        }
        long l = this.instant.getEpochSecond();
        int n = this.instant.getNano();
        long l2 = l * 1000L;
        if ((Math.abs(l) | 1000L) >>> 31 != 0L && l2 / 1000L != l) {
            l2 = l < 0L ? Long.MIN_VALUE : Long.MAX_VALUE;
            return l2;
        }
        return (long)(n / 1000000) + l2;
    }

    public String toString() {
        if (this.valueAsString == null) {
            long l;
            long l2;
            LocalDateTime localDateTime;
            int n = 0;
            if (this.instant == null && this.unit.compareTo(TimeUnit.SECONDS) >= 0) {
                l = this.unit.toSeconds(this.value);
            } else {
                l = this.toInstant().getEpochSecond();
                n = this.toInstant().getNano();
            }
            if (l >= -62167219200L) {
                l2 = l - 315569520000L + 62167219200L;
                l = Math.floorDiv(l2, 315569520000L);
                localDateTime = LocalDateTime.ofEpochSecond(Math.floorMod(l2, 315569520000L) - 62167219200L, n, ZoneOffset.UTC);
                n = localDateTime.getYear() + (int)(l + 1L) * 10000;
            } else {
                l2 = l + 62167219200L;
                l = l2 / 315569520000L;
                localDateTime = LocalDateTime.ofEpochSecond(l2 % 315569520000L - 62167219200L, n, ZoneOffset.UTC);
                n = localDateTime.getYear() + (int)l * 10000;
            }
            int n2 = n;
            if (n <= 0) {
                n2 = n - 1;
            }
            int n3 = localDateTime.getNano();
            StringBuilder stringBuilder = new StringBuilder(64);
            String string = n2 < 0 ? "-" : "";
            stringBuilder.append(string);
            n = Math.abs(n2);
            if (n < 10000) {
                this.append(stringBuilder, 1000, Math.abs(n));
            } else {
                stringBuilder.append(String.valueOf(n));
            }
            stringBuilder.append('-');
            this.append(stringBuilder, 10, localDateTime.getMonthValue());
            stringBuilder.append('-');
            this.append(stringBuilder, 10, localDateTime.getDayOfMonth());
            stringBuilder.append('T');
            this.append(stringBuilder, 10, localDateTime.getHour());
            stringBuilder.append(':');
            this.append(stringBuilder, 10, localDateTime.getMinute());
            stringBuilder.append(':');
            this.append(stringBuilder, 10, localDateTime.getSecond());
            if (n3 != 0) {
                stringBuilder.append('.');
                n = 100000000;
                n2 = n3;
                while (n2 % 10 == 0) {
                    n2 /= 10;
                    n /= 10;
                }
                this.append(stringBuilder, n, n2);
            }
            stringBuilder.append('Z');
            this.valueAsString = stringBuilder.toString();
        }
        return this.valueAsString;
    }

}

