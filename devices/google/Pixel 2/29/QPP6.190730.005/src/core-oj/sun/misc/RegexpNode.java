/*
 * Decompiled with CFR 0.145.
 */
package sun.misc;

import java.io.PrintStream;

class RegexpNode {
    char c;
    int depth;
    boolean exact;
    RegexpNode firstchild;
    RegexpNode nextsibling;
    String re = null;
    Object result;

    RegexpNode() {
        this.c = (char)35;
        this.depth = 0;
    }

    RegexpNode(char c, int n) {
        this.c = c;
        this.depth = n;
    }

    RegexpNode add(char c) {
        RegexpNode regexpNode;
        RegexpNode regexpNode2 = regexpNode = this.firstchild;
        if (regexpNode == null) {
            regexpNode2 = new RegexpNode(c, this.depth + 1);
        } else {
            while (regexpNode2 != null) {
                if (regexpNode2.c == c) {
                    return regexpNode2;
                }
                regexpNode2 = regexpNode2.nextsibling;
            }
            regexpNode2 = new RegexpNode(c, this.depth + 1);
            regexpNode2.nextsibling = this.firstchild;
        }
        this.firstchild = regexpNode2;
        return regexpNode2;
    }

    RegexpNode find(char c) {
        RegexpNode regexpNode = this.firstchild;
        while (regexpNode != null) {
            if (regexpNode.c == c) {
                return regexpNode;
            }
            regexpNode = regexpNode.nextsibling;
        }
        return null;
    }

    void print(PrintStream printStream) {
        if (this.nextsibling != null) {
            RegexpNode regexpNode = this;
            printStream.print("(");
            while (regexpNode != null) {
                printStream.write(regexpNode.c);
                RegexpNode regexpNode2 = regexpNode.firstchild;
                if (regexpNode2 != null) {
                    regexpNode2.print(printStream);
                }
                int n = (regexpNode = regexpNode.nextsibling) != null ? 124 : 41;
                printStream.write(n);
            }
        } else {
            printStream.write(this.c);
            RegexpNode regexpNode = this.firstchild;
            if (regexpNode != null) {
                regexpNode.print(printStream);
            }
        }
    }
}

