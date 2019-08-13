/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import org.apache.xalan.res.XSLMessages;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMFilter;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.NodeVector;
import org.apache.xpath.XPathContext;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;

public class NodeSetDTM
extends NodeVector
implements DTMIterator,
Cloneable {
    static final long serialVersionUID = 7686480133331317070L;
    protected transient boolean m_cacheNodes = true;
    private transient int m_last = 0;
    DTMManager m_manager;
    protected transient boolean m_mutable = true;
    protected transient int m_next = 0;
    protected int m_root = -1;

    public NodeSetDTM(int n, int n2, DTMManager dTMManager) {
        super(n);
        this.m_manager = dTMManager;
    }

    public NodeSetDTM(int n, DTMManager dTMManager) {
        this.m_manager = dTMManager;
        this.addNode(n);
    }

    public NodeSetDTM(DTMIterator dTMIterator) {
        this.m_manager = dTMIterator.getDTMManager();
        this.m_root = dTMIterator.getRoot();
        this.addNodes(dTMIterator);
    }

    public NodeSetDTM(DTMManager dTMManager) {
        this.m_manager = dTMManager;
    }

    public NodeSetDTM(NodeSetDTM nodeSetDTM) {
        this.m_manager = nodeSetDTM.getDTMManager();
        this.m_root = nodeSetDTM.getRoot();
        this.addNodes(nodeSetDTM);
    }

    public NodeSetDTM(NodeList nodeList, XPathContext xPathContext) {
        this.m_manager = xPathContext.getDTMManager();
        int n = nodeList.getLength();
        for (int i = 0; i < n; ++i) {
            this.addNode(xPathContext.getDTMHandleFromNode(nodeList.item(i)));
        }
    }

    public NodeSetDTM(NodeIterator nodeIterator, XPathContext xPathContext) {
        Node node;
        this.m_manager = xPathContext.getDTMManager();
        while ((node = nodeIterator.nextNode()) != null) {
            this.addNodeInDocOrder(xPathContext.getDTMHandleFromNode(node), xPathContext);
        }
    }

    @Override
    public void addElement(int n) {
        if (this.m_mutable) {
            super.addElement(n);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public void addNode(int n) {
        if (this.m_mutable) {
            this.addElement(n);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public int addNodeInDocOrder(int n, XPathContext xPathContext) {
        if (this.m_mutable) {
            return this.addNodeInDocOrder(n, true, xPathContext);
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public int addNodeInDocOrder(int n, boolean bl, XPathContext xPathContext) {
        if (this.m_mutable) {
            int n2;
            int n3 = -1;
            if (bl) {
                int n4 = this.size() - 1;
                do {
                    n2 = n4;
                    if (n4 < 0) break;
                    n2 = this.elementAt(n4);
                    if (n2 == n) {
                        n2 = -2;
                        break;
                    }
                    if (!xPathContext.getDTM(n).isNodeAfter(n, n2)) {
                        n2 = n4;
                        break;
                    }
                    --n4;
                } while (true);
                n4 = n3;
                if (n2 != -2) {
                    n4 = n2 + 1;
                    this.insertElementAt(n, n4);
                }
                n2 = n4;
            } else {
                boolean bl2;
                n3 = this.size();
                boolean bl3 = false;
                n2 = 0;
                do {
                    bl2 = bl3;
                    if (n2 >= n3) break;
                    if (n2 == n) {
                        bl2 = true;
                        break;
                    }
                    ++n2;
                } while (true);
                n2 = n3;
                if (!bl2) {
                    this.addElement(n);
                    n2 = n3;
                }
            }
            return n2;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public void addNodes(DTMIterator dTMIterator) {
        if (this.m_mutable) {
            if (dTMIterator != null) {
                int n;
                while (-1 != (n = dTMIterator.nextNode())) {
                    this.addElement(n);
                }
            }
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public void addNodesInDocOrder(DTMIterator dTMIterator, XPathContext xPathContext) {
        if (this.m_mutable) {
            int n;
            while (-1 != (n = dTMIterator.nextNode())) {
                this.addNodeInDocOrder(n, xPathContext);
            }
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    @Override
    public void allowDetachToRelease(boolean bl) {
    }

    @Override
    public void appendNodes(NodeVector nodeVector) {
        if (this.m_mutable) {
            super.appendNodes(nodeVector);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return (NodeSetDTM)super.clone();
    }

    @Override
    public DTMIterator cloneWithReset() throws CloneNotSupportedException {
        NodeSetDTM nodeSetDTM = (NodeSetDTM)this.clone();
        nodeSetDTM.reset();
        return nodeSetDTM;
    }

    @Override
    public boolean contains(int n) {
        this.runTo(-1);
        return super.contains(n);
    }

    @Override
    public void detach() {
    }

    @Override
    public int elementAt(int n) {
        this.runTo(n);
        return super.elementAt(n);
    }

    @Override
    public int getAxis() {
        return -1;
    }

    @Override
    public int getCurrentNode() {
        if (this.m_cacheNodes) {
            int n;
            int n2 = this.m_next;
            int n3 = n = this.m_next;
            if (n > 0) {
                n3 = n - 1;
            }
            n3 = n3 < this.m_firstFree ? this.elementAt(n3) : -1;
            this.m_next = n2;
            return n3;
        }
        throw new RuntimeException("This NodeSetDTM can not do indexing or counting functions!");
    }

    @Override
    public int getCurrentPos() {
        return this.m_next;
    }

    @Override
    public DTM getDTM(int n) {
        return this.m_manager.getDTM(n);
    }

    @Override
    public DTMManager getDTMManager() {
        return this.m_manager;
    }

    @Override
    public boolean getExpandEntityReferences() {
        return true;
    }

    public DTMFilter getFilter() {
        return null;
    }

    public int getLast() {
        return this.m_last;
    }

    @Override
    public int getLength() {
        this.runTo(-1);
        return this.size();
    }

    @Override
    public int getRoot() {
        int n = this.m_root;
        if (-1 == n) {
            if (this.size() > 0) {
                return this.item(0);
            }
            return -1;
        }
        return n;
    }

    public boolean getShouldCacheNodes() {
        return this.m_cacheNodes;
    }

    @Override
    public int getWhatToShow() {
        return -17;
    }

    @Override
    public int indexOf(int n) {
        this.runTo(-1);
        return super.indexOf(n);
    }

    @Override
    public int indexOf(int n, int n2) {
        this.runTo(-1);
        return super.indexOf(n, n2);
    }

    @Override
    public void insertElementAt(int n, int n2) {
        if (this.m_mutable) {
            super.insertElementAt(n, n2);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public void insertNode(int n, int n2) {
        if (this.m_mutable) {
            this.insertElementAt(n, n2);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    @Override
    public boolean isDocOrdered() {
        return true;
    }

    @Override
    public boolean isFresh() {
        boolean bl = this.m_next == 0;
        return bl;
    }

    @Override
    public boolean isMutable() {
        return this.m_mutable;
    }

    @Override
    public int item(int n) {
        this.runTo(n);
        return this.elementAt(n);
    }

    @Override
    public int nextNode() {
        if (this.m_next < this.size()) {
            int n = this.elementAt(this.m_next);
            ++this.m_next;
            return n;
        }
        return -1;
    }

    @Override
    public int previousNode() {
        if (this.m_cacheNodes) {
            int n = this.m_next;
            if (n - 1 > 0) {
                this.m_next = n - 1;
                return this.elementAt(this.m_next);
            }
            return -1;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_CANNOT_ITERATE", null));
    }

    @Override
    public void removeAllElements() {
        if (this.m_mutable) {
            super.removeAllElements();
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    @Override
    public boolean removeElement(int n) {
        if (this.m_mutable) {
            return super.removeElement(n);
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    @Override
    public void removeElementAt(int n) {
        if (this.m_mutable) {
            super.removeElementAt(n);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public void removeNode(int n) {
        if (this.m_mutable) {
            this.removeElement(n);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    @Override
    public void reset() {
        this.m_next = 0;
    }

    @Override
    public void runTo(int n) {
        if (this.m_cacheNodes) {
            this.m_next = n >= 0 && this.m_next < this.m_firstFree ? n : this.m_firstFree - 1;
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_CANNOT_INDEX", null));
    }

    @Override
    public void setCurrentPos(int n) {
        if (this.m_cacheNodes) {
            this.m_next = n;
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_CANNOT_INDEX", null));
    }

    @Override
    public void setElementAt(int n, int n2) {
        if (this.m_mutable) {
            super.setElementAt(n, n2);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public void setEnvironment(Object object) {
    }

    @Override
    public void setItem(int n, int n2) {
        if (this.m_mutable) {
            super.setElementAt(n, n2);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESETDTM_NOT_MUTABLE", null));
    }

    public void setLast(int n) {
        this.m_last = n;
    }

    @Override
    public void setRoot(int n, Object object) {
    }

    @Override
    public void setShouldCacheNodes(boolean bl) {
        if (this.isFresh()) {
            this.m_cacheNodes = bl;
            this.m_mutable = true;
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_CANNOT_CALL_SETSHOULDCACHENODE", null));
    }

    @Override
    public int size() {
        return super.size();
    }
}

