/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Hashtable;
import javax.xml.transform.TransformerException;
import org.apache.xalan.transformer.KeyManager;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionNode;
import org.apache.xpath.XPathContext;
import org.apache.xpath.axes.NodeSequence;
import org.apache.xpath.axes.UnionPathIterator;
import org.apache.xpath.functions.Function2Args;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;

public class FuncKey
extends Function2Args {
    private static Boolean ISTRUE = new Boolean(true);
    static final long serialVersionUID = 9089293100115347340L;

    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        Hashtable<Object, Boolean> hashtable = (TransformerImpl)((XPathContext)object).getOwnerObject();
        int n = ((XPathContext)object).getCurrentNode();
        int n2 = ((XPathContext)object).getDTM(n).getDocumentRoot(n);
        QName qName = new QName(this.getArg0().execute((XPathContext)object).str(), ((XPathContext)object).getNamespaceContext());
        Object object2 = this.getArg1().execute((XPathContext)object);
        n = 4 == ((XObject)object2).getType() ? 1 : 0;
        KeyManager keyManager = ((TransformerImpl)((Object)hashtable)).getKeyManager();
        if (n != 0) {
            hashtable = (XNodeSet)object2;
            ((NodeSequence)((Object)hashtable)).setShouldCacheNodes(true);
            if (((NodeSequence)((Object)hashtable)).getLength() <= 1) {
                n = 0;
            }
        }
        if (n != 0) {
            hashtable = null;
            object2 = ((XObject)object2).iter();
            UnionPathIterator unionPathIterator = new UnionPathIterator();
            unionPathIterator.exprSetParent(this);
            while (-1 != (n = object2.nextNode())) {
                Object object3 = ((XPathContext)object).getDTM(n);
                if ((object3 = object3.getStringValue(n)) == null) continue;
                if (hashtable == null) {
                    hashtable = new Hashtable<Object, Boolean>();
                }
                if (hashtable.get(object3) != null) continue;
                hashtable.put(object3, ISTRUE);
                object3 = keyManager.getNodeSetDTMByKey((XPathContext)object, n2, qName, (XMLString)object3, ((XPathContext)object).getNamespaceContext());
                ((NodeSequence)object3).setRoot(((XPathContext)object).getCurrentNode(), object);
                unionPathIterator.addIterator((DTMIterator)object3);
            }
            unionPathIterator.setRoot(((XPathContext)object).getCurrentNode(), object);
            object = new XNodeSet(unionPathIterator);
        } else {
            hashtable = ((XObject)object2).xstr();
            hashtable = keyManager.getNodeSetDTMByKey((XPathContext)object, n2, qName, (XMLString)((Object)hashtable), ((XPathContext)object).getNamespaceContext());
            ((NodeSequence)((Object)hashtable)).setRoot(((XPathContext)object).getCurrentNode(), object);
            object = hashtable;
        }
        return object;
    }
}

