/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp.internal.http;

import com.android.okhttp.Protocol;
import com.android.okhttp.Response;
import java.io.IOException;
import java.net.ProtocolException;

public final class StatusLine {
    public static final int HTTP_CONTINUE = 100;
    public static final int HTTP_PERM_REDIRECT = 308;
    public static final int HTTP_TEMP_REDIRECT = 307;
    public final int code;
    public final String message;
    public final Protocol protocol;

    public StatusLine(Protocol protocol, int n, String string) {
        this.protocol = protocol;
        this.code = n;
        this.message = string;
    }

    public static StatusLine get(Response response) {
        return new StatusLine(response.protocol(), response.code(), response.message());
    }

    public static StatusLine parse(String string) throws IOException {
        block14 : {
            int n;
            Object object;
            int n2;
            block12 : {
                block9 : {
                    block10 : {
                        block13 : {
                            block11 : {
                                if (!string.startsWith("HTTP/1.")) break block9;
                                if (string.length() < 9 || string.charAt(8) != ' ') break block10;
                                n = string.charAt(7) - 48;
                                n2 = 9;
                                if (n != 0) break block11;
                                object = Protocol.HTTP_1_0;
                                break block12;
                            }
                            if (n != 1) break block13;
                            object = Protocol.HTTP_1_1;
                            break block12;
                        }
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Unexpected status line: ");
                        stringBuilder.append(string);
                        throw new ProtocolException(stringBuilder.toString());
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected status line: ");
                    stringBuilder.append(string);
                    throw new ProtocolException(stringBuilder.toString());
                }
                if (!string.startsWith("ICY ")) break block14;
                object = Protocol.HTTP_1_0;
                n2 = 4;
            }
            if (string.length() >= n2 + 3) {
                String string2;
                try {
                    n = Integer.parseInt(string.substring(n2, n2 + 3));
                    string2 = "";
                }
                catch (NumberFormatException numberFormatException) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Unexpected status line: ");
                    stringBuilder.append(string);
                    throw new ProtocolException(stringBuilder.toString());
                }
                if (string.length() > n2 + 3) {
                    if (string.charAt(n2 + 3) == ' ') {
                        string2 = string.substring(n2 + 4);
                    } else {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unexpected status line: ");
                        ((StringBuilder)object).append(string);
                        throw new ProtocolException(((StringBuilder)object).toString());
                    }
                }
                return new StatusLine((Protocol)((Object)object), n, string2);
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected status line: ");
            ((StringBuilder)object).append(string);
            throw new ProtocolException(((StringBuilder)object).toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Unexpected status line: ");
        stringBuilder.append(string);
        throw new ProtocolException(stringBuilder.toString());
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        String string = this.protocol == Protocol.HTTP_1_0 ? "HTTP/1.0" : "HTTP/1.1";
        stringBuilder.append(string);
        stringBuilder.append(' ');
        stringBuilder.append(this.code);
        if (this.message != null) {
            stringBuilder.append(' ');
            stringBuilder.append(this.message);
        }
        return stringBuilder.toString();
    }
}

