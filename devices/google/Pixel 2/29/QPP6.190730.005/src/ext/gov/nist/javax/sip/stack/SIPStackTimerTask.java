/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import java.io.PrintStream;
import java.util.TimerTask;

public abstract class SIPStackTimerTask
extends TimerTask {
    @Override
    public final void run() {
        try {
            this.runTask();
        }
        catch (Throwable throwable) {
            System.out.println("SIP stack timer task failed due to exception:");
            throwable.printStackTrace();
        }
    }

    protected abstract void runTask();
}

