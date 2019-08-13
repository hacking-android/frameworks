/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.AbstractMessageLite;
import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.Parser;
import com.android.framework.protobuf.UninitializedMessageException;
import java.io.IOException;
import java.io.InputStream;

public abstract class AbstractParser<MessageType extends MessageLite>
implements Parser<MessageType> {
    private static final ExtensionRegistryLite EMPTY_REGISTRY = ExtensionRegistryLite.getEmptyRegistry();

    private MessageType checkMessageInitialized(MessageType MessageType2) throws InvalidProtocolBufferException {
        if (MessageType2 != null && !MessageType2.isInitialized()) {
            throw this.newUninitializedMessageException(MessageType2).asInvalidProtocolBufferException().setUnfinishedMessage((MessageLite)MessageType2);
        }
        return MessageType2;
    }

    private UninitializedMessageException newUninitializedMessageException(MessageType MessageType2) {
        if (MessageType2 instanceof AbstractMessageLite) {
            return ((AbstractMessageLite)MessageType2).newUninitializedMessageException();
        }
        return new UninitializedMessageException((MessageLite)MessageType2);
    }

    @Override
    public MessageType parseDelimitedFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return (MessageType)this.parseDelimitedFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType)this.checkMessageInitialized(this.parsePartialDelimitedFrom(inputStream, extensionRegistryLite));
    }

    @Override
    public MessageType parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (MessageType)this.parseFrom(byteString, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType)this.checkMessageInitialized(this.parsePartialFrom(byteString, extensionRegistryLite));
    }

    @Override
    public MessageType parseFrom(CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return (MessageType)this.parseFrom(codedInputStream, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType)this.checkMessageInitialized((MessageLite)this.parsePartialFrom(codedInputStream, extensionRegistryLite));
    }

    @Override
    public MessageType parseFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return (MessageType)this.parseFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType)this.checkMessageInitialized(this.parsePartialFrom(inputStream, extensionRegistryLite));
    }

    @Override
    public MessageType parseFrom(byte[] arrby) throws InvalidProtocolBufferException {
        return (MessageType)this.parseFrom(arrby, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parseFrom(byte[] arrby, int n, int n2) throws InvalidProtocolBufferException {
        return (MessageType)this.parseFrom(arrby, n, n2, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parseFrom(byte[] arrby, int n, int n2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType)this.checkMessageInitialized(this.parsePartialFrom(arrby, n, n2, extensionRegistryLite));
    }

    @Override
    public MessageType parseFrom(byte[] arrby, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType)this.parseFrom(arrby, 0, arrby.length, extensionRegistryLite);
    }

    @Override
    public MessageType parsePartialDelimitedFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return (MessageType)this.parsePartialDelimitedFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parsePartialDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        int n;
        block3 : {
            try {
                n = inputStream.read();
                if (n != -1) break block3;
            }
            catch (IOException iOException) {
                throw new InvalidProtocolBufferException(iOException.getMessage());
            }
            return null;
        }
        n = CodedInputStream.readRawVarint32(n, inputStream);
        return (MessageType)this.parsePartialFrom(new AbstractMessageLite.Builder.LimitedInputStream(inputStream, n), extensionRegistryLite);
    }

    @Override
    public MessageType parsePartialFrom(ByteString byteString) throws InvalidProtocolBufferException {
        return (MessageType)this.parsePartialFrom(byteString, EMPTY_REGISTRY);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public MessageType parsePartialFrom(ByteString object, ExtensionRegistryLite object2) throws InvalidProtocolBufferException {
        object = ((ByteString)object).newCodedInput();
        object2 = (MessageLite)this.parsePartialFrom((CodedInputStream)object, (ExtensionRegistryLite)object2);
        {
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException;
            }
        }
        try {
            ((CodedInputStream)object).checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            throw invalidProtocolBufferException.setUnfinishedMessage((MessageLite)object2);
        }
        return (MessageType)object2;
    }

    @Override
    public MessageType parsePartialFrom(CodedInputStream codedInputStream) throws InvalidProtocolBufferException {
        return (MessageType)((MessageLite)this.parsePartialFrom(codedInputStream, EMPTY_REGISTRY));
    }

    @Override
    public MessageType parsePartialFrom(InputStream inputStream) throws InvalidProtocolBufferException {
        return (MessageType)this.parsePartialFrom(inputStream, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parsePartialFrom(InputStream object, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream codedInputStream = CodedInputStream.newInstance((InputStream)object);
        object = (MessageLite)this.parsePartialFrom(codedInputStream, extensionRegistryLite);
        try {
            codedInputStream.checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            throw invalidProtocolBufferException.setUnfinishedMessage((MessageLite)object);
        }
        return (MessageType)object;
    }

    @Override
    public MessageType parsePartialFrom(byte[] arrby) throws InvalidProtocolBufferException {
        return (MessageType)this.parsePartialFrom(arrby, 0, arrby.length, EMPTY_REGISTRY);
    }

    @Override
    public MessageType parsePartialFrom(byte[] arrby, int n, int n2) throws InvalidProtocolBufferException {
        return (MessageType)this.parsePartialFrom(arrby, n, n2, EMPTY_REGISTRY);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public MessageType parsePartialFrom(byte[] object, int n, int n2, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        CodedInputStream codedInputStream = CodedInputStream.newInstance((byte[])object, n, n2);
        object = (MessageLite)this.parsePartialFrom(codedInputStream, extensionRegistryLite);
        {
            catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                throw invalidProtocolBufferException;
            }
        }
        try {
            codedInputStream.checkLastTagWas(0);
        }
        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
            throw invalidProtocolBufferException.setUnfinishedMessage((MessageLite)object);
        }
        return (MessageType)object;
    }

    @Override
    public MessageType parsePartialFrom(byte[] arrby, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
        return (MessageType)this.parsePartialFrom(arrby, 0, arrby.length, extensionRegistryLite);
    }
}

