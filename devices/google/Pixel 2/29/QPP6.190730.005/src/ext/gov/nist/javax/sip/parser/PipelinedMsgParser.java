/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.parser;

import gov.nist.javax.sip.parser.Pipeline;
import gov.nist.javax.sip.parser.SIPMessageListener;
import java.io.IOException;
import java.io.InputStream;

public final class PipelinedMsgParser
implements Runnable {
    private static int uid = 0;
    private int maxMessageSize;
    private Thread mythread;
    private Pipeline rawInputStream;
    protected SIPMessageListener sipMessageListener;
    private int sizeCounter;

    protected PipelinedMsgParser() {
    }

    public PipelinedMsgParser(Pipeline pipeline) {
        this(null, pipeline, false, 0);
    }

    public PipelinedMsgParser(SIPMessageListener sIPMessageListener, Pipeline pipeline, int n) {
        this(sIPMessageListener, pipeline, false, n);
    }

    public PipelinedMsgParser(SIPMessageListener object, Pipeline object2, boolean bl, int n) {
        this();
        this.sipMessageListener = object;
        this.rawInputStream = object2;
        this.maxMessageSize = n;
        this.mythread = new Thread(this);
        object2 = this.mythread;
        object = new StringBuilder();
        ((StringBuilder)object).append("PipelineThread-");
        ((StringBuilder)object).append(PipelinedMsgParser.getNewUid());
        ((Thread)object2).setName(((StringBuilder)object).toString());
    }

    private static int getNewUid() {
        synchronized (PipelinedMsgParser.class) {
            int n = uid;
            uid = n + 1;
            return n;
        }
    }

    private String readLine(InputStream inputStream) throws IOException {
        int n;
        StringBuffer stringBuffer = new StringBuffer("");
        while ((n = inputStream.read()) != -1) {
            char c = (char)n;
            if (this.maxMessageSize > 0) {
                --this.sizeCounter;
                if (this.sizeCounter <= 0) {
                    throw new IOException("Max size exceeded!");
                }
            }
            if (c != '\r') {
                stringBuffer.append(c);
            }
            if (c != '\n') continue;
            return stringBuffer.toString();
        }
        throw new IOException("End of stream");
    }

    protected Object clone() {
        PipelinedMsgParser pipelinedMsgParser = new PipelinedMsgParser();
        pipelinedMsgParser.rawInputStream = this.rawInputStream;
        pipelinedMsgParser.sipMessageListener = this.sipMessageListener;
        new Thread(pipelinedMsgParser).setName("PipelineThread");
        return pipelinedMsgParser;
    }

    public void close() {
        try {
            this.rawInputStream.close();
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    public void processInput() {
        this.mythread.start();
    }

    /*
     * Exception decompiling
     */
    @Override
    public void run() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    public void setMessageListener(SIPMessageListener sIPMessageListener) {
        this.sipMessageListener = sIPMessageListener;
    }
}

