/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.nio.ByteBuffer;
import java.security.DigestException;
import java.security.MessageDigestSpi;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.Security;
import sun.security.jca.Providers;

public abstract class MessageDigest
extends MessageDigestSpi {
    private static final int INITIAL = 0;
    private static final int IN_PROGRESS = 1;
    private String algorithm;
    private Provider provider;
    private int state = 0;

    protected MessageDigest(String string) {
        this.algorithm = string;
    }

    public static MessageDigest getInstance(String string) throws NoSuchAlgorithmException {
        try {
            Object[] arrobject = Security.getImpl(string, "MessageDigest", (String)null);
            MessageDigest messageDigest = arrobject[0] instanceof MessageDigest ? (MessageDigest)arrobject[0] : new Delegate((MessageDigestSpi)arrobject[0], string);
            messageDigest.provider = (Provider)arrobject[1];
            return messageDigest;
        }
        catch (NoSuchProviderException noSuchProviderException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(" not found");
            throw new NoSuchAlgorithmException(stringBuilder.toString());
        }
    }

    public static MessageDigest getInstance(String object, String arrobject) throws NoSuchAlgorithmException, NoSuchProviderException {
        if (arrobject != null && arrobject.length() != 0) {
            Providers.checkBouncyCastleDeprecation((String)arrobject, "MessageDigest", (String)object);
            arrobject = Security.getImpl((String)object, "MessageDigest", (String)arrobject);
            if (arrobject[0] instanceof MessageDigest) {
                object = (MessageDigest)arrobject[0];
                ((MessageDigest)object).provider = (Provider)arrobject[1];
                return object;
            }
            object = new Delegate((MessageDigestSpi)arrobject[0], (String)object);
            ((MessageDigest)object).provider = (Provider)arrobject[1];
            return object;
        }
        throw new IllegalArgumentException("missing provider");
    }

    public static MessageDigest getInstance(String object, Provider arrobject) throws NoSuchAlgorithmException {
        if (arrobject != null) {
            Providers.checkBouncyCastleDeprecation((Provider)arrobject, "MessageDigest", (String)object);
            arrobject = Security.getImpl((String)object, "MessageDigest", (Provider)arrobject);
            if (arrobject[0] instanceof MessageDigest) {
                object = (MessageDigest)arrobject[0];
                ((MessageDigest)object).provider = (Provider)arrobject[1];
                return object;
            }
            object = new Delegate((MessageDigestSpi)arrobject[0], (String)object);
            ((MessageDigest)object).provider = (Provider)arrobject[1];
            return object;
        }
        throw new IllegalArgumentException("missing provider");
    }

    public static boolean isEqual(byte[] arrby, byte[] arrby2) {
        boolean bl = true;
        if (arrby == arrby2) {
            return true;
        }
        if (arrby != null && arrby2 != null) {
            if (arrby.length != arrby2.length) {
                return false;
            }
            int n = 0;
            for (int i = 0; i < arrby.length; ++i) {
                n |= arrby[i] ^ arrby2[i];
            }
            if (n != 0) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        if (this instanceof Cloneable) {
            return super.clone();
        }
        throw new CloneNotSupportedException();
    }

    public int digest(byte[] arrby, int n, int n2) throws DigestException {
        if (arrby != null) {
            if (arrby.length - n >= n2) {
                n = this.engineDigest(arrby, n, n2);
                this.state = 0;
                return n;
            }
            throw new IllegalArgumentException("Output buffer too small for specified offset and length");
        }
        throw new IllegalArgumentException("No output buffer given");
    }

    public byte[] digest() {
        byte[] arrby = this.engineDigest();
        this.state = 0;
        return arrby;
    }

    public byte[] digest(byte[] arrby) {
        this.update(arrby);
        return this.digest();
    }

    public final String getAlgorithm() {
        return this.algorithm;
    }

    public final int getDigestLength() {
        int n = this.engineGetDigestLength();
        if (n == 0) {
            try {
                int n2 = ((MessageDigest)this.clone()).digest().length;
                return n2;
            }
            catch (CloneNotSupportedException cloneNotSupportedException) {
                return n;
            }
        }
        return n;
    }

    public final Provider getProvider() {
        return this.provider;
    }

    public void reset() {
        this.engineReset();
        this.state = 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.algorithm);
        stringBuilder.append(" Message Digest from ");
        stringBuilder.append(this.provider.getName());
        stringBuilder.append(", ");
        int n = this.state;
        if (n != 0) {
            if (n == 1) {
                stringBuilder.append("<in progress>");
            }
        } else {
            stringBuilder.append("<initialized>");
        }
        return stringBuilder.toString();
    }

    public void update(byte by) {
        this.engineUpdate(by);
        this.state = 1;
    }

    public final void update(ByteBuffer byteBuffer) {
        if (byteBuffer != null) {
            this.engineUpdate(byteBuffer);
            this.state = 1;
            return;
        }
        throw new NullPointerException();
    }

    public void update(byte[] arrby) {
        this.engineUpdate(arrby, 0, arrby.length);
        this.state = 1;
    }

    public void update(byte[] arrby, int n, int n2) {
        if (arrby != null) {
            if (arrby.length - n >= n2) {
                this.engineUpdate(arrby, n, n2);
                this.state = 1;
                return;
            }
            throw new IllegalArgumentException("Input buffer too short");
        }
        throw new IllegalArgumentException("No input buffer given");
    }

    static class Delegate
    extends MessageDigest {
        private MessageDigestSpi digestSpi;

        public Delegate(MessageDigestSpi messageDigestSpi, String string) {
            super(string);
            this.digestSpi = messageDigestSpi;
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            MessageDigestSpi messageDigestSpi = this.digestSpi;
            if (messageDigestSpi instanceof Cloneable) {
                messageDigestSpi = new Delegate((MessageDigestSpi)messageDigestSpi.clone(), this.algorithm);
                ((MessageDigest)messageDigestSpi).provider = this.provider;
                ((MessageDigest)messageDigestSpi).state = this.state;
                return messageDigestSpi;
            }
            throw new CloneNotSupportedException();
        }

        @Override
        protected int engineDigest(byte[] arrby, int n, int n2) throws DigestException {
            return this.digestSpi.engineDigest(arrby, n, n2);
        }

        @Override
        protected byte[] engineDigest() {
            return this.digestSpi.engineDigest();
        }

        @Override
        protected int engineGetDigestLength() {
            return this.digestSpi.engineGetDigestLength();
        }

        @Override
        protected void engineReset() {
            this.digestSpi.engineReset();
        }

        @Override
        protected void engineUpdate(byte by) {
            this.digestSpi.engineUpdate(by);
        }

        @Override
        protected void engineUpdate(ByteBuffer byteBuffer) {
            this.digestSpi.engineUpdate(byteBuffer);
        }

        @Override
        protected void engineUpdate(byte[] arrby, int n, int n2) {
            this.digestSpi.engineUpdate(arrby, n, n2);
        }
    }

}

