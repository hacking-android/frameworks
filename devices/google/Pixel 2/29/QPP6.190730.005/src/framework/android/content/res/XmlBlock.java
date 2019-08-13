/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.annotation.optimization.FastNative
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.content.res;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.content.res.StringBlock;
import android.content.res.XmlResourceParser;
import android.util.TypedValue;
import com.android.internal.util.XmlUtils;
import dalvik.annotation.optimization.FastNative;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

final class XmlBlock
implements AutoCloseable {
    private static final boolean DEBUG = false;
    private final AssetManager mAssets;
    private final long mNative;
    private boolean mOpen = true;
    private int mOpenCount = 1;
    final StringBlock mStrings;

    XmlBlock(AssetManager assetManager, long l) {
        this.mAssets = assetManager;
        this.mNative = l;
        this.mStrings = new StringBlock(XmlBlock.nativeGetStringBlock(l), false);
    }

    @UnsupportedAppUsage
    public XmlBlock(byte[] arrby) {
        this.mAssets = null;
        this.mNative = XmlBlock.nativeCreate(arrby, 0, arrby.length);
        this.mStrings = new StringBlock(XmlBlock.nativeGetStringBlock(this.mNative), false);
    }

    public XmlBlock(byte[] arrby, int n, int n2) {
        this.mAssets = null;
        this.mNative = XmlBlock.nativeCreate(arrby, n, n2);
        this.mStrings = new StringBlock(XmlBlock.nativeGetStringBlock(this.mNative), false);
    }

    static /* synthetic */ int access$008(XmlBlock xmlBlock) {
        int n = xmlBlock.mOpenCount;
        xmlBlock.mOpenCount = n + 1;
        return n;
    }

    private void decOpenCountLocked() {
        --this.mOpenCount;
        if (this.mOpenCount == 0) {
            XmlBlock.nativeDestroy(this.mNative);
            AssetManager assetManager = this.mAssets;
            if (assetManager != null) {
                assetManager.xmlBlockGone(this.hashCode());
            }
        }
    }

    private static final native long nativeCreate(byte[] var0, int var1, int var2);

    private static final native long nativeCreateParseState(long var0, int var2);

    private static final native void nativeDestroy(long var0);

    private static final native void nativeDestroyParseState(long var0);

    @FastNative
    private static final native int nativeGetAttributeCount(long var0);

    @FastNative
    private static final native int nativeGetAttributeData(long var0, int var2);

    @FastNative
    private static final native int nativeGetAttributeDataType(long var0, int var2);

    @FastNative
    private static final native int nativeGetAttributeIndex(long var0, String var2, String var3);

    @FastNative
    private static final native int nativeGetAttributeName(long var0, int var2);

    @FastNative
    private static final native int nativeGetAttributeNamespace(long var0, int var2);

    @FastNative
    private static final native int nativeGetAttributeResource(long var0, int var2);

    @FastNative
    private static final native int nativeGetAttributeStringValue(long var0, int var2);

    @FastNative
    private static final native int nativeGetClassAttribute(long var0);

    @FastNative
    private static final native int nativeGetIdAttribute(long var0);

    @FastNative
    private static final native int nativeGetLineNumber(long var0);

    @FastNative
    static final native int nativeGetName(long var0);

    @FastNative
    private static final native int nativeGetNamespace(long var0);

    @FastNative
    private static final native int nativeGetSourceResId(long var0);

    private static final native long nativeGetStringBlock(long var0);

    @FastNative
    private static final native int nativeGetStyleAttribute(long var0);

    @FastNative
    private static final native int nativeGetText(long var0);

    @FastNative
    static final native int nativeNext(long var0);

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() {
        synchronized (this) {
            if (this.mOpen) {
                this.mOpen = false;
                this.decOpenCountLocked();
            }
            return;
        }
    }

    protected void finalize() throws Throwable {
        this.close();
    }

    @UnsupportedAppUsage
    public XmlResourceParser newParser() {
        return this.newParser(0);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public XmlResourceParser newParser(int n) {
        synchronized (this) {
            if (this.mNative == 0L) return null;
            return new Parser(XmlBlock.nativeCreateParseState(this.mNative, n), this);
        }
    }

    final class Parser
    implements XmlResourceParser {
        @UnsupportedAppUsage
        private final XmlBlock mBlock;
        private boolean mDecNextDepth = false;
        private int mDepth = 0;
        private int mEventType = 0;
        @UnsupportedAppUsage
        long mParseState;
        private boolean mStarted = false;

        Parser(long l, XmlBlock xmlBlock2) {
            this.mParseState = l;
            this.mBlock = xmlBlock2;
            XmlBlock.access$008(xmlBlock2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void close() {
            XmlBlock xmlBlock = this.mBlock;
            synchronized (xmlBlock) {
                if (this.mParseState != 0L) {
                    XmlBlock.nativeDestroyParseState(this.mParseState);
                    this.mParseState = 0L;
                    this.mBlock.decOpenCountLocked();
                }
                return;
            }
        }

        public void defineEntityReplacementText(String string2, String string3) throws XmlPullParserException {
            throw new XmlPullParserException("defineEntityReplacementText() not supported");
        }

        protected void finalize() throws Throwable {
            this.close();
        }

        @Override
        public boolean getAttributeBooleanValue(int n, boolean bl) {
            int n2 = XmlBlock.nativeGetAttributeDataType(this.mParseState, n);
            if (n2 >= 16 && n2 <= 31) {
                bl = XmlBlock.nativeGetAttributeData(this.mParseState, n) != 0;
                return bl;
            }
            return bl;
        }

        @Override
        public boolean getAttributeBooleanValue(String string2, String string3, boolean bl) {
            int n = XmlBlock.nativeGetAttributeIndex(this.mParseState, string2, string3);
            if (n >= 0) {
                return this.getAttributeBooleanValue(n, bl);
            }
            return bl;
        }

        @Override
        public int getAttributeCount() {
            int n = this.mEventType == 2 ? XmlBlock.nativeGetAttributeCount(this.mParseState) : -1;
            return n;
        }

        @Override
        public float getAttributeFloatValue(int n, float f) {
            if (XmlBlock.nativeGetAttributeDataType(this.mParseState, n) == 4) {
                return Float.intBitsToFloat(XmlBlock.nativeGetAttributeData(this.mParseState, n));
            }
            throw new RuntimeException("not a float!");
        }

        @Override
        public float getAttributeFloatValue(String string2, String string3, float f) {
            int n = XmlBlock.nativeGetAttributeIndex(this.mParseState, string2, string3);
            if (n >= 0) {
                return this.getAttributeFloatValue(n, f);
            }
            return f;
        }

        @Override
        public int getAttributeIntValue(int n, int n2) {
            int n3 = XmlBlock.nativeGetAttributeDataType(this.mParseState, n);
            if (n3 >= 16 && n3 <= 31) {
                return XmlBlock.nativeGetAttributeData(this.mParseState, n);
            }
            return n2;
        }

        @Override
        public int getAttributeIntValue(String string2, String string3, int n) {
            int n2 = XmlBlock.nativeGetAttributeIndex(this.mParseState, string2, string3);
            if (n2 >= 0) {
                return this.getAttributeIntValue(n2, n);
            }
            return n;
        }

        @Override
        public int getAttributeListValue(int n, String[] arrstring, int n2) {
            int n3 = XmlBlock.nativeGetAttributeDataType(this.mParseState, n);
            n = XmlBlock.nativeGetAttributeData(this.mParseState, n);
            if (n3 == 3) {
                return XmlUtils.convertValueToList(XmlBlock.this.mStrings.get(n), arrstring, n2);
            }
            return n;
        }

        @Override
        public int getAttributeListValue(String string2, String string3, String[] arrstring, int n) {
            int n2 = XmlBlock.nativeGetAttributeIndex(this.mParseState, string2, string3);
            if (n2 >= 0) {
                return this.getAttributeListValue(n2, arrstring, n);
            }
            return n;
        }

        @Override
        public String getAttributeName(int n) {
            int n2 = XmlBlock.nativeGetAttributeName(this.mParseState, n);
            if (n2 >= 0) {
                return XmlBlock.this.mStrings.get(n2).toString();
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public int getAttributeNameResource(int n) {
            return XmlBlock.nativeGetAttributeResource(this.mParseState, n);
        }

        @Override
        public String getAttributeNamespace(int n) {
            int n2 = XmlBlock.nativeGetAttributeNamespace(this.mParseState, n);
            if (n2 >= 0) {
                return XmlBlock.this.mStrings.get(n2).toString();
            }
            if (n2 == -1) {
                return "";
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        public String getAttributePrefix(int n) {
            throw new RuntimeException("getAttributePrefix not supported");
        }

        @Override
        public int getAttributeResourceValue(int n, int n2) {
            if (XmlBlock.nativeGetAttributeDataType(this.mParseState, n) == 1) {
                return XmlBlock.nativeGetAttributeData(this.mParseState, n);
            }
            return n2;
        }

        @Override
        public int getAttributeResourceValue(String string2, String string3, int n) {
            int n2 = XmlBlock.nativeGetAttributeIndex(this.mParseState, string2, string3);
            if (n2 >= 0) {
                return this.getAttributeResourceValue(n2, n);
            }
            return n;
        }

        public String getAttributeType(int n) {
            return "CDATA";
        }

        @Override
        public int getAttributeUnsignedIntValue(int n, int n2) {
            int n3 = XmlBlock.nativeGetAttributeDataType(this.mParseState, n);
            if (n3 >= 16 && n3 <= 31) {
                return XmlBlock.nativeGetAttributeData(this.mParseState, n);
            }
            return n2;
        }

        @Override
        public int getAttributeUnsignedIntValue(String string2, String string3, int n) {
            int n2 = XmlBlock.nativeGetAttributeIndex(this.mParseState, string2, string3);
            if (n2 >= 0) {
                return this.getAttributeUnsignedIntValue(n2, n);
            }
            return n;
        }

        @Override
        public String getAttributeValue(int n) {
            int n2 = XmlBlock.nativeGetAttributeStringValue(this.mParseState, n);
            if (n2 >= 0) {
                return XmlBlock.this.mStrings.get(n2).toString();
            }
            n2 = XmlBlock.nativeGetAttributeDataType(this.mParseState, n);
            if (n2 != 0) {
                return TypedValue.coerceToString(n2, XmlBlock.nativeGetAttributeData(this.mParseState, n));
            }
            throw new IndexOutOfBoundsException(String.valueOf(n));
        }

        @Override
        public String getAttributeValue(String string2, String string3) {
            int n = XmlBlock.nativeGetAttributeIndex(this.mParseState, string2, string3);
            if (n >= 0) {
                return this.getAttributeValue(n);
            }
            return null;
        }

        @Override
        public String getClassAttribute() {
            int n = XmlBlock.nativeGetClassAttribute(this.mParseState);
            String string2 = n >= 0 ? XmlBlock.this.mStrings.get(n).toString() : null;
            return string2;
        }

        public int getColumnNumber() {
            return -1;
        }

        public int getDepth() {
            return this.mDepth;
        }

        public int getEventType() throws XmlPullParserException {
            return this.mEventType;
        }

        public boolean getFeature(String string2) {
            if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(string2)) {
                return true;
            }
            return "http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes".equals(string2);
        }

        @Override
        public String getIdAttribute() {
            int n = XmlBlock.nativeGetIdAttribute(this.mParseState);
            String string2 = n >= 0 ? XmlBlock.this.mStrings.get(n).toString() : null;
            return string2;
        }

        @Override
        public int getIdAttributeResourceValue(int n) {
            return this.getAttributeResourceValue(null, "id", n);
        }

        public String getInputEncoding() {
            return null;
        }

        public int getLineNumber() {
            return XmlBlock.nativeGetLineNumber(this.mParseState);
        }

        public String getName() {
            int n = XmlBlock.nativeGetName(this.mParseState);
            String string2 = n >= 0 ? XmlBlock.this.mStrings.get(n).toString() : null;
            return string2;
        }

        public String getNamespace() {
            int n = XmlBlock.nativeGetNamespace(this.mParseState);
            String string2 = n >= 0 ? XmlBlock.this.mStrings.get(n).toString() : "";
            return string2;
        }

        public String getNamespace(String string2) {
            throw new RuntimeException("getNamespace() not supported");
        }

        public int getNamespaceCount(int n) throws XmlPullParserException {
            throw new XmlPullParserException("getNamespaceCount() not supported");
        }

        public String getNamespacePrefix(int n) throws XmlPullParserException {
            throw new XmlPullParserException("getNamespacePrefix() not supported");
        }

        public String getNamespaceUri(int n) throws XmlPullParserException {
            throw new XmlPullParserException("getNamespaceUri() not supported");
        }

        final CharSequence getPooledString(int n) {
            return XmlBlock.this.mStrings.get(n);
        }

        @Override
        public String getPositionDescription() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Binary XML file line #");
            stringBuilder.append(this.getLineNumber());
            return stringBuilder.toString();
        }

        public String getPrefix() {
            throw new RuntimeException("getPrefix not supported");
        }

        public Object getProperty(String string2) {
            return null;
        }

        public int getSourceResId() {
            return XmlBlock.nativeGetSourceResId(this.mParseState);
        }

        @Override
        public int getStyleAttribute() {
            return XmlBlock.nativeGetStyleAttribute(this.mParseState);
        }

        public String getText() {
            int n = XmlBlock.nativeGetText(this.mParseState);
            String string2 = n >= 0 ? XmlBlock.this.mStrings.get(n).toString() : null;
            return string2;
        }

        public char[] getTextCharacters(int[] arrn) {
            String string2 = this.getText();
            char[] arrc = null;
            if (string2 != null) {
                arrn[0] = 0;
                arrn[1] = string2.length();
                arrc = new char[string2.length()];
                string2.getChars(0, string2.length(), arrc, 0);
            }
            return arrc;
        }

        public boolean isAttributeDefault(int n) {
            return false;
        }

        public boolean isEmptyElementTag() throws XmlPullParserException {
            return false;
        }

        public boolean isWhitespace() throws XmlPullParserException {
            return false;
        }

        public int next() throws XmlPullParserException, IOException {
            if (!this.mStarted) {
                this.mStarted = true;
                return 0;
            }
            long l = this.mParseState;
            if (l == 0L) {
                return 1;
            }
            int n = XmlBlock.nativeNext(l);
            if (this.mDecNextDepth) {
                --this.mDepth;
                this.mDecNextDepth = false;
            }
            if (n != 2) {
                if (n == 3) {
                    this.mDecNextDepth = true;
                }
            } else {
                ++this.mDepth;
            }
            this.mEventType = n;
            if (n == 1) {
                this.close();
            }
            return n;
        }

        public int nextTag() throws XmlPullParserException, IOException {
            int n;
            int n2 = n = this.next();
            if (n == 4) {
                n2 = n;
                if (this.isWhitespace()) {
                    n2 = this.next();
                }
            }
            if (n2 != 2 && n2 != 3) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.getPositionDescription());
                stringBuilder.append(": expected start or end tag");
                throw new XmlPullParserException(stringBuilder.toString(), (XmlPullParser)this, null);
            }
            return n2;
        }

        public String nextText() throws XmlPullParserException, IOException {
            if (this.getEventType() == 2) {
                int n = this.next();
                if (n == 4) {
                    CharSequence charSequence = this.getText();
                    if (this.next() == 3) {
                        return charSequence;
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(this.getPositionDescription());
                    ((StringBuilder)charSequence).append(": event TEXT it must be immediately followed by END_TAG");
                    throw new XmlPullParserException(((StringBuilder)charSequence).toString(), (XmlPullParser)this, null);
                }
                if (n == 3) {
                    return "";
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.getPositionDescription());
                stringBuilder.append(": parser must be on START_TAG or TEXT to read text");
                throw new XmlPullParserException(stringBuilder.toString(), (XmlPullParser)this, null);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getPositionDescription());
            stringBuilder.append(": parser must be on START_TAG to read next text");
            throw new XmlPullParserException(stringBuilder.toString(), (XmlPullParser)this, null);
        }

        public int nextToken() throws XmlPullParserException, IOException {
            return this.next();
        }

        public void require(int n, String charSequence, String string2) throws XmlPullParserException, IOException {
            if (n == this.getEventType() && (charSequence == null || ((String)charSequence).equals(this.getNamespace())) && (string2 == null || string2.equals(this.getName()))) {
                return;
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("expected ");
            ((StringBuilder)charSequence).append(TYPES[n]);
            ((StringBuilder)charSequence).append(this.getPositionDescription());
            throw new XmlPullParserException(((StringBuilder)charSequence).toString());
        }

        public void setFeature(String string2, boolean bl) throws XmlPullParserException {
            if ("http://xmlpull.org/v1/doc/features.html#process-namespaces".equals(string2) && bl) {
                return;
            }
            if ("http://xmlpull.org/v1/doc/features.html#report-namespace-prefixes".equals(string2) && bl) {
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Unsupported feature: ");
            stringBuilder.append(string2);
            throw new XmlPullParserException(stringBuilder.toString());
        }

        public void setInput(InputStream inputStream, String string2) throws XmlPullParserException {
            throw new XmlPullParserException("setInput() not supported");
        }

        public void setInput(Reader reader) throws XmlPullParserException {
            throw new XmlPullParserException("setInput() not supported");
        }

        public void setProperty(String string2, Object object) throws XmlPullParserException {
            throw new XmlPullParserException("setProperty() not supported");
        }
    }

}

