/*
 * Decompiled with CFR 0.145.
 */
package android.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.transition.Transition;
import android.transition.TransitionListenerAdapter;
import android.transition.TransitionValues;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Map;

public class ChangeText
extends Transition {
    public static final int CHANGE_BEHAVIOR_IN = 2;
    public static final int CHANGE_BEHAVIOR_KEEP = 0;
    public static final int CHANGE_BEHAVIOR_OUT = 1;
    public static final int CHANGE_BEHAVIOR_OUT_IN = 3;
    private static final String LOG_TAG = "TextChange";
    private static final String PROPNAME_TEXT = "android:textchange:text";
    private static final String PROPNAME_TEXT_COLOR = "android:textchange:textColor";
    private static final String PROPNAME_TEXT_SELECTION_END = "android:textchange:textSelectionEnd";
    private static final String PROPNAME_TEXT_SELECTION_START = "android:textchange:textSelectionStart";
    private static final String[] sTransitionProperties = new String[]{"android:textchange:text", "android:textchange:textSelectionStart", "android:textchange:textSelectionEnd"};
    private int mChangeBehavior = 0;

    private void captureValues(TransitionValues transitionValues) {
        if (transitionValues.view instanceof TextView) {
            TextView textView = (TextView)transitionValues.view;
            transitionValues.values.put(PROPNAME_TEXT, textView.getText());
            if (textView instanceof EditText) {
                transitionValues.values.put(PROPNAME_TEXT_SELECTION_START, textView.getSelectionStart());
                transitionValues.values.put(PROPNAME_TEXT_SELECTION_END, textView.getSelectionEnd());
            }
            if (this.mChangeBehavior > 0) {
                transitionValues.values.put(PROPNAME_TEXT_COLOR, textView.getCurrentTextColor());
            }
        }
    }

    private void setSelection(EditText editText, int n, int n2) {
        if (n >= 0 && n2 >= 0) {
            editText.setSelection(n, n2);
        }
    }

    @Override
    public void captureEndValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public void captureStartValues(TransitionValues transitionValues) {
        this.captureValues(transitionValues);
    }

    @Override
    public Animator createAnimator(ViewGroup object, TransitionValues object2, TransitionValues object3) {
        if (object2 != null && object3 != null && ((TransitionValues)object2).view instanceof TextView && ((TransitionValues)object3).view instanceof TextView) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            final TextView textView = (TextView)((TransitionValues)object3).view;
            object = ((TransitionValues)object2).values;
            object2 = ((TransitionValues)object3).values;
            object3 = object.get(PROPNAME_TEXT);
            final CharSequence charSequence = "";
            object3 = object3 != null ? (CharSequence)object.get(PROPNAME_TEXT) : "";
            if (object2.get(PROPNAME_TEXT) != null) {
                charSequence = (CharSequence)object2.get(PROPNAME_TEXT);
            }
            boolean bl = textView instanceof EditText;
            final int n6 = -1;
            if (bl) {
                n2 = object.get(PROPNAME_TEXT_SELECTION_START) != null ? (Integer)object.get(PROPNAME_TEXT_SELECTION_START) : -1;
                n = object.get(PROPNAME_TEXT_SELECTION_END) != null ? (Integer)object.get(PROPNAME_TEXT_SELECTION_END) : n2;
                if (object2.get(PROPNAME_TEXT_SELECTION_START) != null) {
                    n6 = (Integer)object2.get(PROPNAME_TEXT_SELECTION_START);
                }
                n5 = object2.get(PROPNAME_TEXT_SELECTION_END) != null ? (Integer)object2.get(PROPNAME_TEXT_SELECTION_END) : n6;
                n3 = n5;
                n5 = n;
                n4 = n2;
                n = n3;
            } else {
                n = -1;
                n4 = -1;
                n6 = -1;
                n5 = -1;
            }
            if (!object3.equals(charSequence)) {
                if (this.mChangeBehavior != 2) {
                    textView.setText((CharSequence)object3);
                    if (textView instanceof EditText) {
                        this.setSelection((EditText)textView, n4, n5);
                    }
                }
                if (this.mChangeBehavior == 0) {
                    n2 = 0;
                    object = ValueAnimator.ofFloat(0.0f, 1.0f);
                    ((Animator)object).addListener(new AnimatorListenerAdapter((CharSequence)object3, textView, charSequence, n6, n){
                        final /* synthetic */ int val$endSelectionEnd;
                        final /* synthetic */ int val$endSelectionStart;
                        final /* synthetic */ CharSequence val$endText;
                        final /* synthetic */ CharSequence val$startText;
                        final /* synthetic */ TextView val$view;
                        {
                            this.val$startText = charSequence;
                            this.val$view = textView;
                            this.val$endText = charSequence2;
                            this.val$endSelectionStart = n;
                            this.val$endSelectionEnd = n2;
                        }

                        @Override
                        public void onAnimationEnd(Animator object) {
                            if (this.val$startText.equals(this.val$view.getText())) {
                                this.val$view.setText(this.val$endText);
                                object = this.val$view;
                                if (object instanceof EditText) {
                                    ChangeText.this.setSelection((EditText)object, this.val$endSelectionStart, this.val$endSelectionEnd);
                                }
                            }
                        }
                    });
                } else {
                    final int n7 = (Integer)object.get(PROPNAME_TEXT_COLOR);
                    n2 = (Integer)object2.get(PROPNAME_TEXT_COLOR);
                    n3 = this.mChangeBehavior;
                    if (n3 != 3 && n3 != 1) {
                        object = null;
                    } else {
                        object = ValueAnimator.ofInt(Color.alpha(n7), 0);
                        ((ValueAnimator)object).addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int n = (Integer)valueAnimator.getAnimatedValue();
                                textView.setTextColor(n << 24 | n7 & 16777215);
                            }
                        });
                        ((Animator)object).addListener(new AnimatorListenerAdapter((CharSequence)object3, textView, charSequence, n6, n, n2){
                            final /* synthetic */ int val$endColor;
                            final /* synthetic */ int val$endSelectionEnd;
                            final /* synthetic */ int val$endSelectionStart;
                            final /* synthetic */ CharSequence val$endText;
                            final /* synthetic */ CharSequence val$startText;
                            final /* synthetic */ TextView val$view;
                            {
                                this.val$startText = charSequence;
                                this.val$view = textView;
                                this.val$endText = charSequence2;
                                this.val$endSelectionStart = n;
                                this.val$endSelectionEnd = n2;
                                this.val$endColor = n3;
                            }

                            @Override
                            public void onAnimationEnd(Animator object) {
                                if (this.val$startText.equals(this.val$view.getText())) {
                                    this.val$view.setText(this.val$endText);
                                    object = this.val$view;
                                    if (object instanceof EditText) {
                                        ChangeText.this.setSelection((EditText)object, this.val$endSelectionStart, this.val$endSelectionEnd);
                                    }
                                }
                                this.val$view.setTextColor(this.val$endColor);
                            }
                        });
                    }
                    n3 = this.mChangeBehavior;
                    if (n3 != 3 && n3 != 2) {
                        object2 = null;
                    } else {
                        object2 = ValueAnimator.ofInt(0, Color.alpha(n2));
                        n3 = n2;
                        ((ValueAnimator)object2).addUpdateListener(new ValueAnimator.AnimatorUpdateListener(){

                            @Override
                            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                                int n = (Integer)valueAnimator.getAnimatedValue();
                                textView.setTextColor(n << 24 | n3 & 16777215);
                            }
                        });
                        ((Animator)object2).addListener(new AnimatorListenerAdapter(){

                            @Override
                            public void onAnimationCancel(Animator animator2) {
                                textView.setTextColor(n3);
                            }
                        });
                    }
                    if (object != null && object2 != null) {
                        AnimatorSet animatorSet = new AnimatorSet();
                        animatorSet.playSequentially(new Animator[]{object, object2});
                        object = animatorSet;
                    } else if (object == null) {
                        object = object2;
                    }
                }
                this.addListener(new TransitionListenerAdapter((CharSequence)object3, n4, n5){
                    int mPausedColor = 0;
                    final /* synthetic */ int val$startSelectionEnd;
                    final /* synthetic */ int val$startSelectionStart;
                    final /* synthetic */ CharSequence val$startText;
                    {
                        this.val$startText = charSequence2;
                        this.val$startSelectionStart = n4;
                        this.val$startSelectionEnd = n5;
                    }

                    @Override
                    public void onTransitionEnd(Transition transition2) {
                        transition2.removeListener(this);
                    }

                    @Override
                    public void onTransitionPause(Transition object) {
                        if (ChangeText.this.mChangeBehavior != 2) {
                            textView.setText(charSequence);
                            object = textView;
                            if (object instanceof EditText) {
                                ChangeText.this.setSelection((EditText)object, n6, n);
                            }
                        }
                        if (ChangeText.this.mChangeBehavior > 0) {
                            this.mPausedColor = textView.getCurrentTextColor();
                            textView.setTextColor(n2);
                        }
                    }

                    @Override
                    public void onTransitionResume(Transition object) {
                        if (ChangeText.this.mChangeBehavior != 2) {
                            textView.setText(this.val$startText);
                            object = textView;
                            if (object instanceof EditText) {
                                ChangeText.this.setSelection((EditText)object, this.val$startSelectionStart, this.val$startSelectionEnd);
                            }
                        }
                        if (ChangeText.this.mChangeBehavior > 0) {
                            textView.setTextColor(this.mPausedColor);
                        }
                    }
                });
                return object;
            }
            return null;
        }
        return null;
    }

    public int getChangeBehavior() {
        return this.mChangeBehavior;
    }

    @Override
    public String[] getTransitionProperties() {
        return sTransitionProperties;
    }

    public ChangeText setChangeBehavior(int n) {
        if (n >= 0 && n <= 3) {
            this.mChangeBehavior = n;
        }
        return this;
    }

}

