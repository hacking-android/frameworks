/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.SQLException
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Build
 *  android.os.PersistableBundle
 *  android.telephony.CarrierConfigManager
 *  android.telephony.PhoneNumberUtils
 *  android.telephony.Rlog
 *  android.telephony.TelephonyManager
 *  android.text.TextUtils
 */
package com.android.internal.telephony;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.PersistableBundle;
import android.telephony.CarrierConfigManager;
import android.telephony.PhoneNumberUtils;
import android.telephony.Rlog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import com.android.internal.telephony.HbpcdLookup;
import com.android.internal.telephony.Phone;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class SmsNumberUtils {
    private static int[] ALL_COUNTRY_CODES;
    private static final int CDMA_HOME_NETWORK = 1;
    private static final int CDMA_ROAMING_NETWORK = 2;
    private static final boolean DBG;
    private static final int GSM_UMTS_NETWORK = 0;
    private static HashMap<String, ArrayList<String>> IDDS_MAPS;
    private static int MAX_COUNTRY_CODES_LENGTH = 0;
    private static final int MIN_COUNTRY_AREA_LOCAL_LENGTH = 10;
    private static final int NANP_CC = 1;
    private static final String NANP_IDD = "011";
    private static final int NANP_LONG_LENGTH = 11;
    private static final int NANP_MEDIUM_LENGTH = 10;
    private static final String NANP_NDD = "1";
    private static final int NANP_SHORT_LENGTH = 7;
    private static final int NP_CC_AREA_LOCAL = 104;
    private static final int NP_HOMEIDD_CC_AREA_LOCAL = 101;
    private static final int NP_INTERNATIONAL_BEGIN = 100;
    private static final int NP_LOCALIDD_CC_AREA_LOCAL = 103;
    private static final int NP_NANP_AREA_LOCAL = 2;
    private static final int NP_NANP_BEGIN = 1;
    private static final int NP_NANP_LOCAL = 1;
    private static final int NP_NANP_LOCALIDD_CC_AREA_LOCAL = 5;
    private static final int NP_NANP_NBPCD_CC_AREA_LOCAL = 4;
    private static final int NP_NANP_NBPCD_HOMEIDD_CC_AREA_LOCAL = 6;
    private static final int NP_NANP_NDD_AREA_LOCAL = 3;
    private static final int NP_NBPCD_CC_AREA_LOCAL = 102;
    private static final int NP_NBPCD_HOMEIDD_CC_AREA_LOCAL = 100;
    private static final int NP_NONE = 0;
    private static final String PLUS_SIGN = "+";
    private static final String TAG = "SmsNumberUtils";

    static {
        DBG = Build.IS_DEBUGGABLE;
        ALL_COUNTRY_CODES = null;
        IDDS_MAPS = new HashMap();
    }

    private static int checkInternationalNumberPlan(Context context, NumberEntry numberEntry, ArrayList<String> object, String string) {
        String string2 = numberEntry.number;
        if (string2.startsWith(PLUS_SIGN)) {
            object = string2.substring(1);
            if (((String)object).startsWith(string)) {
                int n = SmsNumberUtils.getCountryCode(context, ((String)object).substring(string.length()));
                if (n > 0) {
                    numberEntry.countryCode = n;
                    return 100;
                }
            } else {
                int n = SmsNumberUtils.getCountryCode(context, object);
                if (n > 0) {
                    numberEntry.countryCode = n;
                    return 102;
                }
            }
        } else if (string2.startsWith(string)) {
            int n = SmsNumberUtils.getCountryCode(context, string2.substring(string.length()));
            if (n > 0) {
                numberEntry.countryCode = n;
                return 101;
            }
        } else {
            int n;
            object = ((ArrayList)object).iterator();
            while (object.hasNext()) {
                string = (String)object.next();
                if (!string2.startsWith(string) || (n = SmsNumberUtils.getCountryCode(context, string2.substring(string.length()))) <= 0) continue;
                numberEntry.countryCode = n;
                numberEntry.IDD = string;
                return 103;
            }
            if (!string2.startsWith("0") && (n = SmsNumberUtils.getCountryCode(context, string2)) > 0) {
                numberEntry.countryCode = n;
                return 104;
            }
        }
        return 0;
    }

    private static int checkNANP(NumberEntry object, ArrayList<String> object2) {
        int n = 0;
        String string = ((NumberEntry)object).number;
        if (string.length() == 7) {
            int n2 = string.charAt(0);
            int n3 = n;
            if (n2 >= 50) {
                n3 = n;
                if (n2 <= 57) {
                    n2 = 1;
                    n = 1;
                    do {
                        n3 = n2;
                        if (n >= 7) break;
                        if (!PhoneNumberUtils.isISODigit((char)string.charAt(n))) {
                            n3 = 0;
                            break;
                        }
                        ++n;
                    } while (true);
                }
            }
            if (n3 != 0) {
                return 1;
            }
        } else if (string.length() == 10) {
            if (SmsNumberUtils.isNANP(string)) {
                return 2;
            }
        } else if (string.length() == 11) {
            if (SmsNumberUtils.isNANP(string)) {
                return 3;
            }
        } else if (string.startsWith(PLUS_SIGN)) {
            object = string.substring(1);
            if (((String)object).length() == 11) {
                if (SmsNumberUtils.isNANP((String)object)) {
                    return 4;
                }
            } else if (((String)object).startsWith(NANP_IDD) && ((String)object).length() == 14 && SmsNumberUtils.isNANP(((String)object).substring(3))) {
                return 6;
            }
        } else {
            Iterator<String> iterator = ((ArrayList)object2).iterator();
            while (iterator.hasNext()) {
                String string2 = iterator.next();
                if (!string.startsWith(string2) || (object2 = string.substring(string2.length())) == null || !((String)object2).startsWith(String.valueOf(1)) || !SmsNumberUtils.isNANP((String)object2)) continue;
                ((NumberEntry)object).IDD = string2;
                return 5;
            }
        }
        return 0;
    }

    private static boolean compareGid1(Phone object, String charSequence) {
        String string = object.getGroupIdLevel1();
        boolean bl = true;
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            if (DBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("compareGid1 serviceGid is empty, return ");
                ((StringBuilder)object).append(true);
                Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
            }
            return true;
        }
        int n = ((String)charSequence).length();
        if (string == null || string.length() < n || !string.substring(0, n).equalsIgnoreCase((String)charSequence)) {
            if (DBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append(" gid1 ");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append(" serviceGid1 ");
                ((StringBuilder)object).append((String)charSequence);
                Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
            }
            bl = false;
        }
        if (DBG) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("compareGid1 is ");
            object = bl ? "Same" : "Different";
            ((StringBuilder)charSequence).append((String)object);
            Rlog.d((String)TAG, (String)((StringBuilder)charSequence).toString());
        }
        return bl;
    }

    public static String filterDestAddr(Phone object, String charSequence) {
        CharSequence charSequence2;
        if (DBG) {
            charSequence2 = new StringBuilder();
            charSequence2.append("enter filterDestAddr. destAddr=\"");
            charSequence2.append(Rlog.pii((String)TAG, (Object)charSequence));
            charSequence2.append("\"");
            Rlog.d((String)TAG, (String)charSequence2.toString());
        }
        if (charSequence != null && PhoneNumberUtils.isGlobalPhoneNumber((String)charSequence)) {
            StringBuilder stringBuilder;
            String string = TelephonyManager.from((Context)((Phone)object).getContext()).getNetworkOperator(((Phone)object).getSubId());
            charSequence2 = stringBuilder = null;
            if (SmsNumberUtils.needToConvert((Phone)object)) {
                int n = SmsNumberUtils.getNetworkType((Phone)object);
                charSequence2 = stringBuilder;
                if (n != -1) {
                    charSequence2 = stringBuilder;
                    if (!TextUtils.isEmpty((CharSequence)string)) {
                        string = string.substring(0, 3);
                        charSequence2 = stringBuilder;
                        if (string != null) {
                            charSequence2 = stringBuilder;
                            if (string.trim().length() > 0) {
                                charSequence2 = SmsNumberUtils.formatNumber(((Phone)object).getContext(), charSequence, string, n);
                            }
                        }
                    }
                }
            }
            if (DBG) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("destAddr is ");
                object = charSequence2 != null ? "formatted." : "not formatted.";
                stringBuilder.append((String)object);
                Rlog.d((String)TAG, (String)stringBuilder.toString());
                stringBuilder = new StringBuilder();
                stringBuilder.append("leave filterDestAddr, new destAddr=\"");
                object = charSequence2 != null ? Rlog.pii((String)TAG, (Object)charSequence2) : Rlog.pii((String)TAG, (Object)charSequence);
                stringBuilder.append((String)object);
                stringBuilder.append("\"");
                Rlog.d((String)TAG, (String)stringBuilder.toString());
            }
            if (charSequence2 != null) {
                charSequence = charSequence2;
            }
            return charSequence;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("destAddr");
        ((StringBuilder)object).append(Rlog.pii((String)TAG, (Object)charSequence));
        ((StringBuilder)object).append(" is not a global phone number! Nothing changed.");
        Rlog.w((String)TAG, (String)((StringBuilder)object).toString());
        return charSequence;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private static String formatNumber(Context var0, String var1_1, String var2_2, int var3_3) {
        block27 : {
            if (var1_1 == null) throw new IllegalArgumentException("number is null");
            if (var2_2 == null) throw new IllegalArgumentException("activeMcc is null or empty!");
            if (var2_2.trim().length() == 0) throw new IllegalArgumentException("activeMcc is null or empty!");
            if ((var1_1 = PhoneNumberUtils.extractNetworkPortion((String)var1_1)) == null) throw new IllegalArgumentException("Number is invalid!");
            if (var1_1.length() == 0) throw new IllegalArgumentException("Number is invalid!");
            var4_4 = new NumberEntry(var1_1);
            var2_2 = SmsNumberUtils.getAllIDDs((Context)var0, (String)var2_2);
            var5_5 = SmsNumberUtils.checkNANP(var4_4, var2_2);
            if (SmsNumberUtils.DBG) {
                var6_6 = new StringBuilder();
                var6_6.append("NANP type: ");
                var6_6.append(SmsNumberUtils.getNumberPlanType(var5_5));
                Rlog.d((String)"SmsNumberUtils", (String)var6_6.toString());
            }
            if (var5_5 == 1) return var1_1;
            if (var5_5 == 2) return var1_1;
            if (var5_5 == 3) {
                return var1_1;
            }
            if (var5_5 == 4) {
                if (var3_3 == 1) return var1_1.substring(1);
                if (var3_3 != 2) return var1_1;
                return var1_1.substring(1);
            }
            var7_7 = 0;
            var8_8 = 0;
            var9_9 = 0;
            if (var5_5 == 5) {
                if (var3_3 == 1) {
                    return var1_1;
                }
                if (var3_3 == 0) {
                    var3_3 = var9_9;
                    if (var4_4.IDD != null) {
                        var3_3 = var4_4.IDD.length();
                    }
                    var0 = new StringBuilder();
                    var0.append("+");
                    var0.append(var1_1.substring(var3_3));
                    return var0.toString();
                }
                if (var3_3 == 2) {
                    var3_3 = var7_7;
                    if (var4_4.IDD == null) return var1_1.substring(var3_3);
                    var3_3 = var4_4.IDD.length();
                    return var1_1.substring(var3_3);
                }
            }
            var9_9 = SmsNumberUtils.checkInternationalNumberPlan((Context)var0, var4_4, var2_2, "011");
            if (SmsNumberUtils.DBG) {
                var0 = new StringBuilder();
                var0.append("International type: ");
                var0.append(SmsNumberUtils.getNumberPlanType(var9_9));
                Rlog.d((String)"SmsNumberUtils", (String)var0.toString());
            }
            var2_2 = null;
            switch (var9_9) {
                default: {
                    var0 = var2_2;
                    if (var1_1.startsWith("+")) {
                        if (var3_3 == 1) break;
                        var0 = var2_2;
                        if (var3_3 == 2) {
                            break;
                        }
                    }
                    break block27;
                }
                case 104: {
                    var3_3 = var4_4.countryCode;
                    var0 = var2_2;
                    if (!SmsNumberUtils.inExceptionListForNpCcAreaLocal(var4_4)) {
                        var0 = var2_2;
                        if (var1_1.length() >= 11) {
                            var0 = var2_2;
                            if (var3_3 != 1) {
                                var0 = new StringBuilder();
                                var0.append("011");
                                var0.append(var1_1);
                                var0 = var0.toString();
                            }
                        }
                    }
                    break block27;
                }
                case 103: {
                    if (var3_3 == 0) ** GOTO lbl84
                    var0 = var2_2;
                    if (var3_3 != 2) break block27;
lbl84: // 2 sources:
                    var3_3 = var8_8;
                    if (var4_4.IDD != null) {
                        var3_3 = var4_4.IDD.length();
                    }
                    var0 = new StringBuilder();
                    var0.append("011");
                    var0.append(var1_1.substring(var3_3));
                    var0 = var0.toString();
                    break block27;
                }
                case 102: {
                    var0 = new StringBuilder();
                    var0.append("011");
                    var0.append(var1_1.substring(1));
                    var0 = var0.toString();
                    break block27;
                }
                case 101: {
                    var0 = var1_1;
                    break block27;
                }
                case 100: {
                    var0 = var2_2;
                    if (var3_3 == 0) {
                        var0 = var1_1.substring(1);
                    }
                    break block27;
                }
            }
            if (var1_1.startsWith("+011")) {
                var0 = var1_1.substring(1);
            } else {
                var0 = new StringBuilder();
                var0.append("011");
                var0.append(var1_1.substring(1));
                var0 = var0.toString();
            }
        }
        var2_2 = var0;
        if (var0 != null) return var2_2;
        return var1_1;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static int[] getAllCountryCodes(Context var0) {
        block9 : {
            var1_3 = SmsNumberUtils.ALL_COUNTRY_CODES;
            if (var1_3 != null) {
                return var1_3;
            }
            var1_3 = null;
            var2_4 = null;
            var2_4 = var0 = var0.getContentResolver().query(HbpcdLookup.MccLookup.CONTENT_URI, new String[]{"Country_Code"}, null, null, null);
            var1_3 = var0;
            if (var0.getCount() > 0) {
                var2_4 = var0;
                var1_3 = var0;
                SmsNumberUtils.ALL_COUNTRY_CODES = new int[var0.getCount()];
                var3_5 = 0;
                do {
                    var2_4 = var0;
                    var1_3 = var0;
                    if (var0.moveToNext()) {
                        var2_4 = var0;
                        var1_3 = var0;
                        var4_6 = var0.getInt(0);
                        var2_4 = var0;
                        var1_3 = var0;
                        SmsNumberUtils.ALL_COUNTRY_CODES[var3_5] = var4_6;
                        var2_4 = var0;
                        var1_3 = var0;
                        var4_6 = String.valueOf(var4_6).trim().length();
                        var2_4 = var0;
                        var1_3 = var0;
                        if (var4_6 > SmsNumberUtils.MAX_COUNTRY_CODES_LENGTH) {
                            var2_4 = var0;
                            var1_3 = var0;
                            SmsNumberUtils.MAX_COUNTRY_CODES_LENGTH = var4_6;
                        }
                        ++var3_5;
                        continue;
                    }
                    ** GOTO lbl45
                    break;
                } while (true);
            }
            {
                catch (Throwable var0_1) {
                    break block9;
                }
                catch (SQLException var0_2) {}
                var2_4 = var1_3;
                {
                    Rlog.e((String)"SmsNumberUtils", (String)"Can't access HbpcdLookup database", (Throwable)var0_2);
                    if (var1_3 == null) return SmsNumberUtils.ALL_COUNTRY_CODES;
                    var0 = var1_3;
                }
            }
lbl45: // 3 sources:
            var0.close();
            return SmsNumberUtils.ALL_COUNTRY_CODES;
        }
        if (var2_4 == null) throw var0_1;
        var2_4.close();
        throw var0_1;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static ArrayList<String> getAllIDDs(Context var0, String var1_3) {
        block10 : {
            block9 : {
                block8 : {
                    var2_4 = SmsNumberUtils.IDDS_MAPS.get(var1_3);
                    if (var2_4 != null) {
                        return var2_4;
                    }
                    var3_5 = new ArrayList<String>();
                    var4_6 = null;
                    var5_7 = null;
                    if (var1_3 != null) {
                        var4_6 = "MCC=?";
                        var5_7 = new String[]{var1_3};
                    }
                    var2_4 = null;
                    var6_8 = null;
                    var6_8 = var0 = var0.getContentResolver().query(HbpcdLookup.MccIdd.CONTENT_URI, new String[]{"IDD", "MCC"}, var4_6, var5_7, null);
                    var2_4 = var0;
                    if (var0.getCount() <= 0) break block8;
                    do {
                        var6_8 = var0;
                        var2_4 = var0;
                        if (!var0.moveToNext()) break;
                        var6_8 = var0;
                        var2_4 = var0;
                        var4_6 = var0.getString(0);
                        var6_8 = var0;
                        var2_4 = var0;
                        if (var3_5.contains(var4_6)) continue;
                        var6_8 = var0;
                        var2_4 = var0;
                        var3_5.add(var4_6);
                    } while (true);
                }
lbl32: // 2 sources:
                do {
                    var0.close();
                    break block9;
                    break;
                } while (true);
                {
                    catch (Throwable var0_1) {
                        break block10;
                    }
                    catch (SQLException var0_2) {}
                    var6_8 = var2_4;
                    {
                        Rlog.e((String)"SmsNumberUtils", (String)"Can't access HbpcdLookup database", (Throwable)var0_2);
                        if (var2_4 == null) break block9;
                        var0 = var2_4;
                        ** continue;
                    }
                }
            }
            SmsNumberUtils.IDDS_MAPS.put(var1_3, var3_5);
            if (SmsNumberUtils.DBG == false) return var3_5;
            var0 = new StringBuilder();
            var0.append("MCC = ");
            var0.append(var1_3);
            var0.append(", all IDDs = ");
            var0.append(var3_5);
            Rlog.d((String)"SmsNumberUtils", (String)var0.toString());
            return var3_5;
        }
        if (var6_8 == null) throw var0_1;
        var6_8.close();
        throw var0_1;
    }

    private static int getCountryCode(Context object, String string) {
        if (string.length() >= 10) {
            int n;
            if ((object = SmsNumberUtils.getAllCountryCodes((Context)object)) == null) {
                return -1;
            }
            int[] arrn = new int[MAX_COUNTRY_CODES_LENGTH];
            for (n = 0; n < MAX_COUNTRY_CODES_LENGTH; ++n) {
                arrn[n] = Integer.parseInt(string.substring(0, n + 1));
            }
            for (n = 0; n < ((int[])object).length; ++n) {
                int n2 = object[n];
                for (int i = 0; i < MAX_COUNTRY_CODES_LENGTH; ++i) {
                    if (n2 != arrn[i]) continue;
                    if (DBG) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Country code = ");
                        ((StringBuilder)object).append(n2);
                        Rlog.d((String)TAG, (String)((StringBuilder)object).toString());
                    }
                    return n2;
                }
            }
        }
        return -1;
    }

    private static int getNetworkType(Phone object) {
        int n;
        int n2 = -1;
        int n3 = ((Phone)object).getPhoneType();
        if (n3 == 1) {
            n = 0;
        } else if (n3 == 2) {
            n = SmsNumberUtils.isInternationalRoaming((Phone)object) ? 2 : 1;
        } else {
            n = n2;
            if (DBG) {
                object = new StringBuilder();
                ((StringBuilder)object).append("warning! unknown mPhoneType value=");
                ((StringBuilder)object).append(n3);
                Rlog.w((String)TAG, (String)((StringBuilder)object).toString());
                n = n2;
            }
        }
        return n;
    }

    private static String getNumberPlanType(int n) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("Number Plan type (");
        charSequence.append(n);
        charSequence.append("): ");
        charSequence.toString();
        charSequence = n == 1 ? "NP_NANP_LOCAL" : (n == 2 ? "NP_NANP_AREA_LOCAL" : (n == 3 ? "NP_NANP_NDD_AREA_LOCAL" : (n == 4 ? "NP_NANP_NBPCD_CC_AREA_LOCAL" : (n == 5 ? "NP_NANP_LOCALIDD_CC_AREA_LOCAL" : (n == 6 ? "NP_NANP_NBPCD_HOMEIDD_CC_AREA_LOCAL" : (n == 100 ? "NP_NBPCD_HOMEIDD_CC_AREA_LOCAL" : (n == 101 ? "NP_HOMEIDD_CC_AREA_LOCAL" : (n == 102 ? "NP_NBPCD_CC_AREA_LOCAL" : (n == 103 ? "NP_LOCALIDD_CC_AREA_LOCAL" : (n == 104 ? "NP_CC_AREA_LOCAL" : "Unknown type"))))))))));
        return charSequence;
    }

    private static boolean inExceptionListForNpCcAreaLocal(NumberEntry numberEntry) {
        int n = numberEntry.countryCode;
        boolean bl = numberEntry.number.length() == 12 && (n == 7 || n == 20 || n == 65 || n == 90);
        return bl;
    }

    private static boolean isInternationalRoaming(Phone object) {
        String string = TelephonyManager.from((Context)((Phone)object).getContext()).getNetworkCountryIsoForPhone(((Phone)object).getPhoneId());
        object = TelephonyManager.from((Context)((Phone)object).getContext()).getSimCountryIsoForPhone(((Phone)object).getPhoneId());
        boolean bl = !TextUtils.isEmpty((CharSequence)string) && !TextUtils.isEmpty((CharSequence)object) && !((String)object).equals(string);
        boolean bl2 = bl;
        if (bl) {
            if ("us".equals(object)) {
                bl2 = true ^ "vi".equals(string);
            } else {
                bl2 = bl;
                if ("vi".equals(object)) {
                    bl2 = true ^ "us".equals(string);
                }
            }
        }
        return bl2;
    }

    private static boolean isNANP(String string) {
        if (!(string.length() == 10 || string.length() == 11 && string.startsWith(NANP_NDD))) {
            return false;
        }
        String string2 = string;
        if (string.length() == 11) {
            string2 = string.substring(1);
        }
        return PhoneNumberUtils.isNanp((String)string2);
    }

    private static boolean needToConvert(Phone phone) {
        long l;
        block5 : {
            l = Binder.clearCallingIdentity();
            CarrierConfigManager carrierConfigManager = (CarrierConfigManager)phone.getContext().getSystemService("carrier_config");
            if (carrierConfigManager == null) break block5;
            phone = carrierConfigManager.getConfigForSubId(phone.getSubId());
            if (phone == null) break block5;
            boolean bl = phone.getBoolean("sms_requires_destination_number_conversion_bool");
            return bl;
        }
        Binder.restoreCallingIdentity((long)l);
        return false;
        finally {
            Binder.restoreCallingIdentity((long)l);
        }
    }

    private static class NumberEntry {
        public String IDD;
        public int countryCode;
        public String number;

        public NumberEntry(String string) {
            this.number = string;
        }
    }

}

