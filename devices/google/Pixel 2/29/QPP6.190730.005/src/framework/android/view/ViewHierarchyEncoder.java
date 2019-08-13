/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ViewHierarchyEncoder {
    private static final byte SIG_BOOLEAN = 90;
    private static final byte SIG_BYTE = 66;
    private static final byte SIG_DOUBLE = 68;
    private static final short SIG_END_MAP = 0;
    private static final byte SIG_FLOAT = 70;
    private static final byte SIG_INT = 73;
    private static final byte SIG_LONG = 74;
    private static final byte SIG_MAP = 77;
    private static final byte SIG_SHORT = 83;
    private static final byte SIG_STRING = 82;
    private Charset mCharset = Charset.forName("utf-8");
    private short mPropertyId = (short)(true ? 1 : 0);
    private final Map<String, Short> mPropertyNames = new HashMap<String, Short>(200);
    private final DataOutputStream mStream;

    public ViewHierarchyEncoder(ByteArrayOutputStream byteArrayOutputStream) {
        this.mStream = new DataOutputStream(byteArrayOutputStream);
    }

    private short createPropertyIndex(String string2) {
        Short s;
        Short s2 = s = this.mPropertyNames.get(string2);
        if (s == null) {
            short s3 = this.mPropertyId;
            this.mPropertyId = (short)(s3 + 1);
            s2 = s3;
            this.mPropertyNames.put(string2, s2);
        }
        return s2;
    }

    private void endPropertyMap() {
        this.writeShort((short)0);
    }

    private void startPropertyMap() {
        try {
            this.mStream.write(77);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void writeBoolean(boolean bl) {
        this.mStream.write(90);
        DataOutputStream dataOutputStream = this.mStream;
        int n = bl ? 1 : 0;
        try {
            dataOutputStream.write(n);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void writeFloat(float f) {
        try {
            this.mStream.write(70);
            this.mStream.writeFloat(f);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void writeInt(int n) {
        try {
            this.mStream.write(73);
            this.mStream.writeInt(n);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void writeShort(short s) {
        try {
            this.mStream.write(83);
            this.mStream.writeShort(s);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    private void writeString(String arrby) {
        byte[] arrby2 = arrby;
        if (arrby == null) {
            arrby2 = "";
        }
        try {
            this.mStream.write(82);
            arrby = arrby2.getBytes(this.mCharset);
            short s = (short)Math.min(arrby.length, 32767);
            this.mStream.writeShort(s);
            this.mStream.write(arrby, 0, s);
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @UnsupportedAppUsage
    public void addProperty(String string2, float f) {
        this.writeShort(this.createPropertyIndex(string2));
        this.writeFloat(f);
    }

    @UnsupportedAppUsage
    public void addProperty(String string2, int n) {
        this.writeShort(this.createPropertyIndex(string2));
        this.writeInt(n);
    }

    @UnsupportedAppUsage
    public void addProperty(String string2, String string3) {
        this.writeShort(this.createPropertyIndex(string2));
        this.writeString(string3);
    }

    public void addProperty(String string2, short s) {
        this.writeShort(this.createPropertyIndex(string2));
        this.writeShort(s);
    }

    @UnsupportedAppUsage
    public void addProperty(String string2, boolean bl) {
        this.writeShort(this.createPropertyIndex(string2));
        this.writeBoolean(bl);
    }

    public void addPropertyKey(String string2) {
        this.writeShort(this.createPropertyIndex(string2));
    }

    public void beginObject(Object object) {
        this.startPropertyMap();
        this.addProperty("meta:__name__", object.getClass().getName());
        this.addProperty("meta:__hash__", object.hashCode());
    }

    public void endObject() {
        this.endPropertyMap();
    }

    public void endStream() {
        this.startPropertyMap();
        this.addProperty("__name__", "propertyIndex");
        for (Map.Entry<String, Short> entry : this.mPropertyNames.entrySet()) {
            this.writeShort(entry.getValue());
            this.writeString(entry.getKey());
        }
        this.endPropertyMap();
    }
}

