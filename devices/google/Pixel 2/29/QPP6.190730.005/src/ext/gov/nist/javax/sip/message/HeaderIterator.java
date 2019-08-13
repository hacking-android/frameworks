/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.message;

import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.message.SIPMessage;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class HeaderIterator
implements ListIterator {
    private int index;
    private SIPHeader sipHeader;
    private SIPMessage sipMessage;
    private boolean toRemove;

    protected HeaderIterator(SIPMessage sIPMessage, SIPHeader sIPHeader) {
        this.sipMessage = sIPMessage;
        this.sipHeader = sIPHeader;
    }

    public void add(Object object) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasNext() {
        boolean bl = this.index == 0;
        return bl;
    }

    @Override
    public boolean hasPrevious() {
        int n = this.index;
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    @Override
    public Object next() throws NoSuchElementException {
        SIPHeader sIPHeader = this.sipHeader;
        if (sIPHeader != null && this.index != 1) {
            this.toRemove = true;
            this.index = 1;
            return sIPHeader;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int nextIndex() {
        return 1;
    }

    public Object previous() throws NoSuchElementException {
        SIPHeader sIPHeader = this.sipHeader;
        if (sIPHeader != null && this.index != 0) {
            this.toRemove = true;
            this.index = 0;
            return sIPHeader;
        }
        throw new NoSuchElementException();
    }

    @Override
    public int previousIndex() {
        int n = this.index == 0 ? -1 : 0;
        return n;
    }

    @Override
    public void remove() throws IllegalStateException {
        if (this.sipHeader != null) {
            if (this.toRemove) {
                this.sipHeader = null;
                this.sipMessage.removeHeader(this.sipHeader.getName());
                return;
            }
            throw new IllegalStateException();
        }
        throw new IllegalStateException();
    }

    public void set(Object object) {
        throw new UnsupportedOperationException();
    }
}

