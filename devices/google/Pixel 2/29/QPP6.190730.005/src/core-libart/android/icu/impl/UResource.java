/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import java.nio.ByteBuffer;

public final class UResource {

    public static interface Array {
        public int getSize();

        public boolean getValue(int var1, Value var2);
    }

    public static final class Key
    implements CharSequence,
    Cloneable,
    Comparable<Key> {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private byte[] bytes;
        private int length;
        private int offset;
        private String s;

        public Key() {
            this.s = "";
        }

        public Key(String string) {
            this.setString(string);
        }

        private Key(byte[] arrby, int n, int n2) {
            this.bytes = arrby;
            this.offset = n;
            this.length = n2;
        }

        private String internalSubString(int n, int n2) {
            StringBuilder stringBuilder = new StringBuilder(n2 - n);
            while (n < n2) {
                stringBuilder.append((char)this.bytes[this.offset + n]);
                ++n;
            }
            return stringBuilder.toString();
        }

        private boolean regionMatches(int n, CharSequence charSequence, int n2) {
            for (int i = 0; i < n2; ++i) {
                if (this.bytes[this.offset + n + i] == charSequence.charAt(i)) continue;
                return false;
            }
            return true;
        }

        private boolean regionMatches(byte[] arrby, int n, int n2) {
            for (int i = 0; i < n2; ++i) {
                if (this.bytes[this.offset + i] == arrby[n + i]) continue;
                return false;
            }
            return true;
        }

        @Override
        public char charAt(int n) {
            return (char)this.bytes[this.offset + n];
        }

        public Key clone() {
            try {
                Key key = (Key)super.clone();
                return key;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                return null;
            }
        }

        @Override
        public int compareTo(Key key) {
            return this.compareTo((CharSequence)key);
        }

        @Override
        public int compareTo(CharSequence charSequence) {
            int n = this.length;
            int n2 = charSequence.length();
            if (n > n2) {
                n = n2;
            }
            for (int i = 0; i < n; ++i) {
                int n3 = this.charAt(i) - charSequence.charAt(i);
                if (n3 == 0) continue;
                return n3;
            }
            return this.length - n2;
        }

        public boolean contentEquals(CharSequence charSequence) {
            boolean bl;
            block5 : {
                block4 : {
                    boolean bl2 = false;
                    if (charSequence == null) {
                        return false;
                    }
                    if (this == charSequence) break block4;
                    int n = charSequence.length();
                    int n2 = this.length;
                    bl = bl2;
                    if (n != n2) break block5;
                    bl = bl2;
                    if (!this.regionMatches(0, charSequence, n2)) break block5;
                }
                bl = true;
            }
            return bl;
        }

        public boolean endsWith(CharSequence charSequence) {
            int n;
            int n2 = charSequence.length();
            boolean bl = n2 <= (n = this.length) && this.regionMatches(n - n2, charSequence, n2);
            return bl;
        }

        public boolean equals(Object object) {
            boolean bl = false;
            if (object == null) {
                return false;
            }
            if (this == object) {
                return true;
            }
            if (object instanceof Key) {
                object = (Key)object;
                int n = this.length;
                if (n == ((Key)object).length && this.regionMatches(((Key)object).bytes, ((Key)object).offset, n)) {
                    bl = true;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            if (this.length == 0) {
                return 0;
            }
            int n = this.bytes[this.offset];
            for (int i = 1; i < this.length; ++i) {
                n = n * 37 + this.bytes[this.offset];
            }
            return n;
        }

        @Override
        public int length() {
            return this.length;
        }

        public boolean regionMatches(int n, CharSequence charSequence) {
            int n2 = charSequence.length();
            boolean bl = n2 == this.length - n && this.regionMatches(n, charSequence, n2);
            return bl;
        }

        public Key setBytes(byte[] arrby, int n) {
            this.bytes = arrby;
            this.offset = n;
            int n2 = 0;
            while (arrby[n + (n2 = (this.length = ++n2))] != 0) {
            }
            this.s = null;
            return this;
        }

        public Key setString(String string) {
            if (string.isEmpty()) {
                this.setToEmpty();
            } else {
                this.bytes = new byte[string.length()];
                this.offset = 0;
                this.length = string.length();
                for (int i = 0; i < this.length; ++i) {
                    char c = string.charAt(i);
                    if (c <= '') {
                        this.bytes[i] = (byte)c;
                        continue;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append('\"');
                    stringBuilder.append(string);
                    stringBuilder.append("\" is not an ASCII string");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                this.s = string;
            }
            return this;
        }

        public Key setToEmpty() {
            this.bytes = null;
            this.length = 0;
            this.offset = 0;
            this.s = "";
            return this;
        }

        public boolean startsWith(CharSequence charSequence) {
            boolean bl;
            int n = charSequence.length();
            int n2 = this.length;
            boolean bl2 = bl = false;
            if (n <= n2) {
                bl2 = bl;
                if (this.regionMatches(0, charSequence, n)) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        @Override
        public Key subSequence(int n, int n2) {
            return new Key(this.bytes, this.offset + n, n2 - n);
        }

        public String substring(int n) {
            return this.internalSubString(n, this.length);
        }

        public String substring(int n, int n2) {
            return this.internalSubString(n, n2);
        }

        @Override
        public String toString() {
            if (this.s == null) {
                this.s = this.internalSubString(0, this.length);
            }
            return this.s;
        }
    }

    public static abstract class Sink {
        public abstract void put(Key var1, Value var2, boolean var3);
    }

    public static interface Table {
        public boolean getKeyAndValue(int var1, Key var2, Value var3);

        public int getSize();
    }

    public static abstract class Value {
        protected Value() {
        }

        public abstract String getAliasString();

        public abstract Array getArray();

        public abstract ByteBuffer getBinary();

        public abstract int getInt();

        public abstract int[] getIntVector();

        public abstract String getString();

        public abstract String[] getStringArray();

        public abstract String[] getStringArrayOrStringAsArray();

        public abstract String getStringOrFirstOfArray();

        public abstract Table getTable();

        public abstract int getType();

        public abstract int getUInt();

        public abstract boolean isNoInheritanceMarker();

        public String toString() {
            int n = this.getType();
            if (n != 0) {
                if (n != 1) {
                    if (n != 2) {
                        if (n != 7) {
                            if (n != 8) {
                                if (n != 14) {
                                    return "???";
                                }
                                int[] arrn = this.getIntVector();
                                StringBuilder stringBuilder = new StringBuilder("[");
                                stringBuilder.append(arrn.length);
                                stringBuilder.append("]{");
                                if (arrn.length != 0) {
                                    stringBuilder.append(arrn[0]);
                                    for (n = 1; n < arrn.length; ++n) {
                                        stringBuilder.append(", ");
                                        stringBuilder.append(arrn[n]);
                                    }
                                }
                                stringBuilder.append('}');
                                return stringBuilder.toString();
                            }
                            return "(array)";
                        }
                        return Integer.toString(this.getInt());
                    }
                    return "(table)";
                }
                return "(binary blob)";
            }
            return this.getString();
        }
    }

}

