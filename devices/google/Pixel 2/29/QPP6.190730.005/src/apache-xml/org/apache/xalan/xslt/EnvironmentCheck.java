/*
 * Decompiled with CFR 0.145.
 */
package org.apache.xalan.xslt;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;
import org.apache.xalan.xslt.ObjectFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.Attributes;

public class EnvironmentCheck {
    public static final String CLASS_NOTPRESENT = "not-present";
    public static final String CLASS_PRESENT = "present-unknown-version";
    public static final String ERROR = "ERROR.";
    public static final String ERROR_FOUND = "At least one error was found!";
    public static final String FOUNDCLASSES = "foundclasses.";
    public static final String VERSION = "version.";
    public static final String WARNING = "WARNING.";
    private static Hashtable jarVersions = new Hashtable();
    public String[] jarNames = new String[]{"xalan.jar", "xalansamples.jar", "xalanj1compat.jar", "xalanservlet.jar", "serializer.jar", "xerces.jar", "xercesImpl.jar", "testxsl.jar", "crimson.jar", "lotusxsl.jar", "jaxp.jar", "parser.jar", "dom.jar", "sax.jar", "xml.jar", "xml-apis.jar", "xsltc.jar"};
    protected PrintWriter outWriter = new PrintWriter(System.out, true);

    static {
        jarVersions.put(new Long(857192L), "xalan.jar from xalan-j_1_1");
        jarVersions.put(new Long(440237L), "xalan.jar from xalan-j_1_2");
        jarVersions.put(new Long(436094L), "xalan.jar from xalan-j_1_2_1");
        jarVersions.put(new Long(426249L), "xalan.jar from xalan-j_1_2_2");
        jarVersions.put(new Long(702536L), "xalan.jar from xalan-j_2_0_0");
        jarVersions.put(new Long(720930L), "xalan.jar from xalan-j_2_0_1");
        jarVersions.put(new Long(732330L), "xalan.jar from xalan-j_2_1_0");
        jarVersions.put(new Long(872241L), "xalan.jar from xalan-j_2_2_D10");
        jarVersions.put(new Long(882739L), "xalan.jar from xalan-j_2_2_D11");
        jarVersions.put(new Long(923866L), "xalan.jar from xalan-j_2_2_0");
        jarVersions.put(new Long(905872L), "xalan.jar from xalan-j_2_3_D1");
        jarVersions.put(new Long(906122L), "xalan.jar from xalan-j_2_3_0");
        jarVersions.put(new Long(906248L), "xalan.jar from xalan-j_2_3_1");
        jarVersions.put(new Long(983377L), "xalan.jar from xalan-j_2_4_D1");
        jarVersions.put(new Long(997276L), "xalan.jar from xalan-j_2_4_0");
        jarVersions.put(new Long(1031036L), "xalan.jar from xalan-j_2_4_1");
        jarVersions.put(new Long(596540L), "xsltc.jar from xalan-j_2_2_0");
        jarVersions.put(new Long(590247L), "xsltc.jar from xalan-j_2_3_D1");
        jarVersions.put(new Long(589914L), "xsltc.jar from xalan-j_2_3_0");
        jarVersions.put(new Long(589915L), "xsltc.jar from xalan-j_2_3_1");
        jarVersions.put(new Long(1306667L), "xsltc.jar from xalan-j_2_4_D1");
        jarVersions.put(new Long(1328227L), "xsltc.jar from xalan-j_2_4_0");
        jarVersions.put(new Long(1344009L), "xsltc.jar from xalan-j_2_4_1");
        jarVersions.put(new Long(1348361L), "xsltc.jar from xalan-j_2_5_D1");
        jarVersions.put(new Long(1268634L), "xsltc.jar-bundled from xalan-j_2_3_0");
        jarVersions.put(new Long(100196L), "xml-apis.jar from xalan-j_2_2_0 or xalan-j_2_3_D1");
        jarVersions.put(new Long(108484L), "xml-apis.jar from xalan-j_2_3_0, or xalan-j_2_3_1 from xml-commons-1.0.b2");
        jarVersions.put(new Long(109049L), "xml-apis.jar from xalan-j_2_4_0 from xml-commons RIVERCOURT1 branch");
        jarVersions.put(new Long(113749L), "xml-apis.jar from xalan-j_2_4_1 from factoryfinder-build of xml-commons RIVERCOURT1");
        jarVersions.put(new Long(124704L), "xml-apis.jar from tck-jaxp-1_2_0 branch of xml-commons");
        jarVersions.put(new Long(124724L), "xml-apis.jar from tck-jaxp-1_2_0 branch of xml-commons, tag: xml-commons-external_1_2_01");
        jarVersions.put(new Long(194205L), "xml-apis.jar from head branch of xml-commons, tag: xml-commons-external_1_3_02");
        jarVersions.put(new Long(424490L), "xalan.jar from Xerces Tools releases - ERROR:DO NOT USE!");
        jarVersions.put(new Long(1591855L), "xerces.jar from xalan-j_1_1 from xerces-1...");
        jarVersions.put(new Long(1498679L), "xerces.jar from xalan-j_1_2 from xerces-1_2_0.bin");
        jarVersions.put(new Long(1484896L), "xerces.jar from xalan-j_1_2_1 from xerces-1_2_1.bin");
        jarVersions.put(new Long(804460L), "xerces.jar from xalan-j_1_2_2 from xerces-1_2_2.bin");
        jarVersions.put(new Long(1499244L), "xerces.jar from xalan-j_2_0_0 from xerces-1_2_3.bin");
        jarVersions.put(new Long(1605266L), "xerces.jar from xalan-j_2_0_1 from xerces-1_3_0.bin");
        jarVersions.put(new Long(904030L), "xerces.jar from xalan-j_2_1_0 from xerces-1_4.bin");
        jarVersions.put(new Long(904030L), "xerces.jar from xerces-1_4_0.bin");
        jarVersions.put(new Long(1802885L), "xerces.jar from xerces-1_4_2.bin");
        jarVersions.put(new Long(1734594L), "xerces.jar from Xerces-J-bin.2.0.0.beta3");
        jarVersions.put(new Long(1808883L), "xerces.jar from xalan-j_2_2_D10,D11,D12 or xerces-1_4_3.bin");
        jarVersions.put(new Long(1812019L), "xerces.jar from xalan-j_2_2_0");
        jarVersions.put(new Long(1720292L), "xercesImpl.jar from xalan-j_2_3_D1");
        jarVersions.put(new Long(1730053L), "xercesImpl.jar from xalan-j_2_3_0 or xalan-j_2_3_1 from xerces-2_0_0");
        jarVersions.put(new Long(1728861L), "xercesImpl.jar from xalan-j_2_4_D1 from xerces-2_0_1");
        jarVersions.put(new Long(972027L), "xercesImpl.jar from xalan-j_2_4_0 from xerces-2_1");
        jarVersions.put(new Long(831587L), "xercesImpl.jar from xalan-j_2_4_1 from xerces-2_2");
        jarVersions.put(new Long(891817L), "xercesImpl.jar from xalan-j_2_5_D1 from xerces-2_3");
        jarVersions.put(new Long(895924L), "xercesImpl.jar from xerces-2_4");
        jarVersions.put(new Long(1010806L), "xercesImpl.jar from Xerces-J-bin.2.6.2");
        jarVersions.put(new Long(1203860L), "xercesImpl.jar from Xerces-J-bin.2.7.1");
        jarVersions.put(new Long(37485L), "xalanj1compat.jar from xalan-j_2_0_0");
        jarVersions.put(new Long(38100L), "xalanj1compat.jar from xalan-j_2_0_1");
        jarVersions.put(new Long(18779L), "xalanservlet.jar from xalan-j_2_0_0");
        jarVersions.put(new Long(21453L), "xalanservlet.jar from xalan-j_2_0_1");
        jarVersions.put(new Long(24826L), "xalanservlet.jar from xalan-j_2_3_1 or xalan-j_2_4_1");
        jarVersions.put(new Long(24831L), "xalanservlet.jar from xalan-j_2_4_1");
        jarVersions.put(new Long(5618L), "jaxp.jar from jaxp1.0.1");
        jarVersions.put(new Long(136133L), "parser.jar from jaxp1.0.1");
        jarVersions.put(new Long(28404L), "jaxp.jar from jaxp-1.1");
        jarVersions.put(new Long(187162L), "crimson.jar from jaxp-1.1");
        jarVersions.put(new Long(801714L), "xalan.jar from jaxp-1.1");
        jarVersions.put(new Long(196399L), "crimson.jar from crimson-1.1.1");
        jarVersions.put(new Long(33323L), "jaxp.jar from crimson-1.1.1 or jakarta-ant-1.4.1b1");
        jarVersions.put(new Long(152717L), "crimson.jar from crimson-1.1.2beta2");
        jarVersions.put(new Long(88143L), "xml-apis.jar from crimson-1.1.2beta2");
        jarVersions.put(new Long(206384L), "crimson.jar from crimson-1.1.3 or jakarta-ant-1.4.1b1");
        jarVersions.put(new Long(136198L), "parser.jar from jakarta-ant-1.3 or 1.2");
        jarVersions.put(new Long(5537L), "jaxp.jar from jakarta-ant-1.3 or 1.2");
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void main(String[] arrstring) {
        PrintWriter printWriter = new PrintWriter(System.out, true);
        int n = 0;
        do {
            void var1_2;
            if (n >= arrstring.length) {
                new EnvironmentCheck().checkEnvironment((PrintWriter)var1_2);
                return;
            }
            Appendable appendable = var1_2;
            int n2 = n;
            if ("-out".equalsIgnoreCase(arrstring[n])) {
                n2 = n + 1;
                if (n2 < arrstring.length) {
                    void var1_4;
                    try {
                        FileWriter fileWriter = new FileWriter(arrstring[n2], true);
                        Appendable appendable2 = appendable = new PrintWriter(fileWriter);
                    }
                    catch (Exception exception) {
                        PrintStream printStream = System.err;
                        appendable = new StringBuilder();
                        ((StringBuilder)appendable).append("# WARNING: -out ");
                        ((StringBuilder)appendable).append(arrstring[n2]);
                        ((StringBuilder)appendable).append(" threw ");
                        ((StringBuilder)appendable).append(exception.toString());
                        printStream.println(((StringBuilder)appendable).toString());
                    }
                    appendable = var1_4;
                } else {
                    System.err.println("# WARNING: -out argument should have a filename, output sent to console");
                    appendable = var1_2;
                }
            }
            n = n2 + 1;
            void var1_5 = appendable;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void appendEnvironmentReport(Node var1_1, Document var2_7, Hashtable var3_8) {
        block16 : {
            if (var1_1 == null || var2_7 == null) return;
            var4_9 = var2_7.createElement("EnvironmentCheck");
            var4_9.setAttribute("version", "$Revision: 468646 $");
            var1_1.appendChild(var4_9);
            var5_10 = "ERROR";
            if (var3_8 != null) ** GOTO lbl17
            var1_1 = var2_7.createElement("status");
            var1_1.setAttribute("result", "ERROR");
            var1_1.appendChild(var2_7.createTextNode("appendEnvironmentReport called with null Hashtable!"));
            var4_9.appendChild((Node)var1_1);
            return;
lbl17: // 1 sources:
            var6_11 = var2_7.createElement("environment");
            var4_9.appendChild(var6_11);
            var7_12 = var3_8.keys();
            var8_13 = false;
            do {
                block15 : {
                    block14 : {
                        var1_1 = var3_8;
                        if (!var7_12.hasMoreElements()) break;
                        var9_14 = var7_12.nextElement();
                        var10_15 = (String)var9_14;
                        if (!var10_15.startsWith("foundclasses.")) ** GOTO lbl34
                        var1_1 = (Vector)var1_1.get(var10_15);
                        var8_13 |= this.appendFoundJars(var6_11, (Document)var2_7, (Vector)var1_1, (String)var10_15);
                        continue;
lbl34: // 1 sources:
                        if (!var10_15.startsWith("ERROR.")) break block14;
                        var8_13 = true;
                    }
                    try {
                        var11_16 = var2_7.createElement("item");
                        var11_16.setAttribute("key", (String)var10_15);
                        var11_16.appendChild(var2_7.createTextNode((String)var1_1.get(var10_15)));
                        var6_11.appendChild(var11_16);
                        continue;
                    }
                    catch (Exception var1_2) {}
                    break block15;
                    catch (Exception var1_3) {
                        // empty catch block
                    }
                }
                var11_16 = var2_7.createElement("item");
                var11_16.setAttribute("key", (String)var10_15);
                var10_15 = new StringBuilder();
                var10_15.append("ERROR. Reading ");
                var10_15.append(var9_14);
                var10_15.append(" threw: ");
                var10_15.append(var1_1.toString());
                var11_16.appendChild(var2_7.createTextNode(var10_15.toString()));
                var6_11.appendChild(var11_16);
                var8_13 = true;
                continue;
                break;
            } while (true);
            try {
                var2_7 = var2_7.createElement("status");
                var1_1 = var8_13 != false ? var5_10 : "OK";
            }
            catch (Exception var1_4) {}
            var2_7.setAttribute("result", (String)var1_1);
            var4_9.appendChild((Node)var2_7);
            return;
            break block16;
            catch (Exception var1_5) {
                // empty catch block
            }
        }
        var3_8 = System.err;
        var2_7 = new StringBuilder();
        var2_7.append("appendEnvironmentReport threw: ");
        var2_7.append(var1_6.toString());
        var3_8.println(var2_7.toString());
        var1_6.printStackTrace();
    }

    protected boolean appendFoundJars(Node node, Document document, Vector vector, String object) {
        if (vector != null && vector.size() >= 1) {
            boolean bl = false;
            for (int i = 0; i < vector.size(); ++i) {
                Hashtable hashtable = (Hashtable)vector.elementAt(i);
                object = hashtable.keys();
                while (object.hasMoreElements()) {
                    Object object2;
                    Object e;
                    block6 : {
                        e = object.nextElement();
                        object2 = (String)e;
                        if (!((String)object2).startsWith(ERROR)) break block6;
                        bl = true;
                    }
                    try {
                        Element element = document.createElement("foundJar");
                        element.setAttribute("name", ((String)object2).substring(0, ((String)object2).indexOf("-")));
                        element.setAttribute("desc", ((String)object2).substring(((String)object2).indexOf("-") + 1));
                        element.appendChild(document.createTextNode((String)hashtable.get(object2)));
                        node.appendChild(element);
                    }
                    catch (Exception exception) {
                        bl = true;
                        object2 = document.createElement("foundJar");
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("ERROR. Reading ");
                        stringBuilder.append(e);
                        stringBuilder.append(" threw: ");
                        stringBuilder.append(exception.toString());
                        object2.appendChild(document.createTextNode(stringBuilder.toString()));
                        node.appendChild((Node)object2);
                    }
                }
            }
            return bl;
        }
        return false;
    }

    protected void checkAntVersion(Hashtable hashtable) {
        Hashtable<String, String> hashtable2 = hashtable;
        if (hashtable == null) {
            hashtable2 = new Hashtable<String, String>();
        }
        try {
            hashtable2.put("version.ant", (String)ObjectFactory.findProviderClass("org.apache.tools.ant.Main", ObjectFactory.findClassLoader(), true).getMethod("getAntVersion", new Class[0]).invoke(null, new Object[0]));
        }
        catch (Exception exception) {
            hashtable2.put("version.ant", CLASS_NOTPRESENT);
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void checkDOMVersion(Hashtable hashtable) {
        void var2_7;
        Object object;
        block12 : {
            if (hashtable == null) {
                hashtable = new Hashtable();
            }
            Class[] arrclass = new Class[]{String.class, String.class};
            ObjectFactory.findProviderClass("org.w3c.dom.Document", ObjectFactory.findClassLoader(), true).getMethod("createElementNS", arrclass);
            hashtable.put("version.DOM", "2.0");
            try {
                ObjectFactory.findProviderClass("org.w3c.dom.Node", ObjectFactory.findClassLoader(), true).getMethod("supported", arrclass);
                hashtable.put("ERROR.version.DOM.draftlevel", "2.0wd");
                hashtable.put(ERROR, ERROR_FOUND);
                return;
            }
            catch (Exception exception) {
                block11 : {
                    object = ObjectFactory.findClassLoader();
                    try {
                        ObjectFactory.findProviderClass("org.w3c.dom.Node", (ClassLoader)object, true).getMethod("isSupported", arrclass);
                        hashtable.put("version.DOM.draftlevel", "2.0fd");
                        return;
                    }
                    catch (Exception exception2) {}
                    break block11;
                    catch (Exception exception3) {
                        // empty catch block
                    }
                }
                try {
                    hashtable.put("ERROR.version.DOM.draftlevel", "2.0unknown");
                    hashtable.put(ERROR, ERROR_FOUND);
                    return;
                }
                catch (Exception exception4) {}
            }
            break block12;
            catch (Exception exception) {
                // empty catch block
            }
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("ERROR attempting to load DOM level 2 class: ");
        ((StringBuilder)object).append(var2_7.toString());
        hashtable.put("ERROR.version.DOM", ((StringBuilder)object).toString());
        hashtable.put(ERROR, ERROR_FOUND);
    }

    public boolean checkEnvironment(PrintWriter printWriter) {
        if (printWriter != null) {
            this.outWriter = printWriter;
        }
        if (this.writeEnvironmentReport(this.getEnvironmentHash())) {
            this.logMsg("# WARNING: Potential problems found in your environment!");
            this.logMsg("#    Check any 'ERROR' items above against the Xalan FAQs");
            this.logMsg("#    to correct potential problems with your classes/jars");
            this.logMsg("#    http://xml.apache.org/xalan-j/faq.html");
            printWriter = this.outWriter;
            if (printWriter != null) {
                printWriter.flush();
            }
            return false;
        }
        this.logMsg("# YAHOO! Your environment seems to be OK.");
        printWriter = this.outWriter;
        if (printWriter != null) {
            printWriter.flush();
        }
        return true;
    }

    protected void checkJAXPVersion(Hashtable serializable) {
        block6 : {
            Hashtable<String, String> hashtable = serializable;
            if (serializable == null) {
                hashtable = new Hashtable<String, String>();
            }
            serializable = null;
            Class class_ = ObjectFactory.findProviderClass("javax.xml.parsers.DocumentBuilder", ObjectFactory.findClassLoader(), true);
            serializable = class_;
            class_.getMethod("getDOMImplementation", new Class[0]);
            serializable = class_;
            try {
                hashtable.put("version.JAXP", "1.1 or higher");
            }
            catch (Exception exception) {
                if (serializable != null) {
                    hashtable.put("ERROR.version.JAXP", "1.0.1");
                    hashtable.put(ERROR, ERROR_FOUND);
                    break block6;
                }
                hashtable.put("ERROR.version.JAXP", CLASS_NOTPRESENT);
                hashtable.put(ERROR, ERROR_FOUND);
            }
        }
    }

    protected void checkParserVersion(Hashtable hashtable) {
        Hashtable<String, String> hashtable2 = hashtable;
        if (hashtable == null) {
            hashtable2 = new Hashtable<String, String>();
        }
        try {
            hashtable2.put("version.xerces1", (String)ObjectFactory.findProviderClass("org.apache.xerces.framework.Version", ObjectFactory.findClassLoader(), true).getField("fVersion").get(null));
        }
        catch (Exception exception) {
            hashtable2.put("version.xerces1", CLASS_NOTPRESENT);
        }
        try {
            hashtable2.put("version.xerces2", (String)ObjectFactory.findProviderClass("org.apache.xerces.impl.Version", ObjectFactory.findClassLoader(), true).getField("fVersion").get(null));
        }
        catch (Exception exception) {
            hashtable2.put("version.xerces2", CLASS_NOTPRESENT);
        }
        try {
            ObjectFactory.findProviderClass("org.apache.crimson.parser.Parser2", ObjectFactory.findClassLoader(), true);
            hashtable2.put("version.crimson", CLASS_PRESENT);
        }
        catch (Exception exception) {
            hashtable2.put("version.crimson", CLASS_NOTPRESENT);
        }
    }

    protected Vector checkPathForJars(String string, String[] arrstring) {
        if (string != null && arrstring != null && string.length() != 0 && arrstring.length != 0) {
            Vector<Serializable> vector = new Vector<Serializable>();
            StringTokenizer stringTokenizer = new StringTokenizer(string, File.pathSeparator);
            while (stringTokenizer.hasMoreTokens()) {
                string = stringTokenizer.nextToken();
                for (int i = 0; i < arrstring.length; ++i) {
                    CharSequence charSequence;
                    Serializable serializable;
                    if (string.indexOf(arrstring[i]) <= -1) continue;
                    Serializable serializable2 = new File(string);
                    if (((File)serializable2).exists()) {
                        try {
                            serializable = new Hashtable(2);
                            charSequence = new StringBuilder();
                            ((StringBuilder)charSequence).append(arrstring[i]);
                            ((StringBuilder)charSequence).append("-path");
                            ((Hashtable)serializable).put(((StringBuilder)charSequence).toString(), ((File)serializable2).getAbsolutePath());
                            if (!"xalan.jar".equalsIgnoreCase(arrstring[i])) {
                                charSequence = new StringBuilder();
                                ((StringBuilder)charSequence).append(arrstring[i]);
                                ((StringBuilder)charSequence).append("-apparent.version");
                                ((Hashtable)serializable).put(((StringBuilder)charSequence).toString(), this.getApparentVersion(arrstring[i], ((File)serializable2).length()));
                            }
                            vector.addElement(serializable);
                        }
                        catch (Exception exception) {}
                        continue;
                    }
                    serializable2 = new Hashtable(2);
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append(arrstring[i]);
                    ((StringBuilder)serializable).append("-path");
                    charSequence = ((StringBuilder)serializable).toString();
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append("WARNING. Classpath entry: ");
                    ((StringBuilder)serializable).append(string);
                    ((StringBuilder)serializable).append(" does not exist");
                    ((Hashtable)serializable2).put(charSequence, ((StringBuilder)serializable).toString());
                    serializable = new StringBuilder();
                    ((StringBuilder)serializable).append(arrstring[i]);
                    ((StringBuilder)serializable).append("-apparent.version");
                    ((Hashtable)serializable2).put(((StringBuilder)serializable).toString(), CLASS_NOTPRESENT);
                    vector.addElement(serializable2);
                }
            }
            return vector;
        }
        return null;
    }

    protected void checkProcessorVersion(Hashtable hashtable) {
        Serializable serializable;
        Serializable serializable2;
        if (hashtable == null) {
            hashtable = new Hashtable<String, String>();
        }
        try {
            serializable2 = ObjectFactory.findProviderClass("org.apache.xalan.xslt.XSLProcessorVersion", ObjectFactory.findClassLoader(), true);
            serializable = new StringBuffer();
            ((StringBuffer)serializable).append(((Class)serializable2).getField("PRODUCT").get(null));
            ((StringBuffer)serializable).append(';');
            ((StringBuffer)serializable).append(((Class)serializable2).getField("LANGUAGE").get(null));
            ((StringBuffer)serializable).append(';');
            ((StringBuffer)serializable).append(((Class)serializable2).getField("S_VERSION").get(null));
            ((StringBuffer)serializable).append(';');
            hashtable.put("version.xalan1", ((StringBuffer)serializable).toString());
        }
        catch (Exception exception) {
            hashtable.put("version.xalan1", CLASS_NOTPRESENT);
        }
        try {
            serializable = ObjectFactory.findProviderClass("org.apache.xalan.processor.XSLProcessorVersion", ObjectFactory.findClassLoader(), true);
            serializable2 = new StringBuffer();
            ((StringBuffer)serializable2).append(((Class)serializable).getField("S_VERSION").get(null));
            hashtable.put("version.xalan2x", ((StringBuffer)serializable2).toString());
        }
        catch (Exception exception) {
            hashtable.put("version.xalan2x", CLASS_NOTPRESENT);
        }
        try {
            hashtable.put("version.xalan2_2", (String)ObjectFactory.findProviderClass("org.apache.xalan.Version", ObjectFactory.findClassLoader(), true).getMethod("getVersion", new Class[0]).invoke(null, new Object[0]));
        }
        catch (Exception exception) {
            hashtable.put("version.xalan2_2", CLASS_NOTPRESENT);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void checkSAXVersion(Hashtable hashtable) {
        if (hashtable == null) {
            hashtable = new Hashtable();
        }
        Object object = new Class[]{String.class};
        try {
            ObjectFactory.findProviderClass("org.xml.sax.helpers.AttributesImpl", ObjectFactory.findClassLoader(), true).getMethod("setAttributes", Attributes.class);
            hashtable.put("version.SAX", "2.0");
            return;
        }
        catch (Exception exception) {
            block9 : {
                Object object2 = new StringBuilder();
                ((StringBuilder)object2).append("ERROR attempting to load SAX version 2 class: ");
                ((StringBuilder)object2).append(exception.toString());
                hashtable.put("ERROR.version.SAX", ((StringBuilder)object2).toString());
                hashtable.put(ERROR, ERROR_FOUND);
                object2 = ObjectFactory.findClassLoader();
                try {
                    ObjectFactory.findProviderClass("org.xml.sax.XMLReader", (ClassLoader)object2, true).getMethod("parse", (Class<?>)object);
                    hashtable.put("version.SAX-backlevel", "2.0beta2-or-earlier");
                    return;
                }
                catch (Exception exception2) {}
                break block9;
                catch (Exception exception3) {
                    // empty catch block
                }
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ERROR attempting to load SAX version 2 class: ");
            stringBuilder.append(exception.toString());
            hashtable.put("ERROR.version.SAX", stringBuilder.toString());
            hashtable.put(ERROR, ERROR_FOUND);
            try {
                ObjectFactory.findProviderClass("org.xml.sax.Parser", ObjectFactory.findClassLoader(), true).getMethod("parse", (Class<?>)object);
                hashtable.put("version.SAX-backlevel", "1.0");
                return;
            }
            catch (Exception exception4) {
                object = new StringBuilder();
                ((StringBuilder)object).append("ERROR attempting to load SAX version 1 class: ");
                ((StringBuilder)object).append(exception4.toString());
                hashtable.put("ERROR.version.SAX-backlevel", ((StringBuilder)object).toString());
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void checkSystemProperties(Hashtable object) {
        Hashtable<String, String> hashtable = object;
        if (object == null) {
            hashtable = new Hashtable<String, String>();
        }
        try {
            hashtable.put("java.version", System.getProperty("java.version"));
        }
        catch (SecurityException securityException) {
            hashtable.put("java.version", "WARNING: SecurityException thrown accessing system version properties");
        }
        try {
            object = System.getProperty("java.class.path");
            hashtable.put("java.class.path", (String)object);
            object = this.checkPathForJars((String)object, this.jarNames);
            if (object != null) {
                hashtable.put("foundclasses.java.class.path", (String)object);
            }
            if ((object = System.getProperty("sun.boot.class.path")) != null) {
                hashtable.put("sun.boot.class.path", (String)object);
                object = this.checkPathForJars((String)object, this.jarNames);
                if (object != null) {
                    hashtable.put("foundclasses.sun.boot.class.path", (String)object);
                }
            }
            if ((object = System.getProperty("java.ext.dirs")) == null) return;
            hashtable.put("java.ext.dirs", (String)object);
            object = this.checkPathForJars((String)object, this.jarNames);
            if (object == null) return;
            hashtable.put("foundclasses.java.ext.dirs", (String)object);
            return;
        }
        catch (SecurityException securityException) {
            hashtable.put("java.class.path", "WARNING: SecurityException thrown accessing system classpath properties");
        }
    }

    protected String getApparentVersion(String string, long l) {
        CharSequence charSequence = (String)jarVersions.get(new Long(l));
        if (charSequence != null && ((String)charSequence).startsWith(string)) {
            return charSequence;
        }
        if (!"xerces.jar".equalsIgnoreCase(string) && !"xercesImpl.jar".equalsIgnoreCase(string)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(string);
            ((StringBuilder)charSequence).append(" ");
            ((StringBuilder)charSequence).append(CLASS_PRESENT);
            return ((StringBuilder)charSequence).toString();
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append(string);
        ((StringBuilder)charSequence).append(" ");
        ((StringBuilder)charSequence).append(WARNING);
        ((StringBuilder)charSequence).append(CLASS_PRESENT);
        return ((StringBuilder)charSequence).toString();
    }

    public Hashtable getEnvironmentHash() {
        Hashtable hashtable = new Hashtable();
        this.checkJAXPVersion(hashtable);
        this.checkProcessorVersion(hashtable);
        this.checkParserVersion(hashtable);
        this.checkAntVersion(hashtable);
        this.checkDOMVersion(hashtable);
        this.checkSAXVersion(hashtable);
        this.checkSystemProperties(hashtable);
        return hashtable;
    }

    protected boolean logFoundJars(Vector serializable, String string) {
        if (serializable != null && ((Vector)serializable).size() >= 1) {
            boolean bl = false;
            Serializable serializable2 = new StringBuilder();
            ((StringBuilder)serializable2).append("#---- BEGIN Listing XML-related jars in: ");
            ((StringBuilder)serializable2).append(string);
            ((StringBuilder)serializable2).append(" ----");
            this.logMsg(((StringBuilder)serializable2).toString());
            for (int i = 0; i < ((Vector)serializable).size(); ++i) {
                serializable2 = (Hashtable)((Vector)serializable).elementAt(i);
                Enumeration enumeration = ((Hashtable)serializable2).keys();
                while (enumeration.hasMoreElements()) {
                    Object k;
                    String string2;
                    StringBuilder stringBuilder;
                    block6 : {
                        k = enumeration.nextElement();
                        string2 = (String)k;
                        if (!string2.startsWith(ERROR)) break block6;
                        bl = true;
                    }
                    try {
                        stringBuilder = new StringBuilder();
                        stringBuilder.append(string2);
                        stringBuilder.append("=");
                        stringBuilder.append(((Hashtable)serializable2).get(string2));
                        this.logMsg(stringBuilder.toString());
                    }
                    catch (Exception exception) {
                        bl = true;
                        stringBuilder = new StringBuilder();
                        stringBuilder.append("Reading-");
                        stringBuilder.append(k);
                        stringBuilder.append("= threw: ");
                        stringBuilder.append(exception.toString());
                        this.logMsg(stringBuilder.toString());
                    }
                }
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("#----- END Listing XML-related jars in: ");
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append(" -----");
            this.logMsg(((StringBuilder)serializable).toString());
            return bl;
        }
        return false;
    }

    protected void logMsg(String string) {
        this.outWriter.println(string);
    }

    protected boolean writeEnvironmentReport(Hashtable hashtable) {
        if (hashtable == null) {
            this.logMsg("# ERROR: writeEnvironmentReport called with null Hashtable");
            return false;
        }
        boolean bl = false;
        this.logMsg("#---- BEGIN writeEnvironmentReport($Revision: 468646 $): Useful stuff found: ----");
        Enumeration enumeration = hashtable.keys();
        while (enumeration.hasMoreElements()) {
            boolean bl2;
            Object k;
            boolean bl3;
            String string;
            block13 : {
                k = enumeration.nextElement();
                string = (String)k;
                bl2 = bl;
                if (!string.startsWith(FOUNDCLASSES)) break block13;
                bl2 = bl;
                bl3 = bl | this.logFoundJars((Vector)hashtable.get(string), string);
            }
            bl3 = bl;
            bl2 = bl;
            if (string.startsWith(ERROR)) {
                bl3 = true;
            }
            bl2 = bl3;
            bl2 = bl3;
            StringBuilder stringBuilder = new StringBuilder();
            bl2 = bl3;
            stringBuilder.append(string);
            bl2 = bl3;
            stringBuilder.append("=");
            bl2 = bl3;
            stringBuilder.append(hashtable.get(string));
            bl2 = bl3;
            try {
                this.logMsg(stringBuilder.toString());
            }
            catch (Exception exception) {
                stringBuilder = new StringBuilder();
                stringBuilder.append("Reading-");
                stringBuilder.append(k);
                stringBuilder.append("= threw: ");
                stringBuilder.append(exception.toString());
                this.logMsg(stringBuilder.toString());
                bl3 = bl2;
            }
            bl = bl3;
        }
        this.logMsg("#----- END writeEnvironmentReport: Useful properties found: -----");
        return bl;
    }
}

