/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  dalvik.system.CloseGuard
 */
package android.renderscript;

import android.annotation.UnsupportedAppUsage;
import android.renderscript.RSIllegalArgumentException;
import android.renderscript.RSInvalidStateException;
import android.renderscript.RSRuntimeException;
import android.renderscript.RenderScript;
import dalvik.system.CloseGuard;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class BaseObj {
    final CloseGuard guard = CloseGuard.get();
    private boolean mDestroyed;
    private long mID;
    private String mName;
    @UnsupportedAppUsage
    RenderScript mRS;

    BaseObj(long l, RenderScript renderScript) {
        renderScript.validate();
        this.mRS = renderScript;
        this.mID = l;
        this.mDestroyed = false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void helpDestroy() {
        long l;
        boolean bl = false;
        // MONITORENTER : this
        if (!this.mDestroyed) {
            bl = true;
            this.mDestroyed = true;
        }
        // MONITOREXIT : this
        if (!bl) return;
        this.guard.close();
        ReentrantReadWriteLock.ReadLock readLock = this.mRS.mRWLock.readLock();
        readLock.lock();
        if (this.mRS.isAlive() && (l = this.mID) != 0L) {
            this.mRS.nObjDestroy(l);
        }
        readLock.unlock();
        this.mRS = null;
        this.mID = 0L;
    }

    void checkValid() {
        if (this.mID != 0L) {
            return;
        }
        throw new RSIllegalArgumentException("Invalid object.");
    }

    public void destroy() {
        if (!this.mDestroyed) {
            this.helpDestroy();
            return;
        }
        throw new RSInvalidStateException("Object already destroyed.");
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (BaseObj)object;
        if (this.mID != ((BaseObj)object).mID) {
            bl = false;
        }
        return bl;
    }

    protected void finalize() throws Throwable {
        try {
            if (this.guard != null) {
                this.guard.warnIfOpen();
            }
            this.helpDestroy();
            return;
        }
        finally {
            super.finalize();
        }
    }

    long getID(RenderScript renderScript) {
        this.mRS.validate();
        if (!this.mDestroyed) {
            if (this.mID != 0L) {
                if (renderScript != null && renderScript != this.mRS) {
                    throw new RSInvalidStateException("using object with mismatched context.");
                }
                return this.mID;
            }
            throw new RSRuntimeException("Internal error: Object id 0.");
        }
        throw new RSInvalidStateException("using a destroyed object.");
    }

    public String getName() {
        return this.mName;
    }

    public int hashCode() {
        long l = this.mID;
        return (int)(l >> 32 ^ 0xFFFFFFFL & l);
    }

    void setID(long l) {
        if (this.mID == 0L) {
            this.mID = l;
            return;
        }
        throw new RSRuntimeException("Internal Error, reset of object ID.");
    }

    public void setName(String string2) {
        if (string2 != null) {
            if (string2.length() >= 1) {
                if (this.mName == null) {
                    try {
                        byte[] arrby = string2.getBytes("UTF-8");
                        this.mRS.nAssignName(this.mID, arrby);
                        this.mName = string2;
                        return;
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        throw new RuntimeException(unsupportedEncodingException);
                    }
                }
                throw new RSIllegalArgumentException("setName object already has a name.");
            }
            throw new RSIllegalArgumentException("setName does not accept a zero length string.");
        }
        throw new RSIllegalArgumentException("setName requires a string of non-zero length.");
    }

    void updateFromNative() {
        this.mRS.validate();
        RenderScript renderScript = this.mRS;
        this.mName = renderScript.nGetName(this.getID(renderScript));
    }
}

