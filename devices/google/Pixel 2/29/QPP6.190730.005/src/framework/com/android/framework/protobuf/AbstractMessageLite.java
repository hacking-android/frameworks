/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.CodedOutputStream;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.LazyStringList;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.UninitializedMessageException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public abstract class AbstractMessageLite<MessageType extends AbstractMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>>
implements MessageLite {
    protected int memoizedHashCode = 0;

    protected static <T> void addAll(Iterable<T> iterable, Collection<? super T> collection) {
        Builder.addAll(iterable, collection);
    }

    protected static void checkByteStringIsUtf8(ByteString byteString) throws IllegalArgumentException {
        if (byteString.isValidUtf8()) {
            return;
        }
        throw new IllegalArgumentException("Byte string is not UTF-8.");
    }

    UninitializedMessageException newUninitializedMessageException() {
        return new UninitializedMessageException(this);
    }

    @Override
    public byte[] toByteArray() {
        try {
            byte[] arrby = new byte[this.getSerializedSize()];
            CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(arrby);
            this.writeTo(codedOutputStream);
            codedOutputStream.checkNoSpaceLeft();
            return arrby;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Serializing to a byte array threw an IOException (should never happen).", iOException);
        }
    }

    @Override
    public ByteString toByteString() {
        try {
            Object object = ByteString.newCodedBuilder(this.getSerializedSize());
            this.writeTo(((ByteString.CodedBuilder)object).getCodedOutput());
            object = ((ByteString.CodedBuilder)object).build();
            return object;
        }
        catch (IOException iOException) {
            throw new RuntimeException("Serializing to a ByteString threw an IOException (should never happen).", iOException);
        }
    }

    @Override
    public void writeDelimitedTo(OutputStream object) throws IOException {
        int n = this.getSerializedSize();
        int n2 = CodedOutputStream.computePreferredBufferSize(CodedOutputStream.computeRawVarint32Size(n) + n);
        object = CodedOutputStream.newInstance((OutputStream)object, n2);
        ((CodedOutputStream)object).writeRawVarint32(n);
        this.writeTo((CodedOutputStream)object);
        ((CodedOutputStream)object).flush();
    }

    @Override
    public void writeTo(OutputStream object) throws IOException {
        int n = CodedOutputStream.computePreferredBufferSize(this.getSerializedSize());
        object = CodedOutputStream.newInstance((OutputStream)object, n);
        this.writeTo((CodedOutputStream)object);
        ((CodedOutputStream)object).flush();
    }

    public static abstract class Builder<MessageType extends AbstractMessageLite<MessageType, BuilderType>, BuilderType extends Builder<MessageType, BuilderType>>
    implements MessageLite.Builder {
        protected static <T> void addAll(Iterable<T> iterable2, Collection<? super T> collection) {
            if (iterable2 != null) {
                if (iterable2 instanceof LazyStringList) {
                    Builder.checkForNullValues(((LazyStringList)iterable2).getUnderlyingElements());
                    collection.addAll((Collection)iterable2);
                } else if (iterable2 instanceof Collection) {
                    Builder.checkForNullValues(iterable2);
                    collection.addAll((Collection)iterable2);
                } else {
                    for (Iterable<T> iterable2 : iterable2) {
                        if (iterable2 != null) {
                            collection.add(iterable2);
                            continue;
                        }
                        throw new NullPointerException();
                    }
                }
                return;
            }
            throw new NullPointerException();
        }

        private static void checkForNullValues(Iterable<?> object) {
            object = object.iterator();
            while (object.hasNext()) {
                if (object.next() != null) continue;
                throw new NullPointerException();
            }
        }

        protected static UninitializedMessageException newUninitializedMessageException(MessageLite messageLite) {
            return new UninitializedMessageException(messageLite);
        }

        public abstract BuilderType clone();

        protected abstract BuilderType internalMergeFrom(MessageType var1);

        @Override
        public boolean mergeDelimitedFrom(InputStream inputStream) throws IOException {
            return this.mergeDelimitedFrom(inputStream, ExtensionRegistryLite.getEmptyRegistry());
        }

        @Override
        public boolean mergeDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            int n = inputStream.read();
            if (n == -1) {
                return false;
            }
            this.mergeFrom(new LimitedInputStream(inputStream, CodedInputStream.readRawVarint32(n, inputStream)), extensionRegistryLite);
            return true;
        }

        public BuilderType mergeFrom(ByteString object) throws InvalidProtocolBufferException {
            try {
                object = ((ByteString)object).newCodedInput();
                this.mergeFrom((CodedInputStream)object);
                ((CodedInputStream)object).checkLastTagWas(0);
            }
            catch (IOException iOException) {
                throw new RuntimeException("Reading from a ByteString threw an IOException (should never happen).", iOException);
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException;
            }
            return (BuilderType)this;
        }

        public BuilderType mergeFrom(ByteString object, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            try {
                object = ((ByteString)object).newCodedInput();
                this.mergeFrom((CodedInputStream)object, extensionRegistryLite);
                ((CodedInputStream)object).checkLastTagWas(0);
            }
            catch (IOException iOException) {
                throw new RuntimeException("Reading from a ByteString threw an IOException (should never happen).", iOException);
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException;
            }
            return (BuilderType)this;
        }

        public BuilderType mergeFrom(CodedInputStream codedInputStream) throws IOException {
            return (BuilderType)this.mergeFrom(codedInputStream, ExtensionRegistryLite.getEmptyRegistry());
        }

        public abstract BuilderType mergeFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws IOException;

        public BuilderType mergeFrom(MessageLite messageLite) {
            if (this.getDefaultInstanceForType().getClass().isInstance(messageLite)) {
                return this.internalMergeFrom((AbstractMessageLite)messageLite);
            }
            throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
        }

        public BuilderType mergeFrom(InputStream object) throws IOException {
            object = CodedInputStream.newInstance((InputStream)object);
            this.mergeFrom((CodedInputStream)object);
            ((CodedInputStream)object).checkLastTagWas(0);
            return (BuilderType)this;
        }

        public BuilderType mergeFrom(InputStream object, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            object = CodedInputStream.newInstance((InputStream)object);
            this.mergeFrom((CodedInputStream)object, extensionRegistryLite);
            ((CodedInputStream)object).checkLastTagWas(0);
            return (BuilderType)this;
        }

        public BuilderType mergeFrom(byte[] arrby) throws InvalidProtocolBufferException {
            return (BuilderType)this.mergeFrom(arrby, 0, arrby.length);
        }

        public BuilderType mergeFrom(byte[] object, int n, int n2) throws InvalidProtocolBufferException {
            try {
                object = CodedInputStream.newInstance((byte[])object, n, n2);
                this.mergeFrom((CodedInputStream)object);
                ((CodedInputStream)object).checkLastTagWas(0);
            }
            catch (IOException iOException) {
                throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", iOException);
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException;
            }
            return (BuilderType)this;
        }

        public BuilderType mergeFrom(byte[] object, int n, int n2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            try {
                object = CodedInputStream.newInstance((byte[])object, n, n2);
                this.mergeFrom((CodedInputStream)object, extensionRegistryLite);
                ((CodedInputStream)object).checkLastTagWas(0);
            }
            catch (IOException iOException) {
                throw new RuntimeException("Reading from a byte array threw an IOException (should never happen).", iOException);
            }
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException;
            }
            return (BuilderType)this;
        }

        public BuilderType mergeFrom(byte[] arrby, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (BuilderType)this.mergeFrom(arrby, 0, arrby.length, extensionRegistryLite);
        }

        static final class LimitedInputStream
        extends FilterInputStream {
            private int limit;

            LimitedInputStream(InputStream inputStream, int n) {
                super(inputStream);
                this.limit = n;
            }

            @Override
            public int available() throws IOException {
                return Math.min(super.available(), this.limit);
            }

            @Override
            public int read() throws IOException {
                if (this.limit <= 0) {
                    return -1;
                }
                int n = super.read();
                if (n >= 0) {
                    --this.limit;
                }
                return n;
            }

            @Override
            public int read(byte[] arrby, int n, int n2) throws IOException {
                int n3 = this.limit;
                if (n3 <= 0) {
                    return -1;
                }
                if ((n = super.read(arrby, n, Math.min(n2, n3))) >= 0) {
                    this.limit -= n;
                }
                return n;
            }

            @Override
            public long skip(long l) throws IOException {
                if ((l = super.skip(Math.min(l, (long)this.limit))) >= 0L) {
                    this.limit = (int)((long)this.limit - l);
                }
                return l;
            }
        }

    }

}

