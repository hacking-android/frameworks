/*
 * Decompiled with CFR 0.145.
 */
package org.ccil.cowan.tagsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.Writer;
import org.ccil.cowan.tagsoup.PYXWriter;
import org.ccil.cowan.tagsoup.ScanHandler;
import org.ccil.cowan.tagsoup.Scanner;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

public class HTMLScanner
implements Scanner,
Locator {
    private static final int A_ADUP = 1;
    private static final int A_ADUP_SAVE = 2;
    private static final int A_ADUP_STAGC = 3;
    private static final int A_ANAME = 4;
    private static final int A_ANAME_ADUP = 5;
    private static final int A_ANAME_ADUP_STAGC = 6;
    private static final int A_AVAL = 7;
    private static final int A_AVAL_STAGC = 8;
    private static final int A_CDATA = 9;
    private static final int A_CMNT = 10;
    private static final int A_DECL = 11;
    private static final int A_EMPTYTAG = 12;
    private static final int A_ENTITY = 13;
    private static final int A_ENTITY_START = 14;
    private static final int A_ETAG = 15;
    private static final int A_GI = 16;
    private static final int A_GI_STAGC = 17;
    private static final int A_LT = 18;
    private static final int A_LT_PCDATA = 19;
    private static final int A_MINUS = 20;
    private static final int A_MINUS2 = 21;
    private static final int A_MINUS3 = 22;
    private static final int A_PCDATA = 23;
    private static final int A_PI = 24;
    private static final int A_PITARGET = 25;
    private static final int A_PITARGET_PI = 26;
    private static final int A_SAVE = 27;
    private static final int A_SKIP = 28;
    private static final int A_SP = 29;
    private static final int A_STAGC = 30;
    private static final int A_UNGET = 31;
    private static final int A_UNSAVE_PCDATA = 32;
    private static final int S_ANAME = 1;
    private static final int S_APOS = 2;
    private static final int S_AVAL = 3;
    private static final int S_BB = 4;
    private static final int S_BBC = 5;
    private static final int S_BBCD = 6;
    private static final int S_BBCDA = 7;
    private static final int S_BBCDAT = 8;
    private static final int S_BBCDATA = 9;
    private static final int S_CDATA = 10;
    private static final int S_CDATA2 = 11;
    private static final int S_CDSECT = 12;
    private static final int S_CDSECT1 = 13;
    private static final int S_CDSECT2 = 14;
    private static final int S_COM = 15;
    private static final int S_COM2 = 16;
    private static final int S_COM3 = 17;
    private static final int S_COM4 = 18;
    private static final int S_DECL = 19;
    private static final int S_DECL2 = 20;
    private static final int S_DONE = 21;
    private static final int S_EMPTYTAG = 22;
    private static final int S_ENT = 23;
    private static final int S_EQ = 24;
    private static final int S_ETAG = 25;
    private static final int S_GI = 26;
    private static final int S_NCR = 27;
    private static final int S_PCDATA = 28;
    private static final int S_PI = 29;
    private static final int S_PITARGET = 30;
    private static final int S_QUOT = 31;
    private static final int S_STAGC = 32;
    private static final int S_TAG = 33;
    private static final int S_TAGWS = 34;
    private static final int S_XNCR = 35;
    private static final String[] debug_actionnames;
    private static final String[] debug_statenames;
    private static int[] statetable;
    static short[][] statetableIndex;
    static int statetableIndexMaxChar;
    private int theCurrentColumn;
    private int theCurrentLine;
    private int theLastColumn;
    private int theLastLine;
    int theNextState;
    char[] theOutputBuffer = new char[200];
    private String thePublicid;
    int theSize;
    int theState;
    private String theSystemid;
    int[] theWinMap = new int[]{8364, 65533, 8218, 402, 8222, 8230, 8224, 8225, 710, 8240, 352, 8249, 338, 65533, 381, 65533, 65533, 8216, 8217, 8220, 8221, 8226, 8211, 8212, 732, 8482, 353, 8250, 339, 65533, 382, 376};

    static {
        int[] arrn;
        int n;
        int n2;
        int n3;
        statetable = new int[]{1, 47, 5, 22, 1, 61, 4, 3, 1, 62, 6, 28, 1, 0, 27, 1, 1, -1, 6, 21, 1, 32, 4, 24, 1, 10, 4, 24, 1, 9, 4, 24, 2, 39, 7, 34, 2, 0, 27, 2, 2, -1, 8, 21, 2, 32, 29, 2, 2, 10, 29, 2, 2, 9, 29, 2, 3, 34, 28, 31, 3, 39, 28, 2, 3, 62, 8, 28, 3, 0, 27, 32, 3, -1, 8, 21, 3, 32, 28, 3, 3, 10, 28, 3, 3, 9, 28, 3, 4, 67, 28, 5, 4, 0, 28, 19, 4, -1, 28, 21, 5, 68, 28, 6, 5, 0, 28, 19, 5, -1, 28, 21, 6, 65, 28, 7, 6, 0, 28, 19, 6, -1, 28, 21, 7, 84, 28, 8, 7, 0, 28, 19, 7, -1, 28, 21, 8, 65, 28, 9, 8, 0, 28, 19, 8, -1, 28, 21, 9, 91, 28, 12, 9, 0, 28, 19, 9, -1, 28, 21, 10, 60, 27, 11, 10, 0, 27, 10, 10, -1, 23, 21, 11, 47, 32, 25, 11, 0, 27, 10, 11, -1, 32, 21, 12, 93, 27, 13, 12, 0, 27, 12, 12, -1, 28, 21, 13, 93, 27, 14, 13, 0, 27, 12, 13, -1, 28, 21, 14, 62, 9, 28, 14, 0, 27, 12, 14, -1, 28, 21, 15, 45, 28, 16, 15, 0, 27, 16, 15, -1, 10, 21, 16, 45, 28, 17, 16, 0, 27, 16, 16, -1, 10, 21, 17, 45, 28, 18, 17, 0, 20, 16, 17, -1, 10, 21, 18, 45, 22, 18, 18, 62, 10, 28, 18, 0, 21, 16, 18, -1, 10, 21, 19, 45, 28, 15, 19, 62, 28, 28, 19, 91, 28, 4, 19, 0, 27, 20, 19, -1, 28, 21, 20, 62, 11, 28, 20, 0, 27, 20, 20, -1, 28, 21, 22, 62, 12, 28, 22, 0, 27, 1, 22, 32, 28, 34, 22, 10, 28, 34, 22, 9, 28, 34, 23, 0, 13, 23, 23, -1, 13, 21, 24, 61, 28, 3, 24, 62, 3, 28, 24, 0, 2, 1, 24, -1, 3, 21, 24, 32, 28, 24, 24, 10, 28, 24, 24, 9, 28, 24, 25, 62, 15, 28, 25, 0, 27, 25, 25, -1, 15, 21, 25, 32, 28, 25, 25, 10, 28, 25, 25, 9, 28, 25, 26, 47, 28, 22, 26, 62, 17, 28, 26, 0, 27, 26, 26, -1, 28, 21, 26, 32, 16, 34, 26, 10, 16, 34, 26, 9, 16, 34, 27, 0, 13, 27, 27, -1, 13, 21, 28, 38, 14, 23, 28, 60, 23, 33, 28, 0, 27, 28, 28, -1, 23, 21, 29, 62, 24, 28, 29, 0, 27, 29, 29, -1, 24, 21, 30, 62, 26, 28, 30, 0, 27, 30, 30, -1, 26, 21, 30, 32, 25, 29, 30, 10, 25, 29, 30, 9, 25, 29, 31, 34, 7, 34, 31, 0, 27, 31, 31, -1, 8, 21, 31, 32, 29, 31, 31, 10, 29, 31, 31, 9, 29, 31, 32, 62, 8, 28, 32, 0, 27, 32, 32, -1, 8, 21, 32, 32, 7, 34, 32, 10, 7, 34, 32, 9, 7, 34, 33, 33, 28, 19, 33, 47, 28, 25, 33, 60, 27, 33, 33, 63, 28, 30, 33, 0, 27, 26, 33, -1, 19, 21, 33, 32, 18, 28, 33, 10, 18, 28, 33, 9, 18, 28, 34, 47, 28, 22, 34, 62, 30, 28, 34, 0, 27, 1, 34, -1, 30, 21, 34, 32, 28, 34, 34, 10, 28, 34, 34, 9, 28, 34, 35, 0, 13, 35, 35, -1, 13, 21};
        debug_actionnames = new String[]{"", "A_ADUP", "A_ADUP_SAVE", "A_ADUP_STAGC", "A_ANAME", "A_ANAME_ADUP", "A_ANAME_ADUP_STAGC", "A_AVAL", "A_AVAL_STAGC", "A_CDATA", "A_CMNT", "A_DECL", "A_EMPTYTAG", "A_ENTITY", "A_ENTITY_START", "A_ETAG", "A_GI", "A_GI_STAGC", "A_LT", "A_LT_PCDATA", "A_MINUS", "A_MINUS2", "A_MINUS3", "A_PCDATA", "A_PI", "A_PITARGET", "A_PITARGET_PI", "A_SAVE", "A_SKIP", "A_SP", "A_STAGC", "A_UNGET", "A_UNSAVE_PCDATA"};
        debug_statenames = new String[]{"", "S_ANAME", "S_APOS", "S_AVAL", "S_BB", "S_BBC", "S_BBCD", "S_BBCDA", "S_BBCDAT", "S_BBCDATA", "S_CDATA", "S_CDATA2", "S_CDSECT", "S_CDSECT1", "S_CDSECT2", "S_COM", "S_COM2", "S_COM3", "S_COM4", "S_DECL", "S_DECL2", "S_DONE", "S_EMPTYTAG", "S_ENT", "S_EQ", "S_ETAG", "S_GI", "S_NCR", "S_PCDATA", "S_PI", "S_PITARGET", "S_QUOT", "S_STAGC", "S_TAG", "S_TAGWS", "S_XNCR"};
        int n4 = -1;
        int n5 = -1;
        for (n = 0; n < (arrn = statetable).length; n += 4) {
            n2 = n4;
            if (arrn[n] > n4) {
                n2 = arrn[n];
            }
            arrn = statetable;
            n3 = n5;
            if (arrn[n + 1] > n5) {
                n3 = arrn[n + 1];
            }
            n4 = n2;
            n5 = n3;
        }
        statetableIndexMaxChar = n5 + 1;
        statetableIndex = new short[n4 + 1][n5 + 3];
        for (n3 = 0; n3 <= n4; ++n3) {
            for (int i = -2; i <= n5; ++i) {
                int n6;
                n2 = -1;
                int n7 = 0;
                n = 0;
                do {
                    int n8;
                    arrn = statetable;
                    n6 = n2;
                    if (n >= arrn.length) break;
                    if (n3 != arrn[n]) {
                        n6 = n2;
                        n8 = n7;
                        if (n7 != 0) {
                            n6 = n2;
                            break;
                        }
                    } else if (arrn[n + 1] == 0) {
                        n6 = n;
                        n8 = arrn[n + 2];
                    } else {
                        n6 = n2;
                        n8 = n7;
                        if (arrn[n + 1] == i) {
                            n6 = n;
                            n = arrn[n + 2];
                            break;
                        }
                    }
                    n += 4;
                    n2 = n6;
                    n7 = n8;
                } while (true);
                HTMLScanner.statetableIndex[n3][i + 2] = (short)n6;
            }
        }
    }

    public static void main(String[] object) throws IOException, SAXException {
        HTMLScanner hTMLScanner = new HTMLScanner();
        InputStreamReader inputStreamReader = new InputStreamReader(System.in, "UTF-8");
        object = new OutputStreamWriter((OutputStream)System.out, "UTF-8");
        hTMLScanner.scan(inputStreamReader, new PYXWriter((Writer)object));
        ((Writer)object).close();
    }

    private void mark() {
        this.theLastColumn = this.theCurrentColumn;
        this.theLastLine = this.theCurrentLine;
    }

    private static String nicechar(int n) {
        if (n == 10) {
            return "\\n";
        }
        if (n < 32) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0x");
            stringBuilder.append(Integer.toHexString(n));
            return stringBuilder.toString();
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("'");
        stringBuilder.append((char)n);
        stringBuilder.append("'");
        return stringBuilder.toString();
    }

    private void save(int n, ScanHandler arrc) throws IOException, SAXException {
        int n2;
        int n3 = this.theSize;
        char[] arrc2 = this.theOutputBuffer;
        if (n3 >= arrc2.length - 20) {
            n2 = this.theState;
            if (n2 != 28 && n2 != 10) {
                arrc = new char[arrc2.length * 2];
                System.arraycopy(arrc2, 0, arrc, 0, n3 + 1);
                this.theOutputBuffer = arrc;
            } else {
                arrc.pcdata(this.theOutputBuffer, 0, this.theSize);
                this.theSize = 0;
            }
        }
        arrc = this.theOutputBuffer;
        n2 = this.theSize;
        this.theSize = n2 + 1;
        arrc[n2] = (char)n;
    }

    private void unread(PushbackReader pushbackReader, int n) throws IOException {
        if (n != -1) {
            pushbackReader.unread(n);
        }
    }

    @Override
    public int getColumnNumber() {
        return this.theLastColumn;
    }

    @Override
    public int getLineNumber() {
        return this.theLastLine;
    }

    @Override
    public String getPublicId() {
        return this.thePublicid;
    }

    @Override
    public String getSystemId() {
        return this.theSystemid;
    }

    @Override
    public void resetDocumentLocator(String string, String string2) {
        this.thePublicid = string;
        this.theSystemid = string2;
        this.theCurrentColumn = 0;
        this.theCurrentLine = 0;
        this.theLastColumn = 0;
        this.theLastLine = 0;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public void scan(Reader var1_1, ScanHandler var2_2) throws IOException, SAXException {
        this.theState = 28;
        var3_3 = (var1_1 = var1_1 instanceof BufferedReader != false ? new PushbackReader((Reader)var1_1, 5) : new PushbackReader(new BufferedReader((Reader)var1_1), 5)).read();
        if (var3_3 != 65279) {
            this.unread((PushbackReader)var1_1, var3_3);
        }
        block35 : do {
            if (this.theState == 21) {
                var2_2.eof(this.theOutputBuffer, 0, 0);
                return;
            }
            var4_4 = var3_3 = var1_1.read();
            if (var3_3 >= 128) {
                var4_4 = var3_3;
                if (var3_3 <= 159) {
                    var4_4 = this.theWinMap[var3_3 - 128];
                }
            }
            var3_3 = var4_4;
            if (var4_4 == 13) {
                var3_3 = var4_4 = var1_1.read();
                if (var4_4 != 10) {
                    this.unread((PushbackReader)var1_1, var4_4);
                    var3_3 = 10;
                }
            }
            if (var3_3 == 10) {
                ++this.theCurrentLine;
                this.theCurrentColumn = 0;
            } else {
                ++this.theCurrentColumn;
            }
            if (var3_3 < 32 && var3_3 != 10 && var3_3 != 9 && var3_3 != -1) continue;
            var4_4 = var3_3 >= -1 && var3_3 < HTMLScanner.statetableIndexMaxChar ? var3_3 : -2;
            var5_5 = HTMLScanner.statetableIndex[this.theState][var4_4 + 2];
            var4_4 = 0;
            if (var5_5 != -1) {
                var6_6 = HTMLScanner.statetable;
                var4_4 = var6_6[var5_5 + 2];
                this.theNextState = var6_6[var5_5 + 3];
            }
            switch (var4_4) {
                default: {
                    var1_1 = new StringBuilder();
                    var1_1.append("Can't process state ");
                    var1_1.append(var4_4);
                    throw new Error(var1_1.toString());
                }
                case 32: {
                    var3_3 = this.theSize;
                    if (var3_3 > 0) {
                        this.theSize = var3_3 - 1;
                    }
                    var2_2.pcdata(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 31: {
                    this.unread((PushbackReader)var1_1, var3_3);
                    --this.theCurrentColumn;
                    ** GOTO lbl228
                }
                case 30: {
                    var2_2.stagc(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 29: {
                    this.save(32, var2_2);
                    ** GOTO lbl228
                }
                case 28: {
                    ** GOTO lbl228
                }
                case 27: {
                    this.save(var3_3, var2_2);
                    ** GOTO lbl228
                }
                case 26: {
                    var2_2.pitarget(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    var2_2.pi(this.theOutputBuffer, 0, this.theSize);
                    ** GOTO lbl228
                }
                case 25: {
                    var2_2.pitarget(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 24: {
                    this.mark();
                    var2_2.pi(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 23: {
                    this.mark();
                    var2_2.pcdata(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 22: {
                    this.save(45, var2_2);
                    this.save(32, var2_2);
                    ** GOTO lbl228
                }
                case 21: {
                    this.save(45, var2_2);
                    this.save(32, var2_2);
                }
                case 20: {
                    this.save(45, var2_2);
                    this.save(var3_3, var2_2);
                    ** GOTO lbl228
                }
                case 19: {
                    this.mark();
                    this.save(60, var2_2);
                    var2_2.pcdata(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 18: {
                    this.mark();
                    this.save(60, var2_2);
                    this.save(var3_3, var2_2);
                    ** GOTO lbl228
                }
                case 17: {
                    var2_2.gi(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    var2_2.stagc(this.theOutputBuffer, 0, this.theSize);
                    ** GOTO lbl228
                }
                case 16: {
                    var2_2.gi(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 15: {
                    var2_2.etag(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 14: {
                    var2_2.pcdata(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    this.save(var3_3, var2_2);
                    ** GOTO lbl228
                }
                case 13: {
                    this.mark();
                    var7_7 = (char)var3_3;
                    if (this.theState == 23 && var7_7 == '#') {
                        this.theNextState = 27;
                        this.save(var3_3, var2_2);
                    } else if (this.theState == 27 && (var7_7 == 'x' || var7_7 == 'X')) {
                        this.theNextState = 35;
                        this.save(var3_3, var2_2);
                    } else if (this.theState == 23 && Character.isLetterOrDigit(var7_7)) {
                        this.save(var3_3, var2_2);
                    } else if (this.theState == 27 && Character.isDigit(var7_7)) {
                        this.save(var3_3, var2_2);
                    } else if (this.theState == 35 && (Character.isDigit(var7_7) || "abcdefABCDEF".indexOf(var7_7) != -1)) {
                        this.save(var3_3, var2_2);
                    } else {
                        var2_2.entity(this.theOutputBuffer, 1, this.theSize - 1);
                        var5_5 = var2_2.getEntity();
                        if (var5_5 != 0) {
                            this.theSize = 0;
                            var4_4 = var5_5;
                            if (var5_5 >= 128) {
                                var4_4 = var5_5;
                                if (var5_5 <= 159) {
                                    var4_4 = this.theWinMap[var5_5 - 128];
                                }
                            }
                            if (var4_4 >= 32 && (var4_4 < 55296 || var4_4 > 57343)) {
                                if (var4_4 <= 65535) {
                                    this.save(var4_4, var2_2);
                                } else {
                                    this.save(((var4_4 -= 65536) >> 10) + 55296, var2_2);
                                    this.save((var4_4 & 1023) + 56320, var2_2);
                                }
                            }
                            if (var3_3 != 59) {
                                this.unread((PushbackReader)var1_1, var3_3);
                                --this.theCurrentColumn;
                            }
                        } else {
                            this.unread((PushbackReader)var1_1, var3_3);
                            --this.theCurrentColumn;
                        }
                        this.theNextState = 28;
                    }
                    ** GOTO lbl228
                }
                case 12: {
                    this.mark();
                    var3_3 = this.theSize;
                    if (var3_3 > 0) {
                        var2_2.gi(this.theOutputBuffer, 0, var3_3);
                    }
                    this.theSize = 0;
                    var2_2.stage(this.theOutputBuffer, 0, this.theSize);
                    ** GOTO lbl228
                }
                case 11: {
                    var2_2.decl(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 10: {
                    this.mark();
                    var2_2.cmnt(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 9: {
                    this.mark();
                    var3_3 = this.theSize;
                    if (var3_3 > 1) {
                        this.theSize = var3_3 - 2;
                    }
                    var2_2.pcdata(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 8: {
                    var2_2.aval(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    var2_2.stagc(this.theOutputBuffer, 0, this.theSize);
                    ** GOTO lbl228
                }
                case 7: {
                    var2_2.aval(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 6: {
                    var2_2.aname(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    var2_2.adup(this.theOutputBuffer, 0, this.theSize);
                    var2_2.stagc(this.theOutputBuffer, 0, this.theSize);
                    ** GOTO lbl228
                }
                case 5: {
                    var2_2.aname(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    var2_2.adup(this.theOutputBuffer, 0, this.theSize);
                    ** GOTO lbl228
                }
                case 4: {
                    var2_2.aname(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    ** GOTO lbl228
                }
                case 3: {
                    var2_2.adup(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    var2_2.stagc(this.theOutputBuffer, 0, this.theSize);
                    ** GOTO lbl228
                }
                case 2: {
                    var2_2.adup(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
                    this.save(var3_3, var2_2);
                    ** GOTO lbl228
                }
                case 1: {
                    var2_2.adup(this.theOutputBuffer, 0, this.theSize);
                    this.theSize = 0;
lbl228: // 36 sources:
                    this.theState = this.theNextState;
                    continue block35;
                }
                case 0: 
            }
            break;
        } while (true);
        var1_1 = new StringBuilder();
        var1_1.append("HTMLScanner can't cope with ");
        var1_1.append(Integer.toString(var3_3));
        var1_1.append(" in state ");
        var1_1.append(Integer.toString(this.theState));
        throw new Error(var1_1.toString());
    }

    @Override
    public void startCDATA() {
        this.theNextState = 10;
    }
}

