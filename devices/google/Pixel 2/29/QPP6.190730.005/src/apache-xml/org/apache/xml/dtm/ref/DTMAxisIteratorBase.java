/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.utils.WrappedRuntimeException;

public abstract class DTMAxisIteratorBase
implements DTMAxisIterator {
    protected boolean _includeSelf = false;
    protected boolean _isRestartable = true;
    protected int _last = -1;
    protected int _markedNode;
    protected int _position = 0;
    protected int _startNode = -1;

    @Override
    public DTMAxisIterator cloneIterator() {
        try {
            DTMAxisIteratorBase dTMAxisIteratorBase = (DTMAxisIteratorBase)super.clone();
            dTMAxisIteratorBase._isRestartable = false;
            return dTMAxisIteratorBase;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new WrappedRuntimeException(cloneNotSupportedException);
        }
    }

    public int getAxis() {
        return -1;
    }

    @Override
    public int getLast() {
        if (this._last == -1) {
            int n = this._position;
            this.setMark();
            this.reset();
            do {
                ++this._last;
            } while (this.next() != -1);
            this.gotoMark();
            this._position = n;
        }
        return this._last;
    }

    @Override
    public int getNodeByPosition(int n) {
        if (n > 0) {
            int n2;
            if (this.isReverse()) {
                n = this.getLast() - n + 1;
            }
            while ((n2 = this.next()) != -1) {
                if (n != this.getPosition()) continue;
                return n2;
            }
        }
        return -1;
    }

    @Override
    public int getPosition() {
        int n;
        int n2 = n = this._position;
        if (n == 0) {
            n2 = 1;
        }
        return n2;
    }

    @Override
    public int getStartNode() {
        return this._startNode;
    }

    public DTMAxisIterator includeSelf() {
        this._includeSelf = true;
        return this;
    }

    public boolean isDocOrdered() {
        return true;
    }

    @Override
    public boolean isReverse() {
        return false;
    }

    @Override
    public DTMAxisIterator reset() {
        boolean bl = this._isRestartable;
        this._isRestartable = true;
        this.setStartNode(this._startNode);
        this._isRestartable = bl;
        return this;
    }

    protected final DTMAxisIterator resetPosition() {
        this._position = 0;
        return this;
    }

    protected final int returnNode(int n) {
        ++this._position;
        return n;
    }

    @Override
    public void setRestartable(boolean bl) {
        this._isRestartable = bl;
    }
}

