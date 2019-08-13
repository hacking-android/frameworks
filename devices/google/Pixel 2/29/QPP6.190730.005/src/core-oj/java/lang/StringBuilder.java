/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class StringBuilder
extends AbstractStringBuilder
implements Serializable,
CharSequence {
    static final long serialVersionUID = 4383685877147921099L;

    public StringBuilder() {
        super(16);
    }

    public StringBuilder(int n) {
        super(n);
    }

    public StringBuilder(CharSequence charSequence) {
        this(charSequence.length() + 16);
        this.append(charSequence);
    }

    public StringBuilder(String string) {
        super(string.length() + 16);
        this.append(string);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.count = objectInputStream.readInt();
        this.value = (char[])objectInputStream.readObject();
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.count);
        objectOutputStream.writeObject(this.value);
    }

    @Override
    public StringBuilder append(char c) {
        super.append(c);
        return this;
    }

    @Override
    public StringBuilder append(double d) {
        super.append(d);
        return this;
    }

    @Override
    public StringBuilder append(float f) {
        super.append(f);
        return this;
    }

    @Override
    public StringBuilder append(int n) {
        super.append(n);
        return this;
    }

    @Override
    public StringBuilder append(long l) {
        super.append(l);
        return this;
    }

    @Override
    public StringBuilder append(CharSequence charSequence) {
        super.append(charSequence);
        return this;
    }

    @Override
    public StringBuilder append(CharSequence charSequence, int n, int n2) {
        super.append(charSequence, n, n2);
        return this;
    }

    @Override
    public StringBuilder append(Object object) {
        this.append(String.valueOf(object));
        return this;
    }

    @Override
    public StringBuilder append(String string) {
        super.append(string);
        return this;
    }

    @Override
    public StringBuilder append(StringBuffer stringBuffer) {
        super.append(stringBuffer);
        return this;
    }

    @Override
    public StringBuilder append(boolean bl) {
        super.append(bl);
        return this;
    }

    @Override
    public StringBuilder append(char[] arrc) {
        super.append(arrc);
        return this;
    }

    @Override
    public StringBuilder append(char[] arrc, int n, int n2) {
        super.append(arrc, n, n2);
        return this;
    }

    @Override
    public StringBuilder appendCodePoint(int n) {
        super.appendCodePoint(n);
        return this;
    }

    @Override
    public StringBuilder delete(int n, int n2) {
        super.delete(n, n2);
        return this;
    }

    @Override
    public StringBuilder deleteCharAt(int n) {
        super.deleteCharAt(n);
        return this;
    }

    @Override
    public int indexOf(String string) {
        return super.indexOf(string);
    }

    @Override
    public int indexOf(String string, int n) {
        return super.indexOf(string, n);
    }

    @Override
    public StringBuilder insert(int n, char c) {
        super.insert(n, c);
        return this;
    }

    @Override
    public StringBuilder insert(int n, double d) {
        super.insert(n, d);
        return this;
    }

    @Override
    public StringBuilder insert(int n, float f) {
        super.insert(n, f);
        return this;
    }

    @Override
    public StringBuilder insert(int n, int n2) {
        super.insert(n, n2);
        return this;
    }

    @Override
    public StringBuilder insert(int n, long l) {
        super.insert(n, l);
        return this;
    }

    @Override
    public StringBuilder insert(int n, CharSequence charSequence) {
        super.insert(n, charSequence);
        return this;
    }

    @Override
    public StringBuilder insert(int n, CharSequence charSequence, int n2, int n3) {
        super.insert(n, charSequence, n2, n3);
        return this;
    }

    @Override
    public StringBuilder insert(int n, Object object) {
        super.insert(n, object);
        return this;
    }

    @Override
    public StringBuilder insert(int n, String string) {
        super.insert(n, string);
        return this;
    }

    @Override
    public StringBuilder insert(int n, boolean bl) {
        super.insert(n, bl);
        return this;
    }

    @Override
    public StringBuilder insert(int n, char[] arrc) {
        super.insert(n, arrc);
        return this;
    }

    @Override
    public StringBuilder insert(int n, char[] arrc, int n2, int n3) {
        super.insert(n, arrc, n2, n3);
        return this;
    }

    @Override
    public int lastIndexOf(String string) {
        return super.lastIndexOf(string);
    }

    @Override
    public int lastIndexOf(String string, int n) {
        return super.lastIndexOf(string, n);
    }

    @Override
    public StringBuilder replace(int n, int n2, String string) {
        super.replace(n, n2, string);
        return this;
    }

    @Override
    public StringBuilder reverse() {
        super.reverse();
        return this;
    }

    @Override
    public String toString() {
        if (this.count == 0) {
            return "";
        }
        return new String(this.value, 0, this.count);
    }
}

