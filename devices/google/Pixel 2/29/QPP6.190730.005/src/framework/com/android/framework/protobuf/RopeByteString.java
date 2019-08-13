/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteOutput;
import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

final class RopeByteString
extends ByteString {
    private static final int[] minLengthByDepth;
    private static final long serialVersionUID = 1L;
    private final ByteString left;
    private final int leftLength;
    private final ByteString right;
    private final int totalLength;
    private final int treeDepth;

    static {
        int[] arrn;
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        int n = 1;
        int n2 = 1;
        do {
            int n3 = n;
            if (n2 <= 0) break;
            arrayList.add(n2);
            n = n2;
            n2 = n3 + n2;
        } while (true);
        arrayList.add(Integer.MAX_VALUE);
        minLengthByDepth = new int[arrayList.size()];
        for (n2 = 0; n2 < (arrn = minLengthByDepth).length; ++n2) {
            arrn[n2] = (Integer)arrayList.get(n2);
        }
    }

    private RopeByteString(ByteString byteString, ByteString byteString2) {
        this.left = byteString;
        this.right = byteString2;
        this.leftLength = byteString.size();
        this.totalLength = this.leftLength + byteString2.size();
        this.treeDepth = Math.max(byteString.getTreeDepth(), byteString2.getTreeDepth()) + 1;
    }

    static ByteString concatenate(ByteString byteString, ByteString byteString2) {
        int n;
        if (byteString2.size() == 0) {
            return byteString;
        }
        if (byteString.size() == 0) {
            return byteString2;
        }
        int n2 = byteString.size() + byteString2.size();
        if (n2 < 128) {
            return RopeByteString.concatenateBytes(byteString, byteString2);
        }
        if (byteString instanceof RopeByteString) {
            RopeByteString ropeByteString = (RopeByteString)byteString;
            if (ropeByteString.right.size() + byteString2.size() < 128) {
                byteString = RopeByteString.concatenateBytes(ropeByteString.right, byteString2);
                return new RopeByteString(ropeByteString.left, byteString);
            }
            if (ropeByteString.left.getTreeDepth() > ropeByteString.right.getTreeDepth() && ropeByteString.getTreeDepth() > byteString2.getTreeDepth()) {
                byteString = new RopeByteString(ropeByteString.right, byteString2);
                return new RopeByteString(ropeByteString.left, byteString);
            }
        }
        if (n2 >= minLengthByDepth[(n = Math.max(byteString.getTreeDepth(), byteString2.getTreeDepth())) + 1]) {
            return new RopeByteString(byteString, byteString2);
        }
        return new Balancer().balance(byteString, byteString2);
    }

    private static ByteString concatenateBytes(ByteString byteString, ByteString byteString2) {
        int n = byteString.size();
        int n2 = byteString2.size();
        byte[] arrby = new byte[n + n2];
        byteString.copyTo(arrby, 0, 0, n);
        byteString2.copyTo(arrby, 0, n, n2);
        return ByteString.wrap(arrby);
    }

    private boolean equalsFragments(ByteString byteString) {
        int n = 0;
        PieceIterator pieceIterator = new PieceIterator(this);
        ByteString.LeafByteString leafByteString = (ByteString.LeafByteString)pieceIterator.next();
        int n2 = 0;
        PieceIterator pieceIterator2 = new PieceIterator(byteString);
        byteString = (ByteString.LeafByteString)pieceIterator2.next();
        int n3 = 0;
        do {
            int n4 = leafByteString.size() - n;
            int n5 = byteString.size() - n2;
            int n6 = Math.min(n4, n5);
            boolean bl = n == 0 ? leafByteString.equalsRange(byteString, n2, n6) : ((ByteString.LeafByteString)byteString).equalsRange(leafByteString, n, n6);
            if (!bl) {
                return false;
            }
            int n7 = this.totalLength;
            if ((n3 += n6) >= n7) {
                if (n3 == n7) {
                    return true;
                }
                throw new IllegalStateException();
            }
            if (n6 == n4) {
                n = 0;
                leafByteString = (ByteString.LeafByteString)pieceIterator.next();
            } else {
                n += n6;
            }
            if (n6 == n5) {
                n2 = 0;
                byteString = (ByteString.LeafByteString)pieceIterator2.next();
                continue;
            }
            n2 += n6;
        } while (true);
    }

    static RopeByteString newInstanceForTest(ByteString byteString, ByteString byteString2) {
        return new RopeByteString(byteString, byteString2);
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        throw new InvalidObjectException("RopeByteStream instances are not to be serialized directly");
    }

    @Override
    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(this.toByteArray()).asReadOnlyBuffer();
    }

    @Override
    public List<ByteBuffer> asReadOnlyByteBufferList() {
        ArrayList<ByteBuffer> arrayList = new ArrayList<ByteBuffer>();
        PieceIterator pieceIterator = new PieceIterator(this);
        while (pieceIterator.hasNext()) {
            arrayList.add(pieceIterator.next().asReadOnlyByteBuffer());
        }
        return arrayList;
    }

    @Override
    public byte byteAt(int n) {
        RopeByteString.checkIndex(n, this.totalLength);
        int n2 = this.leftLength;
        if (n < n2) {
            return this.left.byteAt(n);
        }
        return this.right.byteAt(n - n2);
    }

    @Override
    public void copyTo(ByteBuffer byteBuffer) {
        this.left.copyTo(byteBuffer);
        this.right.copyTo(byteBuffer);
    }

    @Override
    protected void copyToInternal(byte[] arrby, int n, int n2, int n3) {
        int n4 = this.leftLength;
        if (n + n3 <= n4) {
            this.left.copyToInternal(arrby, n, n2, n3);
        } else if (n >= n4) {
            this.right.copyToInternal(arrby, n - n4, n2, n3);
        } else {
            this.left.copyToInternal(arrby, n, n2, n4 -= n);
            this.right.copyToInternal(arrby, 0, n2 + n4, n3 - n4);
        }
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof ByteString)) {
            return false;
        }
        if (this.totalLength != ((ByteString)(object = (ByteString)object)).size()) {
            return false;
        }
        if (this.totalLength == 0) {
            return true;
        }
        int n = this.peekCachedHashCode();
        int n2 = ((ByteString)object).peekCachedHashCode();
        if (n != 0 && n2 != 0 && n != n2) {
            return false;
        }
        return this.equalsFragments((ByteString)object);
    }

    @Override
    protected int getTreeDepth() {
        return this.treeDepth;
    }

    @Override
    protected boolean isBalanced() {
        boolean bl = this.totalLength >= minLengthByDepth[this.treeDepth];
        return bl;
    }

    @Override
    public boolean isValidUtf8() {
        ByteString byteString = this.left;
        int n = this.leftLength;
        boolean bl = false;
        n = byteString.partialIsValidUtf8(0, 0, n);
        byteString = this.right;
        if (byteString.partialIsValidUtf8(n, 0, byteString.size()) == 0) {
            bl = true;
        }
        return bl;
    }

    @Override
    public CodedInputStream newCodedInput() {
        return CodedInputStream.newInstance(new RopeInputStream());
    }

    @Override
    public InputStream newInput() {
        return new RopeInputStream();
    }

    @Override
    protected int partialHash(int n, int n2, int n3) {
        int n4 = this.leftLength;
        if (n2 + n3 <= n4) {
            return this.left.partialHash(n, n2, n3);
        }
        if (n2 >= n4) {
            return this.right.partialHash(n, n2 - n4, n3);
        }
        n = this.left.partialHash(n, n2, n4 -= n2);
        return this.right.partialHash(n, 0, n3 - n4);
    }

    @Override
    protected int partialIsValidUtf8(int n, int n2, int n3) {
        int n4 = this.leftLength;
        if (n2 + n3 <= n4) {
            return this.left.partialIsValidUtf8(n, n2, n3);
        }
        if (n2 >= n4) {
            return this.right.partialIsValidUtf8(n, n2 - n4, n3);
        }
        n = this.left.partialIsValidUtf8(n, n2, n4 -= n2);
        return this.right.partialIsValidUtf8(n, 0, n3 - n4);
    }

    @Override
    public int size() {
        return this.totalLength;
    }

    @Override
    public ByteString substring(int n, int n2) {
        int n3 = RopeByteString.checkRange(n, n2, this.totalLength);
        if (n3 == 0) {
            return ByteString.EMPTY;
        }
        if (n3 == this.totalLength) {
            return this;
        }
        n3 = this.leftLength;
        if (n2 <= n3) {
            return this.left.substring(n, n2);
        }
        if (n >= n3) {
            return this.right.substring(n - n3, n2 - n3);
        }
        return new RopeByteString(this.left.substring(n), this.right.substring(0, n2 - this.leftLength));
    }

    @Override
    protected String toStringInternal(Charset charset) {
        return new String(this.toByteArray(), charset);
    }

    Object writeReplace() {
        return ByteString.wrap(this.toByteArray());
    }

    @Override
    void writeTo(ByteOutput byteOutput) throws IOException {
        this.left.writeTo(byteOutput);
        this.right.writeTo(byteOutput);
    }

    @Override
    public void writeTo(OutputStream outputStream) throws IOException {
        this.left.writeTo(outputStream);
        this.right.writeTo(outputStream);
    }

    @Override
    void writeToInternal(OutputStream outputStream, int n, int n2) throws IOException {
        int n3 = this.leftLength;
        if (n + n2 <= n3) {
            this.left.writeToInternal(outputStream, n, n2);
        } else if (n >= n3) {
            this.right.writeToInternal(outputStream, n - n3, n2);
        } else {
            this.left.writeToInternal(outputStream, n, n3 -= n);
            this.right.writeToInternal(outputStream, 0, n2 - n3);
        }
    }

    private static class Balancer {
        private final Stack<ByteString> prefixesStack = new Stack();

        private Balancer() {
        }

        private ByteString balance(ByteString byteString, ByteString byteString2) {
            this.doBalance(byteString);
            this.doBalance(byteString2);
            byteString = this.prefixesStack.pop();
            while (!this.prefixesStack.isEmpty()) {
                byteString = new RopeByteString(this.prefixesStack.pop(), byteString);
            }
            return byteString;
        }

        private void doBalance(ByteString byteString) {
            block4 : {
                block3 : {
                    block2 : {
                        if (!byteString.isBalanced()) break block2;
                        this.insert(byteString);
                        break block3;
                    }
                    if (!(byteString instanceof RopeByteString)) break block4;
                    byteString = (RopeByteString)byteString;
                    this.doBalance(((RopeByteString)byteString).left);
                    this.doBalance(((RopeByteString)byteString).right);
                }
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Has a new type of ByteString been created? Found ");
            stringBuilder.append(byteString.getClass());
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        private int getDepthBinForLength(int n) {
            int n2;
            n = n2 = Arrays.binarySearch(minLengthByDepth, n);
            if (n2 < 0) {
                n = -(n2 + 1) - 1;
            }
            return n;
        }

        private void insert(ByteString byteString) {
            int n = this.getDepthBinForLength(byteString.size());
            int n2 = minLengthByDepth[n + 1];
            if (!this.prefixesStack.isEmpty() && this.prefixesStack.peek().size() < n2) {
                n = minLengthByDepth[n];
                ByteString byteString2 = this.prefixesStack.pop();
                while (!this.prefixesStack.isEmpty() && this.prefixesStack.peek().size() < n) {
                    byteString2 = new RopeByteString(this.prefixesStack.pop(), byteString2);
                }
                byteString = new RopeByteString(byteString2, byteString);
                while (!this.prefixesStack.isEmpty()) {
                    n = this.getDepthBinForLength(byteString.size());
                    n = minLengthByDepth[n + 1];
                    if (this.prefixesStack.peek().size() >= n) break;
                    byteString = new RopeByteString(this.prefixesStack.pop(), byteString);
                }
                this.prefixesStack.push(byteString);
            } else {
                this.prefixesStack.push(byteString);
            }
        }
    }

    private static class PieceIterator
    implements Iterator<ByteString.LeafByteString> {
        private final Stack<RopeByteString> breadCrumbs = new Stack();
        private ByteString.LeafByteString next;

        private PieceIterator(ByteString byteString) {
            this.next = this.getLeafByLeft(byteString);
        }

        private ByteString.LeafByteString getLeafByLeft(ByteString byteString) {
            while (byteString instanceof RopeByteString) {
                byteString = (RopeByteString)byteString;
                this.breadCrumbs.push((RopeByteString)byteString);
                byteString = ((RopeByteString)byteString).left;
            }
            return (ByteString.LeafByteString)byteString;
        }

        private ByteString.LeafByteString getNextNonEmptyLeaf() {
            ByteString.LeafByteString leafByteString;
            do {
                if (!this.breadCrumbs.isEmpty()) continue;
                return null;
            } while ((leafByteString = this.getLeafByLeft(this.breadCrumbs.pop().right)).isEmpty());
            return leafByteString;
        }

        @Override
        public boolean hasNext() {
            boolean bl = this.next != null;
            return bl;
        }

        @Override
        public ByteString.LeafByteString next() {
            if (this.next != null) {
                ByteString.LeafByteString leafByteString = this.next;
                this.next = this.getNextNonEmptyLeaf();
                return leafByteString;
            }
            throw new NoSuchElementException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    private class RopeInputStream
    extends InputStream {
        private ByteString.LeafByteString currentPiece;
        private int currentPieceIndex;
        private int currentPieceOffsetInRope;
        private int currentPieceSize;
        private int mark;
        private PieceIterator pieceIterator;

        public RopeInputStream() {
            this.initialize();
        }

        private void advanceIfCurrentPieceFullyRead() {
            int n;
            int n2;
            if (this.currentPiece != null && (n2 = this.currentPieceIndex) == (n = this.currentPieceSize)) {
                this.currentPieceOffsetInRope += n;
                this.currentPieceIndex = 0;
                if (this.pieceIterator.hasNext()) {
                    this.currentPiece = this.pieceIterator.next();
                    this.currentPieceSize = this.currentPiece.size();
                } else {
                    this.currentPiece = null;
                    this.currentPieceSize = 0;
                }
            }
        }

        private void initialize() {
            this.pieceIterator = new PieceIterator(RopeByteString.this);
            this.currentPiece = this.pieceIterator.next();
            this.currentPieceSize = this.currentPiece.size();
            this.currentPieceIndex = 0;
            this.currentPieceOffsetInRope = 0;
        }

        private int readSkipInternal(byte[] arrby, int n, int n2) {
            int n3;
            int n4;
            int n5 = n;
            for (n4 = n2; n4 > 0; n4 -= n3) {
                this.advanceIfCurrentPieceFullyRead();
                if (this.currentPiece == null) {
                    if (n4 != n2) break;
                    return -1;
                }
                n3 = Math.min(this.currentPieceSize - this.currentPieceIndex, n4);
                n = n5;
                if (arrby != null) {
                    this.currentPiece.copyTo(arrby, this.currentPieceIndex, n5, n3);
                    n = n5 + n3;
                }
                this.currentPieceIndex += n3;
                n5 = n;
            }
            return n2 - n4;
        }

        @Override
        public int available() throws IOException {
            int n = this.currentPieceOffsetInRope;
            int n2 = this.currentPieceIndex;
            return RopeByteString.this.size() - (n + n2);
        }

        @Override
        public void mark(int n) {
            this.mark = this.currentPieceOffsetInRope + this.currentPieceIndex;
        }

        @Override
        public boolean markSupported() {
            return true;
        }

        @Override
        public int read() throws IOException {
            this.advanceIfCurrentPieceFullyRead();
            ByteString.LeafByteString leafByteString = this.currentPiece;
            if (leafByteString == null) {
                return -1;
            }
            int n = this.currentPieceIndex;
            this.currentPieceIndex = n + 1;
            return leafByteString.byteAt(n) & 255;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) {
            if (arrby != null) {
                if (n >= 0 && n2 >= 0 && n2 <= arrby.length - n) {
                    return this.readSkipInternal(arrby, n, n2);
                }
                throw new IndexOutOfBoundsException();
            }
            throw new NullPointerException();
        }

        @Override
        public void reset() {
            synchronized (this) {
                this.initialize();
                this.readSkipInternal(null, 0, this.mark);
                return;
            }
        }

        @Override
        public long skip(long l) {
            if (l >= 0L) {
                long l2 = l;
                if (l > Integer.MAX_VALUE) {
                    l2 = Integer.MAX_VALUE;
                }
                return this.readSkipInternal(null, 0, (int)l2);
            }
            throw new IndexOutOfBoundsException();
        }
    }

}

