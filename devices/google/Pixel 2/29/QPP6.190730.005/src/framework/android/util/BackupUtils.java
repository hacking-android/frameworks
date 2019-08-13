/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class BackupUtils {
    public static final int NOT_NULL = 1;
    public static final int NULL = 0;

    public static String readString(DataInputStream object) throws IOException {
        object = ((DataInputStream)object).readByte() == 1 ? ((DataInputStream)object).readUTF() : null;
        return object;
    }

    public static void writeString(DataOutputStream dataOutputStream, String string2) throws IOException {
        if (string2 != null) {
            dataOutputStream.writeByte(1);
            dataOutputStream.writeUTF(string2);
        } else {
            dataOutputStream.writeByte(0);
        }
    }

    public static class BadVersionException
    extends Exception {
        public BadVersionException(String string2) {
            super(string2);
        }
    }

}

