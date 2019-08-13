/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.telephony.Rlog
 */
package com.android.internal.telephony.test;

import android.os.HandlerThread;
import android.os.Looper;
import android.telephony.Rlog;
import com.android.internal.telephony.test.InterpreterEx;
import com.android.internal.telephony.test.LineReader;
import com.android.internal.telephony.test.SimulatedGsmCallState;
import com.android.internal.telephony.test.SimulatedRadioControl;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

public class ModelInterpreter
implements Runnable,
SimulatedRadioControl {
    static final int CONNECTING_PAUSE_MSEC = 500;
    static final String LOG_TAG = "ModelInterpreter";
    static final int MAX_CALLS = 6;
    static final int PROGRESS_CALL_STATE = 1;
    static final String[][] sDefaultResponses;
    private String mFinalResponse;
    HandlerThread mHandlerThread;
    InputStream mIn;
    LineReader mLineReader;
    OutputStream mOut;
    int mPausedResponseCount;
    Object mPausedResponseMonitor = new Object();
    ServerSocket mSS;
    SimulatedGsmCallState mSimulatedCallState;

    static {
        String[] arrstring = new String[]{"E0Q0V1", null};
        String[] arrstring2 = new String[]{"+CMEE=2", null};
        String[] arrstring3 = new String[]{"+CREG=2", null};
        String[] arrstring4 = new String[]{"+CCWA=1", null};
        String[] arrstring5 = new String[]{"+COPS=0", null};
        String[] arrstring6 = new String[]{"+CFUN=1", null};
        String[] arrstring7 = new String[]{"+CGMI", "+CGMI: Android Model AT Interpreter\r"};
        String[] arrstring8 = new String[]{"+CGMM", "+CGMM: Android Model AT Interpreter\r"};
        String[] arrstring9 = new String[]{"+CGMR", "+CGMR: 1.0\r"};
        String[] arrstring10 = new String[]{"+CGSN", "000000000000000\r"};
        String[] arrstring11 = new String[]{"+CIMI", "320720000000000\r"};
        String[] arrstring12 = new String[]{"+CSCS=?", "+CSCS: (\"HEX\",\"UCS2\")\r"};
        String[] arrstring13 = new String[]{"+CFUN?", "+CFUN: 1\r"};
        String[] arrstring14 = new String[]{"+COPS=3,0;+COPS?;+COPS=3,1;+COPS?;+COPS=3,2;+COPS?", "+COPS: 0,0,\"Android\"\r+COPS: 0,1,\"Android\"\r+COPS: 0,2,\"310995\"\r"};
        String[] arrstring15 = new String[]{"+CGREG?", "+CGREG: 2,0\r"};
        String[] arrstring16 = new String[]{"+CNMI?", "+CNMI: 1,2,2,1,1\r"};
        String[] arrstring17 = new String[]{"+CLIR?", "+CLIR: 1,3\r"};
        String[] arrstring18 = new String[]{"%CPVWI=2", "%CPVWI: 0\r"};
        String[] arrstring19 = new String[]{"+CUSD=1,\"#646#\"", "+CUSD=0,\"You have used 23 minutes\"\r"};
        String[] arrstring20 = new String[]{"+CRSM=176,12258,0,0,10", "+CRSM: 144,0,981062200050259429F6\r"};
        String[] arrstring21 = new String[]{"+CRSM=192,12258,0,0,15", "+CRSM: 144,0,0000000A2FE204000FF55501020000\r"};
        String[] arrstring22 = new String[]{"+CRSM=192,28474,0,0,15", "+CRSM: 144,0,0000005a6f3a040011f5220102011e\r"};
        String[] arrstring23 = new String[]{"+CRSM=178,28474,2,4,30", "+CRSM: 144,0,566f696365204d61696cffffffffffff07918150367742f3ffffffffffff\r"};
        String[] arrstring24 = new String[]{"+CRSM=178,28474,4,4,30", "+CRSM: 144,0,810101c1ffffffffffffffffffffffff068114455245f8ffffffffffffff\r"};
        String[] arrstring25 = new String[]{"+CRSM=178,28490,1,4,13", "+CRSM: 144,0,0206092143658709ffffffffff\r"};
        sDefaultResponses = new String[][]{arrstring, arrstring2, arrstring3, {"+CGREG=2", null}, arrstring4, arrstring5, arrstring6, arrstring7, arrstring8, arrstring9, arrstring10, arrstring11, arrstring12, arrstring13, arrstring14, {"+CREG?", "+CREG: 2,5, \"0113\", \"6614\"\r"}, arrstring15, {"+CSQ", "+CSQ: 16,99\r"}, arrstring16, arrstring17, arrstring18, arrstring19, arrstring20, arrstring21, arrstring22, {"+CRSM=178,28474,1,4,30", "+CRSM: 144,0,437573746f6d65722043617265ffffff07818100398799f7ffffffffffff\r"}, arrstring23, {"+CRSM=178,28474,3,4,30", "+CRSM: 144,0,4164676a6dffffffffffffffffffffff0b918188551512c221436587ff01\r"}, arrstring24, {"+CRSM=192,28490,0,0,15", "+CRSM: 144,0,000000416f4a040011f5550102010d\r"}, arrstring25};
    }

    public ModelInterpreter(InputStream inputStream, OutputStream outputStream) {
        this.mIn = inputStream;
        this.mOut = outputStream;
        this.init();
    }

    public ModelInterpreter(InetSocketAddress inetSocketAddress) throws IOException {
        this.mSS = new ServerSocket();
        this.mSS.setReuseAddress(true);
        this.mSS.bind(inetSocketAddress);
        this.init();
    }

    private void init() {
        new Thread((Runnable)this, LOG_TAG).start();
        this.mHandlerThread = new HandlerThread(LOG_TAG);
        this.mHandlerThread.start();
        this.mSimulatedCallState = new SimulatedGsmCallState(this.mHandlerThread.getLooper());
    }

    private void onAnswer() throws InterpreterEx {
        if (this.mSimulatedCallState.onAnswer()) {
            return;
        }
        throw new InterpreterEx("ERROR");
    }

    private void onCHLD(String string) throws InterpreterEx {
        char c = '\u0000';
        char c2 = string.charAt(6);
        char c3 = c;
        if (string.length() >= 8) {
            c3 = c = string.charAt(7);
        }
        if (this.mSimulatedCallState.onChld(c2, c3)) {
            return;
        }
        throw new InterpreterEx("ERROR");
    }

    private void onCLCC() {
        List<String> list = this.mSimulatedCallState.getClccLines();
        int n = list.size();
        for (int i = 0; i < n; ++i) {
            this.println(list.get(i));
        }
    }

    private void onDial(String string) throws InterpreterEx {
        if (this.mSimulatedCallState.onDial(string.substring(1))) {
            return;
        }
        throw new InterpreterEx("ERROR");
    }

    private void onHangup() throws InterpreterEx {
        if (this.mSimulatedCallState.onAnswer()) {
            this.mFinalResponse = "NO CARRIER";
            return;
        }
        throw new InterpreterEx("ERROR");
    }

    private void onSMSSend(String string) {
        this.print("> ");
        this.mLineReader.getNextLineCtrlZ();
        this.println("+CMGS: 1");
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void pauseResponses() {
        Object object = this.mPausedResponseMonitor;
        synchronized (object) {
            ++this.mPausedResponseCount;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void print(String arrby) {
        synchronized (this) {
            try {
                try {
                    arrby = arrby.getBytes("US-ASCII");
                    this.mOut.write(arrby);
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void println(String arrby) {
        synchronized (this) {
            try {
                try {
                    arrby = arrby.getBytes("US-ASCII");
                    this.mOut.write(arrby);
                    this.mOut.write(13);
                }
                catch (IOException iOException) {
                    iOException.printStackTrace();
                }
                return;
            }
            catch (Throwable throwable2) {}
            throw throwable2;
        }
    }

    void processLine(String arrstring) throws InterpreterEx {
        arrstring = this.splitCommands((String)arrstring);
        for (int i = 0; i < arrstring.length; ++i) {
            boolean bl;
            String string = arrstring[i];
            if (string.equals("A")) {
                this.onAnswer();
                continue;
            }
            if (string.equals("H")) {
                this.onHangup();
                continue;
            }
            if (string.startsWith("+CHLD=")) {
                this.onCHLD(string);
                continue;
            }
            if (string.equals("+CLCC")) {
                this.onCLCC();
                continue;
            }
            if (string.startsWith("D")) {
                this.onDial(string);
                continue;
            }
            if (string.startsWith("+CMGS=")) {
                this.onSMSSend(string);
                continue;
            }
            boolean bl2 = false;
            int n = 0;
            do {
                String[][] arrstring2 = sDefaultResponses;
                bl = bl2;
                if (n >= arrstring2.length) break;
                if (string.equals(arrstring2[n][0])) {
                    string = sDefaultResponses[n][1];
                    if (string != null) {
                        this.println(string);
                    }
                    bl = true;
                    break;
                }
                ++n;
            } while (true);
            if (bl) {
                continue;
            }
            throw new InterpreterEx("ERROR");
        }
    }

    @Override
    public void progressConnectingCallState() {
        this.mSimulatedCallState.progressConnectingCallState();
    }

    @Override
    public void progressConnectingToActive() {
        this.mSimulatedCallState.progressConnectingToActive();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void resumeResponses() {
        Object object = this.mPausedResponseMonitor;
        synchronized (object) {
            --this.mPausedResponseCount;
            if (this.mPausedResponseCount == 0) {
                this.mPausedResponseMonitor.notifyAll();
            }
            return;
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void run() {
        block15 : do {
            Object object;
            block21 : {
                if ((object = this.mSS) != null) {
                    try {
                        object = ((ServerSocket)object).accept();
                    }
                    catch (IOException iOException) {
                        Rlog.w((String)LOG_TAG, (String)"IOException on socket.accept(); stopping", (Throwable)iOException);
                        return;
                    }
                    try {
                        this.mIn = ((Socket)object).getInputStream();
                        this.mOut = ((Socket)object).getOutputStream();
                    }
                    catch (IOException iOException) {
                        Rlog.w((String)LOG_TAG, (String)"IOException on accepted socket(); re-listening", (Throwable)iOException);
                    }
                    Rlog.i((String)LOG_TAG, (String)"New connection accepted");
                    break block21;
                    continue;
                }
            }
            this.mLineReader = new LineReader(this.mIn);
            this.println("Welcome");
            do {
                block20 : {
                    int n;
                    String string;
                    if ((string = this.mLineReader.getNextLine()) == null) {
                        Rlog.i((String)LOG_TAG, (String)"Disconnected");
                        if (this.mSS == null) return;
                        continue block15;
                    }
                    object = this.mPausedResponseMonitor;
                    // MONITORENTER : object
                    while ((n = this.mPausedResponseCount) > 0) {
                        try {
                            this.mPausedResponseMonitor.wait();
                        }
                        catch (InterruptedException interruptedException) {}
                    }
                    // MONITOREXIT : object
                    // MONITORENTER : this
                    this.mFinalResponse = "OK";
                    this.processLine(string);
                    this.println(this.mFinalResponse);
                    break block20;
                    catch (RuntimeException runtimeException) {
                        runtimeException.printStackTrace();
                        this.println("ERROR");
                    }
                    catch (InterpreterEx interpreterEx) {
                        this.println(interpreterEx.mResult);
                    }
                }
                // MONITOREXIT : this
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void sendUnsolicited(String string) {
        synchronized (this) {
            this.println(string);
            return;
        }
    }

    @Override
    public void setAutoProgressConnectingCall(boolean bl) {
        this.mSimulatedCallState.setAutoProgressConnectingCall(bl);
    }

    @Override
    public void setNextCallFailCause(int n) {
    }

    @Override
    public void setNextDialFailImmediately(boolean bl) {
        this.mSimulatedCallState.setNextDialFailImmediately(bl);
    }

    @Override
    public void shutdown() {
        Looper looper = this.mHandlerThread.getLooper();
        if (looper != null) {
            looper.quit();
        }
        try {
            this.mIn.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        try {
            this.mOut.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    String[] splitCommands(String string) throws InterpreterEx {
        if (string.startsWith("AT")) {
            if (string.length() == 2) {
                return new String[0];
            }
            return new String[]{string.substring(2)};
        }
        throw new InterpreterEx("ERROR");
    }

    @Override
    public void triggerHangupAll() {
        if (this.mSimulatedCallState.triggerHangupAll()) {
            this.println("NO CARRIER");
        }
    }

    @Override
    public void triggerHangupBackground() {
        if (this.mSimulatedCallState.triggerHangupBackground()) {
            this.println("NO CARRIER");
        }
    }

    @Override
    public void triggerHangupForeground() {
        if (this.mSimulatedCallState.triggerHangupForeground()) {
            this.println("NO CARRIER");
        }
    }

    @Override
    public void triggerIncomingSMS(String string) {
    }

    @Override
    public void triggerIncomingUssd(String string, String string2) {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void triggerRing(String string) {
        synchronized (this) {
            if (this.mSimulatedCallState.triggerRing(string)) {
                this.println("RING");
            }
            return;
        }
    }

    @Override
    public void triggerSsn(int n, int n2) {
    }
}

