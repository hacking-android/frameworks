/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.impl.Assert;
import android.icu.impl.RBBIDataWrapper;
import android.icu.lang.UCharacter;
import android.icu.text.RBBINode;
import android.icu.text.RBBIRuleBuilder;
import android.icu.text.RBBISetBuilder;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class RBBITableBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private List<RBBIStateDescriptor> fDStates;
    private RBBIRuleBuilder fRB;
    private int fRootIx;
    private List<short[]> fSafeTable;

    RBBITableBuilder(RBBIRuleBuilder rBBIRuleBuilder, int n) {
        this.fRootIx = n;
        this.fRB = rBBIRuleBuilder;
        this.fDStates = new ArrayList<RBBIStateDescriptor>();
    }

    void addRuleRootNodes(List<RBBINode> list, RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fRuleRoot) {
            list.add(rBBINode);
            return;
        }
        this.addRuleRootNodes(list, rBBINode.fLeftChild);
        this.addRuleRootNodes(list, rBBINode.fRightChild);
    }

    void bofFixup() {
        RBBINode rBBINode = this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fLeftChild;
        int n = rBBINode.fType;
        boolean bl = true;
        boolean bl2 = n == 3;
        Assert.assrt(bl2);
        bl2 = rBBINode.fVal == 2 ? bl : false;
        Assert.assrt(bl2);
        for (RBBINode rBBINode2 : this.fRB.fTreeRoots[this.fRootIx].fLeftChild.fRightChild.fFirstPosSet) {
            if (rBBINode2.fType != 3 || rBBINode2.fVal != rBBINode.fVal) continue;
            rBBINode.fFollowPos.addAll(rBBINode2.fFollowPos);
        }
    }

    void buildForwardTable() {
        RBBINode rBBINode;
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return;
        }
        this.fRB.fTreeRoots[this.fRootIx] = this.fRB.fTreeRoots[this.fRootIx].flattenVariables();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("ftree") >= 0) {
            System.out.println("Parse tree after flattening variable references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            RBBINode rBBINode2;
            rBBINode = new RBBINode(8);
            rBBINode.fLeftChild = rBBINode2 = new RBBINode(3);
            rBBINode.fRightChild = this.fRB.fTreeRoots[this.fRootIx];
            rBBINode2.fParent = rBBINode;
            rBBINode2.fVal = 2;
            this.fRB.fTreeRoots[this.fRootIx] = rBBINode;
        }
        rBBINode = new RBBINode(8);
        rBBINode.fLeftChild = this.fRB.fTreeRoots[this.fRootIx];
        this.fRB.fTreeRoots[this.fRootIx].fParent = rBBINode;
        rBBINode.fRightChild = new RBBINode(6);
        rBBINode.fRightChild.fParent = rBBINode;
        this.fRB.fTreeRoots[this.fRootIx] = rBBINode;
        this.fRB.fTreeRoots[this.fRootIx].flattenSets();
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("stree") >= 0) {
            System.out.println("Parse tree after flattening Unicode Set references.");
            this.fRB.fTreeRoots[this.fRootIx].printTree(true);
        }
        this.calcNullable(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcFirstPos(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcLastPos(this.fRB.fTreeRoots[this.fRootIx]);
        this.calcFollowPos(this.fRB.fTreeRoots[this.fRootIx]);
        if (this.fRB.fDebugEnv != null && this.fRB.fDebugEnv.indexOf("pos") >= 0) {
            System.out.print("\n");
            this.printPosSets(this.fRB.fTreeRoots[this.fRootIx]);
        }
        if (this.fRB.fChainRules) {
            this.calcChainedFollowPos(this.fRB.fTreeRoots[this.fRootIx]);
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            this.bofFixup();
        }
        this.buildStateTable();
        this.flagAcceptingStates();
        this.flagLookAheadStates();
        this.flagTaggedStates();
        this.mergeRuleStatusVals();
    }

    void buildSafeReverseTable() {
        int n;
        int n2;
        int n3;
        StringBuilder stringBuilder = new StringBuilder();
        int n4 = this.fRB.fSetBuilder.getNumCharCategories();
        int n5 = this.fDStates.size();
        for (n2 = 0; n2 < n4; ++n2) {
            for (n3 = 0; n3 < n4; ++n3) {
                int n6 = -1;
                n = 0;
                for (int i = 1; i < n5; ++i) {
                    int n7;
                    n = this.fDStates.get((int)i).fDtran[n2];
                    n = this.fDStates.get((int)n).fDtran[n3];
                    if (n6 < 0) {
                        n7 = n;
                    } else {
                        n7 = n6;
                        if (n6 != n) break;
                    }
                    n6 = n7;
                }
                if (n6 != n) continue;
                stringBuilder.append((char)n2);
                stringBuilder.append((char)n3);
            }
        }
        this.fSafeTable = new ArrayList<short[]>();
        for (n = 0; n < n4 + 2; ++n) {
            this.fSafeTable.add(new short[n4]);
        }
        Object object = this.fSafeTable.get(1);
        for (n = 0; n < n4; ++n) {
            object[n] = (short)(n + 2);
        }
        for (n = 2; n < n4 + 2; ++n) {
            System.arraycopy(object, 0, this.fSafeTable.get(n), 0, ((short[])object).length);
        }
        for (n = 0; n < stringBuilder.length(); n += 2) {
            n2 = stringBuilder.charAt(n);
            n3 = stringBuilder.charAt(n + 1);
            this.fSafeTable.get((int)(n3 + 2))[n2] = (short)(false ? 1 : 0);
        }
        object = new RBBIRuleBuilder.IntPair(1, 0);
        while (this.findDuplicateSafeState((RBBIRuleBuilder.IntPair)object)) {
            this.removeSafeState((RBBIRuleBuilder.IntPair)object);
        }
    }

    void buildStateTable() {
        int n = this.fRB.fSetBuilder.getNumCharCategories() - 1;
        RBBIStateDescriptor rBBIStateDescriptor = new RBBIStateDescriptor(n);
        this.fDStates.add(rBBIStateDescriptor);
        rBBIStateDescriptor = new RBBIStateDescriptor(n);
        rBBIStateDescriptor.fPositions.addAll(this.fRB.fTreeRoots[this.fRootIx].fFirstPosSet);
        this.fDStates.add(rBBIStateDescriptor);
        block0 : do {
            RBBIStateDescriptor rBBIStateDescriptor2;
            rBBIStateDescriptor = null;
            int n2 = 1;
            do {
                rBBIStateDescriptor2 = rBBIStateDescriptor;
                if (n2 >= this.fDStates.size()) break;
                rBBIStateDescriptor2 = this.fDStates.get(n2);
                if (!rBBIStateDescriptor2.fMarked) break;
                ++n2;
            } while (true);
            if (rBBIStateDescriptor2 == null) {
                return;
            }
            rBBIStateDescriptor2.fMarked = true;
            int n3 = 1;
            do {
                Set<RBBINode> set;
                if (n3 > n) continue block0;
                rBBIStateDescriptor = null;
                for (RBBINode rBBINode : rBBIStateDescriptor2.fPositions) {
                    set = rBBIStateDescriptor;
                    if (rBBINode.fType == 3) {
                        set = rBBIStateDescriptor;
                        if (rBBINode.fVal == n3) {
                            set = rBBIStateDescriptor;
                            if (rBBIStateDescriptor == null) {
                                set = new HashSet();
                            }
                            set.addAll(rBBINode.fFollowPos);
                        }
                    }
                    rBBIStateDescriptor = set;
                }
                int n4 = 0;
                boolean bl = false;
                if (rBBIStateDescriptor != null) {
                    boolean bl2;
                    boolean bl3 = rBBIStateDescriptor.size() > 0;
                    Assert.assrt(bl3);
                    int n5 = 0;
                    do {
                        set = rBBIStateDescriptor;
                        n2 = n4;
                        bl2 = bl;
                        if (n5 >= this.fDStates.size()) break;
                        set = this.fDStates.get(n5);
                        if (rBBIStateDescriptor.equals(((RBBIStateDescriptor)set).fPositions)) {
                            set = ((RBBIStateDescriptor)set).fPositions;
                            n2 = n5;
                            bl2 = true;
                            break;
                        }
                        ++n5;
                    } while (true);
                    if (!bl2) {
                        rBBIStateDescriptor = new RBBIStateDescriptor(n);
                        rBBIStateDescriptor.fPositions = set;
                        this.fDStates.add(rBBIStateDescriptor);
                        n2 = this.fDStates.size() - 1;
                    }
                    rBBIStateDescriptor2.fDtran[n3] = n2;
                }
                ++n3;
            } while (true);
            break;
        } while (true);
    }

    void calcChainedFollowPos(RBBINode object) {
        ArrayList<RBBINode> arrayList = new ArrayList<RBBINode>();
        ArrayList<RBBINode> object22 = new ArrayList<RBBINode>();
        ((RBBINode)object).findNodes(arrayList, 6);
        ((RBBINode)object).findNodes(object22, 3);
        Iterator iterator = new ArrayList<RBBINode>();
        this.addRuleRootNodes((List<RBBINode>)((Object)iterator), (RBBINode)object);
        HashSet<RBBINode> hashSet = new HashSet<RBBINode>();
        object = iterator.iterator();
        while (object.hasNext()) {
            iterator = (RBBINode)object.next();
            if (!((RBBINode)iterator).fChainIn) continue;
            hashSet.addAll(((RBBINode)iterator).fFirstPosSet);
        }
        for (RBBINode rBBINode : object22) {
            int n;
            block4 : {
                iterator = null;
                Iterator iterator2 = arrayList.iterator();
                do {
                    object = iterator;
                    if (!iterator2.hasNext()) break block4;
                } while (!rBBINode.fFollowPos.contains(object = (RBBINode)iterator2.next()));
                object = rBBINode;
            }
            if (object == null || this.fRB.fLBCMNoChain && (n = this.fRB.fSetBuilder.getFirstChar(((RBBINode)object).fVal)) != -1 && UCharacter.getIntPropertyValue(n, 4104) == 9) continue;
            for (RBBINode rBBINode2 : hashSet) {
                if (rBBINode2.fType != 3 || ((RBBINode)object).fVal != rBBINode2.fVal) continue;
                ((RBBINode)object).fFollowPos.addAll(rBBINode2.fFollowPos);
            }
        }
    }

    void calcFirstPos(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fType != 3 && rBBINode.fType != 6 && rBBINode.fType != 4 && rBBINode.fType != 5) {
            this.calcFirstPos(rBBINode.fLeftChild);
            this.calcFirstPos(rBBINode.fRightChild);
            if (rBBINode.fType == 9) {
                rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
                rBBINode.fFirstPosSet.addAll(rBBINode.fRightChild.fFirstPosSet);
            } else if (rBBINode.fType == 8) {
                rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
                if (rBBINode.fLeftChild.fNullable) {
                    rBBINode.fFirstPosSet.addAll(rBBINode.fRightChild.fFirstPosSet);
                }
            } else if (rBBINode.fType == 10 || rBBINode.fType == 12 || rBBINode.fType == 11) {
                rBBINode.fFirstPosSet.addAll(rBBINode.fLeftChild.fFirstPosSet);
            }
            return;
        }
        rBBINode.fFirstPosSet.add(rBBINode);
    }

    void calcFollowPos(RBBINode rBBINode) {
        if (rBBINode != null && rBBINode.fType != 3 && rBBINode.fType != 6) {
            Iterator<RBBINode> iterator;
            this.calcFollowPos(rBBINode.fLeftChild);
            this.calcFollowPos(rBBINode.fRightChild);
            if (rBBINode.fType == 8) {
                iterator = rBBINode.fLeftChild.fLastPosSet.iterator();
                while (iterator.hasNext()) {
                    iterator.next().fFollowPos.addAll(rBBINode.fRightChild.fFirstPosSet);
                }
            }
            if (rBBINode.fType == 10 || rBBINode.fType == 11) {
                iterator = rBBINode.fLastPosSet.iterator();
                while (iterator.hasNext()) {
                    iterator.next().fFollowPos.addAll(rBBINode.fFirstPosSet);
                }
            }
            return;
        }
    }

    void calcLastPos(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        if (rBBINode.fType != 3 && rBBINode.fType != 6 && rBBINode.fType != 4 && rBBINode.fType != 5) {
            this.calcLastPos(rBBINode.fLeftChild);
            this.calcLastPos(rBBINode.fRightChild);
            if (rBBINode.fType == 9) {
                rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
                rBBINode.fLastPosSet.addAll(rBBINode.fRightChild.fLastPosSet);
            } else if (rBBINode.fType == 8) {
                rBBINode.fLastPosSet.addAll(rBBINode.fRightChild.fLastPosSet);
                if (rBBINode.fRightChild.fNullable) {
                    rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
                }
            } else if (rBBINode.fType == 10 || rBBINode.fType == 12 || rBBINode.fType == 11) {
                rBBINode.fLastPosSet.addAll(rBBINode.fLeftChild.fLastPosSet);
            }
            return;
        }
        rBBINode.fLastPosSet.add(rBBINode);
    }

    void calcNullable(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        int n = rBBINode.fType;
        boolean bl = false;
        boolean bl2 = false;
        if (n != 0 && rBBINode.fType != 6) {
            if (rBBINode.fType != 4 && rBBINode.fType != 5) {
                this.calcNullable(rBBINode.fLeftChild);
                this.calcNullable(rBBINode.fRightChild);
                if (rBBINode.fType == 9) {
                    if (rBBINode.fLeftChild.fNullable || rBBINode.fRightChild.fNullable) {
                        bl2 = true;
                    }
                    rBBINode.fNullable = bl2;
                } else if (rBBINode.fType == 8) {
                    bl2 = bl;
                    if (rBBINode.fLeftChild.fNullable) {
                        bl2 = bl;
                        if (rBBINode.fRightChild.fNullable) {
                            bl2 = true;
                        }
                    }
                    rBBINode.fNullable = bl2;
                } else {
                    rBBINode.fNullable = rBBINode.fType == 10 || rBBINode.fType == 12;
                }
                return;
            }
            rBBINode.fNullable = true;
            return;
        }
        rBBINode.fNullable = false;
    }

    RBBIDataWrapper.RBBIStateTable exportSafeTable() {
        RBBIDataWrapper.RBBIStateTable rBBIStateTable = new RBBIDataWrapper.RBBIStateTable();
        rBBIStateTable.fNumStates = this.fSafeTable.size();
        int n = this.fSafeTable.get(0).length;
        int n2 = n + 4;
        rBBIStateTable.fTable = new short[(this.getSafeTableSize() - 16) / 2];
        rBBIStateTable.fRowLen = n2 * 2;
        for (int i = 0; i < rBBIStateTable.fNumStates; ++i) {
            short[] arrs = this.fSafeTable.get(i);
            for (int j = 0; j < n; ++j) {
                rBBIStateTable.fTable[i * n2 + 4 + j] = arrs[j];
            }
        }
        return rBBIStateTable;
    }

    RBBIDataWrapper.RBBIStateTable exportTable() {
        RBBIDataWrapper.RBBIStateTable rBBIStateTable = new RBBIDataWrapper.RBBIStateTable();
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return rBBIStateTable;
        }
        boolean bl = this.fRB.fSetBuilder.getNumCharCategories() < 32767 && this.fDStates.size() < 32767;
        Assert.assrt(bl);
        rBBIStateTable.fNumStates = this.fDStates.size();
        int n = this.fRB.fSetBuilder.getNumCharCategories() + 4;
        rBBIStateTable.fTable = new short[(this.getTableSize() - 16) / 2];
        rBBIStateTable.fRowLen = n * 2;
        if (this.fRB.fLookAheadHardBreak) {
            rBBIStateTable.fFlags |= 1;
        }
        if (this.fRB.fSetBuilder.sawBOF()) {
            rBBIStateTable.fFlags |= 2;
        }
        int n2 = this.fRB.fSetBuilder.getNumCharCategories();
        for (int i = 0; i < rBBIStateTable.fNumStates; ++i) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
            int n3 = i * n;
            bl = -32768 < rBBIStateDescriptor.fAccepting && rBBIStateDescriptor.fAccepting <= 32767;
            Assert.assrt(bl);
            bl = -32768 < rBBIStateDescriptor.fLookAhead && rBBIStateDescriptor.fLookAhead <= 32767;
            Assert.assrt(bl);
            rBBIStateTable.fTable[n3 + 0] = (short)rBBIStateDescriptor.fAccepting;
            rBBIStateTable.fTable[n3 + 1] = (short)rBBIStateDescriptor.fLookAhead;
            rBBIStateTable.fTable[n3 + 2] = (short)rBBIStateDescriptor.fTagsIdx;
            for (int j = 0; j < n2; ++j) {
                rBBIStateTable.fTable[n3 + 4 + j] = (short)rBBIStateDescriptor.fDtran[j];
            }
        }
        return rBBIStateTable;
    }

    boolean findDuplCharClassFrom(RBBIRuleBuilder.IntPair intPair) {
        int n = this.fDStates.size();
        int n2 = this.fRB.fSetBuilder.getNumCharCategories();
        int n3 = 0;
        int n4 = 0;
        while (intPair.first < n2 - 1) {
            int n5 = intPair.first;
            do {
                intPair.second = n5 + 1;
                if (intPair.second >= n2) break;
                for (n5 = 0; n5 < n; ++n5) {
                    RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(n5);
                    n3 = rBBIStateDescriptor.fDtran[intPair.first];
                    n4 = rBBIStateDescriptor.fDtran[intPair.second];
                    if (n3 != n4) break;
                }
                if (n3 == n4) {
                    return true;
                }
                n5 = intPair.second;
            } while (true);
            ++intPair.first;
        }
        return false;
    }

    boolean findDuplicateSafeState(RBBIRuleBuilder.IntPair intPair) {
        int n = this.fSafeTable.size();
        while (intPair.first < n - 1) {
            short[] arrs = this.fSafeTable.get(intPair.first);
            int n2 = intPair.first;
            do {
                short s;
                intPair.second = n2 + 1;
                if (intPair.second >= n) break;
                short[] arrs2 = this.fSafeTable.get(intPair.second);
                short s2 = 1;
                int n3 = arrs.length;
                n2 = 0;
                do {
                    s = s2;
                    if (n2 >= n3) break;
                    s = arrs[n2];
                    short s3 = arrs2[n2];
                    if (s != s3 && (s != intPair.first && s != intPair.second || s3 != intPair.first && s3 != intPair.second)) {
                        s = 0;
                        break;
                    }
                    ++n2;
                } while (true);
                if (s != 0) {
                    return true;
                }
                n2 = intPair.second;
            } while (true);
            ++intPair.first;
        }
        return false;
    }

    boolean findDuplicateState(RBBIRuleBuilder.IntPair intPair) {
        int n = this.fDStates.size();
        int n2 = this.fRB.fSetBuilder.getNumCharCategories();
        while (intPair.first < n - 1) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(intPair.first);
            int n3 = intPair.first;
            do {
                intPair.second = n3 + 1;
                if (intPair.second >= n) break;
                RBBIStateDescriptor rBBIStateDescriptor2 = this.fDStates.get(intPair.second);
                if (rBBIStateDescriptor.fAccepting == rBBIStateDescriptor2.fAccepting && rBBIStateDescriptor.fLookAhead == rBBIStateDescriptor2.fLookAhead && rBBIStateDescriptor.fTagsIdx == rBBIStateDescriptor2.fTagsIdx) {
                    int n4;
                    int n5 = 1;
                    n3 = 0;
                    do {
                        n4 = n5;
                        if (n3 >= n2) break;
                        n4 = rBBIStateDescriptor.fDtran[n3];
                        int n6 = rBBIStateDescriptor2.fDtran[n3];
                        if (n4 != n6 && (n4 != intPair.first && n4 != intPair.second || n6 != intPair.first && n6 != intPair.second)) {
                            n4 = 0;
                            break;
                        }
                        ++n3;
                    } while (true);
                    if (n4 != 0) {
                        return true;
                    }
                }
                n3 = intPair.second;
            } while (true);
            ++intPair.first;
        }
        return false;
    }

    void flagAcceptingStates() {
        ArrayList<RBBINode> arrayList = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 6);
        for (int i = 0; i < arrayList.size(); ++i) {
            RBBINode rBBINode = (RBBINode)arrayList.get(i);
            for (int j = 0; j < this.fDStates.size(); ++j) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(j);
                if (!rBBIStateDescriptor.fPositions.contains(rBBINode)) continue;
                if (rBBIStateDescriptor.fAccepting == 0) {
                    rBBIStateDescriptor.fAccepting = rBBINode.fVal;
                    if (rBBIStateDescriptor.fAccepting == 0) {
                        rBBIStateDescriptor.fAccepting = -1;
                    }
                }
                if (rBBIStateDescriptor.fAccepting == -1 && rBBINode.fVal != 0) {
                    rBBIStateDescriptor.fAccepting = rBBINode.fVal;
                }
                if (!rBBINode.fLookAheadEnd) continue;
                rBBIStateDescriptor.fLookAhead = rBBIStateDescriptor.fAccepting;
            }
        }
    }

    void flagLookAheadStates() {
        ArrayList<RBBINode> arrayList = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 4);
        for (int i = 0; i < arrayList.size(); ++i) {
            RBBINode rBBINode = (RBBINode)arrayList.get(i);
            for (int j = 0; j < this.fDStates.size(); ++j) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(j);
                if (!rBBIStateDescriptor.fPositions.contains(rBBINode)) continue;
                rBBIStateDescriptor.fLookAhead = rBBINode.fVal;
            }
        }
    }

    void flagTaggedStates() {
        ArrayList<RBBINode> arrayList = new ArrayList<RBBINode>();
        this.fRB.fTreeRoots[this.fRootIx].findNodes(arrayList, 5);
        for (int i = 0; i < arrayList.size(); ++i) {
            RBBINode rBBINode = (RBBINode)arrayList.get(i);
            for (int j = 0; j < this.fDStates.size(); ++j) {
                RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(j);
                if (!rBBIStateDescriptor.fPositions.contains(rBBINode)) continue;
                rBBIStateDescriptor.fTagVals.add(rBBINode.fVal);
            }
        }
    }

    int getSafeTableSize() {
        List<short[]> list = this.fSafeTable;
        if (list == null) {
            return 0;
        }
        return 16 + list.size() * (this.fSafeTable.get(0).length * 2 + 8) + 7 & -8;
    }

    int getTableSize() {
        if (this.fRB.fTreeRoots[this.fRootIx] == null) {
            return 0;
        }
        return 16 + this.fDStates.size() * (this.fRB.fSetBuilder.getNumCharCategories() * 2 + 8) + 7 & -8;
    }

    void mergeRuleStatusVals() {
        Serializable serializable;
        Integer n;
        if (this.fRB.fRuleStatusVals.size() == 0) {
            this.fRB.fRuleStatusVals.add(1);
            this.fRB.fRuleStatusVals.add(0);
            serializable = new TreeSet();
            n = 0;
            this.fRB.fStatusSets.put((Set<Integer>)((Object)serializable), n);
            new TreeSet<Integer>().add(n);
            this.fRB.fStatusSets.put((Set<Integer>)((Object)serializable), n);
        }
        for (int i = 0; i < this.fDStates.size(); ++i) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
            SortedSet<Integer> sortedSet = rBBIStateDescriptor.fTagVals;
            n = this.fRB.fStatusSets.get(sortedSet);
            serializable = n;
            if (n == null) {
                serializable = Integer.valueOf(this.fRB.fRuleStatusVals.size());
                this.fRB.fStatusSets.put(sortedSet, (Integer)serializable);
                this.fRB.fRuleStatusVals.add(sortedSet.size());
                this.fRB.fRuleStatusVals.addAll(sortedSet);
            }
            rBBIStateDescriptor.fTagsIdx = (Integer)serializable;
        }
    }

    void printPosSets(RBBINode rBBINode) {
        if (rBBINode == null) {
            return;
        }
        RBBINode.printNode(rBBINode);
        PrintStream printStream = System.out;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("         Nullable:  ");
        stringBuilder.append(rBBINode.fNullable);
        printStream.print(stringBuilder.toString());
        System.out.print("         firstpos:  ");
        this.printSet(rBBINode.fFirstPosSet);
        System.out.print("         lastpos:   ");
        this.printSet(rBBINode.fLastPosSet);
        System.out.print("         followpos: ");
        this.printSet(rBBINode.fFollowPos);
        this.printPosSets(rBBINode.fLeftChild);
        this.printPosSets(rBBINode.fRightChild);
    }

    void printReverseTable() {
        int n;
        short[] arrs = System.out;
        Integer n2 = 0;
        arrs.printf("    Safe Reverse Table \n", new Object[0]);
        arrs = this.fSafeTable;
        if (arrs == null) {
            System.out.printf("   --- nullptr ---\n", new Object[0]);
            return;
        }
        int n3 = ((short[])arrs.get(0)).length;
        System.out.printf("state |           i n p u t     s y m b o l s \n", new Object[0]);
        System.out.printf("      | Acc  LA    Tag", new Object[0]);
        for (n = 0; n < n3; ++n) {
            System.out.printf(" %2d", n);
        }
        System.out.printf("\n", new Object[0]);
        System.out.printf("      |---------------", new Object[0]);
        for (n = 0; n < n3; ++n) {
            System.out.printf("---", new Object[0]);
        }
        System.out.printf("\n", new Object[0]);
        for (n = 0; n < this.fSafeTable.size(); ++n) {
            arrs = this.fSafeTable.get(n);
            System.out.printf("  %3d | ", n);
            System.out.printf("%3d %3d %5d ", n2, n2, n2);
            for (int i = 0; i < n3; ++i) {
                System.out.printf(" %2d", arrs[i]);
            }
            System.out.printf("\n", new Object[0]);
        }
        System.out.printf("\n\n", new Object[0]);
    }

    void printRuleStatusTable() {
        int n = 0;
        List<Integer> list = this.fRB.fRuleStatusVals;
        System.out.print("index |  tags \n");
        System.out.print("-------------------\n");
        while (n < list.size()) {
            int n2 = list.get(n) + n + 1;
            RBBINode.printInt(n, 7);
            ++n;
            while (n < n2) {
                RBBINode.printInt(list.get(n), 7);
                ++n;
            }
            System.out.print("\n");
            n = n2;
        }
        System.out.print("\n\n");
    }

    void printSet(Collection<RBBINode> object) {
        object = object.iterator();
        while (object.hasNext()) {
            RBBINode.printInt(((RBBINode)object.next()).fSerialNum, 8);
        }
        System.out.println();
    }

    void printStates() {
        int n;
        System.out.print("state |           i n p u t     s y m b o l s \n");
        System.out.print("      | Acc  LA    Tag");
        for (n = 0; n < this.fRB.fSetBuilder.getNumCharCategories(); ++n) {
            RBBINode.printInt(n, 3);
        }
        System.out.print("\n");
        System.out.print("      |---------------");
        for (n = 0; n < this.fRB.fSetBuilder.getNumCharCategories(); ++n) {
            System.out.print("---");
        }
        System.out.print("\n");
        for (n = 0; n < this.fDStates.size(); ++n) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(n);
            RBBINode.printInt(n, 5);
            System.out.print(" | ");
            RBBINode.printInt(rBBIStateDescriptor.fAccepting, 3);
            RBBINode.printInt(rBBIStateDescriptor.fLookAhead, 4);
            RBBINode.printInt(rBBIStateDescriptor.fTagsIdx, 6);
            System.out.print(" ");
            for (int i = 0; i < this.fRB.fSetBuilder.getNumCharCategories(); ++i) {
                RBBINode.printInt(rBBIStateDescriptor.fDtran[i], 3);
            }
            System.out.print("\n");
        }
        System.out.print("\n\n");
    }

    void removeColumn(int n) {
        int n2 = this.fDStates.size();
        for (int i = 0; i < n2; ++i) {
            RBBIStateDescriptor rBBIStateDescriptor = this.fDStates.get(i);
            int[] arrn = Arrays.copyOf(rBBIStateDescriptor.fDtran, rBBIStateDescriptor.fDtran.length - 1);
            System.arraycopy(rBBIStateDescriptor.fDtran, n + 1, arrn, n, arrn.length - n);
            rBBIStateDescriptor.fDtran = arrn;
        }
    }

    int removeDuplicateStates() {
        RBBIRuleBuilder.IntPair intPair = new RBBIRuleBuilder.IntPair(3, 0);
        int n = 0;
        while (this.findDuplicateState(intPair)) {
            this.removeState(intPair);
            ++n;
        }
        return n;
    }

    void removeSafeState(RBBIRuleBuilder.IntPair arrs) {
        int n = arrs.first;
        int n2 = arrs.second;
        this.fSafeTable.remove(n2);
        int n3 = this.fSafeTable.size();
        for (int i = 0; i < n3; ++i) {
            arrs = this.fSafeTable.get(i);
            for (int j = 0; j < arrs.length; ++j) {
                int n4;
                int n5 = n4 = arrs[j];
                if (n4 == n2) {
                    n5 = n;
                } else if (n4 > n2) {
                    n5 = n4 - 1;
                }
                arrs[j] = (short)n5;
            }
        }
    }

    void removeState(RBBIRuleBuilder.IntPair object) {
        int n = ((RBBIRuleBuilder.IntPair)object).first;
        int n2 = ((RBBIRuleBuilder.IntPair)object).second;
        this.fDStates.remove(n2);
        int n3 = this.fDStates.size();
        int n4 = this.fRB.fSetBuilder.getNumCharCategories();
        for (int i = 0; i < n3; ++i) {
            object = this.fDStates.get(i);
            for (int j = 0; j < n4; ++j) {
                int n5;
                int n6 = n5 = ((RBBIStateDescriptor)object).fDtran[j];
                if (n5 == n2) {
                    n6 = n;
                } else if (n5 > n2) {
                    n6 = n5 - 1;
                }
                object.fDtran[j] = n6;
            }
            if (((RBBIStateDescriptor)object).fAccepting == n2) {
                ((RBBIStateDescriptor)object).fAccepting = n;
            } else if (((RBBIStateDescriptor)object).fAccepting > n2) {
                --((RBBIStateDescriptor)object).fAccepting;
            }
            if (((RBBIStateDescriptor)object).fLookAhead == n2) {
                ((RBBIStateDescriptor)object).fLookAhead = n;
                continue;
            }
            if (((RBBIStateDescriptor)object).fLookAhead <= n2) continue;
            --((RBBIStateDescriptor)object).fLookAhead;
        }
    }

    static class RBBIStateDescriptor {
        int fAccepting;
        int[] fDtran;
        int fLookAhead;
        boolean fMarked;
        Set<RBBINode> fPositions = new HashSet<RBBINode>();
        SortedSet<Integer> fTagVals = new TreeSet<Integer>();
        int fTagsIdx;

        RBBIStateDescriptor(int n) {
            this.fDtran = new int[n + 1];
        }
    }

}

