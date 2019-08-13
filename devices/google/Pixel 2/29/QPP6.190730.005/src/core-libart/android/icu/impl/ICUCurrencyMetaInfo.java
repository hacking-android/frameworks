/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl;

import android.icu.impl.ICUResourceBundle;
import android.icu.text.CurrencyMetaInfo;
import android.icu.util.Currency;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ICUCurrencyMetaInfo
extends CurrencyMetaInfo {
    private static final int Currency = 2;
    private static final int Date = 4;
    private static final int Everything = Integer.MAX_VALUE;
    private static final long MASK = 0xFFFFFFFFL;
    private static final int Region = 1;
    private static final int Tender = 8;
    private ICUResourceBundle digitInfo;
    private ICUResourceBundle regionInfo;

    public ICUCurrencyMetaInfo() {
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)ICUResourceBundle.getBundleInstance("android/icu/impl/data/icudt63b/curr", "supplementalData", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        this.regionInfo = iCUResourceBundle.findTopLevel("CurrencyMap");
        this.digitInfo = iCUResourceBundle.findTopLevel("CurrencyMeta");
    }

    private <T> List<T> collect(Collector<T> collector, CurrencyMetaInfo.CurrencyFilter object) {
        int n;
        CurrencyMetaInfo.CurrencyFilter currencyFilter;
        int n2;
        block15 : {
            block14 : {
                currencyFilter = object;
                if (object == null) {
                    currencyFilter = CurrencyMetaInfo.CurrencyFilter.all();
                }
                n2 = n = collector.collects();
                if (currencyFilter.region != null) {
                    n2 = n | 1;
                }
                n = n2;
                if (currencyFilter.currency != null) {
                    n = n2 | 2;
                }
                if (currencyFilter.from != Long.MIN_VALUE) break block14;
                n2 = n;
                if (currencyFilter.to == Long.MAX_VALUE) break block15;
            }
            n2 = n | 4;
        }
        n = n2;
        if (currencyFilter.tenderOnly) {
            n = n2 | 8;
        }
        if (n != 0) {
            if (currencyFilter.region != null) {
                object = this.regionInfo.findWithFallback(currencyFilter.region);
                if (object != null) {
                    this.collectRegion(collector, currencyFilter, n, (ICUResourceBundle)object);
                }
            } else {
                for (n2 = 0; n2 < this.regionInfo.getSize(); ++n2) {
                    this.collectRegion(collector, currencyFilter, n, this.regionInfo.at(n2));
                }
            }
        }
        return collector.getList();
    }

    private <T> void collectRegion(Collector<T> collector, CurrencyMetaInfo.CurrencyFilter currencyFilter, int n, ICUResourceBundle iCUResourceBundle) {
        String string = iCUResourceBundle.getKey();
        boolean bl = true;
        if (n == 1) {
            collector.collect(iCUResourceBundle.getKey(), null, 0L, 0L, -1, false);
            return;
        }
        for (int i = 0; i < iCUResourceBundle.getSize(); ++i) {
            long l;
            String string2;
            long l2;
            ICUResourceBundle iCUResourceBundle2 = iCUResourceBundle.at(i);
            if (iCUResourceBundle2.getSize() == 0) continue;
            if ((n & 2) != 0) {
                string2 = iCUResourceBundle2.at("id").getString();
                if (currencyFilter.currency != null && !currencyFilter.currency.equals(string2)) {
                    continue;
                }
            } else {
                string2 = null;
            }
            boolean bl2 = false;
            if ((n & 4) != 0) {
                l = this.getDate(iCUResourceBundle2.at("from"), Long.MIN_VALUE, false);
                ICUResourceBundle iCUResourceBundle3 = iCUResourceBundle2.at("to");
                bl = true;
                l2 = this.getDate(iCUResourceBundle3, Long.MAX_VALUE, true);
                if (currencyFilter.from > l2) {
                    bl = true;
                    continue;
                }
                if (currencyFilter.to < l) {
                    bl = true;
                    continue;
                }
            } else {
                l = Long.MIN_VALUE;
                l2 = Long.MAX_VALUE;
            }
            if ((n & 8) != 0) {
                if ((iCUResourceBundle2 = iCUResourceBundle2.at("tender")) == null || "true".equals(iCUResourceBundle2.getString())) {
                    bl2 = bl;
                }
                if (currencyFilter.tenderOnly && !bl2) {
                    continue;
                }
            } else {
                bl2 = true;
            }
            collector.collect(string, string2, l, l2, i, bl2);
        }
    }

    private long getDate(ICUResourceBundle arrn, long l, boolean bl) {
        if (arrn == null) {
            return l;
        }
        arrn = arrn.getIntVector();
        return (long)arrn[0] << 32 | (long)arrn[1] & 0xFFFFFFFFL;
    }

    @Override
    public List<String> currencies(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return this.collect(new CurrencyCollector(), currencyFilter);
    }

    @Override
    public CurrencyMetaInfo.CurrencyDigits currencyDigits(String string) {
        return this.currencyDigits(string, Currency.CurrencyUsage.STANDARD);
    }

    @Override
    public CurrencyMetaInfo.CurrencyDigits currencyDigits(String object, Currency.CurrencyUsage currencyUsage) {
        ICUResourceBundle iCUResourceBundle = this.digitInfo.findWithFallback((String)object);
        object = iCUResourceBundle;
        if (iCUResourceBundle == null) {
            object = this.digitInfo.findWithFallback("DEFAULT");
        }
        object = object.getIntVector();
        if (currencyUsage == Currency.CurrencyUsage.CASH) {
            return new CurrencyMetaInfo.CurrencyDigits(object[2], object[3]);
        }
        if (currencyUsage == Currency.CurrencyUsage.STANDARD) {
            return new CurrencyMetaInfo.CurrencyDigits(object[0], object[1]);
        }
        return new CurrencyMetaInfo.CurrencyDigits(object[0], object[1]);
    }

    @Override
    public List<CurrencyMetaInfo.CurrencyInfo> currencyInfo(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return this.collect(new InfoCollector(), currencyFilter);
    }

    @Override
    public List<String> regions(CurrencyMetaInfo.CurrencyFilter currencyFilter) {
        return this.collect(new RegionCollector(), currencyFilter);
    }

    private static interface Collector<T> {
        public void collect(String var1, String var2, long var3, long var5, int var7, boolean var8);

        public int collects();

        public List<T> getList();
    }

    private static class CurrencyCollector
    implements Collector<String> {
        private final UniqueList<String> result = UniqueList.access$300();

        private CurrencyCollector() {
        }

        @Override
        public void collect(String string, String string2, long l, long l2, int n, boolean bl) {
            this.result.add(string2);
        }

        @Override
        public int collects() {
            return 2;
        }

        @Override
        public List<String> getList() {
            return this.result.list();
        }
    }

    private static class InfoCollector
    implements Collector<CurrencyMetaInfo.CurrencyInfo> {
        private List<CurrencyMetaInfo.CurrencyInfo> result = new ArrayList<CurrencyMetaInfo.CurrencyInfo>();

        private InfoCollector() {
        }

        @Override
        public void collect(String string, String string2, long l, long l2, int n, boolean bl) {
            this.result.add(new CurrencyMetaInfo.CurrencyInfo(string, string2, l, l2, n, bl));
        }

        @Override
        public int collects() {
            return Integer.MAX_VALUE;
        }

        @Override
        public List<CurrencyMetaInfo.CurrencyInfo> getList() {
            return Collections.unmodifiableList(this.result);
        }
    }

    private static class RegionCollector
    implements Collector<String> {
        private final UniqueList<String> result = UniqueList.access$300();

        private RegionCollector() {
        }

        @Override
        public void collect(String string, String string2, long l, long l2, int n, boolean bl) {
            this.result.add(string);
        }

        @Override
        public int collects() {
            return 1;
        }

        @Override
        public List<String> getList() {
            return this.result.list();
        }
    }

    private static class UniqueList<T> {
        private List<T> list = new ArrayList<T>();
        private Set<T> seen = new HashSet<T>();

        private UniqueList() {
        }

        static /* synthetic */ UniqueList access$300() {
            return UniqueList.create();
        }

        private static <T> UniqueList<T> create() {
            return new UniqueList<T>();
        }

        void add(T t) {
            if (!this.seen.contains(t)) {
                this.list.add(t);
                this.seen.add(t);
            }
        }

        List<T> list() {
            return Collections.unmodifiableList(this.list);
        }
    }

}

