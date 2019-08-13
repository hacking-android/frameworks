/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.io.PrintStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;
import java.util.Vector;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.templates.AVT;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xalan.templates.XSLTVisitor;
import org.apache.xalan.transformer.CountersTable;
import org.apache.xalan.transformer.DecimalToRoman;
import org.apache.xalan.transformer.MsgMgr;
import org.apache.xalan.transformer.TransformerImpl;
import org.apache.xml.dtm.DTM;
import org.apache.xml.dtm.DTMManager;
import org.apache.xml.serializer.SerializationHandler;
import org.apache.xml.utils.FastStringBuffer;
import org.apache.xml.utils.NodeVector;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.StringBufferPool;
import org.apache.xml.utils.res.CharArrayWrapper;
import org.apache.xml.utils.res.IntArrayWrapper;
import org.apache.xml.utils.res.LongArrayWrapper;
import org.apache.xml.utils.res.StringArrayWrapper;
import org.apache.xml.utils.res.XResourceBundle;
import org.apache.xpath.Expression;
import org.apache.xpath.ExpressionOwner;
import org.apache.xpath.NodeSetDTM;
import org.apache.xpath.XPath;
import org.apache.xpath.XPathContext;
import org.apache.xpath.XPathVisitor;
import org.apache.xpath.objects.XObject;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public class ElemNumber
extends ElemTemplateElement {
    private static final DecimalToRoman[] m_romanConvertTable = new DecimalToRoman[]{new DecimalToRoman(1000L, "M", 900L, "CM"), new DecimalToRoman(500L, "D", 400L, "CD"), new DecimalToRoman(100L, "C", 90L, "XC"), new DecimalToRoman(50L, "L", 40L, "XL"), new DecimalToRoman(10L, "X", 9L, "IX"), new DecimalToRoman(5L, "V", 4L, "IV"), new DecimalToRoman(1L, "I", 1L, "I")};
    static final long serialVersionUID = 8118472298274407610L;
    private CharArrayWrapper m_alphaCountTable = null;
    private XPath m_countMatchPattern = null;
    private AVT m_format_avt = null;
    private XPath m_fromMatchPattern = null;
    private AVT m_groupingSeparator_avt = null;
    private AVT m_groupingSize_avt = null;
    private AVT m_lang_avt = null;
    private AVT m_lettervalue_avt = null;
    private int m_level = 1;
    private XPath m_valueExpr = null;

    private int findPrecedingOrAncestorOrSelf(XPathContext xPathContext, XPath xPath, XPath xPath2, int n, ElemNumber object) throws TransformerException {
        int n2;
        object = xPathContext.getDTM(n);
        do {
            int n3;
            n2 = n;
            if (-1 == n) break;
            if (xPath != null && xPath.getMatchScore(xPathContext, n) != Double.NEGATIVE_INFINITY) {
                n2 = -1;
                break;
            }
            if (xPath2 != null && xPath2.getMatchScore(xPathContext, n) != Double.NEGATIVE_INFINITY) {
                n2 = n;
                break;
            }
            n2 = object.getPreviousSibling(n);
            if (-1 == n2) {
                n = object.getParent(n);
                continue;
            }
            n = n3 = object.getLastChild(n2);
            if (n3 != -1) continue;
            n = n2;
        } while (true);
        return n2;
    }

    private void getFormattedNumber(TransformerImpl object, int n, char c, int n2, long l, FastStringBuffer fastStringBuffer) throws TransformerException {
        block46 : {
            Object object2 = this.m_lettervalue_avt;
            object2 = object2 != null ? ((AVT)object2).evaluate(((TransformerImpl)object).getXPathContext(), n, this) : null;
            switch (c) {
                default: {
                    object2 = this.getNumberFormatter((TransformerImpl)object, n);
                    object = object2 == null ? String.valueOf(0) : ((NumberFormat)object2).format(0L);
                }
                case '\u58f9': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("zh", "TW"));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        this.int2alphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet"), fastStringBuffer);
                    }
                    break block46;
                }
                case '\u4e00': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("zh", "CN"));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        this.int2alphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet"), fastStringBuffer);
                    }
                    break block46;
                }
                case '\u30a4': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ja", "JP", "I"));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        fastStringBuffer.append(this.int2singlealphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet")));
                    }
                    break block46;
                }
                case '\u30a2': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ja", "JP", "A"));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        fastStringBuffer.append(this.int2singlealphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet")));
                    }
                    break block46;
                }
                case '\u3044': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ja", "JP", "HI"));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        fastStringBuffer.append(this.int2singlealphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet")));
                    }
                    break block46;
                }
                case '\u3042': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ja", "JP", "HA"));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        fastStringBuffer.append(this.int2singlealphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet")));
                    }
                    break block46;
                }
                case '\u10d0': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("ka", ""));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        this.int2alphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet"), fastStringBuffer);
                    }
                    break block46;
                }
                case '\u0e51': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("th", ""));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        this.int2alphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet"), fastStringBuffer);
                    }
                    break block46;
                }
                case '\u05d0': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("he", ""));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        this.int2alphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet"), fastStringBuffer);
                    }
                    break block46;
                }
                case '\u0430': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("cy", ""));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        this.int2alphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet"), fastStringBuffer);
                    }
                    break block46;
                }
                case '\u03b1': {
                    object = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", new Locale("el", ""));
                    if (object2 != null && ((String)object2).equals("traditional")) {
                        fastStringBuffer.append(this.tradAlphaCount(l, (XResourceBundle)object));
                    } else {
                        this.int2alphaCount(l, (CharArrayWrapper)((ResourceBundle)object).getObject("alphabet"), fastStringBuffer);
                    }
                    break block46;
                }
                case 'i': {
                    fastStringBuffer.append(this.long2roman(l, true).toLowerCase(this.getLocale((TransformerImpl)object, n)));
                    break block46;
                }
                case 'a': {
                    if (this.m_alphaCountTable == null) {
                        object2 = XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", this.getLocale((TransformerImpl)object, n));
                        this.m_alphaCountTable = (CharArrayWrapper)((ResourceBundle)object2).getObject("alphabet");
                    } else {
                        object2 = null;
                    }
                    object2 = StringBufferPool.get();
                    try {
                        this.int2alphaCount(l, this.m_alphaCountTable, (FastStringBuffer)object2);
                        fastStringBuffer.append(((FastStringBuffer)object2).toString().toLowerCase(this.getLocale((TransformerImpl)object, n)));
                        break block46;
                    }
                    finally {
                        StringBufferPool.free((FastStringBuffer)object2);
                    }
                }
                case 'I': {
                    fastStringBuffer.append(this.long2roman(l, true));
                    break block46;
                }
                case 'A': {
                    if (this.m_alphaCountTable == null) {
                        this.m_alphaCountTable = (CharArrayWrapper)XResourceBundle.loadResourceBundle("org.apache.xml.utils.res.XResources", this.getLocale((TransformerImpl)object, n)).getObject("alphabet");
                    }
                    this.int2alphaCount(l, this.m_alphaCountTable, fastStringBuffer);
                    break block46;
                }
            }
            object2 = object2 == null ? String.valueOf(l) : ((NumberFormat)object2).format(l);
            c = (char)((String)object2).length();
            for (n = 0; n < n2 - c; ++n) {
                fastStringBuffer.append((String)object);
            }
            fastStringBuffer.append((String)object2);
        }
    }

    private DecimalFormat getNumberFormatter(TransformerImpl object, int n) throws TransformerException {
        AVT aVT;
        Locale locale = (Locale)this.getLocale((TransformerImpl)object, n).clone();
        Object var4_4 = null;
        DecimalFormat decimalFormat = null;
        Object object2 = this.m_groupingSeparator_avt;
        Object object3 = null;
        if ((object2 = object2 != null ? ((AVT)object2).evaluate(((TransformerImpl)object).getXPathContext(), n, this) : null) != null && !this.m_groupingSeparator_avt.isSimple() && ((String)object2).length() != 1) {
            ((TransformerImpl)object).getMsgMgr().warn(this, "WG_ILLEGAL_ATTRIBUTE_VALUE", new Object[]{"name", this.m_groupingSeparator_avt.getName()});
        }
        if ((aVT = this.m_groupingSize_avt) != null) {
            object3 = aVT.evaluate(((TransformerImpl)object).getXPathContext(), n, this);
        }
        object = var4_4;
        if (object2 != null) {
            object = var4_4;
            if (object3 != null) {
                object = var4_4;
                if (((String)object2).length() > 0) {
                    object = decimalFormat;
                    decimalFormat = (DecimalFormat)NumberFormat.getNumberInstance(locale);
                    object = decimalFormat;
                    decimalFormat.setGroupingSize(Integer.valueOf((String)object3));
                    object = decimalFormat;
                    object3 = decimalFormat.getDecimalFormatSymbols();
                    object = decimalFormat;
                    ((DecimalFormatSymbols)object3).setGroupingSeparator(((String)object2).charAt(0));
                    object = decimalFormat;
                    decimalFormat.setDecimalFormatSymbols((DecimalFormatSymbols)object3);
                    object = decimalFormat;
                    try {
                        decimalFormat.setGroupingUsed(true);
                        object = decimalFormat;
                    }
                    catch (NumberFormatException numberFormatException) {
                        ((DecimalFormat)object).setGroupingUsed(false);
                    }
                }
            }
        }
        return object;
    }

    @Override
    public ElemTemplateElement appendChild(ElemTemplateElement elemTemplateElement) {
        this.error("ER_CANNOT_ADD", new Object[]{elemTemplateElement.getNodeName(), this.getNodeName()});
        return null;
    }

    @Override
    public void callChildVisitors(XSLTVisitor xSLTVisitor, boolean bl) {
        if (bl) {
            Serializable serializable = this.m_countMatchPattern;
            if (serializable != null) {
                ((XPath)serializable).getExpression().callVisitors(this.m_countMatchPattern, xSLTVisitor);
            }
            if ((serializable = this.m_fromMatchPattern) != null) {
                ((XPath)serializable).getExpression().callVisitors(this.m_fromMatchPattern, xSLTVisitor);
            }
            if ((serializable = this.m_valueExpr) != null) {
                ((XPath)serializable).getExpression().callVisitors(this.m_valueExpr, xSLTVisitor);
            }
            if ((serializable = this.m_format_avt) != null) {
                ((AVT)serializable).callVisitors(xSLTVisitor);
            }
            if ((serializable = this.m_groupingSeparator_avt) != null) {
                ((AVT)serializable).callVisitors(xSLTVisitor);
            }
            if ((serializable = this.m_groupingSize_avt) != null) {
                ((AVT)serializable).callVisitors(xSLTVisitor);
            }
            if ((serializable = this.m_lang_avt) != null) {
                ((AVT)serializable).callVisitors(xSLTVisitor);
            }
            if ((serializable = this.m_lettervalue_avt) != null) {
                ((AVT)serializable).callVisitors(xSLTVisitor);
            }
        }
        super.callChildVisitors(xSLTVisitor, bl);
    }

    @Override
    public void compose(StylesheetRoot object) throws TransformerException {
        super.compose((StylesheetRoot)object);
        object = ((StylesheetRoot)object).getComposeState();
        Vector vector = ((StylesheetRoot.ComposeState)object).getVariableNames();
        Serializable serializable = this.m_countMatchPattern;
        if (serializable != null) {
            ((XPath)serializable).fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
        if ((serializable = this.m_format_avt) != null) {
            ((AVT)serializable).fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
        if ((serializable = this.m_fromMatchPattern) != null) {
            ((XPath)serializable).fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
        if ((serializable = this.m_groupingSeparator_avt) != null) {
            ((AVT)serializable).fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
        if ((serializable = this.m_groupingSize_avt) != null) {
            ((AVT)serializable).fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
        if ((serializable = this.m_lang_avt) != null) {
            ((AVT)serializable).fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
        if ((serializable = this.m_lettervalue_avt) != null) {
            ((AVT)serializable).fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
        if ((serializable = this.m_valueExpr) != null) {
            ((XPath)serializable).fixupVariables(vector, ((StylesheetRoot.ComposeState)object).getGlobalsSize());
        }
    }

    @Override
    public void execute(TransformerImpl transformerImpl) throws TransformerException {
        String string = this.getCountString(transformerImpl, transformerImpl.getXPathContext().getCurrentNode());
        try {
            transformerImpl.getResultTreeHandler().characters(string.toCharArray(), 0, string.length());
            return;
        }
        catch (SAXException sAXException) {
            throw new TransformerException(sAXException);
        }
    }

    int findAncestor(XPathContext xPathContext, XPath xPath, XPath xPath2, int n, ElemNumber object) throws TransformerException {
        object = xPathContext.getDTM(n);
        while (!(-1 == n || xPath != null && xPath.getMatchScore(xPathContext, n) != Double.NEGATIVE_INFINITY || xPath2 != null && xPath2.getMatchScore(xPathContext, n) != Double.NEGATIVE_INFINITY)) {
            n = object.getParent(n);
        }
        return n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    String formatNumberList(TransformerImpl var1_1, long[] var2_5, int var3_6) throws TransformerException {
        block22 : {
            var4_7 = StringBufferPool.get();
            var5_8 = var2_5.length;
            var6_9 = 1;
            var7_10 = 49;
            var8_11 = null;
            var9_12 = null;
            var10_13 = ".";
            if (this.m_format_avt == null) ** GOTO lbl15
            var11_14 = this.m_format_avt;
            var12_15 = var1_1.getXPathContext();
            try {
                block24 : {
                    block23 : {
                        var12_15 = var11_14.evaluate((XPathContext)var12_15, var3_6, this);
                        break block23;
lbl15: // 1 sources:
                        var12_15 = null;
                    }
                    if (var12_15 == null) {
                        var12_15 = "1";
                    }
                    var11_14 = new NumberFormatStringTokenizer((String)var12_15);
                    var13_16 = true;
                    var12_15 = var10_13;
                    break block24;
                    catch (Throwable var1_3) {
                        // empty catch block
                    }
                    break block22;
                }
                for (var14_17 = 0; var14_17 < var5_8; ++var14_17) {
                    if (var11_14.hasMoreTokens()) {
                        var10_13 = var11_14.nextToken();
                        if (Character.isLetterOrDigit(var10_13.charAt(var10_13.length() - 1))) {
                            var7_10 = var10_13.length();
                            var15_18 = var6_9 = var10_13.charAt(var7_10 - 1);
                        } else if (var11_14.isLetterOrDigitAhead()) {
                            var9_12 = var10_13;
                            while (var11_14.nextIsSep()) {
                                var10_13 = var11_14.nextToken();
                                var16_19 = new StringBuilder();
                                var16_19.append(var9_12);
                                var16_19.append(var10_13);
                                var9_12 = var16_19.toString();
                            }
                            if (!var13_16) {
                                var12_15 = var9_12;
                            }
                            var10_13 = var11_14.nextToken();
                            var7_10 = var10_13.length();
                            var15_18 = var6_9 = (int)var10_13.charAt(var7_10 - 1);
                        } else {
                            var8_11 = var10_13;
                            while (var11_14.hasMoreTokens()) {
                                var10_13 = var11_14.nextToken();
                                var16_19 = new StringBuilder();
                                var16_19.append(var8_11);
                                var16_19.append(var10_13);
                                var8_11 = var16_19.toString();
                            }
                            var17_20 = var7_10;
                            var7_10 = var6_9;
                            var15_18 = var17_20;
                        }
                    } else {
                        var15_18 = var7_10;
                        var7_10 = var6_9;
                    }
                    if (var9_12 != null && var13_16) {
                        var4_7.append(var9_12);
                    } else if (var12_15 != null && !var13_16) {
                        var4_7.append((String)var12_15);
                    }
                    this.getFormattedNumber((TransformerImpl)var1_1, var3_6, (char)var15_18, var7_10, var2_5[var14_17], var4_7);
                    var13_16 = false;
                    var17_20 = var15_18;
                    var6_9 = var7_10;
                    var7_10 = var17_20;
                }
                while (var11_14.isLetterOrDigitAhead()) {
                    var11_14.nextToken();
                }
                if (var8_11 != null) {
                    var4_7.append(var8_11);
                }
                while (var11_14.hasMoreTokens()) {
                    var4_7.append(var11_14.nextToken());
                }
                var1_1 = var4_7.toString();
            }
            catch (Throwable var1_2) {}
            StringBufferPool.free(var4_7);
            return var1_1;
        }
        StringBufferPool.free(var4_7);
        throw var1_4;
    }

    public XPath getCount() {
        return this.m_countMatchPattern;
    }

    XPath getCountMatchPattern(XPathContext xPathContext, int n) throws TransformerException {
        XPath xPath = this.m_countMatchPattern;
        DTM dTM = xPathContext.getDTM(n);
        Object object = xPath;
        if (xPath == null) {
            short s = dTM.getNodeType(n);
            if (s != 1) {
                if (s != 2) {
                    if (s != 3 && s != 4) {
                        if (s != 7) {
                            object = s != 8 ? (s != 9 ? null : new XPath("/", this, this, 1, xPathContext.getErrorListener())) : new XPath("comment()", this, this, 1, xPathContext.getErrorListener());
                        } else {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("pi(");
                            ((StringBuilder)object).append(dTM.getNodeName(n));
                            ((StringBuilder)object).append(")");
                            object = new XPath(((StringBuilder)object).toString(), this, this, 1, xPathContext.getErrorListener());
                        }
                    } else {
                        object = new XPath("text()", this, this, 1, xPathContext.getErrorListener());
                    }
                } else {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("@");
                    ((StringBuilder)object).append(dTM.getNodeName(n));
                    object = new XPath(((StringBuilder)object).toString(), this, this, 1, xPathContext.getErrorListener());
                }
            } else {
                object = dTM.getNamespaceURI(n) == null ? new MyPrefixResolver(dTM.getNode(n), dTM, n, false) : new MyPrefixResolver(dTM.getNode(n), dTM, n, true);
                object = new XPath(dTM.getNodeName(n), this, (PrefixResolver)object, 1, xPathContext.getErrorListener());
            }
        }
        return object;
    }

    String getCountString(TransformerImpl object, int n) throws TransformerException {
        long[] arrl = null;
        XPathContext xPathContext = ((TransformerImpl)object).getXPathContext();
        CountersTable countersTable = ((TransformerImpl)object).getCountersTable();
        long[] arrl2 = this.m_valueExpr;
        boolean bl = false;
        if (arrl2 != null) {
            double d = Math.floor(arrl2.execute(xPathContext, n, (PrefixResolver)this).num() + 0.5);
            if (Double.isNaN(d)) {
                return "NaN";
            }
            if (d < 0.0 && Double.isInfinite(d)) {
                return "-Infinity";
            }
            if (Double.isInfinite(d)) {
                return "Infinity";
            }
            if (d == 0.0) {
                return "0";
            }
            long l = (long)d;
            arrl = new long[]{l};
        } else {
            int n2 = this.m_level;
            if (3 == n2) {
                arrl = new long[]{countersTable.countNode(xPathContext, this, n)};
            } else {
                NodeVector nodeVector;
                int n3;
                if (1 == n2) {
                    bl = true;
                }
                if ((n3 = (nodeVector = this.getMatchingAncestors(xPathContext, n, bl)).size() - 1) >= 0) {
                    arrl2 = new long[n3 + 1];
                    n2 = n3;
                    do {
                        arrl = arrl2;
                        if (n2 < 0) break;
                        arrl2[n3 - n2] = countersTable.countNode(xPathContext, this, nodeVector.elementAt(n2));
                        --n2;
                    } while (true);
                }
            }
        }
        object = arrl != null ? this.formatNumberList((TransformerImpl)object, arrl, n) : "";
        return object;
    }

    public AVT getFormat() {
        return this.m_format_avt;
    }

    public XPath getFrom() {
        return this.m_fromMatchPattern;
    }

    public AVT getGroupingSeparator() {
        return this.m_groupingSeparator_avt;
    }

    public AVT getGroupingSize() {
        return this.m_groupingSize_avt;
    }

    public AVT getLang() {
        return this.m_lang_avt;
    }

    public AVT getLetterValue() {
        return this.m_lettervalue_avt;
    }

    public int getLevel() {
        return this.m_level;
    }

    Locale getLocale(TransformerImpl object, int n) throws TransformerException {
        Object var3_3 = null;
        if (this.m_lang_avt != null) {
            object = ((TransformerImpl)object).getXPathContext();
            String string = this.m_lang_avt.evaluate((XPathContext)object, n, this);
            object = var3_3;
            if (string != null) {
                object = new Locale(string.toUpperCase(), "");
            }
        } else {
            object = Locale.getDefault();
        }
        return object;
    }

    NodeVector getMatchingAncestors(XPathContext xPathContext, int n, boolean bl) throws TransformerException {
        XPath xPath;
        NodeSetDTM nodeSetDTM = new NodeSetDTM(xPathContext.getDTMManager());
        XPath xPath2 = this.getCountMatchPattern(xPathContext, n);
        DTM dTM = xPathContext.getDTM(n);
        while (-1 != n && ((xPath = this.m_fromMatchPattern) == null || xPath.getMatchScore(xPathContext, n) == Double.NEGATIVE_INFINITY || bl)) {
            if (xPath2 == null) {
                System.out.println("Programmers error! countMatchPattern should never be null!");
            }
            if (xPath2.getMatchScore(xPathContext, n) != Double.NEGATIVE_INFINITY) {
                nodeSetDTM.addElement(n);
                if (bl) break;
            }
            n = dTM.getParent(n);
        }
        return nodeSetDTM;
    }

    @Override
    public String getNodeName() {
        return "number";
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public int getPreviousNode(XPathContext var1_1, int var2_2) throws TransformerException {
        block8 : {
            var3_3 = this.getCountMatchPattern(var1_1, var2_2);
            var4_4 = var1_1.getDTM(var2_2);
            var5_5 = var2_2;
            if (3 == this.m_level) break block8;
            do lbl-1000: // 3 sources:
            {
                var2_2 = var5_5;
                if (-1 == var5_5) return var2_2;
                var5_5 = var7_8 = var4_4.getPreviousSibling(var5_5);
                if (-1 == var7_8) ** GOTO lbl-1000
                var2_2 = var7_8;
                if (var3_3 == null) return var2_2;
                var5_5 = var7_8;
            } while (var3_3.getMatchScore(var1_1, var7_8) == Double.NEGATIVE_INFINITY);
            return var7_8;
        }
        var6_6 = this.m_fromMatchPattern;
        var5_5 = var2_2;
        do lbl-1000: // 3 sources:
        {
            var2_2 = var5_5;
            if (-1 == var5_5) return var2_2;
            var2_2 = var4_4.getPreviousSibling(var5_5);
            if (-1 == var2_2) {
                var2_2 = var5_5 = var4_4.getParent(var5_5);
                if (-1 != var5_5) {
                    if (var6_6 != null) {
                        if (var6_6.getMatchScore(var1_1, var5_5) != Double.NEGATIVE_INFINITY) return -1;
                    }
                    var2_2 = var5_5;
                    if (var4_4.getNodeType(var5_5) == 9) {
                        return -1;
                    }
                }
            } else {
                var7_7 = var2_2;
                var5_5 = var2_2;
                do {
                    var2_2 = var5_5;
                    if (-1 == var7_7) break;
                    var7_7 = var2_2 = var4_4.getLastChild(var5_5);
                    if (-1 == var2_2) continue;
                    var5_5 = var2_2;
                    var7_7 = var2_2;
                } while (true);
            }
            if (-1 == (var5_5 = var2_2)) ** GOTO lbl-1000
            var2_2 = var5_5;
            if (var3_3 == null) return var2_2;
        } while (var3_3.getMatchScore(var1_1, var5_5) == Double.NEGATIVE_INFINITY);
        return var5_5;
    }

    public int getTargetNode(XPathContext xPathContext, int n) throws TransformerException {
        XPath xPath = this.getCountMatchPattern(xPathContext, n);
        n = 3 == this.m_level ? this.findPrecedingOrAncestorOrSelf(xPathContext, this.m_fromMatchPattern, xPath, n, this) : this.findAncestor(xPathContext, this.m_fromMatchPattern, xPath, n, this);
        return n;
    }

    public XPath getValue() {
        return this.m_valueExpr;
    }

    @Override
    public int getXSLToken() {
        return 35;
    }

    String getZeroString() {
        return "0";
    }

    protected void int2alphaCount(long l, CharArrayWrapper arrc, FastStringBuffer fastStringBuffer) {
        int n;
        int n2 = arrc.getLength();
        char[] arrc2 = new char[n2];
        for (n = 0; n < n2 - 1; ++n) {
            arrc2[n + 1] = arrc.getChar(n);
        }
        arrc2[0] = arrc.getChar(n);
        arrc = new char[100];
        n = arrc.length - 1;
        int n3 = 1;
        long l2 = 0L;
        long l3 = l;
        l = l2;
        do {
            int n4;
            block7 : {
                block6 : {
                    block5 : {
                        l = n3 != 0 && (l == 0L || n3 != n2 - 1) ? 0L : (long)(n2 - 1);
                        n4 = (int)(l3 + l) % n2;
                        if (n4 != 0 || (l3 /= (long)n2) != 0L) break block5;
                        n3 = n;
                        break block6;
                    }
                    n3 = n - 1;
                    arrc[n] = arrc2[n4];
                    if (l3 > 0L) break block7;
                }
                fastStringBuffer.append(arrc, n3 + 1, arrc.length - n3 - 1);
                return;
            }
            n = n3;
            n3 = n4;
        } while (true);
    }

    protected String int2singlealphaCount(long l, CharArrayWrapper charArrayWrapper) {
        if (l > (long)charArrayWrapper.getLength()) {
            return this.getZeroString();
        }
        return new Character(charArrayWrapper.getChar((int)l - 1)).toString();
    }

    protected String long2roman(long l, boolean bl) {
        CharSequence charSequence;
        if (l <= 0L) {
            return this.getZeroString();
        }
        CharSequence charSequence2 = "";
        int n = 0;
        if (l <= 3999L) {
            do {
                if (l >= ElemNumber.m_romanConvertTable[n].m_postValue) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append((String)charSequence2);
                    ((StringBuilder)charSequence).append(ElemNumber.m_romanConvertTable[n].m_postLetter);
                    charSequence2 = ((StringBuilder)charSequence).toString();
                    l -= ElemNumber.m_romanConvertTable[n].m_postValue;
                    continue;
                }
                charSequence = charSequence2;
                long l2 = l;
                if (bl) {
                    charSequence = charSequence2;
                    l2 = l;
                    if (l >= ElemNumber.m_romanConvertTable[n].m_preValue) {
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append((String)charSequence2);
                        ((StringBuilder)charSequence).append(ElemNumber.m_romanConvertTable[n].m_preLetter);
                        charSequence = ((StringBuilder)charSequence).toString();
                        l2 = l - ElemNumber.m_romanConvertTable[n].m_preValue;
                    }
                }
                ++n;
                charSequence2 = charSequence;
                l = l2;
                if (l2 <= 0L) break;
            } while (true);
        } else {
            charSequence = "#error";
        }
        return charSequence;
    }

    public void setCount(XPath xPath) {
        this.m_countMatchPattern = xPath;
    }

    public void setFormat(AVT aVT) {
        this.m_format_avt = aVT;
    }

    public void setFrom(XPath xPath) {
        this.m_fromMatchPattern = xPath;
    }

    public void setGroupingSeparator(AVT aVT) {
        this.m_groupingSeparator_avt = aVT;
    }

    public void setGroupingSize(AVT aVT) {
        this.m_groupingSize_avt = aVT;
    }

    public void setLang(AVT aVT) {
        this.m_lang_avt = aVT;
    }

    public void setLetterValue(AVT aVT) {
        this.m_lettervalue_avt = aVT;
    }

    public void setLevel(int n) {
        this.m_level = n;
    }

    public void setValue(XPath xPath) {
        this.m_valueExpr = xPath;
    }

    protected String tradAlphaCount(long l, XResourceBundle xResourceBundle) {
        if (l > Long.MAX_VALUE) {
            this.error("ER_NUMBER_TOO_BIG");
            return "#error";
        }
        char[] arrc = null;
        int n = 1;
        char[] arrc2 = new char[100];
        int n2 = 0;
        int n3 = 0;
        IntArrayWrapper intArrayWrapper = (IntArrayWrapper)xResourceBundle.getObject("numberGroups");
        StringArrayWrapper stringArrayWrapper = (StringArrayWrapper)xResourceBundle.getObject("tables");
        char[] arrc3 = xResourceBundle.getString("numbering");
        if (arrc3.equals("multiplicative-additive")) {
            String string = xResourceBundle.getString("multiplierOrder");
            LongArrayWrapper longArrayWrapper = (LongArrayWrapper)xResourceBundle.getObject("multiplier");
            CharArrayWrapper charArrayWrapper = (CharArrayWrapper)xResourceBundle.getObject("zero");
            for (n2 = 0; n2 < longArrayWrapper.getLength() && l < longArrayWrapper.getLong(n2); ++n2) {
            }
            int n4 = n2;
            n2 = n3;
            while (n4 < longArrayWrapper.getLength()) {
                if (l < longArrayWrapper.getLong(n4)) {
                    if (charArrayWrapper.getLength() == 0) {
                        n3 = n4 + 1;
                    } else {
                        if (arrc2[n2 - 1] != charArrayWrapper.getChar(0)) {
                            n3 = n2 + 1;
                            arrc2[n2] = charArrayWrapper.getChar(0);
                            n2 = n3;
                        }
                        n3 = n4 + 1;
                    }
                } else if (l >= longArrayWrapper.getLong(n4)) {
                    block22 : {
                        long l2 = l / longArrayWrapper.getLong(n4);
                        l %= longArrayWrapper.getLong(n4);
                        for (n3 = 0; n3 < intArrayWrapper.getLength(); ++n3) {
                            if (l2 / (long)intArrayWrapper.getInt(n3) <= 0L) {
                                n = 1;
                                continue;
                            }
                            CharArrayWrapper charArrayWrapper2 = (CharArrayWrapper)xResourceBundle.getObject(stringArrayWrapper.getString(n3));
                            arrc = new char[charArrayWrapper2.getLength() + 1];
                            for (n = 0; n < charArrayWrapper2.getLength(); ++n) {
                                arrc[n + 1] = charArrayWrapper2.getChar(n);
                            }
                            arrc[0] = charArrayWrapper2.getChar(n - 1);
                            if ((n3 = (int)l2 / intArrayWrapper.getInt(n3)) == 0 && l2 == 0L) break block22;
                            char c = ((CharArrayWrapper)xResourceBundle.getObject("multiplierChar")).getChar(n4);
                            if (n3 < arrc.length) {
                                if (string.equals("precedes")) {
                                    n = n2 + 1;
                                    arrc2[n2] = c;
                                    n2 = n + 1;
                                    arrc2[n] = arrc[n3];
                                } else {
                                    if (n3 != 1 || n4 != longArrayWrapper.getLength() - 1) {
                                        n = n2 + 1;
                                        arrc2[n2] = arrc[n3];
                                        n2 = n;
                                    }
                                    n = n2 + 1;
                                    arrc2[n2] = c;
                                    n2 = n;
                                }
                                break block22;
                            }
                            return "#error";
                        }
                        n3 = n;
                    }
                    n = n3;
                    n3 = ++n4;
                } else {
                    n3 = n4;
                }
                if (n3 >= longArrayWrapper.getLength()) break;
                n4 = n3;
            }
        }
        n = 0;
        n3 = n2;
        n2 = n;
        while (n2 < intArrayWrapper.getLength()) {
            if (l / (long)intArrayWrapper.getInt(n2) <= 0L) {
                ++n2;
                continue;
            }
            arrc = (char[])xResourceBundle.getObject(stringArrayWrapper.getString(n2));
            arrc3 = new char[arrc.getLength() + 1];
            for (n = 0; n < arrc.getLength(); ++n) {
                arrc3[n + 1] = arrc.getChar(n);
            }
            arrc3[0] = arrc.getChar(n - 1);
            n = (int)l / intArrayWrapper.getInt(n2);
            if (n == 0 && (l %= (long)intArrayWrapper.getInt(n2)) == 0L) break;
            if (n < arrc3.length) {
                arrc2[n3] = arrc3[n];
                ++n2;
                ++n3;
                continue;
            }
            return "#error";
        }
        return new String(arrc2, 0, n3);
    }

    private class MyPrefixResolver
    implements PrefixResolver {
        DTM dtm;
        int handle;
        boolean handleNullPrefix;

        public MyPrefixResolver(Node node, DTM dTM, int n, boolean bl) {
            this.dtm = dTM;
            this.handle = n;
            this.handleNullPrefix = bl;
        }

        @Override
        public String getBaseIdentifier() {
            return ElemNumber.this.getBaseIdentifier();
        }

        @Override
        public String getNamespaceForPrefix(String string) {
            return this.dtm.getNamespaceURI(this.handle);
        }

        @Override
        public String getNamespaceForPrefix(String string, Node node) {
            return this.getNamespaceForPrefix(string);
        }

        @Override
        public boolean handlesNullPrefixes() {
            return this.handleNullPrefix;
        }
    }

    class NumberFormatStringTokenizer {
        private int currentPosition;
        private int maxPosition;
        private String str;

        public NumberFormatStringTokenizer(String string) {
            this.str = string;
            this.maxPosition = string.length();
        }

        public int countTokens() {
            int n = 0;
            int n2 = this.currentPosition;
            while (n2 < this.maxPosition) {
                int n3;
                int n4 = n2;
                while ((n3 = n4) < this.maxPosition && Character.isLetterOrDigit(this.str.charAt(n3))) {
                    n4 = n3 + 1;
                }
                n4 = n3;
                if (n2 == n3) {
                    n4 = n3;
                    if (!Character.isLetterOrDigit(this.str.charAt(n3))) {
                        n4 = n3 + 1;
                    }
                }
                ++n;
                n2 = n4;
            }
            return n;
        }

        public boolean hasMoreTokens() {
            boolean bl = this.currentPosition < this.maxPosition;
            return bl;
        }

        public boolean isLetterOrDigitAhead() {
            for (int i = this.currentPosition; i < this.maxPosition; ++i) {
                if (!Character.isLetterOrDigit(this.str.charAt(i))) continue;
                return true;
            }
            return false;
        }

        public boolean nextIsSep() {
            return !Character.isLetterOrDigit(this.str.charAt(this.currentPosition));
        }

        public String nextToken() {
            if (this.currentPosition < this.maxPosition) {
                int n;
                int n2 = this.currentPosition;
                while ((n = this.currentPosition++) < this.maxPosition && Character.isLetterOrDigit(this.str.charAt(n))) {
                }
                if (n2 != (n = this.currentPosition++) || !Character.isLetterOrDigit(this.str.charAt(n))) {
                    // empty if block
                }
                return this.str.substring(n2, this.currentPosition);
            }
            throw new NoSuchElementException();
        }

        public void reset() {
            this.currentPosition = 0;
        }
    }

}

