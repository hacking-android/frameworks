/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.address;

import gov.nist.core.GenericObject;
import gov.nist.core.GenericObjectList;
import gov.nist.javax.sip.address.NetObject;
import java.util.ListIterator;

public class NetObjectList
extends GenericObjectList {
    private static final long serialVersionUID = -1551780600806959023L;

    public NetObjectList() {
    }

    public NetObjectList(String string) {
        super(string);
    }

    public NetObjectList(String string, Class<?> class_) {
        super(string, class_);
    }

    public void add(NetObject netObject) {
        super.add(netObject);
    }

    public void concatenate(NetObjectList netObjectList) {
        super.concatenate(netObjectList);
    }

    @Override
    public String debugDump(int n) {
        return super.debugDump(n);
    }

    @Override
    public GenericObject first() {
        return (NetObject)super.first();
    }

    @Override
    public GenericObject next() {
        return (NetObject)super.next();
    }

    @Override
    public GenericObject next(ListIterator listIterator) {
        return (NetObject)super.next(listIterator);
    }

    @Override
    public void setMyClass(Class class_) {
        super.setMyClass(class_);
    }

    @Override
    public String toString() {
        return this.encode();
    }
}

