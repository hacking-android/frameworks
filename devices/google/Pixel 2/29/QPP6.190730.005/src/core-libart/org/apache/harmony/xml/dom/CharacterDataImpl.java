/*
 * Decompiled with CFR 0.145.
 */
package org.apache.harmony.xml.dom;

import org.apache.harmony.xml.dom.DocumentImpl;
import org.apache.harmony.xml.dom.LeafNodeImpl;
import org.w3c.dom.CharacterData;
import org.w3c.dom.DOMException;

public abstract class CharacterDataImpl
extends LeafNodeImpl
implements CharacterData {
    protected StringBuffer buffer;

    CharacterDataImpl(DocumentImpl documentImpl, String string) {
        super(documentImpl);
        this.setData(string);
    }

    @Override
    public void appendData(String string) throws DOMException {
        this.buffer.append(string);
    }

    public void appendDataTo(StringBuilder stringBuilder) {
        stringBuilder.append(this.buffer);
    }

    @Override
    public void deleteData(int n, int n2) throws DOMException {
        this.buffer.delete(n, n + n2);
    }

    @Override
    public String getData() throws DOMException {
        return this.buffer.toString();
    }

    @Override
    public int getLength() {
        return this.buffer.length();
    }

    @Override
    public String getNodeValue() {
        return this.getData();
    }

    @Override
    public void insertData(int n, String string) throws DOMException {
        try {
            this.buffer.insert(n, string);
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new DOMException(1, null);
        }
    }

    @Override
    public void replaceData(int n, int n2, String string) throws DOMException {
        try {
            this.buffer.replace(n, n + n2, string);
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new DOMException(1, null);
        }
    }

    @Override
    public void setData(String string) throws DOMException {
        this.buffer = new StringBuffer(string);
    }

    @Override
    public String substringData(int n, int n2) throws DOMException {
        try {
            String string = this.buffer.substring(n, n + n2);
            return string;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new DOMException(1, null);
        }
    }
}

