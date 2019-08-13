/*
 * Decompiled with CFR 0.145.
 */
package sun.security.provider.certpath;

import java.io.IOException;
import java.math.BigInteger;
import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.CRLReason;
import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateParsingException;
import java.security.cert.Extension;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.security.auth.x500.X500Principal;
import sun.misc.CharacterEncoder;
import sun.misc.HexDumpEncoder;
import sun.security.action.GetIntegerAction;
import sun.security.provider.certpath.AlgorithmChecker;
import sun.security.provider.certpath.CertId;
import sun.security.provider.certpath.OCSP;
import sun.security.util.Debug;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;
import sun.security.x509.AlgorithmId;
import sun.security.x509.KeyIdentifier;
import sun.security.x509.PKIXExtensions;
import sun.security.x509.X509CertImpl;

public final class OCSPResponse {
    private static final int CERT_STATUS_GOOD = 0;
    private static final int CERT_STATUS_REVOKED = 1;
    private static final int CERT_STATUS_UNKNOWN = 2;
    private static final int DEFAULT_MAX_CLOCK_SKEW = 900000;
    private static final int KEY_TAG = 2;
    private static final String KP_OCSP_SIGNING_OID = "1.3.6.1.5.5.7.3.9";
    private static final int MAX_CLOCK_SKEW;
    private static final int NAME_TAG = 1;
    private static final ObjectIdentifier OCSP_BASIC_RESPONSE_OID;
    private static final Debug debug;
    private static final boolean dump;
    private static ResponseStatus[] rsvalues;
    private static CRLReason[] values;
    private List<X509CertImpl> certs;
    private KeyIdentifier responderKeyId;
    private X500Principal responderName;
    private final byte[] responseNonce;
    private final ResponseStatus responseStatus;
    private final AlgorithmId sigAlgId;
    private final byte[] signature;
    private X509CertImpl signerCert;
    private final Map<CertId, SingleResponse> singleResponseMap;
    private final byte[] tbsResponseData;

    static {
        rsvalues = ResponseStatus.values();
        debug = Debug.getInstance("certpath");
        boolean bl = debug != null && Debug.isOn("ocsp");
        dump = bl;
        OCSP_BASIC_RESPONSE_OID = ObjectIdentifier.newInternal(new int[]{1, 3, 6, 1, 5, 5, 7, 48, 1, 1});
        MAX_CLOCK_SKEW = OCSPResponse.initializeClockSkew();
        values = CRLReason.values();
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    OCSPResponse(byte[] object) throws IOException {
        int n;
        Object object2;
        Object object3;
        void var1_5;
        Object object6;
        Object object5;
        block32 : {
            DerValue[] arrderValue;
            Object object4;
            block31 : {
                block27 : {
                    block28 : {
                        block29 : {
                            block30 : {
                                this.signerCert = null;
                                this.responderName = null;
                                this.responderKeyId = null;
                                if (dump) {
                                    object2 = new HexDumpEncoder();
                                    object5 = debug;
                                    object3 = new StringBuilder();
                                    ((StringBuilder)object3).append("OCSPResponse bytes...\n\n");
                                    ((StringBuilder)object3).append(((CharacterEncoder)object2).encode((byte[])object));
                                    ((StringBuilder)object3).append("\n");
                                    ((Debug)object5).println(((StringBuilder)object3).toString());
                                }
                                object = new DerValue((byte[])object);
                                if (((DerValue)object).tag != 48) throw new IOException("Bad encoding in OCSP response: expected ASN.1 SEQUENCE tag.");
                                n = ((DerInputStream)(object = ((DerValue)object).getData())).getEnumerated();
                                if (n < 0 || n >= ((ResponseStatus[])(object2 = rsvalues)).length) break block27;
                                this.responseStatus = object2[n];
                                object2 = debug;
                                if (object2 != null) {
                                    object3 = new StringBuilder();
                                    ((StringBuilder)object3).append("OCSP response status: ");
                                    ((StringBuilder)object3).append((Object)this.responseStatus);
                                    ((Debug)object2).println(((StringBuilder)object3).toString());
                                }
                                if (this.responseStatus != ResponseStatus.SUCCESSFUL) {
                                    this.singleResponseMap = Collections.emptyMap();
                                    this.certs = new ArrayList<X509CertImpl>();
                                    this.sigAlgId = null;
                                    this.signature = null;
                                    this.tbsResponseData = null;
                                    this.responseNonce = null;
                                    return;
                                }
                                object3 = ((DerInputStream)object).getDerValue();
                                if (!((DerValue)object3).isContextSpecific((byte)0)) throw new IOException("Bad encoding in responseBytes element of OCSP response: expected ASN.1 context specific tag 0.");
                                object = ((DerValue)object3).data.getDerValue();
                                if (((DerValue)object).tag != 48) throw new IOException("Bad encoding in responseBytes element of OCSP response: expected ASN.1 SEQUENCE tag.");
                                object2 = ((DerValue)object).data;
                                object = ((DerInputStream)object2).getOID();
                                if (!((ObjectIdentifier)object).equals((Object)OCSP_BASIC_RESPONSE_OID)) break block28;
                                object = debug;
                                if (object != null) {
                                    ((Debug)object).println("OCSP response type: basic");
                                }
                                if (((DerValue[])(object6 = new DerInputStream(((DerInputStream)object2).getOctetString()).getSequence(2))).length < 3) throw new IOException("Unexpected BasicOCSPResponse value");
                                object = object6[0];
                                this.tbsResponseData = object6[0].toByteArray();
                                if (((DerValue)object).tag != 48) throw new IOException("Bad encoding in tbsResponseData element of OCSP response: expected ASN.1 SEQUENCE tag.");
                                arrderValue = ((DerValue)object).data;
                                object = object5 = arrderValue.getDerValue();
                                if (((DerValue)object5).isContextSpecific((byte)0)) {
                                    object = object5;
                                    if (((DerValue)object5).isConstructed()) {
                                        object = object5;
                                        if (((DerValue)object5).isContextSpecific()) {
                                            object = ((DerValue)object5).data.getDerValue();
                                            ((DerValue)object).getInteger();
                                            if (((DerValue)object).data.available() != 0) throw new IOException("Bad encoding in version  element of OCSP response: bad format");
                                            object = arrderValue.getDerValue();
                                        }
                                    }
                                }
                                if ((n = (int)((byte)(((DerValue)object).tag & 31))) == 1) {
                                    this.responderName = new X500Principal(((DerValue)object).getData().toByteArray());
                                    object = debug;
                                    if (object != null) {
                                        object5 = new StringBuilder();
                                        ((StringBuilder)object5).append("Responder's name: ");
                                        ((StringBuilder)object5).append(this.responderName);
                                        ((Debug)object).println(((StringBuilder)object5).toString());
                                    }
                                } else {
                                    if (n != 2) throw new IOException("Bad encoding in responderID element of OCSP response: expected ASN.1 context specific tag 0 or 1");
                                    this.responderKeyId = new KeyIdentifier(((DerValue)object).getData().getOctetString());
                                    object = debug;
                                    if (object != null) {
                                        object5 = new StringBuilder();
                                        ((StringBuilder)object5).append("Responder's key ID: ");
                                        ((StringBuilder)object5).append(Debug.toString(this.responderKeyId.getIdentifier()));
                                        ((Debug)object).println(((StringBuilder)object5).toString());
                                    }
                                }
                                object = arrderValue.getDerValue();
                                if (debug != null) {
                                    object4 = ((DerValue)object).getGeneralizedTime();
                                    object5 = debug;
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("OCSP response produced at: ");
                                    ((StringBuilder)object).append(object4);
                                    ((Debug)object5).println(((StringBuilder)object).toString());
                                }
                                object = arrderValue.getSequence(1);
                                this.singleResponseMap = new HashMap<CertId, SingleResponse>(((Object)object).length);
                                object4 = debug;
                                if (object4 != null) {
                                    object5 = new StringBuilder();
                                    ((StringBuilder)object5).append("OCSP number of SingleResponses: ");
                                    ((StringBuilder)object5).append(((Object)object).length);
                                    ((Debug)object4).println(((StringBuilder)object5).toString());
                                }
                                for (n = 0; n < ((Object)object).length; ++n) {
                                    object5 = new SingleResponse((DerValue)object[n]);
                                    this.singleResponseMap.put(((SingleResponse)object5).getCertId(), (SingleResponse)object5);
                                }
                                object = null;
                                if (arrderValue.available() <= 0) break block29;
                                object5 = arrderValue.getDerValue();
                                if (!((DerValue)object5).isContextSpecific((byte)1)) break block30;
                                arrderValue = ((DerValue)object5).data.getSequence(3);
                                break block31;
                            }
                            object = null;
                            break block32;
                        }
                        object = null;
                        break block32;
                    }
                    object3 = debug;
                    if (object3 != null) {
                        object2 = new StringBuilder();
                        ((StringBuilder)object2).append("OCSP response type: ");
                        ((StringBuilder)object2).append(object);
                        ((Debug)object3).println(((StringBuilder)object2).toString());
                    }
                    object2 = new StringBuilder();
                    ((StringBuilder)object2).append("Unsupported OCSP response type: ");
                    ((StringBuilder)object2).append(object);
                    throw new IOException(((StringBuilder)object2).toString());
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown OCSPResponse status: ");
                ((StringBuilder)object).append(n);
                throw new IOException(((StringBuilder)object).toString());
            }
            for (n = 0; n < arrderValue.length; ++n) {
                object4 = new sun.security.x509.Extension(arrderValue[n]);
                Debug debug = OCSPResponse.debug;
                if (debug != null) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("OCSP extension: ");
                    stringBuilder.append(object4);
                    debug.println(stringBuilder.toString());
                }
                if (((sun.security.x509.Extension)object4).getExtensionId().equals((Object)OCSP.NONCE_EXTENSION_OID)) {
                    object = ((sun.security.x509.Extension)object4).getExtensionValue();
                    continue;
                }
                if (!((sun.security.x509.Extension)object4).isCritical()) continue;
                object = new StringBuilder();
                ((StringBuilder)object).append("Unsupported OCSP critical extension: ");
                ((StringBuilder)object).append(((sun.security.x509.Extension)object4).getExtensionId());
                throw new IOException(((StringBuilder)object).toString());
            }
        }
        this.responseNonce = object;
        this.sigAlgId = AlgorithmId.parse(object6[1]);
        this.signature = object6[2].getBitString();
        if (((DerValue[])object6).length <= 3) {
            this.certs = new ArrayList<X509CertImpl>();
            return;
        }
        object2 = object6[3];
        if (!((DerValue)object2).isContextSpecific((byte)0)) throw new IOException("Bad encoding in certs element of OCSP response: expected ASN.1 context specific tag 0.");
        object2 = ((DerValue)object2).getData().getSequence(3);
        this.certs = new ArrayList<X509CertImpl>(((Object)object2).length);
        n = 0;
        do {
            block26 : {
                if (n >= ((Object)object2).length) return;
                object5 = new X509CertImpl(((DerValue)object2[n]).toByteArray());
                this.certs.add((X509CertImpl)object5);
                if (debug == null) break block26;
                object6 = debug;
                try {
                    object3 = new StringBuilder();
                }
                catch (CertificateException certificateException) {
                    throw new IOException("Bad encoding in X509 Certificate", (Throwable)var1_5);
                }
                try {
                    ((StringBuilder)object3).append("OCSP response cert #");
                    ((StringBuilder)object3).append(n + 1);
                    ((StringBuilder)object3).append(": ");
                    ((StringBuilder)object3).append(((X509CertImpl)object5).getSubjectX500Principal());
                    ((Debug)object6).println(((StringBuilder)object3).toString());
                }
                catch (CertificateException certificateException) {
                    throw new IOException("Bad encoding in X509 Certificate", (Throwable)var1_5);
                }
            }
            ++n;
        } while (true);
        catch (CertificateException certificateException) {
            // empty catch block
        }
        throw new IOException("Bad encoding in X509 Certificate", (Throwable)var1_5);
    }

    private static int initializeClockSkew() {
        Integer n = AccessController.doPrivileged(new GetIntegerAction("com.sun.security.ocsp.clockSkew"));
        if (n != null && n >= 0) {
            return n * 1000;
        }
        return 900000;
    }

    private boolean verifySignature(X509Certificate x509Certificate) throws CertPathValidatorException {
        block5 : {
            Signature signature = Signature.getInstance(this.sigAlgId.getName());
            signature.initVerify(x509Certificate.getPublicKey());
            signature.update(this.tbsResponseData);
            if (!signature.verify(this.signature)) break block5;
            if (debug != null) {
                debug.println("Verified signature of OCSP Response");
            }
            return true;
        }
        try {
            if (debug != null) {
                debug.println("Error verifying signature of OCSP Response");
            }
            return false;
        }
        catch (InvalidKeyException | NoSuchAlgorithmException | SignatureException generalSecurityException) {
            throw new CertPathValidatorException(generalSecurityException);
        }
    }

    ResponseStatus getResponseStatus() {
        return this.responseStatus;
    }

    X509Certificate getSignerCertificate() {
        return this.signerCert;
    }

    SingleResponse getSingleResponse(CertId certId) {
        return this.singleResponseMap.get(certId);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    void verify(List<CertId> var1_1, X509Certificate var2_6, X509Certificate var3_7, Date var4_8, byte[] var5_9) throws CertPathValidatorException {
        block30 : {
            block28 : {
                block29 : {
                    block34 : {
                        block33 : {
                            block32 : {
                                block27 : {
                                    block26 : {
                                        var6_12 = 1.$SwitchMap$sun$security$provider$certpath$OCSPResponse$ResponseStatus[this.responseStatus.ordinal()];
                                        if (var6_12 != 1) {
                                            if (var6_12 != 2 && var6_12 != 3) {
                                                var1_1 = new StringBuilder();
                                                var1_1.append("OCSP response error: ");
                                                var1_1.append((Object)this.responseStatus);
                                                throw new CertPathValidatorException(var1_1.toString());
                                            }
                                            var1_1 = new StringBuilder();
                                            var1_1.append("OCSP response error: ");
                                            var1_1.append((Object)this.responseStatus);
                                            throw new CertPathValidatorException(var1_1.toString(), null, null, -1, CertPathValidatorException.BasicReason.UNDETERMINED_REVOCATION_STATUS);
                                        }
                                        var7_13 = var1_1.iterator();
                                        while (var7_13.hasNext()) {
                                            var1_1 = (CertId)var7_13.next();
                                            var8_15 = this.getSingleResponse((CertId)var1_1);
                                            if (var8_15 == null) {
                                                var3_7 = OCSPResponse.debug;
                                                if (var3_7 == null) throw new CertPathValidatorException("OCSP response does not include a response for a certificate supplied in the OCSP request");
                                                var2_6 = new StringBuilder();
                                                var2_6.append("No response found for CertId: ");
                                                var2_6.append(var1_1);
                                                var3_7.println(var2_6.toString());
                                                throw new CertPathValidatorException("OCSP response does not include a response for a certificate supplied in the OCSP request");
                                            }
                                            var9_18 = OCSPResponse.debug;
                                            if (var9_18 == null) continue;
                                            var10_19 = new StringBuilder();
                                            var10_19.append("Status of certificate (with serial number ");
                                            var10_19.append(var1_1.getSerialNumber());
                                            var10_19.append(") is: ");
                                            var10_19.append((Object)var8_15.getCertStatus());
                                            var9_18.println(var10_19.toString());
                                        }
                                        if (this.signerCert != null) break block27;
                                        try {
                                            this.certs.add(X509CertImpl.toImpl(var2_6));
                                            if (var3_7 != null) {
                                                this.certs.add(X509CertImpl.toImpl((X509Certificate)var3_7));
                                            }
                                            if (this.responderName == null) break block26;
                                            var1_1 = this.certs.iterator();
                                        }
                                        catch (CertificateException var1_2) {
                                            throw new CertPathValidatorException("Invalid issuer or trusted responder certificate", var1_2);
                                        }
                                        while (var1_1.hasNext()) {
                                            var7_13 = (X509CertImpl)var1_1.next();
                                            if (!var7_13.getSubjectX500Principal().equals(this.responderName)) continue;
                                            this.signerCert = var7_13;
                                            break block27;
                                        }
                                        break block27;
                                    }
                                    if (this.responderKeyId == null) break block27;
                                    var10_19 = this.certs.iterator();
                                    while (var10_19.hasNext()) {
                                        var8_17 = (X509CertImpl)var10_19.next();
                                        var1_1 = var8_17.getSubjectKeyId();
                                        if (var1_1 != null && this.responderKeyId.equals(var1_1)) {
                                            this.signerCert = var8_17;
                                            break;
                                        }
                                        try {
                                            var1_1 = var7_13 = new KeyIdentifier(var8_17.getPublicKey());
                                        }
                                        catch (IOException var7_14) {
                                            // empty catch block
                                        }
                                        if (!this.responderKeyId.equals(var1_1)) continue;
                                        this.signerCert = var8_17;
                                        break;
                                    }
                                }
                                if ((var1_1 = this.signerCert) == null) break block30;
                                if (!var1_1.equals(var2_6)) break block32;
                                var1_1 = OCSPResponse.debug;
                                if (var1_1 != null) {
                                    var1_1.println("OCSP response is signed by the target's Issuing CA");
                                }
                                break block30;
                            }
                            if (!this.signerCert.equals(var3_7)) break block33;
                            var1_1 = OCSPResponse.debug;
                            if (var1_1 != null) {
                                var1_1.println("OCSP response is signed by a Trusted Responder");
                            }
                            break block30;
                        }
                        if (this.signerCert.getIssuerX500Principal().equals(var2_6.getSubjectX500Principal()) == false) throw new CertPathValidatorException("Responder's certificate is not authorized to sign OCSP responses");
                        try {
                            var1_1 = this.signerCert.getExtendedKeyUsage();
                            if (var1_1 == null || !(var11_20 = var1_1.contains("1.3.6.1.5.5.7.3.9"))) break block28;
                            var1_1 = new AlgorithmChecker(new TrustAnchor((X509Certificate)var2_6, null));
                            var1_1.init(false);
                            var1_1.check(this.signerCert, Collections.emptySet());
                            if (var4_8 != null) ** GOTO lbl106
                        }
                        catch (CertificateParsingException var1_5) {
                            throw new CertPathValidatorException("Responder's certificate not valid for signing OCSP responses", var1_5);
                        }
                        this.signerCert.checkValidity();
                        break block34;
lbl106: // 1 sources:
                        this.signerCert.checkValidity(var4_8);
                    }
                    if (this.signerCert.getExtension(PKIXExtensions.OCSPNoCheck_Id) == null || (var1_1 = OCSPResponse.debug) == null) break block29;
                    var1_1.println("Responder's certificate includes the extension id-pkix-ocsp-nocheck.");
                }
                try {
                    this.signerCert.verify(var2_6.getPublicKey());
                    if (OCSPResponse.debug != null) {
                        OCSPResponse.debug.println("OCSP response is signed by an Authorized Responder");
                    }
                    break block30;
                }
                catch (GeneralSecurityException var1_3) {
                    this.signerCert = null;
                }
                break block30;
                catch (CertificateException var1_4) {
                    throw new CertPathValidatorException("Responder's certificate not within the validity period", var1_4);
                }
            }
            var1_1 = new CertPathValidatorException("Responder's certificate not valid for signing OCSP responses");
            throw var1_1;
        }
        if ((var1_1 = this.signerCert) == null) throw new CertPathValidatorException("Unable to verify OCSP Response's signature");
        AlgorithmChecker.check(var1_1.getPublicKey(), this.sigAlgId);
        if (this.verifySignature(this.signerCert) == false) throw new CertPathValidatorException("Error verifying OCSP Response's signature");
        if (var5_9 /* !! */  != null && (var1_1 = this.responseNonce) != null) {
            if (Arrays.equals(var5_9 /* !! */ , (byte[])var1_1) == false) throw new CertPathValidatorException("Nonces don't match");
        }
        var12_21 = var4_8 == null ? System.currentTimeMillis() : var4_8.getTime();
        var4_8 = new Date((long)OCSPResponse.MAX_CLOCK_SKEW + var12_21);
        var3_7 = new Date(var12_21 - (long)OCSPResponse.MAX_CLOCK_SKEW);
        var2_6 = this.singleResponseMap.values().iterator();
        while (var2_6.hasNext() != false) {
            var5_11 = var2_6.next();
            if (OCSPResponse.debug != null) {
                var1_1 = "";
                if (SingleResponse.access$200(var5_11) != null) {
                    var1_1 = new StringBuilder();
                    var1_1.append(" until ");
                    var1_1.append(SingleResponse.access$200(var5_11));
                    var1_1 = var1_1.toString();
                }
                var10_19 = OCSPResponse.debug;
                var7_13 = new StringBuilder();
                var7_13.append("OCSP response validity interval is from ");
                var7_13.append(SingleResponse.access$300(var5_11));
                var7_13.append((String)var1_1);
                var10_19.println(var7_13.toString());
                var1_1 = OCSPResponse.debug;
                var7_13 = new StringBuilder();
                var7_13.append("Checking validity of OCSP response on: ");
                var7_13.append(new Date(var12_21));
                var1_1.println(var7_13.toString());
            }
            if (var4_8.before(SingleResponse.access$300(var5_11)) != false) throw new CertPathValidatorException("Response is unreliable: its validity interval is out-of-date");
            var1_1 = SingleResponse.access$200(var5_11) != null ? SingleResponse.access$200(var5_11) : SingleResponse.access$300(var5_11);
            if (var3_7.after((Date)var1_1) != false) throw new CertPathValidatorException("Response is unreliable: its validity interval is out-of-date");
        }
    }

    public static enum ResponseStatus {
        SUCCESSFUL,
        MALFORMED_REQUEST,
        INTERNAL_ERROR,
        TRY_LATER,
        UNUSED,
        SIG_REQUIRED,
        UNAUTHORIZED;
        
    }

    static final class SingleResponse
    implements OCSP.RevocationStatus {
        private final CertId certId;
        private final OCSP.RevocationStatus.CertStatus certStatus;
        private final Date nextUpdate;
        private final CRLReason revocationReason;
        private final Date revocationTime;
        private final Map<String, Extension> singleExtensions;
        private final Date thisUpdate;

        private SingleResponse(DerValue object) throws IOException {
            block18 : {
                block22 : {
                    Object object2;
                    int n;
                    Object object3;
                    block20 : {
                        block21 : {
                            block19 : {
                                if (((DerValue)object).tag != 48) break block18;
                                object = ((DerValue)object).data;
                                this.certId = new CertId(object.getDerValue().data);
                                object3 = ((DerInputStream)object).getDerValue();
                                n = (byte)(((DerValue)object3).tag & 31);
                                if (n != 1) break block19;
                                this.certStatus = OCSP.RevocationStatus.CertStatus.REVOKED;
                                this.revocationTime = ((DerValue)object3).data.getGeneralizedTime();
                                if (((DerValue)object3).data.available() != 0) {
                                    object3 = ((DerValue)object3).data.getDerValue();
                                    this.revocationReason = (short)((byte)(((DerValue)object3).tag & 31)) == 0 ? ((n = ((DerValue)object3).data.getEnumerated()) >= 0 && n < values.length ? values[n] : CRLReason.UNSPECIFIED) : CRLReason.UNSPECIFIED;
                                } else {
                                    this.revocationReason = CRLReason.UNSPECIFIED;
                                }
                                if (debug != null) {
                                    object2 = debug;
                                    object3 = new StringBuilder();
                                    ((StringBuilder)object3).append("Revocation time: ");
                                    ((StringBuilder)object3).append(this.revocationTime);
                                    ((Debug)object2).println(((StringBuilder)object3).toString());
                                    object3 = debug;
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("Revocation reason: ");
                                    ((StringBuilder)object2).append((Object)this.revocationReason);
                                    ((Debug)object3).println(((StringBuilder)object2).toString());
                                }
                                break block20;
                            }
                            this.revocationTime = null;
                            this.revocationReason = CRLReason.UNSPECIFIED;
                            if (n != 0) break block21;
                            this.certStatus = OCSP.RevocationStatus.CertStatus.GOOD;
                            break block20;
                        }
                        if (n != 2) break block22;
                        this.certStatus = OCSP.RevocationStatus.CertStatus.UNKNOWN;
                    }
                    this.thisUpdate = ((DerInputStream)object).getGeneralizedTime();
                    if (((DerInputStream)object).available() == 0) {
                        this.nextUpdate = null;
                    } else {
                        object3 = ((DerInputStream)object).getDerValue();
                        if ((short)((byte)(((DerValue)object3).tag & 31)) == 0) {
                            this.nextUpdate = ((DerValue)object3).data.getGeneralizedTime();
                            if (((DerInputStream)object).available() != 0) {
                                n = (byte)(object.getDerValue().tag & 31);
                            }
                        } else {
                            this.nextUpdate = null;
                        }
                    }
                    if (((DerInputStream)object).available() > 0) {
                        if (((DerValue)(object = ((DerInputStream)object).getDerValue())).isContextSpecific((byte)1)) {
                            object2 = ((DerValue)object).data.getSequence(3);
                            this.singleExtensions = new HashMap<String, Extension>(((DerValue[])object2).length);
                            for (n = 0; n < ((Object)object2).length; ++n) {
                                object = new sun.security.x509.Extension((DerValue)object2[n]);
                                if (debug != null) {
                                    Debug debug = debug;
                                    object3 = new StringBuilder();
                                    ((StringBuilder)object3).append("OCSP single extension: ");
                                    ((StringBuilder)object3).append(object);
                                    debug.println(((StringBuilder)object3).toString());
                                }
                                if (!((sun.security.x509.Extension)object).isCritical()) {
                                    this.singleExtensions.put(((sun.security.x509.Extension)object).getId(), (Extension)object);
                                    continue;
                                }
                                object3 = new StringBuilder();
                                ((StringBuilder)object3).append("Unsupported OCSP critical extension: ");
                                ((StringBuilder)object3).append(((sun.security.x509.Extension)object).getExtensionId());
                                throw new IOException(((StringBuilder)object3).toString());
                            }
                        } else {
                            this.singleExtensions = Collections.emptyMap();
                        }
                    } else {
                        this.singleExtensions = Collections.emptyMap();
                    }
                    return;
                }
                throw new IOException("Invalid certificate status");
            }
            throw new IOException("Bad ASN.1 encoding in SingleResponse");
        }

        static /* synthetic */ Date access$200(SingleResponse singleResponse) {
            return singleResponse.nextUpdate;
        }

        static /* synthetic */ Date access$300(SingleResponse singleResponse) {
            return singleResponse.thisUpdate;
        }

        private CertId getCertId() {
            return this.certId;
        }

        @Override
        public OCSP.RevocationStatus.CertStatus getCertStatus() {
            return this.certStatus;
        }

        @Override
        public CRLReason getRevocationReason() {
            return this.revocationReason;
        }

        @Override
        public Date getRevocationTime() {
            return (Date)this.revocationTime.clone();
        }

        @Override
        public Map<String, Extension> getSingleExtensions() {
            return Collections.unmodifiableMap(this.singleExtensions);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SingleResponse:  \n");
            stringBuilder.append(this.certId);
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("\nCertStatus: ");
            stringBuilder2.append((Object)this.certStatus);
            stringBuilder2.append("\n");
            stringBuilder.append(stringBuilder2.toString());
            if (this.certStatus == OCSP.RevocationStatus.CertStatus.REVOKED) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("revocationTime is ");
                stringBuilder2.append(this.revocationTime);
                stringBuilder2.append("\n");
                stringBuilder.append(stringBuilder2.toString());
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("revocationReason is ");
                stringBuilder2.append((Object)this.revocationReason);
                stringBuilder2.append("\n");
                stringBuilder.append(stringBuilder2.toString());
            }
            stringBuilder2 = new StringBuilder();
            stringBuilder2.append("thisUpdate is ");
            stringBuilder2.append(this.thisUpdate);
            stringBuilder2.append("\n");
            stringBuilder.append(stringBuilder2.toString());
            if (this.nextUpdate != null) {
                stringBuilder2 = new StringBuilder();
                stringBuilder2.append("nextUpdate is ");
                stringBuilder2.append(this.nextUpdate);
                stringBuilder2.append("\n");
                stringBuilder.append(stringBuilder2.toString());
            }
            return stringBuilder.toString();
        }
    }

}

