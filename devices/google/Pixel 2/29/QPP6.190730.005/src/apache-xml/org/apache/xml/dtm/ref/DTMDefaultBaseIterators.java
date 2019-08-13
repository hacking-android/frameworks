/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.dtm.ref;

import javax.xml.transform.Source;
import org.apache.xml.dtm.Axis;
import org.apache.xml.dtm.DTMAxisIterator;
import org.apache.xml.dtm.DTMAxisTraverser;
import org.apache.xml.dtm.DTMException;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.dtm.DTMWSFilter;
import org.apache.xml.dtm.ref.DTMAxisIteratorBase;
import org.apache.xml.dtm.ref.DTMDefaultBaseTraversers;
import org.apache.xml.dtm.ref.ExpandedNameTable;
import org.apache.xml.res.XMLMessages;
import org.apache.xml.utils.NodeVector;
import org.apache.xml.utils.XMLStringFactory;

public abstract class DTMDefaultBaseIterators
extends DTMDefaultBaseTraversers {
    public DTMDefaultBaseIterators(DTMManager dTMManager, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl) {
        super(dTMManager, source, n, dTMWSFilter, xMLStringFactory, bl);
    }

    public DTMDefaultBaseIterators(DTMManager dTMManager, Source source, int n, DTMWSFilter dTMWSFilter, XMLStringFactory xMLStringFactory, boolean bl, int n2, boolean bl2, boolean bl3) {
        super(dTMManager, source, n, dTMWSFilter, xMLStringFactory, bl, n2, bl2, bl3);
    }

    @Override
    public DTMAxisIterator getAxisIterator(int n) {
        DTMAxisIterator dTMAxisIterator;
        block18 : {
            block17 : {
                if (n == 19) break block17;
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_AXIS_NOT_IMPLEMENTED", new Object[]{Axis.getNames(n)}));
                            }
                            case 13: {
                                dTMAxisIterator = new SingletonIterator();
                                break;
                            }
                            case 12: {
                                dTMAxisIterator = new PrecedingSiblingIterator();
                                break;
                            }
                            case 11: {
                                dTMAxisIterator = new PrecedingIterator();
                                break;
                            }
                            case 10: {
                                return new ParentIterator();
                            }
                            case 9: {
                                dTMAxisIterator = new NamespaceIterator();
                                break;
                            }
                        }
                        break block18;
                    }
                    case 7: {
                        dTMAxisIterator = new FollowingSiblingIterator();
                        break block18;
                    }
                    case 6: {
                        dTMAxisIterator = new FollowingIterator();
                        break block18;
                    }
                    case 5: {
                        dTMAxisIterator = new DescendantIterator().includeSelf();
                        break block18;
                    }
                    case 4: {
                        dTMAxisIterator = new DescendantIterator();
                        break block18;
                    }
                    case 3: {
                        dTMAxisIterator = new ChildrenIterator();
                        break block18;
                    }
                    case 2: {
                        return new AttributeIterator();
                    }
                    case 1: {
                        return new AncestorIterator().includeSelf();
                    }
                    case 0: {
                        return new AncestorIterator();
                    }
                }
            }
            dTMAxisIterator = new RootIterator();
        }
        return dTMAxisIterator;
    }

    @Override
    public DTMAxisIterator getTypedAxisIterator(int n, int n2) {
        DTMAxisIterator dTMAxisIterator;
        block18 : {
            block17 : {
                if (n == 19) break block17;
                switch (n) {
                    default: {
                        switch (n) {
                            default: {
                                throw new DTMException(XMLMessages.createXMLMessage("ER_TYPED_ITERATOR_AXIS_NOT_IMPLEMENTED", new Object[]{Axis.getNames(n)}));
                            }
                            case 13: {
                                dTMAxisIterator = new TypedSingletonIterator(n2);
                                break;
                            }
                            case 12: {
                                dTMAxisIterator = new TypedPrecedingSiblingIterator(n2);
                                break;
                            }
                            case 11: {
                                dTMAxisIterator = new TypedPrecedingIterator(n2);
                                break;
                            }
                            case 10: {
                                return new ParentIterator().setNodeType(n2);
                            }
                            case 9: {
                                dTMAxisIterator = new TypedNamespaceIterator(n2);
                                break;
                            }
                        }
                        break block18;
                    }
                    case 7: {
                        dTMAxisIterator = new TypedFollowingSiblingIterator(n2);
                        break block18;
                    }
                    case 6: {
                        dTMAxisIterator = new TypedFollowingIterator(n2);
                        break block18;
                    }
                    case 5: {
                        dTMAxisIterator = new TypedDescendantIterator(n2).includeSelf();
                        break block18;
                    }
                    case 4: {
                        dTMAxisIterator = new TypedDescendantIterator(n2);
                        break block18;
                    }
                    case 3: {
                        dTMAxisIterator = new TypedChildrenIterator(n2);
                        break block18;
                    }
                    case 2: {
                        return new TypedAttributeIterator(n2);
                    }
                    case 1: {
                        return new TypedAncestorIterator(n2).includeSelf();
                    }
                    case 0: {
                        return new TypedAncestorIterator(n2);
                    }
                }
            }
            dTMAxisIterator = new TypedRootIterator(n2);
        }
        return dTMAxisIterator;
    }

    public class AncestorIterator
    extends InternalAxisIteratorBase {
        NodeVector m_ancestors;
        int m_ancestorsPos;
        int m_markedPos;
        int m_realStartNode;

        public AncestorIterator() {
            this.m_ancestors = new NodeVector();
        }

        @Override
        public DTMAxisIterator cloneIterator() {
            this._isRestartable = false;
            try {
                AncestorIterator ancestorIterator = (AncestorIterator)Object.super.clone();
                ancestorIterator._startNode = this._startNode;
                return ancestorIterator;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
            }
        }

        @Override
        public int getStartNode() {
            return this.m_realStartNode;
        }

        @Override
        public void gotoMark() {
            this.m_ancestorsPos = this.m_markedPos;
            int n = this.m_ancestorsPos;
            n = n >= 0 ? this.m_ancestors.elementAt(n) : -1;
            this._currentNode = n;
        }

        @Override
        public final boolean isReverse() {
            return true;
        }

        @Override
        public int next() {
            int n;
            int n2 = this._currentNode;
            this.m_ancestorsPos = n = this.m_ancestorsPos - 1;
            n = n >= 0 ? this.m_ancestors.elementAt(this.m_ancestorsPos) : -1;
            this._currentNode = n;
            return this.returnNode(n2);
        }

        @Override
        public DTMAxisIterator reset() {
            this.m_ancestorsPos = this.m_ancestors.size() - 1;
            int n = this.m_ancestorsPos;
            n = n >= 0 ? this.m_ancestors.elementAt(n) : -1;
            this._currentNode = n;
            return this.resetPosition();
        }

        @Override
        public void setMark() {
            this.m_markedPos = this.m_ancestorsPos;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            this.m_realStartNode = n2;
            if (this._isRestartable) {
                int n3 = DTMDefaultBaseIterators.this.makeNodeIdentity(n2);
                boolean bl = this._includeSelf;
                int n4 = -1;
                n = n3;
                int n5 = n2;
                if (!bl) {
                    n = n3;
                    n5 = n2;
                    if (n2 != -1) {
                        n = DTMDefaultBaseIterators.this._parent(n3);
                        n5 = DTMDefaultBaseIterators.this.makeNodeHandle(n);
                    }
                }
                this._startNode = n5;
                while (n != -1) {
                    this.m_ancestors.addElement(n5);
                    n = DTMDefaultBaseIterators.this._parent(n);
                    n5 = DTMDefaultBaseIterators.this.makeNodeHandle(n);
                }
                this.m_ancestorsPos = this.m_ancestors.size() - 1;
                n = this.m_ancestorsPos;
                n = n >= 0 ? this.m_ancestors.elementAt(n) : n4;
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class AttributeIterator
    extends InternalAxisIteratorBase {
        @Override
        public int next() {
            int n = this._currentNode;
            if (n != -1) {
                this._currentNode = DTMDefaultBaseIterators.this.getNextAttributeIdentity(n);
                return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(n));
            }
            return -1;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                DTMDefaultBaseIterators dTMDefaultBaseIterators = DTMDefaultBaseIterators.this;
                this._currentNode = dTMDefaultBaseIterators.getFirstAttributeIdentity(dTMDefaultBaseIterators.makeNodeIdentity(n2));
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class ChildrenIterator
    extends InternalAxisIteratorBase {
        @Override
        public int next() {
            if (this._currentNode != -1) {
                int n = this._currentNode;
                this._currentNode = DTMDefaultBaseIterators.this._nextsib(n);
                return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(n));
            }
            return -1;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                n = -1;
                if (n2 != -1) {
                    DTMDefaultBaseIterators dTMDefaultBaseIterators = DTMDefaultBaseIterators.this;
                    n = dTMDefaultBaseIterators._firstch(dTMDefaultBaseIterators.makeNodeIdentity(n2));
                }
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public class DescendantIterator
    extends InternalAxisIteratorBase {
        protected boolean isDescendant(int n) {
            boolean bl = DTMDefaultBaseIterators.this._parent(n) >= this._startNode || this._startNode == n;
            return bl;
        }

        @Override
        public int next() {
            int n;
            short s;
            if (this._startNode == -1) {
                return -1;
            }
            if (this._includeSelf && this._currentNode + 1 == this._startNode) {
                int n2;
                DTMDefaultBaseIterators dTMDefaultBaseIterators = DTMDefaultBaseIterators.this;
                this._currentNode = n2 = this._currentNode + 1;
                return this.returnNode(dTMDefaultBaseIterators.makeNodeHandle(n2));
            }
            int n3 = this._currentNode;
            while (-1 != (s = DTMDefaultBaseIterators.this._type(n = n3 + 1)) && this.isDescendant(n)) {
                n3 = n;
                if (2 == s) continue;
                n3 = n;
                if (3 == s) continue;
                n3 = n;
                if (13 == s) continue;
                this._currentNode = n;
                return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(n));
            }
            this._currentNode = -1;
            return -1;
        }

        @Override
        public DTMAxisIterator reset() {
            boolean bl = this._isRestartable;
            this._isRestartable = true;
            this.setStartNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._startNode));
            this._isRestartable = bl;
            return this;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2 = DTMDefaultBaseIterators.this.makeNodeIdentity(n2);
                n = n2;
                if (this._includeSelf) {
                    n = n2 - 1;
                }
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public class FollowingIterator
    extends InternalAxisIteratorBase {
        DTMAxisTraverser m_traverser;

        public FollowingIterator() {
            this.m_traverser = DTMDefaultBaseIterators.this.getAxisTraverser(6);
        }

        @Override
        public int next() {
            int n = this._currentNode;
            this._currentNode = this.m_traverser.next(this._startNode, this._currentNode);
            return this.returnNode(n);
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._currentNode = this.m_traverser.first(n2);
                return this.resetPosition();
            }
            return this;
        }
    }

    public class FollowingSiblingIterator
    extends InternalAxisIteratorBase {
        @Override
        public int next() {
            int n = this._currentNode;
            int n2 = -1;
            if (n != -1) {
                n2 = DTMDefaultBaseIterators.this._nextsib(this._currentNode);
            }
            this._currentNode = n2;
            return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._currentNode));
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._currentNode = DTMDefaultBaseIterators.this.makeNodeIdentity(n2);
                return this.resetPosition();
            }
            return this;
        }
    }

    public abstract class InternalAxisIteratorBase
    extends DTMAxisIteratorBase {
        protected int _currentNode;

        @Override
        public void gotoMark() {
            this._currentNode = this._markedNode;
        }

        @Override
        public void setMark() {
            this._markedNode = this._currentNode;
        }
    }

    public final class NamespaceAttributeIterator
    extends InternalAxisIteratorBase {
        private final int _nsType;

        public NamespaceAttributeIterator(int n) {
            this._nsType = n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            if (-1 != n) {
                this._currentNode = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, n, false);
            }
            return this.returnNode(n);
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._currentNode = DTMDefaultBaseIterators.this.getFirstNamespaceNode(n2, false);
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class NamespaceChildrenIterator
    extends InternalAxisIteratorBase {
        private final int _nsType;

        public NamespaceChildrenIterator(int n) {
            this._nsType = n;
        }

        @Override
        public int next() {
            if (this._currentNode != -1) {
                int n;
                if (-2 == this._currentNode) {
                    DTMDefaultBaseIterators dTMDefaultBaseIterators = DTMDefaultBaseIterators.this;
                    n = dTMDefaultBaseIterators._firstch(dTMDefaultBaseIterators.makeNodeIdentity(this._startNode));
                } else {
                    n = DTMDefaultBaseIterators.this._nextsib(this._currentNode);
                }
                while (n != -1) {
                    if (DTMDefaultBaseIterators.this.m_expandedNameTable.getNamespaceID(DTMDefaultBaseIterators.this._exptype(n)) == this._nsType) {
                        this._currentNode = n;
                        return this.returnNode(n);
                    }
                    n = DTMDefaultBaseIterators.this._nextsib(n);
                }
            }
            return -1;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                n = -1;
                if (n2 != -1) {
                    n = -2;
                }
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public class NamespaceIterator
    extends InternalAxisIteratorBase {
        @Override
        public int next() {
            int n = this._currentNode;
            if (-1 != n) {
                this._currentNode = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, n, true);
            }
            return this.returnNode(n);
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._currentNode = DTMDefaultBaseIterators.this.getFirstNamespaceNode(n2, true);
                return this.resetPosition();
            }
            return this;
        }
    }

    public class NthDescendantIterator
    extends DescendantIterator {
        int _pos;

        public NthDescendantIterator(int n) {
            this._pos = n;
        }

        @Override
        public int next() {
            int n;
            while ((n = super.next()) != -1) {
                int n2;
                int n3;
                int n4 = DTMDefaultBaseIterators.this.makeNodeIdentity(n);
                n = DTMDefaultBaseIterators.this._parent(n4);
                n = DTMDefaultBaseIterators.this._firstch(n);
                int n5 = 0;
                do {
                    int n6 = n5;
                    if (1 == DTMDefaultBaseIterators.this._type(n)) {
                        n6 = n5 + 1;
                    }
                    n3 = n;
                    if (n6 >= this._pos) break;
                    n = n3 = (n2 = DTMDefaultBaseIterators.this._nextsib(n));
                    n5 = n6;
                } while (n2 != -1);
                if (n4 != n3) continue;
                return n4;
            }
            return -1;
        }
    }

    public final class ParentIterator
    extends InternalAxisIteratorBase {
        private int _nodeType = -1;

        @Override
        public int next() {
            int n;
            int n2 = this._currentNode;
            int n3 = this._nodeType;
            if (n3 >= 14) {
                n = n2;
                if (n3 != DTMDefaultBaseIterators.this.getExpandedTypeID(this._currentNode)) {
                    n = -1;
                }
            } else {
                n = n2;
                if (n3 != -1) {
                    n = n2;
                    if (n3 != DTMDefaultBaseIterators.this.getNodeType(this._currentNode)) {
                        n = -1;
                    }
                }
            }
            this._currentNode = -1;
            return this.returnNode(n);
        }

        public DTMAxisIterator setNodeType(int n) {
            this._nodeType = n;
            return this;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._currentNode = DTMDefaultBaseIterators.this.getParent(n2);
                return this.resetPosition();
            }
            return this;
        }
    }

    public class PrecedingIterator
    extends InternalAxisIteratorBase {
        protected int _markedDescendant;
        protected int _markedNode;
        protected int _markedsp;
        private final int _maxAncestors;
        protected int _oldsp;
        protected int _sp;
        protected int[] _stack = new int[8];

        public PrecedingIterator() {
            this._maxAncestors = 8;
        }

        @Override
        public DTMAxisIterator cloneIterator() {
            this._isRestartable = false;
            try {
                PrecedingIterator precedingIterator = (PrecedingIterator)Object.super.clone();
                int[] arrn = new int[this._stack.length];
                System.arraycopy(this._stack, 0, arrn, 0, this._stack.length);
                precedingIterator._stack = arrn;
                return precedingIterator;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                throw new DTMException(XMLMessages.createXMLMessage("ER_ITERATOR_CLONE_NOT_SUPPORTED", null));
            }
        }

        @Override
        public void gotoMark() {
            this._sp = this._markedsp;
            this._currentNode = this._markedNode;
        }

        @Override
        public boolean isReverse() {
            return true;
        }

        @Override
        public int next() {
            ++this._currentNode;
            while (this._sp >= 0) {
                int n = this._currentNode;
                int[] arrn = this._stack;
                int n2 = this._sp;
                if (n < arrn[n2]) {
                    if (DTMDefaultBaseIterators.this._type(this._currentNode) != 2 && DTMDefaultBaseIterators.this._type(this._currentNode) != 13) {
                        return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(this._currentNode));
                    }
                } else {
                    this._sp = n2 - 1;
                }
                ++this._currentNode;
            }
            return -1;
        }

        @Override
        public DTMAxisIterator reset() {
            this._sp = this._oldsp;
            return this.resetPosition();
        }

        @Override
        public void setMark() {
            this._markedsp = this._sp;
            this._markedNode = this._currentNode;
            this._markedDescendant = this._stack[0];
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                n = n2 = DTMDefaultBaseIterators.this.makeNodeIdentity(n2);
                if (DTMDefaultBaseIterators.this._type(n2) == 2) {
                    n = DTMDefaultBaseIterators.this._parent(n2);
                }
                this._startNode = n;
                int[] arrn = this._stack;
                int n3 = 0;
                arrn[0] = n;
                n2 = n;
                n = n3;
                do {
                    n2 = n3 = DTMDefaultBaseIterators.this._parent(n2);
                    if (n3 == -1) break;
                    arrn = this._stack;
                    if (++n == arrn.length) {
                        int[] arrn2 = new int[n + 4];
                        System.arraycopy(arrn, 0, arrn2, 0, n);
                        this._stack = arrn2;
                    }
                    this._stack[n] = n2;
                } while (true);
                n2 = n;
                if (n > 0) {
                    n2 = n - 1;
                }
                this._currentNode = this._stack[n2];
                this._sp = n2;
                this._oldsp = n2;
                return this.resetPosition();
            }
            return this;
        }
    }

    public class PrecedingSiblingIterator
    extends InternalAxisIteratorBase {
        protected int _startNodeID;

        @Override
        public boolean isReverse() {
            return true;
        }

        @Override
        public int next() {
            if (this._currentNode != this._startNodeID && this._currentNode != -1) {
                int n = this._currentNode;
                this._currentNode = DTMDefaultBaseIterators.this._nextsib(n);
                return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(n));
            }
            return -1;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._startNodeID = n2 = DTMDefaultBaseIterators.this.makeNodeIdentity(n2);
                if (n2 == -1) {
                    this._currentNode = n2;
                    return this.resetPosition();
                }
                n = DTMDefaultBaseIterators.this.m_expandedNameTable.getType(DTMDefaultBaseIterators.this._exptype(n2));
                if (2 != n && 13 != n) {
                    this._currentNode = DTMDefaultBaseIterators.this._parent(n2);
                    this._currentNode = -1 != this._currentNode ? DTMDefaultBaseIterators.this._firstch(this._currentNode) : n2;
                } else {
                    this._currentNode = n2;
                }
                return this.resetPosition();
            }
            return this;
        }
    }

    public class RootIterator
    extends InternalAxisIteratorBase {
        @Override
        public int next() {
            if (this._startNode == this._currentNode) {
                return -1;
            }
            this._currentNode = this._startNode;
            return this.returnNode(this._startNode);
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            if (this._isRestartable) {
                this._startNode = DTMDefaultBaseIterators.this.getDocumentRoot(n);
                this._currentNode = -1;
                return this.resetPosition();
            }
            return this;
        }
    }

    public class SingletonIterator
    extends InternalAxisIteratorBase {
        private boolean _isConstant;

        public SingletonIterator() {
            this(Integer.MIN_VALUE, false);
        }

        public SingletonIterator(int n) {
            this(n, false);
        }

        public SingletonIterator(int n, boolean bl) {
            this._startNode = n;
            this._currentNode = n;
            this._isConstant = bl;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            this._currentNode = -1;
            return this.returnNode(n);
        }

        @Override
        public DTMAxisIterator reset() {
            if (this._isConstant) {
                this._currentNode = this._startNode;
                return this.resetPosition();
            }
            boolean bl = this._isRestartable;
            this._isRestartable = true;
            this.setStartNode(this._startNode);
            this._isRestartable = bl;
            return this;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isConstant) {
                this._currentNode = this._startNode;
                return this.resetPosition();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                this._currentNode = n2;
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class TypedAncestorIterator
    extends AncestorIterator {
        private final int _nodeType;

        public TypedAncestorIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            this.m_realStartNode = n2;
            if (this._isRestartable) {
                int n3 = DTMDefaultBaseIterators.this.makeNodeIdentity(n2);
                int n4 = this._nodeType;
                boolean bl = this._includeSelf;
                int n5 = -1;
                n = n3;
                if (!bl) {
                    n = n3;
                    if (n2 != -1) {
                        n = DTMDefaultBaseIterators.this._parent(n3);
                    }
                }
                this._startNode = n2;
                n2 = n;
                if (n4 >= 14) {
                    while (n != -1) {
                        if (DTMDefaultBaseIterators.this._exptype(n) == n4) {
                            this.m_ancestors.addElement(DTMDefaultBaseIterators.this.makeNodeHandle(n));
                        }
                        n = DTMDefaultBaseIterators.this._parent(n);
                    }
                } else {
                    while (n2 != -1) {
                        n = DTMDefaultBaseIterators.this._exptype(n2);
                        if (n >= 14 && DTMDefaultBaseIterators.this.m_expandedNameTable.getType(n) == n4 || n < 14 && n == n4) {
                            this.m_ancestors.addElement(DTMDefaultBaseIterators.this.makeNodeHandle(n2));
                        }
                        n2 = DTMDefaultBaseIterators.this._parent(n2);
                    }
                }
                this.m_ancestorsPos = this.m_ancestors.size() - 1;
                n = this.m_ancestorsPos >= 0 ? this.m_ancestors.elementAt(this.m_ancestorsPos) : n5;
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class TypedAttributeIterator
    extends InternalAxisIteratorBase {
        private final int _nodeType;

        public TypedAttributeIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            this._currentNode = -1;
            return this.returnNode(n);
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            if (this._isRestartable) {
                this._startNode = n;
                this._currentNode = DTMDefaultBaseIterators.this.getTypedAttribute(n, this._nodeType);
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class TypedChildrenIterator
    extends InternalAxisIteratorBase {
        private final int _nodeType;

        public TypedChildrenIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            int n2 = this._nodeType;
            int n3 = n;
            if (n2 >= 14) {
                n3 = n;
                do {
                    n = n3;
                    if (n3 != -1) {
                        n = n3;
                        if (DTMDefaultBaseIterators.this._exptype(n3) != n2) {
                            n3 = DTMDefaultBaseIterators.this._nextsib(n3);
                            continue;
                        }
                    }
                    break;
                } while (true);
            } else {
                do {
                    n = n3;
                    if (n3 == -1) break;
                    n = DTMDefaultBaseIterators.this._exptype(n3);
                    if (n < 14) {
                        if (n == n2) {
                            n = n3;
                            break;
                        }
                    } else if (DTMDefaultBaseIterators.this.m_expandedNameTable.getType(n) == n2) {
                        n = n3;
                        break;
                    }
                    n3 = DTMDefaultBaseIterators.this._nextsib(n3);
                } while (true);
            }
            if (n == -1) {
                this._currentNode = -1;
                return -1;
            }
            this._currentNode = DTMDefaultBaseIterators.this._nextsib(n);
            return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(n));
        }

        @Override
        public DTMAxisIterator setStartNode(int n) {
            int n2 = n;
            if (n == 0) {
                n2 = DTMDefaultBaseIterators.this.getDocument();
            }
            if (this._isRestartable) {
                this._startNode = n2;
                n = -1;
                if (n2 != -1) {
                    DTMDefaultBaseIterators dTMDefaultBaseIterators = DTMDefaultBaseIterators.this;
                    n = dTMDefaultBaseIterators._firstch(dTMDefaultBaseIterators.makeNodeIdentity(this._startNode));
                }
                this._currentNode = n;
                return this.resetPosition();
            }
            return this;
        }
    }

    public final class TypedDescendantIterator
    extends DescendantIterator {
        private final int _nodeType;

        public TypedDescendantIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            block2 : {
                int n;
                if (this._startNode == -1) {
                    return -1;
                }
                int n2 = this._currentNode;
                do {
                    n = n2 + 1;
                    n2 = DTMDefaultBaseIterators.this._type(n);
                    if (-1 == n2 || !this.isDescendant(n)) break block2;
                    if (n2 == this._nodeType) break;
                    n2 = n;
                } while (DTMDefaultBaseIterators.this._exptype(n) != this._nodeType);
                this._currentNode = n;
                return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(n));
            }
            this._currentNode = -1;
            return -1;
        }
    }

    public final class TypedFollowingIterator
    extends FollowingIterator {
        private final int _nodeType;

        public TypedFollowingIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            int n;
            int n2;
            do {
                n2 = this._currentNode;
                this._currentNode = this.m_traverser.next(this._startNode, this._currentNode);
                n = -1;
            } while (n2 != -1 && DTMDefaultBaseIterators.this.getExpandedTypeID(n2) != this._nodeType && DTMDefaultBaseIterators.this.getNodeType(n2) != this._nodeType);
            if (n2 != -1) {
                n = this.returnNode(n2);
            }
            return n;
        }
    }

    public final class TypedFollowingSiblingIterator
    extends FollowingSiblingIterator {
        private final int _nodeType;

        public TypedFollowingSiblingIterator(int n) {
            this._nodeType = n;
        }

        /*
         * Exception decompiling
         */
        @Override
        public int next() {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[DOLOOP]], but top level block is 3[SIMPLE_IF_TAKEN]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }
    }

    public class TypedNamespaceIterator
    extends NamespaceIterator {
        private final int _nodeType;

        public TypedNamespaceIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            while (n != -1) {
                if (DTMDefaultBaseIterators.this.getExpandedTypeID(n) != this._nodeType && DTMDefaultBaseIterators.this.getNodeType(n) != this._nodeType && DTMDefaultBaseIterators.this.getNamespaceType(n) != this._nodeType) {
                    n = DTMDefaultBaseIterators.this.getNextNamespaceNode(this._startNode, n, true);
                    continue;
                }
                this._currentNode = n;
                return this.returnNode(n);
            }
            this._currentNode = -1;
            return -1;
        }
    }

    public final class TypedPrecedingIterator
    extends PrecedingIterator {
        private final int _nodeType;

        public TypedPrecedingIterator(int n) {
            this._nodeType = n;
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        @Override
        public int next() {
            block4 : {
                var1_1 = this._currentNode;
                var2_2 = this._nodeType;
                var3_3 = var1_1;
                if (var2_2 < 14) break block4;
                var3_3 = var1_1;
                do lbl-1000: // 3 sources:
                {
                    block6 : {
                        block5 : {
                            var1_1 = var3_3 + 1;
                            if (this._sp >= 0) break block5;
                            var3_3 = -1;
                            ** GOTO lbl46
                        }
                        if (var1_1 < this._stack[this._sp]) break block6;
                        this._sp = var4_4 = this._sp - 1;
                        var3_3 = var1_1;
                        if (var4_4 >= 0) ** GOTO lbl-1000
                        var3_3 = -1;
                        ** GOTO lbl46
                    }
                    var3_3 = var1_1;
                } while (DTMDefaultBaseIterators.this._exptype(var1_1) != var2_2);
                var3_3 = var1_1;
                ** GOTO lbl46
            }
            do {
                block11 : {
                    block8 : {
                        block10 : {
                            block9 : {
                                block7 : {
                                    var1_1 = var3_3 + 1;
                                    if (this._sp >= 0) break block7;
                                    var3_3 = -1;
                                    break block8;
                                }
                                if (var1_1 < this._stack[this._sp]) break block9;
                                this._sp = var4_5 = this._sp - 1;
                                var3_3 = var1_1;
                                if (var4_5 >= 0) continue;
                                var3_3 = -1;
                                break block8;
                            }
                            var4_5 = DTMDefaultBaseIterators.this._exptype(var1_1);
                            if (var4_5 >= 14) break block10;
                            var3_3 = var1_1;
                            if (var4_5 != var2_2) continue;
                            var3_3 = var1_1;
                            break block8;
                        }
                        if (DTMDefaultBaseIterators.this.m_expandedNameTable.getType(var4_5) != var2_2) break block11;
                        var3_3 = var1_1;
                    }
                    this._currentNode = var3_3;
                    var1_1 = -1;
                    if (var3_3 != -1) return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(var3_3));
                    return var1_1;
                }
                var3_3 = var1_1;
            } while (true);
        }
    }

    public final class TypedPrecedingSiblingIterator
    extends PrecedingSiblingIterator {
        private final int _nodeType;

        public TypedPrecedingSiblingIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            int n2 = this._nodeType;
            int n3 = this._startNodeID;
            int n4 = n;
            if (n2 >= 14) {
                n4 = n;
                do {
                    n = n4;
                    if (n4 != -1) {
                        n = n4;
                        if (n4 != n3) {
                            n = n4;
                            if (DTMDefaultBaseIterators.this._exptype(n4) != n2) {
                                n4 = DTMDefaultBaseIterators.this._nextsib(n4);
                                continue;
                            }
                        }
                    }
                    break;
                } while (true);
            } else {
                do {
                    n = n4;
                    if (n4 == -1) break;
                    n = n4;
                    if (n4 == n3) break;
                    n = DTMDefaultBaseIterators.this._exptype(n4);
                    if (n < 14) {
                        if (n == n2) {
                            n = n4;
                            break;
                        }
                    } else if (DTMDefaultBaseIterators.this.m_expandedNameTable.getType(n) == n2) {
                        n = n4;
                        break;
                    }
                    n4 = DTMDefaultBaseIterators.this._nextsib(n4);
                } while (true);
            }
            if (n != -1 && n != this._startNodeID) {
                this._currentNode = DTMDefaultBaseIterators.this._nextsib(n);
                return this.returnNode(DTMDefaultBaseIterators.this.makeNodeHandle(n));
            }
            this._currentNode = -1;
            return -1;
        }
    }

    public class TypedRootIterator
    extends RootIterator {
        private final int _nodeType;

        public TypedRootIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            if (this._startNode == this._currentNode) {
                return -1;
            }
            int n = this._nodeType;
            int n2 = this._startNode;
            int n3 = DTMDefaultBaseIterators.this.getExpandedTypeID(n2);
            this._currentNode = n2;
            if (n >= 14 ? n == n3 : (n3 < 14 ? n3 == n : DTMDefaultBaseIterators.this.m_expandedNameTable.getType(n3) == n)) {
                return this.returnNode(n2);
            }
            return -1;
        }
    }

    public final class TypedSingletonIterator
    extends SingletonIterator {
        private final int _nodeType;

        public TypedSingletonIterator(int n) {
            this._nodeType = n;
        }

        @Override
        public int next() {
            int n = this._currentNode;
            int n2 = this._nodeType;
            this._currentNode = -1;
            if (n2 >= 14 ? DTMDefaultBaseIterators.this.getExpandedTypeID(n) == n2 : DTMDefaultBaseIterators.this.getNodeType(n) == n2) {
                return this.returnNode(n);
            }
            return -1;
        }
    }

}

