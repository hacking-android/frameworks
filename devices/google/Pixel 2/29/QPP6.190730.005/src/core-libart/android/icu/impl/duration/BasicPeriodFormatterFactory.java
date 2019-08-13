/*
 * Decompiled with CFR 0.145.
 */
package android.icu.impl.duration;

import android.icu.impl.duration.BasicPeriodFormatter;
import android.icu.impl.duration.BasicPeriodFormatterService;
import android.icu.impl.duration.PeriodFormatter;
import android.icu.impl.duration.PeriodFormatterFactory;
import android.icu.impl.duration.impl.PeriodFormatterData;
import android.icu.impl.duration.impl.PeriodFormatterDataService;
import java.util.Locale;

public class BasicPeriodFormatterFactory
implements PeriodFormatterFactory {
    private Customizations customizations;
    private boolean customizationsInUse;
    private PeriodFormatterData data;
    private final PeriodFormatterDataService ds;
    private String localeName;

    BasicPeriodFormatterFactory(PeriodFormatterDataService periodFormatterDataService) {
        this.ds = periodFormatterDataService;
        this.customizations = new Customizations();
        this.localeName = Locale.getDefault().toString();
    }

    public static BasicPeriodFormatterFactory getDefault() {
        return (BasicPeriodFormatterFactory)BasicPeriodFormatterService.getInstance().newPeriodFormatterFactory();
    }

    private Customizations updateCustomizations() {
        if (this.customizationsInUse) {
            this.customizations = this.customizations.copy();
            this.customizationsInUse = false;
        }
        return this.customizations;
    }

    public int getCountVariant() {
        return this.customizations.countVariant;
    }

    PeriodFormatterData getData() {
        if (this.data == null) {
            this.data = this.ds.get(this.localeName);
        }
        return this.data;
    }

    PeriodFormatterData getData(String string) {
        return this.ds.get(string);
    }

    public boolean getDisplayLimit() {
        return this.customizations.displayLimit;
    }

    public boolean getDisplayPastFuture() {
        return this.customizations.displayDirection;
    }

    @Override
    public PeriodFormatter getFormatter() {
        this.customizationsInUse = true;
        return new BasicPeriodFormatter(this, this.localeName, this.getData(), this.customizations);
    }

    public int getSeparatorVariant() {
        return this.customizations.separatorVariant;
    }

    public int getUnitVariant() {
        return this.customizations.unitVariant;
    }

    @Override
    public PeriodFormatterFactory setCountVariant(int n) {
        this.updateCustomizations().countVariant = (byte)n;
        return this;
    }

    @Override
    public PeriodFormatterFactory setDisplayLimit(boolean bl) {
        this.updateCustomizations().displayLimit = bl;
        return this;
    }

    @Override
    public PeriodFormatterFactory setDisplayPastFuture(boolean bl) {
        this.updateCustomizations().displayDirection = bl;
        return this;
    }

    @Override
    public PeriodFormatterFactory setLocale(String string) {
        this.data = null;
        this.localeName = string;
        return this;
    }

    @Override
    public PeriodFormatterFactory setSeparatorVariant(int n) {
        this.updateCustomizations().separatorVariant = (byte)n;
        return this;
    }

    @Override
    public PeriodFormatterFactory setUnitVariant(int n) {
        this.updateCustomizations().unitVariant = (byte)n;
        return this;
    }

    static class Customizations {
        byte countVariant = (byte)(false ? 1 : 0);
        boolean displayDirection = true;
        boolean displayLimit = true;
        byte separatorVariant = (byte)2;
        byte unitVariant = (byte)(false ? 1 : 0);

        Customizations() {
        }

        public Customizations copy() {
            Customizations customizations = new Customizations();
            customizations.displayLimit = this.displayLimit;
            customizations.displayDirection = this.displayDirection;
            customizations.separatorVariant = this.separatorVariant;
            customizations.unitVariant = this.unitVariant;
            customizations.countVariant = this.countVariant;
            return customizations;
        }
    }

}

