/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Security;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;
import sun.misc.HexDumpEncoder;
import sun.security.util.BitArray;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.x509.AlgorithmId;

public class X509Key
implements PublicKey {
    private static final long serialVersionUID = -5359250853002055002L;
    protected AlgorithmId algid;
    private BitArray bitStringKey = null;
    protected byte[] encodedKey;
    @Deprecated
    protected byte[] key = null;
    @Deprecated
    private int unusedBits = 0;

    public X509Key() {
    }

    private X509Key(AlgorithmId algorithmId, BitArray bitArray) throws InvalidKeyException {
        this.algid = algorithmId;
        this.setKey(bitArray);
        this.encode();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static PublicKey buildX509Key(AlgorithmId algorithmId, BitArray bitArray) throws IOException, InvalidKeyException {
        object = new DerOutputStream();
        X509Key.encode((DerOutputStream)object, algorithmId, bitArray);
        object = new X509EncodedKeySpec(object.toByteArray());
        try {
            return KeyFactory.getInstance(algorithmId.getName()).generatePublic((KeySpec)object);
        }
        catch (InvalidKeySpecException invalidKeySpecException) {
            throw new InvalidKeyException(invalidKeySpecException.getMessage(), invalidKeySpecException);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            serializable = "";
            charSequence /* !! */  = serializable;
            try {
                object2 = Security.getProvider("SUN");
                if (object2 == null) ** GOTO lbl63
                charSequence /* !! */  = serializable;
                charSequence /* !! */  = serializable;
                charSequence2 /* !! */  = new StringBuilder();
                charSequence /* !! */  = serializable;
                charSequence2 /* !! */ .append("PublicKey.X.509.");
                charSequence /* !! */  = serializable;
                charSequence2 /* !! */ .append(algorithmId.getName());
                charSequence /* !! */  = serializable;
                charSequence2 /* !! */  = object2.getProperty(charSequence2 /* !! */ .toString());
                if (charSequence2 /* !! */  == null) ** GOTO lbl58
                serializable = null;
                charSequence /* !! */  = charSequence2 /* !! */ ;
                {
                    catch (ClassNotFoundException classNotFoundException) {
                        // empty catch block
                        return new X509Key(algorithmId, bitArray);
                    }
                }
                try {
                    object2 = Class.forName((String)charSequence2 /* !! */ );
                    serializable = object2;
                    ** GOTO lbl43
                }
                catch (ClassNotFoundException classNotFoundException) {
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    object2 = ClassLoader.getSystemClassLoader();
                    if (object2 != null) {
                        charSequence /* !! */  = charSequence2 /* !! */ ;
                        serializable = object2.loadClass((String)charSequence2 /* !! */ );
                    }
lbl43: // 4 sources:
                    object2 = null;
                    if (serializable != null) {
                        charSequence /* !! */  = charSequence2 /* !! */ ;
                        object2 = serializable.newInstance();
                    }
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    if (object2 instanceof X509Key == false) return new X509Key(algorithmId, bitArray);
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    serializable = (X509Key)object2;
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    serializable.algid = algorithmId;
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    serializable.setKey(bitArray);
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    serializable.parseKeyBits();
                    return serializable;
lbl58: // 1 sources:
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    serializable = new InstantiationException();
                    charSequence /* !! */  = charSequence2 /* !! */ ;
                    throw serializable;
lbl63: // 1 sources:
                    charSequence /* !! */  = serializable;
                    charSequence /* !! */  = serializable;
                    instantiationException = new InstantiationException();
                    charSequence /* !! */  = serializable;
                    throw instantiationException;
                }
            }
            catch (IllegalAccessException illegalAccessException) {
                stringBuilder = new StringBuilder();
                stringBuilder.append(charSequence /* !! */ );
                stringBuilder.append(" [internal error]");
                throw new IOException(stringBuilder.toString());
            }
            catch (InstantiationException instantiationException2) {
                return new X509Key(algorithmId, bitArray);
            }
        }
    }

    static void encode(DerOutputStream derOutputStream, AlgorithmId algorithmId, BitArray bitArray) throws IOException {
        DerOutputStream derOutputStream2 = new DerOutputStream();
        algorithmId.encode(derOutputStream2);
        derOutputStream2.putUnalignedBitString(bitArray);
        derOutputStream.write((byte)48, derOutputStream2);
    }

    public static PublicKey parse(DerValue object) throws IOException {
        if (((DerValue)object).tag == 48) {
            Serializable serializable = AlgorithmId.parse(((DerValue)object).data.getDerValue());
            try {
                serializable = X509Key.buildX509Key(serializable, ((DerValue)object).data.getUnalignedBitString());
                if (((DerValue)object).data.available() == 0) {
                    return serializable;
                }
                throw new IOException("excess subject key");
            }
            catch (InvalidKeyException invalidKeyException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("subject key, ");
                ((StringBuilder)object).append(invalidKeyException.getMessage());
                throw new IOException(((StringBuilder)object).toString(), invalidKeyException);
            }
        }
        throw new IOException("corrupt subject key");
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException {
        try {
            this.decode(objectInputStream);
            return;
        }
        catch (InvalidKeyException invalidKeyException) {
            invalidKeyException.printStackTrace();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("deserialized key is invalid: ");
            stringBuilder.append(invalidKeyException.getMessage());
            throw new IOException(stringBuilder.toString());
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.write(this.getEncoded());
    }

    public void decode(InputStream object) throws InvalidKeyException {
        try {
            DerValue derValue = new DerValue((InputStream)object);
            if (derValue.tag == 48) {
                this.algid = AlgorithmId.parse(derValue.data.getDerValue());
                this.setKey(derValue.data.getUnalignedBitString());
                this.parseKeyBits();
                if (derValue.data.available() == 0) {
                    return;
                }
                object = new InvalidKeyException("excess key data");
                throw object;
            }
            object = new InvalidKeyException("invalid key format");
            throw object;
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IOException: ");
            stringBuilder.append(iOException.getMessage());
            throw new InvalidKeyException(stringBuilder.toString());
        }
    }

    public void decode(byte[] arrby) throws InvalidKeyException {
        this.decode(new ByteArrayInputStream(arrby));
    }

    public final void encode(DerOutputStream derOutputStream) throws IOException {
        X509Key.encode(derOutputStream, this.algid, this.getKey());
    }

    public byte[] encode() throws InvalidKeyException {
        return (byte[])this.getEncodedInternal().clone();
    }

    public boolean equals(Object arrby) {
        if (this == arrby) {
            return true;
        }
        if (!(arrby instanceof Key)) {
            return false;
        }
        try {
            byte[] arrby2 = this.getEncodedInternal();
            arrby = arrby instanceof X509Key ? ((X509Key)arrby).getEncodedInternal() : ((Key)arrby).getEncoded();
            boolean bl = Arrays.equals(arrby2, arrby);
            return bl;
        }
        catch (InvalidKeyException invalidKeyException) {
            return false;
        }
    }

    @Override
    public String getAlgorithm() {
        return this.algid.getName();
    }

    public AlgorithmId getAlgorithmId() {
        return this.algid;
    }

    @Override
    public byte[] getEncoded() {
        try {
            byte[] arrby = (byte[])this.getEncodedInternal().clone();
            return arrby;
        }
        catch (InvalidKeyException invalidKeyException) {
            return null;
        }
    }

    public byte[] getEncodedInternal() throws InvalidKeyException {
        byte[] arrby = this.encodedKey;
        Object object = arrby;
        if (arrby == null) {
            try {
                object = new DerOutputStream();
                this.encode((DerOutputStream)object);
                object = ((ByteArrayOutputStream)object).toByteArray();
                this.encodedKey = object;
            }
            catch (IOException iOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("IOException : ");
                ((StringBuilder)object).append(iOException.getMessage());
                throw new InvalidKeyException(((StringBuilder)object).toString());
            }
        }
        return object;
    }

    @Override
    public String getFormat() {
        return "X.509";
    }

    protected BitArray getKey() {
        byte[] arrby = this.key;
        this.bitStringKey = new BitArray(arrby.length * 8 - this.unusedBits, arrby);
        return (BitArray)this.bitStringKey.clone();
    }

    public int hashCode() {
        byte[] arrby;
        int n;
        int n2;
        try {
            arrby = this.getEncodedInternal();
            n2 = arrby.length;
            n = 0;
        }
        catch (InvalidKeyException invalidKeyException) {
            return 0;
        }
        do {
            if (n >= arrby.length) break;
            byte by = arrby[n];
            n2 += (by & 255) * 37;
            ++n;
        } while (true);
        return n2;
    }

    protected void parseKeyBits() throws IOException, InvalidKeyException {
        this.encode();
    }

    protected void setKey(BitArray bitArray) {
        this.bitStringKey = (BitArray)bitArray.clone();
        this.key = bitArray.toByteArray();
        int n = bitArray.length() % 8;
        n = n == 0 ? 0 : 8 - n;
        this.unusedBits = n;
    }

    public String toString() {
        HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("algorithm = ");
        stringBuilder.append(this.algid.toString());
        stringBuilder.append(", unparsed keybits = \n");
        stringBuilder.append(hexDumpEncoder.encodeBuffer(this.key));
        return stringBuilder.toString();
    }
}

