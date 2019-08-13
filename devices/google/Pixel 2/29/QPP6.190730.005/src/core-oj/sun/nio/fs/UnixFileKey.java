/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

class UnixFileKey {
    private final long st_dev;
    private final long st_ino;

    UnixFileKey(long l, long l2) {
        this.st_dev = l;
        this.st_ino = l2;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof UnixFileKey)) {
            return false;
        }
        object = (UnixFileKey)object;
        if (this.st_dev != ((UnixFileKey)object).st_dev || this.st_ino != ((UnixFileKey)object).st_ino) {
            bl = false;
        }
        return bl;
    }

    public int hashCode() {
        long l = this.st_dev;
        int n = (int)(l ^ l >>> 32);
        l = this.st_ino;
        return n + (int)(l >>> 32 ^ l);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(dev=");
        stringBuilder.append(Long.toHexString(this.st_dev));
        stringBuilder.append(",ino=");
        stringBuilder.append(this.st_ino);
        stringBuilder.append(')');
        return stringBuilder.toString();
    }
}

