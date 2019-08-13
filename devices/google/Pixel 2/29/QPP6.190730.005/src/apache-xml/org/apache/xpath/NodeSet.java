/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath;

import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.DOM2Helper;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.ContextNodeList;
import org.w3c.dom.DOMException;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.NodeIterator;

public class NodeSet
implements NodeList,
NodeIterator,
Cloneable,
ContextNodeList {
    private int m_blocksize;
    protected transient boolean m_cacheNodes = true;
    protected int m_firstFree = 0;
    private transient int m_last = 0;
    Node[] m_map;
    private int m_mapSize;
    protected transient boolean m_mutable = true;
    protected transient int m_next = 0;

    public NodeSet() {
        this.m_blocksize = 32;
        this.m_mapSize = 0;
    }

    public NodeSet(int n) {
        this.m_blocksize = n;
        this.m_mapSize = 0;
    }

    public NodeSet(NodeSet nodeSet) {
        this(32);
        this.addNodes((NodeIterator)nodeSet);
    }

    public NodeSet(Node node) {
        this(32);
        this.addNode(node);
    }

    public NodeSet(NodeList nodeList) {
        this(32);
        this.addNodes(nodeList);
    }

    public NodeSet(NodeIterator nodeIterator) {
        this(32);
        this.addNodes(nodeIterator);
    }

    private boolean addNodesInDocOrder(int n, int n2, int n3, NodeList nodeList, XPathContext xPathContext) {
        if (this.m_mutable) {
            int n4;
            Node node = nodeList.item(n3);
            do {
                n4 = --n2;
                if (n2 < n) break;
                Node node2 = this.elementAt(n2);
                if (node2 == node) {
                    n4 = -2;
                    break;
                }
                if (DOM2Helper.isNodeAfter(node, node2)) continue;
                this.insertElementAt(node, n2 + 1);
                n = n3 - 1;
                if (n > 0 && !this.addNodesInDocOrder(0, n2, n, nodeList, xPathContext)) {
                    this.addNodesInDocOrder(n2, this.size() - 1, n, nodeList, xPathContext);
                }
                n4 = n2;
                break;
            } while (true);
            if (n4 == -1) {
                this.insertElementAt(node, 0);
            }
            return false;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void addElement(Node node) {
        if (this.m_mutable) {
            Node[] arrnode;
            int n = this.m_firstFree;
            int n2 = this.m_mapSize;
            if (n + 1 >= n2) {
                Node[] arrnode2 = this.m_map;
                if (arrnode2 == null) {
                    n2 = this.m_blocksize;
                    this.m_map = new Node[n2];
                    this.m_mapSize = n2;
                } else {
                    this.m_mapSize = n2 + this.m_blocksize;
                    arrnode = new Node[this.m_mapSize];
                    System.arraycopy(arrnode2, 0, arrnode, 0, n + 1);
                    this.m_map = arrnode;
                }
            }
            arrnode = this.m_map;
            n2 = this.m_firstFree;
            arrnode[n2] = node;
            this.m_firstFree = n2 + 1;
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void addNode(Node node) {
        if (this.m_mutable) {
            this.addElement(node);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public int addNodeInDocOrder(Node node, XPathContext xPathContext) {
        if (this.m_mutable) {
            return this.addNodeInDocOrder(node, true, xPathContext);
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public int addNodeInDocOrder(Node node, boolean bl, XPathContext object) {
        if (this.m_mutable) {
            int n;
            int n2 = -1;
            if (bl) {
                int n3 = this.size() - 1;
                do {
                    n = --n3;
                    if (n3 < 0) break;
                    object = this.elementAt(n3);
                    if (object == node) {
                        n = -2;
                        break;
                    }
                    if (DOM2Helper.isNodeAfter(node, (Node)object)) continue;
                    n = n3;
                    break;
                } while (true);
                n3 = n2;
                if (n != -2) {
                    n3 = n + 1;
                    this.insertElementAt(node, n3);
                }
                n = n3;
            } else {
                boolean bl2;
                n2 = this.size();
                boolean bl3 = false;
                n = 0;
                do {
                    bl2 = bl3;
                    if (n >= n2) break;
                    if (this.item(n).equals(node)) {
                        bl2 = true;
                        break;
                    }
                    ++n;
                } while (true);
                n = n2;
                if (!bl2) {
                    this.addElement(node);
                    n = n2;
                }
            }
            return n;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void addNodes(NodeSet nodeSet) {
        if (this.m_mutable) {
            this.addNodes((NodeIterator)nodeSet);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void addNodes(NodeList nodeList) {
        if (this.m_mutable) {
            if (nodeList != null) {
                int n = nodeList.getLength();
                for (int i = 0; i < n; ++i) {
                    Node node = nodeList.item(i);
                    if (node == null) continue;
                    this.addElement(node);
                }
            }
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void addNodes(NodeIterator nodeIterator) {
        if (this.m_mutable) {
            if (nodeIterator != null) {
                Node node;
                while ((node = nodeIterator.nextNode()) != null) {
                    this.addElement(node);
                }
            }
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void addNodesInDocOrder(NodeList nodeList, XPathContext xPathContext) {
        if (this.m_mutable) {
            int n = nodeList.getLength();
            for (int i = 0; i < n; ++i) {
                Node node = nodeList.item(i);
                if (node == null) continue;
                this.addNodeInDocOrder(node, xPathContext);
            }
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void addNodesInDocOrder(NodeIterator nodeIterator, XPathContext xPathContext) {
        if (this.m_mutable) {
            Node node;
            while ((node = nodeIterator.nextNode()) != null) {
                this.addNodeInDocOrder(node, xPathContext);
            }
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void appendNodes(NodeSet nodeSet) {
        int n = nodeSet.size();
        Node[] arrnode = this.m_map;
        if (arrnode == null) {
            this.m_mapSize = this.m_blocksize + n;
            this.m_map = new Node[this.m_mapSize];
        } else {
            int n2 = this.m_firstFree;
            int n3 = this.m_mapSize;
            if (n2 + n >= n3) {
                this.m_mapSize = n3 + (this.m_blocksize + n);
                Node[] arrnode2 = new Node[this.m_mapSize];
                System.arraycopy(arrnode, 0, arrnode2, 0, n2 + n);
                this.m_map = arrnode2;
            }
        }
        System.arraycopy(nodeSet.m_map, 0, this.m_map, this.m_firstFree, n);
        this.m_firstFree += n;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        NodeSet nodeSet = (NodeSet)super.clone();
        Node[] arrnode = this.m_map;
        if (arrnode != null && arrnode == nodeSet.m_map) {
            nodeSet.m_map = new Node[arrnode.length];
            arrnode = this.m_map;
            System.arraycopy(arrnode, 0, nodeSet.m_map, 0, arrnode.length);
        }
        return nodeSet;
    }

    @Override
    public NodeIterator cloneWithReset() throws CloneNotSupportedException {
        NodeSet nodeSet = (NodeSet)this.clone();
        nodeSet.reset();
        return nodeSet;
    }

    public boolean contains(Node node) {
        this.runTo(-1);
        if (this.m_map == null) {
            return false;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            Node node2 = this.m_map[i];
            if (node2 == null || !node2.equals(node)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void detach() {
    }

    public Node elementAt(int n) {
        Node[] arrnode = this.m_map;
        if (arrnode == null) {
            return null;
        }
        return arrnode[n];
    }

    @Override
    public Node getCurrentNode() {
        boolean bl = this.m_cacheNodes;
        Node node = null;
        if (bl) {
            int n = this.m_next;
            int n2 = this.m_next;
            if (n2 < this.m_firstFree) {
                node = this.elementAt(n2);
            }
            this.m_next = n;
            return node;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_CANNOT_INDEX", null));
    }

    @Override
    public int getCurrentPos() {
        return this.m_next;
    }

    @Override
    public boolean getExpandEntityReferences() {
        return true;
    }

    @Override
    public NodeFilter getFilter() {
        return null;
    }

    @Override
    public int getLast() {
        return this.m_last;
    }

    @Override
    public int getLength() {
        this.runTo(-1);
        return this.size();
    }

    @Override
    public Node getRoot() {
        return null;
    }

    public boolean getShouldCacheNodes() {
        return this.m_cacheNodes;
    }

    @Override
    public int getWhatToShow() {
        return -17;
    }

    public int indexOf(Node node) {
        this.runTo(-1);
        if (this.m_map == null) {
            return -1;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            Node node2 = this.m_map[i];
            if (node2 == null || !node2.equals(node)) continue;
            return i;
        }
        return -1;
    }

    public int indexOf(Node node, int n) {
        this.runTo(-1);
        if (this.m_map == null) {
            return -1;
        }
        while (n < this.m_firstFree) {
            Node node2 = this.m_map[n];
            if (node2 != null && node2.equals(node)) {
                return n;
            }
            ++n;
        }
        return -1;
    }

    public void insertElementAt(Node node, int n) {
        if (this.m_mutable) {
            Node[] arrnode;
            int n2;
            Node[] arrnode2 = this.m_map;
            if (arrnode2 == null) {
                n2 = this.m_blocksize;
                this.m_map = new Node[n2];
                this.m_mapSize = n2;
            } else {
                n2 = this.m_firstFree;
                int n3 = this.m_mapSize;
                if (n2 + 1 >= n3) {
                    this.m_mapSize = n3 + this.m_blocksize;
                    arrnode = new Node[this.m_mapSize];
                    System.arraycopy(arrnode2, 0, arrnode, 0, n2 + 1);
                    this.m_map = arrnode;
                }
            }
            n2 = this.m_firstFree;
            if (n <= n2 - 1) {
                arrnode = this.m_map;
                System.arraycopy(arrnode, n, arrnode, n + 1, n2 - n);
            }
            this.m_map[n] = node;
            ++this.m_firstFree;
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void insertNode(Node node, int n) {
        if (this.m_mutable) {
            this.insertElementAt(node, n);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    @Override
    public boolean isFresh() {
        boolean bl = this.m_next == 0;
        return bl;
    }

    @Override
    public Node item(int n) {
        this.runTo(n);
        return this.elementAt(n);
    }

    @Override
    public Node nextNode() throws DOMException {
        if (this.m_next < this.size()) {
            Node node = this.elementAt(this.m_next);
            ++this.m_next;
            return node;
        }
        return null;
    }

    public final Node peepOrNull() {
        int n;
        Object object = this.m_map;
        object = object != null && (n = this.m_firstFree) > 0 ? object[n - 1] : null;
        return object;
    }

    public final Node peepTail() {
        return this.m_map[this.m_firstFree - 1];
    }

    public final Node peepTailSub1() {
        return this.m_map[this.m_firstFree - 2];
    }

    public final Node pop() {
        --this.m_firstFree;
        Node[] arrnode = this.m_map;
        int n = this.m_firstFree;
        Node node = arrnode[n];
        arrnode[n] = null;
        return node;
    }

    public final Node popAndTop() {
        --this.m_firstFree;
        Node[] arrnode = this.m_map;
        int n = this.m_firstFree;
        Node node = null;
        arrnode[n] = null;
        if (n != 0) {
            node = arrnode[n - 1];
        }
        return node;
    }

    public final void popPair() {
        this.m_firstFree -= 2;
        Node[] arrnode = this.m_map;
        int n = this.m_firstFree;
        arrnode[n] = null;
        arrnode[n + 1] = null;
    }

    public final void popQuick() {
        --this.m_firstFree;
        this.m_map[this.m_firstFree] = null;
    }

    @Override
    public Node previousNode() throws DOMException {
        if (this.m_cacheNodes) {
            int n = this.m_next;
            if (n - 1 > 0) {
                this.m_next = n - 1;
                return this.elementAt(this.m_next);
            }
            return null;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_CANNOT_ITERATE", null));
    }

    public final void push(Node node) {
        int n = this.m_firstFree;
        int n2 = this.m_mapSize;
        if (n + 1 >= n2) {
            Node[] arrnode = this.m_map;
            if (arrnode == null) {
                n2 = this.m_blocksize;
                this.m_map = new Node[n2];
                this.m_mapSize = n2;
            } else {
                this.m_mapSize = n2 + this.m_blocksize;
                Node[] arrnode2 = new Node[this.m_mapSize];
                System.arraycopy(arrnode, 0, arrnode2, 0, n + 1);
                this.m_map = arrnode2;
            }
        }
        this.m_map[n] = node;
        this.m_firstFree = n + 1;
    }

    public final void pushPair(Node node, Node node2) {
        int n;
        Node[] arrnode = this.m_map;
        if (arrnode == null) {
            n = this.m_blocksize;
            this.m_map = new Node[n];
            this.m_mapSize = n;
        } else {
            n = this.m_firstFree;
            int n2 = this.m_mapSize;
            if (n + 2 >= n2) {
                this.m_mapSize = n2 + this.m_blocksize;
                Node[] arrnode2 = new Node[this.m_mapSize];
                System.arraycopy(arrnode, 0, arrnode2, 0, n);
                this.m_map = arrnode2;
            }
        }
        arrnode = this.m_map;
        n = this.m_firstFree;
        arrnode[n] = node;
        arrnode[n + 1] = node2;
        this.m_firstFree = n + 2;
    }

    public void removeAllElements() {
        if (this.m_map == null) {
            return;
        }
        for (int i = 0; i < this.m_firstFree; ++i) {
            this.m_map[i] = null;
        }
        this.m_firstFree = 0;
    }

    public boolean removeElement(Node arrnode) {
        if (this.m_mutable) {
            if (this.m_map == null) {
                return false;
            }
            for (int i = 0; i < this.m_firstFree; ++i) {
                Node node = this.m_map[i];
                if (node == null || !node.equals(arrnode)) continue;
                int n = this.m_firstFree;
                if (i < n - 1) {
                    arrnode = this.m_map;
                    System.arraycopy(arrnode, i + 1, arrnode, i, n - i - 1);
                }
                --this.m_firstFree;
                this.m_map[this.m_firstFree] = null;
                return true;
            }
            return false;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    public void removeElementAt(int n) {
        Object object = this.m_map;
        if (object == null) {
            return;
        }
        int n2 = this.m_firstFree;
        if (n < n2) {
            if (n >= 0) {
                if (n < n2 - 1) {
                    System.arraycopy(object, n + 1, object, n, n2 - n - 1);
                }
                --this.m_firstFree;
                this.m_map[this.m_firstFree] = null;
                return;
            }
            throw new ArrayIndexOutOfBoundsException(n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" >= ");
        ((StringBuilder)object).append(this.m_firstFree);
        throw new ArrayIndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public void removeNode(Node node) {
        if (this.m_mutable) {
            this.removeElement(node);
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
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
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_CANNOT_INDEX", null));
    }

    @Override
    public void setCurrentPos(int n) {
        if (this.m_cacheNodes) {
            this.m_next = n;
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_CANNOT_INDEX", null));
    }

    public void setElementAt(Node node, int n) {
        if (this.m_mutable) {
            if (this.m_map == null) {
                int n2 = this.m_blocksize;
                this.m_map = new Node[n2];
                this.m_mapSize = n2;
            }
            this.m_map[n] = node;
            return;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_NODESET_NOT_MUTABLE", null));
    }

    @Override
    public void setLast(int n) {
        this.m_last = n;
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

    public final void setTail(Node node) {
        this.m_map[this.m_firstFree - 1] = node;
    }

    public final void setTailSub1(Node node) {
        this.m_map[this.m_firstFree - 2] = node;
    }

    @Override
    public int size() {
        return this.m_firstFree;
    }
}

