/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.impl.Utility;
import android.icu.lang.UCharacter;
import android.icu.text.RBBINode;
import android.icu.text.RBBIRuleBuilder;
import android.icu.text.RBBIRuleParseTable;
import android.icu.text.RBBISymbolTable;
import android.icu.text.SymbolTable;
import android.icu.text.UTF16;
import android.icu.text.UnicodeSet;
import java.io.PrintStream;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.List;

class RBBIRuleScanner {
    static final int chLS = 8232;
    static final int chNEL = 133;
    private static String gRuleSet_digit_char_pattern;
    private static String gRuleSet_name_char_pattern;
    private static String gRuleSet_name_start_char_pattern;
    private static String gRuleSet_rule_char_pattern;
    private static String gRuleSet_white_space_pattern;
    private static String kAny;
    private static final int kStackSize = 100;
    RBBIRuleChar fC = new RBBIRuleChar();
    int fCharNum;
    int fLastChar;
    int fLineNum;
    boolean fLookAheadRule;
    int fNextIndex;
    boolean fNoChainInRule;
    RBBINode[] fNodeStack = new RBBINode[100];
    int fNodeStackPtr;
    int fOptionStart;
    boolean fQuoteMode;
    RBBIRuleBuilder fRB;
    boolean fReverseRule;
    int fRuleNum;
    UnicodeSet[] fRuleSets = new UnicodeSet[10];
    int fScanIndex;
    HashMap<String, RBBISetTableEl> fSetTable = new HashMap();
    short[] fStack = new short[100];
    int fStackPtr;
    RBBISymbolTable fSymbolTable;

    static {
        gRuleSet_rule_char_pattern = "[^[\\p{Z}\\u0020-\\u007f]-[\\p{L}]-[\\p{N}]]";
        gRuleSet_name_char_pattern = "[_\\p{L}\\p{N}]";
        gRuleSet_digit_char_pattern = "[0-9]";
        gRuleSet_name_start_char_pattern = "[_\\p{L}]";
        gRuleSet_white_space_pattern = "[\\p{Pattern_White_Space}]";
        kAny = "any";
    }

    RBBIRuleScanner(RBBIRuleBuilder rBBIRuleBuilder) {
        this.fRB = rBBIRuleBuilder;
        this.fLineNum = 1;
        this.fRuleSets[3] = new UnicodeSet(gRuleSet_rule_char_pattern);
        this.fRuleSets[4] = new UnicodeSet(gRuleSet_white_space_pattern);
        this.fRuleSets[1] = new UnicodeSet(gRuleSet_name_char_pattern);
        this.fRuleSets[2] = new UnicodeSet(gRuleSet_name_start_char_pattern);
        this.fRuleSets[0] = new UnicodeSet(gRuleSet_digit_char_pattern);
        this.fSymbolTable = new RBBISymbolTable(this);
    }

    static String stripRules(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = string.length();
        boolean bl = false;
        int n2 = 0;
        while (n2 < n) {
            int n3 = string.codePointAt(n2);
            boolean bl2 = UCharacter.hasBinaryProperty(n3, 43);
            if (!bl || !bl2) {
                stringBuilder.appendCodePoint(n3);
                bl = bl2;
            }
            n2 = string.offsetByCodePoints(n2, 1);
        }
        return stringBuilder.toString();
    }

    boolean doParseActions(int n) {
        boolean bl;
        boolean bl2 = true;
        int n2 = 3;
        switch (n) {
            default: {
                this.error(66049);
                bl = false;
                break;
            }
            case 32: {
                this.error(66052);
                bl = bl2;
                break;
            }
            case 31: {
                Object object = this.fNodeStack;
                n = this.fNodeStackPtr;
                this.fNodeStackPtr = n - 1;
                RBBINode rBBINode = object[n];
                object = this.pushNewNode(10);
                object.fLeftChild = rBBINode;
                rBBINode.fParent = object;
                bl = bl2;
                break;
            }
            case 30: {
                Object object = this.fNodeStack;
                n = this.fNodeStackPtr;
                this.fNodeStackPtr = n - 1;
                object = object[n];
                RBBINode rBBINode = this.pushNewNode(12);
                rBBINode.fLeftChild = object;
                object.fParent = rBBINode;
                bl = bl2;
                break;
            }
            case 29: {
                Object object = this.fNodeStack;
                n = this.fNodeStackPtr;
                this.fNodeStackPtr = n - 1;
                object = object[n];
                RBBINode rBBINode = this.pushNewNode(11);
                rBBINode.fLeftChild = object;
                object.fParent = rBBINode;
                bl = bl2;
                break;
            }
            case 28: {
                RBBINode rBBINode = this.fNodeStack[this.fNodeStackPtr];
                rBBINode.fLastPos = this.fNextIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
                bl = bl2;
                break;
            }
            case 27: {
                this.error(66062);
                bl = false;
                break;
            }
            case 26: {
                RBBINode rBBINode = this.fNodeStack[this.fNodeStackPtr];
                n = UCharacter.digit((char)this.fC.fChar, 10);
                rBBINode.fVal = rBBINode.fVal * 10 + n;
                bl = bl2;
                break;
            }
            case 25: {
                this.pushNewNode((int)2).fFirstPos = this.fScanIndex;
                bl = bl2;
                break;
            }
            case 24: {
                RBBINode rBBINode = this.pushNewNode(5);
                rBBINode.fVal = 0;
                rBBINode.fFirstPos = this.fScanIndex;
                rBBINode.fLastPos = this.fNextIndex;
                bl = bl2;
                break;
            }
            case 23: {
                this.fNodeStack[this.fNodeStackPtr - 1].fFirstPos = this.fNextIndex;
                this.pushNewNode(7);
                bl = bl2;
                break;
            }
            case 22: {
                RBBINode rBBINode = this.pushNewNode(4);
                rBBINode.fVal = this.fRuleNum;
                rBBINode.fFirstPos = this.fScanIndex;
                rBBINode.fLastPos = this.fNextIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
                this.fLookAheadRule = true;
                bl = bl2;
                break;
            }
            case 21: {
                this.scanSet();
                bl = bl2;
                break;
            }
            case 20: {
                this.error(66054);
                bl = false;
                break;
            }
            case 19: {
                this.error(66052);
                bl = false;
                break;
            }
            case 18: {
                RBBINode rBBINode = this.pushNewNode(0);
                this.findSetFor(String.valueOf((char)this.fC.fChar), rBBINode, null);
                rBBINode.fFirstPos = this.fScanIndex;
                rBBINode.fLastPos = this.fNextIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
                bl = bl2;
                break;
            }
            case 17: {
                this.fReverseRule = true;
                bl = bl2;
                break;
            }
            case 16: {
                this.fOptionStart = this.fScanIndex;
                bl = bl2;
                break;
            }
            case 15: {
                String string = this.fRB.fRules.substring(this.fOptionStart, this.fScanIndex);
                if (string.equals("chain")) {
                    this.fRB.fChainRules = true;
                    bl = bl2;
                    break;
                }
                if (string.equals("LBCMNoChain")) {
                    this.fRB.fLBCMNoChain = true;
                    bl = bl2;
                    break;
                }
                if (string.equals("forward")) {
                    this.fRB.fDefaultTree = 0;
                    bl = bl2;
                    break;
                }
                if (string.equals("reverse")) {
                    this.fRB.fDefaultTree = 1;
                    bl = bl2;
                    break;
                }
                if (string.equals("safe_forward")) {
                    this.fRB.fDefaultTree = 2;
                    bl = bl2;
                    break;
                }
                if (string.equals("safe_reverse")) {
                    this.fRB.fDefaultTree = 3;
                    bl = bl2;
                    break;
                }
                if (string.equals("lookAheadHardBreak")) {
                    this.fRB.fLookAheadHardBreak = true;
                    bl = bl2;
                    break;
                }
                if (string.equals("quoted_literals_only")) {
                    this.fRuleSets[3].clear();
                    bl = bl2;
                    break;
                }
                if (string.equals("unquoted_literals")) {
                    this.fRuleSets[3].applyPattern(gRuleSet_rule_char_pattern);
                    bl = bl2;
                    break;
                }
                this.error(66061);
                bl = bl2;
                break;
            }
            case 14: {
                this.fNoChainInRule = true;
                bl = bl2;
                break;
            }
            case 13: {
                bl = bl2;
                break;
            }
            case 12: {
                this.pushNewNode(15);
                bl = bl2;
                break;
            }
            case 11: {
                this.pushNewNode(7);
                ++this.fRuleNum;
                bl = bl2;
                break;
            }
            case 10: {
                this.fixOpStack(2);
                bl = bl2;
                break;
            }
            case 9: {
                this.fixOpStack(4);
                Object object = this.fNodeStack;
                n = this.fNodeStackPtr;
                this.fNodeStackPtr = n - 1;
                object = object[n];
                RBBINode rBBINode = this.pushNewNode(9);
                rBBINode.fLeftChild = object;
                object.fParent = rBBINode;
                bl = bl2;
                break;
            }
            case 8: {
                bl = bl2;
                break;
            }
            case 7: {
                this.fixOpStack(4);
                Object object = this.fNodeStack;
                n = this.fNodeStackPtr;
                this.fNodeStackPtr = n - 1;
                object = object[n];
                RBBINode rBBINode = this.pushNewNode(8);
                rBBINode.fLeftChild = object;
                object.fParent = rBBINode;
                bl = bl2;
                break;
            }
            case 6: {
                bl = false;
                break;
            }
            case 5: {
                RBBINode rBBINode = this.fNodeStack[this.fNodeStackPtr];
                if (rBBINode != null && rBBINode.fType == 2) {
                    rBBINode.fLastPos = this.fScanIndex;
                    rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos + 1, rBBINode.fLastPos);
                    rBBINode.fLeftChild = this.fSymbolTable.lookupNode(rBBINode.fText);
                    bl = bl2;
                    break;
                }
                this.error(66049);
                bl = bl2;
                break;
            }
            case 4: {
                RBBINode rBBINode;
                RBBINode rBBINode2;
                this.fixOpStack(1);
                if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("rtree") >= 0) {
                    this.printNodeStack("end of rule");
                }
                bl = this.fNodeStackPtr == 1;
                Assert.assrt(bl);
                RBBINode rBBINode3 = rBBINode2 = this.fNodeStack[this.fNodeStackPtr];
                if (this.fLookAheadRule) {
                    rBBINode = this.pushNewNode(6);
                    rBBINode3 = this.pushNewNode(8);
                    this.fNodeStackPtr -= 2;
                    rBBINode3.fLeftChild = rBBINode2;
                    rBBINode3.fRightChild = rBBINode;
                    this.fNodeStack[this.fNodeStackPtr] = rBBINode3;
                    rBBINode.fVal = this.fRuleNum;
                    rBBINode.fLookAheadEnd = true;
                }
                rBBINode3.fRuleRoot = true;
                if (this.fRB.fChainRules && !this.fNoChainInRule) {
                    rBBINode3.fChainIn = true;
                }
                if (this.fRB.fTreeRoots[n = this.fReverseRule ? n2 : this.fRB.fDefaultTree] != null) {
                    rBBINode3 = this.fNodeStack[this.fNodeStackPtr];
                    rBBINode2 = this.fRB.fTreeRoots[n];
                    rBBINode = this.pushNewNode(9);
                    rBBINode.fLeftChild = rBBINode2;
                    rBBINode2.fParent = rBBINode;
                    rBBINode.fRightChild = rBBINode3;
                    rBBINode3.fParent = rBBINode;
                    this.fRB.fTreeRoots[n] = rBBINode;
                } else {
                    this.fRB.fTreeRoots[n] = this.fNodeStack[this.fNodeStackPtr];
                }
                this.fReverseRule = false;
                this.fLookAheadRule = false;
                this.fNoChainInRule = false;
                this.fNodeStackPtr = 0;
                bl = bl2;
                break;
            }
            case 3: {
                this.fixOpStack(1);
                Object object = this.fNodeStack;
                n = this.fNodeStackPtr;
                RBBINode rBBINode = object[n - 2];
                RBBINode rBBINode4 = object[n - 1];
                object = object[n];
                object.fFirstPos = rBBINode.fFirstPos;
                object.fLastPos = this.fScanIndex;
                object.fText = this.fRB.fRules.substring(object.fFirstPos, object.fLastPos);
                rBBINode4.fLeftChild = object;
                object.fParent = rBBINode4;
                this.fSymbolTable.addEntry(rBBINode4.fText, rBBINode4);
                this.fNodeStackPtr -= 3;
                bl = bl2;
                break;
            }
            case 2: {
                RBBINode rBBINode = this.pushNewNode(0);
                this.findSetFor(kAny, rBBINode, null);
                rBBINode.fFirstPos = this.fScanIndex;
                rBBINode.fLastPos = this.fNextIndex;
                rBBINode.fText = this.fRB.fRules.substring(rBBINode.fFirstPos, rBBINode.fLastPos);
                bl = bl2;
                break;
            }
            case 1: {
                bl = bl2;
                if (this.fNodeStack[this.fNodeStackPtr].fLeftChild != null) break;
                this.error(66058);
                bl = false;
            }
        }
        return bl;
    }

    void error(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Error ");
        stringBuilder.append(n);
        stringBuilder.append(" at line ");
        stringBuilder.append(this.fLineNum);
        stringBuilder.append(" column ");
        stringBuilder.append(this.fCharNum);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    void findSetFor(String string, RBBINode object, UnicodeSet object2) {
        Object object3 = this.fSetTable.get(string);
        boolean bl = false;
        if (object3 != null) {
            ((RBBINode)object).fLeftChild = ((RBBISetTableEl)object3).val;
            if (object.fLeftChild.fType == 1) {
                bl = true;
            }
            Assert.assrt(bl);
            return;
        }
        object3 = object2;
        if (object2 == null) {
            if (string.equals(kAny)) {
                object3 = new UnicodeSet(0, 1114111);
            } else {
                int n = UTF16.charAt(string, 0);
                object3 = new UnicodeSet(n, n);
            }
        }
        object2 = new RBBINode(1);
        ((RBBINode)object2).fInputSet = object3;
        ((RBBINode)object2).fParent = object;
        ((RBBINode)object).fLeftChild = object2;
        ((RBBINode)object2).fText = string;
        this.fRB.fUSetNodes.add((RBBINode)object2);
        object = new RBBISetTableEl();
        ((RBBISetTableEl)object).key = string;
        ((RBBISetTableEl)object).val = object2;
        this.fSetTable.put(((RBBISetTableEl)object).key, (RBBISetTableEl)object);
    }

    void fixOpStack(int n) {
        RBBINode[] arrrBBINode;
        RBBINode rBBINode;
        do {
            rBBINode = this.fNodeStack[this.fNodeStackPtr - 1];
            if (rBBINode.fPrecedence == 0) {
                System.out.print("RBBIRuleScanner.fixOpStack, bad operator node");
                this.error(66049);
                return;
            }
            if (rBBINode.fPrecedence < n || rBBINode.fPrecedence <= 2) break;
            arrrBBINode = this.fNodeStack;
            int n2 = this.fNodeStackPtr;
            rBBINode.fRightChild = arrrBBINode[n2];
            arrrBBINode[n2].fParent = rBBINode;
            this.fNodeStackPtr = n2 - 1;
        } while (true);
        if (n <= 2) {
            if (rBBINode.fPrecedence != n) {
                this.error(66056);
            }
            arrrBBINode = this.fNodeStack;
            n = this.fNodeStackPtr;
            arrrBBINode[n - 1] = arrrBBINode[n];
            this.fNodeStackPtr = n - 1;
        }
    }

    void nextChar(RBBIRuleChar rBBIRuleChar) {
        this.fScanIndex = this.fNextIndex;
        rBBIRuleChar.fChar = this.nextCharLL();
        rBBIRuleChar.fEscaped = false;
        if (rBBIRuleChar.fChar == 39) {
            if (UTF16.charAt(this.fRB.fRules, this.fNextIndex) == 39) {
                rBBIRuleChar.fChar = this.nextCharLL();
                rBBIRuleChar.fEscaped = true;
            } else {
                this.fQuoteMode ^= true;
                rBBIRuleChar.fChar = this.fQuoteMode ? 40 : 41;
                rBBIRuleChar.fEscaped = false;
                return;
            }
        }
        if (this.fQuoteMode) {
            rBBIRuleChar.fEscaped = true;
        } else {
            if (rBBIRuleChar.fChar == 35) {
                int n = this.fScanIndex;
                do {
                    rBBIRuleChar.fChar = this.nextCharLL();
                } while (rBBIRuleChar.fChar != -1 && rBBIRuleChar.fChar != 13 && rBBIRuleChar.fChar != 10 && rBBIRuleChar.fChar != 133 && rBBIRuleChar.fChar != 8232);
                while (n < this.fNextIndex - 1) {
                    this.fRB.fStrippedRules.setCharAt(n, ' ');
                    ++n;
                }
            }
            if (rBBIRuleChar.fChar == -1) {
                return;
            }
            if (rBBIRuleChar.fChar == 92) {
                rBBIRuleChar.fEscaped = true;
                int[] arrn = new int[]{this.fNextIndex};
                rBBIRuleChar.fChar = Utility.unescapeAt(this.fRB.fRules, arrn);
                if (arrn[0] == this.fNextIndex) {
                    this.error(66050);
                }
                this.fCharNum += arrn[0] - this.fNextIndex;
                this.fNextIndex = arrn[0];
            }
        }
    }

    int nextCharLL() {
        if (this.fNextIndex >= this.fRB.fRules.length()) {
            return -1;
        }
        int n = UTF16.charAt(this.fRB.fRules, this.fNextIndex);
        this.fNextIndex = UTF16.moveCodePointOffset(this.fRB.fRules, this.fNextIndex, 1);
        if (n != 13 && n != 133 && n != 8232 && (n != 10 || this.fLastChar == 13)) {
            if (n != 10) {
                ++this.fCharNum;
            }
        } else {
            ++this.fLineNum;
            this.fCharNum = 0;
            if (this.fQuoteMode) {
                this.error(66057);
                this.fQuoteMode = false;
            }
        }
        this.fLastChar = n;
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    void parse() {
        var1_1 = 1;
        this.nextChar(this.fC);
        block0 : do {
            if (var1_1 == 0) ** GOTO lbl34
            var2_2 = RBBIRuleParseTable.gRuleParseStateTable[var1_1];
            if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                var3_3 = System.out;
                var4_4 = new StringBuilder();
                var4_4.append("char, line, col = ('");
                var4_4.append((char)this.fC.fChar);
                var4_4.append("', ");
                var4_4.append(this.fLineNum);
                var4_4.append(", ");
                var4_4.append(this.fCharNum);
                var4_4.append("    state = ");
                var4_4.append(var2_2.fStateName);
                var3_3.println(var4_4.toString());
            }
            do {
                block17 : {
                    block18 : {
                        var2_2 = RBBIRuleParseTable.gRuleParseStateTable[var1_1];
                        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                            System.out.print(".");
                        }
                        if (!(var2_2.fCharClass < 127 && this.fC.fEscaped == false && var2_2.fCharClass == this.fC.fChar || var2_2.fCharClass == 255 || var2_2.fCharClass == 254 && this.fC.fEscaped != false || var2_2.fCharClass == 253 && this.fC.fEscaped != false && (this.fC.fChar == 80 || this.fC.fChar == 112) || var2_2.fCharClass == 252 && this.fC.fChar == -1) && (var2_2.fCharClass < 128 || var2_2.fCharClass >= 240 || this.fC.fEscaped || this.fC.fChar == -1 || !this.fRuleSets[var2_2.fCharClass - 128].contains(this.fC.fChar))) break block17;
                        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("scan") >= 0) {
                            System.out.println("");
                        }
                        if (this.doParseActions(var2_2.fAction)) break block18;
lbl34: // 2 sources:
                        if (this.fRB.fTreeRoots[0] == null) {
                            this.error(66052);
                        }
                        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("symbols") >= 0) {
                            this.fSymbolTable.rbbiSymtablePrint();
                        }
                        if (this.fRB.fDebugEnv == null) return;
                        if (this.fRB.fDebugEnv.indexOf("ptree") < 0) return;
                        System.out.println("Completed Forward Rules Parse Tree...");
                        this.fRB.fTreeRoots[0].printTree(true);
                        System.out.println("\nCompleted Reverse Rules Parse Tree...");
                        this.fRB.fTreeRoots[1].printTree(true);
                        System.out.println("\nCompleted Safe Point Forward Rules Parse Tree...");
                        if (this.fRB.fTreeRoots[2] == null) {
                            System.out.println("  -- null -- ");
                        } else {
                            this.fRB.fTreeRoots[2].printTree(true);
                        }
                        System.out.println("\nCompleted Safe Point Reverse Rules Parse Tree...");
                        if (this.fRB.fTreeRoots[3] == null) {
                            System.out.println("  -- null -- ");
                            return;
                        }
                        this.fRB.fTreeRoots[3].printTree(true);
                        return;
                    }
                    if (var2_2.fPushState != 0) {
                        ++this.fStackPtr;
                        if (this.fStackPtr >= 100) {
                            System.out.println("RBBIRuleScanner.parse() - state stack overflow.");
                            this.error(66049);
                        }
                        this.fStack[this.fStackPtr] = var2_2.fPushState;
                    }
                    if (var2_2.fNextChar) {
                        this.nextChar(this.fC);
                    }
                    if (var2_2.fNextState != 255) {
                        var1_1 = var2_2.fNextState;
                        continue block0;
                    }
                    var2_2 = this.fStack;
                    var1_1 = this.fStackPtr;
                    var5_5 = var2_2[var1_1];
                    this.fStackPtr = var1_1 - 1;
                    var1_1 = var5_5;
                    if (this.fStackPtr >= 0) continue block0;
                    System.out.println("RBBIRuleScanner.parse() - state stack underflow.");
                    this.error(66049);
                    var1_1 = var5_5;
                    continue block0;
                }
                ++var1_1;
            } while (true);
            break;
        } while (true);
    }

    void printNodeStack(String string) {
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".  Dumping node stack...\n");
        printStream.println(stringBuilder.toString());
        for (int i = this.fNodeStackPtr; i > 0; --i) {
            this.fNodeStack[i].printTree(true);
        }
    }

    RBBINode pushNewNode(int n) {
        ++this.fNodeStackPtr;
        if (this.fNodeStackPtr >= 100) {
            System.out.println("RBBIRuleScanner.pushNewNode - stack overflow.");
            this.error(66049);
        }
        this.fNodeStack[this.fNodeStackPtr] = new RBBINode(n);
        return this.fNodeStack[this.fNodeStackPtr];
    }

    void scanSet() {
        Object object;
        UnicodeSet unicodeSet = null;
        ParsePosition parsePosition = new ParsePosition(this.fScanIndex);
        int n = this.fScanIndex;
        try {
            object = new UnicodeSet(this.fRB.fRules, parsePosition, this.fSymbolTable, 1);
            unicodeSet = object;
        }
        catch (Exception exception) {
            this.error(66063);
        }
        if (unicodeSet.isEmpty()) {
            this.error(66060);
        }
        int n2 = parsePosition.getIndex();
        do {
            if (this.fNextIndex >= n2) {
                object = this.pushNewNode(0);
                ((RBBINode)object).fFirstPos = n;
                ((RBBINode)object).fLastPos = this.fNextIndex;
                ((RBBINode)object).fText = this.fRB.fRules.substring(((RBBINode)object).fFirstPos, ((RBBINode)object).fLastPos);
                this.findSetFor(((RBBINode)object).fText, (RBBINode)object, unicodeSet);
                return;
            }
            this.nextCharLL();
        } while (true);
    }

    static class RBBIRuleChar {
        int fChar;
        boolean fEscaped;

        RBBIRuleChar() {
        }
    }

    static class RBBISetTableEl {
        String key;
        RBBINode val;

        RBBISetTableEl() {
        }
    }

}

