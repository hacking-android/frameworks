/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.EmptyStackException;
import org.apache.xml.utils.ObjectVector;

public class ObjectStack
extends ObjectVector {
    public ObjectStack() {
    }

    public ObjectStack(int n) {
        super(n);
    }

    public ObjectStack(ObjectStack objectStack) {
        super(objectStack);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (ObjectStack)super.clone();
    }

    public boolean empty() {
        boolean bl = this.m_firstFree == 0;
        return bl;
    }

    public Object peek() {
        try {
            Object object = this.m_map[this.m_firstFree - 1];
            return object;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new EmptyStackException();
        }
    }

    public Object peek(int n) {
        try {
            Object object = this.m_map[this.m_firstFree - (n + 1)];
            return object;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new EmptyStackException();
        }
    }

    public Object pop() {
        int n;
        Object object = this.m_map;
        this.m_firstFree = n = this.m_firstFree - 1;
        object = object[n];
        this.m_map[this.m_firstFree] = null;
        return object;
    }

    public Object push(Object object) {
        if (this.m_firstFree + 1 >= this.m_mapSize) {
            this.m_mapSize += this.m_blocksize;
            Object[] arrobject = new Object[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrobject, 0, this.m_firstFree + 1);
            this.m_map = arrobject;
        }
        this.m_map[this.m_firstFree] = object;
        ++this.m_firstFree;
        return object;
    }

    public void quickPop(int n) {
        this.m_firstFree -= n;
    }

    public int search(Object object) {
        int n = this.lastIndexOf(object);
        if (n >= 0) {
            return this.size() - n;
        }
        return -1;
    }

    public void setTop(Object object) {
        try {
            this.m_map[this.m_firstFree - 1] = object;
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new EmptyStackException();
        }
    }
}

