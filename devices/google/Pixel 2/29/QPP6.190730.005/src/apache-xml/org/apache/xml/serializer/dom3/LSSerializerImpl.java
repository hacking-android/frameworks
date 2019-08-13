/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xml.serializer.dom3;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Properties;
import java.util.StringTokenizer;
import org.apache.xml.serializer.DOM3Serializer;
import org.apache.xml.serializer.Encodings;
import org.apache.xml.serializer.OutputPropertiesFactory;
import org.apache.xml.serializer.Serializer;
import org.apache.xml.serializer.SerializerFactory;
import org.apache.xml.serializer.dom3.DOMErrorImpl;
import org.apache.xml.serializer.dom3.DOMStringListImpl;
import org.apache.xml.serializer.utils.Messages;
import org.apache.xml.serializer.utils.SystemIDResolver;
import org.apache.xml.serializer.utils.Utils;
import org.w3c.dom.DOMConfiguration;
import org.w3c.dom.DOMError;
import org.w3c.dom.DOMErrorHandler;
import org.w3c.dom.DOMException;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.ls.LSException;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.ls.LSSerializerFilter;

public final class LSSerializerImpl
implements DOMConfiguration,
LSSerializer {
    private static final int CANONICAL = 1;
    private static final int CDATA = 2;
    private static final int CHARNORMALIZE = 4;
    private static final int COMMENTS = 8;
    private static final String DEFAULT_END_OF_LINE;
    private static final int DISCARDDEFAULT = 32768;
    private static final int DTNORMALIZE = 16;
    private static final int ELEM_CONTENT_WHITESPACE = 32;
    private static final int ENTITIES = 64;
    private static final int IGNORE_CHAR_DENORMALIZE = 131072;
    private static final int INFOSET = 128;
    private static final int NAMESPACEDECLS = 512;
    private static final int NAMESPACES = 256;
    private static final int NORMALIZECHARS = 1024;
    private static final int PRETTY_PRINT = 65536;
    private static final int SCHEMAVALIDATE = 8192;
    private static final int SPLITCDATA = 2048;
    private static final int VALIDATE = 4096;
    private static final int WELLFORMED = 16384;
    private static final int XMLDECL = 262144;
    private Properties fDOMConfigProperties = null;
    private DOMErrorHandler fDOMErrorHandler = null;
    private DOM3Serializer fDOMSerializer = null;
    private String fEncoding;
    private String fEndOfLine = DEFAULT_END_OF_LINE;
    protected int fFeatures = 0;
    private String[] fRecognizedParameters = new String[]{"canonical-form", "cdata-sections", "check-character-normalization", "comments", "datatype-normalization", "element-content-whitespace", "entities", "infoset", "namespaces", "namespace-declarations", "split-cdata-sections", "validate", "validate-if-schema", "well-formed", "discard-default-content", "format-pretty-print", "ignore-unknown-character-denormalizations", "xml-declaration", "error-handler"};
    private LSSerializerFilter fSerializerFilter = null;
    private Node fVisitedNode = null;
    private Serializer fXMLSerializer = null;

    static {
        String string = (String)AccessController.doPrivileged(new PrivilegedAction(){

            public Object run() {
                try {
                    String string = System.getProperty("line.separator");
                    return string;
                }
                catch (SecurityException securityException) {
                    return null;
                }
            }
        });
        if (string == null || !string.equals("\r\n") && !string.equals("\r")) {
            string = "\n";
        }
        DEFAULT_END_OF_LINE = string;
    }

    public LSSerializerImpl() {
        this.fFeatures |= 2;
        this.fFeatures |= 8;
        this.fFeatures |= 32;
        this.fFeatures |= 64;
        this.fFeatures |= 256;
        this.fFeatures |= 512;
        this.fFeatures |= 2048;
        this.fFeatures |= 16384;
        this.fFeatures |= 32768;
        this.fFeatures |= 262144;
        this.fDOMConfigProperties = new Properties();
        this.initializeSerializerProps();
        this.fXMLSerializer = SerializerFactory.getSerializer(OutputPropertiesFactory.getDefaultMethodProperties("xml"));
        this.fXMLSerializer.setOutputFormat(this.fDOMConfigProperties);
    }

    private static LSException createLSException(short s, Throwable throwable) {
        Object object = throwable != null ? throwable.getMessage() : null;
        object = new LSException(s, (String)object);
        if (throwable != null && ThrowableMethods.fgThrowableMethodsAvailable) {
            try {
                ThrowableMethods.fgThrowableInitCauseMethod.invoke(object, throwable);
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
        return object;
    }

    private static String getPathWithoutEscapes(String string) {
        if (string != null && string.length() != 0 && string.indexOf(37) != -1) {
            StringTokenizer stringTokenizer = new StringTokenizer(string, "%");
            StringBuffer stringBuffer = new StringBuffer(string.length());
            int n = stringTokenizer.countTokens();
            stringBuffer.append(stringTokenizer.nextToken());
            for (int i = 1; i < n; ++i) {
                String string2;
                string = string2 = stringTokenizer.nextToken();
                if (string2.length() >= 2) {
                    string = string2;
                    if (LSSerializerImpl.isHexDigit(string2.charAt(0))) {
                        string = string2;
                        if (LSSerializerImpl.isHexDigit(string2.charAt(1))) {
                            stringBuffer.append((char)Integer.valueOf(string2.substring(0, 2), 16).intValue());
                            string = string2.substring(2);
                        }
                    }
                }
                stringBuffer.append(string);
            }
            return stringBuffer.toString();
        }
        return string;
    }

    private static boolean isHexDigit(char c) {
        boolean bl = c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F';
        return bl;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public boolean canSetParameter(String string, Object object) {
        if (object instanceof Boolean) {
            if (string.equalsIgnoreCase("cdata-sections") || string.equalsIgnoreCase("comments") || string.equalsIgnoreCase("entities") || string.equalsIgnoreCase("infoset") || string.equalsIgnoreCase("element-content-whitespace") || string.equalsIgnoreCase("namespaces") || string.equalsIgnoreCase("namespace-declarations") || string.equalsIgnoreCase("split-cdata-sections") || string.equalsIgnoreCase("well-formed") || string.equalsIgnoreCase("discard-default-content") || string.equalsIgnoreCase("format-pretty-print") || string.equalsIgnoreCase("xml-declaration")) return true;
            if (string.equalsIgnoreCase("canonical-form") || string.equalsIgnoreCase("check-character-normalization") || string.equalsIgnoreCase("datatype-normalization") || string.equalsIgnoreCase("validate-if-schema") || string.equalsIgnoreCase("validate")) return (Boolean)object ^ true;
            if (!string.equalsIgnoreCase("ignore-unknown-character-denormalizations")) return false;
            return (Boolean)object;
        }
        if ((!string.equalsIgnoreCase("error-handler") || object != null) && !(object instanceof DOMErrorHandler)) return false;
        return true;
    }

    @Override
    public DOMConfiguration getDomConfig() {
        return this;
    }

    public DOMErrorHandler getErrorHandler() {
        return this.fDOMErrorHandler;
    }

    @Override
    public LSSerializerFilter getFilter() {
        return this.fSerializerFilter;
    }

    protected String getInputEncoding(Node node) {
        if (node != null && (node = node.getNodeType() == 9 ? (Document)node : node.getOwnerDocument()) != null && node.getImplementation().hasFeature("Core", "3.0")) {
            return node.getInputEncoding();
        }
        return null;
    }

    @Override
    public String getNewLine() {
        return this.fEndOfLine;
    }

    @Override
    public Object getParameter(String object) throws DOMException {
        if (((String)object).equalsIgnoreCase("comments")) {
            object = (this.fFeatures & 8) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("cdata-sections")) {
            object = (this.fFeatures & 2) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("entities")) {
            object = (this.fFeatures & 64) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("namespaces")) {
            object = (this.fFeatures & 256) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("namespace-declarations")) {
            object = (this.fFeatures & 512) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("split-cdata-sections")) {
            object = (this.fFeatures & 2048) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("well-formed")) {
            object = (this.fFeatures & 16384) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("discard-default-content")) {
            object = (this.fFeatures & 32768) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("format-pretty-print")) {
            object = (this.fFeatures & 65536) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("xml-declaration")) {
            object = (this.fFeatures & 262144) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("element-content-whitespace")) {
            object = (this.fFeatures & 32) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("format-pretty-print")) {
            object = (this.fFeatures & 65536) != 0 ? Boolean.TRUE : Boolean.FALSE;
            return object;
        }
        if (((String)object).equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
            return Boolean.TRUE;
        }
        if (!(((String)object).equalsIgnoreCase("canonical-form") || ((String)object).equalsIgnoreCase("check-character-normalization") || ((String)object).equalsIgnoreCase("datatype-normalization") || ((String)object).equalsIgnoreCase("validate") || ((String)object).equalsIgnoreCase("validate-if-schema"))) {
            if (((String)object).equalsIgnoreCase("infoset")) {
                int n = this.fFeatures;
                if ((n & 64) == 0 && (n & 2) == 0 && (n & 32) != 0 && (n & 256) != 0 && (n & 512) != 0 && (n & 16384) != 0 && (n & 8) != 0) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
            if (((String)object).equalsIgnoreCase("error-handler")) {
                return this.fDOMErrorHandler;
            }
            if (!((String)object).equalsIgnoreCase("schema-location") && !((String)object).equalsIgnoreCase("schema-type")) {
                throw new DOMException(8, Utils.messages.createMessage("FEATURE_NOT_FOUND", new Object[]{object}));
            }
            return null;
        }
        return Boolean.FALSE;
    }

    @Override
    public DOMStringList getParameterNames() {
        return new DOMStringListImpl(this.fRecognizedParameters);
    }

    protected String getXMLEncoding(Node node) {
        if (node != null && (node = node.getNodeType() == 9 ? (Document)node : node.getOwnerDocument()) != null && node.getImplementation().hasFeature("Core", "3.0")) {
            return node.getXmlEncoding();
        }
        return "UTF-8";
    }

    protected String getXMLVersion(Node node) {
        if (node != null && (node = node.getNodeType() == 9 ? (Document)node : node.getOwnerDocument()) != null && node.getImplementation().hasFeature("Core", "3.0")) {
            return node.getXmlVersion();
        }
        return "1.0";
    }

    public void initializeSerializerProps() {
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}canonical-form", "default:no");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "default:yes");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}check-character-normalization", "default:no");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "default:yes");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}datatype-normalization", "default:no");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "default:yes");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "default:yes");
        this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "default:yes");
        if ((this.fFeatures & 128) != 0) {
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "default:yes");
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "default:yes");
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "default:yes");
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "default:yes");
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "default:yes");
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "default:no");
            this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "default:no");
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "default:no");
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate-if-schema", "default:no");
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}datatype-normalization", "default:no");
        }
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "default:yes");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "default:yes");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}split-cdata-sections", "default:yes");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate", "default:no");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate-if-schema", "default:no");
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "default:yes");
        this.fDOMConfigProperties.setProperty("indent", "default:yes");
        this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xalan}indent-amount", Integer.toString(3));
        this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}discard-default-content", "default:yes");
        this.fDOMConfigProperties.setProperty("omit-xml-declaration", "no");
    }

    @Override
    public void setFilter(LSSerializerFilter lSSerializerFilter) {
        this.fSerializerFilter = lSSerializerFilter;
    }

    @Override
    public void setNewLine(String string) {
        if (string == null) {
            string = DEFAULT_END_OF_LINE;
        }
        this.fEndOfLine = string;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void setParameter(String string, Object object) throws DOMException {
        if (object instanceof Boolean) {
            boolean bl = (Boolean)object;
            if (string.equalsIgnoreCase("comments")) {
                int n = bl ? this.fFeatures | 8 : this.fFeatures & -9;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("cdata-sections")) {
                int n = bl ? this.fFeatures | 2 : this.fFeatures & -3;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("entities")) {
                int n = bl ? this.fFeatures | 64 : this.fFeatures & -65;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "explicit:yes");
                    this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "explicit:no");
                this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("namespaces")) {
                int n = bl ? this.fFeatures | 256 : this.fFeatures & -257;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("namespace-declarations")) {
                int n = bl ? this.fFeatures | 512 : this.fFeatures & -513;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("split-cdata-sections")) {
                int n = bl ? this.fFeatures | 2048 : this.fFeatures & -2049;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}split-cdata-sections", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}split-cdata-sections", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("well-formed")) {
                int n = bl ? this.fFeatures | 16384 : this.fFeatures & -16385;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("discard-default-content")) {
                int n = bl ? this.fFeatures | 32768 : this.fFeatures & -32769;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}discard-default-content", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}discard-default-content", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("format-pretty-print")) {
                int n = bl ? this.fFeatures | 65536 : this.fFeatures & -65537;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}format-pretty-print", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}format-pretty-print", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("xml-declaration")) {
                int n = bl ? this.fFeatures | 262144 : this.fFeatures & -262145;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("omit-xml-declaration", "no");
                    return;
                }
                this.fDOMConfigProperties.setProperty("omit-xml-declaration", "yes");
                return;
            }
            if (string.equalsIgnoreCase("element-content-whitespace")) {
                int n = bl ? this.fFeatures | 32 : this.fFeatures & -33;
                this.fFeatures = n;
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "explicit:yes");
                    return;
                }
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("ignore-unknown-character-denormalizations")) {
                if (bl) {
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}ignore-unknown-character-denormalizations", "explicit:yes");
                    return;
                }
                throw new DOMException(9, Utils.messages.createMessage("FEATURE_NOT_SUPPORTED", new Object[]{string}));
            }
            if (!(string.equalsIgnoreCase("canonical-form") || string.equalsIgnoreCase("validate-if-schema") || string.equalsIgnoreCase("validate") || string.equalsIgnoreCase("check-character-normalization") || string.equalsIgnoreCase("datatype-normalization"))) {
                if (string.equalsIgnoreCase("infoset")) {
                    if (!bl) return;
                    this.fFeatures &= -65;
                    this.fFeatures &= -3;
                    this.fFeatures &= -8193;
                    this.fFeatures &= -17;
                    this.fFeatures |= 256;
                    this.fFeatures |= 512;
                    this.fFeatures |= 16384;
                    this.fFeatures |= 32;
                    this.fFeatures |= 8;
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespaces", "explicit:yes");
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}namespace-declarations", "explicit:yes");
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}comments", "explicit:yes");
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}element-content-whitespace", "explicit:yes");
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}well-formed", "explicit:yes");
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}entities", "explicit:no");
                    this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}entities", "explicit:no");
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}cdata-sections", "explicit:no");
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate-if-schema", "explicit:no");
                    this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}datatype-normalization", "explicit:no");
                    return;
                }
                if (!(string.equalsIgnoreCase("error-handler") || string.equalsIgnoreCase("schema-location") || string.equalsIgnoreCase("schema-type"))) {
                    throw new DOMException(8, Utils.messages.createMessage("FEATURE_NOT_FOUND", new Object[]{string}));
                }
                throw new DOMException(17, Utils.messages.createMessage("TYPE_MISMATCH_ERR", new Object[]{string}));
            }
            if (bl) {
                throw new DOMException(9, Utils.messages.createMessage("FEATURE_NOT_SUPPORTED", new Object[]{string}));
            }
            if (string.equalsIgnoreCase("canonical-form")) {
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}canonical-form", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("validate-if-schema")) {
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate-if-schema", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("validate")) {
                this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}validate", "explicit:no");
                return;
            }
            if (string.equalsIgnoreCase("validate-if-schema")) {
                this.fDOMConfigProperties.setProperty("check-character-normalizationcheck-character-normalization", "explicit:no");
                return;
            }
            if (!string.equalsIgnoreCase("datatype-normalization")) return;
            this.fDOMConfigProperties.setProperty("{http://www.w3.org/TR/DOM-Level-3-LS}datatype-normalization", "explicit:no");
            return;
        }
        if (string.equalsIgnoreCase("error-handler")) {
            if (object != null && !(object instanceof DOMErrorHandler)) {
                throw new DOMException(17, Utils.messages.createMessage("TYPE_MISMATCH_ERR", new Object[]{string}));
            }
            this.fDOMErrorHandler = (DOMErrorHandler)object;
            return;
        }
        if (!string.equalsIgnoreCase("schema-location") && !string.equalsIgnoreCase("schema-type")) {
            if (!(string.equalsIgnoreCase("comments") || string.equalsIgnoreCase("cdata-sections") || string.equalsIgnoreCase("entities") || string.equalsIgnoreCase("namespaces") || string.equalsIgnoreCase("namespace-declarations") || string.equalsIgnoreCase("split-cdata-sections") || string.equalsIgnoreCase("well-formed") || string.equalsIgnoreCase("discard-default-content") || string.equalsIgnoreCase("format-pretty-print") || string.equalsIgnoreCase("xml-declaration") || string.equalsIgnoreCase("element-content-whitespace") || string.equalsIgnoreCase("ignore-unknown-character-denormalizations") || string.equalsIgnoreCase("canonical-form") || string.equalsIgnoreCase("validate-if-schema") || string.equalsIgnoreCase("validate") || string.equalsIgnoreCase("check-character-normalization") || string.equalsIgnoreCase("datatype-normalization") || string.equalsIgnoreCase("infoset"))) {
                throw new DOMException(8, Utils.messages.createMessage("FEATURE_NOT_FOUND", new Object[]{string}));
            }
            throw new DOMException(17, Utils.messages.createMessage("TYPE_MISMATCH_ERR", new Object[]{string}));
        }
        if (object == null) return;
        if (!(object instanceof String)) {
            throw new DOMException(17, Utils.messages.createMessage("TYPE_MISMATCH_ERR", new Object[]{string}));
        }
        throw new DOMException(9, Utils.messages.createMessage("FEATURE_NOT_SUPPORTED", new Object[]{string}));
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean write(Node var1_1, LSOutput var2_6) throws LSException {
        if (var2_6 == null) {
            var2_6 = Utils.messages.createMessage("no-output-specified", null);
            var1_1 = this.fDOMErrorHandler;
            if (var1_1 == null) throw new LSException(82, (String)var2_6);
            var1_1.handleError(new DOMErrorImpl(3, (String)var2_6, "no-output-specified"));
            throw new LSException(82, (String)var2_6);
        }
        if (var1_1 == null) {
            return false;
        }
        var3_9 = this.fXMLSerializer;
        var3_9.reset();
        if (var1_1 != this.fVisitedNode) {
            var4_10 = this.getXMLVersion((Node)var1_1);
            this.fEncoding = var2_6.getEncoding();
            if (this.fEncoding == null) {
                this.fEncoding = this.getInputEncoding((Node)var1_1);
                var5_11 = this.fEncoding;
                if (var5_11 == null) {
                    var5_11 = this.getXMLEncoding((Node)var1_1) == null ? "UTF-8" : this.getXMLEncoding((Node)var1_1);
                }
                this.fEncoding = var5_11;
            }
            if (!Encodings.isRecognizedEncoding(this.fEncoding)) {
                var2_6 = Utils.messages.createMessage("unsupported-encoding", null);
                var1_1 = this.fDOMErrorHandler;
                if (var1_1 == null) throw new LSException(82, (String)var2_6);
                var1_1.handleError(new DOMErrorImpl(3, (String)var2_6, "unsupported-encoding"));
                throw new LSException(82, (String)var2_6);
            }
            var3_9.getOutputFormat().setProperty("version", var4_10);
            this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}xml-version", var4_10);
            this.fDOMConfigProperties.setProperty("encoding", this.fEncoding);
            if ((var1_1.getNodeType() != 9 || var1_1.getNodeType() != 1 || var1_1.getNodeType() != 6) && (this.fFeatures & 262144) != 0) {
                this.fDOMConfigProperties.setProperty("omit-xml-declaration", "default:no");
            }
            this.fVisitedNode = var1_1;
        }
        this.fXMLSerializer.setOutputFormat(this.fDOMConfigProperties);
        try {
            var5_11 = var2_6.getCharacterStream();
            if (var5_11 != null) ** GOTO lbl75
            var5_11 = var2_6.getByteStream();
            if (var5_11 != null) ** GOTO lbl73
            if ((var2_6 = var2_6.getSystemId()) == null) {
                var1_1 = Utils.messages.createMessage("no-output-specified", null);
                if (this.fDOMErrorHandler != null) {
                    var5_11 = this.fDOMErrorHandler;
                    var2_6 = new DOMErrorImpl(3, (String)var1_1, "no-output-specified");
                    var5_11.handleError((DOMError)var2_6);
                }
                var2_6 = new LSException(82, (String)var1_1);
                throw var2_6;
            }
            var2_6 = SystemIDResolver.getAbsoluteURI((String)var2_6);
            var5_11 = new URL((String)var2_6);
            var4_10 = var5_11.getProtocol();
            var2_6 = var5_11.getHost();
            try {
                block23 : {
                    if (var4_10.equalsIgnoreCase("file") && (var2_6 == null || var2_6.length() == 0 || var2_6.equals("localhost"))) {
                        var2_6 = new FileOutputStream(LSSerializerImpl.getPathWithoutEscapes(var5_11.getPath()));
                    } else {
                        var2_6 = var5_11.openConnection();
                        var2_6.setDoInput(false);
                        var2_6.setDoOutput(true);
                        var2_6.setUseCaches(false);
                        var2_6.setAllowUserInteraction(false);
                        if (var2_6 instanceof HttpURLConnection) {
                            ((HttpURLConnection)var2_6).setRequestMethod("PUT");
                        }
                        var2_6 = var2_6.getOutputStream();
                    }
                    var3_9.setOutputStream((OutputStream)var2_6);
                    break block23;
lbl73: // 1 sources:
                    var3_9.setOutputStream((OutputStream)var5_11);
                    break block23;
lbl75: // 1 sources:
                    var3_9.setWriter((Writer)var5_11);
                }
                if (this.fDOMSerializer == null) {
                    this.fDOMSerializer = (DOM3Serializer)var3_9.asDOM3Serializer();
                }
                if (this.fDOMErrorHandler != null) {
                    this.fDOMSerializer.setErrorHandler(this.fDOMErrorHandler);
                }
                if (this.fSerializerFilter != null) {
                    this.fDOMSerializer.setNodeFilter(this.fSerializerFilter);
                }
                this.fDOMSerializer.setNewLine(this.fEndOfLine.toCharArray());
                this.fDOMSerializer.serializeDOM3((Node)var1_1);
                return true;
            }
            catch (RuntimeException var1_2) {
                throw (LSException)LSSerializerImpl.createLSException((short)82, (Throwable)var1_4).fillInStackTrace();
            }
        }
        catch (Exception var2_7) {
            var1_1 = this.fDOMErrorHandler;
            if (var1_1 == null) throw (LSException)LSSerializerImpl.createLSException((short)82, var2_7).fillInStackTrace();
            var1_1.handleError(new DOMErrorImpl(3, var2_7.getMessage(), null, var2_7));
            throw (LSException)LSSerializerImpl.createLSException((short)82, var2_7).fillInStackTrace();
        }
        catch (RuntimeException var1_3) {
            // empty catch block
        }
        throw (LSException)LSSerializerImpl.createLSException((short)82, (Throwable)var1_4).fillInStackTrace();
        catch (LSException var1_5) {
            throw var1_5;
        }
        catch (UnsupportedEncodingException var2_8) {
            var1_1 = Utils.messages.createMessage("unsupported-encoding", null);
            var5_11 = this.fDOMErrorHandler;
            if (var5_11 == null) throw (LSException)LSSerializerImpl.createLSException((short)82, var2_8).fillInStackTrace();
            var5_11.handleError(new DOMErrorImpl(3, (String)var1_1, "unsupported-encoding", var2_8));
            throw (LSException)LSSerializerImpl.createLSException((short)82, var2_8).fillInStackTrace();
        }
    }

    @Override
    public String writeToString(Node node) throws DOMException, LSException {
        Object object;
        if (node == null) {
            return null;
        }
        Object object2 = this.fXMLSerializer;
        object2.reset();
        if (node != this.fVisitedNode) {
            object = this.getXMLVersion(node);
            object2.getOutputFormat().setProperty("version", (String)object);
            this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}xml-version", (String)object);
            this.fDOMConfigProperties.setProperty("encoding", "UTF-16");
            if ((node.getNodeType() != 9 || node.getNodeType() != 1 || node.getNodeType() != 6) && (this.fFeatures & 262144) != 0) {
                this.fDOMConfigProperties.setProperty("omit-xml-declaration", "default:no");
            }
            this.fVisitedNode = node;
        }
        this.fXMLSerializer.setOutputFormat(this.fDOMConfigProperties);
        object = new StringWriter();
        try {
            object2.setWriter((Writer)object);
            if (this.fDOMSerializer == null) {
                this.fDOMSerializer = (DOM3Serializer)object2.asDOM3Serializer();
            }
            if (this.fDOMErrorHandler != null) {
                this.fDOMSerializer.setErrorHandler(this.fDOMErrorHandler);
            }
            if (this.fSerializerFilter != null) {
                this.fDOMSerializer.setNodeFilter(this.fSerializerFilter);
            }
            this.fDOMSerializer.setNewLine(this.fEndOfLine.toCharArray());
            this.fDOMSerializer.serializeDOM3(node);
        }
        catch (Exception exception) {
            object2 = this.fDOMErrorHandler;
            if (object2 != null) {
                object2.handleError(new DOMErrorImpl(3, exception.getMessage(), null, exception));
            }
            throw (LSException)LSSerializerImpl.createLSException((short)82, exception).fillInStackTrace();
        }
        catch (RuntimeException runtimeException) {
            throw (LSException)LSSerializerImpl.createLSException((short)82, runtimeException).fillInStackTrace();
        }
        catch (LSException lSException) {
            throw lSException;
        }
        return ((StringWriter)object).toString();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public boolean writeToURI(Node var1_1, String var2_4) throws LSException {
        if (var1_1 == null) {
            return false;
        }
        var3_6 = this.fXMLSerializer;
        var3_6.reset();
        if (var1_1 != this.fVisitedNode) {
            var4_7 = this.getXMLVersion((Node)var1_1);
            this.fEncoding = this.getInputEncoding((Node)var1_1);
            var5_8 = this.fEncoding;
            if (var5_8 == null) {
                if (var5_8 == null) {
                    var5_8 = this.getXMLEncoding((Node)var1_1) == null ? "UTF-8" : this.getXMLEncoding((Node)var1_1);
                }
                this.fEncoding = var5_8;
            }
            var3_6.getOutputFormat().setProperty("version", var4_7);
            this.fDOMConfigProperties.setProperty("{http://xml.apache.org/xerces-2j}xml-version", var4_7);
            this.fDOMConfigProperties.setProperty("encoding", this.fEncoding);
            if ((var1_1.getNodeType() != 9 || var1_1.getNodeType() != 1 || var1_1.getNodeType() != 6) && (this.fFeatures & 262144) != 0) {
                this.fDOMConfigProperties.setProperty("omit-xml-declaration", "default:no");
            }
            this.fVisitedNode = var1_1;
        }
        this.fXMLSerializer.setOutputFormat(this.fDOMConfigProperties);
        if (var2_4 != null) ** GOTO lbl35
        try {
            var1_1 = Utils.messages.createMessage("no-output-specified", null);
            if (this.fDOMErrorHandler != null) {
                var2_4 = this.fDOMErrorHandler;
                var5_8 = new DOMErrorImpl(3, (String)var1_1, "no-output-specified");
                var2_4.handleError((DOMError)var5_8);
            }
            var2_4 = new LSException(82, (String)var1_1);
            throw var2_4;
lbl35: // 1 sources:
            var2_4 = SystemIDResolver.getAbsoluteURI((String)var2_4);
            var5_8 = new URL((String)var2_4);
            var4_7 = var5_8.getProtocol();
            var2_4 = var5_8.getHost();
            if (var4_7.equalsIgnoreCase("file") && (var2_4 == null || var2_4.length() == 0 || var2_4.equals("localhost"))) {
                var2_4 = new FileOutputStream(LSSerializerImpl.getPathWithoutEscapes(var5_8.getPath()));
            } else {
                var2_4 = var5_8.openConnection();
                var2_4.setDoInput(false);
                var2_4.setDoOutput(true);
                var2_4.setUseCaches(false);
                var2_4.setAllowUserInteraction(false);
                if (var2_4 instanceof HttpURLConnection) {
                    ((HttpURLConnection)var2_4).setRequestMethod("PUT");
                }
                var2_4 = var2_4.getOutputStream();
            }
            var3_6.setOutputStream((OutputStream)var2_4);
            if (this.fDOMSerializer == null) {
                this.fDOMSerializer = (DOM3Serializer)var3_6.asDOM3Serializer();
            }
            if (this.fDOMErrorHandler != null) {
                this.fDOMSerializer.setErrorHandler(this.fDOMErrorHandler);
            }
            if (this.fSerializerFilter != null) {
                this.fDOMSerializer.setNodeFilter(this.fSerializerFilter);
            }
            this.fDOMSerializer.setNewLine(this.fEndOfLine.toCharArray());
            this.fDOMSerializer.serializeDOM3((Node)var1_1);
            return true;
        }
        catch (Exception var2_5) {
            var1_1 = this.fDOMErrorHandler;
            if (var1_1 == null) throw (LSException)LSSerializerImpl.createLSException((short)82, var2_5).fillInStackTrace();
            var1_1.handleError(new DOMErrorImpl(3, var2_5.getMessage(), null, var2_5));
            throw (LSException)LSSerializerImpl.createLSException((short)82, var2_5).fillInStackTrace();
        }
        catch (RuntimeException var1_2) {
            throw (LSException)LSSerializerImpl.createLSException((short)82, var1_2).fillInStackTrace();
        }
        catch (LSException var1_3) {
            throw var1_3;
        }
    }

    static class ThrowableMethods {
        private static Method fgThrowableInitCauseMethod = null;
        private static boolean fgThrowableMethodsAvailable = false;

        static {
            try {
                fgThrowableInitCauseMethod = Throwable.class.getMethod("initCause", Throwable.class);
                fgThrowableMethodsAvailable = true;
            }
            catch (Exception exception) {
                fgThrowableInitCauseMethod = null;
                fgThrowableMethodsAvailable = false;
            }
        }

        private ThrowableMethods() {
        }
    }

}

