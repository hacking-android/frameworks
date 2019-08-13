/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.objects;

import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.ref.DTMNodeIterator;
import org.apache.xml.dtm.ref.DTMNodeList;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.NodeVector;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.axes.NodeSequence;
import org.apache.xpath.objects.Comparator;
import org.apache.xpath.objects.EqualComparator;
import org.apache.xpath.objects.GreaterThanComparator;
import org.apache.xpath.objects.GreaterThanOrEqualComparator;
import org.apache.xpath.objects.LessThanComparator;
import org.apache.xpath.objects.LessThanOrEqualComparator;
import org.apache.xpath.objects.NotEqualComparator;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;
import org.w3c.dom.NodeList;
import org.w3c.dom.traversal.NodeIterator;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;

public class XNodeSet
extends NodeSequence {
    static final EqualComparator S_EQ;
    static final GreaterThanComparator S_GT;
    static final GreaterThanOrEqualComparator S_GTE;
    static final LessThanComparator S_LT;
    static final LessThanOrEqualComparator S_LTE;
    static final NotEqualComparator S_NEQ;
    static final long serialVersionUID = 1916026368035639667L;

    static {
        S_LT = new LessThanComparator();
        S_LTE = new LessThanOrEqualComparator();
        S_GT = new GreaterThanComparator();
        S_GTE = new GreaterThanOrEqualComparator();
        S_EQ = new EqualComparator();
        S_NEQ = new NotEqualComparator();
    }

    protected XNodeSet() {
    }

    public XNodeSet(int n, DTMManager dTMManager) {
        super(new NodeSetDTM(dTMManager));
        this.m_dtmMgr = dTMManager;
        if (-1 != n) {
            ((NodeSetDTM)this.m_obj).addNode(n);
            this.m_last = 1;
        } else {
            this.m_last = 0;
        }
    }

    public XNodeSet(DTMIterator dTMIterator) {
        if (dTMIterator instanceof XNodeSet) {
            dTMIterator = (XNodeSet)dTMIterator;
            this.setIter(((XNodeSet)dTMIterator).m_iter);
            this.m_dtmMgr = ((XNodeSet)dTMIterator).m_dtmMgr;
            this.m_last = ((XNodeSet)dTMIterator).m_last;
            if (!((NodeSequence)dTMIterator).hasCache()) {
                ((NodeSequence)dTMIterator).setShouldCacheNodes(true);
            }
            this.setObject(((NodeSequence)dTMIterator).getIteratorCache());
        } else {
            this.setIter(dTMIterator);
        }
    }

    public XNodeSet(DTMManager dTMManager) {
        this(-1, dTMManager);
    }

    public XNodeSet(XNodeSet xNodeSet) {
        this.setIter(xNodeSet.m_iter);
        this.m_dtmMgr = xNodeSet.m_dtmMgr;
        this.m_last = xNodeSet.m_last;
        if (!xNodeSet.hasCache()) {
            xNodeSet.setShouldCacheNodes(true);
        }
        this.setObject(xNodeSet.m_obj);
    }

    @Override
    public void appendToFsb(FastStringBuffer fastStringBuffer) {
        ((XString)this.xstr()).appendToFsb(fastStringBuffer);
    }

    @Override
    public boolean bool() {
        boolean bl = false;
        if (this.item(0) != -1) {
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean boolWithSideEffects() {
        boolean bl = this.nextNode() != -1;
        return bl;
    }

    public boolean compare(XObject vector, Comparator comparator) throws TransformerException {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        int n = ((XObject)((Object)vector)).getType();
        if (4 == n) {
            DTMIterator dTMIterator = this.iterRaw();
            DTMIterator dTMIterator2 = ((XNodeSet)((Object)vector)).iterRaw();
            vector = null;
            while (-1 != (n = dTMIterator.nextNode())) {
                Vector<XMLString> vector2;
                XMLString xMLString = this.getStringFromNode(n);
                if (vector == null) {
                    do {
                        n = dTMIterator2.nextNode();
                        bl = bl4;
                        if (-1 == n) break;
                        XMLString xMLString2 = this.getStringFromNode(n);
                        if (comparator.compareStrings(xMLString, xMLString2)) {
                            bl = true;
                            break;
                        }
                        vector2 = vector;
                        if (vector == null) {
                            vector2 = new Vector<XMLString>();
                        }
                        vector2.addElement(xMLString2);
                        vector = vector2;
                    } while (true);
                    vector2 = vector;
                } else {
                    int n2 = vector.size();
                    n = 0;
                    do {
                        bl = bl4;
                        vector2 = vector;
                        if (n >= n2) break;
                        if (comparator.compareStrings(xMLString, (XMLString)vector.elementAt(n))) {
                            bl = true;
                            vector2 = vector;
                            break;
                        }
                        ++n;
                    } while (true);
                }
                bl4 = bl;
                vector = vector2;
            }
            dTMIterator.reset();
            dTMIterator2.reset();
        } else if (1 == n) {
            double d = this.bool() ? 1.0 : 0.0;
            bl4 = comparator.compareNumbers(d, ((XObject)((Object)vector)).num());
        } else if (2 == n) {
            DTMIterator dTMIterator;
            block21 : {
                dTMIterator = this.iterRaw();
                double d = ((XObject)((Object)vector)).num();
                do {
                    n = dTMIterator.nextNode();
                    bl4 = bl;
                    if (-1 == n) break block21;
                } while (!comparator.compareNumbers(this.getNumberFromNode(n), d));
                bl4 = true;
            }
            dTMIterator.reset();
        } else if (5 == n) {
            block22 : {
                XMLString xMLString = ((XObject)((Object)vector)).xstr();
                vector = this.iterRaw();
                do {
                    n = vector.nextNode();
                    bl4 = bl2;
                    if (-1 == n) break block22;
                } while (!comparator.compareStrings(this.getStringFromNode(n), xMLString));
                bl4 = true;
            }
            vector.reset();
        } else if (3 == n) {
            block23 : {
                XMLString xMLString = ((XObject)((Object)vector)).xstr();
                vector = this.iterRaw();
                do {
                    n = vector.nextNode();
                    bl4 = bl3;
                    if (-1 == n) break block23;
                } while (!comparator.compareStrings(this.getStringFromNode(n), xMLString));
                bl4 = true;
            }
            vector.reset();
        } else {
            bl4 = comparator.compareNumbers(this.num(), ((XObject)((Object)vector)).num());
        }
        return bl4;
    }

    @Override
    public void dispatchCharactersEvents(ContentHandler contentHandler) throws SAXException {
        int n = this.item(0);
        if (n != -1) {
            this.m_dtmMgr.getDTM(n).dispatchCharactersEvents(n, contentHandler, false);
        }
    }

    @Override
    public boolean equals(XObject xObject) {
        try {
            boolean bl = this.compare(xObject, S_EQ);
            return bl;
        }
        catch (TransformerException transformerException) {
            throw new WrappedRuntimeException(transformerException);
        }
    }

    @Override
    public XObject getFresh() {
        try {
            if (this.hasCache()) {
                XObject xObject = (XObject)((Object)this.cloneWithReset());
                return xObject;
            }
            return this;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException.getMessage());
        }
    }

    public double getNumberFromNode(int n) {
        return this.m_dtmMgr.getDTM(n).getStringValue(n).toDouble();
    }

    public XMLString getStringFromNode(int n) {
        if (-1 != n) {
            return this.m_dtmMgr.getDTM(n).getStringValue(n);
        }
        return XString.EMPTYSTRING;
    }

    @Override
    public int getType() {
        return 4;
    }

    @Override
    public String getTypeString() {
        return "#NODESET";
    }

    @Override
    public boolean greaterThan(XObject xObject) throws TransformerException {
        return this.compare(xObject, S_GT);
    }

    @Override
    public boolean greaterThanOrEqual(XObject xObject) throws TransformerException {
        return this.compare(xObject, S_GTE);
    }

    @Override
    public DTMIterator iter() {
        try {
            if (this.hasCache()) {
                DTMIterator dTMIterator = this.cloneWithReset();
                return dTMIterator;
            }
            return this;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new RuntimeException(cloneNotSupportedException.getMessage());
        }
    }

    public DTMIterator iterRaw() {
        return this;
    }

    @Override
    public boolean lessThan(XObject xObject) throws TransformerException {
        return this.compare(xObject, S_LT);
    }

    @Override
    public boolean lessThanOrEqual(XObject xObject) throws TransformerException {
        return this.compare(xObject, S_LTE);
    }

    @Override
    public NodeSetDTM mutableNodeset() {
        NodeSetDTM nodeSetDTM;
        if (this.m_obj instanceof NodeSetDTM) {
            nodeSetDTM = (NodeSetDTM)this.m_obj;
        } else {
            nodeSetDTM = new NodeSetDTM(this.iter());
            this.setObject(nodeSetDTM);
            this.setCurrentPos(0);
        }
        return nodeSetDTM;
    }

    @Override
    public NodeList nodelist() throws TransformerException {
        DTMNodeList dTMNodeList = new DTMNodeList(this);
        this.SetVector(((XNodeSet)dTMNodeList.getDTMIterator()).getVector());
        return dTMNodeList;
    }

    @Override
    public NodeIterator nodeset() throws TransformerException {
        return new DTMNodeIterator(this.iter());
    }

    @Override
    public boolean notEquals(XObject xObject) throws TransformerException {
        return this.compare(xObject, S_NEQ);
    }

    @Override
    public double num() {
        int n = this.item(0);
        double d = n != -1 ? this.getNumberFromNode(n) : Double.NaN;
        return d;
    }

    @Override
    public double numWithSideEffects() {
        int n = this.nextNode();
        double d = n != -1 ? this.getNumberFromNode(n) : Double.NaN;
        return d;
    }

    @Override
    public Object object() {
        if (this.m_obj == null) {
            return this;
        }
        return this.m_obj;
    }

    public void release(DTMIterator dTMIterator) {
    }

    @Override
    public String str() {
        int n = this.item(0);
        String string = n != -1 ? this.getStringFromNode(n).toString() : "";
        return string;
    }

    @Override
    public XMLString xstr() {
        int n = this.item(0);
        XMLString xMLString = n != -1 ? this.getStringFromNode(n) : XString.EMPTYSTRING;
        return xMLString;
    }
}

