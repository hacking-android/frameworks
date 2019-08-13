/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.annotation.UnsupportedAppUsage;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.graphics.Region;
import android.util.Log;
import android.view.View;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class ViewTreeObserver {
    private static boolean sIllegalOnDrawModificationIsFatal;
    private boolean mAlive;
    private CopyOnWriteArray<Consumer<List<Rect>>> mGestureExclusionListeners;
    private boolean mInDispatchOnDraw;
    @UnsupportedAppUsage
    private CopyOnWriteArray<OnComputeInternalInsetsListener> mOnComputeInternalInsetsListeners;
    private ArrayList<OnDrawListener> mOnDrawListeners;
    private CopyOnWriteArrayList<OnEnterAnimationCompleteListener> mOnEnterAnimationCompleteListeners;
    private ArrayList<Runnable> mOnFrameCommitListeners;
    private CopyOnWriteArrayList<OnGlobalFocusChangeListener> mOnGlobalFocusListeners;
    @UnsupportedAppUsage
    private CopyOnWriteArray<OnGlobalLayoutListener> mOnGlobalLayoutListeners;
    private CopyOnWriteArray<OnPreDrawListener> mOnPreDrawListeners;
    @UnsupportedAppUsage
    private CopyOnWriteArray<OnScrollChangedListener> mOnScrollChangedListeners;
    @UnsupportedAppUsage
    private CopyOnWriteArrayList<OnTouchModeChangeListener> mOnTouchModeChangeListeners;
    private CopyOnWriteArrayList<OnWindowAttachListener> mOnWindowAttachListeners;
    private CopyOnWriteArrayList<OnWindowFocusChangeListener> mOnWindowFocusListeners;
    private CopyOnWriteArray<OnWindowShownListener> mOnWindowShownListeners;
    private boolean mWindowShown;

    ViewTreeObserver(Context context) {
        boolean bl = true;
        this.mAlive = true;
        if (context.getApplicationInfo().targetSdkVersion < 26) {
            bl = false;
        }
        sIllegalOnDrawModificationIsFatal = bl;
    }

    private void checkIsAlive() {
        if (this.mAlive) {
            return;
        }
        throw new IllegalStateException("This ViewTreeObserver is not alive, call getViewTreeObserver() again");
    }

    private void kill() {
        this.mAlive = false;
    }

    @UnsupportedAppUsage
    public void addOnComputeInternalInsetsListener(OnComputeInternalInsetsListener onComputeInternalInsetsListener) {
        this.checkIsAlive();
        if (this.mOnComputeInternalInsetsListeners == null) {
            this.mOnComputeInternalInsetsListeners = new CopyOnWriteArray();
        }
        this.mOnComputeInternalInsetsListeners.add(onComputeInternalInsetsListener);
    }

    public void addOnDrawListener(OnDrawListener onDrawListener) {
        this.checkIsAlive();
        if (this.mOnDrawListeners == null) {
            this.mOnDrawListeners = new ArrayList();
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException illegalStateException = new IllegalStateException("Cannot call addOnDrawListener inside of onDraw");
            if (!sIllegalOnDrawModificationIsFatal) {
                Log.e("ViewTreeObserver", illegalStateException.getMessage(), illegalStateException);
            } else {
                throw illegalStateException;
            }
        }
        this.mOnDrawListeners.add(onDrawListener);
    }

    public void addOnEnterAnimationCompleteListener(OnEnterAnimationCompleteListener onEnterAnimationCompleteListener) {
        this.checkIsAlive();
        if (this.mOnEnterAnimationCompleteListeners == null) {
            this.mOnEnterAnimationCompleteListeners = new CopyOnWriteArrayList();
        }
        this.mOnEnterAnimationCompleteListeners.add(onEnterAnimationCompleteListener);
    }

    public void addOnGlobalFocusChangeListener(OnGlobalFocusChangeListener onGlobalFocusChangeListener) {
        this.checkIsAlive();
        if (this.mOnGlobalFocusListeners == null) {
            this.mOnGlobalFocusListeners = new CopyOnWriteArrayList();
        }
        this.mOnGlobalFocusListeners.add(onGlobalFocusChangeListener);
    }

    public void addOnGlobalLayoutListener(OnGlobalLayoutListener onGlobalLayoutListener) {
        this.checkIsAlive();
        if (this.mOnGlobalLayoutListeners == null) {
            this.mOnGlobalLayoutListeners = new CopyOnWriteArray();
        }
        this.mOnGlobalLayoutListeners.add(onGlobalLayoutListener);
    }

    public void addOnPreDrawListener(OnPreDrawListener onPreDrawListener) {
        this.checkIsAlive();
        if (this.mOnPreDrawListeners == null) {
            this.mOnPreDrawListeners = new CopyOnWriteArray();
        }
        this.mOnPreDrawListeners.add(onPreDrawListener);
    }

    public void addOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.checkIsAlive();
        if (this.mOnScrollChangedListeners == null) {
            this.mOnScrollChangedListeners = new CopyOnWriteArray();
        }
        this.mOnScrollChangedListeners.add(onScrollChangedListener);
    }

    public void addOnSystemGestureExclusionRectsChangedListener(Consumer<List<Rect>> consumer) {
        this.checkIsAlive();
        if (this.mGestureExclusionListeners == null) {
            this.mGestureExclusionListeners = new CopyOnWriteArray();
        }
        this.mGestureExclusionListeners.add(consumer);
    }

    public void addOnTouchModeChangeListener(OnTouchModeChangeListener onTouchModeChangeListener) {
        this.checkIsAlive();
        if (this.mOnTouchModeChangeListeners == null) {
            this.mOnTouchModeChangeListeners = new CopyOnWriteArrayList();
        }
        this.mOnTouchModeChangeListeners.add(onTouchModeChangeListener);
    }

    public void addOnWindowAttachListener(OnWindowAttachListener onWindowAttachListener) {
        this.checkIsAlive();
        if (this.mOnWindowAttachListeners == null) {
            this.mOnWindowAttachListeners = new CopyOnWriteArrayList();
        }
        this.mOnWindowAttachListeners.add(onWindowAttachListener);
    }

    public void addOnWindowFocusChangeListener(OnWindowFocusChangeListener onWindowFocusChangeListener) {
        this.checkIsAlive();
        if (this.mOnWindowFocusListeners == null) {
            this.mOnWindowFocusListeners = new CopyOnWriteArrayList();
        }
        this.mOnWindowFocusListeners.add(onWindowFocusChangeListener);
    }

    public void addOnWindowShownListener(OnWindowShownListener onWindowShownListener) {
        this.checkIsAlive();
        if (this.mOnWindowShownListeners == null) {
            this.mOnWindowShownListeners = new CopyOnWriteArray();
        }
        this.mOnWindowShownListeners.add(onWindowShownListener);
        if (this.mWindowShown) {
            onWindowShownListener.onWindowShown();
        }
    }

    ArrayList<Runnable> captureFrameCommitCallbacks() {
        ArrayList<Runnable> arrayList = this.mOnFrameCommitListeners;
        this.mOnFrameCommitListeners = null;
        return arrayList;
    }

    @UnsupportedAppUsage
    final void dispatchOnComputeInternalInsets(InternalInsetsInfo internalInsetsInfo) {
        CopyOnWriteArray<OnComputeInternalInsetsListener> copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            int n;
            CopyOnWriteArray.Access<OnComputeInternalInsetsListener> access = copyOnWriteArray.start();
            try {
                n = access.size();
            }
            catch (Throwable throwable) {
                copyOnWriteArray.end();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                access.get(i).onComputeInternalInsets(internalInsetsInfo);
            }
            copyOnWriteArray.end();
        }
    }

    public final void dispatchOnDraw() {
        if (this.mOnDrawListeners != null) {
            this.mInDispatchOnDraw = true;
            ArrayList<OnDrawListener> arrayList = this.mOnDrawListeners;
            int n = arrayList.size();
            for (int i = 0; i < n; ++i) {
                arrayList.get(i).onDraw();
            }
            this.mInDispatchOnDraw = false;
        }
    }

    public final void dispatchOnEnterAnimationComplete() {
        CopyOnWriteArrayList<OnEnterAnimationCompleteListener> copyOnWriteArrayList = this.mOnEnterAnimationCompleteListeners;
        if (copyOnWriteArrayList != null && !copyOnWriteArrayList.isEmpty()) {
            copyOnWriteArrayList = copyOnWriteArrayList.iterator();
            while (copyOnWriteArrayList.hasNext()) {
                ((OnEnterAnimationCompleteListener)copyOnWriteArrayList.next()).onEnterAnimationComplete();
            }
        }
    }

    @UnsupportedAppUsage
    final void dispatchOnGlobalFocusChange(View view, View view2) {
        CopyOnWriteArrayList<OnGlobalFocusChangeListener> copyOnWriteArrayList = this.mOnGlobalFocusListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            copyOnWriteArrayList = copyOnWriteArrayList.iterator();
            while (copyOnWriteArrayList.hasNext()) {
                ((OnGlobalFocusChangeListener)copyOnWriteArrayList.next()).onGlobalFocusChanged(view, view2);
            }
        }
    }

    public final void dispatchOnGlobalLayout() {
        CopyOnWriteArray<OnGlobalLayoutListener> copyOnWriteArray = this.mOnGlobalLayoutListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            int n;
            CopyOnWriteArray.Access<OnGlobalLayoutListener> access = copyOnWriteArray.start();
            try {
                n = access.size();
            }
            catch (Throwable throwable) {
                copyOnWriteArray.end();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                access.get(i).onGlobalLayout();
            }
            copyOnWriteArray.end();
        }
    }

    public final boolean dispatchOnPreDraw() {
        boolean bl = false;
        boolean bl2 = false;
        CopyOnWriteArray<OnPreDrawListener> copyOnWriteArray = this.mOnPreDrawListeners;
        boolean bl3 = bl;
        if (copyOnWriteArray != null) {
            bl3 = bl;
            if (copyOnWriteArray.size() > 0) {
                int n;
                CopyOnWriteArray.Access<OnPreDrawListener> access = copyOnWriteArray.start();
                try {
                    n = access.size();
                    bl3 = bl2;
                }
                catch (Throwable throwable) {
                    copyOnWriteArray.end();
                    throw throwable;
                }
                for (int i = 0; i < n; ++i) {
                    bl2 = access.get(i).onPreDraw();
                    bl3 |= bl2 ^ true;
                }
                copyOnWriteArray.end();
            }
        }
        return bl3;
    }

    @UnsupportedAppUsage
    final void dispatchOnScrollChanged() {
        CopyOnWriteArray<OnScrollChangedListener> copyOnWriteArray = this.mOnScrollChangedListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            int n;
            CopyOnWriteArray.Access<OnScrollChangedListener> access = copyOnWriteArray.start();
            try {
                n = access.size();
            }
            catch (Throwable throwable) {
                copyOnWriteArray.end();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                access.get(i).onScrollChanged();
            }
            copyOnWriteArray.end();
        }
    }

    void dispatchOnSystemGestureExclusionRectsChanged(List<Rect> list) {
        CopyOnWriteArray<Consumer<List<Rect>>> copyOnWriteArray = this.mGestureExclusionListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            int n;
            CopyOnWriteArray.Access<Consumer<List<Rect>>> access = copyOnWriteArray.start();
            try {
                n = access.size();
            }
            catch (Throwable throwable) {
                copyOnWriteArray.end();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                access.get(i).accept(list);
            }
            copyOnWriteArray.end();
        }
    }

    @UnsupportedAppUsage
    final void dispatchOnTouchModeChanged(boolean bl) {
        CopyOnWriteArrayList<OnTouchModeChangeListener> copyOnWriteArrayList = this.mOnTouchModeChangeListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            copyOnWriteArrayList = copyOnWriteArrayList.iterator();
            while (copyOnWriteArrayList.hasNext()) {
                ((OnTouchModeChangeListener)copyOnWriteArrayList.next()).onTouchModeChanged(bl);
            }
        }
    }

    final void dispatchOnWindowAttachedChange(boolean bl) {
        CopyOnWriteArrayList<OnWindowAttachListener> copyOnWriteArrayList2 = this.mOnWindowAttachListeners;
        if (copyOnWriteArrayList2 != null && copyOnWriteArrayList2.size() > 0) {
            for (OnWindowAttachListener onWindowAttachListener : copyOnWriteArrayList2) {
                if (bl) {
                    onWindowAttachListener.onWindowAttached();
                    continue;
                }
                onWindowAttachListener.onWindowDetached();
            }
        }
    }

    final void dispatchOnWindowFocusChange(boolean bl) {
        CopyOnWriteArrayList<OnWindowFocusChangeListener> copyOnWriteArrayList = this.mOnWindowFocusListeners;
        if (copyOnWriteArrayList != null && copyOnWriteArrayList.size() > 0) {
            copyOnWriteArrayList = copyOnWriteArrayList.iterator();
            while (copyOnWriteArrayList.hasNext()) {
                ((OnWindowFocusChangeListener)copyOnWriteArrayList.next()).onWindowFocusChanged(bl);
            }
        }
    }

    public final void dispatchOnWindowShown() {
        this.mWindowShown = true;
        CopyOnWriteArray<OnWindowShownListener> copyOnWriteArray = this.mOnWindowShownListeners;
        if (copyOnWriteArray != null && copyOnWriteArray.size() > 0) {
            int n;
            CopyOnWriteArray.Access<OnWindowShownListener> access = copyOnWriteArray.start();
            try {
                n = access.size();
            }
            catch (Throwable throwable) {
                copyOnWriteArray.end();
                throw throwable;
            }
            for (int i = 0; i < n; ++i) {
                access.get(i).onWindowShown();
            }
            copyOnWriteArray.end();
        }
    }

    @UnsupportedAppUsage
    final boolean hasComputeInternalInsetsListeners() {
        CopyOnWriteArray<OnComputeInternalInsetsListener> copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
        boolean bl = copyOnWriteArray != null && copyOnWriteArray.size() > 0;
        return bl;
    }

    final boolean hasOnPreDrawListeners() {
        CopyOnWriteArray<OnPreDrawListener> copyOnWriteArray = this.mOnPreDrawListeners;
        boolean bl = copyOnWriteArray != null && copyOnWriteArray.size() > 0;
        return bl;
    }

    public boolean isAlive() {
        return this.mAlive;
    }

    void merge(ViewTreeObserver viewTreeObserver) {
        CopyOnWriteArray<OnWindowShownListener> copyOnWriteArray;
        CopyOnWriteArray<Object> copyOnWriteArray2 = viewTreeObserver.mOnWindowAttachListeners;
        if (copyOnWriteArray2 != null) {
            copyOnWriteArray = this.mOnWindowAttachListeners;
            if (copyOnWriteArray != null) {
                ((CopyOnWriteArrayList)((Object)copyOnWriteArray)).addAll((Collection<OnWindowShownListener>)((Object)copyOnWriteArray2));
            } else {
                this.mOnWindowAttachListeners = copyOnWriteArray2;
            }
        }
        if ((copyOnWriteArray = viewTreeObserver.mOnWindowFocusListeners) != null) {
            copyOnWriteArray2 = this.mOnWindowFocusListeners;
            if (copyOnWriteArray2 != null) {
                ((CopyOnWriteArrayList)((Object)copyOnWriteArray2)).addAll((Collection<OnScrollChangedListener>)((Object)copyOnWriteArray));
            } else {
                this.mOnWindowFocusListeners = copyOnWriteArray;
            }
        }
        if ((copyOnWriteArray2 = viewTreeObserver.mOnGlobalFocusListeners) != null) {
            copyOnWriteArray = this.mOnGlobalFocusListeners;
            if (copyOnWriteArray != null) {
                ((CopyOnWriteArrayList)((Object)copyOnWriteArray)).addAll((Collection<OnWindowShownListener>)((Object)copyOnWriteArray2));
            } else {
                this.mOnGlobalFocusListeners = copyOnWriteArray2;
            }
        }
        if ((copyOnWriteArray = viewTreeObserver.mOnGlobalLayoutListeners) != null) {
            copyOnWriteArray2 = this.mOnGlobalLayoutListeners;
            if (copyOnWriteArray2 != null) {
                copyOnWriteArray2.addAll(copyOnWriteArray);
            } else {
                this.mOnGlobalLayoutListeners = copyOnWriteArray;
            }
        }
        if ((copyOnWriteArray = viewTreeObserver.mOnPreDrawListeners) != null) {
            copyOnWriteArray2 = this.mOnPreDrawListeners;
            if (copyOnWriteArray2 != null) {
                copyOnWriteArray2.addAll((CopyOnWriteArray<OnPreDrawListener>)copyOnWriteArray);
            } else {
                this.mOnPreDrawListeners = copyOnWriteArray;
            }
        }
        if ((copyOnWriteArray = viewTreeObserver.mOnDrawListeners) != null) {
            copyOnWriteArray2 = this.mOnDrawListeners;
            if (copyOnWriteArray2 != null) {
                ((ArrayList)((Object)copyOnWriteArray2)).addAll((Collection<OnScrollChangedListener>)((Object)copyOnWriteArray));
            } else {
                this.mOnDrawListeners = copyOnWriteArray;
            }
        }
        if (viewTreeObserver.mOnFrameCommitListeners != null) {
            copyOnWriteArray = this.mOnFrameCommitListeners;
            if (copyOnWriteArray != null) {
                ((ArrayList)((Object)copyOnWriteArray)).addAll(viewTreeObserver.captureFrameCommitCallbacks());
            } else {
                this.mOnFrameCommitListeners = viewTreeObserver.captureFrameCommitCallbacks();
            }
        }
        if ((copyOnWriteArray2 = viewTreeObserver.mOnTouchModeChangeListeners) != null) {
            copyOnWriteArray = this.mOnTouchModeChangeListeners;
            if (copyOnWriteArray != null) {
                ((CopyOnWriteArrayList)((Object)copyOnWriteArray)).addAll((Collection<Runnable>)((Object)copyOnWriteArray2));
            } else {
                this.mOnTouchModeChangeListeners = copyOnWriteArray2;
            }
        }
        if ((copyOnWriteArray2 = viewTreeObserver.mOnComputeInternalInsetsListeners) != null) {
            copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
            if (copyOnWriteArray != null) {
                copyOnWriteArray.addAll((CopyOnWriteArray<OnScrollChangedListener>)copyOnWriteArray2);
            } else {
                this.mOnComputeInternalInsetsListeners = copyOnWriteArray2;
            }
        }
        if ((copyOnWriteArray = viewTreeObserver.mOnScrollChangedListeners) != null) {
            copyOnWriteArray2 = this.mOnScrollChangedListeners;
            if (copyOnWriteArray2 != null) {
                copyOnWriteArray2.addAll(copyOnWriteArray);
            } else {
                this.mOnScrollChangedListeners = copyOnWriteArray;
            }
        }
        if ((copyOnWriteArray2 = viewTreeObserver.mOnWindowShownListeners) != null) {
            copyOnWriteArray = this.mOnWindowShownListeners;
            if (copyOnWriteArray != null) {
                copyOnWriteArray.addAll(copyOnWriteArray2);
            } else {
                this.mOnWindowShownListeners = copyOnWriteArray2;
            }
        }
        if ((copyOnWriteArray = viewTreeObserver.mGestureExclusionListeners) != null) {
            copyOnWriteArray2 = this.mGestureExclusionListeners;
            if (copyOnWriteArray2 != null) {
                copyOnWriteArray2.addAll(copyOnWriteArray);
            } else {
                this.mGestureExclusionListeners = copyOnWriteArray;
            }
        }
        viewTreeObserver.kill();
    }

    public void registerFrameCommitCallback(Runnable runnable) {
        this.checkIsAlive();
        if (this.mOnFrameCommitListeners == null) {
            this.mOnFrameCommitListeners = new ArrayList();
        }
        this.mOnFrameCommitListeners.add(runnable);
    }

    @Deprecated
    public void removeGlobalOnLayoutListener(OnGlobalLayoutListener onGlobalLayoutListener) {
        this.removeOnGlobalLayoutListener(onGlobalLayoutListener);
    }

    @UnsupportedAppUsage
    public void removeOnComputeInternalInsetsListener(OnComputeInternalInsetsListener onComputeInternalInsetsListener) {
        this.checkIsAlive();
        CopyOnWriteArray<OnComputeInternalInsetsListener> copyOnWriteArray = this.mOnComputeInternalInsetsListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onComputeInternalInsetsListener);
    }

    public void removeOnDrawListener(OnDrawListener onDrawListener) {
        this.checkIsAlive();
        if (this.mOnDrawListeners == null) {
            return;
        }
        if (this.mInDispatchOnDraw) {
            IllegalStateException illegalStateException = new IllegalStateException("Cannot call removeOnDrawListener inside of onDraw");
            if (!sIllegalOnDrawModificationIsFatal) {
                Log.e("ViewTreeObserver", illegalStateException.getMessage(), illegalStateException);
            } else {
                throw illegalStateException;
            }
        }
        this.mOnDrawListeners.remove(onDrawListener);
    }

    public void removeOnEnterAnimationCompleteListener(OnEnterAnimationCompleteListener onEnterAnimationCompleteListener) {
        this.checkIsAlive();
        CopyOnWriteArrayList<OnEnterAnimationCompleteListener> copyOnWriteArrayList = this.mOnEnterAnimationCompleteListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onEnterAnimationCompleteListener);
    }

    public void removeOnGlobalFocusChangeListener(OnGlobalFocusChangeListener onGlobalFocusChangeListener) {
        this.checkIsAlive();
        CopyOnWriteArrayList<OnGlobalFocusChangeListener> copyOnWriteArrayList = this.mOnGlobalFocusListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onGlobalFocusChangeListener);
    }

    public void removeOnGlobalLayoutListener(OnGlobalLayoutListener onGlobalLayoutListener) {
        this.checkIsAlive();
        CopyOnWriteArray<OnGlobalLayoutListener> copyOnWriteArray = this.mOnGlobalLayoutListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onGlobalLayoutListener);
    }

    public void removeOnPreDrawListener(OnPreDrawListener onPreDrawListener) {
        this.checkIsAlive();
        CopyOnWriteArray<OnPreDrawListener> copyOnWriteArray = this.mOnPreDrawListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onPreDrawListener);
    }

    public void removeOnScrollChangedListener(OnScrollChangedListener onScrollChangedListener) {
        this.checkIsAlive();
        CopyOnWriteArray<OnScrollChangedListener> copyOnWriteArray = this.mOnScrollChangedListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onScrollChangedListener);
    }

    public void removeOnSystemGestureExclusionRectsChangedListener(Consumer<List<Rect>> consumer) {
        this.checkIsAlive();
        CopyOnWriteArray<Consumer<List<Rect>>> copyOnWriteArray = this.mGestureExclusionListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(consumer);
    }

    public void removeOnTouchModeChangeListener(OnTouchModeChangeListener onTouchModeChangeListener) {
        this.checkIsAlive();
        CopyOnWriteArrayList<OnTouchModeChangeListener> copyOnWriteArrayList = this.mOnTouchModeChangeListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onTouchModeChangeListener);
    }

    public void removeOnWindowAttachListener(OnWindowAttachListener onWindowAttachListener) {
        this.checkIsAlive();
        CopyOnWriteArrayList<OnWindowAttachListener> copyOnWriteArrayList = this.mOnWindowAttachListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onWindowAttachListener);
    }

    public void removeOnWindowFocusChangeListener(OnWindowFocusChangeListener onWindowFocusChangeListener) {
        this.checkIsAlive();
        CopyOnWriteArrayList<OnWindowFocusChangeListener> copyOnWriteArrayList = this.mOnWindowFocusListeners;
        if (copyOnWriteArrayList == null) {
            return;
        }
        copyOnWriteArrayList.remove(onWindowFocusChangeListener);
    }

    public void removeOnWindowShownListener(OnWindowShownListener onWindowShownListener) {
        this.checkIsAlive();
        CopyOnWriteArray<OnWindowShownListener> copyOnWriteArray = this.mOnWindowShownListeners;
        if (copyOnWriteArray == null) {
            return;
        }
        copyOnWriteArray.remove(onWindowShownListener);
    }

    public boolean unregisterFrameCommitCallback(Runnable runnable) {
        this.checkIsAlive();
        ArrayList<Runnable> arrayList = this.mOnFrameCommitListeners;
        if (arrayList == null) {
            return false;
        }
        return arrayList.remove(runnable);
    }

    static class CopyOnWriteArray<T> {
        private final Access<T> mAccess = new Access();
        private ArrayList<T> mData = new ArrayList();
        private ArrayList<T> mDataCopy;
        private boolean mStart;

        CopyOnWriteArray() {
        }

        private ArrayList<T> getArray() {
            if (this.mStart) {
                if (this.mDataCopy == null) {
                    this.mDataCopy = new ArrayList<T>(this.mData);
                }
                return this.mDataCopy;
            }
            return this.mData;
        }

        void add(T t) {
            this.getArray().add(t);
        }

        void addAll(CopyOnWriteArray<T> copyOnWriteArray) {
            this.getArray().addAll(copyOnWriteArray.mData);
        }

        void clear() {
            this.getArray().clear();
        }

        void end() {
            if (this.mStart) {
                this.mStart = false;
                ArrayList<T> arrayList = this.mDataCopy;
                if (arrayList != null) {
                    this.mData = arrayList;
                    this.mAccess.mData.clear();
                    this.mAccess.mSize = 0;
                }
                this.mDataCopy = null;
                return;
            }
            throw new IllegalStateException("Iteration not started");
        }

        void remove(T t) {
            this.getArray().remove(t);
        }

        int size() {
            return this.getArray().size();
        }

        Access<T> start() {
            if (!this.mStart) {
                this.mStart = true;
                this.mDataCopy = null;
                this.mAccess.mData = this.mData;
                this.mAccess.mSize = this.mData.size();
                return this.mAccess;
            }
            throw new IllegalStateException("Iteration already started");
        }

        static class Access<T> {
            private ArrayList<T> mData;
            private int mSize;

            Access() {
            }

            T get(int n) {
                return this.mData.get(n);
            }

            int size() {
                return this.mSize;
            }
        }

    }

    public static final class InternalInsetsInfo {
        public static final int TOUCHABLE_INSETS_CONTENT = 1;
        public static final int TOUCHABLE_INSETS_FRAME = 0;
        @UnsupportedAppUsage
        public static final int TOUCHABLE_INSETS_REGION = 3;
        public static final int TOUCHABLE_INSETS_VISIBLE = 2;
        @UnsupportedAppUsage
        public final Rect contentInsets = new Rect();
        @UnsupportedAppUsage
        int mTouchableInsets;
        @UnsupportedAppUsage
        public final Region touchableRegion = new Region();
        @UnsupportedAppUsage
        public final Rect visibleInsets = new Rect();

        public boolean equals(Object object) {
            boolean bl = true;
            if (this == object) {
                return true;
            }
            if (object != null && this.getClass() == object.getClass()) {
                object = (InternalInsetsInfo)object;
                if (!(this.mTouchableInsets == ((InternalInsetsInfo)object).mTouchableInsets && this.contentInsets.equals(((InternalInsetsInfo)object).contentInsets) && this.visibleInsets.equals(((InternalInsetsInfo)object).visibleInsets) && this.touchableRegion.equals(((InternalInsetsInfo)object).touchableRegion))) {
                    bl = false;
                }
                return bl;
            }
            return false;
        }

        public int hashCode() {
            return ((this.contentInsets.hashCode() * 31 + this.visibleInsets.hashCode()) * 31 + this.touchableRegion.hashCode()) * 31 + this.mTouchableInsets;
        }

        boolean isEmpty() {
            boolean bl = this.contentInsets.isEmpty() && this.visibleInsets.isEmpty() && this.touchableRegion.isEmpty() && this.mTouchableInsets == 0;
            return bl;
        }

        void reset() {
            this.contentInsets.setEmpty();
            this.visibleInsets.setEmpty();
            this.touchableRegion.setEmpty();
            this.mTouchableInsets = 0;
        }

        @UnsupportedAppUsage
        void set(InternalInsetsInfo internalInsetsInfo) {
            this.contentInsets.set(internalInsetsInfo.contentInsets);
            this.visibleInsets.set(internalInsetsInfo.visibleInsets);
            this.touchableRegion.set(internalInsetsInfo.touchableRegion);
            this.mTouchableInsets = internalInsetsInfo.mTouchableInsets;
        }

        @UnsupportedAppUsage
        public void setTouchableInsets(int n) {
            this.mTouchableInsets = n;
        }
    }

    public static interface OnComputeInternalInsetsListener {
        public void onComputeInternalInsets(InternalInsetsInfo var1);
    }

    public static interface OnDrawListener {
        public void onDraw();
    }

    public static interface OnEnterAnimationCompleteListener {
        public void onEnterAnimationComplete();
    }

    public static interface OnGlobalFocusChangeListener {
        public void onGlobalFocusChanged(View var1, View var2);
    }

    public static interface OnGlobalLayoutListener {
        public void onGlobalLayout();
    }

    public static interface OnPreDrawListener {
        public boolean onPreDraw();
    }

    public static interface OnScrollChangedListener {
        public void onScrollChanged();
    }

    public static interface OnTouchModeChangeListener {
        public void onTouchModeChanged(boolean var1);
    }

    public static interface OnWindowAttachListener {
        public void onWindowAttached();

        public void onWindowDetached();
    }

    public static interface OnWindowFocusChangeListener {
        public void onWindowFocusChanged(boolean var1);
    }

    public static interface OnWindowShownListener {
        public void onWindowShown();
    }

}

