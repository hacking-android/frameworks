/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.bouncycastle.jce.provider;

import com.android.org.bouncycastle.crypto.digests.AndroidDigestFactory;
import com.android.org.bouncycastle.util.encoders.Hex;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CertBlacklist {
    private static final Logger logger = Logger.getLogger(CertBlacklist.class.getName());
    public final Set<byte[]> pubkeyBlacklist;
    public final Set<BigInteger> serialBlacklist;

    public CertBlacklist() {
        CharSequence charSequence = System.getenv("ANDROID_DATA");
        CharSequence charSequence2 = new StringBuilder();
        charSequence2.append((String)charSequence);
        charSequence2.append("/misc/keychain/");
        charSequence2 = charSequence2.toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)charSequence2);
        ((StringBuilder)charSequence).append("pubkey_blacklist.txt");
        charSequence = ((StringBuilder)charSequence).toString();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence2);
        stringBuilder.append("serial_blacklist.txt");
        charSequence2 = stringBuilder.toString();
        this.pubkeyBlacklist = CertBlacklist.readPublicKeyBlackList((String)charSequence);
        this.serialBlacklist = CertBlacklist.readSerialBlackList((String)charSequence2);
    }

    public CertBlacklist(String string, String string2) {
        this.pubkeyBlacklist = CertBlacklist.readPublicKeyBlackList(string);
        this.serialBlacklist = CertBlacklist.readSerialBlackList(string2);
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

    private static boolean isHex(String string) {
        try {
            new BigInteger(string, 16);
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            Logger logger = CertBlacklist.logger;
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
            Logger logger = CertBlacklist.logger;
            Level level = Level.WARNING;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid pubkey hash length: ");
            stringBuilder.append(string.length());
            logger.log(level, stringBuilder.toString());
            return false;
        }
        return CertBlacklist.isHex(string);
    }

    private static String readBlacklist(String string) {
        try {
            string = CertBlacklist.readFileAsString(string);
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

    private static ByteArrayOutputStream readFileAsBytes(String object) throws IOException {
        Object object2;
        int n;
        Object object3 = object2 = null;
        object3 = object2;
        byte[] arrby = new RandomAccessFile((String)object, "r");
        object3 = object = arrby;
        object3 = object;
        object2 = new ByteArrayOutputStream((int)((RandomAccessFile)object).length());
        object3 = object;
        try {
            arrby = new byte[8192];
        }
        catch (Throwable throwable) {
            CertBlacklist.closeQuietly(object3);
            throw throwable;
        }
        do {
            object3 = object;
            n = ((RandomAccessFile)object).read(arrby);
            if (n != -1) break block9;
            break;
        } while (true);
        {
            block9 : {
                CertBlacklist.closeQuietly((Closeable)object);
                return object2;
            }
            object3 = object;
            ((ByteArrayOutputStream)object2).write(arrby, 0, n);
            continue;
        }
    }

    private static String readFileAsString(String string) throws IOException {
        return CertBlacklist.readFileAsBytes(string).toString("UTF-8");
    }

    private static final Set<byte[]> readPublicKeyBlackList(String charSequence) {
        Object object = "410f36363258f30b347d12ce4863e433437806a8".getBytes();
        object = new HashSet(Arrays.asList(object, "ba3e7bd38cd7e1e6b9cd4c219962e59d7a2f4e37".getBytes(), "e23b8d105f87710a68d9248050ebefc627be4ca6".getBytes(), "7b2e16bc39bcd72b456e9f055d1de615b74945db".getBytes(), "e8f91200c65cee16e039b9f883841661635f81c5".getBytes(), "0129bcd5b448ae8d2496d1c3e19723919088e152".getBytes(), "5f3ab33d55007054bc5e3e5553cd8d8465d77c61".getBytes(), "783333c9687df63377efceddd82efa9101913e8e".getBytes(), "3ecf4bbbe46096d514bb539bb913d77aa4ef31bf".getBytes()));
        if (!((String)(charSequence = CertBlacklist.readBlacklist((String)charSequence))).equals("")) {
            String[] arrstring = ((String)charSequence).split(",");
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                String string = arrstring[i].trim();
                if (CertBlacklist.isPubkeyHash(string)) {
                    object.add(string.getBytes());
                    continue;
                }
                Logger logger = CertBlacklist.logger;
                Level level = Level.WARNING;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Tried to blacklist invalid pubkey ");
                ((StringBuilder)charSequence).append(string);
                logger.log(level, ((StringBuilder)charSequence).toString());
            }
        }
        return object;
    }

    private static final Set<BigInteger> readSerialBlackList(String arrstring) {
        Serializable serializable = new BigInteger("077a59bcd53459601ca6907267a6dd1c", 16);
        serializable = new HashSet<BigInteger>(Arrays.asList(serializable, new BigInteger("047ecbe9fca55f7bd09eae36e10cae1e", 16), new BigInteger("d8f35f4eb7872b2dab0692e315382fb0", 16), new BigInteger("b0b7133ed096f9b56fae91c874bd3ac0", 16), new BigInteger("9239d5348f40d1695a745470e1f23f43", 16), new BigInteger("e9028b9578e415dc1a710a2b88154447", 16), new BigInteger("d7558fdaf5f1105bb213282b707729a3", 16), new BigInteger("f5c86af36162f13a64f54f6dc9587c06", 16), new BigInteger("392a434f0e07df1f8aa305de34e0c229", 16), new BigInteger("3e75ced46b693021218830ae86a82a71", 16)));
        if (!(arrstring = CertBlacklist.readBlacklist((String)arrstring)).equals("")) {
            for (String string : arrstring.split(",")) {
                try {
                    BigInteger bigInteger = new BigInteger(string, 16);
                    serializable.add(bigInteger);
                }
                catch (NumberFormatException numberFormatException) {
                    Logger logger = CertBlacklist.logger;
                    Level level = Level.WARNING;
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Tried to blacklist invalid serial number ");
                    stringBuilder.append(string);
                    logger.log(level, stringBuilder.toString(), numberFormatException);
                }
            }
        }
        return Collections.unmodifiableSet(serializable);
    }

    public boolean isPublicKeyBlackListed(PublicKey iterator) {
        byte[] arrby = iterator.getEncoded();
        iterator = AndroidDigestFactory.getSHA1();
        iterator.update(arrby, 0, arrby.length);
        arrby = new byte[iterator.getDigestSize()];
        iterator.doFinal(arrby, 0);
        iterator = this.pubkeyBlacklist.iterator();
        while (iterator.hasNext()) {
            if (!Arrays.equals(iterator.next(), Hex.encode(arrby))) continue;
            return true;
        }
        return false;
    }

    public boolean isSerialNumberBlackListed(BigInteger bigInteger) {
        return this.serialBlacklist.contains(bigInteger);
    }
}

