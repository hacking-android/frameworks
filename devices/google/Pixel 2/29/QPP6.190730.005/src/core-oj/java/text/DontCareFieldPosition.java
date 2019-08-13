/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.text.FieldPosition;
import java.text.Format;

class DontCareFieldPosition
extends FieldPosition {
    static final FieldPosition INSTANCE = new DontCareFieldPosition();
    private final Format.FieldDelegate noDelegate = new Format.FieldDelegate(){

        @Override
        public void formatted(int n, Format.Field field, Object object, int n2, int n3, StringBuffer stringBuffer) {
        }

        @Override
        public void formatted(Format.Field field, Object object, int n, int n2, StringBuffer stringBuffer) {
        }
    };

    private DontCareFieldPosition() {
        super(0);
    }

    @Override
    Format.FieldDelegate getFieldDelegate() {
        return this.noDelegate;
    }

}

