/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.text.UnicodeSet;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class RBBINode {
    static final int endMark = 6;
    static int gLastSerial = 0;
    static final int leafChar = 3;
    static final int lookAhead = 4;
    static final int nodeTypeLimit = 16;
    static final String[] nodeTypeNames = new String[]{"setRef", "uset", "varRef", "leafChar", "lookAhead", "tag", "endMark", "opStart", "opCat", "opOr", "opStar", "opPlus", "opQuestion", "opBreak", "opReverse", "opLParen"};
    static final int opBreak = 13;
    static final int opCat = 8;
    static final int opLParen = 15;
    static final int opOr = 9;
    static final int opPlus = 11;
    static final int opQuestion = 12;
    static final int opReverse = 14;
    static final int opStar = 10;
    static final int opStart = 7;
    static final int precLParen = 2;
    static final int precOpCat = 4;
    static final int precOpOr = 3;
    static final int precStart = 1;
    static final int precZero = 0;
    static final int setRef = 0;
    static final int tag = 5;
    static final int uset = 1;
    static final int varRef = 2;
    boolean fChainIn;
    int fFirstPos;
    Set<RBBINode> fFirstPosSet;
    Set<RBBINode> fFollowPos;
    UnicodeSet fInputSet;
    int fLastPos;
    Set<RBBINode> fLastPosSet;
    RBBINode fLeftChild;
    boolean fLookAheadEnd;
    boolean fNullable;
    RBBINode fParent;
    int fPrecedence = 0;
    RBBINode fRightChild;
    boolean fRuleRoot;
    int fSerialNum;
    String fText;
    int fType;
    int fVal;

    RBBINode(int n) {
        int n2;
        boolean bl = n < 16;
        Assert.assrt(bl);
        gLastSerial = n2 = gLastSerial + 1;
        this.fSerialNum = n2;
        this.fType = n;
        this.fFirstPosSet = new HashSet<RBBINode>();
        this.fLastPosSet = new HashSet<RBBINode>();
        this.fFollowPos = new HashSet<RBBINode>();
        this.fPrecedence = n == 8 ? 4 : (n == 9 ? 3 : (n == 7 ? 1 : (n == 15 ? 2 : 0)));
    }

    RBBINode(RBBINode rBBINode) {
        int n;
        gLastSerial = n = gLastSerial + 1;
        this.fSerialNum = n;
        this.fType = rBBINode.fType;
        this.fInputSet = rBBINode.fInputSet;
        this.fPrecedence = rBBINode.fPrecedence;
        this.fText = rBBINode.fText;
        this.fFirstPos = rBBINode.fFirstPos;
        this.fLastPos = rBBINode.fLastPos;
        this.fNullable = rBBINode.fNullable;
        this.fVal = rBBINode.fVal;
        this.fRuleRoot = false;
        this.fChainIn = rBBINode.fChainIn;
        this.fFirstPosSet = new HashSet<RBBINode>(rBBINode.fFirstPosSet);
        this.fLastPosSet = new HashSet<RBBINode>(rBBINode.fLastPosSet);
        this.fFollowPos = new HashSet<RBBINode>(rBBINode.fFollowPos);
    }

    static void printHex(int n, int n2) {
        String string = Integer.toString(n, 16);
        String string2 = "00000".substring(0, Math.max(0, 5 - string.length()));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(string);
        RBBINode.printString(stringBuilder.toString(), n2);
    }

    static void printInt(int n, int n2) {
        String string = Integer.toString(n);
        RBBINode.printString(string, Math.max(n2, string.length() + 1));
    }

    static void printNode(RBBINode rBBINode) {
        if (rBBINode == null) {
            System.out.print(" -- null --\n");
        } else {
            RBBINode.printInt(rBBINode.fSerialNum, 10);
            RBBINode.printString(nodeTypeNames[rBBINode.fType], 11);
            Object object = rBBINode.fParent;
            int n = 0;
            int n2 = object == null ? 0 : ((RBBINode)object).fSerialNum;
            RBBINode.printInt(n2, 11);
            object = rBBINode.fLeftChild;
            n2 = object == null ? 0 : ((RBBINode)object).fSerialNum;
            RBBINode.printInt(n2, 11);
            object = rBBINode.fRightChild;
            n2 = object == null ? n : ((RBBINode)object).fSerialNum;
            RBBINode.printInt(n2, 12);
            RBBINode.printInt(rBBINode.fFirstPos, 12);
            RBBINode.printInt(rBBINode.fVal, 7);
            if (rBBINode.fType == 2) {
                PrintStream printStream = System.out;
                object = new StringBuilder();
                ((StringBuilder)object).append(" ");
                ((StringBuilder)object).append(rBBINode.fText);
                printStream.print(((StringBuilder)object).toString());
            }
        }
        System.out.println("");
    }

    static void printString(String string, int n) {
        int n2;
        for (n2 = n; n2 < 0; ++n2) {
            System.out.print(' ');
        }
        for (n2 = string.length(); n2 < n; ++n2) {
            System.out.print(' ');
        }
        System.out.print(string);
    }

    RBBINode cloneTree() {
        RBBINode rBBINode;
        int n = this.fType;
        if (n == 2) {
            rBBINode = this.fLeftChild.cloneTree();
        } else if (n == 1) {
            rBBINode = this;
        } else {
            RBBINode rBBINode2 = new RBBINode(this);
            rBBINode = this.fLeftChild;
            if (rBBINode != null) {
                rBBINode2.fLeftChild = rBBINode.cloneTree();
                rBBINode2.fLeftChild.fParent = rBBINode2;
            }
            RBBINode rBBINode3 = this.fRightChild;
            rBBINode = rBBINode2;
            if (rBBINode3 != null) {
                rBBINode2.fRightChild = rBBINode3.cloneTree();
                rBBINode2.fRightChild.fParent = rBBINode2;
                rBBINode = rBBINode2;
            }
        }
        return rBBINode;
    }

    void findNodes(List<RBBINode> list, int n) {
        RBBINode rBBINode;
        if (this.fType == n) {
            list.add(this);
        }
        if ((rBBINode = this.fLeftChild) != null) {
            rBBINode.findNodes(list, n);
        }
        if ((rBBINode = this.fRightChild) != null) {
            rBBINode.findNodes(list, n);
        }
    }

    void flattenSets() {
        boolean bl = this.fType != 0;
        Assert.assrt(bl);
        RBBINode rBBINode = this.fLeftChild;
        if (rBBINode != null) {
            if (rBBINode.fType == 0) {
                this.fLeftChild = this.fLeftChild.fLeftChild.fLeftChild.cloneTree();
                this.fLeftChild.fParent = this;
            } else {
                rBBINode.flattenSets();
            }
        }
        if ((rBBINode = this.fRightChild) != null) {
            if (rBBINode.fType == 0) {
                this.fRightChild = this.fRightChild.fLeftChild.fLeftChild.cloneTree();
                this.fRightChild.fParent = this;
            } else {
                rBBINode.flattenSets();
            }
        }
    }

    RBBINode flattenVariables() {
        if (this.fType == 2) {
            RBBINode rBBINode = this.fLeftChild.cloneTree();
            rBBINode.fRuleRoot = this.fRuleRoot;
            rBBINode.fChainIn = this.fChainIn;
            return rBBINode;
        }
        RBBINode rBBINode = this.fLeftChild;
        if (rBBINode != null) {
            this.fLeftChild = rBBINode.flattenVariables();
            this.fLeftChild.fParent = this;
        }
        if ((rBBINode = this.fRightChild) != null) {
            this.fRightChild = rBBINode.flattenVariables();
            this.fRightChild.fParent = this;
        }
        return this;
    }

    void printTree(boolean bl) {
        if (bl) {
            System.out.println("-------------------------------------------------------------------");
            System.out.println("    Serial       type     Parent  LeftChild  RightChild    position  value");
        }
        RBBINode.printNode(this);
        if (this.fType != 2) {
            RBBINode rBBINode = this.fLeftChild;
            if (rBBINode != null) {
                rBBINode.printTree(false);
            }
            if ((rBBINode = this.fRightChild) != null) {
                rBBINode.printTree(false);
            }
        }
    }
}

