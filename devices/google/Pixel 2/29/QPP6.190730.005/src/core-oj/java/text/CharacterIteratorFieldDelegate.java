/*
 * Decompiled with CFR 0.145.
 */
package java.text;

import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.text.Format;
import java.util.ArrayList;

class CharacterIteratorFieldDelegate
implements Format.FieldDelegate {
    private ArrayList<AttributedString> attributedStrings = new ArrayList();
    private int size;

    CharacterIteratorFieldDelegate() {
    }

    @Override
    public void formatted(int n, Format.Field field, Object object, int n2, int n3, StringBuffer stringBuffer) {
        this.formatted(field, object, n2, n3, stringBuffer);
    }

    @Override
    public void formatted(Format.Field field, Object object, int n, int n2, StringBuffer object2) {
        if (n != n2) {
            int n3;
            if (n < this.size) {
                int n4 = this.size;
                n3 = this.attributedStrings.size() - 1;
                while (n < n4) {
                    AttributedString attributedString = this.attributedStrings.get(n3);
                    int n5 = Math.max(0, n - (n4 -= attributedString.length()));
                    attributedString.addAttribute(field, object, n5, Math.min(n2 - n, attributedString.length() - n5) + n5);
                    --n3;
                }
            }
            if ((n3 = this.size) < n) {
                this.attributedStrings.add(new AttributedString(((StringBuffer)object2).substring(n3, n)));
                this.size = n;
            }
            if ((n3 = this.size) < n2) {
                object2 = new AttributedString(((StringBuffer)object2).substring(Math.max(n, n3), n2));
                ((AttributedString)object2).addAttribute(field, object);
                this.attributedStrings.add((AttributedString)object2);
                this.size = n2;
            }
        }
    }

    public AttributedCharacterIterator getIterator(String arrattributedCharacterIterator) {
        int n;
        int n2 = arrattributedCharacterIterator.length();
        if (n2 > (n = this.size)) {
            this.attributedStrings.add(new AttributedString(arrattributedCharacterIterator.substring(n)));
            this.size = arrattributedCharacterIterator.length();
        }
        n2 = this.attributedStrings.size();
        arrattributedCharacterIterator = new AttributedCharacterIterator[n2];
        for (n = 0; n < n2; ++n) {
            arrattributedCharacterIterator[n] = this.attributedStrings.get(n).getIterator();
        }
        return new AttributedString(arrattributedCharacterIterator).getIterator();
    }
}

