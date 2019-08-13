/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package com.android.internal.telephony;

import android.os.Bundle;

public class VisualVoicemailSmsParser {
    private static final String[] ALLOWED_ALTERNATIVE_FORMAT_EVENT = new String[]{"MBOXUPDATE", "UNRECOGNIZED"};

    private static boolean isAllowedAlternativeFormatEvent(String string) {
        String[] arrstring = ALLOWED_ALTERNATIVE_FORMAT_EVENT;
        int n = arrstring.length;
        for (int i = 0; i < n; ++i) {
            if (!arrstring[i].equals(string)) continue;
            return true;
        }
        return false;
    }

    public static WrappedMessageData parse(String object, String string) {
        block9 : {
            int n;
            int n2;
            block8 : {
                block7 : {
                    block6 : {
                        try {
                            if (string.startsWith((String)object)) break block6;
                            return null;
                        }
                        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                            return null;
                        }
                    }
                    n = ((String)object).length();
                    if (string.charAt(n) == ':') break block7;
                    return null;
                }
                n2 = string.indexOf(":", n + 1);
                if (n2 != -1) break block8;
                return null;
            }
            object = string.substring(n + 1, n2);
            string = VisualVoicemailSmsParser.parseSmsBody(string.substring(n2 + 1));
            if (string != null) break block9;
            return null;
        }
        object = new WrappedMessageData((String)object, (Bundle)string);
        return object;
    }

    public static WrappedMessageData parseAlternativeFormat(String object) {
        String string;
        block7 : {
            int n;
            block6 : {
                block5 : {
                    try {
                        n = ((String)object).indexOf("?");
                        if (n != -1) break block5;
                        return null;
                    }
                    catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                        return null;
                    }
                }
                string = ((String)object).substring(0, n);
                if (VisualVoicemailSmsParser.isAllowedAlternativeFormatEvent(string)) break block6;
                return null;
            }
            object = VisualVoicemailSmsParser.parseSmsBody(((String)object).substring(n + 1));
            if (object != null) break block7;
            return null;
        }
        object = new WrappedMessageData(string, (Bundle)object);
        return object;
    }

    private static Bundle parseSmsBody(String string2) {
        Bundle bundle = new Bundle();
        for (String string2 : string2.split(";")) {
            if (string2.length() == 0) continue;
            int n = string2.indexOf("=");
            if (n != -1 && n != 0) {
                bundle.putString(string2.substring(0, n), string2.substring(n + 1));
                continue;
            }
            return null;
        }
        return bundle;
    }

    public static class WrappedMessageData {
        public final Bundle fields;
        public final String prefix;

        WrappedMessageData(String string, Bundle bundle) {
            this.prefix = string;
            this.fields = bundle;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("WrappedMessageData [type=");
            stringBuilder.append(this.prefix);
            stringBuilder.append(" fields=");
            stringBuilder.append((Object)this.fields);
            stringBuilder.append("]");
            return stringBuilder.toString();
        }
    }

}

