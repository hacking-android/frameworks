/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.util.Enumeration;
import java.util.Properties;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xml.serializer.OutputPropertiesFactory;
import org.apache.xml.serializer.OutputPropertyUtils;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.QName;

public class OutputProperties
extends ElemTemplateElement
implements Cloneable {
    static final long serialVersionUID = -6975274363881785488L;
    private Properties m_properties = null;

    public OutputProperties() {
        this("xml");
    }

    public OutputProperties(String string) {
        this.m_properties = new Properties(OutputPropertiesFactory.getDefaultMethodProperties(string));
    }

    public OutputProperties(Properties properties) {
        this.m_properties = new Properties(properties);
    }

    public static Properties getDefaultMethodProperties(String string) {
        return OutputPropertiesFactory.getDefaultMethodProperties(string);
    }

    public static Vector getQNameProperties(String string, Properties cloneable) {
        block6 : {
            if ((string = ((Properties)cloneable).getProperty(string)) == null) break block6;
            cloneable = new Vector();
            int n = string.length();
            boolean bl = false;
            FastStringBuffer fastStringBuffer = new FastStringBuffer();
            for (int i = 0; i < n; ++i) {
                boolean bl2;
                block9 : {
                    char c;
                    block8 : {
                        block7 : {
                            c = string.charAt(i);
                            if (!Character.isWhitespace(c)) break block7;
                            bl2 = bl;
                            if (bl) break block8;
                            bl2 = bl;
                            if (fastStringBuffer.length() > 0) {
                                ((Vector)cloneable).addElement(QName.getQNameFromString(fastStringBuffer.toString()));
                                fastStringBuffer.reset();
                                bl2 = bl;
                            }
                            break block9;
                        }
                        if ('{' == c) {
                            bl2 = true;
                        } else {
                            bl2 = bl;
                            if ('}' == c) {
                                bl2 = false;
                            }
                        }
                    }
                    fastStringBuffer.append(c);
                }
                bl = bl2;
            }
            if (fastStringBuffer.length() > 0) {
                ((Vector)cloneable).addElement(QName.getQNameFromString(fastStringBuffer.toString()));
                fastStringBuffer.reset();
            }
            return cloneable;
        }
        return null;
    }

    public static QName getQNameProperty(String string, Properties properties) {
        if ((string = properties.getProperty(string)) != null) {
            return QName.getQNameFromString(string);
        }
        return null;
    }

    public static boolean isLegalPropertyKey(String string) {
        boolean bl = string.equals("cdata-section-elements");
        boolean bl2 = false;
        if (bl || string.equals("doctype-public") || string.equals("doctype-system") || string.equals("encoding") || string.equals("indent") || string.equals("media-type") || string.equals("method") || string.equals("omit-xml-declaration") || string.equals("standalone") || string.equals("version") || string.length() > 0 && string.charAt(0) == '{' && string.lastIndexOf(123) == 0 && string.indexOf(125) > 0 && string.lastIndexOf(125) == string.indexOf(125)) {
            bl2 = true;
        }
        return bl2;
    }

    public Object clone() {
        try {
            OutputProperties outputProperties = (OutputProperties)Object.super.clone();
            outputProperties.m_properties = (Properties)outputProperties.m_properties.clone();
            return outputProperties;
        }
        catch (CloneNotSupportedException cloneNotSupportedException) {
            return null;
        }
    }

    @Override
    public void compose(StylesheetRoot stylesheetRoot) throws TransformerException {
        super.compose(stylesheetRoot);
    }

    public void copyFrom(Properties properties) {
        this.copyFrom(properties, true);
    }

    public void copyFrom(Properties properties, boolean bl) {
        Enumeration enumeration = properties.keys();
        while (enumeration.hasMoreElements()) {
            String string = (String)enumeration.nextElement();
            if (OutputProperties.isLegalPropertyKey(string)) {
                CharSequence charSequence;
                Object v = this.m_properties.get(string);
                if (v == null) {
                    charSequence = (String)properties.get(string);
                    if (bl && string.equals("method")) {
                        this.setMethodDefaults((String)charSequence);
                    }
                    this.m_properties.put(string, charSequence);
                    continue;
                }
                if (!string.equals("cdata-section-elements")) continue;
                Properties properties2 = this.m_properties;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)v);
                ((StringBuilder)charSequence).append(" ");
                ((StringBuilder)charSequence).append((String)properties.get(string));
                properties2.put(string, ((StringBuilder)charSequence).toString());
                continue;
            }
            throw new IllegalArgumentException(XSLMessages.createMessage("ER_OUTPUT_PROPERTY_NOT_RECOGNIZED", new Object[]{string}));
        }
    }

    public void copyFrom(OutputProperties outputProperties) throws TransformerException {
        this.copyFrom(outputProperties.getProperties());
    }

    public boolean getBooleanProperty(String string) {
        return OutputPropertyUtils.getBooleanProperty(string, this.m_properties);
    }

    public boolean getBooleanProperty(QName qName) {
        return this.getBooleanProperty(qName.toNamespacedString());
    }

    public int getIntProperty(String string) {
        return OutputPropertyUtils.getIntProperty(string, this.m_properties);
    }

    public int getIntProperty(QName qName) {
        return this.getIntProperty(qName.toNamespacedString());
    }

    public Properties getProperties() {
        return this.m_properties;
    }

    public String getProperty(String string) {
        CharSequence charSequence = string;
        if (string.startsWith("{http://xml.apache.org/xslt}")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("{http://xml.apache.org/xalan}");
            ((StringBuilder)charSequence).append(string.substring(OutputPropertiesFactory.S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL_LEN));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return this.m_properties.getProperty((String)charSequence);
    }

    public String getProperty(QName qName) {
        return this.m_properties.getProperty(qName.toNamespacedString());
    }

    public Vector getQNameProperties(String string) {
        return OutputProperties.getQNameProperties(string, this.m_properties);
    }

    public Vector getQNameProperties(QName qName) {
        return this.getQNameProperties(qName.toNamespacedString());
    }

    public QName getQNameProperty(String string) {
        return OutputProperties.getQNameProperty(string, this.m_properties);
    }

    public QName getQNameProperty(QName qName) {
        return this.getQNameProperty(qName.toNamespacedString());
    }

    @Override
    public void recompose(StylesheetRoot stylesheetRoot) throws TransformerException {
        stylesheetRoot.recomposeOutput(this);
    }

    public void setBooleanProperty(String string, boolean bl) {
        Properties properties = this.m_properties;
        String string2 = bl ? "yes" : "no";
        properties.put(string, string2);
    }

    public void setBooleanProperty(QName object, boolean bl) {
        Properties properties = this.m_properties;
        String string = ((QName)object).toNamespacedString();
        object = bl ? "yes" : "no";
        properties.put(string, object);
    }

    public void setIntProperty(String string, int n) {
        this.m_properties.put(string, Integer.toString(n));
    }

    public void setIntProperty(QName qName, int n) {
        this.setIntProperty(qName.toNamespacedString(), n);
    }

    public void setMethodDefaults(String string) {
        Object object = this.m_properties.getProperty("method");
        if (object == null || !((String)object).equals(string) || ((String)object).equals("xml")) {
            object = this.m_properties;
            this.m_properties = new Properties(OutputPropertiesFactory.getDefaultMethodProperties(string));
            this.copyFrom((Properties)object, false);
        }
    }

    public void setProperty(String string, String string2) {
        if (string.equals("method")) {
            this.setMethodDefaults(string2);
        }
        CharSequence charSequence = string;
        if (string.startsWith("{http://xml.apache.org/xslt}")) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("{http://xml.apache.org/xalan}");
            ((StringBuilder)charSequence).append(string.substring(OutputPropertiesFactory.S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL_LEN));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        this.m_properties.put(charSequence, string2);
    }

    public void setProperty(QName qName, String string) {
        this.setProperty(qName.toNamespacedString(), string);
    }

    public void setQNameProperties(String string, Vector vector) {
        int n = vector.size();
        FastStringBuffer fastStringBuffer = new FastStringBuffer(9, 9);
        for (int i = 0; i < n; ++i) {
            fastStringBuffer.append(((QName)vector.elementAt(i)).toNamespacedString());
            if (i >= n - 1) continue;
            fastStringBuffer.append(' ');
        }
        this.m_properties.put(string, fastStringBuffer.toString());
    }

    public void setQNameProperties(QName qName, Vector vector) {
        this.setQNameProperties(qName.toNamespacedString(), vector);
    }

    public void setQNameProperty(String string, QName qName) {
        this.setProperty(string, qName.toNamespacedString());
    }

    public void setQNameProperty(QName qName, QName qName2) {
        this.setQNameProperty(qName.toNamespacedString(), qName2);
    }
}

