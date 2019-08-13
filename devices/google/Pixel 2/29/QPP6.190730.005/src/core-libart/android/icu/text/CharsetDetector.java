/*
 * Decompiled with CFR 0.145.
 */
package android.icu.text;

import android.icu.text.CharsetMatch;
import android.icu.text.CharsetRecog_2022;
import android.icu.text.CharsetRecog_UTF8;
import android.icu.text.CharsetRecog_Unicode;
import android.icu.text.CharsetRecog_mbcs;
import android.icu.text.CharsetRecog_sbcs;
import android.icu.text.CharsetRecognizer;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CharsetDetector {
    private static final List<CSRecognizerInfo> ALL_CS_RECOGNIZERS;
    private static final int kBufSize = 8000;
    short[] fByteStats = new short[256];
    boolean fC1Bytes = false;
    String fDeclaredEncoding;
    private boolean[] fEnabledRecognizers;
    byte[] fInputBytes = new byte[8000];
    int fInputLen;
    InputStream fInputStream;
    byte[] fRawInput;
    int fRawLength;
    private boolean fStripTags = false;

    static {
        ArrayList<CSRecognizerInfo> arrayList = new ArrayList<CSRecognizerInfo>();
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_UTF8(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_BE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_16_LE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_BE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_Unicode.CharsetRecog_UTF_32_LE(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_sjis(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022JP(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022CN(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_2022.CharsetRecog_2022KR(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_gb_18030(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_jp(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_euc.CharsetRecog_euc_kr(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_mbcs.CharsetRecog_big5(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_1(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_2(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_5_ru(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_6_ar(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_7_el(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_I_he(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_8_he(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1251(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_windows_1256(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_KOI8_R(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_8859_9_tr(), true));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_rtl(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM424_he_ltr(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_rtl(), false));
        arrayList.add(new CSRecognizerInfo(new CharsetRecog_sbcs.CharsetRecog_IBM420_ar_ltr(), false));
        ALL_CS_RECOGNIZERS = Collections.unmodifiableList(arrayList);
    }

    private void MungeInput() {
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        if (this.fStripTags) {
            n5 = n6;
            n3 = n4;
            for (int i = 0; i < this.fRawLength && n < this.fInputBytes.length; ++i) {
                byte by = this.fRawInput[i];
                n4 = n2;
                int n7 = n3;
                n6 = n5;
                if (by == 60) {
                    n4 = n5;
                    if (n2 != 0) {
                        n4 = n5 + 1;
                    }
                    n5 = 1;
                    n7 = n3 + 1;
                    n6 = n4;
                    n4 = n5;
                }
                n5 = n;
                if (n4 == 0) {
                    this.fInputBytes[n] = by;
                    n5 = n + 1;
                }
                n2 = n4;
                if (by == 62) {
                    n2 = 0;
                }
                n = n5;
                n3 = n7;
                n5 = n6;
            }
            this.fInputLen = n;
        }
        if (n3 < 5 || n3 / 5 < n5 || this.fInputLen < 100 && this.fRawLength > 600) {
            n5 = n3 = this.fRawLength;
            if (n3 > 8000) {
                n5 = 8000;
            }
            for (n3 = 0; n3 < n5; ++n3) {
                this.fInputBytes[n3] = this.fRawInput[n3];
            }
            this.fInputLen = n3;
        }
        Arrays.fill(this.fByteStats, (short)0);
        for (n5 = 0; n5 < this.fInputLen; ++n5) {
            n3 = this.fInputBytes[n5] & 255;
            short[] arrs = this.fByteStats;
            arrs[n3] = (short)(arrs[n3] + 1);
        }
        this.fC1Bytes = false;
        for (n5 = 128; n5 <= 159; ++n5) {
            if (this.fByteStats[n5] == 0) continue;
            this.fC1Bytes = true;
            break;
        }
    }

    public static String[] getAllDetectableCharsets() {
        String[] arrstring = new String[ALL_CS_RECOGNIZERS.size()];
        for (int i = 0; i < arrstring.length; ++i) {
            arrstring[i] = CharsetDetector.ALL_CS_RECOGNIZERS.get((int)i).recognizer.getName();
        }
        return arrstring;
    }

    public CharsetMatch detect() {
        CharsetMatch[] arrcharsetMatch = this.detectAll();
        if (arrcharsetMatch != null && arrcharsetMatch.length != 0) {
            return arrcharsetMatch[0];
        }
        return null;
    }

    public CharsetMatch[] detectAll() {
        ArrayList<boolean[]> arrayList = new ArrayList<boolean[]>();
        this.MungeInput();
        for (int i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
            CSRecognizerInfo cSRecognizerInfo = ALL_CS_RECOGNIZERS.get(i);
            Object object = this.fEnabledRecognizers;
            boolean bl = object != null ? object[i] : cSRecognizerInfo.isDefaultEnabled;
            if (!bl || (object = cSRecognizerInfo.recognizer.match(this)) == null) continue;
            arrayList.add((boolean[])object);
        }
        Collections.sort(arrayList);
        Collections.reverse(arrayList);
        return arrayList.toArray(new CharsetMatch[arrayList.size()]);
    }

    public boolean enableInputFilter(boolean bl) {
        boolean bl2 = this.fStripTags;
        this.fStripTags = bl;
        return bl2;
    }

    @Deprecated
    public String[] getDetectableCharsets() {
        ArrayList<String> arrayList = new ArrayList<String>(ALL_CS_RECOGNIZERS.size());
        for (int i = 0; i < ALL_CS_RECOGNIZERS.size(); ++i) {
            CSRecognizerInfo cSRecognizerInfo = ALL_CS_RECOGNIZERS.get(i);
            boolean[] arrbl = this.fEnabledRecognizers;
            boolean bl = arrbl == null ? cSRecognizerInfo.isDefaultEnabled : arrbl[i];
            if (!bl) continue;
            arrayList.add(cSRecognizerInfo.recognizer.getName());
        }
        return arrayList.toArray(new String[arrayList.size()]);
    }

    public Reader getReader(InputStream object, String string) {
        block3 : {
            this.fDeclaredEncoding = string;
            try {
                this.setText((InputStream)object);
                object = this.detect();
                if (object != null) break block3;
                return null;
            }
            catch (IOException iOException) {
                return null;
            }
        }
        object = ((CharsetMatch)object).getReader();
        return object;
    }

    public String getString(byte[] object, String string) {
        block3 : {
            this.fDeclaredEncoding = string;
            try {
                this.setText((byte[])object);
                object = this.detect();
                if (object != null) break block3;
                return null;
            }
            catch (IOException iOException) {
                return null;
            }
        }
        object = ((CharsetMatch)object).getString(-1);
        return object;
    }

    public boolean inputFilterEnabled() {
        return this.fStripTags;
    }

    public CharsetDetector setDeclaredEncoding(String string) {
        this.fDeclaredEncoding = string;
        return this;
    }

    @Deprecated
    public CharsetDetector setDetectableCharset(String arrbl, boolean bl) {
        int n;
        int n2;
        Object object;
        int n3 = -1;
        int n4 = 0;
        int n5 = 0;
        do {
            n2 = n3;
            n = n4;
            if (n5 >= ALL_CS_RECOGNIZERS.size()) break;
            object = ALL_CS_RECOGNIZERS.get(n5);
            if (((CSRecognizerInfo)object).recognizer.getName().equals(arrbl)) {
                n2 = n5;
                n5 = ((CSRecognizerInfo)object).isDefaultEnabled == bl ? 1 : 0;
                n = n5;
                break;
            }
            ++n5;
        } while (true);
        if (n2 >= 0) {
            if (this.fEnabledRecognizers == null && n == 0) {
                this.fEnabledRecognizers = new boolean[ALL_CS_RECOGNIZERS.size()];
                for (n5 = 0; n5 < ALL_CS_RECOGNIZERS.size(); ++n5) {
                    this.fEnabledRecognizers[n5] = CharsetDetector.ALL_CS_RECOGNIZERS.get((int)n5).isDefaultEnabled;
                }
            }
            if ((arrbl = this.fEnabledRecognizers) != null) {
                arrbl[n2] = bl;
            }
            return this;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid encoding: \"");
        ((StringBuilder)object).append((String)arrbl);
        ((StringBuilder)object).append("\"");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public CharsetDetector setText(InputStream inputStream) throws IOException {
        int n;
        this.fInputStream = inputStream;
        this.fInputStream.mark(8000);
        this.fRawInput = new byte[8000];
        this.fRawLength = 0;
        for (int i = 8000; i > 0 && (n = this.fInputStream.read(this.fRawInput, this.fRawLength, i)) > 0; i -= n) {
            this.fRawLength += n;
        }
        this.fInputStream.reset();
        return this;
    }

    public CharsetDetector setText(byte[] arrby) {
        this.fRawInput = arrby;
        this.fRawLength = arrby.length;
        return this;
    }

    private static class CSRecognizerInfo {
        boolean isDefaultEnabled;
        CharsetRecognizer recognizer;

        CSRecognizerInfo(CharsetRecognizer charsetRecognizer, boolean bl) {
            this.recognizer = charsetRecognizer;
            this.isDefaultEnabled = bl;
        }
    }

}

