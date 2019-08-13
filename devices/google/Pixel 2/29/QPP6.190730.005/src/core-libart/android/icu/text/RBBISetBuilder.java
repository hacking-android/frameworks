/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.impl.Trie2Writable;
import android.icu.impl.Trie2_16;
import android.icu.text.RBBINode;
import android.icu.text.RBBIRuleBuilder;
import android.icu.text.UnicodeSet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

class RBBISetBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int DICT_BIT = 16384;
    Trie2_16 fFrozenTrie;
    int fGroupCount;
    RBBIRuleBuilder fRB;
    RangeDescriptor fRangeList;
    boolean fSawBOF;
    Trie2Writable fTrie;

    RBBISetBuilder(RBBIRuleBuilder rBBIRuleBuilder) {
        this.fRB = rBBIRuleBuilder;
    }

    void addValToSet(RBBINode rBBINode, int n) {
        RBBINode rBBINode2 = new RBBINode(3);
        rBBINode2.fVal = n;
        if (rBBINode.fLeftChild == null) {
            rBBINode.fLeftChild = rBBINode2;
            rBBINode2.fParent = rBBINode;
        } else {
            RBBINode rBBINode3 = new RBBINode(9);
            rBBINode3.fLeftChild = rBBINode.fLeftChild;
            rBBINode3.fRightChild = rBBINode2;
            rBBINode3.fLeftChild.fParent = rBBINode3;
            rBBINode3.fRightChild.fParent = rBBINode3;
            rBBINode.fLeftChild = rBBINode3;
            rBBINode3.fParent = rBBINode;
        }
    }

    void addValToSets(List<RBBINode> object, int n) {
        object = object.iterator();
        while (object.hasNext()) {
            this.addValToSet((RBBINode)object.next(), n);
        }
    }

    /*
     * WARNING - void declaration
     */
    void buildRanges() {
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("usets") >= 0) {
            this.printSets();
        }
        this.fRangeList = new RangeDescriptor();
        Object object = this.fRangeList;
        ((RangeDescriptor)object).fStartChar = 0;
        ((RangeDescriptor)object).fEndChar = 1114111;
        for (RBBINode rBBINode : this.fRB.fUSetNodes) {
            UnicodeSet unicodeSet = rBBINode.fInputSet;
            int n = unicodeSet.getRangeCount();
            int n2 = 0;
            object = this.fRangeList;
            while (n2 < n) {
                int n3 = unicodeSet.getRangeStart(n2);
                int n4 = unicodeSet.getRangeEnd(n2);
                while (((RangeDescriptor)object).fEndChar < n3) {
                    object = ((RangeDescriptor)object).fNext;
                }
                if (((RangeDescriptor)object).fStartChar < n3) {
                    ((RangeDescriptor)object).split(n3);
                    continue;
                }
                if (((RangeDescriptor)object).fEndChar > n4) {
                    ((RangeDescriptor)object).split(n4 + 1);
                }
                if (((RangeDescriptor)object).fIncludesSets.indexOf(rBBINode) == -1) {
                    ((RangeDescriptor)object).fIncludesSets.add(rBBINode);
                }
                n3 = n2;
                if (n4 == ((RangeDescriptor)object).fEndChar) {
                    n3 = n2 + 1;
                }
                object = ((RangeDescriptor)object).fNext;
                n2 = n3;
            }
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("range") >= 0) {
            this.printRanges();
        }
        object = this.fRangeList;
        while (object != null) {
            void var2_5;
            RangeDescriptor rangeDescriptor = this.fRangeList;
            while (var2_5 != object) {
                if (((RangeDescriptor)object).fIncludesSets.equals(var2_5.fIncludesSets)) {
                    ((RangeDescriptor)object).fNum = var2_5.fNum;
                    break;
                }
                RangeDescriptor rangeDescriptor2 = var2_5.fNext;
            }
            if (((RangeDescriptor)object).fNum == 0) {
                ++this.fGroupCount;
                ((RangeDescriptor)object).fNum = this.fGroupCount + 2;
                ((RangeDescriptor)object).setDictionaryFlag();
                this.addValToSets(((RangeDescriptor)object).fIncludesSets, this.fGroupCount + 2);
            }
            object = ((RangeDescriptor)object).fNext;
        }
        for (RBBINode rBBINode : this.fRB.fUSetNodes) {
            object = rBBINode.fInputSet;
            if (((UnicodeSet)object).contains("eof")) {
                this.addValToSet(rBBINode, 1);
            }
            if (!((UnicodeSet)object).contains("bof")) continue;
            this.addValToSet(rBBINode, 2);
            this.fSawBOF = true;
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rgroup") >= 0) {
            this.printRangeGroups();
        }
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("esets") >= 0) {
            this.printSets();
        }
    }

    void buildTrie() {
        this.fTrie = new Trie2Writable(0, 0);
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            this.fTrie.setRange(rangeDescriptor.fStartChar, rangeDescriptor.fEndChar, rangeDescriptor.fNum, true);
            rangeDescriptor = rangeDescriptor.fNext;
        }
    }

    int getFirstChar(int n) {
        int n2;
        int n3 = -1;
        RangeDescriptor rangeDescriptor = this.fRangeList;
        do {
            n2 = n3;
            if (rangeDescriptor == null) break;
            if (rangeDescriptor.fNum == n) {
                n2 = rangeDescriptor.fStartChar;
                break;
            }
            rangeDescriptor = rangeDescriptor.fNext;
        } while (true);
        return n2;
    }

    int getNumCharCategories() {
        return this.fGroupCount + 3;
    }

    int getTrieSize() {
        if (this.fFrozenTrie == null) {
            this.fFrozenTrie = this.fTrie.toTrie2_16();
            this.fTrie = null;
        }
        return this.fFrozenTrie.getSerializedLength();
    }

    void mergeCategories(RBBIRuleBuilder.IntPair intPair) {
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            int n = rangeDescriptor.fNum & -16385;
            int n2 = rangeDescriptor.fNum--;
            if (n == intPair.second) {
                rangeDescriptor.fNum = intPair.first | n2 & 16384;
            } else if (n > intPair.second) {
                // empty if block
            }
            rangeDescriptor = rangeDescriptor.fNext;
        }
        --this.fGroupCount;
    }

    void printRangeGroups() {
        int n = 0;
        System.out.print("\nRanges grouped by Unicode Set Membership...\n");
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            int n2 = rangeDescriptor.fNum & 49151;
            int n3 = n;
            if (n2 > n) {
                n3 = n2;
                if (n2 < 10) {
                    System.out.print(" ");
                }
                Object object = System.out;
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append(n2);
                ((StringBuilder)object2).append(" ");
                ((PrintStream)object).print(((StringBuilder)object2).toString());
                if ((rangeDescriptor.fNum & 16384) != 0) {
                    System.out.print(" <DICT> ");
                }
                for (n = 0; n < rangeDescriptor.fIncludesSets.size(); ++n) {
                    object2 = rangeDescriptor.fIncludesSets.get(n);
                    object = "anon";
                    RBBINode rBBINode = ((RBBINode)object2).fParent;
                    object2 = object;
                    if (rBBINode != null) {
                        rBBINode = rBBINode.fParent;
                        object2 = object;
                        if (rBBINode != null) {
                            object2 = object;
                            if (rBBINode.fType == 2) {
                                object2 = rBBINode.fText;
                            }
                        }
                    }
                    System.out.print((String)object2);
                    System.out.print(" ");
                }
                n2 = 0;
                object2 = rangeDescriptor;
                while (object2 != null) {
                    n = n2;
                    if (((RangeDescriptor)object2).fNum == rangeDescriptor.fNum) {
                        if (n2 % 5 == 0) {
                            System.out.print("\n    ");
                        }
                        RBBINode.printHex(((RangeDescriptor)object2).fStartChar, -1);
                        System.out.print("-");
                        RBBINode.printHex(((RangeDescriptor)object2).fEndChar, 0);
                        n = n2 + 1;
                    }
                    object2 = ((RangeDescriptor)object2).fNext;
                    n2 = n;
                }
                System.out.print("\n");
            }
            rangeDescriptor = rangeDescriptor.fNext;
            n = n3;
        }
        System.out.print("\n");
    }

    void printRanges() {
        System.out.print("\n\n Nonoverlapping Ranges ...\n");
        RangeDescriptor rangeDescriptor = this.fRangeList;
        while (rangeDescriptor != null) {
            Object object = System.out;
            Object object2 = new StringBuilder();
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append(rangeDescriptor.fNum);
            ((StringBuilder)object2).append("   ");
            ((StringBuilder)object2).append(rangeDescriptor.fStartChar);
            ((StringBuilder)object2).append("-");
            ((StringBuilder)object2).append(rangeDescriptor.fEndChar);
            ((PrintStream)object).print(((StringBuilder)object2).toString());
            for (int i = 0; i < rangeDescriptor.fIncludesSets.size(); ++i) {
                object2 = rangeDescriptor.fIncludesSets.get(i);
                object = "anon";
                RBBINode rBBINode = ((RBBINode)object2).fParent;
                object2 = object;
                if (rBBINode != null) {
                    rBBINode = rBBINode.fParent;
                    object2 = object;
                    if (rBBINode != null) {
                        object2 = object;
                        if (rBBINode.fType == 2) {
                            object2 = rBBINode.fText;
                        }
                    }
                }
                System.out.print((String)object2);
                System.out.print("  ");
            }
            System.out.println("");
            rangeDescriptor = rangeDescriptor.fNext;
        }
    }

    void printSets() {
        System.out.print("\n\nUnicode Sets List\n------------------\n");
        for (int i = 0; i < this.fRB.fUSetNodes.size(); ++i) {
            RBBINode rBBINode = this.fRB.fUSetNodes.get(i);
            RBBINode.printInt(2, i);
            Object object = "anonymous";
            Object object2 = rBBINode.fParent;
            Object object3 = object;
            if (object2 != null) {
                object2 = ((RBBINode)object2).fParent;
                object3 = object;
                if (object2 != null) {
                    object3 = object;
                    if (((RBBINode)object2).fType == 2) {
                        object3 = ((RBBINode)object2).fText;
                    }
                }
            }
            object = System.out;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("  ");
            ((StringBuilder)object2).append((String)object3);
            ((PrintStream)object).print(((StringBuilder)object2).toString());
            System.out.print("   ");
            System.out.print(rBBINode.fText);
            System.out.print("\n");
            if (rBBINode.fLeftChild == null) continue;
            rBBINode.fLeftChild.printTree(true);
        }
        System.out.print("\n");
    }

    boolean sawBOF() {
        return this.fSawBOF;
    }

    void serializeTrie(OutputStream outputStream) throws IOException {
        if (this.fFrozenTrie == null) {
            this.fFrozenTrie = this.fTrie.toTrie2_16();
            this.fTrie = null;
        }
        this.fFrozenTrie.serialize(outputStream);
    }

    static class RangeDescriptor {
        int fEndChar;
        List<RBBINode> fIncludesSets;
        RangeDescriptor fNext;
        int fNum;
        int fStartChar;

        RangeDescriptor() {
            this.fIncludesSets = new ArrayList<RBBINode>();
        }

        RangeDescriptor(RangeDescriptor rangeDescriptor) {
            this.fStartChar = rangeDescriptor.fStartChar;
            this.fEndChar = rangeDescriptor.fEndChar;
            this.fNum = rangeDescriptor.fNum;
            this.fIncludesSets = new ArrayList<RBBINode>(rangeDescriptor.fIncludesSets);
        }

        void setDictionaryFlag() {
            for (int i = 0; i < this.fIncludesSets.size(); ++i) {
                Object object = this.fIncludesSets.get(i);
                String string = "";
                RBBINode rBBINode = ((RBBINode)object).fParent;
                object = string;
                if (rBBINode != null) {
                    rBBINode = rBBINode.fParent;
                    object = string;
                    if (rBBINode != null) {
                        object = string;
                        if (rBBINode.fType == 2) {
                            object = rBBINode.fText;
                        }
                    }
                }
                if (!((String)object).equals("dictionary")) continue;
                this.fNum |= 16384;
                break;
            }
        }

        void split(int n) {
            boolean bl = n > this.fStartChar && n <= this.fEndChar;
            Assert.assrt(bl);
            RangeDescriptor rangeDescriptor = new RangeDescriptor(this);
            rangeDescriptor.fStartChar = n;
            this.fEndChar = n - 1;
            rangeDescriptor.fNext = this.fNext;
            this.fNext = rangeDescriptor;
        }
    }

}

