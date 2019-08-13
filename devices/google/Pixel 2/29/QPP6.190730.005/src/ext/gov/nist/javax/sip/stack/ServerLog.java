/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.stack;

import gov.nist.core.ServerLogger;
import gov.nist.core.StackLogger;
import gov.nist.javax.sip.LogRecord;
import gov.nist.javax.sip.LogRecordFactory;
import gov.nist.javax.sip.header.CallID;
import gov.nist.javax.sip.message.SIPMessage;
import gov.nist.javax.sip.stack.SIPTransactionStack;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Properties;
import javax.sip.SipStack;
import javax.sip.header.CallIdHeader;
import javax.sip.header.Header;
import javax.sip.header.TimeStampHeader;

public class ServerLog
implements ServerLogger {
    private String auxInfo;
    private Properties configurationProperties;
    private String description;
    private boolean logContent;
    private String logFileName;
    private PrintWriter printWriter;
    private SIPTransactionStack sipStack;
    private String stackIpAddress;
    protected StackLogger stackLogger;
    protected int traceLevel = 16;

    private void logMessage(String string) {
        this.checkLogFile();
        PrintWriter printWriter = this.printWriter;
        if (printWriter != null) {
            printWriter.println(string);
        }
        if (this.sipStack.isLoggingEnabled()) {
            this.stackLogger.logInfo(string);
        }
    }

    private void logMessage(String object, String string, String string2, boolean bl, String string3, String string4, String string5, String string6, long l, long l2) {
        if ((object = this.sipStack.logRecordFactory.createLogRecord((String)object, string, string2, l, bl, string4, string6, string3, l2)) != null) {
            this.logMessage(object.toString());
        }
    }

    private void setProperties(Properties object) {
        this.configurationProperties = object;
        this.description = ((Properties)object).getProperty("javax.sip.STACK_NAME");
        this.stackIpAddress = ((Properties)object).getProperty("javax.sip.IP_ADDRESS");
        this.logFileName = ((Properties)object).getProperty("gov.nist.javax.sip.SERVER_LOG");
        String string = ((Properties)object).getProperty("gov.nist.javax.sip.TRACE_LEVEL");
        boolean bl = (object = ((Properties)object).getProperty("gov.nist.javax.sip.LOG_MESSAGE_CONTENT")) != null && ((String)object).equals("true");
        this.logContent = bl;
        if (string != null && !string.equals("LOG4J")) {
            int n;
            block9 : {
                block11 : {
                    block10 : {
                        block8 : {
                            if (!string.equals("DEBUG")) break block8;
                            n = 32;
                            break block9;
                        }
                        if (!string.equals("INFO")) break block10;
                        n = 16;
                        break block9;
                    }
                    if (!string.equals("ERROR")) break block11;
                    n = 4;
                    break block9;
                }
                if (!string.equals("NONE") && !string.equals("OFF")) {
                    n = Integer.parseInt(string);
                    break block9;
                }
                n = 0;
            }
            try {
                this.setTraceLevel(n);
            }
            catch (NumberFormatException numberFormatException) {
                PrintStream printStream = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("ServerLog: WARNING Bad integer ");
                stringBuilder.append(string);
                printStream.println(stringBuilder.toString());
                System.out.println("logging dislabled ");
                this.setTraceLevel(0);
            }
        }
        this.checkLogFile();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void checkLogFile() {
        Object object = this.logFileName;
        if (object == null) return;
        if (this.traceLevel < 16) {
            return;
        }
        try {
            Object object2 = new File((String)object);
            if (!((File)object2).exists()) {
                ((File)object2).createNewFile();
                this.printWriter = null;
            }
            if (this.printWriter != null) return;
            boolean bl = Boolean.valueOf(this.configurationProperties.getProperty("gov.nist.javax.sip.SERVER_LOG_OVERWRITE"));
            object2 = this.logFileName;
            bl = !bl;
            object = new FileWriter((String)object2, bl);
            object = this.printWriter = (object2 = new PrintWriter((Writer)object, true));
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("<!-- Use the  Trace Viewer in src/tools/tracesviewer to view this  trace  \nHere are the stack configuration properties \njavax.sip.IP_ADDRESS= ");
            ((StringBuilder)object2).append(this.configurationProperties.getProperty("javax.sip.IP_ADDRESS"));
            ((StringBuilder)object2).append("\njavax.sip.STACK_NAME= ");
            ((StringBuilder)object2).append(this.configurationProperties.getProperty("javax.sip.STACK_NAME"));
            ((StringBuilder)object2).append("\njavax.sip.ROUTER_PATH= ");
            ((StringBuilder)object2).append(this.configurationProperties.getProperty("javax.sip.ROUTER_PATH"));
            ((StringBuilder)object2).append("\njavax.sip.OUTBOUND_PROXY= ");
            ((StringBuilder)object2).append(this.configurationProperties.getProperty("javax.sip.OUTBOUND_PROXY"));
            ((StringBuilder)object2).append("\n-->");
            ((PrintWriter)object).println(((StringBuilder)object2).toString());
            object2 = this.printWriter;
            object = new StringBuilder();
            ((StringBuilder)object).append("<description\n logDescription=\"");
            ((StringBuilder)object).append(this.description);
            ((StringBuilder)object).append("\"\n name=\"");
            ((StringBuilder)object).append(this.configurationProperties.getProperty("javax.sip.STACK_NAME"));
            ((StringBuilder)object).append("\"\n auxInfo=\"");
            ((StringBuilder)object).append(this.auxInfo);
            ((StringBuilder)object).append("\"/>\n ");
            ((PrintWriter)object2).println(((StringBuilder)object).toString());
            object = this.auxInfo;
            if (object != null) {
                if (!this.sipStack.isLoggingEnabled()) return;
                object = this.stackLogger;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Here are the stack configuration properties \njavax.sip.IP_ADDRESS= ");
                ((StringBuilder)object2).append(this.configurationProperties.getProperty("javax.sip.IP_ADDRESS"));
                ((StringBuilder)object2).append("\njavax.sip.ROUTER_PATH= ");
                ((StringBuilder)object2).append(this.configurationProperties.getProperty("javax.sip.ROUTER_PATH"));
                ((StringBuilder)object2).append("\njavax.sip.OUTBOUND_PROXY= ");
                ((StringBuilder)object2).append(this.configurationProperties.getProperty("javax.sip.OUTBOUND_PROXY"));
                ((StringBuilder)object2).append("\ngov.nist.javax.sip.CACHE_CLIENT_CONNECTIONS= ");
                ((StringBuilder)object2).append(this.configurationProperties.getProperty("gov.nist.javax.sip.CACHE_CLIENT_CONNECTIONS"));
                ((StringBuilder)object2).append("\ngov.nist.javax.sip.CACHE_SERVER_CONNECTIONS= ");
                ((StringBuilder)object2).append(this.configurationProperties.getProperty("gov.nist.javax.sip.CACHE_SERVER_CONNECTIONS"));
                ((StringBuilder)object2).append("\ngov.nist.javax.sip.REENTRANT_LISTENER= ");
                ((StringBuilder)object2).append(this.configurationProperties.getProperty("gov.nist.javax.sip.REENTRANT_LISTENER"));
                ((StringBuilder)object2).append("gov.nist.javax.sip.THREAD_POOL_SIZE= ");
                ((StringBuilder)object2).append(this.configurationProperties.getProperty("gov.nist.javax.sip.THREAD_POOL_SIZE"));
                ((StringBuilder)object2).append("\n");
                object.logDebug(((StringBuilder)object2).toString());
                this.stackLogger.logDebug(" ]]> ");
                this.stackLogger.logDebug("</debug>");
                object = this.stackLogger;
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("<description\n logDescription=\"");
                ((StringBuilder)object2).append(this.description);
                ((StringBuilder)object2).append("\"\n name=\"");
                ((StringBuilder)object2).append(this.stackIpAddress);
                ((StringBuilder)object2).append("\"\n auxInfo=\"");
                ((StringBuilder)object2).append(this.auxInfo);
                ((StringBuilder)object2).append("\"/>\n ");
                object.logDebug(((StringBuilder)object2).toString());
                this.stackLogger.logDebug("<debug>");
                this.stackLogger.logDebug("<![CDATA[ ");
                return;
            }
            if (!this.sipStack.isLoggingEnabled()) return;
            object = this.stackLogger;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Here are the stack configuration properties \n");
            ((StringBuilder)object2).append(this.configurationProperties);
            ((StringBuilder)object2).append("\n");
            object.logDebug(((StringBuilder)object2).toString());
            this.stackLogger.logDebug(" ]]>");
            this.stackLogger.logDebug("</debug>");
            object2 = this.stackLogger;
            object = new StringBuilder();
            ((StringBuilder)object).append("<description\n logDescription=\"");
            ((StringBuilder)object).append(this.description);
            ((StringBuilder)object).append("\"\n name=\"");
            ((StringBuilder)object).append(this.stackIpAddress);
            ((StringBuilder)object).append("\" />\n");
            object2.logDebug(((StringBuilder)object).toString());
            this.stackLogger.logDebug("<debug>");
            this.stackLogger.logDebug("<![CDATA[ ");
            return;
        }
        catch (IOException iOException) {
            // empty catch block
        }
    }

    @Override
    public void closeLogFile() {
        synchronized (this) {
            if (this.printWriter != null) {
                this.printWriter.close();
                this.printWriter = null;
            }
            return;
        }
    }

    public String getLogFileName() {
        return this.logFileName;
    }

    public int getTraceLevel() {
        return this.traceLevel;
    }

    @Override
    public void logException(Exception exception) {
        if (this.traceLevel >= 4) {
            this.checkLogFile();
            exception.printStackTrace();
            PrintWriter printWriter = this.printWriter;
            if (printWriter != null) {
                exception.printStackTrace(printWriter);
            }
        }
    }

    @Override
    public void logMessage(SIPMessage sIPMessage, String string, String string2, String string3, boolean bl) {
        this.logMessage(sIPMessage, string, string2, string3, bl, System.currentTimeMillis());
    }

    @Override
    public void logMessage(SIPMessage cloneable, String string, String string2, String string3, boolean bl, long l) {
        this.checkLogFile();
        Object object = (CallID)((SIPMessage)cloneable).getCallId();
        String string4 = null;
        if (object != null) {
            string4 = ((CallID)object).getCallId();
        }
        String string5 = ((SIPMessage)cloneable).getFirstLine().trim();
        object = this.logContent ? ((SIPMessage)cloneable).encode() : ((SIPMessage)cloneable).encodeMessage();
        String string6 = ((SIPMessage)cloneable).getTransactionId();
        cloneable = (TimeStampHeader)((SIPMessage)cloneable).getHeader("Timestamp");
        long l2 = cloneable == null ? 0L : cloneable.getTime();
        this.logMessage((String)object, string, string2, bl, string4, string5, string3, string6, l, l2);
    }

    @Override
    public void logMessage(SIPMessage cloneable, String string, String string2, boolean bl, long l) {
        this.checkLogFile();
        if (((SIPMessage)cloneable).getFirstLine() == null) {
            return;
        }
        Object object = (CallID)((SIPMessage)cloneable).getCallId();
        String string3 = null;
        if (object != null) {
            string3 = ((CallID)object).getCallId();
        }
        String string4 = ((SIPMessage)cloneable).getFirstLine().trim();
        object = this.logContent ? ((SIPMessage)cloneable).encode() : ((SIPMessage)cloneable).encodeMessage();
        String string5 = ((SIPMessage)cloneable).getTransactionId();
        cloneable = (TimeStampHeader)((SIPMessage)cloneable).getHeader("Timestamp");
        long l2 = cloneable == null ? 0L : cloneable.getTime();
        this.logMessage((String)object, string, string2, bl, string3, string4, null, string5, l, l2);
    }

    public boolean needsLogging() {
        boolean bl = this.logFileName != null;
        return bl;
    }

    public void setAuxInfo(String string) {
        this.auxInfo = string;
    }

    public void setLevel(int n) {
    }

    public void setLogFileName(String string) {
        this.logFileName = string;
    }

    @Override
    public void setSipStack(SipStack sipStack) {
        if (sipStack instanceof SIPTransactionStack) {
            this.sipStack = (SIPTransactionStack)((Object)sipStack);
            this.stackLogger = this.sipStack.getStackLogger();
            return;
        }
        throw new IllegalArgumentException("sipStack must be a SIPTransactionStack");
    }

    public void setStackIpAddress(String string) {
        this.stackIpAddress = string;
    }

    @Override
    public void setStackProperties(Properties properties) {
        this.setProperties(properties);
    }

    public void setTraceLevel(int n) {
        this.traceLevel = n;
    }
}

