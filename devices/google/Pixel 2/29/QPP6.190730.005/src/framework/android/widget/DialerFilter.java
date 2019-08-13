/*
 * Decompiled with CFR 0.145.
 */
package android.widget;

import android.content.Context;
import android.graphics.Rect;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.text.method.DialerKeyListener;
import android.text.method.KeyListener;
import android.text.method.MovementMethod;
import android.text.method.TextKeyListener;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

@Deprecated
public class DialerFilter
extends RelativeLayout {
    public static final int DIGITS_AND_LETTERS = 1;
    public static final int DIGITS_AND_LETTERS_NO_DIGITS = 2;
    public static final int DIGITS_AND_LETTERS_NO_LETTERS = 3;
    public static final int DIGITS_ONLY = 4;
    public static final int LETTERS_ONLY = 5;
    EditText mDigits;
    EditText mHint;
    ImageView mIcon;
    InputFilter[] mInputFilters;
    private boolean mIsQwerty;
    EditText mLetters;
    int mMode;
    EditText mPrimary;

    public DialerFilter(Context context) {
        super(context);
    }

    public DialerFilter(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    private void makeDigitsPrimary() {
        if (this.mPrimary == this.mLetters) {
            this.swapPrimaryAndHint(false);
        }
    }

    private void makeLettersPrimary() {
        if (this.mPrimary == this.mDigits) {
            this.swapPrimaryAndHint(true);
        }
    }

    private void swapPrimaryAndHint(boolean bl) {
        Editable editable = this.mLetters.getText();
        Editable editable2 = this.mDigits.getText();
        Object object = this.mLetters.getKeyListener();
        KeyListener keyListener = this.mDigits.getKeyListener();
        if (bl) {
            this.mLetters = this.mPrimary;
            this.mDigits = this.mHint;
        } else {
            this.mLetters = this.mHint;
            this.mDigits = this.mPrimary;
        }
        this.mLetters.setKeyListener((KeyListener)object);
        this.mLetters.setText(editable);
        object = this.mLetters.getText();
        Selection.setSelection((Spannable)object, object.length());
        this.mDigits.setKeyListener(keyListener);
        this.mDigits.setText(editable2);
        editable2 = this.mDigits.getText();
        Selection.setSelection(editable2, editable2.length());
        this.mPrimary.setFilters(this.mInputFilters);
        this.mHint.setFilters(this.mInputFilters);
    }

    public void append(String string2) {
        block3 : {
            block0 : {
                block1 : {
                    block2 : {
                        int n = this.mMode;
                        if (n == 1) break block0;
                        if (n == 2) break block1;
                        if (n == 3 || n == 4) break block2;
                        if (n == 5) break block1;
                        break block3;
                    }
                    this.mDigits.getText().append(string2);
                    break block3;
                }
                this.mLetters.getText().append(string2);
                break block3;
            }
            this.mDigits.getText().append(string2);
            this.mLetters.getText().append(string2);
        }
    }

    public void clearText() {
        this.mLetters.getText().clear();
        this.mDigits.getText().clear();
        if (this.mIsQwerty) {
            this.setMode(1);
        } else {
            this.setMode(4);
        }
    }

    public CharSequence getDigits() {
        if (this.mDigits.getVisibility() == 0) {
            return this.mDigits.getText();
        }
        return "";
    }

    public CharSequence getFilterText() {
        if (this.mMode != 4) {
            return this.getLetters();
        }
        return this.getDigits();
    }

    public CharSequence getLetters() {
        if (this.mLetters.getVisibility() == 0) {
            return this.mLetters.getText();
        }
        return "";
    }

    public int getMode() {
        return this.mMode;
    }

    public boolean isQwertyKeyboard() {
        return this.mIsQwerty;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mInputFilters = new InputFilter[]{new InputFilter.AllCaps()};
        EditText editText = this.mHint = (EditText)this.findViewById(16908293);
        if (editText != null) {
            editText.setFilters(this.mInputFilters);
            this.mLetters = this.mHint;
            this.mLetters.setKeyListener(TextKeyListener.getInstance());
            this.mLetters.setMovementMethod(null);
            this.mLetters.setFocusable(false);
            editText = this.mPrimary = (EditText)this.findViewById(16908300);
            if (editText != null) {
                editText.setFilters(this.mInputFilters);
                this.mDigits = this.mPrimary;
                this.mDigits.setKeyListener(DialerKeyListener.getInstance());
                this.mDigits.setMovementMethod(null);
                this.mDigits.setFocusable(false);
                this.mIcon = (ImageView)this.findViewById(16908294);
                this.setFocusable(true);
                this.mIsQwerty = true;
                this.setMode(1);
                return;
            }
            throw new IllegalStateException("DialerFilter must have a child EditText named primary");
        }
        throw new IllegalStateException("DialerFilter must have a child EditText named hint");
    }

    @Override
    protected void onFocusChanged(boolean bl, int n, Rect object) {
        super.onFocusChanged(bl, n, (Rect)object);
        object = this.mIcon;
        if (object != null) {
            n = bl ? 0 : 8;
            ((ImageView)object).setVisibility(n);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    public boolean onKeyDown(int var1_1, KeyEvent var2_2) {
        var3_3 = false;
        var4_4 = false;
        if (var1_1 == 66) ** GOTO lbl-1000
        if (var1_1 == 67) ** GOTO lbl34
        switch (var1_1) {
            default: {
                var5_5 = this.mMode;
                if (var5_5 != 1) {
                    if (var5_5 != 2) {
                        if (var5_5 != 3 && var5_5 != 4) {
                            if (var5_5 != 5) {
                                var4_4 = var3_3;
                                break;
                            }
                        } else {
                            var4_4 = this.mDigits.onKeyDown(var1_1, var2_2);
                            break;
                        }
                    }
                    var4_4 = this.mLetters.onKeyDown(var1_1, var2_2);
                    break;
                }
                var3_3 = this.mLetters.onKeyDown(var1_1, var2_2);
                if (KeyEvent.isModifierKey(var1_1)) {
                    this.mDigits.onKeyDown(var1_1, var2_2);
                    var4_4 = true;
                    break;
                }
                if (!var2_2.isPrintingKey() && var1_1 != 62) {
                    var4_4 = var3_3;
                    if (var1_1 != 61) break;
                }
                if (var2_2.getMatch(DialerKeyListener.CHARACTERS) != '\u0000') {
                    var4_4 = var3_3 & this.mDigits.onKeyDown(var1_1, var2_2);
                    break;
                }
                this.setMode(2);
                var4_4 = var3_3;
                break;
            }
lbl34: // 1 sources:
            var5_6 = this.mMode;
            if (var5_6 != 1) {
                if (var5_6 != 2) {
                    if (var5_6 != 3) {
                        if (var5_6 != 4) {
                            if (var5_6 != 5) break;
                            var4_4 = this.mLetters.onKeyDown(var1_1, var2_2);
                            break;
                        }
                        var4_4 = this.mDigits.onKeyDown(var1_1, var2_2);
                        break;
                    }
                    if (this.mDigits.getText().length() == this.mLetters.getText().length()) {
                        this.mLetters.onKeyDown(var1_1, var2_2);
                        this.setMode(1);
                    }
                    var4_4 = this.mDigits.onKeyDown(var1_1, var2_2);
                    break;
                }
                var4_4 = var3_3 = this.mLetters.onKeyDown(var1_1, var2_2);
                if (this.mLetters.getText().length() != this.mDigits.getText().length()) break;
                this.setMode(1);
                var4_4 = var3_3;
                break;
            }
            var4_4 = this.mDigits.onKeyDown(var1_1, var2_2) & this.mLetters.onKeyDown(var1_1, var2_2);
            break;
            case 19: 
            case 20: 
            case 21: 
            case 22: 
            case 23: lbl-1000: // 2 sources:
            {
                var4_4 = var3_3;
            }
        }
        if (var4_4 != false) return true;
        return super.onKeyDown(var1_1, var2_2);
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        boolean bl = this.mLetters.onKeyUp(n, keyEvent);
        boolean bl2 = this.mDigits.onKeyUp(n, keyEvent);
        bl2 = bl || bl2;
        return bl2;
    }

    protected void onModeChange(int n, int n2) {
    }

    public void removeFilterWatcher(TextWatcher textWatcher) {
        Editable editable = this.mMode != 4 ? this.mLetters.getText() : this.mDigits.getText();
        editable.removeSpan(textWatcher);
    }

    public void setDigitsWatcher(TextWatcher textWatcher) {
        Editable editable = this.mDigits.getText();
        ((Spannable)editable).setSpan(textWatcher, 0, editable.length(), 18);
    }

    public void setFilterWatcher(TextWatcher textWatcher) {
        if (this.mMode != 4) {
            this.setLettersWatcher(textWatcher);
        } else {
            this.setDigitsWatcher(textWatcher);
        }
    }

    public void setLettersWatcher(TextWatcher textWatcher) {
        Editable editable = this.mLetters.getText();
        ((Spannable)editable).setSpan(textWatcher, 0, editable.length(), 18);
    }

    public void setMode(int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 3) {
                    if (n != 4) {
                        if (n == 5) {
                            this.makeLettersPrimary();
                            this.mLetters.setVisibility(0);
                            this.mDigits.setVisibility(8);
                        }
                    } else {
                        this.makeDigitsPrimary();
                        this.mLetters.setVisibility(8);
                        this.mDigits.setVisibility(0);
                    }
                } else {
                    this.makeDigitsPrimary();
                    this.mLetters.setVisibility(4);
                    this.mDigits.setVisibility(0);
                }
            } else {
                this.makeLettersPrimary();
                this.mLetters.setVisibility(0);
                this.mDigits.setVisibility(4);
            }
        } else {
            this.makeDigitsPrimary();
            this.mLetters.setVisibility(0);
            this.mDigits.setVisibility(0);
        }
        int n2 = this.mMode;
        this.mMode = n;
        this.onModeChange(n2, n);
    }
}

