/*
 * Decompiled with CFR 0.145.
 */
package java.security;

import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.SecureRandomSpi;
import java.security.Security;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import sun.security.jca.GetInstance;
import sun.security.jca.Providers;

public class SecureRandom
extends Random {
    private static volatile SecureRandom seedGenerator = null;
    static final long serialVersionUID = 4940670005562187L;
    private String algorithm;
    private long counter;
    private MessageDigest digest = null;
    private Provider provider = null;
    private byte[] randomBytes;
    private int randomBytesUsed;
    private SecureRandomSpi secureRandomSpi = null;
    private byte[] state;

    public SecureRandom() {
        super(0L);
        this.getDefaultPRNG(false, null);
    }

    protected SecureRandom(SecureRandomSpi secureRandomSpi, Provider provider) {
        this(secureRandomSpi, provider, null);
    }

    private SecureRandom(SecureRandomSpi secureRandomSpi, Provider provider, String string) {
        super(0L);
        this.secureRandomSpi = secureRandomSpi;
        this.provider = provider;
        this.algorithm = string;
    }

    public SecureRandom(byte[] arrby) {
        super(0L);
        this.getDefaultPRNG(true, arrby);
    }

    private void getDefaultPRNG(boolean bl, byte[] arrby) {
        String string = SecureRandom.getPrngAlgorithm();
        if (string != null) {
            block5 : {
                try {
                    SecureRandom secureRandom = SecureRandom.getInstance(string);
                    this.secureRandomSpi = secureRandom.getSecureRandomSpi();
                    this.provider = secureRandom.getProvider();
                    if (!bl) break block5;
                }
                catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                    throw new RuntimeException(noSuchAlgorithmException);
                }
                this.secureRandomSpi.engineSetSeed(arrby);
            }
            if (this.getClass() == SecureRandom.class) {
                this.algorithm = string;
            }
            return;
        }
        throw new IllegalStateException("No SecureRandom implementation!");
    }

    public static SecureRandom getInstance(String string) throws NoSuchAlgorithmException {
        GetInstance.Instance instance = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, string);
        return new SecureRandom((SecureRandomSpi)instance.impl, instance.provider, string);
    }

    public static SecureRandom getInstance(String string, String object) throws NoSuchAlgorithmException, NoSuchProviderException {
        object = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, string, (String)object);
        return new SecureRandom((SecureRandomSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    public static SecureRandom getInstance(String string, Provider object) throws NoSuchAlgorithmException {
        object = GetInstance.getInstance("SecureRandom", SecureRandomSpi.class, string, (Provider)object);
        return new SecureRandom((SecureRandomSpi)((GetInstance.Instance)object).impl, ((GetInstance.Instance)object).provider, string);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static SecureRandom getInstanceStrong() throws NoSuchAlgorithmException {
        String string = AccessController.doPrivileged(new PrivilegedAction<String>(){

            @Override
            public String run() {
                return Security.getProperty("securerandom.strongAlgorithms");
            }
        });
        if (string == null) throw new NoSuchAlgorithmException("Null/empty securerandom.strongAlgorithms Security Property");
        if (string.length() == 0) throw new NoSuchAlgorithmException("Null/empty securerandom.strongAlgorithms Security Property");
        Object object = string;
        while (object != null) {
            object = StrongPatternHolder.pattern.matcher((CharSequence)object);
            if (((Matcher)object).matches()) {
                String string2 = ((Matcher)object).group(1);
                String string3 = ((Matcher)object).group(3);
                if (string3 != null) return SecureRandom.getInstance(string2, string3);
                try {
                    return SecureRandom.getInstance(string2);
                }
                catch (NoSuchAlgorithmException | NoSuchProviderException generalSecurityException) {
                    object = ((Matcher)object).group(5);
                    continue;
                }
            }
            object = null;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No strong SecureRandom impls available: ");
        ((StringBuilder)object).append(string);
        throw new NoSuchAlgorithmException(((StringBuilder)object).toString());
    }

    private static String getPrngAlgorithm() {
        Iterator<Provider> iterator = Providers.getProviderList().providers().iterator();
        while (iterator.hasNext()) {
            for (Provider.Service service : iterator.next().getServices()) {
                if (!service.getType().equals("SecureRandom")) continue;
                return service.getAlgorithm();
            }
        }
        return null;
    }

    public static byte[] getSeed(int n) {
        if (seedGenerator == null) {
            seedGenerator = new SecureRandom();
        }
        return seedGenerator.generateSeed(n);
    }

    private static byte[] longToByteArray(long l) {
        byte[] arrby = new byte[8];
        for (int i = 0; i < 8; ++i) {
            arrby[i] = (byte)l;
            l >>= 8;
        }
        return arrby;
    }

    public byte[] generateSeed(int n) {
        return this.secureRandomSpi.engineGenerateSeed(n);
    }

    public String getAlgorithm() {
        String string = this.algorithm;
        if (string == null) {
            string = "unknown";
        }
        return string;
    }

    public final Provider getProvider() {
        return this.provider;
    }

    SecureRandomSpi getSecureRandomSpi() {
        return this.secureRandomSpi;
    }

    @Override
    protected final int next(int n) {
        int n2 = (n + 7) / 8;
        byte[] arrby = new byte[n2];
        int n3 = 0;
        this.nextBytes(arrby);
        for (int i = 0; i < n2; ++i) {
            n3 = (n3 << 8) + (arrby[i] & 255);
        }
        return n3 >>> n2 * 8 - n;
    }

    @Override
    public void nextBytes(byte[] arrby) {
        synchronized (this) {
            this.secureRandomSpi.engineNextBytes(arrby);
            return;
        }
    }

    @Override
    public void setSeed(long l) {
        if (l != 0L) {
            this.secureRandomSpi.engineSetSeed(SecureRandom.longToByteArray(l));
        }
    }

    public void setSeed(byte[] arrby) {
        synchronized (this) {
            this.secureRandomSpi.engineSetSeed(arrby);
            return;
        }
    }

    private static final class StrongPatternHolder {
        private static Pattern pattern = Pattern.compile("\\s*([\\S&&[^:,]]*)(\\:([\\S&&[^,]]*))?\\s*(\\,(.*))?");

        private StrongPatternHolder() {
        }
    }

}

