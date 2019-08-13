/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier
 *  com.android.org.bouncycastle.asn1.x509.X509Name
 */
package android.net.http;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.android.internal.util.HexDump;
import com.android.org.bouncycastle.asn1.ASN1ObjectIdentifier;
import com.android.org.bouncycastle.asn1.x509.X509Name;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

public class SslCertificate {
    private static String ISO_8601_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";
    private static final String ISSUED_BY = "issued-by";
    private static final String ISSUED_TO = "issued-to";
    private static final String VALID_NOT_AFTER = "valid-not-after";
    private static final String VALID_NOT_BEFORE = "valid-not-before";
    private static final String X509_CERTIFICATE = "x509-certificate";
    private final DName mIssuedBy;
    private final DName mIssuedTo;
    private final Date mValidNotAfter;
    private final Date mValidNotBefore;
    @UnsupportedAppUsage
    private final X509Certificate mX509Certificate;

    @Deprecated
    public SslCertificate(String string2, String string3, String string4, String string5) {
        this(string2, string3, SslCertificate.parseDate(string4), SslCertificate.parseDate(string5), null);
    }

    @Deprecated
    public SslCertificate(String string2, String string3, Date date, Date date2) {
        this(string2, string3, date, date2, null);
    }

    private SslCertificate(String string2, String string3, Date date, Date date2, X509Certificate x509Certificate) {
        this.mIssuedTo = new DName(string2);
        this.mIssuedBy = new DName(string3);
        this.mValidNotBefore = SslCertificate.cloneDate(date);
        this.mValidNotAfter = SslCertificate.cloneDate(date2);
        this.mX509Certificate = x509Certificate;
    }

    public SslCertificate(X509Certificate x509Certificate) {
        this(x509Certificate.getSubjectDN().getName(), x509Certificate.getIssuerDN().getName(), x509Certificate.getNotBefore(), x509Certificate.getNotAfter(), x509Certificate);
    }

    private static Date cloneDate(Date date) {
        if (date == null) {
            return null;
        }
        return (Date)date.clone();
    }

    private static final String fingerprint(byte[] arrby) {
        if (arrby == null) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < arrby.length; ++i) {
            HexDump.appendByteAsHex(stringBuilder, arrby[i], true);
            if (i + 1 == arrby.length) continue;
            stringBuilder.append(':');
        }
        return stringBuilder.toString();
    }

    private String formatCertificateDate(Context context, Date date) {
        if (date == null) {
            return "";
        }
        return DateFormat.getMediumDateFormat(context).format(date);
    }

    private static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        return new SimpleDateFormat(ISO_8601_DATE_FORMAT).format(date);
    }

    @UnsupportedAppUsage
    private static String getDigest(X509Certificate object, String string2) {
        if (object == null) {
            return "";
        }
        try {
            object = object.getEncoded();
            object = SslCertificate.fingerprint(MessageDigest.getInstance(string2).digest((byte[])object));
            return object;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            return "";
        }
        catch (CertificateEncodingException certificateEncodingException) {
            return "";
        }
    }

    @UnsupportedAppUsage
    private static String getSerialNumber(X509Certificate serializable) {
        if (serializable == null) {
            return "";
        }
        if ((serializable = ((X509Certificate)serializable).getSerialNumber()) == null) {
            return "";
        }
        return SslCertificate.fingerprint(((BigInteger)serializable).toByteArray());
    }

    private static Date parseDate(String object) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ISO_8601_DATE_FORMAT);
            object = simpleDateFormat.parse((String)object);
            return object;
        }
        catch (ParseException parseException) {
            return null;
        }
    }

    public static SslCertificate restoreState(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        Object object = bundle.getByteArray(X509_CERTIFICATE);
        if (object == null) {
            object = null;
        } else {
            try {
                CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream((byte[])object);
                object = (X509Certificate)certificateFactory.generateCertificate(byteArrayInputStream);
            }
            catch (CertificateException certificateException) {
                object = null;
            }
        }
        return new SslCertificate(bundle.getString(ISSUED_TO), bundle.getString(ISSUED_BY), SslCertificate.parseDate(bundle.getString(VALID_NOT_BEFORE)), SslCertificate.parseDate(bundle.getString(VALID_NOT_AFTER)), (X509Certificate)object);
    }

    public static Bundle saveState(SslCertificate object) {
        if (object == null) {
            return null;
        }
        Bundle bundle = new Bundle();
        bundle.putString(ISSUED_TO, ((SslCertificate)object).getIssuedTo().getDName());
        bundle.putString(ISSUED_BY, ((SslCertificate)object).getIssuedBy().getDName());
        bundle.putString(VALID_NOT_BEFORE, ((SslCertificate)object).getValidNotBefore());
        bundle.putString(VALID_NOT_AFTER, ((SslCertificate)object).getValidNotAfter());
        object = ((SslCertificate)object).mX509Certificate;
        if (object != null) {
            try {
                bundle.putByteArray(X509_CERTIFICATE, ((Certificate)object).getEncoded());
            }
            catch (CertificateEncodingException certificateEncodingException) {
                // empty catch block
            }
        }
        return bundle;
    }

    public DName getIssuedBy() {
        return this.mIssuedBy;
    }

    public DName getIssuedTo() {
        return this.mIssuedTo;
    }

    @Deprecated
    public String getValidNotAfter() {
        return SslCertificate.formatDate(this.mValidNotAfter);
    }

    public Date getValidNotAfterDate() {
        return SslCertificate.cloneDate(this.mValidNotAfter);
    }

    @Deprecated
    public String getValidNotBefore() {
        return SslCertificate.formatDate(this.mValidNotBefore);
    }

    public Date getValidNotBeforeDate() {
        return SslCertificate.cloneDate(this.mValidNotBefore);
    }

    public X509Certificate getX509Certificate() {
        return this.mX509Certificate;
    }

    @UnsupportedAppUsage
    public View inflateCertificateView(Context object) {
        View view = LayoutInflater.from((Context)object).inflate(17367301, null);
        Object object2 = this.getIssuedTo();
        if (object2 != null) {
            ((TextView)view.findViewById(16909473)).setText(((DName)object2).getCName());
            ((TextView)view.findViewById(16909475)).setText(((DName)object2).getOName());
            ((TextView)view.findViewById(16909477)).setText(((DName)object2).getUName());
        }
        ((TextView)view.findViewById(16909340)).setText(SslCertificate.getSerialNumber(this.mX509Certificate));
        object2 = this.getIssuedBy();
        if (object2 != null) {
            ((TextView)view.findViewById(16908783)).setText(((DName)object2).getCName());
            ((TextView)view.findViewById(16908785)).setText(((DName)object2).getOName());
            ((TextView)view.findViewById(16908787)).setText(((DName)object2).getUName());
        }
        object2 = this.formatCertificateDate((Context)object, this.getValidNotBeforeDate());
        ((TextView)view.findViewById(16909041)).setText((CharSequence)object2);
        object = this.formatCertificateDate((Context)object, this.getValidNotAfterDate());
        ((TextView)view.findViewById(16908900)).setText((CharSequence)object);
        ((TextView)view.findViewById(16909347)).setText(SslCertificate.getDigest(this.mX509Certificate, "SHA256"));
        ((TextView)view.findViewById(16909345)).setText(SslCertificate.getDigest(this.mX509Certificate, "SHA1"));
        return view;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Issued to: ");
        stringBuilder.append(this.mIssuedTo.getDName());
        stringBuilder.append(";\nIssued by: ");
        stringBuilder.append(this.mIssuedBy.getDName());
        stringBuilder.append(";\n");
        return stringBuilder.toString();
    }

    public class DName {
        private String mCName;
        private String mDName;
        private String mOName;
        private String mUName;

        public DName(String object2) {
            if (object2 != null) {
                int n;
                this.mDName = object2;
                try {
                    super((String)object2);
                    object2 = SslCertificate.this.getValues();
                    SslCertificate.this = SslCertificate.this.getOIDs();
                    n = 0;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    // empty catch block
                }
                do {
                    if (n < ((Vector)SslCertificate.this).size()) {
                        if (((Vector)SslCertificate.this).elementAt(n).equals((Object)X509Name.CN)) {
                            if (this.mCName == null) {
                                this.mCName = (String)((Vector)object2).elementAt(n);
                            }
                        } else if (((Vector)SslCertificate.this).elementAt(n).equals((Object)X509Name.O) && this.mOName == null) {
                            this.mOName = (String)((Vector)object2).elementAt(n);
                        } else if (((Vector)SslCertificate.this).elementAt(n).equals((Object)X509Name.OU) && this.mUName == null) {
                            this.mUName = (String)((Vector)object2).elementAt(n);
                        }
                        ++n;
                        continue;
                    }
                    break;
                } while (true);
            }
        }

        public String getCName() {
            String string2 = this.mCName;
            if (string2 == null) {
                string2 = "";
            }
            return string2;
        }

        public String getDName() {
            String string2 = this.mDName;
            if (string2 == null) {
                string2 = "";
            }
            return string2;
        }

        public String getOName() {
            String string2 = this.mOName;
            if (string2 == null) {
                string2 = "";
            }
            return string2;
        }

        public String getUName() {
            String string2 = this.mUName;
            if (string2 == null) {
                string2 = "";
            }
            return string2;
        }
    }

}

