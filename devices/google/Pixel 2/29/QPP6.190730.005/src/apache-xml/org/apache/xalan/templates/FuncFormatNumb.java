/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.templates;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.TransformerException;
import org.apache.xalan.res.XSLMessages;
import org.apache.xalan.templates.ElemTemplateElement;
import org.apache.xalan.templates.StylesheetRoot;
import org.apache.xml.utils.PrefixResolver;
import org.apache.xml.utils.QName;
import org.apache.xml.utils.SAXSourceLocator;
import org.apache.xpath.Expression;
import org.apache.xpath.XPathContext;
import org.apache.xpath.functions.Function3Args;
import org.apache.xpath.functions.WrongNumberArgsException;
import org.apache.xpath.objects.XObject;
import org.apache.xpath.objects.XString;

public class FuncFormatNumb
extends Function3Args {
    static final long serialVersionUID = -8869935264870858636L;

    @Override
    public void checkNumberArgs(int n) throws WrongNumberArgsException {
        if (n > 3 || n < 2) {
            this.reportWrongNumberArgs();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public XObject execute(XPathContext object) throws TransformerException {
        ElemTemplateElement elemTemplateElement = (ElemTemplateElement)((XPathContext)object).getNamespaceContext();
        StylesheetRoot stylesheetRoot = elemTemplateElement.getStylesheetRoot();
        String string = null;
        double d = this.getArg0().execute((XPathContext)object).num();
        String string2 = this.getArg1().execute((XPathContext)object).str();
        if (string2.indexOf(164) > 0) {
            stylesheetRoot.error("ER_CURRENCY_SIGN_ILLEGAL");
        }
        try {
            Expression expression = this.getArg2();
            Object object2 = string;
            if (expression != null) {
                object2 = expression.execute((XPathContext)object).str();
                QName qName = new QName((String)object2, ((XPathContext)object).getNamespaceContext());
                DecimalFormatSymbols decimalFormatSymbols = stylesheetRoot.getDecimalFormatComposed(qName);
                if (decimalFormatSymbols == null) {
                    this.warn((XPathContext)object, "WG_NO_DECIMALFORMAT_DECLARATION", new Object[]{object2});
                    object2 = string;
                } else {
                    object2 = new DecimalFormat();
                    ((DecimalFormat)object2).setDecimalFormatSymbols(decimalFormatSymbols);
                    ((DecimalFormat)object2).applyLocalizedPattern(string2);
                }
            }
            object = object2;
            if (object2 != null) return new XString(((NumberFormat)object).format(d));
            object = new QName("");
            object2 = stylesheetRoot.getDecimalFormatComposed((QName)object);
            if (object2 != null) {
                object = new DecimalFormat();
                ((DecimalFormat)object).setDecimalFormatSymbols((DecimalFormatSymbols)object2);
                ((DecimalFormat)object).applyLocalizedPattern(string2);
                return new XString(((NumberFormat)object).format(d));
            } else {
                object2 = new DecimalFormatSymbols(Locale.US);
                ((DecimalFormatSymbols)object2).setInfinity("Infinity");
                ((DecimalFormatSymbols)object2).setNaN("NaN");
                object = new DecimalFormat();
                ((DecimalFormat)object).setDecimalFormatSymbols((DecimalFormatSymbols)object2);
                ((DecimalFormat)object).applyLocalizedPattern(string2);
            }
            return new XString(((NumberFormat)object).format(d));
        }
        catch (Exception exception) {
            elemTemplateElement.error("ER_MALFORMED_FORMAT_STRING", new Object[]{string2});
            return XString.EMPTYSTRING;
        }
    }

    @Override
    protected void reportWrongNumberArgs() throws WrongNumberArgsException {
        throw new WrongNumberArgsException(XSLMessages.createMessage("ER_TWO_OR_THREE", null));
    }

    @Override
    public void warn(XPathContext xPathContext, String string, Object[] arrobject) throws TransformerException {
        string = XSLMessages.createWarning(string, arrobject);
        xPathContext.getErrorListener().warning(new TransformerException(string, (SAXSourceLocator)xPathContext.getSAXLocator()));
    }
}

