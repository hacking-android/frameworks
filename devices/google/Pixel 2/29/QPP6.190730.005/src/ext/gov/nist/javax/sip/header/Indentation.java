/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import java.util.Arrays;

class Indentation {
    private int indentation;

    protected Indentation() {
        this.indentation = 0;
    }

    protected Indentation(int n) {
        this.indentation = n;
    }

    protected void decrement() {
        --this.indentation;
    }

    protected int getCount() {
        return this.indentation;
    }

    protected String getIndentation() {
        char[] arrc = new char[this.indentation];
        Arrays.fill(arrc, ' ');
        return new String(arrc);
    }

    protected void increment() {
        ++this.indentation;
    }

    protected void setIndentation(int n) {
        this.indentation = n;
    }
}

