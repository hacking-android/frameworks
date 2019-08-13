/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www.protocol.file;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilePermission;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.Permission;
import java.text.Collator;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import sun.net.ProgressMonitor;
import sun.net.ProgressSource;
import sun.net.www.MessageHeader;
import sun.net.www.MeteredStream;
import sun.net.www.ParseUtil;
import sun.net.www.URLConnection;

public class FileURLConnection
extends URLConnection {
    static String CONTENT_LENGTH = "content-length";
    static String CONTENT_TYPE = "content-type";
    static String LAST_MODIFIED;
    static String TEXT_PLAIN;
    String contentType;
    boolean exists = false;
    File file;
    String filename;
    List<String> files;
    private boolean initializedHeaders = false;
    InputStream is;
    boolean isDirectory = false;
    long lastModified = 0L;
    long length = -1L;
    Permission permission;

    static {
        TEXT_PLAIN = "text/plain";
        LAST_MODIFIED = "last-modified";
    }

    protected FileURLConnection(URL uRL, File file) {
        super(uRL);
        this.file = file;
    }

    private void initializeHeaders() {
        try {
            this.connect();
            this.exists = this.file.exists();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        if (!this.initializedHeaders || !this.exists) {
            this.length = this.file.length();
            this.lastModified = this.file.lastModified();
            if (!this.isDirectory) {
                this.contentType = java.net.URLConnection.getFileNameMap().getContentTypeFor(this.filename);
                if (this.contentType != null) {
                    this.properties.add(CONTENT_TYPE, this.contentType);
                }
                this.properties.add(CONTENT_LENGTH, String.valueOf(this.length));
                long l = this.lastModified;
                if (l != 0L) {
                    Date date = new Date(l);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                    simpleDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
                    this.properties.add(LAST_MODIFIED, simpleDateFormat.format(date));
                }
            } else {
                this.properties.add(CONTENT_TYPE, TEXT_PLAIN);
            }
            this.initializedHeaders = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void connect() throws IOException {
        if (this.connected) return;
        this.filename = this.file.toString();
        this.isDirectory = this.file.isDirectory();
        if (this.isDirectory) {
            String[] arrstring = this.file.list();
            if (arrstring == null) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.filename);
                stringBuilder.append(" exists, but is not accessible");
                FileNotFoundException fileNotFoundException = new FileNotFoundException(stringBuilder.toString());
                throw fileNotFoundException;
            }
            this.files = Arrays.asList(arrstring);
        } else {
            FileInputStream fileInputStream = new FileInputStream(this.filename);
            Object object = new BufferedInputStream(fileInputStream);
            this.is = object;
            if (ProgressMonitor.getDefault().shouldMeterInput(this.url, "GET")) {
                object = new ProgressSource(this.url, "GET", this.file.length());
                MeteredStream meteredStream = new MeteredStream(this.is, (ProgressSource)object, this.file.length());
                this.is = meteredStream;
            }
        }
        this.connected = true;
        return;
    }

    @Override
    public int getContentLength() {
        this.initializeHeaders();
        long l = this.length;
        if (l > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)l;
    }

    @Override
    public long getContentLengthLong() {
        this.initializeHeaders();
        return this.length;
    }

    @Override
    public String getHeaderField(int n) {
        this.initializeHeaders();
        return super.getHeaderField(n);
    }

    @Override
    public String getHeaderField(String string) {
        this.initializeHeaders();
        return super.getHeaderField(string);
    }

    @Override
    public String getHeaderFieldKey(int n) {
        this.initializeHeaders();
        return super.getHeaderFieldKey(n);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public InputStream getInputStream() throws IOException {
        synchronized (this) {
            this.connect();
            if (this.is != null) return this.is;
            if (!this.isDirectory) {
                FileNotFoundException fileNotFoundException = new FileNotFoundException(this.filename);
                throw fileNotFoundException;
            }
            java.net.URLConnection.getFileNameMap();
            Object object = new StringBuffer();
            if (this.files == null) {
                object = new FileNotFoundException(this.filename);
                throw object;
            }
            Collections.sort(this.files, Collator.getInstance());
            for (int i = 0; i < this.files.size(); ++i) {
                ((StringBuffer)object).append(this.files.get(i));
                ((StringBuffer)object).append("\n");
            }
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(((StringBuffer)object).toString().getBytes());
            this.is = byteArrayInputStream;
            return this.is;
        }
    }

    @Override
    public long getLastModified() {
        this.initializeHeaders();
        return this.lastModified;
    }

    @Override
    public Permission getPermission() throws IOException {
        if (this.permission == null) {
            String string = ParseUtil.decode(this.url.getPath());
            this.permission = File.separatorChar == '/' ? new FilePermission(string, "read") : new FilePermission(string.replace('/', File.separatorChar), "read");
        }
        return this.permission;
    }

    @Override
    public MessageHeader getProperties() {
        this.initializeHeaders();
        return super.getProperties();
    }
}

