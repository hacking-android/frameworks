/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.GeneralName;
import sun.security.x509.GeneralNameInterface;
import sun.security.x509.GeneralSubtree;

public class GeneralSubtrees
implements Cloneable {
    private static final int NAME_DIFF_TYPE = -1;
    private static final int NAME_MATCH = 0;
    private static final int NAME_NARROWS = 1;
    private static final int NAME_SAME_TYPE = 3;
    private static final int NAME_WIDENS = 2;
    private final List<GeneralSubtree> trees;

    public GeneralSubtrees() {
        this.trees = new ArrayList<GeneralSubtree>();
    }

    public GeneralSubtrees(DerValue derValue) throws IOException {
        this();
        if (derValue.tag == 48) {
            while (derValue.data.available() != 0) {
                this.add(new GeneralSubtree(derValue.data.getDerValue()));
            }
            return;
        }
        throw new IOException("Invalid encoding of GeneralSubtrees.");
    }

    private GeneralSubtrees(GeneralSubtrees generalSubtrees) {
        this.trees = new ArrayList<GeneralSubtree>(generalSubtrees.trees);
    }

    /*
     * Exception decompiling
     */
    private GeneralSubtree createWidestSubtree(GeneralNameInterface var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CASE]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private GeneralNameInterface getGeneralNameInterface(int n) {
        return GeneralSubtrees.getGeneralNameInterface(this.get(n));
    }

    private static GeneralNameInterface getGeneralNameInterface(GeneralSubtree generalSubtree) {
        return generalSubtree.getName().getName();
    }

    private void minimize() {
        int n = 0;
        while (n < this.size() - 1) {
            int n2;
            GeneralNameInterface generalNameInterface = this.getGeneralNameInterface(n);
            int n3 = 0;
            int n4 = 0;
            int n5 = n + 1;
            do {
                block3 : {
                    block7 : {
                        block4 : {
                            block5 : {
                                block6 : {
                                    n2 = n3;
                                    if (n5 >= this.size()) break;
                                    n2 = generalNameInterface.constrains(this.getGeneralNameInterface(n5));
                                    if (n2 == -1) break block3;
                                    if (n2 == 0) break block4;
                                    if (n2 == 1) break block5;
                                    if (n2 == 2) break block6;
                                    if (n2 == 3) break block3;
                                    n5 = n4;
                                    break block7;
                                }
                                n5 = 1;
                                break block7;
                            }
                            this.remove(n5);
                            --n5;
                            break block3;
                        }
                        n5 = 1;
                    }
                    n2 = n5;
                    break;
                }
                ++n5;
            } while (true);
            n5 = n;
            if (n2 != 0) {
                this.remove(n);
                n5 = n - 1;
            }
            n = n5 + 1;
        }
    }

    public void add(GeneralSubtree generalSubtree) {
        if (generalSubtree != null) {
            this.trees.add(generalSubtree);
            return;
        }
        throw new NullPointerException();
    }

    public Object clone() {
        return new GeneralSubtrees(this);
    }

    public boolean contains(GeneralSubtree generalSubtree) {
        if (generalSubtree != null) {
            return this.trees.contains(generalSubtree);
        }
        throw new NullPointerException();
    }

    public void encode(DerOutputStream derOutputStream) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            this.get(i).encode(derOutputStream2);
        }
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof GeneralSubtrees)) {
            return false;
        }
        object = (GeneralSubtrees)object;
        return this.trees.equals(((GeneralSubtrees)object).trees);
    }

    public GeneralSubtree get(int n) {
        return this.trees.get(n);
    }

    public int hashCode() {
        return this.trees.hashCode();
    }

    public GeneralSubtrees intersect(GeneralSubtrees generalSubtrees) {
        block21 : {
            Object object;
            int n;
            int n2;
            int n3;
            Object object2;
            int n4;
            if (generalSubtrees == null) break block21;
            GeneralSubtrees generalSubtrees2 = new GeneralSubtrees();
            GeneralSubtree generalSubtree = null;
            if (this.size() == 0) {
                this.union(generalSubtrees);
                return null;
            }
            this.minimize();
            generalSubtrees.minimize();
            for (n2 = 0; n2 < this.size(); ++n2) {
                GeneralNameInterface generalNameInterface;
                int n5;
                block18 : {
                    block19 : {
                        generalNameInterface = this.getGeneralNameInterface(n2);
                        n3 = 0;
                        n5 = 0;
                        do {
                            n = n2;
                            n4 = n3;
                            if (n5 >= generalSubtrees.size()) break block18;
                            object = generalSubtrees.get(n5);
                            n = generalNameInterface.constrains(GeneralSubtrees.getGeneralNameInterface((GeneralSubtree)object));
                            if (n == 0) break block19;
                            if (n == 1) break;
                            if (n != 2) {
                                if (n == 3) {
                                    n3 = 1;
                                }
                                ++n5;
                                continue;
                            }
                            break block19;
                            break;
                        } while (true);
                        this.remove(n2);
                        n = n2 - 1;
                        generalSubtrees2.add((GeneralSubtree)object);
                        n4 = 0;
                        break block18;
                    }
                    n4 = 0;
                    n = n2;
                }
                object2 = generalSubtree;
                n2 = n;
                if (n4 != 0) {
                    n3 = 0;
                    for (n2 = 0; n2 < this.size(); ++n2) {
                        block20 : {
                            object = this.getGeneralNameInterface(n2);
                            n5 = n3;
                            if (object.getType() == generalNameInterface.getType()) {
                                n4 = 0;
                                do {
                                    n5 = n3;
                                    if (n4 >= generalSubtrees.size()) break block20;
                                    object2 = generalSubtrees.getGeneralNameInterface(n4);
                                    n5 = object.constrains((GeneralNameInterface)object2);
                                    if (n5 == 0 || n5 == 2 || n5 == 1) break;
                                    ++n4;
                                } while (true);
                                n5 = 1;
                            }
                        }
                        n3 = n5;
                    }
                    object2 = generalSubtree;
                    if (n3 == 0) {
                        object = generalSubtree;
                        if (generalSubtree == null) {
                            object = new GeneralSubtrees();
                        }
                        generalSubtree = this.createWidestSubtree(generalNameInterface);
                        object2 = object;
                        if (!((GeneralSubtrees)object).contains(generalSubtree)) {
                            ((GeneralSubtrees)object).add(generalSubtree);
                            object2 = object;
                        }
                    }
                    this.remove(n);
                    n2 = n - 1;
                }
                generalSubtree = object2;
            }
            if (generalSubtrees2.size() > 0) {
                this.union(generalSubtrees2);
            }
            for (n2 = 0; n2 < generalSubtrees.size(); ++n2) {
                object2 = generalSubtrees.get(n2);
                object = GeneralSubtrees.getGeneralNameInterface((GeneralSubtree)object2);
                n = 0;
                n3 = 0;
                do {
                    n4 = n;
                    if (n3 >= this.size()) break;
                    n4 = this.getGeneralNameInterface(n3).constrains((GeneralNameInterface)object);
                    if (n4 != -1) {
                        if (n4 == 0 || n4 == 1 || n4 == 2 || n4 == 3) {
                            n4 = 0;
                            break;
                        }
                    } else {
                        n = 1;
                    }
                    ++n3;
                } while (true);
                if (n4 == 0) continue;
                this.add((GeneralSubtree)object2);
            }
            return generalSubtree;
        }
        throw new NullPointerException("other GeneralSubtrees must not be null");
    }

    public Iterator<GeneralSubtree> iterator() {
        return this.trees.iterator();
    }

    public void reduce(GeneralSubtrees generalSubtrees) {
        if (generalSubtrees == null) {
            return;
        }
        int n = generalSubtrees.size();
        for (int i = 0; i < n; ++i) {
            GeneralNameInterface generalNameInterface = generalSubtrees.getGeneralNameInterface(i);
            for (int j = 0; j < this.size(); ++j) {
                int n2 = generalNameInterface.constrains(this.getGeneralNameInterface(j));
                if (n2 == -1) continue;
                if (n2 != 0) {
                    if (n2 != 1) {
                        if (n2 == 2) continue;
                    }
                    this.remove(j);
                    --j;
                    continue;
                }
                this.remove(j);
                --j;
            }
        }
    }

    public void remove(int n) {
        this.trees.remove(n);
    }

    public int size() {
        return this.trees.size();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("   GeneralSubtrees:\n");
        stringBuilder.append(this.trees.toString());
        stringBuilder.append("\n");
        return stringBuilder.toString();
    }

    public List<GeneralSubtree> trees() {
        return this.trees;
    }

    public void union(GeneralSubtrees generalSubtrees) {
        if (generalSubtrees != null) {
            int n = generalSubtrees.size();
            for (int i = 0; i < n; ++i) {
                this.add(generalSubtrees.get(i));
            }
            this.minimize();
        }
    }
}

