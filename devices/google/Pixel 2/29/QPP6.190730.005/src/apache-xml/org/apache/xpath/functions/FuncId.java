/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.functions;

import java.util.StringTokenizer;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMIterator;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.utils.StringVector;
import org.apache.xml.utils.XMLString;
import org.apache.xpath.Expression;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.FunctionOneArg;
import org.apache.xpath.objects.XNodeSet;
import org.apache.xpath.objects.XObject;

public class FuncId
extends FunctionOneArg {
    static final long serialVersionUID = 8930573966143567310L;

    private StringVector getNodesByID(XPathContext xPathContext, int n, String object, StringVector object2, NodeSetDTM nodeSetDTM, boolean bl) {
        Object object3;
        block6 : {
            object3 = object2;
            if (object == null) break block6;
            StringTokenizer stringTokenizer = new StringTokenizer((String)object);
            boolean bl2 = stringTokenizer.hasMoreTokens();
            DTM dTM = xPathContext.getDTM(n);
            do {
                block7 : {
                    block8 : {
                        object3 = object2;
                        if (!bl2) break;
                        object3 = stringTokenizer.nextToken();
                        bl2 = stringTokenizer.hasMoreTokens();
                        if (object2 != null && ((StringVector)object2).contains((String)object3)) continue;
                        n = dTM.getElementById((String)object3);
                        if (-1 != n) {
                            nodeSetDTM.addNodeInDocOrder(n, xPathContext);
                        }
                        object = object2;
                        if (object3 == null) break block7;
                        if (bl2) break block8;
                        object = object2;
                        if (!bl) break block7;
                    }
                    object = object2;
                    if (object2 == null) {
                        object = new StringVector();
                    }
                    ((StringVector)object).addElement((String)object3);
                }
                object2 = object;
            } while (true);
        }
        return object3;
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        int n = xPathContext.getDTM(xPathContext.getCurrentNode()).getDocument();
        if (-1 == n) {
            this.error(xPathContext, "ER_CONTEXT_HAS_NO_OWNERDOC", null);
        }
        Object object = this.m_arg0.execute(xPathContext);
        int n2 = ((XObject)object).getType();
        XNodeSet xNodeSet = new XNodeSet(xPathContext.getDTMManager());
        NodeSetDTM nodeSetDTM = xNodeSet.mutableNodeset();
        if (4 == n2) {
            object = ((XObject)object).iter();
            n2 = object.nextNode();
            StringVector stringVector = null;
            while (-1 != n2) {
                String string = object.getDTM(n2).getStringValue(n2).toString();
                n2 = object.nextNode();
                boolean bl = -1 != n2;
                stringVector = this.getNodesByID(xPathContext, n, string, stringVector, nodeSetDTM, bl);
            }
        } else {
            if (-1 == n2) {
                return xNodeSet;
            }
            this.getNodesByID(xPathContext, n, ((XObject)object).str(), null, nodeSetDTM, false);
        }
        return xNodeSet;
    }
}

