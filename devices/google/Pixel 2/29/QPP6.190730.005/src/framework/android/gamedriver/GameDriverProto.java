/*
 * Decompiled with CFR 0.145.
 */
package android.gamedriver;

import com.android.framework.protobuf.AbstractMessageLite;
import com.android.framework.protobuf.ByteString;
import com.android.framework.protobuf.CodedInputStream;
import com.android.framework.protobuf.CodedOutputStream;
import com.android.framework.protobuf.ExtensionRegistryLite;
import com.android.framework.protobuf.GeneratedMessageLite;
import com.android.framework.protobuf.Internal;
import com.android.framework.protobuf.InvalidProtocolBufferException;
import com.android.framework.protobuf.MessageLite;
import com.android.framework.protobuf.MessageLiteOrBuilder;
import com.android.framework.protobuf.Parser;
import com.android.framework.protobuf.UnknownFieldSetLite;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;

public final class GameDriverProto {
    private GameDriverProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    public static final class Blacklist
    extends GeneratedMessageLite<Blacklist, Builder>
    implements BlacklistOrBuilder {
        private static final Blacklist DEFAULT_INSTANCE = new Blacklist();
        public static final int PACKAGE_NAMES_FIELD_NUMBER = 2;
        private static volatile Parser<Blacklist> PARSER;
        public static final int VERSION_CODE_FIELD_NUMBER = 1;
        private int bitField0_;
        private Internal.ProtobufList<String> packageNames_ = GeneratedMessageLite.emptyProtobufList();
        private long versionCode_ = 0L;

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        private Blacklist() {
        }

        private void addAllPackageNames(Iterable<String> iterable) {
            this.ensurePackageNamesIsMutable();
            AbstractMessageLite.addAll(iterable, this.packageNames_);
        }

        private void addPackageNames(String string2) {
            if (string2 != null) {
                this.ensurePackageNamesIsMutable();
                this.packageNames_.add(string2);
                return;
            }
            throw new NullPointerException();
        }

        private void addPackageNamesBytes(ByteString byteString) {
            if (byteString != null) {
                this.ensurePackageNamesIsMutable();
                this.packageNames_.add(byteString.toStringUtf8());
                return;
            }
            throw new NullPointerException();
        }

        private void clearPackageNames() {
            this.packageNames_ = GeneratedMessageLite.emptyProtobufList();
        }

        private void clearVersionCode() {
            this.bitField0_ &= -2;
            this.versionCode_ = 0L;
        }

        private void ensurePackageNamesIsMutable() {
            if (!this.packageNames_.isModifiable()) {
                this.packageNames_ = GeneratedMessageLite.mutableCopy(this.packageNames_);
            }
        }

        public static Blacklist getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder)DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Blacklist blacklist) {
            return (Builder)((Builder)DEFAULT_INSTANCE.toBuilder()).mergeFrom(blacklist);
        }

        public static Blacklist parseDelimitedFrom(InputStream inputStream) throws IOException {
            return Blacklist.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Blacklist parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return Blacklist.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Blacklist parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Blacklist parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Blacklist parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Blacklist parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Blacklist parseFrom(InputStream inputStream) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Blacklist parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Blacklist parseFrom(byte[] arrby) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, arrby);
        }

        public static Blacklist parseFrom(byte[] arrby, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, arrby, extensionRegistryLite);
        }

        public static Parser<Blacklist> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        private void setPackageNames(int n, String string2) {
            if (string2 != null) {
                this.ensurePackageNamesIsMutable();
                this.packageNames_.set(n, string2);
                return;
            }
            throw new NullPointerException();
        }

        private void setVersionCode(long l) {
            this.bitField0_ |= 1;
            this.versionCode_ = l;
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke object, Object object2, Object object3) {
            switch (1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[((Enum)object).ordinal()]) {
                default: {
                    throw new UnsupportedOperationException();
                }
                case 8: {
                    if (PARSER != null) return PARSER;
                    // MONITORENTER : android.gamedriver.GameDriverProto$Blacklist.class
                    if (PARSER == null) {
                        PARSER = object = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                    }
                    // MONITOREXIT : android.gamedriver.GameDriverProto$Blacklist.class
                    return PARSER;
                }
                case 6: {
                    object = (CodedInputStream)object2;
                    object2 = (ExtensionRegistryLite)object3;
                    boolean bl = false;
                    while (!bl) {
                        int n = ((CodedInputStream)object).readTag();
                        if (n != 0) {
                            if (n != 8) {
                                if (n != 18) {
                                    if (this.parseUnknownField(n, (CodedInputStream)object)) continue;
                                    bl = true;
                                    continue;
                                }
                                object2 = ((CodedInputStream)object).readString();
                                if (!this.packageNames_.isModifiable()) {
                                    this.packageNames_ = GeneratedMessageLite.mutableCopy(this.packageNames_);
                                }
                                this.packageNames_.add((String)object2);
                                continue;
                            }
                            this.bitField0_ |= 1;
                            this.versionCode_ = ((CodedInputStream)object).readInt64();
                            continue;
                        }
                        bl = true;
                        continue;
                        catch (IOException iOException) {
                            object2 = new InvalidProtocolBufferException(iOException.getMessage());
                            object3 = new RuntimeException(((InvalidProtocolBufferException)object2).setUnfinishedMessage(this));
                            throw object3;
                        }
                        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                            object = new RuntimeException(invalidProtocolBufferException.setUnfinishedMessage(this));
                            throw object;
                        }
                    }
                    return DEFAULT_INSTANCE;
                }
                case 7: {
                    return DEFAULT_INSTANCE;
                }
                case 5: {
                    object = (GeneratedMessageLite.Visitor)object2;
                    object2 = (Blacklist)object3;
                    this.versionCode_ = object.visitLong(this.hasVersionCode(), this.versionCode_, ((Blacklist)object2).hasVersionCode(), ((Blacklist)object2).versionCode_);
                    this.packageNames_ = object.visitList(this.packageNames_, ((Blacklist)object2).packageNames_);
                    if (object != GeneratedMessageLite.MergeFromVisitor.INSTANCE) return this;
                    this.bitField0_ |= ((Blacklist)object2).bitField0_;
                    return this;
                }
                case 4: {
                    return new Builder();
                }
                case 3: {
                    this.packageNames_.makeImmutable();
                    return null;
                }
                case 2: {
                    return DEFAULT_INSTANCE;
                }
                case 1: 
            }
            return new Blacklist();
        }

        @Override
        public String getPackageNames(int n) {
            return (String)this.packageNames_.get(n);
        }

        @Override
        public ByteString getPackageNamesBytes(int n) {
            return ByteString.copyFromUtf8((String)this.packageNames_.get(n));
        }

        @Override
        public int getPackageNamesCount() {
            return this.packageNames_.size();
        }

        @Override
        public List<String> getPackageNamesList() {
            return this.packageNames_;
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSerializedSize;
            if (n != -1) {
                return n;
            }
            n = 0;
            if ((this.bitField0_ & 1) == 1) {
                n = 0 + CodedOutputStream.computeInt64Size(1, this.versionCode_);
            }
            int n2 = 0;
            for (int i = 0; i < this.packageNames_.size(); ++i) {
                n2 += CodedOutputStream.computeStringSizeNoTag((String)this.packageNames_.get(i));
            }
            this.memoizedSerializedSize = n = n + n2 + this.getPackageNamesList().size() * 1 + this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public long getVersionCode() {
            return this.versionCode_;
        }

        @Override
        public boolean hasVersionCode() {
            int n = this.bitField0_;
            boolean bl = true;
            if ((n & 1) != 1) {
                bl = false;
            }
            return bl;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            if ((this.bitField0_ & 1) == 1) {
                codedOutputStream.writeInt64(1, this.versionCode_);
            }
            for (int i = 0; i < this.packageNames_.size(); ++i) {
                codedOutputStream.writeString(2, (String)this.packageNames_.get(i));
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<Blacklist, Builder>
        implements BlacklistOrBuilder {
            private Builder() {
                super(DEFAULT_INSTANCE);
            }

            public Builder addAllPackageNames(Iterable<String> iterable) {
                this.copyOnWrite();
                ((Blacklist)this.instance).addAllPackageNames(iterable);
                return this;
            }

            public Builder addPackageNames(String string2) {
                this.copyOnWrite();
                ((Blacklist)this.instance).addPackageNames(string2);
                return this;
            }

            public Builder addPackageNamesBytes(ByteString byteString) {
                this.copyOnWrite();
                ((Blacklist)this.instance).addPackageNamesBytes(byteString);
                return this;
            }

            public Builder clearPackageNames() {
                this.copyOnWrite();
                ((Blacklist)this.instance).clearPackageNames();
                return this;
            }

            public Builder clearVersionCode() {
                this.copyOnWrite();
                ((Blacklist)this.instance).clearVersionCode();
                return this;
            }

            @Override
            public String getPackageNames(int n) {
                return ((Blacklist)this.instance).getPackageNames(n);
            }

            @Override
            public ByteString getPackageNamesBytes(int n) {
                return ((Blacklist)this.instance).getPackageNamesBytes(n);
            }

            @Override
            public int getPackageNamesCount() {
                return ((Blacklist)this.instance).getPackageNamesCount();
            }

            @Override
            public List<String> getPackageNamesList() {
                return Collections.unmodifiableList(((Blacklist)this.instance).getPackageNamesList());
            }

            @Override
            public long getVersionCode() {
                return ((Blacklist)this.instance).getVersionCode();
            }

            @Override
            public boolean hasVersionCode() {
                return ((Blacklist)this.instance).hasVersionCode();
            }

            public Builder setPackageNames(int n, String string2) {
                this.copyOnWrite();
                ((Blacklist)this.instance).setPackageNames(n, string2);
                return this;
            }

            public Builder setVersionCode(long l) {
                this.copyOnWrite();
                ((Blacklist)this.instance).setVersionCode(l);
                return this;
            }
        }

    }

    public static interface BlacklistOrBuilder
    extends MessageLiteOrBuilder {
        public String getPackageNames(int var1);

        public ByteString getPackageNamesBytes(int var1);

        public int getPackageNamesCount();

        public List<String> getPackageNamesList();

        public long getVersionCode();

        public boolean hasVersionCode();
    }

    public static final class Blacklists
    extends GeneratedMessageLite<Blacklists, Builder>
    implements BlacklistsOrBuilder {
        public static final int BLACKLISTS_FIELD_NUMBER = 1;
        private static final Blacklists DEFAULT_INSTANCE = new Blacklists();
        private static volatile Parser<Blacklists> PARSER;
        private Internal.ProtobufList<Blacklist> blacklists_ = Blacklists.emptyProtobufList();

        static {
            DEFAULT_INSTANCE.makeImmutable();
        }

        private Blacklists() {
        }

        private void addAllBlacklists(Iterable<? extends Blacklist> iterable) {
            this.ensureBlacklistsIsMutable();
            AbstractMessageLite.addAll(iterable, this.blacklists_);
        }

        private void addBlacklists(int n, Blacklist.Builder builder) {
            this.ensureBlacklistsIsMutable();
            this.blacklists_.add(n, (Blacklist)builder.build());
        }

        private void addBlacklists(int n, Blacklist blacklist) {
            if (blacklist != null) {
                this.ensureBlacklistsIsMutable();
                this.blacklists_.add(n, blacklist);
                return;
            }
            throw new NullPointerException();
        }

        private void addBlacklists(Blacklist.Builder builder) {
            this.ensureBlacklistsIsMutable();
            this.blacklists_.add((Blacklist)builder.build());
        }

        private void addBlacklists(Blacklist blacklist) {
            if (blacklist != null) {
                this.ensureBlacklistsIsMutable();
                this.blacklists_.add(blacklist);
                return;
            }
            throw new NullPointerException();
        }

        private void clearBlacklists() {
            this.blacklists_ = Blacklists.emptyProtobufList();
        }

        private void ensureBlacklistsIsMutable() {
            if (!this.blacklists_.isModifiable()) {
                this.blacklists_ = GeneratedMessageLite.mutableCopy(this.blacklists_);
            }
        }

        public static Blacklists getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Builder newBuilder() {
            return (Builder)DEFAULT_INSTANCE.toBuilder();
        }

        public static Builder newBuilder(Blacklists blacklists) {
            return (Builder)((Builder)DEFAULT_INSTANCE.toBuilder()).mergeFrom(blacklists);
        }

        public static Blacklists parseDelimitedFrom(InputStream inputStream) throws IOException {
            return Blacklists.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Blacklists parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return Blacklists.parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Blacklists parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static Blacklists parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static Blacklists parseFrom(CodedInputStream codedInputStream) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static Blacklists parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Blacklists parseFrom(InputStream inputStream) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static Blacklists parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static Blacklists parseFrom(byte[] arrby) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, arrby);
        }

        public static Blacklists parseFrom(byte[] arrby, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, arrby, extensionRegistryLite);
        }

        public static Parser<Blacklists> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }

        private void removeBlacklists(int n) {
            this.ensureBlacklistsIsMutable();
            this.blacklists_.remove(n);
        }

        private void setBlacklists(int n, Blacklist.Builder builder) {
            this.ensureBlacklistsIsMutable();
            this.blacklists_.set(n, (Blacklist)builder.build());
        }

        private void setBlacklists(int n, Blacklist blacklist) {
            if (blacklist != null) {
                this.ensureBlacklistsIsMutable();
                this.blacklists_.set(n, blacklist);
                return;
            }
            throw new NullPointerException();
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke object, Object object2, Object object3) {
            switch (1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[((Enum)object).ordinal()]) {
                default: {
                    throw new UnsupportedOperationException();
                }
                case 8: {
                    if (PARSER != null) return PARSER;
                    // MONITORENTER : android.gamedriver.GameDriverProto$Blacklists.class
                    if (PARSER == null) {
                        PARSER = object = new GeneratedMessageLite.DefaultInstanceBasedParser(DEFAULT_INSTANCE);
                    }
                    // MONITOREXIT : android.gamedriver.GameDriverProto$Blacklists.class
                    return PARSER;
                }
                case 6: {
                    object = (CodedInputStream)object2;
                    object2 = (ExtensionRegistryLite)object3;
                    boolean bl = false;
                    while (!bl) {
                        int n = ((CodedInputStream)object).readTag();
                        if (n != 0) {
                            if (n != 10) {
                                if (this.parseUnknownField(n, (CodedInputStream)object)) continue;
                                bl = true;
                                continue;
                            }
                            if (!this.blacklists_.isModifiable()) {
                                this.blacklists_ = GeneratedMessageLite.mutableCopy(this.blacklists_);
                            }
                            this.blacklists_.add(((CodedInputStream)object).readMessage(Blacklist.parser(), (ExtensionRegistryLite)object2));
                            continue;
                        }
                        bl = true;
                        continue;
                        catch (IOException iOException) {
                            object3 = new InvalidProtocolBufferException(iOException.getMessage());
                            object2 = new RuntimeException(((InvalidProtocolBufferException)object3).setUnfinishedMessage(this));
                            throw object2;
                        }
                        catch (InvalidProtocolBufferException invalidProtocolBufferException) {
                            object = new RuntimeException(invalidProtocolBufferException.setUnfinishedMessage(this));
                            throw object;
                        }
                    }
                    return DEFAULT_INSTANCE;
                }
                case 7: {
                    return DEFAULT_INSTANCE;
                }
                case 5: {
                    object = (GeneratedMessageLite.Visitor)object2;
                    object2 = (Blacklists)object3;
                    this.blacklists_ = object.visitList(this.blacklists_, ((Blacklists)object2).blacklists_);
                    object = GeneratedMessageLite.MergeFromVisitor.INSTANCE;
                    return this;
                }
                case 4: {
                    return new Builder();
                }
                case 3: {
                    this.blacklists_.makeImmutable();
                    return null;
                }
                case 2: {
                    return DEFAULT_INSTANCE;
                }
                case 1: 
            }
            return new Blacklists();
        }

        @Override
        public Blacklist getBlacklists(int n) {
            return (Blacklist)this.blacklists_.get(n);
        }

        @Override
        public int getBlacklistsCount() {
            return this.blacklists_.size();
        }

        @Override
        public List<Blacklist> getBlacklistsList() {
            return this.blacklists_;
        }

        public BlacklistOrBuilder getBlacklistsOrBuilder(int n) {
            return (BlacklistOrBuilder)this.blacklists_.get(n);
        }

        public List<? extends BlacklistOrBuilder> getBlacklistsOrBuilderList() {
            return this.blacklists_;
        }

        @Override
        public int getSerializedSize() {
            int n = this.memoizedSerializedSize;
            if (n != -1) {
                return n;
            }
            int n2 = 0;
            for (n = 0; n < this.blacklists_.size(); ++n) {
                n2 += CodedOutputStream.computeMessageSize(1, (MessageLite)this.blacklists_.get(n));
            }
            this.memoizedSerializedSize = n = n2 + this.unknownFields.getSerializedSize();
            return n;
        }

        @Override
        public void writeTo(CodedOutputStream codedOutputStream) throws IOException {
            for (int i = 0; i < this.blacklists_.size(); ++i) {
                codedOutputStream.writeMessage(1, (MessageLite)this.blacklists_.get(i));
            }
            this.unknownFields.writeTo(codedOutputStream);
        }

        public static final class Builder
        extends GeneratedMessageLite.Builder<Blacklists, Builder>
        implements BlacklistsOrBuilder {
            private Builder() {
                super(DEFAULT_INSTANCE);
            }

            public Builder addAllBlacklists(Iterable<? extends Blacklist> iterable) {
                this.copyOnWrite();
                ((Blacklists)this.instance).addAllBlacklists(iterable);
                return this;
            }

            public Builder addBlacklists(int n, Blacklist.Builder builder) {
                this.copyOnWrite();
                ((Blacklists)this.instance).addBlacklists(n, builder);
                return this;
            }

            public Builder addBlacklists(int n, Blacklist blacklist) {
                this.copyOnWrite();
                ((Blacklists)this.instance).addBlacklists(n, blacklist);
                return this;
            }

            public Builder addBlacklists(Blacklist.Builder builder) {
                this.copyOnWrite();
                ((Blacklists)this.instance).addBlacklists(builder);
                return this;
            }

            public Builder addBlacklists(Blacklist blacklist) {
                this.copyOnWrite();
                ((Blacklists)this.instance).addBlacklists(blacklist);
                return this;
            }

            public Builder clearBlacklists() {
                this.copyOnWrite();
                ((Blacklists)this.instance).clearBlacklists();
                return this;
            }

            @Override
            public Blacklist getBlacklists(int n) {
                return ((Blacklists)this.instance).getBlacklists(n);
            }

            @Override
            public int getBlacklistsCount() {
                return ((Blacklists)this.instance).getBlacklistsCount();
            }

            @Override
            public List<Blacklist> getBlacklistsList() {
                return Collections.unmodifiableList(((Blacklists)this.instance).getBlacklistsList());
            }

            public Builder removeBlacklists(int n) {
                this.copyOnWrite();
                ((Blacklists)this.instance).removeBlacklists(n);
                return this;
            }

            public Builder setBlacklists(int n, Blacklist.Builder builder) {
                this.copyOnWrite();
                ((Blacklists)this.instance).setBlacklists(n, builder);
                return this;
            }

            public Builder setBlacklists(int n, Blacklist blacklist) {
                this.copyOnWrite();
                ((Blacklists)this.instance).setBlacklists(n, blacklist);
                return this;
            }
        }

    }

    public static interface BlacklistsOrBuilder
    extends MessageLiteOrBuilder {
        public Blacklist getBlacklists(int var1);

        public int getBlacklistsCount();

        public List<Blacklist> getBlacklistsList();
    }

}

