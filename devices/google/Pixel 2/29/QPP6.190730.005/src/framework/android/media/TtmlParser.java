/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 *  org.xmlpull.v1.XmlPullParserFactory
 */
package android.media;

import android.media.TtmlNode;
import android.media.TtmlNodeListener;
import android.media.TtmlUtils;
import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.LinkedList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

class TtmlParser {
    private static final int DEFAULT_FRAMERATE = 30;
    private static final int DEFAULT_SUBFRAMERATE = 1;
    private static final int DEFAULT_TICKRATE = 1;
    static final String TAG = "TtmlParser";
    private long mCurrentRunId;
    private final TtmlNodeListener mListener;
    private XmlPullParser mParser;

    public TtmlParser(TtmlNodeListener ttmlNodeListener) {
        this.mListener = ttmlNodeListener;
    }

    private void extractAttribute(XmlPullParser xmlPullParser, int n, StringBuilder stringBuilder) {
        stringBuilder.append(" ");
        stringBuilder.append(xmlPullParser.getAttributeName(n));
        stringBuilder.append("=\"");
        stringBuilder.append(xmlPullParser.getAttributeValue(n));
        stringBuilder.append("\"");
    }

    private boolean isEndOfDoc() throws XmlPullParserException {
        int n = this.mParser.getEventType();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    private static boolean isSupportedTag(String string2) {
        return string2.equals("tt") || string2.equals("head") || string2.equals("body") || string2.equals("div") || string2.equals("p") || string2.equals("span") || string2.equals("br") || string2.equals("style") || string2.equals("styling") || string2.equals("layout") || string2.equals("region") || string2.equals("metadata") || string2.equals("smpte:image") || string2.equals("smpte:data") || string2.equals("smpte:information");
        {
        }
    }

    private void loadParser(String object) throws XmlPullParserException {
        XmlPullParserFactory xmlPullParserFactory = XmlPullParserFactory.newInstance();
        xmlPullParserFactory.setNamespaceAware(false);
        this.mParser = xmlPullParserFactory.newPullParser();
        object = new StringReader((String)object);
        this.mParser.setInput((Reader)object);
    }

    private TtmlNode parseNode(TtmlNode ttmlNode) throws XmlPullParserException, IOException {
        if (this.mParser.getEventType() != 2) {
            return null;
        }
        StringBuilder stringBuilder = new StringBuilder();
        long l = 0L;
        long l2 = Long.MAX_VALUE;
        long l3 = 0L;
        for (int i = 0; i < this.mParser.getAttributeCount(); ++i) {
            String string2 = this.mParser.getAttributeName(i);
            String string3 = this.mParser.getAttributeValue(i);
            if ((string2 = string2.replaceFirst("^.*:", "")).equals("begin")) {
                l = TtmlUtils.parseTimeExpression(string3, 30, 1, 1);
                continue;
            }
            if (string2.equals("end")) {
                l2 = TtmlUtils.parseTimeExpression(string3, 30, 1, 1);
                continue;
            }
            if (string2.equals("dur")) {
                l3 = TtmlUtils.parseTimeExpression(string3, 30, 1, 1);
                continue;
            }
            this.extractAttribute(this.mParser, i, stringBuilder);
        }
        if (ttmlNode != null) {
            l += ttmlNode.mStartTimeMs;
            if (l2 != Long.MAX_VALUE) {
                l2 += ttmlNode.mStartTimeMs;
            }
        }
        long l4 = l2;
        if (l3 > 0L) {
            if (l2 != Long.MAX_VALUE) {
                Log.e(TAG, "'dur' and 'end' attributes are defined at the same time.'end' value is ignored.");
            }
            l4 = l + l3;
        }
        if (ttmlNode != null && l4 == Long.MAX_VALUE && ttmlNode.mEndTimeMs != Long.MAX_VALUE && l4 > ttmlNode.mEndTimeMs) {
            l4 = ttmlNode.mEndTimeMs;
        }
        return new TtmlNode(this.mParser.getName(), stringBuilder.toString(), null, l, l4, ttmlNode, this.mCurrentRunId);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void parseTtml() throws XmlPullParserException, IOException {
        var1_1 = new LinkedList<Object>();
        var2_2 = 0;
        var3_3 = 1;
        while (this.isEndOfDoc() == false) {
            block14 : {
                block11 : {
                    block15 : {
                        block12 : {
                            block13 : {
                                var4_4 = this.mParser.getEventType();
                                var5_5 = (TtmlNode)var1_1.peekLast();
                                if (var3_3 == 0) break block11;
                                if (var4_4 != 2) break block12;
                                if (TtmlParser.isSupportedTag(this.mParser.getName())) break block13;
                                var5_5 = new StringBuilder();
                                var5_5.append("Unsupported tag ");
                                var5_5.append(this.mParser.getName());
                                var5_5.append(" is ignored.");
                                Log.w("TtmlParser", var5_5.toString());
                                var6_6 = var2_2 + 1;
                                var3_3 = 0;
                                break block14;
                            }
                            var7_7 = this.parseNode((TtmlNode)var5_5);
                            var1_1.addLast(var7_7);
                            if (var5_5 == null) ** GOTO lbl-1000
                            var5_5.mChildren.add((TtmlNode)var7_7);
                            ** GOTO lbl-1000
                        }
                        if (var4_4 != 4) break block15;
                        var7_7 = TtmlUtils.applyDefaultSpacePolicy(this.mParser.getText());
                        if (!TextUtils.isEmpty((CharSequence)var7_7)) {
                            var5_5.mChildren.add(new TtmlNode("#pcdata", "", (String)var7_7, 0L, Long.MAX_VALUE, (TtmlNode)var5_5, this.mCurrentRunId));
                        }
                        ** GOTO lbl-1000
                    }
                    if (var4_4 != 3) ** GOTO lbl-1000
                    if (this.mParser.getName().equals("p")) {
                        this.mListener.onTtmlNodeParsed((TtmlNode)var1_1.getLast());
                    } else if (this.mParser.getName().equals("tt")) {
                        this.mListener.onRootNodeParsed((TtmlNode)var1_1.getLast());
                    }
                    var1_1.removeLast();
                    ** GOTO lbl-1000
                }
                var6_6 = var3_3;
                if (var4_4 == 2) {
                    var3_3 = var6_6;
                    var6_6 = ++var2_2;
                } else if (var4_4 == 3) {
                    if (--var2_2 == 0) {
                        var3_3 = 1;
                        var6_6 = var2_2;
                    } else {
                        var3_3 = var6_6;
                        var6_6 = var2_2;
                    }
                } else lbl-1000: // 6 sources:
                {
                    var6_6 = var2_2;
                }
            }
            this.mParser.next();
            var2_2 = var6_6;
        }
    }

    public void parse(String string2, long l) throws XmlPullParserException, IOException {
        this.mParser = null;
        this.mCurrentRunId = l;
        this.loadParser(string2);
        this.parseTtml();
    }
}

