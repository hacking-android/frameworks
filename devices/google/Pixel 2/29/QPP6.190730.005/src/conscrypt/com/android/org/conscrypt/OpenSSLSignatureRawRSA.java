/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.NativeCrypto;
import com.android.org.conscrypt.NativeRef;
import com.android.org.conscrypt.OpenSSLKey;
import com.android.org.conscrypt.OpenSSLRSAPrivateCrtKey;
import com.android.org.conscrypt.OpenSSLRSAPrivateKey;
import com.android.org.conscrypt.OpenSSLRSAPublicKey;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.SignatureSpi;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

public final class OpenSSLSignatureRawRSA
extends SignatureSpi {
    private byte[] inputBuffer;
    private boolean inputIsTooLong;
    private int inputOffset;
    private OpenSSLKey key;

    @Override
    protected Object engineGetParameter(String string) throws InvalidParameterException {
        return null;
    }

    @Override
    protected void engineInitSign(PrivateKey privateKey) throws InvalidKeyException {
        block5 : {
            block3 : {
                block4 : {
                    block2 : {
                        if (!(privateKey instanceof OpenSSLRSAPrivateKey)) break block2;
                        this.key = ((OpenSSLRSAPrivateKey)privateKey).getOpenSSLKey();
                        break block3;
                    }
                    if (!(privateKey instanceof RSAPrivateCrtKey)) break block4;
                    this.key = OpenSSLRSAPrivateCrtKey.getInstance((RSAPrivateCrtKey)privateKey);
                    break block3;
                }
                if (!(privateKey instanceof RSAPrivateKey)) break block5;
                this.key = OpenSSLRSAPrivateKey.getInstance((RSAPrivateKey)privateKey);
            }
            this.inputBuffer = new byte[NativeCrypto.RSA_size(this.key.getNativeRef())];
            this.inputOffset = 0;
            return;
        }
        throw new InvalidKeyException("Need RSA private key");
    }

    @Override
    protected void engineInitVerify(PublicKey publicKey) throws InvalidKeyException {
        block4 : {
            block3 : {
                block2 : {
                    if (!(publicKey instanceof OpenSSLRSAPublicKey)) break block2;
                    this.key = ((OpenSSLRSAPublicKey)publicKey).getOpenSSLKey();
                    break block3;
                }
                if (!(publicKey instanceof RSAPublicKey)) break block4;
                this.key = OpenSSLRSAPublicKey.getInstance((RSAPublicKey)publicKey);
            }
            this.inputBuffer = new byte[NativeCrypto.RSA_size(this.key.getNativeRef())];
            this.inputOffset = 0;
            return;
        }
        throw new InvalidKeyException("Need RSA public key");
    }

    @Override
    protected void engineSetParameter(String string, Object object) throws InvalidParameterException {
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    protected byte[] engineSign() throws SignatureException {
        Throwable throwable2222;
        Object object = this.key;
        if (object == null) throw new SignatureException("Need RSA private key");
        if (this.inputIsTooLong) {
            object = new StringBuilder();
            ((StringBuilder)object).append("input length ");
            ((StringBuilder)object).append(this.inputOffset);
            ((StringBuilder)object).append(" != ");
            ((StringBuilder)object).append(this.inputBuffer.length);
            ((StringBuilder)object).append(" (modulus size)");
            throw new SignatureException(((StringBuilder)object).toString());
        }
        byte[] arrby = this.inputBuffer;
        byte[] arrby2 = new byte[arrby.length];
        NativeCrypto.RSA_private_encrypt(this.inputOffset, arrby, arrby2, ((OpenSSLKey)object).getNativeRef(), 1);
        this.inputOffset = 0;
        return arrby2;
        {
            catch (Throwable throwable2222) {
            }
            catch (Exception exception) {}
            {
                object = new SignatureException(exception);
                throw object;
            }
        }
        this.inputOffset = 0;
        throw throwable2222;
    }

    @Override
    protected void engineUpdate(byte by) {
        int n = this.inputOffset;
        this.inputOffset = n + 1;
        int n2 = this.inputOffset;
        byte[] arrby = this.inputBuffer;
        if (n2 > arrby.length) {
            this.inputIsTooLong = true;
            return;
        }
        arrby[n] = by;
    }

    @Override
    protected void engineUpdate(byte[] arrby, int n, int n2) {
        int n3 = this.inputOffset;
        this.inputOffset += n2;
        int n4 = this.inputOffset;
        byte[] arrby2 = this.inputBuffer;
        if (n4 > arrby2.length) {
            this.inputIsTooLong = true;
            return;
        }
        System.arraycopy(arrby, n, arrby2, n3, n2);
    }

    /*
     * Exception decompiling
     */
    @Override
    protected boolean engineVerify(byte[] var1_1) throws SignatureException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 7[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }
}

