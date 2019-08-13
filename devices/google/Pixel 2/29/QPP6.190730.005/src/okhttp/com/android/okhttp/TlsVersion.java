/*
 * Decompiled with CFR 0.145.
 */
package com.android.okhttp;

public enum TlsVersion {
    TLS_1_2("TLSv1.2"),
    TLS_1_1("TLSv1.1"),
    TLS_1_0("TLSv1"),
    SSL_3_0("SSLv3");
    
    final String javaName;

    private TlsVersion(String string2) {
        this.javaName = string2;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public static TlsVersion forJavaName(String var0) {
        block7 : {
            switch (var0.hashCode()) {
                case 79923350: {
                    if (!var0.equals("TLSv1")) break;
                    var1_1 = 2;
                    break block7;
                }
                case 79201641: {
                    if (!var0.equals("SSLv3")) break;
                    var1_1 = 3;
                    break block7;
                }
                case -503070502: {
                    if (!var0.equals("TLSv1.2")) break;
                    var1_1 = 0;
                    break block7;
                }
                case -503070503: {
                    if (!var0.equals("TLSv1.1")) break;
                    var1_1 = 1;
                    break block7;
                }
            }
            ** break;
lbl19: // 1 sources:
            var1_1 = -1;
        }
        if (var1_1 == 0) return TlsVersion.TLS_1_2;
        if (var1_1 == 1) return TlsVersion.TLS_1_1;
        if (var1_1 == 2) return TlsVersion.TLS_1_0;
        if (var1_1 == 3) {
            return TlsVersion.SSL_3_0;
        }
        var2_2 = new StringBuilder();
        var2_2.append("Unexpected TLS version: ");
        var2_2.append(var0);
        throw new IllegalArgumentException(var2_2.toString());
    }

    public String javaName() {
        return this.javaName;
    }
}

