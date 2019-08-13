/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.ProviderMismatchException;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.nio.file.attribute.GroupPrincipal;
import java.nio.file.attribute.PosixFileAttributeView;
import java.nio.file.attribute.PosixFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.UserPrincipal;
import java.security.Permission;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import sun.nio.fs.AbstractBasicFileAttributeView;
import sun.nio.fs.FileOwnerAttributeViewImpl;
import sun.nio.fs.UnixConstants;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileAttributes;
import sun.nio.fs.UnixFileModeAttribute;
import sun.nio.fs.UnixNativeDispatcher;
import sun.nio.fs.UnixPath;
import sun.nio.fs.UnixUserPrincipals;
import sun.nio.fs.Util;

class UnixFileAttributeViews {
    UnixFileAttributeViews() {
    }

    static Basic createBasicView(UnixPath unixPath, boolean bl) {
        return new Basic(unixPath, bl);
    }

    static FileOwnerAttributeViewImpl createOwnerView(UnixPath unixPath, boolean bl) {
        return new FileOwnerAttributeViewImpl(UnixFileAttributeViews.createPosixView(unixPath, bl));
    }

    static Posix createPosixView(UnixPath unixPath, boolean bl) {
        return new Posix(unixPath, bl);
    }

    static Unix createUnixView(UnixPath unixPath, boolean bl) {
        return new Unix(unixPath, bl);
    }

    static class Basic
    extends AbstractBasicFileAttributeView {
        protected final UnixPath file;
        protected final boolean followLinks;

        Basic(UnixPath unixPath, boolean bl) {
            this.file = unixPath;
            this.followLinks = bl;
        }

        @Override
        public BasicFileAttributes readAttributes() throws IOException {
            this.file.checkRead();
            try {
                BasicFileAttributes basicFileAttributes = UnixFileAttributes.get(this.file, this.followLinks).asBasicFileAttributes();
                return basicFileAttributes;
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException(this.file);
                return null;
            }
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        @Override
        public void setTimes(FileTime fileTime, FileTime fileTime2, FileTime fileTime3) throws IOException {
            int n;
            Throwable throwable222;
            block22 : {
                Object object;
                block21 : {
                    block23 : {
                        if (fileTime == null && fileTime2 == null) {
                            return;
                        }
                        this.file.checkWrite();
                        n = this.file.openForAttributeAccess(this.followLinks);
                        if (fileTime == null) break block23;
                        fileTime3 = fileTime;
                        object = fileTime2;
                        if (fileTime2 != null) break block21;
                    }
                    FileTime fileTime4 = fileTime;
                    try {
                        object = UnixFileAttributes.get(n);
                        fileTime3 = fileTime;
                        if (fileTime == null) {
                            fileTime4 = fileTime;
                            fileTime3 = ((UnixFileAttributes)object).lastModifiedTime();
                        }
                        fileTime = fileTime2;
                        if (fileTime2 == null) {
                            fileTime4 = fileTime3;
                            fileTime = ((UnixFileAttributes)object).lastAccessTime();
                        }
                        object = fileTime;
                        break block21;
                    }
                    catch (Throwable throwable222) {
                        break block22;
                    }
                    catch (UnixException unixException) {
                        unixException.rethrowAsIOException(this.file);
                        object = fileTime2;
                        fileTime3 = fileTime4;
                    }
                }
                long l = fileTime3.to(TimeUnit.MICROSECONDS);
                long l2 = ((FileTime)object).to(TimeUnit.MICROSECONDS);
                boolean bl = false;
                try {
                    if (UnixNativeDispatcher.futimesSupported()) {
                        UnixNativeDispatcher.futimes(n, l2, l);
                    } else {
                        UnixNativeDispatcher.utimes(this.file, l2, l);
                    }
                }
                catch (UnixException unixException) {
                    if (unixException.errno() == UnixConstants.EINVAL && (l < 0L || l2 < 0L)) {
                        bl = true;
                    }
                    unixException.rethrowAsIOException(this.file);
                }
                if (bl) {
                    long l3 = l;
                    if (l < 0L) {
                        l3 = 0L;
                    }
                    l = l2;
                    if (l2 < 0L) {
                        l = 0L;
                    }
                    try {
                        if (UnixNativeDispatcher.futimesSupported()) {
                            UnixNativeDispatcher.futimes(n, l, l3);
                        } else {
                            UnixNativeDispatcher.utimes(this.file, l, l3);
                        }
                    }
                    catch (UnixException unixException) {
                        unixException.rethrowAsIOException(this.file);
                    }
                }
                UnixNativeDispatcher.close(n);
                return;
            }
            UnixNativeDispatcher.close(n);
            throw throwable222;
        }
    }

    private static class Posix
    extends Basic
    implements PosixFileAttributeView {
        private static final String GROUP_NAME = "group";
        private static final String OWNER_NAME = "owner";
        private static final String PERMISSIONS_NAME = "permissions";
        static final Set<String> posixAttributeNames = Util.newSet(basicAttributeNames, new String[]{"permissions", "owner", "group"});

        Posix(UnixPath unixPath, boolean bl) {
            super(unixPath, bl);
        }

        final void addRequestedPosixAttributes(PosixFileAttributes posixFileAttributes, AbstractBasicFileAttributeView.AttributesBuilder attributesBuilder) {
            this.addRequestedBasicAttributes(posixFileAttributes, attributesBuilder);
            if (attributesBuilder.match(PERMISSIONS_NAME)) {
                attributesBuilder.add(PERMISSIONS_NAME, posixFileAttributes.permissions());
            }
            if (attributesBuilder.match(OWNER_NAME)) {
                attributesBuilder.add(OWNER_NAME, posixFileAttributes.owner());
            }
            if (attributesBuilder.match(GROUP_NAME)) {
                attributesBuilder.add(GROUP_NAME, posixFileAttributes.group());
            }
        }

        final void checkReadExtended() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                this.file.checkRead();
                securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
            }
        }

        final void checkWriteExtended() {
            SecurityManager securityManager = System.getSecurityManager();
            if (securityManager != null) {
                this.file.checkWrite();
                securityManager.checkPermission(new RuntimePermission("accessUserInformation"));
            }
        }

        @Override
        public UserPrincipal getOwner() throws IOException {
            return ((UnixFileAttributes)this.readAttributes()).owner();
        }

        @Override
        public String name() {
            return "posix";
        }

        @Override
        public Map<String, Object> readAttributes(String[] object) throws IOException {
            object = AbstractBasicFileAttributeView.AttributesBuilder.create(posixAttributeNames, (String[])object);
            this.addRequestedPosixAttributes((PosixFileAttributes)this.readAttributes(), (AbstractBasicFileAttributeView.AttributesBuilder)object);
            return ((AbstractBasicFileAttributeView.AttributesBuilder)object).unmodifiableMap();
        }

        @Override
        public UnixFileAttributes readAttributes() throws IOException {
            this.checkReadExtended();
            try {
                UnixFileAttributes unixFileAttributes = UnixFileAttributes.get(this.file, this.followLinks);
                return unixFileAttributes;
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException(this.file);
                return null;
            }
        }

        @Override
        public void setAttribute(String string, Object object) throws IOException {
            if (string.equals(PERMISSIONS_NAME)) {
                this.setPermissions((Set)object);
                return;
            }
            if (string.equals(OWNER_NAME)) {
                this.setOwner((UserPrincipal)object);
                return;
            }
            if (string.equals(GROUP_NAME)) {
                this.setGroup((GroupPrincipal)object);
                return;
            }
            super.setAttribute(string, object);
        }

        @Override
        public void setGroup(GroupPrincipal groupPrincipal) throws IOException {
            if (groupPrincipal != null) {
                if (groupPrincipal instanceof UnixUserPrincipals.Group) {
                    this.setOwners(-1, ((UnixUserPrincipals.Group)groupPrincipal).gid());
                    return;
                }
                throw new ProviderMismatchException();
            }
            throw new NullPointerException("'owner' is null");
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        final void setMode(int n) throws IOException {
            this.checkWriteExtended();
            if (this.followLinks) {
                UnixNativeDispatcher.chmod(this.file, n);
                return;
            }
            int n2 = this.file.openForAttributeAccess(false);
            UnixNativeDispatcher.fchmod(n2, n);
            {
                catch (Throwable throwable) {
                    UnixNativeDispatcher.close(n2);
                    throw throwable;
                }
            }
            try {
                UnixNativeDispatcher.close(n2);
                return;
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException(this.file);
            }
        }

        @Override
        public void setOwner(UserPrincipal userPrincipal) throws IOException {
            if (userPrincipal != null) {
                if (userPrincipal instanceof UnixUserPrincipals.User) {
                    if (!(userPrincipal instanceof UnixUserPrincipals.Group)) {
                        this.setOwners(((UnixUserPrincipals.User)userPrincipal).uid(), -1);
                        return;
                    }
                    throw new IOException("'owner' parameter can't be a group");
                }
                throw new ProviderMismatchException();
            }
            throw new NullPointerException("'owner' is null");
        }

        final void setOwners(int n, int n2) throws IOException {
            this.checkWriteExtended();
            try {
                if (this.followLinks) {
                    UnixNativeDispatcher.chown(this.file, n, n2);
                } else {
                    UnixNativeDispatcher.lchown(this.file, n, n2);
                }
            }
            catch (UnixException unixException) {
                unixException.rethrowAsIOException(this.file);
            }
        }

        @Override
        public void setPermissions(Set<PosixFilePermission> set) throws IOException {
            this.setMode(UnixFileModeAttribute.toUnixMode(set));
        }
    }

    private static class Unix
    extends Posix {
        private static final String CTIME_NAME = "ctime";
        private static final String DEV_NAME = "dev";
        private static final String GID_NAME = "gid";
        private static final String INO_NAME = "ino";
        private static final String MODE_NAME = "mode";
        private static final String NLINK_NAME = "nlink";
        private static final String RDEV_NAME = "rdev";
        private static final String UID_NAME = "uid";
        static final Set<String> unixAttributeNames = Util.newSet(posixAttributeNames, new String[]{"mode", "ino", "dev", "rdev", "nlink", "uid", "gid", "ctime"});

        Unix(UnixPath unixPath, boolean bl) {
            super(unixPath, bl);
        }

        @Override
        public String name() {
            return "unix";
        }

        @Override
        public Map<String, Object> readAttributes(String[] object) throws IOException {
            AbstractBasicFileAttributeView.AttributesBuilder attributesBuilder = AbstractBasicFileAttributeView.AttributesBuilder.create(unixAttributeNames, (String[])object);
            object = this.readAttributes();
            this.addRequestedPosixAttributes((PosixFileAttributes)object, attributesBuilder);
            if (attributesBuilder.match(MODE_NAME)) {
                attributesBuilder.add(MODE_NAME, ((UnixFileAttributes)object).mode());
            }
            if (attributesBuilder.match(INO_NAME)) {
                attributesBuilder.add(INO_NAME, ((UnixFileAttributes)object).ino());
            }
            if (attributesBuilder.match(DEV_NAME)) {
                attributesBuilder.add(DEV_NAME, ((UnixFileAttributes)object).dev());
            }
            if (attributesBuilder.match(RDEV_NAME)) {
                attributesBuilder.add(RDEV_NAME, ((UnixFileAttributes)object).rdev());
            }
            if (attributesBuilder.match(NLINK_NAME)) {
                attributesBuilder.add(NLINK_NAME, ((UnixFileAttributes)object).nlink());
            }
            if (attributesBuilder.match(UID_NAME)) {
                attributesBuilder.add(UID_NAME, ((UnixFileAttributes)object).uid());
            }
            if (attributesBuilder.match(GID_NAME)) {
                attributesBuilder.add(GID_NAME, ((UnixFileAttributes)object).gid());
            }
            if (attributesBuilder.match(CTIME_NAME)) {
                attributesBuilder.add(CTIME_NAME, ((UnixFileAttributes)object).ctime());
            }
            return attributesBuilder.unmodifiableMap();
        }

        @Override
        public void setAttribute(String string, Object object) throws IOException {
            if (string.equals(MODE_NAME)) {
                this.setMode((Integer)object);
                return;
            }
            if (string.equals(UID_NAME)) {
                this.setOwners((Integer)object, -1);
                return;
            }
            if (string.equals(GID_NAME)) {
                this.setOwners(-1, (Integer)object);
                return;
            }
            super.setAttribute(string, object);
        }
    }

}

