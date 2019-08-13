/*
 * Decompiled with CFR 0.145.
 */
package android.util;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.proto.ProtoOutputStream;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.format.DateTimeParseException;
import java.util.Iterator;

public class KeyValueListParser {
    private final TextUtils.StringSplitter mSplitter;
    private final ArrayMap<String, String> mValues = new ArrayMap();

    public KeyValueListParser(char c) {
        this.mSplitter = new TextUtils.SimpleStringSplitter(c);
    }

    public boolean getBoolean(String string2, boolean bl) {
        if ((string2 = this.mValues.get(string2)) != null) {
            try {
                boolean bl2 = Boolean.parseBoolean(string2);
                return bl2;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return bl;
    }

    public long getDurationMillis(String string2, long l) {
        if ((string2 = this.mValues.get(string2)) != null) {
            try {
                if (!string2.startsWith("P") && !string2.startsWith("p")) {
                    return Long.parseLong(string2);
                }
                long l2 = Duration.parse(string2).toMillis();
                return l2;
            }
            catch (NumberFormatException | DateTimeParseException runtimeException) {
                // empty catch block
            }
        }
        return l;
    }

    public float getFloat(String string2, float f) {
        if ((string2 = this.mValues.get(string2)) != null) {
            try {
                float f2 = Float.parseFloat(string2);
                return f2;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return f;
    }

    public int getInt(String string2, int n) {
        if ((string2 = this.mValues.get(string2)) != null) {
            try {
                int n2 = Integer.parseInt(string2);
                return n2;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return n;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int[] getIntArray(String object, int[] arrn) {
        String[] arrstring;
        int n;
        if ((object = this.mValues.get(object)) == null) return arrn;
        try {
            arrstring = ((String)object).split(":");
            if (arrstring.length <= 0) return arrn;
            object = new int[arrstring.length];
            n = 0;
        }
        catch (NumberFormatException numberFormatException) {
            // empty catch block
        }
        do {
            if (n >= arrstring.length) return object;
            object[n] = Integer.parseInt(arrstring[n]);
            ++n;
            continue;
            break;
        } while (true);
        return arrn;
    }

    public long getLong(String string2, long l) {
        if ((string2 = this.mValues.get(string2)) != null) {
            try {
                long l2 = Long.parseLong(string2);
                return l2;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        return l;
    }

    public String getString(String string2, String string3) {
        if ((string2 = this.mValues.get(string2)) != null) {
            return string2;
        }
        return string3;
    }

    public String keyAt(int n) {
        return this.mValues.keyAt(n);
    }

    public void setString(String string2) throws IllegalArgumentException {
        this.mValues.clear();
        if (string2 != null) {
            this.mSplitter.setString(string2);
            Object object = this.mSplitter.iterator();
            while (object.hasNext()) {
                String string3 = (String)object.next();
                int n = string3.indexOf(61);
                if (n >= 0) {
                    this.mValues.put(string3.substring(0, n).trim(), string3.substring(n + 1).trim());
                    continue;
                }
                this.mValues.clear();
                object = new StringBuilder();
                ((StringBuilder)object).append("'");
                ((StringBuilder)object).append(string3);
                ((StringBuilder)object).append("' in '");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append("' is not a valid key-value pair");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
        }
    }

    public int size() {
        return this.mValues.size();
    }

    public static class FloatValue {
        private final float mDefaultValue;
        private final String mKey;
        private float mValue;

        public FloatValue(String string2, float f) {
            this.mKey = string2;
            this.mValue = this.mDefaultValue = f;
        }

        public void dump(PrintWriter printWriter, String string2) {
            printWriter.print(string2);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(ProtoOutputStream protoOutputStream, long l) {
            protoOutputStream.write(l, this.mValue);
        }

        public float getDefaultValue() {
            return this.mDefaultValue;
        }

        public String getKey() {
            return this.mKey;
        }

        public float getValue() {
            return this.mValue;
        }

        public void parse(KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getFloat(this.mKey, this.mDefaultValue);
        }

        public void setValue(float f) {
            this.mValue = f;
        }
    }

    public static class IntValue {
        private final int mDefaultValue;
        private final String mKey;
        private int mValue;

        public IntValue(String string2, int n) {
            this.mKey = string2;
            this.mValue = this.mDefaultValue = n;
        }

        public void dump(PrintWriter printWriter, String string2) {
            printWriter.print(string2);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(ProtoOutputStream protoOutputStream, long l) {
            protoOutputStream.write(l, this.mValue);
        }

        public int getDefaultValue() {
            return this.mDefaultValue;
        }

        public String getKey() {
            return this.mKey;
        }

        public int getValue() {
            return this.mValue;
        }

        public void parse(KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getInt(this.mKey, this.mDefaultValue);
        }

        public void setValue(int n) {
            this.mValue = n;
        }
    }

    public static class LongValue {
        private final long mDefaultValue;
        private final String mKey;
        private long mValue;

        public LongValue(String string2, long l) {
            this.mKey = string2;
            this.mValue = this.mDefaultValue = l;
        }

        public void dump(PrintWriter printWriter, String string2) {
            printWriter.print(string2);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(ProtoOutputStream protoOutputStream, long l) {
            protoOutputStream.write(l, this.mValue);
        }

        public long getDefaultValue() {
            return this.mDefaultValue;
        }

        public String getKey() {
            return this.mKey;
        }

        public long getValue() {
            return this.mValue;
        }

        public void parse(KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getLong(this.mKey, this.mDefaultValue);
        }

        public void setValue(long l) {
            this.mValue = l;
        }
    }

    public static class StringValue {
        private final String mDefaultValue;
        private final String mKey;
        private String mValue;

        public StringValue(String string2, String string3) {
            this.mKey = string2;
            this.mValue = this.mDefaultValue = string3;
        }

        public void dump(PrintWriter printWriter, String string2) {
            printWriter.print(string2);
            printWriter.print(this.mKey);
            printWriter.print("=");
            printWriter.print(this.mValue);
            printWriter.println();
        }

        public void dumpProto(ProtoOutputStream protoOutputStream, long l) {
            protoOutputStream.write(l, this.mValue);
        }

        public String getDefaultValue() {
            return this.mDefaultValue;
        }

        public String getKey() {
            return this.mKey;
        }

        public String getValue() {
            return this.mValue;
        }

        public void parse(KeyValueListParser keyValueListParser) {
            this.mValue = keyValueListParser.getString(this.mKey, this.mDefaultValue);
        }

        public void setValue(String string2) {
            this.mValue = string2;
        }
    }

}

