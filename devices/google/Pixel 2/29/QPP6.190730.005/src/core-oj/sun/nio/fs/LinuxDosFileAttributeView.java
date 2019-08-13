/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.DosFileAttributeView;
import java.nio.file.attribute.DosFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Map;
import java.util.Set;
import sun.misc.Unsafe;
import sun.nio.fs.AbstractBasicFileAttributeView;
import sun.nio.fs.LinuxNativeDispatcher;
import sun.nio.fs.NativeBuffer;
import sun.nio.fs.NativeBuffers;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributeViews;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;
import sun.nio.fs.Util;

class LinuxDosFileAttributeView
extends UnixFileAttributeViews.Basic
implements DosFileAttributeView {
    private static final String ARCHIVE_NAME = "archive";
    private static final int DOS_XATTR_ARCHIVE = 32;
    private static final int DOS_XATTR_HIDDEN = 2;
    private static final String DOS_XATTR_NAME = "user.DOSATTRIB";
    private static final byte[] DOS_XATTR_NAME_AS_BYTES;
    private static final int DOS_XATTR_READONLY = 1;
    private static final int DOS_XATTR_SYSTEM = 4;
    private static final String HIDDEN_NAME = "hidden";
    private static final String READONLY_NAME = "readonly";
    private static final String SYSTEM_NAME = "system";
    private static final Set<String> dosAttributeNames;
    private static final Unsafe unsafe;

    static {
        unsafe = Unsafe.getUnsafe();
        DOS_XATTR_NAME_AS_BYTES = Util.toBytes(DOS_XATTR_NAME);
        dosAttributeNames = Util.newSet(basicAttributeNames, new String[]{READONLY_NAME, ARCHIVE_NAME, SYSTEM_NAME, HIDDEN_NAME});
    }

    LinuxDosFileAttributeView(UnixPath unixPath, boolean bl) {
        super(unixPath, bl);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private int getDosAttribute(int n) throws UnixException {
        Throwable throwable22;
        NativeBuffer nativeBuffer;
        block13 : {
            int n2;
            Object object;
            block11 : {
                boolean bl;
                block12 : {
                    nativeBuffer = NativeBuffers.getNativeBuffer(24);
                    n2 = LinuxNativeDispatcher.fgetxattr(n, DOS_XATTR_NAME_AS_BYTES, nativeBuffer.address(), 24);
                    if (n2 <= 0) break block11;
                    n = n2;
                    if (unsafe.getByte(nativeBuffer.address() + (long)n2 - 1L) != 0) break block12;
                    n = n2 - 1;
                }
                object = new byte[n];
                for (n2 = 0; n2 < n; ++n2) {
                    object[n2] = unsafe.getByte(nativeBuffer.address() + (long)n2);
                }
                object = Util.toString((byte[])object);
                if (((String)object).length() < 3 || !(bl = ((String)object).startsWith("0x"))) break block11;
                try {
                    n = Integer.parseInt(((String)object).substring(2), 16);
                }
                catch (NumberFormatException numberFormatException) {
                    // empty catch block
                }
                nativeBuffer.release();
                return n;
            }
            object = new UnixException("Value of user.DOSATTRIB attribute is invalid");
            throw object;
            {
                catch (Throwable throwable22) {
                    break block13;
                }
                catch (UnixException unixException) {}
                n2 = unixException.errno();
                n = UnixConstants.ENODATA;
                if (n2 != n) throw unixException;
                nativeBuffer.release();
                return 0;
            }
        }
        nativeBuffer.release();
        throw throwable22;
    }

    /*
     * Exception decompiling
     */
    private void updateDosAttribute(int var1_1, boolean var2_2) throws IOException {
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

    @Override
    public String name() {
        return "dos";
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @Override
    public DosFileAttributes readAttributes() throws IOException {
        Throwable throwable2222;
        this.file.checkRead();
        int n = this.file.openForAttributeAccess(this.followLinks);
        DosFileAttributes dosFileAttributes = new DosFileAttributes(UnixFileAttributes.get(n), this.getDosAttribute(n)){
            final /* synthetic */ UnixFileAttributes val$attrs;
            final /* synthetic */ int val$dosAttribute;
            {
                this.val$attrs = unixFileAttributes;
                this.val$dosAttribute = n;
            }

            @Override
            public FileTime creationTime() {
                return this.val$attrs.creationTime();
            }

            @Override
            public Object fileKey() {
                return this.val$attrs.fileKey();
            }

            @Override
            public boolean isArchive() {
                boolean bl = (this.val$dosAttribute & 32) != 0;
                return bl;
            }

            @Override
            public boolean isDirectory() {
                return this.val$attrs.isDirectory();
            }

            @Override
            public boolean isHidden() {
                boolean bl = (this.val$dosAttribute & 2) != 0;
                return bl;
            }

            @Override
            public boolean isOther() {
                return this.val$attrs.isOther();
            }

            @Override
            public boolean isReadOnly() {
                int n = this.val$dosAttribute;
                boolean bl = true;
                if ((n & 1) == 0) {
                    bl = false;
                }
                return bl;
            }

            @Override
            public boolean isRegularFile() {
                return this.val$attrs.isRegularFile();
            }

            @Override
            public boolean isSymbolicLink() {
                return this.val$attrs.isSymbolicLink();
            }

            @Override
            public boolean isSystem() {
                boolean bl = (this.val$dosAttribute & 4) != 0;
                return bl;
            }

            @Override
            public FileTime lastAccessTime() {
                return this.val$attrs.lastAccessTime();
            }

            @Override
            public FileTime lastModifiedTime() {
                return this.val$attrs.lastModifiedTime();
            }

            @Override
            public long size() {
                return this.val$attrs.size();
            }
        };
        UnixNativeDispatcher.close(n);
        return dosFileAttributes;
        {
            catch (Throwable throwable2222) {
            }
            catch (UnixException unixException) {}
            {
                unixException.rethrowAsIOException(this.file);
            }
            UnixNativeDispatcher.close(n);
            return null;
        }
        UnixNativeDispatcher.close(n);
        throw throwable2222;
    }

    @Override
    public Map<String, Object> readAttributes(String[] object) throws IOException {
        object = AbstractBasicFileAttributeView.AttributesBuilder.create(dosAttributeNames, (String[])object);
        BasicFileAttributes basicFileAttributes = this.readAttributes();
        this.addRequestedBasicAttributes(basicFileAttributes, (AbstractBasicFileAttributeView.AttributesBuilder)object);
        if (((AbstractBasicFileAttributeView.AttributesBuilder)object).match(READONLY_NAME)) {
            ((AbstractBasicFileAttributeView.AttributesBuilder)object).add(READONLY_NAME, basicFileAttributes.isReadOnly());
        }
        if (((AbstractBasicFileAttributeView.AttributesBuilder)object).match(ARCHIVE_NAME)) {
            ((AbstractBasicFileAttributeView.AttributesBuilder)object).add(ARCHIVE_NAME, basicFileAttributes.isArchive());
        }
        if (((AbstractBasicFileAttributeView.AttributesBuilder)object).match(SYSTEM_NAME)) {
            ((AbstractBasicFileAttributeView.AttributesBuilder)object).add(SYSTEM_NAME, basicFileAttributes.isSystem());
        }
        if (((AbstractBasicFileAttributeView.AttributesBuilder)object).match(HIDDEN_NAME)) {
            ((AbstractBasicFileAttributeView.AttributesBuilder)object).add(HIDDEN_NAME, basicFileAttributes.isHidden());
        }
        return ((AbstractBasicFileAttributeView.AttributesBuilder)object).unmodifiableMap();
    }

    @Override
    public void setArchive(boolean bl) throws IOException {
        this.updateDosAttribute(32, bl);
    }

    @Override
    public void setAttribute(String string, Object object) throws IOException {
        if (string.equals(READONLY_NAME)) {
            this.setReadOnly((Boolean)object);
            return;
        }
        if (string.equals(ARCHIVE_NAME)) {
            this.setArchive((Boolean)object);
            return;
        }
        if (string.equals(SYSTEM_NAME)) {
            this.setSystem((Boolean)object);
            return;
        }
        if (string.equals(HIDDEN_NAME)) {
            this.setHidden((Boolean)object);
            return;
        }
        super.setAttribute(string, object);
    }

    @Override
    public void setHidden(boolean bl) throws IOException {
        this.updateDosAttribute(2, bl);
    }

    @Override
    public void setReadOnly(boolean bl) throws IOException {
        this.updateDosAttribute(1, bl);
    }

    @Override
    public void setSystem(boolean bl) throws IOException {
        this.updateDosAttribute(4, bl);
    }

}

