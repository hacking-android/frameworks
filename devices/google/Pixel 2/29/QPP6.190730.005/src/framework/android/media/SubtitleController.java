/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.media.MediaFormat;
import android.media.MediaTimeProvider;
import android.media.SubtitleTrack;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.accessibility.CaptioningManager;
import java.util.Iterator;
import java.util.Locale;
import java.util.Vector;

public class SubtitleController {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int WHAT_HIDE = 2;
    private static final int WHAT_SELECT_DEFAULT_TRACK = 4;
    private static final int WHAT_SELECT_TRACK = 3;
    private static final int WHAT_SHOW = 1;
    private Anchor mAnchor;
    private final Handler.Callback mCallback = new Handler.Callback(){

        @Override
        public boolean handleMessage(Message message) {
            int n = message.what;
            if (n != 1) {
                if (n != 2) {
                    if (n != 3) {
                        if (n != 4) {
                            return false;
                        }
                        SubtitleController.this.doSelectDefaultTrack();
                        return true;
                    }
                    SubtitleController.this.doSelectTrack((SubtitleTrack)message.obj);
                    return true;
                }
                SubtitleController.this.doHide();
                return true;
            }
            SubtitleController.this.doShow();
            return true;
        }
    };
    private CaptioningManager.CaptioningChangeListener mCaptioningChangeListener = new CaptioningManager.CaptioningChangeListener(){

        @Override
        public void onEnabledChanged(boolean bl) {
            SubtitleController.this.selectDefaultTrack();
        }

        @Override
        public void onLocaleChanged(Locale locale) {
            SubtitleController.this.selectDefaultTrack();
        }
    };
    private CaptioningManager mCaptioningManager;
    @UnsupportedAppUsage
    private Handler mHandler;
    private Listener mListener;
    private Vector<Renderer> mRenderers;
    private SubtitleTrack mSelectedTrack;
    private boolean mShowing;
    private MediaTimeProvider mTimeProvider;
    private boolean mTrackIsExplicit = false;
    private Vector<SubtitleTrack> mTracks;
    private boolean mVisibilityIsExplicit = false;

    @UnsupportedAppUsage
    public SubtitleController(Context context, MediaTimeProvider mediaTimeProvider, Listener listener) {
        this.mTimeProvider = mediaTimeProvider;
        this.mListener = listener;
        this.mRenderers = new Vector();
        this.mShowing = false;
        this.mTracks = new Vector();
        this.mCaptioningManager = (CaptioningManager)context.getSystemService("captioning");
    }

    private void checkAnchorLooper() {
    }

    private void doHide() {
        this.mVisibilityIsExplicit = true;
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack != null) {
            subtitleTrack.hide();
        }
        this.mShowing = false;
    }

    private void doSelectDefaultTrack() {
        if (this.mTrackIsExplicit) {
            if (!this.mVisibilityIsExplicit) {
                SubtitleTrack subtitleTrack;
                if (!(this.mCaptioningManager.isEnabled() || (subtitleTrack = this.mSelectedTrack) != null && subtitleTrack.getFormat().getInteger("is-forced-subtitle", 0) != 0)) {
                    subtitleTrack = this.mSelectedTrack;
                    if (subtitleTrack != null && subtitleTrack.getTrackType() == 4) {
                        this.hide();
                    }
                } else {
                    this.show();
                }
                this.mVisibilityIsExplicit = false;
            }
            return;
        }
        SubtitleTrack subtitleTrack = this.getDefaultTrack();
        if (subtitleTrack != null) {
            this.selectTrack(subtitleTrack);
            this.mTrackIsExplicit = false;
            if (!this.mVisibilityIsExplicit) {
                this.show();
                this.mVisibilityIsExplicit = false;
            }
        }
    }

    private void doSelectTrack(SubtitleTrack subtitleTrack) {
        this.mTrackIsExplicit = true;
        Object object = this.mSelectedTrack;
        if (object == subtitleTrack) {
            return;
        }
        if (object != null) {
            ((SubtitleTrack)object).hide();
            this.mSelectedTrack.setTimeProvider(null);
        }
        this.mSelectedTrack = subtitleTrack;
        object = this.mAnchor;
        if (object != null) {
            object.setSubtitleWidget(this.getRenderingWidget());
        }
        if ((object = this.mSelectedTrack) != null) {
            ((SubtitleTrack)object).setTimeProvider(this.mTimeProvider);
            this.mSelectedTrack.show();
        }
        if ((object = this.mListener) != null) {
            object.onSubtitleTrackSelected(subtitleTrack);
        }
    }

    private void doShow() {
        this.mShowing = true;
        this.mVisibilityIsExplicit = true;
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack != null) {
            subtitleTrack.show();
        }
    }

    private SubtitleTrack.RenderingWidget getRenderingWidget() {
        SubtitleTrack subtitleTrack = this.mSelectedTrack;
        if (subtitleTrack == null) {
            return null;
        }
        return subtitleTrack.getRenderingWidget();
    }

    private void processOnAnchor(Message message) {
        if (Looper.myLooper() == this.mHandler.getLooper()) {
            this.mHandler.dispatchMessage(message);
        } else {
            this.mHandler.sendMessage(message);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SubtitleTrack addTrack(MediaFormat object) {
        Vector<Renderer> vector = this.mRenderers;
        synchronized (vector) {
            Object object2;
            Iterator<Renderer> iterator = this.mRenderers.iterator();
            do {
                if (iterator.hasNext()) continue;
                return null;
            } while (!((Renderer)(object2 = iterator.next())).supports((MediaFormat)object) || (object2 = ((Renderer)object2).createTrack((MediaFormat)object)) == null);
            object = this.mTracks;
            synchronized (object) {
                if (this.mTracks.size() == 0) {
                    this.mCaptioningManager.addCaptioningChangeListener(this.mCaptioningChangeListener);
                }
                this.mTracks.add((SubtitleTrack)object2);
                return object2;
            }
        }
    }

    protected void finalize() throws Throwable {
        this.mCaptioningManager.removeCaptioningChangeListener(this.mCaptioningChangeListener);
        super.finalize();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SubtitleTrack getDefaultTrack() {
        String string2 = null;
        int n = -1;
        Locale locale = this.mCaptioningManager.getLocale();
        Locale locale2 = locale == null ? Locale.getDefault() : locale;
        boolean bl = this.mCaptioningManager.isEnabled();
        Vector<SubtitleTrack> vector = this.mTracks;
        synchronized (vector) {
            Iterator<SubtitleTrack> iterator = this.mTracks.iterator();
            do {
                int n2;
                Object object;
                block10 : {
                    int n3;
                    SubtitleTrack subtitleTrack;
                    block9 : {
                        if (!iterator.hasNext()) {
                            return string2;
                        }
                        subtitleTrack = iterator.next();
                        MediaFormat mediaFormat = subtitleTrack.getFormat();
                        object = mediaFormat.getString("language");
                        boolean bl2 = mediaFormat.getInteger("is-forced-subtitle", 0) != 0;
                        boolean bl3 = mediaFormat.getInteger("is-autoselect", 1) != 0;
                        n2 = mediaFormat.getInteger("is-default", 0) != 0 ? 1 : 0;
                        boolean bl4 = locale2 == null || locale2.getLanguage().equals("") || locale2.getISO3Language().equals(object) || locale2.getLanguage().equals(object);
                        n3 = bl2 ? 0 : 8;
                        int n4 = locale == null && n2 != 0 ? 4 : 0;
                        int n5 = bl3 ? 0 : 2;
                        int n6 = bl4 ? 1 : 0;
                        n3 = n3 + n4 + n5 + n6;
                        if (bl ^ true && !bl2) continue;
                        if (locale == null && n2 != 0) break block9;
                        object = string2;
                        n2 = n;
                        if (!bl4) break block10;
                        if (bl3 || bl2) break block9;
                        object = string2;
                        n2 = n;
                        if (locale == null) break block10;
                    }
                    object = string2;
                    n2 = n;
                    if (n3 > n) {
                        n2 = n3;
                        object = subtitleTrack;
                    }
                }
                string2 = object;
                n = n2;
            } while (true);
        }
    }

    public SubtitleTrack getSelectedTrack() {
        return this.mSelectedTrack;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public SubtitleTrack[] getTracks() {
        Vector<SubtitleTrack> vector = this.mTracks;
        synchronized (vector) {
            SubtitleTrack[] arrsubtitleTrack = new SubtitleTrack[this.mTracks.size()];
            this.mTracks.toArray(arrsubtitleTrack);
            return arrsubtitleTrack;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean hasRendererFor(MediaFormat mediaFormat) {
        Vector<Renderer> vector = this.mRenderers;
        synchronized (vector) {
            Iterator<Renderer> iterator = this.mRenderers.iterator();
            do {
                if (iterator.hasNext()) continue;
                return false;
            } while (!iterator.next().supports(mediaFormat));
            return true;
        }
    }

    @UnsupportedAppUsage
    public void hide() {
        this.processOnAnchor(this.mHandler.obtainMessage(2));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @UnsupportedAppUsage
    public void registerRenderer(Renderer renderer) {
        Vector<Renderer> vector = this.mRenderers;
        synchronized (vector) {
            if (!this.mRenderers.contains(renderer)) {
                this.mRenderers.add(renderer);
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public void reset() {
        this.checkAnchorLooper();
        this.hide();
        this.selectTrack(null);
        this.mTracks.clear();
        this.mTrackIsExplicit = false;
        this.mVisibilityIsExplicit = false;
        this.mCaptioningManager.removeCaptioningChangeListener(this.mCaptioningChangeListener);
    }

    public void selectDefaultTrack() {
        this.processOnAnchor(this.mHandler.obtainMessage(4));
    }

    public boolean selectTrack(SubtitleTrack subtitleTrack) {
        if (subtitleTrack != null && !this.mTracks.contains(subtitleTrack)) {
            return false;
        }
        this.processOnAnchor(this.mHandler.obtainMessage(3, subtitleTrack));
        return true;
    }

    public void setAnchor(Anchor anchor) {
        Anchor anchor2 = this.mAnchor;
        if (anchor2 == anchor) {
            return;
        }
        if (anchor2 != null) {
            this.checkAnchorLooper();
            this.mAnchor.setSubtitleWidget(null);
        }
        this.mAnchor = anchor;
        this.mHandler = null;
        anchor = this.mAnchor;
        if (anchor != null) {
            this.mHandler = new Handler(anchor.getSubtitleLooper(), this.mCallback);
            this.checkAnchorLooper();
            this.mAnchor.setSubtitleWidget(this.getRenderingWidget());
        }
    }

    @UnsupportedAppUsage
    public void show() {
        this.processOnAnchor(this.mHandler.obtainMessage(1));
    }

    public static interface Anchor {
        public Looper getSubtitleLooper();

        public void setSubtitleWidget(SubtitleTrack.RenderingWidget var1);
    }

    public static interface Listener {
        public void onSubtitleTrackSelected(SubtitleTrack var1);
    }

    public static abstract class Renderer {
        public abstract SubtitleTrack createTrack(MediaFormat var1);

        public abstract boolean supports(MediaFormat var1);
    }

}

