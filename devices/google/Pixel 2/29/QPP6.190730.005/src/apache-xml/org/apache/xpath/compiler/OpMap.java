/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xpath.compiler;

import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xml.utils.ObjectVector;
import org.apache.xpath.compiler.OpMapVector;

public class OpMap {
    static final int BLOCKTOKENQUEUESIZE = 500;
    public static final int MAPINDEX_LENGTH = 1;
    static final int MAXTOKENQUEUESIZE = 500;
    protected String m_currentPattern;
    OpMapVector m_opMap = null;
    ObjectVector m_tokenQueue = new ObjectVector(500, 500);

    public static int getFirstChildPos(int n) {
        return n + 2;
    }

    public static int getFirstChildPosOfStep(int n) {
        return n + 3;
    }

    public static int getNextOpPos(int[] arrn, int n) {
        return arrn[n + 1] + n;
    }

    public void error(String string, Object[] arrobject) throws TransformerException {
        throw new TransformerException(XSLMessages.createXPATHMessage(string, arrobject));
    }

    public int getArgLength(int n) {
        return this.m_opMap.elementAt(n + 1);
    }

    public int getArgLengthOfStep(int n) {
        return this.m_opMap.elementAt(n + 1 + 1) - 3;
    }

    public int getFirstPredicateOpPos(int n) throws TransformerException {
        int n2 = this.m_opMap.elementAt(n);
        if (n2 >= 37 && n2 <= 53) {
            return this.m_opMap.elementAt(n + 2) + n;
        }
        if (n2 >= 22 && n2 <= 25) {
            return this.m_opMap.elementAt(n + 1) + n;
        }
        if (-2 == n2) {
            return -2;
        }
        this.error("ER_UNKNOWN_OPCODE", new Object[]{String.valueOf(n2)});
        return -1;
    }

    public int getNextOpPos(int n) {
        return this.m_opMap.elementAt(n + 1) + n;
    }

    public int getNextStepPos(int n) {
        int n2 = this.getOp(n);
        if (n2 >= 37 && n2 <= 53) {
            return this.getNextOpPos(n);
        }
        if (n2 >= 22 && n2 <= 25) {
            n = this.getNextOpPos(n);
            while (29 == this.getOp(n)) {
                n = this.getNextOpPos(n);
            }
            n2 = this.getOp(n);
            if (n2 >= 37 && n2 <= 53) {
                return n;
            }
            return -1;
        }
        throw new RuntimeException(XSLMessages.createXPATHMessage("ER_UNKNOWN_STEP", new Object[]{String.valueOf(n2)}));
    }

    public int getOp(int n) {
        return this.m_opMap.elementAt(n);
    }

    public OpMapVector getOpMap() {
        return this.m_opMap;
    }

    public String getPatternString() {
        return this.m_currentPattern;
    }

    public String getStepLocalName(int n) {
        int n2 = this.getArgLengthOfStep(n);
        n = n2 != 0 ? (n2 != 1 ? (n2 != 2 ? (n2 != 3 ? -2 : this.m_opMap.elementAt(n + 5)) : this.m_opMap.elementAt(n + 4)) : -3) : -2;
        if (n >= 0) {
            return this.m_tokenQueue.elementAt(n).toString();
        }
        if (-3 == n) {
            return "*";
        }
        return null;
    }

    public String getStepNS(int n) {
        if (this.getArgLengthOfStep(n) == 3) {
            if ((n = this.m_opMap.elementAt(n + 4)) >= 0) {
                return (String)this.m_tokenQueue.elementAt(n);
            }
            if (-3 == n) {
                return "*";
            }
            return null;
        }
        return null;
    }

    public int getStepTestType(int n) {
        return this.m_opMap.elementAt(n + 3);
    }

    public Object getToken(int n) {
        return this.m_tokenQueue.elementAt(n);
    }

    public ObjectVector getTokenQueue() {
        return this.m_tokenQueue;
    }

    public int getTokenQueueSize() {
        return this.m_tokenQueue.size();
    }

    public void setOp(int n, int n2) {
        this.m_opMap.setElementAt(n2, n);
    }

    void shrink() {
        int n = this.m_opMap.elementAt(1);
        this.m_opMap.setToSize(n + 4);
        this.m_opMap.setElementAt(0, n);
        this.m_opMap.setElementAt(0, n + 1);
        this.m_opMap.setElementAt(0, n + 2);
        n = this.m_tokenQueue.size();
        this.m_tokenQueue.setToSize(n + 4);
        this.m_tokenQueue.setElementAt(null, n);
        this.m_tokenQueue.setElementAt(null, n + 1);
        this.m_tokenQueue.setElementAt(null, n + 2);
    }

    public String toString() {
        return this.m_currentPattern;
    }
}

