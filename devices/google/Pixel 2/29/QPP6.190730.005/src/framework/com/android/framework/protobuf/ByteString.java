/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteOutput;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.CodedOutputStream;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.RopeByteString;
import com.android.framework.protobuf.Utf8;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class ByteString
implements Iterable<Byte>,
Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final int CONCATENATE_BY_COPY_SIZE = 128;
    public static final ByteString EMPTY = new LiteralByteString(Internal.EMPTY_BYTE_ARRAY);
    static final int MAX_READ_FROM_CHUNK_SIZE = 8192;
    static final int MIN_READ_FROM_CHUNK_SIZE = 256;
    private static final ByteArrayCopier byteArrayCopier;
    private int hash = 0;

    static {
        boolean bl = true;
        try {
            Class.forName("android.content.Context");
        }
        catch (ClassNotFoundException classNotFoundException) {
            bl = false;
        }
        ByteArrayCopier byteArrayCopier = bl ? new SystemByteArrayCopier() : new ArraysByteArrayCopier();
        ByteString.byteArrayCopier = byteArrayCopier;
    }

    ByteString() {
    }

    private static ByteString balancedConcat(Iterator<ByteString> object, int n) {
        if (n == 1) {
            object = object.next();
        } else {
            int n2 = n >>> 1;
            object = ByteString.balancedConcat((Iterator<ByteString>)object, n2).concat(ByteString.balancedConcat((Iterator<ByteString>)object, n - n2));
        }
        return object;
    }

    static void checkIndex(int n, int n2) {
        if ((n2 - (n + 1) | n) < 0) {
            if (n < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Index < 0: ");
                stringBuilder.append(n);
                throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Index > length: ");
            stringBuilder.append(n);
            stringBuilder.append(", ");
            stringBuilder.append(n2);
            throw new ArrayIndexOutOfBoundsException(stringBuilder.toString());
        }
    }

    static int checkRange(int n, int n2, int n3) {
        int n4 = n2 - n;
        if ((n | n2 | n4 | n3 - n2) < 0) {
            if (n >= 0) {
                if (n2 < n) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Beginning index larger than ending index: ");
                    stringBuilder.append(n);
                    stringBuilder.append(", ");
                    stringBuilder.append(n2);
                    throw new IndexOutOfBoundsException(stringBuilder.toString());
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("End index: ");
                stringBuilder.append(n2);
                stringBuilder.append(" >= ");
                stringBuilder.append(n3);
                throw new IndexOutOfBoundsException(stringBuilder.toString());
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Beginning index: ");
            stringBuilder.append(n);
            stringBuilder.append(" < 0");
            throw new IndexOutOfBoundsException(stringBuilder.toString());
        }
        return n4;
    }

    public static ByteString copyFrom(Iterable<ByteString> iterable) {
        int n;
        if (!(iterable instanceof Collection)) {
            n = 0;
            Iterator<ByteString> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                iterator.next();
                ++n;
            }
        } else {
            n = ((Collection)iterable).size();
        }
        if (n == 0) {
            return EMPTY;
        }
        return ByteString.balancedConcat(iterable.iterator(), n);
    }

    public static ByteString copyFrom(String string2, String string3) throws UnsupportedEncodingException {
        return new LiteralByteString(string2.getBytes(string3));
    }

    public static ByteString copyFrom(String string2, Charset charset) {
        return new LiteralByteString(string2.getBytes(charset));
    }

    public static ByteString copyFrom(ByteBuffer byteBuffer) {
        return ByteString.copyFrom(byteBuffer, byteBuffer.remaining());
    }

    public static ByteString copyFrom(ByteBuffer byteBuffer, int n) {
        byte[] arrby = new byte[n];
        byteBuffer.get(arrby);
        return new LiteralByteString(arrby);
    }

    public static ByteString copyFrom(byte[] arrby) {
        return ByteString.copyFrom(arrby, 0, arrby.length);
    }

    public static ByteString copyFrom(byte[] arrby, int n, int n2) {
        return new LiteralByteString(byteArrayCopier.copyFrom(arrby, n, n2));
    }

    public static ByteString copyFromUtf8(String string2) {
        return new LiteralByteString(string2.getBytes(Internal.UTF_8));
    }

    static CodedBuilder newCodedBuilder(int n) {
        return new CodedBuilder(n);
    }

    public static Output newOutput() {
        return new Output(128);
    }

    public static Output newOutput(int n) {
        return new Output(n);
    }

    private static ByteString readChunk(InputStream inputStream, int n) throws IOException {
        int n2;
        int n3;
        byte[] arrby = new byte[n];
        for (n2 = 0; n2 < n && (n3 = inputStream.read(arrby, n2, n - n2)) != -1; n2 += n3) {
        }
        if (n2 == 0) {
            return null;
        }
        return ByteString.copyFrom(arrby, 0, n2);
    }

    public static ByteString readFrom(InputStream inputStream) throws IOException {
        return ByteString.readFrom(inputStream, 256, 8192);
    }

    public static ByteString readFrom(InputStream inputStream, int n) throws IOException {
        return ByteString.readFrom(inputStream, n, n);
    }

    public static ByteString readFrom(InputStream inputStream, int n, int n2) throws IOException {
        ArrayList<ByteString> arrayList = new ArrayList<ByteString>();
        ByteString byteString;
        while ((byteString = ByteString.readChunk(inputStream, n)) != null) {
            arrayList.add(byteString);
            n = Math.min(n * 2, n2);
        }
        return ByteString.copyFrom(arrayList);
    }

    static ByteString wrap(byte[] arrby) {
        return new LiteralByteString(arrby);
    }

    static ByteString wrap(byte[] arrby, int n, int n2) {
        return new BoundedByteString(arrby, n, n2);
    }

    public abstract ByteBuffer asReadOnlyByteBuffer();

    public abstract List<ByteBuffer> asReadOnlyByteBufferList();

    public abstract byte byteAt(int var1);

    public final ByteString concat(ByteString byteString) {
        if (Integer.MAX_VALUE - this.size() >= byteString.size()) {
            return RopeByteString.concatenate(this, byteString);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ByteString would be too long: ");
        stringBuilder.append(this.size());
        stringBuilder.append("+");
        stringBuilder.append(byteString.size());
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public abstract void copyTo(ByteBuffer var1);

    public void copyTo(byte[] arrby, int n) {
        this.copyTo(arrby, 0, n, this.size());
    }

    public final void copyTo(byte[] arrby, int n, int n2, int n3) {
        ByteString.checkRange(n, n + n3, this.size());
        ByteString.checkRange(n2, n2 + n3, arrby.length);
        if (n3 > 0) {
            this.copyToInternal(arrby, n, n2, n3);
        }
    }

    protected abstract void copyToInternal(byte[] var1, int var2, int var3, int var4);

    public final boolean endsWith(ByteString byteString) {
        boolean bl = this.size() >= byteString.size() && this.substring(this.size() - byteString.size()).equals(byteString);
        return bl;
    }

    public abstract boolean equals(Object var1);

    protected abstract int getTreeDepth();

    public final int hashCode() {
        int n;
        int n2 = n = this.hash;
        if (n == 0) {
            n2 = this.size();
            n2 = n = this.partialHash(n2, 0, n2);
            if (n == 0) {
                n2 = 1;
            }
            this.hash = n2;
        }
        return n2;
    }

    protected abstract boolean isBalanced();

    public final boolean isEmpty() {
        boolean bl = this.size() == 0;
        return bl;
    }

    public abstract boolean isValidUtf8();

    public final ByteIterator iterator() {
        return new ByteIterator(){
            private final int limit = ByteString.this.size();
            private int position = 0;

            @Override
            public boolean hasNext() {
                boolean bl = this.position < this.limit;
                return bl;
            }

            @Override
            public Byte next() {
                return this.nextByte();
            }

            @Override
            public byte nextByte() {
                try {
                    ByteString byteString = ByteString.this;
                    int n = this.position;
                    this.position = n + 1;
                    byte by = byteString.byteAt(n);
                    return by;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new NoSuchElementException(indexOutOfBoundsException.getMessage());
                }
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public abstract CodedInputStream newCodedInput();

    public abstract InputStream newInput();

    protected abstract int partialHash(int var1, int var2, int var3);

    protected abstract int partialIsValidUtf8(int var1, int var2, int var3);

    protected final int peekCachedHashCode() {
        return this.hash;
    }

    public abstract int size();

    public final boolean startsWith(ByteString byteString) {
        boolean bl;
        block0 : {
            int n = this.size();
            int n2 = byteString.size();
            bl = false;
            if (n < n2 || !this.substring(0, byteString.size()).equals(byteString)) break block0;
            bl = true;
        }
        return bl;
    }

    public final ByteString substring(int n) {
        return this.substring(n, this.size());
    }

    public abstract ByteString substring(int var1, int var2);

    public final byte[] toByteArray() {
        int n = this.size();
        if (n == 0) {
            return Internal.EMPTY_BYTE_ARRAY;
        }
        byte[] arrby = new byte[n];
        this.copyToInternal(arrby, 0, 0, n);
        return arrby;
    }

    public final String toString() {
        return String.format("<ByteString@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), this.size());
    }

    public final String toString(String object) throws UnsupportedEncodingException {
        try {
            String string2 = this.toString(Charset.forName((String)object));
            return string2;
        }
        catch (UnsupportedCharsetException unsupportedCharsetException) {
            object = new UnsupportedEncodingException((String)object);
            ((Throwable)object).initCause(unsupportedCharsetException);
            throw object;
        }
    }

    public final String toString(Charset object) {
        object = this.size() == 0 ? "" : this.toStringInternal((Charset)object);
        return object;
    }

    protected abstract String toStringInternal(Charset var1);

    public final String toStringUtf8() {
        return this.toString(Internal.UTF_8);
    }

    abstract void writeTo(ByteOutput var1) throws IOException;

    public abstract void writeTo(OutputStream var1) throws IOException;

    final void writeTo(OutputStream outputStream, int n, int n2) throws IOException {
        ByteString.checkRange(n, n + n2, this.size());
        if (n2 > 0) {
            this.writeToInternal(outputStream, n, n2);
        }
    }

    abstract void writeToInternal(OutputStream var1, int var2, int var3) throws IOException;

    private static final class ArraysByteArrayCopier
    implements ByteArrayCopier {
        private ArraysByteArrayCopier() {
        }

        @Override
        public byte[] copyFrom(byte[] arrby, int n, int n2) {
            return Arrays.copyOfRange(arrby, n, n + n2);
        }
    }

    private static final class BoundedByteString
    extends LiteralByteString {
        private static final long serialVersionUID = 1L;
        private final int bytesLength;
        private final int bytesOffset;

        BoundedByteString(byte[] arrby, int n, int n2) {
            super(arrby);
            BoundedByteString.checkRange(n, n + n2, arrby.length);
            this.bytesOffset = n;
            this.bytesLength = n2;
        }

        private void readObject(ObjectInputStream objectInputStream) throws IOException {
            throw new InvalidObjectException("BoundedByteStream instances are not to be serialized directly");
        }

        @Override
        public byte byteAt(int n) {
            BoundedByteString.checkIndex(n, this.size());
            return this.bytes[this.bytesOffset + n];
        }

        @Override
        protected void copyToInternal(byte[] arrby, int n, int n2, int n3) {
            System.arraycopy(this.bytes, this.getOffsetIntoBytes() + n, arrby, n2, n3);
        }

        @Override
        protected int getOffsetIntoBytes() {
            return this.bytesOffset;
        }

        @Override
        public int size() {
            return this.bytesLength;
        }

        Object writeReplace() {
            return ByteString.wrap(this.toByteArray());
        }
    }

    private static interface ByteArrayCopier {
        public byte[] copyFrom(byte[] var1, int var2, int var3);
    }

    public static interface ByteIterator
    extends Iterator<Byte> {
        public byte nextByte();
    }

    static final class CodedBuilder {
        private final byte[] buffer;
        private final CodedOutputStream output;

        private CodedBuilder(int n) {
            this.buffer = new byte[n];
            this.output = CodedOutputStream.newInstance(this.buffer);
        }

        public ByteString build() {
            this.output.checkNoSpaceLeft();
            return new LiteralByteString(this.buffer);
        }

        public CodedOutputStream getCodedOutput() {
            return this.output;
        }
    }

    static abstract class LeafByteString
    extends ByteString {
        LeafByteString() {
        }

        abstract boolean equalsRange(ByteString var1, int var2, int var3);

        @Override
        protected final int getTreeDepth() {
            return 0;
        }

        @Override
        protected final boolean isBalanced() {
            return true;
        }
    }

    private static class LiteralByteString
    extends LeafByteString {
        private static final long serialVersionUID = 1L;
        protected final byte[] bytes;

        LiteralByteString(byte[] arrby) {
            this.bytes = arrby;
        }

        @Override
        public final ByteBuffer asReadOnlyByteBuffer() {
            return ByteBuffer.wrap(this.bytes, this.getOffsetIntoBytes(), this.size()).asReadOnlyBuffer();
        }

        @Override
        public final List<ByteBuffer> asReadOnlyByteBufferList() {
            return Collections.singletonList(this.asReadOnlyByteBuffer());
        }

        @Override
        public byte byteAt(int n) {
            return this.bytes[n];
        }

        @Override
        public final void copyTo(ByteBuffer byteBuffer) {
            byteBuffer.put(this.bytes, this.getOffsetIntoBytes(), this.size());
        }

        @Override
        protected void copyToInternal(byte[] arrby, int n, int n2, int n3) {
            System.arraycopy(this.bytes, n, arrby, n2, n3);
        }

        @Override
        public final boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof ByteString)) {
                return false;
            }
            if (this.size() != ((ByteString)object).size()) {
                return false;
            }
            if (this.size() == 0) {
                return true;
            }
            if (object instanceof LiteralByteString) {
                LiteralByteString literalByteString = (LiteralByteString)object;
                int n = this.peekCachedHashCode();
                int n2 = literalByteString.peekCachedHashCode();
                if (n != 0 && n2 != 0 && n != n2) {
                    return false;
                }
                return this.equalsRange((LiteralByteString)object, 0, this.size());
            }
            return object.equals(this);
        }

        @Override
        final boolean equalsRange(ByteString serializable, int n, int n2) {
            if (n2 <= ((ByteString)serializable).size()) {
                if (n + n2 <= ((ByteString)serializable).size()) {
                    if (serializable instanceof LiteralByteString) {
                        LiteralByteString literalByteString = (LiteralByteString)serializable;
                        byte[] arrby = this.bytes;
                        serializable = literalByteString.bytes;
                        int n3 = this.getOffsetIntoBytes();
                        int n4 = this.getOffsetIntoBytes();
                        n = literalByteString.getOffsetIntoBytes() + n;
                        while (n4 < n3 + n2) {
                            if (arrby[n4] != serializable[n]) {
                                return false;
                            }
                            ++n4;
                            ++n;
                        }
                        return true;
                    }
                    return ((ByteString)serializable).substring(n, n + n2).equals(this.substring(0, n2));
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Ran off end of other: ");
                stringBuilder.append(n);
                stringBuilder.append(", ");
                stringBuilder.append(n2);
                stringBuilder.append(", ");
                stringBuilder.append(((ByteString)serializable).size());
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Length too large: ");
            ((StringBuilder)serializable).append(n2);
            ((StringBuilder)serializable).append(this.size());
            throw new IllegalArgumentException(((StringBuilder)serializable).toString());
        }

        protected int getOffsetIntoBytes() {
            return 0;
        }

        @Override
        public final boolean isValidUtf8() {
            int n = this.getOffsetIntoBytes();
            return Utf8.isValidUtf8(this.bytes, n, this.size() + n);
        }

        @Override
        public final CodedInputStream newCodedInput() {
            return CodedInputStream.newInstance(this.bytes, this.getOffsetIntoBytes(), this.size(), true);
        }

        @Override
        public final InputStream newInput() {
            return new ByteArrayInputStream(this.bytes, this.getOffsetIntoBytes(), this.size());
        }

        @Override
        protected final int partialHash(int n, int n2, int n3) {
            return Internal.partialHash(n, this.bytes, this.getOffsetIntoBytes() + n2, n3);
        }

        @Override
        protected final int partialIsValidUtf8(int n, int n2, int n3) {
            n2 = this.getOffsetIntoBytes() + n2;
            return Utf8.partialIsValidUtf8(n, this.bytes, n2, n2 + n3);
        }

        @Override
        public int size() {
            return this.bytes.length;
        }

        @Override
        public final ByteString substring(int n, int n2) {
            if ((n2 = LiteralByteString.checkRange(n, n2, this.size())) == 0) {
                return EMPTY;
            }
            return new BoundedByteString(this.bytes, this.getOffsetIntoBytes() + n, n2);
        }

        @Override
        protected final String toStringInternal(Charset charset) {
            return new String(this.bytes, this.getOffsetIntoBytes(), this.size(), charset);
        }

        @Override
        final void writeTo(ByteOutput byteOutput) throws IOException {
            byteOutput.writeLazy(this.bytes, this.getOffsetIntoBytes(), this.size());
        }

        @Override
        public final void writeTo(OutputStream outputStream) throws IOException {
            outputStream.write(this.toByteArray());
        }

        @Override
        final void writeToInternal(OutputStream outputStream, int n, int n2) throws IOException {
            outputStream.write(this.bytes, this.getOffsetIntoBytes() + n, n2);
        }
    }

    public static final class Output
    extends OutputStream {
        private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
        private byte[] buffer;
        private int bufferPos;
        private final ArrayList<ByteString> flushedBuffers;
        private int flushedBuffersTotalBytes;
        private final int initialCapacity;

        Output(int n) {
            if (n >= 0) {
                this.initialCapacity = n;
                this.flushedBuffers = new ArrayList();
                this.buffer = new byte[n];
                return;
            }
            throw new IllegalArgumentException("Buffer size < 0");
        }

        private byte[] copyArray(byte[] arrby, int n) {
            byte[] arrby2 = new byte[n];
            System.arraycopy(arrby, 0, arrby2, 0, Math.min(arrby.length, n));
            return arrby2;
        }

        private void flushFullBuffer(int n) {
            this.flushedBuffers.add(new LiteralByteString(this.buffer));
            this.flushedBuffersTotalBytes += this.buffer.length;
            this.buffer = new byte[Math.max(this.initialCapacity, Math.max(n, this.flushedBuffersTotalBytes >>> 1))];
            this.bufferPos = 0;
        }

        private void flushLastBuffer() {
            int n = this.bufferPos;
            byte[] arrby = this.buffer;
            if (n < arrby.length) {
                if (n > 0) {
                    arrby = this.copyArray(arrby, n);
                    this.flushedBuffers.add(new LiteralByteString(arrby));
                }
            } else {
                this.flushedBuffers.add(new LiteralByteString(arrby));
                this.buffer = EMPTY_BYTE_ARRAY;
            }
            this.flushedBuffersTotalBytes += this.bufferPos;
            this.bufferPos = 0;
        }

        public void reset() {
            synchronized (this) {
                this.flushedBuffers.clear();
                this.flushedBuffersTotalBytes = 0;
                this.bufferPos = 0;
                return;
            }
        }

        public int size() {
            synchronized (this) {
                int n = this.flushedBuffersTotalBytes;
                int n2 = this.bufferPos;
                return n + n2;
            }
        }

        public ByteString toByteString() {
            synchronized (this) {
                this.flushLastBuffer();
                ByteString byteString = ByteString.copyFrom(this.flushedBuffers);
                return byteString;
            }
        }

        public String toString() {
            return String.format("<ByteString.Output@%s size=%d>", Integer.toHexString(System.identityHashCode(this)), this.size());
        }

        @Override
        public void write(int n) {
            synchronized (this) {
                if (this.bufferPos == this.buffer.length) {
                    this.flushFullBuffer(1);
                }
                byte[] arrby = this.buffer;
                int n2 = this.bufferPos;
                this.bufferPos = n2 + 1;
                arrby[n2] = (byte)n;
                return;
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void write(byte[] arrby, int n, int n2) {
            synchronized (this) {
                void var2_2;
                void var3_3;
                if (var3_3 <= this.buffer.length - this.bufferPos) {
                    System.arraycopy(arrby, (int)var2_2, this.buffer, this.bufferPos, (int)var3_3);
                    this.bufferPos += var3_3;
                } else {
                    int n3 = this.buffer.length - this.bufferPos;
                    System.arraycopy(arrby, (int)var2_2, this.buffer, this.bufferPos, n3);
                    this.flushFullBuffer((int)(var3_3 -= n3));
                    System.arraycopy(arrby, (int)(var2_2 + n3), this.buffer, 0, (int)var3_3);
                    this.bufferPos = var3_3;
                }
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void writeTo(OutputStream outputStream) throws IOException {
            byte[] arrby;
            int n;
            ByteString[] arrbyteString;
            synchronized (this) {
                arrbyteString = this.flushedBuffers.toArray(new ByteString[this.flushedBuffers.size()]);
                arrby = this.buffer;
                n = this.bufferPos;
            }
            int n2 = arrbyteString.length;
            int n3 = 0;
            do {
                if (n3 >= n2) {
                    outputStream.write(this.copyArray(arrby, n));
                    return;
                }
                arrbyteString[n3].writeTo(outputStream);
                ++n3;
            } while (true);
        }
    }

    private static final class SystemByteArrayCopier
    implements ByteArrayCopier {
        private SystemByteArrayCopier() {
        }

        @Override
        public byte[] copyFrom(byte[] arrby, int n, int n2) {
            byte[] arrby2 = new byte[n2];
            System.arraycopy(arrby, n, arrby2, 0, n2);
            return arrby2;
        }
    }

}

