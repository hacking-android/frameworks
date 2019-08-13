/*
 * Decompiled with CFR 0.145.
 */
package com.google.android.mms.pdu;

import com.google.android.mms.pdu.PduPart;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PduBody {
    private Map<String, PduPart> mPartMapByContentId = new HashMap<String, PduPart>();
    private Map<String, PduPart> mPartMapByContentLocation = new HashMap<String, PduPart>();
    private Map<String, PduPart> mPartMapByFileName = new HashMap<String, PduPart>();
    private Map<String, PduPart> mPartMapByName = new HashMap<String, PduPart>();
    private Vector<PduPart> mParts = new Vector();

    private void putPartToMaps(PduPart pduPart) {
        Object object = pduPart.getContentId();
        if (object != null) {
            this.mPartMapByContentId.put(new String((byte[])object), pduPart);
        }
        if ((object = pduPart.getContentLocation()) != null) {
            object = new String((byte[])object);
            this.mPartMapByContentLocation.put((String)object, pduPart);
        }
        if ((object = pduPart.getName()) != null) {
            object = new String((byte[])object);
            this.mPartMapByName.put((String)object, pduPart);
        }
        if ((object = pduPart.getFilename()) != null) {
            object = new String((byte[])object);
            this.mPartMapByFileName.put((String)object, pduPart);
        }
    }

    public void addPart(int n, PduPart pduPart) {
        if (pduPart != null) {
            this.putPartToMaps(pduPart);
            this.mParts.add(n, pduPart);
            return;
        }
        throw new NullPointerException();
    }

    public boolean addPart(PduPart pduPart) {
        if (pduPart != null) {
            this.putPartToMaps(pduPart);
            return this.mParts.add(pduPart);
        }
        throw new NullPointerException();
    }

    public PduPart getPart(int n) {
        return this.mParts.get(n);
    }

    public PduPart getPartByContentId(String string) {
        return this.mPartMapByContentId.get(string);
    }

    public PduPart getPartByContentLocation(String string) {
        return this.mPartMapByContentLocation.get(string);
    }

    public PduPart getPartByFileName(String string) {
        return this.mPartMapByFileName.get(string);
    }

    public PduPart getPartByName(String string) {
        return this.mPartMapByName.get(string);
    }

    public int getPartIndex(PduPart pduPart) {
        return this.mParts.indexOf(pduPart);
    }

    public int getPartsNum() {
        return this.mParts.size();
    }

    public void removeAll() {
        this.mParts.clear();
    }

    public PduPart removePart(int n) {
        return this.mParts.remove(n);
    }
}

