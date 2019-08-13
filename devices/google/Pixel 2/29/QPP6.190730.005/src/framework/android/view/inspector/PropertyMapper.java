/*
 * Decompiled with CFR 0.145.
 */
package android.view.inspector;

import java.util.Set;
import java.util.function.IntFunction;

public interface PropertyMapper {
    public int mapBoolean(String var1, int var2);

    public int mapByte(String var1, int var2);

    public int mapChar(String var1, int var2);

    public int mapColor(String var1, int var2);

    public int mapDouble(String var1, int var2);

    public int mapFloat(String var1, int var2);

    public int mapGravity(String var1, int var2);

    public int mapInt(String var1, int var2);

    public int mapIntEnum(String var1, int var2, IntFunction<String> var3);

    public int mapIntFlag(String var1, int var2, IntFunction<Set<String>> var3);

    public int mapLong(String var1, int var2);

    public int mapObject(String var1, int var2);

    public int mapResourceId(String var1, int var2);

    public int mapShort(String var1, int var2);

    public static class PropertyConflictException
    extends RuntimeException {
        public PropertyConflictException(String string2, String string3, String string4) {
            super(String.format("Attempted to map property \"%s\" as type %s, but it is already mapped as %s.", string2, string3, string4));
        }
    }

}

