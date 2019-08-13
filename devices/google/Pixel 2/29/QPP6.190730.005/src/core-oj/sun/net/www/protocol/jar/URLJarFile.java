/*
 * Decompiled with CFR 0.145.
 */
package sun.net.www.protocol.jar;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.security.CodeSigner;
import java.security.PrivilegedExceptionAction;
import java.security.cert.Certificate;
import java.util.Map;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import sun.net.www.ParseUtil;

public class URLJarFile
extends JarFile {
    private static int BUF_SIZE = 2048;
    private URLJarFileCloseController closeController = null;
    private Attributes superAttr;
    private Map<String, Attributes> superEntries;
    private Manifest superMan;

    public URLJarFile(File file) throws IOException {
        this(file, null);
    }

    public URLJarFile(File file, URLJarFileCloseController uRLJarFileCloseController) throws IOException {
        super(file, true, 5);
        this.closeController = uRLJarFileCloseController;
    }

    private URLJarFile(URL uRL, URLJarFileCloseController uRLJarFileCloseController) throws IOException {
        super(ParseUtil.decode(uRL.getFile()));
        this.closeController = uRLJarFileCloseController;
    }

    static JarFile getJarFile(URL uRL) throws IOException {
        return URLJarFile.getJarFile(uRL, null);
    }

    static JarFile getJarFile(URL uRL, URLJarFileCloseController uRLJarFileCloseController) throws IOException {
        if (URLJarFile.isFileURL(uRL)) {
            return new URLJarFile(uRL, uRLJarFileCloseController);
        }
        return URLJarFile.retrieve(uRL, uRLJarFileCloseController);
    }

    private static boolean isFileURL(URL object) {
        return ((URL)object).getProtocol().equalsIgnoreCase("file") && ((object = ((URL)object).getHost()) == null || ((String)object).equals("") || ((String)object).equals("~") || ((String)object).equalsIgnoreCase("localhost"));
    }

    private boolean isSuperMan() throws IOException {
        synchronized (this) {
            if (this.superMan == null) {
                this.superMan = super.getManifest();
            }
            if (this.superMan != null) {
                this.superAttr = this.superMan.getMainAttributes();
                this.superEntries = this.superMan.getEntries();
                return true;
            }
            return false;
        }
    }

    private static JarFile retrieve(URL uRL) throws IOException {
        return URLJarFile.retrieve(uRL, null);
    }

    /*
     * Exception decompiling
     */
    private static JarFile retrieve(URL var0, URLJarFileCloseController var1_3) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 3 blocks at once
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

    @Override
    public void close() throws IOException {
        URLJarFileCloseController uRLJarFileCloseController = this.closeController;
        if (uRLJarFileCloseController != null) {
            uRLJarFileCloseController.close(this);
        }
        super.close();
    }

    @Override
    protected void finalize() throws IOException {
        this.close();
    }

    @Override
    public ZipEntry getEntry(String object) {
        if ((object = super.getEntry((String)object)) != null) {
            if (object instanceof JarEntry) {
                return new URLJarFileEntry((JarEntry)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(Object.super.getClass());
            stringBuilder.append(" returned unexpected entry type ");
            stringBuilder.append(object.getClass());
            throw new InternalError(stringBuilder.toString());
        }
        return null;
    }

    @Override
    public Manifest getManifest() throws IOException {
        if (!this.isSuperMan()) {
            return null;
        }
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().putAll((Map)this.superAttr.clone());
        if (this.superEntries != null) {
            Map<String, Attributes> map = manifest.getEntries();
            for (String string : this.superEntries.keySet()) {
                map.put(string, (Attributes)this.superEntries.get(string).clone());
            }
        }
        return manifest;
    }

    public static interface URLJarFileCloseController {
        public void close(JarFile var1);
    }

    private class URLJarFileEntry
    extends JarEntry {
        private JarEntry je;

        URLJarFileEntry(JarEntry jarEntry) {
            super(jarEntry);
            this.je = jarEntry;
        }

        @Override
        public Attributes getAttributes() throws IOException {
            Map map;
            if (URLJarFile.this.isSuperMan() && (map = URLJarFile.this.superEntries) != null && (map = (Attributes)map.get(this.getName())) != null) {
                return (Attributes)((Attributes)map).clone();
            }
            return null;
        }

        @Override
        public Certificate[] getCertificates() {
            Object object = this.je.getCertificates();
            object = object == null ? null : (Certificate[])object.clone();
            return object;
        }

        @Override
        public CodeSigner[] getCodeSigners() {
            Object object = this.je.getCodeSigners();
            object = object == null ? null : (CodeSigner[])object.clone();
            return object;
        }
    }

}

