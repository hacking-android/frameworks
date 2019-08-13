/*
 * Decompiled with CFR 0.145.
 */
package android.drm;

import java.util.ArrayList;
import java.util.Iterator;

public class DrmSupportInfo {
    private String mDescription = "";
    private final ArrayList<String> mFileSuffixList = new ArrayList();
    private final ArrayList<String> mMimeTypeList = new ArrayList();

    public void addFileSuffix(String string2) {
        if (string2 != "") {
            this.mFileSuffixList.add(string2);
            return;
        }
        throw new IllegalArgumentException("fileSuffix is an empty string");
    }

    public void addMimeType(String string2) {
        if (string2 != null) {
            if (string2 != "") {
                this.mMimeTypeList.add(string2);
                return;
            }
            throw new IllegalArgumentException("mimeType is an empty string");
        }
        throw new IllegalArgumentException("mimeType is null");
    }

    public boolean equals(Object object) {
        boolean bl = object instanceof DrmSupportInfo;
        boolean bl2 = false;
        if (bl) {
            object = (DrmSupportInfo)object;
            if (this.mFileSuffixList.equals(((DrmSupportInfo)object).mFileSuffixList) && this.mMimeTypeList.equals(((DrmSupportInfo)object).mMimeTypeList) && this.mDescription.equals(((DrmSupportInfo)object).mDescription)) {
                bl2 = true;
            }
            return bl2;
        }
        return false;
    }

    public String getDescriprition() {
        return this.mDescription;
    }

    public String getDescription() {
        return this.mDescription;
    }

    public Iterator<String> getFileSuffixIterator() {
        return this.mFileSuffixList.iterator();
    }

    public Iterator<String> getMimeTypeIterator() {
        return this.mMimeTypeList.iterator();
    }

    public int hashCode() {
        return this.mFileSuffixList.hashCode() + this.mMimeTypeList.hashCode() + this.mDescription.hashCode();
    }

    boolean isSupportedFileSuffix(String string2) {
        return this.mFileSuffixList.contains(string2);
    }

    boolean isSupportedMimeType(String string2) {
        if (string2 != null && !string2.equals("")) {
            for (int i = 0; i < this.mMimeTypeList.size(); ++i) {
                if (!this.mMimeTypeList.get(i).startsWith(string2)) continue;
                return true;
            }
        }
        return false;
    }

    public void setDescription(String string2) {
        if (string2 != null) {
            if (string2 != "") {
                this.mDescription = string2;
                return;
            }
            throw new IllegalArgumentException("description is an empty string");
        }
        throw new IllegalArgumentException("description is null");
    }
}

