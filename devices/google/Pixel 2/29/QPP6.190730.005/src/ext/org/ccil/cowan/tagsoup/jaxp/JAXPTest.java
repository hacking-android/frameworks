/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup.jaxp;

import java.io.File;
import java.io.PrintStream;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.w3c.dom.Document;
import org.xml.sax.helpers.DefaultHandler;

public class JAXPTest {
    public static void main(String[] arrstring) throws Exception {
        new JAXPTest().test(arrstring);
    }

    private void test(String[] object) throws Exception {
        PrintStream printStream;
        Object object2;
        if (((String[])object).length != 1) {
            printStream = System.err;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Usage: java ");
            ((StringBuilder)object2).append(this.getClass());
            ((StringBuilder)object2).append(" [input-file]");
            printStream.println(((StringBuilder)object2).toString());
            System.exit(1);
        }
        object = new File(object[0]);
        System.setProperty("javax.xml.parsers.SAXParserFactory", "org.ccil.cowan.tagsoup.jaxp.SAXFactoryImpl");
        object2 = SAXParserFactory.newInstance();
        printStream = System.out;
        Object object3 = new StringBuilder();
        ((StringBuilder)object3).append("Ok, SAX factory JAXP creates is: ");
        ((StringBuilder)object3).append(object2);
        printStream.println(((StringBuilder)object3).toString());
        System.out.println("Let's parse...");
        ((SAXParserFactory)object2).newSAXParser().parse((File)object, new DefaultHandler());
        System.out.println("Done. And then DOM build:");
        object3 = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse((File)object);
        printStream = System.out;
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Succesfully built DOM tree from '");
        ((StringBuilder)object2).append(object);
        ((StringBuilder)object2).append("', -> ");
        ((StringBuilder)object2).append(object3);
        printStream.println(((StringBuilder)object2).toString());
    }
}

