/*
 * Decompiled with CFR 0.145.
 */
package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.nio.channels.Channel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import sun.nio.ch.FileKey;
import sun.nio.ch.FileLockTable;

class SharedFileLockTable
extends FileLockTable {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static ConcurrentHashMap<FileKey, List<FileLockReference>> lockMap = new ConcurrentHashMap();
    private static ReferenceQueue<FileLock> queue = new ReferenceQueue();
    private final Channel channel;
    private final FileKey fileKey;

    SharedFileLockTable(Channel channel, FileDescriptor fileDescriptor) throws IOException {
        this.channel = channel;
        this.fileKey = FileKey.create(fileDescriptor);
    }

    private void checkList(List<FileLockReference> object, long l, long l2) throws OverlappingFileLockException {
        object = object.iterator();
        while (object.hasNext()) {
            FileLock fileLock = (FileLock)((FileLockReference)object.next()).get();
            if (fileLock == null || !fileLock.overlaps(l, l2)) continue;
            throw new OverlappingFileLockException();
        }
    }

    private void removeKeyIfEmpty(FileKey fileKey, List<FileLockReference> list) {
        if (list.isEmpty()) {
            lockMap.remove(fileKey);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void removeStaleEntries() {
        FileLockReference fileLockReference;
        while ((fileLockReference = (FileLockReference)queue.poll()) != null) {
            FileKey fileKey = fileLockReference.fileKey();
            List<FileLockReference> list = lockMap.get(fileKey);
            if (list == null) continue;
            synchronized (list) {
                list.remove(fileLockReference);
                this.removeKeyIfEmpty(fileKey, list);
            }
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void add(FileLock fileLock) throws OverlappingFileLockException {
        Object object = lockMap.get(this.fileKey);
        do {
            block16 : {
                block15 : {
                    Object object2;
                    block14 : {
                        object2 = object;
                        if (object != null) break;
                        object = new ArrayList<FileLockReference>(2);
                        // MONITORENTER : object
                        object2 = lockMap.putIfAbsent(this.fileKey, (List<FileLockReference>)object);
                        if (object2 != null) break block14;
                        object2 = new FileLockReference(fileLock, queue, this.fileKey);
                        object.add(object2);
                        // MONITOREXIT : object
                        break block15;
                    }
                    // MONITOREXIT : object
                    // MONITORENTER : object2
                    object = lockMap.get(this.fileKey);
                    if (object2 != object) break block16;
                    try {
                        this.checkList((List<FileLockReference>)object2, fileLock.position(), fileLock.size());
                        object = new FileLockReference(fileLock, queue, this.fileKey);
                        object2.add(object);
                        // MONITOREXIT : object2
                    }
                    catch (Throwable throwable) {
                        throw throwable;
                    }
                }
                this.removeStaleEntries();
                return;
            }
            try {
                // MONITOREXIT : object2
            }
            catch (Throwable throwable) {
                // empty catch block
                throw throwable;
            }
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void remove(FileLock fileLock) {
        List<FileLockReference> list = lockMap.get(this.fileKey);
        if (list == null) {
            return;
        }
        synchronized (list) {
            for (int i = 0; i < list.size(); ++i) {
                FileLockReference fileLockReference = list.get(i);
                if ((FileLock)fileLockReference.get() != fileLock) continue;
                fileLockReference.clear();
                list.remove(i);
                break;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public List<FileLock> removeAll() {
        ArrayList<FileLock> arrayList = new ArrayList<FileLock>();
        List<FileLockReference> list = lockMap.get(this.fileKey);
        if (list == null) return arrayList;
        synchronized (list) {
            int n = 0;
            do {
                if (n >= list.size()) {
                    this.removeKeyIfEmpty(this.fileKey, list);
                    return arrayList;
                }
                FileLockReference fileLockReference = list.get(n);
                FileLock fileLock = (FileLock)fileLockReference.get();
                if (fileLock != null && fileLock.acquiredBy() == this.channel) {
                    fileLockReference.clear();
                    list.remove(n);
                    arrayList.add(fileLock);
                    continue;
                }
                ++n;
            } while (true);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void replace(FileLock object, FileLock fileLock) {
        List<FileLockReference> list = lockMap.get(this.fileKey);
        synchronized (list) {
            for (int i = 0; i < list.size(); ++i) {
                FileLockReference fileLockReference = list.get(i);
                if ((FileLock)fileLockReference.get() != object) continue;
                fileLockReference.clear();
                object = new FileLockReference(fileLock, queue, this.fileKey);
                list.set(i, (FileLockReference)object);
                break;
            }
            return;
        }
    }

    private static class FileLockReference
    extends WeakReference<FileLock> {
        private FileKey fileKey;

        FileLockReference(FileLock fileLock, ReferenceQueue<FileLock> referenceQueue, FileKey fileKey) {
            super(fileLock, referenceQueue);
            this.fileKey = fileKey;
        }

        FileKey fileKey() {
            return this.fileKey;
        }
    }

}

