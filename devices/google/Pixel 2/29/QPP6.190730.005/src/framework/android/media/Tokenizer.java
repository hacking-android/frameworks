/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.WebVttParser;
import android.util.Log;

class Tokenizer {
    private static final String TAG = "Tokenizer";
    private TokenizerPhase mDataTokenizer = new DataTokenizer();
    private int mHandledLen;
    private String mLine;
    private OnTokenListener mListener;
    private TokenizerPhase mPhase;
    private TokenizerPhase mTagTokenizer = new TagTokenizer();

    Tokenizer(OnTokenListener onTokenListener) {
        this.reset();
        this.mListener = onTokenListener;
    }

    static /* synthetic */ int access$108(Tokenizer tokenizer) {
        int n = tokenizer.mHandledLen;
        tokenizer.mHandledLen = n + 1;
        return n;
    }

    static /* synthetic */ int access$112(Tokenizer tokenizer, int n) {
        tokenizer.mHandledLen = n = tokenizer.mHandledLen + n;
        return n;
    }

    void reset() {
        this.mPhase = this.mDataTokenizer.start();
    }

    void tokenize(String string2) {
        this.mHandledLen = 0;
        this.mLine = string2;
        while (this.mHandledLen < this.mLine.length()) {
            this.mPhase.tokenize();
        }
        if (!(this.mPhase instanceof TagTokenizer)) {
            this.mListener.onLineEnd();
        }
    }

    class DataTokenizer
    implements TokenizerPhase {
        private StringBuilder mData;

        DataTokenizer() {
        }

        private boolean replaceEscape(String string2, String string3, int n) {
            if (Tokenizer.this.mLine.startsWith(string2, n)) {
                this.mData.append(Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen, n));
                this.mData.append(string3);
                Tokenizer.this.mHandledLen = string2.length() + n;
                Tokenizer.this.mHandledLen;
                return true;
            }
            return false;
        }

        @Override
        public TokenizerPhase start() {
            this.mData = new StringBuilder();
            return this;
        }

        @Override
        public void tokenize() {
            Object object;
            int n;
            int n2 = Tokenizer.this.mLine.length();
            int n3 = Tokenizer.this.mHandledLen;
            do {
                n = n2;
                if (n3 >= Tokenizer.this.mLine.length()) break;
                if (Tokenizer.this.mLine.charAt(n3) == '&') {
                    if (!(this.replaceEscape("&amp;", "&", n3) || this.replaceEscape("&lt;", "<", n3) || this.replaceEscape("&gt;", ">", n3) || this.replaceEscape("&lrm;", "\u200e", n3) || this.replaceEscape("&rlm;", "\u200f", n3) || !this.replaceEscape("&nbsp;", "\u00a0", n3))) {
                        // empty if block
                    }
                } else if (Tokenizer.this.mLine.charAt(n3) == '<') {
                    object = Tokenizer.this;
                    ((Tokenizer)object).mPhase = ((Tokenizer)object).mTagTokenizer.start();
                    n = n3;
                    break;
                }
                ++n3;
            } while (true);
            this.mData.append(Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen, n));
            Tokenizer.this.mListener.onData(this.mData.toString());
            object = this.mData;
            ((StringBuilder)object).delete(0, ((StringBuilder)object).length());
            Tokenizer.this.mHandledLen = n;
        }
    }

    static interface OnTokenListener {
        public void onData(String var1);

        public void onEnd(String var1);

        public void onLineEnd();

        public void onStart(String var1, String[] var2, String var3);

        public void onTimeStamp(long var1);
    }

    class TagTokenizer
    implements TokenizerPhase {
        private String mAnnotation;
        private boolean mAtAnnotation;
        private String mName;

        TagTokenizer() {
        }

        private void yield_tag() {
            if (this.mName.startsWith("/")) {
                Tokenizer.this.mListener.onEnd(this.mName.substring(1));
            } else if (this.mName.length() > 0 && Character.isDigit(this.mName.charAt(0))) {
                try {
                    long l = WebVttParser.parseTimestampMs(this.mName);
                    Tokenizer.this.mListener.onTimeStamp(l);
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("invalid timestamp tag: <");
                    stringBuilder.append(this.mName);
                    stringBuilder.append(">");
                    Log.d(Tokenizer.TAG, stringBuilder.toString());
                }
            } else {
                String[] arrstring;
                this.mAnnotation = this.mAnnotation.replaceAll("\\s+", " ");
                if (this.mAnnotation.startsWith(" ")) {
                    this.mAnnotation = this.mAnnotation.substring(1);
                }
                if (this.mAnnotation.endsWith(" ")) {
                    arrstring = this.mAnnotation;
                    this.mAnnotation = arrstring.substring(0, arrstring.length() - 1);
                }
                arrstring = null;
                int n = this.mName.indexOf(46);
                if (n >= 0) {
                    arrstring = this.mName.substring(n + 1).split("\\.");
                    this.mName = this.mName.substring(0, n);
                }
                Tokenizer.this.mListener.onStart(this.mName, arrstring, this.mAnnotation);
            }
        }

        @Override
        public TokenizerPhase start() {
            this.mAnnotation = "";
            this.mName = "";
            this.mAtAnnotation = false;
            return this;
        }

        @Override
        public void tokenize() {
            Object object;
            if (!this.mAtAnnotation) {
                Tokenizer.access$108(Tokenizer.this);
            }
            if (Tokenizer.this.mHandledLen < Tokenizer.this.mLine.length()) {
                object = !this.mAtAnnotation && Tokenizer.this.mLine.charAt(Tokenizer.this.mHandledLen) != '/' ? Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen).split("[\t\f >]") : Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen).split(">");
                String string2 = Tokenizer.this.mLine.substring(Tokenizer.this.mHandledLen, Tokenizer.this.mHandledLen + object[0].length());
                Tokenizer.access$112(Tokenizer.this, object[0].length());
                if (this.mAtAnnotation) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(this.mAnnotation);
                    ((StringBuilder)object).append(" ");
                    ((StringBuilder)object).append(string2);
                    this.mAnnotation = ((StringBuilder)object).toString();
                } else {
                    this.mName = string2;
                }
            }
            this.mAtAnnotation = true;
            if (Tokenizer.this.mHandledLen < Tokenizer.this.mLine.length() && Tokenizer.this.mLine.charAt(Tokenizer.this.mHandledLen) == '>') {
                this.yield_tag();
                object = Tokenizer.this;
                ((Tokenizer)object).mPhase = ((Tokenizer)object).mDataTokenizer.start();
                Tokenizer.access$108(Tokenizer.this);
            }
        }
    }

    static interface TokenizerPhase {
        public TokenizerPhase start();

        public void tokenize();
    }

}

