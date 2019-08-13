/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.transformer;

import java.util.Hashtable;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.KeyDeclaration;
import org.apache.xalan.transformer.KeyIterator;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.WrappedRuntimeException;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.NodeSequence;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;

public class KeyTable {
    private int m_docKey;
    private Vector m_keyDeclarations;
    private XNodeSet m_keyNodes;
    private Hashtable m_refsTable = null;

    public KeyTable(int n, PrefixResolver prefixResolver, QName qName, Vector vector, XPathContext xPathContext) throws TransformerException {
        this.m_docKey = n;
        this.m_keyDeclarations = vector;
        this.m_keyNodes = new XNodeSet(new KeyIterator(qName, vector));
        this.m_keyNodes.allowDetachToRelease(false);
        this.m_keyNodes.setRoot(n, xPathContext);
    }

    private void addValueInRefsTable(XPathContext object, XMLString xMLString, int n) {
        XNodeSet xNodeSet = (XNodeSet)this.m_refsTable.get(xMLString);
        if (xNodeSet == null) {
            object = new XNodeSet(n, ((XPathContext)object).getDTMManager());
            ((NodeSequence)object).nextNode();
            this.m_refsTable.put(xMLString, object);
        } else if (xNodeSet.getCurrentNode() != n) {
            xNodeSet.mutableNodeset().addNode(n);
            xNodeSet.nextNode();
        }
    }

    private Vector getKeyDeclarations() {
        int n = this.m_keyDeclarations.size();
        Vector<KeyDeclaration> vector = new Vector<KeyDeclaration>(n);
        for (int i = 0; i < n; ++i) {
            KeyDeclaration keyDeclaration = (KeyDeclaration)this.m_keyDeclarations.elementAt(i);
            if (!keyDeclaration.getName().equals(this.getKeyTableName())) continue;
            vector.add(keyDeclaration);
        }
        return vector;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    private Hashtable getRefsTable() {
        if (this.m_refsTable == null) {
            int n;
            this.m_refsTable = new Hashtable(89);
            KeyIterator keyIterator = (KeyIterator)this.m_keyNodes.getContainedIter();
            XPathContext xPathContext = keyIterator.getXPathContext();
            Vector vector = this.getKeyDeclarations();
            int n2 = vector.size();
            this.m_keyNodes.reset();
            while (-1 != (n = this.m_keyNodes.nextNode())) {
                for (int i = 0; i < n2; ++i) {
                    try {
                        int n3;
                        Object object = (KeyDeclaration)vector.elementAt(i);
                        object = ((KeyDeclaration)object).getUse().execute(xPathContext, n, keyIterator.getPrefixResolver());
                        if (((XObject)object).getType() != 4) {
                            this.addValueInRefsTable(xPathContext, ((XObject)object).xstr(), n);
                            continue;
                        }
                        DTMIterator dTMIterator = ((XNodeSet)object).iterRaw();
                        while (-1 != (n3 = dTMIterator.nextNode())) {
                            object = xPathContext.getDTM(n3);
                            this.addValueInRefsTable(xPathContext, object.getStringValue(n3), n);
                        }
                        continue;
                    }
                    catch (TransformerException transformerException) {
                        throw new WrappedRuntimeException(transformerException);
                    }
                }
            }
        }
        return this.m_refsTable;
    }

    public int getDocKey() {
        return this.m_docKey;
    }

    KeyIterator getKeyIterator() {
        return (KeyIterator)this.m_keyNodes.getContainedIter();
    }

    public QName getKeyTableName() {
        return this.getKeyIterator().getName();
    }

    public XNodeSet getNodeSetDTMByKey(QName object, XMLString object2) {
        object = object2 = (XNodeSet)this.getRefsTable().get(object2);
        if (object2 != null) {
            try {
                object = (XNodeSet)((NodeSequence)object2).cloneWithReset();
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                object = null;
            }
        }
        object2 = object;
        if (object == null) {
            object2 = new XNodeSet(((KeyIterator)this.m_keyNodes.getContainedIter()).getXPathContext().getDTMManager()){

                @Override
                public void setRoot(int n, Object object) {
                }
            };
            ((NodeSequence)object2).reset();
        }
        return object2;
    }

}

