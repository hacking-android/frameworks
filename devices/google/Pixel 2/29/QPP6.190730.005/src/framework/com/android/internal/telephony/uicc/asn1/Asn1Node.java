/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.asn1;

import com.android.internal.telephony.uicc.IccUtils;
import com.android.internal.telephony.uicc.asn1.Asn1Decoder;
import com.android.internal.telephony.uicc.asn1.InvalidAsn1DataException;
import com.android.internal.telephony.uicc.asn1.TagNotFoundException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Asn1Node {
    private static final List<Asn1Node> EMPTY_NODE_LIST = Collections.emptyList();
    private static final byte[] FALSE_BYTES;
    private static final int INT_BYTES = 4;
    private static final byte[] TRUE_BYTES;
    private final List<Asn1Node> mChildren;
    private final boolean mConstructed;
    private byte[] mDataBytes;
    private int mDataLength;
    private int mDataOffset;
    private int mEncodedLength;
    private final int mTag;

    static {
        TRUE_BYTES = new byte[]{-1};
        FALSE_BYTES = new byte[]{0};
    }

    private Asn1Node(int n, List<Asn1Node> list) {
        this.mTag = n;
        this.mConstructed = true;
        this.mChildren = list;
        this.mDataLength = 0;
        int n2 = list.size();
        for (n = 0; n < n2; ++n) {
            this.mDataLength += list.get((int)n).mEncodedLength;
        }
        this.mEncodedLength = IccUtils.byteNumForUnsignedInt(this.mTag) + Asn1Node.calculateEncodedBytesNumForLength(this.mDataLength) + this.mDataLength;
    }

    Asn1Node(int n, byte[] object, int n2, int n3) {
        this.mTag = n;
        this.mConstructed = Asn1Node.isConstructedTag(n);
        this.mDataBytes = object;
        this.mDataOffset = n2;
        this.mDataLength = n3;
        object = this.mConstructed ? new ArrayList() : EMPTY_NODE_LIST;
        this.mChildren = object;
        this.mEncodedLength = IccUtils.byteNumForUnsignedInt(this.mTag) + Asn1Node.calculateEncodedBytesNumForLength(this.mDataLength) + this.mDataLength;
    }

    private static int calculateEncodedBytesNumForLength(int n) {
        int n2 = 1;
        if (n > 127) {
            n2 = 1 + IccUtils.byteNumForUnsignedInt(n);
        }
        return n2;
    }

    private static boolean isConstructedTag(int n) {
        byte[] arrby = IccUtils.unsignedIntToBytes(n);
        boolean bl = false;
        if ((arrby[0] & 32) != 0) {
            bl = true;
        }
        return bl;
    }

    public static Builder newBuilder(int n) {
        return new Builder(n);
    }

    private int write(byte[] arrby, int n) {
        int n2;
        block4 : {
            block3 : {
                int n3 = n + IccUtils.unsignedIntToBytes(this.mTag, arrby, n);
                n2 = this.mDataLength;
                if (n2 <= 127) {
                    n = n3 + 1;
                    arrby[n3] = (byte)n2;
                } else {
                    n = n3 + 1;
                    n2 = IccUtils.unsignedIntToBytes(n2, arrby, n);
                    arrby[n - 1] = (byte)(n2 | 128);
                    n += n2;
                }
                if (!this.mConstructed || this.mDataBytes != null) break block3;
                n3 = this.mChildren.size();
                for (n2 = 0; n2 < n3; ++n2) {
                    n = this.mChildren.get(n2).write(arrby, n);
                }
                n2 = n;
                break block4;
            }
            byte[] arrby2 = this.mDataBytes;
            n2 = n;
            if (arrby2 == null) break block4;
            System.arraycopy(arrby2, this.mDataOffset, arrby, n, this.mDataLength);
            n2 = n + this.mDataLength;
        }
        return n2;
    }

    public int asBits() throws InvalidAsn1DataException {
        if (!this.mConstructed) {
            byte[] arrby = this.mDataBytes;
            if (arrby != null) {
                int n;
                try {
                    n = IccUtils.bytesToInt(arrby, this.mDataOffset + 1, this.mDataLength - 1);
                }
                catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {
                    throw new InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", runtimeException);
                }
                for (int i = this.mDataLength - 1; i < 4; ++i) {
                    n <<= 8;
                }
                return Integer.reverse(n);
            }
            throw new InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        throw new IllegalStateException("Cannot get value of a constructed node.");
    }

    public boolean asBoolean() throws InvalidAsn1DataException {
        if (!this.mConstructed) {
            Object object = this.mDataBytes;
            if (object != null) {
                if (this.mDataLength == 1) {
                    int n = this.mDataOffset;
                    if (n >= 0 && n < ((byte[])object).length) {
                        if (object[n] == -1) {
                            return Boolean.TRUE;
                        }
                        if (object[n] == false) {
                            return Boolean.FALSE;
                        }
                        n = this.mTag;
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Cannot parse data bytes as boolean: ");
                        ((StringBuilder)object).append(this.mDataBytes[this.mDataOffset]);
                        throw new InvalidAsn1DataException(n, ((StringBuilder)object).toString());
                    }
                    throw new InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", new ArrayIndexOutOfBoundsException(this.mDataOffset));
                }
                int n = this.mTag;
                object = new StringBuilder();
                ((StringBuilder)object).append("Cannot parse data bytes as boolean: length=");
                ((StringBuilder)object).append(this.mDataLength);
                throw new InvalidAsn1DataException(n, ((StringBuilder)object).toString());
            }
            throw new InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        throw new IllegalStateException("Cannot get value of a constructed node.");
    }

    public byte[] asBytes() throws InvalidAsn1DataException {
        if (!this.mConstructed) {
            byte[] arrby = this.mDataBytes;
            if (arrby != null) {
                int n = this.mDataLength;
                byte[] arrby2 = new byte[n];
                try {
                    System.arraycopy(arrby, this.mDataOffset, arrby2, 0, n);
                    return arrby2;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", indexOutOfBoundsException);
                }
            }
            throw new InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        throw new IllegalStateException("Cannot get value of a constructed node.");
    }

    public int asInteger() throws InvalidAsn1DataException {
        if (!this.mConstructed) {
            byte[] arrby = this.mDataBytes;
            if (arrby != null) {
                try {
                    int n = IccUtils.bytesToInt(arrby, this.mDataOffset, this.mDataLength);
                    return n;
                }
                catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {
                    throw new InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", runtimeException);
                }
            }
            throw new InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        throw new IllegalStateException("Cannot get value of a constructed node.");
    }

    public long asRawLong() throws InvalidAsn1DataException {
        if (!this.mConstructed) {
            byte[] arrby = this.mDataBytes;
            if (arrby != null) {
                try {
                    long l = IccUtils.bytesToRawLong(arrby, this.mDataOffset, this.mDataLength);
                    return l;
                }
                catch (IllegalArgumentException | IndexOutOfBoundsException runtimeException) {
                    throw new InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", runtimeException);
                }
            }
            throw new InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        throw new IllegalStateException("Cannot get value of a constructed node.");
    }

    public String asString() throws InvalidAsn1DataException {
        if (!this.mConstructed) {
            Object object = this.mDataBytes;
            if (object != null) {
                try {
                    object = new String((byte[])object, this.mDataOffset, this.mDataLength, StandardCharsets.UTF_8);
                    return object;
                }
                catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    throw new InvalidAsn1DataException(this.mTag, "Cannot parse data bytes.", indexOutOfBoundsException);
                }
            }
            throw new InvalidAsn1DataException(this.mTag, "Data bytes cannot be null.");
        }
        throw new IllegalStateException("Cannot get value of a constructed node.");
    }

    public Asn1Node getChild(int n, int ... arrn) throws TagNotFoundException, InvalidAsn1DataException {
        if (this.mConstructed) {
            Asn1Node asn1Node;
            int n2 = 0;
            Asn1Node asn1Node2 = this;
            int n3 = n;
            n = n2;
            do {
                asn1Node = asn1Node2;
                if (asn1Node2 == null) break;
                List<Asn1Node> list = asn1Node2.getChildren();
                int n4 = list.size();
                asn1Node = null;
                n2 = 0;
                do {
                    asn1Node2 = asn1Node;
                    if (n2 >= n4 || (asn1Node2 = list.get(n2)).getTag() == n3) break;
                    ++n2;
                } while (true);
                if (n >= arrn.length) {
                    asn1Node = asn1Node2;
                    break;
                }
                n3 = arrn[n];
                ++n;
            } while (true);
            if (asn1Node != null) {
                return asn1Node;
            }
            throw new TagNotFoundException(n3);
        }
        throw new TagNotFoundException(n);
    }

    public List<Asn1Node> getChildren() throws InvalidAsn1DataException {
        if (!this.mConstructed) {
            return EMPTY_NODE_LIST;
        }
        Object object = this.mDataBytes;
        if (object != null) {
            object = new Asn1Decoder((byte[])object, this.mDataOffset, this.mDataLength);
            while (((Asn1Decoder)object).hasNextNode()) {
                this.mChildren.add(((Asn1Decoder)object).nextNode());
            }
            this.mDataBytes = null;
            this.mDataOffset = 0;
        }
        return this.mChildren;
    }

    public List<Asn1Node> getChildren(int n) throws TagNotFoundException, InvalidAsn1DataException {
        List<Asn1Node> list;
        block3 : {
            if (!this.mConstructed) {
                return EMPTY_NODE_LIST;
            }
            List<Asn1Node> list2 = this.getChildren();
            if (list2.isEmpty()) {
                return EMPTY_NODE_LIST;
            }
            list = new ArrayList<Asn1Node>();
            int n2 = list2.size();
            for (int i = 0; i < n2; ++i) {
                Asn1Node asn1Node = list2.get(i);
                if (asn1Node.getTag() != n) continue;
                list.add(asn1Node);
            }
            if (!list.isEmpty()) break block3;
            list = EMPTY_NODE_LIST;
        }
        return list;
    }

    public int getDataLength() {
        return this.mDataLength;
    }

    public int getEncodedLength() {
        return this.mEncodedLength;
    }

    public String getHeadAsHex() {
        Object object;
        CharSequence charSequence = IccUtils.bytesToHexString(IccUtils.unsignedIntToBytes(this.mTag));
        int n = this.mDataLength;
        if (n <= 127) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append(IccUtils.byteToHex((byte)this.mDataLength));
            object = ((StringBuilder)object).toString();
        } else {
            object = IccUtils.unsignedIntToBytes(n);
            CharSequence charSequence2 = new StringBuilder();
            charSequence2.append((String)charSequence);
            charSequence2.append(IccUtils.byteToHex((byte)(((Object)object).length | 128)));
            charSequence2 = charSequence2.toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(IccUtils.bytesToHexString((byte[])object));
            object = ((StringBuilder)charSequence).toString();
        }
        return object;
    }

    public int getTag() {
        return this.mTag;
    }

    public boolean hasChild(int n, int ... arrn) throws InvalidAsn1DataException {
        try {
            this.getChild(n, arrn);
            return true;
        }
        catch (TagNotFoundException tagNotFoundException) {
            return false;
        }
    }

    public boolean hasValue() {
        boolean bl = !this.mConstructed && this.mDataBytes != null;
        return bl;
    }

    public boolean isConstructed() {
        return this.mConstructed;
    }

    public byte[] toBytes() {
        byte[] arrby = new byte[this.mEncodedLength];
        this.write(arrby, 0);
        return arrby;
    }

    public String toHex() {
        return IccUtils.bytesToHexString(this.toBytes());
    }

    public void writeToBytes(byte[] object, int n) {
        if (n >= 0 && this.mEncodedLength + n <= ((byte[])object).length) {
            this.write((byte[])object, n);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Not enough space to write. Required bytes: ");
        ((StringBuilder)object).append(this.mEncodedLength);
        throw new IndexOutOfBoundsException(((StringBuilder)object).toString());
    }

    public static final class Builder {
        private final List<Asn1Node> mChildren;
        private final int mTag;

        private Builder(int n) {
            if (Asn1Node.isConstructedTag(n)) {
                this.mTag = n;
                this.mChildren = new ArrayList<Asn1Node>();
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Builder should be created for a constructed tag: ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }

        public Builder addChild(Builder builder) {
            this.mChildren.add(builder.build());
            return this;
        }

        public Builder addChild(Asn1Node asn1Node) {
            this.mChildren.add(asn1Node);
            return this;
        }

        public Builder addChildAsBits(int n, int n2) {
            if (!Asn1Node.isConstructedTag(n)) {
                byte[] arrby = new byte[5];
                int n3 = Integer.reverse(n2);
                int n4 = 0;
                for (n2 = 1; n2 < arrby.length; ++n2) {
                    arrby[n2] = (byte)(n3 >> (4 - n2) * 8);
                    if (arrby[n2] == 0) continue;
                    n4 = n2;
                }
                n2 = n4 + 1;
                arrby[0] = IccUtils.countTrailingZeros(arrby[n2 - 1]);
                this.addChild(new Asn1Node(n, arrby, 0, n2));
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot set value of a constructed tag: ");
            stringBuilder.append(n);
            throw new IllegalStateException(stringBuilder.toString());
        }

        public Builder addChildAsBoolean(int n, boolean bl) {
            if (!Asn1Node.isConstructedTag(n)) {
                byte[] arrby = bl ? TRUE_BYTES : FALSE_BYTES;
                this.addChild(new Asn1Node(n, arrby, 0, 1));
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot set value of a constructed tag: ");
            stringBuilder.append(n);
            throw new IllegalStateException(stringBuilder.toString());
        }

        public Builder addChildAsBytes(int n, byte[] object) {
            if (!Asn1Node.isConstructedTag(n)) {
                this.addChild(new Asn1Node(n, (byte[])object, 0, ((Object)object).length));
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot set value of a constructed tag: ");
            ((StringBuilder)object).append(n);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        public Builder addChildAsBytesFromHex(int n, String string2) {
            return this.addChildAsBytes(n, IccUtils.hexStringToBytes(string2));
        }

        public Builder addChildAsInteger(int n, int n2) {
            if (!Asn1Node.isConstructedTag(n)) {
                byte[] arrby = IccUtils.signedIntToBytes(n2);
                this.addChild(new Asn1Node(n, arrby, 0, arrby.length));
                return this;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot set value of a constructed tag: ");
            stringBuilder.append(n);
            throw new IllegalStateException(stringBuilder.toString());
        }

        public Builder addChildAsString(int n, String object) {
            if (!Asn1Node.isConstructedTag(n)) {
                object = ((String)object).getBytes(StandardCharsets.UTF_8);
                this.addChild(new Asn1Node(n, (byte[])object, 0, ((Object)object).length));
                return this;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Cannot set value of a constructed tag: ");
            ((StringBuilder)object).append(n);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }

        public Builder addChildren(byte[] object) throws InvalidAsn1DataException {
            object = new Asn1Decoder((byte[])object, 0, ((byte[])object).length);
            while (((Asn1Decoder)object).hasNextNode()) {
                this.mChildren.add(((Asn1Decoder)object).nextNode());
            }
            return this;
        }

        public Asn1Node build() {
            return new Asn1Node(this.mTag, this.mChildren);
        }
    }

}

