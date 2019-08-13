/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.fs;

import java.io.IOException;
import java.nio.file.FileStore;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import sun.nio.fs.LinuxFileStore;
import sun.nio.fs.LinuxNativeDispatcher;
import sun.nio.fs.LinuxUserDefinedFileAttributeView;
import sun.nio.fs.LinuxWatchService;
import sun.nio.fs.UnixException;
import sun.nio.fs.UnixFileSystem;
import sun.nio.fs.UnixFileSystemProvider;
import sun.nio.fs.UnixMountEntry;
import sun.nio.fs.Util;

class LinuxFileSystem
extends UnixFileSystem {
    LinuxFileSystem(UnixFileSystemProvider unixFileSystemProvider, String string) {
        super(unixFileSystemProvider, string);
    }

    @Override
    void copyNonPosixAttributes(int n, int n2) {
        LinuxUserDefinedFileAttributeView.copyExtendedAttributes(n, n2);
    }

    @Override
    FileStore getFileStore(UnixMountEntry unixMountEntry) throws IOException {
        return new LinuxFileStore(this, unixMountEntry);
    }

    @Override
    Iterable<UnixMountEntry> getMountEntries() {
        return this.getMountEntries("/proc/mounts");
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    Iterable<UnixMountEntry> getMountEntries(String object) {
        ArrayList<UnixMountEntry> arrayList = new ArrayList<UnixMountEntry>();
        long l = LinuxNativeDispatcher.setmntent(Util.toBytes((String)object), Util.toBytes("r"));
        do {
            block8 : {
                object = new UnixMountEntry();
                int n = LinuxNativeDispatcher.getmntent(l, (UnixMountEntry)object);
                if (n >= 0) break block8;
                LinuxNativeDispatcher.endmntent(l);
                return arrayList;
            }
            arrayList.add((UnixMountEntry)object);
            continue;
            break;
        } while (true);
        catch (Throwable throwable) {
            try {
                LinuxNativeDispatcher.endmntent(l);
                throw throwable;
            }
            catch (UnixException unixException) {
                // empty catch block
            }
        }
        return arrayList;
    }

    @Override
    public WatchService newWatchService() throws IOException {
        return new LinuxWatchService(this);
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
        return SupportedFileFileAttributeViewsHolder.supportedFileAttributeViews;
    }

    private static class SupportedFileFileAttributeViewsHolder {
        static final Set<String> supportedFileAttributeViews = SupportedFileFileAttributeViewsHolder.supportedFileAttributeViews();

        private SupportedFileFileAttributeViewsHolder() {
        }

        private static Set<String> supportedFileAttributeViews() {
            HashSet<String> hashSet = new HashSet<String>();
            hashSet.addAll(UnixFileSystem.standardFileAttributeViews());
            hashSet.add("dos");
            hashSet.add("user");
            return Collections.unmodifiableSet(hashSet);
        }
    }

}

