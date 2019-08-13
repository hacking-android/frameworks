/*
 * Decompiled with CFR 0.145.
 */
package sun.security.util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Arrays;
import sun.security.util.DerInputBuffer;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;

public final class ObjectIdentifier
implements Serializable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final long serialVersionUID = 8697030238860181294L;
    private int componentLen;
    private Object components;
    private transient boolean componentsCalculated;
    private byte[] encoding;
    private volatile transient String stringForm;

    /*
     * Exception decompiling
     */
    public ObjectIdentifier(String var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[DOLOOP]], but top level block is 2[TRYBLOCK]
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

    ObjectIdentifier(DerInputBuffer object) throws IOException {
        this.encoding = null;
        this.components = null;
        this.componentLen = -1;
        this.componentsCalculated = false;
        object = new DerInputStream((DerInputBuffer)object);
        this.encoding = new byte[((DerInputStream)object).available()];
        ((DerInputStream)object).getBytes(this.encoding);
        ObjectIdentifier.check(this.encoding);
    }

    public ObjectIdentifier(DerInputStream object) throws IOException {
        this.encoding = null;
        this.components = null;
        this.componentLen = -1;
        this.componentsCalculated = false;
        int n = ((DerInputStream)object).getByte();
        if (n == 6) {
            n = ((DerInputStream)object).getLength();
            if (n <= ((DerInputStream)object).available()) {
                this.encoding = new byte[n];
                ((DerInputStream)object).getBytes(this.encoding);
                ObjectIdentifier.check(this.encoding);
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ObjectIdentifier() -- length exceedsdata available.  Length: ");
            stringBuilder.append(n);
            stringBuilder.append(", Available: ");
            stringBuilder.append(((DerInputStream)object).available());
            throw new IOException(stringBuilder.toString());
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("ObjectIdentifier() -- data isn't an object ID (tag = ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(")");
        throw new IOException(((StringBuilder)object).toString());
    }

    public ObjectIdentifier(int[] arrn) throws IOException {
        this.encoding = null;
        this.components = null;
        this.componentLen = -1;
        this.componentsCalculated = false;
        ObjectIdentifier.checkCount(arrn.length);
        ObjectIdentifier.checkFirstComponent(arrn[0]);
        ObjectIdentifier.checkSecondComponent(arrn[0], arrn[1]);
        for (int i = 2; i < arrn.length; ++i) {
            ObjectIdentifier.checkOtherComponent(i, arrn[i]);
        }
        this.init(arrn, arrn.length);
    }

    private static void check(byte[] arrby) throws IOException {
        int n = arrby.length;
        if (n >= 1 && (arrby[n - 1] & 128) == 0) {
            for (int i = 0; i < n; ++i) {
                if (arrby[i] != -128 || i != 0 && (arrby[i - 1] & 128) != 0) continue;
                throw new IOException("ObjectIdentifier() -- Invalid DER encoding, useless extra octet detected");
            }
            return;
        }
        throw new IOException("ObjectIdentifier() -- Invalid DER encoding, not ended");
    }

    private static void checkCount(int n) throws IOException {
        if (n >= 2) {
            return;
        }
        throw new IOException("ObjectIdentifier() -- Must be at least two oid components ");
    }

    private static void checkFirstComponent(int n) throws IOException {
        if (n >= 0 && n <= 2) {
            return;
        }
        throw new IOException("ObjectIdentifier() -- First oid component is invalid ");
    }

    private static void checkFirstComponent(BigInteger bigInteger) throws IOException {
        if (bigInteger.signum() != -1 && bigInteger.compareTo(BigInteger.valueOf(2L)) != 1) {
            return;
        }
        throw new IOException("ObjectIdentifier() -- First oid component is invalid ");
    }

    private static void checkOtherComponent(int n, int n2) throws IOException {
        if (n2 >= 0) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("ObjectIdentifier() -- oid component #");
        stringBuilder.append(n + 1);
        stringBuilder.append(" must be non-negative ");
        throw new IOException(stringBuilder.toString());
    }

    private static void checkOtherComponent(int n, BigInteger serializable) throws IOException {
        if (((BigInteger)serializable).signum() != -1) {
            return;
        }
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("ObjectIdentifier() -- oid component #");
        ((StringBuilder)serializable).append(n + 1);
        ((StringBuilder)serializable).append(" must be non-negative ");
        throw new IOException(((StringBuilder)serializable).toString());
    }

    private static void checkSecondComponent(int n, int n2) throws IOException {
        if (n2 >= 0 && (n == 2 || n2 <= 39)) {
            return;
        }
        throw new IOException("ObjectIdentifier() -- Second oid component is invalid ");
    }

    private static void checkSecondComponent(int n, BigInteger bigInteger) throws IOException {
        if (bigInteger.signum() != -1 && (n == 2 || bigInteger.compareTo(BigInteger.valueOf(39L)) != 1)) {
            return;
        }
        throw new IOException("ObjectIdentifier() -- Second oid component is invalid ");
    }

    private void init(int[] arrn, int n) {
        byte[] arrby = new byte[n * 5 + 1];
        int n2 = arrn[1] < Integer.MAX_VALUE - arrn[0] * 40 ? 0 + ObjectIdentifier.pack7Oid(arrn[0] * 40 + arrn[1], arrby, 0) : 0 + ObjectIdentifier.pack7Oid(BigInteger.valueOf(arrn[1]).add(BigInteger.valueOf(arrn[0] * 40)), arrby, 0);
        for (int i = 2; i < n; ++i) {
            n2 += ObjectIdentifier.pack7Oid(arrn[i], arrby, n2);
        }
        this.encoding = new byte[n2];
        System.arraycopy(arrby, 0, this.encoding, 0, n2);
    }

    public static ObjectIdentifier newInternal(int[] object) {
        try {
            object = new ObjectIdentifier((int[])object);
            return object;
        }
        catch (IOException iOException) {
            throw new RuntimeException(iOException);
        }
    }

    private static byte[] pack(byte[] arrby, int n, int n2, int n3, int n4) {
        if (n3 == n4) {
            return (byte[])arrby.clone();
        }
        int n5 = n2 * n3;
        byte[] arrby2 = new byte[(n5 + n4 - 1) / n4];
        int n6 = 0;
        n2 = (n5 + n4 - 1) / n4 * n4 - n5;
        while (n6 < n5) {
            int n7;
            int n8 = n7 = n3 - n6 % n3;
            if (n7 > n4 - n2 % n4) {
                n8 = n4 - n2 % n4;
            }
            n7 = n2 / n4;
            arrby2[n7] = (byte)(arrby2[n7] | (arrby[n6 / n3 + n] + 256 >> n3 - n6 % n3 - n8 & (1 << n8) - 1) << n4 - n2 % n4 - n8);
            n6 += n8;
            n2 += n8;
        }
        return arrby2;
    }

    private static int pack7Oid(int n, byte[] arrby, int n2) {
        return ObjectIdentifier.pack7Oid(new byte[]{(byte)(n >> 24), (byte)(n >> 16), (byte)(n >> 8), (byte)n}, 0, 4, arrby, n2);
    }

    private static int pack7Oid(BigInteger arrby, byte[] arrby2, int n) {
        arrby = arrby.toByteArray();
        return ObjectIdentifier.pack7Oid(arrby, 0, arrby.length, arrby2, n);
    }

    private static int pack7Oid(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        arrby = ObjectIdentifier.pack(arrby, n, n2, 8, 7);
        n2 = arrby.length - 1;
        for (n = arrby.length - 2; n >= 0; --n) {
            if (arrby[n] != 0) {
                n2 = n;
            }
            arrby[n] = (byte)(arrby[n] | 128);
        }
        System.arraycopy(arrby, n2, arrby2, n3, arrby.length - n2);
        return arrby.length - n2;
    }

    private static int pack8(byte[] arrby, int n, int n2, byte[] arrby2, int n3) {
        arrby = ObjectIdentifier.pack(arrby, n, n2, 7, 8);
        n2 = arrby.length - 1;
        for (n = arrby.length - 2; n >= 0; --n) {
            if (arrby[n] == 0) continue;
            n2 = n;
        }
        System.arraycopy(arrby, n2, arrby2, n3, arrby.length - n2);
        return arrby.length - n2;
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        if (this.encoding == null) {
            this.init((int[])this.components, this.componentLen);
        }
    }

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        if (!this.componentsCalculated) {
            int[] arrn = this.toIntArray();
            if (arrn != null) {
                this.components = arrn;
                this.componentLen = arrn.length;
            } else {
                this.components = HugeOidNotSupportedByOldJDK.theOne;
            }
            this.componentsCalculated = true;
        }
        objectOutputStream.defaultWriteObject();
    }

    void encode(DerOutputStream derOutputStream) throws IOException {
        derOutputStream.write((byte)6, this.encoding);
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ObjectIdentifier)) {
            return false;
        }
        object = (ObjectIdentifier)object;
        return Arrays.equals(this.encoding, ((ObjectIdentifier)object).encoding);
    }

    @Deprecated
    public boolean equals(ObjectIdentifier objectIdentifier) {
        return this.equals((Object)objectIdentifier);
    }

    public int hashCode() {
        return Arrays.hashCode(this.encoding);
    }

    public int[] toIntArray() {
        int n = this.encoding.length;
        int[] arrn = new int[20];
        int n2 = 0;
        int n3 = 0;
        for (int i = 0; i < n; ++i) {
            Object object = this.encoding;
            int n4 = n2;
            int n5 = n3;
            if ((object[i] & 128) == 0) {
                if (i - n3 + 1 > 4) {
                    object = new BigInteger(ObjectIdentifier.pack((byte[])object, n3, i - n3 + 1, 7, 8));
                    if (n3 == 0) {
                        n5 = n2 + 1;
                        arrn[n2] = 2;
                        if (((BigInteger)(object = ((BigInteger)object).subtract(BigInteger.valueOf(80L)))).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1) {
                            return null;
                        }
                        n3 = n5 + 1;
                        arrn[n5] = ((BigInteger)object).intValue();
                    } else {
                        if (((BigInteger)object).compareTo(BigInteger.valueOf(Integer.MAX_VALUE)) == 1) {
                            return null;
                        }
                        n3 = n2 + 1;
                        arrn[n2] = ((BigInteger)object).intValue();
                    }
                } else {
                    n5 = 0;
                    for (n4 = n3; n4 <= i; ++n4) {
                        n5 = n5 << 7 | this.encoding[n4] & 127;
                    }
                    if (n3 == 0) {
                        if (n5 < 80) {
                            n4 = n2 + 1;
                            arrn[n2] = n5 / 40;
                            n3 = n4 + 1;
                            arrn[n4] = n5 % 40;
                        } else {
                            n4 = n2 + 1;
                            arrn[n2] = 2;
                            n3 = n4 + 1;
                            arrn[n4] = n5 - 80;
                        }
                    } else {
                        arrn[n2] = n5;
                        n3 = n2 + 1;
                    }
                }
                n5 = i + 1;
                n4 = n3;
            }
            object = arrn;
            if (n4 >= arrn.length) {
                object = Arrays.copyOf(arrn, n4 + 10);
            }
            arrn = object;
            n2 = n4;
            n3 = n5;
        }
        return Arrays.copyOf(arrn, n2);
    }

    public String toString() {
        CharSequence charSequence = this.stringForm;
        Object object = charSequence;
        if (charSequence == null) {
            int n = this.encoding.length;
            charSequence = new StringBuffer(n * 4);
            int n2 = 0;
            for (int i = 0; i < n; ++i) {
                int n3 = n2;
                if ((this.encoding[i] & 128) == 0) {
                    if (n2 != 0) {
                        ((StringBuffer)charSequence).append('.');
                    }
                    if (i - n2 + 1 > 4) {
                        object = new BigInteger(ObjectIdentifier.pack(this.encoding, n2, i - n2 + 1, 7, 8));
                        if (n2 == 0) {
                            ((StringBuffer)charSequence).append("2.");
                            ((StringBuffer)charSequence).append(((BigInteger)object).subtract(BigInteger.valueOf(80L)));
                        } else {
                            ((StringBuffer)charSequence).append(object);
                        }
                    } else {
                        int n4 = 0;
                        for (n3 = n2; n3 <= i; ++n3) {
                            n4 = n4 << 7 | this.encoding[n3] & 127;
                        }
                        if (n2 == 0) {
                            if (n4 < 80) {
                                ((StringBuffer)charSequence).append(n4 / 40);
                                ((StringBuffer)charSequence).append('.');
                                ((StringBuffer)charSequence).append(n4 % 40);
                            } else {
                                ((StringBuffer)charSequence).append("2.");
                                ((StringBuffer)charSequence).append(n4 - 80);
                            }
                        } else {
                            ((StringBuffer)charSequence).append(n4);
                        }
                    }
                    n3 = i + 1;
                }
                n2 = n3;
            }
            object = ((StringBuffer)charSequence).toString();
            this.stringForm = object;
        }
        return object;
    }

    static class HugeOidNotSupportedByOldJDK
    implements Serializable {
        private static final long serialVersionUID = 1L;
        static HugeOidNotSupportedByOldJDK theOne = new HugeOidNotSupportedByOldJDK();

        HugeOidNotSupportedByOldJDK() {
        }
    }

}

