/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  com.android.i18n.phonenumbers.NumberParseException
 *  com.android.i18n.phonenumbers.PhoneNumberUtil
 *  com.android.i18n.phonenumbers.PhoneNumberUtil$PhoneNumberFormat
 *  com.android.i18n.phonenumbers.Phonenumber
 *  com.android.i18n.phonenumbers.Phonenumber$PhoneNumber
 *  com.android.i18n.phonenumbers.Phonenumber$PhoneNumber$CountryCodeSource
 */
package android.telephony;

import android.annotation.UnsupportedAppUsage;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.location.Country;
import android.location.CountryDetector;
import android.net.Uri;
import android.os.PersistableBundle;
import android.os.SystemProperties;
import android.telephony.CarrierConfigManager;
import android.telephony.JapanesePhoneNumberFormatter;
import android.telephony.Rlog;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TtsSpan;
import android.util.SparseIntArray;
import com.android.i18n.phonenumbers.NumberParseException;
import com.android.i18n.phonenumbers.PhoneNumberUtil;
import com.android.i18n.phonenumbers.Phonenumber;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneNumberUtils {
    private static final String BCD_CALLED_PARTY_EXTENDED = "*#abc";
    private static final String BCD_EF_ADN_EXTENDED = "*#,N;";
    public static final int BCD_EXTENDED_TYPE_CALLED_PARTY = 2;
    public static final int BCD_EXTENDED_TYPE_EF_ADN = 1;
    private static final int CCC_LENGTH;
    private static final String CLIR_OFF = "#31#";
    private static final String CLIR_ON = "*31#";
    private static final boolean[] COUNTRY_CALLING_CALL;
    private static final boolean DBG = false;
    public static final int FORMAT_JAPAN = 2;
    public static final int FORMAT_NANP = 1;
    public static final int FORMAT_UNKNOWN = 0;
    private static final Pattern GLOBAL_PHONE_NUMBER_PATTERN;
    private static final String JAPAN_ISO_COUNTRY_CODE = "JP";
    private static final SparseIntArray KEYPAD_MAP;
    private static final String KOREA_ISO_COUNTRY_CODE = "KR";
    static final String LOG_TAG = "PhoneNumberUtils";
    @UnsupportedAppUsage
    static final int MIN_MATCH = 7;
    private static final String[] NANP_COUNTRIES;
    private static final String NANP_IDP_STRING = "011";
    private static final int NANP_LENGTH = 10;
    private static final int NANP_STATE_DASH = 4;
    private static final int NANP_STATE_DIGIT = 1;
    private static final int NANP_STATE_ONE = 3;
    private static final int NANP_STATE_PLUS = 2;
    public static final char PAUSE = ',';
    private static final char PLUS_SIGN_CHAR = '+';
    private static final String PLUS_SIGN_STRING = "+";
    public static final int TOA_International = 145;
    public static final int TOA_Unknown = 129;
    public static final char WAIT = ';';
    public static final char WILD = 'N';
    private static String[] sConvertToEmergencyMap;

    static {
        GLOBAL_PHONE_NUMBER_PATTERN = Pattern.compile("[\\+]?[0-9.-]+");
        NANP_COUNTRIES = new String[]{"US", "CA", "AS", "AI", "AG", "BS", "BB", "BM", "VG", "KY", "DM", "DO", "GD", "GU", "JM", "PR", "MS", "MP", "KN", "LC", "VC", "TT", "TC", "VI"};
        KEYPAD_MAP = new SparseIntArray();
        KEYPAD_MAP.put(97, 50);
        KEYPAD_MAP.put(98, 50);
        KEYPAD_MAP.put(99, 50);
        KEYPAD_MAP.put(65, 50);
        KEYPAD_MAP.put(66, 50);
        KEYPAD_MAP.put(67, 50);
        KEYPAD_MAP.put(100, 51);
        KEYPAD_MAP.put(101, 51);
        KEYPAD_MAP.put(102, 51);
        KEYPAD_MAP.put(68, 51);
        KEYPAD_MAP.put(69, 51);
        KEYPAD_MAP.put(70, 51);
        KEYPAD_MAP.put(103, 52);
        KEYPAD_MAP.put(104, 52);
        KEYPAD_MAP.put(105, 52);
        KEYPAD_MAP.put(71, 52);
        KEYPAD_MAP.put(72, 52);
        KEYPAD_MAP.put(73, 52);
        KEYPAD_MAP.put(106, 53);
        KEYPAD_MAP.put(107, 53);
        KEYPAD_MAP.put(108, 53);
        KEYPAD_MAP.put(74, 53);
        KEYPAD_MAP.put(75, 53);
        KEYPAD_MAP.put(76, 53);
        KEYPAD_MAP.put(109, 54);
        KEYPAD_MAP.put(110, 54);
        KEYPAD_MAP.put(111, 54);
        KEYPAD_MAP.put(77, 54);
        KEYPAD_MAP.put(78, 54);
        KEYPAD_MAP.put(79, 54);
        KEYPAD_MAP.put(112, 55);
        KEYPAD_MAP.put(113, 55);
        KEYPAD_MAP.put(114, 55);
        KEYPAD_MAP.put(115, 55);
        KEYPAD_MAP.put(80, 55);
        KEYPAD_MAP.put(81, 55);
        KEYPAD_MAP.put(82, 55);
        KEYPAD_MAP.put(83, 55);
        KEYPAD_MAP.put(116, 56);
        KEYPAD_MAP.put(117, 56);
        KEYPAD_MAP.put(118, 56);
        KEYPAD_MAP.put(84, 56);
        KEYPAD_MAP.put(85, 56);
        KEYPAD_MAP.put(86, 56);
        KEYPAD_MAP.put(119, 57);
        KEYPAD_MAP.put(120, 57);
        KEYPAD_MAP.put(121, 57);
        KEYPAD_MAP.put(122, 57);
        KEYPAD_MAP.put(87, 57);
        KEYPAD_MAP.put(88, 57);
        KEYPAD_MAP.put(89, 57);
        KEYPAD_MAP.put(90, 57);
        COUNTRY_CALLING_CALL = new boolean[]{true, true, false, false, false, false, false, true, false, false, false, false, false, false, false, false, false, false, false, false, true, false, false, false, false, false, false, true, true, false, true, true, true, true, true, false, true, false, false, true, true, false, false, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, true, false, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, false, true, false, false, true, true, true, true, true, true, true, false, false, true, false};
        CCC_LENGTH = COUNTRY_CALLING_CALL.length;
        sConvertToEmergencyMap = null;
    }

    public static void addTtsSpan(Spannable spannable, int n, int n2) {
        spannable.setSpan(PhoneNumberUtils.createTtsSpan(spannable.subSequence(n, n2).toString()), n, n2, 33);
    }

    private static String appendPwCharBackToOrigDialStr(int n, String string2, String string3) {
        string2 = n == 1 ? string2 + string3.charAt(0) : string2.concat(string3.substring(0, n));
        return string2;
    }

    private static char bcdToChar(byte by, int n) {
        if (by < 10) {
            return (char)(by + 48);
        }
        String string2 = null;
        if (1 == n) {
            string2 = BCD_EF_ADN_EXTENDED;
        } else if (2 == n) {
            string2 = BCD_CALLED_PARTY_EXTENDED;
        }
        if (string2 != null && by - 10 < string2.length()) {
            return string2.charAt(by - 10);
        }
        return '\u0000';
    }

    @Deprecated
    public static String calledPartyBCDFragmentToString(byte[] arrby, int n, int n2) {
        return PhoneNumberUtils.calledPartyBCDFragmentToString(arrby, n, n2, 1);
    }

    public static String calledPartyBCDFragmentToString(byte[] arrby, int n, int n2, int n3) {
        StringBuilder stringBuilder = new StringBuilder(n2 * 2);
        PhoneNumberUtils.internalCalledPartyBCDFragmentToString(stringBuilder, arrby, n, n2, n3);
        return stringBuilder.toString();
    }

    @Deprecated
    public static String calledPartyBCDToString(byte[] arrby, int n, int n2) {
        return PhoneNumberUtils.calledPartyBCDToString(arrby, n, n2, 1);
    }

    public static String calledPartyBCDToString(byte[] object, int n, int n2, int n3) {
        boolean bl = false;
        CharSequence charSequence = new StringBuilder(n2 * 2 + 1);
        if (n2 < 2) {
            return "";
        }
        if ((object[n] & 240) == 144) {
            bl = true;
        }
        PhoneNumberUtils.internalCalledPartyBCDFragmentToString(charSequence, (byte[])object, n + 1, n2 - 1, n3);
        if (bl && charSequence.length() == 0) {
            return "";
        }
        object = charSequence;
        if (bl) {
            charSequence = charSequence.toString();
            Matcher matcher = Pattern.compile("(^[#*])(.*)([#*])(.*)(#)$").matcher(charSequence);
            if (matcher.matches()) {
                if ("".equals(matcher.group(2))) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(matcher.group(1));
                    ((StringBuilder)object).append(matcher.group(3));
                    ((StringBuilder)object).append(matcher.group(4));
                    ((StringBuilder)object).append(matcher.group(5));
                    ((StringBuilder)object).append(PLUS_SIGN_STRING);
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(matcher.group(1));
                    ((StringBuilder)object).append(matcher.group(2));
                    ((StringBuilder)object).append(matcher.group(3));
                    ((StringBuilder)object).append(PLUS_SIGN_STRING);
                    ((StringBuilder)object).append(matcher.group(4));
                    ((StringBuilder)object).append(matcher.group(5));
                }
            } else {
                matcher = Pattern.compile("(^[#*])(.*)([#*])(.*)").matcher(charSequence);
                if (matcher.matches()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(matcher.group(1));
                    ((StringBuilder)object).append(matcher.group(2));
                    ((StringBuilder)object).append(matcher.group(3));
                    ((StringBuilder)object).append(PLUS_SIGN_STRING);
                    ((StringBuilder)object).append(matcher.group(4));
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append('+');
                    ((StringBuilder)object).append((String)charSequence);
                }
            }
        }
        return ((StringBuilder)object).toString();
    }

    @UnsupportedAppUsage
    public static String cdmaCheckAndProcessPlusCode(String string2) {
        if (!TextUtils.isEmpty(string2) && PhoneNumberUtils.isReallyDialable(string2.charAt(0)) && PhoneNumberUtils.isNonSeparator(string2)) {
            String string3 = TelephonyManager.getDefault().getNetworkCountryIso();
            String string4 = TelephonyManager.getDefault().getSimCountryIso();
            if (!TextUtils.isEmpty(string3) && !TextUtils.isEmpty(string4)) {
                return PhoneNumberUtils.cdmaCheckAndProcessPlusCodeByNumberFormat(string2, PhoneNumberUtils.getFormatTypeFromCountryCode(string3), PhoneNumberUtils.getFormatTypeFromCountryCode(string4));
            }
        }
        return string2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String cdmaCheckAndProcessPlusCodeByNumberFormat(String string2, int n, int n2) {
        String string3;
        block5 : {
            String string4;
            string3 = string2;
            boolean bl = n == n2 && n == 1;
            String string5 = string3;
            if (string2 == null) return string5;
            string5 = string3;
            if (string2.lastIndexOf(PLUS_SIGN_STRING) == -1) return string5;
            String string6 = string2;
            string5 = null;
            do {
                string3 = bl ? PhoneNumberUtils.extractNetworkPortion(string6) : PhoneNumberUtils.extractNetworkPortionAlt(string6);
                if (TextUtils.isEmpty(string3 = PhoneNumberUtils.processPlusCode(string3, bl))) break block5;
                string5 = string5 == null ? string3 : string5.concat(string3);
                String string7 = PhoneNumberUtils.extractPostDialPortion(string6);
                string3 = string5;
                String string8 = string7;
                string4 = string6;
                if (!TextUtils.isEmpty(string7)) {
                    n = PhoneNumberUtils.findDialableIndexFromPostDialStr(string7);
                    if (n >= 1) {
                        string3 = PhoneNumberUtils.appendPwCharBackToOrigDialStr(n, string5, string7);
                        string4 = string7.substring(n);
                        string8 = string7;
                    } else {
                        string3 = string7;
                        if (n < 0) {
                            string3 = "";
                        }
                        Rlog.e("wrong postDialStr=", string3);
                        string4 = string6;
                        string8 = string3;
                        string3 = string5;
                    }
                }
                string5 = string3;
                if (TextUtils.isEmpty(string8)) return string5;
                string5 = string3;
                string6 = string4;
            } while (!TextUtils.isEmpty(string4));
            return string3;
        }
        Rlog.e("checkAndProcessPlusCode: null newDialStr", string3);
        return string2;
    }

    public static String cdmaCheckAndProcessPlusCodeForSms(String string2) {
        String string3;
        if (!TextUtils.isEmpty(string2) && PhoneNumberUtils.isReallyDialable(string2.charAt(0)) && PhoneNumberUtils.isNonSeparator(string2) && !TextUtils.isEmpty(string3 = TelephonyManager.getDefault().getSimCountryIso())) {
            int n = PhoneNumberUtils.getFormatTypeFromCountryCode(string3);
            return PhoneNumberUtils.cdmaCheckAndProcessPlusCodeByNumberFormat(string2, n, n);
        }
        return string2;
    }

    private static int charToBCD(char c, int n) {
        if ('0' <= c && c <= '9') {
            return c - 48;
        }
        CharSequence charSequence = null;
        if (1 == n) {
            charSequence = BCD_EF_ADN_EXTENDED;
        } else if (2 == n) {
            charSequence = BCD_CALLED_PARTY_EXTENDED;
        }
        if (charSequence != null && ((String)charSequence).indexOf(c) != -1) {
            return ((String)charSequence).indexOf(c) + 10;
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("invalid char for BCD ");
        ((StringBuilder)charSequence).append(c);
        throw new RuntimeException(((StringBuilder)charSequence).toString());
    }

    private static boolean checkPrefixIsIgnorable(String string2, int n, int n2) {
        boolean bl = false;
        while (n2 >= n) {
            if (PhoneNumberUtils.tryGetISODigit(string2.charAt(n2)) >= 0) {
                if (bl) {
                    return false;
                }
                bl = true;
            } else if (PhoneNumberUtils.isDialable(string2.charAt(n2))) {
                return false;
            }
            --n2;
        }
        return true;
    }

    public static boolean compare(Context context, String string2, String string3) {
        return PhoneNumberUtils.compare(string2, string3, context.getResources().getBoolean(17891565));
    }

    public static boolean compare(String string2, String string3) {
        return PhoneNumberUtils.compare(string2, string3, false);
    }

    @UnsupportedAppUsage
    public static boolean compare(String string2, String string3, boolean bl) {
        bl = bl ? PhoneNumberUtils.compareStrictly(string2, string3) : PhoneNumberUtils.compareLoosely(string2, string3);
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean compareLoosely(String string2, String string3) {
        int n = 0;
        int n2 = 0;
        boolean bl = false;
        if (string2 != null && string3 != null) {
            if (string2.length() != 0 && string3.length() != 0) {
                int n3;
                int n4;
                int n5;
                int n6;
                int n7 = PhoneNumberUtils.indexOfLastNetworkChar(string2);
                int n8 = PhoneNumberUtils.indexOfLastNetworkChar(string3);
                int n9 = 0;
                do {
                    n3 = n;
                    n4 = n2;
                    n6 = n7;
                    n5 = n8;
                    if (n7 < 0) break;
                    n3 = n;
                    n4 = n2;
                    n6 = n7;
                    n5 = n8;
                    if (n8 < 0) break;
                    n5 = 0;
                    char c = string2.charAt(n7);
                    n3 = n;
                    n6 = n7;
                    if (!PhoneNumberUtils.isDialable(c)) {
                        n6 = n7 - 1;
                        n5 = 1;
                        n3 = n + 1;
                    }
                    char c2 = string3.charAt(n8);
                    n4 = n2;
                    n7 = n8;
                    n = n5;
                    if (!PhoneNumberUtils.isDialable(c2)) {
                        n7 = n8 - 1;
                        n = 1;
                        n4 = n2 + 1;
                    }
                    n5 = n6;
                    n8 = n7;
                    int n10 = n9;
                    if (n == 0) {
                        if (c2 != c && c != 'N' && c2 != 'N') {
                            n5 = n7;
                            break;
                        }
                        n5 = n6 - 1;
                        n8 = n7 - 1;
                        n10 = n9 + 1;
                    }
                    n = n3;
                    n2 = n4;
                    n7 = n5;
                    n9 = n10;
                } while (true);
                if (n9 < 7) {
                    n8 = string2.length() - n3;
                    return n8 == string3.length() - n4 && n8 == n9;
                }
                if (n9 >= 7 && (n6 < 0 || n5 < 0)) {
                    return true;
                }
                if (PhoneNumberUtils.matchIntlPrefix(string2, n6 + 1) && PhoneNumberUtils.matchIntlPrefix(string3, n5 + 1)) {
                    return true;
                }
                if (PhoneNumberUtils.matchTrunkPrefix(string2, n6 + 1) && PhoneNumberUtils.matchIntlPrefixAndCC(string3, n5 + 1)) {
                    return true;
                }
                return PhoneNumberUtils.matchTrunkPrefix(string3, n5 + 1) && PhoneNumberUtils.matchIntlPrefixAndCC(string2, n6 + 1);
            }
            return false;
        }
        if (string2 == string3) {
            bl = true;
        }
        return bl;
    }

    @UnsupportedAppUsage
    public static boolean compareStrictly(String string2, String string3) {
        return PhoneNumberUtils.compareStrictly(string2, string3, true);
    }

    @UnsupportedAppUsage
    public static boolean compareStrictly(String string2, String string3, boolean bl) {
        if (string2 != null && string3 != null) {
            int n;
            int n2;
            int n3;
            char c;
            if (string2.length() == 0 && string3.length() == 0) {
                return false;
            }
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            CountryCallingCodeAndNewIndex countryCallingCodeAndNewIndex = PhoneNumberUtils.tryGetCountryCallingCodeAndNewIndex(string2, bl);
            CountryCallingCodeAndNewIndex countryCallingCodeAndNewIndex2 = PhoneNumberUtils.tryGetCountryCallingCodeAndNewIndex(string3, bl);
            int n7 = 0;
            int n8 = 1;
            int n9 = 0;
            int n10 = 0;
            boolean bl2 = false;
            if (countryCallingCodeAndNewIndex != null && countryCallingCodeAndNewIndex2 != null) {
                if (countryCallingCodeAndNewIndex.countryCallingCode != countryCallingCodeAndNewIndex2.countryCallingCode) {
                    return false;
                }
                n = 0;
                n3 = 1;
                n4 = countryCallingCodeAndNewIndex.newIndex;
                n6 = countryCallingCodeAndNewIndex2.newIndex;
            } else if (countryCallingCodeAndNewIndex == null && countryCallingCodeAndNewIndex2 == null) {
                n = 0;
                n3 = n7;
            } else {
                if (countryCallingCodeAndNewIndex != null) {
                    n5 = countryCallingCodeAndNewIndex.newIndex;
                } else {
                    n = PhoneNumberUtils.tryGetTrunkPrefixOmittedIndex(string3, 0);
                    if (n >= 0) {
                        n5 = n;
                        n10 = 1;
                    }
                }
                if (countryCallingCodeAndNewIndex2 != null) {
                    n6 = countryCallingCodeAndNewIndex2.newIndex;
                    n4 = n5;
                    n3 = n7;
                    n = n8;
                    n9 = n10;
                } else {
                    n2 = PhoneNumberUtils.tryGetTrunkPrefixOmittedIndex(string3, 0);
                    n4 = n5;
                    n3 = n7;
                    n = n8;
                    n9 = n10;
                    if (n2 >= 0) {
                        n6 = n2;
                        bl2 = true;
                        n9 = n10;
                        n = n8;
                        n3 = n7;
                        n4 = n5;
                    }
                }
            }
            n10 = string2.length() - 1;
            n5 = string3.length() - 1;
            while (n10 >= n4 && n5 >= n6) {
                n8 = 0;
                char c2 = string2.charAt(n10);
                c = string3.charAt(n5);
                n7 = n10;
                if (PhoneNumberUtils.isSeparator(c2)) {
                    n7 = n10 - 1;
                    n8 = 1;
                }
                n10 = n5;
                n2 = n8;
                if (PhoneNumberUtils.isSeparator(c)) {
                    n10 = n5 - 1;
                    n2 = 1;
                }
                n8 = n7;
                n5 = n10;
                if (n2 == 0) {
                    if (c2 != c) {
                        return false;
                    }
                    n8 = n7 - 1;
                    n5 = n10 - 1;
                }
                n10 = n8;
            }
            if (n != 0) {
                if (n9 != 0 && n4 <= n10 || !PhoneNumberUtils.checkPrefixIsIgnorable(string2, n4, n10)) {
                    if (bl) {
                        return PhoneNumberUtils.compare(string2, string3, false);
                    }
                    return false;
                }
                if (bl2 && n6 <= n5 || !PhoneNumberUtils.checkPrefixIsIgnorable(string3, n4, n5)) {
                    if (bl) {
                        return PhoneNumberUtils.compare(string2, string3, false);
                    }
                    return false;
                }
            } else {
                n = n3 == 0 ? 1 : 0;
                do {
                    n8 = n;
                    if (n10 < n4) break;
                    c = string2.charAt(n10);
                    n7 = n;
                    if (PhoneNumberUtils.isDialable(c)) {
                        if (n != 0 && PhoneNumberUtils.tryGetISODigit(c) == 1) {
                            n7 = 0;
                        } else {
                            return false;
                        }
                    }
                    --n10;
                    n = n7;
                } while (true);
                for (n7 = n5; n7 >= n6; --n7) {
                    c = string3.charAt(n7);
                    if (!PhoneNumberUtils.isDialable(c)) continue;
                    if (n8 != 0 && PhoneNumberUtils.tryGetISODigit(c) == 1) {
                        n8 = 0;
                        continue;
                    }
                    return false;
                }
            }
            return true;
        }
        bl = true;
        if (string2 != string3) {
            bl = false;
        }
        return bl;
    }

    public static String convertAndStrip(String string2) {
        return PhoneNumberUtils.stripSeparators(PhoneNumberUtils.convertKeypadLettersToDigits(string2));
    }

    public static String convertKeypadLettersToDigits(String arrc) {
        if (arrc == null) {
            return arrc;
        }
        int n = arrc.length();
        if (n == 0) {
            return arrc;
        }
        arrc = arrc.toCharArray();
        for (int i = 0; i < n; ++i) {
            char c = arrc[i];
            arrc[i] = (char)KEYPAD_MAP.get(c, c);
        }
        return new String(arrc);
    }

    @UnsupportedAppUsage
    public static String convertPreDial(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c;
            char c2;
            char c3 = string2.charAt(i);
            if (PhoneNumberUtils.isPause(c3)) {
                c2 = c = ',';
            } else {
                c2 = c3;
                if (PhoneNumberUtils.isToneWait(c3)) {
                    c2 = c = ';';
                }
            }
            stringBuilder.append(c2);
        }
        return stringBuilder.toString();
    }

    public static Uri convertSipUriToTelUri(Uri uri) {
        if (!"sip".equals(uri.getScheme())) {
            return uri;
        }
        String[] arrstring = uri.getSchemeSpecificPart().split("[@;:]");
        if (arrstring.length == 0) {
            return uri;
        }
        return Uri.fromParts("tel", arrstring[0], null);
    }

    /*
     * WARNING - void declaration
     */
    public static String convertToEmergencyNumber(Context object2, String string2) {
        void var1_8;
        if (object2 != null && !TextUtils.isEmpty((CharSequence)var1_8)) {
            String[] arrstring;
            String string3 = PhoneNumberUtils.normalizeNumber((String)var1_8);
            if (PhoneNumberUtils.isEmergencyNumber(string3)) {
                return var1_8;
            }
            if (sConvertToEmergencyMap == null) {
                sConvertToEmergencyMap = ((Context)object2).getResources().getStringArray(17236001);
            }
            if ((arrstring = sConvertToEmergencyMap) != null && arrstring.length != 0) {
                for (String string4 : arrstring) {
                    void var6_16;
                    void var0_7;
                    Object var6_14 = null;
                    String[] arrstring2 = null;
                    Object var8_19 = null;
                    if (!TextUtils.isEmpty(string4)) {
                        String[] arrstring3 = string4.split(":");
                    }
                    String[] arrstring4 = arrstring2;
                    Object var0_3 = var8_19;
                    if (var6_16 != null) {
                        arrstring4 = arrstring2;
                        Object var0_4 = var8_19;
                        if (((void)var6_16).length == 2) {
                            var8_19 = var6_16[1];
                            arrstring4 = arrstring2;
                            Object var0_5 = var8_19;
                            if (!TextUtils.isEmpty((CharSequence)var6_16[0])) {
                                arrstring4 = var6_16[0].split(",");
                                Object var0_6 = var8_19;
                            }
                        }
                    }
                    if (TextUtils.isEmpty((CharSequence)var0_7) || arrstring4 == null || arrstring4.length == 0) continue;
                    for (void var6_17 : arrstring4) {
                        if (TextUtils.isEmpty((CharSequence)var6_17) || !var6_17.equals(string3)) continue;
                        return var0_7;
                    }
                }
                return var1_8;
            }
            return var1_8;
        }
        return var1_8;
    }

    public static TtsSpan createTtsSpan(String string2) {
        if (string2 == null) {
            return null;
        }
        Object object = PhoneNumberUtil.getInstance();
        PhoneNumberUtil phoneNumberUtil = null;
        try {
            object = object.parse((CharSequence)string2, null);
            phoneNumberUtil = object;
        }
        catch (NumberParseException numberParseException) {
            // empty catch block
        }
        object = new TtsSpan.TelephoneBuilder();
        if (phoneNumberUtil == null) {
            ((TtsSpan.TelephoneBuilder)object).setNumberParts(PhoneNumberUtils.splitAtNonNumerics(string2));
        } else {
            if (phoneNumberUtil.hasCountryCode()) {
                ((TtsSpan.TelephoneBuilder)object).setCountryCode(Integer.toString(phoneNumberUtil.getCountryCode()));
            }
            ((TtsSpan.TelephoneBuilder)object).setNumberParts(Long.toString(phoneNumberUtil.getNationalNumber()));
        }
        return ((TtsSpan.Builder)object).build();
    }

    public static CharSequence createTtsSpannable(CharSequence charSequence) {
        if (charSequence == null) {
            return null;
        }
        charSequence = Spannable.Factory.getInstance().newSpannable(charSequence);
        PhoneNumberUtils.addTtsSpan((Spannable)charSequence, 0, charSequence.length());
        return charSequence;
    }

    public static String extractNetworkPortion(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            int n2 = Character.digit(c, 10);
            if (n2 != -1) {
                stringBuilder.append(n2);
                continue;
            }
            if (c == '+') {
                String string3 = stringBuilder.toString();
                if (string3.length() != 0 && !string3.equals(CLIR_ON) && !string3.equals(CLIR_OFF)) continue;
                stringBuilder.append(c);
                continue;
            }
            if (PhoneNumberUtils.isDialable(c)) {
                stringBuilder.append(c);
                continue;
            }
            if (PhoneNumberUtils.isStartsPostDial(c)) break;
        }
        return stringBuilder.toString();
    }

    @UnsupportedAppUsage
    public static String extractNetworkPortionAlt(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            boolean bl2 = bl;
            if (c == '+') {
                if (bl) continue;
                bl2 = true;
            }
            if (PhoneNumberUtils.isDialable(c)) {
                stringBuilder.append(c);
                bl = bl2;
                continue;
            }
            bl = bl2;
            if (PhoneNumberUtils.isStartsPostDial(c)) break;
        }
        return stringBuilder.toString();
    }

    public static String extractPostDialPortion(String string2) {
        if (string2 == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = string2.length();
        for (int i = PhoneNumberUtils.indexOfLastNetworkChar((String)string2) + 1; i < n; ++i) {
            char c = string2.charAt(i);
            if (!PhoneNumberUtils.isNonSeparator(c)) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static int findDialableIndexFromPostDialStr(String string2) {
        for (int i = 0; i < string2.length(); ++i) {
            if (!PhoneNumberUtils.isReallyDialable(string2.charAt(i))) continue;
            return i;
        }
        return -1;
    }

    @Deprecated
    public static void formatJapaneseNumber(Editable editable) {
        JapanesePhoneNumberFormatter.format(editable);
    }

    @Deprecated
    public static void formatNanpNumber(Editable editable) {
        int n;
        int n2 = editable.length();
        if (n2 > "+1-nnn-nnn-nnnn".length()) {
            return;
        }
        if (n2 <= 5) {
            return;
        }
        CharSequence charSequence = editable.subSequence(0, n2);
        PhoneNumberUtils.removeDashes(editable);
        int n3 = editable.length();
        int[] arrn = new int[3];
        int n4 = 0;
        n2 = 1;
        int n5 = 0;
        for (n = 0; n < n3; ++n) {
            block19 : {
                block17 : {
                    block18 : {
                        char c = editable.charAt(n);
                        if (c == '+') break block17;
                        if (c == '-') break block18;
                        switch (c) {
                            default: {
                                break block19;
                            }
                            case '1': {
                                if (n5 == 0 || n2 == 2) {
                                    n2 = 3;
                                    break;
                                }
                            }
                            case '0': 
                            case '2': 
                            case '3': 
                            case '4': 
                            case '5': 
                            case '6': 
                            case '7': 
                            case '8': 
                            case '9': {
                                if (n2 == 2) {
                                    editable.replace(0, n3, charSequence);
                                    return;
                                }
                                if (n2 == 3) {
                                    n2 = n4 + 1;
                                    arrn[n4] = n;
                                } else if (n2 != 4 && (n5 == 3 || n5 == 6)) {
                                    n2 = n4 + 1;
                                    arrn[n4] = n;
                                } else {
                                    n2 = n4;
                                }
                                ++n5;
                                c = '\u0001';
                                n4 = n2;
                                n2 = c;
                                break;
                            }
                        }
                        continue;
                    }
                    n2 = 4;
                    continue;
                }
                if (n == 0) {
                    n2 = 2;
                    continue;
                }
            }
            editable.replace(0, n3, charSequence);
            return;
        }
        n2 = n4;
        if (n5 == 7) {
            n2 = n4 - 1;
        }
        for (n4 = 0; n4 < n2; ++n4) {
            n = arrn[n4];
            editable.replace(n + n4, n + n4, "-");
        }
        for (n2 = editable.length(); n2 > 0 && editable.charAt(n2 - 1) == '-'; --n2) {
            editable.delete(n2 - 1, n2);
        }
    }

    @Deprecated
    public static String formatNumber(String charSequence) {
        charSequence = new SpannableStringBuilder(charSequence);
        PhoneNumberUtils.formatNumber((Editable)charSequence, PhoneNumberUtils.getFormatTypeForLocale(Locale.getDefault()));
        return ((SpannableStringBuilder)charSequence).toString();
    }

    @Deprecated
    @UnsupportedAppUsage
    public static String formatNumber(String charSequence, int n) {
        charSequence = new SpannableStringBuilder(charSequence);
        PhoneNumberUtils.formatNumber((Editable)charSequence, n);
        return ((SpannableStringBuilder)charSequence).toString();
    }

    public static String formatNumber(String string2, String string3) {
        if (!string2.startsWith("#") && !string2.startsWith("*")) {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            Object var3_4 = null;
            try {
                string2 = phoneNumberUtil.parseAndKeepRawInput((CharSequence)string2, string3);
                string2 = KOREA_ISO_COUNTRY_CODE.equalsIgnoreCase(string3) && string2.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(KOREA_ISO_COUNTRY_CODE) && string2.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN ? phoneNumberUtil.format((Phonenumber.PhoneNumber)string2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL) : (JAPAN_ISO_COUNTRY_CODE.equalsIgnoreCase(string3) && string2.getCountryCode() == phoneNumberUtil.getCountryCodeForRegion(JAPAN_ISO_COUNTRY_CODE) && string2.getCountryCodeSource() == Phonenumber.PhoneNumber.CountryCodeSource.FROM_NUMBER_WITH_PLUS_SIGN ? phoneNumberUtil.format((Phonenumber.PhoneNumber)string2, PhoneNumberUtil.PhoneNumberFormat.NATIONAL) : phoneNumberUtil.formatInOriginalFormat((Phonenumber.PhoneNumber)string2, string3));
            }
            catch (NumberParseException numberParseException) {
                string2 = var3_4;
            }
            return string2;
        }
        return string2;
    }

    public static String formatNumber(String string2, String string3, String string4) {
        block9 : {
            int n;
            int n2 = string2.length();
            for (n = 0; n < n2; ++n) {
                if (PhoneNumberUtils.isDialable(string2.charAt(n))) continue;
                return string2;
            }
            Object object = PhoneNumberUtil.getInstance();
            String string5 = string4;
            if (string3 != null) {
                string5 = string4;
                if (string3.length() >= 2) {
                    string5 = string4;
                    if (string3.charAt(0) == '+') {
                        object = object.getRegionCodeForNumber(object.parse((CharSequence)string3, "ZZ"));
                        string5 = string4;
                        try {
                            if (!TextUtils.isEmpty((CharSequence)object)) {
                                n = PhoneNumberUtils.normalizeNumber(string2).indexOf(string3.substring(1));
                                string5 = string4;
                                if (n <= 0) {
                                    string5 = object;
                                }
                            }
                        }
                        catch (NumberParseException numberParseException) {
                            string5 = string4;
                        }
                    }
                }
            }
            if ((string3 = PhoneNumberUtils.formatNumber(string2, string5)) == null) break block9;
            string2 = string3;
        }
        return string2;
    }

    @Deprecated
    public static void formatNumber(Editable editable, int n) {
        int n2;
        n = n2 = n;
        if (editable.length() > 2) {
            n = n2;
            if (editable.charAt(0) == '+') {
                n = editable.charAt(1) == '1' ? 1 : (editable.length() >= 3 && editable.charAt(1) == '8' && editable.charAt(2) == '1' ? 2 : 0);
            }
        }
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    return;
                }
                PhoneNumberUtils.formatJapaneseNumber(editable);
                return;
            }
            PhoneNumberUtils.formatNanpNumber(editable);
            return;
        }
        PhoneNumberUtils.removeDashes(editable);
    }

    private static String formatNumberInternal(String string2, String string3, PhoneNumberUtil.PhoneNumberFormat phoneNumberFormat) {
        PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
        try {
            string2 = phoneNumberUtil.parse((CharSequence)string2, string3);
            if (phoneNumberUtil.isValidNumber((Phonenumber.PhoneNumber)string2)) {
                string2 = phoneNumberUtil.format((Phonenumber.PhoneNumber)string2, phoneNumberFormat);
                return string2;
            }
        }
        catch (NumberParseException numberParseException) {
            // empty catch block
        }
        return null;
    }

    public static String formatNumberToE164(String string2, String string3) {
        return PhoneNumberUtils.formatNumberInternal(string2, string3, PhoneNumberUtil.PhoneNumberFormat.E164);
    }

    public static String formatNumberToRFC3966(String string2, String string3) {
        return PhoneNumberUtils.formatNumberInternal(string2, string3, PhoneNumberUtil.PhoneNumberFormat.RFC3966);
    }

    private static String getCurrentIdp(boolean bl) {
        String string2 = bl ? NANP_IDP_STRING : SystemProperties.get("gsm.operator.idpstring", PLUS_SIGN_STRING);
        return string2;
    }

    private static int getDefaultVoiceSubId() {
        return SubscriptionManager.getDefaultVoiceSubscriptionId();
    }

    @Deprecated
    public static int getFormatTypeForLocale(Locale locale) {
        return PhoneNumberUtils.getFormatTypeFromCountryCode(locale.getCountry());
    }

    private static int getFormatTypeFromCountryCode(String string2) {
        int n = NANP_COUNTRIES.length;
        for (int i = 0; i < n; ++i) {
            if (NANP_COUNTRIES[i].compareToIgnoreCase(string2) != 0) continue;
            return 1;
        }
        if ("jp".compareToIgnoreCase(string2) == 0) {
            return 2;
        }
        return 0;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static String getNumberFromIntent(Intent var0, Context var1_2) {
        var2_4 = null;
        var3_5 = null;
        var4_6 = null;
        var5_7 = var0.getData();
        if (var5_7 == null) {
            return null;
        }
        var6_8 = var5_7.getScheme();
        if (var6_8.equals("tel") != false) return var5_7.getSchemeSpecificPart();
        if (var6_8.equals("sip")) {
            return var5_7.getSchemeSpecificPart();
        }
        if (var1_2 == null) {
            return null;
        }
        var0.resolveType((Context)var1_2);
        var0 = var5_7.getAuthority();
        var7_9 = "contacts".equals(var0) != false ? "number" : ("com.android.contacts".equals(var0) != false ? "data1" : null);
        var0 = null;
        var6_8 = null;
        var5_7 = var1_2.getContentResolver().query((Uri)var5_7, new String[]{var7_9}, null, null, null);
        var1_2 = var4_6;
        if (var5_7 != null) {
            var1_2 = var4_6;
            var6_8 = var5_7;
            var0 = var5_7;
            if (var5_7.moveToFirst()) {
                var6_8 = var5_7;
                var0 = var5_7;
                var1_2 = var5_7.getString(var5_7.getColumnIndex(var7_9));
            }
        }
        var6_8 = var1_2;
        if (var5_7 == null) return var6_8;
        var0 = var5_7;
lbl33: // 2 sources:
        do {
            var0.close();
            return var1_2;
            break;
        } while (true);
        {
            catch (Throwable var0_1) {
            }
            catch (RuntimeException var1_3) {}
            var6_8 = var0;
            {
                Rlog.e("PhoneNumberUtils", "Error getting phone number.", var1_3);
                var6_8 = var3_5;
                if (var0 == null) return var6_8;
                var1_2 = var2_4;
                ** continue;
            }
        }
        if (var6_8 == null) throw var0_1;
        var6_8.close();
        throw var0_1;
    }

    public static String getStrippedReversed(String string2) {
        if ((string2 = PhoneNumberUtils.extractNetworkPortionAlt(string2)) == null) {
            return null;
        }
        return PhoneNumberUtils.internalGetStrippedReversed(string2, string2.length());
    }

    @UnsupportedAppUsage
    public static String getUsernameFromUriNumber(String string2) {
        int n;
        int n2 = n = string2.indexOf(64);
        if (n < 0) {
            n2 = string2.indexOf("%40");
        }
        n = n2;
        if (n2 < 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("getUsernameFromUriNumber: no delimiter found in SIP addr '");
            stringBuilder.append(string2);
            stringBuilder.append("'");
            Rlog.w(LOG_TAG, stringBuilder.toString());
            n = string2.length();
        }
        return string2.substring(0, n);
    }

    private static int indexOfLastNetworkChar(String string2) {
        int n = string2.length();
        int n2 = PhoneNumberUtils.minPositive(string2.indexOf(44), string2.indexOf(59));
        if (n2 < 0) {
            return n - 1;
        }
        return n2 - 1;
    }

    private static void internalCalledPartyBCDFragmentToString(StringBuilder stringBuilder, byte[] arrby, int n, int n2, int n3) {
        for (int i = n; i < n2 + n; ++i) {
            char c = PhoneNumberUtils.bcdToChar((byte)(arrby[i] & 15), n3);
            if (c == '\u0000') {
                return;
            }
            stringBuilder.append(c);
            byte by = (byte)(arrby[i] >> 4 & 15);
            if (by == 15 && i + 1 == n2 + n) break;
            c = PhoneNumberUtils.bcdToChar(by, n3);
            if (c == '\u0000') {
                return;
            }
            stringBuilder.append(c);
        }
    }

    private static String internalGetStrippedReversed(String string2, int n) {
        if (string2 == null) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder(n);
        int n2 = string2.length();
        for (int i = n2 - 1; i >= 0 && n2 - i <= n; --i) {
            stringBuilder.append(string2.charAt(i));
        }
        return stringBuilder.toString();
    }

    public static final boolean is12Key(char c) {
        boolean bl = c >= '0' && c <= '9' || c == '*' || c == '#';
        return bl;
    }

    private static boolean isCountryCallingCode(int n) {
        boolean bl = n > 0 && n < CCC_LENGTH && COUNTRY_CALLING_CALL[n];
        return bl;
    }

    public static final boolean isDialable(char c) {
        boolean bl = c >= '0' && c <= '9' || c == '*' || c == '#' || c == '+' || c == 'N';
        return bl;
    }

    private static boolean isDialable(String string2) {
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            if (PhoneNumberUtils.isDialable(string2.charAt(i))) continue;
            return false;
        }
        return true;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isEmergencyNumber(int n, String string2) {
        return PhoneNumberUtils.isEmergencyNumberInternal(n, string2, true);
    }

    @Deprecated
    public static boolean isEmergencyNumber(int n, String string2, String string3) {
        return PhoneNumberUtils.isEmergencyNumberInternal(n, string2, string3, true);
    }

    @Deprecated
    public static boolean isEmergencyNumber(String string2) {
        return PhoneNumberUtils.isEmergencyNumber(PhoneNumberUtils.getDefaultVoiceSubId(), string2);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isEmergencyNumber(String string2, String string3) {
        return PhoneNumberUtils.isEmergencyNumber(PhoneNumberUtils.getDefaultVoiceSubId(), string2, string3);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static boolean isEmergencyNumberInternal(int n, String string2, String charSequence, boolean bl) {
        if (!bl) return TelephonyManager.getDefault().isPotentialEmergencyNumber(string2);
        try {
            return TelephonyManager.getDefault().isEmergencyNumber(string2);
        }
        catch (RuntimeException runtimeException) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("isEmergencyNumberInternal: RuntimeException: ");
            ((StringBuilder)charSequence).append(runtimeException);
            Rlog.e(LOG_TAG, ((StringBuilder)charSequence).toString());
            return false;
        }
    }

    private static boolean isEmergencyNumberInternal(int n, String string2, boolean bl) {
        return PhoneNumberUtils.isEmergencyNumberInternal(n, string2, null, bl);
    }

    private static boolean isEmergencyNumberInternal(String string2, String string3, boolean bl) {
        return PhoneNumberUtils.isEmergencyNumberInternal(PhoneNumberUtils.getDefaultVoiceSubId(), string2, string3, bl);
    }

    private static boolean isEmergencyNumberInternal(String string2, boolean bl) {
        return PhoneNumberUtils.isEmergencyNumberInternal(PhoneNumberUtils.getDefaultVoiceSubId(), string2, bl);
    }

    public static boolean isGlobalPhoneNumber(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return false;
        }
        return GLOBAL_PHONE_NUMBER_PATTERN.matcher(string2).matches();
    }

    public static boolean isISODigit(char c) {
        boolean bl = c >= '0' && c <= '9';
        return bl;
    }

    public static boolean isInternationalNumber(String string2, String string3) {
        boolean bl = TextUtils.isEmpty(string2);
        boolean bl2 = false;
        if (bl) {
            return false;
        }
        if (!string2.startsWith("#") && !string2.startsWith("*")) {
            PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
            try {
                int n = phoneNumberUtil.parseAndKeepRawInput((CharSequence)string2, string3).getCountryCode();
                int n2 = phoneNumberUtil.getCountryCodeForRegion(string3);
                if (n != n2) {
                    bl2 = true;
                }
                return bl2;
            }
            catch (NumberParseException numberParseException) {
                return false;
            }
        }
        return false;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isLocalEmergencyNumber(Context context, int n, String string2) {
        return PhoneNumberUtils.isLocalEmergencyNumberInternal(n, string2, context, true);
    }

    @Deprecated
    public static boolean isLocalEmergencyNumber(Context context, String string2) {
        return PhoneNumberUtils.isLocalEmergencyNumber(context, PhoneNumberUtils.getDefaultVoiceSubId(), string2);
    }

    private static boolean isLocalEmergencyNumberInternal(int n, String string2, Context object, boolean bl) {
        Object object2 = (CountryDetector)((Context)object).getSystemService("country_detector");
        if (object2 != null && ((CountryDetector)object2).detectCountry() != null) {
            object = ((CountryDetector)object2).detectCountry().getCountryIso();
        } else {
            object = object.getResources().getConfiguration().locale.getCountry();
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("No CountryDetector; falling back to countryIso based on locale: ");
            ((StringBuilder)object2).append((String)object);
            Rlog.w(LOG_TAG, ((StringBuilder)object2).toString());
        }
        return PhoneNumberUtils.isEmergencyNumberInternal(n, string2, (String)object, bl);
    }

    private static boolean isLocalEmergencyNumberInternal(String string2, Context context, boolean bl) {
        return PhoneNumberUtils.isLocalEmergencyNumberInternal(PhoneNumberUtils.getDefaultVoiceSubId(), string2, context, bl);
    }

    @UnsupportedAppUsage
    public static boolean isNanp(String string2) {
        boolean bl;
        boolean bl2 = false;
        if (string2 != null) {
            bl = bl2;
            if (string2.length() == 10) {
                bl = bl2;
                if (PhoneNumberUtils.isTwoToNine(string2.charAt(0))) {
                    bl = bl2;
                    if (PhoneNumberUtils.isTwoToNine(string2.charAt(3))) {
                        bl2 = true;
                        int n = 1;
                        do {
                            bl = bl2;
                            if (n >= 10) break;
                            if (!PhoneNumberUtils.isISODigit(string2.charAt(n))) {
                                bl = false;
                                break;
                            }
                            ++n;
                        } while (true);
                    }
                }
            }
        } else {
            Rlog.e("isNanp: null dialStr passed in", string2);
            bl = bl2;
        }
        return bl;
    }

    public static final boolean isNonSeparator(char c) {
        boolean bl = c >= '0' && c <= '9' || c == '*' || c == '#' || c == '+' || c == 'N' || c == ';' || c == ',';
        return bl;
    }

    private static boolean isNonSeparator(String string2) {
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            if (PhoneNumberUtils.isNonSeparator(string2.charAt(i))) continue;
            return false;
        }
        return true;
    }

    private static boolean isOneNanp(String string2) {
        boolean bl = false;
        boolean bl2 = false;
        if (string2 != null) {
            String string3 = string2.substring(1);
            bl = bl2;
            if (string2.charAt(0) == '1') {
                bl = bl2;
                if (PhoneNumberUtils.isNanp(string3)) {
                    bl = true;
                }
            }
        } else {
            Rlog.e("isOneNanp: null dialStr passed in", string2);
        }
        return bl;
    }

    private static boolean isPause(char c) {
        boolean bl = c == 'p' || c == 'P';
        return bl;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isPotentialEmergencyNumber(int n, String string2) {
        return PhoneNumberUtils.isEmergencyNumberInternal(n, string2, false);
    }

    @Deprecated
    public static boolean isPotentialEmergencyNumber(int n, String string2, String string3) {
        return PhoneNumberUtils.isEmergencyNumberInternal(n, string2, string3, false);
    }

    @Deprecated
    public static boolean isPotentialEmergencyNumber(String string2) {
        return PhoneNumberUtils.isPotentialEmergencyNumber(PhoneNumberUtils.getDefaultVoiceSubId(), string2);
    }

    @Deprecated
    public static boolean isPotentialEmergencyNumber(String string2, String string3) {
        return PhoneNumberUtils.isPotentialEmergencyNumber(PhoneNumberUtils.getDefaultVoiceSubId(), string2, string3);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isPotentialLocalEmergencyNumber(Context context, int n, String string2) {
        return PhoneNumberUtils.isLocalEmergencyNumberInternal(n, string2, context, false);
    }

    @Deprecated
    @UnsupportedAppUsage
    public static boolean isPotentialLocalEmergencyNumber(Context context, String string2) {
        return PhoneNumberUtils.isPotentialLocalEmergencyNumber(context, PhoneNumberUtils.getDefaultVoiceSubId(), string2);
    }

    public static final boolean isReallyDialable(char c) {
        boolean bl = c >= '0' && c <= '9' || c == '*' || c == '#' || c == '+';
        return bl;
    }

    private static boolean isSeparator(char c) {
        boolean bl = !(PhoneNumberUtils.isDialable(c) || 'a' <= c && c <= 'z' || 'A' <= c && c <= 'Z');
        return bl;
    }

    public static final boolean isStartsPostDial(char c) {
        boolean bl = c == ',' || c == ';';
        return bl;
    }

    private static boolean isToneWait(char c) {
        boolean bl = c == 'w' || c == 'W';
        return bl;
    }

    private static boolean isTwoToNine(char c) {
        return c >= '2' && c <= '9';
    }

    @UnsupportedAppUsage
    public static boolean isUriNumber(String string2) {
        boolean bl = string2 != null && (string2.contains("@") || string2.contains("%40"));
        return bl;
    }

    public static boolean isVoiceMailNumber(int n, String string2) {
        return PhoneNumberUtils.isVoiceMailNumber(null, n, string2);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public static boolean isVoiceMailNumber(Context var0, int var1_2, String var2_3) {
        var3_4 = false;
        if (var0 != null) ** GOTO lbl6
        try {
            block6 : {
                var4_5 = TelephonyManager.getDefault();
                break block6;
lbl6: // 1 sources:
                var4_5 = TelephonyManager.from((Context)var0);
            }
            var5_6 = var4_5.getVoiceMailNumber(var1_2);
            var4_5 = var4_5.getLine1Number(var1_2);
        }
        catch (SecurityException var0_1) {
            return false;
        }
        var2_3 = PhoneNumberUtils.extractNetworkPortionAlt(var2_3);
        if (TextUtils.isEmpty(var2_3)) {
            return false;
        }
        var7_8 = var6_7 = false;
        if (var0 != null) {
            var0 = (CarrierConfigManager)var0.getSystemService("carrier_config");
            var7_8 = var6_7;
            if (var0 != null) {
                var0 = var0.getConfigForSubId(var1_2);
                var7_8 = var6_7;
                if (var0 != null) {
                    var7_8 = var0.getBoolean("mdn_is_additional_voicemail_number_bool");
                }
            }
        }
        if (var7_8 == false) return PhoneNumberUtils.compare(var2_3, var5_6);
        if (PhoneNumberUtils.compare(var2_3, var5_6) != false) return true;
        var7_8 = var3_4;
        if (PhoneNumberUtils.compare(var2_3, (String)var4_5) == false) return var7_8;
        return true;
    }

    public static boolean isVoiceMailNumber(String string2) {
        return PhoneNumberUtils.isVoiceMailNumber(SubscriptionManager.getDefaultSubscriptionId(), string2);
    }

    public static boolean isWellFormedSmsAddress(String string2) {
        boolean bl = !(string2 = PhoneNumberUtils.extractNetworkPortion(string2)).equals(PLUS_SIGN_STRING) && !TextUtils.isEmpty(string2) && PhoneNumberUtils.isDialable(string2);
        return bl;
    }

    private static void log(String string2) {
        Rlog.d(LOG_TAG, string2);
    }

    private static boolean matchIntlPrefix(String string2, int n) {
        boolean bl;
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (n2 != 0) {
                if (n2 != 2) {
                    if (n2 != 4) {
                        if (!PhoneNumberUtils.isNonSeparator(c)) continue;
                        return false;
                    }
                    if (c == '1') {
                        n2 = 5;
                        continue;
                    }
                    if (!PhoneNumberUtils.isNonSeparator(c)) continue;
                    return false;
                }
                if (c == '0') {
                    n2 = 3;
                    continue;
                }
                if (c == '1') {
                    n2 = 4;
                    continue;
                }
                if (!PhoneNumberUtils.isNonSeparator(c)) continue;
                return false;
            }
            if (c == '+') {
                n2 = 1;
                continue;
            }
            if (c == '0') {
                n2 = 2;
                continue;
            }
            if (!PhoneNumberUtils.isNonSeparator(c)) continue;
            return false;
        }
        boolean bl2 = bl = true;
        if (n2 != 1) {
            bl2 = bl;
            if (n2 != 3) {
                bl2 = n2 == 5 ? bl : false;
            }
        }
        return bl2;
    }

    private static boolean matchIntlPrefixAndCC(String string2, int n) {
        boolean bl;
        int n2 = 0;
        int n3 = 0;
        do {
            bl = false;
            if (n3 >= n) break;
            char c = string2.charAt(n3);
            switch (n2) {
                default: {
                    if (!PhoneNumberUtils.isNonSeparator(c)) break;
                    return false;
                }
                case 6: 
                case 7: {
                    if (PhoneNumberUtils.isISODigit(c)) {
                        ++n2;
                        break;
                    }
                    if (!PhoneNumberUtils.isNonSeparator(c)) break;
                    return false;
                }
                case 4: {
                    if (c == '1') {
                        n2 = 5;
                        break;
                    }
                    if (!PhoneNumberUtils.isNonSeparator(c)) break;
                    return false;
                }
                case 2: {
                    if (c == '0') {
                        n2 = 3;
                        break;
                    }
                    if (c == '1') {
                        n2 = 4;
                        break;
                    }
                    if (!PhoneNumberUtils.isNonSeparator(c)) break;
                    return false;
                }
                case 1: 
                case 3: 
                case 5: {
                    if (PhoneNumberUtils.isISODigit(c)) {
                        n2 = 6;
                        break;
                    }
                    if (!PhoneNumberUtils.isNonSeparator(c)) break;
                    return false;
                }
                case 0: {
                    if (c == '+') {
                        n2 = 1;
                        break;
                    }
                    if (c == '0') {
                        n2 = 2;
                        break;
                    }
                    if (!PhoneNumberUtils.isNonSeparator(c)) break;
                    return false;
                }
            }
            ++n3;
        } while (true);
        if (n2 == 6 || n2 == 7 || n2 == 8) {
            bl = true;
        }
        return bl;
    }

    private static boolean matchTrunkPrefix(String string2, int n) {
        boolean bl = false;
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            if (c == '0' && !bl) {
                bl = true;
                continue;
            }
            if (!PhoneNumberUtils.isNonSeparator(c)) continue;
            return false;
        }
        return bl;
    }

    private static int minPositive(int n, int n2) {
        if (n >= 0 && n2 >= 0) {
            if (n >= n2) {
                n = n2;
            }
            return n;
        }
        if (n >= 0) {
            return n;
        }
        if (n2 >= 0) {
            return n2;
        }
        return -1;
    }

    public static byte[] networkPortionToCalledPartyBCD(String string2) {
        return PhoneNumberUtils.numberToCalledPartyBCDHelper(PhoneNumberUtils.extractNetworkPortion(string2), false, 1);
    }

    public static byte[] networkPortionToCalledPartyBCDWithLength(String string2) {
        return PhoneNumberUtils.numberToCalledPartyBCDHelper(PhoneNumberUtils.extractNetworkPortion(string2), true, 1);
    }

    public static String normalizeNumber(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        int n = string2.length();
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            int n2 = Character.digit(c, 10);
            if (n2 != -1) {
                stringBuilder.append(n2);
                continue;
            }
            if (stringBuilder.length() == 0 && c == '+') {
                stringBuilder.append(c);
                continue;
            }
            if ((c < 'a' || c > 'z') && (c < 'A' || c > 'Z')) continue;
            return PhoneNumberUtils.normalizeNumber(PhoneNumberUtils.convertKeypadLettersToDigits(string2));
        }
        return stringBuilder.toString();
    }

    @Deprecated
    public static byte[] numberToCalledPartyBCD(String string2) {
        return PhoneNumberUtils.numberToCalledPartyBCD(string2, 1);
    }

    public static byte[] numberToCalledPartyBCD(String string2, int n) {
        return PhoneNumberUtils.numberToCalledPartyBCDHelper(string2, false, n);
    }

    private static byte[] numberToCalledPartyBCDHelper(String string2, boolean bl, int n) {
        int n2;
        int n3 = n2 = string2.length();
        int n4 = string2.indexOf(43) != -1 ? 1 : 0;
        int n5 = n3;
        if (n4 != 0) {
            n5 = n3 - 1;
        }
        if (n5 == 0) {
            return null;
        }
        n5 = (n5 + 1) / 2;
        n3 = 1;
        if (bl) {
            n3 = 1 + 1;
        }
        int n6 = n5 + n3;
        byte[] arrby = new byte[n6];
        int n7 = 0;
        for (n5 = 0; n5 < n2; ++n5) {
            char c = string2.charAt(n5);
            if (c == '+') continue;
            int n8 = (n7 & 1) == 1 ? 4 : 0;
            int n9 = (n7 >> 1) + n3;
            byte by = arrby[n9];
            arrby[n9] = (byte)((byte)((PhoneNumberUtils.charToBCD(c, n) & 15) << n8) | by);
            ++n7;
        }
        if (n7 & true) {
            n = (n7 >> 1) + n3;
            arrby[n] = (byte)(arrby[n] | 240);
        }
        n = 0;
        if (bl) {
            arrby[0] = (byte)(n6 - 1);
            n = 0 + 1;
        }
        n4 = n4 != 0 ? 145 : 129;
        arrby[n] = (byte)n4;
        return arrby;
    }

    private static String processPlusCode(String string2, boolean bl) {
        String string3;
        String string4 = string3 = string2;
        if (string2 != null) {
            string4 = string3;
            if (string2.charAt(0) == '+') {
                string4 = string3;
                if (string2.length() > 1) {
                    string4 = string2.substring(1);
                    if (!bl || !PhoneNumberUtils.isOneNanp(string4)) {
                        string4 = string2.replaceFirst("[+]", PhoneNumberUtils.getCurrentIdp(bl));
                    }
                }
            }
        }
        return string4;
    }

    private static void removeDashes(Editable editable) {
        int n = 0;
        while (n < editable.length()) {
            if (editable.charAt(n) == '-') {
                editable.delete(n, n + 1);
                continue;
            }
            ++n;
        }
    }

    public static String replaceUnicodeDigits(String arrc) {
        StringBuilder stringBuilder = new StringBuilder(arrc.length());
        for (char c : arrc.toCharArray()) {
            int n = Character.digit(c, 10);
            if (n != -1) {
                stringBuilder.append(n);
                continue;
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    private static String splitAtNonNumerics(CharSequence charSequence) {
        StringBuilder stringBuilder = new StringBuilder(charSequence.length());
        int n = 0;
        do {
            int n2 = charSequence.length();
            Object object = " ";
            if (n >= n2) break;
            if (PhoneNumberUtils.is12Key(charSequence.charAt(n))) {
                object = Character.valueOf(charSequence.charAt(n));
            }
            stringBuilder.append(object);
            ++n;
        } while (true);
        return stringBuilder.toString().replaceAll(" +", " ").trim();
    }

    public static String stringFromStringAndTOA(String string2, int n) {
        if (string2 == null) {
            return null;
        }
        if (n == 145 && string2.length() > 0 && string2.charAt(0) != '+') {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(PLUS_SIGN_STRING);
            stringBuilder.append(string2);
            return stringBuilder.toString();
        }
        return string2;
    }

    public static String stripSeparators(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        for (int i = 0; i < n; ++i) {
            char c = string2.charAt(i);
            int n2 = Character.digit(c, 10);
            if (n2 != -1) {
                stringBuilder.append(n2);
                continue;
            }
            if (!PhoneNumberUtils.isNonSeparator(c)) continue;
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    public static String toCallerIDMinMatch(String string2) {
        return PhoneNumberUtils.internalGetStrippedReversed(PhoneNumberUtils.extractNetworkPortionAlt(string2), 7);
    }

    public static int toaFromString(String string2) {
        if (string2 != null && string2.length() > 0 && string2.charAt(0) == '+') {
            return 145;
        }
        return 129;
    }

    private static CountryCallingCodeAndNewIndex tryGetCountryCallingCodeAndNewIndex(String string2, boolean bl) {
        int n = 0;
        int n2 = 0;
        int n3 = string2.length();
        block8 : for (int i = 0; i < n3; ++i) {
            char c = string2.charAt(i);
            switch (n) {
                default: {
                    return null;
                }
                case 9: {
                    if (c == '6') {
                        return new CountryCallingCodeAndNewIndex(66, i + 1);
                    }
                    return null;
                }
                case 8: {
                    if (c == '6') {
                        n = 9;
                        continue block8;
                    }
                    if (!PhoneNumberUtils.isDialable(c)) continue block8;
                    return null;
                }
                case 4: {
                    if (c == '1') {
                        n = 5;
                        continue block8;
                    }
                    if (!PhoneNumberUtils.isDialable(c)) continue block8;
                    return null;
                }
                case 2: {
                    if (c == '0') {
                        n = 3;
                        continue block8;
                    }
                    if (c == '1') {
                        n = 4;
                        continue block8;
                    }
                    if (!PhoneNumberUtils.isDialable(c)) continue block8;
                    return null;
                }
                case 1: 
                case 3: 
                case 5: 
                case 6: 
                case 7: {
                    int n4 = PhoneNumberUtils.tryGetISODigit(c);
                    if (n4 > 0) {
                        if ((n2 = n2 * 10 + n4) < 100 && !PhoneNumberUtils.isCountryCallingCode(n2)) {
                            if (n != 1 && n != 3 && n != 5) {
                                ++n;
                                continue block8;
                            }
                            n = 6;
                            continue block8;
                        }
                        return new CountryCallingCodeAndNewIndex(n2, i + 1);
                    }
                    if (!PhoneNumberUtils.isDialable(c)) continue block8;
                    return null;
                }
                case 0: {
                    if (c == '+') {
                        n = 1;
                        continue block8;
                    }
                    if (c == '0') {
                        n = 2;
                        continue block8;
                    }
                    if (c == '1') {
                        if (bl) {
                            n = 8;
                            continue block8;
                        }
                        return null;
                    }
                    if (!PhoneNumberUtils.isDialable(c)) continue block8;
                    return null;
                }
            }
        }
        return null;
    }

    private static int tryGetISODigit(char c) {
        if ('0' <= c && c <= '9') {
            return c - 48;
        }
        return -1;
    }

    private static int tryGetTrunkPrefixOmittedIndex(String string2, int n) {
        int n2 = string2.length();
        while (n < n2) {
            char c = string2.charAt(n);
            if (PhoneNumberUtils.tryGetISODigit(c) >= 0) {
                return n + 1;
            }
            if (PhoneNumberUtils.isDialable(c)) {
                return -1;
            }
            ++n;
        }
        return -1;
    }

    @Deprecated
    @UnsupportedAppUsage
    public static CharSequence ttsSpanAsPhoneNumber(CharSequence charSequence) {
        return PhoneNumberUtils.createTtsSpannable(charSequence);
    }

    @Deprecated
    public static void ttsSpanAsPhoneNumber(Spannable spannable, int n, int n2) {
        PhoneNumberUtils.addTtsSpan(spannable, n, n2);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BcdExtendType {
    }

    private static class CountryCallingCodeAndNewIndex {
        public final int countryCallingCode;
        public final int newIndex;

        public CountryCallingCodeAndNewIndex(int n, int n2) {
            this.countryCallingCode = n;
            this.newIndex = n2;
        }
    }

}

