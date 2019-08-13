/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.cert.CertPath;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import sun.security.pkcs.ContentInfo;
import sun.security.pkcs.PKCS7;
import sun.security.pkcs.SignerInfo;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;

public class X509CertPath
extends CertPath {
    private static final String COUNT_ENCODING = "count";
    private static final String PKCS7_ENCODING = "PKCS7";
    private static final String PKIPATH_ENCODING = "PkiPath";
    private static final Collection<String> encodingList;
    private static final long serialVersionUID = 4989800333263052980L;
    private List<X509Certificate> certs;

    static {
        ArrayList<String> arrayList = new ArrayList<String>(2);
        arrayList.add(PKIPATH_ENCODING);
        arrayList.add(PKCS7_ENCODING);
        encodingList = Collections.unmodifiableCollection(arrayList);
    }

    public X509CertPath(InputStream inputStream) throws CertificateException {
        this(inputStream, PKIPATH_ENCODING);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public X509CertPath(InputStream var1_1, String var2_2) throws CertificateException {
        block4 : {
            block3 : {
                super("X.509");
                var3_3 = var2_2.hashCode();
                if (var3_3 == 76183020) break block3;
                if (var3_3 != 1148619507 || !var2_2.equals("PkiPath")) ** GOTO lbl-1000
                var3_3 = 0;
                break block4;
            }
            if (var2_2.equals("PKCS7")) {
                var3_3 = 1;
            } else lbl-1000: // 2 sources:
            {
                var3_3 = -1;
            }
        }
        if (var3_3 != 0) {
            if (var3_3 != 1) throw new CertificateException("unsupported encoding");
            this.certs = X509CertPath.parsePKCS7(var1_1);
            return;
        }
        this.certs = X509CertPath.parsePKIPATH(var1_1);
    }

    public X509CertPath(List<? extends Certificate> object) throws CertificateException {
        super("X.509");
        Iterator<? extends Certificate> iterator = object.iterator();
        while (iterator.hasNext()) {
            Certificate certificate = iterator.next();
            if (certificate instanceof X509Certificate) continue;
            object = new StringBuilder();
            ((StringBuilder)object).append("List is not all X509Certificates: ");
            ((StringBuilder)object).append(certificate.getClass().getName());
            throw new CertificateException(((StringBuilder)object).toString());
        }
        this.certs = Collections.unmodifiableList(new ArrayList<Certificate>((Collection<? extends Certificate>)object));
    }

    private byte[] encodePKCS7() throws CertificateEncodingException {
        Object object = new ContentInfo(ContentInfo.DATA_OID, null);
        Object object2 = this.certs;
        object2 = object2.toArray(new X509Certificate[object2.size()]);
        object = new PKCS7(new AlgorithmId[0], (ContentInfo)object, (X509Certificate[])object2, new SignerInfo[0]);
        object2 = new DerOutputStream();
        try {
            ((PKCS7)object).encodeSignedData((DerOutputStream)object2);
        }
        catch (IOException iOException) {
            throw new CertificateEncodingException(iOException.getMessage());
        }
        return ((ByteArrayOutputStream)object2).toByteArray();
    }

    private byte[] encodePKIPATH() throws CertificateEncodingException {
        Object object = this.certs;
        ListIterator<X509Certificate> listIterator = object.listIterator(object.size());
        try {
            Object object2;
            object = new DerOutputStream();
            while (listIterator.hasPrevious()) {
                object2 = listIterator.previous();
                if (this.certs.lastIndexOf(object2) == this.certs.indexOf(object2)) {
                    object.write(((Certificate)object2).getEncoded());
                    continue;
                }
                object = new CertificateEncodingException("Duplicate Certificate");
                throw object;
            }
            object2 = new DerOutputStream();
            ((DerOutputStream)object2).write((byte)48, (DerOutputStream)object);
            object = ((ByteArrayOutputStream)object2).toByteArray();
            return object;
        }
        catch (IOException iOException) {
            object = new StringBuilder();
            object.append("IOException encoding PkiPath data: ");
            object.append(iOException);
            throw new CertificateEncodingException(object.toString(), iOException);
        }
    }

    public static Iterator<String> getEncodingsStatic() {
        return encodingList.iterator();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static List<X509Certificate> parsePKCS7(InputStream object) throws CertificateException {
        void var0_5;
        if (object == null) throw new CertificateException("input stream is null");
        Object object2 = object;
        try {
            X509Certificate[] arrx509Certificate;
            PKCS7 pKCS7;
            if (!((InputStream)object).markSupported()) {
                object2 = new ByteArrayInputStream(X509CertPath.readAllBytes((InputStream)object));
            }
            if ((arrx509Certificate = (pKCS7 = new PKCS7((InputStream)object2)).getCertificates()) != null) {
                List<X509Certificate> list = Arrays.asList(arrx509Certificate);
                return Collections.unmodifiableList(var0_5);
            }
            ArrayList arrayList = new ArrayList(0);
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("IOException parsing PKCS7 data: ");
            stringBuilder.append(iOException);
            throw new CertificateException(stringBuilder.toString());
        }
        return Collections.unmodifiableList(var0_5);
    }

    private static List<X509Certificate> parsePKIPATH(InputStream list) throws CertificateException {
        if (list != null) {
            ArrayList<X509Certificate> arrayList = new ArrayList<X509Certificate>(X509CertPath.readAllBytes((InputStream)((Object)list)));
            DerValue[] arrderValue = ((DerInputStream)((Object)arrayList)).getSequence(3);
            if (arrderValue.length == 0) {
                return Collections.emptyList();
            }
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            arrayList = new ArrayList<X509Certificate>(arrderValue.length);
            for (int i = arrderValue.length - 1; i >= 0; --i) {
                list = new List<X509Certificate>(arrderValue[i].toByteArray());
                arrayList.add((X509Certificate)certificateFactory.generateCertificate((InputStream)((Object)list)));
                continue;
            }
            try {
                list = Collections.unmodifiableList(arrayList);
                return list;
            }
            catch (IOException iOException) {
                list = new StringBuilder();
                ((StringBuilder)((Object)list)).append("IOException parsing PkiPath data: ");
                ((StringBuilder)((Object)list)).append(iOException);
                throw new CertificateException(((StringBuilder)((Object)list)).toString(), iOException);
            }
        }
        throw new CertificateException("input stream is null");
    }

    private static byte[] readAllBytes(InputStream inputStream) throws IOException {
        int n;
        byte[] arrby = new byte[8192];
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);
        while ((n = inputStream.read(arrby)) != -1) {
            byteArrayOutputStream.write(arrby, 0, n);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public List<X509Certificate> getCertificates() {
        return this.certs;
    }

    @Override
    public byte[] getEncoded() throws CertificateEncodingException {
        return this.encodePKIPATH();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public byte[] getEncoded(String var1_1) throws CertificateEncodingException {
        block3 : {
            block2 : {
                var2_2 = var1_1.hashCode();
                if (var2_2 == 76183020) break block2;
                if (var2_2 != 1148619507 || !var1_1.equals("PkiPath")) ** GOTO lbl-1000
                var2_2 = 0;
                break block3;
            }
            if (var1_1.equals("PKCS7")) {
                var2_2 = 1;
            } else lbl-1000: // 2 sources:
            {
                var2_2 = -1;
            }
        }
        if (var2_2 == 0) return this.encodePKIPATH();
        if (var2_2 != 1) throw new CertificateEncodingException("unsupported encoding");
        return this.encodePKCS7();
    }

    @Override
    public Iterator<String> getEncodings() {
        return X509CertPath.getEncodingsStatic();
    }
}

