/*
 * Decompiled with CFR 0.145.
 */
package android.system;

import android.system.OsConstants;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class UnixSocketAddress
extends SocketAddress {
    private static final int NAMED_PATH_LENGTH = OsConstants.UNIX_PATH_MAX;
    private static final byte[] UNNAMED_PATH = new byte[0];
    private byte[] sun_path;

    private UnixSocketAddress(byte[] arrby) {
        if (arrby != null) {
            if (arrby.length <= NAMED_PATH_LENGTH) {
                if (arrby.length == 0) {
                    this.sun_path = UNNAMED_PATH;
                } else {
                    this.sun_path = new byte[arrby.length];
                    System.arraycopy((byte[])arrby, (int)0, (byte[])this.sun_path, (int)0, (int)arrby.length);
                }
                return;
            }
            throw new IllegalArgumentException("sun_path exceeds the maximum length");
        }
        throw new IllegalArgumentException("sun_path must not be null");
    }

    public static UnixSocketAddress createAbstract(String arrby) {
        byte[] arrby2 = arrby.getBytes(StandardCharsets.UTF_8);
        arrby = new byte[arrby2.length + 1];
        System.arraycopy((byte[])arrby2, (int)0, (byte[])arrby, (int)1, (int)arrby2.length);
        return new UnixSocketAddress(arrby);
    }

    public static UnixSocketAddress createFileSystem(String arrby) {
        arrby = arrby.getBytes(StandardCharsets.UTF_8);
        byte[] arrby2 = new byte[arrby.length + 1];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby.length);
        return new UnixSocketAddress(arrby2);
    }

    public static UnixSocketAddress createUnnamed() {
        return new UnixSocketAddress(UNNAMED_PATH);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (UnixSocketAddress)object;
            return Arrays.equals(this.sun_path, ((UnixSocketAddress)object).sun_path);
        }
        return false;
    }

    public byte[] getSunPath() {
        byte[] arrby = this.sun_path;
        if (arrby.length == 0) {
            return arrby;
        }
        byte[] arrby2 = new byte[arrby.length];
        System.arraycopy((byte[])arrby, (int)0, (byte[])arrby2, (int)0, (int)arrby.length);
        return arrby2;
    }

    public int hashCode() {
        return Arrays.hashCode(this.sun_path);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UnixSocketAddress[sun_path=");
        stringBuilder.append(Arrays.toString(this.sun_path));
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}

