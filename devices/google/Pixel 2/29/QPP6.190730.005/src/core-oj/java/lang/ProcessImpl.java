/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.IOException;
import java.util.Map;

final class ProcessImpl {
    static final /* synthetic */ boolean $assertionsDisabled = false;

    private ProcessImpl() {
    }

    static Process start(String[] arrstring, Map<String, String> map, String string, ProcessBuilder.Redirect[] arrredirect, boolean bl) throws IOException {
        throw new RuntimeException("d2j fail translate: java.lang.NullPointerException\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.genRegGraph(UnSSATransformer.java:370)\n\tat com.googlecode.dex2jar.ir.ts.UnSSATransformer.transform(UnSSATransformer.java:302)\n\tat com.googlecode.d2j.dex.Dex2jar$2.optimize(Dex2jar.java:166)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertCode(Dex2Asm.java:452)\n\tat com.googlecode.d2j.dex.ExDex2Asm.convertCode(ExDex2Asm.java:40)\n\tat com.googlecode.d2j.dex.Dex2jar$2.convertCode(Dex2jar.java:132)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertMethod(Dex2Asm.java:596)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:444)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertClass(Dex2Asm.java:357)\n\tat com.googlecode.d2j.dex.Dex2Asm.convertDex(Dex2Asm.java:460)\n\tat com.googlecode.d2j.dex.Dex2jar.doTranslate(Dex2jar.java:175)\n\tat com.googlecode.d2j.dex.Dex2jar.to(Dex2jar.java:275)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.doCommandLine(Dex2jarCmd.java:112)\n\tat com.googlecode.dex2jar.tools.BaseCmd.doMain(BaseCmd.java:290)\n\tat com.googlecode.dex2jar.tools.Dex2jarCmd.main(Dex2jarCmd.java:33)\n");
    }

    private static byte[] toCString(String arrby) {
        if (arrby == null) {
            return null;
        }
        byte[] arrby2 = arrby.getBytes();
        arrby = new byte[arrby2.length + 1];
        System.arraycopy(arrby2, 0, arrby, 0, arrby2.length);
        arrby[arrby.length - 1] = (byte)(false ? 1 : 0);
        return arrby;
    }
}

