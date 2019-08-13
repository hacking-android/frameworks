/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.javax.sip.header;

import gov.nist.core.GenericObject;
import gov.nist.javax.sip.header.ExtensionHeaderList;
import gov.nist.javax.sip.header.Indentation;
import gov.nist.javax.sip.header.SIPHeader;
import gov.nist.javax.sip.header.SIPObject;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import javax.sip.header.Header;

public abstract class SIPHeaderList<HDR extends SIPHeader>
extends SIPHeader
implements List<HDR>,
Header {
    private static boolean prettyEncode = false;
    protected List<HDR> hlist = new LinkedList<HDR>();
    private Class<HDR> myClass;

    private SIPHeaderList() {
    }

    protected SIPHeaderList(Class<HDR> class_, String string) {
        this();
        this.headerName = string;
        this.myClass = class_;
    }

    public static void setPrettyEncode(boolean bl) {
        prettyEncode = bl;
    }

    @Override
    public void add(int n, HDR HDR) throws IndexOutOfBoundsException {
        this.hlist.add(n, HDR);
    }

    public void add(HDR HDR, boolean bl) {
        if (bl) {
            this.addFirst(HDR);
        } else {
            this.add(HDR);
        }
    }

    @Override
    public boolean add(HDR HDR) {
        this.hlist.add(HDR);
        return true;
    }

    @Override
    public boolean addAll(int n, Collection<? extends HDR> collection) {
        return this.hlist.addAll(n, collection);
    }

    @Override
    public boolean addAll(Collection<? extends HDR> collection) {
        return this.hlist.addAll(collection);
    }

    public void addFirst(HDR HDR) {
        this.hlist.add(0, HDR);
    }

    @Override
    public void clear() {
        this.hlist.clear();
    }

    @Override
    public Object clone() {
        try {
            SIPHeaderList<HDR> sIPHeaderList = (SIPHeaderList<HDR>)this.getClass().getConstructor(null).newInstance(null);
            sIPHeaderList.headerName = this.headerName;
            sIPHeaderList.myClass = this.myClass;
            sIPHeaderList = sIPHeaderList.clonehlist(this.hlist);
            return sIPHeaderList;
        }
        catch (Exception exception) {
            throw new RuntimeException("Could not clone!", exception);
        }
    }

    protected final SIPHeaderList<HDR> clonehlist(List<HDR> object) {
        if (object != null) {
            object = object.iterator();
            while (object.hasNext()) {
                Header header = (Header)object.next();
                this.hlist.add((SIPHeader)header.clone());
            }
        }
        return this;
    }

    public void concatenate(SIPHeaderList<HDR> sIPHeaderList, boolean bl) throws IllegalArgumentException {
        if (!bl) {
            this.addAll(sIPHeaderList);
        } else {
            this.addAll(0, sIPHeaderList);
        }
    }

    @Override
    public boolean contains(Object object) {
        return this.hlist.contains(object);
    }

    @Override
    public boolean containsAll(Collection<?> collection) {
        return this.hlist.containsAll(collection);
    }

    @Override
    public String debugDump() {
        return this.debugDump(0);
    }

    @Override
    public String debugDump(int n) {
        this.stringRepresentation = "";
        String string = new Indentation(n).getIndentation();
        Object object2 = this.getClass().getName();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string);
        stringBuilder.append((String)object2);
        this.sprint(stringBuilder.toString());
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(string);
        ((StringBuilder)object2).append("{");
        this.sprint(((StringBuilder)object2).toString());
        for (Object object2 : this.hlist) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append(string);
            stringBuilder2.append(((SIPObject)object2).debugDump());
            this.sprint(stringBuilder2.toString());
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append(string);
        ((StringBuilder)object2).append("}");
        this.sprint(((StringBuilder)object2).toString());
        return this.stringRepresentation;
    }

    @Override
    public String encode() {
        return this.encode(new StringBuffer()).toString();
    }

    @Override
    public StringBuffer encode(StringBuffer stringBuffer) {
        if (this.hlist.isEmpty()) {
            stringBuffer.append(this.headerName);
            stringBuffer.append(':');
            stringBuffer.append("\r\n");
        } else if (!(this.headerName.equals("WWW-Authenticate") || this.headerName.equals("Proxy-Authenticate") || this.headerName.equals("Authorization") || this.headerName.equals("Proxy-Authorization") || prettyEncode && (this.headerName.equals("Via") || this.headerName.equals("Route") || this.headerName.equals("Record-Route")) || this.getClass().equals(ExtensionHeaderList.class))) {
            stringBuffer.append(this.headerName);
            stringBuffer.append(":");
            stringBuffer.append(" ");
            this.encodeBody(stringBuffer);
            stringBuffer.append("\r\n");
        } else {
            ListIterator<HDR> listIterator = this.hlist.listIterator();
            while (listIterator.hasNext()) {
                ((SIPHeader)listIterator.next()).encode(stringBuffer);
            }
        }
        return stringBuffer;
    }

    @Override
    protected String encodeBody() {
        return this.encodeBody(new StringBuffer()).toString();
    }

    @Override
    protected StringBuffer encodeBody(StringBuffer stringBuffer) {
        SIPHeader sIPHeader;
        ListIterator<HDR> listIterator = this.listIterator();
        while ((sIPHeader = (SIPHeader)listIterator.next()) != this) {
            sIPHeader.encodeBody(stringBuffer);
            if (listIterator.hasNext()) {
                if (!this.headerName.equals("Privacy")) {
                    stringBuffer.append(",");
                    continue;
                }
                stringBuffer.append(";");
                continue;
            }
            return stringBuffer;
        }
        throw new RuntimeException("Unexpected circularity in SipHeaderList");
    }

    @Override
    public boolean equals(Object list) {
        boolean bl = true;
        if (list == this) {
            return true;
        }
        if (list instanceof SIPHeaderList) {
            List<HDR> list2 = (SIPHeaderList)list;
            list = this.hlist;
            list2 = ((SIPHeaderList)list2).hlist;
            if (list == list2) {
                return true;
            }
            if (list == null) {
                boolean bl2 = bl;
                if (list2 != null) {
                    bl2 = list2.size() == 0 ? bl : false;
                }
                return bl2;
            }
            return list.equals(list2);
        }
        return false;
    }

    @Override
    public HDR get(int n) {
        return (HDR)((SIPHeader)this.hlist.get(n));
    }

    public Header getFirst() {
        List<HDR> list = this.hlist;
        if (list != null && !list.isEmpty()) {
            return (Header)this.hlist.get(0);
        }
        return null;
    }

    public List<HDR> getHeaderList() {
        return this.hlist;
    }

    public List<String> getHeadersAsEncodedStrings() {
        LinkedList<String> linkedList = new LinkedList<String>();
        ListIterator<HDR> listIterator = this.hlist.listIterator();
        while (listIterator.hasNext()) {
            linkedList.add(((Header)listIterator.next()).toString());
        }
        return linkedList;
    }

    public Header getLast() {
        List<HDR> list = this.hlist;
        if (list != null && !list.isEmpty()) {
            list = this.hlist;
            return (Header)list.get(list.size() - 1);
        }
        return null;
    }

    public Class<HDR> getMyClass() {
        return this.myClass;
    }

    @Override
    public String getName() {
        return this.headerName;
    }

    @Override
    public int hashCode() {
        return this.headerName.hashCode();
    }

    public int indexOf(GenericObject genericObject) {
        return this.hlist.indexOf(genericObject);
    }

    @Override
    public int indexOf(Object object) {
        return this.hlist.indexOf(object);
    }

    @Override
    public boolean isEmpty() {
        return this.hlist.isEmpty();
    }

    @Override
    public boolean isHeaderList() {
        return true;
    }

    @Override
    public Iterator<HDR> iterator() {
        return this.hlist.listIterator();
    }

    @Override
    public int lastIndexOf(Object object) {
        return this.hlist.lastIndexOf(object);
    }

    @Override
    public ListIterator<HDR> listIterator() {
        return this.hlist.listIterator(0);
    }

    @Override
    public ListIterator<HDR> listIterator(int n) {
        return this.hlist.listIterator(n);
    }

    public boolean match(SIPHeaderList<?> list3) {
        if (list3 == null) {
            return true;
        }
        if (!this.getClass().equals(list3.getClass())) {
            return false;
        }
        List<HDR> list2 = this.hlist;
        list3 = ((SIPHeaderList)list3).hlist;
        if (list2 == list3) {
            return true;
        }
        if (list2 == null) {
            return false;
        }
        for (List<HDR> list3 : list3) {
            boolean bl = false;
            list2 = this.hlist.iterator();
            while (list2.hasNext() && !bl) {
                bl = ((SIPHeader)list2.next()).match(list3);
            }
            if (bl) continue;
            return false;
        }
        return true;
    }

    @Override
    public HDR remove(int n) {
        return (HDR)((SIPHeader)this.hlist.remove(n));
    }

    @Override
    public boolean remove(HDR HDR) {
        if (this.hlist.size() == 0) {
            return false;
        }
        return this.hlist.remove(HDR);
    }

    @Override
    public boolean remove(Object object) {
        return this.hlist.remove(object);
    }

    @Override
    public boolean removeAll(Collection<?> collection) {
        return this.hlist.removeAll(collection);
    }

    public void removeFirst() {
        if (this.hlist.size() != 0) {
            this.hlist.remove(0);
        }
    }

    public void removeLast() {
        if (this.hlist.size() != 0) {
            List<HDR> list = this.hlist;
            list.remove(list.size() - 1);
        }
    }

    @Override
    public boolean retainAll(Collection<?> collection) {
        return this.hlist.retainAll(collection);
    }

    @Override
    public HDR set(int n, HDR HDR) {
        return (HDR)((SIPHeader)this.hlist.set(n, HDR));
    }

    protected void setMyClass(Class<HDR> class_) {
        this.myClass = class_;
    }

    @Override
    public int size() {
        return this.hlist.size();
    }

    @Override
    public List<HDR> subList(int n, int n2) {
        return this.hlist.subList(n, n2);
    }

    @Override
    public Object[] toArray() {
        return this.hlist.toArray();
    }

    @Override
    public <T> T[] toArray(T[] arrT) {
        return this.hlist.toArray(arrT);
    }
}

