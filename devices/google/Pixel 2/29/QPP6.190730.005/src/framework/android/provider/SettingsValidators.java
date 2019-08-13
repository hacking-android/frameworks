/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.provider.-$
 *  android.provider.-$$Lambda
 *  android.provider.-$$Lambda$SettingsValidators
 *  android.provider.-$$Lambda$SettingsValidators$0swA5rhyuVHADD7MEwgs2ihTCGM
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package android.provider;

import android.content.ComponentName;
import android.net.Uri;
import android.provider.-$;
import android.provider._$$Lambda$SettingsValidators$0swA5rhyuVHADD7MEwgs2ihTCGM;
import android.text.TextUtils;
import com.android.internal.util.ArrayUtils;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public class SettingsValidators {
    public static final Validator ANY_INTEGER_VALIDATOR;
    public static final Validator ANY_STRING_VALIDATOR;
    public static final Validator BOOLEAN_VALIDATOR;
    public static final Validator COMPONENT_NAME_VALIDATOR;
    public static final Validator JSON_OBJECT_VALIDATOR;
    public static final Validator LENIENT_IP_ADDRESS_VALIDATOR;
    public static final Validator LOCALE_VALIDATOR;
    public static final Validator NON_NEGATIVE_INTEGER_VALIDATOR;
    public static final Validator NULLABLE_COMPONENT_NAME_VALIDATOR;
    public static final Validator PACKAGE_NAME_VALIDATOR;
    public static final Validator URI_VALIDATOR;

    static {
        BOOLEAN_VALIDATOR = new DiscreteValueValidator(new String[]{"0", "1"});
        ANY_STRING_VALIDATOR = new Validator(){

            @Override
            public boolean validate(String string2) {
                return true;
            }
        };
        NON_NEGATIVE_INTEGER_VALIDATOR = new Validator(){

            @Override
            public boolean validate(String string2) {
                boolean bl = false;
                try {
                    int n = Integer.parseInt(string2);
                    if (n >= 0) {
                        bl = true;
                    }
                    return bl;
                }
                catch (NumberFormatException numberFormatException) {
                    return false;
                }
            }
        };
        ANY_INTEGER_VALIDATOR = new Validator(){

            @Override
            public boolean validate(String string2) {
                try {
                    Integer.parseInt(string2);
                    return true;
                }
                catch (NumberFormatException numberFormatException) {
                    return false;
                }
            }
        };
        URI_VALIDATOR = new Validator(){

            @Override
            public boolean validate(String string2) {
                try {
                    Uri.decode(string2);
                    return true;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    return false;
                }
            }
        };
        COMPONENT_NAME_VALIDATOR = new Validator(){

            @Override
            public boolean validate(String string2) {
                boolean bl = string2 != null && ComponentName.unflattenFromString(string2) != null;
                return bl;
            }
        };
        NULLABLE_COMPONENT_NAME_VALIDATOR = new Validator(){

            @Override
            public boolean validate(String string2) {
                boolean bl = string2 == null || COMPONENT_NAME_VALIDATOR.validate(string2);
                return bl;
            }
        };
        PACKAGE_NAME_VALIDATOR = new Validator(){

            private boolean isStringPackageName(String arrstring) {
                boolean bl;
                int n = 0;
                if (arrstring == null) {
                    return false;
                }
                arrstring = arrstring.split("\\.");
                boolean bl2 = true;
                int n2 = arrstring.length;
                do {
                    bl = bl2;
                    if (n >= n2) break;
                    if (!(bl2 &= this.isSubpartValidForPackageName(arrstring[n]))) {
                        bl = bl2;
                        break;
                    }
                    ++n;
                } while (true);
                return bl;
            }

            private boolean isSubpartValidForPackageName(String string2) {
                boolean bl;
                if (string2.length() == 0) {
                    return false;
                }
                boolean bl2 = Character.isLetter(string2.charAt(0));
                int n = 1;
                do {
                    bl = bl2;
                    if (n >= string2.length()) break;
                    boolean bl3 = Character.isLetterOrDigit(string2.charAt(n)) || string2.charAt(n) == '_';
                    if (!(bl2 &= bl3)) {
                        bl = bl2;
                        break;
                    }
                    ++n;
                } while (true);
                return bl;
            }

            @Override
            public boolean validate(String string2) {
                boolean bl = string2 != null && this.isStringPackageName(string2);
                return bl;
            }
        };
        LENIENT_IP_ADDRESS_VALIDATOR = new Validator(){
            private static final int MAX_IPV6_LENGTH = 45;

            @Override
            public boolean validate(String string2) {
                boolean bl = false;
                if (string2 == null) {
                    return false;
                }
                if (string2.length() <= 45) {
                    bl = true;
                }
                return bl;
            }
        };
        LOCALE_VALIDATOR = new Validator(){

            @Override
            public boolean validate(String string2) {
                if (string2 == null) {
                    return false;
                }
                Locale[] arrlocale = Locale.getAvailableLocales();
                int n = arrlocale.length;
                for (int i = 0; i < n; ++i) {
                    if (!string2.equals(arrlocale[i].toString())) continue;
                    return true;
                }
                return false;
            }
        };
        JSON_OBJECT_VALIDATOR = _$$Lambda$SettingsValidators$0swA5rhyuVHADD7MEwgs2ihTCGM.INSTANCE;
    }

    static /* synthetic */ boolean lambda$static$0(String string2) {
        if (TextUtils.isEmpty(string2)) {
            return false;
        }
        try {
            new JSONObject(string2);
            return true;
        }
        catch (JSONException jSONException) {
            return false;
        }
    }

    public static final class ComponentNameListValidator
    implements Validator {
        private final String mSeparator;

        public ComponentNameListValidator(String string2) {
            this.mSeparator = string2;
        }

        @Override
        public boolean validate(String arrstring) {
            if (arrstring == null) {
                return false;
            }
            for (String string2 : arrstring.split(this.mSeparator)) {
                if (COMPONENT_NAME_VALIDATOR.validate(string2)) continue;
                return false;
            }
            return true;
        }
    }

    public static final class DiscreteValueValidator
    implements Validator {
        private final String[] mValues;

        public DiscreteValueValidator(String[] arrstring) {
            this.mValues = arrstring;
        }

        @Override
        public boolean validate(String string2) {
            return ArrayUtils.contains(this.mValues, string2);
        }
    }

    public static final class InclusiveFloatRangeValidator
    implements Validator {
        private final float mMax;
        private final float mMin;

        public InclusiveFloatRangeValidator(float f, float f2) {
            this.mMin = f;
            this.mMax = f2;
        }

        @Override
        public boolean validate(String string2) {
            boolean bl;
            block4 : {
                float f;
                boolean bl2 = false;
                float f2 = Float.parseFloat(string2);
                bl = bl2;
                try {
                    if (!(f2 >= this.mMin)) break block4;
                    f = this.mMax;
                    bl = bl2;
                }
                catch (NullPointerException | NumberFormatException runtimeException) {
                    return false;
                }
                if (f2 <= f) {
                    bl = true;
                }
            }
            return bl;
        }
    }

    public static final class InclusiveIntegerRangeValidator
    implements Validator {
        private final int mMax;
        private final int mMin;

        public InclusiveIntegerRangeValidator(int n, int n2) {
            this.mMin = n;
            this.mMax = n2;
        }

        @Override
        public boolean validate(String string2) {
            boolean bl;
            int n;
            boolean bl2 = false;
            try {
                n = Integer.parseInt(string2);
                bl = bl2;
            }
            catch (NumberFormatException numberFormatException) {
                return false;
            }
            if (n >= this.mMin) {
                int n2 = this.mMax;
                bl = bl2;
                if (n <= n2) {
                    bl = true;
                }
            }
            return bl;
        }
    }

    public static final class PackageNameListValidator
    implements Validator {
        private final String mSeparator;

        public PackageNameListValidator(String string2) {
            this.mSeparator = string2;
        }

        @Override
        public boolean validate(String string22) {
            if (string22 == null) {
                return false;
            }
            for (String string22 : string22.split(this.mSeparator)) {
                if (PACKAGE_NAME_VALIDATOR.validate(string22)) continue;
                return false;
            }
            return true;
        }
    }

    public static interface Validator {
        public boolean validate(String var1);
    }

}

