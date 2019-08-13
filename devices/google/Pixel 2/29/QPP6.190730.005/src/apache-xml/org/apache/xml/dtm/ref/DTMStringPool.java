/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import java.io.PrintStream;
import java.util.Vector;
import org.apache.xml.utils.IntVector;

public class DTMStringPool {
    static final int HASHPRIME = 101;
    public static final int NULL = -1;
    IntVector m_hashChain;
    int[] m_hashStart = new int[101];
    Vector m_intToString = new Vector();

    public DTMStringPool() {
        this(512);
    }

    public DTMStringPool(int n) {
        this.m_hashChain = new IntVector(n);
        this.removeAllElements();
        this.stringToIndex("");
    }

    public static void main(String[] object) {
        String[] arrstring = new String[]{"Zero", "One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten", "Eleven", "Twelve", "Thirteen", "Fourteen", "Fifteen", "Sixteen", "Seventeen", "Eighteen", "Nineteen", "Twenty", "Twenty-One", "Twenty-Two", "Twenty-Three", "Twenty-Four", "Twenty-Five", "Twenty-Six", "Twenty-Seven", "Twenty-Eight", "Twenty-Nine", "Thirty", "Thirty-One", "Thirty-Two", "Thirty-Three", "Thirty-Four", "Thirty-Five", "Thirty-Six", "Thirty-Seven", "Thirty-Eight", "Thirty-Nine"};
        object = new DTMStringPool();
        System.out.println("If no complaints are printed below, we passed initial test.");
        for (int i = 0; i <= 1; ++i) {
            StringBuilder stringBuilder;
            int n;
            Object object2;
            int n2;
            for (n = 0; n < arrstring.length; ++n) {
                n2 = ((DTMStringPool)object).stringToIndex(arrstring[n]);
                if (n2 == n) continue;
                object2 = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("\tMismatch populating pool: assigned ");
                stringBuilder.append(n2);
                stringBuilder.append(" for create ");
                stringBuilder.append(n);
                ((PrintStream)object2).println(stringBuilder.toString());
            }
            for (n = 0; n < arrstring.length; ++n) {
                n2 = ((DTMStringPool)object).stringToIndex(arrstring[n]);
                if (n2 == n) continue;
                object2 = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("\tMismatch in stringToIndex: returned ");
                stringBuilder.append(n2);
                stringBuilder.append(" for lookup ");
                stringBuilder.append(n);
                ((PrintStream)object2).println(stringBuilder.toString());
            }
            for (n = 0; n < arrstring.length; ++n) {
                object2 = ((DTMStringPool)object).indexToString(n);
                if (arrstring[n].equals(object2)) continue;
                PrintStream printStream = System.out;
                stringBuilder = new StringBuilder();
                stringBuilder.append("\tMismatch in indexToString: returned");
                stringBuilder.append((String)object2);
                stringBuilder.append(" for lookup ");
                stringBuilder.append(n);
                printStream.println(stringBuilder.toString());
            }
            ((DTMStringPool)object).removeAllElements();
            object2 = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append("\nPass ");
            stringBuilder.append(i);
            stringBuilder.append(" complete\n");
            ((PrintStream)object2).println(stringBuilder.toString());
        }
    }

    public String indexToString(int n) throws ArrayIndexOutOfBoundsException {
        if (n == -1) {
            return null;
        }
        return (String)this.m_intToString.elementAt(n);
    }

    public void removeAllElements() {
        this.m_intToString.removeAllElements();
        for (int i = 0; i < 101; ++i) {
            this.m_hashStart[i] = -1;
        }
        this.m_hashChain.removeAllElements();
    }

    public int stringToIndex(String string) {
        int n;
        int n2;
        if (string == null) {
            return -1;
        }
        int n3 = n = string.hashCode() % 101;
        if (n < 0) {
            n3 = -n;
        }
        n = n2 = this.m_hashStart[n3];
        while (n != -1) {
            if (this.m_intToString.elementAt(n).equals(string)) {
                return n;
            }
            n2 = n;
            n = this.m_hashChain.elementAt(n);
        }
        n = this.m_intToString.size();
        this.m_intToString.addElement(string);
        this.m_hashChain.addElement(-1);
        if (n2 == -1) {
            this.m_hashStart[n3] = n;
        } else {
            this.m_hashChain.setElementAt(n, n2);
        }
        return n;
    }
}

