/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

import java.util.EmptyStackException;
import org.apache.xml.utils.IntVector;

public class IntStack
extends IntVector {
    public IntStack() {
    }

    public IntStack(int n) {
        super(n);
    }

    public IntStack(IntStack intStack) {
        super(intStack);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (IntStack)super.clone();
    }

    public boolean empty() {
        boolean bl = this.m_firstFree == 0;
        return bl;
    }

    public final int peek() {
        try {
            int n = this.m_map[this.m_firstFree - 1];
            return n;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new EmptyStackException();
        }
    }

    public int peek(int n) {
        try {
            n = this.m_map[this.m_firstFree - (n + 1)];
            return n;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new EmptyStackException();
        }
    }

    public final int pop() {
        int n;
        int[] arrn = this.m_map;
        this.m_firstFree = n = this.m_firstFree - 1;
        return arrn[n];
    }

    public int push(int n) {
        if (this.m_firstFree + 1 >= this.m_mapSize) {
            this.m_mapSize += this.m_blocksize;
            int[] arrn = new int[this.m_mapSize];
            System.arraycopy(this.m_map, 0, arrn, 0, this.m_firstFree + 1);
            this.m_map = arrn;
        }
        this.m_map[this.m_firstFree] = n;
        ++this.m_firstFree;
        return n;
    }

    public final void quickPop(int n) {
        this.m_firstFree -= n;
    }

    public int search(int n) {
        if ((n = this.lastIndexOf(n)) >= 0) {
            return this.size() - n;
        }
        return -1;
    }

    public void setTop(int n) {
        try {
            this.m_map[this.m_firstFree - 1] = n;
            return;
        }
        catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            throw new EmptyStackException();
        }
    }
}

