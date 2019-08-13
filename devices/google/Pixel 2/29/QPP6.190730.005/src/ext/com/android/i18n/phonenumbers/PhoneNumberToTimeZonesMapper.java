/*
 * Decompiled with CFR 0.145.
 */
package com.android.i18n.phonenumbers;

import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import com.android.i18n.phonenumbers.prefixmapper.PrefixTimeZonesMap;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PhoneNumberToTimeZonesMapper {
    private static final String MAPPING_DATA_DIRECTORY = "/com/android/i18n/phonenumbers/timezones/data/";
    private static final String MAPPING_DATA_FILE_NAME = "map_data";
    private static final String UNKNOWN_TIMEZONE = "Etc/Unknown";
    static final List<String> UNKNOWN_TIME_ZONE_LIST = new ArrayList<String>(1);
    private static final Logger logger;
    private PrefixTimeZonesMap prefixTimeZonesMap = null;

    static {
        UNKNOWN_TIME_ZONE_LIST.add(UNKNOWN_TIMEZONE);
        logger = Logger.getLogger(PhoneNumberToTimeZonesMapper.class.getName());
    }

    private PhoneNumberToTimeZonesMapper(PrefixTimeZonesMap prefixTimeZonesMap) {
        this.prefixTimeZonesMap = prefixTimeZonesMap;
    }

    PhoneNumberToTimeZonesMapper(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(MAPPING_DATA_FILE_NAME);
        this.prefixTimeZonesMap = PhoneNumberToTimeZonesMapper.loadPrefixTimeZonesMapFromFile(stringBuilder.toString());
    }

    static /* synthetic */ PrefixTimeZonesMap access$000(String string) {
        return PhoneNumberToTimeZonesMapper.loadPrefixTimeZonesMapFromFile(string);
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

    private List<String> getCountryLevelTimeZonesforNumber(Phonenumber.PhoneNumber list) {
        block0 : {
            if (!(list = this.prefixTimeZonesMap.lookupCountryLevelTimeZonesForNumber((Phonenumber.PhoneNumber)((Object)list))).isEmpty()) break block0;
            list = UNKNOWN_TIME_ZONE_LIST;
        }
        return Collections.unmodifiableList(list);
    }

    public static PhoneNumberToTimeZonesMapper getInstance() {
        synchronized (PhoneNumberToTimeZonesMapper.class) {
            PhoneNumberToTimeZonesMapper phoneNumberToTimeZonesMapper = LazyHolder.INSTANCE;
            return phoneNumberToTimeZonesMapper;
        }
    }

    private List<String> getTimeZonesForGeocodableNumber(Phonenumber.PhoneNumber list) {
        block0 : {
            if (!(list = this.prefixTimeZonesMap.lookupTimeZonesForNumber((Phonenumber.PhoneNumber)((Object)list))).isEmpty()) break block0;
            list = UNKNOWN_TIME_ZONE_LIST;
        }
        return Collections.unmodifiableList(list);
    }

    public static String getUnknownTimeZone() {
        return UNKNOWN_TIMEZONE;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static PrefixTimeZonesMap loadPrefixTimeZonesMapFromFile(String var0) {
        var1_2 = PhoneNumberToTimeZonesMapper.class.getResourceAsStream((String)var0);
        var2_3 = null;
        var3_4 = null;
        var4_6 = new PrefixTimeZonesMap();
        var5_7 = var3_4;
        var0 = var2_3;
        var5_7 = var3_4;
        var0 = var2_3;
        var6_8 = new ObjectInputStream(var1_2);
        var5_7 = var3_4 = var6_8;
        var0 = var3_4;
        var4_6.readExternal((ObjectInput)var3_4);
        var0 = var3_4;
lbl15: // 2 sources:
        do {
            PhoneNumberToTimeZonesMapper.close((InputStream)var0);
            return var4_6;
            break;
        } while (true);
        {
            catch (Throwable var0_1) {
            }
            catch (IOException var3_5) {}
            var5_7 = var0;
            {
                PhoneNumberToTimeZonesMapper.logger.log(Level.WARNING, var3_5.toString());
                ** continue;
            }
        }
        PhoneNumberToTimeZonesMapper.close((InputStream)var5_7);
        throw var0_1;
    }

    public List<String> getTimeZonesForGeographicalNumber(Phonenumber.PhoneNumber phoneNumber) {
        return this.getTimeZonesForGeocodableNumber(phoneNumber);
    }

    public List<String> getTimeZonesForNumber(Phonenumber.PhoneNumber phoneNumber) {
        PhoneNumberUtil.PhoneNumberType phoneNumberType = PhoneNumberUtil.getInstance().getNumberType(phoneNumber);
        if (phoneNumberType == PhoneNumberUtil.PhoneNumberType.UNKNOWN) {
            return UNKNOWN_TIME_ZONE_LIST;
        }
        if (!PhoneNumberUtil.getInstance().isNumberGeographical(phoneNumberType, phoneNumber.getCountryCode())) {
            return this.getCountryLevelTimeZonesforNumber(phoneNumber);
        }
        return this.getTimeZonesForGeographicalNumber(phoneNumber);
    }

    private static class LazyHolder {
        private static final PhoneNumberToTimeZonesMapper INSTANCE = new PhoneNumberToTimeZonesMapper(PhoneNumberToTimeZonesMapper.access$000("/com/android/i18n/phonenumbers/timezones/data/map_data"));

        private LazyHolder() {
        }
    }

}

