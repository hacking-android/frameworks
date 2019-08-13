/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;

public final class SignedObject
implements Serializable {
    private static final long serialVersionUID = 720502720485447167L;
    private byte[] content;
    private byte[] signature;
    private String thealgorithm;

    public SignedObject(Serializable serializable, PrivateKey privateKey, Signature signature) throws IOException, InvalidKeyException, SignatureException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(serializable);
        objectOutputStream.flush();
        objectOutputStream.close();
        this.content = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        this.sign(privateKey, signature);
    }

    private void readObject(ObjectInputStream object) throws IOException, ClassNotFoundException {
        object = ((ObjectInputStream)object).readFields();
        this.content = (byte[])((byte[])((ObjectInputStream.GetField)object).get("content", null)).clone();
        this.signature = (byte[])((byte[])((ObjectInputStream.GetField)object).get("signature", null)).clone();
        this.thealgorithm = (String)((ObjectInputStream.GetField)object).get("thealgorithm", null);
    }

    private void sign(PrivateKey privateKey, Signature signature) throws InvalidKeyException, SignatureException {
        signature.initSign(privateKey);
        signature.update((byte[])this.content.clone());
        this.signature = (byte[])signature.sign().clone();
        this.thealgorithm = signature.getAlgorithm();
    }

    public String getAlgorithm() {
        return this.thealgorithm;
    }

    public Object getObject() throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.content);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        Object object = objectInputStream.readObject();
        byteArrayInputStream.close();
        objectInputStream.close();
        return object;
    }

    public byte[] getSignature() {
        return (byte[])this.signature.clone();
    }

    public boolean verify(PublicKey publicKey, Signature signature) throws InvalidKeyException, SignatureException {
        signature.initVerify(publicKey);
        signature.update((byte[])this.content.clone());
        return signature.verify((byte[])this.signature.clone());
    }
}

