/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.media.Cea708CCParser;
import android.media.ClosedCaptionWidget;
import android.media.SubtitleTrack;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.CharacterStyle;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.CaptioningManager;
import android.widget.RelativeLayout;
import com.android.internal.widget.SubtitleView;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

class Cea708CCWidget
extends ClosedCaptionWidget
implements Cea708CCParser.DisplayListener {
    private final CCHandler mCCHandler;

    public Cea708CCWidget(Context context) {
        this(context, null);
    }

    public Cea708CCWidget(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public Cea708CCWidget(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public Cea708CCWidget(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.mCCHandler = new CCHandler((CCLayout)this.mClosedCaptionLayout);
    }

    @Override
    public ClosedCaptionWidget.ClosedCaptionLayout createCaptionLayout(Context context) {
        return new CCLayout(context);
    }

    @Override
    public void emitEvent(Cea708CCParser.CaptionEvent captionEvent) {
        this.mCCHandler.processCaptionEvent(captionEvent);
        this.setSize(this.getWidth(), this.getHeight());
        if (this.mListener != null) {
            this.mListener.onChanged(this);
        }
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        ((ViewGroup)((Object)this.mClosedCaptionLayout)).draw(canvas);
    }

    static class CCHandler
    implements Handler.Callback {
        private static final int CAPTION_ALL_WINDOWS_BITMAP = 255;
        private static final long CAPTION_CLEAR_INTERVAL_MS = 60000L;
        private static final int CAPTION_WINDOWS_MAX = 8;
        private static final boolean DEBUG = false;
        private static final int MSG_CAPTION_CLEAR = 2;
        private static final int MSG_DELAY_CANCEL = 1;
        private static final String TAG = "CCHandler";
        private static final int TENTHS_OF_SECOND_IN_MILLIS = 100;
        private final CCLayout mCCLayout;
        private final CCWindowLayout[] mCaptionWindowLayouts = new CCWindowLayout[8];
        private CCWindowLayout mCurrentWindowLayout;
        private final Handler mHandler;
        private boolean mIsDelayed = false;
        private final ArrayList<Cea708CCParser.CaptionEvent> mPendingCaptionEvents = new ArrayList();

        public CCHandler(CCLayout cCLayout) {
            this.mCCLayout = cCLayout;
            this.mHandler = new Handler(this);
        }

        private void clearWindows(int n) {
            if (n == 0) {
                return;
            }
            Iterator<CCWindowLayout> iterator = this.getWindowsFromBitmap(n).iterator();
            while (iterator.hasNext()) {
                iterator.next().clear();
            }
        }

        private void defineWindow(Cea708CCParser.CaptionWindow captionWindow) {
            Object object;
            if (captionWindow == null) {
                return;
            }
            int n = captionWindow.id;
            if (n >= 0 && n < ((CCWindowLayout[])(object = this.mCaptionWindowLayouts)).length) {
                CCWindowLayout cCWindowLayout = object[n];
                object = cCWindowLayout;
                if (cCWindowLayout == null) {
                    object = new CCWindowLayout(this.mCCLayout.getContext());
                }
                ((CCWindowLayout)object).initWindow(this.mCCLayout, captionWindow);
                this.mCaptionWindowLayouts[n] = object;
                this.mCurrentWindowLayout = object;
                return;
            }
        }

        private void delay(int n) {
            if (n >= 0 && n <= 255) {
                this.mIsDelayed = true;
                Handler handler = this.mHandler;
                handler.sendMessageDelayed(handler.obtainMessage(1), n * 100);
                return;
            }
        }

        private void delayCancel() {
            this.mIsDelayed = false;
            this.processPendingBuffer();
        }

        private void deleteWindows(int n) {
            if (n == 0) {
                return;
            }
            for (CCWindowLayout cCWindowLayout : this.getWindowsFromBitmap(n)) {
                cCWindowLayout.removeFromCaptionView();
                this.mCaptionWindowLayouts[cCWindowLayout.getCaptionWindowId()] = null;
            }
        }

        private void displayWindows(int n) {
            if (n == 0) {
                return;
            }
            Iterator<CCWindowLayout> iterator = this.getWindowsFromBitmap(n).iterator();
            while (iterator.hasNext()) {
                iterator.next().show();
            }
        }

        private ArrayList<CCWindowLayout> getWindowsFromBitmap(int n) {
            ArrayList<CCWindowLayout> arrayList = new ArrayList<CCWindowLayout>();
            for (int i = 0; i < 8; ++i) {
                CCWindowLayout cCWindowLayout;
                if ((1 << i & n) == 0 || (cCWindowLayout = this.mCaptionWindowLayouts[i]) == null) continue;
                arrayList.add(cCWindowLayout);
            }
            return arrayList;
        }

        private void hideWindows(int n) {
            if (n == 0) {
                return;
            }
            Iterator<CCWindowLayout> iterator = this.getWindowsFromBitmap(n).iterator();
            while (iterator.hasNext()) {
                iterator.next().hide();
            }
        }

        private void processPendingBuffer() {
            Iterator<Cea708CCParser.CaptionEvent> iterator = this.mPendingCaptionEvents.iterator();
            while (iterator.hasNext()) {
                this.processCaptionEvent(iterator.next());
            }
            this.mPendingCaptionEvents.clear();
        }

        private void sendBufferToCurrentWindow(String object) {
            CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
            if (cCWindowLayout != null) {
                cCWindowLayout.sendBuffer((String)object);
                this.mHandler.removeMessages(2);
                object = this.mHandler;
                ((Handler)object).sendMessageDelayed(((Handler)object).obtainMessage(2), 60000L);
            }
        }

        private void sendControlToCurrentWindow(char c) {
            CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
            if (cCWindowLayout != null) {
                cCWindowLayout.sendControl(c);
            }
        }

        private void setCurrentWindowLayout(int n) {
            Object object;
            if (n >= 0 && n < ((CCWindowLayout[])(object = this.mCaptionWindowLayouts)).length) {
                if ((object = object[n]) == null) {
                    return;
                }
                this.mCurrentWindowLayout = object;
                return;
            }
        }

        private void setPenAttr(Cea708CCParser.CaptionPenAttr captionPenAttr) {
            CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
            if (cCWindowLayout != null) {
                cCWindowLayout.setPenAttr(captionPenAttr);
            }
        }

        private void setPenColor(Cea708CCParser.CaptionPenColor captionPenColor) {
            CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
            if (cCWindowLayout != null) {
                cCWindowLayout.setPenColor(captionPenColor);
            }
        }

        private void setPenLocation(Cea708CCParser.CaptionPenLocation captionPenLocation) {
            CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
            if (cCWindowLayout != null) {
                cCWindowLayout.setPenLocation(captionPenLocation.row, captionPenLocation.column);
            }
        }

        private void setWindowAttr(Cea708CCParser.CaptionWindowAttr captionWindowAttr) {
            CCWindowLayout cCWindowLayout = this.mCurrentWindowLayout;
            if (cCWindowLayout != null) {
                cCWindowLayout.setWindowAttr(captionWindowAttr);
            }
        }

        private void toggleWindows(int n) {
            if (n == 0) {
                return;
            }
            for (CCWindowLayout cCWindowLayout : this.getWindowsFromBitmap(n)) {
                if (cCWindowLayout.isShown()) {
                    cCWindowLayout.hide();
                    continue;
                }
                cCWindowLayout.show();
            }
        }

        @Override
        public boolean handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    return false;
                }
                this.clearWindows(255);
                return true;
            }
            this.delayCancel();
            return true;
        }

        public void processCaptionEvent(Cea708CCParser.CaptionEvent captionEvent) {
            if (this.mIsDelayed) {
                this.mPendingCaptionEvents.add(captionEvent);
                return;
            }
            switch (captionEvent.type) {
                default: {
                    break;
                }
                case 16: {
                    this.defineWindow((Cea708CCParser.CaptionWindow)captionEvent.obj);
                    break;
                }
                case 15: {
                    this.setWindowAttr((Cea708CCParser.CaptionWindowAttr)captionEvent.obj);
                    break;
                }
                case 14: {
                    this.setPenLocation((Cea708CCParser.CaptionPenLocation)captionEvent.obj);
                    break;
                }
                case 13: {
                    this.setPenColor((Cea708CCParser.CaptionPenColor)captionEvent.obj);
                    break;
                }
                case 12: {
                    this.setPenAttr((Cea708CCParser.CaptionPenAttr)captionEvent.obj);
                    break;
                }
                case 11: {
                    this.reset();
                    break;
                }
                case 10: {
                    this.delayCancel();
                    break;
                }
                case 9: {
                    this.delay((Integer)captionEvent.obj);
                    break;
                }
                case 8: {
                    this.deleteWindows((Integer)captionEvent.obj);
                    break;
                }
                case 7: {
                    this.toggleWindows((Integer)captionEvent.obj);
                    break;
                }
                case 6: {
                    this.hideWindows((Integer)captionEvent.obj);
                    break;
                }
                case 5: {
                    this.displayWindows((Integer)captionEvent.obj);
                    break;
                }
                case 4: {
                    this.clearWindows((Integer)captionEvent.obj);
                    break;
                }
                case 3: {
                    this.setCurrentWindowLayout((Integer)captionEvent.obj);
                    break;
                }
                case 2: {
                    this.sendControlToCurrentWindow(((Character)captionEvent.obj).charValue());
                    break;
                }
                case 1: {
                    this.sendBufferToCurrentWindow((String)captionEvent.obj);
                }
            }
        }

        public void reset() {
            this.mCurrentWindowLayout = null;
            this.mIsDelayed = false;
            this.mPendingCaptionEvents.clear();
            for (int i = 0; i < 8; ++i) {
                CCWindowLayout[] arrcCWindowLayout = this.mCaptionWindowLayouts;
                if (arrcCWindowLayout[i] != null) {
                    arrcCWindowLayout[i].removeFromCaptionView();
                }
                this.mCaptionWindowLayouts[i] = null;
            }
            this.mCCLayout.setVisibility(4);
            this.mHandler.removeMessages(2);
        }
    }

    static class CCLayout
    extends ScaledLayout
    implements ClosedCaptionWidget.ClosedCaptionLayout {
        private static final float SAFE_TITLE_AREA_SCALE_END_X = 0.9f;
        private static final float SAFE_TITLE_AREA_SCALE_END_Y = 0.9f;
        private static final float SAFE_TITLE_AREA_SCALE_START_X = 0.1f;
        private static final float SAFE_TITLE_AREA_SCALE_START_Y = 0.1f;
        private final ScaledLayout mSafeTitleAreaLayout;

        public CCLayout(Context context) {
            super(context);
            this.mSafeTitleAreaLayout = new ScaledLayout(context);
            this.addView((View)this.mSafeTitleAreaLayout, new ScaledLayout.ScaledLayoutParams(0.1f, 0.9f, 0.1f, 0.9f));
        }

        public void addOrUpdateViewToSafeTitleArea(CCWindowLayout cCWindowLayout, ScaledLayout.ScaledLayoutParams scaledLayoutParams) {
            if (this.mSafeTitleAreaLayout.indexOfChild(cCWindowLayout) < 0) {
                this.mSafeTitleAreaLayout.addView((View)cCWindowLayout, scaledLayoutParams);
                return;
            }
            this.mSafeTitleAreaLayout.updateViewLayout(cCWindowLayout, scaledLayoutParams);
        }

        public void removeViewFromSafeTitleArea(CCWindowLayout cCWindowLayout) {
            this.mSafeTitleAreaLayout.removeView(cCWindowLayout);
        }

        @Override
        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
            int n = this.mSafeTitleAreaLayout.getChildCount();
            for (int i = 0; i < n; ++i) {
                ((CCWindowLayout)this.mSafeTitleAreaLayout.getChildAt(i)).setCaptionStyle(captionStyle);
            }
        }

        @Override
        public void setFontScale(float f) {
            int n = this.mSafeTitleAreaLayout.getChildCount();
            for (int i = 0; i < n; ++i) {
                ((CCWindowLayout)this.mSafeTitleAreaLayout.getChildAt(i)).setFontScale(f);
            }
        }
    }

    static class CCView
    extends SubtitleView {
        private static final CaptioningManager.CaptionStyle DEFAULT_CAPTION_STYLE = CaptioningManager.CaptionStyle.DEFAULT;

        public CCView(Context context) {
            this(context, null);
        }

        public CCView(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public CCView(Context context, AttributeSet attributeSet, int n) {
            this(context, attributeSet, n, 0);
        }

        public CCView(Context context, AttributeSet attributeSet, int n, int n2) {
            super(context, attributeSet, n, n2);
        }

        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
            int n = captionStyle.hasForegroundColor() ? captionStyle.foregroundColor : CCView.DEFAULT_CAPTION_STYLE.foregroundColor;
            this.setForegroundColor(n);
            n = captionStyle.hasBackgroundColor() ? captionStyle.backgroundColor : CCView.DEFAULT_CAPTION_STYLE.backgroundColor;
            this.setBackgroundColor(n);
            n = captionStyle.hasEdgeType() ? captionStyle.edgeType : CCView.DEFAULT_CAPTION_STYLE.edgeType;
            this.setEdgeType(n);
            n = captionStyle.hasEdgeColor() ? captionStyle.edgeColor : CCView.DEFAULT_CAPTION_STYLE.edgeColor;
            this.setEdgeColor(n);
            this.setTypeface(captionStyle.getTypeface());
        }
    }

    static class CCWindowLayout
    extends RelativeLayout
    implements View.OnLayoutChangeListener {
        private static final int ANCHOR_HORIZONTAL_16_9_MAX = 209;
        private static final int ANCHOR_HORIZONTAL_MODE_CENTER = 1;
        private static final int ANCHOR_HORIZONTAL_MODE_LEFT = 0;
        private static final int ANCHOR_HORIZONTAL_MODE_RIGHT = 2;
        private static final int ANCHOR_MODE_DIVIDER = 3;
        private static final int ANCHOR_RELATIVE_POSITIONING_MAX = 99;
        private static final int ANCHOR_VERTICAL_MAX = 74;
        private static final int ANCHOR_VERTICAL_MODE_BOTTOM = 2;
        private static final int ANCHOR_VERTICAL_MODE_CENTER = 1;
        private static final int ANCHOR_VERTICAL_MODE_TOP = 0;
        private static final int MAX_COLUMN_COUNT_16_9 = 42;
        private static final float PROPORTION_PEN_SIZE_LARGE = 1.25f;
        private static final float PROPORTION_PEN_SIZE_SMALL = 0.75f;
        private static final String TAG = "CCWindowLayout";
        private final SpannableStringBuilder mBuilder = new SpannableStringBuilder();
        private CCLayout mCCLayout;
        private CCView mCCView;
        private CaptioningManager.CaptionStyle mCaptionStyle;
        private int mCaptionWindowId;
        private final List<CharacterStyle> mCharacterStyles = new ArrayList<CharacterStyle>();
        private float mFontScale;
        private int mLastCaptionLayoutHeight;
        private int mLastCaptionLayoutWidth;
        private int mRow = -1;
        private int mRowLimit = 0;
        private float mTextSize;
        private String mWidestChar;

        public CCWindowLayout(Context context) {
            this(context, null);
        }

        public CCWindowLayout(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, 0);
        }

        public CCWindowLayout(Context context, AttributeSet attributeSet, int n) {
            this(context, attributeSet, n, 0);
        }

        public CCWindowLayout(Context object, AttributeSet object2, int n, int n2) {
            super((Context)object, (AttributeSet)object2, n, n2);
            this.mCCView = new CCView((Context)object);
            object2 = new RelativeLayout.LayoutParams(-2, -2);
            this.addView((View)this.mCCView, (ViewGroup.LayoutParams)object2);
            object = (CaptioningManager)((Context)object).getSystemService("captioning");
            this.mFontScale = ((CaptioningManager)object).getFontScale();
            this.setCaptionStyle(((CaptioningManager)object).getUserStyle());
            this.mCCView.setText("");
            this.updateWidestChar();
        }

        private int getScreenColumnCount() {
            return 42;
        }

        private void updateText(String object, boolean bl) {
            SpannableStringBuilder spannableStringBuilder;
            int n;
            int n2;
            int n3;
            if (!bl) {
                this.mBuilder.clear();
            }
            if (object != null && ((String)object).length() > 0) {
                n3 = this.mBuilder.length();
                this.mBuilder.append((CharSequence)object);
                for (CharacterStyle characterStyle : this.mCharacterStyles) {
                    spannableStringBuilder = this.mBuilder;
                    spannableStringBuilder.setSpan(characterStyle, n3, spannableStringBuilder.length(), 33);
                }
            }
            object = TextUtils.split(this.mBuilder.toString(), "\n");
            object = TextUtils.join((CharSequence)"\n", Arrays.copyOfRange(object, Math.max(0, ((String[])object).length - (this.mRowLimit + 1)), ((Object)object).length));
            spannableStringBuilder = this.mBuilder;
            spannableStringBuilder.delete(0, spannableStringBuilder.length() - ((String)object).length());
            int n4 = 0;
            n3 = n2 = this.mBuilder.length() - 1;
            do {
                n = n3;
                if (n4 > n3) break;
                n = n3;
                if (this.mBuilder.charAt(n4) > ' ') break;
                ++n4;
            } while (true);
            while (n >= n4 && this.mBuilder.charAt(n) <= ' ') {
                --n;
            }
            if (n4 == 0 && n == n2) {
                this.mCCView.setText(this.mBuilder);
            } else {
                object = new SpannableStringBuilder();
                ((SpannableStringBuilder)object).append(this.mBuilder);
                if (n < n2) {
                    ((SpannableStringBuilder)object).delete(n + 1, n2 + 1);
                }
                if (n4 > 0) {
                    ((SpannableStringBuilder)object).delete(0, n4);
                }
                this.mCCView.setText((CharSequence)object);
            }
        }

        private void updateTextSize() {
            if (this.mCCLayout == null) {
                return;
            }
            CharSequence charSequence = new StringBuilder();
            int n = this.getScreenColumnCount();
            for (int i = 0; i < n; ++i) {
                charSequence.append(this.mWidestChar);
            }
            charSequence = charSequence.toString();
            Paint paint = new Paint();
            paint.setTypeface(this.mCaptionStyle.getTypeface());
            float f = 0.0f;
            float f2 = 255.0f;
            while (f < f2) {
                float f3 = (f + f2) / 2.0f;
                paint.setTextSize(f3);
                float f4 = paint.measureText((String)charSequence);
                if ((float)this.mCCLayout.getWidth() * 0.8f > f4) {
                    f = 0.01f + f3;
                    continue;
                }
                f2 = f3 - 0.01f;
            }
            this.mTextSize = this.mFontScale * f2;
            this.mCCView.setTextSize(this.mTextSize);
        }

        private void updateWidestChar() {
            Paint paint = new Paint();
            paint.setTypeface(this.mCaptionStyle.getTypeface());
            Charset charset = Charset.forName("ISO-8859-1");
            float f = 0.0f;
            for (int i = 0; i < 256; ++i) {
                String string2 = new String(new byte[]{(byte)i}, charset);
                float f2 = paint.measureText(string2);
                float f3 = f;
                if (f < f2) {
                    f3 = f2;
                    this.mWidestChar = string2;
                }
                f = f3;
            }
            this.updateTextSize();
        }

        public void appendText(String string2) {
            this.updateText(string2, true);
        }

        public void clear() {
            this.clearText();
            this.hide();
        }

        public void clearText() {
            this.mBuilder.clear();
            this.mCCView.setText("");
        }

        public int getCaptionWindowId() {
            return this.mCaptionWindowId;
        }

        public void hide() {
            this.setVisibility(4);
            this.requestLayout();
        }

        public void initWindow(CCLayout object, Cea708CCParser.CaptionWindow captionWindow) {
            float f;
            int n;
            float f2;
            int n2;
            Object object2;
            float f3;
            block28 : {
                block27 : {
                    block26 : {
                        block25 : {
                            object2 = this.mCCLayout;
                            if (object2 != object) {
                                if (object2 != null) {
                                    ((View)object2).removeOnLayoutChangeListener(this);
                                }
                                this.mCCLayout = object;
                                this.mCCLayout.addOnLayoutChangeListener(this);
                                this.updateWidestChar();
                            }
                            f2 = captionWindow.anchorVertical;
                            boolean bl = captionWindow.relativePositioning;
                            n2 = 99;
                            n = bl ? 99 : 74;
                            f3 = f2 / (float)n;
                            f2 = captionWindow.anchorHorizontal;
                            n = captionWindow.relativePositioning ? n2 : 209;
                            f = f2 / (float)n;
                            if (f3 < 0.0f) break block25;
                            f2 = f3;
                            if (!(f3 > 1.0f)) break block26;
                        }
                        object = new StringBuilder();
                        ((StringBuilder)object).append("The vertical position of the anchor point should be at the range of 0 and 1 but ");
                        ((StringBuilder)object).append(f3);
                        Log.i(TAG, ((StringBuilder)object).toString());
                        f2 = Math.max(0.0f, Math.min(f3, 1.0f));
                    }
                    if (f < 0.0f) break block27;
                    f3 = f;
                    if (!(f > 1.0f)) break block28;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("The horizontal position of the anchor point should be at the range of 0 and 1 but ");
                ((StringBuilder)object).append(f);
                Log.i(TAG, ((StringBuilder)object).toString());
                f3 = Math.max(0.0f, Math.min(f, 1.0f));
            }
            n = 17;
            int n3 = captionWindow.anchorId % 3;
            n2 = captionWindow.anchorId / 3;
            float f4 = 0.0f;
            float f5 = 1.0f;
            float f6 = 0.0f;
            f = 1.0f;
            if (n3 != 0) {
                if (n3 != 1) {
                    if (n3 != 2) {
                        f3 = f6;
                    } else {
                        n = 5;
                        this.mCCView.setAlignment(Layout.Alignment.ALIGN_RIGHT);
                        f = f3;
                        f3 = f6;
                    }
                } else {
                    f6 = Math.min(1.0f - f3, f3);
                    n = captionWindow.columnCount;
                    n3 = Math.min(this.getScreenColumnCount(), n + 1);
                    object2 = new StringBuilder();
                    for (n = 0; n < n3; ++n) {
                        ((StringBuilder)object2).append(this.mWidestChar);
                    }
                    object = new Paint();
                    ((Paint)object).setTypeface(this.mCaptionStyle.getTypeface());
                    ((Paint)object).setTextSize(this.mTextSize);
                    f = ((Paint)object).measureText(((StringBuilder)object2).toString());
                    f = this.mCCLayout.getWidth() > 0 ? f / 2.0f / ((float)this.mCCLayout.getWidth() * 0.8f) : 0.0f;
                    if (f > 0.0f && f < f3) {
                        this.mCCView.setAlignment(Layout.Alignment.ALIGN_NORMAL);
                        f3 -= f;
                        f = 1.0f;
                        n = 3;
                    } else {
                        n = 1;
                        this.mCCView.setAlignment(Layout.Alignment.ALIGN_CENTER);
                        f = f3 - f6;
                        f6 = f3 + f6;
                        f3 = f;
                        f = f6;
                    }
                }
            } else {
                n = 3;
                this.mCCView.setAlignment(Layout.Alignment.ALIGN_NORMAL);
            }
            if (n2 != 0) {
                if (n2 != 1) {
                    if (n2 != 2) {
                        f2 = f4;
                    } else {
                        n |= 80;
                        f5 = f2;
                        f2 = f4;
                    }
                } else {
                    n |= 16;
                    f4 = Math.min(1.0f - f2, f2);
                    f5 = f2 - f4;
                    f4 = f2 + f4;
                    f2 = f5;
                    f5 = f4;
                }
            } else {
                n |= 48;
            }
            this.mCCLayout.addOrUpdateViewToSafeTitleArea(this, new ScaledLayout.ScaledLayoutParams(f2, f5, f3, f));
            this.setCaptionWindowId(captionWindow.id);
            this.setRowLimit(captionWindow.rowCount);
            this.setGravity(n);
            if (captionWindow.visible) {
                this.show();
            } else {
                this.hide();
            }
        }

        @Override
        public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
            n = n3 - n;
            n2 = n4 - n2;
            if (n != this.mLastCaptionLayoutWidth || n2 != this.mLastCaptionLayoutHeight) {
                this.mLastCaptionLayoutWidth = n;
                this.mLastCaptionLayoutHeight = n2;
                this.updateTextSize();
            }
        }

        public void removeFromCaptionView() {
            CCLayout cCLayout = this.mCCLayout;
            if (cCLayout != null) {
                cCLayout.removeViewFromSafeTitleArea(this);
                this.mCCLayout.removeOnLayoutChangeListener(this);
                this.mCCLayout = null;
            }
        }

        public void sendBuffer(String string2) {
            this.appendText(string2);
        }

        public void sendControl(char c) {
        }

        public void setCaptionStyle(CaptioningManager.CaptionStyle captionStyle) {
            this.mCaptionStyle = captionStyle;
            this.mCCView.setCaptionStyle(captionStyle);
        }

        public void setCaptionWindowId(int n) {
            this.mCaptionWindowId = n;
        }

        public void setFontScale(float f) {
            this.mFontScale = f;
            this.updateTextSize();
        }

        public void setPenAttr(Cea708CCParser.CaptionPenAttr captionPenAttr) {
            int n;
            this.mCharacterStyles.clear();
            if (captionPenAttr.italic) {
                this.mCharacterStyles.add(new StyleSpan(2));
            }
            if (captionPenAttr.underline) {
                this.mCharacterStyles.add(new UnderlineSpan());
            }
            if ((n = captionPenAttr.penSize) != 0) {
                if (n == 2) {
                    this.mCharacterStyles.add(new RelativeSizeSpan(1.25f));
                }
            } else {
                this.mCharacterStyles.add(new RelativeSizeSpan(0.75f));
            }
            n = captionPenAttr.penOffset;
            if (n != 0) {
                if (n == 2) {
                    this.mCharacterStyles.add(new SuperscriptSpan());
                }
            } else {
                this.mCharacterStyles.add(new SubscriptSpan());
            }
        }

        public void setPenColor(Cea708CCParser.CaptionPenColor captionPenColor) {
        }

        public void setPenLocation(int n, int n2) {
            if (this.mRow >= 0) {
                for (n2 = this.mRow; n2 < n; ++n2) {
                    this.appendText("\n");
                }
            }
            this.mRow = n;
        }

        public void setRowLimit(int n) {
            if (n >= 0) {
                this.mRowLimit = n;
                return;
            }
            throw new IllegalArgumentException("A rowLimit should have a positive number");
        }

        public void setText(String string2) {
            this.updateText(string2, false);
        }

        public void setWindowAttr(Cea708CCParser.CaptionWindowAttr captionWindowAttr) {
        }

        public void show() {
            this.setVisibility(0);
            this.requestLayout();
        }
    }

    static class ScaledLayout
    extends ViewGroup {
        private static final boolean DEBUG = false;
        private static final String TAG = "ScaledLayout";
        private static final Comparator<Rect> mRectTopLeftSorter = new Comparator<Rect>(){

            @Override
            public int compare(Rect rect, Rect rect2) {
                if (rect.top != rect2.top) {
                    return rect.top - rect2.top;
                }
                return rect.left - rect2.left;
            }
        };
        private Rect[] mRectArray;

        public ScaledLayout(Context context) {
            super(context);
        }

        @Override
        protected boolean checkLayoutParams(ViewGroup.LayoutParams layoutParams) {
            return layoutParams instanceof ScaledLayoutParams;
        }

        @Override
        public void dispatchDraw(Canvas canvas) {
            int n = this.getPaddingLeft();
            int n2 = this.getPaddingTop();
            int n3 = this.getChildCount();
            for (int i = 0; i < n3; ++i) {
                View view = this.getChildAt(i);
                if (view.getVisibility() == 8) continue;
                Rect[] arrrect = this.mRectArray;
                if (i >= arrrect.length) break;
                int n4 = arrrect[i].left;
                int n5 = this.mRectArray[i].top;
                int n6 = canvas.save();
                canvas.translate(n4 + n, n5 + n2);
                view.draw(canvas);
                canvas.restoreToCount(n6);
            }
        }

        @Override
        public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attributeSet) {
            return new ScaledLayoutParams(this.getContext(), attributeSet);
        }

        @Override
        protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
            n3 = this.getPaddingLeft();
            n2 = this.getPaddingTop();
            n4 = this.getChildCount();
            for (n = 0; n < n4; ++n) {
                View view = this.getChildAt(n);
                if (view.getVisibility() == 8) continue;
                int n5 = this.mRectArray[n].left;
                int n6 = this.mRectArray[n].top;
                int n7 = this.mRectArray[n].bottom;
                view.layout(n5 + n3, n6 + n2, this.mRectArray[n].right + n2, n7 + n3);
            }
        }

        @Override
        protected void onMeasure(int n, int n2) {
            Object object;
            int n3;
            Rect[] arrrect;
            int n4 = View.MeasureSpec.getSize(n);
            n = View.MeasureSpec.getSize(n2);
            int n5 = n4 - this.getPaddingLeft() - this.getPaddingRight();
            int n6 = n - this.getPaddingTop() - this.getPaddingBottom();
            int n7 = this.getChildCount();
            this.mRectArray = new Rect[n7];
            n2 = n4;
            for (n3 = 0; n3 < n7; ++n3) {
                arrrect = this.getChildAt(n3);
                object = arrrect.getLayoutParams();
                if (object instanceof ScaledLayoutParams) {
                    float f = ((ScaledLayoutParams)object).scaleStartRow;
                    float f2 = ((ScaledLayoutParams)object).scaleEndRow;
                    float f3 = ((ScaledLayoutParams)object).scaleStartCol;
                    float f4 = ((ScaledLayoutParams)object).scaleEndCol;
                    if (!(f < 0.0f) && !(f > 1.0f)) {
                        if (!(f2 < f) && !(f > 1.0f)) {
                            if (!(f4 < 0.0f) && !(f4 > 1.0f)) {
                                if (!(f4 < f3) && !(f4 > 1.0f)) {
                                    this.mRectArray[n3] = new Rect((int)((float)n5 * f3), (int)((float)n6 * f), (int)((float)n5 * f4), (int)((float)n6 * f2));
                                    n4 = View.MeasureSpec.makeMeasureSpec((int)((float)n5 * (f4 - f3)), 1073741824);
                                    arrrect.measure(n4, View.MeasureSpec.makeMeasureSpec(0, 0));
                                    if (arrrect.getMeasuredHeight() > this.mRectArray[n3].height()) {
                                        int n8 = (arrrect.getMeasuredHeight() - this.mRectArray[n3].height() + 1) / 2;
                                        object = this.mRectArray[n3];
                                        object.bottom += n8;
                                        object = this.mRectArray[n3];
                                        object.top -= n8;
                                        if (this.mRectArray[n3].top < 0) {
                                            object = this.mRectArray[n3];
                                            object.bottom -= this.mRectArray[n3].top;
                                            this.mRectArray[n3].top = 0;
                                        }
                                        if (this.mRectArray[n3].bottom > n6) {
                                            object = this.mRectArray[n3];
                                            object.top -= this.mRectArray[n3].bottom - n6;
                                            this.mRectArray[n3].bottom = n6;
                                        }
                                    }
                                    arrrect.measure(n4, View.MeasureSpec.makeMeasureSpec((int)((float)n6 * (f2 - f)), 1073741824));
                                    continue;
                                }
                                throw new RuntimeException("A child of ScaledLayout should have a range of scaleEndCol between scaleStartCol and 1");
                            }
                            throw new RuntimeException("A child of ScaledLayout should have a range of scaleStartCol between 0 and 1");
                        }
                        throw new RuntimeException("A child of ScaledLayout should have a range of scaleEndRow between scaleStartRow and 1");
                    }
                    throw new RuntimeException("A child of ScaledLayout should have a range of scaleStartRow between 0 and 1");
                }
                throw new RuntimeException("A child of ScaledLayout cannot have the UNSPECIFIED scale factors");
            }
            n3 = 0;
            object = new int[n7];
            arrrect = new Rect[n7];
            for (n4 = 0; n4 < n7; ++n4) {
                n5 = n3;
                if (this.getChildAt(n4).getVisibility() == 0) {
                    object[n3] = n3;
                    arrrect[n3] = this.mRectArray[n4];
                    n5 = n3 + 1;
                }
                n3 = n5;
            }
            Arrays.sort(arrrect, 0, n3, mRectTopLeftSorter);
            for (n4 = 0; n4 < n3 - 1; ++n4) {
                for (n5 = n4 + 1; n5 < n3; ++n5) {
                    if (!Rect.intersects(arrrect[n4], arrrect[n5])) continue;
                    object[n5] = object[n4];
                    arrrect[n5].set(arrrect[n5].left, arrrect[n4].bottom, arrrect[n5].right, arrrect[n4].bottom + arrrect[n5].height());
                }
            }
            --n3;
            while (n3 >= 0) {
                if (arrrect[n3].bottom > n6) {
                    n5 = arrrect[n3].bottom - n6;
                    for (n4 = 0; n4 <= n3; ++n4) {
                        if (object[n3] != object[n4]) continue;
                        arrrect[n4].set(arrrect[n4].left, arrrect[n4].top - n5, arrrect[n4].right, arrrect[n4].bottom - n5);
                    }
                }
                --n3;
            }
            this.setMeasuredDimension(n2, n);
        }

        static class ScaledLayoutParams
        extends ViewGroup.LayoutParams {
            public static final float SCALE_UNSPECIFIED = -1.0f;
            public float scaleEndCol;
            public float scaleEndRow;
            public float scaleStartCol;
            public float scaleStartRow;

            public ScaledLayoutParams(float f, float f2, float f3, float f4) {
                super(-1, -1);
                this.scaleStartRow = f;
                this.scaleEndRow = f2;
                this.scaleStartCol = f3;
                this.scaleEndCol = f4;
            }

            public ScaledLayoutParams(Context context, AttributeSet attributeSet) {
                super(-1, -1);
            }
        }

    }

}

