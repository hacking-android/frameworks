/*
 * Decompiled with CFR 0.145.
 */
package com.android.org.conscrypt.ct;

import com.android.org.conscrypt.InternalUtil;
import com.android.org.conscrypt.ct.CTLogInfo;
import com.android.org.conscrypt.ct.CTLogStore;
import com.android.org.conscrypt.ct.KnownLogs;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class CTLogStoreImpl
implements CTLogStore {
    private static final char[] HEX_DIGITS;
    private static final Charset US_ASCII;
    private static volatile CTLogInfo[] defaultFallbackLogs;
    private static final File defaultSystemLogDir;
    private static final File defaultUserLogDir;
    private CTLogInfo[] fallbackLogs;
    private HashMap<ByteBuffer, CTLogInfo> logCache = new HashMap();
    private Set<ByteBuffer> missingLogCache = Collections.synchronizedSet(new HashSet());
    private File systemLogDir;
    private File userLogDir;

    static {
        US_ASCII = Charset.forName("US-ASCII");
        defaultFallbackLogs = null;
        CharSequence charSequence = System.getenv("ANDROID_DATA");
        String string = System.getenv("ANDROID_ROOT");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((String)charSequence);
        stringBuilder.append("/misc/keychain/trusted_ct_logs/current/");
        defaultUserLogDir = new File(stringBuilder.toString());
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append("/etc/security/ct_known_logs/");
        defaultSystemLogDir = new File(((StringBuilder)charSequence).toString());
        HEX_DIGITS = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    }

    public CTLogStoreImpl() {
        this(defaultUserLogDir, defaultSystemLogDir, CTLogStoreImpl.getDefaultFallbackLogs());
    }

    public CTLogStoreImpl(File file, File file2, CTLogInfo[] arrcTLogInfo) {
        this.userLogDir = file;
        this.systemLogDir = file2;
        this.fallbackLogs = arrcTLogInfo;
    }

    private static CTLogInfo[] createDefaultFallbackLogs() {
        CTLogInfo[] arrcTLogInfo = new CTLogInfo[8];
        for (int i = 0; i < 8; ++i) {
            try {
                arrcTLogInfo[i] = new CTLogInfo(InternalUtil.logKeyToPublicKey(KnownLogs.LOG_KEYS[i]), KnownLogs.LOG_DESCRIPTIONS[i], KnownLogs.LOG_URLS[i]);
                continue;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new RuntimeException(noSuchAlgorithmException);
            }
        }
        defaultFallbackLogs = arrcTLogInfo;
        return arrcTLogInfo;
    }

    private CTLogInfo findKnownLog(byte[] arrby) {
        Object object = CTLogStoreImpl.hexEncode(arrby);
        try {
            Object object2 = new File(this.userLogDir, (String)object);
            object2 = CTLogStoreImpl.loadLog((File)object2);
            return object2;
        }
        catch (FileNotFoundException fileNotFoundException) {
            try {
                File file = new File(this.systemLogDir, (String)object);
                object = CTLogStoreImpl.loadLog(file);
                return object;
            }
            catch (FileNotFoundException fileNotFoundException2) {
                if (!this.userLogDir.exists()) {
                    for (CTLogInfo cTLogInfo : this.fallbackLogs) {
                        if (!Arrays.equals(arrby, cTLogInfo.getID())) continue;
                        return cTLogInfo;
                    }
                }
                return null;
            }
            catch (InvalidLogFileException invalidLogFileException) {
                return null;
            }
        }
        catch (InvalidLogFileException invalidLogFileException) {
            return null;
        }
    }

    public static CTLogInfo[] getDefaultFallbackLogs() {
        CTLogInfo[] arrcTLogInfo;
        CTLogInfo[] arrcTLogInfo2 = arrcTLogInfo = defaultFallbackLogs;
        if (arrcTLogInfo == null) {
            arrcTLogInfo2 = arrcTLogInfo = CTLogStoreImpl.createDefaultFallbackLogs();
            defaultFallbackLogs = arrcTLogInfo;
        }
        return arrcTLogInfo2;
    }

    private static String hexEncode(byte[] arrby) {
        StringBuilder stringBuilder = new StringBuilder(arrby.length * 2);
        for (byte by : arrby) {
            stringBuilder.append(HEX_DIGITS[by >> 4 & 15]);
            stringBuilder.append(HEX_DIGITS[by & 15]);
        }
        return stringBuilder.toString();
    }

    public static CTLogInfo loadLog(File file) throws FileNotFoundException, InvalidLogFileException {
        return CTLogStoreImpl.loadLog(new FileInputStream(file));
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static CTLogInfo loadLog(InputStream object) throws InvalidLogFileException {
        block13 : {
            Closeable closeable = new Scanner((InputStream)object, "UTF-8");
            closeable.useDelimiter("\n");
            Object object2 = null;
            Object object3 = null;
            Object object4 = null;
            try {
                boolean bl = closeable.hasNext();
                if (!bl) {
                    closeable.close();
                    return null;
                }
                while (closeable.hasNext()) {
                    int n;
                    block17 : {
                        block16 : {
                            Object object5;
                            block14 : {
                                block15 : {
                                    object = closeable.next().split(":", 2);
                                    if (((Object)object).length < 2) continue;
                                    n = 0;
                                    object5 = object[0];
                                    object = object[1];
                                    int n2 = ((String)object5).hashCode();
                                    if (n2 == -1724546052) break block14;
                                    if (n2 == 106079) break block15;
                                    if (n2 != 116079 || !((String)object5).equals("url")) break block16;
                                    n = 1;
                                    break block17;
                                }
                                if (!((String)object5).equals("key")) break block16;
                                n = 2;
                                break block17;
                            }
                            bl = ((String)object5).equals("description");
                            if (bl) break block17;
                        }
                        n = -1;
                    }
                    if (n != 0) {
                        if (n != 1) {
                            if (n != 2) continue;
                            object4 = object;
                            continue;
                        }
                        object3 = object;
                        continue;
                    }
                    object2 = object;
                }
                closeable.close();
                if (object2 == null || object3 == null || object4 == null) break block13;
            }
            catch (Throwable throwable) {
                closeable.close();
                throw throwable;
            }
            try {
                object = new StringBuilder();
                ((StringBuilder)object).append("-----BEGIN PUBLIC KEY-----\n");
                ((StringBuilder)object).append((String)object4);
                ((StringBuilder)object).append("\n-----END PUBLIC KEY-----");
                closeable = new ByteArrayInputStream(((StringBuilder)object).toString().getBytes(US_ASCII));
                object = InternalUtil.readPublicKeyPem((InputStream)closeable);
                return new CTLogInfo((PublicKey)object, (String)object2, (String)object3);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                throw new InvalidLogFileException(noSuchAlgorithmException);
            }
            catch (InvalidKeyException invalidKeyException) {
                throw new InvalidLogFileException(invalidKeyException);
            }
        }
        throw new InvalidLogFileException("Missing one of 'description', 'url' or 'key'");
    }

    @Override
    public CTLogInfo getKnownLog(byte[] object) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(object);
        CTLogInfo cTLogInfo = this.logCache.get(byteBuffer);
        if (cTLogInfo != null) {
            return cTLogInfo;
        }
        if (this.missingLogCache.contains(byteBuffer)) {
            return null;
        }
        if ((object = this.findKnownLog((byte[])object)) != null) {
            this.logCache.put(byteBuffer, (CTLogInfo)object);
        } else {
            this.missingLogCache.add(byteBuffer);
        }
        return object;
    }

    public static class InvalidLogFileException
    extends Exception {
        public InvalidLogFileException() {
        }

        public InvalidLogFileException(String string) {
            super(string);
        }

        public InvalidLogFileException(String string, Throwable throwable) {
            super(string, throwable);
        }

        public InvalidLogFileException(Throwable throwable) {
            super(throwable);
        }
    }

}

