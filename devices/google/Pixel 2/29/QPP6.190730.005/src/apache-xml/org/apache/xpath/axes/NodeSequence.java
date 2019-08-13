/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.axes;

import java.util.Vector;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.NodeVector;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.PathComponent;
import org.apache.xpath.objects.XObject;

public class NodeSequence
extends XObject
implements DTMIterator,
Cloneable,
PathComponent {
    static final long serialVersionUID = 3866261934726581044L;
    private IteratorCache m_cache;
    protected DTMManager m_dtmMgr;
    protected DTMIterator m_iter;
    protected int m_last = -1;
    protected int m_next = 0;

    public NodeSequence() {
    }

    public NodeSequence(Object object) {
        super(object);
        if (object instanceof NodeVector) {
            this.SetVector((NodeVector)object);
        }
        if (object != null) {
            this.assertion(object instanceof NodeVector, "Must have a NodeVector as the object for NodeSequence!");
            if (object instanceof DTMIterator) {
                this.setIter((DTMIterator)object);
                this.m_last = ((DTMIterator)object).getLength();
            }
        }
    }

    private NodeSequence(DTMIterator dTMIterator, int n, XPathContext xPathContext, boolean bl) {
        this.setIter(dTMIterator);
        this.setRoot(n, xPathContext);
        this.setShouldCacheNodes(bl);
    }

    private NodeSequence(DTMManager dTMManager) {
        super(new NodeVector());
        this.m_last = 0;
        this.m_dtmMgr = dTMManager;
    }

    private boolean cacheComplete() {
        IteratorCache iteratorCache = this.m_cache;
        boolean bl = iteratorCache != null ? iteratorCache.isComplete() : false;
        return bl;
    }

    private IteratorCache getCache() {
        return this.m_cache;
    }

    private void markCacheComplete() {
        if (this.getVector() != null) {
            this.m_cache.setCacheComplete(true);
        }
    }

    protected void SetVector(NodeVector nodeVector) {
        this.setObject(nodeVector);
    }

    protected int addNodeInDocOrder(int n) {
        int n2;
        this.assertion(this.hasCache(), "addNodeInDocOrder must be done on a mutable sequence!");
        int n3 = -1;
        NodeVector nodeVector = this.getVector();
        int n4 = nodeVector.size() - 1;
        do {
            n2 = n4;
            if (n4 < 0) break;
            n2 = nodeVector.elementAt(n4);
            if (n2 == n) {
                n2 = -2;
                break;
            }
            if (!this.m_dtmMgr.getDTM(n).isNodeAfter(n, n2)) {
                n2 = n4;
                break;
            }
            --n4;
        } while (true);
        n4 = n3;
        if (n2 != -2) {
            n4 = n2 + 1;
            nodeVector.insertElementAt(n, n4);
        }
        return n4;
    }

    @Override
    public void allowDetachToRelease(boolean bl) {
        DTMIterator dTMIterator;
        if (!bl && !this.hasCache()) {
            this.setShouldCacheNodes(true);
        }
        if ((dTMIterator = this.m_iter) != null) {
            dTMIterator.allowDetachToRelease(bl);
        }
        super.allowDetachToRelease(bl);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        NodeSequence nodeSequence = (NodeSequence)Object.super.clone();
        Object object = this.m_iter;
        if (object != null) {
            nodeSequence.m_iter = (DTMIterator)object.clone();
        }
        if ((object = this.m_cache) != null) {
            ((IteratorCache)object).increaseUseCount();
        }
        return nodeSequence;
    }

    @Override
    public DTMIterator cloneWithReset() throws CloneNotSupportedException {
        NodeSequence nodeSequence = (NodeSequence)Object.super.clone();
        nodeSequence.m_next = 0;
        IteratorCache iteratorCache = this.m_cache;
        if (iteratorCache != null) {
            iteratorCache.increaseUseCount();
        }
        return nodeSequence;
    }

    @Override
    public void detach() {
        DTMIterator dTMIterator = this.m_iter;
        if (dTMIterator != null) {
            dTMIterator.detach();
        }
        super.detach();
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
        super.fixupVariables(vector, n);
    }

    @Override
    public int getAnalysisBits() {
        DTMIterator dTMIterator = this.m_iter;
        if (dTMIterator != null && dTMIterator instanceof PathComponent) {
            return ((PathComponent)((Object)dTMIterator)).getAnalysisBits();
        }
        return 0;
    }

    @Override
    public int getAxis() {
        DTMIterator dTMIterator = this.m_iter;
        if (dTMIterator != null) {
            return dTMIterator.getAxis();
        }
        this.assertion(false, "Can not getAxis from a non-iterated node sequence!");
        return 0;
    }

    public final DTMIterator getContainedIter() {
        return this.m_iter;
    }

    @Override
    public int getCurrentNode() {
        if (this.hasCache()) {
            int n = this.m_next - 1;
            NodeVector nodeVector = this.getVector();
            if (n >= 0 && n < nodeVector.size()) {
                return nodeVector.elementAt(n);
            }
            return -1;
        }
        DTMIterator dTMIterator = this.m_iter;
        if (dTMIterator != null) {
            return dTMIterator.getCurrentNode();
        }
        return -1;
    }

    @Override
    public int getCurrentPos() {
        return this.m_next;
    }

    @Override
    public DTM getDTM(int n) {
        if (this.getDTMManager() != null) {
            return this.getDTMManager().getDTM(n);
        }
        this.assertion(false, "Can not get a DTM Unless a DTMManager has been set!");
        return null;
    }

    @Override
    public DTMManager getDTMManager() {
        return this.m_dtmMgr;
    }

    @Override
    public boolean getExpandEntityReferences() {
        DTMIterator dTMIterator = this.m_iter;
        if (dTMIterator != null) {
            return dTMIterator.getExpandEntityReferences();
        }
        return true;
    }

    protected IteratorCache getIteratorCache() {
        return this.m_cache;
    }

    @Override
    public int getLength() {
        int n;
        Object object = this.getCache();
        if (object != null) {
            if (((IteratorCache)object).isComplete()) {
                return ((IteratorCache)object).getVector().size();
            }
            object = this.m_iter;
            if (object instanceof NodeSetDTM) {
                return object.getLength();
            }
            if (-1 == this.m_last) {
                int n2 = this.m_next;
                this.runTo(-1);
                this.m_next = n2;
            }
            return this.m_last;
        }
        int n3 = n = this.m_last;
        if (-1 == n) {
            this.m_last = n3 = this.m_iter.getLength();
        }
        return n3;
    }

    @Override
    public int getRoot() {
        DTMIterator dTMIterator = this.m_iter;
        if (dTMIterator != null) {
            return dTMIterator.getRoot();
        }
        return -1;
    }

    protected NodeVector getVector() {
        Object object = this.m_cache;
        object = object != null ? ((IteratorCache)object).getVector() : null;
        return object;
    }

    @Override
    public int getWhatToShow() {
        int n = this.hasCache() ? -17 : this.m_iter.getWhatToShow();
        return n;
    }

    public boolean hasCache() {
        boolean bl = this.getVector() != null;
        return bl;
    }

    @Override
    public boolean isDocOrdered() {
        DTMIterator dTMIterator = this.m_iter;
        if (dTMIterator != null) {
            return dTMIterator.isDocOrdered();
        }
        return true;
    }

    @Override
    public boolean isFresh() {
        boolean bl = this.m_next == 0;
        return bl;
    }

    @Override
    public boolean isMutable() {
        return this.hasCache();
    }

    @Override
    public int item(int n) {
        this.setCurrentPos(n);
        int n2 = this.nextNode();
        this.m_next = n;
        return n2;
    }

    @Override
    public int nextNode() {
        Object object = this.getVector();
        if (object != null) {
            if (this.m_next < ((NodeVector)object).size()) {
                int n = ((NodeVector)object).elementAt(this.m_next);
                ++this.m_next;
                return n;
            }
            if (this.cacheComplete() || -1 != this.m_last || this.m_iter == null) {
                ++this.m_next;
                return -1;
            }
        }
        if ((object = this.m_iter) == null) {
            return -1;
        }
        int n = object.nextNode();
        if (-1 != n) {
            if (this.hasCache()) {
                if (this.m_iter.isDocOrdered()) {
                    this.getVector().addElement(n);
                    ++this.m_next;
                } else if (this.addNodeInDocOrder(n) >= 0) {
                    ++this.m_next;
                }
            } else {
                ++this.m_next;
            }
        } else {
            int n2;
            this.markCacheComplete();
            this.m_last = n2 = this.m_next;
            this.m_next = n2 + 1;
        }
        return n;
    }

    @Override
    public int previousNode() {
        if (this.hasCache()) {
            int n = this.m_next;
            if (n <= 0) {
                return -1;
            }
            this.m_next = n - 1;
            return this.item(this.m_next);
        }
        this.m_iter.previousNode();
        this.m_next = this.m_iter.getCurrentPos();
        return this.m_next;
    }

    @Override
    public void reset() {
        this.m_next = 0;
    }

    @Override
    public void runTo(int n) {
        if (-1 == n) {
            n = this.m_next;
            while (-1 != this.nextNode()) {
            }
            this.m_next = n;
        } else {
            if (this.m_next == n) {
                return;
            }
            if (this.hasCache() && this.m_next < this.getVector().size()) {
                this.m_next = n;
            } else if (this.getVector() == null && n < this.m_next) {
                while (this.m_next >= n && -1 != this.previousNode()) {
                }
            } else {
                while (this.m_next < n && -1 != this.nextNode()) {
                }
            }
        }
    }

    @Override
    public void setCurrentPos(int n) {
        this.runTo(n);
    }

    @Override
    public void setItem(int n, int n2) {
        NodeVector nodeVector = this.getVector();
        if (nodeVector != null) {
            Object object = nodeVector;
            if (nodeVector.elementAt(n2) != n) {
                object = nodeVector;
                if (this.m_cache.useCount() > 1) {
                    object = new IteratorCache();
                    try {
                        nodeVector = (NodeVector)nodeVector.clone();
                    }
                    catch (CloneNotSupportedException cloneNotSupportedException) {
                        cloneNotSupportedException.printStackTrace();
                        throw new RuntimeException(cloneNotSupportedException.getMessage());
                    }
                    ((IteratorCache)object).setVector(nodeVector);
                    ((IteratorCache)object).setCacheComplete(true);
                    this.m_cache = object;
                    object = nodeVector;
                    super.setObject(nodeVector);
                }
            }
            ((NodeVector)object).setElementAt(n, n2);
            this.m_last = ((NodeVector)object).size();
        } else {
            this.m_iter.setItem(n, n2);
        }
    }

    public final void setIter(DTMIterator dTMIterator) {
        this.m_iter = dTMIterator;
    }

    @Override
    protected void setObject(Object object) {
        if (object instanceof NodeVector) {
            super.setObject(object);
            NodeVector nodeVector = (NodeVector)object;
            object = this.m_cache;
            if (object != null) {
                ((IteratorCache)object).setVector(nodeVector);
            } else if (nodeVector != null) {
                this.m_cache = new IteratorCache();
                this.m_cache.setVector(nodeVector);
            }
        } else if (object instanceof IteratorCache) {
            this.m_cache = object = (IteratorCache)object;
            this.m_cache.increaseUseCount();
            super.setObject(((IteratorCache)object).getVector());
        } else {
            super.setObject(object);
        }
    }

    @Override
    public void setRoot(int n, Object object) {
        if (this.m_iter != null) {
            this.m_dtmMgr = ((XPathContext)object).getDTMManager();
            this.m_iter.setRoot(n, object);
            if (!this.m_iter.isDocOrdered()) {
                if (!this.hasCache()) {
                    this.setShouldCacheNodes(true);
                }
                this.runTo(-1);
                this.m_next = 0;
            }
        } else {
            this.assertion(false, "Can not setRoot on a non-iterated NodeSequence!");
        }
    }

    @Override
    public void setShouldCacheNodes(boolean bl) {
        if (bl) {
            if (!this.hasCache()) {
                this.SetVector(new NodeVector());
            }
        } else {
            this.SetVector(null);
        }
    }

    private static final class IteratorCache {
        private boolean m_isComplete2 = false;
        private int m_useCount2 = 1;
        private NodeVector m_vec2 = null;

        IteratorCache() {
        }

        private NodeVector getVector() {
            return this.m_vec2;
        }

        private void increaseUseCount() {
            if (this.m_vec2 != null) {
                ++this.m_useCount2;
            }
        }

        private boolean isComplete() {
            return this.m_isComplete2;
        }

        private void setCacheComplete(boolean bl) {
            this.m_isComplete2 = bl;
        }

        private void setVector(NodeVector nodeVector) {
            this.m_vec2 = nodeVector;
            this.m_useCount2 = 1;
        }

        private int useCount() {
            return this.m_useCount2;
        }
    }

}

