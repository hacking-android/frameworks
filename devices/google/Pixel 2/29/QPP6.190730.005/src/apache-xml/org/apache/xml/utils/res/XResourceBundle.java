/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.utils.res;

import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import org.apache.xml.utils.res.CharArrayWrapper;

public class XResourceBundle
extends ListResourceBundle {
    public static final String ERROR_RESOURCES = "org.apache.xalan.res.XSLTErrorResources";
    public static final String LANG_ADDITIVE = "additive";
    public static final String LANG_ALPHABET = "alphabet";
    public static final String LANG_BUNDLE_NAME = "org.apache.xml.utils.res.XResources";
    public static final String LANG_LEFTTORIGHT = "leftToRight";
    public static final String LANG_MULTIPLIER = "multiplier";
    public static final String LANG_MULTIPLIER_CHAR = "multiplierChar";
    public static final String LANG_MULT_ADD = "multiplicative-additive";
    public static final String LANG_NUMBERGROUPS = "numberGroups";
    public static final String LANG_NUMBERING = "numbering";
    public static final String LANG_NUM_TABLES = "tables";
    public static final String LANG_ORIENTATION = "orientation";
    public static final String LANG_RIGHTTOLEFT = "rightToLeft";
    public static final String LANG_TRAD_ALPHABET = "tradAlphabet";
    public static final String MULT_FOLLOWS = "follows";
    public static final String MULT_ORDER = "multiplierOrder";
    public static final String MULT_PRECEDES = "precedes";
    public static final String XSLT_RESOURCE = "org.apache.xml.utils.res.XResourceBundle";

    private static final String getResourceSuffix(Locale object) {
        String string = ((Locale)object).getLanguage();
        String string2 = ((Locale)object).getCountry();
        String string3 = ((Locale)object).getVariant();
        Object object2 = new StringBuilder();
        ((StringBuilder)object2).append("_");
        ((StringBuilder)object2).append(((Locale)object).getLanguage());
        object = object2 = ((StringBuilder)object2).toString();
        if (string.equals("zh")) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)object2);
            ((StringBuilder)object).append("_");
            ((StringBuilder)object).append(string2);
            object = ((StringBuilder)object).toString();
        }
        object2 = object;
        if (string2.equals("JP")) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)object);
            ((StringBuilder)object2).append("_");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append("_");
            ((StringBuilder)object2).append(string3);
            object2 = ((StringBuilder)object2).toString();
        }
        return object2;
    }

    public static final XResourceBundle loadResourceBundle(String string, Locale object) throws MissingResourceException {
        String string2 = XResourceBundle.getResourceSuffix((Locale)object);
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(string2);
            object = (XResourceBundle)ResourceBundle.getBundle(stringBuilder.toString(), (Locale)object);
            return object;
        }
        catch (MissingResourceException missingResourceException) {
            try {
                Object object2 = new Locale("en", "US");
                object2 = (XResourceBundle)ResourceBundle.getBundle(XSLT_RESOURCE, (Locale)object2);
                return object2;
            }
            catch (MissingResourceException missingResourceException2) {
                throw new MissingResourceException("Could not load any resource bundles.", string, "");
            }
        }
    }

    @Override
    public Object[][] getContents() {
        return new Object[][]{{"ui_language", "en"}, {"help_language", "en"}, {"language", "en"}, {LANG_ALPHABET, new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'})}, {LANG_TRAD_ALPHABET, new CharArrayWrapper(new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'})}, {LANG_ORIENTATION, "LeftToRight"}, {LANG_NUMBERING, LANG_ADDITIVE}};
    }
}

