/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.os.Process;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.ClickableSpan;
import android.util.LongSparseArray;
import android.util.Slog;
import android.view.Display;
import android.view.InputEvent;
import android.view.MagnificationSpec;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewRootImpl;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeIdManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.accessibility.AccessibilityRequestPreparer;
import android.view.accessibility.IAccessibilityInteractionConnectionCallback;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.annotations.VisibleForTesting;
import com.android.internal.os.SomeArgs;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

@VisibleForTesting(visibility=VisibleForTesting.Visibility.PACKAGE)
public final class AccessibilityInteractionController {
    private static final boolean CONSIDER_REQUEST_PREPARERS = false;
    private static final boolean ENFORCE_NODE_TREE_CONSISTENT = false;
    private static final boolean IGNORE_REQUEST_PREPARERS = true;
    private static final String LOG_TAG = "AccessibilityInteractionController";
    private static final long REQUEST_PREPARER_TIMEOUT_MS = 500L;
    private final AccessibilityManager mA11yManager;
    @GuardedBy(value={"mLock"})
    private int mActiveRequestPreparerId;
    private AddNodeInfosForViewId mAddNodeInfosForViewId;
    private final PrivateHandler mHandler;
    private final Object mLock = new Object();
    @GuardedBy(value={"mLock"})
    private List<MessageHolder> mMessagesWaitingForRequestPreparer;
    private final long mMyLooperThreadId;
    private final int mMyProcessId;
    @GuardedBy(value={"mLock"})
    private int mNumActiveRequestPreparers;
    private final AccessibilityNodePrefetcher mPrefetcher;
    private final ArrayList<AccessibilityNodeInfo> mTempAccessibilityNodeInfoList = new ArrayList();
    private final ArrayList<View> mTempArrayList = new ArrayList();
    private final Point mTempPoint = new Point();
    private final Rect mTempRect = new Rect();
    private final Rect mTempRect1 = new Rect();
    private final Rect mTempRect2 = new Rect();
    private final ViewRootImpl mViewRootImpl;

    public AccessibilityInteractionController(ViewRootImpl viewRootImpl) {
        Looper looper = viewRootImpl.mHandler.getLooper();
        this.mMyLooperThreadId = looper.getThread().getId();
        this.mMyProcessId = Process.myPid();
        this.mHandler = new PrivateHandler(looper);
        this.mViewRootImpl = viewRootImpl;
        this.mPrefetcher = new AccessibilityNodePrefetcher();
        this.mA11yManager = this.mViewRootImpl.mContext.getSystemService(AccessibilityManager.class);
    }

    static /* synthetic */ ViewRootImpl access$200(AccessibilityInteractionController accessibilityInteractionController) {
        return accessibilityInteractionController.mViewRootImpl;
    }

    private void adjustIsVisibleToUserIfNeeded(AccessibilityNodeInfo accessibilityNodeInfo, Region region) {
        if (region != null && accessibilityNodeInfo != null) {
            Rect rect = this.mTempRect;
            accessibilityNodeInfo.getBoundsInScreen(rect);
            if (region.quickReject(rect) && !this.shouldBypassAdjustIsVisible()) {
                accessibilityNodeInfo.setVisibleToUser(false);
            }
            return;
        }
    }

    private void adjustIsVisibleToUserIfNeeded(List<AccessibilityNodeInfo> list, Region region) {
        if (region != null && list != null) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                this.adjustIsVisibleToUserIfNeeded(list.get(i), region);
            }
            return;
        }
    }

    private void applyAppScaleAndMagnificationSpecIfNeeded(AccessibilityNodeInfo accessibilityNodeInfo, MagnificationSpec parcelable) {
        Object object;
        int n;
        if (accessibilityNodeInfo == null) {
            return;
        }
        float f = this.mViewRootImpl.mAttachInfo.mApplicationScale;
        if (!this.shouldApplyAppScaleAndMagnificationSpec(f, (MagnificationSpec)parcelable)) {
            return;
        }
        Parcelable parcelable2 = this.mTempRect;
        Rect rect = this.mTempRect1;
        accessibilityNodeInfo.getBoundsInParent((Rect)parcelable2);
        accessibilityNodeInfo.getBoundsInScreen(rect);
        if (f != 1.0f) {
            ((Rect)parcelable2).scale(f);
            rect.scale(f);
        }
        if (parcelable != null) {
            ((Rect)parcelable2).scale(((MagnificationSpec)parcelable).scale);
            rect.scale(((MagnificationSpec)parcelable).scale);
            rect.offset((int)((MagnificationSpec)parcelable).offsetX, (int)((MagnificationSpec)parcelable).offsetY);
        }
        accessibilityNodeInfo.setBoundsInParent((Rect)parcelable2);
        accessibilityNodeInfo.setBoundsInScreen(rect);
        if (accessibilityNodeInfo.hasExtras()) {
            parcelable2 = accessibilityNodeInfo.getExtras();
            if ((parcelable2 = ((Bundle)parcelable2).getParcelableArray("android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY")) != null) {
                for (n = 0; n < ((Parcelable)parcelable2).length; ++n) {
                    object = (RectF)parcelable2[n];
                    ((RectF)object).scale(f);
                    if (parcelable == null) continue;
                    ((RectF)object).scale(((MagnificationSpec)parcelable).scale);
                    ((RectF)object).offset(((MagnificationSpec)parcelable).offsetX, ((MagnificationSpec)parcelable).offsetY);
                }
            }
        }
        if (parcelable != null) {
            object = this.mViewRootImpl.mAttachInfo;
            if (((View.AttachInfo)object).mDisplay == null) {
                return;
            }
            f = ((View.AttachInfo)object).mApplicationScale * ((MagnificationSpec)parcelable).scale;
            parcelable2 = this.mTempRect1;
            ((Rect)parcelable2).left = (int)((float)((View.AttachInfo)object).mWindowLeft * f + ((MagnificationSpec)parcelable).offsetX);
            ((Rect)parcelable2).top = (int)((float)((View.AttachInfo)object).mWindowTop * f + ((MagnificationSpec)parcelable).offsetY);
            ((Rect)parcelable2).right = (int)((float)((Rect)parcelable2).left + (float)this.mViewRootImpl.mWidth * f);
            ((Rect)parcelable2).bottom = (int)((float)((Rect)parcelable2).top + (float)this.mViewRootImpl.mHeight * f);
            ((View.AttachInfo)object).mDisplay.getRealSize(this.mTempPoint);
            n = this.mTempPoint.x;
            int n2 = this.mTempPoint.y;
            parcelable = this.mTempRect2;
            ((Rect)parcelable).set(0, 0, n, n2);
            if (!((Rect)parcelable2).intersect((Rect)parcelable)) {
                ((Rect)parcelable).setEmpty();
            }
            if (!((Rect)parcelable2).intersects(rect.left, rect.top, rect.right, rect.bottom)) {
                accessibilityNodeInfo.setVisibleToUser(false);
            }
        }
    }

    private void applyAppScaleAndMagnificationSpecIfNeeded(List<AccessibilityNodeInfo> list, MagnificationSpec magnificationSpec) {
        if (list == null) {
            return;
        }
        if (this.shouldApplyAppScaleAndMagnificationSpec(this.mViewRootImpl.mAttachInfo.mApplicationScale, magnificationSpec)) {
            int n = list.size();
            for (int i = 0; i < n; ++i) {
                this.applyAppScaleAndMagnificationSpecIfNeeded(list.get(i), magnificationSpec);
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void clearAccessibilityFocusUiThread() {
        if (this.mViewRootImpl.mView == null) return;
        if (this.mViewRootImpl.mAttachInfo == null) {
            return;
        }
        try {
            this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 8;
            Object object = this.mViewRootImpl.mView;
            if (object == null) return;
            if (!this.isShown((View)object)) return;
            View view = this.mViewRootImpl.mAccessibilityFocusedHost;
            if (view == null) return;
            if (!ViewRootImpl.isViewDescendantOf(view, (View)object)) {
                return;
            }
            AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
            object = this.mViewRootImpl.mAccessibilityFocusedVirtualView;
            if (accessibilityNodeProvider != null && object != null) {
                accessibilityNodeProvider.performAction(AccessibilityNodeInfo.getVirtualDescendantId(((AccessibilityNodeInfo)object).getSourceNodeId()), AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
                return;
            }
            view.performAccessibilityAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLEAR_ACCESSIBILITY_FOCUS.getId(), null);
            return;
        }
        finally {
            this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 0;
        }
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void findAccessibilityNodeInfoByAccessibilityIdUiThread(Message parcelable) {
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback;
        MagnificationSpec magnificationSpec;
        int n;
        Object object;
        void var1_4;
        Region region;
        block6 : {
            block4 : {
                block5 : {
                    int n2 = ((Message)parcelable).arg1;
                    object = (SomeArgs)((Message)parcelable).obj;
                    int n3 = ((SomeArgs)object).argi1;
                    int n4 = ((SomeArgs)object).argi2;
                    n = ((SomeArgs)object).argi3;
                    iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback)((SomeArgs)object).arg1;
                    magnificationSpec = (MagnificationSpec)((SomeArgs)object).arg2;
                    region = (Region)((SomeArgs)object).arg3;
                    parcelable = (Bundle)((SomeArgs)object).arg4;
                    ((SomeArgs)object).recycle();
                    object = this.mTempAccessibilityNodeInfoList;
                    object.clear();
                    if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) break block4;
                    this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = n2;
                    View view = this.findViewByAccessibilityId(n3);
                    if (view == null || !this.isShown(view)) break block5;
                    AccessibilityNodePrefetcher accessibilityNodePrefetcher = this.mPrefetcher;
                    try {
                        accessibilityNodePrefetcher.prefetchAccessibilityNodeInfos(view, n4, n2, (List<AccessibilityNodeInfo>)object, (Bundle)parcelable);
                    }
                    catch (Throwable throwable) {
                        break block6;
                    }
                }
                this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
                return;
            }
            this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
            return;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
        throw var1_4;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void findAccessibilityNodeInfosByTextUiThread(Message object) {
        int n;
        MagnificationSpec magnificationSpec;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback;
        Region region;
        block10 : {
            int n3;
            String string2;
            Object object3;
            Object object2;
            int n2;
            block11 : {
                block12 : {
                    block9 : {
                        n2 = ((Message)object).arg1;
                        object = (SomeArgs)((Message)object).obj;
                        string2 = (String)((SomeArgs)object).arg1;
                        iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback)((SomeArgs)object).arg2;
                        magnificationSpec = (MagnificationSpec)((SomeArgs)object).arg3;
                        int n4 = ((SomeArgs)object).argi1;
                        n3 = ((SomeArgs)object).argi2;
                        n = ((SomeArgs)object).argi3;
                        region = (Region)((SomeArgs)object).arg4;
                        ((SomeArgs)object).recycle();
                        object3 = null;
                        object = null;
                        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) break block9;
                        this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = n2;
                        View view = this.findViewByAccessibilityId(n4);
                        if (view == null) break block10;
                        try {
                            if (!this.isShown(view)) break block10;
                            object2 = view.getAccessibilityNodeProvider();
                            if (object2 != null) {
                                object = ((AccessibilityNodeProvider)object2).findAccessibilityNodeInfosByText(string2, n3);
                                break block10;
                            }
                            if (n3 != -1) break block10;
                            object2 = this.mTempArrayList;
                            ((ArrayList)object2).clear();
                            view.findViewsWithText((ArrayList<View>)object2, string2, 7);
                            if (((ArrayList)object2).isEmpty()) break block10;
                            object = this.mTempAccessibilityNodeInfoList;
                        }
                        catch (Throwable throwable) {}
                        try {
                            object.clear();
                            n2 = ((ArrayList)object2).size();
                            object3 = object2;
                            break block11;
                        }
                        catch (Throwable throwable) {
                            object3 = object;
                            object = throwable;
                            break block12;
                        }
                        break block12;
                    }
                    this.updateInfosForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
                    return;
                    catch (Throwable throwable) {
                        // empty catch block
                    }
                }
                this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object3, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
                throw object;
            }
            for (n3 = 0; n3 < n2; ++n3) {
                View view = (View)((ArrayList)object3).get(n3);
                if (!this.isShown(view)) continue;
                object2 = view.getAccessibilityNodeProvider();
                if (object2 != null) {
                    if ((object2 = ((AccessibilityNodeProvider)object2).findAccessibilityNodeInfosByText(string2, -1)) == null) continue;
                    object.addAll(object2);
                    continue;
                }
                object.add(view.createAccessibilityNodeInfo());
            }
        }
        this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void findAccessibilityNodeInfosByViewIdUiThread(Message object) {
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback;
        Object object2;
        void var1_4;
        Region region;
        MagnificationSpec magnificationSpec;
        int n;
        block8 : {
            block5 : {
                block6 : {
                    View view;
                    int n2;
                    block7 : {
                        n2 = ((Message)object).arg1;
                        int n3 = ((Message)object).arg2;
                        object2 = (SomeArgs)((Message)object).obj;
                        n = ((SomeArgs)object2).argi1;
                        iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback)((SomeArgs)object2).arg1;
                        magnificationSpec = (MagnificationSpec)((SomeArgs)object2).arg2;
                        object = (String)((SomeArgs)object2).arg3;
                        region = (Region)((SomeArgs)object2).arg4;
                        ((SomeArgs)object2).recycle();
                        object2 = this.mTempAccessibilityNodeInfoList;
                        object2.clear();
                        if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) break block5;
                        this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = n2;
                        view = this.findViewByAccessibilityId(n3);
                        if (view == null) break block6;
                        n2 = view.getContext().getResources().getIdentifier((String)object, null, null);
                        if (n2 > 0) break block7;
                        this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object2, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
                        return;
                    }
                    try {
                        if (this.mAddNodeInfosForViewId == null) {
                            this.mAddNodeInfosForViewId = object = new AddNodeInfosForViewId();
                        }
                        this.mAddNodeInfosForViewId.init(n2, (List<AccessibilityNodeInfo>)object2);
                        view.findViewByPredicate(this.mAddNodeInfosForViewId);
                        this.mAddNodeInfosForViewId.reset();
                    }
                    catch (Throwable throwable) {
                        break block8;
                    }
                }
                this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object2, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
                return;
            }
            this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object2, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
            return;
            catch (Throwable throwable) {
                // empty catch block
            }
        }
        this.updateInfosForViewportAndReturnFindNodeResult((List<AccessibilityNodeInfo>)object2, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
        throw var1_4;
    }

    private void findFocusUiThread(Message object) {
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback;
        Region region;
        int n;
        MagnificationSpec magnificationSpec;
        block29 : {
            block30 : {
                View view;
                Object object2;
                block34 : {
                    Object var11_12;
                    Object object3;
                    View view2;
                    int n2;
                    Object object4;
                    block31 : {
                        block32 : {
                            int n3;
                            block33 : {
                                int n4 = ((Message)object).arg1;
                                n2 = ((Message)object).arg2;
                                object = (SomeArgs)((Message)object).obj;
                                n = ((SomeArgs)object).argi1;
                                int n5 = ((SomeArgs)object).argi2;
                                n3 = ((SomeArgs)object).argi3;
                                iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback)((SomeArgs)object).arg1;
                                magnificationSpec = (MagnificationSpec)((SomeArgs)object).arg2;
                                region = (Region)((SomeArgs)object).arg3;
                                ((SomeArgs)object).recycle();
                                object4 = null;
                                var11_12 = null;
                                object3 = null;
                                view = null;
                                object2 = object3;
                                try {
                                    if (this.mViewRootImpl.mView == null) break block29;
                                    object2 = object3;
                                }
                                catch (Throwable throwable) {
                                    this.updateInfoForViewportAndReturnFindNodeResult((AccessibilityNodeInfo)object2, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
                                    throw throwable;
                                }
                                if (this.mViewRootImpl.mAttachInfo == null) break block29;
                                object2 = object3;
                                this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = n4;
                                object2 = object3;
                                view2 = this.findViewByAccessibilityId(n5);
                                object = var11_12;
                                if (view2 == null) break block30;
                                object = var11_12;
                                object2 = object3;
                                if (!this.isShown(view2)) break block30;
                                if (n2 == 1) break block31;
                                if (n2 != 2) break block32;
                                object2 = object3;
                                object4 = this.mViewRootImpl.mAccessibilityFocusedHost;
                                object = var11_12;
                                if (object4 == null) break block30;
                                object2 = object3;
                                if (!ViewRootImpl.isViewDescendantOf((View)object4, view2)) {
                                    object = var11_12;
                                    break block30;
                                }
                                object2 = object3;
                                if (!this.isShown((View)object4)) {
                                    object = var11_12;
                                    break block30;
                                }
                                object2 = object3;
                                if (((View)object4).getAccessibilityNodeProvider() == null) break block33;
                                object = view;
                                object2 = object3;
                                if (this.mViewRootImpl.mAccessibilityFocusedVirtualView == null) break block30;
                                object2 = object3;
                                object = AccessibilityNodeInfo.obtain(this.mViewRootImpl.mAccessibilityFocusedVirtualView);
                                break block30;
                            }
                            object = view;
                            if (n3 != -1) break block30;
                            object2 = object3;
                            object = ((View)object4).createAccessibilityNodeInfo();
                            break block30;
                        }
                        object2 = object3;
                        object2 = object3;
                        object2 = object3;
                        object = new StringBuilder();
                        object2 = object3;
                        ((StringBuilder)object).append("Unknown focus type: ");
                        object2 = object3;
                        ((StringBuilder)object).append(n2);
                        object2 = object3;
                        object4 = new IllegalArgumentException(((StringBuilder)object).toString());
                        object2 = object3;
                        throw object4;
                    }
                    object2 = object3;
                    view = view2.findFocus();
                    object2 = object3;
                    if (!this.isShown(view)) {
                        object = var11_12;
                        break block30;
                    }
                    object2 = object3;
                    object = view.getAccessibilityNodeProvider();
                    object2 = object4;
                    if (object == null) break block34;
                    object2 = object3;
                    object2 = object = ((AccessibilityNodeProvider)object).findFocus(n2);
                }
                object = object2;
                if (object2 == null) {
                    object = view.createAccessibilityNodeInfo();
                }
            }
            this.updateInfoForViewportAndReturnFindNodeResult((AccessibilityNodeInfo)object, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
            return;
        }
        this.updateInfoForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
    }

    private View findViewByAccessibilityId(int n) {
        if (n == 2147483646) {
            return this.mViewRootImpl.mView;
        }
        return AccessibilityNodeIdManager.getInstance().findView(n);
    }

    private void focusSearchUiThread(Message object) {
        Region region;
        int n;
        MagnificationSpec magnificationSpec;
        IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback;
        block4 : {
            block5 : {
                View view;
                int n2 = ((Message)object).arg1;
                int n3 = ((Message)object).arg2;
                object = (SomeArgs)((Message)object).obj;
                int n4 = ((SomeArgs)object).argi2;
                n = ((SomeArgs)object).argi3;
                iAccessibilityInteractionConnectionCallback = (IAccessibilityInteractionConnectionCallback)((SomeArgs)object).arg1;
                magnificationSpec = (MagnificationSpec)((SomeArgs)object).arg2;
                region = (Region)((SomeArgs)object).arg3;
                ((SomeArgs)object).recycle();
                Object var9_10 = null;
                try {
                    if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null) break block4;
                    this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = n2;
                    view = this.findViewByAccessibilityId(n3);
                    object = var9_10;
                    if (view == null) break block5;
                    object = var9_10;
                }
                catch (Throwable throwable) {
                    this.updateInfoForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
                    throw throwable;
                }
                if (!this.isShown(view)) break block5;
                view = view.focusSearch(n4);
                object = var9_10;
                if (view == null) break block5;
                object = view.createAccessibilityNodeInfo();
            }
            this.updateInfoForViewportAndReturnFindNodeResult((AccessibilityNodeInfo)object, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
            return;
        }
        this.updateInfoForViewportAndReturnFindNodeResult(null, iAccessibilityInteractionConnectionCallback, n, magnificationSpec, region);
    }

    private boolean handleClickableSpanActionUiThread(View view, int n, Bundle object) {
        Object t = ((Bundle)object).getParcelable("android.view.accessibility.action.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN");
        if (!(t instanceof AccessibilityClickableSpan)) {
            return false;
        }
        object = null;
        AccessibilityNodeProvider accessibilityNodeProvider = view.getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            object = accessibilityNodeProvider.createAccessibilityNodeInfo(n);
        } else if (n == -1) {
            object = view.createAccessibilityNodeInfo();
        }
        if (object == null) {
            return false;
        }
        if ((object = ((AccessibilityClickableSpan)t).findClickableSpan(((AccessibilityNodeInfo)object).getOriginalText())) != null) {
            ((ClickableSpan)object).onClick(view);
            return true;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean holdOffMessageIfNeeded(Message message, int n, long l) {
        Object object = this.mLock;
        synchronized (object) {
            if (this.mNumActiveRequestPreparers != 0) {
                this.queueMessageToHandleOncePrepared(message, n, l);
                return true;
            }
            if (message.what != 2) {
                return false;
            }
            SomeArgs someArgs = (SomeArgs)message.obj;
            Bundle bundle = (Bundle)someArgs.arg4;
            if (bundle == null) {
                return false;
            }
            int n2 = someArgs.argi1;
            List<AccessibilityRequestPreparer> list = this.mA11yManager.getRequestPreparersForAccessibilityId(n2);
            if (list == null) {
                return false;
            }
            String string2 = bundle.getString("android.view.accessibility.AccessibilityNodeInfo.extra_data_requested");
            if (string2 == null) {
                return false;
            }
            this.mNumActiveRequestPreparers = list.size();
            n2 = 0;
            do {
                if (n2 >= list.size()) {
                    this.queueMessageToHandleOncePrepared(message, n, l);
                    return true;
                }
                Message message2 = this.mHandler.obtainMessage(7);
                SomeArgs someArgs2 = SomeArgs.obtain();
                int n3 = someArgs.argi2 == Integer.MAX_VALUE ? -1 : someArgs.argi2;
                someArgs2.argi1 = n3;
                someArgs2.arg1 = list.get(n2);
                someArgs2.arg2 = string2;
                someArgs2.arg3 = bundle;
                Message message3 = this.mHandler.obtainMessage(8);
                this.mActiveRequestPreparerId = n3 = this.mActiveRequestPreparerId + 1;
                message3.arg1 = n3;
                someArgs2.arg4 = message3;
                message2.obj = someArgs2;
                this.scheduleMessage(message2, n, l, true);
                this.mHandler.obtainMessage(9);
                this.mHandler.sendEmptyMessageDelayed(9, 500L);
                ++n2;
            } while (true);
        }
    }

    private boolean isShown(View view) {
        boolean bl = view != null && view.getWindowVisibility() == 0 && view.isShown();
        return bl;
    }

    private void notifyOutsideTouchUiThread() {
        if (this.mViewRootImpl.mView != null && this.mViewRootImpl.mAttachInfo != null && !this.mViewRootImpl.mStopped && !this.mViewRootImpl.mPausedForTransition) {
            Object object = this.mViewRootImpl.mView;
            if (object != null && this.isShown((View)object)) {
                long l = SystemClock.uptimeMillis();
                object = MotionEvent.obtain(l, l, 4, 0.0f, 0.0f, 0);
                ((MotionEvent)object).setSource(4098);
                this.mViewRootImpl.dispatchInputEvent((InputEvent)object);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void performAccessibilityActionUiThread(Message object) {
        int n;
        block10 : {
            boolean bl;
            block11 : {
                int n2 = ((Message)object).arg1;
                int n3 = ((Message)object).arg2;
                Object object2 = (SomeArgs)((Message)object).obj;
                int n4 = ((SomeArgs)object2).argi1;
                int n5 = ((SomeArgs)object2).argi2;
                n = ((SomeArgs)object2).argi3;
                object = (IAccessibilityInteractionConnectionCallback)((SomeArgs)object2).arg1;
                Bundle bundle = (Bundle)((SomeArgs)object2).arg2;
                ((SomeArgs)object2).recycle();
                boolean bl2 = false;
                try {
                    if (this.mViewRootImpl.mView == null || this.mViewRootImpl.mAttachInfo == null || this.mViewRootImpl.mStopped || this.mViewRootImpl.mPausedForTransition) break block10;
                    this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = n2;
                    object2 = this.findViewByAccessibilityId(n3);
                    bl = bl2;
                    if (object2 == null) break block11;
                    bl = bl2;
                    if (!this.isShown((View)object2)) break block11;
                    if (n5 == 16908658) {
                        bl = this.handleClickableSpanActionUiThread((View)object2, n4, bundle);
                        break block11;
                    }
                    AccessibilityNodeProvider accessibilityNodeProvider = ((View)object2).getAccessibilityNodeProvider();
                    if (accessibilityNodeProvider != null) {
                        bl = accessibilityNodeProvider.performAction(n4, n5, bundle);
                        break block11;
                    }
                    bl = bl2;
                    if (n4 != -1) break block11;
                    bl = ((View)object2).performAccessibilityAction(n5, bundle);
                }
                catch (Throwable throwable) {
                    try {
                        this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 0;
                        object.setPerformAccessibilityActionResult(false, n);
                        throw throwable;
                    }
                    catch (RemoteException remoteException) {
                        // empty catch block
                    }
                    throw throwable;
                }
            }
            try {
                this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 0;
                object.setPerformAccessibilityActionResult(bl, n);
                return;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
        try {
            this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 0;
            object.setPerformAccessibilityActionResult(false, n);
            return;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
    }

    private void prepareForExtraDataRequestUiThread(Message object) {
        object = (SomeArgs)((Message)object).obj;
        int n = ((SomeArgs)object).argi1;
        ((AccessibilityRequestPreparer)((SomeArgs)object).arg1).onPrepareExtraData(n, (String)((SomeArgs)object).arg2, (Bundle)((SomeArgs)object).arg3, (Message)((SomeArgs)object).arg4);
    }

    private void queueMessageToHandleOncePrepared(Message object, int n, long l) {
        if (this.mMessagesWaitingForRequestPreparer == null) {
            this.mMessagesWaitingForRequestPreparer = new ArrayList<MessageHolder>(1);
        }
        object = new MessageHolder((Message)object, n, l);
        this.mMessagesWaitingForRequestPreparer.add((MessageHolder)object);
    }

    private void recycleMagnificationSpecAndRegionIfNeeded(MagnificationSpec magnificationSpec, Region region) {
        if (Process.myPid() != Binder.getCallingPid()) {
            if (magnificationSpec != null) {
                magnificationSpec.recycle();
            }
        } else if (region != null) {
            region.recycle();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void requestPreparerDoneUiThread(Message message) {
        Object object = this.mLock;
        synchronized (object) {
            if (message.arg1 != this.mActiveRequestPreparerId) {
                Slog.e(LOG_TAG, "Surprising AccessibilityRequestPreparer callback (likely late)");
                return;
            }
            --this.mNumActiveRequestPreparers;
            if (this.mNumActiveRequestPreparers <= 0) {
                this.mHandler.removeMessages(9);
                this.scheduleAllMessagesWaitingForRequestPreparerLocked();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void requestPreparerTimeoutUiThread() {
        Object object = this.mLock;
        synchronized (object) {
            Slog.e(LOG_TAG, "AccessibilityRequestPreparer timed out");
            this.scheduleAllMessagesWaitingForRequestPreparerLocked();
            return;
        }
    }

    @GuardedBy(value={"mLock"})
    private void scheduleAllMessagesWaitingForRequestPreparerLocked() {
        int n = this.mMessagesWaitingForRequestPreparer.size();
        int n2 = 0;
        do {
            boolean bl = false;
            if (n2 >= n) break;
            MessageHolder messageHolder = this.mMessagesWaitingForRequestPreparer.get(n2);
            Message message = messageHolder.mMessage;
            int n3 = messageHolder.mInterrogatingPid;
            long l = messageHolder.mInterrogatingTid;
            if (n2 == 0) {
                bl = true;
            }
            this.scheduleMessage(message, n3, l, bl);
            ++n2;
        } while (true);
        this.mMessagesWaitingForRequestPreparer.clear();
        this.mNumActiveRequestPreparers = 0;
        this.mActiveRequestPreparerId = -1;
    }

    private void scheduleMessage(Message message, int n, long l, boolean bl) {
        if (bl || !this.holdOffMessageIfNeeded(message, n, l)) {
            if (n == this.mMyProcessId && l == this.mMyLooperThreadId && this.mHandler.hasAccessibilityCallback(message)) {
                AccessibilityInteractionClient.getInstanceForThread(l).setSameThreadMessage(message);
            } else if (!this.mHandler.hasAccessibilityCallback(message) && Thread.currentThread().getId() == this.mMyLooperThreadId) {
                this.mHandler.handleMessage(message);
            } else {
                this.mHandler.sendMessage(message);
            }
        }
    }

    private boolean shouldApplyAppScaleAndMagnificationSpec(float f, MagnificationSpec magnificationSpec) {
        boolean bl = f != 1.0f || magnificationSpec != null && !magnificationSpec.isNop();
        return bl;
    }

    private boolean shouldBypassAdjustIsVisible() {
        return this.mViewRootImpl.mOrigWindowType == 2011;
    }

    private void updateInfoForViewportAndReturnFindNodeResult(AccessibilityNodeInfo accessibilityNodeInfo, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n, MagnificationSpec magnificationSpec, Region region) {
        try {
            this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 0;
            this.applyAppScaleAndMagnificationSpecIfNeeded(accessibilityNodeInfo, magnificationSpec);
            this.adjustIsVisibleToUserIfNeeded(accessibilityNodeInfo, region);
            iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfoResult(accessibilityNodeInfo, n);
        }
        catch (Throwable throwable) {
            this.recycleMagnificationSpecAndRegionIfNeeded(magnificationSpec, region);
            throw throwable;
        }
        catch (RemoteException remoteException) {
            // empty catch block
        }
        this.recycleMagnificationSpecAndRegionIfNeeded(magnificationSpec, region);
    }

    private void updateInfosForViewportAndReturnFindNodeResult(List<AccessibilityNodeInfo> list, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n, MagnificationSpec magnificationSpec, Region region) {
        block4 : {
            this.mViewRootImpl.mAttachInfo.mAccessibilityFetchFlags = 0;
            this.applyAppScaleAndMagnificationSpecIfNeeded(list, magnificationSpec);
            this.adjustIsVisibleToUserIfNeeded(list, region);
            iAccessibilityInteractionConnectionCallback.setFindAccessibilityNodeInfosResult(list, n);
            if (list == null) break block4;
            try {
                list.clear();
            }
            catch (Throwable throwable) {
                this.recycleMagnificationSpecAndRegionIfNeeded(magnificationSpec, region);
                throw throwable;
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        this.recycleMagnificationSpecAndRegionIfNeeded(magnificationSpec, region);
    }

    public void clearAccessibilityFocusClientThread() {
        Message message = this.mHandler.obtainMessage();
        message.what = 101;
        this.scheduleMessage(message, 0, 0L, false);
    }

    public void findAccessibilityNodeInfoByAccessibilityIdClientThread(long l, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec, Bundle bundle) {
        Message message = this.mHandler.obtainMessage();
        message.what = 2;
        message.arg1 = n2;
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.argi1 = AccessibilityNodeInfo.getAccessibilityViewId(l);
        someArgs.argi2 = AccessibilityNodeInfo.getVirtualDescendantId(l);
        someArgs.argi3 = n;
        someArgs.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgs.arg2 = magnificationSpec;
        someArgs.arg3 = region;
        someArgs.arg4 = bundle;
        message.obj = someArgs;
        this.scheduleMessage(message, n3, l2, false);
    }

    public void findAccessibilityNodeInfosByTextClientThread(long l, String string2, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec) {
        Message message = this.mHandler.obtainMessage();
        message.what = 4;
        message.arg1 = n2;
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = string2;
        someArgs.arg2 = iAccessibilityInteractionConnectionCallback;
        someArgs.arg3 = magnificationSpec;
        someArgs.argi1 = AccessibilityNodeInfo.getAccessibilityViewId(l);
        someArgs.argi2 = AccessibilityNodeInfo.getVirtualDescendantId(l);
        someArgs.argi3 = n;
        someArgs.arg4 = region;
        message.obj = someArgs;
        this.scheduleMessage(message, n3, l2, false);
    }

    public void findAccessibilityNodeInfosByViewIdClientThread(long l, String string2, Region region, int n, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n2, int n3, long l2, MagnificationSpec magnificationSpec) {
        Message message = this.mHandler.obtainMessage();
        message.what = 3;
        message.arg1 = n2;
        message.arg2 = AccessibilityNodeInfo.getAccessibilityViewId(l);
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.argi1 = n;
        someArgs.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgs.arg2 = magnificationSpec;
        someArgs.arg3 = string2;
        someArgs.arg4 = region;
        message.obj = someArgs;
        this.scheduleMessage(message, n3, l2, false);
    }

    public void findFocusClientThread(long l, int n, Region region, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2, MagnificationSpec magnificationSpec) {
        Message message = this.mHandler.obtainMessage();
        message.what = 5;
        message.arg1 = n3;
        message.arg2 = n;
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.argi1 = n2;
        someArgs.argi2 = AccessibilityNodeInfo.getAccessibilityViewId(l);
        someArgs.argi3 = AccessibilityNodeInfo.getVirtualDescendantId(l);
        someArgs.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgs.arg2 = magnificationSpec;
        someArgs.arg3 = region;
        message.obj = someArgs;
        this.scheduleMessage(message, n4, l2, false);
    }

    public void focusSearchClientThread(long l, int n, Region region, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2, MagnificationSpec magnificationSpec) {
        Message message = this.mHandler.obtainMessage();
        message.what = 6;
        message.arg1 = n3;
        message.arg2 = AccessibilityNodeInfo.getAccessibilityViewId(l);
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.argi2 = n;
        someArgs.argi3 = n2;
        someArgs.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgs.arg2 = magnificationSpec;
        someArgs.arg3 = region;
        message.obj = someArgs;
        this.scheduleMessage(message, n4, l2, false);
    }

    public void notifyOutsideTouchClientThread() {
        Message message = this.mHandler.obtainMessage();
        message.what = 102;
        this.scheduleMessage(message, 0, 0L, false);
    }

    public void performAccessibilityActionClientThread(long l, int n, Bundle bundle, int n2, IAccessibilityInteractionConnectionCallback iAccessibilityInteractionConnectionCallback, int n3, int n4, long l2) {
        Message message = this.mHandler.obtainMessage();
        message.what = 1;
        message.arg1 = n3;
        message.arg2 = AccessibilityNodeInfo.getAccessibilityViewId(l);
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.argi1 = AccessibilityNodeInfo.getVirtualDescendantId(l);
        someArgs.argi2 = n;
        someArgs.argi3 = n2;
        someArgs.arg1 = iAccessibilityInteractionConnectionCallback;
        someArgs.arg2 = bundle;
        message.obj = someArgs;
        this.scheduleMessage(message, n4, l2, false);
    }

    private class AccessibilityNodePrefetcher {
        private static final int MAX_ACCESSIBILITY_NODE_INFO_BATCH_SIZE = 50;
        private final ArrayList<View> mTempViewList = new ArrayList();

        private AccessibilityNodePrefetcher() {
        }

        private void enforceNodeTreeConsistent(List<AccessibilityNodeInfo> object) {
            Object object2;
            int n;
            LongSparseArray<AccessibilityNodeInfo> longSparseArray = new LongSparseArray<AccessibilityNodeInfo>();
            int n2 = object.size();
            for (n = 0; n < n2; ++n) {
                object2 = object.get(n);
                longSparseArray.put(((AccessibilityNodeInfo)object2).getSourceNodeId(), (AccessibilityNodeInfo)object2);
            }
            object = object2 = (AccessibilityNodeInfo)longSparseArray.valueAt(0);
            while (object != null) {
                object2 = object;
                object = (AccessibilityNodeInfo)longSparseArray.get(((AccessibilityNodeInfo)object).getParentNodeId());
            }
            Object object3 = null;
            object = null;
            HashSet<Object> hashSet = new HashSet<Object>();
            LinkedList<Object> linkedList = new LinkedList<Object>();
            linkedList.add(object2);
            object2 = object;
            while (!linkedList.isEmpty()) {
                object = (AccessibilityNodeInfo)linkedList.poll();
                if (hashSet.add(object)) {
                    Object object4 = object3;
                    if (((AccessibilityNodeInfo)object).isAccessibilityFocused()) {
                        if (object3 == null) {
                            object4 = object;
                        } else {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Duplicate accessibility focus:");
                            ((StringBuilder)object2).append(object);
                            ((StringBuilder)object2).append(" in window:");
                            ((StringBuilder)object2).append(AccessibilityInteractionController.access$200((AccessibilityInteractionController)AccessibilityInteractionController.this).mAttachInfo.mAccessibilityWindowId);
                            throw new IllegalStateException(((StringBuilder)object2).toString());
                        }
                    }
                    Object object5 = object2;
                    if (((AccessibilityNodeInfo)object).isFocused()) {
                        if (object2 == null) {
                            object5 = object;
                        } else {
                            object2 = new StringBuilder();
                            ((StringBuilder)object2).append("Duplicate input focus: ");
                            ((StringBuilder)object2).append(object);
                            ((StringBuilder)object2).append(" in window:");
                            ((StringBuilder)object2).append(AccessibilityInteractionController.access$200((AccessibilityInteractionController)AccessibilityInteractionController.this).mAttachInfo.mAccessibilityWindowId);
                            throw new IllegalStateException(((StringBuilder)object2).toString());
                        }
                    }
                    n2 = ((AccessibilityNodeInfo)object).getChildCount();
                    for (n = 0; n < n2; ++n) {
                        object2 = (AccessibilityNodeInfo)longSparseArray.get(((AccessibilityNodeInfo)object).getChildId(n));
                        if (object2 == null) continue;
                        linkedList.add(object2);
                    }
                    object3 = object4;
                    object2 = object5;
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Duplicate node: ");
                ((StringBuilder)object2).append(object);
                ((StringBuilder)object2).append(" in window:");
                ((StringBuilder)object2).append(AccessibilityInteractionController.access$200((AccessibilityInteractionController)AccessibilityInteractionController.this).mAttachInfo.mAccessibilityWindowId);
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }
            for (n = longSparseArray.size() - 1; n >= 0; --n) {
                object = (AccessibilityNodeInfo)longSparseArray.valueAt(n);
                if (hashSet.contains(object)) {
                    continue;
                }
                object2 = new StringBuilder();
                ((StringBuilder)object2).append("Disconnected node: ");
                ((StringBuilder)object2).append(object);
                throw new IllegalStateException(((StringBuilder)object2).toString());
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void prefetchDescendantsOfRealNode(View view, List<AccessibilityNodeInfo> list) {
            block9 : {
                if (!(view instanceof ViewGroup)) {
                    return;
                }
                HashMap<View, Object> hashMap = new HashMap<View, Object>();
                Object object = this.mTempViewList;
                ((ArrayList)object).clear();
                try {
                    view.addChildrenForAccessibility((ArrayList<View>)object);
                    int n = ((ArrayList)object).size();
                    for (int i = 0; i < n; ++i) {
                        int n2 = list.size();
                        if (n2 >= 50) {
                            ((ArrayList)object).clear();
                            return;
                        }
                        view = ((ArrayList)object).get(i);
                        if (!AccessibilityInteractionController.this.isShown(view)) continue;
                        Object object2 = view.getAccessibilityNodeProvider();
                        if (object2 == null) {
                            object2 = view.createAccessibilityNodeInfo();
                            if (object2 == null) continue;
                            list.add((AccessibilityNodeInfo)object2);
                            hashMap.put(view, null);
                            continue;
                        }
                        if ((object2 = ((AccessibilityNodeProvider)object2).createAccessibilityNodeInfo(-1)) == null) continue;
                        list.add((AccessibilityNodeInfo)object2);
                        hashMap.put(view, object2);
                    }
                    ((ArrayList)object).clear();
                    if (list.size() >= 50) break block9;
                    object = hashMap.entrySet().iterator();
                }
                catch (Throwable throwable) {
                    ((ArrayList)object).clear();
                    throw throwable;
                }
                while (object.hasNext()) {
                    Map.Entry entry = (Map.Entry)object.next();
                    view = (View)entry.getKey();
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo)entry.getValue();
                    if (accessibilityNodeInfo == null) {
                        this.prefetchDescendantsOfRealNode(view, list);
                        continue;
                    }
                    this.prefetchDescendantsOfVirtualNode(accessibilityNodeInfo, view.getAccessibilityNodeProvider(), list);
                }
            }
        }

        private void prefetchDescendantsOfVirtualNode(AccessibilityNodeInfo accessibilityNodeInfo, AccessibilityNodeProvider accessibilityNodeProvider, List<AccessibilityNodeInfo> list) {
            int n;
            int n2 = list.size();
            int n3 = accessibilityNodeInfo.getChildCount();
            for (n = 0; n < n3; ++n) {
                if (list.size() >= 50) {
                    return;
                }
                long l = accessibilityNodeInfo.getChildId(n);
                AccessibilityNodeInfo accessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(l));
                if (accessibilityNodeInfo2 == null) continue;
                list.add(accessibilityNodeInfo2);
            }
            if (list.size() < 50) {
                n3 = list.size();
                for (n = 0; n < n3 - n2; ++n) {
                    this.prefetchDescendantsOfVirtualNode(list.get(n2 + n), accessibilityNodeProvider, list);
                }
            }
        }

        private void prefetchPredecessorsOfRealNode(View object, List<AccessibilityNodeInfo> list) {
            object = ((View)object).getParentForAccessibility();
            while (object instanceof View && list.size() < 50) {
                AccessibilityNodeInfo accessibilityNodeInfo = ((View)object).createAccessibilityNodeInfo();
                if (accessibilityNodeInfo != null) {
                    list.add(accessibilityNodeInfo);
                }
                object = object.getParentForAccessibility();
            }
        }

        private void prefetchPredecessorsOfVirtualNode(AccessibilityNodeInfo accessibilityNodeInfo, View view, AccessibilityNodeProvider accessibilityNodeProvider, List<AccessibilityNodeInfo> list) {
            int n = list.size();
            long l = accessibilityNodeInfo.getParentNodeId();
            int n2 = AccessibilityNodeInfo.getAccessibilityViewId(l);
            while (n2 != Integer.MAX_VALUE) {
                if (list.size() >= 50) {
                    return;
                }
                int n3 = AccessibilityNodeInfo.getVirtualDescendantId(l);
                if (n3 == -1 && n2 != view.getAccessibilityViewId()) {
                    this.prefetchPredecessorsOfRealNode(view, list);
                    return;
                }
                accessibilityNodeInfo = accessibilityNodeProvider.createAccessibilityNodeInfo(n3);
                if (accessibilityNodeInfo == null) {
                    for (n2 = list.size() - 1; n2 >= n; --n2) {
                        list.remove(n2);
                    }
                    return;
                }
                list.add(accessibilityNodeInfo);
                l = accessibilityNodeInfo.getParentNodeId();
                n2 = AccessibilityNodeInfo.getAccessibilityViewId(l);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void prefetchSiblingsOfRealNode(View view, List<AccessibilityNodeInfo> list) {
            Object object = view.getParentForAccessibility();
            if (!(object instanceof ViewGroup)) return;
            object = (ViewGroup)object;
            ArrayList<View> arrayList = this.mTempViewList;
            arrayList.clear();
            try {
                ((ViewGroup)object).addChildrenForAccessibility(arrayList);
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    AccessibilityNodeProvider accessibilityNodeProvider;
                    int n2 = list.size();
                    if (n2 >= 50) {
                        arrayList.clear();
                        return;
                    }
                    object = arrayList.get(i);
                    if (((View)object).getAccessibilityViewId() == view.getAccessibilityViewId() || !AccessibilityInteractionController.this.isShown((View)object) || (object = (accessibilityNodeProvider = ((View)object).getAccessibilityNodeProvider()) == null ? ((View)object).createAccessibilityNodeInfo() : accessibilityNodeProvider.createAccessibilityNodeInfo(-1)) == null) continue;
                    list.add((AccessibilityNodeInfo)object);
                }
                arrayList.clear();
                return;
            }
            catch (Throwable throwable) {
                arrayList.clear();
                throw throwable;
            }
        }

        private void prefetchSiblingsOfVirtualNode(AccessibilityNodeInfo accessibilityNodeInfo, View object, AccessibilityNodeProvider accessibilityNodeProvider, List<AccessibilityNodeInfo> list) {
            long l = accessibilityNodeInfo.getParentNodeId();
            int n = AccessibilityNodeInfo.getAccessibilityViewId(l);
            int n2 = AccessibilityNodeInfo.getVirtualDescendantId(l);
            if (n2 == -1 && n != ((View)object).getAccessibilityViewId()) {
                this.prefetchSiblingsOfRealNode((View)object, list);
            } else {
                object = accessibilityNodeProvider.createAccessibilityNodeInfo(n2);
                if (object != null) {
                    n2 = ((AccessibilityNodeInfo)object).getChildCount();
                    for (n = 0; n < n2; ++n) {
                        AccessibilityNodeInfo accessibilityNodeInfo2;
                        if (list.size() >= 50) {
                            return;
                        }
                        l = ((AccessibilityNodeInfo)object).getChildId(n);
                        if (l == accessibilityNodeInfo.getSourceNodeId() || (accessibilityNodeInfo2 = accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(l))) == null) continue;
                        list.add(accessibilityNodeInfo2);
                    }
                }
            }
        }

        public void prefetchAccessibilityNodeInfos(View view, int n, int n2, List<AccessibilityNodeInfo> list, Bundle bundle) {
            Object object = view.getAccessibilityNodeProvider();
            String string2 = bundle == null ? null : bundle.getString("android.view.accessibility.AccessibilityNodeInfo.extra_data_requested");
            if (object == null) {
                object = view.createAccessibilityNodeInfo();
                if (object != null) {
                    if (string2 != null) {
                        view.addExtraDataToAccessibilityNodeInfo((AccessibilityNodeInfo)object, string2, bundle);
                    }
                    list.add((AccessibilityNodeInfo)object);
                    if ((n2 & 1) != 0) {
                        this.prefetchPredecessorsOfRealNode(view, list);
                    }
                    if ((n2 & 2) != 0) {
                        this.prefetchSiblingsOfRealNode(view, list);
                    }
                    if ((n2 & 4) != 0) {
                        this.prefetchDescendantsOfRealNode(view, list);
                    }
                }
            } else {
                AccessibilityNodeInfo accessibilityNodeInfo = ((AccessibilityNodeProvider)object).createAccessibilityNodeInfo(n);
                if (accessibilityNodeInfo != null) {
                    if (string2 != null) {
                        ((AccessibilityNodeProvider)object).addExtraDataToAccessibilityNodeInfo(n, accessibilityNodeInfo, string2, bundle);
                    }
                    list.add(accessibilityNodeInfo);
                    if ((n2 & 1) != 0) {
                        this.prefetchPredecessorsOfVirtualNode(accessibilityNodeInfo, view, (AccessibilityNodeProvider)object, list);
                    }
                    if ((n2 & 2) != 0) {
                        this.prefetchSiblingsOfVirtualNode(accessibilityNodeInfo, view, (AccessibilityNodeProvider)object, list);
                    }
                    if ((n2 & 4) != 0) {
                        this.prefetchDescendantsOfVirtualNode(accessibilityNodeInfo, (AccessibilityNodeProvider)object, list);
                    }
                }
            }
        }
    }

    private final class AddNodeInfosForViewId
    implements Predicate<View> {
        private List<AccessibilityNodeInfo> mInfos;
        private int mViewId = -1;

        private AddNodeInfosForViewId() {
        }

        public void init(int n, List<AccessibilityNodeInfo> list) {
            this.mViewId = n;
            this.mInfos = list;
        }

        public void reset() {
            this.mViewId = -1;
            this.mInfos = null;
        }

        @Override
        public boolean test(View view) {
            if (view.getId() == this.mViewId && AccessibilityInteractionController.this.isShown(view)) {
                this.mInfos.add(view.createAccessibilityNodeInfo());
            }
            return false;
        }
    }

    private static final class MessageHolder {
        final int mInterrogatingPid;
        final long mInterrogatingTid;
        final Message mMessage;

        MessageHolder(Message message, int n, long l) {
            this.mMessage = message;
            this.mInterrogatingPid = n;
            this.mInterrogatingTid = l;
        }
    }

    private class PrivateHandler
    extends Handler {
        private static final int FIRST_NO_ACCESSIBILITY_CALLBACK_MSG = 100;
        private static final int MSG_APP_PREPARATION_FINISHED = 8;
        private static final int MSG_APP_PREPARATION_TIMEOUT = 9;
        private static final int MSG_CLEAR_ACCESSIBILITY_FOCUS = 101;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFOS_BY_VIEW_ID = 3;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_ACCESSIBILITY_ID = 2;
        private static final int MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_TEXT = 4;
        private static final int MSG_FIND_FOCUS = 5;
        private static final int MSG_FOCUS_SEARCH = 6;
        private static final int MSG_NOTIFY_OUTSIDE_TOUCH = 102;
        private static final int MSG_PERFORM_ACCESSIBILITY_ACTION = 1;
        private static final int MSG_PREPARE_FOR_EXTRA_DATA_REQUEST = 7;

        public PrivateHandler(Looper looper) {
            super(looper);
        }

        @Override
        public String getMessageName(Message object) {
            int n = ((Message)object).what;
            if (n != 101) {
                if (n != 102) {
                    switch (n) {
                        default: {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unknown message type: ");
                            ((StringBuilder)object).append(n);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        case 9: {
                            return "MSG_APP_PREPARATION_TIMEOUT";
                        }
                        case 8: {
                            return "MSG_APP_PREPARATION_FINISHED";
                        }
                        case 7: {
                            return "MSG_PREPARE_FOR_EXTRA_DATA_REQUEST";
                        }
                        case 6: {
                            return "MSG_FOCUS_SEARCH";
                        }
                        case 5: {
                            return "MSG_FIND_FOCUS";
                        }
                        case 4: {
                            return "MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_TEXT";
                        }
                        case 3: {
                            return "MSG_FIND_ACCESSIBILITY_NODE_INFOS_BY_VIEW_ID";
                        }
                        case 2: {
                            return "MSG_FIND_ACCESSIBILITY_NODE_INFO_BY_ACCESSIBILITY_ID";
                        }
                        case 1: 
                    }
                    return "MSG_PERFORM_ACCESSIBILITY_ACTION";
                }
                return "MSG_NOTIFY_OUTSIDE_TOUCH";
            }
            return "MSG_CLEAR_ACCESSIBILITY_FOCUS";
        }

        @Override
        public void handleMessage(Message object) {
            int n = ((Message)object).what;
            if (n != 101) {
                if (n != 102) {
                    switch (n) {
                        default: {
                            object = new StringBuilder();
                            ((StringBuilder)object).append("Unknown message type: ");
                            ((StringBuilder)object).append(n);
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        case 9: {
                            AccessibilityInteractionController.this.requestPreparerTimeoutUiThread();
                            break;
                        }
                        case 8: {
                            AccessibilityInteractionController.this.requestPreparerDoneUiThread((Message)object);
                            break;
                        }
                        case 7: {
                            AccessibilityInteractionController.this.prepareForExtraDataRequestUiThread((Message)object);
                            break;
                        }
                        case 6: {
                            AccessibilityInteractionController.this.focusSearchUiThread((Message)object);
                            break;
                        }
                        case 5: {
                            AccessibilityInteractionController.this.findFocusUiThread((Message)object);
                            break;
                        }
                        case 4: {
                            AccessibilityInteractionController.this.findAccessibilityNodeInfosByTextUiThread((Message)object);
                            break;
                        }
                        case 3: {
                            AccessibilityInteractionController.this.findAccessibilityNodeInfosByViewIdUiThread((Message)object);
                            break;
                        }
                        case 2: {
                            AccessibilityInteractionController.this.findAccessibilityNodeInfoByAccessibilityIdUiThread((Message)object);
                            break;
                        }
                        case 1: {
                            AccessibilityInteractionController.this.performAccessibilityActionUiThread((Message)object);
                            break;
                        }
                    }
                } else {
                    AccessibilityInteractionController.this.notifyOutsideTouchUiThread();
                }
            } else {
                AccessibilityInteractionController.this.clearAccessibilityFocusUiThread();
            }
        }

        boolean hasAccessibilityCallback(Message message) {
            boolean bl = message.what < 100;
            return bl;
        }
    }

}

