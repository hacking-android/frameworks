/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.security;

import android.os.Environment;
import android.os.FileUtils;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import libcore.io.IoUtils;

public class SystemKeyStore {
    private static final String KEY_FILE_EXTENSION = ".sks";
    private static final String SYSTEM_KEYSTORE_DIRECTORY = "misc/systemkeys";
    private static SystemKeyStore mInstance = new SystemKeyStore();

    private SystemKeyStore() {
    }

    public static SystemKeyStore getInstance() {
        return mInstance;
    }

    private File getKeyFile(String string2) {
        File file = new File(Environment.getDataDirectory(), SYSTEM_KEYSTORE_DIRECTORY);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(KEY_FILE_EXTENSION);
        return new File(file, stringBuilder.toString());
    }

    public static String toHexString(byte[] arrby) {
        if (arrby == null) {
            return null;
        }
        int n = arrby.length;
        StringBuilder stringBuilder = new StringBuilder(arrby.length * 2);
        for (n = 0; n < arrby.length; ++n) {
            String string2 = Integer.toString(arrby[n] & 255, 16);
            CharSequence charSequence = string2;
            if (string2.length() == 1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("0");
                ((StringBuilder)charSequence).append(string2);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            stringBuilder.append((String)charSequence);
        }
        return stringBuilder.toString();
    }

    public void deleteKey(String object) {
        if (((File)(object = this.getKeyFile((String)object))).exists()) {
            ((File)object).delete();
            return;
        }
        throw new IllegalArgumentException();
    }

    public byte[] generateNewKey(int n, String object, String object2) throws NoSuchAlgorithmException {
        if (!((File)(object2 = this.getKeyFile((String)object2))).exists()) {
            block4 : {
                object = KeyGenerator.getInstance((String)object);
                object.init(n, SecureRandom.getInstance("SHA1PRNG"));
                object = object.generateKey().getEncoded();
                try {
                    if (!((File)object2).createNewFile()) break block4;
                    FileOutputStream fileOutputStream = new FileOutputStream((File)object2);
                    fileOutputStream.write((byte[])object);
                    fileOutputStream.flush();
                    FileUtils.sync(fileOutputStream);
                    fileOutputStream.close();
                    FileUtils.setPermissions(((File)object2).getName(), 384, -1, -1);
                    return object;
                }
                catch (IOException iOException) {
                    return null;
                }
            }
            object = new IllegalArgumentException();
            throw object;
        }
        throw new IllegalArgumentException();
    }

    public String generateNewKeyHexString(int n, String string2, String string3) throws NoSuchAlgorithmException {
        return SystemKeyStore.toHexString(this.generateNewKey(n, string2, string3));
    }

    public byte[] retrieveKey(String object) throws IOException {
        if (!((File)(object = this.getKeyFile((String)object))).exists()) {
            return null;
        }
        return IoUtils.readFileAsByteArray((String)((File)object).toString());
    }

    public String retrieveKeyHexString(String string2) throws IOException {
        return SystemKeyStore.toHexString(this.retrieveKey(string2));
    }
}

