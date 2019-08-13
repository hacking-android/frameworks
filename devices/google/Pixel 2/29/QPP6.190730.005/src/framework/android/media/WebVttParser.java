/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.TextTrackCue;
import android.media.TextTrackRegion;
import android.media.WebVttCueListener;
import android.util.Log;
import java.util.Vector;

class WebVttParser {
    private static final String TAG = "WebVttParser";
    private String mBuffer = "";
    private TextTrackCue mCue;
    private Vector<String> mCueTexts;
    private WebVttCueListener mListener;
    private final Phase mParseCueId = new Phase(){
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override
        public void parse(String string2) {
            WebVttParser webVttParser;
            if (string2.length() == 0) {
                return;
            }
            if (string2.equals("NOTE") || string2.startsWith("NOTE ")) {
                webVttParser = WebVttParser.this;
                webVttParser.mPhase = webVttParser.mParseCueText;
            }
            WebVttParser.this.mCue = new TextTrackCue();
            WebVttParser.this.mCueTexts.clear();
            webVttParser = WebVttParser.this;
            webVttParser.mPhase = webVttParser.mParseCueTime;
            if (string2.contains("-->")) {
                WebVttParser.this.mPhase.parse(string2);
            } else {
                WebVttParser.access$900((WebVttParser)WebVttParser.this).mId = string2;
            }
        }
    };
    private final Phase mParseCueText = new Phase(){

        @Override
        public void parse(String object) {
            if (((String)object).length() == 0) {
                WebVttParser.this.yieldCue();
                object = WebVttParser.this;
                ((WebVttParser)object).mPhase = ((WebVttParser)object).mParseCueId;
                return;
            }
            if (WebVttParser.this.mCue != null) {
                WebVttParser.this.mCueTexts.add(object);
            }
        }
    };
    private final Phase mParseCueTime = new Phase(){
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override
        public void parse(String object) {
            int n = ((String)object).indexOf("-->");
            if (n < 0) {
                WebVttParser.this.mCue = null;
                object = WebVttParser.this;
                ((WebVttParser)object).mPhase = ((WebVttParser)object).mParseCueId;
                return;
            }
            int n2 = 0;
            String string2 = ((String)object).substring(0, n).trim();
            Object object2 = ((String)object).substring(n + 3).replaceFirst("^\\s+", "").replaceFirst("\\s+", " ");
            int n3 = object2.indexOf(32);
            object = n3 > 0 ? object2.substring(0, n3) : object2;
            object2 = n3 > 0 ? object2.substring(n3 + 1) : "";
            WebVttParser.access$900((WebVttParser)WebVttParser.this).mStartTimeMs = WebVttParser.parseTimestampMs(string2);
            WebVttParser.access$900((WebVttParser)WebVttParser.this).mEndTimeMs = WebVttParser.parseTimestampMs((String)object);
            object2 = object2.split(" +");
            int n4 = ((String[])object2).length;
            object = string2;
            for (n3 = 0; n3 < n4; ++n3) {
                String string3 = object2[n3];
                int n5 = string3.indexOf(58);
                if (n5 <= 0 || n5 == string3.length() - 1) continue;
                string2 = string3.substring(n2, n5);
                string3 = string3.substring(n5 + 1);
                if (string2.equals("region")) {
                    WebVttParser.access$900((WebVttParser)WebVttParser.this).mRegionId = string3;
                    n2 = 0;
                    continue;
                }
                if (string2.equals("vertical")) {
                    if (string3.equals("rl")) {
                        WebVttParser.access$900((WebVttParser)WebVttParser.this).mWritingDirection = 101;
                        n2 = 0;
                        continue;
                    }
                    if (string3.equals("lr")) {
                        WebVttParser.access$900((WebVttParser)WebVttParser.this).mWritingDirection = 102;
                        n2 = 0;
                        continue;
                    }
                    WebVttParser.this.log_warning("cue setting", string2, "has invalid value", string3);
                    n2 = 0;
                    continue;
                }
                if (string2.equals("line")) {
                    try {
                        if (string3.endsWith("%")) {
                            WebVttParser.access$900((WebVttParser)WebVttParser.this).mSnapToLines = false;
                            WebVttParser.access$900((WebVttParser)WebVttParser.this).mLinePosition = WebVttParser.parseIntPercentage(string3);
                        } else if (string3.matches(".*[^0-9].*")) {
                            WebVttParser.this.log_warning("cue setting", string2, "contains an invalid character", string3);
                        } else {
                            WebVttParser.access$900((WebVttParser)WebVttParser.this).mSnapToLines = true;
                            WebVttParser.access$900((WebVttParser)WebVttParser.this).mLinePosition = Integer.parseInt(string3);
                        }
                    }
                    catch (NumberFormatException numberFormatException) {
                        WebVttParser.this.log_warning("cue setting", string2, "is not numeric or percentage", string3);
                    }
                    n2 = 0;
                    continue;
                }
                n5 = 0;
                if (string2.equals("position")) {
                    try {
                        WebVttParser.access$900((WebVttParser)WebVttParser.this).mTextPosition = WebVttParser.parseIntPercentage(string3);
                    }
                    catch (NumberFormatException numberFormatException) {
                        WebVttParser.this.log_warning("cue setting", string2, "is not numeric or percentage", string3);
                    }
                    n2 = n5;
                    continue;
                }
                if (string2.equals("size")) {
                    try {
                        WebVttParser.access$900((WebVttParser)WebVttParser.this).mSize = WebVttParser.parseIntPercentage(string3);
                    }
                    catch (NumberFormatException numberFormatException) {
                        WebVttParser.this.log_warning("cue setting", string2, "is not numeric or percentage", string3);
                    }
                    n2 = n5;
                    continue;
                }
                n2 = n5;
                if (!string2.equals("align")) continue;
                if (string3.equals("start")) {
                    WebVttParser.access$900((WebVttParser)WebVttParser.this).mAlignment = 201;
                    n2 = n5;
                    continue;
                }
                if (string3.equals("middle")) {
                    WebVttParser.access$900((WebVttParser)WebVttParser.this).mAlignment = 200;
                    n2 = n5;
                    continue;
                }
                if (string3.equals("end")) {
                    WebVttParser.access$900((WebVttParser)WebVttParser.this).mAlignment = 202;
                    n2 = n5;
                    continue;
                }
                if (string3.equals("left")) {
                    WebVttParser.access$900((WebVttParser)WebVttParser.this).mAlignment = 203;
                    n2 = n5;
                    continue;
                }
                if (string3.equals("right")) {
                    WebVttParser.access$900((WebVttParser)WebVttParser.this).mAlignment = 204;
                    n2 = n5;
                    continue;
                }
                WebVttParser.this.log_warning("cue setting", string2, "has invalid value", string3);
                n2 = n5;
            }
            if (WebVttParser.access$900((WebVttParser)WebVttParser.this).mLinePosition != null || WebVttParser.access$900((WebVttParser)WebVttParser.this).mSize != 100 || WebVttParser.access$900((WebVttParser)WebVttParser.this).mWritingDirection != 100) {
                WebVttParser.access$900((WebVttParser)WebVttParser.this).mRegionId = "";
            }
            object = WebVttParser.this;
            ((WebVttParser)object).mPhase = ((WebVttParser)object).mParseCueText;
        }
    };
    private final Phase mParseHeader = new Phase(){
        static final /* synthetic */ boolean $assertionsDisabled = false;

        @Override
        public void parse(String object) {
            if (((String)object).length() == 0) {
                object = WebVttParser.this;
                ((WebVttParser)object).mPhase = ((WebVttParser)object).mParseCueId;
            } else if (((String)object).contains("-->")) {
                WebVttParser webVttParser = WebVttParser.this;
                webVttParser.mPhase = webVttParser.mParseCueTime;
                WebVttParser.this.mPhase.parse((String)object);
            } else {
                int n = ((String)object).indexOf(58);
                if (n <= 0 || n >= ((String)object).length() - 1) {
                    WebVttParser.this.log_warning("meta data header has invalid format", (String)object);
                }
                String string2 = ((String)object).substring(0, n);
                object = ((String)object).substring(n + 1);
                if (string2.equals("Region")) {
                    object = this.parseRegion((String)object);
                    WebVttParser.this.mListener.onRegionParsed((TextTrackRegion)object);
                }
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        TextTrackRegion parseRegion(String arrstring) {
            TextTrackRegion textTrackRegion = new TextTrackRegion();
            arrstring = arrstring.split(" +");
            int n = arrstring.length;
            int n2 = 0;
            int i = 0;
            while (i < n) {
                block28 : {
                    String string2 = arrstring[i];
                    int n3 = string2.indexOf(61);
                    if (n3 > 0 && n3 != string2.length() - 1) {
                        Object object;
                        String string3 = string2.substring(n2, n3);
                        string2 = string2.substring(n3 + 1);
                        if (string3.equals("id")) {
                            textTrackRegion.mId = string2;
                        } else if (string3.equals("width")) {
                            try {
                                textTrackRegion.mWidth = WebVttParser.parseFloatPercentage(string2);
                            }
                            catch (NumberFormatException numberFormatException) {
                                WebVttParser.this.log_warning("region setting", string3, "has invalid value", numberFormatException.getMessage(), string2);
                                n2 = 0;
                            }
                        } else if (string3.equals("lines")) {
                            if (string2.matches(".*[^0-9].*")) {
                                WebVttParser.this.log_warning("lines", string3, "contains an invalid character", string2);
                                n2 = 0;
                            } else {
                                try {
                                    textTrackRegion.mLines = Integer.parseInt(string2);
                                }
                                catch (NumberFormatException numberFormatException) {
                                    WebVttParser.this.log_warning("region setting", string3, "is not numeric", string2);
                                }
                                n2 = 0;
                            }
                        } else if (!string3.equals("regionanchor") && !string3.equals("viewportanchor")) {
                            if (string3.equals("scroll")) {
                                if (string2.equals("up")) {
                                    textTrackRegion.mScrollValue = 301;
                                    n2 = 0;
                                } else {
                                    WebVttParser.this.log_warning("region setting", string3, "has invalid value", string2);
                                    n2 = 0;
                                }
                            } else {
                                n2 = 0;
                            }
                        } else {
                            n2 = string2.indexOf(",");
                            if (n2 < 0) {
                                WebVttParser.this.log_warning("region setting", string3, "contains no comma", string2);
                                n2 = 0;
                            } else {
                                object = string2.substring(0, n2);
                                string2 = string2.substring(n2 + 1);
                                float f2 = WebVttParser.parseFloatPercentage((String)object);
                                float f = WebVttParser.parseFloatPercentage(string2);
                                if (string3.charAt(0) == 'r') {
                                    textTrackRegion.mAnchorPointX = f2;
                                    textTrackRegion.mAnchorPointY = f;
                                } else {
                                    textTrackRegion.mViewportAnchorPointX = f2;
                                    textTrackRegion.mViewportAnchorPointY = f;
                                }
                                n2 = 0;
                            }
                        }
                        break block28;
                        catch (NumberFormatException numberFormatException) {
                            object = WebVttParser.this;
                            String string4 = numberFormatException.getMessage();
                            n2 = 0;
                            ((WebVttParser)object).log_warning("region setting", string3, "has invalid y component", string4, string2);
                            break block28;
                        }
                        catch (NumberFormatException numberFormatException) {
                            n2 = 0;
                            WebVttParser.this.log_warning("region setting", string3, "has invalid x component", numberFormatException.getMessage(), (String)object);
                        }
                    }
                }
                ++i;
            }
            return textTrackRegion;
        }
    };
    private final Phase mParseStart = new Phase(){

        @Override
        public void parse(String object) {
            String string2 = object;
            if (((String)object).startsWith("\ufeff")) {
                string2 = ((String)object).substring(1);
            }
            if (!(string2.equals("WEBVTT") || string2.startsWith("WEBVTT ") || string2.startsWith("WEBVTT\t"))) {
                WebVttParser.this.log_warning("Not a WEBVTT header", string2);
                object = WebVttParser.this;
                ((WebVttParser)object).mPhase = ((WebVttParser)object).mSkipRest;
            } else {
                object = WebVttParser.this;
                ((WebVttParser)object).mPhase = ((WebVttParser)object).mParseHeader;
            }
        }
    };
    private Phase mPhase = this.mParseStart;
    private final Phase mSkipRest = new Phase(){

        @Override
        public void parse(String string2) {
        }
    };

    WebVttParser(WebVttCueListener webVttCueListener) {
        this.mListener = webVttCueListener;
        this.mCueTexts = new Vector();
    }

    private void log_warning(String string2, String string3) {
        String string4 = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" ('");
        stringBuilder.append(string3);
        stringBuilder.append("')");
        Log.w(string4, stringBuilder.toString());
    }

    private void log_warning(String string2, String string3, String string4, String string5) {
        String string6 = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" '");
        stringBuilder.append(string3);
        stringBuilder.append("' ");
        stringBuilder.append(string4);
        stringBuilder.append(" ('");
        stringBuilder.append(string5);
        stringBuilder.append("')");
        Log.w(string6, stringBuilder.toString());
    }

    private void log_warning(String string2, String string3, String string4, String string5, String string6) {
        String string7 = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(" '");
        stringBuilder.append(string3);
        stringBuilder.append("' ");
        stringBuilder.append(string4);
        stringBuilder.append(" ('");
        stringBuilder.append(string6);
        stringBuilder.append("' ");
        stringBuilder.append(string5);
        stringBuilder.append(")");
        Log.w(string7, stringBuilder.toString());
    }

    public static float parseFloatPercentage(String object) throws NumberFormatException {
        if (((String)object).endsWith("%")) {
            if (!((String)(object = ((String)object).substring(0, ((String)object).length() - 1))).matches(".*[^0-9.].*")) {
                block5 : {
                    try {
                        float f = Float.parseFloat((String)object);
                        if (f < 0.0f || f > 100.0f) break block5;
                        return f;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw new NumberFormatException("is not a number");
                    }
                }
                object = new NumberFormatException("is out of range");
                throw object;
            }
            throw new NumberFormatException("contains an invalid character");
        }
        throw new NumberFormatException("does not end in %");
    }

    public static int parseIntPercentage(String object) throws NumberFormatException {
        if (((String)object).endsWith("%")) {
            if (!((String)(object = ((String)object).substring(0, ((String)object).length() - 1))).matches(".*[^0-9].*")) {
                block5 : {
                    try {
                        int n = Integer.parseInt((String)object);
                        if (n < 0 || n > 100) break block5;
                        return n;
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw new NumberFormatException("is not a number");
                    }
                }
                object = new NumberFormatException("is out of range");
                throw object;
            }
            throw new NumberFormatException("contains an invalid character");
        }
        throw new NumberFormatException("does not end in %");
    }

    public static long parseTimestampMs(String arrstring) throws NumberFormatException {
        if (arrstring.matches("(\\d+:)?[0-5]\\d:[0-5]\\d\\.\\d{3}")) {
            String[] arrstring2 = arrstring.split("\\.", 2);
            long l = 0L;
            arrstring = arrstring2[0].split(":");
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                l = 60L * l + Long.parseLong(arrstring[i]);
            }
            return 1000L * l + Long.parseLong(arrstring2[1]);
        }
        throw new NumberFormatException("has invalid format");
    }

    public static String timeToString(long l) {
        return String.format("%d:%02d:%02d.%03d", l / 3600000L, l / 60000L % 60L, l / 1000L % 60L, l % 1000L);
    }

    public void eos() {
        if (this.mBuffer.endsWith("\r")) {
            String string2 = this.mBuffer;
            this.mBuffer = string2.substring(0, string2.length() - 1);
        }
        this.mPhase.parse(this.mBuffer);
        this.mBuffer = "";
        this.yieldCue();
        this.mPhase = this.mParseStart;
    }

    public void parse(String object) {
        boolean bl = false;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.mBuffer);
        stringBuilder.append(((String)object).replace("\u0000", "\ufffd"));
        this.mBuffer = stringBuilder.toString().replace("\r\n", "\n");
        if (this.mBuffer.endsWith("\r")) {
            bl = true;
            object = this.mBuffer;
            this.mBuffer = ((String)object).substring(0, ((String)object).length() - 1);
        }
        object = this.mBuffer.split("[\r\n]");
        for (int i = 0; i < ((String[])object).length - 1; ++i) {
            this.mPhase.parse(object[i]);
        }
        this.mBuffer = object[((String[])object).length - 1];
        if (bl) {
            object = new StringBuilder();
            ((StringBuilder)object).append(this.mBuffer);
            ((StringBuilder)object).append("\r");
            this.mBuffer = ((StringBuilder)object).toString();
        }
    }

    public void yieldCue() {
        if (this.mCue != null && this.mCueTexts.size() > 0) {
            this.mCue.mStrings = new String[this.mCueTexts.size()];
            this.mCueTexts.toArray(this.mCue.mStrings);
            this.mCueTexts.clear();
            this.mListener.onCueParsed(this.mCue);
        }
        this.mCue = null;
    }

    static interface Phase {
        public void parse(String var1);
    }

}

