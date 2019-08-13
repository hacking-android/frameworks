/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.VMRuntime
 */
package sun.security.jca;

import dalvik.system.VMRuntime;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import sun.security.jca.ProviderList;
import sun.security.util.Debug;

public class Providers {
    private static final String BACKUP_PROVIDER_CLASSNAME = "sun.security.provider.VerificationProvider";
    public static final int DEFAULT_MAXIMUM_ALLOWABLE_TARGET_API_LEVEL_FOR_BC_DEPRECATION = 27;
    private static final Set<String> DEPRECATED_ALGORITHMS;
    private static volatile Provider SYSTEM_BOUNCY_CASTLE_PROVIDER;
    private static final String[] jarVerificationProviders;
    private static int maximumAllowableApiLevelForBcDeprecation;
    private static volatile ProviderList providerList;
    private static final ThreadLocal<ProviderList> threadLists;
    private static volatile int threadListsUsed;

    static {
        threadLists = new InheritableThreadLocal<ProviderList>();
        providerList = ProviderList.EMPTY;
        providerList = ProviderList.fromSecurityProperties();
        int n = providerList.size();
        if (n == (providerList = providerList.removeInvalid()).size()) {
            SYSTEM_BOUNCY_CASTLE_PROVIDER = providerList.getProvider("BC");
            jarVerificationProviders = new String[]{"com.android.org.conscrypt.OpenSSLProvider", "com.android.org.bouncycastle.jce.provider.BouncyCastleProvider", "com.android.org.conscrypt.JSSEProvider", BACKUP_PROVIDER_CLASSNAME};
            maximumAllowableApiLevelForBcDeprecation = 27;
            DEPRECATED_ALGORITHMS = new HashSet<String>();
            DEPRECATED_ALGORITHMS.addAll(Arrays.asList("ALGORITHMPARAMETERS.1.2.840.113549.3.7", "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.2", "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.22", "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.26", "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.42", "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.46", "ALGORITHMPARAMETERS.2.16.840.1.101.3.4.1.6", "ALGORITHMPARAMETERS.AES", "ALGORITHMPARAMETERS.DESEDE", "ALGORITHMPARAMETERS.EC", "ALGORITHMPARAMETERS.GCM", "ALGORITHMPARAMETERS.OAEP", "ALGORITHMPARAMETERS.TDEA", "CERTIFICATEFACTORY.X.509", "CERTIFICATEFACTORY.X509", "CIPHER.1.2.840.113549.3.4", "CIPHER.2.16.840.1.101.3.4.1.26", "CIPHER.2.16.840.1.101.3.4.1.46", "CIPHER.2.16.840.1.101.3.4.1.6", "CIPHER.AES/GCM/NOPADDING", "CIPHER.ARC4", "CIPHER.ARCFOUR", "CIPHER.OID.1.2.840.113549.3.4", "CIPHER.RC4", "CIPHER.ARC4/ECB/NOPADDING", "CIPHER.ARC4/NONE/NOPADDING", "CIPHER.ARCFOUR/ECB/NOPADDING", "CIPHER.ARCFOUR/NONE/NOPADDING", "CIPHER.RC4/ECB/NOPADDING", "CIPHER.RC4/NONE/NOPADDING", "KEYAGREEMENT.ECDH", "KEYFACTORY.1.2.840.10045.2.1", "KEYFACTORY.1.2.840.113549.1.1.1", "KEYFACTORY.1.2.840.113549.1.1.7", "KEYFACTORY.1.3.133.16.840.63.0.2", "KEYFACTORY.2.5.8.1.1", "KEYFACTORY.EC", "KEYFACTORY.RSA", "KEYGENERATOR.1.2.840.113549.2.10", "KEYGENERATOR.1.2.840.113549.2.11", "KEYGENERATOR.1.2.840.113549.2.7", "KEYGENERATOR.1.2.840.113549.2.8", "KEYGENERATOR.1.2.840.113549.2.9", "KEYGENERATOR.1.3.6.1.5.5.8.1.1", "KEYGENERATOR.1.3.6.1.5.5.8.1.2", "KEYGENERATOR.2.16.840.1.101.3.4.2.1", "KEYGENERATOR.AES", "KEYGENERATOR.DESEDE", "KEYGENERATOR.HMAC-MD5", "KEYGENERATOR.HMAC-SHA1", "KEYGENERATOR.HMAC-SHA224", "KEYGENERATOR.HMAC-SHA256", "KEYGENERATOR.HMAC-SHA384", "KEYGENERATOR.HMAC-SHA512", "KEYGENERATOR.HMAC/MD5", "KEYGENERATOR.HMAC/SHA1", "KEYGENERATOR.HMAC/SHA224", "KEYGENERATOR.HMAC/SHA256", "KEYGENERATOR.HMAC/SHA384", "KEYGENERATOR.HMAC/SHA512", "KEYGENERATOR.HMACMD5", "KEYGENERATOR.HMACSHA1", "KEYGENERATOR.HMACSHA224", "KEYGENERATOR.HMACSHA256", "KEYGENERATOR.HMACSHA384", "KEYGENERATOR.HMACSHA512", "KEYGENERATOR.TDEA", "KEYPAIRGENERATOR.1.2.840.10045.2.1", "KEYPAIRGENERATOR.1.2.840.113549.1.1.1", "KEYPAIRGENERATOR.1.2.840.113549.1.1.7", "KEYPAIRGENERATOR.1.3.133.16.840.63.0.2", "KEYPAIRGENERATOR.2.5.8.1.1", "KEYPAIRGENERATOR.EC", "KEYPAIRGENERATOR.RSA", "MAC.1.2.840.113549.2.10", "MAC.1.2.840.113549.2.11", "MAC.1.2.840.113549.2.7", "MAC.1.2.840.113549.2.8", "MAC.1.2.840.113549.2.9", "MAC.1.3.6.1.5.5.8.1.1", "MAC.1.3.6.1.5.5.8.1.2", "MAC.2.16.840.1.101.3.4.2.1", "MAC.HMAC-MD5", "MAC.HMAC-SHA1", "MAC.HMAC-SHA224", "MAC.HMAC-SHA256", "MAC.HMAC-SHA384", "MAC.HMAC-SHA512", "MAC.HMAC/MD5", "MAC.HMAC/SHA1", "MAC.HMAC/SHA224", "MAC.HMAC/SHA256", "MAC.HMAC/SHA384", "MAC.HMAC/SHA512", "MAC.HMACMD5", "MAC.HMACSHA1", "MAC.HMACSHA224", "MAC.HMACSHA256", "MAC.HMACSHA384", "MAC.HMACSHA512", "MAC.PBEWITHHMACSHA224", "MAC.PBEWITHHMACSHA256", "MAC.PBEWITHHMACSHA384", "MAC.PBEWITHHMACSHA512", "MESSAGEDIGEST.1.2.840.113549.2.5", "MESSAGEDIGEST.1.3.14.3.2.26", "MESSAGEDIGEST.2.16.840.1.101.3.4.2.1", "MESSAGEDIGEST.2.16.840.1.101.3.4.2.2", "MESSAGEDIGEST.2.16.840.1.101.3.4.2.3", "MESSAGEDIGEST.2.16.840.1.101.3.4.2.4", "MESSAGEDIGEST.MD5", "MESSAGEDIGEST.SHA", "MESSAGEDIGEST.SHA-1", "MESSAGEDIGEST.SHA-224", "MESSAGEDIGEST.SHA-256", "MESSAGEDIGEST.SHA-384", "MESSAGEDIGEST.SHA-512", "MESSAGEDIGEST.SHA1", "MESSAGEDIGEST.SHA224", "MESSAGEDIGEST.SHA256", "MESSAGEDIGEST.SHA384", "MESSAGEDIGEST.SHA512", "SECRETKEYFACTORY.DESEDE", "SECRETKEYFACTORY.TDEA", "SIGNATURE.1.2.840.10045.4.1", "SIGNATURE.1.2.840.10045.4.3.1", "SIGNATURE.1.2.840.10045.4.3.2", "SIGNATURE.1.2.840.10045.4.3.3", "SIGNATURE.1.2.840.10045.4.3.4", "SIGNATURE.1.2.840.113549.1.1.11", "SIGNATURE.1.2.840.113549.1.1.12", "SIGNATURE.1.2.840.113549.1.1.13", "SIGNATURE.1.2.840.113549.1.1.14", "SIGNATURE.1.2.840.113549.1.1.4", "SIGNATURE.1.2.840.113549.1.1.5", "SIGNATURE.1.3.14.3.2.29", "SIGNATURE.ECDSA", "SIGNATURE.ECDSAWITHSHA1", "SIGNATURE.MD5/RSA", "SIGNATURE.MD5WITHRSA", "SIGNATURE.MD5WITHRSAENCRYPTION", "SIGNATURE.NONEWITHECDSA", "SIGNATURE.OID.1.2.840.10045.4.3.1", "SIGNATURE.OID.1.2.840.10045.4.3.2", "SIGNATURE.OID.1.2.840.10045.4.3.3", "SIGNATURE.OID.1.2.840.10045.4.3.4", "SIGNATURE.OID.1.2.840.113549.1.1.11", "SIGNATURE.OID.1.2.840.113549.1.1.12", "SIGNATURE.OID.1.2.840.113549.1.1.13", "SIGNATURE.OID.1.2.840.113549.1.1.14", "SIGNATURE.OID.1.2.840.113549.1.1.4", "SIGNATURE.OID.1.2.840.113549.1.1.5", "SIGNATURE.OID.1.3.14.3.2.29", "SIGNATURE.SHA1/RSA", "SIGNATURE.SHA1WITHECDSA", "SIGNATURE.SHA1WITHRSA", "SIGNATURE.SHA1WITHRSAENCRYPTION", "SIGNATURE.SHA224/ECDSA", "SIGNATURE.SHA224/RSA", "SIGNATURE.SHA224WITHECDSA", "SIGNATURE.SHA224WITHRSA", "SIGNATURE.SHA224WITHRSAENCRYPTION", "SIGNATURE.SHA256/ECDSA", "SIGNATURE.SHA256/RSA", "SIGNATURE.SHA256WITHECDSA", "SIGNATURE.SHA256WITHRSA", "SIGNATURE.SHA256WITHRSAENCRYPTION", "SIGNATURE.SHA384/ECDSA", "SIGNATURE.SHA384/RSA", "SIGNATURE.SHA384WITHECDSA", "SIGNATURE.SHA384WITHRSA", "SIGNATURE.SHA384WITHRSAENCRYPTION", "SIGNATURE.SHA512/ECDSA", "SIGNATURE.SHA512/RSA", "SIGNATURE.SHA512WITHECDSA", "SIGNATURE.SHA512WITHRSA", "SIGNATURE.SHA512WITHRSAENCRYPTION"));
            return;
        }
        throw new AssertionError((Object)"Unable to configure default providers");
    }

    private Providers() {
    }

    public static ProviderList beginThreadProviderList(ProviderList providerList) {
        synchronized (Providers.class) {
            Object object;
            if (ProviderList.debug != null) {
                Debug debug = ProviderList.debug;
                object = new StringBuilder();
                ((StringBuilder)object).append("ThreadLocal providers: ");
                ((StringBuilder)object).append(providerList);
                debug.println(((StringBuilder)object).toString());
            }
            object = threadLists.get();
            ++threadListsUsed;
            threadLists.set(providerList);
            return object;
        }
    }

    private static void changeThreadProviderList(ProviderList providerList) {
        threadLists.set(providerList);
    }

    private static void checkBouncyCastleDeprecation(String string, String charSequence) throws NoSuchAlgorithmException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".");
        stringBuilder.append((String)charSequence);
        string = stringBuilder.toString();
        if (DEPRECATED_ALGORITHMS.contains(string.toUpperCase(Locale.US))) {
            if (VMRuntime.getRuntime().getTargetSdkVersion() <= maximumAllowableApiLevelForBcDeprecation) {
                System.logE(" ******** DEPRECATED FUNCTIONALITY ********");
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(" * The implementation of the ");
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(" algorithm from");
                System.logE(((StringBuilder)charSequence).toString());
                System.logE(" * the BC provider is deprecated in this version of Android.");
                System.logE(" * It will be removed in a future version of Android and your");
                System.logE(" * application will no longer be able to request it.  Please see");
                System.logE(" * https://android-developers.googleblog.com/2018/03/cryptography-changes-in-android-p.html");
                System.logE(" * for more details.");
            } else {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("The BC provider no longer provides an implementation for ");
                ((StringBuilder)charSequence).append(string);
                ((StringBuilder)charSequence).append(".  Please see https://android-developers.googleblog.com/2018/03/cryptography-changes-in-android-p.html for more details.");
                throw new NoSuchAlgorithmException(((StringBuilder)charSequence).toString());
            }
        }
    }

    public static void checkBouncyCastleDeprecation(String string, String string2, String string3) throws NoSuchAlgorithmException {
        synchronized (Providers.class) {
            if ("BC".equals(string) && providerList.getProvider(string) == SYSTEM_BOUNCY_CASTLE_PROVIDER) {
                Providers.checkBouncyCastleDeprecation(string2, string3);
            }
            return;
        }
    }

    public static void checkBouncyCastleDeprecation(Provider provider, String string, String string2) throws NoSuchAlgorithmException {
        synchronized (Providers.class) {
            if (provider == SYSTEM_BOUNCY_CASTLE_PROVIDER) {
                Providers.checkBouncyCastleDeprecation(string, string2);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void endThreadProviderList(ProviderList providerList) {
        synchronized (Providers.class) {
            if (providerList == null) {
                if (ProviderList.debug != null) {
                    ProviderList.debug.println("Disabling ThreadLocal providers");
                }
                threadLists.remove();
            } else {
                if (ProviderList.debug != null) {
                    Debug debug = ProviderList.debug;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Restoring previous ThreadLocal providers: ");
                    stringBuilder.append(providerList);
                    debug.println(stringBuilder.toString());
                }
                threadLists.set(providerList);
            }
            --threadListsUsed;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ProviderList getFullProviderList() {
        ProviderList providerList;
        synchronized (Providers.class) {
            providerList = Providers.getThreadProviderList();
            if (providerList != null) {
                ProviderList providerList2 = providerList.removeInvalid();
                ProviderList providerList3 = providerList;
                if (providerList2 == providerList) return providerList3;
                Providers.changeThreadProviderList(providerList2);
                return providerList2;
            }
        }
        providerList = Providers.getSystemProviderList();
        ProviderList providerList4 = providerList.removeInvalid();
        ProviderList providerList5 = providerList;
        if (providerList4 == providerList) return providerList5;
        Providers.setSystemProviderList(providerList4);
        return providerList4;
    }

    public static int getMaximumAllowableApiLevelForBcDeprecation() {
        return maximumAllowableApiLevelForBcDeprecation;
    }

    public static ProviderList getProviderList() {
        ProviderList providerList;
        ProviderList providerList2 = providerList = Providers.getThreadProviderList();
        if (providerList == null) {
            providerList2 = Providers.getSystemProviderList();
        }
        return providerList2;
    }

    public static Provider getSunProvider() {
        try {
            Provider provider = (Provider)Class.forName(jarVerificationProviders[0]).newInstance();
            return provider;
        }
        catch (Exception exception) {
            try {
                Provider provider = (Provider)Class.forName(BACKUP_PROVIDER_CLASSNAME).newInstance();
                return provider;
            }
            catch (Exception exception2) {
                throw new RuntimeException("Sun provider not found", exception);
            }
        }
    }

    private static ProviderList getSystemProviderList() {
        return providerList;
    }

    public static ProviderList getThreadProviderList() {
        if (threadListsUsed == 0) {
            return null;
        }
        return threadLists.get();
    }

    public static void setMaximumAllowableApiLevelForBcDeprecation(int n) {
        maximumAllowableApiLevelForBcDeprecation = n;
    }

    public static void setProviderList(ProviderList providerList) {
        if (Providers.getThreadProviderList() == null) {
            Providers.setSystemProviderList(providerList);
        } else {
            Providers.changeThreadProviderList(providerList);
        }
    }

    private static void setSystemProviderList(ProviderList providerList) {
        Providers.providerList = providerList;
    }

    public static Object startJarVerification() {
        return Providers.beginThreadProviderList(Providers.getProviderList().getJarList(jarVerificationProviders));
    }

    public static void stopJarVerification(Object object) {
        Providers.endThreadProviderList((ProviderList)object);
    }
}

