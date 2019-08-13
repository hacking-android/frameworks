/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.patterns;

import java.io.PrintStream;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xml.dtm.DTM;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XNumber;
import org.apache.xpath.objects.XObject;

public class NodeTest
extends Expression {
    public static final XNumber SCORE_NODETEST = new XNumber(-0.5);
    public static final XNumber SCORE_NONE;
    public static final XNumber SCORE_NSWILD;
    public static final XNumber SCORE_OTHER;
    public static final XNumber SCORE_QNAME;
    public static final int SHOW_BYFUNCTION = 65536;
    public static final String SUPPORTS_PRE_STRIPPING = "http://xml.apache.org/xpath/features/whitespace-pre-stripping";
    public static final String WILD = "*";
    static final long serialVersionUID = -5736721866747906182L;
    private boolean m_isTotallyWild;
    protected String m_name;
    String m_namespace;
    XNumber m_score;
    protected int m_whatToShow;

    static {
        SCORE_NSWILD = new XNumber(-0.25);
        SCORE_QNAME = new XNumber(0.0);
        SCORE_OTHER = new XNumber(0.5);
        SCORE_NONE = new XNumber(Double.NEGATIVE_INFINITY);
    }

    public NodeTest() {
    }

    public NodeTest(int n) {
        this.initNodeTest(n);
    }

    public NodeTest(int n, String string, String string2) {
        this.initNodeTest(n, string, string2);
    }

    public static void debugWhatToShow(int n) {
        Object object = new Vector<String>();
        if ((n & 2) != 0) {
            ((Vector)object).addElement("SHOW_ATTRIBUTE");
        }
        if ((n & 4096) != 0) {
            ((Vector)object).addElement("SHOW_NAMESPACE");
        }
        if ((n & 8) != 0) {
            ((Vector)object).addElement("SHOW_CDATA_SECTION");
        }
        if ((n & 128) != 0) {
            ((Vector)object).addElement("SHOW_COMMENT");
        }
        if ((n & 256) != 0) {
            ((Vector)object).addElement("SHOW_DOCUMENT");
        }
        if ((n & 1024) != 0) {
            ((Vector)object).addElement("SHOW_DOCUMENT_FRAGMENT");
        }
        if ((n & 512) != 0) {
            ((Vector)object).addElement("SHOW_DOCUMENT_TYPE");
        }
        if ((n & 1) != 0) {
            ((Vector)object).addElement("SHOW_ELEMENT");
        }
        if ((n & 32) != 0) {
            ((Vector)object).addElement("SHOW_ENTITY");
        }
        if ((n & 16) != 0) {
            ((Vector)object).addElement("SHOW_ENTITY_REFERENCE");
        }
        if ((n & 2048) != 0) {
            ((Vector)object).addElement("SHOW_NOTATION");
        }
        if ((n & 64) != 0) {
            ((Vector)object).addElement("SHOW_PROCESSING_INSTRUCTION");
        }
        if ((n & 4) != 0) {
            ((Vector)object).addElement("SHOW_TEXT");
        }
        int n2 = ((Vector)object).size();
        for (int i = 0; i < n2; ++i) {
            if (i > 0) {
                System.out.print(" | ");
            }
            System.out.print(((Vector)object).elementAt(i));
        }
        if (n2 == 0) {
            object = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("empty whatToShow: ");
            stringBuilder.append(n);
            ((PrintStream)object).print(stringBuilder.toString());
        }
        System.out.println();
    }

    public static int getNodeTypeTest(int n) {
        if ((n & 1) != 0) {
            return 1;
        }
        if ((n & 2) != 0) {
            return 2;
        }
        if ((n & 4) != 0) {
            return 3;
        }
        if ((n & 256) != 0) {
            return 9;
        }
        if ((n & 1024) != 0) {
            return 11;
        }
        if ((n & 4096) != 0) {
            return 13;
        }
        if ((n & 128) != 0) {
            return 8;
        }
        if ((n & 64) != 0) {
            return 7;
        }
        if ((n & 512) != 0) {
            return 10;
        }
        if ((n & 32) != 0) {
            return 6;
        }
        if ((n & 16) != 0) {
            return 5;
        }
        if ((n & 2048) != 0) {
            return 12;
        }
        if ((n & 8) != 0) {
            return 4;
        }
        return 0;
    }

    private static final boolean subPartMatch(String string, String string2) {
        boolean bl = string == string2 || string != null && (string2 == WILD || string.equals(string2));
        return bl;
    }

    private static final boolean subPartMatchNS(String string, String string2) {
        boolean bl = string == string2 || string != null && (string.length() > 0 ? string2 == WILD || string.equals(string2) : string2 == null);
        return bl;
    }

    protected void calcScore() {
        String string;
        this.m_score = this.m_namespace == null && this.m_name == null ? SCORE_NODETEST : (((string = this.m_namespace) == WILD || string == null) && this.m_name == WILD ? SCORE_NODETEST : (this.m_namespace != WILD && this.m_name == WILD ? SCORE_NSWILD : SCORE_QNAME));
        boolean bl = this.m_namespace == null && this.m_name == WILD;
        this.m_isTotallyWild = bl;
    }

    @Override
    public void callVisitors(ExpressionOwner expressionOwner, XPathVisitor xPathVisitor) {
        this.assertion(false, "callVisitors should not be called for this object!!!");
    }

    @Override
    public boolean deepEquals(Expression expression) {
        String string;
        if (!this.isSameClass(expression)) {
            return false;
        }
        expression = (NodeTest)expression;
        String string2 = ((NodeTest)expression).m_name;
        if (string2 != null) {
            string = this.m_name;
            if (string == null) {
                return false;
            }
            if (!string2.equals(string)) {
                return false;
            }
        } else if (this.m_name != null) {
            return false;
        }
        if ((string2 = ((NodeTest)expression).m_namespace) != null) {
            string = this.m_namespace;
            if (string == null) {
                return false;
            }
            if (!string2.equals(string)) {
                return false;
            }
        } else if (this.m_namespace != null) {
            return false;
        }
        if (this.m_whatToShow != ((NodeTest)expression).m_whatToShow) {
            return false;
        }
        return this.m_isTotallyWild == ((NodeTest)expression).m_isTotallyWild;
    }

    @Override
    public XObject execute(XPathContext xPathContext) throws TransformerException {
        return this.execute(xPathContext, xPathContext.getCurrentNode());
    }

    @Override
    public XObject execute(XPathContext object, int n) throws TransformerException {
        object = ((XPathContext)object).getDTM(n);
        int n2 = object.getNodeType(n);
        int n3 = this.m_whatToShow;
        if (n3 == -1) {
            return this.m_score;
        }
        if ((n2 = n3 & 1 << n2 - 1) != 1 && n2 != 2) {
            if (n2 != 4 && n2 != 8) {
                if (n2 != 64) {
                    if (n2 != 128) {
                        if (n2 != 256 && n2 != 1024) {
                            if (n2 != 4096) {
                                return SCORE_NONE;
                            }
                            object = NodeTest.subPartMatch(object.getLocalName(n), this.m_name) ? this.m_score : SCORE_NONE;
                            return object;
                        }
                        return SCORE_OTHER;
                    }
                    return this.m_score;
                }
                object = NodeTest.subPartMatch(object.getNodeName(n), this.m_name) ? this.m_score : SCORE_NONE;
                return object;
            }
            return this.m_score;
        }
        object = !(this.m_isTotallyWild || NodeTest.subPartMatchNS(object.getNamespaceURI(n), this.m_namespace) && NodeTest.subPartMatch(object.getLocalName(n), this.m_name)) ? SCORE_NONE : this.m_score;
        return object;
    }

    @Override
    public XObject execute(XPathContext object, int n, DTM dTM, int n2) throws TransformerException {
        n2 = this.m_whatToShow;
        if (n2 == -1) {
            return this.m_score;
        }
        if ((n2 &= 1 << dTM.getNodeType(n) - 1) != 1 && n2 != 2) {
            if (n2 != 4 && n2 != 8) {
                if (n2 != 64) {
                    if (n2 != 128) {
                        if (n2 != 256 && n2 != 1024) {
                            if (n2 != 4096) {
                                return SCORE_NONE;
                            }
                            object = NodeTest.subPartMatch(dTM.getLocalName(n), this.m_name) ? this.m_score : SCORE_NONE;
                            return object;
                        }
                        return SCORE_OTHER;
                    }
                    return this.m_score;
                }
                object = NodeTest.subPartMatch(dTM.getNodeName(n), this.m_name) ? this.m_score : SCORE_NONE;
                return object;
            }
            return this.m_score;
        }
        object = !(this.m_isTotallyWild || NodeTest.subPartMatchNS(dTM.getNamespaceURI(n), this.m_namespace) && NodeTest.subPartMatch(dTM.getLocalName(n), this.m_name)) ? SCORE_NONE : this.m_score;
        return object;
    }

    @Override
    public void fixupVariables(Vector vector, int n) {
    }

    public double getDefaultScore() {
        return this.m_score.num();
    }

    public String getLocalName() {
        String string;
        String string2 = string = this.m_name;
        if (string == null) {
            string2 = "";
        }
        return string2;
    }

    public String getNamespace() {
        return this.m_namespace;
    }

    public XNumber getStaticScore() {
        return this.m_score;
    }

    public int getWhatToShow() {
        return this.m_whatToShow;
    }

    public void initNodeTest(int n) {
        this.m_whatToShow = n;
        this.calcScore();
    }

    public void initNodeTest(int n, String string, String string2) {
        this.m_whatToShow = n;
        this.m_namespace = string;
        this.m_name = string2;
        this.calcScore();
    }

    public void setLocalName(String string) {
        this.m_name = string;
    }

    public void setNamespace(String string) {
        this.m_namespace = string;
    }

    public void setStaticScore(XNumber xNumber) {
        this.m_score = xNumber;
    }

    public void setWhatToShow(int n) {
        this.m_whatToShow = n;
    }
}

