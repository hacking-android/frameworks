/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.util.Enumeration;
import java.util.Hashtable;
import org.ccil.cowan.tagsoup.HTMLSchema;
import org.ccil.cowan.tagsoup.PYXScanner;
import org.ccil.cowan.tagsoup.PYXWriter;
import org.ccil.cowan.tagsoup.Parser;
import org.ccil.cowan.tagsoup.XMLWriter;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class CommandLine {
    static Hashtable options = new Hashtable();
    private static String theOutputEncoding;
    private static Parser theParser;
    private static HTMLSchema theSchema;

    static {
        options.put("--nocdata", Boolean.FALSE);
        options.put("--files", Boolean.FALSE);
        options.put("--reuse", Boolean.FALSE);
        options.put("--nons", Boolean.FALSE);
        options.put("--nobogons", Boolean.FALSE);
        options.put("--any", Boolean.FALSE);
        options.put("--emptybogons", Boolean.FALSE);
        options.put("--norootbogons", Boolean.FALSE);
        options.put("--pyxin", Boolean.FALSE);
        options.put("--lexical", Boolean.FALSE);
        options.put("--pyx", Boolean.FALSE);
        options.put("--html", Boolean.FALSE);
        options.put("--method=", Boolean.FALSE);
        options.put("--doctype-public=", Boolean.FALSE);
        options.put("--doctype-system=", Boolean.FALSE);
        options.put("--output-encoding=", Boolean.FALSE);
        options.put("--omit-xml-declaration", Boolean.FALSE);
        options.put("--encoding=", Boolean.FALSE);
        options.put("--help", Boolean.FALSE);
        options.put("--version", Boolean.FALSE);
        options.put("--nodefaults", Boolean.FALSE);
        options.put("--nocolons", Boolean.FALSE);
        options.put("--norestart", Boolean.FALSE);
        options.put("--ignorable", Boolean.FALSE);
        theParser = null;
        theSchema = null;
        theOutputEncoding = null;
    }

    private static ContentHandler chooseContentHandler(Writer object) {
        String string;
        if (CommandLine.hasOption(options, "--pyx")) {
            return new PYXWriter((Writer)object);
        }
        object = new XMLWriter((Writer)object);
        if (CommandLine.hasOption(options, "--html")) {
            ((XMLWriter)object).setOutputProperty("method", "html");
            ((XMLWriter)object).setOutputProperty("omit-xml-declaration", "yes");
        }
        if (CommandLine.hasOption(options, "--method=") && (string = (String)options.get("--method=")) != null) {
            ((XMLWriter)object).setOutputProperty("method", string);
        }
        if (CommandLine.hasOption(options, "--doctype-public=") && (string = (String)options.get("--doctype-public=")) != null) {
            ((XMLWriter)object).setOutputProperty("doctype-public", string);
        }
        if (CommandLine.hasOption(options, "--doctype-system=") && (string = (String)options.get("--doctype-system=")) != null) {
            ((XMLWriter)object).setOutputProperty("doctype-system", string);
        }
        if (CommandLine.hasOption(options, "--output-encoding=") && (string = (theOutputEncoding = (String)options.get("--output-encoding="))) != null) {
            ((XMLWriter)object).setOutputProperty("encoding", string);
        }
        if (CommandLine.hasOption(options, "--omit-xml-declaration")) {
            ((XMLWriter)object).setOutputProperty("omit-xml-declaration", "yes");
        }
        ((XMLWriter)object).setPrefix(theSchema.getURI(), "");
        return object;
    }

    private static void doHelp() {
        System.err.print("usage: java -jar tagsoup-*.jar ");
        System.err.print(" [ ");
        boolean bl = true;
        Enumeration enumeration = options.keys();
        while (enumeration.hasMoreElements()) {
            if (!bl) {
                System.err.print("| ");
            }
            bl = false;
            String string = (String)enumeration.nextElement();
            System.err.print(string);
            if (string.endsWith("=")) {
                System.err.print("?");
            }
            System.err.print(" ");
        }
        System.err.println("]*");
    }

    private static int getopts(Hashtable hashtable, String[] arrstring) {
        int n;
        for (n = 0; n < arrstring.length; ++n) {
            String string = arrstring[n];
            String string2 = null;
            if (string.charAt(0) != '-') break;
            int n2 = string.indexOf(61);
            String string3 = string;
            if (n2 != -1) {
                string2 = string.substring(n2 + 1, string.length());
                string3 = string.substring(0, n2 + 1);
            }
            if (hashtable.containsKey(string3)) {
                if (string2 == null) {
                    hashtable.put(string3, Boolean.TRUE);
                    continue;
                }
                hashtable.put(string3, string2);
                continue;
            }
            System.err.print("Unknown option ");
            System.err.println(string3);
            System.exit(1);
        }
        return n;
    }

    private static boolean hasOption(Hashtable hashtable, String string) {
        if (Boolean.getBoolean(string)) {
            return true;
        }
        return hashtable.get(string) != Boolean.FALSE;
    }

    public static void main(String[] arrstring) throws IOException, SAXException {
        int n;
        if (CommandLine.hasOption(options, "--help")) {
            CommandLine.doHelp();
            return;
        }
        if (CommandLine.hasOption(options, "--version")) {
            System.err.println("TagSoup version 1.2");
            return;
        }
        if (arrstring.length == n) {
            CommandLine.process("", System.out);
        } else if (CommandLine.hasOption(options, "--files")) {
            for (n = CommandLine.getopts((Hashtable)CommandLine.options, (String[])arrstring); n < arrstring.length; ++n) {
                CharSequence charSequence;
                String string = arrstring[n];
                int n2 = string.lastIndexOf(46);
                if (n2 == -1) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append(".xhtml");
                    charSequence = ((StringBuilder)charSequence).toString();
                } else if (string.endsWith(".xhtml")) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string);
                    ((StringBuilder)charSequence).append("_");
                    charSequence = ((StringBuilder)charSequence).toString();
                } else {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string.substring(0, n2));
                    ((StringBuilder)charSequence).append(".xhtml");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src: ");
                stringBuilder.append(string);
                stringBuilder.append(" dst: ");
                stringBuilder.append((String)charSequence);
                printStream.println(stringBuilder.toString());
                CommandLine.process(string, new FileOutputStream((String)charSequence));
            }
        } else {
            while (n < arrstring.length) {
                PrintStream printStream = System.err;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("src: ");
                stringBuilder.append(arrstring[n]);
                printStream.println(stringBuilder.toString());
                CommandLine.process(arrstring[n], System.out);
                ++n;
            }
        }
    }

    private static void process(String string, OutputStream object) throws IOException, SAXException {
        String string2;
        Parser parser;
        if (CommandLine.hasOption(options, "--reuse")) {
            if (theParser == null) {
                theParser = new Parser();
            }
            parser = theParser;
        } else {
            parser = new Parser();
        }
        theSchema = new HTMLSchema();
        parser.setProperty("http://www.ccil.org/~cowan/tagsoup/properties/schema", theSchema);
        if (CommandLine.hasOption(options, "--nocdata")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/cdata-elements", false);
        }
        if (CommandLine.hasOption(options, "--nons") || CommandLine.hasOption(options, "--html")) {
            parser.setFeature("http://xml.org/sax/features/namespaces", false);
        }
        if (CommandLine.hasOption(options, "--nobogons")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/ignore-bogons", true);
        }
        if (CommandLine.hasOption(options, "--any")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/bogons-empty", false);
        } else if (CommandLine.hasOption(options, "--emptybogons")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/bogons-empty", true);
        }
        if (CommandLine.hasOption(options, "--norootbogons")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/root-bogons", false);
        }
        if (CommandLine.hasOption(options, "--nodefaults")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/default-attributes", false);
        }
        if (CommandLine.hasOption(options, "--nocolons")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/translate-colons", true);
        }
        if (CommandLine.hasOption(options, "--norestart")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/restart-elements", false);
        }
        if (CommandLine.hasOption(options, "--ignorable")) {
            parser.setFeature("http://www.ccil.org/~cowan/tagsoup/features/ignorable-whitespace", true);
        }
        if (CommandLine.hasOption(options, "--pyxin")) {
            parser.setProperty("http://www.ccil.org/~cowan/tagsoup/properties/scanner", new PYXScanner());
        }
        object = (string2 = theOutputEncoding) == null ? new OutputStreamWriter((OutputStream)object) : new OutputStreamWriter((OutputStream)object, string2);
        object = CommandLine.chooseContentHandler((Writer)object);
        parser.setContentHandler((ContentHandler)object);
        if (CommandLine.hasOption(options, "--lexical") && object instanceof LexicalHandler) {
            parser.setProperty("http://xml.org/sax/properties/lexical-handler", object);
        }
        object = new InputSource();
        if (string != "") {
            ((InputSource)object).setSystemId(string);
        } else {
            ((InputSource)object).setByteStream(System.in);
        }
        if (CommandLine.hasOption(options, "--encoding=") && (string = (String)options.get("--encoding=")) != null) {
            ((InputSource)object).setEncoding(string);
        }
        parser.parse((InputSource)object);
    }
}

