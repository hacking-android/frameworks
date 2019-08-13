/*
 * Decompiled with CFR 0.145.
 */
package java.lang;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.security.Permission;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class ProcessBuilder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private List<String> command;
    private File directory;
    private Map<String, String> environment;
    private boolean redirectErrorStream;
    private Redirect[] redirects;

    public ProcessBuilder(List<String> list) {
        if (list != null) {
            this.command = list;
            return;
        }
        throw new NullPointerException();
    }

    public ProcessBuilder(String ... arrstring) {
        this.command = new ArrayList<String>(arrstring.length);
        for (String string : arrstring) {
            this.command.add(string);
        }
    }

    private Redirect[] redirects() {
        if (this.redirects == null) {
            this.redirects = new Redirect[]{Redirect.PIPE, Redirect.PIPE, Redirect.PIPE};
        }
        return this.redirects;
    }

    public ProcessBuilder command(List<String> list) {
        if (list != null) {
            this.command = list;
            return this;
        }
        throw new NullPointerException();
    }

    public ProcessBuilder command(String ... arrstring) {
        this.command = new ArrayList<String>(arrstring.length);
        for (String string : arrstring) {
            this.command.add(string);
        }
        return this;
    }

    public List<String> command() {
        return this.command;
    }

    public File directory() {
        return this.directory;
    }

    public ProcessBuilder directory(File file) {
        this.directory = file;
        return this;
    }

    ProcessBuilder environment(String[] arrstring) {
        if (arrstring != null) {
            this.environment = ProcessEnvironment.emptyEnvironment(arrstring.length);
            int n = arrstring.length;
            for (int i = 0; i < n; ++i) {
                int n2;
                String string;
                String string2 = string = arrstring[i];
                if (string.indexOf(0) != -1) {
                    string2 = string.replaceFirst("\u0000.*", "");
                }
                if ((n2 = string2.indexOf(61, 0)) == -1) continue;
                this.environment.put(string2.substring(0, n2), string2.substring(n2 + 1));
            }
        }
        return this;
    }

    public Map<String, String> environment() {
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkPermission(new RuntimePermission("getenv.*"));
        }
        if (this.environment == null) {
            this.environment = ProcessEnvironment.environment();
        }
        return this.environment;
    }

    public ProcessBuilder inheritIO() {
        Arrays.fill(this.redirects(), Redirect.INHERIT);
        return this;
    }

    public Redirect redirectError() {
        Object object = this.redirects;
        object = object == null ? Redirect.PIPE : object[2];
        return object;
    }

    public ProcessBuilder redirectError(File file) {
        return this.redirectError(Redirect.to(file));
    }

    public ProcessBuilder redirectError(Redirect redirect) {
        if (redirect.type() != Redirect.Type.READ) {
            this.redirects()[2] = redirect;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Redirect invalid for writing: ");
        stringBuilder.append(redirect);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public ProcessBuilder redirectErrorStream(boolean bl) {
        this.redirectErrorStream = bl;
        return this;
    }

    public boolean redirectErrorStream() {
        return this.redirectErrorStream;
    }

    public Redirect redirectInput() {
        Object object = this.redirects;
        object = object == null ? Redirect.PIPE : object[0];
        return object;
    }

    public ProcessBuilder redirectInput(File file) {
        return this.redirectInput(Redirect.from(file));
    }

    public ProcessBuilder redirectInput(Redirect redirect) {
        if (redirect.type() != Redirect.Type.WRITE && redirect.type() != Redirect.Type.APPEND) {
            this.redirects()[0] = redirect;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Redirect invalid for reading: ");
        stringBuilder.append(redirect);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Redirect redirectOutput() {
        Object object = this.redirects;
        object = object == null ? Redirect.PIPE : object[1];
        return object;
    }

    public ProcessBuilder redirectOutput(File file) {
        return this.redirectOutput(Redirect.to(file));
    }

    public ProcessBuilder redirectOutput(Redirect redirect) {
        if (redirect.type() != Redirect.Type.READ) {
            this.redirects()[1] = redirect;
            return this;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Redirect invalid for writing: ");
        stringBuilder.append(redirect);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public Process start() throws IOException {
        int n;
        Object object = this.command;
        Object object2 = (String[])object.toArray(new String[object.size()]).clone();
        int n2 = ((String[])object2).length;
        for (n = 0; n < n2; ++n) {
            if (object2[n] != null) {
                continue;
            }
            throw new NullPointerException();
        }
        String string = object2[0];
        SecurityManager securityManager = System.getSecurityManager();
        if (securityManager != null) {
            securityManager.checkExec(string);
        }
        object = (object = this.directory) == null ? null : ((File)object).toString();
        for (n = 1; n < ((String[])object2).length; ++n) {
            if (object2[n].indexOf(0) < 0) {
                continue;
            }
            throw new IOException("invalid null character in command");
        }
        try {
            object2 = ProcessImpl.start((String[])object2, this.environment, (String)object, this.redirects, this.redirectErrorStream);
            return object2;
        }
        catch (IOException | IllegalArgumentException exception) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(": ");
            ((StringBuilder)object2).append(exception.getMessage());
            String string2 = ((StringBuilder)object2).toString();
            Serializable serializable = exception;
            String string3 = string2;
            object2 = serializable;
            if (exception instanceof IOException) {
                string3 = string2;
                object2 = serializable;
                if (securityManager != null) {
                    try {
                        securityManager.checkRead(string);
                        string3 = string2;
                        object2 = serializable;
                    }
                    catch (SecurityException securityException) {
                        string3 = "";
                    }
                }
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Cannot run program \"");
            ((StringBuilder)serializable).append(string);
            ((StringBuilder)serializable).append("\"");
            if (object == null) {
                object = "";
            } else {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(" (in directory \"");
                stringBuilder.append((String)object);
                stringBuilder.append("\")");
                object = stringBuilder.toString();
            }
            ((StringBuilder)serializable).append((String)object);
            ((StringBuilder)serializable).append(string3);
            throw new IOException(((StringBuilder)serializable).toString(), (Throwable)object2);
        }
    }

    static class NullInputStream
    extends InputStream {
        static final NullInputStream INSTANCE = new NullInputStream();

        private NullInputStream() {
        }

        @Override
        public int available() {
            return 0;
        }

        @Override
        public int read() {
            return -1;
        }
    }

    static class NullOutputStream
    extends OutputStream {
        static final NullOutputStream INSTANCE = new NullOutputStream();

        private NullOutputStream() {
        }

        @Override
        public void write(int n) throws IOException {
            throw new IOException("Stream closed");
        }
    }

    public static abstract class Redirect {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        public static final Redirect INHERIT;
        public static final Redirect PIPE;

        static {
            PIPE = new Redirect(){

                public String toString() {
                    return this.type().toString();
                }

                @Override
                public Type type() {
                    return Type.PIPE;
                }
            };
            INHERIT = new Redirect(){

                public String toString() {
                    return this.type().toString();
                }

                @Override
                public Type type() {
                    return Type.INHERIT;
                }
            };
        }

        private Redirect() {
        }

        public static Redirect appendTo(final File file) {
            if (file != null) {
                return new Redirect(){

                    @Override
                    boolean append() {
                        return true;
                    }

                    @Override
                    public File file() {
                        return file;
                    }

                    public String toString() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("redirect to append to file \"");
                        stringBuilder.append(file);
                        stringBuilder.append("\"");
                        return stringBuilder.toString();
                    }

                    @Override
                    public Type type() {
                        return Type.APPEND;
                    }
                };
            }
            throw new NullPointerException();
        }

        public static Redirect from(final File file) {
            if (file != null) {
                return new Redirect(){

                    @Override
                    public File file() {
                        return file;
                    }

                    public String toString() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("redirect to read from file \"");
                        stringBuilder.append(file);
                        stringBuilder.append("\"");
                        return stringBuilder.toString();
                    }

                    @Override
                    public Type type() {
                        return Type.READ;
                    }
                };
            }
            throw new NullPointerException();
        }

        public static Redirect to(final File file) {
            if (file != null) {
                return new Redirect(){

                    @Override
                    boolean append() {
                        return false;
                    }

                    @Override
                    public File file() {
                        return file;
                    }

                    public String toString() {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("redirect to write to file \"");
                        stringBuilder.append(file);
                        stringBuilder.append("\"");
                        return stringBuilder.toString();
                    }

                    @Override
                    public Type type() {
                        return Type.WRITE;
                    }
                };
            }
            throw new NullPointerException();
        }

        boolean append() {
            throw new UnsupportedOperationException();
        }

        public boolean equals(Object object) {
            if (object == this) {
                return true;
            }
            if (!(object instanceof Redirect)) {
                return false;
            }
            if (((Redirect)(object = (Redirect)object)).type() != this.type()) {
                return false;
            }
            return this.file().equals(((Redirect)object).file());
        }

        public File file() {
            return null;
        }

        public int hashCode() {
            File file = this.file();
            if (file == null) {
                return super.hashCode();
            }
            return file.hashCode();
        }

        public abstract Type type();

        public static enum Type {
            PIPE,
            INHERIT,
            READ,
            WRITE,
            APPEND;
            
        }

    }

}

