/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.webkit.WebMessagePort;

public class WebMessage {
    private String mData;
    private WebMessagePort[] mPorts;

    public WebMessage(String string2) {
        this.mData = string2;
    }

    public WebMessage(String string2, WebMessagePort[] arrwebMessagePort) {
        this.mData = string2;
        this.mPorts = arrwebMessagePort;
    }

    public String getData() {
        return this.mData;
    }

    public WebMessagePort[] getPorts() {
        return this.mPorts;
    }
}

