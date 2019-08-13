/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.util.Arrays;

public final class StringBuffer
extends AbstractStringBuilder
implements Serializable,
CharSequence {
    private static final ObjectStreamField[] serialPersistentFields = new ObjectStreamField[]{new ObjectStreamField("value", char[].class), new ObjectStreamField("count", Integer.TYPE), new ObjectStreamField("shared", Boolean.TYPE)};
    static final long serialVersionUID = 3388685877147921107L;
    private transient char[] toStringCache;

    public StringBuffer() {
        super(16);
    }

    public StringBuffer(int n) {
        super(n);
    }

    public StringBuffer(CharSequence charSequence) {
        this(charSequence.length() + 16);
        this.append(charSequence);
    }

    public StringBuffer(String string) {
        super(string.length() + 16);
        this.append(string);
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        object = ((ObjectInputStream)object).readFields();
        this.value = (char[])((ObjectInputStream.GetField)object).get("value", null);
        this.count = ((ObjectInputStream.GetField)object).get("count", 0);
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        synchronized (this) {
            ObjectOutputStream.PutField putField = objectOutputStream.putFields();
            putField.put("value", this.value);
            putField.put("count", this.count);
            putField.put("shared", false);
            objectOutputStream.writeFields();
            return;
        }
    }

    @Override
    public StringBuffer append(char c) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(c);
            return this;
        }
    }

    @Override
    public StringBuffer append(double d) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(d);
            return this;
        }
    }

    @Override
    public StringBuffer append(float f) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(f);
            return this;
        }
    }

    @Override
    public StringBuffer append(int n) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(n);
            return this;
        }
    }

    @Override
    public StringBuffer append(long l) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(l);
            return this;
        }
    }

    @Override
    StringBuffer append(AbstractStringBuilder abstractStringBuilder) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(abstractStringBuilder);
            return this;
        }
    }

    @Override
    public StringBuffer append(CharSequence charSequence) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(charSequence);
            return this;
        }
    }

    @Override
    public StringBuffer append(CharSequence charSequence, int n, int n2) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(charSequence, n, n2);
            return this;
        }
    }

    @Override
    public StringBuffer append(Object object) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(String.valueOf(object));
            return this;
        }
    }

    @Override
    public StringBuffer append(String string) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(string);
            return this;
        }
    }

    @Override
    public StringBuffer append(StringBuffer stringBuffer) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(stringBuffer);
            return this;
        }
    }

    @Override
    public StringBuffer append(boolean bl) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(bl);
            return this;
        }
    }

    @Override
    public StringBuffer append(char[] arrc) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(arrc);
            return this;
        }
    }

    @Override
    public StringBuffer append(char[] arrc, int n, int n2) {
        synchronized (this) {
            this.toStringCache = null;
            super.append(arrc, n, n2);
            return this;
        }
    }

    @Override
    public StringBuffer appendCodePoint(int n) {
        synchronized (this) {
            this.toStringCache = null;
            super.appendCodePoint(n);
            return this;
        }
    }

    @Override
    public int capacity() {
        synchronized (this) {
            int n = this.value.length;
            return n;
        }
    }

    @Override
    public char charAt(int n) {
        synchronized (this) {
            block5 : {
                if (n >= 0) {
                    if (n >= this.count) break block5;
                    char c = this.value[n];
                    return c;
                }
            }
            StringIndexOutOfBoundsException stringIndexOutOfBoundsException = new StringIndexOutOfBoundsException(n);
            throw stringIndexOutOfBoundsException;
        }
    }

    @Override
    public int codePointAt(int n) {
        synchronized (this) {
            n = super.codePointAt(n);
            return n;
        }
    }

    @Override
    public int codePointBefore(int n) {
        synchronized (this) {
            n = super.codePointBefore(n);
            return n;
        }
    }

    @Override
    public int codePointCount(int n, int n2) {
        synchronized (this) {
            n = super.codePointCount(n, n2);
            return n;
        }
    }

    @Override
    public StringBuffer delete(int n, int n2) {
        synchronized (this) {
            this.toStringCache = null;
            super.delete(n, n2);
            return this;
        }
    }

    @Override
    public StringBuffer deleteCharAt(int n) {
        synchronized (this) {
            this.toStringCache = null;
            super.deleteCharAt(n);
            return this;
        }
    }

    @Override
    public void ensureCapacity(int n) {
        synchronized (this) {
            super.ensureCapacity(n);
            return;
        }
    }

    @Override
    public void getChars(int n, int n2, char[] arrc, int n3) {
        synchronized (this) {
            super.getChars(n, n2, arrc, n3);
            return;
        }
    }

    @Override
    public int indexOf(String string) {
        return super.indexOf(string);
    }

    @Override
    public int indexOf(String string, int n) {
        synchronized (this) {
            n = super.indexOf(string, n);
            return n;
        }
    }

    @Override
    public StringBuffer insert(int n, char c) {
        synchronized (this) {
            this.toStringCache = null;
            super.insert(n, c);
            return this;
        }
    }

    @Override
    public StringBuffer insert(int n, double d) {
        super.insert(n, d);
        return this;
    }

    @Override
    public StringBuffer insert(int n, float f) {
        super.insert(n, f);
        return this;
    }

    @Override
    public StringBuffer insert(int n, int n2) {
        super.insert(n, n2);
        return this;
    }

    @Override
    public StringBuffer insert(int n, long l) {
        super.insert(n, l);
        return this;
    }

    @Override
    public StringBuffer insert(int n, CharSequence charSequence) {
        super.insert(n, charSequence);
        return this;
    }

    @Override
    public StringBuffer insert(int n, CharSequence charSequence, int n2, int n3) {
        synchronized (this) {
            this.toStringCache = null;
            super.insert(n, charSequence, n2, n3);
            return this;
        }
    }

    @Override
    public StringBuffer insert(int n, Object object) {
        synchronized (this) {
            this.toStringCache = null;
            super.insert(n, String.valueOf(object));
            return this;
        }
    }

    @Override
    public StringBuffer insert(int n, String string) {
        synchronized (this) {
            this.toStringCache = null;
            super.insert(n, string);
            return this;
        }
    }

    @Override
    public StringBuffer insert(int n, boolean bl) {
        super.insert(n, bl);
        return this;
    }

    @Override
    public StringBuffer insert(int n, char[] arrc) {
        synchronized (this) {
            this.toStringCache = null;
            super.insert(n, arrc);
            return this;
        }
    }

    @Override
    public StringBuffer insert(int n, char[] arrc, int n2, int n3) {
        synchronized (this) {
            this.toStringCache = null;
            super.insert(n, arrc, n2, n3);
            return this;
        }
    }

    @Override
    public int lastIndexOf(String string) {
        return this.lastIndexOf(string, this.count);
    }

    @Override
    public int lastIndexOf(String string, int n) {
        synchronized (this) {
            n = super.lastIndexOf(string, n);
            return n;
        }
    }

    @Override
    public int length() {
        synchronized (this) {
            int n = this.count;
            return n;
        }
    }

    @Override
    public int offsetByCodePoints(int n, int n2) {
        synchronized (this) {
            n = super.offsetByCodePoints(n, n2);
            return n;
        }
    }

    @Override
    public StringBuffer replace(int n, int n2, String string) {
        synchronized (this) {
            this.toStringCache = null;
            super.replace(n, n2, string);
            return this;
        }
    }

    @Override
    public StringBuffer reverse() {
        synchronized (this) {
            this.toStringCache = null;
            super.reverse();
            return this;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void setCharAt(int n, char c) {
        synchronized (this) {
            if (n >= 0 && n < this.count) {
                this.toStringCache = null;
                this.value[n] = c;
                return;
            }
            StringIndexOutOfBoundsException stringIndexOutOfBoundsException = new StringIndexOutOfBoundsException(n);
            throw stringIndexOutOfBoundsException;
        }
    }

    @Override
    public void setLength(int n) {
        synchronized (this) {
            this.toStringCache = null;
            super.setLength(n);
            return;
        }
    }

    @Override
    public CharSequence subSequence(int n, int n2) {
        synchronized (this) {
            String string = super.substring(n, n2);
            return string;
        }
    }

    @Override
    public String substring(int n) {
        synchronized (this) {
            String string = this.substring(n, this.count);
            return string;
        }
    }

    @Override
    public String substring(int n, int n2) {
        synchronized (this) {
            String string = super.substring(n, n2);
            return string;
        }
    }

    @Override
    public String toString() {
        synchronized (this) {
            if (this.toStringCache == null) {
                this.toStringCache = Arrays.copyOfRange(this.value, 0, this.count);
            }
            String string = new String(this.toStringCache, 0, this.count);
            return string;
        }
    }

    @Override
    public void trimToSize() {
        synchronized (this) {
            super.trimToSize();
            return;
        }
    }
}

