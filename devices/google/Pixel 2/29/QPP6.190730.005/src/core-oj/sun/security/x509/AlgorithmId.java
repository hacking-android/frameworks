/*
 * Decompiled with CFR 0.145.
 */
package sun.security.x509;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import sun.security.util.DerEncoder;
import sun.security.util.DerInputStream;
import sun.security.util.DerOutputStream;
import sun.security.util.DerValue;
import sun.security.util.ObjectIdentifier;

public class AlgorithmId
implements Serializable,
DerEncoder {
    public static final ObjectIdentifier AES_oid;
    private static final int[] DH_PKIX_data;
    public static final ObjectIdentifier DH_PKIX_oid;
    private static final int[] DH_data;
    public static final ObjectIdentifier DH_oid;
    private static final int[] DSA_OIW_data;
    public static final ObjectIdentifier DSA_OIW_oid;
    private static final int[] DSA_PKIX_data;
    public static final ObjectIdentifier DSA_oid;
    public static final ObjectIdentifier ECDH_oid;
    public static final ObjectIdentifier EC_oid;
    public static final ObjectIdentifier MD2_oid;
    public static final ObjectIdentifier MD5_oid;
    private static final int[] RSAEncryption_data;
    public static final ObjectIdentifier RSAEncryption_oid;
    private static final int[] RSA_data;
    public static final ObjectIdentifier RSA_oid;
    public static final ObjectIdentifier SHA224_oid;
    public static final ObjectIdentifier SHA256_oid;
    public static final ObjectIdentifier SHA384_oid;
    public static final ObjectIdentifier SHA512_oid;
    public static final ObjectIdentifier SHA_oid;
    private static final int[] dsaWithSHA1_PKIX_data;
    private static int initOidTableVersion = 0;
    private static final int[] md2WithRSAEncryption_data;
    public static final ObjectIdentifier md2WithRSAEncryption_oid;
    private static final int[] md5WithRSAEncryption_data;
    public static final ObjectIdentifier md5WithRSAEncryption_oid;
    private static final Map<ObjectIdentifier, String> nameTable;
    private static final Map<String, ObjectIdentifier> oidTable;
    public static final ObjectIdentifier pbeWithMD5AndDES_oid;
    public static final ObjectIdentifier pbeWithMD5AndRC2_oid;
    public static final ObjectIdentifier pbeWithSHA1AndDES_oid;
    public static ObjectIdentifier pbeWithSHA1AndDESede_oid;
    public static ObjectIdentifier pbeWithSHA1AndRC2_40_oid;
    public static final ObjectIdentifier pbeWithSHA1AndRC2_oid;
    private static final long serialVersionUID = 7205873507486557157L;
    private static final int[] sha1WithDSA_OIW_data;
    public static final ObjectIdentifier sha1WithDSA_OIW_oid;
    public static final ObjectIdentifier sha1WithDSA_oid;
    public static final ObjectIdentifier sha1WithECDSA_oid;
    private static final int[] sha1WithRSAEncryption_OIW_data;
    public static final ObjectIdentifier sha1WithRSAEncryption_OIW_oid;
    private static final int[] sha1WithRSAEncryption_data;
    public static final ObjectIdentifier sha1WithRSAEncryption_oid;
    public static final ObjectIdentifier sha224WithDSA_oid;
    public static final ObjectIdentifier sha224WithECDSA_oid;
    private static final int[] sha224WithRSAEncryption_data;
    public static final ObjectIdentifier sha224WithRSAEncryption_oid;
    public static final ObjectIdentifier sha256WithDSA_oid;
    public static final ObjectIdentifier sha256WithECDSA_oid;
    private static final int[] sha256WithRSAEncryption_data;
    public static final ObjectIdentifier sha256WithRSAEncryption_oid;
    public static final ObjectIdentifier sha384WithECDSA_oid;
    private static final int[] sha384WithRSAEncryption_data;
    public static final ObjectIdentifier sha384WithRSAEncryption_oid;
    public static final ObjectIdentifier sha512WithECDSA_oid;
    private static final int[] sha512WithRSAEncryption_data;
    public static final ObjectIdentifier sha512WithRSAEncryption_oid;
    private static final int[] shaWithDSA_OIW_data;
    public static final ObjectIdentifier shaWithDSA_OIW_oid;
    public static final ObjectIdentifier specifiedWithECDSA_oid;
    private AlgorithmParameters algParams;
    private ObjectIdentifier algid;
    private boolean constructedFromDer = true;
    protected DerValue params;

    static {
        initOidTableVersion = -1;
        oidTable = new HashMap<String, ObjectIdentifier>(1);
        nameTable = new HashMap<ObjectIdentifier, String>();
        MD2_oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 2, 2});
        MD5_oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 2, 5});
        SHA_oid = ObjectIdentifier.newInternal(new int[]{1, 3, 14, 3, 2, 26});
        SHA224_oid = ObjectIdentifier.newInternal(new int[]{2, 16, 840, 1, 101, 3, 4, 2, 4});
        SHA256_oid = ObjectIdentifier.newInternal(new int[]{2, 16, 840, 1, 101, 3, 4, 2, 1});
        SHA384_oid = ObjectIdentifier.newInternal(new int[]{2, 16, 840, 1, 101, 3, 4, 2, 2});
        SHA512_oid = ObjectIdentifier.newInternal(new int[]{2, 16, 840, 1, 101, 3, 4, 2, 3});
        DH_data = new int[]{1, 2, 840, 113549, 1, 3, 1};
        DH_PKIX_data = new int[]{1, 2, 840, 10046, 2, 1};
        DSA_OIW_data = new int[]{1, 3, 14, 3, 2, 12};
        DSA_PKIX_data = new int[]{1, 2, 840, 10040, 4, 1};
        RSA_data = new int[]{2, 5, 8, 1, 1};
        RSAEncryption_data = new int[]{1, 2, 840, 113549, 1, 1, 1};
        EC_oid = AlgorithmId.oid(1, 2, 840, 10045, 2, 1);
        ECDH_oid = AlgorithmId.oid(1, 3, 132, 1, 12);
        AES_oid = AlgorithmId.oid(2, 16, 840, 1, 101, 3, 4, 1);
        md2WithRSAEncryption_data = new int[]{1, 2, 840, 113549, 1, 1, 2};
        md5WithRSAEncryption_data = new int[]{1, 2, 840, 113549, 1, 1, 4};
        sha1WithRSAEncryption_data = new int[]{1, 2, 840, 113549, 1, 1, 5};
        sha1WithRSAEncryption_OIW_data = new int[]{1, 3, 14, 3, 2, 29};
        sha224WithRSAEncryption_data = new int[]{1, 2, 840, 113549, 1, 1, 14};
        sha256WithRSAEncryption_data = new int[]{1, 2, 840, 113549, 1, 1, 11};
        sha384WithRSAEncryption_data = new int[]{1, 2, 840, 113549, 1, 1, 12};
        sha512WithRSAEncryption_data = new int[]{1, 2, 840, 113549, 1, 1, 13};
        shaWithDSA_OIW_data = new int[]{1, 3, 14, 3, 2, 13};
        sha1WithDSA_OIW_data = new int[]{1, 3, 14, 3, 2, 27};
        dsaWithSHA1_PKIX_data = new int[]{1, 2, 840, 10040, 4, 3};
        sha224WithDSA_oid = AlgorithmId.oid(2, 16, 840, 1, 101, 3, 4, 3, 1);
        sha256WithDSA_oid = AlgorithmId.oid(2, 16, 840, 1, 101, 3, 4, 3, 2);
        sha1WithECDSA_oid = AlgorithmId.oid(1, 2, 840, 10045, 4, 1);
        sha224WithECDSA_oid = AlgorithmId.oid(1, 2, 840, 10045, 4, 3, 1);
        sha256WithECDSA_oid = AlgorithmId.oid(1, 2, 840, 10045, 4, 3, 2);
        sha384WithECDSA_oid = AlgorithmId.oid(1, 2, 840, 10045, 4, 3, 3);
        sha512WithECDSA_oid = AlgorithmId.oid(1, 2, 840, 10045, 4, 3, 4);
        specifiedWithECDSA_oid = AlgorithmId.oid(1, 2, 840, 10045, 4, 3);
        pbeWithMD5AndDES_oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 5, 3});
        pbeWithMD5AndRC2_oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 5, 6});
        pbeWithSHA1AndDES_oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 5, 10});
        pbeWithSHA1AndRC2_oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 5, 11});
        pbeWithSHA1AndDESede_oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 12, 1, 3});
        pbeWithSHA1AndRC2_40_oid = ObjectIdentifier.newInternal(new int[]{1, 2, 840, 113549, 1, 12, 1, 6});
        DH_oid = ObjectIdentifier.newInternal(DH_data);
        DH_PKIX_oid = ObjectIdentifier.newInternal(DH_PKIX_data);
        DSA_OIW_oid = ObjectIdentifier.newInternal(DSA_OIW_data);
        DSA_oid = ObjectIdentifier.newInternal(DSA_PKIX_data);
        RSA_oid = ObjectIdentifier.newInternal(RSA_data);
        RSAEncryption_oid = ObjectIdentifier.newInternal(RSAEncryption_data);
        md2WithRSAEncryption_oid = ObjectIdentifier.newInternal(md2WithRSAEncryption_data);
        md5WithRSAEncryption_oid = ObjectIdentifier.newInternal(md5WithRSAEncryption_data);
        sha1WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha1WithRSAEncryption_data);
        sha1WithRSAEncryption_OIW_oid = ObjectIdentifier.newInternal(sha1WithRSAEncryption_OIW_data);
        sha224WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha224WithRSAEncryption_data);
        sha256WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha256WithRSAEncryption_data);
        sha384WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha384WithRSAEncryption_data);
        sha512WithRSAEncryption_oid = ObjectIdentifier.newInternal(sha512WithRSAEncryption_data);
        shaWithDSA_OIW_oid = ObjectIdentifier.newInternal(shaWithDSA_OIW_data);
        sha1WithDSA_OIW_oid = ObjectIdentifier.newInternal(sha1WithDSA_OIW_data);
        sha1WithDSA_oid = ObjectIdentifier.newInternal(dsaWithSHA1_PKIX_data);
        nameTable.put(MD5_oid, "MD5");
        nameTable.put(MD2_oid, "MD2");
        nameTable.put(SHA_oid, "SHA-1");
        nameTable.put(SHA224_oid, "SHA-224");
        nameTable.put(SHA256_oid, "SHA-256");
        nameTable.put(SHA384_oid, "SHA-384");
        nameTable.put(SHA512_oid, "SHA-512");
        nameTable.put(RSAEncryption_oid, "RSA");
        nameTable.put(RSA_oid, "RSA");
        nameTable.put(DH_oid, "Diffie-Hellman");
        nameTable.put(DH_PKIX_oid, "Diffie-Hellman");
        nameTable.put(DSA_oid, "DSA");
        nameTable.put(DSA_OIW_oid, "DSA");
        nameTable.put(EC_oid, "EC");
        nameTable.put(ECDH_oid, "ECDH");
        nameTable.put(AES_oid, "AES");
        nameTable.put(sha1WithECDSA_oid, "SHA1withECDSA");
        nameTable.put(sha224WithECDSA_oid, "SHA224withECDSA");
        nameTable.put(sha256WithECDSA_oid, "SHA256withECDSA");
        nameTable.put(sha384WithECDSA_oid, "SHA384withECDSA");
        nameTable.put(sha512WithECDSA_oid, "SHA512withECDSA");
        nameTable.put(md5WithRSAEncryption_oid, "MD5withRSA");
        nameTable.put(md2WithRSAEncryption_oid, "MD2withRSA");
        nameTable.put(sha1WithDSA_oid, "SHA1withDSA");
        nameTable.put(sha1WithDSA_OIW_oid, "SHA1withDSA");
        nameTable.put(shaWithDSA_OIW_oid, "SHA1withDSA");
        nameTable.put(sha224WithDSA_oid, "SHA224withDSA");
        nameTable.put(sha256WithDSA_oid, "SHA256withDSA");
        nameTable.put(sha1WithRSAEncryption_oid, "SHA1withRSA");
        nameTable.put(sha1WithRSAEncryption_OIW_oid, "SHA1withRSA");
        nameTable.put(sha224WithRSAEncryption_oid, "SHA224withRSA");
        nameTable.put(sha256WithRSAEncryption_oid, "SHA256withRSA");
        nameTable.put(sha384WithRSAEncryption_oid, "SHA384withRSA");
        nameTable.put(sha512WithRSAEncryption_oid, "SHA512withRSA");
        nameTable.put(pbeWithMD5AndDES_oid, "PBEWithMD5AndDES");
        nameTable.put(pbeWithMD5AndRC2_oid, "PBEWithMD5AndRC2");
        nameTable.put(pbeWithSHA1AndDES_oid, "PBEWithSHA1AndDES");
        nameTable.put(pbeWithSHA1AndRC2_oid, "PBEWithSHA1AndRC2");
        nameTable.put(pbeWithSHA1AndDESede_oid, "PBEWithSHA1AndDESede");
        nameTable.put(pbeWithSHA1AndRC2_40_oid, "PBEWithSHA1AndRC2_40");
    }

    @Deprecated
    public AlgorithmId() {
    }

    public AlgorithmId(ObjectIdentifier objectIdentifier) {
        this.algid = objectIdentifier;
    }

    public AlgorithmId(ObjectIdentifier objectIdentifier, AlgorithmParameters algorithmParameters) {
        this.algid = objectIdentifier;
        this.algParams = algorithmParameters;
        this.constructedFromDer = false;
    }

    private AlgorithmId(ObjectIdentifier objectIdentifier, DerValue derValue) throws IOException {
        this.algid = objectIdentifier;
        this.params = derValue;
        if (this.params != null) {
            this.decodeParams();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static ObjectIdentifier algOID(String object) throws IOException {
        if (((String)object).indexOf(46) != -1) {
            if (!((String)object).startsWith("OID.")) return new ObjectIdentifier((String)object);
            return new ObjectIdentifier(((String)object).substring("OID.".length()));
        }
        if (((String)object).equalsIgnoreCase("MD5")) {
            return MD5_oid;
        }
        if (((String)object).equalsIgnoreCase("MD2")) {
            return MD2_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA")) return SHA_oid;
        if (((String)object).equalsIgnoreCase("SHA1")) return SHA_oid;
        if (((String)object).equalsIgnoreCase("SHA-1")) {
            return SHA_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA-256")) return SHA256_oid;
        if (((String)object).equalsIgnoreCase("SHA256")) {
            return SHA256_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA-384")) return SHA384_oid;
        if (((String)object).equalsIgnoreCase("SHA384")) {
            return SHA384_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA-512")) return SHA512_oid;
        if (((String)object).equalsIgnoreCase("SHA512")) {
            return SHA512_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA-224")) return SHA224_oid;
        if (((String)object).equalsIgnoreCase("SHA224")) {
            return SHA224_oid;
        }
        if (((String)object).equalsIgnoreCase("RSA")) {
            return RSAEncryption_oid;
        }
        if (((String)object).equalsIgnoreCase("Diffie-Hellman")) return DH_oid;
        if (((String)object).equalsIgnoreCase("DH")) {
            return DH_oid;
        }
        if (((String)object).equalsIgnoreCase("DSA")) {
            return DSA_oid;
        }
        if (((String)object).equalsIgnoreCase("EC")) {
            return EC_oid;
        }
        if (((String)object).equalsIgnoreCase("ECDH")) {
            return ECDH_oid;
        }
        if (((String)object).equalsIgnoreCase("AES")) {
            return AES_oid;
        }
        if (((String)object).equalsIgnoreCase("MD5withRSA")) return md5WithRSAEncryption_oid;
        if (((String)object).equalsIgnoreCase("MD5/RSA")) {
            return md5WithRSAEncryption_oid;
        }
        if (((String)object).equalsIgnoreCase("MD2withRSA")) return md2WithRSAEncryption_oid;
        if (((String)object).equalsIgnoreCase("MD2/RSA")) {
            return md2WithRSAEncryption_oid;
        }
        if (((String)object).equalsIgnoreCase("SHAwithDSA")) return sha1WithDSA_oid;
        if (((String)object).equalsIgnoreCase("SHA1withDSA")) return sha1WithDSA_oid;
        if (((String)object).equalsIgnoreCase("SHA/DSA")) return sha1WithDSA_oid;
        if (((String)object).equalsIgnoreCase("SHA1/DSA")) return sha1WithDSA_oid;
        if (((String)object).equalsIgnoreCase("DSAWithSHA1")) return sha1WithDSA_oid;
        if (((String)object).equalsIgnoreCase("DSS")) return sha1WithDSA_oid;
        if (((String)object).equalsIgnoreCase("SHA-1/DSA")) {
            return sha1WithDSA_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA224WithDSA")) {
            return sha224WithDSA_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA256WithDSA")) {
            return sha256WithDSA_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA1WithRSA")) return sha1WithRSAEncryption_oid;
        if (((String)object).equalsIgnoreCase("SHA1/RSA")) {
            return sha1WithRSAEncryption_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA1withECDSA")) return sha1WithECDSA_oid;
        if (((String)object).equalsIgnoreCase("ECDSA")) {
            return sha1WithECDSA_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA224withECDSA")) {
            return sha224WithECDSA_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA256withECDSA")) {
            return sha256WithECDSA_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA384withECDSA")) {
            return sha384WithECDSA_oid;
        }
        if (((String)object).equalsIgnoreCase("SHA512withECDSA")) {
            return sha512WithECDSA_oid;
        }
        Map<String, ObjectIdentifier> map = oidTable;
        synchronized (map) {
            AlgorithmId.reinitializeMappingTableLocked();
            return oidTable.get(((String)object).toUpperCase(Locale.ENGLISH));
        }
    }

    public static AlgorithmId get(String string) throws NoSuchAlgorithmException {
        Serializable serializable;
        try {
            serializable = AlgorithmId.algOID(string);
            if (serializable != null) {
                return new AlgorithmId((ObjectIdentifier)serializable);
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("unrecognized algorithm name: ");
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid ObjectIdentifier ");
            stringBuilder.append(string);
            throw new NoSuchAlgorithmException(stringBuilder.toString());
        }
        ((StringBuilder)serializable).append(string);
        throw new NoSuchAlgorithmException(((StringBuilder)serializable).toString());
    }

    public static AlgorithmId get(AlgorithmParameters object) throws NoSuchAlgorithmException {
        String string = ((AlgorithmParameters)object).getAlgorithm();
        try {
            ObjectIdentifier objectIdentifier = AlgorithmId.algOID(string);
            if (objectIdentifier != null) {
                return new AlgorithmId(objectIdentifier, (AlgorithmParameters)object);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("unrecognized algorithm name: ");
        }
        catch (IOException iOException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid ObjectIdentifier ");
            stringBuilder.append(string);
            throw new NoSuchAlgorithmException(stringBuilder.toString());
        }
        ((StringBuilder)object).append(string);
        throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
    }

    @Deprecated
    public static AlgorithmId getAlgorithmId(String string) throws NoSuchAlgorithmException {
        return AlgorithmId.get(string);
    }

    public static String getDigAlgFromSigAlg(String string) {
        int n = (string = string.toUpperCase(Locale.ENGLISH)).indexOf("WITH");
        if (n > 0) {
            return string.substring(0, n);
        }
        return null;
    }

    public static String getEncAlgFromSigAlg(String string) {
        String string2 = string.toUpperCase(Locale.ENGLISH);
        int n = string2.indexOf("WITH");
        string = null;
        if (n > 0) {
            int n2 = string2.indexOf("AND", n + 4);
            string2 = n2 > 0 ? string2.substring(n + 4, n2) : string2.substring(n + 4);
            string = string2;
            if (string2.equalsIgnoreCase("ECDSA")) {
                string = "EC";
            }
        }
        return string;
    }

    public static String makeSigAlg(String string, String charSequence) {
        String string2 = string.replace("-", "");
        string = charSequence;
        if (((String)charSequence).equalsIgnoreCase("EC")) {
            string = "ECDSA";
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append("with");
        ((StringBuilder)charSequence).append(string);
        return ((StringBuilder)charSequence).toString();
    }

    private static ObjectIdentifier oid(int ... arrn) {
        return ObjectIdentifier.newInternal(arrn);
    }

    public static AlgorithmId parse(DerValue derValue) throws IOException {
        block6 : {
            block9 : {
                ObjectIdentifier objectIdentifier;
                block8 : {
                    DerValue derValue2;
                    DerInputStream derInputStream;
                    block7 : {
                        if (derValue.tag != 48) break block6;
                        derInputStream = derValue.toDerInputStream();
                        objectIdentifier = derInputStream.getOID();
                        if (derInputStream.available() != 0) break block7;
                        derValue = null;
                        break block8;
                    }
                    derValue = derValue2 = derInputStream.getDerValue();
                    if (derValue2.tag == 5) {
                        if (derValue2.length() == 0) {
                            derValue = null;
                        } else {
                            throw new IOException("invalid NULL");
                        }
                    }
                    if (derInputStream.available() != 0) break block9;
                }
                return new AlgorithmId(objectIdentifier, derValue);
            }
            throw new IOException("Invalid AlgorithmIdentifier: extra data");
        }
        throw new IOException("algid parse error, not a sequence");
    }

    private static void reinitializeMappingTableLocked() {
        int n = Security.getVersion();
        if (initOidTableVersion != n) {
            Provider[] arrprovider = Security.getProviders();
            block4 : for (int i = 0; i < arrprovider.length; ++i) {
                Enumeration<Object> enumeration = arrprovider[i].keys();
                while (enumeration.hasMoreElements()) {
                    String string;
                    Object object;
                    String string2 = (String)enumeration.nextElement();
                    Object object2 = string2.toUpperCase(Locale.ENGLISH);
                    if (!((String)object2).startsWith("ALG.ALIAS")) continue;
                    int n2 = ((String)object2).indexOf("OID.", 0);
                    if (n2 != -1) {
                        if ((n2 += "OID.".length()) == string2.length()) continue block4;
                        string = string2.substring(n2);
                        object2 = arrprovider[i].getProperty(string2);
                        if (object2 == null) continue;
                        string2 = ((String)object2).toUpperCase(Locale.ENGLISH);
                        object2 = null;
                        try {
                            object2 = object = new ObjectIdentifier(string);
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                        if (object2 == null) continue;
                        if (!oidTable.containsKey(string2)) {
                            oidTable.put(string2, (ObjectIdentifier)object2);
                        }
                        if (nameTable.containsKey(object2)) continue;
                        nameTable.put((ObjectIdentifier)object2, string2);
                        continue;
                    }
                    string = string2.substring(string2.indexOf(46, "ALG.ALIAS.".length()) + 1);
                    object2 = null;
                    try {
                        object2 = object = new ObjectIdentifier(string);
                    }
                    catch (IOException iOException) {
                        // empty catch block
                    }
                    if (object2 == null || (object = arrprovider[i].getProperty(string2)) == null) continue;
                    if (!oidTable.containsKey(object = ((String)object).toUpperCase(Locale.ENGLISH))) {
                        oidTable.put((String)object, (ObjectIdentifier)object2);
                    }
                    if (nameTable.containsKey(object2)) continue;
                    nameTable.put((ObjectIdentifier)object2, (String)object);
                }
            }
            initOidTableVersion = n;
        }
    }

    protected void decodeParams() throws IOException {
        String string = this.algid.toString();
        try {
            this.algParams = AlgorithmParameters.getInstance(string);
            this.algParams.init(this.params.toByteArray());
            return;
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            this.algParams = null;
            return;
        }
    }

    @Override
    public void derEncode(OutputStream outputStream) throws IOException {
        Object object;
        DerOutputStream derOutputStream = new DerOutputStream();
        DerOutputStream derOutputStream2 = new DerOutputStream();
        derOutputStream.putOID(this.algid);
        if (!this.constructedFromDer) {
            object = this.algParams;
            this.params = object != null ? new DerValue(((AlgorithmParameters)object).getEncoded()) : null;
        }
        if ((object = this.params) == null) {
            derOutputStream.putNull();
        } else {
            derOutputStream.putDerValue((DerValue)object);
        }
        derOutputStream2.write((byte)48, derOutputStream);
        outputStream.write(derOutputStream2.toByteArray());
    }

    public final void encode(DerOutputStream derOutputStream) throws IOException {
        this.derEncode(derOutputStream);
    }

    public final byte[] encode() throws IOException {
        DerOutputStream derOutputStream = new DerOutputStream();
        this.derEncode(derOutputStream);
        return derOutputStream.toByteArray();
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object instanceof AlgorithmId) {
            return this.equals((AlgorithmId)object);
        }
        if (object instanceof ObjectIdentifier) {
            return this.equals((ObjectIdentifier)object);
        }
        return false;
    }

    public final boolean equals(ObjectIdentifier objectIdentifier) {
        return this.algid.equals((Object)objectIdentifier);
    }

    public boolean equals(AlgorithmId algorithmId) {
        DerValue derValue = this.params;
        boolean bl = true;
        boolean bl2 = derValue == null ? algorithmId.params == null : derValue.equals(algorithmId.params);
        bl2 = this.algid.equals((Object)algorithmId.algid) && bl2 ? bl : false;
        return bl2;
    }

    public byte[] getEncodedParams() throws IOException {
        Object object = this.params;
        object = object == null ? null : object.toByteArray();
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String getName() {
        Object object = nameTable.get(this.algid);
        if (object != null) {
            return object;
        }
        if (this.params != null && this.algid.equals((Object)specifiedWithECDSA_oid)) {
            try {
                object = new DerValue(this.getEncodedParams());
                AlgorithmId.makeSigAlg(AlgorithmId.parse((DerValue)object).getName(), "EC");
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        Map<String, ObjectIdentifier> map = oidTable;
        synchronized (map) {
            AlgorithmId.reinitializeMappingTableLocked();
            object = nameTable.get(this.algid);
        }
        if (object != null) return object;
        return this.algid.toString();
    }

    public final ObjectIdentifier getOID() {
        return this.algid;
    }

    public AlgorithmParameters getParameters() {
        return this.algParams;
    }

    public int hashCode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.algid.toString());
        stringBuilder.append(this.paramsToString());
        return stringBuilder.toString().hashCode();
    }

    protected String paramsToString() {
        if (this.params == null) {
            return "";
        }
        AlgorithmParameters algorithmParameters = this.algParams;
        if (algorithmParameters != null) {
            return algorithmParameters.toString();
        }
        return ", params unparsed";
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getName());
        stringBuilder.append(this.paramsToString());
        return stringBuilder.toString();
    }
}

