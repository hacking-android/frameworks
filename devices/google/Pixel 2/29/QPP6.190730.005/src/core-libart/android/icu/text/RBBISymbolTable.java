/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.lang.UCharacter;
import android.icu.text.RBBINode;
import android.icu.text.RBBIRuleScanner;
import android.icu.text.SymbolTable;
import android.icu.text.UTF16;
import android.icu.text.UnicodeMatcher;
import android.icu.text.UnicodeSet;
import java.io.PrintStream;
import java.text.ParsePosition;
import java.util.Collection;
import java.util.HashMap;

class RBBISymbolTable
implements SymbolTable {
    UnicodeSet fCachedSetLookup;
    HashMap<String, RBBISymbolTableEntry> fHashTable;
    RBBIRuleScanner fRuleScanner;
    String ffffString;

    RBBISymbolTable(RBBIRuleScanner rBBIRuleScanner) {
        this.fRuleScanner = rBBIRuleScanner;
        this.fHashTable = new HashMap();
        this.ffffString = "\uffff";
    }

    void addEntry(String string, RBBINode rBBINode) {
        if (this.fHashTable.get(string) != null) {
            this.fRuleScanner.error(66055);
            return;
        }
        RBBISymbolTableEntry rBBISymbolTableEntry = new RBBISymbolTableEntry();
        rBBISymbolTableEntry.key = string;
        rBBISymbolTableEntry.val = rBBINode;
        this.fHashTable.put(rBBISymbolTableEntry.key, rBBISymbolTableEntry);
    }

    @Override
    public char[] lookup(String object) {
        if ((object = this.fHashTable.get(object)) == null) {
            return null;
        }
        object = ((RBBISymbolTableEntry)object).val;
        while (object.fLeftChild.fType == 2) {
            object = ((RBBINode)object).fLeftChild;
        }
        object = ((RBBINode)object).fLeftChild;
        if (((RBBINode)object).fType == 0) {
            this.fCachedSetLookup = object.fLeftChild.fInputSet;
            object = this.ffffString;
        } else {
            this.fRuleScanner.error(66063);
            object = ((RBBINode)object).fText;
            this.fCachedSetLookup = null;
        }
        return ((String)object).toCharArray();
    }

    @Override
    public UnicodeMatcher lookupMatcher(int n) {
        UnicodeSet unicodeSet = null;
        if (n == 65535) {
            unicodeSet = this.fCachedSetLookup;
            this.fCachedSetLookup = null;
        }
        return unicodeSet;
    }

    RBBINode lookupNode(String object) {
        Object var2_2 = null;
        RBBISymbolTableEntry rBBISymbolTableEntry = this.fHashTable.get(object);
        object = var2_2;
        if (rBBISymbolTableEntry != null) {
            object = rBBISymbolTableEntry.val;
        }
        return object;
    }

    @Override
    public String parseReference(String string, ParsePosition parsePosition, int n) {
        int n2;
        int n3;
        int n4;
        for (n4 = n2 = parsePosition.getIndex(); n4 < n; n4 += UTF16.getCharCount((int)n3)) {
            n3 = UTF16.charAt(string, n4);
            if (n4 == n2 && !UCharacter.isUnicodeIdentifierStart(n3) || !UCharacter.isUnicodeIdentifierPart(n3)) break;
        }
        if (n4 == n2) {
            return "";
        }
        parsePosition.setIndex(n4);
        return string.substring(n2, n4);
    }

    void rbbiSymtablePrint() {
        RBBISymbolTableEntry rBBISymbolTableEntry;
        int n;
        System.out.print("Variable Definitions\nName               Node Val     String Val\n----------------------------------------------------------------------\n");
        RBBISymbolTableEntry[] arrrBBISymbolTableEntry = this.fHashTable.values().toArray(new RBBISymbolTableEntry[0]);
        for (n = 0; n < arrrBBISymbolTableEntry.length; ++n) {
            rBBISymbolTableEntry = arrrBBISymbolTableEntry[n];
            PrintStream printStream = System.out;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("  ");
            stringBuilder.append(rBBISymbolTableEntry.key);
            stringBuilder.append("  ");
            printStream.print(stringBuilder.toString());
            printStream = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append("  ");
            stringBuilder.append(rBBISymbolTableEntry.val);
            stringBuilder.append("  ");
            printStream.print(stringBuilder.toString());
            System.out.print(rBBISymbolTableEntry.val.fLeftChild.fText);
            System.out.print("\n");
        }
        System.out.println("\nParsed Variable Definitions\n");
        for (n = 0; n < arrrBBISymbolTableEntry.length; ++n) {
            rBBISymbolTableEntry = arrrBBISymbolTableEntry[n];
            System.out.print(rBBISymbolTableEntry.key);
            rBBISymbolTableEntry.val.fLeftChild.printTree(true);
            System.out.print("\n");
        }
    }

    static class RBBISymbolTableEntry {
        String key;
        RBBINode val;

        RBBISymbolTableEntry() {
        }
    }

}

