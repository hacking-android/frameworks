/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMDOMException;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.WrappedRuntimeException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

public class DTMNodeIterator
implements NodeIterator {
    private DTMIterator dtm_iter;
    private boolean valid = true;

    public DTMNodeIterator(DTMIterator dTMIterator) {
        try {
            this.dtm_iter = (DTMIterator)dTMIterator.clone();
            return;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new WrappedRuntimeException(cloneNotSupportedException);
        }
    }

    @Override
    public void detach() {
        this.valid = false;
    }

    public DTMIterator getDTMIterator() {
        return this.dtm_iter;
    }

    @Override
    public boolean getExpandEntityReferences() {
        return false;
    }

    @Override
    public NodeFilter getFilter() {
        throw new DTMDOMException(9);
    }

    @Override
    public Node getRoot() {
        int n = this.dtm_iter.getRoot();
        return this.dtm_iter.getDTM(n).getNode(n);
    }

    @Override
    public int getWhatToShow() {
        return this.dtm_iter.getWhatToShow();
    }

    @Override
    public Node nextNode() throws DOMException {
        if (this.valid) {
            int n = this.dtm_iter.nextNode();
            if (n == -1) {
                return null;
            }
            return this.dtm_iter.getDTM(n).getNode(n);
        }
        throw new DTMDOMException(11);
    }

    @Override
    public Node previousNode() {
        if (this.valid) {
            int n = this.dtm_iter.previousNode();
            if (n == -1) {
                return null;
            }
            return this.dtm_iter.getDTM(n).getNode(n);
        }
        throw new DTMDOMException(11);
    }
}

