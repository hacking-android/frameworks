/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers.prefixmapper;

import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.prefixmapper.MappingFileProvider;
import com.android.i18n.phonenumbers.prefixmapper.PhonePrefixMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PrefixFileReader {
    private static final Logger logger = Logger.getLogger(PrefixFileReader.class.getName());
    private Map<String, PhonePrefixMap> availablePhonePrefixMaps = new HashMap<String, PhonePrefixMap>();
    private MappingFileProvider mappingFileProvider = new MappingFileProvider();
    private final String phonePrefixDataDirectory;

    public PrefixFileReader(String string) {
        this.phonePrefixDataDirectory = string;
        this.loadMappingFileProvider();
    }

    private static void close(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            }
            catch (IOException iOException) {
                logger.log(Level.WARNING, iOException.toString());
            }
        }
    }

    private PhonePrefixMap getPhonePrefixDescriptions(int n, String string, String string2, String string3) {
        if ((string = this.mappingFileProvider.getFileName(n, string, string2, string3)).length() == 0) {
            return null;
        }
        if (!this.availablePhonePrefixMaps.containsKey(string)) {
            this.loadPhonePrefixMapFromFile(string);
        }
        return this.availablePhonePrefixMaps.get(string);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadMappingFileProvider() {
        var1_1 = new StringBuilder();
        var1_1.append(this.phonePrefixDataDirectory);
        var1_1.append("config");
        var2_3 = PrefixFileReader.class.getResourceAsStream(var1_1.toString());
        var3_4 = null;
        var5_7 = var4_5 = null;
        var1_1 = var3_4;
        var5_7 = var4_5;
        var1_1 = var3_4;
        var6_8 = new ObjectInputStream(var2_3);
        var5_7 = var4_5 = var6_8;
        var1_1 = var4_5;
        this.mappingFileProvider.readExternal((ObjectInput)var4_5);
        var1_1 = var4_5;
lbl18: // 2 sources:
        do {
            PrefixFileReader.close((InputStream)var1_1);
            return;
            break;
        } while (true);
        {
            catch (Throwable var1_2) {
            }
            catch (IOException var4_6) {}
            var5_7 = var1_1;
            {
                PrefixFileReader.logger.log(Level.WARNING, var4_6.toString());
                ** continue;
            }
        }
        PrefixFileReader.close((InputStream)var5_7);
        throw var1_2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void loadPhonePrefixMapFromFile(String string) {
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append(this.phonePrefixDataDirectory);
        ((StringBuilder)object2).append(string);
        InputStream inputStream = PrefixFileReader.class.getResourceAsStream(((StringBuilder)object2).toString());
        Object var4_6 = null;
        Serializable serializable = null;
        Object object = serializable;
        object2 = var4_6;
        try {
            try {
                object = serializable;
                object2 = var4_6;
                ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
                object = objectInputStream;
                object2 = objectInputStream;
                object = objectInputStream;
                object2 = objectInputStream;
                serializable = new PhonePrefixMap();
                object = objectInputStream;
                object2 = objectInputStream;
                ((PhonePrefixMap)serializable).readExternal(objectInputStream);
                object = objectInputStream;
                object2 = objectInputStream;
                this.availablePhonePrefixMaps.put(string, (PhonePrefixMap)serializable);
                object2 = objectInputStream;
            }
            catch (IOException iOException) {
                object = object2;
                logger.log(Level.WARNING, iOException.toString());
            }
        }
        catch (Throwable throwable2) {}
        PrefixFileReader.close((InputStream)object2);
        return;
        PrefixFileReader.close((InputStream)object);
        throw throwable2;
    }

    private boolean mayFallBackToEnglish(String string) {
        boolean bl = !string.equals("zh") && !string.equals("ja") && !string.equals("ko");
        return bl;
    }

    public String getDescriptionForNumber(Phonenumber.PhoneNumber object, String object2, String object3, String string) {
        String string2;
        block8 : {
            int n;
            block7 : {
                n = ((Phonenumber.PhoneNumber)object).getCountryCode();
                if (n == 1) {
                    n = (int)(((Phonenumber.PhoneNumber)object).getNationalNumber() / 10000000L) + 1000;
                }
                object3 = this.getPhonePrefixDescriptions(n, (String)object2, (String)object3, string);
                string = object3 != null ? ((PhonePrefixMap)object3).lookup((Phonenumber.PhoneNumber)object) : null;
                string2 = "";
                if (string == null) break block7;
                object3 = string;
                if (string.length() != 0) break block8;
            }
            object3 = string;
            if (this.mayFallBackToEnglish((String)object2)) {
                object2 = this.getPhonePrefixDescriptions(n, "en", "", "");
                if (object2 == null) {
                    return "";
                }
                object3 = ((PhonePrefixMap)object2).lookup((Phonenumber.PhoneNumber)object);
            }
        }
        object = string2;
        if (object3 != null) {
            object = object3;
        }
        return object;
    }
}

