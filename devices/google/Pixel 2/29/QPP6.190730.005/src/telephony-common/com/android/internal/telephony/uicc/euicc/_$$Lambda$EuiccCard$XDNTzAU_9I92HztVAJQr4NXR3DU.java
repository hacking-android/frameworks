/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.uicc.euicc;

import com.android.internal.telephony.uicc.euicc.EuiccCard;
import com.android.internal.telephony.uicc.euicc.apdu.RequestBuilder;

public final class _$$Lambda$EuiccCard$XDNTzAU_9I92HztVAJQr4NXR3DU
implements EuiccCard.ApduRequestBuilder {
    private final /* synthetic */ byte[] f$0;

    public /* synthetic */ _$$Lambda$EuiccCard$XDNTzAU_9I92HztVAJQr4NXR3DU(byte[] arrby) {
        this.f$0 = arrby;
    }

    @Override
    public final void build(RequestBuilder requestBuilder) {
        EuiccCard.lambda$loadBoundProfilePackage$36(this.f$0, requestBuilder);
    }
}

