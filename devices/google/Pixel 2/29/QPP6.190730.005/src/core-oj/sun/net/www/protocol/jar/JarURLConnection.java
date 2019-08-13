/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www.protocol.jar;

import java.io.BufferedInputStream;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import sun.net.www.protocol.jar.Handler;
import sun.net.www.protocol.jar.JarFileFactory;

public class JarURLConnection
extends java.net.JarURLConnection {
    private static final boolean debug = false;
    private static final JarFileFactory factory = JarFileFactory.getInstance();
    private String contentType;
    private String entryName = this.getEntryName();
    private JarEntry jarEntry;
    private JarFile jarFile;
    private URL jarFileURL = this.getJarFileURL();
    private URLConnection jarFileURLConnection = this.jarFileURL.openConnection();
    private Permission permission;

    public JarURLConnection(URL uRL, Handler handler) throws MalformedURLException, IOException {
        super(uRL);
    }

    @Override
    public void addRequestProperty(String string, String string2) {
        this.jarFileURLConnection.addRequestProperty(string, string2);
    }

    @Override
    public void connect() throws IOException {
        if (!this.connected) {
            CharSequence charSequence;
            this.jarFile = factory.get(this.getJarFileURL(), this.getUseCaches());
            if (this.getUseCaches()) {
                boolean bl = this.jarFileURLConnection.getUseCaches();
                this.jarFileURLConnection = factory.getConnection(this.jarFile);
                this.jarFileURLConnection.setUseCaches(bl);
            }
            if ((charSequence = this.entryName) != null) {
                this.jarEntry = (JarEntry)this.jarFile.getEntry((String)charSequence);
                if (this.jarEntry == null) {
                    try {
                        if (!this.getUseCaches()) {
                            this.jarFile.close();
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("JAR entry ");
                    ((StringBuilder)charSequence).append(this.entryName);
                    ((StringBuilder)charSequence).append(" not found in ");
                    ((StringBuilder)charSequence).append(this.jarFile.getName());
                    throw new FileNotFoundException(((StringBuilder)charSequence).toString());
                }
            }
            this.connected = true;
        }
    }

    @Override
    public boolean getAllowUserInteraction() {
        return this.jarFileURLConnection.getAllowUserInteraction();
    }

    @Override
    public Object getContent() throws IOException {
        this.connect();
        Object object = this.entryName == null ? this.jarFile : super.getContent();
        return object;
    }

    @Override
    public int getContentLength() {
        long l = this.getContentLengthLong();
        if (l > Integer.MAX_VALUE) {
            return -1;
        }
        return (int)l;
    }

    @Override
    public long getContentLengthLong() {
        long l;
        block3 : {
            long l2;
            l = -1L;
            this.connect();
            if (this.jarEntry != null) break block3;
            l = l2 = this.jarFileURLConnection.getContentLengthLong();
        }
        try {
            long l3;
            l = l3 = this.getJarEntry().getSize();
        }
        catch (IOException iOException) {
            // empty catch block
        }
        return l;
    }

    @Override
    public String getContentType() {
        if (this.contentType == null) {
            if (this.entryName == null) {
                this.contentType = "x-java/jar";
            } else {
                try {
                    this.connect();
                    InputStream inputStream = this.jarFile.getInputStream(this.jarEntry);
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                    this.contentType = JarURLConnection.guessContentTypeFromStream(bufferedInputStream);
                    inputStream.close();
                }
                catch (IOException iOException) {
                    // empty catch block
                }
            }
            if (this.contentType == null) {
                this.contentType = JarURLConnection.guessContentTypeFromName(this.entryName);
            }
            if (this.contentType == null) {
                this.contentType = "content/unknown";
            }
        }
        return this.contentType;
    }

    @Override
    public boolean getDefaultUseCaches() {
        return this.jarFileURLConnection.getDefaultUseCaches();
    }

    @Override
    public String getHeaderField(String string) {
        return this.jarFileURLConnection.getHeaderField(string);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        this.connect();
        if (this.entryName != null) {
            Object object = this.jarEntry;
            if (object != null) {
                return new JarURLInputStream(this.jarFile.getInputStream((ZipEntry)object));
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("JAR entry ");
            ((StringBuilder)object).append(this.entryName);
            ((StringBuilder)object).append(" not found in ");
            ((StringBuilder)object).append(this.jarFile.getName());
            throw new FileNotFoundException(((StringBuilder)object).toString());
        }
        throw new IOException("no entry name specified");
    }

    @Override
    public JarEntry getJarEntry() throws IOException {
        this.connect();
        return this.jarEntry;
    }

    @Override
    public JarFile getJarFile() throws IOException {
        this.connect();
        return this.jarFile;
    }

    @Override
    public Permission getPermission() throws IOException {
        return this.jarFileURLConnection.getPermission();
    }

    @Override
    public Map<String, List<String>> getRequestProperties() {
        return this.jarFileURLConnection.getRequestProperties();
    }

    @Override
    public String getRequestProperty(String string) {
        return this.jarFileURLConnection.getRequestProperty(string);
    }

    @Override
    public boolean getUseCaches() {
        return this.jarFileURLConnection.getUseCaches();
    }

    @Override
    public void setAllowUserInteraction(boolean bl) {
        this.jarFileURLConnection.setAllowUserInteraction(bl);
    }

    @Override
    public void setDefaultUseCaches(boolean bl) {
        this.jarFileURLConnection.setDefaultUseCaches(bl);
    }

    @Override
    public void setIfModifiedSince(long l) {
        this.jarFileURLConnection.setIfModifiedSince(l);
    }

    @Override
    public void setRequestProperty(String string, String string2) {
        this.jarFileURLConnection.setRequestProperty(string, string2);
    }

    @Override
    public void setUseCaches(boolean bl) {
        this.jarFileURLConnection.setUseCaches(bl);
    }

    class JarURLInputStream
    extends FilterInputStream {
        JarURLInputStream(InputStream inputStream) {
            super(inputStream);
        }

        @Override
        public void close() throws IOException {
            try {
                super.close();
                return;
            }
            finally {
                if (!JarURLConnection.this.getUseCaches()) {
                    JarURLConnection.this.jarFile.close();
                }
            }
        }
    }

}

