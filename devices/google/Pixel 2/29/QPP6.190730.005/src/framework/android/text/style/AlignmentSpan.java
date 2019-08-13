/*
 * Decompiled with CFR 0.145.
 */
package android.text.style;

import android.os.Parcel;
import android.text.Layout;
import android.text.ParcelableSpan;
import android.text.style.ParagraphStyle;

public interface AlignmentSpan
extends ParagraphStyle {
    public Layout.Alignment getAlignment();

    public static class Standard
    implements AlignmentSpan,
    ParcelableSpan {
        private final Layout.Alignment mAlignment;

        public Standard(Parcel parcel) {
            this.mAlignment = Layout.Alignment.valueOf(parcel.readString());
        }

        public Standard(Layout.Alignment alignment) {
            this.mAlignment = alignment;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public Layout.Alignment getAlignment() {
            return this.mAlignment;
        }

        @Override
        public int getSpanTypeId() {
            return this.getSpanTypeIdInternal();
        }

        @Override
        public int getSpanTypeIdInternal() {
            return 1;
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            this.writeToParcelInternal(parcel, n);
        }

        @Override
        public void writeToParcelInternal(Parcel parcel, int n) {
            parcel.writeString(this.mAlignment.name());
        }
    }

}

