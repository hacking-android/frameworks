/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.CodedOutputStream;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.MessageLiteOrBuilder;
import com.android.framework.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface MessageLite
extends MessageLiteOrBuilder {
    public Parser<? extends MessageLite> getParserForType();

    public int getSerializedSize();

    public Builder newBuilderForType();

    public Builder toBuilder();

    public byte[] toByteArray();

    public ByteString toByteString();

    public void writeDelimitedTo(OutputStream var1) throws IOException;

    public void writeTo(CodedOutputStream var1) throws IOException;

    public void writeTo(OutputStream var1) throws IOException;

    public static interface Builder
    extends MessageLiteOrBuilder,
    Cloneable {
        public MessageLite build();

        public MessageLite buildPartial();

        public Builder clear();

        public Builder clone();

        public boolean mergeDelimitedFrom(InputStream var1) throws IOException;

        public boolean mergeDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws IOException;

        public Builder mergeFrom(ByteString var1) throws InvalidProtocolBufferException;

        public Builder mergeFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

        public Builder mergeFrom(CodedInputStream var1) throws IOException;

        public Builder mergeFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws IOException;

        public Builder mergeFrom(MessageLite var1);

        public Builder mergeFrom(InputStream var1) throws IOException;

        public Builder mergeFrom(InputStream var1, ExtensionRegistryLite var2) throws IOException;

        public Builder mergeFrom(byte[] var1) throws InvalidProtocolBufferException;

        public Builder mergeFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException;

        public Builder mergeFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException;

        public Builder mergeFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;
    }

}

