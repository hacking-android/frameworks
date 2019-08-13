/*
 * Decompiled with CFR 0.145.
 */
package java.security.cert;

import java.io.IOException;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class PolicyQualifierInfo {
    private byte[] mData;
    private byte[] mEncoded;
    private String mId;
    private String pqiString;

    public PolicyQualifierInfo(byte[] object) throws IOException {
        this.mEncoded = (byte[])object.clone();
        object = new DerValue(this.mEncoded);
        if (object.tag == 48) {
            this.mId = object.data.getDerValue().getOID().toString();
            object = object.data.toByteArray();
            if (object == null) {
                this.mData = null;
            } else {
                this.mData = new byte[((byte[])object).length];
                System.arraycopy(object, 0, this.mData, 0, ((byte[])object).length);
            }
            return;
        }
        throw new IOException("Invalid encoding for PolicyQualifierInfo");
    }

    public final byte[] getEncoded() {
        return (byte[])this.mEncoded.clone();
    }

    public final byte[] getPolicyQualifier() {
        Object object = this.mData;
        object = object == null ? null : (byte[])object.clone();
        return object;
    }

    public final String getPolicyQualifierId() {
        return this.mId;
    }

    public String toString() {
        Object object = this.pqiString;
        if (object != null) {
            return object;
        }
        object = new HexDumpEncoder();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("PolicyQualifierInfo: [\n");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("  qualifierID: ");
        stringBuilder.append(this.mId);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuilder = new StringBuilder();
        stringBuilder.append("  qualifier: ");
        byte[] arrby = this.mData;
        object = arrby == null ? "null" : ((CharacterEncoder)object).encodeBuffer(arrby);
        stringBuilder.append((String)object);
        stringBuilder.append("\n");
        stringBuffer.append(stringBuilder.toString());
        stringBuffer.append("]");
        this.pqiString = stringBuffer.toString();
        return this.pqiString;
    }
}

