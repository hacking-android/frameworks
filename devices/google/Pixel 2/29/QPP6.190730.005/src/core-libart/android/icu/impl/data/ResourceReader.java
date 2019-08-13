/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.data;

import android.icu.impl.ICUData;
import android.icu.impl.PatternProps;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;

public class ResourceReader
implements Closeable {
    private String encoding;
    private int lineNo;
    private BufferedReader reader;
    private String resourceName;
    private Class<?> root;

    public ResourceReader(InputStream inputStream, String string) {
        this(inputStream, string, null);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public ResourceReader(InputStream var1_1, String var2_3, String var3_4) {
        super();
        this.reader = null;
        this.root = null;
        this.resourceName = var2_3;
        this.encoding = var3_4;
        this.lineNo = -1;
        if (var3_4 != null) ** GOTO lbl11
        try {
            block2 : {
                var1_1 = var2_3 = new InputStreamReader((InputStream)var1_1);
                break block2;
lbl11: // 1 sources:
                var1_1 = new InputStreamReader((InputStream)var1_1, var3_4);
            }
            this.reader = var2_3 = new BufferedReader((Reader)var1_1);
            this.lineNo = 0;
            return;
        }
        catch (UnsupportedEncodingException var1_2) {
            // empty catch block
        }
    }

    public ResourceReader(Class<?> class_, String string) {
        this.reader = null;
        this.root = class_;
        this.resourceName = string;
        this.encoding = null;
        this.lineNo = -1;
        try {
            this._reset();
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {}
    }

    public ResourceReader(Class<?> class_, String string, String string2) throws UnsupportedEncodingException {
        this.reader = null;
        this.root = class_;
        this.resourceName = string;
        this.encoding = string2;
        this.lineNo = -1;
        this._reset();
    }

    public ResourceReader(String string) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data/");
        stringBuilder.append(string);
        this(ICUData.class, stringBuilder.toString());
    }

    public ResourceReader(String string, String string2) throws UnsupportedEncodingException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("data/");
        stringBuilder.append(string);
        this(ICUData.class, stringBuilder.toString(), string2);
    }

    private void _reset() throws UnsupportedEncodingException {
        try {
            this.close();
        }
        catch (IOException iOException) {}
        if (this.lineNo == 0) {
            return;
        }
        InputStream inputStream = ICUData.getStream(this.root, this.resourceName);
        if (inputStream != null) {
            Object object = this.encoding;
            object = object == null ? new InputStreamReader(inputStream) : new InputStreamReader(inputStream, (String)object);
            this.reader = new BufferedReader((Reader)object);
            this.lineNo = 0;
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Can't open ");
        stringBuilder.append(this.resourceName);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void close() throws IOException {
        BufferedReader bufferedReader = this.reader;
        if (bufferedReader != null) {
            bufferedReader.close();
            this.reader = null;
        }
    }

    public String describePosition() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.resourceName);
        stringBuilder.append(':');
        stringBuilder.append(this.lineNo);
        return stringBuilder.toString();
    }

    public int getLineNumber() {
        return this.lineNo;
    }

    public String readLine() throws IOException {
        int n;
        block2 : {
            String string;
            block3 : {
                String string2;
                block4 : {
                    n = this.lineNo;
                    if (n != 0) break block2;
                    this.lineNo = n + 1;
                    string = string2 = this.reader.readLine();
                    if (string2 == null) break block3;
                    if (string2.charAt(0) == '\uffef') break block4;
                    string = string2;
                    if (string2.charAt(0) != '\ufeff') break block3;
                }
                string = string2.substring(1);
            }
            return string;
        }
        this.lineNo = n + 1;
        return this.reader.readLine();
    }

    public String readLineSkippingComments() throws IOException {
        return this.readLineSkippingComments(false);
    }

    public String readLineSkippingComments(boolean bl) throws IOException {
        String string;
        int n;
        do {
            if ((string = this.readLine()) != null) continue;
            return string;
        } while ((n = PatternProps.skipWhiteSpace(string, 0)) == string.length() || string.charAt(n) == '#');
        String string2 = string;
        if (bl) {
            string2 = string.substring(n);
        }
        return string2;
    }

    public void reset() {
        try {
            this._reset();
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {}
    }
}

