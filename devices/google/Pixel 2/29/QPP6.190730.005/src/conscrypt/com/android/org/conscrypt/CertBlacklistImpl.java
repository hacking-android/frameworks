/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt;

import com.android.org.conscrypt.CertBlacklist;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class CertBlacklistImpl
implements CertBlacklist {
    private static final byte[] HEX_TABLE;
    private static final Logger logger;
    private final Set<byte[]> pubkeyBlacklist;
    private final Set<BigInteger> serialBlacklist;

    static {
        logger = Logger.getLogger(CertBlacklistImpl.class.getName());
        HEX_TABLE = new byte[]{48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
    }

    public CertBlacklistImpl(Set<BigInteger> set, Set<byte[]> set2) {
        this.serialBlacklist = set;
        this.pubkeyBlacklist = set2;
    }

    private static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            }
            catch (Exception exception) {
            }
            catch (RuntimeException runtimeException) {
                throw runtimeException;
            }
        }
    }

    public static CertBlacklist getDefault() {
        String string = System.getenv("ANDROID_DATA");
        Object object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("/misc/keychain/");
        string = ((StringBuilder)object).toString();
        object = new StringBuilder();
        ((StringBuilder)object).append(string);
        ((StringBuilder)object).append("pubkey_blacklist.txt");
        object = ((StringBuilder)object).toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append("serial_blacklist.txt");
        string = stringBuilder.toString();
        object = CertBlacklistImpl.readPublicKeyBlackList((String)object);
        return new CertBlacklistImpl(CertBlacklistImpl.readSerialBlackList(string), (Set<byte[]>)object);
    }

    private static boolean isHex(String string) {
        try {
            new BigInteger(string, 16);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            Logger logger = CertBlacklistImpl.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not parse hex value ");
            stringBuilder.append(string);
            logger.log(level, stringBuilder.toString(), numberFormatException);
            return false;
        }
    }

    private static boolean isPubkeyHash(String string) {
        if (string.length() != 40) {
            Logger logger = CertBlacklistImpl.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid pubkey hash length: ");
            stringBuilder.append(string.length());
            logger.log(level, stringBuilder.toString());
            return false;
        }
        return CertBlacklistImpl.isHex(string);
    }

    private static String readBlacklist(String string) {
        try {
            string = CertBlacklistImpl.readFileAsString(string);
            return string;
        }
        catch (IOException iOException) {
            logger.log(Level.WARNING, "Could not read blacklist", iOException);
        }
        catch (FileNotFoundException fileNotFoundException) {
            // empty catch block
        }
        return "";
    }

    private static ByteArrayOutputStream readFileAsBytes(String arrby) throws IOException {
        byte[] arrby2;
        int n;
        byte[] arrby3 = arrby2 = null;
        arrby3 = arrby2;
        Closeable closeable = new RandomAccessFile((String)arrby, "r");
        arrby3 = arrby = closeable;
        arrby3 = arrby;
        closeable = new ByteArrayOutputStream((int)arrby.length());
        arrby3 = arrby;
        try {
            arrby2 = new byte[8192];
        }
        catch (Throwable throwable) {
            CertBlacklistImpl.closeQuietly(arrby3);
            throw throwable;
        }
        do {
            arrby3 = arrby;
            n = arrby.read(arrby2);
            if (n != -1) break block9;
            break;
        } while (true);
        {
            block9 : {
                CertBlacklistImpl.closeQuietly((Closeable)arrby);
                return closeable;
            }
            arrby3 = arrby;
            ((ByteArrayOutputStream)closeable).write(arrby2, 0, n);
            continue;
        }
    }

    private static String readFileAsString(String string) throws IOException {
        return CertBlacklistImpl.readFileAsBytes(string).toString("UTF-8");
    }

    private static Set<byte[]> readPublicKeyBlackList(String object) {
        Object object2 = "bae78e6bed65a2bf60ddedde7fd91e825865e93d".getBytes(StandardCharsets.UTF_8);
        object2 = new HashSet(Arrays.asList(object2, "410f36363258f30b347d12ce4863e433437806a8".getBytes(StandardCharsets.UTF_8), "ba3e7bd38cd7e1e6b9cd4c219962e59d7a2f4e37".getBytes(StandardCharsets.UTF_8), "e23b8d105f87710a68d9248050ebefc627be4ca6".getBytes(StandardCharsets.UTF_8), "7b2e16bc39bcd72b456e9f055d1de615b74945db".getBytes(StandardCharsets.UTF_8), "e8f91200c65cee16e039b9f883841661635f81c5".getBytes(StandardCharsets.UTF_8), "0129bcd5b448ae8d2496d1c3e19723919088e152".getBytes(StandardCharsets.UTF_8), "5f3ab33d55007054bc5e3e5553cd8d8465d77c61".getBytes(StandardCharsets.UTF_8), "783333c9687df63377efceddd82efa9101913e8e".getBytes(StandardCharsets.UTF_8), "3ecf4bbbe46096d514bb539bb913d77aa4ef31bf".getBytes(StandardCharsets.UTF_8)));
        if (!((String)(object = CertBlacklistImpl.readBlacklist((String)object))).equals("")) {
            String[] arrstring = ((String)object).split(",");
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                String string = arrstring[i].trim();
                if (CertBlacklistImpl.isPubkeyHash(string)) {
                    object2.add(string.getBytes(StandardCharsets.UTF_8));
                    continue;
                }
                object = logger;
                Level level = Level.WARNING;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Tried to blacklist invalid pubkey ");
                stringBuilder.append(string);
                ((Logger)object).log(level, stringBuilder.toString());
            }
        }
        return object2;
    }

    private static Set<BigInteger> readSerialBlackList(String arrstring) {
        Serializable serializable = new BigInteger("077a59bcd53459601ca6907267a6dd1c", 16);
        serializable = new HashSet<BigInteger>(Arrays.asList(serializable, new BigInteger("047ecbe9fca55f7bd09eae36e10cae1e", 16), new BigInteger("d8f35f4eb7872b2dab0692e315382fb0", 16), new BigInteger("b0b7133ed096f9b56fae91c874bd3ac0", 16), new BigInteger("9239d5348f40d1695a745470e1f23f43", 16), new BigInteger("e9028b9578e415dc1a710a2b88154447", 16), new BigInteger("d7558fdaf5f1105bb213282b707729a3", 16), new BigInteger("f5c86af36162f13a64f54f6dc9587c06", 16), new BigInteger("392a434f0e07df1f8aa305de34e0c229", 16), new BigInteger("3e75ced46b693021218830ae86a82a71", 16)));
        if (!(arrstring = CertBlacklistImpl.readBlacklist((String)arrstring)).equals("")) {
            for (String string : arrstring.split(",")) {
                Object object;
                try {
                    object = new BigInteger(string, 16);
                    serializable.add(object);
                }
                catch (NumberFormatException numberFormatException) {
                    object = logger;
                    Level level = Level.WARNING;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Tried to blacklist invalid serial number ");
                    stringBuilder.append(string);
                    ((Logger)object).log(level, stringBuilder.toString(), numberFormatException);
                }
            }
        }
        return Collections.unmodifiableSet(serializable);
    }

    private static byte[] toHex(byte[] arrby) {
        byte[] arrby2 = new byte[arrby.length * 2];
        int n = 0;
        for (int i = 0; i < arrby.length; ++i) {
            int n2 = arrby[i] & 255;
            int n3 = n + 1;
            byte[] arrby3 = HEX_TABLE;
            arrby2[n] = arrby3[n2 >> 4];
            n = n3 + 1;
            arrby2[n3] = arrby3[n2 & 15];
        }
        return arrby2;
    }

    @Override
    public boolean isPublicKeyBlackListed(PublicKey iterator) {
        byte[] arrby = iterator.getEncoded();
        try {
            iterator = MessageDigest.getInstance("SHA1");
        }
        catch (GeneralSecurityException generalSecurityException) {
            logger.log(Level.SEVERE, "Unable to get SHA1 MessageDigest", generalSecurityException);
            return false;
        }
        arrby = CertBlacklistImpl.toHex(((MessageDigest)((Object)iterator)).digest(arrby));
        iterator = this.pubkeyBlacklist.iterator();
        while (iterator.hasNext()) {
            if (!Arrays.equals(iterator.next(), arrby)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean isSerialNumberBlackListed(BigInteger bigInteger) {
        return this.serialBlacklist.contains(bigInteger);
    }
}

