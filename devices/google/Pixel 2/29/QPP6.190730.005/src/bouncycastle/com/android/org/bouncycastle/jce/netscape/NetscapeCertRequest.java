/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.netscape;

import com.android.org.bouncycastle.asn1.ASN1BitString;
import com.android.org.bouncycastle.asn1.ASN1Encodable;
import com.android.org.bouncycastle.asn1.ASN1EncodableVector;
import com.android.org.bouncycastle.asn1.ASN1InputStream;
import com.android.org.bouncycastle.asn1.ASN1Object;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.ASN1Primitive;
import com.android.org.bouncycastle.asn1.ASN1Sequence;
import com.android.org.bouncycastle.asn1.DERBitString;
import com.android.org.bouncycastle.asn1.DERIA5String;
import com.android.org.bouncycastle.asn1.DERSequence;
import com.android.org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import com.android.org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.X509EncodedKeySpec;

public class NetscapeCertRequest
extends ASN1Object {
    String challenge;
    DERBitString content;
    AlgorithmIdentifier keyAlg;
    PublicKey pubkey;
    AlgorithmIdentifier sigAlg;
    byte[] sigBits;

    public NetscapeCertRequest(ASN1Sequence aSN1Primitive) {
        try {
            if (((ASN1Sequence)aSN1Primitive).size() == 3) {
                this.sigAlg = AlgorithmIdentifier.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(1));
                this.sigBits = ((DERBitString)((ASN1Sequence)aSN1Primitive).getObjectAt(2)).getOctets();
                if (((ASN1Sequence)(aSN1Primitive = (ASN1Sequence)((ASN1Sequence)aSN1Primitive).getObjectAt(0))).size() == 2) {
                    this.challenge = ((DERIA5String)((ASN1Sequence)aSN1Primitive).getObjectAt(1)).getString();
                    Object object = new DERBitString(aSN1Primitive);
                    this.content = object;
                    SubjectPublicKeyInfo subjectPublicKeyInfo = SubjectPublicKeyInfo.getInstance(((ASN1Sequence)aSN1Primitive).getObjectAt(0));
                    super(subjectPublicKeyInfo);
                    object = new X509EncodedKeySpec(((ASN1BitString)aSN1Primitive).getBytes());
                    this.keyAlg = subjectPublicKeyInfo.getAlgorithm();
                    this.pubkey = KeyFactory.getInstance(this.keyAlg.getAlgorithm().getId(), "BC").generatePublic((KeySpec)object);
                    return;
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("invalid PKAC (len): ");
                stringBuilder.append(((ASN1Sequence)aSN1Primitive).size());
                IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
                throw illegalArgumentException;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("invalid SPKAC (size):");
            stringBuilder.append(((ASN1Sequence)aSN1Primitive).size());
            IllegalArgumentException illegalArgumentException = new IllegalArgumentException(stringBuilder.toString());
            throw illegalArgumentException;
        }
        catch (Exception exception) {
            throw new IllegalArgumentException(exception.toString());
        }
    }

    public NetscapeCertRequest(String object, AlgorithmIdentifier object2, PublicKey object3) throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        this.challenge = object;
        this.sigAlg = object2;
        this.pubkey = object3;
        object2 = new ASN1EncodableVector();
        ((ASN1EncodableVector)object2).add(this.getKeySpec());
        ((ASN1EncodableVector)object2).add(new DERIA5String((String)object));
        try {
            object3 = new DERSequence((ASN1EncodableVector)object2);
            this.content = object = new DERBitString((ASN1Encodable)object3);
            return;
        }
        catch (IOException iOException) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("exception encoding key: ");
            ((StringBuilder)object2).append(iOException.toString());
            throw new InvalidKeySpecException(((StringBuilder)object2).toString());
        }
    }

    public NetscapeCertRequest(byte[] arrby) throws IOException {
        this(NetscapeCertRequest.getReq(arrby));
    }

    private ASN1Primitive getKeySpec() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchProviderException {
        Object object = new ByteArrayOutputStream();
        try {
            ((OutputStream)object).write(this.pubkey.getEncoded());
            ((ByteArrayOutputStream)object).close();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(((ByteArrayOutputStream)object).toByteArray());
            ASN1InputStream aSN1InputStream = new ASN1InputStream(byteArrayInputStream);
            object = aSN1InputStream.readObject();
            return object;
        }
        catch (IOException iOException) {
            throw new InvalidKeySpecException(iOException.getMessage());
        }
    }

    private static ASN1Sequence getReq(byte[] arrby) throws IOException {
        return ASN1Sequence.getInstance(new ASN1InputStream(new ByteArrayInputStream(arrby)).readObject());
    }

    public String getChallenge() {
        return this.challenge;
    }

    public AlgorithmIdentifier getKeyAlgorithm() {
        return this.keyAlg;
    }

    public PublicKey getPublicKey() {
        return this.pubkey;
    }

    public AlgorithmIdentifier getSigningAlgorithm() {
        return this.sigAlg;
    }

    public void setChallenge(String string) {
        this.challenge = string;
    }

    public void setKeyAlgorithm(AlgorithmIdentifier algorithmIdentifier) {
        this.keyAlg = algorithmIdentifier;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.pubkey = publicKey;
    }

    public void setSigningAlgorithm(AlgorithmIdentifier algorithmIdentifier) {
        this.sigAlg = algorithmIdentifier;
    }

    public void sign(PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidKeySpecException {
        this.sign(privateKey, null);
    }

    public void sign(PrivateKey object, SecureRandom object2) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException, InvalidKeySpecException {
        Signature signature = Signature.getInstance(this.sigAlg.getAlgorithm().getId(), "BC");
        if (object2 != null) {
            signature.initSign((PrivateKey)object, (SecureRandom)object2);
        } else {
            signature.initSign((PrivateKey)object);
        }
        object2 = new ASN1EncodableVector();
        ((ASN1EncodableVector)object2).add(this.getKeySpec());
        ((ASN1EncodableVector)object2).add(new DERIA5String(this.challenge));
        try {
            object = new DERSequence((ASN1EncodableVector)object2);
            signature.update(((ASN1Object)object).getEncoded("DER"));
            this.sigBits = signature.sign();
            return;
        }
        catch (IOException iOException) {
            throw new SignatureException(iOException.getMessage());
        }
    }

    @Override
    public ASN1Primitive toASN1Primitive() {
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        ASN1EncodableVector aSN1EncodableVector2 = new ASN1EncodableVector();
        try {
            aSN1EncodableVector2.add(this.getKeySpec());
        }
        catch (Exception exception) {
            // empty catch block
        }
        aSN1EncodableVector2.add(new DERIA5String(this.challenge));
        aSN1EncodableVector.add(new DERSequence(aSN1EncodableVector2));
        aSN1EncodableVector.add(this.sigAlg);
        aSN1EncodableVector.add(new DERBitString(this.sigBits));
        return new DERSequence(aSN1EncodableVector);
    }

    public boolean verify(String object) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, NoSuchProviderException {
        if (!((String)object).equals(this.challenge)) {
            return false;
        }
        object = Signature.getInstance(this.sigAlg.getAlgorithm().getId(), "BC");
        ((Signature)object).initVerify(this.pubkey);
        ((Signature)object).update(this.content.getBytes());
        return ((Signature)object).verify(this.sigBits);
    }
}

