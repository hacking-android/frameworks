/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.GenericObject;
import gov.nist.core.GenericObjectList;
import gov.nist.javax.sip.header.SIPObject;
import java.util.AbstractList;
import java.util.ListIterator;

public class SIPObjectList
extends GenericObjectList {
    private static final long serialVersionUID = -3015154738977508905L;

    public SIPObjectList() {
    }

    public SIPObjectList(String string) {
        super(string);
    }

    public void concatenate(SIPObjectList sIPObjectList) {
        super.concatenate(sIPObjectList);
    }

    public void concatenate(SIPObjectList sIPObjectList, boolean bl) {
        super.concatenate(sIPObjectList, bl);
    }

    @Override
    public String debugDump(int n) {
        return super.debugDump(n);
    }

    @Override
    public GenericObject first() {
        return (SIPObject)super.first();
    }

    @Override
    public void mergeObjects(GenericObjectList cloneable) {
        ListIterator listIterator = this.listIterator();
        ListIterator listIterator2 = ((AbstractList)((Object)cloneable)).listIterator();
        while (listIterator.hasNext()) {
            cloneable = (GenericObject)listIterator.next();
            while (listIterator2.hasNext()) {
                ((GenericObject)cloneable).merge(listIterator2.next());
            }
        }
    }

    @Override
    public GenericObject next() {
        return (SIPObject)super.next();
    }
}

