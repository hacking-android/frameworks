/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.processor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.xml.transform.TransformerException;
import org.apache.xalan.processor.StylesheetHandler;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.StringToIntTable;
import org.apache.xml.utils.StringVector;
import org.apache.xml.utils.XML11Char;
import org.apache.xpath.XPath;
import org.xml.sax.SAXException;

public class XSLTAttributeDef {
    static final int ERROR = 1;
    static final int FATAL = 0;
    static final String S_FOREIGNATTR_SETTER = "setForeignAttr";
    static final int T_AVT = 3;
    static final int T_AVT_QNAME = 18;
    static final int T_CDATA = 1;
    static final int T_CHAR = 6;
    static final int T_ENUM = 11;
    static final int T_ENUM_OR_PQNAME = 16;
    static final int T_EXPR = 5;
    static final int T_NCNAME = 17;
    static final int T_NMTOKEN = 13;
    static final int T_NUMBER = 7;
    static final int T_PATTERN = 4;
    static final int T_PREFIXLIST = 20;
    static final int T_PREFIX_URLLIST = 15;
    static final int T_QNAME = 9;
    static final int T_QNAMES = 10;
    static final int T_QNAMES_RESOLVE_NULL = 19;
    static final int T_SIMPLEPATTERNLIST = 12;
    static final int T_STRINGLIST = 14;
    static final int T_URL = 2;
    static final int T_YESNO = 8;
    static final int WARNING = 2;
    static final XSLTAttributeDef m_foreignAttr = new XSLTAttributeDef("*", "*", 1, false, false, 2);
    private String m_default;
    private StringToIntTable m_enums;
    int m_errorType = 2;
    private String m_name;
    private String m_namespace;
    private boolean m_required;
    String m_setterString = null;
    private boolean m_supportsAVT;
    private int m_type;

    XSLTAttributeDef(String string, String string2, int n, boolean bl, int n2, String string3) {
        this.m_namespace = string;
        this.m_name = string2;
        this.m_type = n;
        this.m_required = false;
        this.m_supportsAVT = bl;
        this.m_errorType = n2;
        this.m_default = string3;
    }

    XSLTAttributeDef(String string, String string2, int n, boolean bl, boolean bl2, int n2) {
        this.m_namespace = string;
        this.m_name = string2;
        this.m_type = n;
        this.m_required = bl;
        this.m_supportsAVT = bl2;
        this.m_errorType = n2;
    }

    XSLTAttributeDef(String string, String string2, boolean bl, boolean bl2, boolean bl3, int n, String string3, int n2, String string4, int n3) {
        this.m_namespace = string;
        this.m_name = string2;
        int n4 = bl3 ? 16 : 11;
        this.m_type = n4;
        this.m_required = bl;
        this.m_supportsAVT = bl2;
        this.m_errorType = n;
        this.m_enums = new StringToIntTable(2);
        this.m_enums.put(string3, n2);
        this.m_enums.put(string4, n3);
    }

    XSLTAttributeDef(String string, String string2, boolean bl, boolean bl2, boolean bl3, int n, String string3, int n2, String string4, int n3, String string5, int n4) {
        this.m_namespace = string;
        this.m_name = string2;
        int n5 = bl3 ? 16 : 11;
        this.m_type = n5;
        this.m_required = bl;
        this.m_supportsAVT = bl2;
        this.m_errorType = n;
        this.m_enums = new StringToIntTable(3);
        this.m_enums.put(string3, n2);
        this.m_enums.put(string4, n3);
        this.m_enums.put(string5, n4);
    }

    XSLTAttributeDef(String string, String string2, boolean bl, boolean bl2, boolean bl3, int n, String string3, int n2, String string4, int n3, String string5, int n4, String string6, int n5) {
        this.m_namespace = string;
        this.m_name = string2;
        int n6 = bl3 ? 16 : 11;
        this.m_type = n6;
        this.m_required = bl;
        this.m_supportsAVT = bl2;
        this.m_errorType = n;
        this.m_enums = new StringToIntTable(4);
        this.m_enums.put(string3, n2);
        this.m_enums.put(string4, n3);
        this.m_enums.put(string5, n4);
        this.m_enums.put(string6, n5);
    }

    private int getEnum(String string) {
        return this.m_enums.get(string);
    }

    private String[] getEnumNames() {
        return this.m_enums.keys();
    }

    private StringBuffer getListOfEnums() {
        StringBuffer stringBuffer = new StringBuffer();
        String[] arrstring = this.getEnumNames();
        for (int i = 0; i < arrstring.length; ++i) {
            if (i > 0) {
                stringBuffer.append(' ');
            }
            stringBuffer.append(arrstring[i]);
        }
        return stringBuffer;
    }

    private Class getPrimativeClass(Object class_) {
        if (class_ instanceof XPath) {
            return XPath.class;
        }
        Class<Object> class_2 = class_ = class_.getClass();
        if (class_ == Double.class) {
            class_2 = Double.TYPE;
        }
        if (class_2 == Float.class) {
            class_ = Float.TYPE;
        } else if (class_2 == Boolean.class) {
            class_ = Boolean.TYPE;
        } else if (class_2 == Byte.class) {
            class_ = Byte.TYPE;
        } else if (class_2 == Character.class) {
            class_ = Character.TYPE;
        } else if (class_2 == Short.class) {
            class_ = Short.TYPE;
        } else if (class_2 == Integer.class) {
            class_ = Integer.TYPE;
        } else {
            class_ = class_2;
            if (class_2 == Long.class) {
                class_ = Long.TYPE;
            }
        }
        return class_;
    }

    private void handleError(StylesheetHandler stylesheetHandler, String string, Object[] arrobject, Exception exception) throws SAXException {
        int n = this.getErrorType();
        if (n != 0 && n != 1) {
            if (n == 2) {
                stylesheetHandler.warn(string, arrobject);
            }
        } else {
            stylesheetHandler.error(string, arrobject, exception);
        }
    }

    private Boolean processYESNO(StylesheetHandler stylesheetHandler, String string, String string2, String string3, String string4) throws SAXException {
        if (!string4.equals("yes") && !string4.equals("no")) {
            this.handleError(stylesheetHandler, "INVALID_BOOLEAN", new Object[]{string2, string4}, null);
            return null;
        }
        return new Boolean(string4.equals("yes"));
    }

    String getDefault() {
        return this.m_default;
    }

    int getErrorType() {
        return this.m_errorType;
    }

    String getName() {
        return this.m_name;
    }

    String getNamespace() {
        return this.m_namespace;
    }

    boolean getRequired() {
        return this.m_required;
    }

    public String getSetterMethodName() {
        if (this.m_setterString == null) {
            if (m_foreignAttr == this) {
                return S_FOREIGNATTR_SETTER;
            }
            if (this.m_name.equals("*")) {
                this.m_setterString = "addLiteralResultAttribute";
                return this.m_setterString;
            }
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("set");
            String string = this.m_namespace;
            if (string != null && string.equals("http://www.w3.org/XML/1998/namespace")) {
                stringBuffer.append("Xml");
            }
            int n = this.m_name.length();
            int n2 = 0;
            while (n2 < n) {
                int n3;
                int n4;
                int n5 = this.m_name.charAt(n2);
                if (45 == n5) {
                    n3 = n2 + 1;
                    n4 = n2 = (int)Character.toUpperCase(this.m_name.charAt(n3));
                } else {
                    n3 = n2;
                    n4 = n5;
                    if (n2 == 0) {
                        n4 = n3 = (int)Character.toUpperCase((char)n5);
                        n3 = n2;
                    }
                }
                stringBuffer.append((char)n4);
                n2 = n3 + 1;
            }
            this.m_setterString = stringBuffer.toString();
        }
        return this.m_setterString;
    }

    boolean getSupportsAVT() {
        return this.m_supportsAVT;
    }

    int getType() {
        return this.m_type;
    }

    AVT processAVT(StylesheetHandler object, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        try {
            object = new AVT((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
            return object;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Object processAVT_QNAME(StylesheetHandler stylesheetHandler, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        try {
            AVT aVT = new AVT(stylesheetHandler, string, string2, string3, string4, elemTemplateElement);
            if (aVT.isSimple()) {
                int n = string4.indexOf(58);
                if (n >= 0 && !XML11Char.isXML11ValidNCName(string4.substring(0, n))) {
                    this.handleError(stylesheetHandler, "INVALID_QNAME", new Object[]{string2, string4}, null);
                    return null;
                }
                string = n < 0 ? string4 : string4.substring(n + 1);
                if (string == null || string.length() == 0 || !XML11Char.isXML11ValidNCName(string)) {
                    this.handleError(stylesheetHandler, "INVALID_QNAME", new Object[]{string2, string4}, null);
                    return null;
                }
            }
            return aVT;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    Object processCDATA(StylesheetHandler object, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        if (this.getSupportsAVT()) {
            try {
                object = new AVT((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                return object;
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
        return string4;
    }

    Object processCHAR(StylesheetHandler stylesheetHandler, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        if (this.getSupportsAVT()) {
            try {
                AVT aVT = new AVT(stylesheetHandler, string, string2, string3, string4, elemTemplateElement);
                if (aVT.isSimple() && string4.length() != 1) {
                    this.handleError(stylesheetHandler, "INVALID_TCHAR", new Object[]{string2, string4}, null);
                    return null;
                }
                return aVT;
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
        if (string4.length() != 1) {
            this.handleError(stylesheetHandler, "INVALID_TCHAR", new Object[]{string2, string4}, null);
            return null;
        }
        return new Character(string4.charAt(0));
    }

    Object processENUM(StylesheetHandler stylesheetHandler, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        int n;
        AVT aVT = null;
        if (this.getSupportsAVT()) {
            try {
                aVT = new AVT(stylesheetHandler, string, string2, string3, string4, elemTemplateElement);
                boolean bl = aVT.isSimple();
                if (!bl) {
                    return aVT;
                }
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
        if ((n = this.getEnum(string4)) == -10000) {
            this.handleError(stylesheetHandler, "INVALID_ENUM", new Object[]{string2, string4, this.getListOfEnums().toString()}, null);
            return null;
        }
        if (this.getSupportsAVT()) {
            return aVT;
        }
        return new Integer(n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    Object processENUM_OR_PQNAME(StylesheetHandler stylesheetHandler, String object, String string, String object2, String string2, ElemTemplateElement elemTemplateElement) throws SAXException {
        block9 : {
            int n;
            AVT aVT = null;
            if (this.getSupportsAVT()) {
                try {
                    aVT = new AVT(stylesheetHandler, (String)object, string, (String)object2, string2, elemTemplateElement);
                    boolean bl = aVT.isSimple();
                    if (!bl) {
                        return aVT;
                    }
                }
                catch (TransformerException transformerException) {
                    throw new SAXException(transformerException);
                }
            }
            if ((n = this.getEnum(string2)) != -10000) {
                object = aVT;
                if (aVT != null) return object;
                return new Integer(n);
            }
            object2 = new QName(string2, stylesheetHandler, true);
            object = aVT;
            if (aVT != null) break block9;
            object = object2;
        }
        try {
            if (((QName)object2).getPrefix() != null) return object;
            object = this.getListOfEnums();
            ((StringBuffer)object).append(" <qname-but-not-ncname>");
            this.handleError(stylesheetHandler, "INVALID_ENUM", new Object[]{string, string2, ((StringBuffer)object).toString()}, null);
            return null;
        }
        catch (RuntimeException runtimeException) {
            object = this.getListOfEnums();
            ((StringBuffer)object).append(" <qname-but-not-ncname>");
            this.handleError(stylesheetHandler, "INVALID_ENUM", new Object[]{string, string2, ((StringBuffer)object).toString()}, runtimeException);
            return null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            object2 = this.getListOfEnums();
            ((StringBuffer)object2).append(" <qname-but-not-ncname>");
            this.handleError(stylesheetHandler, "INVALID_ENUM", new Object[]{string, string2, ((StringBuffer)object2).toString()}, illegalArgumentException);
            return null;
        }
    }

    Object processEXPR(StylesheetHandler object, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        try {
            object = ((StylesheetHandler)object).createXPath(string4, elemTemplateElement);
            return object;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    Object processNCNAME(StylesheetHandler stylesheetHandler, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        if (this.getSupportsAVT()) {
            try {
                AVT aVT = new AVT(stylesheetHandler, string, string2, string3, string4, elemTemplateElement);
                if (aVT.isSimple() && !XML11Char.isXML11ValidNCName(string4)) {
                    this.handleError(stylesheetHandler, "INVALID_NCNAME", new Object[]{string2, string4}, null);
                    return null;
                }
                return aVT;
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
        if (!XML11Char.isXML11ValidNCName(string4)) {
            this.handleError(stylesheetHandler, "INVALID_NCNAME", new Object[]{string2, string4}, null);
            return null;
        }
        return string4;
    }

    Object processNMTOKEN(StylesheetHandler stylesheetHandler, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        if (this.getSupportsAVT()) {
            try {
                AVT aVT = new AVT(stylesheetHandler, string, string2, string3, string4, elemTemplateElement);
                if (aVT.isSimple() && !XML11Char.isXML11ValidNmtoken(string4)) {
                    this.handleError(stylesheetHandler, "INVALID_NMTOKEN", new Object[]{string2, string4}, null);
                    return null;
                }
                return aVT;
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
        if (!XML11Char.isXML11ValidNmtoken(string4)) {
            this.handleError(stylesheetHandler, "INVALID_NMTOKEN", new Object[]{string2, string4}, null);
            return null;
        }
        return string4;
    }

    Object processNUMBER(StylesheetHandler stylesheetHandler, String object, String string, String string2, String string3, ElemTemplateElement elemTemplateElement) throws SAXException {
        if (this.getSupportsAVT()) {
            try {
                AVT aVT = new AVT(stylesheetHandler, (String)object, string, string2, string3, elemTemplateElement);
                if (aVT.isSimple()) {
                    Double.valueOf(string3);
                }
                return aVT;
            }
            catch (NumberFormatException numberFormatException) {
                this.handleError(stylesheetHandler, "INVALID_NUMBER", new Object[]{string, string3}, numberFormatException);
                return null;
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
        try {
            object = Double.valueOf(string3);
            return object;
        }
        catch (NumberFormatException numberFormatException) {
            this.handleError(stylesheetHandler, "INVALID_NUMBER", new Object[]{string, string3}, numberFormatException);
            return null;
        }
    }

    Object processPATTERN(StylesheetHandler object, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        try {
            object = ((StylesheetHandler)object).createMatchPatternXPath(string4, elemTemplateElement);
            return object;
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
    }

    StringVector processPREFIX_LIST(StylesheetHandler stylesheetHandler, String string, String object, String string2, String object2) throws SAXException {
        object2 = new StringTokenizer((String)object2, " \t\n\r\f");
        int n = ((StringTokenizer)object2).countTokens();
        object = new StringVector(n);
        for (int i = 0; i < n; ++i) {
            string2 = ((StringTokenizer)object2).nextToken();
            string = stylesheetHandler.getNamespaceForPrefix(string2);
            if (!string2.equals("#default") && string == null) {
                throw new SAXException(XSLMessages.createMessage("ER_CANT_RESOLVE_NSPREFIX", new Object[]{string2}));
            }
            ((StringVector)object).addElement(string2);
        }
        return object;
    }

    StringVector processPREFIX_URLLIST(StylesheetHandler stylesheetHandler, String string, String string2, String object, String object2) throws SAXException {
        object2 = new StringTokenizer((String)object2, " \t\n\r\f");
        int n = ((StringTokenizer)object2).countTokens();
        object = new StringVector(n);
        for (int i = 0; i < n; ++i) {
            string = ((StringTokenizer)object2).nextToken();
            string2 = stylesheetHandler.getNamespaceForPrefix(string);
            if (string2 != null) {
                ((StringVector)object).addElement(string2);
                continue;
            }
            throw new SAXException(XSLMessages.createMessage("ER_CANT_RESOLVE_NSPREFIX", new Object[]{string}));
        }
        return object;
    }

    Object processQNAME(StylesheetHandler stylesheetHandler, String object, String string, String string2, String string3, ElemTemplateElement elemTemplateElement) throws SAXException {
        try {
            object = new QName(string3, stylesheetHandler, true);
            return object;
        }
        catch (RuntimeException runtimeException) {
            this.handleError(stylesheetHandler, "INVALID_QNAME", new Object[]{string, string3}, runtimeException);
            return null;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            this.handleError(stylesheetHandler, "INVALID_QNAME", new Object[]{string, string3}, illegalArgumentException);
            return null;
        }
    }

    Vector processQNAMES(StylesheetHandler stylesheetHandler, String object, String object2, String string, String string2) throws SAXException {
        object = new StringTokenizer(string2, " \t\n\r\f");
        int n = ((StringTokenizer)object).countTokens();
        object2 = new Vector(n);
        for (int i = 0; i < n; ++i) {
            ((Vector)object2).addElement(new QName(((StringTokenizer)object).nextToken(), stylesheetHandler));
        }
        return object2;
    }

    final Vector processQNAMESRNU(StylesheetHandler stylesheetHandler, String object, String string, String object2, String string2) throws SAXException {
        object = new StringTokenizer(string2, " \t\n\r\f");
        int n = ((StringTokenizer)object).countTokens();
        object2 = new Vector(n);
        string = stylesheetHandler.getNamespaceForPrefix("");
        for (int i = 0; i < n; ++i) {
            string2 = ((StringTokenizer)object).nextToken();
            if (string2.indexOf(58) == -1) {
                ((Vector)object2).addElement(new QName(string, string2));
                continue;
            }
            ((Vector)object2).addElement(new QName(string2, stylesheetHandler));
        }
        return object2;
    }

    Vector processSIMPLEPATTERNLIST(StylesheetHandler stylesheetHandler, String object, String vector, String string, String string2, ElemTemplateElement elemTemplateElement) throws SAXException {
        int n;
        try {
            object = new StringTokenizer(string2, " \t\n\r\f");
            n = ((StringTokenizer)object).countTokens();
            vector = new Vector<XPath>(n);
        }
        catch (TransformerException transformerException) {
            throw new SAXException(transformerException);
        }
        for (int i = 0; i < n; ++i) {
            vector.addElement(stylesheetHandler.createMatchPatternXPath(((StringTokenizer)object).nextToken(), elemTemplateElement));
            continue;
        }
        return vector;
    }

    StringVector processSTRINGLIST(StylesheetHandler object, String object2, String string, String string2, String string3) {
        object2 = new StringTokenizer(string3, " \t\n\r\f");
        int n = ((StringTokenizer)object2).countTokens();
        object = new StringVector(n);
        for (int i = 0; i < n; ++i) {
            ((StringVector)object).addElement(((StringTokenizer)object2).nextToken());
        }
        return object;
    }

    Object processURL(StylesheetHandler object, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        if (this.getSupportsAVT()) {
            try {
                object = new AVT((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                return object;
            }
            catch (TransformerException transformerException) {
                throw new SAXException(transformerException);
            }
        }
        return string4;
    }

    Object processValue(StylesheetHandler object, String string, String string2, String string3, String string4, ElemTemplateElement elemTemplateElement) throws SAXException {
        int n = this.getType();
        Object var8_8 = null;
        switch (n) {
            default: {
                object = var8_8;
                break;
            }
            case 20: {
                object = this.processPREFIX_LIST((StylesheetHandler)object, string, string2, string3, string4);
                break;
            }
            case 19: {
                object = this.processQNAMESRNU((StylesheetHandler)object, string, string2, string3, string4);
                break;
            }
            case 18: {
                object = this.processAVT_QNAME((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 17: {
                object = this.processNCNAME((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 16: {
                object = this.processENUM_OR_PQNAME((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 15: {
                object = this.processPREFIX_URLLIST((StylesheetHandler)object, string, string2, string3, string4);
                break;
            }
            case 14: {
                object = this.processSTRINGLIST((StylesheetHandler)object, string, string2, string3, string4);
                break;
            }
            case 13: {
                object = this.processNMTOKEN((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 12: {
                object = this.processSIMPLEPATTERNLIST((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 11: {
                object = this.processENUM((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 10: {
                object = this.processQNAMES((StylesheetHandler)object, string, string2, string3, string4);
                break;
            }
            case 9: {
                object = this.processQNAME((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 8: {
                object = this.processYESNO((StylesheetHandler)object, string, string2, string3, string4);
                break;
            }
            case 7: {
                object = this.processNUMBER((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 6: {
                object = this.processCHAR((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 5: {
                object = this.processEXPR((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 4: {
                object = this.processPATTERN((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 3: {
                object = this.processAVT((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 2: {
                object = this.processURL((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
                break;
            }
            case 1: {
                object = this.processCDATA((StylesheetHandler)object, string, string2, string3, string4, elemTemplateElement);
            }
        }
        return object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    boolean setAttrValue(StylesheetHandler var1_1, String var2_2, String var3_18, String var4_19, String var5_20, ElemTemplateElement var6_21) throws SAXException {
        block24 : {
            block23 : {
                block22 : {
                    block20 : {
                        block21 : {
                            if (var4_19.equals("xmlns") != false) return true;
                            if (var4_19.startsWith("xmlns:")) {
                                return true;
                            }
                            var7_22 = this.getSetterMethodName();
                            if (var7_22 == null) return true;
                            var8_23 = var7_22.equals("setForeignAttr");
                            if (!var8_23) break block20;
                            if (var2_2 != null) break block21;
                            var2_2 = "";
                        }
                        try {
                            var9_24 = var2_2.getClass();
                            var9_24 = var6_21.getClass().getMethod(var7_22, new Class[]{var9_24, var9_24, var9_24, var9_24});
                        }
                        catch (InvocationTargetException var2_5) {
                            break block22;
                        }
                        catch (IllegalAccessException var2_6) {
                            break block23;
                        }
                        catch (NoSuchMethodException var2_7) {
                            break block24;
                        }
                        var2_2 = new Object[]{var2_2, var3_18, var4_19, var5_20};
                        var3_18 = var9_24;
                        ** GOTO lbl44
                    }
                    var3_18 = this.processValue(var1_1, (String)var2_2, (String)var3_18, (String)var4_19, var5_20, var6_21);
                    if (var3_18 == null) {
                        return false;
                    }
                    var4_19 = new Class[]{this.getPrimativeClass(var3_18)};
                    try {
                        var2_2 = var6_21.getClass().getMethod(var7_22, (Class<?>[])var4_19);
                        ** GOTO lbl41
                    }
                    catch (InvocationTargetException var2_3) {
                    }
                    catch (IllegalAccessException var2_4) {
                        break block23;
                    }
                    catch (NoSuchMethodException var2_8) {
                        var4_19[0] = var3_18.getClass();
                        var2_2 = var6_21.getClass().getMethod(var7_22, (Class<?>[])var4_19);
lbl41: // 2 sources:
                        var4_19 = new Object[]{var3_18};
                        var3_18 = var2_2;
                        var2_2 = var4_19;
lbl44: // 2 sources:
                        try {
                            var3_18.invoke((Object)var6_21, (Object[])var2_2);
                            return true;
                        }
                        catch (InvocationTargetException var2_9) {
                            break block22;
                        }
                        catch (IllegalAccessException var2_10) {
                            break block23;
                        }
                        catch (NoSuchMethodException var2_11) {
                            break block24;
                        }
                        catch (InvocationTargetException var2_12) {
                            break block22;
                        }
                        catch (IllegalAccessException var2_13) {
                            break block23;
                        }
                        catch (NoSuchMethodException var2_14) {
                            break block24;
                        }
                    }
                }
                this.handleError(var1_1, "WG_ILLEGAL_ATTRIBUTE_VALUE", new Object[]{"name", this.getName()}, (Exception)var2_15);
                return false;
            }
            var1_1.error("ER_FAILED_CALLING_METHOD", new Object[]{var7_22}, (Exception)var2_16);
            return false;
        }
        if (var7_22.equals("setForeignAttr") != false) return true;
        var1_1.error("ER_FAILED_CALLING_METHOD", new Object[]{var7_22}, (Exception)var2_17);
        return false;
    }

    void setDefAttrValue(StylesheetHandler stylesheetHandler, ElemTemplateElement elemTemplateElement) throws SAXException {
        this.setAttrValue(stylesheetHandler, this.getNamespace(), this.getName(), this.getName(), this.getDefault(), elemTemplateElement);
    }

    void setDefault(String string) {
        this.m_default = string;
    }
}

