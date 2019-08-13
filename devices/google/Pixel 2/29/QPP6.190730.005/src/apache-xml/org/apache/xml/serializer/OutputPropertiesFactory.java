/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivilegedAction;
import java.util.Properties;
import org.apache.xml.serializer.Encodings;
import org.apache.xml.serializer.SerializerBase;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.Utils;
import org.apache.xml.serializer.utils.WrappedRuntimeException;

public final class OutputPropertiesFactory {
    private static final Class ACCESS_CONTROLLER_CLASS;
    private static final String PROP_DIR;
    private static final String PROP_FILE_HTML = "output_html.properties";
    private static final String PROP_FILE_TEXT = "output_text.properties";
    private static final String PROP_FILE_UNKNOWN = "output_unknown.properties";
    private static final String PROP_FILE_XML = "output_xml.properties";
    public static final String S_BUILTIN_EXTENSIONS_UNIVERSAL = "{http://xml.apache.org/xalan}";
    private static final String S_BUILTIN_EXTENSIONS_URL = "http://xml.apache.org/xalan";
    public static final String S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL = "{http://xml.apache.org/xslt}";
    public static final int S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL_LEN;
    private static final String S_BUILTIN_OLD_EXTENSIONS_URL = "http://xml.apache.org/xslt";
    public static final String S_KEY_CONTENT_HANDLER = "{http://xml.apache.org/xalan}content-handler";
    public static final String S_KEY_ENTITIES = "{http://xml.apache.org/xalan}entities";
    public static final String S_KEY_INDENT_AMOUNT = "{http://xml.apache.org/xalan}indent-amount";
    public static final String S_KEY_LINE_SEPARATOR = "{http://xml.apache.org/xalan}line-separator";
    public static final String S_OMIT_META_TAG = "{http://xml.apache.org/xalan}omit-meta-tag";
    public static final String S_USE_URL_ESCAPING = "{http://xml.apache.org/xalan}use-url-escaping";
    private static final String S_XALAN_PREFIX = "org.apache.xslt.";
    private static final int S_XALAN_PREFIX_LEN;
    private static final String S_XSLT_PREFIX = "xslt.output.";
    private static final int S_XSLT_PREFIX_LEN;
    private static Properties m_html_properties;
    private static Integer m_synch_object;
    private static Properties m_text_properties;
    private static Properties m_unknown_properties;
    private static Properties m_xml_properties;

    static {
        S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL_LEN = S_BUILTIN_OLD_EXTENSIONS_UNIVERSAL.length();
        S_XSLT_PREFIX_LEN = S_XSLT_PREFIX.length();
        S_XALAN_PREFIX_LEN = S_XALAN_PREFIX.length();
        m_synch_object = new Integer(1);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(SerializerBase.PKG_PATH);
        stringBuilder.append('/');
        PROP_DIR = stringBuilder.toString();
        m_xml_properties = null;
        m_html_properties = null;
        m_text_properties = null;
        m_unknown_properties = null;
        ACCESS_CONTROLLER_CLASS = OutputPropertiesFactory.findAccessControllerClass();
    }

    private static Class findAccessControllerClass() {
        try {
            Class<?> class_ = Class.forName("java.security.AccessController");
            return class_;
        }
        catch (Exception exception) {
            return null;
        }
    }

    private static String fixupPropertyString(String charSequence, boolean bl) {
        CharSequence charSequence2 = charSequence;
        if (bl) {
            charSequence2 = charSequence;
            if (((String)charSequence).startsWith(S_XSLT_PREFIX)) {
                charSequence2 = ((String)charSequence).substring(S_XSLT_PREFIX_LEN);
            }
        }
        charSequence = charSequence2;
        if (((String)charSequence2).startsWith(S_XALAN_PREFIX)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(S_BUILTIN_EXTENSIONS_UNIVERSAL);
            ((StringBuilder)charSequence).append(((String)charSequence2).substring(S_XALAN_PREFIX_LEN));
            charSequence = ((StringBuilder)charSequence).toString();
        }
        int n = ((String)charSequence).indexOf("\\u003a");
        charSequence2 = charSequence;
        if (n > 0) {
            String string = ((String)charSequence).substring(n + 6);
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(((String)charSequence).substring(0, n));
            ((StringBuilder)charSequence2).append(":");
            ((StringBuilder)charSequence2).append(string);
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        return charSequence2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final Properties getDefaultMethodProperties(String object) {
        Object object2 = null;
        String string = null;
        Object object3 = null;
        Object object4 = string;
        try {
            Integer n = m_synch_object;
            object4 = string;
            synchronized (n) {
                object4 = object2;
                if (m_xml_properties != null) break block15;
                object4 = object3 = PROP_FILE_XML;
            }
        }
        catch (IOException iOException) {
            throw new WrappedRuntimeException(Utils.messages.createMessage("ER_COULD_NOT_LOAD_METHOD_PROPERTY", new Object[]{object4, object}), iOException);
        }
        {
            block15 : {
                m_xml_properties = OutputPropertiesFactory.loadPropertiesFile((String)object3, null);
            }
            object4 = object3;
        }
        object4 = object3;
        if (((String)object).equals("xml")) {
            object4 = object3;
            object = object3 = m_xml_properties;
            return new Properties((Properties)object);
        }
        object4 = object3;
        if (((String)object).equals("html")) {
            object2 = object3;
            object4 = object3;
            if (m_html_properties == null) {
                object4 = object2 = PROP_FILE_HTML;
                m_html_properties = OutputPropertiesFactory.loadPropertiesFile((String)object2, m_xml_properties);
            }
            object4 = object2;
            object = object3 = m_html_properties;
            return new Properties((Properties)object);
        }
        object4 = object3;
        if (((String)object).equals("text")) {
            object2 = object3;
            object4 = object3;
            if (m_text_properties == null) {
                object4 = object3 = PROP_FILE_TEXT;
                m_text_properties = OutputPropertiesFactory.loadPropertiesFile((String)object3, m_xml_properties);
                object2 = object3;
                object4 = object3;
                if (m_text_properties.getProperty("encoding") == null) {
                    object4 = object3;
                    object2 = Encodings.getMimeEncoding(null);
                    object4 = object3;
                    m_text_properties.put("encoding", object2);
                    object2 = object3;
                }
            }
            object4 = object2;
            object = object3 = m_text_properties;
            return new Properties((Properties)object);
        }
        object4 = object3;
        if (!((String)object).equals("")) {
            object4 = object3;
            object = object3 = m_xml_properties;
            return new Properties((Properties)object);
        }
        object2 = object3;
        object4 = object3;
        if (m_unknown_properties == null) {
            object4 = object2 = PROP_FILE_UNKNOWN;
            m_unknown_properties = OutputPropertiesFactory.loadPropertiesFile((String)object2, m_xml_properties);
        }
        object4 = object2;
        object = object3 = m_unknown_properties;
        return new Properties((Properties)object);
    }

    /*
     * Exception decompiling
     */
    private static Properties loadPropertiesFile(String var0, Properties var1_2) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [7[CATCHBLOCK]], but top level block is 3[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

}

