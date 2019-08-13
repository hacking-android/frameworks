/*
 * Decompiled with CFR 0.145.
 */
package libcore.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;
import sun.net.www.ParseUtil;
import sun.net.www.protocol.jar.Handler;

public class ClassPathURLStreamHandler
extends Handler {
    private final String fileUri;
    private final JarFile jarFile;

    public ClassPathURLStreamHandler(String string) throws IOException {
        this.jarFile = new JarFile(string);
        this.fileUri = new File(string).toURI().toString();
    }

    public void close() throws IOException {
        this.jarFile.close();
    }

    public URL getEntryUrlOrNull(String object) {
        if (this.jarFile.getEntry((String)object) != null) {
            try {
                String string = ParseUtil.encodePath((String)object, false);
                object = new StringBuilder();
                ((StringBuilder)object).append(this.fileUri);
                ((StringBuilder)object).append("!/");
                ((StringBuilder)object).append(string);
                object = new URL("jar", null, -1, ((StringBuilder)object).toString(), this);
                return object;
            }
            catch (MalformedURLException malformedURLException) {
                throw new RuntimeException("Invalid entry name", malformedURLException);
            }
        }
        return null;
    }

    public boolean isEntryStored(String object) {
        boolean bl = (object = this.jarFile.getEntry((String)object)) != null && ((ZipEntry)object).getMethod() == 0;
        return bl;
    }

    @Override
    protected URLConnection openConnection(URL uRL) throws IOException {
        return new ClassPathURLConnection(uRL);
    }

    private class ClassPathURLConnection
    extends JarURLConnection {
        private boolean closed;
        private JarFile connectionJarFile;
        private ZipEntry jarEntry;
        private InputStream jarInput;
        private boolean useCachedJarFile;

        public ClassPathURLConnection(URL uRL) throws MalformedURLException {
            super(uRL);
        }

        @Override
        public void connect() throws IOException {
            if (!this.connected) {
                this.jarEntry = ClassPathURLStreamHandler.this.jarFile.getEntry(this.getEntryName());
                if (this.jarEntry != null) {
                    this.useCachedJarFile = this.getUseCaches();
                    this.connected = true;
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("URL does not correspond to an entry in the zip file. URL=");
                    stringBuilder.append(this.url);
                    stringBuilder.append(", zipfile=");
                    stringBuilder.append(ClassPathURLStreamHandler.this.jarFile.getName());
                    throw new FileNotFoundException(stringBuilder.toString());
                }
            }
        }

        @Override
        public int getContentLength() {
            long l;
            try {
                this.connect();
                l = this.getJarEntry().getSize();
            }
            catch (IOException iOException) {
                return -1;
            }
            return (int)l;
        }

        @Override
        public String getContentType() {
            String string;
            String string2 = string = ClassPathURLConnection.guessContentTypeFromName(this.getEntryName());
            if (string == null) {
                string2 = "content/unknown";
            }
            return string2;
        }

        @Override
        public InputStream getInputStream() throws IOException {
            if (!this.closed) {
                this.connect();
                InputStream inputStream = this.jarInput;
                if (inputStream != null) {
                    return inputStream;
                }
                this.jarInput = inputStream = new FilterInputStream(ClassPathURLStreamHandler.this.jarFile.getInputStream(this.jarEntry)){

                    @Override
                    public void close() throws IOException {
                        super.close();
                        if (ClassPathURLConnection.this.connectionJarFile != null && !ClassPathURLConnection.this.useCachedJarFile) {
                            ClassPathURLConnection.this.connectionJarFile.close();
                            ClassPathURLConnection.this.closed = true;
                        }
                    }
                };
                return inputStream;
            }
            throw new IllegalStateException("JarURLConnection InputStream has been closed");
        }

        @Override
        public JarFile getJarFile() throws IOException {
            this.connect();
            this.connectionJarFile = this.useCachedJarFile ? ClassPathURLStreamHandler.this.jarFile : new JarFile(ClassPathURLStreamHandler.this.jarFile.getName());
            return this.connectionJarFile;
        }

    }

}

