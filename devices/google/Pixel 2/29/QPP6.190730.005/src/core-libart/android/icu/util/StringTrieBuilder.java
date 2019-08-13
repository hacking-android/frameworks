/*
 * Decompiled with CFR 0.145.
 */
package android.icu.util;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class StringTrieBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private ValueNode lookupFinalValueNode = new ValueNode();
    private HashMap<Node, Node> nodes = new HashMap();
    private Node root;
    private State state = State.ADDING;
    @Deprecated
    protected StringBuilder strings = new StringBuilder();

    @Deprecated
    protected StringTrieBuilder() {
    }

    private ValueNode createSuffixNode(CharSequence charSequence, int n, int n2) {
        ValueNode valueNode;
        ValueNode valueNode2 = valueNode = this.registerFinalValue(n2);
        if (n < charSequence.length()) {
            n2 = this.strings.length();
            this.strings.append(charSequence, n, charSequence.length());
            valueNode2 = new LinearMatchNode(this.strings, n2, charSequence.length() - n, valueNode);
        }
        return valueNode2;
    }

    private final ValueNode registerFinalValue(int n) {
        this.lookupFinalValueNode.setFinalValue(n);
        Node node = this.nodes.get(this.lookupFinalValueNode);
        if (node != null) {
            return (ValueNode)node;
        }
        node = new ValueNode(n);
        Node node2 = this.nodes.put(node, node);
        return node;
    }

    private final Node registerNode(Node node) {
        if (this.state == State.BUILDING_FAST) {
            return node;
        }
        Node node2 = this.nodes.get(node);
        if (node2 != null) {
            return node2;
        }
        node2 = this.nodes.put(node, node);
        return node;
    }

    @Deprecated
    protected void addImpl(CharSequence charSequence, int n) {
        if (this.state == State.ADDING) {
            if (charSequence.length() <= 65535) {
                Node node = this.root;
                this.root = node == null ? this.createSuffixNode(charSequence, 0, n) : node.add(this, charSequence, 0, n);
                return;
            }
            throw new IndexOutOfBoundsException("The maximum string length is 0xffff.");
        }
        throw new IllegalStateException("Cannot add (string, value) pairs after build().");
    }

    @Deprecated
    protected final void buildImpl(Option option) {
        block8 : {
            block7 : {
                block5 : {
                    block6 : {
                        int n = 1.$SwitchMap$android$icu$util$StringTrieBuilder$State[this.state.ordinal()];
                        if (n == 1) break block5;
                        if (n == 2 || n == 3) break block6;
                        if (n == 4) {
                            return;
                        }
                        break block7;
                    }
                    throw new IllegalStateException("Builder failed and must be clear()ed.");
                }
                if (this.root == null) break block8;
                this.state = option == Option.FAST ? State.BUILDING_FAST : State.BUILDING_SMALL;
            }
            this.root = this.root.register(this);
            this.root.markRightEdgesFirst(-1);
            this.root.write(this);
            this.state = State.BUILT;
            return;
        }
        throw new IndexOutOfBoundsException("No (string, value) pairs were added.");
    }

    @Deprecated
    protected void clearImpl() {
        this.strings.setLength(0);
        this.nodes.clear();
        this.root = null;
        this.state = State.ADDING;
    }

    @Deprecated
    protected abstract int getMaxBranchLinearSubNodeLength();

    @Deprecated
    protected abstract int getMaxLinearMatchLength();

    @Deprecated
    protected abstract int getMinLinearMatch();

    @Deprecated
    protected abstract boolean matchNodesCanHaveValues();

    @Deprecated
    protected abstract int write(int var1);

    @Deprecated
    protected abstract int write(int var1, int var2);

    @Deprecated
    protected abstract int writeDeltaTo(int var1);

    @Deprecated
    protected abstract int writeValueAndFinal(int var1, boolean var2);

    @Deprecated
    protected abstract int writeValueAndType(boolean var1, int var2, int var3);

    private static final class BranchHeadNode
    extends ValueNode {
        private int length;
        private Node next;

        public BranchHeadNode(int n, Node node) {
            this.length = n;
            this.next = node;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            object = (BranchHeadNode)object;
            if (this.length != ((BranchHeadNode)object).length || this.next != ((BranchHeadNode)object).next) {
                bl = false;
            }
            return bl;
        }

        @Override
        public int hashCode() {
            return (this.length + 248302782) * 37 + this.next.hashCode();
        }

        @Override
        public int markRightEdgesFirst(int n) {
            int n2 = n;
            if (this.offset == 0) {
                n = n2 = this.next.markRightEdgesFirst(n);
                this.offset = n2;
                n2 = n;
            }
            return n2;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            if (this.length <= stringTrieBuilder.getMinLinearMatch()) {
                this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, this.length - 1);
            } else {
                stringTrieBuilder.write(this.length - 1);
                this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, 0);
            }
        }
    }

    private static abstract class BranchNode
    extends Node {
        protected int firstEdgeNumber;
        protected int hash;

        @Override
        public int hashCode() {
            return this.hash;
        }
    }

    private static final class DynamicBranchNode
    extends ValueNode {
        private StringBuilder chars = new StringBuilder();
        private ArrayList<Node> equal = new ArrayList();

        private int find(char c) {
            int n = 0;
            int n2 = this.chars.length();
            while (n < n2) {
                int n3 = (n + n2) / 2;
                char c2 = this.chars.charAt(n3);
                if (c < c2) {
                    n2 = n3;
                    continue;
                }
                if (c == c2) {
                    return n3;
                }
                n = n3 + 1;
            }
            return n;
        }

        private Node register(StringTrieBuilder stringTrieBuilder, int n, int n2) {
            int n3 = n2 - n;
            if (n3 > stringTrieBuilder.getMaxBranchLinearSubNodeLength()) {
                n3 = n3 / 2 + n;
                return stringTrieBuilder.registerNode(new SplitBranchNode(this.chars.charAt(n3), this.register(stringTrieBuilder, n, n3), this.register(stringTrieBuilder, n3, n2)));
            }
            ListBranchNode listBranchNode = new ListBranchNode(n3);
            do {
                n3 = this.chars.charAt(n);
                Node node = this.equal.get(n);
                if (node.getClass() == ValueNode.class) {
                    listBranchNode.add(n3, ((ValueNode)node).value);
                    continue;
                }
                listBranchNode.add(n3, node.register(stringTrieBuilder));
            } while (++n < n2);
            return stringTrieBuilder.registerNode(listBranchNode);
        }

        @Override
        public Node add(StringTrieBuilder stringTrieBuilder, CharSequence charSequence, int n, int n2) {
            if (n == charSequence.length()) {
                if (!this.hasValue) {
                    this.setValue(n2);
                    return this;
                }
                throw new IllegalArgumentException("Duplicate string.");
            }
            int n3 = n + 1;
            char c = charSequence.charAt(n);
            n = this.find(c);
            if (n < this.chars.length() && c == this.chars.charAt(n)) {
                ArrayList<Node> arrayList = this.equal;
                arrayList.set(n, arrayList.get(n).add(stringTrieBuilder, charSequence, n3, n2));
            } else {
                this.chars.insert(n, c);
                this.equal.add(n, stringTrieBuilder.createSuffixNode(charSequence, n3, n2));
            }
            return this;
        }

        public void add(char c, Node node) {
            int n = this.find(c);
            this.chars.insert(n, c);
            this.equal.add(n, node);
        }

        @Override
        public Node register(StringTrieBuilder stringTrieBuilder) {
            Node node = this.register(stringTrieBuilder, 0, this.chars.length());
            BranchHeadNode branchHeadNode = new BranchHeadNode(this.chars.length(), node);
            Node node2 = node = branchHeadNode;
            if (this.hasValue) {
                if (stringTrieBuilder.matchNodesCanHaveValues()) {
                    branchHeadNode.setValue(this.value);
                    node2 = node;
                } else {
                    node2 = new IntermediateValueNode(this.value, stringTrieBuilder.registerNode(branchHeadNode));
                }
            }
            return stringTrieBuilder.registerNode(node2);
        }
    }

    private static final class IntermediateValueNode
    extends ValueNode {
        private Node next;

        public IntermediateValueNode(int n, Node node) {
            this.next = node;
            this.setValue(n);
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            object = (IntermediateValueNode)object;
            if (this.next != ((IntermediateValueNode)object).next) {
                bl = false;
            }
            return bl;
        }

        @Override
        public int hashCode() {
            return (this.value + 82767594) * 37 + this.next.hashCode();
        }

        @Override
        public int markRightEdgesFirst(int n) {
            int n2 = n;
            if (this.offset == 0) {
                n2 = n = this.next.markRightEdgesFirst(n);
                this.offset = n;
            }
            return n2;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            this.offset = stringTrieBuilder.writeValueAndFinal(this.value, false);
        }
    }

    private static final class LinearMatchNode
    extends ValueNode {
        private int hash;
        private int length;
        private Node next;
        private int stringOffset;
        private CharSequence strings;

        public LinearMatchNode(CharSequence charSequence, int n, int n2, Node node) {
            this.strings = charSequence;
            this.stringOffset = n;
            this.length = n2;
            this.next = node;
        }

        private void setHashCode() {
            this.hash = (this.length + 124151391) * 37 + this.next.hashCode();
            if (this.hasValue) {
                this.hash = this.hash * 37 + this.value;
            }
            int n = this.stringOffset;
            int n2 = this.length;
            for (int i = this.stringOffset; i < n + n2; ++i) {
                this.hash = this.hash * 37 + this.strings.charAt(i);
            }
        }

        @Override
        public Node add(StringTrieBuilder object, CharSequence charSequence, int n, int n2) {
            if (n == charSequence.length()) {
                if (!this.hasValue) {
                    this.setValue(n2);
                    return this;
                }
                throw new IllegalArgumentException("Duplicate string.");
            }
            int n3 = this.stringOffset + this.length;
            int n4 = this.stringOffset;
            int n5 = n;
            n = n4;
            while (n < n3) {
                char c;
                if (n5 == charSequence.length()) {
                    n5 = n - this.stringOffset;
                    object = new LinearMatchNode(this.strings, n, this.length - n5, this.next);
                    ((ValueNode)object).setValue(n2);
                    this.length = n5;
                    this.next = object;
                    return this;
                }
                char c2 = this.strings.charAt(n);
                if (c2 != (c = charSequence.charAt(n5))) {
                    Node node;
                    ValueNode valueNode;
                    DynamicBranchNode dynamicBranchNode = new DynamicBranchNode();
                    n4 = this.stringOffset;
                    if (n == n4) {
                        if (this.hasValue) {
                            dynamicBranchNode.setValue(this.value);
                            this.value = 0;
                            this.hasValue = false;
                        }
                        ++this.stringOffset;
                        --this.length;
                        node = this.length > 0 ? this : this.next;
                        valueNode = dynamicBranchNode;
                    } else if (n == n3 - 1) {
                        --this.length;
                        node = this.next;
                        this.next = dynamicBranchNode;
                        valueNode = this;
                    } else {
                        n4 = n - n4;
                        node = new LinearMatchNode(this.strings, n + 1, this.length - (n4 + 1), this.next);
                        this.length = n4;
                        this.next = dynamicBranchNode;
                        valueNode = this;
                    }
                    object = ((StringTrieBuilder)object).createSuffixNode(charSequence, n5 + 1, n2);
                    dynamicBranchNode.add(c2, node);
                    dynamicBranchNode.add(c, (Node)object);
                    return valueNode;
                }
                ++n;
                ++n5;
            }
            this.next = this.next.add((StringTrieBuilder)object, charSequence, n5, n2);
            return this;
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            object = (LinearMatchNode)object;
            int n = this.length;
            if (n == ((LinearMatchNode)object).length && this.next == ((LinearMatchNode)object).next) {
                int n2 = this.stringOffset;
                int n3 = ((LinearMatchNode)object).stringOffset;
                int n4 = this.stringOffset;
                while (n2 < n4 + n) {
                    if (this.strings.charAt(n2) != this.strings.charAt(n3)) {
                        return false;
                    }
                    ++n2;
                    ++n3;
                }
                return true;
            }
            return false;
        }

        @Override
        public int hashCode() {
            return this.hash;
        }

        @Override
        public int markRightEdgesFirst(int n) {
            int n2 = n;
            if (this.offset == 0) {
                n = n2 = this.next.markRightEdgesFirst(n);
                this.offset = n2;
                n2 = n;
            }
            return n2;
        }

        @Override
        public Node register(StringTrieBuilder stringTrieBuilder) {
            int n;
            ValueNode valueNode;
            int n2;
            this.next = this.next.register(stringTrieBuilder);
            int n3 = stringTrieBuilder.getMaxLinearMatchLength();
            while ((n2 = this.length) > n3) {
                n = this.stringOffset;
                this.length = n2 - n3;
                valueNode = new LinearMatchNode(this.strings, n + n2 - n3, n3, this.next);
                valueNode.setHashCode();
                this.next = stringTrieBuilder.registerNode(valueNode);
            }
            if (this.hasValue && !stringTrieBuilder.matchNodesCanHaveValues()) {
                n = this.value;
                this.value = 0;
                this.hasValue = false;
                this.setHashCode();
                valueNode = new IntermediateValueNode(n, stringTrieBuilder.registerNode(this));
            } else {
                this.setHashCode();
                valueNode = this;
            }
            return stringTrieBuilder.registerNode(valueNode);
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.next.write(stringTrieBuilder);
            stringTrieBuilder.write(this.stringOffset, this.length);
            this.offset = stringTrieBuilder.writeValueAndType(this.hasValue, this.value, stringTrieBuilder.getMinLinearMatch() + this.length - 1);
        }
    }

    private static final class ListBranchNode
    extends BranchNode {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Node[] equal;
        private int length;
        private char[] units;
        private int[] values;

        public ListBranchNode(int n) {
            this.hash = 165535188 + n;
            this.equal = new Node[n];
            this.values = new int[n];
            this.units = new char[n];
        }

        public void add(int n, int n2) {
            char[] arrc = this.units;
            int n3 = this.length;
            arrc[n3] = (char)n;
            this.equal[n3] = null;
            this.values[n3] = n2;
            this.length = n3 + 1;
            this.hash = (this.hash * 37 + n) * 37 + n2;
        }

        public void add(int n, Node node) {
            char[] arrc = this.units;
            int n2 = this.length;
            arrc[n2] = (char)n;
            this.equal[n2] = node;
            this.values[n2] = 0;
            this.length = n2 + 1;
            this.hash = (this.hash * 37 + n) * 37 + node.hashCode();
        }

        @Override
        public boolean equals(Object object) {
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            object = (ListBranchNode)object;
            for (int i = 0; i < this.length; ++i) {
                if (this.units[i] == ((ListBranchNode)object).units[i] && this.values[i] == ((ListBranchNode)object).values[i] && this.equal[i] == ((ListBranchNode)object).equal[i]) {
                    continue;
                }
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public int markRightEdgesFirst(int n) {
            int n2 = n;
            if (this.offset == 0) {
                int n3;
                this.firstEdgeNumber = n;
                int n4 = 0;
                int n5 = this.length;
                n2 = n;
                do {
                    Object object = this.equal;
                    n3 = n5 - 1;
                    object = object[n3];
                    n = n2;
                    if (object != null) {
                        n = ((Node)object).markRightEdgesFirst(n2 - n4);
                    }
                    n4 = 1;
                    n5 = n3;
                    n2 = n;
                } while (n3 > 0);
                this.offset = n;
                n2 = n;
            }
            return n2;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            int n = this.length - 1;
            Node node = this.equal[n];
            int n2 = node == null ? this.firstEdgeNumber : node.getOffset();
            do {
                Node[] arrnode;
                if ((arrnode = this.equal)[--n] == null) continue;
                arrnode[n].writeUnlessInsideRightEdge(this.firstEdgeNumber, n2, stringTrieBuilder);
            } while (n > 0);
            n2 = this.length - 1;
            if (node == null) {
                stringTrieBuilder.writeValueAndFinal(this.values[n2], true);
            } else {
                node.write(stringTrieBuilder);
            }
            this.offset = stringTrieBuilder.write(this.units[n2]);
            while ((n = n2 - 1) >= 0) {
                boolean bl;
                if (this.equal[n] == null) {
                    n2 = this.values[n];
                    bl = true;
                } else {
                    n2 = this.offset - this.equal[n].getOffset();
                    bl = false;
                }
                stringTrieBuilder.writeValueAndFinal(n2, bl);
                this.offset = stringTrieBuilder.write(this.units[n]);
                n2 = n;
            }
        }
    }

    private static abstract class Node {
        protected int offset = 0;

        public Node add(StringTrieBuilder stringTrieBuilder, CharSequence charSequence, int n, int n2) {
            return this;
        }

        public boolean equals(Object object) {
            boolean bl = this == object || this.getClass() == object.getClass();
            return bl;
        }

        public final int getOffset() {
            return this.offset;
        }

        public abstract int hashCode();

        public int markRightEdgesFirst(int n) {
            if (this.offset == 0) {
                this.offset = n;
            }
            return n;
        }

        public Node register(StringTrieBuilder stringTrieBuilder) {
            return this;
        }

        public abstract void write(StringTrieBuilder var1);

        public final void writeUnlessInsideRightEdge(int n, int n2, StringTrieBuilder stringTrieBuilder) {
            int n3 = this.offset;
            if (n3 < 0 && (n3 < n2 || n < n3)) {
                this.write(stringTrieBuilder);
            }
        }
    }

    public static enum Option {
        FAST,
        SMALL;
        
    }

    private static final class SplitBranchNode
    extends BranchNode {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private Node greaterOrEqual;
        private Node lessThan;
        private char unit;

        public SplitBranchNode(char c, Node node, Node node2) {
            this.hash = ((206918985 + c) * 37 + node.hashCode()) * 37 + node2.hashCode();
            this.unit = c;
            this.lessThan = node;
            this.greaterOrEqual = node2;
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            object = (SplitBranchNode)object;
            if (this.unit != ((SplitBranchNode)object).unit || this.lessThan != ((SplitBranchNode)object).lessThan || this.greaterOrEqual != ((SplitBranchNode)object).greaterOrEqual) {
                bl = false;
            }
            return bl;
        }

        @Override
        public int hashCode() {
            return super.hashCode();
        }

        @Override
        public int markRightEdgesFirst(int n) {
            int n2 = n;
            if (this.offset == 0) {
                this.firstEdgeNumber = n;
                n = this.greaterOrEqual.markRightEdgesFirst(n);
                n2 = n = this.lessThan.markRightEdgesFirst(n - 1);
                this.offset = n;
            }
            return n2;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.lessThan.writeUnlessInsideRightEdge(this.firstEdgeNumber, this.greaterOrEqual.getOffset(), stringTrieBuilder);
            this.greaterOrEqual.write(stringTrieBuilder);
            stringTrieBuilder.writeDeltaTo(this.lessThan.getOffset());
            this.offset = stringTrieBuilder.write(this.unit);
        }
    }

    private static enum State {
        ADDING,
        BUILDING_FAST,
        BUILDING_SMALL,
        BUILT;
        
    }

    private static class ValueNode
    extends Node {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        protected boolean hasValue;
        protected int value;

        public ValueNode() {
        }

        public ValueNode(int n) {
            this.hasValue = true;
            this.value = n;
        }

        private void setFinalValue(int n) {
            this.hasValue = true;
            this.value = n;
        }

        @Override
        public Node add(StringTrieBuilder object, CharSequence charSequence, int n, int n2) {
            if (n != charSequence.length()) {
                object = ((StringTrieBuilder)object).createSuffixNode(charSequence, n, n2);
                ((ValueNode)object).setValue(this.value);
                return object;
            }
            throw new IllegalArgumentException("Duplicate string.");
        }

        @Override
        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (!super.equals(object)) {
                return false;
            }
            object = (ValueNode)object;
            boolean bl2 = this.hasValue;
            if (bl2 != ((ValueNode)object).hasValue || bl2 && this.value != ((ValueNode)object).value) {
                bl = false;
            }
            return bl;
        }

        @Override
        public int hashCode() {
            int n = 1118481;
            if (this.hasValue) {
                n = 1118481 * 37 + this.value;
            }
            return n;
        }

        public final void setValue(int n) {
            this.hasValue = true;
            this.value = n;
        }

        @Override
        public void write(StringTrieBuilder stringTrieBuilder) {
            this.offset = stringTrieBuilder.writeValueAndFinal(this.value, true);
        }
    }

}

