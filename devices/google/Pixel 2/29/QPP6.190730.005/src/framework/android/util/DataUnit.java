/*
 * Decompiled with CFR 0.145.
 */
package android.util;

public enum DataUnit {
    KILOBYTES{

        @Override
        public long toBytes(long l) {
            return 1000L * l;
        }
    }
    ,
    MEGABYTES{

        @Override
        public long toBytes(long l) {
            return 1000000L * l;
        }
    }
    ,
    GIGABYTES{

        @Override
        public long toBytes(long l) {
            return 1000000000L * l;
        }
    }
    ,
    KIBIBYTES{

        @Override
        public long toBytes(long l) {
            return 1024L * l;
        }
    }
    ,
    MEBIBYTES{

        @Override
        public long toBytes(long l) {
            return 0x100000L * l;
        }
    }
    ,
    GIBIBYTES{

        @Override
        public long toBytes(long l) {
            return 0x40000000L * l;
        }
    };
    

    public long toBytes(long l) {
        throw new AbstractMethodError();
    }

}

