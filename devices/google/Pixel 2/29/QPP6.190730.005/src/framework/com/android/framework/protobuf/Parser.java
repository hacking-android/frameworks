/*
 * Decompiled with CFR 0.145.
 */
package com.android.framework.protobuf;

import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import java.io.InputStream;

public interface Parser<MessageType> {
    public MessageType parseDelimitedFrom(InputStream var1) throws InvalidProtocolBufferException;

    public MessageType parseDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(ByteString var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(CodedInputStream var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(InputStream var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parseFrom(byte[] var1) throws InvalidProtocolBufferException;

    public MessageType parseFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException;

    public MessageType parseFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException;

    public MessageType parseFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialDelimitedFrom(InputStream var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialDelimitedFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(ByteString var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(ByteString var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(CodedInputStream var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(CodedInputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(InputStream var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(InputStream var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(byte[] var1) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(byte[] var1, int var2, int var3) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(byte[] var1, int var2, int var3, ExtensionRegistryLite var4) throws InvalidProtocolBufferException;

    public MessageType parsePartialFrom(byte[] var1, ExtensionRegistryLite var2) throws InvalidProtocolBufferException;
}

