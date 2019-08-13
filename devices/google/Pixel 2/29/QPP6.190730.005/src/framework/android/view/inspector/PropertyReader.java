/*
 * Decompiled with CFR 0.145.
 */
package android.view.inspector;

import android.graphics.Color;

public interface PropertyReader {
    public void readBoolean(int var1, boolean var2);

    public void readByte(int var1, byte var2);

    public void readChar(int var1, char var2);

    public void readColor(int var1, int var2);

    public void readColor(int var1, long var2);

    public void readColor(int var1, Color var2);

    public void readDouble(int var1, double var2);

    public void readFloat(int var1, float var2);

    public void readGravity(int var1, int var2);

    public void readInt(int var1, int var2);

    public void readIntEnum(int var1, int var2);

    public void readIntFlag(int var1, int var2);

    public void readLong(int var1, long var2);

    public void readObject(int var1, Object var2);

    public void readResourceId(int var1, int var2);

    public void readShort(int var1, short var2);

    public static class PropertyTypeMismatchException
    extends RuntimeException {
        public PropertyTypeMismatchException(int n, String string2, String string3) {
            super(PropertyTypeMismatchException.formatMessage(n, string2, string3, null));
        }

        public PropertyTypeMismatchException(int n, String string2, String string3, String string4) {
            super(PropertyTypeMismatchException.formatMessage(n, string2, string3, string4));
        }

        private static String formatMessage(int n, String string2, String string3, String string4) {
            if (string4 == null) {
                return String.format("Attempted to read property with ID 0x%08X as type %s, but the ID is of type %s.", n, string2, string3);
            }
            return String.format("Attempted to read property \"%s\" with ID 0x%08X as type %s, but the ID is of type %s.", string4, n, string2, string3);
        }
    }

}

