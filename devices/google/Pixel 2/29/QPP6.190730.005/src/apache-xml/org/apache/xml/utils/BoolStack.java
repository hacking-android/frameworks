/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils;

public final class BoolStack
implements Cloneable {
    private int m_allocatedSize;
    private int m_index;
    private boolean[] m_values;

    public BoolStack() {
        this(32);
    }

    public BoolStack(int n) {
        this.m_allocatedSize = n;
        this.m_values = new boolean[n];
        this.m_index = -1;
    }

    private void grow() {
        this.m_allocatedSize *= 2;
        boolean[] arrbl = new boolean[this.m_allocatedSize];
        System.arraycopy(this.m_values, 0, arrbl, 0, this.m_index + 1);
        this.m_values = arrbl;
    }

    public final void clear() {
        this.m_index = -1;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public boolean isEmpty() {
        boolean bl = this.m_index == -1;
        return bl;
    }

    public final boolean peek() {
        return this.m_values[this.m_index];
    }

    public final boolean peekOrFalse() {
        int n = this.m_index;
        boolean bl = n > -1 ? this.m_values[n] : false;
        return bl;
    }

    public final boolean peekOrTrue() {
        int n = this.m_index;
        boolean bl = n > -1 ? this.m_values[n] : true;
        return bl;
    }

    public final boolean pop() {
        boolean[] arrbl = this.m_values;
        int n = this.m_index;
        this.m_index = n - 1;
        return arrbl[n];
    }

    public final boolean popAndTop() {
        --this.m_index;
        int n = this.m_index;
        boolean bl = n >= 0 ? this.m_values[n] : false;
        return bl;
    }

    public final boolean push(boolean bl) {
        int n;
        if (this.m_index == this.m_allocatedSize - 1) {
            this.grow();
        }
        boolean[] arrbl = this.m_values;
        this.m_index = n = this.m_index + 1;
        arrbl[n] = bl;
        return bl;
    }

    public final void setTop(boolean bl) {
        this.m_values[this.m_index] = bl;
    }

    public final int size() {
        return this.m_index + 1;
    }
}

