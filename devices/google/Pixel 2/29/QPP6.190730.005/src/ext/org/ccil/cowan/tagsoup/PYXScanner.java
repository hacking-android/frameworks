/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Reader;
import java.io.Writer;
import org.ccil.cowan.tagsoup.PYXWriter;
import org.ccil.cowan.tagsoup.ScanHandler;
import org.ccil.cowan.tagsoup.Scanner;
import org.xml.sax.SAXException;

public class PYXScanner
implements Scanner {
    public static void main(String[] arrstring) throws IOException, SAXException {
        new PYXScanner().scan(new InputStreamReader(System.in, "UTF-8"), new PYXWriter(new BufferedWriter(new OutputStreamWriter((OutputStream)System.out, "UTF-8"))));
    }

    @Override
    public void resetDocumentLocator(String string, String string2) {
    }

    @Override
    public void scan(Reader arrc, ScanHandler scanHandler) throws IOException, SAXException {
        String string;
        BufferedReader bufferedReader = new BufferedReader((Reader)arrc);
        arrc = null;
        int n = 0;
        while ((string = bufferedReader.readLine()) != null) {
            int n2;
            char[] arrc2;
            block24 : {
                block23 : {
                    n2 = string.length();
                    if (arrc == null) break block23;
                    arrc2 = arrc;
                    if (arrc.length >= n2) break block24;
                }
                arrc2 = new char[n2];
            }
            string.getChars(0, n2, arrc2, 0);
            int n3 = arrc2[0];
            if (n3 != 40) {
                if (n3 != 41) {
                    if (n3 != 45) {
                        if (n3 != 63) {
                            if (n3 != 65) {
                                if (n3 == 69) {
                                    n3 = n;
                                    if (n != 0) {
                                        scanHandler.stagc(arrc2, 0, 0);
                                        n3 = 0;
                                    }
                                    scanHandler.entity(arrc2, 1, n2 - 1);
                                    n = n3;
                                }
                            } else {
                                n3 = string.indexOf(32);
                                scanHandler.aname(arrc2, 1, n3 - 1);
                                scanHandler.aval(arrc2, n3 + 1, n2 - n3 - 1);
                            }
                        } else {
                            n3 = n;
                            if (n != 0) {
                                scanHandler.stagc(arrc2, 0, 0);
                                n3 = 0;
                            }
                            scanHandler.pi(arrc2, 1, n2 - 1);
                            n = n3;
                        }
                    } else {
                        n3 = n;
                        if (n != 0) {
                            scanHandler.stagc(arrc2, 0, 0);
                            n3 = 0;
                        }
                        if (string.equals("-\\n")) {
                            arrc2[0] = (char)10;
                            scanHandler.pcdata(arrc2, 0, 1);
                            n = n3;
                        } else {
                            scanHandler.pcdata(arrc2, 1, n2 - 1);
                            n = n3;
                        }
                    }
                } else {
                    n3 = n;
                    if (n != 0) {
                        scanHandler.stagc(arrc2, 0, 0);
                        n3 = 0;
                    }
                    scanHandler.etag(arrc2, 1, n2 - 1);
                    n = n3;
                }
            } else {
                if (n != 0) {
                    scanHandler.stagc(arrc2, 0, 0);
                }
                scanHandler.gi(arrc2, 1, n2 - 1);
                n = 1;
            }
            arrc = arrc2;
        }
        scanHandler.eof(arrc, 0, 0);
    }

    @Override
    public void startCDATA() {
    }
}

