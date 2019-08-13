/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import javax.xml.transform.Source;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xml.dtm.DTMException;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMDefaultBase;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.SuballocatedIntVector;
import org.apache.xml.utils.XMLStringFactory;

public abstract class DTMDefaultBaseTraversers
extends DTMDefaultBase {
    public DTMDefaultBaseTraversers(DTMManager dTMManager, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl) {
        super(dTMManager, source, n, dTMWSFilter, xMLStringFactory, bl);
    }

    public DTMDefaultBaseTraversers(DTMManager dTMManager, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl, int n2, boolean bl2, boolean bl3) {
        super(dTMManager, source, n, dTMWSFilter, xMLStringFactory, bl, n2, bl2, bl3);
    }

    @Override
    public DTMAxisTraverser getAxisTraverser(int n) {
        DTMAxisTraverser dTMAxisTraverser;
        if (this.m_traversers == null) {
            this.m_traversers = new DTMAxisTraverser[org.apache.xml.dtm.Axis.getNamesLength()];
        } else {
            dTMAxisTraverser = this.m_traversers[n];
            if (dTMAxisTraverser != null) {
                return dTMAxisTraverser;
            }
        }
        switch (n) {
            default: {
                throw new DTMException(XMLMessages.createXMLMessage("ER_UNKNOWN_AXIS_TYPE", new Object[]{Integer.toString(n)}));
            }
            case 20: {
                return null;
            }
            case 19: {
                dTMAxisTraverser = new RootTraverser();
                break;
            }
            case 18: {
                dTMAxisTraverser = new DescendantOrSelfFromRootTraverser();
                break;
            }
            case 17: {
                dTMAxisTraverser = new DescendantFromRootTraverser();
                break;
            }
            case 16: {
                dTMAxisTraverser = new AllFromRootTraverser();
                break;
            }
            case 15: {
                dTMAxisTraverser = new PrecedingAndAncestorTraverser();
                break;
            }
            case 14: {
                dTMAxisTraverser = new AllFromNodeTraverser();
                break;
            }
            case 13: {
                dTMAxisTraverser = new SelfTraverser();
                break;
            }
            case 12: {
                dTMAxisTraverser = new PrecedingSiblingTraverser();
                break;
            }
            case 11: {
                dTMAxisTraverser = new PrecedingTraverser();
                break;
            }
            case 10: {
                dTMAxisTraverser = new ParentTraverser();
                break;
            }
            case 9: {
                dTMAxisTraverser = new NamespaceTraverser();
                break;
            }
            case 8: {
                dTMAxisTraverser = new NamespaceDeclsTraverser();
                break;
            }
            case 7: {
                dTMAxisTraverser = new FollowingSiblingTraverser();
                break;
            }
            case 6: {
                dTMAxisTraverser = new FollowingTraverser();
                break;
            }
            case 5: {
                dTMAxisTraverser = new DescendantOrSelfTraverser();
                break;
            }
            case 4: {
                dTMAxisTraverser = new DescendantTraverser();
                break;
            }
            case 3: {
                dTMAxisTraverser = new ChildTraverser();
                break;
            }
            case 2: {
                dTMAxisTraverser = new AttributeTraverser();
                break;
            }
            case 1: {
                dTMAxisTraverser = new AncestorOrSelfTraverser();
                break;
            }
            case 0: {
                dTMAxisTraverser = new AncestorTraverser();
            }
        }
        this.m_traversers[n] = dTMAxisTraverser;
        return dTMAxisTraverser;
    }

    private class AllFromNodeTraverser
    extends DescendantOrSelfTraverser {
        private AllFromNodeTraverser() {
        }

        @Override
        public int next(int n, int n2) {
            n = DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
            n2 = DTMDefaultBaseTraversers.this.makeNodeIdentity(n2) + 1;
            DTMDefaultBaseTraversers.this._exptype(n2);
            if (!this.isDescendant(n, n2)) {
                return -1;
            }
            return DTMDefaultBaseTraversers.this.makeNodeHandle(n2);
        }
    }

    private class AllFromRootTraverser
    extends AllFromNodeTraverser {
        private AllFromRootTraverser() {
        }

        @Override
        public int first(int n) {
            return DTMDefaultBaseTraversers.this.getDocumentRoot(n);
        }

        @Override
        public int first(int n, int n2) {
            DTMDefaultBaseTraversers dTMDefaultBaseTraversers = DTMDefaultBaseTraversers.this;
            if (dTMDefaultBaseTraversers.getExpandedTypeID(dTMDefaultBaseTraversers.getDocumentRoot(n)) != n2) {
                n = this.next(n, n, n2);
            }
            return n;
        }

        @Override
        public int next(int n, int n2) {
            DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
            n = DTMDefaultBaseTraversers.this.makeNodeIdentity(n2) + 1;
            if (DTMDefaultBaseTraversers.this._type(n) == -1) {
                return -1;
            }
            return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
        }

        @Override
        public int next(int n, int n2, int n3) {
            DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
            n = DTMDefaultBaseTraversers.this.makeNodeIdentity(n2) + 1;
            do {
                if ((n2 = DTMDefaultBaseTraversers.this._exptype(n)) == -1) {
                    return -1;
                }
                if (n2 == n3) break;
                ++n;
            } while (true);
            return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
        }
    }

    private class AncestorOrSelfTraverser
    extends AncestorTraverser {
        private AncestorOrSelfTraverser() {
        }

        @Override
        public int first(int n) {
            return n;
        }

        @Override
        public int first(int n, int n2) {
            if (DTMDefaultBaseTraversers.this.getExpandedTypeID(n) != n2) {
                n = this.next(n, n, n2);
            }
            return n;
        }
    }

    private class AncestorTraverser
    extends DTMAxisTraverser {
        private AncestorTraverser() {
        }

        @Override
        public int next(int n, int n2) {
            return DTMDefaultBaseTraversers.this.getParent(n2);
        }

        @Override
        public int next(int n, int n2, int n3) {
            block1 : {
                n2 = DTMDefaultBaseTraversers.this.makeNodeIdentity(n2);
                do {
                    n = n2 = DTMDefaultBaseTraversers.this.m_parent.elementAt(n2);
                    if (-1 == n2) break block1;
                    n2 = n;
                } while (DTMDefaultBaseTraversers.this.m_exptype.elementAt(n) != n3);
                return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
            }
            return -1;
        }
    }

    private class AttributeTraverser
    extends DTMAxisTraverser {
        private AttributeTraverser() {
        }

        @Override
        public int next(int n, int n2) {
            n = n == n2 ? DTMDefaultBaseTraversers.this.getFirstAttribute(n) : DTMDefaultBaseTraversers.this.getNextAttribute(n2);
            return n;
        }

        @Override
        public int next(int n, int n2, int n3) {
            n = n == n2 ? DTMDefaultBaseTraversers.this.getFirstAttribute(n) : DTMDefaultBaseTraversers.this.getNextAttribute(n2);
            do {
                if (DTMDefaultBaseTraversers.this.getExpandedTypeID(n) == n3) {
                    return n;
                }
                n = n2 = DTMDefaultBaseTraversers.this.getNextAttribute(n);
            } while (-1 != n2);
            return -1;
        }
    }

    private class ChildTraverser
    extends DTMAxisTraverser {
        private ChildTraverser() {
        }

        @Override
        public int first(int n) {
            return DTMDefaultBaseTraversers.this.getFirstChild(n);
        }

        @Override
        public int first(int n, int n2) {
            n = DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
            n = this.getNextIndexed(n, DTMDefaultBaseTraversers.this._firstch(n), n2);
            return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
        }

        protected int getNextIndexed(int n, int n2, int n3) {
            int n4 = DTMDefaultBaseTraversers.this.m_expandedNameTable.getNamespaceID(n3);
            int n5 = DTMDefaultBaseTraversers.this.m_expandedNameTable.getLocalNameID(n3);
            do {
                int n6;
                if (-2 != (n6 = DTMDefaultBaseTraversers.this.findElementFromIndex(n4, n5, n2))) {
                    n3 = DTMDefaultBaseTraversers.this.m_parent.elementAt(n6);
                    if (n3 == n) {
                        return n6;
                    }
                    n2 = n3;
                    if (n3 < n) {
                        return -1;
                    }
                    do {
                        if ((n3 = DTMDefaultBaseTraversers.this.m_parent.elementAt(n2)) < n) {
                            return -1;
                        }
                        n2 = n3;
                    } while (n3 > n);
                    n2 = n6 + 1;
                    continue;
                }
                DTMDefaultBaseTraversers.this.nextNode();
                if (DTMDefaultBaseTraversers.this.m_nextsib.elementAt(n) != -2) break;
            } while (true);
            return -1;
        }

        @Override
        public int next(int n, int n2) {
            return DTMDefaultBaseTraversers.this.getNextSibling(n2);
        }

        @Override
        public int next(int n, int n2, int n3) {
            DTMDefaultBaseTraversers dTMDefaultBaseTraversers = DTMDefaultBaseTraversers.this;
            n = dTMDefaultBaseTraversers._nextsib(dTMDefaultBaseTraversers.makeNodeIdentity(n2));
            while (-1 != n) {
                if (DTMDefaultBaseTraversers.this.m_exptype.elementAt(n) == n3) {
                    return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
                }
                n = DTMDefaultBaseTraversers.this._nextsib(n);
            }
            return -1;
        }
    }

    private class DescendantFromRootTraverser
    extends DescendantTraverser {
        private DescendantFromRootTraverser() {
        }

        @Override
        public int first(int n) {
            DTMDefaultBaseTraversers dTMDefaultBaseTraversers = DTMDefaultBaseTraversers.this;
            return dTMDefaultBaseTraversers.makeNodeHandle(dTMDefaultBaseTraversers._firstch(0));
        }

        @Override
        public int first(int n, int n2) {
            if (this.isIndexed(n2)) {
                n = this.getFirstPotential(0);
                return DTMDefaultBaseTraversers.this.makeNodeHandle(this.getNextIndexed(0, n, n2));
            }
            n = DTMDefaultBaseTraversers.this.getDocumentRoot(n);
            return this.next(n, n, n2);
        }

        @Override
        protected int getFirstPotential(int n) {
            return DTMDefaultBaseTraversers.this._firstch(0);
        }

        @Override
        protected int getSubtreeRoot(int n) {
            return 0;
        }
    }

    private class DescendantOrSelfFromRootTraverser
    extends DescendantTraverser {
        private DescendantOrSelfFromRootTraverser() {
        }

        @Override
        public int first(int n) {
            return DTMDefaultBaseTraversers.this.getDocumentRoot(n);
        }

        @Override
        public int first(int n, int n2) {
            if (this.isIndexed(n2)) {
                n = this.getFirstPotential(0);
                return DTMDefaultBaseTraversers.this.makeNodeHandle(this.getNextIndexed(0, n, n2));
            }
            n = this.first(n);
            return this.next(n, n, n2);
        }

        @Override
        protected int getFirstPotential(int n) {
            return n;
        }

        @Override
        protected int getSubtreeRoot(int n) {
            DTMDefaultBaseTraversers dTMDefaultBaseTraversers = DTMDefaultBaseTraversers.this;
            return dTMDefaultBaseTraversers.makeNodeIdentity(dTMDefaultBaseTraversers.getDocument());
        }
    }

    private class DescendantOrSelfTraverser
    extends DescendantTraverser {
        private DescendantOrSelfTraverser() {
        }

        @Override
        public int first(int n) {
            return n;
        }

        @Override
        protected int getFirstPotential(int n) {
            return n;
        }
    }

    private class DescendantTraverser
    extends IndexedDTMAxisTraverser {
        private DescendantTraverser() {
        }

        @Override
        protected boolean axisHasBeenProcessed(int n) {
            boolean bl = DTMDefaultBaseTraversers.this.m_nextsib.elementAt(n) != -2;
            return bl;
        }

        @Override
        public int first(int n, int n2) {
            if (this.isIndexed(n2)) {
                int n3 = this.getSubtreeRoot(n);
                n = this.getFirstPotential(n3);
                return DTMDefaultBaseTraversers.this.makeNodeHandle(this.getNextIndexed(n3, n, n2));
            }
            return this.next(n, n, n2);
        }

        protected int getFirstPotential(int n) {
            return n + 1;
        }

        protected int getSubtreeRoot(int n) {
            return DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
        }

        @Override
        protected boolean isAfterAxis(int n, int n2) {
            do {
                if (n2 != n) continue;
                return false;
            } while ((n2 = DTMDefaultBaseTraversers.this.m_parent.elementAt(n2)) >= n);
            return true;
        }

        protected boolean isDescendant(int n, int n2) {
            boolean bl = DTMDefaultBaseTraversers.this._parent(n2) >= n;
            return bl;
        }

        @Override
        public int next(int n, int n2) {
            int n3 = this.getSubtreeRoot(n);
            n = DTMDefaultBaseTraversers.this.makeNodeIdentity(n2) + 1;
            do {
                n2 = DTMDefaultBaseTraversers.this._type(n);
                if (!this.isDescendant(n3, n)) {
                    return -1;
                }
                if (2 != n2 && 13 != n2) {
                    return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
                }
                ++n;
            } while (true);
        }

        @Override
        public int next(int n, int n2, int n3) {
            int n4 = this.getSubtreeRoot(n);
            n = n2 = DTMDefaultBaseTraversers.this.makeNodeIdentity(n2) + 1;
            if (this.isIndexed(n3)) {
                return DTMDefaultBaseTraversers.this.makeNodeHandle(this.getNextIndexed(n4, n2, n3));
            }
            do {
                n2 = DTMDefaultBaseTraversers.this._exptype(n);
                if (!this.isDescendant(n4, n)) {
                    return -1;
                }
                if (n2 == n3) break;
                ++n;
            } while (true);
            return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
        }
    }

    private class FollowingSiblingTraverser
    extends DTMAxisTraverser {
        private FollowingSiblingTraverser() {
        }

        @Override
        public int next(int n, int n2) {
            return DTMDefaultBaseTraversers.this.getNextSibling(n2);
        }

        @Override
        public int next(int n, int n2, int n3) {
            block1 : {
                do {
                    n = n2 = DTMDefaultBaseTraversers.this.getNextSibling(n2);
                    if (-1 == n2) break block1;
                    n2 = n;
                } while (DTMDefaultBaseTraversers.this.getExpandedTypeID(n) != n3);
                return n;
            }
            return -1;
        }
    }

    private class FollowingTraverser
    extends DescendantTraverser {
        private FollowingTraverser() {
        }

        @Override
        public int first(int n) {
            int n2;
            int n3;
            block7 : {
                block6 : {
                    n3 = DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
                    n2 = DTMDefaultBaseTraversers.this._type(n3);
                    if (2 == n2) break block6;
                    n = n3;
                    if (13 != n2) break block7;
                }
                n = DTMDefaultBaseTraversers.this._parent(n3);
                n3 = DTMDefaultBaseTraversers.this._firstch(n);
                if (-1 != n3) {
                    return DTMDefaultBaseTraversers.this.makeNodeHandle(n3);
                }
            }
            do {
                n2 = DTMDefaultBaseTraversers.this._nextsib(n);
                n3 = n;
                if (-1 == n2) {
                    n3 = DTMDefaultBaseTraversers.this._parent(n);
                }
                if (-1 != n2) break;
                n = n3;
            } while (-1 != n3);
            return DTMDefaultBaseTraversers.this.makeNodeHandle(n2);
        }

        @Override
        public int first(int n, int n2) {
            int n3;
            int n4;
            block9 : {
                block8 : {
                    n3 = DTMDefaultBaseTraversers.this.getNodeType(n);
                    if (2 == n3) break block8;
                    n4 = n;
                    if (13 != n3) break block9;
                }
                n = DTMDefaultBaseTraversers.this.getParent(n);
                n3 = DTMDefaultBaseTraversers.this.getFirstChild(n);
                n4 = n;
                if (-1 != n3) {
                    if (DTMDefaultBaseTraversers.this.getExpandedTypeID(n3) == n2) {
                        return n3;
                    }
                    return this.next(n, n3, n2);
                }
            }
            while (-1 == (n3 = DTMDefaultBaseTraversers.this.getNextSibling(n4))) {
                n = DTMDefaultBaseTraversers.this.getParent(n4);
                if (-1 == n3) {
                    n4 = n;
                    if (-1 != n) continue;
                }
                return n3;
            }
            if (DTMDefaultBaseTraversers.this.getExpandedTypeID(n3) == n2) {
                return n3;
            }
            return this.next(n4, n3, n2);
        }

        @Override
        public int next(int n, int n2) {
            n = DTMDefaultBaseTraversers.this.makeNodeIdentity(n2);
            do {
                short s;
                if (-1 == (s = DTMDefaultBaseTraversers.this._type(n2 = n + 1))) {
                    return -1;
                }
                n = n2;
                if (2 == s) continue;
                if (13 != s) break;
                n = n2;
            } while (true);
            return DTMDefaultBaseTraversers.this.makeNodeHandle(n2);
        }

        @Override
        public int next(int n, int n2, int n3) {
            n = DTMDefaultBaseTraversers.this.makeNodeIdentity(n2);
            do {
                if (-1 != (n2 = DTMDefaultBaseTraversers.this._exptype(++n))) continue;
                return -1;
            } while (n2 != n3);
            return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
        }
    }

    private abstract class IndexedDTMAxisTraverser
    extends DTMAxisTraverser {
        private IndexedDTMAxisTraverser() {
        }

        protected abstract boolean axisHasBeenProcessed(int var1);

        protected int getNextIndexed(int n, int n2, int n3) {
            int n4 = DTMDefaultBaseTraversers.this.m_expandedNameTable.getNamespaceID(n3);
            int n5 = DTMDefaultBaseTraversers.this.m_expandedNameTable.getLocalNameID(n3);
            do {
                if (-2 != (n3 = DTMDefaultBaseTraversers.this.findElementFromIndex(n4, n5, n2))) {
                    if (this.isAfterAxis(n, n3)) {
                        return -1;
                    }
                    return n3;
                }
                if (this.axisHasBeenProcessed(n)) {
                    return -1;
                }
                DTMDefaultBaseTraversers.this.nextNode();
            } while (true);
        }

        protected abstract boolean isAfterAxis(int var1, int var2);

        protected final boolean isIndexed(int n) {
            boolean bl = DTMDefaultBaseTraversers.this.m_indexing;
            boolean bl2 = true;
            if (!bl || 1 != DTMDefaultBaseTraversers.this.m_expandedNameTable.getType(n)) {
                bl2 = false;
            }
            return bl2;
        }
    }

    private class NamespaceDeclsTraverser
    extends DTMAxisTraverser {
        private NamespaceDeclsTraverser() {
        }

        @Override
        public int next(int n, int n2) {
            n = n == n2 ? DTMDefaultBaseTraversers.this.getFirstNamespaceNode(n, false) : DTMDefaultBaseTraversers.this.getNextNamespaceNode(n, n2, false);
            return n;
        }

        @Override
        public int next(int n, int n2, int n3) {
            int n4;
            n2 = n == n2 ? DTMDefaultBaseTraversers.this.getFirstNamespaceNode(n, false) : DTMDefaultBaseTraversers.this.getNextNamespaceNode(n, n2, false);
            do {
                if (DTMDefaultBaseTraversers.this.getExpandedTypeID(n2) == n3) {
                    return n2;
                }
                n2 = n4 = DTMDefaultBaseTraversers.this.getNextNamespaceNode(n, n2, false);
            } while (-1 != n4);
            return -1;
        }
    }

    private class NamespaceTraverser
    extends DTMAxisTraverser {
        private NamespaceTraverser() {
        }

        @Override
        public int next(int n, int n2) {
            n = n == n2 ? DTMDefaultBaseTraversers.this.getFirstNamespaceNode(n, true) : DTMDefaultBaseTraversers.this.getNextNamespaceNode(n, n2, true);
            return n;
        }

        @Override
        public int next(int n, int n2, int n3) {
            int n4;
            n2 = n == n2 ? DTMDefaultBaseTraversers.this.getFirstNamespaceNode(n, true) : DTMDefaultBaseTraversers.this.getNextNamespaceNode(n, n2, true);
            do {
                if (DTMDefaultBaseTraversers.this.getExpandedTypeID(n2) == n3) {
                    return n2;
                }
                n2 = n4 = DTMDefaultBaseTraversers.this.getNextNamespaceNode(n, n2, true);
            } while (-1 != n4);
            return -1;
        }
    }

    private class ParentTraverser
    extends DTMAxisTraverser {
        private ParentTraverser() {
        }

        @Override
        public int first(int n) {
            return DTMDefaultBaseTraversers.this.getParent(n);
        }

        @Override
        public int first(int n, int n2) {
            block1 : {
                int n3 = DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
                do {
                    n = n3 = DTMDefaultBaseTraversers.this.m_parent.elementAt(n3);
                    if (-1 == n3) break block1;
                    n3 = n;
                } while (DTMDefaultBaseTraversers.this.m_exptype.elementAt(n) != n2);
                return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
            }
            return -1;
        }

        @Override
        public int next(int n, int n2) {
            return -1;
        }

        @Override
        public int next(int n, int n2, int n3) {
            return -1;
        }
    }

    private class PrecedingAndAncestorTraverser
    extends DTMAxisTraverser {
        private PrecedingAndAncestorTraverser() {
        }

        @Override
        public int next(int n, int n2) {
            DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
            for (n = DTMDefaultBaseTraversers.this.makeNodeIdentity((int)n2) - 1; n >= 0; --n) {
                n2 = DTMDefaultBaseTraversers.this._type(n);
                if (2 == n2 || 13 == n2) continue;
                return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
            }
            return -1;
        }

        @Override
        public int next(int n, int n2, int n3) {
            DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
            for (n = DTMDefaultBaseTraversers.this.makeNodeIdentity((int)n2) - 1; n >= 0; --n) {
                if (DTMDefaultBaseTraversers.this.m_exptype.elementAt(n) != n3) {
                    continue;
                }
                return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
            }
            return -1;
        }
    }

    private class PrecedingSiblingTraverser
    extends DTMAxisTraverser {
        private PrecedingSiblingTraverser() {
        }

        @Override
        public int next(int n, int n2) {
            return DTMDefaultBaseTraversers.this.getPreviousSibling(n2);
        }

        @Override
        public int next(int n, int n2, int n3) {
            block1 : {
                do {
                    n = n2 = DTMDefaultBaseTraversers.this.getPreviousSibling(n2);
                    if (-1 == n2) break block1;
                    n2 = n;
                } while (DTMDefaultBaseTraversers.this.getExpandedTypeID(n) != n3);
                return n;
            }
            return -1;
        }
    }

    private class PrecedingTraverser
    extends DTMAxisTraverser {
        private PrecedingTraverser() {
        }

        protected boolean isAncestor(int n, int n2) {
            n = DTMDefaultBaseTraversers.this.m_parent.elementAt(n);
            while (-1 != n) {
                if (n == n2) {
                    return true;
                }
                n = DTMDefaultBaseTraversers.this.m_parent.elementAt(n);
            }
            return false;
        }

        @Override
        public int next(int n, int n2) {
            int n3 = DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
            for (n = DTMDefaultBaseTraversers.this.makeNodeIdentity((int)n2) - 1; n >= 0; --n) {
                n2 = DTMDefaultBaseTraversers.this._type(n);
                if (2 == n2 || 13 == n2 || this.isAncestor(n3, n)) continue;
                return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
            }
            return -1;
        }

        @Override
        public int next(int n, int n2, int n3) {
            int n4 = DTMDefaultBaseTraversers.this.makeNodeIdentity(n);
            for (n = DTMDefaultBaseTraversers.this.makeNodeIdentity((int)n2) - 1; n >= 0; --n) {
                if (DTMDefaultBaseTraversers.this.m_exptype.elementAt(n) != n3 || this.isAncestor(n4, n)) continue;
                return DTMDefaultBaseTraversers.this.makeNodeHandle(n);
            }
            return -1;
        }
    }

    private class RootTraverser
    extends AllFromRootTraverser {
        private RootTraverser() {
        }

        @Override
        public int first(int n, int n2) {
            if (DTMDefaultBaseTraversers.this.getExpandedTypeID(n = DTMDefaultBaseTraversers.this.getDocumentRoot(n)) != n2) {
                n = -1;
            }
            return n;
        }

        @Override
        public int next(int n, int n2) {
            return -1;
        }

        @Override
        public int next(int n, int n2, int n3) {
            return -1;
        }
    }

    private class SelfTraverser
    extends DTMAxisTraverser {
        private SelfTraverser() {
        }

        @Override
        public int first(int n) {
            return n;
        }

        @Override
        public int first(int n, int n2) {
            if (DTMDefaultBaseTraversers.this.getExpandedTypeID(n) != n2) {
                n = -1;
            }
            return n;
        }

        @Override
        public int next(int n, int n2) {
            return -1;
        }

        @Override
        public int next(int n, int n2, int n3) {
            return -1;
        }
    }

}

