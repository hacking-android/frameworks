/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.text.CollationKey;
import java.text.Collator;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.transformer.NodeSortKey;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;

public class NodeSorter {
    XPathContext m_execContext;
    Vector m_keys;

    public NodeSorter(XPathContext xPathContext) {
        this.m_execContext = xPathContext;
    }

    int compare(NodeCompareElem nodeCompareElem, NodeCompareElem nodeCompareElem2, int n, XPathContext xPathContext) throws TransformerException {
        NodeSortKey nodeSortKey = (NodeSortKey)this.m_keys.elementAt(n);
        boolean bl = nodeSortKey.m_treatAsNumbers;
        int n2 = 0;
        int n3 = 0;
        int n4 = -1;
        if (bl) {
            double d;
            double d2;
            if (n == 0) {
                d = (Double)nodeCompareElem.m_key1Value;
                d2 = (Double)nodeCompareElem2.m_key1Value;
            } else if (n == 1) {
                d = (Double)nodeCompareElem.m_key2Value;
                d2 = (Double)nodeCompareElem2.m_key2Value;
            } else {
                XObject xObject = nodeSortKey.m_selectPat.execute(this.m_execContext, nodeCompareElem.m_node, nodeSortKey.m_namespaceContext);
                XObject xObject2 = nodeSortKey.m_selectPat.execute(this.m_execContext, nodeCompareElem2.m_node, nodeSortKey.m_namespaceContext);
                d = xObject.num();
                d2 = xObject2.num();
            }
            if (d == d2 && n + 1 < this.m_keys.size()) {
                n2 = this.compare(nodeCompareElem, nodeCompareElem2, n + 1, xPathContext);
            } else {
                d = Double.isNaN(d) ? (Double.isNaN(d2) ? 0.0 : -1.0) : (Double.isNaN(d2) ? 1.0 : (d -= d2));
                if (d < 0.0) {
                    n2 = nodeSortKey.m_descending ? 1 : -1;
                } else {
                    n2 = n3;
                    if (d > 0.0) {
                        n2 = nodeSortKey.m_descending ? -1 : 1;
                    }
                }
            }
        } else {
            Object object;
            Object object2;
            int n5;
            if (n == 0) {
                object2 = (CollationKey)nodeCompareElem.m_key1Value;
                object = (CollationKey)nodeCompareElem2.m_key1Value;
            } else if (n == 1) {
                object2 = (CollationKey)nodeCompareElem.m_key2Value;
                object = (CollationKey)nodeCompareElem2.m_key2Value;
            } else {
                object2 = nodeSortKey.m_selectPat.execute(this.m_execContext, nodeCompareElem.m_node, nodeSortKey.m_namespaceContext);
                object = nodeSortKey.m_selectPat.execute(this.m_execContext, nodeCompareElem2.m_node, nodeSortKey.m_namespaceContext);
                object2 = nodeSortKey.m_col.getCollationKey(((XObject)object2).str());
                object = nodeSortKey.m_col.getCollationKey(((XObject)object).str());
            }
            n3 = n5 = ((CollationKey)object2).compareTo((CollationKey)object);
            if (nodeSortKey.m_caseOrderUpper) {
                n3 = n5;
                if (((CollationKey)object2).getSourceString().toLowerCase().equals(((CollationKey)object).getSourceString().toLowerCase())) {
                    if (n5 != 0) {
                        n2 = -n5;
                    }
                    n3 = n2;
                }
            }
            n2 = n3;
            if (nodeSortKey.m_descending) {
                n2 = -n3;
            }
        }
        n3 = n2;
        if (n2 == 0) {
            n3 = n2;
            if (n + 1 < this.m_keys.size()) {
                n3 = this.compare(nodeCompareElem, nodeCompareElem2, n + 1, xPathContext);
            }
        }
        n = n3;
        if (n3 == 0) {
            n = xPathContext.getDTM(nodeCompareElem.m_node).isNodeAfter(nodeCompareElem.m_node, nodeCompareElem2.m_node) ? n4 : 1;
        }
        return n;
    }

    void mergesort(Vector vector, Vector vector2, int n, int n2, XPathContext xPathContext) throws TransformerException {
        if (n2 - n > 0) {
            int n3;
            int n4 = (n2 + n) / 2;
            this.mergesort(vector, vector2, n, n4, xPathContext);
            this.mergesort(vector, vector2, n4 + 1, n2, xPathContext);
            for (n3 = n4; n3 >= n; --n3) {
                if (n3 >= vector2.size()) {
                    vector2.insertElementAt(vector.elementAt(n3), n3);
                    continue;
                }
                vector2.setElementAt(vector.elementAt(n3), n3);
            }
            int n5 = n;
            for (n3 = n4 + 1; n3 <= n2; ++n3) {
                if (n2 + n4 + 1 - n3 >= vector2.size()) {
                    vector2.insertElementAt(vector.elementAt(n3), n2 + n4 + 1 - n3);
                    continue;
                }
                vector2.setElementAt(vector.elementAt(n3), n2 + n4 + 1 - n3);
            }
            n3 = n2;
            while (n <= n2) {
                int n6;
                int n7 = n5 == n3 ? -1 : this.compare((NodeCompareElem)vector2.elementAt(n5), (NodeCompareElem)vector2.elementAt(n3), 0, xPathContext);
                if (n7 < 0) {
                    vector.setElementAt(vector2.elementAt(n5), n);
                    n4 = n5 + 1;
                    n6 = n3;
                } else {
                    n4 = n5;
                    n6 = n3;
                    if (n7 > 0) {
                        vector.setElementAt(vector2.elementAt(n3), n);
                        n6 = n3 - 1;
                        n4 = n5;
                    }
                }
                ++n;
                n5 = n4;
                n3 = n6;
            }
        }
    }

    public void sort(DTMIterator dTMIterator, Vector vector, XPathContext xPathContext) throws TransformerException {
        int n;
        this.m_keys = vector;
        int n2 = dTMIterator.getLength();
        vector = new Vector<NodeCompareElem>();
        for (n = 0; n < n2; ++n) {
            vector.addElement(new NodeCompareElem(dTMIterator.item(n)));
        }
        this.mergesort(vector, new Vector(), 0, n2 - 1, xPathContext);
        for (n = 0; n < n2; ++n) {
            dTMIterator.setItem(((NodeCompareElem)vector.elementAt((int)n)).m_node, n);
        }
        dTMIterator.setCurrentPos(0);
    }

    class NodeCompareElem {
        Object m_key1Value;
        Object m_key2Value;
        int m_node;
        int maxkey = 2;

        NodeCompareElem(int n) throws TransformerException {
            this.m_node = n;
            if (!((NodeSorter)NodeSorter.this).m_keys.isEmpty()) {
                Object object2 = (NodeSortKey)((NodeSorter)NodeSorter.this).m_keys.elementAt(0);
                XObject xObject = ((NodeSortKey)object2).m_selectPat.execute(((NodeSorter)NodeSorter.this).m_execContext, n, ((NodeSortKey)object2).m_namespaceContext);
                this.m_key1Value = ((NodeSortKey)object2).m_treatAsNumbers ? new Double(xObject.num()) : ((NodeSortKey)object2).m_col.getCollationKey(xObject.str());
                if (xObject.getType() == 4 && -1 == (object2 = ((XNodeSet)xObject).iterRaw()).getCurrentNode()) {
                    object2.nextNode();
                }
                if (((NodeSorter)NodeSorter.this).m_keys.size() > 1) {
                    object2 = (NodeSortKey)((NodeSorter)NodeSorter.this).m_keys.elementAt(1);
                    NodeSorter.this = ((NodeSortKey)object2).m_selectPat.execute(((NodeSorter)NodeSorter.this).m_execContext, n, ((NodeSortKey)object2).m_namespaceContext);
                    this.m_key2Value = ((NodeSortKey)object2).m_treatAsNumbers ? new Double(((XObject)NodeSorter.this).num()) : ((NodeSortKey)object2).m_col.getCollationKey(((XObject)NodeSorter.this).str());
                }
            }
        }
    }

}

