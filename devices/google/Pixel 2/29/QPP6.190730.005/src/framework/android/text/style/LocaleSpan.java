/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.graphics.Paint;
import android.os.LocaleList;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.ParcelableSpan;
import android.text.TextPaint;
import android.text.style.MetricAffectingSpan;
import com.android.internal.util.Preconditions;
import java.util.Locale;

public class LocaleSpan
extends MetricAffectingSpan
implements ParcelableSpan {
    private final LocaleList mLocales;

    public LocaleSpan(LocaleList localeList) {
        Preconditions.checkNotNull(localeList, "locales cannot be null");
        this.mLocales = localeList;
    }

    public LocaleSpan(Parcel parcel) {
        this.mLocales = LocaleList.CREATOR.createFromParcel(parcel);
    }

    public LocaleSpan(Locale object) {
        object = object == null ? LocaleList.getEmptyLocaleList() : new LocaleList(new Locale[]{object});
        this.mLocales = object;
    }

    private static void apply(Paint paint, LocaleList localeList) {
        paint.setTextLocales(localeList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public Locale getLocale() {
        return this.mLocales.get(0);
    }

    public LocaleList getLocales() {
        return this.mLocales;
    }

    @Override
    public int getSpanTypeId() {
        return this.getSpanTypeIdInternal();
    }

    @Override
    public int getSpanTypeIdInternal() {
        return 23;
    }

    @Override
    public void updateDrawState(TextPaint textPaint) {
        LocaleSpan.apply(textPaint, this.mLocales);
    }

    @Override
    public void updateMeasureState(TextPaint textPaint) {
        LocaleSpan.apply(textPaint, this.mLocales);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.writeToParcelInternal(parcel, n);
    }

    @Override
    public void writeToParcelInternal(Parcel parcel, int n) {
        this.mLocales.writeToParcel(parcel, n);
    }
}

