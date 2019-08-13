/*
 * Decompiled with CFR 0.145.
 */
package android.webkit;

import android.annotation.SystemApi;
import android.webkit.JsResult;

public class JsPromptResult
extends JsResult {
    private String mStringResult;

    @SystemApi
    public JsPromptResult(JsResult.ResultReceiver resultReceiver) {
        super(resultReceiver);
    }

    public void confirm(String string2) {
        this.mStringResult = string2;
        this.confirm();
    }

    @SystemApi
    public String getStringResult() {
        return this.mStringResult;
    }
}

