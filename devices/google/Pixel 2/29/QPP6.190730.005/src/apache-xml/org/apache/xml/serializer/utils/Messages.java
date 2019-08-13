/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.utils;

import java.text.MessageFormat;
import java.util.ListResourceBundle;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public final class Messages {
    private final Locale m_locale = Locale.getDefault();
    private ListResourceBundle m_resourceBundle;
    private String m_resourceBundleName;

    Messages(String string) {
        this.m_resourceBundleName = string;
    }

    private final String createMsg(ListResourceBundle object, String string, Object[] object2) {
        String string2 = null;
        int n = 0;
        int n2 = 0;
        Object var7_11 = null;
        if (string != null) {
            object = ((ResourceBundle)object).getString(string);
        } else {
            string = "";
            object = var7_11;
        }
        if (object == null) {
            n = 1;
            try {
                MessageFormat.format("BAD_MSGKEY", string, this.m_resourceBundleName);
            }
            catch (Exception exception) {
                object = new StringBuilder();
                ((StringBuilder)object).append("The message key '");
                ((StringBuilder)object).append(string);
                ((StringBuilder)object).append("' is not in the message class '");
                ((StringBuilder)object).append(this.m_resourceBundleName);
                ((StringBuilder)object).append("'");
                ((StringBuilder)object).toString();
            }
            object = string2;
        } else if (object2 != null) {
            int n3 = ((Object[])object2).length;
            for (n = 0; n < n3; ++n) {
                if (object2[n] != null) continue;
                object2[n] = "";
            }
            try {
                object = object2 = MessageFormat.format((String)object, (Object[])object2);
                n = n2;
            }
            catch (Exception exception) {
                n = 1;
                try {
                    string2 = MessageFormat.format("BAD_MSGFORMAT", string, this.m_resourceBundleName);
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(string2);
                    stringBuilder.append(" ");
                    stringBuilder.append((String)object);
                    object = stringBuilder.toString();
                }
                catch (Exception exception2) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("The format of message '");
                    ((StringBuilder)object).append(string);
                    ((StringBuilder)object).append("' in message class '");
                    ((StringBuilder)object).append(this.m_resourceBundleName);
                    ((StringBuilder)object).append("' failed.");
                    object = ((StringBuilder)object).toString();
                }
            }
        }
        if (n == 0) {
            return object;
        }
        throw new RuntimeException((String)object);
    }

    private Locale getLocale() {
        return this.m_locale;
    }

    private ListResourceBundle getResourceBundle() {
        return this.m_resourceBundle;
    }

    private static String getResourceSuffix(Locale object) {
        CharSequence charSequence = new StringBuilder();
        charSequence.append("_");
        charSequence.append(((Locale)object).getLanguage());
        charSequence = charSequence.toString();
        String string = ((Locale)object).getCountry();
        object = charSequence;
        if (string.equals("TW")) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("_");
            ((StringBuilder)object).append(string);
            object = ((StringBuilder)object).toString();
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private ListResourceBundle loadResourceBundle(String object) throws MissingResourceException {
        this.m_resourceBundleName = object;
        object = this.getLocale();
        try {
            object = (ListResourceBundle)ResourceBundle.getBundle(this.m_resourceBundleName, (Locale)object);
        }
        catch (MissingResourceException missingResourceException) {
            try {
                object = this.m_resourceBundleName;
                Locale locale = new Locale("en", "US");
                object = (ListResourceBundle)ResourceBundle.getBundle((String)object, locale);
            }
            catch (MissingResourceException missingResourceException2) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Could not load any resource bundles.");
                stringBuilder.append(this.m_resourceBundleName);
                throw new MissingResourceException(stringBuilder.toString(), this.m_resourceBundleName, "");
            }
        }
        this.m_resourceBundle = object;
        return object;
    }

    public final String createMessage(String charSequence, Object[] arrobject) {
        ListResourceBundle listResourceBundle;
        if (this.m_resourceBundle == null) {
            this.m_resourceBundle = this.loadResourceBundle(this.m_resourceBundleName);
        }
        if ((listResourceBundle = this.m_resourceBundle) != null) {
            return this.createMsg(listResourceBundle, (String)charSequence, arrobject);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Could not load the resource bundles: ");
        ((StringBuilder)charSequence).append(this.m_resourceBundleName);
        return ((StringBuilder)charSequence).toString();
    }
}

