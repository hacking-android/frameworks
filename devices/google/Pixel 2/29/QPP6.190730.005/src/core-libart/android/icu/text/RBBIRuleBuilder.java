/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.impl.ICUBinary;
import android.icu.impl.ICUDebug;
import android.icu.impl.RBBIDataWrapper;
import android.icu.text.RBBINode;
import android.icu.text.RBBIRuleScanner;
import android.icu.text.RBBISetBuilder;
import android.icu.text.RBBITableBuilder;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

class RBBIRuleBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int U_BRK_ASSIGN_ERROR = 66054;
    static final int U_BRK_ERROR_LIMIT = 66064;
    static final int U_BRK_ERROR_START = 66048;
    static final int U_BRK_HEX_DIGITS_EXPECTED = 66050;
    static final int U_BRK_INIT_ERROR = 66059;
    static final int U_BRK_INTERNAL_ERROR = 66049;
    static final int U_BRK_MALFORMED_RULE_TAG = 66062;
    static final int U_BRK_MALFORMED_SET = 66063;
    static final int U_BRK_MISMATCHED_PAREN = 66056;
    static final int U_BRK_NEW_LINE_IN_QUOTED_STRING = 66057;
    static final int U_BRK_RULE_EMPTY_SET = 66060;
    static final int U_BRK_RULE_SYNTAX = 66052;
    static final int U_BRK_SEMICOLON_EXPECTED = 66051;
    static final int U_BRK_UNCLOSED_SET = 66053;
    static final int U_BRK_UNDEFINED_VARIABLE = 66058;
    static final int U_BRK_UNRECOGNIZED_OPTION = 66061;
    static final int U_BRK_VARIABLE_REDFINITION = 66055;
    static final int fForwardTree = 0;
    static final int fReverseTree = 1;
    static final int fSafeFwdTree = 2;
    static final int fSafeRevTree = 3;
    boolean fChainRules;
    String fDebugEnv;
    int fDefaultTree = 0;
    RBBITableBuilder fForwardTable;
    boolean fLBCMNoChain;
    boolean fLookAheadHardBreak;
    List<Integer> fRuleStatusVals;
    String fRules;
    RBBIRuleScanner fScanner;
    RBBISetBuilder fSetBuilder;
    Map<Set<Integer>, Integer> fStatusSets = new HashMap<Set<Integer>, Integer>();
    StringBuilder fStrippedRules;
    RBBINode[] fTreeRoots = new RBBINode[4];
    List<RBBINode> fUSetNodes;

    RBBIRuleBuilder(String string) {
        String string2 = ICUDebug.enabled("rbbi") ? ICUDebug.value("rbbi") : null;
        this.fDebugEnv = string2;
        this.fRules = string;
        this.fStrippedRules = new StringBuilder(string);
        this.fUSetNodes = new ArrayList<RBBINode>();
        this.fRuleStatusVals = new ArrayList<Integer>();
        this.fScanner = new RBBIRuleScanner(this);
        this.fSetBuilder = new RBBISetBuilder(this);
    }

    static final int align8(int n) {
        return n + 7 & -8;
    }

    static void compileRules(String string, OutputStream outputStream) throws IOException {
        new RBBIRuleBuilder(string).build(outputStream);
    }

    void build(OutputStream outputStream) throws IOException {
        this.fScanner.parse();
        this.fSetBuilder.buildRanges();
        this.fForwardTable = new RBBITableBuilder(this, 0);
        this.fForwardTable.buildForwardTable();
        this.optimizeTables();
        this.fForwardTable.buildSafeReverseTable();
        String string = this.fDebugEnv;
        if (string != null && string.indexOf("states") >= 0) {
            this.fForwardTable.printStates();
            this.fForwardTable.printRuleStatusTable();
            this.fForwardTable.printReverseTable();
        }
        this.fSetBuilder.buildTrie();
        this.flattenData(outputStream);
    }

    void flattenData(OutputStream object) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream((OutputStream)object);
        String string = RBBIRuleScanner.stripRules(this.fStrippedRules.toString());
        int n = RBBIRuleBuilder.align8(this.fForwardTable.getTableSize());
        int n2 = RBBIRuleBuilder.align8(this.fForwardTable.getSafeTableSize());
        int n3 = RBBIRuleBuilder.align8(this.fSetBuilder.getTrieSize());
        int n4 = RBBIRuleBuilder.align8(this.fRuleStatusVals.size() * 4);
        int n5 = RBBIRuleBuilder.align8(string.length() * 2);
        int n6 = 0;
        ICUBinary.writeHeader(1114794784, 83886080, 0, dataOutputStream);
        int[] arrn = new int[20];
        arrn[0] = 45472;
        boolean bl = true;
        arrn[1] = 83886080;
        arrn[2] = 80 + n + n2 + n4 + n3 + n5;
        arrn[3] = this.fSetBuilder.getNumCharCategories();
        arrn[4] = 80;
        arrn[5] = n;
        arrn[6] = arrn[4] + n;
        arrn[7] = n2;
        arrn[8] = arrn[6] + arrn[7];
        arrn[9] = this.fSetBuilder.getTrieSize();
        arrn[12] = arrn[8] + arrn[9];
        arrn[13] = n4;
        arrn[10] = arrn[12] + n4;
        arrn[11] = string.length() * 2;
        for (n = 0; n < arrn.length; ++n) {
            dataOutputStream.writeInt(arrn[n]);
            n6 += 4;
        }
        RBBIDataWrapper.RBBIStateTable rBBIStateTable = this.fForwardTable.exportTable();
        n = n6 + rBBIStateTable.put(dataOutputStream);
        rBBIStateTable = this.fForwardTable.exportSafeTable();
        boolean bl2 = n == arrn[6];
        Assert.assrt(bl2);
        bl2 = (n += rBBIStateTable.put(dataOutputStream)) == arrn[8];
        Assert.assrt(bl2);
        this.fSetBuilder.serializeTrie((OutputStream)object);
        n += arrn[9];
        while (n % 8 != 0) {
            dataOutputStream.write(0);
            ++n;
        }
        bl2 = n == arrn[12];
        Assert.assrt(bl2);
        object = this.fRuleStatusVals.iterator();
        do {
            n6 = n;
            if (!object.hasNext()) break;
            dataOutputStream.writeInt((Integer)object.next());
            n += 4;
        } while (true);
        while (n6 % 8 != 0) {
            dataOutputStream.write(0);
            ++n6;
        }
        bl2 = n6 == arrn[10] ? bl : false;
        Assert.assrt(bl2);
        dataOutputStream.writeChars(string);
        n = n6 + string.length() * 2;
        while (n % 8 != 0) {
            dataOutputStream.write(0);
            ++n;
        }
    }

    void optimizeTables() {
        boolean bl;
        do {
            bl = false;
            IntPair intPair = new IntPair(3, 0);
            while (this.fForwardTable.findDuplCharClassFrom(intPair)) {
                this.fSetBuilder.mergeCategories(intPair);
                this.fForwardTable.removeColumn(intPair.second);
                bl = true;
            }
            while (this.fForwardTable.removeDuplicateStates() > 0) {
                bl = true;
            }
        } while (bl);
    }

    static class IntPair {
        int first = 0;
        int second = 0;

        IntPair() {
        }

        IntPair(int n, int n2) {
            this.first = n;
            this.second = n2;
        }
    }

}

