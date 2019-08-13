/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.CacheValue;
import android.icu.impl.ICUResourceBundle;
import android.icu.impl.ICUResourceBundleReader;
import android.icu.util.UResourceBundle;
import android.icu.util.UResourceTypeMismatchException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

class ICUResourceBundleImpl
extends ICUResourceBundle {
    protected int resource;

    ICUResourceBundleImpl(ICUResourceBundle.WholeBundle wholeBundle) {
        super(wholeBundle);
        this.resource = wholeBundle.reader.getRootResource();
    }

    protected ICUResourceBundleImpl(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
        super(iCUResourceBundleImpl, string);
        this.resource = n;
    }

    protected final ICUResourceBundle createBundleObject(String string, int n, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
        int n2 = ICUResourceBundleReader.RES_GET_TYPE(n);
        if (n2 != 14) {
            switch (n2) {
                default: {
                    throw new IllegalStateException("The resource type is unknown");
                }
                case 8: 
                case 9: {
                    return new ResourceArray(this, string, n);
                }
                case 7: {
                    return new ResourceInt(this, string, n);
                }
                case 3: {
                    return ICUResourceBundleImpl.getAliasedResource(this, null, 0, string, n, hashMap, uResourceBundle);
                }
                case 2: 
                case 4: 
                case 5: {
                    return new ResourceTable(this, string, n);
                }
                case 1: {
                    return new ResourceBinary(this, string, n);
                }
                case 0: 
                case 6: 
            }
            return new ResourceString(this, string, n);
        }
        return new ResourceIntVector(this, string, n);
    }

    public int getResource() {
        return this.resource;
    }

    static class ResourceArray
    extends ResourceContainer {
        ResourceArray(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
            this.value = this.wholeBundle.reader.getArray(n);
        }

        @Override
        public String[] getStringArray() {
            return this.handleGetStringArray();
        }

        @Override
        public int getType() {
            return 8;
        }

        @Override
        protected UResourceBundle handleGet(int n, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            return this.createBundleObject(n, Integer.toString(n), hashMap, uResourceBundle);
        }

        @Override
        protected UResourceBundle handleGet(String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            return this.createBundleObject(Integer.parseInt(string), string, hashMap, uResourceBundle);
        }

        @Override
        protected String[] handleGetStringArray() {
            ICUResourceBundleReader iCUResourceBundleReader = this.wholeBundle.reader;
            int n = this.value.getSize();
            String[] arrstring = new String[n];
            for (int i = 0; i < n; ++i) {
                String string = iCUResourceBundleReader.getString(this.value.getContainerResource(iCUResourceBundleReader, i));
                if (string != null) {
                    arrstring[i] = string;
                    continue;
                }
                throw new UResourceTypeMismatchException("");
            }
            return arrstring;
        }
    }

    private static final class ResourceBinary
    extends ICUResourceBundleImpl {
        ResourceBinary(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
        }

        @Override
        public ByteBuffer getBinary() {
            return this.wholeBundle.reader.getBinary(this.resource);
        }

        @Override
        public byte[] getBinary(byte[] arrby) {
            return this.wholeBundle.reader.getBinary(this.resource, arrby);
        }

        @Override
        public int getType() {
            return 1;
        }
    }

    static abstract class ResourceContainer
    extends ICUResourceBundleImpl {
        protected ICUResourceBundleReader.Container value;

        ResourceContainer(ICUResourceBundle.WholeBundle wholeBundle) {
            super(wholeBundle);
        }

        ResourceContainer(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
        }

        protected UResourceBundle createBundleObject(int n, String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            if ((n = this.getContainerResource(n)) != -1) {
                return this.createBundleObject(string, n, hashMap, uResourceBundle);
            }
            throw new IndexOutOfBoundsException();
        }

        protected int getContainerResource(int n) {
            return this.value.getContainerResource(this.wholeBundle.reader, n);
        }

        @Override
        public int getSize() {
            return this.value.getSize();
        }

        @Override
        public String getString(int n) {
            int n2 = this.value.getContainerResource(this.wholeBundle.reader, n);
            if (n2 != -1) {
                String string = this.wholeBundle.reader.getString(n2);
                if (string != null) {
                    return string;
                }
                return super.getString(n);
            }
            throw new IndexOutOfBoundsException();
        }
    }

    private static final class ResourceInt
    extends ICUResourceBundleImpl {
        ResourceInt(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
        }

        @Override
        public int getInt() {
            return ICUResourceBundleReader.RES_GET_INT(this.resource);
        }

        @Override
        public int getType() {
            return 7;
        }

        @Override
        public int getUInt() {
            return ICUResourceBundleReader.RES_GET_UINT(this.resource);
        }
    }

    private static final class ResourceIntVector
    extends ICUResourceBundleImpl {
        ResourceIntVector(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
        }

        @Override
        public int[] getIntVector() {
            return this.wholeBundle.reader.getIntVector(this.resource);
        }

        @Override
        public int getType() {
            return 14;
        }
    }

    private static final class ResourceString
    extends ICUResourceBundleImpl {
        private String value;

        ResourceString(ICUResourceBundleImpl object, String string, int n) {
            super((ICUResourceBundleImpl)object, string, n);
            object = this.wholeBundle.reader.getString(n);
            if (((String)object).length() < 12 || CacheValue.futureInstancesWillBeStrong()) {
                this.value = object;
            }
        }

        @Override
        public String getString() {
            String string = this.value;
            if (string != null) {
                return string;
            }
            return this.wholeBundle.reader.getString(this.resource);
        }

        @Override
        public int getType() {
            return 0;
        }
    }

    static class ResourceTable
    extends ResourceContainer {
        ResourceTable(ICUResourceBundle.WholeBundle wholeBundle, int n) {
            super(wholeBundle);
            this.value = wholeBundle.reader.getTable(n);
        }

        ResourceTable(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
            this.value = this.wholeBundle.reader.getTable(n);
        }

        String findString(String string) {
            ICUResourceBundleReader iCUResourceBundleReader = this.wholeBundle.reader;
            int n = ((ICUResourceBundleReader.Table)this.value).findTableItem(iCUResourceBundleReader, string);
            if (n < 0) {
                return null;
            }
            return iCUResourceBundleReader.getString(this.value.getContainerResource(iCUResourceBundleReader, n));
        }

        protected String getKey(int n) {
            return ((ICUResourceBundleReader.Table)this.value).getKey(this.wholeBundle.reader, n);
        }

        @Override
        public int getType() {
            return 2;
        }

        @Override
        protected UResourceBundle handleGet(int n, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            String string = ((ICUResourceBundleReader.Table)this.value).getKey(this.wholeBundle.reader, n);
            if (string != null) {
                return this.createBundleObject(string, this.getContainerResource(n), hashMap, uResourceBundle);
            }
            throw new IndexOutOfBoundsException();
        }

        @Override
        protected UResourceBundle handleGet(String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            int n = ((ICUResourceBundleReader.Table)this.value).findTableItem(this.wholeBundle.reader, string);
            if (n < 0) {
                return null;
            }
            return this.createBundleObject(string, this.getContainerResource(n), hashMap, uResourceBundle);
        }

        @Override
        protected Object handleGetObject(String string) {
            ICUResourceBundleReader iCUResourceBundleReader = this.wholeBundle.reader;
            int n = ((ICUResourceBundleReader.Table)this.value).findTableItem(iCUResourceBundleReader, string);
            if (n >= 0) {
                String string2 = iCUResourceBundleReader.getString(n = this.value.getContainerResource(iCUResourceBundleReader, n));
                if (string2 != null) {
                    return string2;
                }
                ICUResourceBundleReader.Array array = iCUResourceBundleReader.getArray(n);
                if (array != null) {
                    int n2 = array.getSize();
                    String[] arrstring = new String[n2];
                    n = 0;
                    do {
                        if (n == n2) {
                            return arrstring;
                        }
                        string2 = iCUResourceBundleReader.getString(array.getContainerResource(iCUResourceBundleReader, n));
                        if (string2 == null) break;
                        arrstring[n] = string2;
                        ++n;
                    } while (true);
                }
            }
            return super.handleGetObject(string);
        }

        @Override
        protected Set<String> handleKeySet() {
            ICUResourceBundleReader iCUResourceBundleReader = this.wholeBundle.reader;
            TreeSet<String> treeSet = new TreeSet<String>();
            ICUResourceBundleReader.Table table = (ICUResourceBundleReader.Table)this.value;
            for (int i = 0; i < table.getSize(); ++i) {
                treeSet.add(table.getKey(iCUResourceBundleReader, i));
            }
            return treeSet;
        }
    }

}

