/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.net.NetworkSecurityPolicy
 */
package java.util.logging;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Filter;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;
import java.util.logging.XMLFormatter;
import libcore.net.NetworkSecurityPolicy;

public class SocketHandler
extends StreamHandler {
    private String host;
    private int port;
    private Socket sock;

    public SocketHandler() throws IOException {
        this.sealed = false;
        this.configure();
        try {
            this.connect();
            this.sealed = true;
            return;
        }
        catch (IOException iOException) {
            PrintStream printStream = System.err;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SocketHandler: connect failed to ");
            stringBuilder.append(this.host);
            stringBuilder.append(":");
            stringBuilder.append(this.port);
            printStream.println(stringBuilder.toString());
            throw iOException;
        }
    }

    public SocketHandler(String string, int n) throws IOException {
        this.sealed = false;
        this.configure();
        this.sealed = true;
        this.port = n;
        this.host = string;
        this.connect();
    }

    private void configure() {
        LogManager logManager = LogManager.getLogManager();
        String string = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".level");
        this.setLevel(logManager.getLevelProperty(stringBuilder.toString(), Level.ALL));
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".filter");
        this.setFilter(logManager.getFilterProperty(stringBuilder.toString(), null));
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".formatter");
        this.setFormatter(logManager.getFormatterProperty(stringBuilder.toString(), new XMLFormatter()));
        try {
            stringBuilder = new StringBuilder();
            stringBuilder.append(string);
            stringBuilder.append(".encoding");
            this.setEncoding(logManager.getStringProperty(stringBuilder.toString(), null));
        }
        catch (Exception exception) {
            try {
                this.setEncoding(null);
            }
            catch (Exception exception2) {
                // empty catch block
            }
        }
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".port");
        this.port = logManager.getIntProperty(stringBuilder.toString(), 0);
        stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append(".host");
        this.host = logManager.getStringProperty(stringBuilder.toString(), null);
    }

    private void connect() throws IOException {
        if (this.port != 0) {
            if (this.host != null) {
                if (NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted()) {
                    this.sock = new Socket(this.host, this.port);
                    this.setOutputStream(new BufferedOutputStream(this.sock.getOutputStream()));
                    return;
                }
                throw new IOException("Cleartext traffic not permitted");
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Null host name: ");
            stringBuilder.append(this.host);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Bad port: ");
        stringBuilder.append(this.port);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    @Override
    public void close() throws SecurityException {
        synchronized (this) {
            block6 : {
                super.close();
                Socket socket = this.sock;
                if (socket == null) break block6;
                try {
                    this.sock.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            this.sock = null;
            return;
        }
    }

    @Override
    public void publish(LogRecord logRecord) {
        synchronized (this) {
            block4 : {
                boolean bl = this.isLoggable(logRecord);
                if (bl) break block4;
                return;
            }
            super.publish(logRecord);
            this.flush();
            return;
        }
    }
}

