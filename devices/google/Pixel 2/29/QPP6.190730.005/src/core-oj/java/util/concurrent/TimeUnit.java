/*
 * Decompiled with CFR 0.145.
 */
package java.util.concurrent;

public enum TimeUnit {
    NANOSECONDS{

        @Override
        public long convert(long l, TimeUnit timeUnit) {
            return timeUnit.toNanos(l);
        }

        @Override
        int excessNanos(long l, long l2) {
            return (int)(l - 1000000L * l2);
        }

        @Override
        public long toDays(long l) {
            return l / 86400000000000L;
        }

        @Override
        public long toHours(long l) {
            return l / 3600000000000L;
        }

        @Override
        public long toMicros(long l) {
            return l / 1000L;
        }

        @Override
        public long toMillis(long l) {
            return l / 1000000L;
        }

        @Override
        public long toMinutes(long l) {
            return l / 60000000000L;
        }

        @Override
        public long toNanos(long l) {
            return l;
        }

        @Override
        public long toSeconds(long l) {
            return l / 1000000000L;
        }
    }
    ,
    MICROSECONDS{

        @Override
        public long convert(long l, TimeUnit timeUnit) {
            return timeUnit.toMicros(l);
        }

        @Override
        int excessNanos(long l, long l2) {
            return (int)(1000L * l - 1000000L * l2);
        }

        @Override
        public long toDays(long l) {
            return l / 86400000000L;
        }

        @Override
        public long toHours(long l) {
            return l / 3600000000L;
        }

        @Override
        public long toMicros(long l) {
            return l;
        }

        @Override
        public long toMillis(long l) {
            return l / 1000L;
        }

        @Override
        public long toMinutes(long l) {
            return l / 60000000L;
        }

        @Override
        public long toNanos(long l) {
            return 2.x(l, 1000L, 9223372036854775L);
        }

        @Override
        public long toSeconds(long l) {
            return l / 1000000L;
        }
    }
    ,
    MILLISECONDS{

        @Override
        public long convert(long l, TimeUnit timeUnit) {
            return timeUnit.toMillis(l);
        }

        @Override
        int excessNanos(long l, long l2) {
            return 0;
        }

        @Override
        public long toDays(long l) {
            return l / 86400000L;
        }

        @Override
        public long toHours(long l) {
            return l / 3600000L;
        }

        @Override
        public long toMicros(long l) {
            return 3.x(l, 1000L, 9223372036854775L);
        }

        @Override
        public long toMillis(long l) {
            return l;
        }

        @Override
        public long toMinutes(long l) {
            return l / 60000L;
        }

        @Override
        public long toNanos(long l) {
            return 3.x(l, 1000000L, 9223372036854L);
        }

        @Override
        public long toSeconds(long l) {
            return l / 1000L;
        }
    }
    ,
    SECONDS{

        @Override
        public long convert(long l, TimeUnit timeUnit) {
            return timeUnit.toSeconds(l);
        }

        @Override
        int excessNanos(long l, long l2) {
            return 0;
        }

        @Override
        public long toDays(long l) {
            return l / 86400L;
        }

        @Override
        public long toHours(long l) {
            return l / 3600L;
        }

        @Override
        public long toMicros(long l) {
            return 4.x(l, 1000000L, 9223372036854L);
        }

        @Override
        public long toMillis(long l) {
            return 4.x(l, 1000L, 9223372036854775L);
        }

        @Override
        public long toMinutes(long l) {
            return l / 60L;
        }

        @Override
        public long toNanos(long l) {
            return 4.x(l, 1000000000L, 9223372036L);
        }

        @Override
        public long toSeconds(long l) {
            return l;
        }
    }
    ,
    MINUTES{

        @Override
        public long convert(long l, TimeUnit timeUnit) {
            return timeUnit.toMinutes(l);
        }

        @Override
        int excessNanos(long l, long l2) {
            return 0;
        }

        @Override
        public long toDays(long l) {
            return l / 1440L;
        }

        @Override
        public long toHours(long l) {
            return l / 60L;
        }

        @Override
        public long toMicros(long l) {
            return 5.x(l, 60000000L, 153722867280L);
        }

        @Override
        public long toMillis(long l) {
            return 5.x(l, 60000L, 153722867280912L);
        }

        @Override
        public long toMinutes(long l) {
            return l;
        }

        @Override
        public long toNanos(long l) {
            return 5.x(l, 60000000000L, 153722867L);
        }

        @Override
        public long toSeconds(long l) {
            return 5.x(l, 60L, 0x222222222222222L);
        }
    }
    ,
    HOURS{

        @Override
        public long convert(long l, TimeUnit timeUnit) {
            return timeUnit.toHours(l);
        }

        @Override
        int excessNanos(long l, long l2) {
            return 0;
        }

        @Override
        public long toDays(long l) {
            return l / 24L;
        }

        @Override
        public long toHours(long l) {
            return l;
        }

        @Override
        public long toMicros(long l) {
            return 6.x(l, 3600000000L, 2562047788L);
        }

        @Override
        public long toMillis(long l) {
            return 6.x(l, 3600000L, 2562047788015L);
        }

        @Override
        public long toMinutes(long l) {
            return 6.x(l, 60L, 0x222222222222222L);
        }

        @Override
        public long toNanos(long l) {
            return 6.x(l, 3600000000000L, 2562047L);
        }

        @Override
        public long toSeconds(long l) {
            return 6.x(l, 3600L, 2562047788015215L);
        }
    }
    ,
    DAYS{

        @Override
        public long convert(long l, TimeUnit timeUnit) {
            return timeUnit.toDays(l);
        }

        @Override
        int excessNanos(long l, long l2) {
            return 0;
        }

        @Override
        public long toDays(long l) {
            return l;
        }

        @Override
        public long toHours(long l) {
            return 7.x(l, 24L, 0x555555555555555L);
        }

        @Override
        public long toMicros(long l) {
            return 7.x(l, 86400000000L, 106751991L);
        }

        @Override
        public long toMillis(long l) {
            return 7.x(l, 86400000L, 106751991167L);
        }

        @Override
        public long toMinutes(long l) {
            return 7.x(l, 1440L, 6405119470038038L);
        }

        @Override
        public long toNanos(long l) {
            return 7.x(l, 86400000000000L, 106751L);
        }

        @Override
        public long toSeconds(long l) {
            return 7.x(l, 86400L, 106751991167300L);
        }
    };
    
    static final long C0 = 1L;
    static final long C1 = 1000L;
    static final long C2 = 1000000L;
    static final long C3 = 1000000000L;
    static final long C4 = 60000000000L;
    static final long C5 = 3600000000000L;
    static final long C6 = 86400000000000L;
    static final long MAX = Long.MAX_VALUE;

    static long x(long l, long l2, long l3) {
        if (l > l3) {
            return Long.MAX_VALUE;
        }
        if (l < -l3) {
            return Long.MIN_VALUE;
        }
        return l * l2;
    }

    public long convert(long l, TimeUnit timeUnit) {
        throw new AbstractMethodError();
    }

    abstract int excessNanos(long var1, long var3);

    public void sleep(long l) throws InterruptedException {
        if (l > 0L) {
            long l2 = this.toMillis(l);
            Thread.sleep(l2, this.excessNanos(l, l2));
        }
    }

    public void timedJoin(Thread thread, long l) throws InterruptedException {
        if (l > 0L) {
            long l2 = this.toMillis(l);
            thread.join(l2, this.excessNanos(l, l2));
        }
    }

    public void timedWait(Object object, long l) throws InterruptedException {
        if (l > 0L) {
            long l2 = this.toMillis(l);
            object.wait(l2, this.excessNanos(l, l2));
        }
    }

    public long toDays(long l) {
        throw new AbstractMethodError();
    }

    public long toHours(long l) {
        throw new AbstractMethodError();
    }

    public long toMicros(long l) {
        throw new AbstractMethodError();
    }

    public long toMillis(long l) {
        throw new AbstractMethodError();
    }

    public long toMinutes(long l) {
        throw new AbstractMethodError();
    }

    public long toNanos(long l) {
        throw new AbstractMethodError();
    }

    public long toSeconds(long l) {
        throw new AbstractMethodError();
    }

}

