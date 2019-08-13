/*
 * Decompiled with CFR 0.145.
 */
package gov.nist.core;

import gov.nist.core.GenericObject;
import gov.nist.core.InternalErrorHandler;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class GenericObjectList
extends LinkedList<GenericObject>
implements Serializable,
Cloneable {
    protected static final String AND = "&";
    protected static final String AT = "@";
    protected static final String COLON = ":";
    protected static final String COMMA = ",";
    protected static final String DOT = ".";
    protected static final String DOUBLE_QUOTE = "\"";
    protected static final String EQUALS = "=";
    protected static final String GREATER_THAN = ">";
    protected static final String HT = "\t";
    protected static final String LESS_THAN = "<";
    protected static final String LPAREN = "(";
    protected static final String NEWLINE = "\r\n";
    protected static final String PERCENT = "%";
    protected static final String POUND = "#";
    protected static final String QUESTION = "?";
    protected static final String QUOTE = "'";
    protected static final String RETURN = "\n";
    protected static final String RPAREN = ")";
    protected static final String SEMICOLON = ";";
    protected static final String SLASH = "/";
    protected static final String SP = " ";
    protected static final String STAR = "*";
    protected int indentation;
    protected String listName = null;
    protected Class<?> myClass;
    private ListIterator<? extends GenericObject> myListIterator;
    protected String separator = ";";
    private String stringRep = "";

    protected GenericObjectList() {
    }

    protected GenericObjectList(String string) {
        this();
        this.listName = string;
    }

    protected GenericObjectList(String string, Class class_) {
        this(string);
        this.myClass = class_;
    }

    protected GenericObjectList(String string, String string2) {
        this(string);
        try {
            this.myClass = Class.forName(string2);
        }
        catch (ClassNotFoundException classNotFoundException) {
            InternalErrorHandler.handleException(classNotFoundException);
        }
    }

    protected static boolean isCloneable(Object object) {
        return object instanceof Cloneable;
    }

    public static boolean isMySubclass(Class<?> class_) {
        return GenericObjectList.class.isAssignableFrom(class_);
    }

    private void sprint(String charSequence) {
        if (charSequence == null) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.stringRep);
            ((StringBuilder)charSequence).append(this.getIndentation());
            this.stringRep = ((StringBuilder)charSequence).toString();
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.stringRep);
            ((StringBuilder)charSequence).append("<null>\n");
            this.stringRep = ((StringBuilder)charSequence).toString();
            return;
        }
        if (((String)charSequence).compareTo("}") == 0 || ((String)charSequence).compareTo("]") == 0) {
            --this.indentation;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.stringRep);
        stringBuilder.append(this.getIndentation());
        this.stringRep = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.stringRep);
        stringBuilder.append((String)charSequence);
        this.stringRep = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(this.stringRep);
        stringBuilder.append(RETURN);
        this.stringRep = stringBuilder.toString();
        if (((String)charSequence).compareTo("{") == 0 || ((String)charSequence).compareTo("[") == 0) {
            ++this.indentation;
        }
    }

    @Override
    public void addFirst(GenericObject genericObject) {
        if (this.myClass == null) {
            this.myClass = genericObject.getClass();
        } else {
            super.addFirst(genericObject);
        }
    }

    @Override
    public Object clone() {
        GenericObjectList genericObjectList = (GenericObjectList)super.clone();
        ListIterator<GenericObject> listIterator = genericObjectList.listIterator();
        while (listIterator.hasNext()) {
            listIterator.set((GenericObject)((GenericObject)listIterator.next()).clone());
        }
        return genericObjectList;
    }

    protected void concatenate(GenericObjectList genericObjectList) {
        this.concatenate(genericObjectList, false);
    }

    protected void concatenate(GenericObjectList genericObjectList, boolean bl) {
        if (!bl) {
            this.addAll(genericObjectList);
        } else {
            this.addAll(0, genericObjectList);
        }
    }

    public String debugDump() {
        this.stringRep = "";
        GenericObject genericObject = this.first();
        if (genericObject == null) {
            return "<null>";
        }
        this.sprint("listName:");
        this.sprint(this.listName);
        this.sprint("{");
        while (genericObject != null) {
            this.sprint("[");
            this.sprint(genericObject.debugDump(this.indentation));
            genericObject = this.next();
            this.sprint("]");
        }
        this.sprint("}");
        return this.stringRep;
    }

    public String debugDump(int n) {
        int n2 = this.indentation;
        this.indentation = n;
        String string = this.debugDump();
        this.indentation = n2;
        return string;
    }

    public String encode() {
        if (this.isEmpty()) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        ListIterator listIterator = this.listIterator();
        if (listIterator.hasNext()) {
            do {
                Object e;
                if ((e = listIterator.next()) instanceof GenericObject) {
                    stringBuffer.append(((GenericObject)e).encode());
                } else {
                    stringBuffer.append(e.toString());
                }
                if (!listIterator.hasNext()) break;
                stringBuffer.append(this.separator);
            } while (true);
        }
        return stringBuffer.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public boolean equals(Object listIterator) {
        boolean bl;
        if (listIterator == null) {
            return false;
        }
        if (!this.getClass().equals(listIterator.getClass())) {
            return false;
        }
        Object object = (GenericObjectList)((Object)listIterator);
        if (this.size() != ((LinkedList)object).size()) {
            return false;
        }
        ListIterator listIterator2 = this.listIterator();
        do {
            if (!listIterator2.hasNext()) break;
            Object e = listIterator2.next();
            listIterator = ((AbstractList)object).listIterator();
            try {
                while (!(bl = e.equals(listIterator.next()))) {
                }
            }
            catch (NoSuchElementException noSuchElementException) {
                return false;
            }
        } while (true);
        listIterator2 = ((AbstractList)object).listIterator();
        while (listIterator2.hasNext()) {
            listIterator = listIterator2.next();
            object = this.listIterator();
            try {
                while (!(bl = listIterator.equals(object.next()))) {
                }
            }
            catch (NoSuchElementException noSuchElementException) {
                return false;
            }
        }
        return true;
    }

    protected GenericObject first() {
        this.myListIterator = this.listIterator(0);
        try {
            GenericObject genericObject = this.myListIterator.next();
            return genericObject;
        }
        catch (NoSuchElementException noSuchElementException) {
            return null;
        }
    }

    protected String getIndentation() {
        char[] arrc = new char[this.indentation];
        Arrays.fill(arrc, ' ');
        return new String(arrc);
    }

    @Override
    public int hashCode() {
        return 42;
    }

    public boolean match(Object object) {
        block4 : {
            if (!this.getClass().equals(object.getClass())) {
                return false;
            }
            if ((object = ((GenericObjectList)object).listIterator()).hasNext()) {
                Object e = object.next();
                ListIterator listIterator = this.listIterator();
                while (listIterator.hasNext()) {
                    Object e2 = listIterator.next();
                    if (e2 instanceof GenericObject) {
                        object = System.out;
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append("Trying to match  = ");
                        stringBuilder.append(((GenericObject)e2).encode());
                        ((PrintStream)object).println(stringBuilder.toString());
                    }
                    if ((!GenericObject.isMySubclass(e2.getClass()) || !((GenericObject)e2).match(e)) && (!GenericObjectList.isMySubclass(e2.getClass()) || !((GenericObjectList)e2).match(e))) continue;
                    break block4;
                }
                System.out.println(((GenericObject)e).encode());
                return false;
            }
        }
        return true;
    }

    public void mergeObjects(GenericObjectList cloneable) {
        if (cloneable == null) {
            return;
        }
        ListIterator listIterator = this.listIterator();
        ListIterator listIterator2 = ((AbstractList)((Object)cloneable)).listIterator();
        while (listIterator.hasNext()) {
            cloneable = (GenericObject)listIterator.next();
            while (listIterator2.hasNext()) {
                ((GenericObject)cloneable).merge(listIterator2.next());
            }
        }
    }

    protected GenericObject next() {
        if (this.myListIterator == null) {
            this.myListIterator = this.listIterator(0);
        }
        try {
            GenericObject genericObject = this.myListIterator.next();
            return genericObject;
        }
        catch (NoSuchElementException noSuchElementException) {
            this.myListIterator = null;
            return null;
        }
    }

    protected GenericObject next(ListIterator object) {
        try {
            object = (GenericObject)object.next();
            return object;
        }
        catch (NoSuchElementException noSuchElementException) {
            return null;
        }
    }

    public void setMyClass(Class class_) {
        this.myClass = class_;
    }

    public void setSeparator(String string) {
        this.separator = string;
    }

    @Override
    public String toString() {
        return this.encode();
    }
}

