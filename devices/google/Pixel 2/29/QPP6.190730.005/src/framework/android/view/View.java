/*
 * Decompiled with CFR 0.145.
 */
package android.view;

import android.animation.AnimatorInflater;
import android.animation.StateListAnimator;
import android.annotation.UnsupportedAppUsage;
import android.content.AutofillOptions;
import android.content.ClipData;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Insets;
import android.graphics.Interpolator;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Outline;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RecordingCanvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RenderNode;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.display.DisplayManagerGlobal;
import android.os.BaseBundle;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.os.SystemClock;
import android.os.Trace;
import android.sysprop.DisplayProperties;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.FloatProperty;
import android.util.Log;
import android.util.LongArray;
import android.util.LongSparseLongArray;
import android.util.Pools;
import android.util.Property;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.StateSet;
import android.util.StatsLog;
import android.util.SuperNotCalledException;
import android.util.TypedValue;
import android.view.AbsSavedState;
import android.view.AccessibilityIterators;
import android.view.ActionMode;
import android.view.Choreographer;
import android.view.ContextMenu;
import android.view.Display;
import android.view.DisplayCutout;
import android.view.DragEvent;
import android.view.FocusFinder;
import android.view.FrameMetricsObserver;
import android.view.GhostView;
import android.view.Gravity;
import android.view.HandlerActionQueue;
import android.view.IWindow;
import android.view.IWindowId;
import android.view.IWindowSession;
import android.view.InputEvent;
import android.view.InputEventConsistencyVerifier;
import android.view.InsetsController;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.RemotableViewMethod;
import android.view.RoundScrollbarRenderer;
import android.view.Surface;
import android.view.SurfaceControl;
import android.view.SurfaceSession;
import android.view.ThreadedRenderer;
import android.view.TouchDelegate;
import android.view.ViewAnimationHostBridge;
import android.view.ViewConfiguration;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.view.ViewHierarchyEncoder;
import android.view.ViewOutlineProvider;
import android.view.ViewOverlay;
import android.view.ViewParent;
import android.view.ViewPropertyAnimator;
import android.view.ViewRootImpl;
import android.view.ViewStructure;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowId;
import android.view.WindowInsets;
import android.view.WindowInsetsAnimationListener;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.view._$$Lambda$QI1s392qW8l6mC24bcy9050SkuY;
import android.view._$$Lambda$View$7kZ4TXHKswReUMQB8098MEBcx_U;
import android.view._$$Lambda$View$llq76MkPXP4bNcb9oJt_msw0fnQ;
import android.view._$$Lambda$WlJa6OPA72p3gYtA3nVKC7Z1tGY;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityEventSource;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeIdManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Transformation;
import android.view.autofill.AutofillId;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.contentcapture.ContentCaptureManager;
import android.view.contentcapture.ContentCaptureSession;
import android.view.contentcapture.MainContentCaptureSession;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ScrollBarDrawable;
import com.android.internal.R;
import com.android.internal.view.TooltipPopup;
import com.android.internal.view.menu.MenuBuilder;
import com.android.internal.widget.ScrollBarUtils;
import com.google.android.collect.Lists;
import com.google.android.collect.Maps;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.ref.WeakReference;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

public class View
implements Drawable.Callback,
KeyEvent.Callback,
AccessibilityEventSource {
    public static final int ACCESSIBILITY_CURSOR_POSITION_UNDEFINED = -1;
    public static final int ACCESSIBILITY_LIVE_REGION_ASSERTIVE = 2;
    static final int ACCESSIBILITY_LIVE_REGION_DEFAULT = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_NONE = 0;
    public static final int ACCESSIBILITY_LIVE_REGION_POLITE = 1;
    static final int ALL_RTL_PROPERTIES_RESOLVED = 1610678816;
    public static final Property<View, Float> ALPHA;
    public static final int AUTOFILL_FLAG_INCLUDE_NOT_IMPORTANT_VIEWS = 1;
    private static final int[] AUTOFILL_HIGHLIGHT_ATTR;
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DATE = "creditCardExpirationDate";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_DAY = "creditCardExpirationDay";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_MONTH = "creditCardExpirationMonth";
    public static final String AUTOFILL_HINT_CREDIT_CARD_EXPIRATION_YEAR = "creditCardExpirationYear";
    public static final String AUTOFILL_HINT_CREDIT_CARD_NUMBER = "creditCardNumber";
    public static final String AUTOFILL_HINT_CREDIT_CARD_SECURITY_CODE = "creditCardSecurityCode";
    public static final String AUTOFILL_HINT_EMAIL_ADDRESS = "emailAddress";
    public static final String AUTOFILL_HINT_NAME = "name";
    public static final String AUTOFILL_HINT_PASSWORD = "password";
    public static final String AUTOFILL_HINT_PHONE = "phone";
    public static final String AUTOFILL_HINT_POSTAL_ADDRESS = "postalAddress";
    public static final String AUTOFILL_HINT_POSTAL_CODE = "postalCode";
    public static final String AUTOFILL_HINT_USERNAME = "username";
    private static final String AUTOFILL_LOG_TAG = "View.Autofill";
    public static final int AUTOFILL_TYPE_DATE = 4;
    public static final int AUTOFILL_TYPE_LIST = 3;
    public static final int AUTOFILL_TYPE_NONE = 0;
    public static final int AUTOFILL_TYPE_TEXT = 1;
    public static final int AUTOFILL_TYPE_TOGGLE = 2;
    static final int CLICKABLE = 16384;
    private static final String CONTENT_CAPTURE_LOG_TAG = "View.ContentCapture";
    static final int CONTEXT_CLICKABLE = 8388608;
    @UnsupportedAppUsage
    private static final boolean DBG = false;
    private static final boolean DEBUG_CONTENT_CAPTURE = false;
    static final int DEBUG_CORNERS_COLOR;
    static final int DEBUG_CORNERS_SIZE_DIP = 8;
    public static boolean DEBUG_DRAW = false;
    static final int DISABLED = 32;
    public static final int DRAG_FLAG_GLOBAL = 256;
    public static final int DRAG_FLAG_GLOBAL_PERSISTABLE_URI_PERMISSION = 64;
    public static final int DRAG_FLAG_GLOBAL_PREFIX_URI_PERMISSION = 128;
    public static final int DRAG_FLAG_GLOBAL_URI_READ = 1;
    public static final int DRAG_FLAG_GLOBAL_URI_WRITE = 2;
    public static final int DRAG_FLAG_OPAQUE = 512;
    static final int DRAG_MASK = 3;
    static final int DRAWING_CACHE_ENABLED = 32768;
    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_AUTO = 0;
    private static final int[] DRAWING_CACHE_QUALITY_FLAGS;
    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_HIGH = 1048576;
    @Deprecated
    public static final int DRAWING_CACHE_QUALITY_LOW = 524288;
    static final int DRAWING_CACHE_QUALITY_MASK = 1572864;
    static final int DRAW_MASK = 128;
    static final int DUPLICATE_PARENT_STATE = 4194304;
    protected static final int[] EMPTY_STATE_SET;
    static final int ENABLED = 0;
    protected static final int[] ENABLED_FOCUSED_SELECTED_STATE_SET;
    protected static final int[] ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] ENABLED_FOCUSED_STATE_SET;
    protected static final int[] ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET;
    static final int ENABLED_MASK = 32;
    protected static final int[] ENABLED_SELECTED_STATE_SET;
    protected static final int[] ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] ENABLED_STATE_SET;
    protected static final int[] ENABLED_WINDOW_FOCUSED_STATE_SET;
    static final int FADING_EDGE_HORIZONTAL = 4096;
    static final int FADING_EDGE_MASK = 12288;
    static final int FADING_EDGE_NONE = 0;
    static final int FADING_EDGE_VERTICAL = 8192;
    static final int FILTER_TOUCHES_WHEN_OBSCURED = 1024;
    public static final int FIND_VIEWS_WITH_ACCESSIBILITY_NODE_PROVIDERS = 4;
    public static final int FIND_VIEWS_WITH_CONTENT_DESCRIPTION = 2;
    public static final int FIND_VIEWS_WITH_TEXT = 1;
    private static final int FITS_SYSTEM_WINDOWS = 2;
    public static final int FOCUSABLE = 1;
    public static final int FOCUSABLES_ALL = 0;
    public static final int FOCUSABLES_TOUCH_MODE = 1;
    public static final int FOCUSABLE_AUTO = 16;
    static final int FOCUSABLE_IN_TOUCH_MODE = 262144;
    private static final int FOCUSABLE_MASK = 17;
    protected static final int[] FOCUSED_SELECTED_STATE_SET;
    protected static final int[] FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] FOCUSED_STATE_SET;
    protected static final int[] FOCUSED_WINDOW_FOCUSED_STATE_SET;
    public static final int FOCUS_BACKWARD = 1;
    public static final int FOCUS_DOWN = 130;
    public static final int FOCUS_FORWARD = 2;
    public static final int FOCUS_LEFT = 17;
    public static final int FOCUS_RIGHT = 66;
    public static final int FOCUS_UP = 33;
    public static final int GONE = 8;
    public static final int HAPTIC_FEEDBACK_ENABLED = 268435456;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_AUTO = 0;
    static final int IMPORTANT_FOR_ACCESSIBILITY_DEFAULT = 0;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO = 2;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_NO_HIDE_DESCENDANTS = 4;
    public static final int IMPORTANT_FOR_ACCESSIBILITY_YES = 1;
    public static final int IMPORTANT_FOR_AUTOFILL_AUTO = 0;
    public static final int IMPORTANT_FOR_AUTOFILL_NO = 2;
    public static final int IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS = 8;
    public static final int IMPORTANT_FOR_AUTOFILL_YES = 1;
    public static final int IMPORTANT_FOR_AUTOFILL_YES_EXCLUDE_DESCENDANTS = 4;
    public static final int INVISIBLE = 4;
    public static final int KEEP_SCREEN_ON = 67108864;
    public static final int LAST_APP_AUTOFILL_ID = 1073741823;
    public static final int LAYER_TYPE_HARDWARE = 2;
    public static final int LAYER_TYPE_NONE = 0;
    public static final int LAYER_TYPE_SOFTWARE = 1;
    private static final int LAYOUT_DIRECTION_DEFAULT = 2;
    private static final int[] LAYOUT_DIRECTION_FLAGS;
    public static final int LAYOUT_DIRECTION_INHERIT = 2;
    public static final int LAYOUT_DIRECTION_LOCALE = 3;
    public static final int LAYOUT_DIRECTION_LTR = 0;
    static final int LAYOUT_DIRECTION_RESOLVED_DEFAULT = 0;
    public static final int LAYOUT_DIRECTION_RTL = 1;
    public static final int LAYOUT_DIRECTION_UNDEFINED = -1;
    static final int LONG_CLICKABLE = 2097152;
    public static final int MEASURED_HEIGHT_STATE_SHIFT = 16;
    public static final int MEASURED_SIZE_MASK = 16777215;
    public static final int MEASURED_STATE_MASK = -16777216;
    public static final int MEASURED_STATE_TOO_SMALL = 16777216;
    @UnsupportedAppUsage
    public static final int NAVIGATION_BAR_TRANSIENT = 134217728;
    public static final int NAVIGATION_BAR_TRANSLUCENT = Integer.MIN_VALUE;
    public static final int NAVIGATION_BAR_TRANSPARENT = 32768;
    public static final int NAVIGATION_BAR_UNHIDE = 536870912;
    public static final int NOT_FOCUSABLE = 0;
    public static final int NO_ID = -1;
    static final int OPTIONAL_FITS_SYSTEM_WINDOWS = 2048;
    public static final int OVER_SCROLL_ALWAYS = 0;
    public static final int OVER_SCROLL_IF_CONTENT_SCROLLS = 1;
    public static final int OVER_SCROLL_NEVER = 2;
    static final int PARENT_SAVE_DISABLED = 536870912;
    static final int PARENT_SAVE_DISABLED_MASK = 536870912;
    static final int PFLAG2_ACCESSIBILITY_FOCUSED = 67108864;
    static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_MASK = 25165824;
    static final int PFLAG2_ACCESSIBILITY_LIVE_REGION_SHIFT = 23;
    static final int PFLAG2_DRAG_CAN_ACCEPT = 1;
    static final int PFLAG2_DRAG_HOVERED = 2;
    static final int PFLAG2_DRAWABLE_RESOLVED = 1073741824;
    static final int PFLAG2_HAS_TRANSIENT_STATE = Integer.MIN_VALUE;
    static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_MASK = 7340032;
    static final int PFLAG2_IMPORTANT_FOR_ACCESSIBILITY_SHIFT = 20;
    static final int PFLAG2_LAYOUT_DIRECTION_MASK = 12;
    static final int PFLAG2_LAYOUT_DIRECTION_MASK_SHIFT = 2;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED = 32;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_MASK = 48;
    static final int PFLAG2_LAYOUT_DIRECTION_RESOLVED_RTL = 16;
    static final int PFLAG2_PADDING_RESOLVED = 536870912;
    static final int PFLAG2_SUBTREE_ACCESSIBILITY_STATE_CHANGED = 134217728;
    private static final int[] PFLAG2_TEXT_ALIGNMENT_FLAGS;
    static final int PFLAG2_TEXT_ALIGNMENT_MASK = 57344;
    static final int PFLAG2_TEXT_ALIGNMENT_MASK_SHIFT = 13;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED = 65536;
    private static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_DEFAULT = 131072;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK = 917504;
    static final int PFLAG2_TEXT_ALIGNMENT_RESOLVED_MASK_SHIFT = 17;
    private static final int[] PFLAG2_TEXT_DIRECTION_FLAGS;
    static final int PFLAG2_TEXT_DIRECTION_MASK = 448;
    static final int PFLAG2_TEXT_DIRECTION_MASK_SHIFT = 6;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED = 512;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_DEFAULT = 1024;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK = 7168;
    static final int PFLAG2_TEXT_DIRECTION_RESOLVED_MASK_SHIFT = 10;
    static final int PFLAG2_VIEW_QUICK_REJECTED = 268435456;
    private static final int PFLAG3_ACCESSIBILITY_HEADING = Integer.MIN_VALUE;
    private static final int PFLAG3_AGGREGATED_VISIBLE = 536870912;
    static final int PFLAG3_APPLYING_INSETS = 32;
    static final int PFLAG3_ASSIST_BLOCKED = 16384;
    private static final int PFLAG3_AUTOFILLID_EXPLICITLY_SET = 1073741824;
    static final int PFLAG3_CALLED_SUPER = 16;
    private static final int PFLAG3_CLUSTER = 32768;
    private static final int PFLAG3_FINGER_DOWN = 131072;
    static final int PFLAG3_FITTING_SYSTEM_WINDOWS = 64;
    private static final int PFLAG3_FOCUSED_BY_DEFAULT = 262144;
    private static final int PFLAG3_HAS_OVERLAPPING_RENDERING_FORCED = 16777216;
    static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_MASK = 7864320;
    static final int PFLAG3_IMPORTANT_FOR_AUTOFILL_SHIFT = 19;
    private static final int PFLAG3_IS_AUTOFILLED = 65536;
    static final int PFLAG3_IS_LAID_OUT = 4;
    static final int PFLAG3_MEASURE_NEEDED_BEFORE_LAYOUT = 8;
    static final int PFLAG3_NESTED_SCROLLING_ENABLED = 128;
    static final int PFLAG3_NOTIFY_AUTOFILL_ENTER_ON_LAYOUT = 134217728;
    private static final int PFLAG3_NO_REVEAL_ON_FOCUS = 67108864;
    private static final int PFLAG3_OVERLAPPING_RENDERING_FORCED_VALUE = 8388608;
    private static final int PFLAG3_SCREEN_READER_FOCUSABLE = 268435456;
    static final int PFLAG3_SCROLL_INDICATOR_BOTTOM = 512;
    static final int PFLAG3_SCROLL_INDICATOR_END = 8192;
    static final int PFLAG3_SCROLL_INDICATOR_LEFT = 1024;
    static final int PFLAG3_SCROLL_INDICATOR_RIGHT = 2048;
    static final int PFLAG3_SCROLL_INDICATOR_START = 4096;
    static final int PFLAG3_SCROLL_INDICATOR_TOP = 256;
    static final int PFLAG3_TEMPORARY_DETACH = 33554432;
    static final int PFLAG3_VIEW_IS_ANIMATING_ALPHA = 2;
    static final int PFLAG3_VIEW_IS_ANIMATING_TRANSFORM = 1;
    static final int PFLAG_ACTIVATED = 1073741824;
    static final int PFLAG_ALPHA_SET = 262144;
    static final int PFLAG_ANIMATION_STARTED = 65536;
    private static final int PFLAG_AWAKEN_SCROLL_BARS_ON_ATTACH = 134217728;
    static final int PFLAG_CANCEL_NEXT_UP_EVENT = 67108864;
    static final int PFLAG_DIRTY = 2097152;
    static final int PFLAG_DIRTY_MASK = 2097152;
    static final int PFLAG_DRAWABLE_STATE_DIRTY = 1024;
    static final int PFLAG_DRAWING_CACHE_VALID = 32768;
    static final int PFLAG_DRAWN = 32;
    static final int PFLAG_DRAW_ANIMATION = 64;
    static final int PFLAG_FOCUSED = 2;
    static final int PFLAG_FORCE_LAYOUT = 4096;
    static final int PFLAG_HAS_BOUNDS = 16;
    private static final int PFLAG_HOVERED = 268435456;
    static final int PFLAG_INVALIDATED = Integer.MIN_VALUE;
    static final int PFLAG_IS_ROOT_NAMESPACE = 8;
    static final int PFLAG_LAYOUT_REQUIRED = 8192;
    static final int PFLAG_MEASURED_DIMENSION_SET = 2048;
    private static final int PFLAG_NOTIFY_AUTOFILL_MANAGER_ON_CLICK = 536870912;
    static final int PFLAG_OPAQUE_BACKGROUND = 8388608;
    static final int PFLAG_OPAQUE_MASK = 25165824;
    static final int PFLAG_OPAQUE_SCROLLBARS = 16777216;
    private static final int PFLAG_PREPRESSED = 33554432;
    private static final int PFLAG_PRESSED = 16384;
    static final int PFLAG_REQUEST_TRANSPARENT_REGIONS = 512;
    private static final int PFLAG_SAVE_STATE_CALLED = 131072;
    static final int PFLAG_SCROLL_CONTAINER = 524288;
    static final int PFLAG_SCROLL_CONTAINER_ADDED = 1048576;
    static final int PFLAG_SELECTED = 4;
    static final int PFLAG_SKIP_DRAW = 128;
    static final int PFLAG_WANTS_FOCUS = 1;
    private static final int POPULATING_ACCESSIBILITY_EVENT_TYPES = 172479;
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_STATE_SET;
    protected static final int[] PRESSED_ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_ENABLED_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_ENABLED_SELECTED_STATE_SET;
    protected static final int[] PRESSED_ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_ENABLED_STATE_SET;
    protected static final int[] PRESSED_ENABLED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_FOCUSED_SELECTED_STATE_SET;
    protected static final int[] PRESSED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_FOCUSED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_SELECTED_STATE_SET;
    protected static final int[] PRESSED_SELECTED_WINDOW_FOCUSED_STATE_SET;
    protected static final int[] PRESSED_STATE_SET;
    protected static final int[] PRESSED_WINDOW_FOCUSED_STATE_SET;
    private static final int PROVIDER_BACKGROUND = 0;
    private static final int PROVIDER_BOUNDS = 2;
    private static final int PROVIDER_NONE = 1;
    private static final int PROVIDER_PADDED_BOUNDS = 3;
    public static final int PUBLIC_STATUS_BAR_VISIBILITY_MASK = 16375;
    public static final Property<View, Float> ROTATION;
    public static final Property<View, Float> ROTATION_X;
    public static final Property<View, Float> ROTATION_Y;
    static final int SAVE_DISABLED = 65536;
    static final int SAVE_DISABLED_MASK = 65536;
    public static final Property<View, Float> SCALE_X;
    public static final Property<View, Float> SCALE_Y;
    public static final int SCREEN_STATE_OFF = 0;
    public static final int SCREEN_STATE_ON = 1;
    static final int SCROLLBARS_HORIZONTAL = 256;
    static final int SCROLLBARS_INSET_MASK = 16777216;
    public static final int SCROLLBARS_INSIDE_INSET = 16777216;
    public static final int SCROLLBARS_INSIDE_OVERLAY = 0;
    static final int SCROLLBARS_MASK = 768;
    static final int SCROLLBARS_NONE = 0;
    public static final int SCROLLBARS_OUTSIDE_INSET = 50331648;
    static final int SCROLLBARS_OUTSIDE_MASK = 33554432;
    public static final int SCROLLBARS_OUTSIDE_OVERLAY = 33554432;
    static final int SCROLLBARS_STYLE_MASK = 50331648;
    static final int SCROLLBARS_VERTICAL = 512;
    public static final int SCROLLBAR_POSITION_DEFAULT = 0;
    public static final int SCROLLBAR_POSITION_LEFT = 1;
    public static final int SCROLLBAR_POSITION_RIGHT = 2;
    public static final int SCROLL_AXIS_HORIZONTAL = 1;
    public static final int SCROLL_AXIS_NONE = 0;
    public static final int SCROLL_AXIS_VERTICAL = 2;
    static final int SCROLL_INDICATORS_NONE = 0;
    static final int SCROLL_INDICATORS_PFLAG3_MASK = 16128;
    static final int SCROLL_INDICATORS_TO_PFLAGS3_LSHIFT = 8;
    public static final int SCROLL_INDICATOR_BOTTOM = 2;
    public static final int SCROLL_INDICATOR_END = 32;
    public static final int SCROLL_INDICATOR_LEFT = 4;
    public static final int SCROLL_INDICATOR_RIGHT = 8;
    public static final int SCROLL_INDICATOR_START = 16;
    public static final int SCROLL_INDICATOR_TOP = 1;
    protected static final int[] SELECTED_STATE_SET;
    protected static final int[] SELECTED_WINDOW_FOCUSED_STATE_SET;
    public static final int SOUND_EFFECTS_ENABLED = 134217728;
    @UnsupportedAppUsage
    public static final int STATUS_BAR_DISABLE_BACK = 4194304;
    public static final int STATUS_BAR_DISABLE_CLOCK = 8388608;
    @UnsupportedAppUsage
    public static final int STATUS_BAR_DISABLE_EXPAND = 65536;
    @UnsupportedAppUsage
    public static final int STATUS_BAR_DISABLE_HOME = 2097152;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_ALERTS = 262144;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_ICONS = 131072;
    public static final int STATUS_BAR_DISABLE_NOTIFICATION_TICKER = 524288;
    @UnsupportedAppUsage
    public static final int STATUS_BAR_DISABLE_RECENT = 16777216;
    public static final int STATUS_BAR_DISABLE_SEARCH = 33554432;
    public static final int STATUS_BAR_DISABLE_SYSTEM_INFO = 1048576;
    @Deprecated
    public static final int STATUS_BAR_HIDDEN = 1;
    public static final int STATUS_BAR_TRANSIENT = 67108864;
    public static final int STATUS_BAR_TRANSLUCENT = 1073741824;
    public static final int STATUS_BAR_TRANSPARENT = 8;
    public static final int STATUS_BAR_UNHIDE = 268435456;
    @Deprecated
    public static final int STATUS_BAR_VISIBLE = 0;
    public static final int SYSTEM_UI_CLEARABLE_FLAGS = 7;
    public static final int SYSTEM_UI_FLAG_FULLSCREEN = 4;
    public static final int SYSTEM_UI_FLAG_HIDE_NAVIGATION = 2;
    public static final int SYSTEM_UI_FLAG_IMMERSIVE = 2048;
    public static final int SYSTEM_UI_FLAG_IMMERSIVE_STICKY = 4096;
    public static final int SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN = 1024;
    public static final int SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION = 512;
    public static final int SYSTEM_UI_FLAG_LAYOUT_STABLE = 256;
    public static final int SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR = 16;
    public static final int SYSTEM_UI_FLAG_LIGHT_STATUS_BAR = 8192;
    public static final int SYSTEM_UI_FLAG_LOW_PROFILE = 1;
    public static final int SYSTEM_UI_FLAG_VISIBLE = 0;
    public static final int SYSTEM_UI_LAYOUT_FLAGS = 1536;
    private static final int SYSTEM_UI_RESERVED_LEGACY1 = 16384;
    private static final int SYSTEM_UI_RESERVED_LEGACY2 = 65536;
    public static final int SYSTEM_UI_TRANSPARENT = 32776;
    public static final int TEXT_ALIGNMENT_CENTER = 4;
    private static final int TEXT_ALIGNMENT_DEFAULT = 1;
    public static final int TEXT_ALIGNMENT_GRAVITY = 1;
    public static final int TEXT_ALIGNMENT_INHERIT = 0;
    static final int TEXT_ALIGNMENT_RESOLVED_DEFAULT = 1;
    public static final int TEXT_ALIGNMENT_TEXT_END = 3;
    public static final int TEXT_ALIGNMENT_TEXT_START = 2;
    public static final int TEXT_ALIGNMENT_VIEW_END = 6;
    public static final int TEXT_ALIGNMENT_VIEW_START = 5;
    public static final int TEXT_DIRECTION_ANY_RTL = 2;
    private static final int TEXT_DIRECTION_DEFAULT = 0;
    public static final int TEXT_DIRECTION_FIRST_STRONG = 1;
    public static final int TEXT_DIRECTION_FIRST_STRONG_LTR = 6;
    public static final int TEXT_DIRECTION_FIRST_STRONG_RTL = 7;
    public static final int TEXT_DIRECTION_INHERIT = 0;
    public static final int TEXT_DIRECTION_LOCALE = 5;
    public static final int TEXT_DIRECTION_LTR = 3;
    static final int TEXT_DIRECTION_RESOLVED_DEFAULT = 1;
    public static final int TEXT_DIRECTION_RTL = 4;
    static final int TOOLTIP = 1073741824;
    public static final Property<View, Float> TRANSLATION_X;
    public static final Property<View, Float> TRANSLATION_Y;
    public static final Property<View, Float> TRANSLATION_Z;
    private static final int UNDEFINED_PADDING = Integer.MIN_VALUE;
    protected static final String VIEW_LOG_TAG = "View";
    protected static final int VIEW_STRUCTURE_FOR_ASSIST = 0;
    protected static final int VIEW_STRUCTURE_FOR_AUTOFILL = 1;
    protected static final int VIEW_STRUCTURE_FOR_CONTENT_CAPTURE = 2;
    private static final int[] VISIBILITY_FLAGS;
    static final int VISIBILITY_MASK = 12;
    public static final int VISIBLE = 0;
    static final int WILL_NOT_CACHE_DRAWING = 131072;
    static final int WILL_NOT_DRAW = 128;
    protected static final int[] WINDOW_FOCUSED_STATE_SET;
    public static final Property<View, Float> X;
    public static final Property<View, Float> Y;
    public static final Property<View, Float> Z;
    private static SparseArray<String> mAttributeMap;
    private static boolean sAcceptZeroSizeDragShadow;
    private static boolean sAlwaysAssignFocus;
    private static boolean sAlwaysRemeasureExactly;
    private static boolean sAutoFocusableOffUIThreadWontNotifyParents;
    static boolean sBrokenInsetsDispatch;
    protected static boolean sBrokenWindowBackground;
    private static boolean sCanFocusZeroSized;
    static boolean sCascadedDragDrop;
    private static boolean sCompatibilityDone;
    private static Paint sDebugPaint;
    public static boolean sDebugViewAttributes;
    public static String sDebugViewAttributesApplicationPackage;
    static boolean sHasFocusableExcludeAutoFocusable;
    private static boolean sIgnoreMeasureCache;
    private static int sNextAccessibilityViewId;
    private static final AtomicInteger sNextGeneratedId;
    protected static boolean sPreserveMarginParamsInLayoutParamConversion;
    static boolean sTextureViewIgnoresDrawableSetters;
    static final ThreadLocal<Rect> sThreadLocal;
    private static boolean sThrowOnInvalidFloatProperties;
    private static boolean sUseBrokenMakeMeasureSpec;
    private static boolean sUseDefaultFocusHighlight;
    static boolean sUseZeroUnspecifiedMeasureSpec;
    private int mAccessibilityCursorPosition;
    @UnsupportedAppUsage
    AccessibilityDelegate mAccessibilityDelegate;
    private CharSequence mAccessibilityPaneTitle;
    private int mAccessibilityTraversalAfterId;
    private int mAccessibilityTraversalBeforeId;
    @UnsupportedAppUsage
    private int mAccessibilityViewId;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private ViewPropertyAnimator mAnimator;
    @UnsupportedAppUsage(maxTargetSdk=28)
    AttachInfo mAttachInfo;
    private SparseArray<int[]> mAttributeResolutionStacks;
    private SparseIntArray mAttributeSourceResId;
    @ViewDebug.ExportedProperty(category="attributes", hasAdjacentMapping=true)
    public String[] mAttributes;
    private String[] mAutofillHints;
    private AutofillId mAutofillId;
    private int mAutofillViewId;
    @ViewDebug.ExportedProperty(deepExport=true, prefix="bg_")
    @UnsupportedAppUsage
    private Drawable mBackground;
    private RenderNode mBackgroundRenderNode;
    @UnsupportedAppUsage
    private int mBackgroundResource;
    private boolean mBackgroundSizeChanged;
    private TintInfo mBackgroundTint;
    @ViewDebug.ExportedProperty(category="layout")
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected int mBottom;
    private ContentCaptureSession mCachedContentCaptureSession;
    @UnsupportedAppUsage
    public boolean mCachingFailed;
    @ViewDebug.ExportedProperty(category="drawing")
    Rect mClipBounds;
    private ContentCaptureSession mContentCaptureSession;
    private CharSequence mContentDescription;
    @ViewDebug.ExportedProperty(deepExport=true)
    @UnsupportedAppUsage
    protected Context mContext;
    protected Animation mCurrentAnimation;
    private Drawable mDefaultFocusHighlight;
    private Drawable mDefaultFocusHighlightCache;
    boolean mDefaultFocusHighlightEnabled;
    private boolean mDefaultFocusHighlightSizeChanged;
    private int[] mDrawableState;
    @UnsupportedAppUsage
    private Bitmap mDrawingCache;
    private int mDrawingCacheBackgroundColor;
    private int mExplicitStyle;
    private ViewTreeObserver mFloatingTreeObserver;
    @ViewDebug.ExportedProperty(deepExport=true, prefix="fg_")
    private ForegroundInfo mForegroundInfo;
    private ArrayList<FrameMetricsObserver> mFrameMetricsObservers;
    GhostView mGhostView;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private boolean mHasPerformedLongPress;
    private boolean mHoveringTouchDelegate;
    @ViewDebug.ExportedProperty(resolveId=true)
    int mID;
    private boolean mIgnoreNextUpEvent;
    private boolean mInContextButtonPress;
    protected final InputEventConsistencyVerifier mInputEventConsistencyVerifier;
    @UnsupportedAppUsage
    private SparseArray<Object> mKeyedTags;
    private int mLabelForId;
    private boolean mLastIsOpaque;
    Paint mLayerPaint;
    @ViewDebug.ExportedProperty(category="drawing", mapping={@ViewDebug.IntToString(from=0, to="NONE"), @ViewDebug.IntToString(from=1, to="SOFTWARE"), @ViewDebug.IntToString(from=2, to="HARDWARE")})
    int mLayerType;
    private Insets mLayoutInsets;
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected ViewGroup.LayoutParams mLayoutParams;
    @ViewDebug.ExportedProperty(category="layout")
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected int mLeft;
    private boolean mLeftPaddingDefined;
    @UnsupportedAppUsage
    ListenerInfo mListenerInfo;
    private float mLongClickX;
    private float mLongClickY;
    private MatchIdPredicate mMatchIdPredicate;
    private MatchLabelForPredicate mMatchLabelForPredicate;
    private LongSparseLongArray mMeasureCache;
    @ViewDebug.ExportedProperty(category="measurement")
    @UnsupportedAppUsage
    int mMeasuredHeight;
    @ViewDebug.ExportedProperty(category="measurement")
    @UnsupportedAppUsage
    int mMeasuredWidth;
    @ViewDebug.ExportedProperty(category="measurement")
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mMinHeight;
    @ViewDebug.ExportedProperty(category="measurement")
    @UnsupportedAppUsage(maxTargetSdk=28)
    private int mMinWidth;
    private ViewParent mNestedScrollingParent;
    int mNextClusterForwardId;
    private int mNextFocusDownId;
    int mNextFocusForwardId;
    private int mNextFocusLeftId;
    private int mNextFocusRightId;
    private int mNextFocusUpId;
    int mOldHeightMeasureSpec;
    int mOldWidthMeasureSpec;
    ViewOutlineProvider mOutlineProvider;
    private int mOverScrollMode;
    ViewOverlay mOverlay;
    @ViewDebug.ExportedProperty(category="padding")
    @UnsupportedAppUsage
    protected int mPaddingBottom;
    @ViewDebug.ExportedProperty(category="padding")
    @UnsupportedAppUsage
    protected int mPaddingLeft;
    @ViewDebug.ExportedProperty(category="padding")
    @UnsupportedAppUsage
    protected int mPaddingRight;
    @ViewDebug.ExportedProperty(category="padding")
    @UnsupportedAppUsage
    protected int mPaddingTop;
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected ViewParent mParent;
    private CheckForLongPress mPendingCheckForLongPress;
    @UnsupportedAppUsage
    private CheckForTap mPendingCheckForTap;
    private PerformClick mPerformClick;
    private PointerIcon mPointerIcon;
    @ViewDebug.ExportedProperty(flagMapping={@ViewDebug.FlagToString(equals=4096, mask=4096, name="FORCE_LAYOUT"), @ViewDebug.FlagToString(equals=8192, mask=8192, name="LAYOUT_REQUIRED"), @ViewDebug.FlagToString(equals=32768, mask=32768, name="DRAWING_CACHE_INVALID", outputIf=false), @ViewDebug.FlagToString(equals=32, mask=32, name="DRAWN", outputIf=true), @ViewDebug.FlagToString(equals=32, mask=32, name="NOT_DRAWN", outputIf=false), @ViewDebug.FlagToString(equals=2097152, mask=2097152, name="DIRTY")}, formatToHexString=true)
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123769414L)
    public int mPrivateFlags;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768943L)
    int mPrivateFlags2;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=129147060L)
    int mPrivateFlags3;
    @UnsupportedAppUsage
    boolean mRecreateDisplayList;
    @UnsupportedAppUsage
    final RenderNode mRenderNode;
    @UnsupportedAppUsage
    private final Resources mResources;
    @ViewDebug.ExportedProperty(category="layout")
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected int mRight;
    private boolean mRightPaddingDefined;
    private RoundScrollbarRenderer mRoundScrollbarRenderer;
    private HandlerActionQueue mRunQueue;
    @UnsupportedAppUsage
    private ScrollabilityCache mScrollCache;
    private Drawable mScrollIndicatorDrawable;
    @ViewDebug.ExportedProperty(category="scrolling")
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected int mScrollX;
    @ViewDebug.ExportedProperty(category="scrolling")
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected int mScrollY;
    private SendViewScrolledAccessibilityEvent mSendViewScrolledAccessibilityEvent;
    private boolean mSendingHoverAccessibilityEvents;
    private int mSourceLayoutId;
    @UnsupportedAppUsage
    String mStartActivityRequestWho;
    private StateListAnimator mStateListAnimator;
    @ViewDebug.ExportedProperty(flagMapping={@ViewDebug.FlagToString(equals=1, mask=1, name="LOW_PROFILE"), @ViewDebug.FlagToString(equals=2, mask=2, name="HIDE_NAVIGATION"), @ViewDebug.FlagToString(equals=4, mask=4, name="FULLSCREEN"), @ViewDebug.FlagToString(equals=256, mask=256, name="LAYOUT_STABLE"), @ViewDebug.FlagToString(equals=512, mask=512, name="LAYOUT_HIDE_NAVIGATION"), @ViewDebug.FlagToString(equals=1024, mask=1024, name="LAYOUT_FULLSCREEN"), @ViewDebug.FlagToString(equals=2048, mask=2048, name="IMMERSIVE"), @ViewDebug.FlagToString(equals=4096, mask=4096, name="IMMERSIVE_STICKY"), @ViewDebug.FlagToString(equals=8192, mask=8192, name="LIGHT_STATUS_BAR"), @ViewDebug.FlagToString(equals=16, mask=16, name="LIGHT_NAVIGATION_BAR"), @ViewDebug.FlagToString(equals=65536, mask=65536, name="STATUS_BAR_DISABLE_EXPAND"), @ViewDebug.FlagToString(equals=131072, mask=131072, name="STATUS_BAR_DISABLE_NOTIFICATION_ICONS"), @ViewDebug.FlagToString(equals=262144, mask=262144, name="STATUS_BAR_DISABLE_NOTIFICATION_ALERTS"), @ViewDebug.FlagToString(equals=524288, mask=524288, name="STATUS_BAR_DISABLE_NOTIFICATION_TICKER"), @ViewDebug.FlagToString(equals=1048576, mask=1048576, name="STATUS_BAR_DISABLE_SYSTEM_INFO"), @ViewDebug.FlagToString(equals=2097152, mask=2097152, name="STATUS_BAR_DISABLE_HOME"), @ViewDebug.FlagToString(equals=4194304, mask=4194304, name="STATUS_BAR_DISABLE_BACK"), @ViewDebug.FlagToString(equals=8388608, mask=8388608, name="STATUS_BAR_DISABLE_CLOCK"), @ViewDebug.FlagToString(equals=16777216, mask=16777216, name="STATUS_BAR_DISABLE_RECENT"), @ViewDebug.FlagToString(equals=33554432, mask=33554432, name="STATUS_BAR_DISABLE_SEARCH"), @ViewDebug.FlagToString(equals=67108864, mask=67108864, name="STATUS_BAR_TRANSIENT"), @ViewDebug.FlagToString(equals=134217728, mask=134217728, name="NAVIGATION_BAR_TRANSIENT"), @ViewDebug.FlagToString(equals=268435456, mask=268435456, name="STATUS_BAR_UNHIDE"), @ViewDebug.FlagToString(equals=536870912, mask=536870912, name="NAVIGATION_BAR_UNHIDE"), @ViewDebug.FlagToString(equals=1073741824, mask=1073741824, name="STATUS_BAR_TRANSLUCENT"), @ViewDebug.FlagToString(equals=Integer.MIN_VALUE, mask=Integer.MIN_VALUE, name="NAVIGATION_BAR_TRANSLUCENT"), @ViewDebug.FlagToString(equals=32768, mask=32768, name="NAVIGATION_BAR_TRANSPARENT"), @ViewDebug.FlagToString(equals=8, mask=8, name="STATUS_BAR_TRANSPARENT")}, formatToHexString=true)
    int mSystemUiVisibility;
    @UnsupportedAppUsage
    protected Object mTag;
    private int[] mTempNestedScrollConsumed;
    TooltipInfo mTooltipInfo;
    @ViewDebug.ExportedProperty(category="layout")
    @UnsupportedAppUsage(maxTargetSdk=28)
    protected int mTop;
    private TouchDelegate mTouchDelegate;
    private int mTouchSlop;
    @UnsupportedAppUsage
    public TransformationInfo mTransformationInfo;
    int mTransientStateCount;
    private String mTransitionName;
    @UnsupportedAppUsage
    private Bitmap mUnscaledDrawingCache;
    private UnsetPressedState mUnsetPressedState;
    @ViewDebug.ExportedProperty(category="padding")
    protected int mUserPaddingBottom;
    @ViewDebug.ExportedProperty(category="padding")
    int mUserPaddingEnd;
    @ViewDebug.ExportedProperty(category="padding")
    protected int mUserPaddingLeft;
    int mUserPaddingLeftInitial;
    @ViewDebug.ExportedProperty(category="padding")
    protected int mUserPaddingRight;
    int mUserPaddingRightInitial;
    @ViewDebug.ExportedProperty(category="padding")
    int mUserPaddingStart;
    private float mVerticalScrollFactor;
    @UnsupportedAppUsage
    private int mVerticalScrollbarPosition;
    @ViewDebug.ExportedProperty(formatToHexString=true)
    @UnsupportedAppUsage(maxTargetSdk=28)
    int mViewFlags;
    private Handler mVisibilityChangeForAutofillHandler;
    int mWindowAttachCount;

    static {
        DEBUG_DRAW = false;
        sDebugViewAttributes = false;
        AUTOFILL_HIGHLIGHT_ATTR = new int[]{16844136};
        sCompatibilityDone = false;
        sUseBrokenMakeMeasureSpec = false;
        sUseZeroUnspecifiedMeasureSpec = false;
        sIgnoreMeasureCache = false;
        sAlwaysRemeasureExactly = false;
        sTextureViewIgnoresDrawableSetters = false;
        VISIBILITY_FLAGS = new int[]{0, 4, 8};
        DRAWING_CACHE_QUALITY_FLAGS = new int[]{0, 524288, 1048576};
        EMPTY_STATE_SET = StateSet.get(0);
        WINDOW_FOCUSED_STATE_SET = StateSet.get(1);
        SELECTED_STATE_SET = StateSet.get(2);
        SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(3);
        FOCUSED_STATE_SET = StateSet.get(4);
        FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(5);
        FOCUSED_SELECTED_STATE_SET = StateSet.get(6);
        FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(7);
        ENABLED_STATE_SET = StateSet.get(8);
        ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(9);
        ENABLED_SELECTED_STATE_SET = StateSet.get(10);
        ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(11);
        ENABLED_FOCUSED_STATE_SET = StateSet.get(12);
        ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(13);
        ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(14);
        ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(15);
        PRESSED_STATE_SET = StateSet.get(16);
        PRESSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(17);
        PRESSED_SELECTED_STATE_SET = StateSet.get(18);
        PRESSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(19);
        PRESSED_FOCUSED_STATE_SET = StateSet.get(20);
        PRESSED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(21);
        PRESSED_FOCUSED_SELECTED_STATE_SET = StateSet.get(22);
        PRESSED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(23);
        PRESSED_ENABLED_STATE_SET = StateSet.get(24);
        PRESSED_ENABLED_WINDOW_FOCUSED_STATE_SET = StateSet.get(25);
        PRESSED_ENABLED_SELECTED_STATE_SET = StateSet.get(26);
        PRESSED_ENABLED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(27);
        PRESSED_ENABLED_FOCUSED_STATE_SET = StateSet.get(28);
        PRESSED_ENABLED_FOCUSED_WINDOW_FOCUSED_STATE_SET = StateSet.get(29);
        PRESSED_ENABLED_FOCUSED_SELECTED_STATE_SET = StateSet.get(30);
        PRESSED_ENABLED_FOCUSED_SELECTED_WINDOW_FOCUSED_STATE_SET = StateSet.get(31);
        DEBUG_CORNERS_COLOR = Color.rgb(63, 127, 255);
        sThreadLocal = new ThreadLocal();
        LAYOUT_DIRECTION_FLAGS = new int[]{0, 1, 2, 3};
        PFLAG2_TEXT_DIRECTION_FLAGS = new int[]{0, 64, 128, 192, 256, 320, 384, 448};
        PFLAG2_TEXT_ALIGNMENT_FLAGS = new int[]{0, 8192, 16384, 24576, 32768, 40960, 49152};
        sNextGeneratedId = new AtomicInteger(1);
        ALPHA = new FloatProperty<View>("alpha"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getAlpha());
            }

            @Override
            public void setValue(View view, float f) {
                view.setAlpha(f);
            }
        };
        TRANSLATION_X = new FloatProperty<View>("translationX"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getTranslationX());
            }

            @Override
            public void setValue(View view, float f) {
                view.setTranslationX(f);
            }
        };
        TRANSLATION_Y = new FloatProperty<View>("translationY"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getTranslationY());
            }

            @Override
            public void setValue(View view, float f) {
                view.setTranslationY(f);
            }
        };
        TRANSLATION_Z = new FloatProperty<View>("translationZ"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getTranslationZ());
            }

            @Override
            public void setValue(View view, float f) {
                view.setTranslationZ(f);
            }
        };
        X = new FloatProperty<View>("x"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getX());
            }

            @Override
            public void setValue(View view, float f) {
                view.setX(f);
            }
        };
        Y = new FloatProperty<View>("y"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getY());
            }

            @Override
            public void setValue(View view, float f) {
                view.setY(f);
            }
        };
        Z = new FloatProperty<View>("z"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getZ());
            }

            @Override
            public void setValue(View view, float f) {
                view.setZ(f);
            }
        };
        ROTATION = new FloatProperty<View>("rotation"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getRotation());
            }

            @Override
            public void setValue(View view, float f) {
                view.setRotation(f);
            }
        };
        ROTATION_X = new FloatProperty<View>("rotationX"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getRotationX());
            }

            @Override
            public void setValue(View view, float f) {
                view.setRotationX(f);
            }
        };
        ROTATION_Y = new FloatProperty<View>("rotationY"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getRotationY());
            }

            @Override
            public void setValue(View view, float f) {
                view.setRotationY(f);
            }
        };
        SCALE_X = new FloatProperty<View>("scaleX"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getScaleX());
            }

            @Override
            public void setValue(View view, float f) {
                view.setScaleX(f);
            }
        };
        SCALE_Y = new FloatProperty<View>("scaleY"){

            @Override
            public Float get(View view) {
                return Float.valueOf(view.getScaleY());
            }

            @Override
            public void setValue(View view, float f) {
                view.setScaleY(f);
            }
        };
    }

    @UnsupportedAppUsage
    View() {
        this.mCurrentAnimation = null;
        this.mRecreateDisplayList = false;
        this.mID = -1;
        this.mAutofillViewId = -1;
        this.mAccessibilityViewId = -1;
        this.mAccessibilityCursorPosition = -1;
        this.mTag = null;
        this.mTransientStateCount = 0;
        this.mClipBounds = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mLabelForId = -1;
        this.mAccessibilityTraversalBeforeId = -1;
        this.mAccessibilityTraversalAfterId = -1;
        this.mLeftPaddingDefined = false;
        this.mRightPaddingDefined = false;
        this.mOldWidthMeasureSpec = Integer.MIN_VALUE;
        this.mOldHeightMeasureSpec = Integer.MIN_VALUE;
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        this.mDrawableState = null;
        this.mOutlineProvider = ViewOutlineProvider.BACKGROUND;
        this.mNextFocusLeftId = -1;
        this.mNextFocusRightId = -1;
        this.mNextFocusUpId = -1;
        this.mNextFocusDownId = -1;
        this.mNextFocusForwardId = -1;
        this.mNextClusterForwardId = -1;
        this.mDefaultFocusHighlightEnabled = true;
        this.mPendingCheckForTap = null;
        this.mTouchDelegate = null;
        this.mHoveringTouchDelegate = false;
        this.mDrawingCacheBackgroundColor = 0;
        this.mAnimator = null;
        this.mLayerType = 0;
        InputEventConsistencyVerifier inputEventConsistencyVerifier = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mInputEventConsistencyVerifier = inputEventConsistencyVerifier;
        this.mSourceLayoutId = 0;
        this.mResources = null;
        this.mRenderNode = RenderNode.create(this.getClass().getName(), new ViewAnimationHostBridge(this));
    }

    public View(Context context) {
        Object var2_2 = null;
        this.mCurrentAnimation = null;
        boolean bl = false;
        this.mRecreateDisplayList = false;
        this.mID = -1;
        this.mAutofillViewId = -1;
        this.mAccessibilityViewId = -1;
        this.mAccessibilityCursorPosition = -1;
        this.mTag = null;
        this.mTransientStateCount = 0;
        this.mClipBounds = null;
        this.mPaddingLeft = 0;
        this.mPaddingRight = 0;
        this.mLabelForId = -1;
        this.mAccessibilityTraversalBeforeId = -1;
        this.mAccessibilityTraversalAfterId = -1;
        this.mLeftPaddingDefined = false;
        this.mRightPaddingDefined = false;
        this.mOldWidthMeasureSpec = Integer.MIN_VALUE;
        this.mOldHeightMeasureSpec = Integer.MIN_VALUE;
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        this.mDrawableState = null;
        this.mOutlineProvider = ViewOutlineProvider.BACKGROUND;
        this.mNextFocusLeftId = -1;
        this.mNextFocusRightId = -1;
        this.mNextFocusUpId = -1;
        this.mNextFocusDownId = -1;
        this.mNextFocusForwardId = -1;
        this.mNextClusterForwardId = -1;
        this.mDefaultFocusHighlightEnabled = true;
        this.mPendingCheckForTap = null;
        this.mTouchDelegate = null;
        this.mHoveringTouchDelegate = false;
        this.mDrawingCacheBackgroundColor = 0;
        this.mAnimator = null;
        this.mLayerType = 0;
        Object object = InputEventConsistencyVerifier.isInstrumentationEnabled() ? new InputEventConsistencyVerifier(this, 0) : null;
        this.mInputEventConsistencyVerifier = object;
        this.mSourceLayoutId = 0;
        this.mContext = context;
        object = var2_2;
        if (context != null) {
            object = context.getResources();
        }
        this.mResources = object;
        this.mViewFlags = 402653200;
        this.mPrivateFlags2 = 140296;
        this.mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        this.setOverScrollMode(1);
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mRenderNode = RenderNode.create(this.getClass().getName(), new ViewAnimationHostBridge(this));
        if (!sCompatibilityDone && context != null) {
            int n = context.getApplicationInfo().targetSdkVersion;
            boolean bl2 = n <= 17;
            sUseBrokenMakeMeasureSpec = bl2;
            bl2 = n < 19;
            sIgnoreMeasureCache = bl2;
            bl2 = n < 23;
            Canvas.sCompatibilityRestore = bl2;
            bl2 = n < 26;
            Canvas.sCompatibilitySetBitmap = bl2;
            Canvas.setCompatibilityVersion(n);
            bl2 = n < 23;
            sUseZeroUnspecifiedMeasureSpec = bl2;
            bl2 = n <= 23;
            sAlwaysRemeasureExactly = bl2;
            bl2 = n <= 23;
            sTextureViewIgnoresDrawableSetters = bl2;
            bl2 = n >= 24;
            sPreserveMarginParamsInLayoutParamConversion = bl2;
            bl2 = n < 24;
            sCascadedDragDrop = bl2;
            bl2 = n < 26;
            sHasFocusableExcludeAutoFocusable = bl2;
            bl2 = n < 26;
            sAutoFocusableOffUIThreadWontNotifyParents = bl2;
            sUseDefaultFocusHighlight = context.getResources().getBoolean(17891556);
            bl2 = n >= 28;
            sThrowOnInvalidFloatProperties = bl2;
            bl2 = n < 28;
            sCanFocusZeroSized = bl2;
            bl2 = n < 28;
            sAlwaysAssignFocus = bl2;
            bl2 = n < 28;
            sAcceptZeroSizeDragShadow = bl2;
            bl2 = ViewRootImpl.sNewInsetsMode != 2 || n < 29;
            sBrokenInsetsDispatch = bl2;
            bl2 = bl;
            if (n < 29) {
                bl2 = true;
            }
            sBrokenWindowBackground = bl2;
            sCompatibilityDone = true;
        }
    }

    public View(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public View(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public View(Context var1_1, AttributeSet var2_2, int var3_3, int var4_4) {
        this(var1_1);
        this.mSourceLayoutId = Resources.getAttributeSetSourceResId((AttributeSet)var2_2 /* !! */ );
        var5_17 = var1_1.obtainStyledAttributes((AttributeSet)var2_2 /* !! */ , R.styleable.View, var3_15, var4_16);
        this.retrieveExplicitStyle(var1_1.getTheme(), (AttributeSet)var2_2 /* !! */ );
        this.saveAttributeDataForStyleable(var1_1, R.styleable.View, (AttributeSet)var2_2 /* !! */ , var5_17, var3_15, var4_16);
        if (View.sDebugViewAttributes) {
            this.saveAttributeData((AttributeSet)var2_2 /* !! */ , var5_17);
        }
        var6_18 = Integer.MIN_VALUE;
        var7_19 = Integer.MIN_VALUE;
        var8_20 = 0;
        var9_21 = 0;
        var10_22 = this.mOverScrollMode;
        var11_23 = false;
        var12_24 = false;
        var13_25 = 0;
        var14_26 = 0;
        var2_3 = var1_1.getApplicationInfo();
        var15_27 = -1;
        var16_28 = var2_3.targetSdkVersion;
        var17_29 = -1;
        var18_30 = var5_17.getIndexCount();
        var19_31 = 0 | 16;
        var3_15 = -1;
        var20_32 = 0 | 16;
        var21_33 = 0.0f;
        var22_34 = 0.0f;
        var23_35 = 0.0f;
        var24_36 = 0.0f;
        var25_37 = 0.0f;
        var26_38 = 0.0f;
        var27_39 = 0.0f;
        var28_40 = 1.0f;
        var29_41 = 1.0f;
        var30_42 = null;
        var31_43 = -1;
        var32_44 = -1;
        var33_45 = -1;
        var34_46 = 0;
        var35_47 = 0;
        var36_48 = false;
        var37_49 = false;
        var39_51 = false;
        var40_52 = -1;
        for (var38_50 = 0; var38_50 < var18_30; ++var38_50) {
            block142 : {
                block143 : {
                    block141 : {
                        var4_16 = var5_17.getIndex(var38_50);
                        if (var4_16 == 109) break block141;
                        switch (var4_16) {
                            default: {
                                block20 : switch (var4_16) {
                                    default: {
                                        switch (var4_16) {
                                            default: {
                                                ** break;
                                            }
                                            case 102: {
                                                this.mRenderNode.setForceDarkAllowed(var5_17.getBoolean(var4_16, true));
                                                ** break;
                                            }
                                            case 101: {
                                                this.setOutlineAmbientShadowColor(var5_17.getColor(var4_16, -16777216));
                                                ** break;
                                            }
                                            case 100: {
                                                this.setOutlineSpotShadowColor(var5_17.getColor(var4_16, -16777216));
                                                ** break;
                                            }
                                            case 99: {
                                                this.setAccessibilityHeading(var5_17.getBoolean(var4_16, false));
                                                ** break;
                                            }
                                            case 98: {
                                                if (var5_17.peekValue(var4_16) == null) break block20;
                                                this.setAccessibilityPaneTitle(var5_17.getString(var4_16));
                                                ** break;
                                            }
                                            case 97: {
                                                if (var5_17.peekValue(var4_16) == null) break block20;
                                                this.setScreenReaderFocusable(var5_17.getBoolean(var4_16, false));
                                                ** break;
                                            }
                                            case 96: {
                                                if (var5_17.peekValue(var4_16) == null) break block20;
                                                this.setDefaultFocusHighlightEnabled(var5_17.getBoolean(var4_16, true));
                                                ** break;
                                            }
                                            case 95: {
                                                if (var5_17.peekValue(var4_16) == null) break block20;
                                                this.setImportantForAutofill(var5_17.getInt(var4_16, 0));
                                                ** break;
                                            }
                                            case 94: {
                                                if (var5_17.peekValue(var4_16) == null) break block20;
                                                var41_53 = null;
                                                var42_54 = var5_17.getType(var4_16);
                                                var2_5 = null;
                                                if (var42_54 == 1) {
                                                    var42_54 = var5_17.getResourceId(var4_16, 0);
                                                    try {
                                                        var43_55 = var5_17.getTextArray(var4_16);
                                                        var2_6 = var41_53;
                                                    }
                                                    catch (Resources.NotFoundException var43_56) {
                                                        var41_53 = this.getResources().getString(var42_54);
                                                        var43_55 = var2_5;
                                                        var2_7 = var41_53;
                                                    }
                                                    var41_53 = var2_8;
                                                    var2_9 = var43_55;
                                                } else {
                                                    var41_53 = var5_17.getString(var4_16);
                                                }
                                                if (var2_10 == null) {
                                                    if (var41_53 == null) throw new IllegalArgumentException("Could not resolve autofillHints");
                                                    var2_11 = var41_53.split(",");
                                                }
                                                var43_55 = new String[((void)var2_12).length];
                                                var42_54 = ((void)var2_12).length;
                                                for (var4_16 = 0; var4_16 < var42_54; ++var4_16) {
                                                    var43_55[var4_16] = var2_12[var4_16].toString().trim();
                                                }
                                                this.setAutofillHints((String[])var43_55);
                                                ** break;
                                            }
                                            case 93: {
                                                if (var5_17.peekValue(var4_16) == null) break block20;
                                                this.setFocusedByDefault(var5_17.getBoolean(var4_16, true));
                                                ** break;
                                            }
                                            case 92: {
                                                this.mNextClusterForwardId = var5_17.getResourceId(var4_16, -1);
                                                ** break;
                                            }
                                            case 91: {
                                                if (var5_17.peekValue(var4_16) == null) break block20;
                                                this.setKeyboardNavigationCluster(var5_17.getBoolean(var4_16, true));
                                                ** break;
                                            }
                                            case 90: {
                                                var33_45 = var5_17.getDimensionPixelSize(var4_16, -1);
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                var44_57 = var8_20;
                                                ** break;
                                            }
                                            case 89: {
                                                this.mUserPaddingLeftInitial = var32_44 = var5_17.getDimensionPixelSize(var4_16, -1);
                                                this.mUserPaddingRightInitial = var32_44;
                                                var37_49 = true;
                                                var36_48 = true;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                var44_57 = var8_20;
                                                ** break;
                                            }
                                            case 88: {
                                                this.setTooltipText(var5_17.getText(var4_16));
                                                ** break;
                                            }
                                            case 87: {
                                                if (var5_17.peekValue(var4_16) == null) break block20;
                                                this.forceHasOverlappingRendering(var5_17.getBoolean(var4_16, true));
                                                ** break;
                                            }
                                            case 86: {
                                                var42_54 = var5_17.getResourceId(var4_16, 0);
                                                if (var42_54 != 0) {
                                                    this.setPointerIcon(PointerIcon.load(var1_1.getResources(), var42_54));
                                                    ** break;
                                                }
                                                if ((var4_16 = var5_17.getInt(var4_16, 1)) == 1) break block20;
                                                this.setPointerIcon(PointerIcon.getSystemIcon(var1_1, var4_16));
                                                ** break;
                                            }
                                            case 85: {
                                                if (!var5_17.getBoolean(var4_16, false)) break block20;
                                                var4_16 = 8388608 | var19_31;
                                                var42_54 = 8388608 | var20_32;
                                                var44_57 = var8_20;
                                                ** break;
                                            }
                                            case 84: {
                                                var4_16 = var5_17.getInt(var4_16, 0) << 8 & 16128;
                                                if (var4_16 == 0) break block20;
                                                this.mPrivateFlags3 |= var4_16;
                                                var12_24 = true;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                var44_57 = var8_20;
                                                ** break;
                                            }
                                            case 83: {
                                                this.setAccessibilityTraversalAfter(var5_17.getResourceId(var4_16, -1));
                                                ** break;
                                            }
                                            case 82: {
                                                this.setAccessibilityTraversalBefore(var5_17.getResourceId(var4_16, -1));
                                                ** break;
                                            }
                                            case 81: {
                                                this.setOutlineProviderFromAttribute(var5_17.getInt(81, 0));
                                                ** break;
                                            }
                                            case 80: {
                                                if (var16_28 < 23 && !(this instanceof FrameLayout)) break block20;
                                                this.setForegroundTintBlendMode(Drawable.parseBlendMode(var5_17.getInt(var4_16, -1), null));
                                                ** break;
                                            }
                                            case 79: {
                                                if (var16_28 < 23 && !(this instanceof FrameLayout)) break block20;
                                                this.setForegroundTintList(var5_17.getColorStateList(var4_16));
                                                ** break;
                                            }
                                            case 78: {
                                                if (this.mBackgroundTint == null) {
                                                    this.mBackgroundTint = new TintInfo();
                                                }
                                                this.mBackgroundTint.mBlendMode = Drawable.parseBlendMode(var5_17.getInt(78, -1), null);
                                                this.mBackgroundTint.mHasTintMode = true;
                                                ** break;
                                            }
                                            case 77: {
                                                if (this.mBackgroundTint == null) {
                                                    this.mBackgroundTint = new TintInfo();
                                                }
                                                this.mBackgroundTint.mTintList = var5_17.getColorStateList(77);
                                                this.mBackgroundTint.mHasTintList = true;
                                                ** break;
                                            }
                                            case 76: {
                                                this.setStateListAnimator(AnimatorInflater.loadStateListAnimator(var1_1, var5_17.getResourceId(var4_16, 0)));
                                                ** break;
                                            }
                                            case 75: {
                                                var24_36 = var5_17.getDimension(var4_16, 0.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 74: {
                                                this.setNestedScrollingEnabled(var5_17.getBoolean(var4_16, false));
                                                ** break;
                                            }
                                            case 73: {
                                                this.setTransitionName(var5_17.getString(var4_16));
                                                ** break;
                                            }
                                            case 72: {
                                                var23_35 = var5_17.getDimension(var4_16, 0.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 71: {
                                                this.setAccessibilityLiveRegion(var5_17.getInt(var4_16, 0));
                                                ** break;
                                            }
                                            case 70: {
                                                this.setLabelFor(var5_17.getResourceId(var4_16, -1));
                                                ** break;
                                            }
                                            case 69: {
                                                var7_19 = var5_17.getDimensionPixelSize(var4_16, Integer.MIN_VALUE);
                                                var4_16 = var7_19 != Integer.MIN_VALUE ? 1 : 0;
                                                var14_26 = var4_16;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                var44_57 = var8_20;
                                                ** break;
                                            }
                                            case 68: {
                                                var6_18 = var5_17.getDimensionPixelSize(var4_16, Integer.MIN_VALUE);
                                                var4_16 = var6_18 != Integer.MIN_VALUE ? 1 : 0;
                                                var13_25 = var4_16;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                var44_57 = var8_20;
                                                ** break;
                                            }
                                            case 67: {
                                                this.mPrivateFlags2 &= -61;
                                                var4_16 = var5_17.getInt(var4_16, -1);
                                                var4_16 = var4_16 != -1 ? View.LAYOUT_DIRECTION_FLAGS[var4_16] : 2;
                                                this.mPrivateFlags2 |= var4_16 << 2;
                                                ** break;
                                            }
                                            case 66: {
                                                this.mPrivateFlags2 &= -57345;
                                                var4_16 = var5_17.getInt(var4_16, 1);
                                                this.mPrivateFlags2 |= View.PFLAG2_TEXT_ALIGNMENT_FLAGS[var4_16];
                                                ** break;
                                            }
                                            case 65: {
                                                this.mPrivateFlags2 &= -449;
                                                var4_16 = var5_17.getInt(var4_16, -1);
                                                if (var4_16 == -1) break block20;
                                                this.mPrivateFlags2 |= View.PFLAG2_TEXT_DIRECTION_FLAGS[var4_16];
                                                ** break;
                                            }
                                            case 64: {
                                                this.setImportantForAccessibility(var5_17.getInt(var4_16, 0));
                                                ** break;
                                            }
                                            case 63: {
                                                ** GOTO lbl433
                                            }
                                            case 62: {
                                                this.setLayerType(var5_17.getInt(var4_16, 0), null);
                                                ** break;
                                            }
                                            case 61: {
                                                this.mNextFocusForwardId = var5_17.getResourceId(var4_16, -1);
                                                ** break;
                                            }
                                            case 60: {
                                                this.mVerticalScrollbarPosition = var5_17.getInt(var4_16, 0);
                                                ** break;
                                            }
                                            case 59: {
                                                var27_39 = var5_17.getFloat(var4_16, 0.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 58: {
                                                var26_38 = var5_17.getFloat(var4_16, 0.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 57: {
                                                var25_37 = var5_17.getFloat(var4_16, 0.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 56: {
                                                var29_41 = var5_17.getFloat(var4_16, 1.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 55: {
                                                var28_40 = var5_17.getFloat(var4_16, 1.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 54: {
                                                var22_34 = var5_17.getDimension(var4_16, 0.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 53: {
                                                var21_33 = var5_17.getDimension(var4_16, 0.0f);
                                                var44_57 = 1;
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                ** break;
                                            }
                                            case 52: {
                                                this.setPivotY(var5_17.getDimension(var4_16, 0.0f));
                                                ** break;
                                            }
                                            case 51: {
                                                this.setPivotX(var5_17.getDimension(var4_16, 0.0f));
                                                ** break;
                                            }
                                            case 50: {
                                                this.setAlpha(var5_17.getFloat(var4_16, 1.0f));
                                                ** break;
                                            }
                                            case 49: {
                                                if (!var5_17.getBoolean(var4_16, false)) break block20;
                                                var4_16 = var19_31 | 1024;
                                                var42_54 = var20_32 | 1024;
                                                var44_57 = var8_20;
                                                ** break;
                                            }
                                            case 48: {
                                                var10_22 = var5_17.getInt(var4_16, 1);
                                                var42_54 = var20_32;
                                                var4_16 = var19_31;
                                                var44_57 = var8_20;
                                                ** break;
lbl334: // 17 sources:
                                                break;
                                            }
                                        }
                                        break block142;
                                    }
                                    case 44: {
                                        this.setContentDescription(var5_17.getString(var4_16));
                                        ** break;
                                    }
                                    case 43: {
                                        if (var1_1.isRestricted() != false) throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                                        var2_13 = var5_17.getString(var4_16);
                                        if (var2_13 == null) break;
                                        this.setOnClickListener(new DeclaredOnClickListener(this, var2_13));
                                        ** break;
                                    }
                                    case 42: {
                                        if (var5_17.getBoolean(var4_16, true)) break;
                                        var4_16 = -268435457 & var19_31;
                                        var42_54 = 268435456 | var20_32;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 41: {
                                        if (var5_17.getBoolean(var4_16, false)) {
                                            this.setScrollContainer(true);
                                        }
                                        var39_51 = true;
                                        var42_54 = var20_32;
                                        var4_16 = var19_31;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 40: {
                                        if (!var5_17.getBoolean(var4_16, false)) break;
                                        var4_16 = 67108864 | var19_31;
                                        var42_54 = 67108864 | var20_32;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 39: {
                                        if (var5_17.getBoolean(var4_16, true)) break;
                                        var4_16 = -134217729 & var19_31;
                                        var42_54 = 134217728 | var20_32;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 38: {
                                        if (var16_28 < 23 && !(this instanceof FrameLayout)) break;
                                        this.setForegroundGravity(var5_17.getInt(var4_16, 0));
                                        ** break;
                                    }
                                    case 37: {
                                        this.mMinHeight = var5_17.getDimensionPixelSize(var4_16, 0);
                                        ** break;
                                    }
                                    case 36: {
                                        this.mMinWidth = var5_17.getDimensionPixelSize(var4_16, 0);
                                        ** break;
                                    }
                                    case 35: {
                                        if (var16_28 < 23 && !(this instanceof FrameLayout)) break;
                                        this.setForeground(var5_17.getDrawable(var4_16));
                                        ** break;
                                    }
                                    case 34: {
                                        if (!var5_17.getBoolean(var4_16, false)) break;
                                        var4_16 = 4194304 | var19_31;
                                        var42_54 = 4194304 | var20_32;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 33: {
                                        var4_16 = var5_17.getInt(var4_16, 0);
                                        if (var4_16 == 0) break;
                                        var4_16 = View.DRAWING_CACHE_QUALITY_FLAGS[var4_16];
                                        var42_54 = var20_32 | 1572864;
                                        var4_16 |= var19_31;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 32: {
                                        if (var5_17.getBoolean(var4_16, true)) break;
                                        var42_54 = 65536 | var20_32;
                                        var4_16 = var19_31 | 65536;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 31: {
                                        if (!var5_17.getBoolean(var4_16, false)) break;
                                        var42_54 = 2097152 | var20_32;
                                        var4_16 = var19_31 | 2097152;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 30: {
                                        if (!var5_17.getBoolean(var4_16, false)) break;
                                        var4_16 = var19_31 | 16384;
                                        var42_54 = var20_32 | 16384;
                                        var44_57 = var8_20;
                                        break block142;
                                    }
                                    case 29: {
                                        this.mNextFocusDownId = var5_17.getResourceId(var4_16, -1);
                                        ** break;
                                    }
                                    case 28: {
                                        this.mNextFocusUpId = var5_17.getResourceId(var4_16, -1);
                                        ** break;
                                    }
                                    case 27: {
                                        this.mNextFocusRightId = var5_17.getResourceId(var4_16, -1);
                                        ** break;
                                    }
                                    case 26: {
                                        this.mNextFocusLeftId = var5_17.getResourceId(var4_16, -1);
                                        ** break;
lbl429: // 49 sources:
                                        break;
                                    }
                                }
                                break block143;
                            }
                            case 24: {
                                if (var16_28 >= 14) break block143;
lbl433: // 2 sources:
                                var4_16 = var5_17.getInt(var4_16, 0);
                                if (var4_16 == 0) break block143;
                                var42_54 = var20_32 | 12288;
                                this.initializeFadingEdgeInternal(var5_17);
                                var4_16 = var19_31 | var4_16;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 23: {
                                var4_16 = var5_17.getInt(var4_16, 0);
                                if (var4_16 == 0) break block143;
                                var42_54 = var20_32 | 768;
                                var11_23 = true;
                                var4_16 = var19_31 | var4_16;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 22: {
                                if (!var5_17.getBoolean(var4_16, false)) break block143;
                                var4_16 = var19_31 | 2;
                                var42_54 = var20_32 | 2;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 21: {
                                var4_16 = var5_17.getInt(var4_16, 0);
                                if (var4_16 == 0) break block143;
                                var4_16 = View.VISIBILITY_FLAGS[var4_16];
                                var42_54 = var20_32 | 12;
                                var4_16 |= var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 20: {
                                if (!var5_17.getBoolean(var4_16, false)) break block143;
                                var4_16 = var19_31 & -17 | 262145;
                                var42_54 = 262161 | var20_32;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 19: {
                                var4_16 = var19_31 & -18 | this.getFocusableAttribute(var5_17);
                                if ((var4_16 & 16) == 0) {
                                    var42_54 = var20_32 | 17;
                                    var44_57 = var8_20;
                                } else {
                                    var42_54 = var20_32;
                                    var44_57 = var8_20;
                                }
                                break block142;
                            }
                            case 18: {
                                var40_52 = var5_17.getDimensionPixelSize(var4_16, -1);
                                var42_54 = var20_32;
                                var4_16 = var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 17: {
                                this.mUserPaddingRightInitial = var31_43 = var5_17.getDimensionPixelSize(var4_16, -1);
                                var37_49 = true;
                                var42_54 = var20_32;
                                var4_16 = var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 16: {
                                var17_29 = var5_17.getDimensionPixelSize(var4_16, -1);
                                var42_54 = var20_32;
                                var4_16 = var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 15: {
                                this.mUserPaddingLeftInitial = var15_27 = var5_17.getDimensionPixelSize(var4_16, -1);
                                var36_48 = true;
                                var42_54 = var20_32;
                                var4_16 = var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 14: {
                                this.mUserPaddingLeftInitial = var3_15 = var5_17.getDimensionPixelSize(var4_16, -1);
                                this.mUserPaddingRightInitial = var3_15;
                                var37_49 = true;
                                var36_48 = true;
                                var42_54 = var20_32;
                                var4_16 = var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 13: {
                                var30_42 = var5_17.getDrawable(var4_16);
                                var42_54 = var20_32;
                                var4_16 = var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 12: {
                                var35_47 = var5_17.getDimensionPixelOffset(var4_16, 0);
                                var42_54 = var20_32;
                                var4_16 = var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 11: {
                                var34_46 = var5_17.getDimensionPixelOffset(var4_16, 0);
                                var42_54 = var20_32;
                                var4_16 = var19_31;
                                var44_57 = var8_20;
                                break block142;
                            }
                            case 10: {
                                this.mTag = var5_17.getText(var4_16);
                                break block143;
                            }
                            case 9: {
                                this.mID = var5_17.getResourceId(var4_16, -1);
                                break block143;
                            }
                            case 8: 
                        }
                        var9_21 = var5_17.getInt(var4_16, 0);
                        if (var9_21 != 0) {
                            var4_16 = var19_31 | var9_21 & 50331648;
                            var42_54 = 50331648 | var20_32;
                            var44_57 = var8_20;
                        } else {
                            var42_54 = var20_32;
                            var4_16 = var19_31;
                            var44_57 = var8_20;
                        }
                        break block142;
                    }
                    if (var16_28 >= 23 || this instanceof FrameLayout) {
                        if (this.mForegroundInfo == null) {
                            this.mForegroundInfo = new ForegroundInfo();
                        }
                        var2_14 = this.mForegroundInfo;
                        ForegroundInfo.access$102(var2_14, var5_17.getBoolean(var4_16, ForegroundInfo.access$100(var2_14)));
                    }
                }
                var44_57 = var8_20;
                var4_16 = var19_31;
                var42_54 = var20_32;
            }
            var20_32 = var42_54;
            var19_31 = var4_16;
            var8_20 = var44_57;
        }
        this.setOverScrollMode(var10_22);
        this.mUserPaddingStart = var6_18;
        this.mUserPaddingEnd = var7_19;
        if (var30_42 != null) {
            this.setBackground(var30_42);
        }
        this.mLeftPaddingDefined = var36_48;
        this.mRightPaddingDefined = var37_49;
        if (var3_15 >= 0) {
            var44_57 = var3_15;
            var4_16 = var3_15;
            var32_44 = var3_15;
            this.mUserPaddingLeftInitial = var42_54 = var3_15;
            this.mUserPaddingRightInitial = var42_54;
            var42_54 = var3_15;
            var3_15 = var32_44;
        } else {
            if (var32_44 >= 0) {
                var3_15 = var32_44;
                this.mUserPaddingLeftInitial = var4_16 = var32_44;
                this.mUserPaddingRightInitial = var4_16;
                var44_57 = var32_44;
            } else {
                var44_57 = var15_27;
                var3_15 = var31_43;
            }
            if (var33_45 >= 0) {
                var4_16 = var33_45;
                var42_54 = var3_15;
                var3_15 = var33_45;
            } else {
                var42_54 = var3_15;
                var3_15 = var40_52;
                var4_16 = var17_29;
            }
        }
        if (this.isRtlCompatibilityMode()) {
            var32_44 = var44_57;
            if (!this.mLeftPaddingDefined) {
                var32_44 = var44_57;
                if (var13_25 != 0) {
                    var32_44 = var6_18;
                }
            }
            var44_57 = var32_44 >= 0 ? var32_44 : this.mUserPaddingLeftInitial;
            this.mUserPaddingLeftInitial = var44_57;
            var44_57 = var42_54;
            if (!this.mRightPaddingDefined) {
                var44_57 = var42_54;
                if (var14_26 != 0) {
                    var44_57 = var7_19;
                }
            }
            if (var44_57 < 0) {
                var44_57 = this.mUserPaddingRightInitial;
            }
            this.mUserPaddingRightInitial = var44_57;
        } else {
            var33_45 = var13_25 == 0 && var14_26 == 0 ? 0 : 1;
            if (this.mLeftPaddingDefined && var33_45 == 0) {
                this.mUserPaddingLeftInitial = var44_57;
            }
            var32_44 = var44_57;
            if (this.mRightPaddingDefined) {
                var32_44 = var44_57;
                if (var33_45 == 0) {
                    this.mUserPaddingRightInitial = var42_54;
                    var32_44 = var44_57;
                }
            }
        }
        var42_54 = this.mUserPaddingLeftInitial;
        if (var4_16 < 0) {
            var4_16 = this.mPaddingTop;
        }
        var44_57 = this.mUserPaddingRightInitial;
        if (var3_15 < 0) {
            var3_15 = this.mPaddingBottom;
        }
        this.internalSetPadding(var42_54, var4_16, var44_57, var3_15);
        if (var20_32 != 0) {
            this.setFlags(var19_31, var20_32);
        }
        if (var11_23) {
            this.initializeScrollbarsInternal(var5_17);
        }
        if (var12_24) {
            this.initializeScrollIndicatorsInternal();
        }
        var5_17.recycle();
        if (var9_21 != 0) {
            this.recomputePadding();
        }
        if (var34_46 != 0 || var35_47 != 0) {
            this.scrollTo(var34_46, var35_47);
        }
        if (var8_20 != 0) {
            this.setTranslationX(var21_33);
            this.setTranslationY(var22_34);
            this.setTranslationZ(var23_35);
            this.setElevation(var24_36);
            this.setRotation(var25_37);
            this.setRotationX(var26_38);
            this.setRotationY(var27_39);
            this.setScaleX(var28_40);
            this.setScaleY(var29_41);
        }
        if (!var39_51 && (var19_31 & 512) != 0) {
            this.setScrollContainer(true);
        }
        this.computeOpaqueFlags();
    }

    private void applyBackgroundTint() {
        if (this.mBackground != null && this.mBackgroundTint != null) {
            TintInfo tintInfo = this.mBackgroundTint;
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                this.mBackground = this.mBackground.mutate();
                if (tintInfo.mHasTintList) {
                    this.mBackground.setTintList(tintInfo.mTintList);
                }
                if (tintInfo.mHasTintMode) {
                    this.mBackground.setTintBlendMode(tintInfo.mBlendMode);
                }
                if (this.mBackground.isStateful()) {
                    this.mBackground.setState(this.getDrawableState());
                }
            }
        }
    }

    private void applyForegroundTint() {
        Object object = this.mForegroundInfo;
        if (object != null && ((ForegroundInfo)object).mDrawable != null && this.mForegroundInfo.mTintInfo != null) {
            object = this.mForegroundInfo.mTintInfo;
            if (((TintInfo)object).mHasTintList || ((TintInfo)object).mHasTintMode) {
                ForegroundInfo foregroundInfo = this.mForegroundInfo;
                foregroundInfo.mDrawable = foregroundInfo.mDrawable.mutate();
                if (((TintInfo)object).mHasTintList) {
                    this.mForegroundInfo.mDrawable.setTintList(((TintInfo)object).mTintList);
                }
                if (((TintInfo)object).mHasTintMode) {
                    this.mForegroundInfo.mDrawable.setTintBlendMode(((TintInfo)object).mBlendMode);
                }
                if (this.mForegroundInfo.mDrawable.isStateful()) {
                    this.mForegroundInfo.mDrawable.setState(this.getDrawableState());
                }
            }
        }
    }

    private boolean applyLegacyAnimation(ViewGroup viewGroup, long l, Animation animation, boolean bl) {
        Object object;
        int n = viewGroup.mGroupFlags;
        if (!animation.isInitialized()) {
            animation.initialize(this.mRight - this.mLeft, this.mBottom - this.mTop, viewGroup.getWidth(), viewGroup.getHeight());
            animation.initializeInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            object = this.mAttachInfo;
            if (object != null) {
                animation.setListenerHandler(((AttachInfo)object).mHandler);
            }
            this.onAnimationStart();
        }
        object = viewGroup.getChildTransformation();
        boolean bl2 = animation.getTransformation(l, (Transformation)object, 1.0f);
        if (bl && this.mAttachInfo.mApplicationScale != 1.0f) {
            if (viewGroup.mInvalidationTransformation == null) {
                viewGroup.mInvalidationTransformation = new Transformation();
            }
            object = viewGroup.mInvalidationTransformation;
            animation.getTransformation(l, (Transformation)object, 1.0f);
        }
        if (bl2) {
            if (!animation.willChangeBounds()) {
                if ((n & 144) == 128) {
                    viewGroup.mGroupFlags |= 4;
                } else if ((n & 4) == 0) {
                    viewGroup.mPrivateFlags |= 64;
                    viewGroup.invalidate(this.mLeft, this.mTop, this.mRight, this.mBottom);
                }
            } else {
                if (viewGroup.mInvalidateRegion == null) {
                    viewGroup.mInvalidateRegion = new RectF();
                }
                RectF rectF = viewGroup.mInvalidateRegion;
                animation.getInvalidateRegion(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, rectF, (Transformation)object);
                viewGroup.mPrivateFlags |= 64;
                int n2 = this.mLeft + (int)rectF.left;
                n = this.mTop + (int)rectF.top;
                viewGroup.invalidate(n2, n, (int)(rectF.width() + 0.5f) + n2, (int)(rectF.height() + 0.5f) + n);
            }
        }
        return bl2;
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void buildDrawingCacheImpl(boolean bl) {
        int n2;
        int n;
        long l;
        long l2;
        block27 : {
            block26 : {
                int n3;
                Object object;
                int n5;
                int n4;
                AttachInfo attachInfo;
                Object object2;
                block29 : {
                    block30 : {
                        int n7;
                        int n6;
                        block25 : {
                            block24 : {
                                block28 : {
                                    this.mCachingFailed = false;
                                    n6 = this.mRight - this.mLeft;
                                    n7 = this.mBottom - this.mTop;
                                    attachInfo = this.mAttachInfo;
                                    n4 = attachInfo != null && attachInfo.mScalingRequired ? 1 : 0;
                                    n = n6;
                                    n2 = n7;
                                    if (bl) {
                                        n = n6;
                                        n2 = n7;
                                        if (n4 != 0) {
                                            n = (int)((float)n6 * attachInfo.mApplicationScale + 0.5f);
                                            n2 = (int)((float)n7 * attachInfo.mApplicationScale + 0.5f);
                                        }
                                    }
                                    n7 = (n3 = this.mDrawingCacheBackgroundColor) == 0 && !this.isOpaque() ? 0 : 1;
                                    n6 = attachInfo != null && attachInfo.mUse32BitDrawingCache ? 1 : 0;
                                    n5 = n7 != 0 && n6 == 0 ? 2 : 4;
                                    l2 = n * n2 * n5;
                                    l = ViewConfiguration.get(this.mContext).getScaledMaximumDrawingCacheSize();
                                    if (n <= 0 || n2 <= 0 || l2 > l) break block27;
                                    n5 = 1;
                                    object2 = bl ? this.mDrawingCache : this.mUnscaledDrawingCache;
                                    if (object2 == null || ((Bitmap)object2).getWidth() != n) break block28;
                                    object = object2;
                                    if (((Bitmap)object2).getHeight() == n2) break block29;
                                }
                                if (n7 == 0) {
                                    n5 = this.mViewFlags;
                                    object = Bitmap.Config.ARGB_8888;
                                } else {
                                    object = n6 != 0 ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565;
                                }
                                if (object2 != null) {
                                    ((Bitmap)object2).recycle();
                                }
                                object = Bitmap.createBitmap(this.mResources.getDisplayMetrics(), n, n2, (Bitmap.Config)((Object)object));
                                object.setDensity(this.getResources().getDisplayMetrics().densityDpi);
                                if (!bl) break block24;
                                try {
                                    this.mDrawingCache = object;
                                    break block25;
                                }
                                catch (OutOfMemoryError outOfMemoryError) {
                                    break block26;
                                }
                            }
                            this.mUnscaledDrawingCache = object;
                        }
                        if (n7 == 0 || n6 == 0) break block30;
                        object.setHasAlpha(false);
                    }
                    n2 = 0;
                    if (n3 != 0) {
                        n2 = 1;
                    }
                    n5 = n2;
                }
                if (attachInfo != null) {
                    Canvas canvas = attachInfo.mCanvas;
                    object2 = canvas;
                    if (canvas == null) {
                        object2 = new Canvas();
                    }
                    ((Canvas)object2).setBitmap((Bitmap)object);
                    attachInfo.mCanvas = null;
                } else {
                    object2 = new Canvas((Bitmap)object);
                }
                if (n5 != 0) {
                    object.eraseColor(n3);
                }
                this.computeScroll();
                n2 = ((Canvas)object2).save();
                if (bl && n4 != 0) {
                    float f = attachInfo.mApplicationScale;
                    ((Canvas)object2).scale(f, f);
                }
                ((Canvas)object2).translate(-this.mScrollX, -this.mScrollY);
                this.mPrivateFlags |= 32;
                object = this.mAttachInfo;
                if (object == null || !((AttachInfo)object).mHardwareAccelerated || this.mLayerType != 0) {
                    this.mPrivateFlags |= 32768;
                }
                if (((n4 = this.mPrivateFlags) & 128) == 128) {
                    this.mPrivateFlags = n4 & -2097153;
                    this.dispatchDraw((Canvas)object2);
                    this.drawAutofilledHighlight((Canvas)object2);
                    object = this.mOverlay;
                    if (object != null && !((ViewOverlay)object).isEmpty()) {
                        this.mOverlay.getOverlayView().draw((Canvas)object2);
                    }
                } else {
                    this.draw((Canvas)object2);
                }
                ((Canvas)object2).restoreToCount(n2);
                ((Canvas)object2).setBitmap(null);
                if (attachInfo == null) return;
                attachInfo.mCanvas = object2;
                return;
                catch (OutOfMemoryError outOfMemoryError) {
                    // empty catch block
                }
            }
            if (bl) {
                this.mDrawingCache = null;
            } else {
                this.mUnscaledDrawingCache = null;
            }
            this.mCachingFailed = true;
            return;
        }
        if (n > 0 && n2 > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.getClass().getSimpleName());
            stringBuilder.append(" not displayed because it is too large to fit into a software layer (or drawing cache), needs ");
            stringBuilder.append(l2);
            stringBuilder.append(" bytes, only ");
            stringBuilder.append(l);
            stringBuilder.append(" available");
            Log.w(VIEW_LOG_TAG, stringBuilder.toString());
        }
        this.destroyDrawingCache();
        this.mCachingFailed = true;
    }

    private boolean canTakeFocus() {
        int n = this.mViewFlags;
        boolean bl = true;
        if ((n & 12) != 0 || (n & 1) != 1 || (n & 32) != 0 || !sCanFocusZeroSized && this.isLayoutValid() && !this.hasSize()) {
            bl = false;
        }
        return bl;
    }

    @UnsupportedAppUsage
    private void cancel(SendViewScrolledAccessibilityEvent sendViewScrolledAccessibilityEvent) {
        if (sendViewScrolledAccessibilityEvent != null && sendViewScrolledAccessibilityEvent.mIsPending) {
            this.removeCallbacks(sendViewScrolledAccessibilityEvent);
            sendViewScrolledAccessibilityEvent.reset();
            return;
        }
    }

    private void checkForLongClick(long l, float f, float f2, int n) {
        int n2 = this.mViewFlags;
        if ((n2 & 2097152) == 2097152 || (n2 & 1073741824) == 1073741824) {
            this.mHasPerformedLongPress = false;
            if (this.mPendingCheckForLongPress == null) {
                this.mPendingCheckForLongPress = new CheckForLongPress();
            }
            this.mPendingCheckForLongPress.setAnchor(f, f2);
            this.mPendingCheckForLongPress.rememberWindowAttachCount();
            this.mPendingCheckForLongPress.rememberPressedState();
            this.mPendingCheckForLongPress.setClassification(n);
            this.postDelayed(this.mPendingCheckForLongPress, l);
        }
    }

    private void cleanupDraw() {
        this.resetDisplayList();
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.cancelInvalidate(this);
        }
    }

    public static int combineMeasuredStates(int n, int n2) {
        return n | n2;
    }

    private final void debugDrawFocus(Canvas canvas) {
        if (this.isFocused()) {
            int n = this.dipsToPixels(8);
            int n2 = this.mScrollX;
            int n3 = this.mRight + n2 - this.mLeft;
            int n4 = this.mScrollY;
            int n5 = this.mBottom + n4 - this.mTop;
            Paint paint = View.getDebugPaint();
            paint.setColor(DEBUG_CORNERS_COLOR);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(n2, n4, n2 + n, n4 + n, paint);
            canvas.drawRect(n3 - n, n4, n3, n4 + n, paint);
            canvas.drawRect(n2, n5 - n, n2 + n, n5, paint);
            canvas.drawRect(n3 - n, n5 - n, n3, n5, paint);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawLine(n2, n4, n3, n5, paint);
            canvas.drawLine(n2, n5, n3, n4, paint);
        }
    }

    protected static String debugIndent(int n) {
        StringBuilder stringBuilder = new StringBuilder((n * 2 + 3) * 2);
        for (int i = 0; i < n * 2 + 3; ++i) {
            stringBuilder.append(' ');
            stringBuilder.append(' ');
        }
        return stringBuilder.toString();
    }

    private boolean dispatchGenericMotionEventInternal(MotionEvent motionEvent) {
        Object object = this.mListenerInfo;
        if (object != null && ((ListenerInfo)object).mOnGenericMotionListener != null && (this.mViewFlags & 32) == 0 && ((ListenerInfo)object).mOnGenericMotionListener.onGenericMotion(this, motionEvent)) {
            return true;
        }
        if (this.onGenericMotionEvent(motionEvent)) {
            return true;
        }
        int n = motionEvent.getActionButton();
        int n2 = motionEvent.getActionMasked();
        if (n2 != 11) {
            if (n2 == 12 && this.mInContextButtonPress && (n == 32 || n == 2)) {
                this.mInContextButtonPress = false;
                this.mIgnoreNextUpEvent = true;
            }
        } else if (this.isContextClickable() && !this.mInContextButtonPress && !this.mHasPerformedLongPress && (n == 32 || n == 2) && this.performContextClick(motionEvent.getX(), motionEvent.getY())) {
            this.mInContextButtonPress = true;
            this.setPressed(true, motionEvent.getX(), motionEvent.getY());
            this.removeTapCallback();
            this.removeLongPressCallback();
            return true;
        }
        if ((object = this.mInputEventConsistencyVerifier) != null) {
            ((InputEventConsistencyVerifier)object).onUnhandledEvent(motionEvent, 0);
        }
        return false;
    }

    private void dispatchProvideStructure(ViewStructure viewStructure, int n, int n2) {
        if (n == 1) {
            viewStructure.setAutofillId(this.getAutofillId());
            this.onProvideAutofillStructure(viewStructure, n2);
            this.onProvideAutofillVirtualStructure(viewStructure, n2);
        } else if (!this.isAssistBlocked()) {
            this.onProvideStructure(viewStructure);
            this.onProvideVirtualStructure(viewStructure);
        } else {
            viewStructure.setClassName(this.getAccessibilityClassName().toString());
            viewStructure.setAssistBlocked(true);
        }
    }

    private boolean dispatchTouchExplorationHoverEvent(MotionEvent motionEvent) {
        Object object = AccessibilityManager.getInstance(this.mContext);
        if (((AccessibilityManager)object).isEnabled() && ((AccessibilityManager)object).isTouchExplorationEnabled()) {
            boolean bl;
            boolean bl2 = this.mHoveringTouchDelegate;
            int n = motionEvent.getActionMasked();
            boolean bl3 = false;
            boolean bl4 = false;
            boolean bl5 = false;
            object = this.mTouchDelegate.getTouchDelegateInfo();
            for (int i = 0; i < ((AccessibilityNodeInfo.TouchDelegateInfo)object).getRegionCount(); ++i) {
                if (!((AccessibilityNodeInfo.TouchDelegateInfo)object).getRegionAt(i).contains((int)motionEvent.getX(), (int)motionEvent.getY())) continue;
                bl3 = true;
            }
            if (!bl2) {
                if ((n == 9 || n == 7) && !this.pointInHoveredChild(motionEvent) && bl3) {
                    this.mHoveringTouchDelegate = true;
                }
            } else if (n == 10 || n == 7 && (this.pointInHoveredChild(motionEvent) || !bl3)) {
                this.mHoveringTouchDelegate = false;
            }
            if (n != 7) {
                if (n != 9) {
                    if (n != 10) {
                        bl = bl4;
                    } else {
                        bl = bl4;
                        if (bl2) {
                            this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                            bl = bl4;
                        }
                    }
                } else {
                    bl = bl4;
                    if (!bl2) {
                        bl = bl4;
                        if (this.mHoveringTouchDelegate) {
                            bl = this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                        }
                    }
                }
            } else if (bl2 && this.mHoveringTouchDelegate) {
                bl = this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
            } else if (!bl2 && this.mHoveringTouchDelegate) {
                if (motionEvent.getHistorySize() != 0) {
                    motionEvent = MotionEvent.obtainNoHistory(motionEvent);
                }
                motionEvent.setAction(9);
                bl = this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                motionEvent.setAction(n);
                bl |= this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
            } else {
                bl = bl5;
                if (bl2) {
                    bl = bl5;
                    if (!this.mHoveringTouchDelegate) {
                        bl = motionEvent.isHoverExitPending();
                        motionEvent.setHoverExitPending(true);
                        this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                        if (motionEvent.getHistorySize() != 0) {
                            motionEvent = MotionEvent.obtainNoHistory(motionEvent);
                        }
                        motionEvent.setHoverExitPending(bl);
                        motionEvent.setAction(10);
                        this.mTouchDelegate.onTouchExplorationHoverEvent(motionEvent);
                        bl = bl4;
                    }
                }
            }
            return bl;
        }
        return false;
    }

    private void drawAutofilledHighlight(Canvas canvas) {
        Drawable drawable2;
        if (this.isAutofilled() && (drawable2 = this.getAutofilledDrawable()) != null) {
            drawable2.setBounds(0, 0, this.getWidth(), this.getHeight());
            drawable2.draw(canvas);
        }
    }

    @UnsupportedAppUsage
    private void drawBackground(Canvas canvas) {
        int n;
        Object object;
        int n2;
        Drawable drawable2 = this.mBackground;
        if (drawable2 == null) {
            return;
        }
        this.setBackgroundBounds();
        if (canvas.isHardwareAccelerated() && (object = this.mAttachInfo) != null && ((AttachInfo)object).mThreadedRenderer != null) {
            this.mBackgroundRenderNode = this.getDrawableRenderNode(drawable2, this.mBackgroundRenderNode);
            object = this.mBackgroundRenderNode;
            if (object != null && ((RenderNode)object).hasDisplayList()) {
                this.setBackgroundRenderNodeProperties((RenderNode)object);
                ((RecordingCanvas)canvas).drawRenderNode((RenderNode)object);
                return;
            }
        }
        if (((n2 = this.mScrollX) | (n = this.mScrollY)) == 0) {
            drawable2.draw(canvas);
        } else {
            canvas.translate(n2, n);
            drawable2.draw(canvas);
            canvas.translate(-n2, -n);
        }
    }

    private void drawDefaultFocusHighlight(Canvas canvas) {
        Drawable drawable2 = this.mDefaultFocusHighlight;
        if (drawable2 != null) {
            if (this.mDefaultFocusHighlightSizeChanged) {
                this.mDefaultFocusHighlightSizeChanged = false;
                int n = this.mScrollX;
                int n2 = this.mRight;
                int n3 = this.mLeft;
                int n4 = this.mScrollY;
                drawable2.setBounds(n, n4, n2 + n - n3, this.mBottom + n4 - this.mTop);
            }
            this.mDefaultFocusHighlight.draw(canvas);
        }
    }

    private static void dumpFlag(HashMap<String, String> hashMap, String string2, int n) {
        String string3 = String.format("%32s", Integer.toBinaryString(n)).replace('0', ' ');
        n = string2.indexOf(95);
        StringBuilder stringBuilder = new StringBuilder();
        String string4 = n > 0 ? string2.substring(0, n) : string2;
        stringBuilder.append(string4);
        stringBuilder.append(string3);
        stringBuilder.append(string2);
        string4 = stringBuilder.toString();
        stringBuilder = new StringBuilder();
        stringBuilder.append(string3);
        stringBuilder.append(" ");
        stringBuilder.append(string2);
        hashMap.put(string4, stringBuilder.toString());
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void dumpFlags() {
        HashMap<String, String> hashMap = Maps.newHashMap();
        try {
            for (Field field : View.class.getDeclaredFields()) {
                int n = field.getModifiers();
                if (!Modifier.isStatic(n) || !Modifier.isFinal(n)) continue;
                if (field.getType().equals(Integer.TYPE)) {
                    n = field.getInt(null);
                    View.dumpFlag(hashMap, field.getName(), n);
                    continue;
                }
                if (!field.getType().equals(int[].class)) continue;
                int[] arrn = (int[])field.get(null);
                for (n = 0; n < arrn.length; ++n) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(field.getName());
                    stringBuilder.append("[");
                    stringBuilder.append(n);
                    stringBuilder.append("]");
                    View.dumpFlag(hashMap, stringBuilder.toString(), arrn[n]);
                }
            }
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new RuntimeException(illegalAccessException);
        }
        ArrayList arrayList = Lists.newArrayList();
        arrayList.addAll(hashMap.keySet());
        Collections.sort(arrayList);
        Iterator iterator = arrayList.iterator();
        do {
            if (!iterator.hasNext()) {
                return;
            }
            Log.d(VIEW_LOG_TAG, (String)hashMap.get((String)iterator.next()));
        } while (true);
    }

    private View findAccessibilityFocusHost(boolean bl) {
        Object object;
        if (this.isAccessibilityFocusedViewOrHost()) {
            return this;
        }
        if (bl && (object = this.getViewRootImpl()) != null && (object = ((ViewRootImpl)object).getAccessibilityFocusedHost()) != null && ViewRootImpl.isViewDescendantOf((View)object, this)) {
            return object;
        }
        return null;
    }

    private FrameMetricsObserver findFrameMetricsObserver(Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener) {
        if (this.mFrameMetricsObservers != null) {
            for (int i = 0; i < this.mFrameMetricsObservers.size(); ++i) {
                FrameMetricsObserver frameMetricsObserver = this.mFrameMetricsObservers.get(i);
                if (frameMetricsObserver.mListener != onFrameMetricsAvailableListener) continue;
                return frameMetricsObserver;
            }
        }
        return null;
    }

    private View findLabelForView(View view, int n) {
        if (this.mMatchLabelForPredicate == null) {
            this.mMatchLabelForPredicate = new MatchLabelForPredicate();
        }
        this.mMatchLabelForPredicate.mLabeledId = n;
        return this.findViewByPredicateInsideOut(view, this.mMatchLabelForPredicate);
    }

    private View findViewInsideOutShouldExist(View view, int n) {
        if (this.mMatchIdPredicate == null) {
            this.mMatchIdPredicate = new MatchIdPredicate();
        }
        Object object = this.mMatchIdPredicate;
        ((MatchIdPredicate)object).mId = n;
        if ((view = view.findViewByPredicateInsideOut(this, (Predicate<View>)object)) == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("couldn't find view with id ");
            ((StringBuilder)object).append(n);
            Log.w(VIEW_LOG_TAG, ((StringBuilder)object).toString());
        }
        return view;
    }

    private boolean fitSystemWindowsInt(Rect rect) {
        if ((this.mViewFlags & 2) == 2) {
            Rect rect2;
            this.mUserPaddingStart = Integer.MIN_VALUE;
            this.mUserPaddingEnd = Integer.MIN_VALUE;
            Rect rect3 = rect2 = sThreadLocal.get();
            if (rect2 == null) {
                rect3 = new Rect();
                sThreadLocal.set(rect3);
            }
            boolean bl = this.computeFitSystemWindows(rect, rect3);
            this.mUserPaddingLeftInitial = rect3.left;
            this.mUserPaddingRightInitial = rect3.right;
            this.internalSetPadding(rect3.left, rect3.top, rect3.right, rect3.bottom);
            return bl;
        }
        return false;
    }

    public static int generateViewId() {
        int n;
        int n2;
        do {
            int n3;
            n = sNextGeneratedId.get();
            n2 = n3 = n + 1;
            if (n3 <= 16777215) continue;
            n2 = 1;
        } while (!sNextGeneratedId.compareAndSet(n, n2));
        return n;
    }

    private ContentCaptureSession getAndCacheContentCaptureSession() {
        Object object = this.mContentCaptureSession;
        if (object != null) {
            return object;
        }
        object = null;
        ViewParent viewParent = this.mParent;
        if (viewParent instanceof View) {
            object = ((View)((Object)viewParent)).getContentCaptureSession();
        }
        if (object == null) {
            object = this.mContext.getSystemService(ContentCaptureManager.class);
            object = object == null ? null : ((ContentCaptureManager)object).getMainContentCaptureSession();
            return object;
        }
        return object;
    }

    private static SparseArray<String> getAttributeMap() {
        if (mAttributeMap == null) {
            mAttributeMap = new SparseArray();
        }
        return mAttributeMap;
    }

    private AutofillManager getAutofillManager() {
        return this.mContext.getSystemService(AutofillManager.class);
    }

    private Drawable getAutofilledDrawable() {
        Object object = this.mAttachInfo;
        if (object == null) {
            return null;
        }
        if (((AttachInfo)object).mAutofilledDrawable == null) {
            object = this.getRootView().getContext();
            TypedArray typedArray = ((Context)object).getTheme().obtainStyledAttributes(AUTOFILL_HIGHLIGHT_ATTR);
            int n = typedArray.getResourceId(0, 0);
            this.mAttachInfo.mAutofilledDrawable = ((Context)object).getDrawable(n);
            typedArray.recycle();
        }
        return this.mAttachInfo.mAutofilledDrawable;
    }

    static Paint getDebugPaint() {
        if (sDebugPaint == null) {
            sDebugPaint = new Paint();
            sDebugPaint.setAntiAlias(false);
        }
        return sDebugPaint;
    }

    private Drawable getDefaultFocusHighlightDrawable() {
        Object object;
        if (this.mDefaultFocusHighlightCache == null && (object = this.mContext) != null) {
            object = ((Context)object).obtainStyledAttributes(new int[]{16843534});
            this.mDefaultFocusHighlightCache = ((TypedArray)object).getDrawable(0);
            ((TypedArray)object).recycle();
        }
        return this.mDefaultFocusHighlightCache;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getDefaultSize(int n, int n2) {
        int n3 = n;
        int n4 = MeasureSpec.getMode(n2);
        n2 = MeasureSpec.getSize(n2);
        if (n4 == Integer.MIN_VALUE) return n2;
        if (n4 == 0) return n;
        if (n4 == 1073741824) return n2;
        return n3;
    }

    private RenderNode getDrawableRenderNode(Drawable drawable2, RenderNode object) {
        RenderNode renderNode = object;
        if (object == null) {
            renderNode = RenderNode.create(drawable2.getClass().getName(), new ViewAnimationHostBridge(this));
            renderNode.setUsageHint(1);
        }
        Rect rect = drawable2.getBounds();
        object = renderNode.beginRecording(rect.width(), rect.height());
        ((Canvas)object).translate(-rect.left, -rect.top);
        drawable2.draw((Canvas)object);
        renderNode.setLeftTopRightBottom(rect.left, rect.top, rect.right, rect.bottom);
        renderNode.setProjectBackwards(drawable2.isProjected());
        renderNode.setProjectionReceiver(true);
        renderNode.setClipToBounds(false);
        return renderNode;
        finally {
            renderNode.endRecording();
        }
    }

    private float getFinalAlpha() {
        TransformationInfo transformationInfo = this.mTransformationInfo;
        if (transformationInfo != null) {
            return transformationInfo.mAlpha * this.mTransformationInfo.mTransitionAlpha;
        }
        return 1.0f;
    }

    private int getFocusableAttribute(TypedArray typedArray) {
        TypedValue typedValue = new TypedValue();
        if (typedArray.getValue(19, typedValue)) {
            if (typedValue.type == 18) {
                int n = typedValue.data == 0 ? 0 : 1;
                return n;
            }
            return typedValue.data;
        }
        return 16;
    }

    private void getHorizontalScrollBarBounds(Rect rect, Rect rect2) {
        if (rect == null) {
            rect = rect2;
        }
        if (rect == null) {
            return;
        }
        int n = this.mViewFlags;
        int n2 = 0;
        n = (n & 33554432) == 0 ? -1 : 0;
        int n3 = this.isVerticalScrollBarEnabled() && !this.isVerticalScrollBarHidden() ? 1 : 0;
        int n4 = this.getHorizontalScrollbarHeight();
        n3 = n3 != 0 ? this.getVerticalScrollbarWidth() : n2;
        int n5 = this.mRight;
        int n6 = this.mLeft;
        n2 = this.mBottom - this.mTop;
        rect.top = this.mScrollY + n2 - n4 - (this.mUserPaddingBottom & n);
        int n7 = this.mScrollX;
        rect.left = (this.mPaddingLeft & n) + n7;
        rect.right = n7 + (n5 - n6) - (this.mUserPaddingRight & n) - n3;
        rect.bottom = rect.top + n4;
        if (rect2 == null) {
            return;
        }
        if (rect2 != rect) {
            rect2.set(rect);
        }
        n = this.mScrollCache.scrollBarMinTouchTarget;
        if (rect2.height() < n) {
            n3 = (n - rect2.height()) / 2;
            rect2.bottom = Math.min(rect2.bottom + n3, this.mScrollY + n2);
            rect2.top = rect2.bottom - n;
        }
        if (rect2.width() < n) {
            n3 = (n - rect2.width()) / 2;
            rect2.left -= n3;
            rect2.right = rect2.left + n;
        }
    }

    private View getProjectionReceiver() {
        for (ViewParent viewParent = this.getParent(); viewParent != null && viewParent instanceof View; viewParent = viewParent.getParent()) {
            View view = (View)((Object)viewParent);
            if (!view.isProjectionReceiver()) continue;
            return view;
        }
        return null;
    }

    private void getRoundVerticalScrollBarBounds(Rect rect) {
        int n = this.mRight;
        int n2 = this.mLeft;
        int n3 = this.mBottom;
        int n4 = this.mTop;
        rect.left = this.mScrollX;
        rect.top = this.mScrollY;
        rect.right = rect.left + (n - n2);
        rect.bottom = this.mScrollY + (n3 - n4);
    }

    private HandlerActionQueue getRunQueue() {
        if (this.mRunQueue == null) {
            this.mRunQueue = new HandlerActionQueue();
        }
        return this.mRunQueue;
    }

    @UnsupportedAppUsage
    private ScrollabilityCache getScrollCache() {
        this.initScrollCache();
        return this.mScrollCache;
    }

    private void getStraightVerticalScrollBarBounds(Rect rect, Rect rect2) {
        int n;
        if (rect == null) {
            rect = rect2;
        }
        if (rect == null) {
            return;
        }
        int n2 = (this.mViewFlags & 33554432) == 0 ? -1 : 0;
        int n3 = this.getVerticalScrollbarWidth();
        int n4 = n = this.mVerticalScrollbarPosition;
        if (n == 0) {
            n4 = this.isLayoutRtl() ? 1 : 2;
        }
        n = this.mRight - this.mLeft;
        int n5 = this.mBottom;
        int n6 = this.mTop;
        rect.left = n4 != 1 ? this.mScrollX + n - n3 - (this.mUserPaddingRight & n2) : this.mScrollX + (this.mUserPaddingLeft & n2);
        rect.top = this.mScrollY + (this.mPaddingTop & n2);
        rect.right = rect.left + n3;
        rect.bottom = this.mScrollY + (n5 - n6) - (this.mUserPaddingBottom & n2);
        if (rect2 == null) {
            return;
        }
        if (rect2 != rect) {
            rect2.set(rect);
        }
        n2 = this.mScrollCache.scrollBarMinTouchTarget;
        if (rect2.width() < n2) {
            n3 = (n2 - rect2.width()) / 2;
            if (n4 == 2) {
                rect2.right = Math.min(rect2.right + n3, this.mScrollX + n);
                rect2.left = rect2.right - n2;
            } else {
                rect2.left = Math.max(rect2.left + n3, this.mScrollX);
                rect2.right = rect2.left + n2;
            }
        }
        if (rect2.height() < n2) {
            n4 = (n2 - rect2.height()) / 2;
            rect2.top -= n4;
            rect2.bottom = rect2.top + n2;
        }
    }

    private void getVerticalScrollBarBounds(Rect rect, Rect rect2) {
        if (this.mRoundScrollbarRenderer == null) {
            this.getStraightVerticalScrollBarBounds(rect, rect2);
        } else {
            if (rect == null) {
                rect = rect2;
            }
            this.getRoundVerticalScrollBarBounds(rect);
        }
    }

    private void handleTooltipUp() {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo != null && tooltipInfo.mTooltipPopup != null) {
            this.removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
            this.postDelayed(this.mTooltipInfo.mHideTooltipRunnable, ViewConfiguration.getLongPressTooltipHideTimeout());
            return;
        }
    }

    private boolean hasAncestorThatBlocksDescendantFocus() {
        boolean bl = this.isFocusableInTouchMode();
        ViewParent viewParent = this.mParent;
        while (viewParent instanceof ViewGroup) {
            if (((ViewGroup)(viewParent = (ViewGroup)viewParent)).getDescendantFocusability() != 393216 && (bl || !((ViewGroup)viewParent).shouldBlockFocusForTouchscreen())) {
                viewParent = ((View)((Object)viewParent)).getParent();
                continue;
            }
            return true;
        }
        return false;
    }

    private boolean hasListenersForAccessibility() {
        ListenerInfo listenerInfo = this.getListenerInfo();
        boolean bl = this.mTouchDelegate != null || listenerInfo.mOnKeyListener != null || listenerInfo.mOnTouchListener != null || listenerInfo.mOnGenericMotionListener != null || listenerInfo.mOnHoverListener != null || listenerInfo.mOnDragListener != null;
        return bl;
    }

    private boolean hasParentWantsFocus() {
        ViewParent viewParent = this.mParent;
        while (viewParent instanceof ViewGroup) {
            viewParent = (ViewGroup)viewParent;
            if ((((ViewGroup)viewParent).mPrivateFlags & 1) != 0) {
                return true;
            }
            viewParent = ((ViewGroup)viewParent).mParent;
        }
        return false;
    }

    private boolean hasPendingLongPressCallback() {
        if (this.mPendingCheckForLongPress == null) {
            return false;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return false;
        }
        return attachInfo.mHandler.hasCallbacks(this.mPendingCheckForLongPress);
    }

    @UnsupportedAppUsage
    private boolean hasRtlSupport() {
        return this.mContext.getApplicationInfo().hasRtlSupport();
    }

    private boolean hasSize() {
        boolean bl = this.mBottom > this.mTop && this.mRight > this.mLeft;
        return bl;
    }

    public static View inflate(Context context, int n, ViewGroup viewGroup) {
        return LayoutInflater.from(context).inflate(n, viewGroup);
    }

    private void initScrollCache() {
        if (this.mScrollCache == null) {
            this.mScrollCache = new ScrollabilityCache(ViewConfiguration.get(this.mContext), this);
        }
    }

    private boolean initialAwakenScrollBars() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        boolean bl = true;
        if (scrollabilityCache == null || !this.awakenScrollBars(scrollabilityCache.scrollBarDefaultDelayBeforeFade * 4, true)) {
            bl = false;
        }
        return bl;
    }

    private void initializeScrollBarDrawable() {
        this.initScrollCache();
        if (this.mScrollCache.scrollBar == null) {
            this.mScrollCache.scrollBar = new ScrollBarDrawable();
            this.mScrollCache.scrollBar.setState(this.getDrawableState());
            this.mScrollCache.scrollBar.setCallback(this);
        }
    }

    private void initializeScrollIndicatorsInternal() {
        if (this.mScrollIndicatorDrawable == null) {
            this.mScrollIndicatorDrawable = this.mContext.getDrawable(17303389);
        }
    }

    private boolean isAccessibilityPane() {
        boolean bl = this.mAccessibilityPaneTitle != null;
        return bl;
    }

    private boolean isAutofillable() {
        int n = this.getAutofillType();
        boolean bl = false;
        if (n == 0) {
            return false;
        }
        if (!this.isImportantForAutofill()) {
            Object object = this.mContext.getAutofillOptions();
            if (object != null && ((AutofillOptions)object).isAugmentedAutofillEnabled(this.mContext)) {
                object = this.getAutofillManager();
                if (object == null) {
                    return false;
                }
                ((AutofillManager)object).notifyViewEnteredForAugmentedAutofill(this);
            } else {
                return false;
            }
        }
        if (this.getAutofillViewId() > 1073741823) {
            bl = true;
        }
        return bl;
    }

    public static boolean isDefaultFocusHighlightEnabled() {
        return sUseDefaultFocusHighlight;
    }

    private boolean isHoverable() {
        int n = this.mViewFlags;
        boolean bl = false;
        if ((n & 32) == 32) {
            return false;
        }
        if ((n & 16384) == 16384 || (n & 2097152) == 2097152 || (n & 8388608) == 8388608) {
            bl = true;
        }
        return bl;
    }

    public static boolean isLayoutModeOptical(Object object) {
        boolean bl = object instanceof ViewGroup && ((ViewGroup)object).isLayoutModeOptical();
        return bl;
    }

    private boolean isOnHorizontalScrollbarThumb(float f, float f2) {
        if (this.mScrollCache != null && this.isHorizontalScrollBarEnabled()) {
            int n;
            int n2 = this.computeHorizontalScrollRange();
            if (n2 > (n = this.computeHorizontalScrollExtent())) {
                f += (float)this.getScrollX();
                f2 += (float)this.getScrollY();
                Rect rect = this.mScrollCache.mScrollBarBounds;
                Rect rect2 = this.mScrollCache.mScrollBarTouchBounds;
                this.getHorizontalScrollBarBounds(rect, rect2);
                int n3 = this.computeHorizontalScrollOffset();
                int n4 = ScrollBarUtils.getThumbLength(rect.width(), rect.height(), n, n2);
                n2 = ScrollBarUtils.getThumbOffset(rect.width(), n4, n, n2, n3);
                n2 = rect.left + n2;
                n3 = Math.max(this.mScrollCache.scrollBarMinTouchTarget - n4, 0) / 2;
                if (f >= (float)(n2 - n3) && f <= (float)(n2 + n4 + n3) && f2 >= (float)rect2.top && f2 <= (float)rect2.bottom) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean isOnVerticalScrollbarThumb(float f, float f2) {
        if (this.mScrollCache != null && this.isVerticalScrollBarEnabled() && !this.isVerticalScrollBarHidden()) {
            int n;
            int n2 = this.computeVerticalScrollRange();
            if (n2 > (n = this.computeVerticalScrollExtent())) {
                f += (float)this.getScrollX();
                f2 += (float)this.getScrollY();
                Rect rect = this.mScrollCache.mScrollBarBounds;
                Rect rect2 = this.mScrollCache.mScrollBarTouchBounds;
                this.getVerticalScrollBarBounds(rect, rect2);
                int n3 = this.computeVerticalScrollOffset();
                int n4 = ScrollBarUtils.getThumbLength(rect.height(), rect.width(), n, n2);
                n2 = ScrollBarUtils.getThumbOffset(rect.height(), n4, n, n2, n3);
                n3 = rect.top + n2;
                n2 = Math.max(this.mScrollCache.scrollBarMinTouchTarget - n4, 0) / 2;
                if (f >= (float)rect2.left && f <= (float)rect2.right && f2 >= (float)(n3 - n2) && f2 <= (float)(n3 + n4 + n2)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    private boolean isProjectionReceiver() {
        boolean bl = this.mBackground != null;
        return bl;
    }

    private boolean isRtlCompatibilityMode() {
        boolean bl = this.getContext().getApplicationInfo().targetSdkVersion < 17 || !this.hasRtlSupport();
        return bl;
    }

    private static boolean isViewIdGenerated(int n) {
        boolean bl = (-16777216 & n) == 0 && (16777215 & n) != 0;
        return bl;
    }

    static /* synthetic */ boolean lambda$findUserSetNextKeyboardNavigationCluster$0(int n, View view) {
        boolean bl = view.mNextClusterForwardId == n;
        return bl;
    }

    public static /* synthetic */ boolean lambda$llq76MkPXP4bNcb9oJt_msw0fnQ(View view) {
        return view.showHoverTooltip();
    }

    protected static int[] mergeDrawableStates(int[] arrn, int[] arrn2) {
        int n;
        for (n = arrn.length - 1; n >= 0 && arrn[n] == 0; --n) {
        }
        System.arraycopy(arrn2, 0, arrn, n + 1, arrn2.length);
        return arrn;
    }

    private boolean needRtlPropertiesResolution() {
        boolean bl = (this.mPrivateFlags2 & 1610678816) != 1610678816;
        return bl;
    }

    private void notifyAutofillManagerOnClick() {
        if ((this.mPrivateFlags & 536870912) != 0) {
            try {
                this.getAutofillManager().notifyViewClicked(this);
            }
            finally {
                this.mPrivateFlags = -536870913 & this.mPrivateFlags;
            }
        }
    }

    private void notifyFocusChangeToInputMethodManager(boolean bl) {
        InputMethodManager inputMethodManager = this.getContext().getSystemService(InputMethodManager.class);
        if (inputMethodManager == null) {
            return;
        }
        if (bl) {
            inputMethodManager.focusIn(this);
        } else {
            inputMethodManager.focusOut(this);
        }
    }

    private static int numViewsForAccessibility(View view) {
        if (view != null) {
            if (view.includeForAccessibility()) {
                return 1;
            }
            if (view instanceof ViewGroup) {
                return ((ViewGroup)view).getNumChildrenForAccessibility();
            }
        }
        return 0;
    }

    private void onDrawScrollIndicators(Canvas canvas) {
        int n;
        if ((this.mPrivateFlags3 & 16128) == 0) {
            return;
        }
        Drawable drawable2 = this.mScrollIndicatorDrawable;
        if (drawable2 == null) {
            return;
        }
        int n2 = drawable2.getIntrinsicHeight();
        int n3 = drawable2.getIntrinsicWidth();
        Rect rect = this.mAttachInfo.mTmpInvalRect;
        this.getScrollIndicatorBounds(rect);
        if ((this.mPrivateFlags3 & 256) != 0 && this.canScrollVertically(-1)) {
            drawable2.setBounds(rect.left, rect.top, rect.right, rect.top + n2);
            drawable2.draw(canvas);
        }
        if ((this.mPrivateFlags3 & 512) != 0 && this.canScrollVertically(1)) {
            drawable2.setBounds(rect.left, rect.bottom - n2, rect.right, rect.bottom);
            drawable2.draw(canvas);
        }
        if (this.getLayoutDirection() == 1) {
            n = 8192;
            n2 = 4096;
        } else {
            n = 4096;
            n2 = 8192;
        }
        if ((this.mPrivateFlags3 & (n | 1024)) != 0 && this.canScrollHorizontally(-1)) {
            drawable2.setBounds(rect.left, rect.top, rect.left + n3, rect.bottom);
            drawable2.draw(canvas);
        }
        if ((this.mPrivateFlags3 & (n2 | 2048)) != 0 && this.canScrollHorizontally(1)) {
            drawable2.setBounds(rect.right - n3, rect.top, rect.right, rect.bottom);
            drawable2.draw(canvas);
        }
    }

    private void onProvideVirtualStructureCompat(ViewStructure viewStructure, boolean bl) {
        AccessibilityNodeProvider accessibilityNodeProvider = this.getAccessibilityNodeProvider();
        if (accessibilityNodeProvider != null) {
            Object object;
            if (bl && Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("onProvideVirtualStructureCompat() for ");
                ((StringBuilder)object).append(this);
                Log.v(AUTOFILL_LOG_TAG, ((StringBuilder)object).toString());
            }
            object = this.createAccessibilityNodeInfo();
            viewStructure.setChildCount(1);
            this.populateVirtualStructure(viewStructure.newChild(0), accessibilityNodeProvider, (AccessibilityNodeInfo)object, bl);
            ((AccessibilityNodeInfo)object).recycle();
        }
    }

    private boolean performClickInternal() {
        this.notifyAutofillManagerOnClick();
        return this.performClick();
    }

    private boolean performLongClickInternal(float f, float f2) {
        this.sendAccessibilityEvent(2);
        boolean bl = false;
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean bl2 = bl;
        if (listenerInfo != null) {
            bl2 = bl;
            if (listenerInfo.mOnLongClickListener != null) {
                bl2 = listenerInfo.mOnLongClickListener.onLongClick(this);
            }
        }
        bl = bl2;
        if (!bl2) {
            boolean bl3 = !Float.isNaN(f) && !Float.isNaN(f2);
            bl2 = bl3 ? this.showContextMenu(f, f2) : this.showContextMenu();
            bl = bl2;
        }
        bl2 = bl;
        if ((this.mViewFlags & 1073741824) == 1073741824) {
            bl2 = bl;
            if (!bl) {
                bl2 = this.showLongClickTooltip((int)f, (int)f2);
            }
        }
        if (bl2) {
            this.performHapticFeedback(0);
        }
        return bl2;
    }

    private void populateAccessibilityNodeInfoDrawingOrderInParent(AccessibilityNodeInfo accessibilityNodeInfo) {
        int n;
        if ((this.mPrivateFlags & 16) == 0) {
            accessibilityNodeInfo.setDrawingOrder(0);
            return;
        }
        int n2 = 1;
        View view = this;
        ViewParent viewParent = this.getParentForAccessibility();
        do {
            n = n2;
            if (view == viewParent) break;
            ViewParent viewParent2 = view.getParent();
            if (!(viewParent2 instanceof ViewGroup)) {
                n = 0;
                break;
            }
            ViewGroup viewGroup = (ViewGroup)viewParent2;
            int n3 = viewGroup.getChildCount();
            int n4 = n2;
            if (n3 > 1) {
                int n5;
                ArrayList<View> arrayList = viewGroup.buildOrderedChildList();
                if (arrayList != null) {
                    n5 = arrayList.indexOf(view);
                    for (n = 0; n < n5; ++n) {
                        n2 += View.numViewsForAccessibility((View)arrayList.get(n));
                    }
                    n4 = n2;
                } else {
                    n = viewGroup.indexOfChild(view);
                    boolean bl = viewGroup.isChildrenDrawingOrderEnabled();
                    if (n >= 0 && bl) {
                        n = viewGroup.getChildDrawingOrder(n3, n);
                    }
                    int n6 = bl ? n3 : n;
                    n4 = n2;
                    if (n != 0) {
                        n5 = 0;
                        do {
                            n4 = n2;
                            if (n5 >= n6) break;
                            int n7 = bl ? viewGroup.getChildDrawingOrder(n3, n5) : n5;
                            n4 = n2;
                            if (n7 < n) {
                                n4 = n2 + View.numViewsForAccessibility(viewGroup.getChildAt(n5));
                            }
                            ++n5;
                            n2 = n4;
                        } while (true);
                    }
                }
            }
            view = (View)((Object)viewParent2);
            n2 = n4;
        } while (true);
        accessibilityNodeInfo.setDrawingOrder(n);
    }

    private void populateVirtualStructure(ViewStructure viewStructure, AccessibilityNodeProvider accessibilityNodeProvider, AccessibilityNodeInfo accessibilityNodeInfo, boolean bl) {
        int n;
        int n2 = AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId());
        Object object = accessibilityNodeInfo.getViewIdResourceName();
        Object object2 = null;
        viewStructure.setId(n2, null, null, (String)object);
        object = viewStructure.getTempRect();
        accessibilityNodeInfo.getBoundsInParent((Rect)object);
        viewStructure.setDimens(((Rect)object).left, ((Rect)object).top, 0, 0, ((Rect)object).width(), ((Rect)object).height());
        viewStructure.setVisibility(0);
        viewStructure.setEnabled(accessibilityNodeInfo.isEnabled());
        if (accessibilityNodeInfo.isClickable()) {
            viewStructure.setClickable(true);
        }
        if (accessibilityNodeInfo.isFocusable()) {
            viewStructure.setFocusable(true);
        }
        if (accessibilityNodeInfo.isFocused()) {
            viewStructure.setFocused(true);
        }
        if (accessibilityNodeInfo.isAccessibilityFocused()) {
            viewStructure.setAccessibilityFocused(true);
        }
        if (accessibilityNodeInfo.isSelected()) {
            viewStructure.setSelected(true);
        }
        if (accessibilityNodeInfo.isLongClickable()) {
            viewStructure.setLongClickable(true);
        }
        if (accessibilityNodeInfo.isCheckable()) {
            viewStructure.setCheckable(true);
            if (accessibilityNodeInfo.isChecked()) {
                viewStructure.setChecked(true);
            }
        }
        if (accessibilityNodeInfo.isContextClickable()) {
            viewStructure.setContextClickable(true);
        }
        if (bl) {
            viewStructure.setAutofillId(new AutofillId(this.getAutofillId(), AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getSourceNodeId())));
        }
        if ((object = accessibilityNodeInfo.getClassName()) != null) {
            object2 = object.toString();
        }
        viewStructure.setClassName((String)object2);
        viewStructure.setContentDescription(accessibilityNodeInfo.getContentDescription());
        if (bl) {
            n2 = accessibilityNodeInfo.getMaxTextLength();
            if (n2 != -1) {
                viewStructure.setMaxTextLength(n2);
            }
            viewStructure.setHint(accessibilityNodeInfo.getHintText());
        }
        n2 = (object2 = accessibilityNodeInfo.getText()) == null && accessibilityNodeInfo.getError() == null ? 0 : 1;
        if (n2 != 0) {
            viewStructure.setText((CharSequence)object2, accessibilityNodeInfo.getTextSelectionStart(), accessibilityNodeInfo.getTextSelectionEnd());
        }
        if (bl) {
            if (accessibilityNodeInfo.isEditable()) {
                viewStructure.setDataIsSensitive(true);
                if (n2 != 0) {
                    viewStructure.setAutofillType(1);
                    viewStructure.setAutofillValue(AutofillValue.forText((CharSequence)object2));
                }
                n2 = n = accessibilityNodeInfo.getInputType();
                if (n == 0) {
                    n2 = n;
                    if (accessibilityNodeInfo.isPassword()) {
                        n2 = 129;
                    }
                }
                viewStructure.setInputType(n2);
            } else {
                viewStructure.setDataIsSensitive(false);
            }
        }
        if ((n = accessibilityNodeInfo.getChildCount()) > 0) {
            viewStructure.setChildCount(n);
            for (n2 = 0; n2 < n; ++n2) {
                if (AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getChildNodeIds().get(n2)) == -1) {
                    Log.e(VIEW_LOG_TAG, "Virtual view pointing to its host. Ignoring");
                    continue;
                }
                object2 = accessibilityNodeProvider.createAccessibilityNodeInfo(AccessibilityNodeInfo.getVirtualDescendantId(accessibilityNodeInfo.getChildId(n2)));
                this.populateVirtualStructure(viewStructure.newChild(n2), accessibilityNodeProvider, (AccessibilityNodeInfo)object2, bl);
                ((AccessibilityNodeInfo)object2).recycle();
            }
        }
    }

    private void postSendViewScrolledAccessibilityEventCallback(int n, int n2) {
        if (this.mSendViewScrolledAccessibilityEvent == null) {
            this.mSendViewScrolledAccessibilityEvent = new SendViewScrolledAccessibilityEvent();
        }
        this.mSendViewScrolledAccessibilityEvent.post(n, n2);
    }

    private static String printFlags(int n) {
        CharSequence charSequence = "";
        int n2 = 0;
        if ((n & 1) == 1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("");
            ((StringBuilder)charSequence).append("TAKES_FOCUS");
            charSequence = ((StringBuilder)charSequence).toString();
            n2 = 0 + 1;
        }
        if ((n &= 12) != 4) {
            if (n == 8) {
                CharSequence charSequence2 = charSequence;
                if (n2 > 0) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    ((StringBuilder)charSequence2).append(" ");
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append("GONE");
                charSequence = ((StringBuilder)charSequence).toString();
            }
        } else {
            CharSequence charSequence3 = charSequence;
            if (n2 > 0) {
                charSequence3 = new StringBuilder();
                ((StringBuilder)charSequence3).append((String)charSequence);
                ((StringBuilder)charSequence3).append(" ");
                charSequence3 = ((StringBuilder)charSequence3).toString();
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence3);
            ((StringBuilder)charSequence).append("INVISIBLE");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        return charSequence;
    }

    private static String printPrivateFlags(int n) {
        CharSequence charSequence;
        CharSequence charSequence2 = "";
        int n2 = 0;
        if ((n & 1) == 1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append("");
            ((StringBuilder)charSequence).append("WANTS_FOCUS");
            charSequence2 = ((StringBuilder)charSequence).toString();
            n2 = 0 + 1;
        }
        charSequence = charSequence2;
        int n3 = n2;
        if ((n & 2) == 2) {
            charSequence = charSequence2;
            if (n2 > 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append(" ");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("FOCUSED");
            charSequence = ((StringBuilder)charSequence2).toString();
            n3 = n2 + 1;
        }
        charSequence2 = charSequence;
        n2 = n3;
        if ((n & 4) == 4) {
            charSequence2 = charSequence;
            if (n3 > 0) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(" ");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("SELECTED");
            charSequence2 = ((StringBuilder)charSequence).toString();
            n2 = n3 + 1;
        }
        CharSequence charSequence3 = charSequence2;
        n3 = n2;
        if ((n & 8) == 8) {
            charSequence = charSequence2;
            if (n2 > 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence2);
                ((StringBuilder)charSequence).append(" ");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("IS_ROOT_NAMESPACE");
            charSequence3 = ((StringBuilder)charSequence2).toString();
            n3 = n2 + 1;
        }
        charSequence = charSequence3;
        n2 = n3;
        if ((n & 16) == 16) {
            charSequence = charSequence3;
            if (n3 > 0) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append((String)charSequence3);
                ((StringBuilder)charSequence).append(" ");
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)charSequence);
            ((StringBuilder)charSequence2).append("HAS_BOUNDS");
            charSequence = ((StringBuilder)charSequence2).toString();
            n2 = n3 + 1;
        }
        charSequence2 = charSequence;
        if ((n & 32) == 32) {
            charSequence2 = charSequence;
            if (n2 > 0) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(" ");
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append("DRAWN");
            charSequence2 = ((StringBuilder)charSequence).toString();
        }
        return charSequence2;
    }

    private void rebuildOutline() {
        Object object = this.mAttachInfo;
        if (object == null) {
            return;
        }
        if (this.mOutlineProvider == null) {
            this.mRenderNode.setOutline(null);
        } else {
            object = ((AttachInfo)object).mTmpOutline;
            ((Outline)object).setEmpty();
            ((Outline)object).setAlpha(1.0f);
            this.mOutlineProvider.getOutline(this, (Outline)object);
            this.mRenderNode.setOutline((Outline)object);
        }
    }

    private void recordGestureClassification(int n) {
        if (n == 0) {
            return;
        }
        StatsLog.write(177, this.getClass().getName(), n);
    }

    private void registerPendingFrameMetricsObservers() {
        if (this.mFrameMetricsObservers != null) {
            ThreadedRenderer threadedRenderer = this.getThreadedRenderer();
            if (threadedRenderer != null) {
                Iterator<FrameMetricsObserver> iterator = this.mFrameMetricsObservers.iterator();
                while (iterator.hasNext()) {
                    threadedRenderer.addFrameMetricsObserver(iterator.next());
                }
            } else {
                Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
            }
        }
    }

    private void removeLongPressCallback() {
        CheckForLongPress checkForLongPress = this.mPendingCheckForLongPress;
        if (checkForLongPress != null) {
            this.removeCallbacks(checkForLongPress);
        }
    }

    @UnsupportedAppUsage
    private void removePerformClickCallback() {
        PerformClick performClick = this.mPerformClick;
        if (performClick != null) {
            this.removeCallbacks(performClick);
        }
    }

    private void removeTapCallback() {
        CheckForTap checkForTap = this.mPendingCheckForTap;
        if (checkForTap != null) {
            this.mPrivateFlags &= -33554433;
            this.removeCallbacks(checkForTap);
        }
    }

    private void removeUnsetPressCallback() {
        if ((this.mPrivateFlags & 16384) != 0 && this.mUnsetPressedState != null) {
            this.setPressed(false);
            this.removeCallbacks(this.mUnsetPressedState);
        }
    }

    private boolean requestFocusNoSearch(int n, Rect rect) {
        if (!this.canTakeFocus()) {
            return false;
        }
        if (this.isInTouchMode() && 262144 != (this.mViewFlags & 262144)) {
            return false;
        }
        if (this.hasAncestorThatBlocksDescendantFocus()) {
            return false;
        }
        if (!this.isLayoutValid()) {
            this.mPrivateFlags |= 1;
        } else {
            this.clearParentsWantFocus();
        }
        this.handleFocusGainInternal(n, rect);
        return true;
    }

    @UnsupportedAppUsage
    private void resetDisplayList() {
        this.mRenderNode.discardDisplayList();
        RenderNode renderNode = this.mBackgroundRenderNode;
        if (renderNode != null) {
            renderNode.discardDisplayList();
        }
    }

    private void resetPressedState() {
        if ((this.mViewFlags & 32) == 32) {
            return;
        }
        if (this.isPressed()) {
            this.setPressed(false);
            if (!this.mHasPerformedLongPress) {
                this.removeLongPressCallback();
            }
        }
    }

    public static int resolveSize(int n, int n2) {
        return View.resolveSizeAndState(n, n2, 0) & 16777215;
    }

    public static int resolveSizeAndState(int n, int n2, int n3) {
        block1 : {
            block0 : {
                int n4 = MeasureSpec.getMode(n2);
                n2 = MeasureSpec.getSize(n2);
                if (n4 == Integer.MIN_VALUE) break block0;
                if (n4 != 1073741824) break block1;
                n = n2;
                break block1;
            }
            if (n2 >= n) break block1;
            n = 16777216 | n2;
        }
        return -16777216 & n3 | n;
    }

    private void retrieveExplicitStyle(Resources.Theme theme, AttributeSet attributeSet) {
        if (!sDebugViewAttributes) {
            return;
        }
        this.mExplicitStyle = theme.getExplicitStyle(attributeSet);
    }

    private static float sanitizeFloatPropertyValue(float f, String string2) {
        return View.sanitizeFloatPropertyValue(f, string2, -3.4028235E38f, Float.MAX_VALUE);
    }

    private static float sanitizeFloatPropertyValue(float f, String charSequence, float f2, float f3) {
        if (f >= f2 && f <= f3) {
            return f;
        }
        if (!(f < f2) && f != Float.NEGATIVE_INFINITY) {
            if (!(f > f3) && f != Float.POSITIVE_INFINITY) {
                if (Float.isNaN(f)) {
                    if (!sThrowOnInvalidFloatProperties) {
                        return 0.0f;
                    }
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Cannot set '");
                    stringBuilder.append((String)charSequence);
                    stringBuilder.append("' to Float.NaN");
                    throw new IllegalArgumentException(stringBuilder.toString());
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("How do you get here?? ");
                ((StringBuilder)charSequence).append(f);
                throw new IllegalStateException(((StringBuilder)charSequence).toString());
            }
            if (!sThrowOnInvalidFloatProperties) {
                return f3;
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Cannot set '");
            stringBuilder.append((String)charSequence);
            stringBuilder.append("' to ");
            stringBuilder.append(f);
            stringBuilder.append(", the value must be <= ");
            stringBuilder.append(f3);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (!sThrowOnInvalidFloatProperties) {
            return f2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Cannot set '");
        stringBuilder.append((String)charSequence);
        stringBuilder.append("' to ");
        stringBuilder.append(f);
        stringBuilder.append(", the value must be >= ");
        stringBuilder.append(f2);
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    private void saveAttributeData(AttributeSet object, TypedArray typedArray) {
        int n;
        int n2 = object == null ? 0 : object.getAttributeCount();
        int n3 = typedArray.getIndexCount();
        String[] arrstring = new String[(n2 + n3) * 2];
        int n4 = 0;
        for (n = 0; n < n2; ++n) {
            arrstring[n4] = object.getAttributeName(n);
            arrstring[n4 + 1] = object.getAttributeValue(n);
            n4 += 2;
        }
        Resources resources = typedArray.getResources();
        SparseArray<String> sparseArray = View.getAttributeMap();
        n = 0;
        n2 = n4;
        for (n4 = n; n4 < n3; ++n4) {
            int n5;
            n = typedArray.getIndex(n4);
            if (!typedArray.hasValueOrEmpty(n) || (n5 = typedArray.getResourceId(n, 0)) == 0) continue;
            String string2 = sparseArray.get(n5);
            object = string2;
            if (string2 == null) {
                try {
                    object = resources.getResourceName(n5);
                }
                catch (Resources.NotFoundException notFoundException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("0x");
                    ((StringBuilder)object).append(Integer.toHexString(n5));
                    object = ((StringBuilder)object).toString();
                }
                sparseArray.put(n5, (String)object);
            }
            arrstring[n2] = object;
            arrstring[n2 + 1] = typedArray.getString(n);
            n2 += 2;
        }
        object = new String[n2];
        System.arraycopy(arrstring, 0, object, 0, n2);
        this.mAttributes = object;
    }

    private void sendAccessibilityHoverEvent(int n) {
        Object object = this;
        do {
            if (((View)object).includeForAccessibility()) {
                ((View)object).sendAccessibilityEvent(n);
                return;
            }
            if (!((object = ((View)object).getParent()) instanceof View)) break;
            object = (View)object;
        } while (true);
    }

    private void sendViewTextTraversedAtGranularityEvent(int n, int n2, int n3, int n4) {
        if (this.mParent == null) {
            return;
        }
        AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(131072);
        this.onInitializeAccessibilityEvent(accessibilityEvent);
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setFromIndex(n3);
        accessibilityEvent.setToIndex(n4);
        accessibilityEvent.setAction(n);
        accessibilityEvent.setMovementGranularity(n2);
        this.mParent.requestSendAccessibilityEvent(this, accessibilityEvent);
    }

    private void setBackgroundRenderNodeProperties(RenderNode renderNode) {
        renderNode.setTranslationX(this.mScrollX);
        renderNode.setTranslationY(this.mScrollY);
    }

    private void setDefaultFocusHighlight(Drawable object) {
        this.mDefaultFocusHighlight = object;
        boolean bl = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        if (object != null) {
            int n = this.mPrivateFlags;
            if ((n & 128) != 0) {
                this.mPrivateFlags = n & -129;
            }
            ((Drawable)object).setLayoutDirection(this.getLayoutDirection());
            if (((Drawable)object).isStateful()) {
                ((Drawable)object).setState(this.getDrawableState());
            }
            if (this.isAttachedToWindow()) {
                if (this.getWindowVisibility() != 0 || !this.isShown()) {
                    bl = false;
                }
                ((Drawable)object).setVisible(bl, false);
            }
            ((Drawable)object).setCallback(this);
        } else if ((this.mViewFlags & 128) != 0 && this.mBackground == null && ((object = this.mForegroundInfo) == null || ((ForegroundInfo)object).mDrawable == null)) {
            this.mPrivateFlags |= 128;
        }
        this.invalidate();
    }

    private void setFocusedInCluster(View view) {
        if (this instanceof ViewGroup) {
            ((ViewGroup)this).mFocusedInCluster = null;
        }
        if (view == this) {
            return;
        }
        ViewParent viewParent = this.mParent;
        View view2 = this;
        while (viewParent instanceof ViewGroup) {
            ((ViewGroup)viewParent).mFocusedInCluster = view2;
            if (viewParent == view) break;
            view2 = (View)((Object)viewParent);
            viewParent = viewParent.getParent();
        }
    }

    private void setKeyedTag(int n, Object object) {
        if (this.mKeyedTags == null) {
            this.mKeyedTags = new SparseArray(2);
        }
        this.mKeyedTags.put(n, object);
    }

    private void setMeasuredDimensionRaw(int n, int n2) {
        this.mMeasuredWidth = n;
        this.mMeasuredHeight = n2;
        this.mPrivateFlags |= 2048;
    }

    private boolean setOpticalFrame(int n, int n2, int n3, int n4) {
        Object object = this.mParent;
        object = object instanceof View ? ((View)object).getOpticalInsets() : Insets.NONE;
        Insets insets = this.getOpticalInsets();
        return this.setFrame(((Insets)object).left + n - insets.left, ((Insets)object).top + n2 - insets.top, ((Insets)object).left + n3 + insets.right, ((Insets)object).top + n4 + insets.bottom);
    }

    private void setOutlineProviderFromAttribute(int n) {
        if (n != 0) {
            if (n != 1) {
                if (n != 2) {
                    if (n == 3) {
                        this.setOutlineProvider(ViewOutlineProvider.PADDED_BOUNDS);
                    }
                } else {
                    this.setOutlineProvider(ViewOutlineProvider.BOUNDS);
                }
            } else {
                this.setOutlineProvider(null);
            }
        } else {
            this.setOutlineProvider(ViewOutlineProvider.BACKGROUND);
        }
    }

    private void setPressed(boolean bl, float f, float f2) {
        if (bl) {
            this.drawableHotspotChanged(f, f2);
        }
        this.setPressed(bl);
    }

    private boolean showHoverTooltip() {
        return this.showTooltip(this.mTooltipInfo.mAnchorX, this.mTooltipInfo.mAnchorY, false);
    }

    private boolean showLongClickTooltip(int n, int n2) {
        this.removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
        this.removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
        return this.showTooltip(n, n2, true);
    }

    private boolean showTooltip(int n, int n2, boolean bl) {
        if (this.mAttachInfo != null && this.mTooltipInfo != null) {
            if (bl && (this.mViewFlags & 32) != 0) {
                return false;
            }
            if (TextUtils.isEmpty(this.mTooltipInfo.mTooltipText)) {
                return false;
            }
            this.hideTooltip();
            TooltipInfo tooltipInfo = this.mTooltipInfo;
            tooltipInfo.mTooltipFromLongClick = bl;
            tooltipInfo.mTooltipPopup = new TooltipPopup(this.getContext());
            bl = (this.mPrivateFlags3 & 131072) == 131072;
            this.mTooltipInfo.mTooltipPopup.show(this, n, n2, bl, this.mTooltipInfo.mTooltipText);
            this.mAttachInfo.mTooltipHost = this;
            this.notifyViewAccessibilityStateChangedIfNeeded(0);
            return true;
        }
        return false;
    }

    private void sizeChange(int n, int n2, int n3, int n4) {
        this.onSizeChanged(n, n2, n3, n4);
        Object object = this.mOverlay;
        if (object != null) {
            ((ViewOverlay)object).getOverlayView().setRight(n);
            this.mOverlay.getOverlayView().setBottom(n2);
        }
        if (!(sCanFocusZeroSized || !this.isLayoutValid() || (object = this.mParent) instanceof ViewGroup && ((ViewGroup)object).isLayoutSuppressed())) {
            if (n > 0 && n2 > 0) {
                if ((n3 <= 0 || n4 <= 0) && this.mParent != null && this.canTakeFocus()) {
                    this.mParent.focusableViewAvailable(this);
                }
            } else {
                if (this.hasFocus()) {
                    this.clearFocus();
                    object = this.mParent;
                    if (object instanceof ViewGroup) {
                        ((ViewGroup)object).clearFocusedInCluster();
                    }
                }
                this.clearAccessibilityFocus();
            }
        }
        this.rebuildOutline();
    }

    private boolean skipInvalidate() {
        ViewParent viewParent;
        boolean bl = (this.mViewFlags & 12) != 0 && this.mCurrentAnimation == null && (!((viewParent = this.mParent) instanceof ViewGroup) || !((ViewGroup)viewParent).isViewTransitioning(this));
        return bl;
    }

    private void switchDefaultFocusHighlight() {
        if (this.isFocused()) {
            Drawable drawable2 = this.mBackground;
            Object object = this.mForegroundInfo;
            object = object == null ? null : ((ForegroundInfo)object).mDrawable;
            boolean bl = this.isDefaultFocusHighlightNeeded(drawable2, (Drawable)object);
            boolean bl2 = this.mDefaultFocusHighlight != null;
            if (bl && !bl2) {
                this.setDefaultFocusHighlight(this.getDefaultFocusHighlightDrawable());
            } else if (!bl && bl2) {
                this.setDefaultFocusHighlight(null);
            }
        }
    }

    private boolean traverseAtGranularity(int n, boolean bl, boolean bl2) {
        CharSequence charSequence = this.getIterableTextForAccessibility();
        if (charSequence != null && charSequence.length() != 0) {
            int n2;
            int n3;
            int[] arrn = this.getIteratorForGranularity(n);
            if (arrn == null) {
                return false;
            }
            int n4 = n3 = this.getAccessibilitySelectionEnd();
            if (n3 == -1) {
                n4 = bl ? 0 : charSequence.length();
            }
            if ((arrn = bl ? arrn.following(n4) : arrn.preceding(n4)) == null) {
                return false;
            }
            n3 = arrn[0];
            int n5 = arrn[1];
            if (bl2 && this.isAccessibilitySelectionExtendable()) {
                n4 = n2 = this.getAccessibilitySelectionStart();
                if (n2 == -1) {
                    n4 = bl ? n3 : n5;
                }
                n2 = bl ? n5 : n3;
            } else {
                n4 = bl ? n5 : n3;
                n2 = n4;
            }
            this.setAccessibilitySelection(n4, n2);
            n4 = bl ? 256 : 512;
            this.sendViewTextTraversedAtGranularityEvent(n4, n, n3, n5);
            return true;
        }
        return false;
    }

    private void updateFocusedInCluster(View view, int n) {
        View view2;
        if (view != null && (view2 = view.findKeyboardNavigationCluster()) != this.findKeyboardNavigationCluster()) {
            view.setFocusedInCluster(view2);
            if (!(view.mParent instanceof ViewGroup)) {
                return;
            }
            if (n != 2 && n != 1) {
                if (view instanceof ViewGroup && ((ViewGroup)view).getDescendantFocusability() == 262144 && ViewRootImpl.isViewDescendantOf(this, view)) {
                    ((ViewGroup)view.mParent).clearFocusedInCluster(view);
                }
            } else {
                ((ViewGroup)view.mParent).clearFocusedInCluster(view);
            }
        }
    }

    private void updatePflags3AndNotifyA11yIfChanged(int n, boolean bl) {
        int n2 = this.mPrivateFlags3;
        n = bl ? n2 | n : n2 & n;
        if (n != this.mPrivateFlags3) {
            this.mPrivateFlags3 = n;
            this.notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public void addChildrenForAccessibility(ArrayList<View> arrayList) {
    }

    public void addExtraDataToAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo, String string2, Bundle bundle) {
    }

    public void addFocusables(ArrayList<View> arrayList, int n) {
        this.addFocusables(arrayList, n, (int)this.isInTouchMode());
    }

    public void addFocusables(ArrayList<View> arrayList, int n, int n2) {
        if (arrayList == null) {
            return;
        }
        if (!this.canTakeFocus()) {
            return;
        }
        if ((n2 & 1) == 1 && !this.isFocusableInTouchMode()) {
            return;
        }
        arrayList.add(this);
    }

    public void addFrameMetricsListener(Window object, Window.OnFrameMetricsAvailableListener onFrameMetricsAvailableListener, Handler handler) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            if (attachInfo.mThreadedRenderer != null) {
                if (this.mFrameMetricsObservers == null) {
                    this.mFrameMetricsObservers = new ArrayList();
                }
                object = new FrameMetricsObserver((Window)object, handler.getLooper(), onFrameMetricsAvailableListener);
                this.mFrameMetricsObservers.add((FrameMetricsObserver)object);
                this.mAttachInfo.mThreadedRenderer.addFrameMetricsObserver((FrameMetricsObserver)object);
            } else {
                Log.w(VIEW_LOG_TAG, "View not hardware-accelerated. Unable to observe frame stats");
            }
        } else {
            if (this.mFrameMetricsObservers == null) {
                this.mFrameMetricsObservers = new ArrayList();
            }
            object = new FrameMetricsObserver((Window)object, handler.getLooper(), onFrameMetricsAvailableListener);
            this.mFrameMetricsObservers.add((FrameMetricsObserver)object);
        }
    }

    public void addKeyboardNavigationClusters(Collection<View> collection, int n) {
        if (!this.isKeyboardNavigationCluster()) {
            return;
        }
        if (!this.hasFocusable()) {
            return;
        }
        collection.add(this);
    }

    public void addOnAttachStateChangeListener(OnAttachStateChangeListener onAttachStateChangeListener) {
        ListenerInfo listenerInfo = this.getListenerInfo();
        if (listenerInfo.mOnAttachStateChangeListeners == null) {
            listenerInfo.mOnAttachStateChangeListeners = new CopyOnWriteArrayList();
        }
        listenerInfo.mOnAttachStateChangeListeners.add(onAttachStateChangeListener);
    }

    public void addOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        ListenerInfo listenerInfo = this.getListenerInfo();
        if (listenerInfo.mOnLayoutChangeListeners == null) {
            listenerInfo.mOnLayoutChangeListeners = new ArrayList();
        }
        if (!listenerInfo.mOnLayoutChangeListeners.contains(onLayoutChangeListener)) {
            listenerInfo.mOnLayoutChangeListeners.add(onLayoutChangeListener);
        }
    }

    public void addOnUnhandledKeyEventListener(OnUnhandledKeyEventListener object) {
        ArrayList<OnUnhandledKeyEventListener> arrayList;
        ArrayList<OnUnhandledKeyEventListener> arrayList2 = arrayList = this.getListenerInfo().mUnhandledKeyListeners;
        if (arrayList == null) {
            arrayList2 = new ArrayList<OnUnhandledKeyEventListener>();
            this.getListenerInfo().mUnhandledKeyListeners = arrayList2;
        }
        arrayList2.add((OnUnhandledKeyEventListener)object);
        if (arrayList2.size() == 1 && (object = this.mParent) instanceof ViewGroup) {
            ((ViewGroup)object).incrementChildUnhandledKeyListeners();
        }
    }

    public void addTouchables(ArrayList<View> arrayList) {
        int n = this.mViewFlags;
        if (((n & 16384) == 16384 || (n & 2097152) == 2097152 || (n & 8388608) == 8388608) && (n & 32) == 0) {
            arrayList.add(this);
        }
    }

    public ViewPropertyAnimator animate() {
        if (this.mAnimator == null) {
            this.mAnimator = new ViewPropertyAnimator(this);
        }
        return this.mAnimator;
    }

    public void announceForAccessibility(CharSequence charSequence) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mParent != null) {
            AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(16384);
            this.onInitializeAccessibilityEvent(accessibilityEvent);
            accessibilityEvent.getText().add(charSequence);
            accessibilityEvent.setContentDescription(null);
            this.mParent.requestSendAccessibilityEvent(this, accessibilityEvent);
        }
    }

    @UnsupportedAppUsage
    public void applyDrawableToTransparentRegion(Drawable arrn, Region region) {
        Region region2 = arrn.getTransparentRegion();
        arrn = arrn.getBounds();
        AttachInfo attachInfo = this.mAttachInfo;
        if (region2 != null && attachInfo != null) {
            int n = this.getRight() - this.getLeft();
            int n2 = this.getBottom() - this.getTop();
            if (arrn.left > 0) {
                region2.op(0, 0, arrn.left, n2, Region.Op.UNION);
            }
            if (arrn.right < n) {
                region2.op(arrn.right, 0, n, n2, Region.Op.UNION);
            }
            if (arrn.top > 0) {
                region2.op(0, 0, n, arrn.top, Region.Op.UNION);
            }
            if (arrn.bottom < n2) {
                region2.op(0, arrn.bottom, n, n2, Region.Op.UNION);
            }
            arrn = attachInfo.mTransparentLocation;
            this.getLocationInWindow(arrn);
            region2.translate(arrn[0], arrn[1]);
            region.op(region2, Region.Op.INTERSECT);
        } else {
            region.op((Rect)arrn, Region.Op.DIFFERENCE);
        }
    }

    boolean areDrawablesResolved() {
        boolean bl = (this.mPrivateFlags2 & 1073741824) == 1073741824;
        return bl;
    }

    @UnsupportedAppUsage
    void assignParent(ViewParent object) {
        block4 : {
            block3 : {
                block2 : {
                    if (this.mParent != null) break block2;
                    this.mParent = object;
                    break block3;
                }
                if (object != null) break block4;
                this.mParent = null;
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("view ");
        ((StringBuilder)object).append(this);
        ((StringBuilder)object).append(" being added, but it already has a parent");
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public void autofill(SparseArray<AutofillValue> sparseArray) {
        if (!this.mContext.isAutofillCompatibilityEnabled()) {
            return;
        }
        AccessibilityNodeProvider accessibilityNodeProvider = this.getAccessibilityNodeProvider();
        if (accessibilityNodeProvider == null) {
            return;
        }
        int n = sparseArray.size();
        for (int i = 0; i < n; ++i) {
            Object object = sparseArray.valueAt(i);
            if (!((AutofillValue)object).isText()) continue;
            int n2 = sparseArray.keyAt(i);
            object = ((AutofillValue)object).getTextValue();
            Bundle bundle = new Bundle();
            bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", (CharSequence)object);
            accessibilityNodeProvider.performAction(n2, 2097152, bundle);
        }
    }

    public void autofill(AutofillValue autofillValue) {
    }

    protected boolean awakenScrollBars() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        boolean bl = true;
        if (scrollabilityCache == null || !this.awakenScrollBars(scrollabilityCache.scrollBarDefaultDelayBeforeFade, true)) {
            bl = false;
        }
        return bl;
    }

    protected boolean awakenScrollBars(int n) {
        return this.awakenScrollBars(n, true);
    }

    protected boolean awakenScrollBars(int n, boolean bl) {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null && scrollabilityCache.fadeScrollBars) {
            long l;
            if (scrollabilityCache.scrollBar == null) {
                scrollabilityCache.scrollBar = new ScrollBarDrawable();
                scrollabilityCache.scrollBar.setState(this.getDrawableState());
                scrollabilityCache.scrollBar.setCallback(this);
            }
            if (!this.isHorizontalScrollBarEnabled() && !this.isVerticalScrollBarEnabled()) {
                return false;
            }
            if (bl) {
                this.postInvalidateOnAnimation();
            }
            int n2 = n;
            if (scrollabilityCache.state == 0) {
                n2 = Math.max(750, n);
            }
            scrollabilityCache.fadeStartTime = l = AnimationUtils.currentAnimationTimeMillis() + (long)n2;
            scrollabilityCache.state = 1;
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mHandler.removeCallbacks(scrollabilityCache);
                this.mAttachInfo.mHandler.postAtTime(scrollabilityCache, l);
            }
            return true;
        }
        return false;
    }

    public void bringToFront() {
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.bringChildToFront(this);
        }
    }

    @Deprecated
    public void buildDrawingCache() {
        this.buildDrawingCache(false);
    }

    @Deprecated
    public void buildDrawingCache(boolean bl) {
        if ((this.mPrivateFlags & 32768) == 0 || (bl ? this.mDrawingCache == null : this.mUnscaledDrawingCache == null)) {
            if (Trace.isTagEnabled(8L)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("buildDrawingCache/SW Layer for ");
                stringBuilder.append(this.getClass().getSimpleName());
                Trace.traceBegin(8L, stringBuilder.toString());
            }
            this.buildDrawingCacheImpl(bl);
        }
        return;
        finally {
            Trace.traceEnd(8L);
        }
    }

    public void buildLayer() {
        if (this.mLayerType == 0) {
            return;
        }
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            if (this.getWidth() != 0 && this.getHeight() != 0) {
                int n = this.mLayerType;
                if (n != 1) {
                    if (n == 2) {
                        this.updateDisplayListIfDirty();
                        if (attachInfo.mThreadedRenderer != null && this.mRenderNode.hasDisplayList()) {
                            attachInfo.mThreadedRenderer.buildLayer(this.mRenderNode);
                        }
                    }
                } else {
                    this.buildDrawingCache(true);
                }
                return;
            }
            return;
        }
        throw new IllegalStateException("This view must be attached to a window first");
    }

    final boolean callDragEventHandler(DragEvent dragEvent) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean bl = listenerInfo != null && listenerInfo.mOnDragListener != null && (this.mViewFlags & 32) == 0 && listenerInfo.mOnDragListener.onDrag(this, dragEvent) ? true : this.onDragEvent(dragEvent);
        int n = dragEvent.mAction;
        if (n != 4) {
            if (n != 5) {
                if (n == 6) {
                    this.mPrivateFlags2 &= -3;
                    this.refreshDrawableState();
                }
            } else {
                this.mPrivateFlags2 |= 2;
                this.refreshDrawableState();
            }
        } else {
            this.mPrivateFlags2 &= -4;
            this.refreshDrawableState();
        }
        return bl;
    }

    public boolean callOnClick() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnClickListener != null) {
            listenerInfo.mOnClickListener.onClick(this);
            return true;
        }
        return false;
    }

    boolean canAcceptDrag() {
        int n = this.mPrivateFlags2;
        boolean bl = true;
        if ((n & 1) == 0) {
            bl = false;
        }
        return bl;
    }

    public boolean canHaveDisplayList() {
        AttachInfo attachInfo = this.mAttachInfo;
        boolean bl = attachInfo != null && attachInfo.mThreadedRenderer != null;
        return bl;
    }

    public boolean canNotifyAutofillEnterExitEvent() {
        boolean bl = this.isAutofillable() && this.isAttachedToWindow();
        return bl;
    }

    protected boolean canReceivePointerEvents() {
        boolean bl = (this.mViewFlags & 12) == 0 || this.getAnimation() != null;
        return bl;
    }

    public boolean canResolveLayoutDirection() {
        if (this.getRawLayoutDirection() != 2) {
            return true;
        }
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            try {
                boolean bl = viewParent.canResolveLayoutDirection();
                return bl;
            }
            catch (AbstractMethodError abstractMethodError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mParent.getClass().getSimpleName());
                stringBuilder.append(" does not fully implement ViewParent");
                Log.e(VIEW_LOG_TAG, stringBuilder.toString(), abstractMethodError);
            }
        }
        return false;
    }

    public boolean canResolveTextAlignment() {
        if (this.getRawTextAlignment() != 0) {
            return true;
        }
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            try {
                boolean bl = viewParent.canResolveTextAlignment();
                return bl;
            }
            catch (AbstractMethodError abstractMethodError) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.mParent.getClass().getSimpleName());
                stringBuilder.append(" does not fully implement ViewParent");
                Log.e(VIEW_LOG_TAG, stringBuilder.toString(), abstractMethodError);
            }
        }
        return false;
    }

    public boolean canResolveTextDirection() {
        if (this.getRawTextDirection() != 0) {
            return true;
        }
        Object object = this.mParent;
        if (object != null) {
            try {
                boolean bl = object.canResolveTextDirection();
                return bl;
            }
            catch (AbstractMethodError abstractMethodError) {
                object = new StringBuilder();
                ((StringBuilder)object).append(this.mParent.getClass().getSimpleName());
                ((StringBuilder)object).append(" does not fully implement ViewParent");
                Log.e(VIEW_LOG_TAG, ((StringBuilder)object).toString(), abstractMethodError);
            }
        }
        return false;
    }

    public boolean canScrollHorizontally(int n) {
        int n2 = this.computeHorizontalScrollOffset();
        int n3 = this.computeHorizontalScrollRange() - this.computeHorizontalScrollExtent();
        boolean bl = false;
        boolean bl2 = false;
        if (n3 == 0) {
            return false;
        }
        if (n < 0) {
            if (n2 > 0) {
                bl2 = true;
            }
            return bl2;
        }
        bl2 = bl;
        if (n2 < n3 - 1) {
            bl2 = true;
        }
        return bl2;
    }

    public boolean canScrollVertically(int n) {
        int n2 = this.computeVerticalScrollOffset();
        int n3 = this.computeVerticalScrollRange() - this.computeVerticalScrollExtent();
        boolean bl = false;
        boolean bl2 = false;
        if (n3 == 0) {
            return false;
        }
        if (n < 0) {
            if (n2 > 0) {
                bl2 = true;
            }
            return bl2;
        }
        bl2 = bl;
        if (n2 < n3 - 1) {
            bl2 = true;
        }
        return bl2;
    }

    public final void cancelDragAndDrop() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            Log.w(VIEW_LOG_TAG, "cancelDragAndDrop called on a detached view.");
            return;
        }
        if (attachInfo.mDragToken != null) {
            try {
                this.mAttachInfo.mSession.cancelDragAndDrop(this.mAttachInfo.mDragToken, false);
            }
            catch (Exception exception) {
                Log.e(VIEW_LOG_TAG, "Unable to cancel drag", exception);
            }
            this.mAttachInfo.mDragToken = null;
        } else {
            Log.e(VIEW_LOG_TAG, "No active drag to cancel");
        }
    }

    public void cancelLongPress() {
        this.removeLongPressCallback();
        this.removeTapCallback();
    }

    public final void cancelPendingInputEvents() {
        this.dispatchCancelPendingInputEvents();
    }

    public void captureTransitioningViews(List<View> list) {
        if (this.getVisibility() == 0) {
            list.add(this);
        }
    }

    public boolean checkInputConnectionProxy(View view) {
        return false;
    }

    @UnsupportedAppUsage
    public void clearAccessibilityFocus() {
        View view;
        this.clearAccessibilityFocusNoCallbacks(0);
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        if (viewRootImpl != null && (view = viewRootImpl.getAccessibilityFocusedHost()) != null && ViewRootImpl.isViewDescendantOf(view, this)) {
            viewRootImpl.setAccessibilityFocus(null, null);
        }
    }

    void clearAccessibilityFocusNoCallbacks(int n) {
        int n2 = this.mPrivateFlags2;
        if ((67108864 & n2) != 0) {
            this.mPrivateFlags2 = n2 & -67108865;
            this.invalidate();
            if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(65536);
                accessibilityEvent.setAction(n);
                AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
                if (accessibilityDelegate != null) {
                    accessibilityDelegate.sendAccessibilityEventUnchecked(this, accessibilityEvent);
                } else {
                    this.sendAccessibilityEventUnchecked(accessibilityEvent);
                }
            }
        }
    }

    public void clearAnimation() {
        Animation animation = this.mCurrentAnimation;
        if (animation != null) {
            animation.detach();
        }
        this.mCurrentAnimation = null;
        this.invalidateParentIfNeeded();
    }

    public void clearFocus() {
        boolean bl = sAlwaysAssignFocus || !this.isInTouchMode();
        this.clearFocusInternal(null, true, bl);
    }

    void clearFocusInternal(View object, boolean bl, boolean bl2) {
        int n = this.mPrivateFlags;
        if ((n & 2) != 0) {
            this.mPrivateFlags = n & -3;
            this.clearParentsWantFocus();
            if (bl && (object = this.mParent) != null) {
                object.clearChildFocus(this);
            }
            this.onFocusChanged(false, 0, null);
            this.refreshDrawableState();
            if (!(!bl || bl2 && this.rootViewRequestFocus())) {
                this.notifyGlobalFocusCleared(this);
            }
        }
    }

    void clearParentsWantFocus() {
        ViewParent viewParent = this.mParent;
        if (viewParent instanceof View) {
            View view = (View)((Object)viewParent);
            view.mPrivateFlags &= -2;
            ((View)((Object)viewParent)).clearParentsWantFocus();
        }
    }

    int combineVisibility(int n, int n2) {
        return Math.max(n, n2);
    }

    @Deprecated
    @UnsupportedAppUsage
    protected boolean computeFitSystemWindows(Rect rect, Rect object) {
        object = this.computeSystemWindowInsets(new WindowInsets(rect), (Rect)object);
        rect.set(((WindowInsets)object).getSystemWindowInsetsAsRect());
        return ((WindowInsets)object).isSystemWindowInsetsConsumed();
    }

    protected int computeHorizontalScrollExtent() {
        return this.getWidth();
    }

    protected int computeHorizontalScrollOffset() {
        return this.mScrollX;
    }

    protected int computeHorizontalScrollRange() {
        return this.getWidth();
    }

    @UnsupportedAppUsage
    protected void computeOpaqueFlags() {
        Drawable drawable2 = this.mBackground;
        this.mPrivateFlags = drawable2 != null && drawable2.getOpacity() == -1 ? (this.mPrivateFlags |= 8388608) : (this.mPrivateFlags &= -8388609);
        int n = this.mViewFlags;
        this.mPrivateFlags = ((n & 512) != 0 || (n & 256) != 0) && (n & 50331648) != 0 && (50331648 & n) != 33554432 ? (this.mPrivateFlags &= -16777217) : (this.mPrivateFlags |= 16777216);
    }

    Insets computeOpticalInsets() {
        Object object = this.mBackground;
        object = object == null ? Insets.NONE : ((Drawable)object).getOpticalInsets();
        return object;
    }

    public void computeScroll() {
    }

    public WindowInsets computeSystemWindowInsets(WindowInsets windowInsets, Rect rect) {
        AttachInfo attachInfo;
        if ((this.mViewFlags & 2048) != 0 && (attachInfo = this.mAttachInfo) != null && ((attachInfo.mSystemUiVisibility & 1536) != 0 || this.mAttachInfo.mOverscanRequested)) {
            rect.set(this.mAttachInfo.mOverscanInsets);
            return windowInsets.inset(rect);
        }
        rect.set(windowInsets.getSystemWindowInsetsAsRect());
        return windowInsets.consumeSystemWindowInsets().inset(rect);
    }

    protected int computeVerticalScrollExtent() {
        return this.getHeight();
    }

    protected int computeVerticalScrollOffset() {
        return this.mScrollY;
    }

    protected int computeVerticalScrollRange() {
        return this.getHeight();
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfo() {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            return accessibilityDelegate.createAccessibilityNodeInfo(this);
        }
        return this.createAccessibilityNodeInfoInternal();
    }

    public AccessibilityNodeInfo createAccessibilityNodeInfoInternal() {
        Object object = this.getAccessibilityNodeProvider();
        if (object != null) {
            return ((AccessibilityNodeProvider)object).createAccessibilityNodeInfo(-1);
        }
        object = AccessibilityNodeInfo.obtain(this);
        this.onInitializeAccessibilityNodeInfo((AccessibilityNodeInfo)object);
        return object;
    }

    public void createContextMenu(ContextMenu contextMenu) {
        ContextMenu.ContextMenuInfo contextMenuInfo = this.getContextMenuInfo();
        ((MenuBuilder)((Object)contextMenu)).setCurrentMenuInfo(contextMenuInfo);
        this.onCreateContextMenu(contextMenu);
        Object object = this.mListenerInfo;
        if (object != null && ((ListenerInfo)object).mOnCreateContextMenuListener != null) {
            ((ListenerInfo)object).mOnCreateContextMenuListener.onCreateContextMenu(contextMenu, this, contextMenuInfo);
        }
        ((MenuBuilder)((Object)contextMenu)).setCurrentMenuInfo(null);
        object = this.mParent;
        if (object != null) {
            object.createContextMenu(contextMenu);
        }
    }

    @UnsupportedAppUsage
    public Bitmap createSnapshot(ViewDebug.CanvasProvider object, boolean bl) {
        Canvas canvas;
        Canvas canvas2;
        Canvas canvas3;
        int n;
        int n2;
        block26 : {
            block25 : {
                float f;
                block24 : {
                    n = this.mRight;
                    int n3 = this.mLeft;
                    int n4 = this.mBottom;
                    n2 = this.mTop;
                    AttachInfo attachInfo = this.mAttachInfo;
                    f = attachInfo != null ? attachInfo.mApplicationScale : 1.0f;
                    n = (int)((float)(n - n3) * f + 0.5f);
                    n4 = (int)((float)(n4 - n2) * f + 0.5f);
                    Canvas canvas4 = null;
                    canvas3 = null;
                    n2 = 1;
                    if (n <= 0) {
                        n = 1;
                    }
                    if (n4 > 0) {
                        n2 = n4;
                    }
                    canvas2 = canvas4;
                    try {
                        canvas = object.getCanvas(this, n, n2);
                        if (attachInfo == null) break block24;
                        canvas2 = canvas4;
                    }
                    catch (Throwable throwable) {
                        if (canvas2 != null) {
                            attachInfo.mCanvas = canvas2;
                        }
                        throw throwable;
                    }
                    canvas2 = canvas3 = attachInfo.mCanvas;
                    attachInfo.mCanvas = null;
                }
                canvas2 = canvas3;
                this.computeScroll();
                canvas2 = canvas3;
                n = canvas.save();
                canvas2 = canvas3;
                canvas.scale(f, f);
                canvas2 = canvas3;
                canvas.translate(-this.mScrollX, -this.mScrollY);
                canvas2 = canvas3;
                n2 = this.mPrivateFlags;
                canvas2 = canvas3;
                this.mPrivateFlags &= -2097153;
                canvas2 = canvas3;
                if ((this.mPrivateFlags & 128) != 128) break block25;
                canvas2 = canvas3;
                this.dispatchDraw(canvas);
                canvas2 = canvas3;
                this.drawAutofilledHighlight(canvas);
                canvas2 = canvas3;
                if (this.mOverlay == null) break block26;
                canvas2 = canvas3;
                if (this.mOverlay.isEmpty()) break block26;
                canvas2 = canvas3;
                this.mOverlay.getOverlayView().draw(canvas);
                break block26;
            }
            canvas2 = canvas3;
            this.draw(canvas);
        }
        canvas2 = canvas3;
        this.mPrivateFlags = n2;
        canvas2 = canvas3;
        canvas.restoreToCount(n);
        canvas2 = canvas3;
        object = object.createBitmap();
        if (canvas3 != null) {
            attachInfo.mCanvas = canvas3;
        }
        return object;
    }

    protected void damageInParent() {
        ViewParent viewParent = this.mParent;
        if (viewParent != null && this.mAttachInfo != null) {
            viewParent.onDescendantInvalidated(this, this);
        }
    }

    @UnsupportedAppUsage
    public void debug() {
        this.debug(0);
    }

    @UnsupportedAppUsage
    protected void debug(int n) {
        CharSequence charSequence = View.debugIndent(n - 1);
        Object object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("+ ");
        ((StringBuilder)object).append(this);
        object = ((StringBuilder)object).toString();
        int n2 = this.getId();
        charSequence = object;
        if (n2 != -1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append(" (id=");
            ((StringBuilder)charSequence).append(n2);
            ((StringBuilder)charSequence).append(")");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        Object object2 = this.getTag();
        object = charSequence;
        if (object2 != null) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append(" (tag=");
            ((StringBuilder)object).append(object2);
            ((StringBuilder)object).append(")");
            object = ((StringBuilder)object).toString();
        }
        Log.d(VIEW_LOG_TAG, (String)object);
        if ((this.mPrivateFlags & 2) != 0) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(View.debugIndent(n));
            ((StringBuilder)charSequence).append(" FOCUSED");
            Log.d(VIEW_LOG_TAG, ((StringBuilder)charSequence).toString());
        }
        charSequence = View.debugIndent(n);
        object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("frame={");
        ((StringBuilder)object).append(this.mLeft);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(this.mTop);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(this.mRight);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(this.mBottom);
        ((StringBuilder)object).append("} scroll={");
        ((StringBuilder)object).append(this.mScrollX);
        ((StringBuilder)object).append(", ");
        ((StringBuilder)object).append(this.mScrollY);
        ((StringBuilder)object).append("} ");
        Log.d(VIEW_LOG_TAG, ((StringBuilder)object).toString());
        if (this.mPaddingLeft != 0 || this.mPaddingTop != 0 || this.mPaddingRight != 0 || this.mPaddingBottom != 0) {
            object = View.debugIndent(n);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)object);
            ((StringBuilder)charSequence).append("padding={");
            ((StringBuilder)charSequence).append(this.mPaddingLeft);
            ((StringBuilder)charSequence).append(", ");
            ((StringBuilder)charSequence).append(this.mPaddingTop);
            ((StringBuilder)charSequence).append(", ");
            ((StringBuilder)charSequence).append(this.mPaddingRight);
            ((StringBuilder)charSequence).append(", ");
            ((StringBuilder)charSequence).append(this.mPaddingBottom);
            ((StringBuilder)charSequence).append("}");
            Log.d(VIEW_LOG_TAG, ((StringBuilder)charSequence).toString());
        }
        object = View.debugIndent(n);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("mMeasureWidth=");
        ((StringBuilder)charSequence).append(this.mMeasuredWidth);
        ((StringBuilder)charSequence).append(" mMeasureHeight=");
        ((StringBuilder)charSequence).append(this.mMeasuredHeight);
        Log.d(VIEW_LOG_TAG, ((StringBuilder)charSequence).toString());
        charSequence = View.debugIndent(n);
        object = this.mLayoutParams;
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("BAD! no layout params");
            charSequence = ((StringBuilder)object).toString();
        } else {
            charSequence = ((ViewGroup.LayoutParams)object).debug((String)charSequence);
        }
        Log.d(VIEW_LOG_TAG, (String)charSequence);
        object = View.debugIndent(n);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append("flags={");
        charSequence = ((StringBuilder)charSequence).toString();
        object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append(View.printFlags(this.mViewFlags));
        charSequence = ((StringBuilder)object).toString();
        object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("}");
        Log.d(VIEW_LOG_TAG, ((StringBuilder)object).toString());
        charSequence = View.debugIndent(n);
        object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("privateFlags={");
        object = ((StringBuilder)object).toString();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append(View.printPrivateFlags(this.mPrivateFlags));
        charSequence = ((StringBuilder)charSequence).toString();
        object = new StringBuilder();
        ((StringBuilder)object).append((String)charSequence);
        ((StringBuilder)object).append("}");
        Log.d(VIEW_LOG_TAG, ((StringBuilder)object).toString());
    }

    final boolean debugDraw() {
        AttachInfo attachInfo;
        boolean bl = DEBUG_DRAW || (attachInfo = this.mAttachInfo) != null && attachInfo.mDebugLayout;
        return bl;
    }

    @Deprecated
    public void destroyDrawingCache() {
        Bitmap bitmap = this.mDrawingCache;
        if (bitmap != null) {
            bitmap.recycle();
            this.mDrawingCache = null;
        }
        if ((bitmap = this.mUnscaledDrawingCache) != null) {
            bitmap.recycle();
            this.mUnscaledDrawingCache = null;
        }
    }

    @UnsupportedAppUsage
    protected void destroyHardwareResources() {
        Object object = this.mOverlay;
        if (object != null) {
            ((ViewOverlay)object).getOverlayView().destroyHardwareResources();
        }
        if ((object = this.mGhostView) != null) {
            ((View)object).destroyHardwareResources();
        }
    }

    final int dipsToPixels(int n) {
        float f = this.getContext().getResources().getDisplayMetrics().density;
        return (int)((float)n * f + 0.5f);
    }

    public boolean dispatchActivityResult(String string2, int n, int n2, Intent intent) {
        String string3 = this.mStartActivityRequestWho;
        if (string3 != null && string3.equals(string2)) {
            this.onActivityResult(n, n2, intent);
            this.mStartActivityRequestWho = null;
            return true;
        }
        return false;
    }

    public WindowInsets dispatchApplyWindowInsets(WindowInsets windowInsets) {
        try {
            this.mPrivateFlags3 |= 32;
            if (this.mListenerInfo != null && this.mListenerInfo.mOnApplyWindowInsetsListener != null) {
                windowInsets = this.mListenerInfo.mOnApplyWindowInsetsListener.onApplyWindowInsets(this, windowInsets);
                return windowInsets;
            }
            windowInsets = this.onApplyWindowInsets(windowInsets);
            return windowInsets;
        }
        finally {
            this.mPrivateFlags3 &= -33;
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    void dispatchAttachedToWindow(AttachInfo attachInfo, int n) {
        int n2;
        this.mAttachInfo = attachInfo;
        Object object = this.mOverlay;
        if (object != null) {
            ((ViewOverlay)object).getOverlayView().dispatchAttachedToWindow(attachInfo, n);
        }
        ++this.mWindowAttachCount;
        this.mPrivateFlags |= 1024;
        Object object2 = this.mFloatingTreeObserver;
        object = null;
        if (object2 != null) {
            attachInfo.mTreeObserver.merge(this.mFloatingTreeObserver);
            this.mFloatingTreeObserver = null;
        }
        this.registerPendingFrameMetricsObservers();
        if ((this.mPrivateFlags & 524288) != 0) {
            this.mAttachInfo.mScrollContainers.add(this);
            this.mPrivateFlags |= 1048576;
        }
        if ((object2 = this.mRunQueue) != null) {
            ((HandlerActionQueue)object2).executeActions(attachInfo.mHandler);
            this.mRunQueue = null;
        }
        this.performCollectViewAttributes(this.mAttachInfo, n);
        this.onAttachedToWindow();
        object2 = this.mListenerInfo;
        if (object2 != null) {
            object = ((ListenerInfo)object2).mOnAttachStateChangeListeners;
        }
        if (object != null && ((CopyOnWriteArrayList)object).size() > 0) {
            object = ((CopyOnWriteArrayList)object).iterator();
            while (object.hasNext()) {
                ((OnAttachStateChangeListener)object.next()).onViewAttachedToWindow(this);
            }
        }
        if ((n2 = attachInfo.mWindowVisibility) != 8) {
            this.onWindowVisibilityChanged(n2);
            if (this.isShown()) {
                boolean bl = n2 == 0;
                this.onVisibilityAggregated(bl);
            }
        }
        this.onVisibilityChanged(this, n);
        if ((this.mPrivateFlags & 1024) != 0) {
            this.refreshDrawableState();
        }
        this.needGlobalAttributesUpdate(false);
        this.notifyEnterOrExitForAutoFillIfNeeded(true);
    }

    void dispatchCancelPendingInputEvents() {
        this.mPrivateFlags3 &= -17;
        this.onCancelPendingInputEvents();
        if ((this.mPrivateFlags3 & 16) == 16) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("View ");
        stringBuilder.append(this.getClass().getSimpleName());
        stringBuilder.append(" did not call through to super.onCancelPendingInputEvents()");
        throw new SuperNotCalledException(stringBuilder.toString());
    }

    public boolean dispatchCapturedPointerEvent(MotionEvent motionEvent) {
        if (!this.hasPointerCapture()) {
            return false;
        }
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnCapturedPointerListener != null && listenerInfo.mOnCapturedPointerListener.onCapturedPointer(this, motionEvent)) {
            return true;
        }
        return this.onCapturedPointerEvent(motionEvent);
    }

    void dispatchCollectViewAttributes(AttachInfo attachInfo, int n) {
        this.performCollectViewAttributes(attachInfo, n);
    }

    public void dispatchConfigurationChanged(Configuration configuration) {
        this.onConfigurationChanged(configuration);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    void dispatchDetachedFromWindow() {
        Object object = this.mAttachInfo;
        if (object != null && ((AttachInfo)object).mWindowVisibility != 8) {
            this.onWindowVisibilityChanged(8);
            if (this.isShown()) {
                this.onVisibilityAggregated(false);
            }
        }
        this.onDetachedFromWindow();
        this.onDetachedFromWindowInternal();
        object = this.getContext().getSystemService(InputMethodManager.class);
        if (object != null) {
            ((InputMethodManager)object).onViewDetachedFromWindow(this);
        }
        if ((object = (object = this.mListenerInfo) != null ? ((ListenerInfo)object).mOnAttachStateChangeListeners : null) != null && ((CopyOnWriteArrayList)object).size() > 0) {
            object = ((CopyOnWriteArrayList)object).iterator();
            while (object.hasNext()) {
                ((OnAttachStateChangeListener)object.next()).onViewDetachedFromWindow(this);
            }
        }
        if ((this.mPrivateFlags & 1048576) != 0) {
            this.mAttachInfo.mScrollContainers.remove(this);
            this.mPrivateFlags &= -1048577;
        }
        this.mAttachInfo = null;
        object = this.mOverlay;
        if (object != null) {
            ((ViewOverlay)object).getOverlayView().dispatchDetachedFromWindow();
        }
        this.notifyEnterOrExitForAutoFillIfNeeded(false);
    }

    public void dispatchDisplayHint(int n) {
        this.onDisplayHint(n);
    }

    boolean dispatchDragEnterExitInPreN(DragEvent dragEvent) {
        return this.callDragEventHandler(dragEvent);
    }

    public boolean dispatchDragEvent(DragEvent dragEvent) {
        dragEvent.mEventHandlerWasCalled = true;
        if (dragEvent.mAction == 2 || dragEvent.mAction == 3) {
            this.getViewRootImpl().setDragFocus(this, dragEvent);
        }
        return this.callDragEventHandler(dragEvent);
    }

    protected void dispatchDraw(Canvas canvas) {
    }

    public void dispatchDrawableHotspotChanged(float f, float f2) {
    }

    public void dispatchFinishTemporaryDetach() {
        this.mPrivateFlags3 &= -33554433;
        this.onFinishTemporaryDetach();
        if (this.hasWindowFocus() && this.hasFocus()) {
            this.notifyFocusChangeToInputMethodManager(true);
        }
        this.notifyEnterOrExitForAutoFillIfNeeded(true);
    }

    protected boolean dispatchGenericFocusedEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean dispatchGenericMotionEvent(MotionEvent motionEvent) {
        int n;
        InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onGenericMotionEvent(motionEvent, 0);
        }
        if ((motionEvent.getSource() & 2) != 0 ? ((n = motionEvent.getAction()) != 9 && n != 7 && n != 10 ? this.dispatchGenericPointerEvent(motionEvent) : this.dispatchHoverEvent(motionEvent)) : this.dispatchGenericFocusedEvent(motionEvent)) {
            return true;
        }
        if (this.dispatchGenericMotionEventInternal(motionEvent)) {
            return true;
        }
        inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onUnhandledEvent(motionEvent, 0);
        }
        return false;
    }

    protected boolean dispatchGenericPointerEvent(MotionEvent motionEvent) {
        return false;
    }

    protected void dispatchGetDisplayList() {
    }

    protected boolean dispatchHoverEvent(MotionEvent motionEvent) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnHoverListener != null && (this.mViewFlags & 32) == 0 && listenerInfo.mOnHoverListener.onHover(this, motionEvent)) {
            return true;
        }
        return this.onHoverEvent(motionEvent);
    }

    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        Object object = this.mInputEventConsistencyVerifier;
        if (object != null) {
            ((InputEventConsistencyVerifier)object).onKeyEvent(keyEvent, 0);
        }
        if ((object = this.mListenerInfo) != null && ((ListenerInfo)object).mOnKeyListener != null && (this.mViewFlags & 32) == 0 && ((ListenerInfo)object).mOnKeyListener.onKey(this, keyEvent.getKeyCode(), keyEvent)) {
            return true;
        }
        object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mKeyDispatchState : null;
        if (keyEvent.dispatch(this, (KeyEvent.DispatcherState)object, this)) {
            return true;
        }
        object = this.mInputEventConsistencyVerifier;
        if (object != null) {
            ((InputEventConsistencyVerifier)object).onUnhandledEvent(keyEvent, 0);
        }
        return false;
    }

    public boolean dispatchKeyEventPreIme(KeyEvent keyEvent) {
        return this.onKeyPreIme(keyEvent.getKeyCode(), keyEvent);
    }

    public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
        return this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
    }

    void dispatchMovedToDisplay(Display display, Configuration configuration) {
        AttachInfo attachInfo = this.mAttachInfo;
        attachInfo.mDisplay = display;
        attachInfo.mDisplayState = display.getState();
        this.onMovedToDisplay(display.getDisplayId(), configuration);
    }

    public boolean dispatchNestedFling(float f, float f2, boolean bl) {
        ViewParent viewParent;
        if (this.isNestedScrollingEnabled() && (viewParent = this.mNestedScrollingParent) != null) {
            return viewParent.onNestedFling(this, f, f2, bl);
        }
        return false;
    }

    public boolean dispatchNestedPreFling(float f, float f2) {
        ViewParent viewParent;
        if (this.isNestedScrollingEnabled() && (viewParent = this.mNestedScrollingParent) != null) {
            return viewParent.onNestedPreFling(this, f, f2);
        }
        return false;
    }

    public boolean dispatchNestedPrePerformAccessibilityAction(int n, Bundle bundle) {
        for (ViewParent viewParent = this.getParent(); viewParent != null; viewParent = viewParent.getParent()) {
            if (!viewParent.onNestedPrePerformAccessibilityAction(this, n, bundle)) continue;
            return true;
        }
        return false;
    }

    public boolean dispatchNestedPreScroll(int n, int n2, int[] arrn, int[] arrn2) {
        if (this.isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            boolean bl = true;
            if (n == 0 && n2 == 0) {
                if (arrn2 != null) {
                    arrn2[0] = 0;
                    arrn2[1] = 0;
                }
            } else {
                int n3 = 0;
                int n4 = 0;
                if (arrn2 != null) {
                    this.getLocationInWindow(arrn2);
                    n3 = arrn2[0];
                    n4 = arrn2[1];
                }
                int[] arrn3 = arrn;
                if (arrn == null) {
                    if (this.mTempNestedScrollConsumed == null) {
                        this.mTempNestedScrollConsumed = new int[2];
                    }
                    arrn3 = this.mTempNestedScrollConsumed;
                }
                arrn3[0] = 0;
                arrn3[1] = 0;
                this.mNestedScrollingParent.onNestedPreScroll(this, n, n2, arrn3);
                if (arrn2 != null) {
                    this.getLocationInWindow(arrn2);
                    arrn2[0] = arrn2[0] - n3;
                    arrn2[1] = arrn2[1] - n4;
                }
                boolean bl2 = bl;
                if (arrn3[0] == 0) {
                    bl2 = arrn3[1] != 0 ? bl : false;
                }
                return bl2;
            }
        }
        return false;
    }

    public boolean dispatchNestedScroll(int n, int n2, int n3, int n4, int[] arrn) {
        if (this.isNestedScrollingEnabled() && this.mNestedScrollingParent != null) {
            if (n == 0 && n2 == 0 && n3 == 0 && n4 == 0) {
                if (arrn != null) {
                    arrn[0] = 0;
                    arrn[1] = 0;
                }
            } else {
                int n5 = 0;
                int n6 = 0;
                if (arrn != null) {
                    this.getLocationInWindow(arrn);
                    n5 = arrn[0];
                    n6 = arrn[1];
                }
                this.mNestedScrollingParent.onNestedScroll(this, n, n2, n3, n4);
                if (arrn != null) {
                    this.getLocationInWindow(arrn);
                    arrn[0] = arrn[0] - n5;
                    arrn[1] = arrn[1] - n6;
                }
                return true;
            }
        }
        return false;
    }

    public void dispatchPointerCaptureChanged(boolean bl) {
        this.onPointerCaptureChange(bl);
    }

    @UnsupportedAppUsage
    public final boolean dispatchPointerEvent(MotionEvent motionEvent) {
        if (motionEvent.isTouchEvent()) {
            return this.dispatchTouchEvent(motionEvent);
        }
        return this.dispatchGenericMotionEvent(motionEvent);
    }

    public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            return accessibilityDelegate.dispatchPopulateAccessibilityEvent(this, accessibilityEvent);
        }
        return this.dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
    }

    public boolean dispatchPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        this.onPopulateAccessibilityEvent(accessibilityEvent);
        return false;
    }

    public void dispatchProvideAutofillStructure(ViewStructure viewStructure, int n) {
        this.dispatchProvideStructure(viewStructure, 1, n);
    }

    public void dispatchProvideStructure(ViewStructure viewStructure) {
        this.dispatchProvideStructure(viewStructure, 0, 0);
    }

    protected void dispatchRestoreInstanceState(SparseArray<Parcelable> object) {
        int n = this.mID;
        if (n != -1 && (object = ((SparseArray)object).get(n)) != null) {
            this.mPrivateFlags &= -131073;
            this.onRestoreInstanceState((Parcelable)object);
            if ((this.mPrivateFlags & 131072) == 0) {
                throw new IllegalStateException("Derived class did not call super.onRestoreInstanceState()");
            }
        }
    }

    protected void dispatchSaveInstanceState(SparseArray<Parcelable> sparseArray) {
        if (this.mID != -1 && (this.mViewFlags & 65536) == 0) {
            this.mPrivateFlags &= -131073;
            Parcelable parcelable = this.onSaveInstanceState();
            if ((this.mPrivateFlags & 131072) != 0) {
                if (parcelable != null) {
                    sparseArray.put(this.mID, parcelable);
                }
            } else {
                throw new IllegalStateException("Derived class did not call super.onSaveInstanceState()");
            }
        }
    }

    void dispatchScreenStateChanged(int n) {
        this.onScreenStateChanged(n);
    }

    protected void dispatchSetActivated(boolean bl) {
    }

    protected void dispatchSetPressed(boolean bl) {
    }

    protected void dispatchSetSelected(boolean bl) {
    }

    public void dispatchStartTemporaryDetach() {
        this.mPrivateFlags3 |= 33554432;
        this.notifyEnterOrExitForAutoFillIfNeeded(false);
        this.onStartTemporaryDetach();
    }

    public void dispatchSystemUiVisibilityChanged(int n) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnSystemUiVisibilityChangeListener != null) {
            listenerInfo.mOnSystemUiVisibilityChangeListener.onSystemUiVisibilityChange(n & 16375);
        }
    }

    boolean dispatchTooltipHoverEvent(MotionEvent motionEvent) {
        int n;
        block11 : {
            block10 : {
                block9 : {
                    if (this.mTooltipInfo == null) {
                        return false;
                    }
                    n = motionEvent.getAction();
                    if (n == 7) break block9;
                    if (n == 10) {
                        this.mTooltipInfo.clearAnchorPos();
                        if (!this.mTooltipInfo.mTooltipFromLongClick) {
                            this.hideTooltip();
                        }
                    }
                    break block10;
                }
                if ((this.mViewFlags & 1073741824) == 1073741824) break block11;
            }
            return false;
        }
        if (!this.mTooltipInfo.mTooltipFromLongClick && this.mTooltipInfo.updateAnchorPos(motionEvent)) {
            if (this.mTooltipInfo.mTooltipPopup == null) {
                this.removeCallbacks(this.mTooltipInfo.mShowTooltipRunnable);
                this.postDelayed(this.mTooltipInfo.mShowTooltipRunnable, ViewConfiguration.getHoverTooltipShowTimeout());
            }
            n = (this.getWindowSystemUiVisibility() & 1) == 1 ? ViewConfiguration.getHoverTooltipHideShortTimeout() : ViewConfiguration.getHoverTooltipHideTimeout();
            this.removeCallbacks(this.mTooltipInfo.mHideTooltipRunnable);
            this.postDelayed(this.mTooltipInfo.mHideTooltipRunnable, n);
        }
        return true;
    }

    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int n;
        if (motionEvent.isTargetAccessibilityFocus()) {
            if (!this.isAccessibilityFocusedViewOrHost()) {
                return false;
            }
            motionEvent.setTargetAccessibilityFocus(false);
        }
        boolean bl = false;
        boolean bl2 = false;
        Object object = this.mInputEventConsistencyVerifier;
        if (object != null) {
            ((InputEventConsistencyVerifier)object).onTouchEvent(motionEvent, 0);
        }
        if ((n = motionEvent.getActionMasked()) == 0) {
            this.stopNestedScroll();
        }
        if (this.onFilterTouchEventForSecurity(motionEvent)) {
            bl = bl2;
            if ((this.mViewFlags & 32) == 0) {
                bl = bl2;
                if (this.handleScrollBarDragging(motionEvent)) {
                    bl = true;
                }
            }
            object = this.mListenerInfo;
            bl2 = bl;
            if (object != null) {
                bl2 = bl;
                if (((ListenerInfo)object).mOnTouchListener != null) {
                    bl2 = bl;
                    if ((this.mViewFlags & 32) == 0) {
                        bl2 = bl;
                        if (((ListenerInfo)object).mOnTouchListener.onTouch(this, motionEvent)) {
                            bl2 = true;
                        }
                    }
                }
            }
            bl = bl2;
            if (!bl2) {
                bl = bl2;
                if (this.onTouchEvent(motionEvent)) {
                    bl = true;
                }
            }
        }
        if (!bl && (object = this.mInputEventConsistencyVerifier) != null) {
            ((InputEventConsistencyVerifier)object).onUnhandledEvent(motionEvent, 0);
        }
        if (n == 1 || n == 3 || n == 0 && !bl) {
            this.stopNestedScroll();
        }
        return bl;
    }

    public boolean dispatchTrackballEvent(MotionEvent motionEvent) {
        InputEventConsistencyVerifier inputEventConsistencyVerifier = this.mInputEventConsistencyVerifier;
        if (inputEventConsistencyVerifier != null) {
            inputEventConsistencyVerifier.onTrackballEvent(motionEvent, 0);
        }
        return this.onTrackballEvent(motionEvent);
    }

    View dispatchUnhandledKeyEvent(KeyEvent keyEvent) {
        if (this.onUnhandledKeyEvent(keyEvent)) {
            return this;
        }
        return null;
    }

    public boolean dispatchUnhandledMove(View view, int n) {
        return false;
    }

    boolean dispatchVisibilityAggregated(boolean bl) {
        int n = this.getVisibility();
        boolean bl2 = true;
        if ((n = n == 0 ? 1 : 0) != 0 || !bl) {
            this.onVisibilityAggregated(bl);
        }
        bl = n != 0 && bl ? bl2 : false;
        return bl;
    }

    protected void dispatchVisibilityChanged(View view, int n) {
        this.onVisibilityChanged(view, n);
    }

    public void dispatchWindowFocusChanged(boolean bl) {
        this.onWindowFocusChanged(bl);
    }

    void dispatchWindowInsetsAnimationFinished(WindowInsetsAnimationListener.InsetsAnimation insetsAnimation) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mWindowInsetsAnimationListener != null) {
            this.mListenerInfo.mWindowInsetsAnimationListener.onFinished(insetsAnimation);
        }
    }

    WindowInsets dispatchWindowInsetsAnimationProgress(WindowInsets windowInsets) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mWindowInsetsAnimationListener != null) {
            return this.mListenerInfo.mWindowInsetsAnimationListener.onProgress(windowInsets);
        }
        return windowInsets;
    }

    void dispatchWindowInsetsAnimationStarted(WindowInsetsAnimationListener.InsetsAnimation insetsAnimation) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mWindowInsetsAnimationListener != null) {
            this.mListenerInfo.mWindowInsetsAnimationListener.onStarted(insetsAnimation);
        }
    }

    public void dispatchWindowSystemUiVisiblityChanged(int n) {
        this.onWindowSystemUiVisibilityChanged(n);
    }

    public void dispatchWindowVisibilityChanged(int n) {
        this.onWindowVisibilityChanged(n);
    }

    public void draw(Canvas canvas) {
        int n;
        int n2;
        this.mPrivateFlags = -2097153 & this.mPrivateFlags | 32;
        this.drawBackground(canvas);
        int n3 = this.mViewFlags;
        int n4 = (n3 & 4096) != 0 ? 1 : 0;
        int n5 = (n3 & 8192) != 0 ? 1 : 0;
        if (n5 == 0 && n4 == 0) {
            this.onDraw(canvas);
            this.dispatchDraw(canvas);
            this.drawAutofilledHighlight(canvas);
            ViewOverlay viewOverlay = this.mOverlay;
            if (viewOverlay != null && !viewOverlay.isEmpty()) {
                this.mOverlay.getOverlayView().dispatchDraw(canvas);
            }
            this.onDrawForeground(canvas);
            this.drawDefaultFocusHighlight(canvas);
            if (this.debugDraw()) {
                this.debugDrawFocus(canvas);
            }
            return;
        }
        float f = 0.0f;
        float f2 = 0.0f;
        float f3 = 0.0f;
        n3 = this.mPaddingLeft;
        boolean bl = this.isPaddingOffsetRequired();
        if (bl) {
            n3 += this.getLeftPaddingOffset();
        }
        int n6 = this.mScrollX + n3;
        int n7 = this.mRight;
        int n8 = 0;
        n7 = n7 + n6 - this.mLeft - this.mPaddingRight - n3;
        int n9 = this.mScrollY + this.getFadeTop(bl);
        int n10 = n9 + this.getFadeHeight(bl);
        if (bl) {
            n = this.getRightPaddingOffset();
            n3 = this.getBottomPaddingOffset();
            n7 += n;
            n10 += n3;
        }
        Object object = this.mScrollCache;
        int n11 = 0;
        float f4 = ((ScrollabilityCache)object).fadingEdgeLength;
        int n12 = 0;
        n = (int)f4;
        if (n5 != 0) {
            n3 = n;
            if (n9 + n > n10 - n) {
                n3 = (n10 - n9) / 2;
            }
        } else {
            n3 = n;
        }
        float f5 = 0.0f;
        int n13 = 0;
        n = n4 != 0 && n6 + n3 > n7 - n3 ? (n7 - n6) / 2 : n3;
        if (n5 != 0) {
            f5 = Math.max(0.0f, Math.min(1.0f, this.getTopFadingEdgeStrength()));
            n3 = f5 * f4 > 1.0f ? 1 : 0;
            n8 = n3;
            f = Math.max(0.0f, Math.min(1.0f, this.getBottomFadingEdgeStrength()));
            n3 = f * f4 > 1.0f ? 1 : 0;
            n11 = n3;
        }
        if (n4 != 0) {
            f2 = Math.max(0.0f, Math.min(1.0f, this.getLeftFadingEdgeStrength()));
            n3 = f2 * f4 > 1.0f ? 1 : 0;
            f3 = Math.max(0.0f, Math.min(1.0f, this.getRightFadingEdgeStrength()));
            n4 = f3 * f4 > 1.0f ? 1 : 0;
            n12 = n3;
            n13 = n4;
        }
        int n14 = canvas.getSaveCount();
        n5 = -1;
        n3 = -1;
        n4 = -1;
        int n15 = this.getSolidColor();
        if (n15 == 0) {
            if (n8 != 0) {
                n5 = canvas.saveUnclippedLayer(n6, n9, n7, n9 + n);
            }
            if (n11 != 0) {
                n3 = canvas.saveUnclippedLayer(n6, n10 - n, n7, n10);
            }
            if (n12 != 0) {
                n4 = canvas.saveUnclippedLayer(n6, n9, n6 + n, n10);
            }
            if (n13 != 0) {
                n2 = canvas.saveUnclippedLayer(n7 - n, n9, n7, n10);
                int n16 = n5;
                n5 = n4;
                n4 = n16;
            } else {
                int n17 = -1;
                n2 = n5;
                n5 = n4;
                n4 = n2;
                n2 = n17;
            }
        } else {
            ((ScrollabilityCache)object).setFadeColor(n15);
            n3 = -1;
            n5 = -1;
            n2 = -1;
            n4 = -1;
        }
        this.onDraw(canvas);
        this.dispatchDraw(canvas);
        Paint paint = ((ScrollabilityCache)object).paint;
        Object object2 = ((ScrollabilityCache)object).matrix;
        object = ((ScrollabilityCache)object).shader;
        if (n13 != 0) {
            ((Matrix)object2).setScale(1.0f, f4 * f3);
            ((Matrix)object2).postRotate(90.0f);
            ((Matrix)object2).postTranslate(n7, n9);
            ((Shader)object).setLocalMatrix((Matrix)object2);
            paint.setShader((Shader)object);
            if (n15 == 0) {
                canvas.restoreUnclippedLayer(n2, paint);
            } else {
                canvas.drawRect(n7 - n, n9, n7, n10, paint);
            }
        }
        if (n12 != 0) {
            ((Matrix)object2).setScale(1.0f, f4 * f2);
            ((Matrix)object2).postRotate(-90.0f);
            ((Matrix)object2).postTranslate(n6, n9);
            ((Shader)object).setLocalMatrix((Matrix)object2);
            paint.setShader((Shader)object);
            if (n15 == 0) {
                canvas.restoreUnclippedLayer(n5, paint);
            } else {
                canvas.drawRect(n6, n9, n6 + n, n10, paint);
            }
        }
        if (n11 != 0) {
            ((Matrix)object2).setScale(1.0f, f4 * f);
            ((Matrix)object2).postRotate(180.0f);
            ((Matrix)object2).postTranslate(n6, n10);
            ((Shader)object).setLocalMatrix((Matrix)object2);
            paint.setShader((Shader)object);
            if (n15 == 0) {
                canvas.restoreUnclippedLayer(n3, paint);
            } else {
                canvas.drawRect(n6, n10 - n, n7, n10, paint);
            }
        }
        if (n8 != 0) {
            ((Matrix)object2).setScale(1.0f, f4 * f5);
            ((Matrix)object2).postTranslate(n6, n9);
            ((Shader)object).setLocalMatrix((Matrix)object2);
            paint.setShader((Shader)object);
            if (n15 == 0) {
                canvas.restoreUnclippedLayer(n4, paint);
            } else {
                canvas.drawRect(n6, n9, n7, n9 + n, paint);
            }
        }
        canvas.restoreToCount(n14);
        this.drawAutofilledHighlight(canvas);
        object2 = this.mOverlay;
        if (object2 != null && !((ViewOverlay)object2).isEmpty()) {
            this.mOverlay.getOverlayView().dispatchDraw(canvas);
        }
        this.onDrawForeground(canvas);
        if (this.debugDraw()) {
            this.debugDrawFocus(canvas);
        }
    }

    boolean draw(Canvas canvas, ViewGroup viewGroup, long l) {
        int n;
        boolean bl;
        boolean bl2;
        Object object;
        int n2;
        int n3;
        float f;
        boolean bl3 = canvas.isHardwareAccelerated();
        Object object2 = this.mAttachInfo;
        int n4 = object2 != null && ((AttachInfo)object2).mHardwareAccelerated && bl3 ? 1 : 0;
        int n5 = n4;
        boolean bl4 = false;
        boolean bl5 = this.hasIdentityMatrix();
        int n6 = viewGroup.mGroupFlags;
        if ((n6 & 256) != 0) {
            viewGroup.getChildTransformation().clear();
            viewGroup.mGroupFlags &= -257;
        }
        object2 = null;
        boolean bl6 = false;
        Object object3 = this.mAttachInfo;
        boolean bl7 = object3 != null && ((AttachInfo)object3).mScalingRequired;
        Animation animation = this.getAnimation();
        if (animation != null) {
            bl2 = this.applyLegacyAnimation(viewGroup, l, animation, bl7);
            bl = animation.willChangeTransformationMatrix();
            if (bl) {
                this.mPrivateFlags3 |= 1;
            }
            object3 = viewGroup.getChildTransformation();
        } else {
            n4 = this.mPrivateFlags3;
            Object var18_17 = null;
            if ((n4 & 1) != 0) {
                this.mRenderNode.setAnimationMatrix(null);
                this.mPrivateFlags3 &= -2;
            }
            bl2 = bl4;
            object3 = object2;
            bl = bl6;
            if (n5 == 0) {
                bl2 = bl4;
                object3 = object2;
                bl = bl6;
                if ((n6 & 2048) != 0) {
                    object = viewGroup.getChildTransformation();
                    bl2 = bl4;
                    object3 = object2;
                    bl = bl6;
                    if (viewGroup.getChildStaticTransformation(this, (Transformation)object)) {
                        n4 = ((Transformation)object).getTransformationType();
                        object2 = var18_17;
                        if (n4 != 0) {
                            object2 = object;
                        }
                        bl2 = (n4 & 2) != 0;
                        bl = bl2;
                        object3 = object2;
                        bl2 = bl4;
                    }
                }
            }
        }
        boolean bl8 = bl | bl5 ^ true;
        this.mPrivateFlags |= 32;
        if (!bl8 && (n6 & 2049) == 1 && canvas.quickReject(this.mLeft, this.mTop, this.mRight, this.mBottom, Canvas.EdgeType.BW) && (this.mPrivateFlags & 64) == 0) {
            this.mPrivateFlags2 |= 268435456;
            return bl2;
        }
        this.mPrivateFlags2 &= -268435457;
        if (bl3) {
            bl = (this.mPrivateFlags & Integer.MIN_VALUE) != 0;
            this.mRecreateDisplayList = bl;
            this.mPrivateFlags &= Integer.MAX_VALUE;
        }
        if ((n3 = this.getLayerType()) != 1 && n5 != 0) {
            object2 = null;
            n2 = n3;
        } else {
            n4 = n3;
            if (n3 != 0) {
                n4 = 1;
                this.buildDrawingCache(true);
            }
            object2 = this.getDrawingCache(true);
            n2 = n4;
        }
        if (n5 != 0) {
            object = this.updateDisplayListIfDirty();
            if (!((RenderNode)object).hasDisplayList()) {
                n5 = 0;
                object = null;
            }
        } else {
            object = null;
        }
        if (n5 == 0) {
            this.computeScroll();
            n = this.mScrollX;
            n3 = this.mScrollY;
        } else {
            n = 0;
            n3 = 0;
        }
        boolean bl9 = object2 != null && n5 == 0;
        boolean bl10 = object2 == null && n5 == 0;
        n4 = -1;
        if (n5 == 0 || object3 != null) {
            n4 = canvas.save();
        }
        if (bl10) {
            canvas.translate(this.mLeft - n, this.mTop - n3);
        } else {
            if (n5 == 0) {
                canvas.translate(this.mLeft, this.mTop);
            }
            if (bl7) {
                if (n5 != 0) {
                    n4 = canvas.save();
                }
                f = 1.0f / this.mAttachInfo.mApplicationScale;
                canvas.scale(f, f);
            }
        }
        f = n5 != 0 ? 1.0f : this.getAlpha() * this.getTransitionAlpha();
        if (object3 == null && !(f < 1.0f) && this.hasIdentityMatrix() && (this.mPrivateFlags3 & 2) == 0) {
            if ((this.mPrivateFlags & 262144) == 262144) {
                this.onSetAlpha(255);
                this.mPrivateFlags &= -262145;
            }
        } else {
            int n7;
            if (object3 != null || !bl5) {
                float f2;
                int n8 = 0;
                if (bl10) {
                    n8 = -n;
                    n7 = -n3;
                } else {
                    n7 = 0;
                }
                if (object3 != null) {
                    if (bl8) {
                        if (n5 != 0) {
                            ((RenderNode)object).setAnimationMatrix(((Transformation)object3).getMatrix());
                        } else {
                            canvas.translate(-n8, -n7);
                            canvas.concat(((Transformation)object3).getMatrix());
                            canvas.translate(n8, n7);
                        }
                        viewGroup.mGroupFlags |= 256;
                    }
                    float f3 = ((Transformation)object3).getAlpha();
                    f2 = f;
                    if (f3 < 1.0f) {
                        f2 = f * f3;
                        viewGroup.mGroupFlags |= 256;
                    }
                } else {
                    f2 = f;
                }
                if (!bl5 && n5 == 0) {
                    canvas.translate(-n8, -n7);
                    canvas.concat(this.getMatrix());
                    canvas.translate(n8, n7);
                }
                f = f2;
            }
            if (f < 1.0f || (this.mPrivateFlags3 & 2) != 0) {
                this.mPrivateFlags3 = f < 1.0f ? (this.mPrivateFlags3 |= 2) : (this.mPrivateFlags3 &= -3);
                viewGroup.mGroupFlags |= 256;
                if (!bl9) {
                    n7 = (int)(f * 255.0f);
                    if (!this.onSetAlpha(n7)) {
                        if (n5 != 0) {
                            ((RenderNode)object).setAlpha(this.getAlpha() * f * this.getTransitionAlpha());
                        } else if (n2 == 0) {
                            canvas.saveLayerAlpha(n, n3, this.getWidth() + n, n3 + this.getHeight(), n7);
                        }
                    } else {
                        this.mPrivateFlags |= 262144;
                    }
                }
            }
        }
        if (n5 == 0) {
            if ((n6 & 1) != 0 && object2 == null) {
                if (bl10) {
                    canvas.clipRect(n, n3, n + this.getWidth(), n3 + this.getHeight());
                } else if (bl7 && object2 != null) {
                    canvas.clipRect(0, 0, ((Bitmap)object2).getWidth(), ((Bitmap)object2).getHeight());
                } else {
                    canvas.clipRect(0, 0, this.getWidth(), this.getHeight());
                }
            }
            if ((object3 = this.mClipBounds) != null) {
                canvas.clipRect((Rect)object3);
            }
        }
        if (!bl9) {
            if (n5 != 0) {
                this.mPrivateFlags = -2097153 & this.mPrivateFlags;
                ((RecordingCanvas)canvas).drawRenderNode((RenderNode)object);
            } else {
                n3 = this.mPrivateFlags;
                if ((n3 & 128) == 128) {
                    this.mPrivateFlags = -2097153 & n3;
                    this.dispatchDraw(canvas);
                } else {
                    this.draw(canvas);
                }
            }
        } else if (object2 != null) {
            this.mPrivateFlags = -2097153 & this.mPrivateFlags;
            if (n2 != 0 && (object3 = this.mLayerPaint) != null) {
                n3 = ((Paint)object3).getAlpha();
                if (f < 1.0f) {
                    this.mLayerPaint.setAlpha((int)((float)n3 * f));
                }
                canvas.drawBitmap((Bitmap)object2, 0.0f, 0.0f, this.mLayerPaint);
                if (f < 1.0f) {
                    this.mLayerPaint.setAlpha(n3);
                }
            } else {
                object3 = object = viewGroup.mCachePaint;
                if (object == null) {
                    object3 = new Paint();
                    ((Paint)object3).setDither(false);
                    viewGroup.mCachePaint = object3;
                }
                ((Paint)object3).setAlpha((int)(f * 255.0f));
                canvas.drawBitmap((Bitmap)object2, 0.0f, 0.0f, (Paint)object3);
            }
        }
        if (n4 >= 0) {
            canvas.restoreToCount(n4);
        }
        if (animation != null && !bl2) {
            if (!bl3 && !animation.getFillAfter()) {
                this.onSetAlpha(255);
            }
            viewGroup.finishAnimatingView(this, animation);
        }
        if (bl2 && bl3 && animation.hasAlpha() && (this.mPrivateFlags & 262144) == 262144) {
            this.invalidate(true);
        }
        this.mRecreateDisplayList = false;
        return bl2;
    }

    public void drawableHotspotChanged(float f, float f2) {
        Object object = this.mBackground;
        if (object != null) {
            ((Drawable)object).setHotspot(f, f2);
        }
        if ((object = this.mDefaultFocusHighlight) != null) {
            ((Drawable)object).setHotspot(f, f2);
        }
        if ((object = this.mForegroundInfo) != null && ((ForegroundInfo)object).mDrawable != null) {
            this.mForegroundInfo.mDrawable.setHotspot(f, f2);
        }
        this.dispatchDrawableHotspotChanged(f, f2);
    }

    protected void drawableStateChanged() {
        int[] arrn = this.getDrawableState();
        boolean bl = false;
        Object object = this.mBackground;
        boolean bl2 = bl;
        if (object != null) {
            bl2 = bl;
            if (((Drawable)object).isStateful()) {
                bl2 = false | ((Drawable)object).setState(arrn);
            }
        }
        object = this.mDefaultFocusHighlight;
        bl = bl2;
        if (object != null) {
            bl = bl2;
            if (((Drawable)object).isStateful()) {
                bl = bl2 | ((Drawable)object).setState(arrn);
            }
        }
        object = (object = this.mForegroundInfo) != null ? ((ForegroundInfo)object).mDrawable : null;
        bl2 = bl;
        if (object != null) {
            bl2 = bl;
            if (((Drawable)object).isStateful()) {
                bl2 = bl | ((Drawable)object).setState(arrn);
            }
        }
        object = this.mScrollCache;
        bl = bl2;
        if (object != null) {
            object = ((ScrollabilityCache)object).scrollBar;
            bl = bl2;
            if (object != null) {
                bl = bl2;
                if (((Drawable)object).isStateful()) {
                    bl = ((Drawable)object).setState(arrn) && this.mScrollCache.state != 0;
                    bl = bl2 | bl;
                }
            }
        }
        if ((object = this.mStateListAnimator) != null) {
            ((StateListAnimator)object).setState(arrn);
        }
        if (bl) {
            this.invalidate();
        }
    }

    public void encode(ViewHierarchyEncoder viewHierarchyEncoder) {
        viewHierarchyEncoder.beginObject(this);
        this.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.endObject();
    }

    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        Object object = ViewDebug.resolveId(this.getContext(), this.mID);
        if (object instanceof String) {
            viewHierarchyEncoder.addProperty("id", (String)object);
        } else {
            viewHierarchyEncoder.addProperty("id", this.mID);
        }
        object = this.mTransformationInfo;
        float f = object != null ? ((TransformationInfo)object).mAlpha : 0.0f;
        viewHierarchyEncoder.addProperty("misc:transformation.alpha", f);
        viewHierarchyEncoder.addProperty("misc:transitionName", this.getTransitionName());
        viewHierarchyEncoder.addProperty("layout:left", this.mLeft);
        viewHierarchyEncoder.addProperty("layout:right", this.mRight);
        viewHierarchyEncoder.addProperty("layout:top", this.mTop);
        viewHierarchyEncoder.addProperty("layout:bottom", this.mBottom);
        viewHierarchyEncoder.addProperty("layout:width", this.getWidth());
        viewHierarchyEncoder.addProperty("layout:height", this.getHeight());
        viewHierarchyEncoder.addProperty("layout:layoutDirection", this.getLayoutDirection());
        viewHierarchyEncoder.addProperty("layout:layoutRtl", this.isLayoutRtl());
        viewHierarchyEncoder.addProperty("layout:hasTransientState", this.hasTransientState());
        viewHierarchyEncoder.addProperty("layout:baseline", this.getBaseline());
        object = this.getLayoutParams();
        if (object != null) {
            viewHierarchyEncoder.addPropertyKey("layoutParams");
            ((ViewGroup.LayoutParams)object).encode(viewHierarchyEncoder);
        }
        viewHierarchyEncoder.addProperty("scrolling:scrollX", this.mScrollX);
        viewHierarchyEncoder.addProperty("scrolling:scrollY", this.mScrollY);
        viewHierarchyEncoder.addProperty("padding:paddingLeft", this.mPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:paddingRight", this.mPaddingRight);
        viewHierarchyEncoder.addProperty("padding:paddingTop", this.mPaddingTop);
        viewHierarchyEncoder.addProperty("padding:paddingBottom", this.mPaddingBottom);
        viewHierarchyEncoder.addProperty("padding:userPaddingRight", this.mUserPaddingRight);
        viewHierarchyEncoder.addProperty("padding:userPaddingLeft", this.mUserPaddingLeft);
        viewHierarchyEncoder.addProperty("padding:userPaddingBottom", this.mUserPaddingBottom);
        viewHierarchyEncoder.addProperty("padding:userPaddingStart", this.mUserPaddingStart);
        viewHierarchyEncoder.addProperty("padding:userPaddingEnd", this.mUserPaddingEnd);
        viewHierarchyEncoder.addProperty("measurement:minHeight", this.mMinHeight);
        viewHierarchyEncoder.addProperty("measurement:minWidth", this.mMinWidth);
        viewHierarchyEncoder.addProperty("measurement:measuredWidth", this.mMeasuredWidth);
        viewHierarchyEncoder.addProperty("measurement:measuredHeight", this.mMeasuredHeight);
        viewHierarchyEncoder.addProperty("drawing:elevation", this.getElevation());
        viewHierarchyEncoder.addProperty("drawing:translationX", this.getTranslationX());
        viewHierarchyEncoder.addProperty("drawing:translationY", this.getTranslationY());
        viewHierarchyEncoder.addProperty("drawing:translationZ", this.getTranslationZ());
        viewHierarchyEncoder.addProperty("drawing:rotation", this.getRotation());
        viewHierarchyEncoder.addProperty("drawing:rotationX", this.getRotationX());
        viewHierarchyEncoder.addProperty("drawing:rotationY", this.getRotationY());
        viewHierarchyEncoder.addProperty("drawing:scaleX", this.getScaleX());
        viewHierarchyEncoder.addProperty("drawing:scaleY", this.getScaleY());
        viewHierarchyEncoder.addProperty("drawing:pivotX", this.getPivotX());
        viewHierarchyEncoder.addProperty("drawing:pivotY", this.getPivotY());
        object = this.mClipBounds;
        object = object == null ? null : ((Rect)object).toString();
        viewHierarchyEncoder.addProperty("drawing:clipBounds", (String)object);
        viewHierarchyEncoder.addProperty("drawing:opaque", this.isOpaque());
        viewHierarchyEncoder.addProperty("drawing:alpha", this.getAlpha());
        viewHierarchyEncoder.addProperty("drawing:transitionAlpha", this.getTransitionAlpha());
        viewHierarchyEncoder.addProperty("drawing:shadow", this.hasShadow());
        viewHierarchyEncoder.addProperty("drawing:solidColor", this.getSolidColor());
        viewHierarchyEncoder.addProperty("drawing:layerType", this.mLayerType);
        viewHierarchyEncoder.addProperty("drawing:willNotDraw", this.willNotDraw());
        viewHierarchyEncoder.addProperty("drawing:hardwareAccelerated", this.isHardwareAccelerated());
        viewHierarchyEncoder.addProperty("drawing:willNotCacheDrawing", this.willNotCacheDrawing());
        viewHierarchyEncoder.addProperty("drawing:drawingCacheEnabled", this.isDrawingCacheEnabled());
        viewHierarchyEncoder.addProperty("drawing:overlappingRendering", this.hasOverlappingRendering());
        viewHierarchyEncoder.addProperty("drawing:outlineAmbientShadowColor", this.getOutlineAmbientShadowColor());
        viewHierarchyEncoder.addProperty("drawing:outlineSpotShadowColor", this.getOutlineSpotShadowColor());
        viewHierarchyEncoder.addProperty("focus:hasFocus", this.hasFocus());
        viewHierarchyEncoder.addProperty("focus:isFocused", this.isFocused());
        viewHierarchyEncoder.addProperty("focus:focusable", this.getFocusable());
        viewHierarchyEncoder.addProperty("focus:isFocusable", this.isFocusable());
        viewHierarchyEncoder.addProperty("focus:isFocusableInTouchMode", this.isFocusableInTouchMode());
        viewHierarchyEncoder.addProperty("misc:clickable", this.isClickable());
        viewHierarchyEncoder.addProperty("misc:pressed", this.isPressed());
        viewHierarchyEncoder.addProperty("misc:selected", this.isSelected());
        viewHierarchyEncoder.addProperty("misc:touchMode", this.isInTouchMode());
        viewHierarchyEncoder.addProperty("misc:hovered", this.isHovered());
        viewHierarchyEncoder.addProperty("misc:activated", this.isActivated());
        viewHierarchyEncoder.addProperty("misc:visibility", this.getVisibility());
        viewHierarchyEncoder.addProperty("misc:fitsSystemWindows", this.getFitsSystemWindows());
        viewHierarchyEncoder.addProperty("misc:filterTouchesWhenObscured", this.getFilterTouchesWhenObscured());
        viewHierarchyEncoder.addProperty("misc:enabled", this.isEnabled());
        viewHierarchyEncoder.addProperty("misc:soundEffectsEnabled", this.isSoundEffectsEnabled());
        viewHierarchyEncoder.addProperty("misc:hapticFeedbackEnabled", this.isHapticFeedbackEnabled());
        object = this.getContext().getTheme();
        if (object != null) {
            viewHierarchyEncoder.addPropertyKey("theme");
            ((Resources.Theme)object).encode(viewHierarchyEncoder);
        }
        int n = (object = this.mAttributes) != null ? ((String[])object).length : 0;
        viewHierarchyEncoder.addProperty("meta:__attrCount__", n / 2);
        for (int i = 0; i < n; i += 2) {
            object = new StringBuilder();
            ((StringBuilder)object).append("meta:__attr__");
            ((StringBuilder)object).append(this.mAttributes[i]);
            viewHierarchyEncoder.addProperty(((StringBuilder)object).toString(), this.mAttributes[i + 1]);
        }
        viewHierarchyEncoder.addProperty("misc:scrollBarStyle", this.getScrollBarStyle());
        viewHierarchyEncoder.addProperty("text:textDirection", this.getTextDirection());
        viewHierarchyEncoder.addProperty("text:textAlignment", this.getTextAlignment());
        object = this.getContentDescription();
        object = object == null ? "" : object.toString();
        viewHierarchyEncoder.addProperty("accessibility:contentDescription", (String)object);
        viewHierarchyEncoder.addProperty("accessibility:labelFor", this.getLabelFor());
        viewHierarchyEncoder.addProperty("accessibility:importantForAccessibility", this.getImportantForAccessibility());
    }

    @UnsupportedAppUsage
    void ensureTransformationInfo() {
        if (this.mTransformationInfo == null) {
            this.mTransformationInfo = new TransformationInfo();
        }
    }

    public View findFocus() {
        View view = (this.mPrivateFlags & 2) != 0 ? this : null;
        return view;
    }

    View findKeyboardNavigationCluster() {
        Object object = this.mParent;
        if (object instanceof View) {
            if ((object = ((View)object).findKeyboardNavigationCluster()) != null) {
                return object;
            }
            if (this.isKeyboardNavigationCluster()) {
                return this;
            }
        }
        return null;
    }

    public void findNamedViews(Map<String, View> map) {
        String string2;
        if ((this.getVisibility() == 0 || this.mGhostView != null) && (string2 = this.getTransitionName()) != null) {
            map.put(string2, this);
        }
    }

    View findUserSetNextFocus(View view, int n) {
        if (n != 1) {
            if (n != 2) {
                if (n != 17) {
                    if (n != 33) {
                        if (n != 66) {
                            if (n != 130) {
                                return null;
                            }
                            n = this.mNextFocusDownId;
                            if (n == -1) {
                                return null;
                            }
                            return this.findViewInsideOutShouldExist(view, n);
                        }
                        n = this.mNextFocusRightId;
                        if (n == -1) {
                            return null;
                        }
                        return this.findViewInsideOutShouldExist(view, n);
                    }
                    n = this.mNextFocusUpId;
                    if (n == -1) {
                        return null;
                    }
                    return this.findViewInsideOutShouldExist(view, n);
                }
                n = this.mNextFocusLeftId;
                if (n == -1) {
                    return null;
                }
                return this.findViewInsideOutShouldExist(view, n);
            }
            n = this.mNextFocusForwardId;
            if (n == -1) {
                return null;
            }
            return this.findViewInsideOutShouldExist(view, n);
        }
        if (this.mID == -1) {
            return null;
        }
        return view.findViewByPredicateInsideOut(this, new Predicate<View>(){

            @Override
            public boolean test(View view) {
                boolean bl = view.mNextFocusForwardId == mID;
                return bl;
            }
        });
    }

    View findUserSetNextKeyboardNavigationCluster(View view, int n) {
        if (n != 1) {
            if (n != 2) {
                return null;
            }
            n = this.mNextClusterForwardId;
            if (n == -1) {
                return null;
            }
            return this.findViewInsideOutShouldExist(view, n);
        }
        if (this.mID == -1) {
            return null;
        }
        return view.findViewByPredicateInsideOut(this, new _$$Lambda$View$7kZ4TXHKswReUMQB8098MEBcx_U(this.mID));
    }

    public <T extends View> T findViewByAccessibilityIdTraversal(int n) {
        if (this.getAccessibilityViewId() == n) {
            return (T)this;
        }
        return null;
    }

    public <T extends View> T findViewByAutofillIdTraversal(int n) {
        if (this.getAutofillViewId() == n) {
            return (T)this;
        }
        return null;
    }

    public final <T extends View> T findViewById(int n) {
        if (n == -1) {
            return null;
        }
        return this.findViewTraversal(n);
    }

    public final <T extends View> T findViewByPredicate(Predicate<View> predicate) {
        return this.findViewByPredicateTraversal(predicate, null);
    }

    public final <T extends View> T findViewByPredicateInsideOut(View view, Predicate<View> predicate) {
        Object object = null;
        while ((object = (Object)view.findViewByPredicateTraversal(predicate, (View)object)) == null && view != this) {
            object = view.getParent();
            if (object != null && object instanceof View) {
                View view2 = (View)object;
                object = view;
                view = view2;
                continue;
            }
            return null;
        }
        return (T)object;
    }

    protected <T extends View> T findViewByPredicateTraversal(Predicate<View> predicate, View view) {
        if (predicate.test(this)) {
            return (T)this;
        }
        return null;
    }

    protected <T extends View> T findViewTraversal(int n) {
        if (n == this.mID) {
            return (T)this;
        }
        return null;
    }

    public final <T extends View> T findViewWithTag(Object object) {
        if (object == null) {
            return null;
        }
        return this.findViewWithTagTraversal(object);
    }

    protected <T extends View> T findViewWithTagTraversal(Object object) {
        if (object != null && object.equals(this.mTag)) {
            return (T)this;
        }
        return null;
    }

    public void findViewsWithText(ArrayList<View> arrayList, CharSequence charSequence, int n) {
        CharSequence charSequence2;
        if (this.getAccessibilityNodeProvider() != null) {
            if ((n & 4) != 0) {
                arrayList.add(this);
            }
        } else if ((n & 2) != 0 && charSequence != null && charSequence.length() > 0 && (charSequence2 = this.mContentDescription) != null && charSequence2.length() > 0) {
            charSequence = charSequence.toString().toLowerCase();
            if (this.mContentDescription.toString().toLowerCase().contains(charSequence)) {
                arrayList.add(this);
            }
        }
    }

    public void finishMovingTask() {
        try {
            this.mAttachInfo.mSession.finishMovingTask(this.mAttachInfo.mWindow);
        }
        catch (RemoteException remoteException) {
            Log.e(VIEW_LOG_TAG, "Unable to finish moving", remoteException);
        }
    }

    @Deprecated
    protected boolean fitSystemWindows(Rect rect) {
        int n = this.mPrivateFlags3;
        if ((n & 32) == 0) {
            if (rect == null) {
                return false;
            }
            try {
                this.mPrivateFlags3 = n | 64;
                WindowInsets windowInsets = new WindowInsets(rect);
                boolean bl = this.dispatchApplyWindowInsets(windowInsets).isConsumed();
                return bl;
            }
            finally {
                this.mPrivateFlags3 &= -65;
            }
        }
        return this.fitSystemWindowsInt(rect);
    }

    @UnsupportedAppUsage
    public boolean fitsSystemWindows() {
        return this.getFitsSystemWindows();
    }

    public View focusSearch(int n) {
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            return viewParent.focusSearch(this, n);
        }
        return null;
    }

    public void forceHasOverlappingRendering(boolean bl) {
        this.mPrivateFlags3 |= 16777216;
        this.mPrivateFlags3 = bl ? (this.mPrivateFlags3 |= 8388608) : (this.mPrivateFlags3 &= -8388609);
    }

    public void forceLayout() {
        LongSparseLongArray longSparseLongArray = this.mMeasureCache;
        if (longSparseLongArray != null) {
            longSparseLongArray.clear();
        }
        this.mPrivateFlags |= 4096;
        this.mPrivateFlags |= Integer.MIN_VALUE;
    }

    @UnsupportedAppUsage
    public boolean gatherTransparentRegion(Region region) {
        Object object = this.mAttachInfo;
        if (region != null && object != null) {
            if ((this.mPrivateFlags & 128) == 0) {
                object = ((AttachInfo)object).mTransparentLocation;
                this.getLocationInWindow((int[])object);
                int n = this.getZ() > 0.0f ? (int)this.getZ() : 0;
                region.op((int)(object[0] - n), (int)(object[1] - n), (int)(object[0] + this.mRight - this.mLeft + n), (int)(object[1] + this.mBottom - this.mTop + n * 3), Region.Op.DIFFERENCE);
            } else {
                object = this.mBackground;
                if (object != null && ((Drawable)object).getOpacity() != -2) {
                    this.applyDrawableToTransparentRegion(this.mBackground, region);
                }
                if ((object = this.mForegroundInfo) != null && ((ForegroundInfo)object).mDrawable != null && this.mForegroundInfo.mDrawable.getOpacity() != -2) {
                    this.applyDrawableToTransparentRegion(this.mForegroundInfo.mDrawable, region);
                }
                if ((object = this.mDefaultFocusHighlight) != null && ((Drawable)object).getOpacity() != -2) {
                    this.applyDrawableToTransparentRegion(this.mDefaultFocusHighlight, region);
                }
            }
        }
        return true;
    }

    public CharSequence getAccessibilityClassName() {
        return View.class.getName();
    }

    public AccessibilityDelegate getAccessibilityDelegate() {
        return this.mAccessibilityDelegate;
    }

    public int getAccessibilityLiveRegion() {
        return (this.mPrivateFlags2 & 25165824) >> 23;
    }

    public AccessibilityNodeProvider getAccessibilityNodeProvider() {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            return accessibilityDelegate.getAccessibilityNodeProvider(this);
        }
        return null;
    }

    public CharSequence getAccessibilityPaneTitle() {
        return this.mAccessibilityPaneTitle;
    }

    public int getAccessibilitySelectionEnd() {
        return this.getAccessibilitySelectionStart();
    }

    public int getAccessibilitySelectionStart() {
        return this.mAccessibilityCursorPosition;
    }

    public int getAccessibilityTraversalAfter() {
        return this.mAccessibilityTraversalAfterId;
    }

    public int getAccessibilityTraversalBefore() {
        return this.mAccessibilityTraversalBeforeId;
    }

    @UnsupportedAppUsage
    public int getAccessibilityViewId() {
        if (this.mAccessibilityViewId == -1) {
            int n = sNextAccessibilityViewId;
            sNextAccessibilityViewId = n + 1;
            this.mAccessibilityViewId = n;
        }
        return this.mAccessibilityViewId;
    }

    public int getAccessibilityWindowId() {
        AttachInfo attachInfo = this.mAttachInfo;
        int n = attachInfo != null ? attachInfo.mAccessibilityWindowId : -1;
        return n;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getAlpha() {
        TransformationInfo transformationInfo = this.mTransformationInfo;
        float f = transformationInfo != null ? transformationInfo.mAlpha : 1.0f;
        return f;
    }

    public Animation getAnimation() {
        return this.mCurrentAnimation;
    }

    public Matrix getAnimationMatrix() {
        return this.mRenderNode.getAnimationMatrix();
    }

    public IBinder getApplicationWindowToken() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            IBinder iBinder;
            IBinder iBinder2 = iBinder = attachInfo.mPanelParentWindowToken;
            if (iBinder == null) {
                iBinder2 = attachInfo.mWindowToken;
            }
            return iBinder2;
        }
        return null;
    }

    public int[] getAttributeResolutionStack(int n) {
        int[] arrn;
        if (sDebugViewAttributes && (arrn = this.mAttributeResolutionStacks) != null && arrn.get(n) != null) {
            int n2;
            int[] arrn2 = this.mAttributeResolutionStacks.get(n);
            n = n2 = arrn2.length;
            if (this.mSourceLayoutId != 0) {
                n = n2 + 1;
            }
            n2 = 0;
            arrn = new int[n];
            int n3 = this.mSourceLayoutId;
            n = n2;
            if (n3 != 0) {
                arrn[0] = n3;
                n = 0 + 1;
            }
            for (n2 = 0; n2 < arrn2.length; ++n2) {
                arrn[n] = arrn2[n2];
                ++n;
            }
            return arrn;
        }
        return new int[0];
    }

    public Map<Integer, Integer> getAttributeSourceResourceMap() {
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        if (sDebugViewAttributes && this.mAttributeSourceResId != null) {
            for (int i = 0; i < this.mAttributeSourceResId.size(); ++i) {
                hashMap.put(this.mAttributeSourceResId.keyAt(i), this.mAttributeSourceResId.valueAt(i));
            }
            return hashMap;
        }
        return hashMap;
    }

    @ViewDebug.ExportedProperty
    public String[] getAutofillHints() {
        return this.mAutofillHints;
    }

    public final AutofillId getAutofillId() {
        if (this.mAutofillId == null) {
            this.mAutofillId = new AutofillId(this.getAutofillViewId());
        }
        return this.mAutofillId;
    }

    public int getAutofillType() {
        return 0;
    }

    public AutofillValue getAutofillValue() {
        return null;
    }

    public int getAutofillViewId() {
        if (this.mAutofillViewId == -1) {
            this.mAutofillViewId = this.mContext.getNextAutofillId();
        }
        return this.mAutofillViewId;
    }

    public Drawable getBackground() {
        return this.mBackground;
    }

    public BlendMode getBackgroundTintBlendMode() {
        Object object = this.mBackgroundTint;
        object = object != null ? object.mBlendMode : null;
        return object;
    }

    public ColorStateList getBackgroundTintList() {
        Object object = this.mBackgroundTint;
        object = object != null ? ((TintInfo)object).mTintList : null;
        return object;
    }

    public PorterDuff.Mode getBackgroundTintMode() {
        Object object = this.mBackgroundTint;
        object = object != null && object.mBlendMode != null ? BlendMode.blendModeToPorterDuffMode(this.mBackgroundTint.mBlendMode) : null;
        return object;
    }

    @ViewDebug.ExportedProperty(category="layout")
    public int getBaseline() {
        return -1;
    }

    @ViewDebug.CapturedViewProperty
    public final int getBottom() {
        return this.mBottom;
    }

    protected float getBottomFadingEdgeStrength() {
        float f = this.computeVerticalScrollOffset() + this.computeVerticalScrollExtent() < this.computeVerticalScrollRange() ? 1.0f : 0.0f;
        return f;
    }

    protected int getBottomPaddingOffset() {
        return 0;
    }

    @UnsupportedAppUsage
    public void getBoundsOnScreen(Rect rect) {
        this.getBoundsOnScreen(rect, false);
    }

    @UnsupportedAppUsage
    public void getBoundsOnScreen(Rect rect, boolean bl) {
        Object object = this.mAttachInfo;
        if (object == null) {
            return;
        }
        object = ((AttachInfo)object).mTmpTransformRect;
        ((RectF)object).set(0.0f, 0.0f, this.mRight - this.mLeft, this.mBottom - this.mTop);
        this.mapRectFromViewToScreenCoords((RectF)object, bl);
        rect.set(Math.round(((RectF)object).left), Math.round(((RectF)object).top), Math.round(((RectF)object).right), Math.round(((RectF)object).bottom));
    }

    public float getCameraDistance() {
        float f = this.mResources.getDisplayMetrics().densityDpi;
        return this.mRenderNode.getCameraDistance() * f;
    }

    public Rect getClipBounds() {
        Rect rect = this.mClipBounds;
        rect = rect != null ? new Rect(rect) : null;
        return rect;
    }

    public boolean getClipBounds(Rect rect) {
        Rect rect2 = this.mClipBounds;
        if (rect2 != null) {
            rect.set(rect2);
            return true;
        }
        return false;
    }

    public final boolean getClipToOutline() {
        return this.mRenderNode.getClipToOutline();
    }

    public final ContentCaptureSession getContentCaptureSession() {
        ContentCaptureSession contentCaptureSession = this.mCachedContentCaptureSession;
        if (contentCaptureSession != null) {
            return contentCaptureSession;
        }
        this.mCachedContentCaptureSession = this.getAndCacheContentCaptureSession();
        return this.mCachedContentCaptureSession;
    }

    @ViewDebug.ExportedProperty(category="accessibility")
    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    @ViewDebug.CapturedViewProperty
    public final Context getContext() {
        return this.mContext;
    }

    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return null;
    }

    @ViewDebug.ExportedProperty(category="focus")
    public final boolean getDefaultFocusHighlightEnabled() {
        return this.mDefaultFocusHighlightEnabled;
    }

    public Display getDisplay() {
        Object object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mDisplay : null;
        return object;
    }

    public final int[] getDrawableState() {
        int[] arrn = this.mDrawableState;
        if (arrn != null && (this.mPrivateFlags & 1024) == 0) {
            return arrn;
        }
        this.mDrawableState = this.onCreateDrawableState(0);
        this.mPrivateFlags &= -1025;
        return this.mDrawableState;
    }

    @Deprecated
    public Bitmap getDrawingCache() {
        return this.getDrawingCache(false);
    }

    @Deprecated
    public Bitmap getDrawingCache(boolean bl) {
        int n = this.mViewFlags;
        if ((n & 131072) == 131072) {
            return null;
        }
        if ((n & 32768) == 32768) {
            this.buildDrawingCache(bl);
        }
        Bitmap bitmap = bl ? this.mDrawingCache : this.mUnscaledDrawingCache;
        return bitmap;
    }

    @Deprecated
    public int getDrawingCacheBackgroundColor() {
        return this.mDrawingCacheBackgroundColor;
    }

    @Deprecated
    public int getDrawingCacheQuality() {
        return this.mViewFlags & 1572864;
    }

    public void getDrawingRect(Rect rect) {
        int n;
        int n2;
        rect.left = n = this.mScrollX;
        rect.top = n2 = this.mScrollY;
        rect.right = n + (this.mRight - this.mLeft);
        rect.bottom = n2 + (this.mBottom - this.mTop);
    }

    public long getDrawingTime() {
        AttachInfo attachInfo = this.mAttachInfo;
        long l = attachInfo != null ? attachInfo.mDrawingTime : 0L;
        return l;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getElevation() {
        return this.mRenderNode.getElevation();
    }

    public int getExplicitStyle() {
        if (!sDebugViewAttributes) {
            return 0;
        }
        return this.mExplicitStyle;
    }

    protected int getFadeHeight(boolean bl) {
        int n;
        int n2 = n = this.mPaddingTop;
        if (bl) {
            n2 = n + this.getTopPaddingOffset();
        }
        return this.mBottom - this.mTop - this.mPaddingBottom - n2;
    }

    protected int getFadeTop(boolean bl) {
        int n;
        int n2 = n = this.mPaddingTop;
        if (bl) {
            n2 = n + this.getTopPaddingOffset();
        }
        return n2;
    }

    public int getFadingEdge() {
        return this.mViewFlags & 12288;
    }

    public int getFadingEdgeLength() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null && (this.mViewFlags & 12288) != 0) {
            return scrollabilityCache.fadingEdgeLength;
        }
        return 0;
    }

    @ViewDebug.ExportedProperty
    public boolean getFilterTouchesWhenObscured() {
        boolean bl = (this.mViewFlags & 1024) != 0;
        return bl;
    }

    @ViewDebug.ExportedProperty
    public boolean getFitsSystemWindows() {
        boolean bl = (this.mViewFlags & 2) == 2;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="focus", mapping={@ViewDebug.IntToString(from=0, to="NOT_FOCUSABLE"), @ViewDebug.IntToString(from=1, to="FOCUSABLE"), @ViewDebug.IntToString(from=16, to="FOCUSABLE_AUTO")})
    public int getFocusable() {
        int n = this.mViewFlags;
        n = (n & 16) > 0 ? 16 : (n &= 1);
        return n;
    }

    public ArrayList<View> getFocusables(int n) {
        ArrayList<View> arrayList = new ArrayList<View>(24);
        this.addFocusables(arrayList, n);
        return arrayList;
    }

    public void getFocusedRect(Rect rect) {
        this.getDrawingRect(rect);
    }

    public Drawable getForeground() {
        Object object = this.mForegroundInfo;
        object = object != null ? ((ForegroundInfo)object).mDrawable : null;
        return object;
    }

    public int getForegroundGravity() {
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        int n = foregroundInfo != null ? foregroundInfo.mGravity : 8388659;
        return n;
    }

    public BlendMode getForegroundTintBlendMode() {
        Object object = this.mForegroundInfo;
        object = object != null && object.mTintInfo != null ? ForegroundInfo.access$2600((ForegroundInfo)this.mForegroundInfo).mBlendMode : null;
        return object;
    }

    public ColorStateList getForegroundTintList() {
        Object object = this.mForegroundInfo;
        object = object != null && ((ForegroundInfo)object).mTintInfo != null ? ForegroundInfo.access$2600((ForegroundInfo)this.mForegroundInfo).mTintList : null;
        return object;
    }

    public PorterDuff.Mode getForegroundTintMode() {
        Object object = this.mForegroundInfo;
        object = object != null && object.mTintInfo != null ? ForegroundInfo.access$2600((ForegroundInfo)this.mForegroundInfo).mBlendMode : null;
        if (object != null) {
            return BlendMode.blendModeToPorterDuffMode((BlendMode)((Object)object));
        }
        return null;
    }

    public final boolean getGlobalVisibleRect(Rect rect) {
        return this.getGlobalVisibleRect(rect, null);
    }

    public boolean getGlobalVisibleRect(Rect rect, Point point) {
        int n = this.mRight - this.mLeft;
        int n2 = this.mBottom - this.mTop;
        boolean bl = false;
        if (n > 0 && n2 > 0) {
            ViewParent viewParent;
            rect.set(0, 0, n, n2);
            if (point != null) {
                point.set(-this.mScrollX, -this.mScrollY);
            }
            if ((viewParent = this.mParent) == null || viewParent.getChildVisibleRect(this, rect, point)) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public Handler getHandler() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler;
        }
        return null;
    }

    public final boolean getHasOverlappingRendering() {
        int n = this.mPrivateFlags3;
        boolean bl = (16777216 & n) != 0 ? (n & 8388608) != 0 : this.hasOverlappingRendering();
        return bl;
    }

    @ViewDebug.ExportedProperty(category="layout")
    public final int getHeight() {
        return this.mBottom - this.mTop;
    }

    public void getHitRect(Rect rect) {
        Object object;
        if (!this.hasIdentityMatrix() && (object = this.mAttachInfo) != null) {
            object = ((AttachInfo)object).mTmpTransformRect;
            ((RectF)object).set(0.0f, 0.0f, this.getWidth(), this.getHeight());
            this.getMatrix().mapRect((RectF)object);
            rect.set((int)((RectF)object).left + this.mLeft, (int)((RectF)object).top + this.mTop, (int)((RectF)object).right + this.mLeft, (int)((RectF)object).bottom + this.mTop);
        } else {
            rect.set(this.mLeft, this.mTop, this.mRight, this.mBottom);
        }
    }

    public int getHorizontalFadingEdgeLength() {
        ScrollabilityCache scrollabilityCache;
        if (this.isHorizontalFadingEdgeEnabled() && (scrollabilityCache = this.mScrollCache) != null) {
            return scrollabilityCache.fadingEdgeLength;
        }
        return 0;
    }

    @UnsupportedAppUsage
    protected float getHorizontalScrollFactor() {
        return this.getVerticalScrollFactor();
    }

    protected int getHorizontalScrollbarHeight() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null) {
            ScrollBarDrawable scrollBarDrawable = scrollabilityCache.scrollBar;
            if (scrollBarDrawable != null) {
                int n;
                int n2 = n = scrollBarDrawable.getSize(false);
                if (n <= 0) {
                    n2 = scrollabilityCache.scrollBarSize;
                }
                return n2;
            }
            return 0;
        }
        return 0;
    }

    public Drawable getHorizontalScrollbarThumbDrawable() {
        Object object = this.mScrollCache;
        object = object != null ? ((ScrollabilityCache)object).scrollBar.getHorizontalThumbDrawable() : null;
        return object;
    }

    public Drawable getHorizontalScrollbarTrackDrawable() {
        Object object = this.mScrollCache;
        object = object != null ? ((ScrollabilityCache)object).scrollBar.getHorizontalTrackDrawable() : null;
        return object;
    }

    public void getHotspotBounds(Rect rect) {
        Drawable drawable2 = this.getBackground();
        if (drawable2 != null) {
            drawable2.getHotspotBounds(rect);
        } else {
            this.getBoundsOnScreen(rect);
        }
    }

    @ViewDebug.CapturedViewProperty
    public int getId() {
        return this.mID;
    }

    @ViewDebug.ExportedProperty(category="accessibility", mapping={@ViewDebug.IntToString(from=0, to="auto"), @ViewDebug.IntToString(from=1, to="yes"), @ViewDebug.IntToString(from=2, to="no"), @ViewDebug.IntToString(from=4, to="noHideDescendants")})
    public int getImportantForAccessibility() {
        return (this.mPrivateFlags2 & 7340032) >> 20;
    }

    @ViewDebug.ExportedProperty(mapping={@ViewDebug.IntToString(from=0, to="auto"), @ViewDebug.IntToString(from=1, to="yes"), @ViewDebug.IntToString(from=2, to="no"), @ViewDebug.IntToString(from=4, to="yesExcludeDescendants"), @ViewDebug.IntToString(from=8, to="noExcludeDescendants")})
    public int getImportantForAutofill() {
        return (this.mPrivateFlags3 & 7864320) >> 19;
    }

    @UnsupportedAppUsage
    public final Matrix getInverseMatrix() {
        this.ensureTransformationInfo();
        if (this.mTransformationInfo.mInverseMatrix == null) {
            this.mTransformationInfo.mInverseMatrix = new Matrix();
        }
        Matrix matrix = this.mTransformationInfo.mInverseMatrix;
        this.mRenderNode.getInverseMatrix(matrix);
        return matrix;
    }

    @UnsupportedAppUsage
    public CharSequence getIterableTextForAccessibility() {
        return this.getContentDescription();
    }

    @UnsupportedAppUsage
    public AccessibilityIterators.TextSegmentIterator getIteratorForGranularity(int n) {
        if (n != 1) {
            if (n != 2) {
                CharSequence charSequence;
                if (n == 8 && (charSequence = this.getIterableTextForAccessibility()) != null && charSequence.length() > 0) {
                    AccessibilityIterators.ParagraphTextSegmentIterator paragraphTextSegmentIterator = AccessibilityIterators.ParagraphTextSegmentIterator.getInstance();
                    paragraphTextSegmentIterator.initialize(charSequence.toString());
                    return paragraphTextSegmentIterator;
                }
            } else {
                CharSequence charSequence = this.getIterableTextForAccessibility();
                if (charSequence != null && charSequence.length() > 0) {
                    AccessibilityIterators.WordTextSegmentIterator wordTextSegmentIterator = AccessibilityIterators.WordTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
                    wordTextSegmentIterator.initialize(charSequence.toString());
                    return wordTextSegmentIterator;
                }
            }
        } else {
            CharSequence charSequence = this.getIterableTextForAccessibility();
            if (charSequence != null && charSequence.length() > 0) {
                AccessibilityIterators.CharacterTextSegmentIterator characterTextSegmentIterator = AccessibilityIterators.CharacterTextSegmentIterator.getInstance(this.mContext.getResources().getConfiguration().locale);
                characterTextSegmentIterator.initialize(charSequence.toString());
                return characterTextSegmentIterator;
            }
        }
        return null;
    }

    public boolean getKeepScreenOn() {
        boolean bl = (this.mViewFlags & 67108864) != 0;
        return bl;
    }

    public KeyEvent.DispatcherState getKeyDispatcherState() {
        Object object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mKeyDispatchState : null;
        return object;
    }

    @ViewDebug.ExportedProperty(category="accessibility")
    public int getLabelFor() {
        return this.mLabelForId;
    }

    public int getLayerType() {
        return this.mLayerType;
    }

    @ViewDebug.ExportedProperty(category="layout", mapping={@ViewDebug.IntToString(from=0, to="RESOLVED_DIRECTION_LTR"), @ViewDebug.IntToString(from=1, to="RESOLVED_DIRECTION_RTL")})
    public int getLayoutDirection() {
        int n;
        block1 : {
            int n2 = this.getContext().getApplicationInfo().targetSdkVersion;
            n = 0;
            if (n2 < 17) {
                this.mPrivateFlags2 |= 32;
                return 0;
            }
            if ((this.mPrivateFlags2 & 16) != 16) break block1;
            n = 1;
        }
        return n;
    }

    @ViewDebug.ExportedProperty(deepExport=true, prefix="layout_")
    public ViewGroup.LayoutParams getLayoutParams() {
        return this.mLayoutParams;
    }

    @ViewDebug.CapturedViewProperty
    public final int getLeft() {
        return this.mLeft;
    }

    protected float getLeftFadingEdgeStrength() {
        float f = this.computeHorizontalScrollOffset() > 0 ? 1.0f : 0.0f;
        return f;
    }

    protected int getLeftPaddingOffset() {
        return 0;
    }

    @UnsupportedAppUsage
    ListenerInfo getListenerInfo() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null) {
            return listenerInfo;
        }
        this.mListenerInfo = new ListenerInfo();
        return this.mListenerInfo;
    }

    public final boolean getLocalVisibleRect(Rect rect) {
        Object object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mPoint : new Point();
        if (this.getGlobalVisibleRect(rect, (Point)object)) {
            rect.offset(-((Point)object).x, -((Point)object).y);
            return true;
        }
        return false;
    }

    public void getLocationInSurface(int[] arrn) {
        this.getLocationInWindow(arrn);
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mViewRootImpl != null) {
            arrn[0] = arrn[0] + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.left;
            arrn[1] = arrn[1] + this.mAttachInfo.mViewRootImpl.mWindowAttributes.surfaceInsets.top;
        }
    }

    public void getLocationInWindow(int[] arrn) {
        if (arrn != null && arrn.length >= 2) {
            arrn[0] = 0;
            arrn[1] = 0;
            this.transformFromViewToWindowSpace(arrn);
            return;
        }
        throw new IllegalArgumentException("outLocation must be an array of two integers");
    }

    public void getLocationOnScreen(int[] arrn) {
        this.getLocationInWindow(arrn);
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            arrn[0] = arrn[0] + attachInfo.mWindowLeft;
            arrn[1] = arrn[1] + attachInfo.mWindowTop;
        }
    }

    @ViewDebug.ExportedProperty(category="layout", indexMapping={@ViewDebug.IntToString(from=0, to="x"), @ViewDebug.IntToString(from=1, to="y")})
    @UnsupportedAppUsage
    public int[] getLocationOnScreen() {
        int[] arrn = new int[2];
        this.getLocationOnScreen(arrn);
        return arrn;
    }

    public Matrix getMatrix() {
        this.ensureTransformationInfo();
        Matrix matrix = this.mTransformationInfo.mMatrix;
        this.mRenderNode.getMatrix(matrix);
        return matrix;
    }

    public final int getMeasuredHeight() {
        return this.mMeasuredHeight & 16777215;
    }

    @ViewDebug.ExportedProperty(category="measurement", flagMapping={@ViewDebug.FlagToString(equals=16777216, mask=-16777216, name="MEASURED_STATE_TOO_SMALL")})
    public final int getMeasuredHeightAndState() {
        return this.mMeasuredHeight;
    }

    public final int getMeasuredState() {
        return this.mMeasuredWidth & -16777216 | this.mMeasuredHeight >> 16 & -256;
    }

    public final int getMeasuredWidth() {
        return this.mMeasuredWidth & 16777215;
    }

    @ViewDebug.ExportedProperty(category="measurement", flagMapping={@ViewDebug.FlagToString(equals=16777216, mask=-16777216, name="MEASURED_STATE_TOO_SMALL")})
    public final int getMeasuredWidthAndState() {
        return this.mMeasuredWidth;
    }

    public int getMinimumHeight() {
        return this.mMinHeight;
    }

    public int getMinimumWidth() {
        return this.mMinWidth;
    }

    public int getNextClusterForwardId() {
        return this.mNextClusterForwardId;
    }

    public int getNextFocusDownId() {
        return this.mNextFocusDownId;
    }

    public int getNextFocusForwardId() {
        return this.mNextFocusForwardId;
    }

    public int getNextFocusLeftId() {
        return this.mNextFocusLeftId;
    }

    public int getNextFocusRightId() {
        return this.mNextFocusRightId;
    }

    public int getNextFocusUpId() {
        return this.mNextFocusUpId;
    }

    public OnFocusChangeListener getOnFocusChangeListener() {
        Object object = this.mListenerInfo;
        object = object != null ? ((ListenerInfo)object).mOnFocusChangeListener : null;
        return object;
    }

    public Insets getOpticalInsets() {
        if (this.mLayoutInsets == null) {
            this.mLayoutInsets = this.computeOpticalInsets();
        }
        return this.mLayoutInsets;
    }

    public int getOutlineAmbientShadowColor() {
        return this.mRenderNode.getAmbientShadowColor();
    }

    public ViewOutlineProvider getOutlineProvider() {
        return this.mOutlineProvider;
    }

    public int getOutlineSpotShadowColor() {
        return this.mRenderNode.getSpotShadowColor();
    }

    public void getOutsets(Rect rect) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            rect.set(attachInfo.mOutsets);
        } else {
            rect.setEmpty();
        }
    }

    public int getOverScrollMode() {
        return this.mOverScrollMode;
    }

    public ViewOverlay getOverlay() {
        if (this.mOverlay == null) {
            this.mOverlay = new ViewOverlay(this.mContext, this);
        }
        return this.mOverlay;
    }

    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public int getPaddingEnd() {
        if (!this.isPaddingResolved()) {
            this.resolvePadding();
        }
        int n = this.getLayoutDirection() == 1 ? this.mPaddingLeft : this.mPaddingRight;
        return n;
    }

    public int getPaddingLeft() {
        if (!this.isPaddingResolved()) {
            this.resolvePadding();
        }
        return this.mPaddingLeft;
    }

    public int getPaddingRight() {
        if (!this.isPaddingResolved()) {
            this.resolvePadding();
        }
        return this.mPaddingRight;
    }

    public int getPaddingStart() {
        if (!this.isPaddingResolved()) {
            this.resolvePadding();
        }
        int n = this.getLayoutDirection() == 1 ? this.mPaddingRight : this.mPaddingLeft;
        return n;
    }

    public int getPaddingTop() {
        return this.mPaddingTop;
    }

    public final ViewParent getParent() {
        return this.mParent;
    }

    public ViewParent getParentForAccessibility() {
        ViewParent viewParent = this.mParent;
        if (viewParent instanceof View) {
            if (((View)((Object)viewParent)).includeForAccessibility()) {
                return this.mParent;
            }
            return this.mParent.getParentForAccessibility();
        }
        return null;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getPivotX() {
        return this.mRenderNode.getPivotX();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getPivotY() {
        return this.mRenderNode.getPivotY();
    }

    public PointerIcon getPointerIcon() {
        return this.mPointerIcon;
    }

    @ViewDebug.ExportedProperty(category="layout", mapping={@ViewDebug.IntToString(from=0, to="LTR"), @ViewDebug.IntToString(from=1, to="RTL"), @ViewDebug.IntToString(from=2, to="INHERIT"), @ViewDebug.IntToString(from=3, to="LOCALE")})
    public int getRawLayoutDirection() {
        return (this.mPrivateFlags2 & 12) >> 2;
    }

    @ViewDebug.ExportedProperty(category="text", mapping={@ViewDebug.IntToString(from=0, to="INHERIT"), @ViewDebug.IntToString(from=1, to="GRAVITY"), @ViewDebug.IntToString(from=2, to="TEXT_START"), @ViewDebug.IntToString(from=3, to="TEXT_END"), @ViewDebug.IntToString(from=4, to="CENTER"), @ViewDebug.IntToString(from=5, to="VIEW_START"), @ViewDebug.IntToString(from=6, to="VIEW_END")})
    @UnsupportedAppUsage
    public int getRawTextAlignment() {
        return (this.mPrivateFlags2 & 57344) >> 13;
    }

    @ViewDebug.ExportedProperty(category="text", mapping={@ViewDebug.IntToString(from=0, to="INHERIT"), @ViewDebug.IntToString(from=1, to="FIRST_STRONG"), @ViewDebug.IntToString(from=2, to="ANY_RTL"), @ViewDebug.IntToString(from=3, to="LTR"), @ViewDebug.IntToString(from=4, to="RTL"), @ViewDebug.IntToString(from=5, to="LOCALE"), @ViewDebug.IntToString(from=6, to="FIRST_STRONG_LTR"), @ViewDebug.IntToString(from=7, to="FIRST_STRONG_RTL")})
    @UnsupportedAppUsage
    public int getRawTextDirection() {
        return (this.mPrivateFlags2 & 448) >> 6;
    }

    public Resources getResources() {
        return this.mResources;
    }

    public final boolean getRevealOnFocusHint() {
        boolean bl = (this.mPrivateFlags3 & 67108864) == 0;
        return bl;
    }

    @ViewDebug.CapturedViewProperty
    public final int getRight() {
        return this.mRight;
    }

    protected float getRightFadingEdgeStrength() {
        float f = this.computeHorizontalScrollOffset() + this.computeHorizontalScrollExtent() < this.computeHorizontalScrollRange() ? 1.0f : 0.0f;
        return f;
    }

    protected int getRightPaddingOffset() {
        return 0;
    }

    public View getRootView() {
        ViewParent viewParent;
        Object object = this.mAttachInfo;
        if (object != null && (object = ((AttachInfo)object).mRootView) != null) {
            return object;
        }
        object = this;
        while ((viewParent = ((View)object).mParent) != null && viewParent instanceof View) {
            object = (View)((Object)viewParent);
        }
        return object;
    }

    public WindowInsets getRootWindowInsets() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mViewRootImpl.getWindowInsets(false);
        }
        return null;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getRotation() {
        return this.mRenderNode.getRotationZ();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getRotationX() {
        return this.mRenderNode.getRotationX();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getRotationY() {
        return this.mRenderNode.getRotationY();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getScaleX() {
        return this.mRenderNode.getScaleX();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getScaleY() {
        return this.mRenderNode.getScaleY();
    }

    public int getScrollBarDefaultDelayBeforeFade() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        int n = scrollabilityCache == null ? ViewConfiguration.getScrollDefaultDelay() : scrollabilityCache.scrollBarDefaultDelayBeforeFade;
        return n;
    }

    public int getScrollBarFadeDuration() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        int n = scrollabilityCache == null ? ViewConfiguration.getScrollBarFadeDuration() : scrollabilityCache.scrollBarFadeDuration;
        return n;
    }

    public int getScrollBarSize() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        int n = scrollabilityCache == null ? ViewConfiguration.get(this.mContext).getScaledScrollBarSize() : scrollabilityCache.scrollBarSize;
        return n;
    }

    @ViewDebug.ExportedProperty(mapping={@ViewDebug.IntToString(from=0, to="INSIDE_OVERLAY"), @ViewDebug.IntToString(from=16777216, to="INSIDE_INSET"), @ViewDebug.IntToString(from=33554432, to="OUTSIDE_OVERLAY"), @ViewDebug.IntToString(from=50331648, to="OUTSIDE_INSET")})
    public int getScrollBarStyle() {
        return this.mViewFlags & 50331648;
    }

    void getScrollIndicatorBounds(Rect rect) {
        int n;
        rect.left = n = this.mScrollX;
        rect.right = n + this.mRight - this.mLeft;
        rect.top = n = this.mScrollY;
        rect.bottom = n + this.mBottom - this.mTop;
    }

    public int getScrollIndicators() {
        return (this.mPrivateFlags3 & 16128) >>> 8;
    }

    public final int getScrollX() {
        return this.mScrollX;
    }

    public final int getScrollY() {
        return this.mScrollY;
    }

    View getSelfOrParentImportantForA11y() {
        if (this.isImportantForAccessibility()) {
            return this;
        }
        ViewParent viewParent = this.getParentForAccessibility();
        if (viewParent instanceof View) {
            return (View)((Object)viewParent);
        }
        return null;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public int getSolidColor() {
        return 0;
    }

    public int getSourceLayoutResId() {
        return this.mSourceLayoutId;
    }

    public StateListAnimator getStateListAnimator() {
        return this.mStateListAnimator;
    }

    protected int getSuggestedMinimumHeight() {
        Drawable drawable2 = this.mBackground;
        int n = drawable2 == null ? this.mMinHeight : Math.max(this.mMinHeight, drawable2.getMinimumHeight());
        return n;
    }

    protected int getSuggestedMinimumWidth() {
        Drawable drawable2 = this.mBackground;
        int n = drawable2 == null ? this.mMinWidth : Math.max(this.mMinWidth, drawable2.getMinimumWidth());
        return n;
    }

    public List<Rect> getSystemGestureExclusionRects() {
        Object object = this.mListenerInfo;
        if (object != null && (object = ((ListenerInfo)object).mSystemGestureExclusionRects) != null) {
            return object;
        }
        return Collections.emptyList();
    }

    public int getSystemUiVisibility() {
        return this.mSystemUiVisibility;
    }

    @ViewDebug.ExportedProperty
    public Object getTag() {
        return this.mTag;
    }

    public Object getTag(int n) {
        SparseArray<Object> sparseArray = this.mKeyedTags;
        if (sparseArray != null) {
            return sparseArray.get(n);
        }
        return null;
    }

    @ViewDebug.ExportedProperty(category="text", mapping={@ViewDebug.IntToString(from=0, to="INHERIT"), @ViewDebug.IntToString(from=1, to="GRAVITY"), @ViewDebug.IntToString(from=2, to="TEXT_START"), @ViewDebug.IntToString(from=3, to="TEXT_END"), @ViewDebug.IntToString(from=4, to="CENTER"), @ViewDebug.IntToString(from=5, to="VIEW_START"), @ViewDebug.IntToString(from=6, to="VIEW_END")})
    public int getTextAlignment() {
        return (this.mPrivateFlags2 & 917504) >> 17;
    }

    @ViewDebug.ExportedProperty(category="text", mapping={@ViewDebug.IntToString(from=0, to="INHERIT"), @ViewDebug.IntToString(from=1, to="FIRST_STRONG"), @ViewDebug.IntToString(from=2, to="ANY_RTL"), @ViewDebug.IntToString(from=3, to="LTR"), @ViewDebug.IntToString(from=4, to="RTL"), @ViewDebug.IntToString(from=5, to="LOCALE"), @ViewDebug.IntToString(from=6, to="FIRST_STRONG_LTR"), @ViewDebug.IntToString(from=7, to="FIRST_STRONG_RTL")})
    public int getTextDirection() {
        return (this.mPrivateFlags2 & 7168) >> 10;
    }

    @UnsupportedAppUsage
    public ThreadedRenderer getThreadedRenderer() {
        Object object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mThreadedRenderer : null;
        return object;
    }

    public CharSequence getTooltip() {
        return this.getTooltipText();
    }

    public CharSequence getTooltipText() {
        Object object = this.mTooltipInfo;
        object = object != null ? ((TooltipInfo)object).mTooltipText : null;
        return object;
    }

    public View getTooltipView() {
        TooltipInfo tooltipInfo = this.mTooltipInfo;
        if (tooltipInfo != null && tooltipInfo.mTooltipPopup != null) {
            return this.mTooltipInfo.mTooltipPopup.getContentView();
        }
        return null;
    }

    @ViewDebug.CapturedViewProperty
    public final int getTop() {
        return this.mTop;
    }

    protected float getTopFadingEdgeStrength() {
        float f = this.computeVerticalScrollOffset() > 0 ? 1.0f : 0.0f;
        return f;
    }

    protected int getTopPaddingOffset() {
        return 0;
    }

    public TouchDelegate getTouchDelegate() {
        return this.mTouchDelegate;
    }

    public ArrayList<View> getTouchables() {
        ArrayList<View> arrayList = new ArrayList<View>();
        this.addTouchables(arrayList);
        return arrayList;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getTransitionAlpha() {
        TransformationInfo transformationInfo = this.mTransformationInfo;
        float f = transformationInfo != null ? transformationInfo.mTransitionAlpha : 1.0f;
        return f;
    }

    @ViewDebug.ExportedProperty
    public String getTransitionName() {
        return this.mTransitionName;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getTranslationX() {
        return this.mRenderNode.getTranslationX();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getTranslationY() {
        return this.mRenderNode.getTranslationY();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getTranslationZ() {
        return this.mRenderNode.getTranslationZ();
    }

    public long getUniqueDrawingId() {
        return this.mRenderNode.getUniqueId();
    }

    public int getVerticalFadingEdgeLength() {
        ScrollabilityCache scrollabilityCache;
        if (this.isVerticalFadingEdgeEnabled() && (scrollabilityCache = this.mScrollCache) != null) {
            return scrollabilityCache.fadingEdgeLength;
        }
        return 0;
    }

    @UnsupportedAppUsage
    protected float getVerticalScrollFactor() {
        if (this.mVerticalScrollFactor == 0.0f) {
            TypedValue typedValue = new TypedValue();
            if (this.mContext.getTheme().resolveAttribute(16842829, typedValue, true)) {
                this.mVerticalScrollFactor = typedValue.getDimension(this.mContext.getResources().getDisplayMetrics());
            } else {
                throw new IllegalStateException("Expected theme to define listPreferredItemHeight.");
            }
        }
        return this.mVerticalScrollFactor;
    }

    public int getVerticalScrollbarPosition() {
        return this.mVerticalScrollbarPosition;
    }

    public Drawable getVerticalScrollbarThumbDrawable() {
        Object object = this.mScrollCache;
        object = object != null ? ((ScrollabilityCache)object).scrollBar.getVerticalThumbDrawable() : null;
        return object;
    }

    public Drawable getVerticalScrollbarTrackDrawable() {
        Object object = this.mScrollCache;
        object = object != null ? ((ScrollabilityCache)object).scrollBar.getVerticalTrackDrawable() : null;
        return object;
    }

    public int getVerticalScrollbarWidth() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache != null) {
            ScrollBarDrawable scrollBarDrawable = scrollabilityCache.scrollBar;
            if (scrollBarDrawable != null) {
                int n;
                int n2 = n = scrollBarDrawable.getSize(true);
                if (n <= 0) {
                    n2 = scrollabilityCache.scrollBarSize;
                }
                return n2;
            }
            return 0;
        }
        return 0;
    }

    @UnsupportedAppUsage
    public ViewRootImpl getViewRootImpl() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mViewRootImpl;
        }
        return null;
    }

    public ViewTreeObserver getViewTreeObserver() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mTreeObserver;
        }
        if (this.mFloatingTreeObserver == null) {
            this.mFloatingTreeObserver = new ViewTreeObserver(this.mContext);
        }
        return this.mFloatingTreeObserver;
    }

    @ViewDebug.ExportedProperty(mapping={@ViewDebug.IntToString(from=0, to="VISIBLE"), @ViewDebug.IntToString(from=4, to="INVISIBLE"), @ViewDebug.IntToString(from=8, to="GONE")})
    public int getVisibility() {
        return this.mViewFlags & 12;
    }

    @ViewDebug.ExportedProperty(category="layout")
    public final int getWidth() {
        return this.mRight - this.mLeft;
    }

    protected IWindow getWindow() {
        Object object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mWindow : null;
        return object;
    }

    protected int getWindowAttachCount() {
        return this.mWindowAttachCount;
    }

    @UnsupportedAppUsage
    public void getWindowDisplayFrame(Rect rect) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            try {
                attachInfo.mSession.getDisplayFrame(this.mAttachInfo.mWindow, rect);
                return;
            }
            catch (RemoteException remoteException) {
                return;
            }
        }
        DisplayManagerGlobal.getInstance().getRealDisplay(0).getRectSize(rect);
    }

    public WindowId getWindowId() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo == null) {
            return null;
        }
        if (attachInfo.mWindowId == null) {
            try {
                attachInfo.mIWindowId = attachInfo.mSession.getWindowId(attachInfo.mWindowToken);
                if (attachInfo.mIWindowId != null) {
                    WindowId windowId;
                    attachInfo.mWindowId = windowId = new WindowId(attachInfo.mIWindowId);
                }
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
        }
        return attachInfo.mWindowId;
    }

    public WindowInsetsController getWindowInsetsController() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mViewRootImpl.getInsetsController();
        }
        return null;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    IWindowSession getWindowSession() {
        Object object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mSession : null;
        return object;
    }

    public int getWindowSystemUiVisibility() {
        AttachInfo attachInfo = this.mAttachInfo;
        int n = attachInfo != null ? attachInfo.mSystemUiVisibility : 0;
        return n;
    }

    public IBinder getWindowToken() {
        Object object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mWindowToken : null;
        return object;
    }

    public int getWindowVisibility() {
        AttachInfo attachInfo = this.mAttachInfo;
        int n = attachInfo != null ? attachInfo.mWindowVisibility : 8;
        return n;
    }

    public void getWindowVisibleDisplayFrame(Rect rect) {
        Object object = this.mAttachInfo;
        if (object != null) {
            try {
                ((AttachInfo)object).mSession.getDisplayFrame(this.mAttachInfo.mWindow, rect);
                object = this.mAttachInfo.mVisibleInsets;
                rect.left += ((Rect)object).left;
                rect.top += ((Rect)object).top;
                rect.right -= ((Rect)object).right;
                rect.bottom -= ((Rect)object).bottom;
                return;
            }
            catch (RemoteException remoteException) {
                return;
            }
        }
        DisplayManagerGlobal.getInstance().getRealDisplay(0).getRectSize(rect);
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getX() {
        return (float)this.mLeft + this.getTranslationX();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getY() {
        return (float)this.mTop + this.getTranslationY();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public float getZ() {
        return this.getElevation() + this.getTranslationZ();
    }

    void handleFocusGainInternal(int n, Rect rect) {
        int n2 = this.mPrivateFlags;
        if ((n2 & 2) == 0) {
            this.mPrivateFlags = n2 | 2;
            View view = this.mAttachInfo != null ? this.getRootView().findFocus() : null;
            Object object = this.mParent;
            if (object != null) {
                object.requestChildFocus(this, this);
                this.updateFocusedInCluster(view, n);
            }
            if ((object = this.mAttachInfo) != null) {
                ((AttachInfo)object).mTreeObserver.dispatchOnGlobalFocusChange(view, this);
            }
            this.onFocusChanged(true, n, rect);
            this.refreshDrawableState();
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    protected boolean handleScrollBarDragging(MotionEvent var1_1) {
        if (this.mScrollCache == null) {
            return false;
        }
        var2_2 = var1_1.getX();
        var3_3 = var1_1.getY();
        var4_4 = var1_1.getAction();
        if ((this.mScrollCache.mScrollBarDraggingState != 0 || var4_4 == 0) && var1_1.isFromSource(8194) && var1_1.isButtonPressed(1)) {
            if (var4_4 != 0) {
                if (var4_4 == 2) {
                    if (this.mScrollCache.mScrollBarDraggingState == 0) {
                        return false;
                    }
                    if (this.mScrollCache.mScrollBarDraggingState == 1) {
                        var1_1 = this.mScrollCache.mScrollBarBounds;
                        this.getVerticalScrollBarBounds((Rect)var1_1, null);
                        var4_4 = this.computeVerticalScrollRange();
                        var5_5 = this.computeVerticalScrollOffset();
                        var6_7 = this.computeVerticalScrollExtent();
                        var7_9 = ScrollBarUtils.getThumbLength(var1_1.height(), var1_1.width(), var6_7, var4_4);
                        var5_5 = ScrollBarUtils.getThumbOffset(var1_1.height(), var7_9, var6_7, var4_4, var5_5);
                        var8_11 = this.mScrollCache.mScrollBarDraggingPos;
                        var2_2 = var1_1.height() - var7_9;
                        var8_11 = Math.min(Math.max((float)var5_5 + (var3_3 - var8_11), 0.0f), var2_2);
                        var7_9 = this.getHeight();
                        if (Math.round(var8_11) == var5_5) return true;
                        if (!(var2_2 > 0.0f)) return true;
                        if (var7_9 <= 0) return true;
                        if (var6_7 <= 0) return true;
                        if ((var4_4 = Math.round((float)(var4_4 - var6_7) / ((float)var6_7 / (float)var7_9) * (var8_11 / var2_2))) == this.getScrollY()) return true;
                        this.mScrollCache.mScrollBarDraggingPos = var3_3;
                        this.setScrollY(var4_4);
                        return true;
                    }
                    if (this.mScrollCache.mScrollBarDraggingState == 2) {
                        var1_1 = this.mScrollCache.mScrollBarBounds;
                        this.getHorizontalScrollBarBounds((Rect)var1_1, null);
                        var4_4 = this.computeHorizontalScrollRange();
                        var7_10 = this.computeHorizontalScrollOffset();
                        var6_8 = this.computeHorizontalScrollExtent();
                        var5_6 = ScrollBarUtils.getThumbLength(var1_1.width(), var1_1.height(), var6_8, var4_4);
                        var7_10 = ScrollBarUtils.getThumbOffset(var1_1.width(), var5_6, var6_8, var4_4, var7_10);
                        var8_12 = this.mScrollCache.mScrollBarDraggingPos;
                        var3_3 = var1_1.width() - var5_6;
                        var8_12 = Math.min(Math.max((float)var7_10 + (var2_2 - var8_12), 0.0f), var3_3);
                        var5_6 = this.getWidth();
                        if (Math.round(var8_12) == var7_10) return true;
                        if (!(var3_3 > 0.0f)) return true;
                        if (var5_6 <= 0) return true;
                        if (var6_8 <= 0) return true;
                        if ((var4_4 = Math.round((float)(var4_4 - var6_8) / ((float)var6_8 / (float)var5_6) * (var8_12 / var3_3))) == this.getScrollX()) return true;
                        this.mScrollCache.mScrollBarDraggingPos = var2_2;
                        this.setScrollX(var4_4);
                        return true;
                    } else {
                        ** GOTO lbl-1000
                    }
                }
            } else lbl-1000: // 3 sources:
            {
                if (this.mScrollCache.state == 0) {
                    return false;
                }
                if (this.isOnVerticalScrollbarThumb(var2_2, var3_3)) {
                    var1_1 = this.mScrollCache;
                    var1_1.mScrollBarDraggingState = 1;
                    var1_1.mScrollBarDraggingPos = var3_3;
                    return true;
                }
                if (this.isOnHorizontalScrollbarThumb(var2_2, var3_3)) {
                    var1_1 = this.mScrollCache;
                    var1_1.mScrollBarDraggingState = 2;
                    var1_1.mScrollBarDraggingPos = var2_2;
                    return true;
                }
            }
            this.mScrollCache.mScrollBarDraggingState = 0;
            return false;
        }
        this.mScrollCache.mScrollBarDraggingState = 0;
        return false;
    }

    void handleTooltipKey(KeyEvent keyEvent) {
        int n = keyEvent.getAction();
        if (n != 0) {
            if (n == 1) {
                this.handleTooltipUp();
            }
        } else if (keyEvent.getRepeatCount() == 0) {
            this.hideTooltip();
        }
    }

    boolean hasDefaultFocus() {
        return this.isFocusedByDefault();
    }

    public boolean hasExplicitFocusable() {
        return this.hasFocusable(false, true);
    }

    @ViewDebug.ExportedProperty(category="focus")
    public boolean hasFocus() {
        boolean bl = (this.mPrivateFlags & 2) != 0;
        return bl;
    }

    public boolean hasFocusable() {
        return this.hasFocusable(sHasFocusableExcludeAutoFocusable ^ true, false);
    }

    boolean hasFocusable(boolean bl, boolean bl2) {
        int n;
        if (!this.isFocusableInTouchMode()) {
            ViewParent viewParent = this.mParent;
            while (viewParent instanceof ViewGroup) {
                if (((ViewGroup)viewParent).shouldBlockFocusForTouchscreen()) {
                    return false;
                }
                viewParent = viewParent.getParent();
            }
        }
        if (((n = this.mViewFlags) & 12) == 0 && (n & 32) == 0) {
            return (bl || this.getFocusable() != 16) && this.isFocusable();
        }
        return false;
    }

    protected boolean hasHoveredChild() {
        return false;
    }

    @UnsupportedAppUsage
    public final boolean hasIdentityMatrix() {
        return this.mRenderNode.hasIdentityMatrix();
    }

    public boolean hasNestedScrollingParent() {
        boolean bl = this.mNestedScrollingParent != null;
        return bl;
    }

    public boolean hasOnClickListeners() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean bl = listenerInfo != null && listenerInfo.mOnClickListener != null;
        return bl;
    }

    protected boolean hasOpaqueScrollbars() {
        boolean bl = (this.mPrivateFlags & 16777216) == 16777216;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public boolean hasOverlappingRendering() {
        return true;
    }

    public boolean hasPointerCapture() {
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        if (viewRootImpl == null) {
            return false;
        }
        return viewRootImpl.hasPointerCapture();
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public boolean hasShadow() {
        return this.mRenderNode.hasShadow();
    }

    @ViewDebug.ExportedProperty(category="layout")
    public boolean hasTransientState() {
        boolean bl = (this.mPrivateFlags2 & Integer.MIN_VALUE) == Integer.MIN_VALUE;
        return bl;
    }

    boolean hasUnhandledKeyListener() {
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean bl = listenerInfo != null && listenerInfo.mUnhandledKeyListeners != null && !this.mListenerInfo.mUnhandledKeyListeners.isEmpty();
        return bl;
    }

    public boolean hasWindowFocus() {
        AttachInfo attachInfo = this.mAttachInfo;
        boolean bl = attachInfo != null && attachInfo.mHasWindowFocus;
        return bl;
    }

    @UnsupportedAppUsage
    void hideTooltip() {
        Object object = this.mTooltipInfo;
        if (object == null) {
            return;
        }
        this.removeCallbacks(((TooltipInfo)object).mShowTooltipRunnable);
        if (this.mTooltipInfo.mTooltipPopup == null) {
            return;
        }
        this.mTooltipInfo.mTooltipPopup.hide();
        object = this.mTooltipInfo;
        ((TooltipInfo)object).mTooltipPopup = null;
        ((TooltipInfo)object).mTooltipFromLongClick = false;
        ((TooltipInfo)object).clearAnchorPos();
        object = this.mAttachInfo;
        if (object != null) {
            ((AttachInfo)object).mTooltipHost = null;
        }
        this.notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    @UnsupportedAppUsage
    public boolean includeForAccessibility() {
        AttachInfo attachInfo = this.mAttachInfo;
        boolean bl = false;
        if (attachInfo != null) {
            if ((attachInfo.mAccessibilityFetchFlags & 8) != 0 || this.isImportantForAccessibility()) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    protected void initializeFadingEdge(TypedArray typedArray) {
        typedArray = this.mContext.obtainStyledAttributes(R.styleable.View);
        this.initializeFadingEdgeInternal(typedArray);
        typedArray.recycle();
    }

    protected void initializeFadingEdgeInternal(TypedArray typedArray) {
        this.initScrollCache();
        this.mScrollCache.fadingEdgeLength = typedArray.getDimensionPixelSize(25, ViewConfiguration.get(this.mContext).getScaledFadingEdgeLength());
    }

    protected void initializeScrollbars(TypedArray typedArray) {
        typedArray = this.mContext.obtainStyledAttributes(R.styleable.View);
        this.initializeScrollbarsInternal(typedArray);
        typedArray.recycle();
    }

    @UnsupportedAppUsage
    protected void initializeScrollbarsInternal(TypedArray typedArray) {
        boolean bl;
        this.initScrollCache();
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        if (scrollabilityCache.scrollBar == null) {
            scrollabilityCache.scrollBar = new ScrollBarDrawable();
            scrollabilityCache.scrollBar.setState(this.getDrawableState());
            scrollabilityCache.scrollBar.setCallback(this);
        }
        if (!(bl = typedArray.getBoolean(47, true))) {
            scrollabilityCache.state = 1;
        }
        scrollabilityCache.fadeScrollBars = bl;
        scrollabilityCache.scrollBarFadeDuration = typedArray.getInt(45, ViewConfiguration.getScrollBarFadeDuration());
        scrollabilityCache.scrollBarDefaultDelayBeforeFade = typedArray.getInt(46, ViewConfiguration.getScrollDefaultDelay());
        scrollabilityCache.scrollBarSize = typedArray.getDimensionPixelSize(1, ViewConfiguration.get(this.mContext).getScaledScrollBarSize());
        Drawable drawable2 = typedArray.getDrawable(4);
        scrollabilityCache.scrollBar.setHorizontalTrackDrawable(drawable2);
        drawable2 = typedArray.getDrawable(2);
        if (drawable2 != null) {
            scrollabilityCache.scrollBar.setHorizontalThumbDrawable(drawable2);
        }
        if (typedArray.getBoolean(6, false)) {
            scrollabilityCache.scrollBar.setAlwaysDrawHorizontalTrack(true);
        }
        Drawable drawable3 = typedArray.getDrawable(5);
        scrollabilityCache.scrollBar.setVerticalTrackDrawable(drawable3);
        drawable2 = typedArray.getDrawable(3);
        if (drawable2 != null) {
            scrollabilityCache.scrollBar.setVerticalThumbDrawable(drawable2);
        }
        if (typedArray.getBoolean(7, false)) {
            scrollabilityCache.scrollBar.setAlwaysDrawVerticalTrack(true);
        }
        int n = this.getLayoutDirection();
        if (drawable3 != null) {
            drawable3.setLayoutDirection(n);
        }
        if (drawable2 != null) {
            drawable2.setLayoutDirection(n);
        }
        this.resolvePadding();
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768420L)
    protected void internalSetPadding(int n, int n2, int n3, int n4) {
        this.mUserPaddingLeft = n;
        this.mUserPaddingRight = n3;
        this.mUserPaddingBottom = n4;
        int n5 = this.mViewFlags;
        int n6 = 0;
        int n7 = n;
        int n8 = n3;
        int n9 = n4;
        if ((n5 & 768) != 0) {
            int n10 = 0;
            int n11 = n;
            int n12 = n3;
            if ((n5 & 512) != 0) {
                n11 = (n5 & 16777216) == 0 ? 0 : this.getVerticalScrollbarWidth();
                n12 = this.mVerticalScrollbarPosition;
                if (n12 != 0) {
                    if (n12 != 1) {
                        if (n12 != 2) {
                            n11 = n;
                            n12 = n3;
                        } else {
                            n12 = n3 + n11;
                            n11 = n;
                        }
                    } else {
                        n11 = n + n11;
                        n12 = n3;
                    }
                } else if (this.isLayoutRtl()) {
                    n11 = n + n11;
                    n12 = n3;
                } else {
                    n12 = n3 + n11;
                    n11 = n;
                }
            }
            n7 = n11;
            n8 = n12;
            n9 = n4;
            if ((n5 & 256) != 0) {
                n = (n5 & 16777216) == 0 ? n10 : this.getHorizontalScrollbarHeight();
                n9 = n4 + n;
                n8 = n12;
                n7 = n11;
            }
        }
        n = n6;
        if (this.mPaddingLeft != n7) {
            n = 1;
            this.mPaddingLeft = n7;
        }
        if (this.mPaddingTop != n2) {
            n = 1;
            this.mPaddingTop = n2;
        }
        if (this.mPaddingRight != n8) {
            n = 1;
            this.mPaddingRight = n8;
        }
        if (this.mPaddingBottom != n9) {
            n = 1;
            this.mPaddingBottom = n9;
        }
        if (n != 0) {
            this.requestLayout();
            this.invalidateOutline();
        }
    }

    public void invalidate() {
        this.invalidate(true);
    }

    @Deprecated
    public void invalidate(int n, int n2, int n3, int n4) {
        int n5 = this.mScrollX;
        int n6 = this.mScrollY;
        this.invalidateInternal(n - n5, n2 - n6, n3 - n5, n4 - n6, true, false);
    }

    @Deprecated
    public void invalidate(Rect rect) {
        int n = this.mScrollX;
        int n2 = this.mScrollY;
        this.invalidateInternal(rect.left - n, rect.top - n2, rect.right - n, rect.bottom - n2, true, false);
    }

    @UnsupportedAppUsage
    public void invalidate(boolean bl) {
        this.invalidateInternal(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop, bl, true);
    }

    @Override
    public void invalidateDrawable(Drawable object) {
        if (this.verifyDrawable((Drawable)object)) {
            object = ((Drawable)object).getDirtyBounds();
            int n = this.mScrollX;
            int n2 = this.mScrollY;
            this.invalidate(((Rect)object).left + n, ((Rect)object).top + n2, ((Rect)object).right + n, ((Rect)object).bottom + n2);
            this.rebuildOutline();
        }
    }

    void invalidateInheritedLayoutMode(int n) {
    }

    void invalidateInternal(int n, int n2, int n3, int n4, boolean bl, boolean bl2) {
        Object object = this.mGhostView;
        if (object != null) {
            ((View)object).invalidate(true);
            return;
        }
        if (this.skipInvalidate()) {
            return;
        }
        this.mCachedContentCaptureSession = null;
        int n5 = this.mPrivateFlags;
        if ((n5 & 48) == 48 || bl && (n5 & 32768) == 32768 || (this.mPrivateFlags & Integer.MIN_VALUE) != Integer.MIN_VALUE || bl2 && this.isOpaque() != this.mLastIsOpaque) {
            if (bl2) {
                this.mLastIsOpaque = this.isOpaque();
                this.mPrivateFlags &= -33;
            }
            this.mPrivateFlags |= 2097152;
            if (bl) {
                this.mPrivateFlags |= Integer.MIN_VALUE;
                this.mPrivateFlags &= -32769;
            }
            Object object2 = this.mAttachInfo;
            object = this.mParent;
            if (object != null && object2 != null && n < n3 && n2 < n4) {
                object2 = ((AttachInfo)object2).mTmpInvalRect;
                ((Rect)object2).set(n, n2, n3, n4);
                object.invalidateChild(this, (Rect)object2);
            }
            if ((object = this.mBackground) != null && ((Drawable)object).isProjected() && (object = this.getProjectionReceiver()) != null) {
                ((View)object).damageInParent();
            }
        }
    }

    public void invalidateOutline() {
        this.rebuildOutline();
        this.notifySubtreeAccessibilityStateChangedIfNeeded();
        this.invalidateViewProperty(false, false);
    }

    @UnsupportedAppUsage
    protected void invalidateParentCaches() {
        Object object = this.mParent;
        if (object instanceof View) {
            object = (View)object;
            ((View)object).mPrivateFlags |= Integer.MIN_VALUE;
        }
    }

    @UnsupportedAppUsage
    protected void invalidateParentIfNeeded() {
        ViewParent viewParent;
        if (this.isHardwareAccelerated() && (viewParent = this.mParent) instanceof View) {
            ((View)((Object)viewParent)).invalidate(true);
        }
    }

    protected void invalidateParentIfNeededAndWasQuickRejected() {
        if ((this.mPrivateFlags2 & 268435456) != 0) {
            this.invalidateParentIfNeeded();
        }
    }

    @UnsupportedAppUsage
    void invalidateViewProperty(boolean bl, boolean bl2) {
        if (this.isHardwareAccelerated() && this.mRenderNode.hasDisplayList() && (this.mPrivateFlags & 64) == 0) {
            this.damageInParent();
        } else {
            if (bl) {
                this.invalidateParentCaches();
            }
            if (bl2) {
                this.mPrivateFlags |= 32;
            }
            this.invalidate(false);
        }
    }

    public boolean isAccessibilityFocused() {
        boolean bl = (this.mPrivateFlags2 & 67108864) != 0;
        return bl;
    }

    boolean isAccessibilityFocusedViewOrHost() {
        boolean bl = this.isAccessibilityFocused() || this.getViewRootImpl() != null && this.getViewRootImpl().getAccessibilityFocusedHost() == this;
        return bl;
    }

    public boolean isAccessibilityHeading() {
        boolean bl = (this.mPrivateFlags3 & Integer.MIN_VALUE) != 0;
        return bl;
    }

    public boolean isAccessibilitySelectionExtendable() {
        return false;
    }

    public boolean isActionableForAccessibility() {
        boolean bl = this.isClickable() || this.isLongClickable() || this.isFocusable();
        return bl;
    }

    @ViewDebug.ExportedProperty
    public boolean isActivated() {
        boolean bl = (this.mPrivateFlags & 1073741824) != 0;
        return bl;
    }

    public boolean isAssistBlocked() {
        boolean bl = (this.mPrivateFlags3 & 16384) != 0;
        return bl;
    }

    public boolean isAttachedToWindow() {
        boolean bl = this.mAttachInfo != null;
        return bl;
    }

    public boolean isAutofilled() {
        boolean bl = (this.mPrivateFlags3 & 65536) != 0;
        return bl;
    }

    @ViewDebug.ExportedProperty
    public boolean isClickable() {
        boolean bl = (this.mViewFlags & 16384) == 16384;
        return bl;
    }

    public boolean isContextClickable() {
        boolean bl = (this.mViewFlags & 8388608) == 8388608;
        return bl;
    }

    public boolean isDefaultFocusHighlightNeeded(Drawable drawable2, Drawable drawable3) {
        boolean bl;
        block0 : {
            bl = false;
            boolean bl2 = !(drawable2 != null && drawable2.isStateful() && drawable2.hasFocusStateSpecified() || drawable3 != null && drawable3.isStateful() && drawable3.hasFocusStateSpecified());
            if (this.isInTouchMode() || !this.getDefaultFocusHighlightEnabled() || !bl2 || !this.isAttachedToWindow() || !sUseDefaultFocusHighlight) break block0;
            bl = true;
        }
        return bl;
    }

    public boolean isDirty() {
        boolean bl = (this.mPrivateFlags & 2097152) != 0;
        return bl;
    }

    @UnsupportedAppUsage
    boolean isDraggingScrollBar() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        boolean bl = scrollabilityCache != null && scrollabilityCache.mScrollBarDraggingState != 0;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    @Deprecated
    public boolean isDrawingCacheEnabled() {
        boolean bl = (this.mViewFlags & 32768) == 32768;
        return bl;
    }

    public boolean isDuplicateParentStateEnabled() {
        boolean bl = (this.mViewFlags & 4194304) == 4194304;
        return bl;
    }

    @ViewDebug.ExportedProperty
    public boolean isEnabled() {
        boolean bl = (this.mViewFlags & 32) == 0;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="focus")
    public final boolean isFocusable() {
        int n = this.mViewFlags;
        boolean bl = true;
        if (1 != (n & 1)) {
            bl = false;
        }
        return bl;
    }

    @ViewDebug.ExportedProperty(category="focus")
    public final boolean isFocusableInTouchMode() {
        boolean bl = 262144 == (this.mViewFlags & 262144);
        return bl;
    }

    @ViewDebug.ExportedProperty(category="focus")
    public boolean isFocused() {
        boolean bl = (this.mPrivateFlags & 2) != 0;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="focus")
    public final boolean isFocusedByDefault() {
        boolean bl = (this.mPrivateFlags3 & 262144) != 0;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public boolean isForceDarkAllowed() {
        return this.mRenderNode.isForceDarkAllowed();
    }

    public boolean isForegroundInsidePadding() {
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        boolean bl = foregroundInfo != null ? foregroundInfo.mInsidePadding : true;
        return bl;
    }

    @ViewDebug.ExportedProperty
    public boolean isHapticFeedbackEnabled() {
        boolean bl = 268435456 == (this.mViewFlags & 268435456);
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public boolean isHardwareAccelerated() {
        AttachInfo attachInfo = this.mAttachInfo;
        boolean bl = attachInfo != null && attachInfo.mHardwareAccelerated;
        return bl;
    }

    public boolean isHorizontalFadingEdgeEnabled() {
        boolean bl = (this.mViewFlags & 4096) == 4096;
        return bl;
    }

    public boolean isHorizontalScrollBarEnabled() {
        boolean bl = (this.mViewFlags & 256) == 256;
        return bl;
    }

    @ViewDebug.ExportedProperty
    public boolean isHovered() {
        boolean bl = (this.mPrivateFlags & 268435456) != 0;
        return bl;
    }

    public boolean isImportantForAccessibility() {
        int n = (this.mPrivateFlags2 & 7340032) >> 20;
        boolean bl = false;
        if (n != 2 && n != 4) {
            ViewParent viewParent = this.mParent;
            while (viewParent instanceof View) {
                if (((View)((Object)viewParent)).getImportantForAccessibility() == 4) {
                    return false;
                }
                viewParent = viewParent.getParent();
            }
            if (n == 1 || this.isActionableForAccessibility() || this.hasListenersForAccessibility() || this.getAccessibilityNodeProvider() != null || this.getAccessibilityLiveRegion() != 0 || this.isAccessibilityPane()) {
                bl = true;
            }
            return bl;
        }
        return false;
    }

    public final boolean isImportantForAutofill() {
        int n;
        Object object = this.mParent;
        while (object instanceof View) {
            n = ((View)object).getImportantForAutofill();
            if (n != 8 && n != 4) {
                object = object.getParent();
                continue;
            }
            if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("View (");
                stringBuilder.append(this);
                stringBuilder.append(") is not important for autofill because parent ");
                stringBuilder.append(object);
                stringBuilder.append("'s importance is ");
                stringBuilder.append(n);
                Log.v(AUTOFILL_LOG_TAG, stringBuilder.toString());
            }
            return false;
        }
        n = this.getImportantForAutofill();
        if (n != 4 && n != 1) {
            if (n != 8 && n != 2) {
                if (n != 0) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("invalid autofill importance (");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(" on view ");
                    ((StringBuilder)object).append(this);
                    Log.w(AUTOFILL_LOG_TAG, ((StringBuilder)object).toString());
                    return false;
                }
                n = this.mID;
                if (n != -1 && !View.isViewIdGenerated(n)) {
                    Object object2;
                    Object object3 = this.getResources();
                    object = null;
                    Object object4 = null;
                    object = object2 = ((Resources)object3).getResourceEntryName(n);
                    try {
                        object = object3 = ((Resources)object3).getResourcePackageName(n);
                        object4 = object2;
                        object2 = object;
                    }
                    catch (Resources.NotFoundException notFoundException) {
                        object2 = object4;
                        object4 = object;
                    }
                    if (object4 != null && object2 != null && ((String)object2).equals(this.mContext.getPackageName())) {
                        return true;
                    }
                }
                return this.getAutofillHints() != null;
            }
            if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
                object = new StringBuilder();
                ((StringBuilder)object).append("View (");
                ((StringBuilder)object).append(this);
                ((StringBuilder)object).append(") is not important for autofill because its importance is ");
                ((StringBuilder)object).append(n);
                Log.v(AUTOFILL_LOG_TAG, ((StringBuilder)object).toString());
            }
            return false;
        }
        return true;
    }

    public boolean isInEditMode() {
        return false;
    }

    public boolean isInLayout() {
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        boolean bl = viewRootImpl != null && viewRootImpl.isInLayout();
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isInScrollingContainer() {
        for (ViewParent viewParent = this.getParent(); viewParent != null && viewParent instanceof ViewGroup; viewParent = viewParent.getParent()) {
            if (!((ViewGroup)viewParent).shouldDelayChildPressedState()) continue;
            return true;
        }
        return false;
    }

    @ViewDebug.ExportedProperty
    public boolean isInTouchMode() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mInTouchMode;
        }
        return ViewRootImpl.isInTouchMode();
    }

    @ViewDebug.ExportedProperty(category="focus")
    public final boolean isKeyboardNavigationCluster() {
        boolean bl = (this.mPrivateFlags3 & 32768) != 0;
        return bl;
    }

    public boolean isLaidOut() {
        boolean bl = (this.mPrivateFlags3 & 4) == 4;
        return bl;
    }

    public boolean isLayoutDirectionInherited() {
        boolean bl = this.getRawLayoutDirection() == 2;
        return bl;
    }

    public boolean isLayoutDirectionResolved() {
        boolean bl = (this.mPrivateFlags2 & 32) == 32;
        return bl;
    }

    public boolean isLayoutRequested() {
        boolean bl = (this.mPrivateFlags & 4096) == 4096;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="layout")
    @UnsupportedAppUsage
    public boolean isLayoutRtl() {
        int n = this.getLayoutDirection();
        boolean bl = true;
        if (n != 1) {
            bl = false;
        }
        return bl;
    }

    boolean isLayoutValid() {
        boolean bl = this.isLaidOut() && (this.mPrivateFlags & 4096) == 0;
        return bl;
    }

    public boolean isLongClickable() {
        boolean bl = (this.mViewFlags & 2097152) == 2097152;
        return bl;
    }

    public boolean isNestedScrollingEnabled() {
        boolean bl = (this.mPrivateFlags3 & 128) == 128;
        return bl;
    }

    boolean isOnScrollbar(float f, float f2) {
        Rect rect;
        if (this.mScrollCache == null) {
            return false;
        }
        f += (float)this.getScrollX();
        f2 += (float)this.getScrollY();
        boolean bl = this.computeVerticalScrollRange() > this.computeVerticalScrollExtent();
        if (this.isVerticalScrollBarEnabled() && !this.isVerticalScrollBarHidden() && bl) {
            rect = this.mScrollCache.mScrollBarTouchBounds;
            this.getVerticalScrollBarBounds(null, rect);
            if (rect.contains((int)f, (int)f2)) {
                return true;
            }
        }
        bl = this.computeHorizontalScrollRange() > this.computeHorizontalScrollExtent();
        if (this.isHorizontalScrollBarEnabled() && bl) {
            rect = this.mScrollCache.mScrollBarTouchBounds;
            this.getHorizontalScrollBarBounds(null, rect);
            if (rect.contains((int)f, (int)f2)) {
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage
    boolean isOnScrollbarThumb(float f, float f2) {
        boolean bl = this.isOnVerticalScrollbarThumb(f, f2) || this.isOnHorizontalScrollbarThumb(f, f2);
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public boolean isOpaque() {
        boolean bl = (this.mPrivateFlags & 25165824) == 25165824 && this.getFinalAlpha() >= 1.0f;
        return bl;
    }

    protected boolean isPaddingOffsetRequired() {
        return false;
    }

    public boolean isPaddingRelative() {
        boolean bl = this.mUserPaddingStart != Integer.MIN_VALUE || this.mUserPaddingEnd != Integer.MIN_VALUE;
        return bl;
    }

    @UnsupportedAppUsage
    boolean isPaddingResolved() {
        boolean bl = (this.mPrivateFlags2 & 536870912) == 536870912;
        return bl;
    }

    public boolean isPivotSet() {
        return this.mRenderNode.isPivotExplicitlySet();
    }

    @ViewDebug.ExportedProperty
    public boolean isPressed() {
        boolean bl = (this.mPrivateFlags & 16384) == 16384;
        return bl;
    }

    @UnsupportedAppUsage
    public boolean isRootNamespace() {
        boolean bl = (this.mPrivateFlags & 8) != 0;
        return bl;
    }

    public boolean isSaveEnabled() {
        boolean bl = (this.mViewFlags & 65536) != 65536;
        return bl;
    }

    public boolean isSaveFromParentEnabled() {
        boolean bl = (this.mViewFlags & 536870912) != 536870912;
        return bl;
    }

    public boolean isScreenReaderFocusable() {
        boolean bl = (this.mPrivateFlags3 & 268435456) != 0;
        return bl;
    }

    public boolean isScrollContainer() {
        boolean bl = (this.mPrivateFlags & 1048576) != 0;
        return bl;
    }

    public boolean isScrollbarFadingEnabled() {
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        boolean bl = scrollabilityCache != null && scrollabilityCache.fadeScrollBars;
        return bl;
    }

    @ViewDebug.ExportedProperty
    public boolean isSelected() {
        boolean bl = (this.mPrivateFlags & 4) != 0;
        return bl;
    }

    public boolean isShown() {
        Object object = this;
        while ((((View)object).mViewFlags & 12) == 0) {
            object = ((View)object).mParent;
            if (object == null) {
                return false;
            }
            if (!(object instanceof View)) {
                return true;
            }
            object = (View)object;
        }
        return false;
    }

    @ViewDebug.ExportedProperty
    public boolean isSoundEffectsEnabled() {
        boolean bl = 134217728 == (this.mViewFlags & 134217728);
        return bl;
    }

    public final boolean isTemporarilyDetached() {
        boolean bl = (this.mPrivateFlags3 & 33554432) != 0;
        return bl;
    }

    public boolean isTextAlignmentInherited() {
        boolean bl = this.getRawTextAlignment() == 0;
        return bl;
    }

    public boolean isTextAlignmentResolved() {
        boolean bl = (this.mPrivateFlags2 & 65536) == 65536;
        return bl;
    }

    public boolean isTextDirectionInherited() {
        boolean bl = this.getRawTextDirection() == 0;
        return bl;
    }

    public boolean isTextDirectionResolved() {
        boolean bl = (this.mPrivateFlags2 & 512) == 512;
        return bl;
    }

    public boolean isVerticalFadingEdgeEnabled() {
        boolean bl = (this.mViewFlags & 8192) == 8192;
        return bl;
    }

    public boolean isVerticalScrollBarEnabled() {
        boolean bl = (this.mViewFlags & 512) == 512;
        return bl;
    }

    protected boolean isVerticalScrollBarHidden() {
        return false;
    }

    @UnsupportedAppUsage
    public boolean isVisibleToUser() {
        return this.isVisibleToUser(null);
    }

    @UnsupportedAppUsage
    protected boolean isVisibleToUser(Rect rect) {
        Object object = this.mAttachInfo;
        if (object != null) {
            if (((AttachInfo)object).mWindowVisibility != 0) {
                return false;
            }
            object = this;
            while (object instanceof View) {
                if (!(((View)(object = (View)object)).getAlpha() <= 0.0f) && !(((View)object).getTransitionAlpha() <= 0.0f) && ((View)object).getVisibility() == 0) {
                    object = ((View)object).mParent;
                    continue;
                }
                return false;
            }
            object = this.mAttachInfo.mTmpInvalRect;
            Point point = this.mAttachInfo.mPoint;
            if (!this.getGlobalVisibleRect((Rect)object, point)) {
                return false;
            }
            if (rect != null) {
                ((Rect)object).offset(-point.x, -point.y);
                return rect.intersect((Rect)object);
            }
            return true;
        }
        return false;
    }

    public boolean isVisibleToUserForAutofill(int n) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            Object object = this.getAccessibilityNodeProvider();
            if (object != null) {
                if ((object = ((AccessibilityNodeProvider)object).createAccessibilityNodeInfo(n)) != null) {
                    return ((AccessibilityNodeInfo)object).isVisibleToUser();
                }
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append("isVisibleToUserForAutofill(");
                ((StringBuilder)object).append(n);
                ((StringBuilder)object).append("): no provider");
                Log.w(VIEW_LOG_TAG, ((StringBuilder)object).toString());
            }
            return false;
        }
        return true;
    }

    public void jumpDrawablesToCurrentState() {
        Object object = this.mBackground;
        if (object != null) {
            ((Drawable)object).jumpToCurrentState();
        }
        if ((object = this.mStateListAnimator) != null) {
            ((StateListAnimator)object).jumpToCurrentState();
        }
        if ((object = this.mDefaultFocusHighlight) != null) {
            ((Drawable)object).jumpToCurrentState();
        }
        if ((object = this.mForegroundInfo) != null && ((ForegroundInfo)object).mDrawable != null) {
            this.mForegroundInfo.mDrawable.jumpToCurrentState();
        }
    }

    public View keyboardNavigationClusterSearch(View view, int n) {
        if (this.isKeyboardNavigationCluster()) {
            view = this;
        }
        if (this.isRootNamespace()) {
            return FocusFinder.getInstance().findNextKeyboardNavigationCluster(this, view, n);
        }
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            return viewParent.keyboardNavigationClusterSearch(view, n);
        }
        return null;
    }

    public void layout(int n, int n2, int n3, int n4) {
        Object object;
        if ((this.mPrivateFlags3 & 8) != 0) {
            this.onMeasure(this.mOldWidthMeasureSpec, this.mOldHeightMeasureSpec);
            this.mPrivateFlags3 &= -9;
        }
        int n5 = this.mLeft;
        int n6 = this.mTop;
        int n7 = this.mBottom;
        int n8 = this.mRight;
        boolean bl = View.isLayoutModeOptical(this.mParent) ? this.setOpticalFrame(n, n2, n3, n4) : this.setFrame(n, n2, n3, n4);
        View view = null;
        if (!bl && (this.mPrivateFlags & 8192) != 8192) {
            view = null;
        } else {
            this.onLayout(bl, n, n2, n3, n4);
            if (this.shouldDrawRoundScrollbar()) {
                if (this.mRoundScrollbarRenderer == null) {
                    this.mRoundScrollbarRenderer = new RoundScrollbarRenderer(this);
                }
            } else {
                this.mRoundScrollbarRenderer = null;
            }
            this.mPrivateFlags &= -8193;
            object = this.mListenerInfo;
            if (object != null && ((ListenerInfo)object).mOnLayoutChangeListeners != null) {
                ArrayList arrayList = (ArrayList)((ListenerInfo)object).mOnLayoutChangeListeners.clone();
                int n9 = arrayList.size();
                for (int i = 0; i < n9; ++i) {
                    ((OnLayoutChangeListener)arrayList.get(i)).onLayoutChange(this, n, n2, n3, n4, n5, n6, n8, n7);
                }
            } else {
                view = null;
            }
        }
        bl = this.isLayoutValid();
        this.mPrivateFlags &= -4097;
        this.mPrivateFlags3 |= 4;
        if (!bl && this.isFocused()) {
            this.mPrivateFlags &= -2;
            if (this.canTakeFocus()) {
                this.clearParentsWantFocus();
            } else if (this.getViewRootImpl() != null && this.getViewRootImpl().isInLayout()) {
                if (!this.hasParentWantsFocus()) {
                    this.clearFocusInternal(view, true, false);
                }
            } else {
                this.clearFocusInternal(view, true, false);
                this.clearParentsWantFocus();
            }
        } else {
            n = this.mPrivateFlags;
            if ((n & 1) != 0) {
                this.mPrivateFlags = n & -2;
                object = this.findFocus();
                if (object != null && !this.restoreDefaultFocus() && !this.hasParentWantsFocus()) {
                    ((View)object).clearFocusInternal(view, true, false);
                }
            }
        }
        n = this.mPrivateFlags3;
        if ((134217728 & n) != 0) {
            this.mPrivateFlags3 = n & -134217729;
            this.notifyEnterOrExitForAutoFillIfNeeded(true);
        }
    }

    @UnsupportedAppUsage
    public void makeOptionalFitsSystemWindows() {
        this.setFlags(2048, 2048);
    }

    public void mapRectFromViewToScreenCoords(RectF rectF, boolean bl) {
        if (!this.hasIdentityMatrix()) {
            this.getMatrix().mapRect(rectF);
        }
        rectF.offset(this.mLeft, this.mTop);
        Object object = this.mParent;
        while (object instanceof View) {
            object = (View)object;
            rectF.offset(-((View)object).mScrollX, -((View)object).mScrollY);
            if (bl) {
                rectF.left = Math.max(rectF.left, 0.0f);
                rectF.top = Math.max(rectF.top, 0.0f);
                rectF.right = Math.min(rectF.right, (float)((View)object).getWidth());
                rectF.bottom = Math.min(rectF.bottom, (float)((View)object).getHeight());
            }
            if (!((View)object).hasIdentityMatrix()) {
                ((View)object).getMatrix().mapRect(rectF);
            }
            rectF.offset(((View)object).mLeft, ((View)object).mTop);
            object = ((View)object).mParent;
        }
        if (object instanceof ViewRootImpl) {
            rectF.offset(0.0f, -((ViewRootImpl)object).mCurScrollY);
        }
        rectF.offset(this.mAttachInfo.mWindowLeft, this.mAttachInfo.mWindowTop);
    }

    public final void measure(int n, int n2) {
        Object object;
        block9 : {
            long l;
            long l2;
            block8 : {
                int n3;
                int n4;
                boolean bl = View.isLayoutModeOptical(this);
                if (bl != View.isLayoutModeOptical(this.mParent)) {
                    object = this.getOpticalInsets();
                    n3 = ((Insets)object).left + ((Insets)object).right;
                    n4 = ((Insets)object).top + ((Insets)object).bottom;
                    if (bl) {
                        n3 = -n3;
                    }
                    n3 = MeasureSpec.adjust(n, n3);
                    n = bl ? -n4 : n4;
                    n2 = MeasureSpec.adjust(n2, n);
                    n = n3;
                }
                l = (long)n << 32 | (long)n2 & 0xFFFFFFFFL;
                if (this.mMeasureCache == null) {
                    this.mMeasureCache = new LongSparseLongArray(2);
                }
                n3 = this.mPrivateFlags;
                int n5 = 1;
                n3 = (n3 & 4096) == 4096 ? 1 : 0;
                n4 = n == this.mOldWidthMeasureSpec && n2 == this.mOldHeightMeasureSpec ? 0 : 1;
                boolean bl2 = MeasureSpec.getMode(n) == 1073741824 && MeasureSpec.getMode(n2) == 1073741824;
                boolean bl3 = this.getMeasuredWidth() == MeasureSpec.getSize(n) && this.getMeasuredHeight() == MeasureSpec.getSize(n2);
                n4 = n4 != 0 && (sAlwaysRemeasureExactly || !bl2 || !bl3) ? n5 : 0;
                if (n3 == 0 && n4 == 0) break block8;
                this.mPrivateFlags &= -2049;
                this.resolveRtlPropertiesIfNeeded();
                n3 = n3 != 0 ? -1 : this.mMeasureCache.indexOfKey(l);
                if (n3 >= 0 && !sIgnoreMeasureCache) {
                    l2 = this.mMeasureCache.valueAt(n3);
                    this.setMeasuredDimensionRaw((int)(l2 >> 32), (int)l2);
                    this.mPrivateFlags3 |= 8;
                } else {
                    this.onMeasure(n, n2);
                    this.mPrivateFlags3 &= -9;
                }
                n3 = this.mPrivateFlags;
                if ((n3 & 2048) != 2048) break block9;
                this.mPrivateFlags = n3 | 8192;
            }
            this.mOldWidthMeasureSpec = n;
            this.mOldHeightMeasureSpec = n2;
            object = this.mMeasureCache;
            l2 = this.mMeasuredWidth;
            ((LongSparseLongArray)object).put(l, (long)this.mMeasuredHeight & 0xFFFFFFFFL | l2 << 32);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("View with id ");
        ((StringBuilder)object).append(this.getId());
        ((StringBuilder)object).append(": ");
        ((StringBuilder)object).append(this.getClass().getName());
        ((StringBuilder)object).append("#onMeasure() did not set the measured dimension by calling setMeasuredDimension()");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    void needGlobalAttributesUpdate(boolean bl) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && !attachInfo.mRecomputeGlobalAttributes && (bl || attachInfo.mKeepScreenOn || attachInfo.mSystemUiVisibility != 0 || attachInfo.mHasSystemUiListeners)) {
            attachInfo.mRecomputeGlobalAttributes = true;
        }
    }

    public void notifyEnterOrExitForAutoFillIfNeeded(boolean bl) {
        AutofillManager autofillManager;
        if (this.canNotifyAutofillEnterExitEvent() && (autofillManager = this.getAutofillManager()) != null) {
            if (bl && this.isFocused()) {
                if (!this.isLaidOut()) {
                    this.mPrivateFlags3 |= 134217728;
                } else if (this.isVisibleToUser()) {
                    autofillManager.notifyViewEntered(this);
                }
            } else if (!bl && !this.isFocused()) {
                autofillManager.notifyViewExited(this);
            }
        }
    }

    void notifyGlobalFocusCleared(View view) {
        AttachInfo attachInfo;
        if (view != null && (attachInfo = this.mAttachInfo) != null) {
            attachInfo.mTreeObserver.dispatchOnGlobalFocusChange(view, null);
        }
    }

    @UnsupportedAppUsage
    public void notifySubtreeAccessibilityStateChangedIfNeeded() {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mAttachInfo != null) {
            int n = this.mPrivateFlags2;
            if ((n & 134217728) == 0) {
                this.mPrivateFlags2 = n | 134217728;
                ViewParent viewParent = this.mParent;
                if (viewParent != null) {
                    try {
                        viewParent.notifySubtreeAccessibilityStateChanged(this, this, 1);
                    }
                    catch (AbstractMethodError abstractMethodError) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(this.mParent.getClass().getSimpleName());
                        stringBuilder.append(" does not fully implement ViewParent");
                        Log.e(VIEW_LOG_TAG, stringBuilder.toString(), abstractMethodError);
                    }
                }
            }
            return;
        }
    }

    @UnsupportedAppUsage
    public void notifyViewAccessibilityStateChangedIfNeeded(int n) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled() && this.mAttachInfo != null) {
            if (n != 1 && this.isAccessibilityPane() && (this.getVisibility() == 0 || n == 32)) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
                this.onInitializeAccessibilityEvent(accessibilityEvent);
                accessibilityEvent.setEventType(32);
                accessibilityEvent.setContentChangeTypes(n);
                accessibilityEvent.setSource(this);
                this.onPopulateAccessibilityEvent(accessibilityEvent);
                Object object = this.mParent;
                if (object != null) {
                    try {
                        object.requestSendAccessibilityEvent(this, accessibilityEvent);
                    }
                    catch (AbstractMethodError abstractMethodError) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(this.mParent.getClass().getSimpleName());
                        ((StringBuilder)object).append(" does not fully implement ViewParent");
                        Log.e(VIEW_LOG_TAG, ((StringBuilder)object).toString(), abstractMethodError);
                    }
                }
                return;
            }
            if (this.getAccessibilityLiveRegion() != 0) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain();
                accessibilityEvent.setEventType(2048);
                accessibilityEvent.setContentChangeTypes(n);
                this.sendAccessibilityEventUnchecked(accessibilityEvent);
            } else {
                ViewParent viewParent = this.mParent;
                if (viewParent != null) {
                    try {
                        viewParent.notifySubtreeAccessibilityStateChanged(this, this, n);
                    }
                    catch (AbstractMethodError abstractMethodError) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(this.mParent.getClass().getSimpleName());
                        stringBuilder.append(" does not fully implement ViewParent");
                        Log.e(VIEW_LOG_TAG, stringBuilder.toString(), abstractMethodError);
                    }
                }
            }
            return;
        }
    }

    public void offsetLeftAndRight(int n) {
        if (n != 0) {
            boolean bl = this.hasIdentityMatrix();
            if (bl) {
                if (this.isHardwareAccelerated()) {
                    this.invalidateViewProperty(false, false);
                } else {
                    Object object;
                    ViewParent viewParent = this.mParent;
                    if (viewParent != null && (object = this.mAttachInfo) != null) {
                        int n2;
                        int n3;
                        object = ((AttachInfo)object).mTmpInvalRect;
                        if (n < 0) {
                            n2 = this.mLeft + n;
                            n3 = this.mRight;
                        } else {
                            n2 = this.mLeft;
                            n3 = this.mRight + n;
                        }
                        ((Rect)object).set(0, 0, n3 - n2, this.mBottom - this.mTop);
                        viewParent.invalidateChild(this, (Rect)object);
                    }
                }
            } else {
                this.invalidateViewProperty(false, false);
            }
            this.mLeft += n;
            this.mRight += n;
            this.mRenderNode.offsetLeftAndRight(n);
            if (this.isHardwareAccelerated()) {
                this.invalidateViewProperty(false, false);
                this.invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!bl) {
                    this.invalidateViewProperty(false, true);
                }
                this.invalidateParentIfNeeded();
            }
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void offsetTopAndBottom(int n) {
        if (n != 0) {
            boolean bl = this.hasIdentityMatrix();
            if (bl) {
                if (this.isHardwareAccelerated()) {
                    this.invalidateViewProperty(false, false);
                } else {
                    Object object;
                    ViewParent viewParent = this.mParent;
                    if (viewParent != null && (object = this.mAttachInfo) != null) {
                        int n2;
                        int n3;
                        int n4;
                        object = ((AttachInfo)object).mTmpInvalRect;
                        if (n < 0) {
                            n2 = this.mTop + n;
                            n4 = this.mBottom;
                            n3 = n;
                        } else {
                            n2 = this.mTop;
                            n4 = this.mBottom + n;
                            n3 = 0;
                        }
                        ((Rect)object).set(0, n3, this.mRight - this.mLeft, n4 - n2);
                        viewParent.invalidateChild(this, (Rect)object);
                    }
                }
            } else {
                this.invalidateViewProperty(false, false);
            }
            this.mTop += n;
            this.mBottom += n;
            this.mRenderNode.offsetTopAndBottom(n);
            if (this.isHardwareAccelerated()) {
                this.invalidateViewProperty(false, false);
                this.invalidateParentIfNeededAndWasQuickRejected();
            } else {
                if (!bl) {
                    this.invalidateViewProperty(false, true);
                }
                this.invalidateParentIfNeeded();
            }
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void onActivityResult(int n, int n2, Intent intent) {
    }

    protected void onAnimationEnd() {
        this.mPrivateFlags &= -65537;
    }

    protected void onAnimationStart() {
        this.mPrivateFlags |= 65536;
    }

    public WindowInsets onApplyWindowInsets(WindowInsets windowInsets) {
        if ((this.mPrivateFlags3 & 64) == 0 ? this.fitSystemWindows(windowInsets.getSystemWindowInsetsAsRect()) : this.fitSystemWindowsInt(windowInsets.getSystemWindowInsetsAsRect())) {
            return windowInsets.consumeSystemWindowInsets();
        }
        return windowInsets;
    }

    protected void onAttachedToWindow() {
        if ((this.mPrivateFlags & 512) != 0) {
            this.mParent.requestTransparentRegion(this);
        }
        this.mPrivateFlags3 &= -5;
        this.jumpDrawablesToCurrentState();
        AccessibilityNodeIdManager.getInstance().registerViewWithId(this, this.getAccessibilityViewId());
        this.resetSubtreeAccessibilityStateChanged();
        this.rebuildOutline();
        if (this.isFocused()) {
            this.notifyFocusChangeToInputMethodManager(true);
        }
    }

    public void onCancelPendingInputEvents() {
        this.removePerformClickCallback();
        this.cancelLongPress();
        this.mPrivateFlags3 |= 16;
    }

    public boolean onCapturedPointerEvent(MotionEvent motionEvent) {
        return false;
    }

    public boolean onCheckIsTextEditor() {
        return false;
    }

    @UnsupportedAppUsage
    public void onCloseSystemDialogs(String string2) {
    }

    protected void onConfigurationChanged(Configuration configuration) {
    }

    protected void onCreateContextMenu(ContextMenu contextMenu) {
    }

    protected int[] onCreateDrawableState(int n) {
        int[] arrn;
        if ((this.mViewFlags & 4194304) == 4194304 && (arrn = this.mParent) instanceof View) {
            return ((View)arrn).onCreateDrawableState(n);
        }
        int n2 = this.mPrivateFlags;
        int n3 = 0;
        if ((n2 & 16384) != 0) {
            n3 = 0 | 16;
        }
        int n4 = n3;
        if ((this.mViewFlags & 32) == 0) {
            n4 = n3 | 8;
        }
        n3 = n4;
        if (this.isFocused()) {
            n3 = n4 | 4;
        }
        n4 = n3;
        if ((n2 & 4) != 0) {
            n4 = n3 | 2;
        }
        n3 = n4;
        if (this.hasWindowFocus()) {
            n3 = n4 | 1;
        }
        n4 = n3;
        if ((1073741824 & n2) != 0) {
            n4 = n3 | 32;
        }
        arrn = this.mAttachInfo;
        n3 = n4;
        if (arrn != null) {
            n3 = n4;
            if (arrn.mHardwareAccelerationRequested) {
                n3 = n4;
                if (ThreadedRenderer.isAvailable()) {
                    n3 = n4 | 64;
                }
            }
        }
        n4 = n3;
        if ((268435456 & n2) != 0) {
            n4 = n3 | 128;
        }
        n2 = this.mPrivateFlags2;
        n3 = n4;
        if ((n2 & 1) != 0) {
            n3 = n4 | 256;
        }
        n4 = n3;
        if ((n2 & 2) != 0) {
            n4 = n3 | 512;
        }
        int[] arrn2 = StateSet.get(n4);
        if (n == 0) {
            return arrn2;
        }
        if (arrn2 != null) {
            arrn = new int[arrn2.length + n];
            System.arraycopy(arrn2, 0, arrn, 0, arrn2.length);
        } else {
            arrn = new int[n];
        }
        return arrn;
    }

    public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
        return null;
    }

    protected void onDetachedFromWindow() {
    }

    @UnsupportedAppUsage
    protected void onDetachedFromWindowInternal() {
        this.mPrivateFlags &= -67108865;
        this.mPrivateFlags3 &= -5;
        this.mPrivateFlags3 &= -33554433;
        this.removeUnsetPressCallback();
        this.removeLongPressCallback();
        this.removePerformClickCallback();
        this.cancel(this.mSendViewScrolledAccessibilityEvent);
        this.stopNestedScroll();
        this.jumpDrawablesToCurrentState();
        this.destroyDrawingCache();
        this.cleanupDraw();
        this.mCurrentAnimation = null;
        if ((this.mViewFlags & 1073741824) == 1073741824) {
            this.hideTooltip();
        }
        AccessibilityNodeIdManager.getInstance().unregisterViewWithId(this.getAccessibilityViewId());
    }

    protected void onDisplayHint(int n) {
    }

    public boolean onDragEvent(DragEvent dragEvent) {
        return false;
    }

    protected void onDraw(Canvas canvas) {
    }

    public void onDrawForeground(Canvas canvas) {
        this.onDrawScrollIndicators(canvas);
        this.onDrawScrollBars(canvas);
        Object object = this.mForegroundInfo;
        object = object != null ? ((ForegroundInfo)object).mDrawable : null;
        if (object != null) {
            if (this.mForegroundInfo.mBoundsChanged) {
                this.mForegroundInfo.mBoundsChanged = false;
                Rect rect = this.mForegroundInfo.mSelfBounds;
                Rect rect2 = this.mForegroundInfo.mOverlayBounds;
                if (this.mForegroundInfo.mInsidePadding) {
                    rect.set(0, 0, this.getWidth(), this.getHeight());
                } else {
                    rect.set(this.getPaddingLeft(), this.getPaddingTop(), this.getWidth() - this.getPaddingRight(), this.getHeight() - this.getPaddingBottom());
                }
                int n = this.getLayoutDirection();
                Gravity.apply(this.mForegroundInfo.mGravity, ((Drawable)object).getIntrinsicWidth(), ((Drawable)object).getIntrinsicHeight(), rect, rect2, n);
                ((Drawable)object).setBounds(rect2);
            }
            ((Drawable)object).draw(canvas);
        }
    }

    @UnsupportedAppUsage
    protected void onDrawHorizontalScrollBar(Canvas canvas, Drawable drawable2, int n, int n2, int n3, int n4) {
        drawable2.setBounds(n, n2, n3, n4);
        drawable2.draw(canvas);
    }

    protected final void onDrawScrollBars(Canvas canvas) {
        block11 : {
            Object object;
            boolean bl;
            Object object2;
            boolean bl2;
            int n;
            block12 : {
                object2 = this.mScrollCache;
                if (object2 == null) break block11;
                n = ((ScrollabilityCache)object2).state;
                if (n == 0) {
                    return;
                }
                if (n == 2) {
                    if (((ScrollabilityCache)object2).interpolatorValues == null) {
                        ((ScrollabilityCache)object2).interpolatorValues = new float[1];
                    }
                    if (((ScrollabilityCache)object2).scrollBarInterpolator.timeToValues((float[])(object = ((ScrollabilityCache)object2).interpolatorValues)) == Interpolator.Result.FREEZE_END) {
                        ((ScrollabilityCache)object2).state = 0;
                    } else {
                        ((ScrollBarDrawable)((ScrollabilityCache)object2).scrollBar.mutate()).setAlpha(Math.round(object[0]));
                    }
                    n = 1;
                } else {
                    ((ScrollBarDrawable)((ScrollabilityCache)object2).scrollBar.mutate()).setAlpha(255);
                    n = 0;
                }
                bl2 = this.isHorizontalScrollBarEnabled();
                bl = this.isVerticalScrollBarEnabled() && !this.isVerticalScrollBarHidden();
                if (this.mRoundScrollbarRenderer == null) break block12;
                if (!bl) break block11;
                object = ((ScrollabilityCache)object2).mScrollBarBounds;
                this.getVerticalScrollBarBounds((Rect)object, null);
                this.mRoundScrollbarRenderer.drawRoundScrollbars(canvas, (float)((ScrollabilityCache)object2).scrollBar.getAlpha() / 255.0f, (Rect)object);
                if (n != 0) {
                    this.invalidate();
                }
                break block11;
            }
            if (!bl && !bl2) break block11;
            object = ((ScrollabilityCache)object2).scrollBar;
            if (bl2) {
                ((ScrollBarDrawable)object).setParameters(this.computeHorizontalScrollRange(), this.computeHorizontalScrollOffset(), this.computeHorizontalScrollExtent(), false);
                Rect rect = ((ScrollabilityCache)object2).mScrollBarBounds;
                this.getHorizontalScrollBarBounds(rect, null);
                this.onDrawHorizontalScrollBar(canvas, (Drawable)object, rect.left, rect.top, rect.right, rect.bottom);
                if (n != 0) {
                    this.invalidate(rect);
                }
            }
            if (bl) {
                ((ScrollBarDrawable)object).setParameters(this.computeVerticalScrollRange(), this.computeVerticalScrollOffset(), this.computeVerticalScrollExtent(), true);
                object2 = ((ScrollabilityCache)object2).mScrollBarBounds;
                this.getVerticalScrollBarBounds((Rect)object2, null);
                this.onDrawVerticalScrollBar(canvas, (Drawable)object, ((Rect)object2).left, ((Rect)object2).top, ((Rect)object2).right, ((Rect)object2).bottom);
                if (n != 0) {
                    this.invalidate((Rect)object2);
                }
            }
        }
    }

    @UnsupportedAppUsage
    protected void onDrawVerticalScrollBar(Canvas canvas, Drawable drawable2, int n, int n2, int n3, int n4) {
        drawable2.setBounds(n, n2, n3, n4);
        drawable2.draw(canvas);
    }

    public boolean onFilterTouchEventForSecurity(MotionEvent motionEvent) {
        return (this.mViewFlags & 1024) == 0 || (motionEvent.getFlags() & 1) == 0;
    }

    protected void onFinishInflate() {
    }

    public void onFinishTemporaryDetach() {
    }

    protected void onFocusChanged(boolean bl, int n, Rect object) {
        if (bl) {
            this.sendAccessibilityEvent(8);
        } else {
            this.notifyViewAccessibilityStateChangedIfNeeded(0);
        }
        this.switchDefaultFocusHighlight();
        if (!bl) {
            if (this.isPressed()) {
                this.setPressed(false);
            }
            if ((object = this.mAttachInfo) != null && ((AttachInfo)object).mHasWindowFocus) {
                this.notifyFocusChangeToInputMethodManager(false);
            }
            this.onFocusLost();
        } else {
            object = this.mAttachInfo;
            if (object != null && ((AttachInfo)object).mHasWindowFocus) {
                this.notifyFocusChangeToInputMethodManager(true);
            }
        }
        this.invalidate(true);
        object = this.mListenerInfo;
        if (object != null && ((ListenerInfo)object).mOnFocusChangeListener != null) {
            ((ListenerInfo)object).mOnFocusChangeListener.onFocusChange(this, bl);
        }
        if ((object = this.mAttachInfo) != null) {
            ((AttachInfo)object).mKeyDispatchState.reset(this);
        }
        this.notifyEnterOrExitForAutoFillIfNeeded(bl);
    }

    @UnsupportedAppUsage
    protected void onFocusLost() {
        this.resetPressedState();
    }

    public boolean onGenericMotionEvent(MotionEvent motionEvent) {
        return false;
    }

    public void onHoverChanged(boolean bl) {
    }

    public boolean onHoverEvent(MotionEvent motionEvent) {
        if (this.mTouchDelegate != null && this.dispatchTouchExplorationHoverEvent(motionEvent)) {
            return true;
        }
        int n = motionEvent.getActionMasked();
        if (!this.mSendingHoverAccessibilityEvents) {
            if ((n == 9 || n == 7) && !this.hasHoveredChild() && this.pointInView(motionEvent.getX(), motionEvent.getY())) {
                this.sendAccessibilityHoverEvent(128);
                this.mSendingHoverAccessibilityEvents = true;
            }
        } else if (n == 10 || n == 7 && !this.pointInView(motionEvent.getX(), motionEvent.getY())) {
            this.mSendingHoverAccessibilityEvents = false;
            this.sendAccessibilityHoverEvent(256);
        }
        if ((n == 9 || n == 7) && motionEvent.isFromSource(8194) && this.isOnScrollbar(motionEvent.getX(), motionEvent.getY())) {
            this.awakenScrollBars();
        }
        if (!this.isHoverable() && !this.isHovered()) {
            return false;
        }
        if (n != 9) {
            if (n == 10) {
                this.setHovered(false);
            }
        } else {
            this.setHovered(true);
        }
        this.dispatchGenericMotionEventInternal(motionEvent);
        return true;
    }

    public void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.onInitializeAccessibilityEvent(this, accessibilityEvent);
        } else {
            this.onInitializeAccessibilityEventInternal(accessibilityEvent);
        }
    }

    @UnsupportedAppUsage
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        accessibilityEvent.setSource(this);
        accessibilityEvent.setClassName(this.getAccessibilityClassName());
        accessibilityEvent.setPackageName(this.getContext().getPackageName());
        accessibilityEvent.setEnabled(this.isEnabled());
        accessibilityEvent.setContentDescription(this.mContentDescription);
        int n = accessibilityEvent.getEventType();
        if (n != 8) {
            CharSequence charSequence;
            if (n == 8192 && (charSequence = this.getIterableTextForAccessibility()) != null && charSequence.length() > 0) {
                accessibilityEvent.setFromIndex(this.getAccessibilitySelectionStart());
                accessibilityEvent.setToIndex(this.getAccessibilitySelectionEnd());
                accessibilityEvent.setItemCount(charSequence.length());
            }
        } else {
            Object object = this.mAttachInfo;
            object = object != null ? ((AttachInfo)object).mTempArrayList : new ArrayList<View>();
            this.getRootView().addFocusables((ArrayList<View>)object, 2, 0);
            accessibilityEvent.setItemCount(((ArrayList)object).size());
            accessibilityEvent.setCurrentItemIndex(((ArrayList)object).indexOf(this));
            if (this.mAttachInfo != null) {
                ((ArrayList)object).clear();
            }
        }
    }

    public void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(this, accessibilityNodeInfo);
        } else {
            this.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        }
    }

    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        View view;
        Object object = this.mAttachInfo;
        if (object == null) {
            return;
        }
        object = ((AttachInfo)object).mTmpInvalRect;
        this.getDrawingRect((Rect)object);
        accessibilityNodeInfo.setBoundsInParent((Rect)object);
        this.getBoundsOnScreen((Rect)object, true);
        accessibilityNodeInfo.setBoundsInScreen((Rect)object);
        object = this.getParentForAccessibility();
        if (object instanceof View) {
            accessibilityNodeInfo.setParent((View)object);
        }
        if (this.mID != -1) {
            view = this.getRootView();
            object = view;
            if (view == null) {
                object = this;
            }
            if ((object = View.super.findLabelForView(this, this.mID)) != null) {
                accessibilityNodeInfo.setLabeledBy((View)object);
            }
            if ((this.mAttachInfo.mAccessibilityFetchFlags & 16) != 0 && Resources.resourceHasPackage(this.mID)) {
                try {
                    accessibilityNodeInfo.setViewIdResourceName(this.getResources().getResourceName(this.mID));
                }
                catch (Resources.NotFoundException notFoundException) {
                    // empty catch block
                }
            }
        }
        if (this.mLabelForId != -1) {
            view = this.getRootView();
            object = view;
            if (view == null) {
                object = this;
            }
            if ((object = View.super.findViewInsideOutShouldExist(this, this.mLabelForId)) != null) {
                accessibilityNodeInfo.setLabelFor((View)object);
            }
        }
        if (this.mAccessibilityTraversalBeforeId != -1) {
            view = this.getRootView();
            object = view;
            if (view == null) {
                object = this;
            }
            if ((object = View.super.findViewInsideOutShouldExist(this, this.mAccessibilityTraversalBeforeId)) != null && ((View)object).includeForAccessibility()) {
                accessibilityNodeInfo.setTraversalBefore((View)object);
            }
        }
        if (this.mAccessibilityTraversalAfterId != -1) {
            view = this.getRootView();
            object = view;
            if (view == null) {
                object = this;
            }
            if ((object = View.super.findViewInsideOutShouldExist(this, this.mAccessibilityTraversalAfterId)) != null && ((View)object).includeForAccessibility()) {
                accessibilityNodeInfo.setTraversalAfter((View)object);
            }
        }
        accessibilityNodeInfo.setVisibleToUser(this.isVisibleToUser());
        accessibilityNodeInfo.setImportantForAccessibility(this.isImportantForAccessibility());
        accessibilityNodeInfo.setPackageName(this.mContext.getPackageName());
        accessibilityNodeInfo.setClassName(this.getAccessibilityClassName());
        accessibilityNodeInfo.setContentDescription(this.getContentDescription());
        accessibilityNodeInfo.setEnabled(this.isEnabled());
        accessibilityNodeInfo.setClickable(this.isClickable());
        accessibilityNodeInfo.setFocusable(this.isFocusable());
        accessibilityNodeInfo.setScreenReaderFocusable(this.isScreenReaderFocusable());
        accessibilityNodeInfo.setFocused(this.isFocused());
        accessibilityNodeInfo.setAccessibilityFocused(this.isAccessibilityFocused());
        accessibilityNodeInfo.setSelected(this.isSelected());
        accessibilityNodeInfo.setLongClickable(this.isLongClickable());
        accessibilityNodeInfo.setContextClickable(this.isContextClickable());
        accessibilityNodeInfo.setLiveRegion(this.getAccessibilityLiveRegion());
        object = this.mTooltipInfo;
        if (object != null && ((TooltipInfo)object).mTooltipText != null) {
            accessibilityNodeInfo.setTooltipText(this.mTooltipInfo.mTooltipText);
            object = this.mTooltipInfo.mTooltipPopup == null ? AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_TOOLTIP : AccessibilityNodeInfo.AccessibilityAction.ACTION_HIDE_TOOLTIP;
            accessibilityNodeInfo.addAction((AccessibilityNodeInfo.AccessibilityAction)object);
        }
        accessibilityNodeInfo.addAction(4);
        accessibilityNodeInfo.addAction(8);
        if (this.isFocusable()) {
            if (this.isFocused()) {
                accessibilityNodeInfo.addAction(2);
            } else {
                accessibilityNodeInfo.addAction(1);
            }
        }
        if (!this.isAccessibilityFocused()) {
            accessibilityNodeInfo.addAction(64);
        } else {
            accessibilityNodeInfo.addAction(128);
        }
        if (this.isClickable() && this.isEnabled()) {
            accessibilityNodeInfo.addAction(16);
        }
        if (this.isLongClickable() && this.isEnabled()) {
            accessibilityNodeInfo.addAction(32);
        }
        if (this.isContextClickable() && this.isEnabled()) {
            accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CONTEXT_CLICK);
        }
        if ((object = this.getIterableTextForAccessibility()) != null && object.length() > 0) {
            accessibilityNodeInfo.setTextSelection(this.getAccessibilitySelectionStart(), this.getAccessibilitySelectionEnd());
            accessibilityNodeInfo.addAction(131072);
            accessibilityNodeInfo.addAction(256);
            accessibilityNodeInfo.addAction(512);
            accessibilityNodeInfo.setMovementGranularities(11);
        }
        accessibilityNodeInfo.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_SHOW_ON_SCREEN);
        this.populateAccessibilityNodeInfoDrawingOrderInParent(accessibilityNodeInfo);
        accessibilityNodeInfo.setPaneTitle(this.mAccessibilityPaneTitle);
        accessibilityNodeInfo.setHeading(this.isAccessibilityHeading());
        object = this.mTouchDelegate;
        if (object != null) {
            accessibilityNodeInfo.setTouchDelegateInfo(((TouchDelegate)object).getTouchDelegateInfo());
        }
    }

    @Override
    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (KeyEvent.isConfirmKey(n)) {
            if ((this.mViewFlags & 32) == 32) {
                return true;
            }
            if (keyEvent.getRepeatCount() == 0) {
                n = this.mViewFlags;
                n = (n & 16384) != 16384 && (n & 2097152) != 2097152 ? 0 : 1;
                if (n != 0 || (this.mViewFlags & 1073741824) == 1073741824) {
                    float f = (float)this.getWidth() / 2.0f;
                    float f2 = (float)this.getHeight() / 2.0f;
                    if (n != 0) {
                        this.setPressed(true, f, f2);
                    }
                    this.checkForLongClick(ViewConfiguration.getLongPressTimeout(), f, f2, 0);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean onKeyLongPress(int n, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyMultiple(int n, int n2, KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
        return false;
    }

    public boolean onKeyShortcut(int n, KeyEvent keyEvent) {
        return false;
    }

    @Override
    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (KeyEvent.isConfirmKey(n)) {
            n = this.mViewFlags;
            if ((n & 32) == 32) {
                return true;
            }
            if ((n & 16384) == 16384 && this.isPressed()) {
                this.setPressed(false);
                if (!this.mHasPerformedLongPress) {
                    this.removeLongPressCallback();
                    if (!keyEvent.isCanceled()) {
                        return this.performClickInternal();
                    }
                }
            }
        }
        return false;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
    }

    protected void onMeasure(int n, int n2) {
        this.setMeasuredDimension(View.getDefaultSize(this.getSuggestedMinimumWidth(), n), View.getDefaultSize(this.getSuggestedMinimumHeight(), n2));
    }

    public void onMovedToDisplay(int n, Configuration configuration) {
    }

    protected void onOverScrolled(int n, int n2, boolean bl, boolean bl2) {
    }

    public void onPointerCaptureChange(boolean bl) {
    }

    public void onPopulateAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.onPopulateAccessibilityEvent(this, accessibilityEvent);
        } else {
            this.onPopulateAccessibilityEventInternal(accessibilityEvent);
        }
    }

    public void onPopulateAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getEventType() == 32 && this.isAccessibilityPane()) {
            accessibilityEvent.getText().add(this.getAccessibilityPaneTitle());
        }
    }

    public void onProvideAutofillStructure(ViewStructure viewStructure, int n) {
        this.onProvideStructure(viewStructure, 1, n);
    }

    public void onProvideAutofillVirtualStructure(ViewStructure viewStructure, int n) {
        if (this.mContext.isAutofillCompatibilityEnabled()) {
            this.onProvideVirtualStructureCompat(viewStructure, true);
        }
    }

    public void onProvideStructure(ViewStructure viewStructure) {
        this.onProvideStructure(viewStructure, 0, 0);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    protected void onProvideStructure(ViewStructure var1_1, int var2_2, int var3_3) {
        var4_4 = this.mID;
        if (var4_4 != -1 && !View.isViewIdGenerated(var4_4)) {
            try {
                var5_5 = this.getResources();
                var6_6 = var5_5.getResourceEntryName(var4_4);
                var7_7 = var5_5.getResourceTypeName(var4_4);
                var5_5 = var5_5.getResourcePackageName(var4_4);
            }
            catch (Resources.NotFoundException var7_8) {
                var7_7 = null;
                var6_6 = null;
                var5_5 = null;
            }
            var1_1.setId(var4_4, (String)var5_5, (String)var7_7, var6_6);
        } else {
            var1_1.setId(var4_4, null, null, null);
        }
        if (var2_2 == 1 || var2_2 == 2) {
            var4_4 = this.getAutofillType();
            if (var4_4 != 0) {
                var1_1.setAutofillType(var4_4);
                var1_1.setAutofillHints(this.getAutofillHints());
                var1_1.setAutofillValue(this.getAutofillValue());
            }
            var1_1.setImportantForAutofill(this.getImportantForAutofill());
        }
        var8_9 = 0;
        var9_10 = 0;
        var10_11 = 0;
        var11_12 = 0;
        var12_13 = var8_9;
        var4_4 = var10_11;
        if (var2_2 != 1) ** GOTO lbl-1000
        var12_13 = var8_9;
        var4_4 = var10_11;
        if ((var3_3 & 1) == 0) {
            var7_7 = null;
            var5_5 = this.getParent();
            var8_9 = var9_10;
            var3_3 = var11_12;
            if (var5_5 instanceof View) {
                var7_7 = (View)var5_5;
                var3_3 = var11_12;
                var8_9 = var9_10;
            }
            do {
                var12_13 = var8_9;
                var4_4 = var3_3;
                if (var7_7 == null) ** break block23
                var12_13 = var8_9;
                var4_4 = var3_3;
                if (var7_7.isImportantForAutofill()) ** break block23
                var8_9 += var7_7.mLeft;
                var3_3 += var7_7.mTop;
                if (!((var7_7 = var7_7.getParent()) instanceof View)) break;
                var7_7 = (View)var7_7;
            } while (true);
            var4_4 = var8_9;
        } else lbl-1000: // 4 sources:
        {
            var3_3 = var4_4;
            var4_4 = var12_13;
        }
        var8_9 = this.mLeft;
        var12_13 = this.mTop;
        var1_1.setDimens(var4_4 + var8_9, var3_3 + var12_13, this.mScrollX, this.mScrollY, this.mRight - var8_9, this.mBottom - var12_13);
        if (var2_2 == 0) {
            if (!this.hasIdentityMatrix()) {
                var1_1.setTransformation(this.getMatrix());
            }
            var1_1.setElevation(this.getZ());
        }
        var1_1.setVisibility(this.getVisibility());
        var1_1.setEnabled(this.isEnabled());
        if (this.isClickable()) {
            var1_1.setClickable(true);
        }
        if (this.isFocusable()) {
            var1_1.setFocusable(true);
        }
        if (this.isFocused()) {
            var1_1.setFocused(true);
        }
        if (this.isAccessibilityFocused()) {
            var1_1.setAccessibilityFocused(true);
        }
        if (this.isSelected()) {
            var1_1.setSelected(true);
        }
        if (this.isActivated()) {
            var1_1.setActivated(true);
        }
        if (this.isLongClickable()) {
            var1_1.setLongClickable(true);
        }
        if (this instanceof Checkable) {
            var1_1.setCheckable(true);
            if (((Checkable)this).isChecked()) {
                var1_1.setChecked(true);
            }
        }
        if (this.isOpaque()) {
            var1_1.setOpaque(true);
        }
        if (this.isContextClickable()) {
            var1_1.setContextClickable(true);
        }
        var1_1.setClassName(this.getAccessibilityClassName().toString());
        var1_1.setContentDescription(this.getContentDescription());
    }

    public void onProvideVirtualStructure(ViewStructure viewStructure) {
        this.onProvideVirtualStructureCompat(viewStructure, false);
    }

    public void onResolveDrawables(int n) {
    }

    public PointerIcon onResolvePointerIcon(MotionEvent motionEvent, int n) {
        float f = motionEvent.getX(n);
        float f2 = motionEvent.getY(n);
        if (!this.isDraggingScrollBar() && !this.isOnScrollbarThumb(f, f2)) {
            return this.mPointerIcon;
        }
        return PointerIcon.getSystemIcon(this.mContext, 1000);
    }

    protected void onRestoreInstanceState(Parcelable object) {
        this.mPrivateFlags |= 131072;
        if (object != null && !(object instanceof AbsSavedState)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Wrong state class, expecting View State but received ");
            stringBuilder.append(object.getClass().toString());
            stringBuilder.append(" instead. This usually happens when two views of different type have the same id in the same hierarchy. This view's id is ");
            stringBuilder.append(ViewDebug.resolveId(this.mContext, this.getId()));
            stringBuilder.append(". Make sure other views do not use the same id.");
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        if (object != null && object instanceof BaseSavedState) {
            BaseSavedState baseSavedState = (BaseSavedState)object;
            if ((baseSavedState.mSavedData & 1) != 0) {
                this.mStartActivityRequestWho = baseSavedState.mStartActivityRequestWhoSaved;
            }
            if ((baseSavedState.mSavedData & 2) != 0) {
                this.setAutofilled(baseSavedState.mIsAutofilled);
            }
            if ((baseSavedState.mSavedData & 4) != 0) {
                object = (BaseSavedState)object;
                ((BaseSavedState)object).mSavedData &= -5;
                if ((this.mPrivateFlags3 & 1073741824) != 0) {
                    if (Log.isLoggable(AUTOFILL_LOG_TAG, 3)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("onRestoreInstanceState(): not setting autofillId to ");
                        ((StringBuilder)object).append(baseSavedState.mAutofillViewId);
                        ((StringBuilder)object).append(" because view explicitly set it to ");
                        ((StringBuilder)object).append(this.mAutofillId);
                        Log.d(AUTOFILL_LOG_TAG, ((StringBuilder)object).toString());
                    }
                } else {
                    this.mAutofillViewId = baseSavedState.mAutofillViewId;
                    this.mAutofillId = null;
                }
            }
        }
    }

    public void onRtlPropertiesChanged(int n) {
    }

    protected Parcelable onSaveInstanceState() {
        this.mPrivateFlags |= 131072;
        if (this.mStartActivityRequestWho == null && !this.isAutofilled() && this.mAutofillViewId <= 1073741823) {
            return BaseSavedState.EMPTY_STATE;
        }
        BaseSavedState baseSavedState = new BaseSavedState(AbsSavedState.EMPTY_STATE);
        if (this.mStartActivityRequestWho != null) {
            baseSavedState.mSavedData |= 1;
        }
        if (this.isAutofilled()) {
            baseSavedState.mSavedData |= 2;
        }
        if (this.mAutofillViewId > 1073741823) {
            baseSavedState.mSavedData |= 4;
        }
        baseSavedState.mStartActivityRequestWhoSaved = this.mStartActivityRequestWho;
        baseSavedState.mIsAutofilled = this.isAutofilled();
        baseSavedState.mAutofillViewId = this.mAutofillViewId;
        return baseSavedState;
    }

    public void onScreenStateChanged(int n) {
    }

    protected void onScrollChanged(int n, int n2, int n3, int n4) {
        this.notifySubtreeAccessibilityStateChangedIfNeeded();
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            this.postSendViewScrolledAccessibilityEventCallback(n - n3, n2 - n4);
        }
        this.mBackgroundSizeChanged = true;
        this.mDefaultFocusHighlightSizeChanged = true;
        Object object = this.mForegroundInfo;
        if (object != null) {
            ((ForegroundInfo)object).mBoundsChanged = true;
        }
        if ((object = this.mAttachInfo) != null) {
            ((AttachInfo)object).mViewScrollChanged = true;
        }
        if ((object = this.mListenerInfo) != null && ((ListenerInfo)object).mOnScrollChangeListener != null) {
            this.mListenerInfo.mOnScrollChangeListener.onScrollChange(this, n, n2, n3, n4);
        }
    }

    protected boolean onSetAlpha(int n) {
        return false;
    }

    protected void onSizeChanged(int n, int n2, int n3, int n4) {
    }

    public void onStartTemporaryDetach() {
        this.removeUnsetPressCallback();
        this.mPrivateFlags |= 67108864;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        float f = motionEvent.getX();
        float f2 = motionEvent.getY();
        int n = this.mViewFlags;
        int n2 = motionEvent.getAction();
        int n3 = 0;
        boolean bl = (n & 16384) == 16384 || (n & 2097152) == 2097152 || (n & 8388608) == 8388608;
        if ((n & 32) == 32) {
            if (n2 == 1 && (this.mPrivateFlags & 16384) != 0) {
                this.setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            return bl;
        }
        TouchDelegate touchDelegate = this.mTouchDelegate;
        if (touchDelegate != null && touchDelegate.onTouchEvent(motionEvent)) {
            return true;
        }
        if (!bl && (n & 1073741824) != 1073741824) {
            return false;
        }
        if (n2 != 0) {
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 == 3) {
                        if (bl) {
                            this.setPressed(false);
                        }
                        this.removeTapCallback();
                        this.removeLongPressCallback();
                        this.mInContextButtonPress = false;
                        this.mHasPerformedLongPress = false;
                        this.mIgnoreNextUpEvent = false;
                        this.mPrivateFlags3 &= -131073;
                    }
                } else {
                    int n4;
                    if (bl) {
                        this.drawableHotspotChanged(f, f2);
                    }
                    n2 = (n4 = motionEvent.getClassification()) == 1 ? 1 : 0;
                    n = this.mTouchSlop;
                    if (n2 != 0 && this.hasPendingLongPressCallback()) {
                        float f3 = ViewConfiguration.getAmbiguousGestureMultiplier();
                        if (!this.pointInView(f, f2, n)) {
                            this.removeLongPressCallback();
                            this.checkForLongClick((long)((float)ViewConfiguration.getLongPressTimeout() * f3) - (motionEvent.getEventTime() - motionEvent.getDownTime()), f, f2, 3);
                        }
                        n2 = (int)((float)n * f3);
                    } else {
                        n2 = n;
                    }
                    if (!this.pointInView(f, f2, n2)) {
                        this.removeTapCallback();
                        this.removeLongPressCallback();
                        if ((this.mPrivateFlags & 16384) != 0) {
                            this.setPressed(false);
                        }
                        this.mPrivateFlags3 &= -131073;
                    }
                    n2 = n3;
                    if (n4 == 2) {
                        n2 = 1;
                    }
                    if (n2 != 0 && this.hasPendingLongPressCallback()) {
                        this.removeLongPressCallback();
                        this.checkForLongClick(0L, f, f2, 4);
                    }
                }
            } else {
                this.mPrivateFlags3 &= -131073;
                if ((n & 1073741824) == 1073741824) {
                    this.handleTooltipUp();
                }
                if (!bl) {
                    this.removeTapCallback();
                    this.removeLongPressCallback();
                    this.mInContextButtonPress = false;
                    this.mHasPerformedLongPress = false;
                    this.mIgnoreNextUpEvent = false;
                } else {
                    n2 = (this.mPrivateFlags & 33554432) != 0 ? 1 : 0;
                    if ((this.mPrivateFlags & 16384) != 0 || n2 != 0) {
                        boolean bl2;
                        bl = bl2 = false;
                        if (this.isFocusable()) {
                            bl = bl2;
                            if (this.isFocusableInTouchMode()) {
                                bl = bl2;
                                if (!this.isFocused()) {
                                    bl = this.requestFocus();
                                }
                            }
                        }
                        if (n2 != 0) {
                            this.setPressed(true, f, f2);
                        }
                        if (!this.mHasPerformedLongPress && !this.mIgnoreNextUpEvent) {
                            this.removeLongPressCallback();
                            if (!bl) {
                                if (this.mPerformClick == null) {
                                    this.mPerformClick = new PerformClick();
                                }
                                if (!this.post(this.mPerformClick)) {
                                    this.performClickInternal();
                                }
                            }
                        }
                        if (this.mUnsetPressedState == null) {
                            this.mUnsetPressedState = new UnsetPressedState();
                        }
                        if (n2 != 0) {
                            this.postDelayed(this.mUnsetPressedState, ViewConfiguration.getPressedStateDuration());
                        } else if (!this.post(this.mUnsetPressedState)) {
                            this.mUnsetPressedState.run();
                        }
                        this.removeTapCallback();
                    }
                    this.mIgnoreNextUpEvent = false;
                }
            }
        } else {
            if (motionEvent.getSource() == 4098) {
                this.mPrivateFlags3 |= 131072;
            }
            this.mHasPerformedLongPress = false;
            if (!bl) {
                this.checkForLongClick(ViewConfiguration.getLongPressTimeout(), f, f2, 3);
            } else if (!this.performButtonActionOnTouchDown(motionEvent)) {
                if (this.isInScrollingContainer()) {
                    this.mPrivateFlags |= 33554432;
                    if (this.mPendingCheckForTap == null) {
                        this.mPendingCheckForTap = new CheckForTap();
                    }
                    this.mPendingCheckForTap.x = motionEvent.getX();
                    this.mPendingCheckForTap.y = motionEvent.getY();
                    this.postDelayed(this.mPendingCheckForTap, ViewConfiguration.getTapTimeout());
                } else {
                    this.setPressed(true, f, f2);
                    this.checkForLongClick(ViewConfiguration.getLongPressTimeout(), f, f2, 3);
                }
            }
        }
        return true;
    }

    public boolean onTrackballEvent(MotionEvent motionEvent) {
        return false;
    }

    boolean onUnhandledKeyEvent(KeyEvent keyEvent) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mUnhandledKeyListeners != null) {
            for (int i = ListenerInfo.access$4100((ListenerInfo)this.mListenerInfo).size() - 1; i >= 0; --i) {
                if (!((OnUnhandledKeyEventListener)this.mListenerInfo.mUnhandledKeyListeners.get(i)).onUnhandledKeyEvent(this, keyEvent)) continue;
                return true;
            }
        }
        return false;
    }

    public void onVisibilityAggregated(boolean bl) {
        Object object;
        boolean bl2 = (this.mPrivateFlags3 & 536870912) != 0;
        int n = bl ? 536870912 | this.mPrivateFlags3 : this.mPrivateFlags3 & -536870913;
        this.mPrivateFlags3 = n;
        if (bl && this.mAttachInfo != null) {
            this.initialAwakenScrollBars();
        }
        if ((object = this.mBackground) != null && bl != ((Drawable)object).isVisible()) {
            ((Drawable)object).setVisible(bl, false);
        }
        if ((object = this.mDefaultFocusHighlight) != null && bl != ((Drawable)object).isVisible()) {
            ((Drawable)object).setVisible(bl, false);
        }
        if ((object = (object = this.mForegroundInfo) != null ? ((ForegroundInfo)object).mDrawable : null) != null && bl != ((Drawable)object).isVisible()) {
            ((Drawable)object).setVisible(bl, false);
        }
        if (this.isAutofillable() && (object = this.getAutofillManager()) != null && this.getAutofillViewId() > 1073741823) {
            Handler handler = this.mVisibilityChangeForAutofillHandler;
            if (handler != null) {
                handler.removeMessages(0);
            }
            if (bl) {
                ((AutofillManager)object).notifyViewVisibilityChanged(this, true);
            } else {
                if (this.mVisibilityChangeForAutofillHandler == null) {
                    this.mVisibilityChangeForAutofillHandler = new VisibilityChangeForAutofillHandler((AutofillManager)object, this);
                }
                this.mVisibilityChangeForAutofillHandler.obtainMessage(0, this).sendToTarget();
            }
        }
        if (this.isAccessibilityPane() && bl != bl2) {
            n = bl ? 16 : 32;
            this.notifyViewAccessibilityStateChangedIfNeeded(n);
        }
    }

    protected void onVisibilityChanged(View view, int n) {
    }

    public void onWindowFocusChanged(boolean bl) {
        if (!bl) {
            if (this.isPressed()) {
                this.setPressed(false);
            }
            this.mPrivateFlags3 &= -131073;
            if ((this.mPrivateFlags & 2) != 0) {
                this.notifyFocusChangeToInputMethodManager(false);
            }
            this.removeLongPressCallback();
            this.removeTapCallback();
            this.onFocusLost();
        } else if ((this.mPrivateFlags & 2) != 0) {
            this.notifyFocusChangeToInputMethodManager(true);
        }
        this.refreshDrawableState();
    }

    public void onWindowSystemUiVisibilityChanged(int n) {
    }

    protected void onWindowVisibilityChanged(int n) {
        if (n == 0) {
            this.initialAwakenScrollBars();
        }
    }

    public void outputDirtyFlags(String string2, boolean bl, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(this);
        stringBuilder.append("             DIRTY(");
        stringBuilder.append(this.mPrivateFlags & 2097152);
        stringBuilder.append(") DRAWN(");
        stringBuilder.append(this.mPrivateFlags & 32);
        stringBuilder.append(") CACHE_VALID(");
        stringBuilder.append(this.mPrivateFlags & 32768);
        stringBuilder.append(") INVALIDATED(");
        stringBuilder.append(this.mPrivateFlags & Integer.MIN_VALUE);
        stringBuilder.append(")");
        Log.d(VIEW_LOG_TAG, stringBuilder.toString());
        if (bl) {
            this.mPrivateFlags &= n;
        }
        if (this instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup)this;
            int n2 = viewGroup.getChildCount();
            for (int i = 0; i < n2; ++i) {
                View view = viewGroup.getChildAt(i);
                stringBuilder = new StringBuilder();
                stringBuilder.append(string2);
                stringBuilder.append("  ");
                view.outputDirtyFlags(stringBuilder.toString(), bl, n);
            }
        }
    }

    protected boolean overScrollBy(int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8, boolean bl) {
        boolean bl2;
        int n9 = this.mOverScrollMode;
        boolean bl3 = this.computeHorizontalScrollRange() > this.computeHorizontalScrollExtent();
        boolean bl4 = this.computeVerticalScrollRange() > this.computeVerticalScrollExtent();
        bl3 = n9 == 0 || n9 == 1 && bl3;
        bl4 = n9 == 0 || n9 == 1 && bl4;
        n3 += n;
        n = !bl3 ? 0 : n7;
        n4 += n2;
        n2 = !bl4 ? 0 : n8;
        n7 = -n;
        n += n5;
        n5 = -n2;
        n2 += n6;
        if (n3 > n) {
            bl = true;
        } else if (n3 < n7) {
            n = n7;
            bl = true;
        } else {
            bl = false;
            n = n3;
        }
        if (n4 > n2) {
            bl2 = true;
        } else if (n4 < n5) {
            n2 = n5;
            bl2 = true;
        } else {
            bl2 = false;
            n2 = n4;
        }
        this.onOverScrolled(n, n2, bl, bl2);
        bl = bl || bl2;
        return bl;
    }

    public boolean performAccessibilityAction(int n, Bundle bundle) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            return accessibilityDelegate.performAccessibilityAction(this, n, bundle);
        }
        return this.performAccessibilityActionInternal(n, bundle);
    }

    @UnsupportedAppUsage
    public boolean performAccessibilityActionInternal(int n, Bundle object) {
        if (this.isNestedScrollingEnabled() && (n == 8192 || n == 4096 || n == 16908344 || n == 16908345 || n == 16908346 || n == 16908347) && this.dispatchNestedPrePerformAccessibilityAction(n, (Bundle)object)) {
            return true;
        }
        switch (n) {
            default: {
                break;
            }
            case 16908357: {
                object = this.mTooltipInfo;
                if (object != null && ((TooltipInfo)object).mTooltipPopup != null) {
                    this.hideTooltip();
                    return true;
                }
                return false;
            }
            case 16908356: {
                object = this.mTooltipInfo;
                if (object != null && ((TooltipInfo)object).mTooltipPopup != null) {
                    return false;
                }
                return this.showLongClickTooltip(0, 0);
            }
            case 16908348: {
                if (!this.isContextClickable()) break;
                this.performContextClick();
                return true;
            }
            case 16908342: {
                object = this.mAttachInfo;
                if (object == null) break;
                object = ((AttachInfo)object).mTmpInvalRect;
                this.getDrawingRect((Rect)object);
                return this.requestRectangleOnScreen((Rect)object, true);
            }
            case 131072: {
                if (this.getIterableTextForAccessibility() == null) {
                    return false;
                }
                int n2 = -1;
                n = object != null ? ((BaseBundle)object).getInt("ACTION_ARGUMENT_SELECTION_START_INT", -1) : -1;
                if (object != null) {
                    n2 = ((BaseBundle)object).getInt("ACTION_ARGUMENT_SELECTION_END_INT", -1);
                }
                if (this.getAccessibilitySelectionStart() == n && this.getAccessibilitySelectionEnd() == n2 || n != n2) break;
                this.setAccessibilitySelection(n, n2);
                this.notifyViewAccessibilityStateChangedIfNeeded(0);
                return true;
            }
            case 512: {
                if (object == null) break;
                return this.traverseAtGranularity(((BaseBundle)object).getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT"), false, ((BaseBundle)object).getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN"));
            }
            case 256: {
                if (object == null) break;
                return this.traverseAtGranularity(((BaseBundle)object).getInt("ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT"), true, ((BaseBundle)object).getBoolean("ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN"));
            }
            case 128: {
                if (!this.isAccessibilityFocused()) break;
                this.clearAccessibilityFocus();
                return true;
            }
            case 64: {
                if (this.isAccessibilityFocused()) break;
                return this.requestAccessibilityFocus();
            }
            case 32: {
                if (!this.isLongClickable()) break;
                this.performLongClick();
                return true;
            }
            case 16: {
                if (!this.isClickable()) break;
                this.performClickInternal();
                return true;
            }
            case 8: {
                if (!this.isSelected()) break;
                this.setSelected(false);
                return this.isSelected() ^ true;
            }
            case 4: {
                if (this.isSelected()) break;
                this.setSelected(true);
                return this.isSelected();
            }
            case 2: {
                if (!this.hasFocus()) break;
                this.clearFocus();
                return this.isFocused() ^ true;
            }
            case 1: {
                if (this.hasFocus()) break;
                this.getViewRootImpl().ensureTouchMode(false);
                return this.requestFocus();
            }
        }
        return false;
    }

    protected boolean performButtonActionOnTouchDown(MotionEvent motionEvent) {
        if (motionEvent.isFromSource(8194) && (motionEvent.getButtonState() & 2) != 0) {
            this.showContextMenu(motionEvent.getX(), motionEvent.getY());
            this.mPrivateFlags |= 67108864;
            return true;
        }
        return false;
    }

    public boolean performClick() {
        boolean bl;
        this.notifyAutofillManagerOnClick();
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnClickListener != null) {
            this.playSoundEffect(0);
            listenerInfo.mOnClickListener.onClick(this);
            bl = true;
        } else {
            bl = false;
        }
        this.sendAccessibilityEvent(1);
        this.notifyEnterOrExitForAutoFillIfNeeded(true);
        return bl;
    }

    void performCollectViewAttributes(AttachInfo attachInfo, int n) {
        if ((n & 12) == 0) {
            if ((this.mViewFlags & 67108864) == 67108864) {
                attachInfo.mKeepScreenOn = true;
            }
            attachInfo.mSystemUiVisibility |= this.mSystemUiVisibility;
            ListenerInfo listenerInfo = this.mListenerInfo;
            if (listenerInfo != null && listenerInfo.mOnSystemUiVisibilityChangeListener != null) {
                attachInfo.mHasSystemUiListeners = true;
            }
        }
    }

    public boolean performContextClick() {
        this.sendAccessibilityEvent(8388608);
        boolean bl = false;
        ListenerInfo listenerInfo = this.mListenerInfo;
        boolean bl2 = bl;
        if (listenerInfo != null) {
            bl2 = bl;
            if (listenerInfo.mOnContextClickListener != null) {
                bl2 = listenerInfo.mOnContextClickListener.onContextClick(this);
            }
        }
        if (bl2) {
            this.performHapticFeedback(6);
        }
        return bl2;
    }

    public boolean performContextClick(float f, float f2) {
        return this.performContextClick();
    }

    public boolean performHapticFeedback(int n) {
        return this.performHapticFeedback(n, 0);
    }

    public boolean performHapticFeedback(int n, int n2) {
        Object object = this.mAttachInfo;
        boolean bl = false;
        if (object == null) {
            return false;
        }
        if ((n2 & 1) == 0 && !this.isHapticFeedbackEnabled()) {
            return false;
        }
        object = this.mAttachInfo.mRootCallbacks;
        if ((n2 & 2) != 0) {
            bl = true;
        }
        return object.performHapticFeedback(n, bl);
    }

    public boolean performLongClick() {
        return this.performLongClickInternal(this.mLongClickX, this.mLongClickY);
    }

    public boolean performLongClick(float f, float f2) {
        this.mLongClickX = f;
        this.mLongClickY = f2;
        boolean bl = this.performLongClick();
        this.mLongClickX = Float.NaN;
        this.mLongClickY = Float.NaN;
        return bl;
    }

    public void playSoundEffect(int n) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && attachInfo.mRootCallbacks != null && this.isSoundEffectsEnabled()) {
            this.mAttachInfo.mRootCallbacks.playSoundEffect(n);
            return;
        }
    }

    protected boolean pointInHoveredChild(MotionEvent motionEvent) {
        return false;
    }

    final boolean pointInView(float f, float f2) {
        return this.pointInView(f, f2, 0.0f);
    }

    @UnsupportedAppUsage
    public boolean pointInView(float f, float f2, float f3) {
        boolean bl = f >= -f3 && f2 >= -f3 && f < (float)(this.mRight - this.mLeft) + f3 && f2 < (float)(this.mBottom - this.mTop) + f3;
        return bl;
    }

    public boolean post(Runnable runnable) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.post(runnable);
        }
        this.getRunQueue().post(runnable);
        return true;
    }

    public boolean postDelayed(Runnable runnable, long l) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            return attachInfo.mHandler.postDelayed(runnable, l);
        }
        this.getRunQueue().postDelayed(runnable, l);
        return true;
    }

    public void postInvalidate() {
        this.postInvalidateDelayed(0L);
    }

    public void postInvalidate(int n, int n2, int n3, int n4) {
        this.postInvalidateDelayed(0L, n, n2, n3, n4);
    }

    public void postInvalidateDelayed(long l) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateDelayed(this, l);
        }
    }

    public void postInvalidateDelayed(long l, int n, int n2, int n3, int n4) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            AttachInfo.InvalidateInfo invalidateInfo = AttachInfo.InvalidateInfo.obtain();
            invalidateInfo.target = this;
            invalidateInfo.left = n;
            invalidateInfo.top = n2;
            invalidateInfo.right = n3;
            invalidateInfo.bottom = n4;
            attachInfo.mViewRootImpl.dispatchInvalidateRectDelayed(invalidateInfo, l);
        }
    }

    public void postInvalidateOnAnimation() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.dispatchInvalidateOnAnimation(this);
        }
    }

    public void postInvalidateOnAnimation(int n, int n2, int n3, int n4) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            AttachInfo.InvalidateInfo invalidateInfo = AttachInfo.InvalidateInfo.obtain();
            invalidateInfo.target = this;
            invalidateInfo.left = n;
            invalidateInfo.top = n2;
            invalidateInfo.right = n3;
            invalidateInfo.bottom = n4;
            attachInfo.mViewRootImpl.dispatchInvalidateRectOnAnimation(invalidateInfo);
        }
    }

    public void postOnAnimation(Runnable runnable) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallback(1, runnable, null);
        } else {
            this.getRunQueue().post(runnable);
        }
    }

    public void postOnAnimationDelayed(Runnable runnable, long l) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, runnable, null, l);
        } else {
            this.getRunQueue().postDelayed(runnable, l);
        }
    }

    void postUpdateSystemGestureExclusionRects() {
        Handler handler = this.getHandler();
        if (handler != null) {
            handler.postAtFrontOfQueue(new _$$Lambda$WlJa6OPA72p3gYtA3nVKC7Z1tGY(this));
        }
    }

    @UnsupportedAppUsage
    protected void recomputePadding() {
        this.internalSetPadding(this.mUserPaddingLeft, this.mPaddingTop, this.mUserPaddingRight, this.mUserPaddingBottom);
    }

    public void refreshDrawableState() {
        this.mPrivateFlags |= 1024;
        this.drawableStateChanged();
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.childDrawableStateChanged(this);
        }
    }

    public void releasePointerCapture() {
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestPointerCapture(false);
        }
    }

    public boolean removeCallbacks(Runnable runnable) {
        if (runnable != null) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mHandler.removeCallbacks(runnable);
                attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, runnable, null);
            }
            this.getRunQueue().removeCallbacks(runnable);
        }
        return true;
    }

    public void removeFrameMetricsListener(Window.OnFrameMetricsAvailableListener arrayList) {
        ThreadedRenderer threadedRenderer = this.getThreadedRenderer();
        FrameMetricsObserver frameMetricsObserver = this.findFrameMetricsObserver((Window.OnFrameMetricsAvailableListener)((Object)arrayList));
        if (frameMetricsObserver != null) {
            arrayList = this.mFrameMetricsObservers;
            if (arrayList != null) {
                arrayList.remove(frameMetricsObserver);
                if (threadedRenderer != null) {
                    threadedRenderer.removeFrameMetricsObserver(frameMetricsObserver);
                }
            }
            return;
        }
        throw new IllegalArgumentException("attempt to remove OnFrameMetricsAvailableListener that was never added");
    }

    public void removeOnAttachStateChangeListener(OnAttachStateChangeListener onAttachStateChangeListener) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnAttachStateChangeListeners != null) {
            listenerInfo.mOnAttachStateChangeListeners.remove(onAttachStateChangeListener);
            return;
        }
    }

    public void removeOnLayoutChangeListener(OnLayoutChangeListener onLayoutChangeListener) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mOnLayoutChangeListeners != null) {
            listenerInfo.mOnLayoutChangeListeners.remove(onLayoutChangeListener);
            return;
        }
    }

    public void removeOnUnhandledKeyEventListener(OnUnhandledKeyEventListener object) {
        ListenerInfo listenerInfo = this.mListenerInfo;
        if (listenerInfo != null && listenerInfo.mUnhandledKeyListeners != null && !this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
            this.mListenerInfo.mUnhandledKeyListeners.remove(object);
            if (this.mListenerInfo.mUnhandledKeyListeners.isEmpty()) {
                this.mListenerInfo.mUnhandledKeyListeners = null;
                object = this.mParent;
                if (object instanceof ViewGroup) {
                    ((ViewGroup)object).decrementChildUnhandledKeyListeners();
                }
            }
        }
    }

    @UnsupportedAppUsage
    public boolean requestAccessibilityFocus() {
        Object object = AccessibilityManager.getInstance(this.mContext);
        if (((AccessibilityManager)object).isEnabled() && ((AccessibilityManager)object).isTouchExplorationEnabled()) {
            if ((this.mViewFlags & 12) != 0) {
                return false;
            }
            int n = this.mPrivateFlags2;
            if ((n & 67108864) == 0) {
                this.mPrivateFlags2 = n | 67108864;
                object = this.getViewRootImpl();
                if (object != null) {
                    ((ViewRootImpl)object).setAccessibilityFocus(this, null);
                }
                this.invalidate();
                this.sendAccessibilityEvent(32768);
                return true;
            }
            return false;
        }
        return false;
    }

    public void requestApplyInsets() {
        this.requestFitSystemWindows();
    }

    @Deprecated
    public void requestFitSystemWindows() {
        ViewParent viewParent = this.mParent;
        if (viewParent != null) {
            viewParent.requestFitSystemWindows();
        }
    }

    public final boolean requestFocus() {
        return this.requestFocus(130);
    }

    public final boolean requestFocus(int n) {
        return this.requestFocus(n, null);
    }

    public boolean requestFocus(int n, Rect rect) {
        return this.requestFocusNoSearch(n, rect);
    }

    public final boolean requestFocusFromTouch() {
        ViewRootImpl viewRootImpl;
        if (this.isInTouchMode() && (viewRootImpl = this.getViewRootImpl()) != null) {
            viewRootImpl.ensureTouchMode(false);
        }
        return this.requestFocus(130);
    }

    public void requestKeyboardShortcuts(List<KeyboardShortcutGroup> list, int n) {
    }

    public void requestLayout() {
        Object object = this.mMeasureCache;
        if (object != null) {
            ((LongSparseLongArray)object).clear();
        }
        if ((object = this.mAttachInfo) != null && ((AttachInfo)object).mViewRequestingLayout == null) {
            object = this.getViewRootImpl();
            if (object != null && ((ViewRootImpl)object).isInLayout() && !((ViewRootImpl)object).requestLayoutDuringLayout(this)) {
                return;
            }
            this.mAttachInfo.mViewRequestingLayout = this;
        }
        this.mPrivateFlags |= 4096;
        this.mPrivateFlags |= Integer.MIN_VALUE;
        object = this.mParent;
        if (object != null && !object.isLayoutRequested()) {
            this.mParent.requestLayout();
        }
        if ((object = this.mAttachInfo) != null && ((AttachInfo)object).mViewRequestingLayout == this) {
            this.mAttachInfo.mViewRequestingLayout = null;
        }
    }

    public void requestPointerCapture() {
        ViewRootImpl viewRootImpl = this.getViewRootImpl();
        if (viewRootImpl != null) {
            viewRootImpl.requestPointerCapture(true);
        }
    }

    public boolean requestRectangleOnScreen(Rect rect) {
        return this.requestRectangleOnScreen(rect, false);
    }

    public boolean requestRectangleOnScreen(Rect rect, boolean bl) {
        boolean bl2;
        if (this.mParent == null) {
            return false;
        }
        View view = this;
        Object object = this.mAttachInfo;
        object = object != null ? ((AttachInfo)object).mTmpTransformRect : new RectF();
        ((RectF)object).set(rect);
        ViewParent viewParent = this.mParent;
        boolean bl3 = false;
        do {
            bl2 = bl3;
            if (viewParent == null) break;
            rect.set((int)((RectF)object).left, (int)((RectF)object).top, (int)((RectF)object).right, (int)((RectF)object).bottom);
            bl3 |= viewParent.requestChildRectangleOnScreen(view, rect, bl);
            if (!(viewParent instanceof View)) {
                bl2 = bl3;
                break;
            }
            ((RectF)object).offset(view.mLeft - view.getScrollX(), view.mTop - view.getScrollY());
            view = (View)((Object)viewParent);
            viewParent = view.getParent();
        } while (true);
        return bl2;
    }

    public final void requestUnbufferedDispatch(MotionEvent motionEvent) {
        int n = motionEvent.getAction();
        if (this.mAttachInfo != null && (n == 0 || n == 2) && motionEvent.isTouchEvent()) {
            this.mAttachInfo.mUnbufferedDispatchRequested = true;
            return;
        }
    }

    public final <T extends View> T requireViewById(int n) {
        T t = this.findViewById(n);
        if (t != null) {
            return t;
        }
        throw new IllegalArgumentException("ID does not reference a View inside this View");
    }

    @UnsupportedAppUsage
    public void resetPaddingToInitialValues() {
        if (this.isRtlCompatibilityMode()) {
            this.mPaddingLeft = this.mUserPaddingLeftInitial;
            this.mPaddingRight = this.mUserPaddingRightInitial;
            return;
        }
        if (this.isLayoutRtl()) {
            int n = this.mUserPaddingEnd;
            if (n < 0) {
                n = this.mUserPaddingLeftInitial;
            }
            this.mPaddingLeft = n;
            n = this.mUserPaddingStart;
            if (n < 0) {
                n = this.mUserPaddingRightInitial;
            }
            this.mPaddingRight = n;
        } else {
            int n = this.mUserPaddingStart;
            if (n < 0) {
                n = this.mUserPaddingLeftInitial;
            }
            this.mPaddingLeft = n;
            n = this.mUserPaddingEnd;
            if (n < 0) {
                n = this.mUserPaddingRightInitial;
            }
            this.mPaddingRight = n;
        }
    }

    public void resetPivot() {
        if (this.mRenderNode.resetPivot()) {
            this.invalidateViewProperty(false, false);
        }
    }

    protected void resetResolvedDrawables() {
        this.resetResolvedDrawablesInternal();
    }

    void resetResolvedDrawablesInternal() {
        this.mPrivateFlags2 &= -1073741825;
    }

    public void resetResolvedLayoutDirection() {
        this.mPrivateFlags2 &= -49;
    }

    public void resetResolvedPadding() {
        this.resetResolvedPaddingInternal();
    }

    void resetResolvedPaddingInternal() {
        this.mPrivateFlags2 &= -536870913;
    }

    public void resetResolvedTextAlignment() {
        this.mPrivateFlags2 &= -983041;
        this.mPrivateFlags2 |= 131072;
    }

    public void resetResolvedTextDirection() {
        this.mPrivateFlags2 &= -7681;
        this.mPrivateFlags2 |= 1024;
    }

    public void resetRtlProperties() {
        this.resetResolvedLayoutDirection();
        this.resetResolvedTextDirection();
        this.resetResolvedTextAlignment();
        this.resetResolvedPadding();
        this.resetResolvedDrawables();
    }

    void resetSubtreeAccessibilityStateChanged() {
        this.mPrivateFlags2 &= -134217729;
    }

    protected void resolveDrawables() {
        if (!this.isLayoutDirectionResolved() && this.getRawLayoutDirection() == 2) {
            return;
        }
        int n = this.isLayoutDirectionResolved() ? this.getLayoutDirection() : this.getRawLayoutDirection();
        Object object = this.mBackground;
        if (object != null) {
            ((Drawable)object).setLayoutDirection(n);
        }
        if ((object = this.mForegroundInfo) != null && ((ForegroundInfo)object).mDrawable != null) {
            this.mForegroundInfo.mDrawable.setLayoutDirection(n);
        }
        if ((object = this.mDefaultFocusHighlight) != null) {
            ((Drawable)object).setLayoutDirection(n);
        }
        this.mPrivateFlags2 |= 1073741824;
        this.onResolveDrawables(n);
    }

    public boolean resolveLayoutDirection() {
        this.mPrivateFlags2 &= -49;
        if (this.hasRtlSupport()) {
            int n = this.mPrivateFlags2;
            int n2 = (n & 12) >> 2;
            if (n2 != 1) {
                if (n2 != 2) {
                    if (n2 == 3 && 1 == TextUtils.getLayoutDirectionFromLocale(Locale.getDefault())) {
                        this.mPrivateFlags2 |= 16;
                    }
                } else {
                    block11 : {
                        if (!this.canResolveLayoutDirection()) {
                            return false;
                        }
                        if (this.mParent.isLayoutDirectionResolved()) break block11;
                        return false;
                    }
                    try {
                        if (this.mParent.getLayoutDirection() == 1) {
                            this.mPrivateFlags2 |= 16;
                        }
                    }
                    catch (AbstractMethodError abstractMethodError) {
                        StringBuilder stringBuilder = new StringBuilder();
                        stringBuilder.append(this.mParent.getClass().getSimpleName());
                        stringBuilder.append(" does not fully implement ViewParent");
                        Log.e(VIEW_LOG_TAG, stringBuilder.toString(), abstractMethodError);
                    }
                }
            } else {
                this.mPrivateFlags2 = n | 16;
            }
        }
        this.mPrivateFlags2 |= 32;
        return true;
    }

    public void resolveLayoutParams() {
        ViewGroup.LayoutParams layoutParams = this.mLayoutParams;
        if (layoutParams != null) {
            layoutParams.resolveLayoutDirection(this.getLayoutDirection());
        }
    }

    @UnsupportedAppUsage
    public void resolvePadding() {
        int n = this.getLayoutDirection();
        if (!this.isRtlCompatibilityMode()) {
            int n2;
            if (!(this.mBackground == null || this.mLeftPaddingDefined && this.mRightPaddingDefined)) {
                Rect rect;
                Rect rect2 = rect = sThreadLocal.get();
                if (rect == null) {
                    rect2 = new Rect();
                    sThreadLocal.set(rect2);
                }
                this.mBackground.getPadding(rect2);
                if (!this.mLeftPaddingDefined) {
                    this.mUserPaddingLeftInitial = rect2.left;
                }
                if (!this.mRightPaddingDefined) {
                    this.mUserPaddingRightInitial = rect2.right;
                }
            }
            if (n != 1) {
                n2 = this.mUserPaddingStart;
                this.mUserPaddingLeft = n2 != Integer.MIN_VALUE ? n2 : this.mUserPaddingLeftInitial;
                n2 = this.mUserPaddingEnd;
                this.mUserPaddingRight = n2 != Integer.MIN_VALUE ? n2 : this.mUserPaddingRightInitial;
            } else {
                n2 = this.mUserPaddingStart;
                this.mUserPaddingRight = n2 != Integer.MIN_VALUE ? n2 : this.mUserPaddingRightInitial;
                n2 = this.mUserPaddingEnd;
                this.mUserPaddingLeft = n2 != Integer.MIN_VALUE ? n2 : this.mUserPaddingLeftInitial;
            }
            n2 = this.mUserPaddingBottom;
            if (n2 < 0) {
                n2 = this.mPaddingBottom;
            }
            this.mUserPaddingBottom = n2;
        }
        this.internalSetPadding(this.mUserPaddingLeft, this.mPaddingTop, this.mUserPaddingRight, this.mUserPaddingBottom);
        this.onRtlPropertiesChanged(n);
        this.mPrivateFlags2 |= 536870912;
    }

    public boolean resolveRtlPropertiesIfNeeded() {
        if (!this.needRtlPropertiesResolution()) {
            return false;
        }
        if (!this.isLayoutDirectionResolved()) {
            this.resolveLayoutDirection();
            this.resolveLayoutParams();
        }
        if (!this.isTextDirectionResolved()) {
            this.resolveTextDirection();
        }
        if (!this.isTextAlignmentResolved()) {
            this.resolveTextAlignment();
        }
        if (!this.areDrawablesResolved()) {
            this.resolveDrawables();
        }
        if (!this.isPaddingResolved()) {
            this.resolvePadding();
        }
        this.onRtlPropertiesChanged(this.getLayoutDirection());
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean resolveTextAlignment() {
        this.mPrivateFlags2 &= -983041;
        if (this.hasRtlSupport()) {
            block14 : {
                var1_1 = this.getRawTextAlignment();
                switch (var1_1) {
                    default: {
                        this.mPrivateFlags2 |= 131072;
                        ** break;
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: {
                        this.mPrivateFlags2 |= var1_1 << 17;
                        ** break;
                    }
                    case 0: 
                }
                if (!this.canResolveTextAlignment()) {
                    this.mPrivateFlags2 |= 131072;
                    return false;
                }
                try {
                    if (this.mParent.isTextAlignmentResolved()) break block14;
                    this.mPrivateFlags2 = 131072 | this.mPrivateFlags2;
                    return false;
                }
                catch (AbstractMethodError var2_3) {
                    var3_5 = new StringBuilder();
                    var3_5.append(this.mParent.getClass().getSimpleName());
                    var3_5.append(" does not fully implement ViewParent");
                    Log.e("View", var3_5.toString(), var2_3);
                    this.mPrivateFlags2 |= 196608;
                    return true;
                }
            }
            try {
                var1_1 = this.mParent.getTextAlignment();
            }
            catch (AbstractMethodError var2_2) {
                var3_4 = new StringBuilder();
                var3_4.append(this.mParent.getClass().getSimpleName());
                var3_4.append(" does not fully implement ViewParent");
                Log.e("View", var3_4.toString(), var2_2);
                var1_1 = 1;
            }
            switch (var1_1) {
                default: {
                    this.mPrivateFlags2 |= 131072;
                    ** break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
            }
            this.mPrivateFlags2 |= var1_1 << 17;
            ** break;
lbl49: // 4 sources:
        } else {
            this.mPrivateFlags2 |= 131072;
        }
        this.mPrivateFlags2 |= 65536;
        return true;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public boolean resolveTextDirection() {
        this.mPrivateFlags2 &= -7681;
        if (this.hasRtlSupport()) {
            block14 : {
                var1_1 = this.getRawTextDirection();
                switch (var1_1) {
                    default: {
                        this.mPrivateFlags2 |= 1024;
                        ** break;
                    }
                    case 1: 
                    case 2: 
                    case 3: 
                    case 4: 
                    case 5: 
                    case 6: 
                    case 7: {
                        this.mPrivateFlags2 |= var1_1 << 10;
                        ** break;
                    }
                    case 0: 
                }
                if (!this.canResolveTextDirection()) {
                    this.mPrivateFlags2 |= 1024;
                    return false;
                }
                try {
                    if (this.mParent.isTextDirectionResolved()) break block14;
                    this.mPrivateFlags2 |= 1024;
                    return false;
                }
                catch (AbstractMethodError var3_5) {
                    var2_3 = new StringBuilder();
                    var2_3.append(this.mParent.getClass().getSimpleName());
                    var2_3.append(" does not fully implement ViewParent");
                    Log.e("View", var2_3.toString(), var3_5);
                    this.mPrivateFlags2 |= 1536;
                    return true;
                }
            }
            try {
                var1_1 = this.mParent.getTextDirection();
            }
            catch (AbstractMethodError var2_2) {
                var3_4 = new StringBuilder();
                var3_4.append(this.mParent.getClass().getSimpleName());
                var3_4.append(" does not fully implement ViewParent");
                Log.e("View", var3_4.toString(), var2_2);
                var1_1 = 3;
            }
            switch (var1_1) {
                default: {
                    this.mPrivateFlags2 |= 1024;
                    ** break;
                }
                case 1: 
                case 2: 
                case 3: 
                case 4: 
                case 5: 
                case 6: 
                case 7: 
            }
            this.mPrivateFlags2 |= var1_1 << 10;
            ** break;
lbl49: // 4 sources:
        } else {
            this.mPrivateFlags2 |= 1024;
        }
        this.mPrivateFlags2 |= 512;
        return true;
    }

    public boolean restoreDefaultFocus() {
        return this.requestFocus(130);
    }

    public boolean restoreFocusInCluster(int n) {
        if (this.restoreDefaultFocus()) {
            return true;
        }
        return this.requestFocus(n);
    }

    public boolean restoreFocusNotInCluster() {
        return this.requestFocus(130);
    }

    public void restoreHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.dispatchRestoreInstanceState(sparseArray);
    }

    boolean rootViewRequestFocus() {
        View view = this.getRootView();
        boolean bl = view != null && view.requestFocus();
        return bl;
    }

    public final void saveAttributeDataForStyleable(Context arrn, int[] arrn2, AttributeSet attributeSet, TypedArray typedArray, int n, int n2) {
        if (!sDebugViewAttributes) {
            return;
        }
        arrn = arrn.getTheme().getAttributeResolutionStack(n, n2, this.mExplicitStyle);
        if (this.mAttributeResolutionStacks == null) {
            this.mAttributeResolutionStacks = new SparseArray();
        }
        if (this.mAttributeSourceResId == null) {
            this.mAttributeSourceResId = new SparseIntArray();
        }
        n2 = typedArray.getIndexCount();
        for (n = 0; n < n2; ++n) {
            int n3 = typedArray.getIndex(n);
            this.mAttributeSourceResId.append(arrn2[n3], typedArray.getSourceResourceId(n3, 0));
            this.mAttributeResolutionStacks.append(arrn2[n3], arrn);
        }
    }

    public void saveHierarchyState(SparseArray<Parcelable> sparseArray) {
        this.dispatchSaveInstanceState(sparseArray);
    }

    @Override
    public void scheduleDrawable(Drawable drawable2, Runnable runnable, long l) {
        if (this.verifyDrawable(drawable2) && runnable != null) {
            l -= SystemClock.uptimeMillis();
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mViewRootImpl.mChoreographer.postCallbackDelayed(1, runnable, drawable2, Choreographer.subtractFrameDelay(l));
            } else {
                this.getRunQueue().postDelayed(runnable, l);
            }
        }
    }

    public void scrollBy(int n, int n2) {
        this.scrollTo(this.mScrollX + n, this.mScrollY + n2);
    }

    public void scrollTo(int n, int n2) {
        if (this.mScrollX != n || this.mScrollY != n2) {
            int n3 = this.mScrollX;
            int n4 = this.mScrollY;
            this.mScrollX = n;
            this.mScrollY = n2;
            this.invalidateParentCaches();
            this.onScrollChanged(this.mScrollX, this.mScrollY, n3, n4);
            if (!this.awakenScrollBars()) {
                this.postInvalidateOnAnimation();
            }
        }
    }

    @Override
    public void sendAccessibilityEvent(int n) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.sendAccessibilityEvent(this, n);
        } else {
            this.sendAccessibilityEventInternal(n);
        }
    }

    public void sendAccessibilityEventInternal(int n) {
        if (AccessibilityManager.getInstance(this.mContext).isEnabled()) {
            this.sendAccessibilityEventUnchecked(AccessibilityEvent.obtain(n));
        }
    }

    @Override
    public void sendAccessibilityEventUnchecked(AccessibilityEvent accessibilityEvent) {
        AccessibilityDelegate accessibilityDelegate = this.mAccessibilityDelegate;
        if (accessibilityDelegate != null) {
            accessibilityDelegate.sendAccessibilityEventUnchecked(this, accessibilityEvent);
        } else {
            this.sendAccessibilityEventUncheckedInternal(accessibilityEvent);
        }
    }

    public void sendAccessibilityEventUncheckedInternal(AccessibilityEvent accessibilityEvent) {
        int n = accessibilityEvent.getEventType();
        int n2 = 1;
        n = n == 32 ? 1 : 0;
        n = n != 0 && (32 & accessibilityEvent.getContentChangeTypes()) != 0 ? n2 : 0;
        if (!this.isShown() && n == 0) {
            return;
        }
        this.onInitializeAccessibilityEvent(accessibilityEvent);
        if ((accessibilityEvent.getEventType() & 172479) != 0) {
            this.dispatchPopulateAccessibilityEvent(accessibilityEvent);
        }
        if (this.getParent() != null) {
            this.getParent().requestSendAccessibilityEvent(this, accessibilityEvent);
        }
    }

    public void setAccessibilityDelegate(AccessibilityDelegate accessibilityDelegate) {
        this.mAccessibilityDelegate = accessibilityDelegate;
    }

    public void setAccessibilityHeading(boolean bl) {
        this.updatePflags3AndNotifyA11yIfChanged(Integer.MIN_VALUE, bl);
    }

    public void setAccessibilityLiveRegion(int n) {
        if (n != this.getAccessibilityLiveRegion()) {
            this.mPrivateFlags2 &= -25165825;
            this.mPrivateFlags2 |= n << 23 & 25165824;
            this.notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public void setAccessibilityPaneTitle(CharSequence charSequence) {
        if (!TextUtils.equals(charSequence, this.mAccessibilityPaneTitle)) {
            this.mAccessibilityPaneTitle = charSequence;
            this.notifyViewAccessibilityStateChangedIfNeeded(8);
        }
    }

    public void setAccessibilitySelection(int n, int n2) {
        if (n == n2 && n2 == this.mAccessibilityCursorPosition) {
            return;
        }
        this.mAccessibilityCursorPosition = n >= 0 && n == n2 && n2 <= this.getIterableTextForAccessibility().length() ? n : -1;
        this.sendAccessibilityEvent(8192);
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalAfter(int n) {
        if (this.mAccessibilityTraversalAfterId == n) {
            return;
        }
        this.mAccessibilityTraversalAfterId = n;
        this.notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    @RemotableViewMethod
    public void setAccessibilityTraversalBefore(int n) {
        if (this.mAccessibilityTraversalBeforeId == n) {
            return;
        }
        this.mAccessibilityTraversalBeforeId = n;
        this.notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public void setActivated(boolean bl) {
        int n = this.mPrivateFlags;
        int n2 = 1073741824;
        boolean bl2 = (n & 1073741824) != 0;
        if (bl2 != bl) {
            n = this.mPrivateFlags;
            if (!bl) {
                n2 = 0;
            }
            this.mPrivateFlags = n & -1073741825 | n2;
            this.invalidate(true);
            this.refreshDrawableState();
            this.dispatchSetActivated(bl);
        }
    }

    public void setAlpha(float f) {
        this.ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != f) {
            this.setAlphaInternal(f);
            if (this.onSetAlpha((int)(255.0f * f))) {
                this.mPrivateFlags |= 262144;
                this.invalidateParentCaches();
                this.invalidate(true);
            } else {
                this.mPrivateFlags &= -262145;
                this.invalidateViewProperty(true, false);
                this.mRenderNode.setAlpha(this.getFinalAlpha());
            }
        }
    }

    void setAlphaInternal(float f) {
        float f2 = this.mTransformationInfo.mAlpha;
        this.mTransformationInfo.mAlpha = f;
        boolean bl = true;
        boolean bl2 = f == 0.0f;
        if (f2 != 0.0f) {
            bl = false;
        }
        if (bl2 ^ bl) {
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=123768435L)
    boolean setAlphaNoInvalidation(float f) {
        this.ensureTransformationInfo();
        if (this.mTransformationInfo.mAlpha != f) {
            this.setAlphaInternal(f);
            if (this.onSetAlpha((int)(255.0f * f))) {
                this.mPrivateFlags |= 262144;
                return true;
            }
            this.mPrivateFlags &= -262145;
            this.mRenderNode.setAlpha(this.getFinalAlpha());
        }
        return false;
    }

    public void setAnimation(Animation animation) {
        this.mCurrentAnimation = animation;
        if (animation != null) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null && attachInfo.mDisplayState == 1 && animation.getStartTime() == -1L) {
                animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());
            }
            animation.reset();
        }
    }

    public void setAnimationMatrix(Matrix matrix) {
        this.invalidateViewProperty(true, false);
        this.mRenderNode.setAnimationMatrix(matrix);
        this.invalidateViewProperty(false, true);
        this.invalidateParentIfNeededAndWasQuickRejected();
    }

    @UnsupportedAppUsage
    public void setAssistBlocked(boolean bl) {
        this.mPrivateFlags3 = bl ? (this.mPrivateFlags3 |= 16384) : (this.mPrivateFlags3 &= -16385);
    }

    public void setAutofillHints(String ... arrstring) {
        this.mAutofillHints = arrstring != null && arrstring.length != 0 ? arrstring : null;
    }

    public void setAutofillId(AutofillId autofillId) {
        if (Log.isLoggable(AUTOFILL_LOG_TAG, 2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("setAutofill(): from ");
            stringBuilder.append(this.mAutofillId);
            stringBuilder.append(" to ");
            stringBuilder.append(autofillId);
            Log.v(AUTOFILL_LOG_TAG, stringBuilder.toString());
        }
        if (!this.isAttachedToWindow()) {
            if (autofillId != null && !autofillId.isNonVirtual()) {
                throw new IllegalStateException("Cannot set autofill id assigned to virtual views");
            }
            if (autofillId == null && (this.mPrivateFlags3 & 1073741824) == 0) {
                return;
            }
            this.mAutofillId = autofillId;
            if (autofillId != null) {
                this.mAutofillViewId = autofillId.getViewId();
                this.mPrivateFlags3 = 1073741824 | this.mPrivateFlags3;
            } else {
                this.mAutofillViewId = -1;
                this.mPrivateFlags3 &= -1073741825;
            }
            return;
        }
        throw new IllegalStateException("Cannot set autofill id when view is attached");
    }

    public void setAutofilled(boolean bl) {
        boolean bl2 = bl != this.isAutofilled();
        if (bl2) {
            this.mPrivateFlags3 = bl ? (this.mPrivateFlags3 |= 65536) : (this.mPrivateFlags3 &= -65537);
            this.invalidate();
        }
    }

    public void setBackground(Drawable drawable2) {
        this.setBackgroundDrawable(drawable2);
    }

    void setBackgroundBounds() {
        Drawable drawable2;
        if (this.mBackgroundSizeChanged && (drawable2 = this.mBackground) != null) {
            drawable2.setBounds(0, 0, this.mRight - this.mLeft, this.mBottom - this.mTop);
            this.mBackgroundSizeChanged = false;
            this.rebuildOutline();
        }
    }

    @RemotableViewMethod
    public void setBackgroundColor(int n) {
        Drawable drawable2 = this.mBackground;
        if (drawable2 instanceof ColorDrawable) {
            ((ColorDrawable)drawable2.mutate()).setColor(n);
            this.computeOpaqueFlags();
            this.mBackgroundResource = 0;
        } else {
            this.setBackground(new ColorDrawable(n));
        }
    }

    @Deprecated
    public void setBackgroundDrawable(Drawable object) {
        this.computeOpaqueFlags();
        Object object2 = this.mBackground;
        if (object == object2) {
            return;
        }
        boolean bl = false;
        this.mBackgroundResource = 0;
        if (object2 != null) {
            if (this.isAttachedToWindow()) {
                this.mBackground.setVisible(false, false);
            }
            this.mBackground.setCallback(null);
            this.unscheduleDrawable(this.mBackground);
        }
        if (object != null) {
            Rect rect = sThreadLocal.get();
            object2 = rect;
            if (rect == null) {
                object2 = new Rect();
                sThreadLocal.set((Rect)object2);
            }
            this.resetResolvedDrawablesInternal();
            ((Drawable)object).setLayoutDirection(this.getLayoutDirection());
            if (((Drawable)object).getPadding((Rect)object2)) {
                this.resetResolvedPaddingInternal();
                if (((Drawable)object).getLayoutDirection() != 1) {
                    this.mUserPaddingLeftInitial = ((Rect)object2).left;
                    this.mUserPaddingRightInitial = ((Rect)object2).right;
                    this.internalSetPadding(((Rect)object2).left, ((Rect)object2).top, ((Rect)object2).right, ((Rect)object2).bottom);
                } else {
                    this.mUserPaddingLeftInitial = ((Rect)object2).right;
                    this.mUserPaddingRightInitial = ((Rect)object2).left;
                    this.internalSetPadding(((Rect)object2).right, ((Rect)object2).top, ((Rect)object2).left, ((Rect)object2).bottom);
                }
                this.mLeftPaddingDefined = false;
                this.mRightPaddingDefined = false;
            }
            if ((object2 = this.mBackground) == null || ((Drawable)object2).getMinimumHeight() != ((Drawable)object).getMinimumHeight() || this.mBackground.getMinimumWidth() != ((Drawable)object).getMinimumWidth()) {
                bl = true;
            }
            this.mBackground = object;
            if (((Drawable)object).isStateful()) {
                ((Drawable)object).setState(this.getDrawableState());
            }
            if (this.isAttachedToWindow()) {
                boolean bl2 = this.getWindowVisibility() == 0 && this.isShown();
                ((Drawable)object).setVisible(bl2, false);
            }
            this.applyBackgroundTint();
            ((Drawable)object).setCallback(this);
            int n = this.mPrivateFlags;
            if ((n & 128) != 0) {
                this.mPrivateFlags = n & -129;
                bl = true;
            }
        } else {
            this.mBackground = null;
            if ((this.mViewFlags & 128) != 0 && this.mDefaultFocusHighlight == null && ((object = this.mForegroundInfo) == null || ((ForegroundInfo)object).mDrawable == null)) {
                this.mPrivateFlags |= 128;
            }
            bl = true;
        }
        this.computeOpaqueFlags();
        if (bl) {
            this.requestLayout();
        }
        this.mBackgroundSizeChanged = true;
        this.invalidate(true);
        this.invalidateOutline();
    }

    @RemotableViewMethod
    public void setBackgroundResource(int n) {
        if (n != 0 && n == this.mBackgroundResource) {
            return;
        }
        Drawable drawable2 = null;
        if (n != 0) {
            drawable2 = this.mContext.getDrawable(n);
        }
        this.setBackground(drawable2);
        this.mBackgroundResource = n;
    }

    public void setBackgroundTintBlendMode(BlendMode blendMode) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        TintInfo tintInfo = this.mBackgroundTint;
        tintInfo.mBlendMode = blendMode;
        tintInfo.mHasTintMode = true;
        this.applyBackgroundTint();
    }

    public void setBackgroundTintList(ColorStateList colorStateList) {
        if (this.mBackgroundTint == null) {
            this.mBackgroundTint = new TintInfo();
        }
        TintInfo tintInfo = this.mBackgroundTint;
        tintInfo.mTintList = colorStateList;
        tintInfo.mHasTintList = true;
        this.applyBackgroundTint();
    }

    public void setBackgroundTintMode(PorterDuff.Mode mode) {
        BlendMode blendMode = null;
        if (mode != null) {
            blendMode = BlendMode.fromValue(mode.nativeInt);
        }
        this.setBackgroundTintBlendMode(blendMode);
    }

    public final void setBottom(int n) {
        if (n != this.mBottom) {
            int n2;
            boolean bl = this.hasIdentityMatrix();
            if (bl) {
                if (this.mAttachInfo != null) {
                    n2 = n < this.mBottom ? this.mBottom : n;
                    this.invalidate(0, 0, this.mRight - this.mLeft, n2 - this.mTop);
                }
            } else {
                this.invalidate(true);
            }
            int n3 = this.mRight - this.mLeft;
            n2 = this.mBottom;
            int n4 = this.mTop;
            this.mBottom = n;
            this.mRenderNode.setBottom(this.mBottom);
            this.sizeChange(n3, this.mBottom - this.mTop, n3, n2 - n4);
            if (!bl) {
                this.mPrivateFlags |= 32;
                this.invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
            }
            this.invalidateParentIfNeeded();
            if ((this.mPrivateFlags2 & 268435456) == 268435456) {
                this.invalidateParentIfNeeded();
            }
        }
    }

    public void setCameraDistance(float f) {
        float f2 = this.mResources.getDisplayMetrics().densityDpi;
        this.invalidateViewProperty(true, false);
        this.mRenderNode.setCameraDistance(Math.abs(f) / f2);
        this.invalidateViewProperty(false, false);
        this.invalidateParentIfNeededAndWasQuickRejected();
    }

    public void setClickable(boolean bl) {
        int n = bl ? 16384 : 0;
        this.setFlags(n, 16384);
    }

    public void setClipBounds(Rect rect) {
        Rect rect2 = this.mClipBounds;
        if (!(rect == rect2 || rect != null && rect.equals(rect2))) {
            if (rect != null) {
                rect2 = this.mClipBounds;
                if (rect2 == null) {
                    this.mClipBounds = new Rect(rect);
                } else {
                    rect2.set(rect);
                }
            } else {
                this.mClipBounds = null;
            }
            this.mRenderNode.setClipRect(this.mClipBounds);
            this.invalidateViewProperty(false, false);
            return;
        }
    }

    public void setClipToOutline(boolean bl) {
        this.damageInParent();
        if (this.getClipToOutline() != bl) {
            this.mRenderNode.setClipToOutline(bl);
        }
    }

    public void setContentCaptureSession(ContentCaptureSession contentCaptureSession) {
        this.mContentCaptureSession = contentCaptureSession;
    }

    @RemotableViewMethod
    public void setContentDescription(CharSequence charSequence) {
        CharSequence charSequence2 = this.mContentDescription;
        if (charSequence2 == null ? charSequence == null : charSequence2.equals(charSequence)) {
            return;
        }
        this.mContentDescription = charSequence;
        boolean bl = charSequence != null && charSequence.length() > 0;
        if (bl && this.getImportantForAccessibility() == 0) {
            this.setImportantForAccessibility(1);
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        } else {
            this.notifyViewAccessibilityStateChangedIfNeeded(4);
        }
    }

    public void setContextClickable(boolean bl) {
        int n = bl ? 8388608 : 0;
        this.setFlags(n, 8388608);
    }

    public void setDefaultFocusHighlightEnabled(boolean bl) {
        this.mDefaultFocusHighlightEnabled = bl;
    }

    @UnsupportedAppUsage
    public void setDisabledSystemUiVisibility(int n) {
        Object object = this.mAttachInfo;
        if (object != null && ((AttachInfo)object).mDisabledSystemUiVisibility != n) {
            this.mAttachInfo.mDisabledSystemUiVisibility = n;
            object = this.mParent;
            if (object != null) {
                object.recomputeViewAttributes(this);
            }
        }
    }

    void setDisplayListProperties(RenderNode renderNode) {
        if (renderNode != null) {
            renderNode.setHasOverlappingRendering(this.getHasOverlappingRendering());
            Object object = this.mParent;
            boolean bl = object instanceof ViewGroup && ((ViewGroup)object).getClipChildren();
            renderNode.setClipToBounds(bl);
            float f = 1.0f;
            object = this.mParent;
            float f2 = f;
            if (object instanceof ViewGroup) {
                f2 = f;
                if ((((ViewGroup)object).mGroupFlags & 2048) != 0) {
                    ViewGroup viewGroup = (ViewGroup)this.mParent;
                    object = viewGroup.getChildTransformation();
                    f2 = f;
                    if (viewGroup.getChildStaticTransformation(this, (Transformation)object)) {
                        int n = ((Transformation)object).getTransformationType();
                        f2 = f;
                        if (n != 0) {
                            if ((n & 1) != 0) {
                                f = ((Transformation)object).getAlpha();
                            }
                            f2 = f;
                            if ((n & 2) != 0) {
                                renderNode.setStaticMatrix(((Transformation)object).getMatrix());
                                f2 = f;
                            }
                        }
                    }
                }
            }
            if (this.mTransformationInfo != null) {
                f = f2 *= this.getFinalAlpha();
                if (f2 < 1.0f) {
                    f = f2;
                    if (this.onSetAlpha((int)(255.0f * f2))) {
                        f = 1.0f;
                    }
                }
                renderNode.setAlpha(f);
            } else if (f2 < 1.0f) {
                renderNode.setAlpha(f2);
            }
        }
    }

    @Deprecated
    public void setDrawingCacheBackgroundColor(int n) {
        if (n != this.mDrawingCacheBackgroundColor) {
            this.mDrawingCacheBackgroundColor = n;
            this.mPrivateFlags &= -32769;
        }
    }

    @Deprecated
    public void setDrawingCacheEnabled(boolean bl) {
        int n = 0;
        this.mCachingFailed = false;
        if (bl) {
            n = 32768;
        }
        this.setFlags(n, 32768);
    }

    @Deprecated
    public void setDrawingCacheQuality(int n) {
        this.setFlags(n, 1572864);
    }

    public void setDuplicateParentStateEnabled(boolean bl) {
        int n = bl ? 4194304 : 0;
        this.setFlags(n, 4194304);
    }

    public void setElevation(float f) {
        if (f != this.getElevation()) {
            f = View.sanitizeFloatPropertyValue(f, "elevation");
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setElevation(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    @RemotableViewMethod
    public void setEnabled(boolean bl) {
        if (bl == this.isEnabled()) {
            return;
        }
        int n = bl ? 0 : 32;
        this.setFlags(n, 32);
        this.refreshDrawableState();
        this.invalidate(true);
        if (!bl) {
            this.cancelPendingInputEvents();
        }
    }

    public void setFadingEdgeLength(int n) {
        this.initScrollCache();
        this.mScrollCache.fadingEdgeLength = n;
    }

    public void setFilterTouchesWhenObscured(boolean bl) {
        int n = bl ? 1024 : 0;
        this.setFlags(n, 1024);
    }

    public void setFitsSystemWindows(boolean bl) {
        int n = bl ? 2 : 0;
        this.setFlags(n, 2);
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    void setFlags(int n, int n2) {
        boolean bl;
        Object object;
        boolean bl2;
        boolean bl3;
        boolean bl4;
        block45 : {
            block47 : {
                int n3;
                int n4;
                int n5;
                block46 : {
                    bl3 = AccessibilityManager.getInstance(this.mContext).isEnabled();
                    bl = bl3 && this.includeForAccessibility();
                    this.mViewFlags = this.mViewFlags & n2 | n & n2;
                    int n6 = this.mViewFlags;
                    n5 = this.mViewFlags;
                    int n7 = n6 ^ n5;
                    if (n7 == 0) {
                        return;
                    }
                    n3 = this.mPrivateFlags;
                    bl2 = false;
                    int n8 = 0;
                    n2 = n7;
                    n4 = n8;
                    if ((n6 & 16) != 0) {
                        n2 = n7;
                        n4 = n8;
                        if ((n7 & 16401) != 0) {
                            n2 = (n6 & 16384) != 0 ? 1 : 0;
                            this.mViewFlags = this.mViewFlags & -2 | n2;
                            n4 = n5 & 1 ^ n2 & 1;
                            n2 = n7 & -2 | n4;
                        }
                    }
                    bl4 = bl2;
                    if ((n2 & 1) == 0) break block45;
                    bl4 = bl2;
                    if ((n3 & 16) == 0) break block45;
                    if ((n5 & 1) != 1 || (n3 & 2) == 0) break block46;
                    this.clearFocus();
                    object = this.mParent;
                    bl4 = bl2;
                    if (object instanceof ViewGroup) {
                        ((ViewGroup)object).clearFocusedInCluster();
                        bl4 = bl2;
                    }
                    break block45;
                }
                bl4 = bl2;
                if ((n5 & 1) != 0) break block45;
                bl4 = bl2;
                if ((n3 & 2) != 0) break block45;
                bl4 = bl2;
                if (this.mParent == null) break block45;
                object = this.getViewRootImpl();
                if (!sAutoFocusableOffUIThreadWontNotifyParents || n4 == 0 || object == null) break block47;
                bl4 = bl2;
                if (((ViewRootImpl)object).mThread != Thread.currentThread()) break block45;
            }
            bl4 = this.canTakeFocus();
        }
        bl2 = bl4;
        if ((n &= 12) == 0) {
            bl2 = bl4;
            if ((n2 & 12) != 0) {
                this.mPrivateFlags |= 32;
                this.invalidate(true);
                this.needGlobalAttributesUpdate(true);
                bl2 = this.hasSize();
            }
        }
        bl4 = bl2;
        if ((n2 & 32) != 0) {
            if ((this.mViewFlags & 32) == 0) {
                bl4 = this.canTakeFocus();
            } else {
                bl4 = bl2;
                if (this.isFocused()) {
                    this.clearFocus();
                    bl4 = bl2;
                }
            }
        }
        if (bl4 && (object = this.mParent) != null) {
            object.focusableViewAvailable(this);
        }
        if ((n2 & 8) != 0) {
            this.needGlobalAttributesUpdate(false);
            this.requestLayout();
            if ((this.mViewFlags & 12) == 8) {
                if (this.hasFocus()) {
                    this.clearFocus();
                    object = this.mParent;
                    if (object instanceof ViewGroup) {
                        ((ViewGroup)object).clearFocusedInCluster();
                    }
                }
                this.clearAccessibilityFocus();
                this.destroyDrawingCache();
                object = this.mParent;
                if (object instanceof View) {
                    ((View)object).invalidate(true);
                }
                this.mPrivateFlags |= 32;
            }
            if ((object = this.mAttachInfo) != null) {
                ((AttachInfo)object).mViewVisibilityChanged = true;
            }
        }
        if ((n2 & 4) != 0) {
            this.needGlobalAttributesUpdate(false);
            this.mPrivateFlags |= 32;
            if ((this.mViewFlags & 12) == 4 && this.getRootView() != this) {
                if (this.hasFocus()) {
                    this.clearFocus();
                    object = this.mParent;
                    if (object instanceof ViewGroup) {
                        ((ViewGroup)object).clearFocusedInCluster();
                    }
                }
                this.clearAccessibilityFocus();
            }
            if ((object = this.mAttachInfo) != null) {
                ((AttachInfo)object).mViewVisibilityChanged = true;
            }
        }
        if ((n2 & 12) != 0) {
            if (n != 0 && this.mAttachInfo != null) {
                this.cleanupDraw();
            }
            if ((object = this.mParent) instanceof ViewGroup) {
                object = (ViewGroup)object;
                ((ViewGroup)object).onChildVisibilityChanged(this, n2 & 12, n);
                ((View)object).invalidate(true);
            } else if (object != null) {
                object.invalidateChild(this, null);
            }
            if (this.mAttachInfo != null) {
                this.dispatchVisibilityChanged(this, n);
                if (this.mParent != null && this.getWindowVisibility() == 0 && (!((object = this.mParent) instanceof ViewGroup) || ((ViewGroup)object).isShown())) {
                    bl2 = n == 0;
                    this.dispatchVisibilityAggregated(bl2);
                }
                this.notifySubtreeAccessibilityStateChangedIfNeeded();
            }
        }
        if ((131072 & n2) != 0) {
            this.destroyDrawingCache();
        }
        if ((32768 & n2) != 0) {
            this.destroyDrawingCache();
            this.mPrivateFlags &= -32769;
            this.invalidateParentCaches();
        }
        if ((1572864 & n2) != 0) {
            this.destroyDrawingCache();
            this.mPrivateFlags &= -32769;
        }
        if ((n2 & 128) != 0) {
            this.mPrivateFlags = (this.mViewFlags & 128) != 0 ? (this.mBackground == null && this.mDefaultFocusHighlight == null && ((object = this.mForegroundInfo) == null || ((ForegroundInfo)object).mDrawable == null) ? (this.mPrivateFlags |= 128) : (this.mPrivateFlags &= -129)) : (this.mPrivateFlags &= -129);
            this.requestLayout();
            this.invalidate(true);
        }
        if ((67108864 & n2) != 0 && this.mParent != null && (object = this.mAttachInfo) != null && !((AttachInfo)object).mRecomputeGlobalAttributes) {
            this.mParent.recomputeViewAttributes(this);
        }
        if (bl3) {
            n = n2;
            if (this.isAccessibilityPane()) {
                n = n2 & -13;
            }
            if ((n & 1) == 0 && (n & 12) == 0 && (n & 16384) == 0 && (2097152 & n) == 0 && (8388608 & n) == 0) {
                if ((n & 32) != 0) {
                    this.notifyViewAccessibilityStateChangedIfNeeded(0);
                }
            } else if (bl != this.includeForAccessibility()) {
                this.notifySubtreeAccessibilityStateChangedIfNeeded();
            } else {
                this.notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    public void setFocusable(int n) {
        if ((n & 17) == 0) {
            this.setFlags(0, 262144);
        }
        this.setFlags(n, 17);
    }

    public void setFocusable(boolean bl) {
        this.setFocusable((int)bl);
    }

    public void setFocusableInTouchMode(boolean bl) {
        int n = bl ? 262144 : 0;
        this.setFlags(n, 262144);
        if (bl) {
            this.setFlags(1, 17);
        }
    }

    public void setFocusedByDefault(boolean bl) {
        boolean bl2 = (this.mPrivateFlags3 & 262144) != 0;
        if (bl == bl2) {
            return;
        }
        this.mPrivateFlags3 = bl ? (this.mPrivateFlags3 |= 262144) : (this.mPrivateFlags3 &= -262145);
        ViewParent viewParent = this.mParent;
        if (viewParent instanceof ViewGroup) {
            if (bl) {
                ((ViewGroup)viewParent).setDefaultFocus(this);
            } else {
                ((ViewGroup)viewParent).clearDefaultFocus(this);
            }
        }
    }

    public final void setFocusedInCluster() {
        this.setFocusedInCluster(this.findKeyboardNavigationCluster());
    }

    public void setForceDarkAllowed(boolean bl) {
        if (this.mRenderNode.setForceDarkAllowed(bl)) {
            this.invalidate();
        }
    }

    public void setForeground(Drawable drawable2) {
        if (this.mForegroundInfo == null) {
            if (drawable2 == null) {
                return;
            }
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (drawable2 == this.mForegroundInfo.mDrawable) {
            return;
        }
        if (this.mForegroundInfo.mDrawable != null) {
            if (this.isAttachedToWindow()) {
                this.mForegroundInfo.mDrawable.setVisible(false, false);
            }
            this.mForegroundInfo.mDrawable.setCallback(null);
            this.unscheduleDrawable(this.mForegroundInfo.mDrawable);
        }
        this.mForegroundInfo.mDrawable = drawable2;
        ForegroundInfo foregroundInfo = this.mForegroundInfo;
        boolean bl = true;
        foregroundInfo.mBoundsChanged = true;
        if (drawable2 != null) {
            int n = this.mPrivateFlags;
            if ((n & 128) != 0) {
                this.mPrivateFlags = n & -129;
            }
            drawable2.setLayoutDirection(this.getLayoutDirection());
            if (drawable2.isStateful()) {
                drawable2.setState(this.getDrawableState());
            }
            this.applyForegroundTint();
            if (this.isAttachedToWindow()) {
                if (this.getWindowVisibility() != 0 || !this.isShown()) {
                    bl = false;
                }
                drawable2.setVisible(bl, false);
            }
            drawable2.setCallback(this);
        } else if ((this.mViewFlags & 128) != 0 && this.mBackground == null && this.mDefaultFocusHighlight == null) {
            this.mPrivateFlags |= 128;
        }
        this.requestLayout();
        this.invalidate();
    }

    public void setForegroundGravity(int n) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mGravity != n) {
            int n2 = n;
            if ((8388615 & n) == 0) {
                n2 = n | 8388611;
            }
            n = n2;
            if ((n2 & 112) == 0) {
                n = n2 | 48;
            }
            this.mForegroundInfo.mGravity = n;
            this.requestLayout();
        }
    }

    public void setForegroundTintBlendMode(BlendMode blendMode) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            this.mForegroundInfo.mTintInfo = new TintInfo();
        }
        ForegroundInfo.access$2600((ForegroundInfo)this.mForegroundInfo).mBlendMode = blendMode;
        ForegroundInfo.access$2600((ForegroundInfo)this.mForegroundInfo).mHasTintMode = true;
        this.applyForegroundTint();
    }

    public void setForegroundTintList(ColorStateList colorStateList) {
        if (this.mForegroundInfo == null) {
            this.mForegroundInfo = new ForegroundInfo();
        }
        if (this.mForegroundInfo.mTintInfo == null) {
            this.mForegroundInfo.mTintInfo = new TintInfo();
        }
        ForegroundInfo.access$2600((ForegroundInfo)this.mForegroundInfo).mTintList = colorStateList;
        ForegroundInfo.access$2600((ForegroundInfo)this.mForegroundInfo).mHasTintList = true;
        this.applyForegroundTint();
    }

    public void setForegroundTintMode(PorterDuff.Mode mode) {
        BlendMode blendMode = null;
        if (mode != null) {
            blendMode = BlendMode.fromValue(mode.nativeInt);
        }
        this.setForegroundTintBlendMode(blendMode);
    }

    @UnsupportedAppUsage(maxTargetSdk=28)
    protected boolean setFrame(int n, int n2, int n3, int n4) {
        boolean bl = false;
        if (this.mLeft != n || this.mRight != n3 || this.mTop != n2 || this.mBottom != n4) {
            boolean bl2 = true;
            int n5 = this.mPrivateFlags;
            int n6 = this.mRight - this.mLeft;
            int n7 = this.mBottom - this.mTop;
            int n8 = n3 - n;
            int n9 = n4 - n2;
            bl = n8 != n6 || n9 != n7;
            this.invalidate(bl);
            this.mLeft = n;
            this.mTop = n2;
            this.mRight = n3;
            this.mBottom = n4;
            this.mRenderNode.setLeftTopRightBottom(this.mLeft, this.mTop, this.mRight, this.mBottom);
            this.mPrivateFlags |= 16;
            if (bl) {
                this.sizeChange(n8, n9, n6, n7);
            }
            if ((this.mViewFlags & 12) == 0 || this.mGhostView != null) {
                this.mPrivateFlags |= 32;
                this.invalidate(bl);
                this.invalidateParentCaches();
            }
            this.mPrivateFlags |= n5 & 32;
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
            }
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
            bl = bl2;
        }
        return bl;
    }

    public void setHapticFeedbackEnabled(boolean bl) {
        int n = bl ? 268435456 : 0;
        this.setFlags(n, 268435456);
    }

    public void setHasTransientState(boolean bl) {
        boolean bl2 = this.hasTransientState();
        int n = bl ? this.mTransientStateCount + 1 : this.mTransientStateCount - 1;
        int n2 = this.mTransientStateCount = n;
        n = 0;
        if (n2 < 0) {
            this.mTransientStateCount = 0;
            Log.e(VIEW_LOG_TAG, "hasTransientState decremented below 0: unmatched pair of setHasTransientState calls");
        } else if (bl && n2 == 1 || !bl && this.mTransientStateCount == 0) {
            n2 = this.mPrivateFlags2;
            if (bl) {
                n = Integer.MIN_VALUE;
            }
            this.mPrivateFlags2 = n2 & Integer.MAX_VALUE | n;
            bl = this.hasTransientState();
            ViewParent viewParent = this.mParent;
            if (viewParent != null && bl != bl2) {
                try {
                    viewParent.childHasTransientStateChanged(this, bl);
                }
                catch (AbstractMethodError abstractMethodError) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(this.mParent.getClass().getSimpleName());
                    stringBuilder.append(" does not fully implement ViewParent");
                    Log.e(VIEW_LOG_TAG, stringBuilder.toString(), abstractMethodError);
                }
            }
        }
    }

    public void setHorizontalFadingEdgeEnabled(boolean bl) {
        if (this.isHorizontalFadingEdgeEnabled() != bl) {
            if (bl) {
                this.initScrollCache();
            }
            this.mViewFlags ^= 4096;
        }
    }

    public void setHorizontalScrollBarEnabled(boolean bl) {
        if (this.isHorizontalScrollBarEnabled() != bl) {
            this.mViewFlags ^= 256;
            this.computeOpaqueFlags();
            this.resolvePadding();
        }
    }

    public void setHorizontalScrollbarThumbDrawable(Drawable drawable2) {
        this.initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setHorizontalThumbDrawable(drawable2);
    }

    public void setHorizontalScrollbarTrackDrawable(Drawable drawable2) {
        this.initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setHorizontalTrackDrawable(drawable2);
    }

    public void setHovered(boolean bl) {
        if (bl) {
            int n = this.mPrivateFlags;
            if ((n & 268435456) == 0) {
                this.mPrivateFlags = 268435456 | n;
                this.refreshDrawableState();
                this.onHoverChanged(true);
            }
        } else {
            int n = this.mPrivateFlags;
            if ((268435456 & n) != 0) {
                this.mPrivateFlags = -268435457 & n;
                this.refreshDrawableState();
                this.onHoverChanged(false);
            }
        }
    }

    public void setId(int n) {
        this.mID = n;
        if (this.mID == -1 && this.mLabelForId != -1) {
            this.mID = View.generateViewId();
        }
    }

    public void setImportantForAccessibility(int n) {
        int n2 = this.getImportantForAccessibility();
        if (n != n2) {
            View view;
            boolean bl = true;
            boolean bl2 = n == 4;
            if ((n == 2 || bl2) && (view = this.findAccessibilityFocusHost(bl2)) != null) {
                view.clearAccessibilityFocus();
            }
            n2 = n2 != 0 && n != 0 ? 0 : 1;
            bl2 = n2 != 0 && this.includeForAccessibility() ? bl : false;
            this.mPrivateFlags2 &= -7340033;
            this.mPrivateFlags2 |= n << 20 & 7340032;
            if (n2 != 0 && bl2 == this.includeForAccessibility()) {
                this.notifyViewAccessibilityStateChangedIfNeeded(0);
            } else {
                this.notifySubtreeAccessibilityStateChangedIfNeeded();
            }
        }
    }

    public void setImportantForAutofill(int n) {
        this.mPrivateFlags3 &= -7864321;
        this.mPrivateFlags3 |= n << 19 & 7864320;
    }

    public void setIsRootNamespace(boolean bl) {
        this.mPrivateFlags = bl ? (this.mPrivateFlags |= 8) : (this.mPrivateFlags &= -9);
    }

    public void setKeepScreenOn(boolean bl) {
        int n = bl ? 67108864 : 0;
        this.setFlags(n, 67108864);
    }

    public void setKeyboardNavigationCluster(boolean bl) {
        this.mPrivateFlags3 = bl ? (this.mPrivateFlags3 |= 32768) : (this.mPrivateFlags3 &= -32769);
    }

    @RemotableViewMethod
    public void setLabelFor(int n) {
        if (this.mLabelForId == n) {
            return;
        }
        this.mLabelForId = n;
        if (this.mLabelForId != -1 && this.mID == -1) {
            this.mID = View.generateViewId();
        }
        this.notifyViewAccessibilityStateChangedIfNeeded(0);
    }

    public void setLayerPaint(Paint paint) {
        int n = this.getLayerType();
        if (n != 0) {
            this.mLayerPaint = paint;
            if (n == 2) {
                if (this.mRenderNode.setLayerPaint(paint)) {
                    this.invalidateViewProperty(false, false);
                }
            } else {
                this.invalidate();
            }
        }
    }

    public void setLayerType(int n, Paint paint) {
        if (n >= 0 && n <= 2) {
            if (!this.mRenderNode.setLayerType(n)) {
                this.setLayerPaint(paint);
                return;
            }
            if (n != 1) {
                this.destroyDrawingCache();
            }
            this.mLayerType = n;
            if (this.mLayerType == 0) {
                paint = null;
            }
            this.mLayerPaint = paint;
            this.mRenderNode.setLayerPaint(this.mLayerPaint);
            this.invalidateParentCaches();
            this.invalidate(true);
            return;
        }
        throw new IllegalArgumentException("Layer type can only be one of: LAYER_TYPE_NONE, LAYER_TYPE_SOFTWARE or LAYER_TYPE_HARDWARE");
    }

    @RemotableViewMethod
    public void setLayoutDirection(int n) {
        if (this.getRawLayoutDirection() != n) {
            this.mPrivateFlags2 &= -13;
            this.resetRtlProperties();
            this.mPrivateFlags2 |= n << 2 & 12;
            this.resolveRtlPropertiesIfNeeded();
            this.requestLayout();
            this.invalidate(true);
        }
    }

    public void setLayoutParams(ViewGroup.LayoutParams layoutParams) {
        if (layoutParams != null) {
            this.mLayoutParams = layoutParams;
            this.resolveLayoutParams();
            ViewParent viewParent = this.mParent;
            if (viewParent instanceof ViewGroup) {
                ((ViewGroup)viewParent).onSetLayoutParams(this, layoutParams);
            }
            this.requestLayout();
            return;
        }
        throw new NullPointerException("Layout parameters cannot be null");
    }

    public final void setLeft(int n) {
        if (n != this.mLeft) {
            int n2;
            int n3;
            boolean bl = this.hasIdentityMatrix();
            if (bl) {
                if (this.mAttachInfo != null) {
                    n2 = this.mLeft;
                    if (n < n2) {
                        n3 = n;
                        n2 = n - n2;
                    } else {
                        n3 = this.mLeft;
                        n2 = 0;
                    }
                    this.invalidate(n2, 0, this.mRight - n3, this.mBottom - this.mTop);
                }
            } else {
                this.invalidate(true);
            }
            n2 = this.mRight;
            n3 = this.mLeft;
            int n4 = this.mBottom - this.mTop;
            this.mLeft = n;
            this.mRenderNode.setLeft(n);
            this.sizeChange(this.mRight - this.mLeft, n4, n2 - n3, n4);
            if (!bl) {
                this.mPrivateFlags |= 32;
                this.invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
            }
            this.invalidateParentIfNeeded();
            if ((this.mPrivateFlags2 & 268435456) == 268435456) {
                this.invalidateParentIfNeeded();
            }
        }
    }

    public final void setLeftTopRightBottom(int n, int n2, int n3, int n4) {
        this.setFrame(n, n2, n3, n4);
    }

    public void setLongClickable(boolean bl) {
        int n = bl ? 2097152 : 0;
        this.setFlags(n, 2097152);
    }

    protected final void setMeasuredDimension(int n, int n2) {
        boolean bl = View.isLayoutModeOptical(this);
        int n3 = n;
        int n4 = n2;
        if (bl != View.isLayoutModeOptical(this.mParent)) {
            Insets insets = this.getOpticalInsets();
            n4 = insets.left + insets.right;
            int n5 = insets.top + insets.bottom;
            if (!bl) {
                n4 = -n4;
            }
            n3 = n + n4;
            n = bl ? n5 : -n5;
            n4 = n2 + n;
        }
        this.setMeasuredDimensionRaw(n3, n4);
    }

    @RemotableViewMethod
    public void setMinimumHeight(int n) {
        this.mMinHeight = n;
        this.requestLayout();
    }

    public void setMinimumWidth(int n) {
        this.mMinWidth = n;
        this.requestLayout();
    }

    public void setNestedScrollingEnabled(boolean bl) {
        if (bl) {
            this.mPrivateFlags3 |= 128;
        } else {
            this.stopNestedScroll();
            this.mPrivateFlags3 &= -129;
        }
    }

    public void setNextClusterForwardId(int n) {
        this.mNextClusterForwardId = n;
    }

    public void setNextFocusDownId(int n) {
        this.mNextFocusDownId = n;
    }

    public void setNextFocusForwardId(int n) {
        this.mNextFocusForwardId = n;
    }

    public void setNextFocusLeftId(int n) {
        this.mNextFocusLeftId = n;
    }

    public void setNextFocusRightId(int n) {
        this.mNextFocusRightId = n;
    }

    public void setNextFocusUpId(int n) {
        this.mNextFocusUpId = n;
    }

    public void setNotifyAutofillManagerOnClick(boolean bl) {
        this.mPrivateFlags = bl ? (this.mPrivateFlags |= 536870912) : (this.mPrivateFlags &= -536870913);
    }

    public void setOnApplyWindowInsetsListener(OnApplyWindowInsetsListener onApplyWindowInsetsListener) {
        this.getListenerInfo().mOnApplyWindowInsetsListener = onApplyWindowInsetsListener;
    }

    public void setOnCapturedPointerListener(OnCapturedPointerListener onCapturedPointerListener) {
        this.getListenerInfo().mOnCapturedPointerListener = onCapturedPointerListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        if (!this.isClickable()) {
            this.setClickable(true);
        }
        this.getListenerInfo().mOnClickListener = onClickListener;
    }

    public void setOnContextClickListener(OnContextClickListener onContextClickListener) {
        if (!this.isContextClickable()) {
            this.setContextClickable(true);
        }
        this.getListenerInfo().mOnContextClickListener = onContextClickListener;
    }

    public void setOnCreateContextMenuListener(OnCreateContextMenuListener onCreateContextMenuListener) {
        if (!this.isLongClickable()) {
            this.setLongClickable(true);
        }
        this.getListenerInfo().mOnCreateContextMenuListener = onCreateContextMenuListener;
    }

    public void setOnDragListener(OnDragListener onDragListener) {
        this.getListenerInfo().mOnDragListener = onDragListener;
    }

    public void setOnFocusChangeListener(OnFocusChangeListener onFocusChangeListener) {
        this.getListenerInfo().mOnFocusChangeListener = onFocusChangeListener;
    }

    public void setOnGenericMotionListener(OnGenericMotionListener onGenericMotionListener) {
        this.getListenerInfo().mOnGenericMotionListener = onGenericMotionListener;
    }

    public void setOnHoverListener(OnHoverListener onHoverListener) {
        this.getListenerInfo().mOnHoverListener = onHoverListener;
    }

    public void setOnKeyListener(OnKeyListener onKeyListener) {
        this.getListenerInfo().mOnKeyListener = onKeyListener;
    }

    public void setOnLongClickListener(OnLongClickListener onLongClickListener) {
        if (!this.isLongClickable()) {
            this.setLongClickable(true);
        }
        this.getListenerInfo().mOnLongClickListener = onLongClickListener;
    }

    public void setOnScrollChangeListener(OnScrollChangeListener onScrollChangeListener) {
        this.getListenerInfo().mOnScrollChangeListener = onScrollChangeListener;
    }

    public void setOnSystemUiVisibilityChangeListener(OnSystemUiVisibilityChangeListener object) {
        this.getListenerInfo().mOnSystemUiVisibilityChangeListener = (OnSystemUiVisibilityChangeListener)object;
        if (this.mParent != null && (object = this.mAttachInfo) != null && !((AttachInfo)object).mRecomputeGlobalAttributes) {
            this.mParent.recomputeViewAttributes(this);
        }
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.getListenerInfo().mOnTouchListener = onTouchListener;
    }

    public void setOpticalInsets(Insets insets) {
        this.mLayoutInsets = insets;
    }

    public void setOutlineAmbientShadowColor(int n) {
        if (this.mRenderNode.setAmbientShadowColor(n)) {
            this.invalidateViewProperty(true, true);
        }
    }

    public void setOutlineProvider(ViewOutlineProvider viewOutlineProvider) {
        this.mOutlineProvider = viewOutlineProvider;
        this.invalidateOutline();
    }

    public void setOutlineSpotShadowColor(int n) {
        if (this.mRenderNode.setSpotShadowColor(n)) {
            this.invalidateViewProperty(true, true);
        }
    }

    public void setOverScrollMode(int n) {
        if (n != 0 && n != 1 && n != 2) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Invalid overscroll mode ");
            stringBuilder.append(n);
            throw new IllegalArgumentException(stringBuilder.toString());
        }
        this.mOverScrollMode = n;
    }

    public void setPadding(int n, int n2, int n3, int n4) {
        this.resetResolvedPaddingInternal();
        this.mUserPaddingStart = Integer.MIN_VALUE;
        this.mUserPaddingEnd = Integer.MIN_VALUE;
        this.mUserPaddingLeftInitial = n;
        this.mUserPaddingRightInitial = n3;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        this.internalSetPadding(n, n2, n3, n4);
    }

    public void setPaddingRelative(int n, int n2, int n3, int n4) {
        this.resetResolvedPaddingInternal();
        this.mUserPaddingStart = n;
        this.mUserPaddingEnd = n3;
        this.mLeftPaddingDefined = true;
        this.mRightPaddingDefined = true;
        if (this.getLayoutDirection() != 1) {
            this.mUserPaddingLeftInitial = n;
            this.mUserPaddingRightInitial = n3;
            this.internalSetPadding(n, n2, n3, n4);
        } else {
            this.mUserPaddingLeftInitial = n3;
            this.mUserPaddingRightInitial = n;
            this.internalSetPadding(n3, n2, n, n4);
        }
    }

    public void setPivotX(float f) {
        if (!this.mRenderNode.isPivotExplicitlySet() || f != this.getPivotX()) {
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setPivotX(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public void setPivotY(float f) {
        if (!this.mRenderNode.isPivotExplicitlySet() || f != this.getPivotY()) {
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setPivotY(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public void setPointerIcon(PointerIcon object) {
        this.mPointerIcon = object;
        object = this.mAttachInfo;
        if (object != null && !((AttachInfo)object).mHandlingPointerEvent) {
            try {
                this.mAttachInfo.mSession.updatePointerIcon(this.mAttachInfo.mWindow);
            }
            catch (RemoteException remoteException) {
                // empty catch block
            }
            return;
        }
    }

    public void setPressed(boolean bl) {
        int n = this.mPrivateFlags;
        boolean bl2 = true;
        boolean bl3 = (n & 16384) == 16384;
        if (bl == bl3) {
            bl2 = false;
        }
        this.mPrivateFlags = bl ? 16384 | this.mPrivateFlags : (this.mPrivateFlags &= -16385);
        if (bl2) {
            this.refreshDrawableState();
        }
        this.dispatchSetPressed(bl);
    }

    public void setRevealClip(boolean bl, float f, float f2, float f3) {
        this.mRenderNode.setRevealClip(bl, f, f2, f3);
        this.invalidateViewProperty(false, false);
    }

    public final void setRevealOnFocusHint(boolean bl) {
        this.mPrivateFlags3 = bl ? (this.mPrivateFlags3 &= -67108865) : (this.mPrivateFlags3 |= 67108864);
    }

    public final void setRight(int n) {
        if (n != this.mRight) {
            int n2;
            boolean bl = this.hasIdentityMatrix();
            if (bl) {
                if (this.mAttachInfo != null) {
                    n2 = n < this.mRight ? this.mRight : n;
                    this.invalidate(0, 0, n2 - this.mLeft, this.mBottom - this.mTop);
                }
            } else {
                this.invalidate(true);
            }
            int n3 = this.mRight;
            n2 = this.mLeft;
            int n4 = this.mBottom - this.mTop;
            this.mRight = n;
            this.mRenderNode.setRight(this.mRight);
            this.sizeChange(this.mRight - this.mLeft, n4, n3 - n2, n4);
            if (!bl) {
                this.mPrivateFlags |= 32;
                this.invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
            }
            this.invalidateParentIfNeeded();
            if ((this.mPrivateFlags2 & 268435456) == 268435456) {
                this.invalidateParentIfNeeded();
            }
        }
    }

    public void setRotation(float f) {
        if (f != this.getRotation()) {
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setRotationZ(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setRotationX(float f) {
        if (f != this.getRotationX()) {
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setRotationX(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setRotationY(float f) {
        if (f != this.getRotationY()) {
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setRotationY(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setSaveEnabled(boolean bl) {
        int n = bl ? 0 : 65536;
        this.setFlags(n, 65536);
    }

    public void setSaveFromParentEnabled(boolean bl) {
        int n = bl ? 0 : 536870912;
        this.setFlags(n, 536870912);
    }

    public void setScaleX(float f) {
        if (f != this.getScaleX()) {
            f = View.sanitizeFloatPropertyValue(f, "scaleX");
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setScaleX(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setScaleY(float f) {
        if (f != this.getScaleY()) {
            f = View.sanitizeFloatPropertyValue(f, "scaleY");
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setScaleY(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setScreenReaderFocusable(boolean bl) {
        this.updatePflags3AndNotifyA11yIfChanged(268435456, bl);
    }

    public void setScrollBarDefaultDelayBeforeFade(int n) {
        this.getScrollCache().scrollBarDefaultDelayBeforeFade = n;
    }

    public void setScrollBarFadeDuration(int n) {
        this.getScrollCache().scrollBarFadeDuration = n;
    }

    public void setScrollBarSize(int n) {
        this.getScrollCache().scrollBarSize = n;
    }

    public void setScrollBarStyle(int n) {
        int n2 = this.mViewFlags;
        if (n != (n2 & 50331648)) {
            this.mViewFlags = n2 & -50331649 | 50331648 & n;
            this.computeOpaqueFlags();
            this.resolvePadding();
        }
    }

    public void setScrollContainer(boolean bl) {
        if (bl) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null && (this.mPrivateFlags & 1048576) == 0) {
                attachInfo.mScrollContainers.add(this);
                this.mPrivateFlags = 1048576 | this.mPrivateFlags;
            }
            this.mPrivateFlags |= 524288;
        } else {
            if ((1048576 & this.mPrivateFlags) != 0) {
                this.mAttachInfo.mScrollContainers.remove(this);
            }
            this.mPrivateFlags &= -1572865;
        }
    }

    public void setScrollIndicators(int n) {
        this.setScrollIndicators(n, 63);
    }

    public void setScrollIndicators(int n, int n2) {
        n2 = n2 << 8 & 16128;
        n = n << 8 & n2;
        int n3 = this.mPrivateFlags3;
        if (n3 != (n2 = n2 & n3 | n)) {
            this.mPrivateFlags3 = n2;
            if (n != 0) {
                this.initializeScrollIndicatorsInternal();
            }
            this.invalidate();
        }
    }

    public void setScrollX(int n) {
        this.scrollTo(n, this.mScrollY);
    }

    public void setScrollY(int n) {
        this.scrollTo(this.mScrollX, n);
    }

    public void setScrollbarFadingEnabled(boolean bl) {
        this.initScrollCache();
        ScrollabilityCache scrollabilityCache = this.mScrollCache;
        scrollabilityCache.fadeScrollBars = bl;
        scrollabilityCache.state = bl ? 0 : 1;
    }

    public void setSelected(boolean bl) {
        boolean bl2 = (this.mPrivateFlags & 4) != 0;
        if (bl2 != bl) {
            int n = this.mPrivateFlags;
            int n2 = bl ? 4 : 0;
            this.mPrivateFlags = n & -5 | n2;
            if (!bl) {
                this.resetPressedState();
            }
            this.invalidate(true);
            this.refreshDrawableState();
            this.dispatchSetSelected(bl);
            if (bl) {
                this.sendAccessibilityEvent(4);
            } else {
                this.notifyViewAccessibilityStateChangedIfNeeded(0);
            }
        }
    }

    public void setSoundEffectsEnabled(boolean bl) {
        int n = bl ? 134217728 : 0;
        this.setFlags(n, 134217728);
    }

    public void setStateListAnimator(StateListAnimator stateListAnimator) {
        StateListAnimator stateListAnimator2 = this.mStateListAnimator;
        if (stateListAnimator2 == stateListAnimator) {
            return;
        }
        if (stateListAnimator2 != null) {
            stateListAnimator2.setTarget(null);
        }
        this.mStateListAnimator = stateListAnimator;
        if (stateListAnimator != null) {
            stateListAnimator.setTarget(this);
            if (this.isAttachedToWindow()) {
                stateListAnimator.setState(this.getDrawableState());
            }
        }
    }

    public void setSystemGestureExclusionRects(List<Rect> list) {
        if (list.isEmpty() && this.mListenerInfo == null) {
            return;
        }
        ListenerInfo listenerInfo = this.getListenerInfo();
        if (list.isEmpty()) {
            listenerInfo.mSystemGestureExclusionRects = null;
            if (listenerInfo.mPositionUpdateListener != null) {
                this.mRenderNode.removePositionUpdateListener(listenerInfo.mPositionUpdateListener);
            }
        } else {
            listenerInfo.mSystemGestureExclusionRects = list;
            if (listenerInfo.mPositionUpdateListener == null) {
                listenerInfo.mPositionUpdateListener = new RenderNode.PositionUpdateListener(){

                    @Override
                    public void positionChanged(long l, int n, int n2, int n3, int n4) {
                        View.this.postUpdateSystemGestureExclusionRects();
                    }

                    @Override
                    public void positionLost(long l) {
                        View.this.postUpdateSystemGestureExclusionRects();
                    }
                };
                this.mRenderNode.addPositionUpdateListener(listenerInfo.mPositionUpdateListener);
            }
        }
        this.postUpdateSystemGestureExclusionRects();
    }

    public void setSystemUiVisibility(int n) {
        if (n != this.mSystemUiVisibility) {
            AttachInfo attachInfo;
            this.mSystemUiVisibility = n;
            if (this.mParent != null && (attachInfo = this.mAttachInfo) != null && !attachInfo.mRecomputeGlobalAttributes) {
                this.mParent.recomputeViewAttributes(this);
            }
        }
    }

    public void setTag(int n, Object object) {
        if (n >>> 24 >= 2) {
            this.setKeyedTag(n, object);
            return;
        }
        throw new IllegalArgumentException("The key must be an application-specific resource id.");
    }

    public void setTag(Object object) {
        this.mTag = object;
    }

    @UnsupportedAppUsage
    public void setTagInternal(int n, Object object) {
        if (n >>> 24 == 1) {
            this.setKeyedTag(n, object);
            return;
        }
        throw new IllegalArgumentException("The key must be a framework-specific resource id.");
    }

    public void setTextAlignment(int n) {
        if (n != this.getRawTextAlignment()) {
            this.mPrivateFlags2 &= -57345;
            this.resetResolvedTextAlignment();
            this.mPrivateFlags2 |= n << 13 & 57344;
            this.resolveTextAlignment();
            this.onRtlPropertiesChanged(this.getLayoutDirection());
            this.requestLayout();
            this.invalidate(true);
        }
    }

    public void setTextDirection(int n) {
        if (this.getRawTextDirection() != n) {
            this.mPrivateFlags2 &= -449;
            this.resetResolvedTextDirection();
            this.mPrivateFlags2 |= n << 6 & 448;
            this.resolveTextDirection();
            this.onRtlPropertiesChanged(this.getLayoutDirection());
            this.requestLayout();
            this.invalidate(true);
        }
    }

    @UnsupportedAppUsage
    public void setTooltip(CharSequence charSequence) {
        this.setTooltipText(charSequence);
    }

    public void setTooltipText(CharSequence charSequence) {
        if (TextUtils.isEmpty(charSequence)) {
            this.setFlags(0, 1073741824);
            this.hideTooltip();
            this.mTooltipInfo = null;
        } else {
            this.setFlags(1073741824, 1073741824);
            if (this.mTooltipInfo == null) {
                TooltipInfo tooltipInfo = this.mTooltipInfo = new TooltipInfo();
                tooltipInfo.mShowTooltipRunnable = new _$$Lambda$View$llq76MkPXP4bNcb9oJt_msw0fnQ(this);
                tooltipInfo.mHideTooltipRunnable = new _$$Lambda$QI1s392qW8l6mC24bcy9050SkuY(this);
                tooltipInfo.mHoverSlop = ViewConfiguration.get(this.mContext).getScaledHoverSlop();
                this.mTooltipInfo.clearAnchorPos();
            }
            this.mTooltipInfo.mTooltipText = charSequence;
        }
    }

    public final void setTop(int n) {
        if (n != this.mTop) {
            int n2;
            int n3;
            boolean bl = this.hasIdentityMatrix();
            if (bl) {
                if (this.mAttachInfo != null) {
                    n2 = this.mTop;
                    if (n < n2) {
                        n3 = n;
                        n2 = n - n2;
                    } else {
                        n3 = this.mTop;
                        n2 = 0;
                    }
                    this.invalidate(0, n2, this.mRight - this.mLeft, this.mBottom - n3);
                }
            } else {
                this.invalidate(true);
            }
            n2 = this.mRight - this.mLeft;
            int n4 = this.mBottom;
            n3 = this.mTop;
            this.mTop = n;
            this.mRenderNode.setTop(this.mTop);
            this.sizeChange(n2, this.mBottom - this.mTop, n2, n4 - n3);
            if (!bl) {
                this.mPrivateFlags |= 32;
                this.invalidate(true);
            }
            this.mBackgroundSizeChanged = true;
            this.mDefaultFocusHighlightSizeChanged = true;
            ForegroundInfo foregroundInfo = this.mForegroundInfo;
            if (foregroundInfo != null) {
                foregroundInfo.mBoundsChanged = true;
            }
            this.invalidateParentIfNeeded();
            if ((this.mPrivateFlags2 & 268435456) == 268435456) {
                this.invalidateParentIfNeeded();
            }
        }
    }

    public void setTouchDelegate(TouchDelegate touchDelegate) {
        this.mTouchDelegate = touchDelegate;
    }

    public void setTransitionAlpha(float f) {
        this.ensureTransformationInfo();
        if (this.mTransformationInfo.mTransitionAlpha != f) {
            this.mTransformationInfo.mTransitionAlpha = f;
            this.mPrivateFlags &= -262145;
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setAlpha(this.getFinalAlpha());
        }
    }

    public final void setTransitionName(String string2) {
        this.mTransitionName = string2;
    }

    public void setTransitionVisibility(int n) {
        this.mViewFlags = this.mViewFlags & -13 | n;
    }

    public void setTranslationX(float f) {
        if (f != this.getTranslationX()) {
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationX(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTranslationY(float f) {
        if (f != this.getTranslationY()) {
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationY(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
            this.notifySubtreeAccessibilityStateChangedIfNeeded();
        }
    }

    public void setTranslationZ(float f) {
        if (f != this.getTranslationZ()) {
            f = View.sanitizeFloatPropertyValue(f, "translationZ");
            this.invalidateViewProperty(true, false);
            this.mRenderNode.setTranslationZ(f);
            this.invalidateViewProperty(false, true);
            this.invalidateParentIfNeededAndWasQuickRejected();
        }
    }

    public void setVerticalFadingEdgeEnabled(boolean bl) {
        if (this.isVerticalFadingEdgeEnabled() != bl) {
            if (bl) {
                this.initScrollCache();
            }
            this.mViewFlags ^= 8192;
        }
    }

    public void setVerticalScrollBarEnabled(boolean bl) {
        if (this.isVerticalScrollBarEnabled() != bl) {
            this.mViewFlags ^= 512;
            this.computeOpaqueFlags();
            this.resolvePadding();
        }
    }

    public void setVerticalScrollbarPosition(int n) {
        if (this.mVerticalScrollbarPosition != n) {
            this.mVerticalScrollbarPosition = n;
            this.computeOpaqueFlags();
            this.resolvePadding();
        }
    }

    public void setVerticalScrollbarThumbDrawable(Drawable drawable2) {
        this.initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setVerticalThumbDrawable(drawable2);
    }

    public void setVerticalScrollbarTrackDrawable(Drawable drawable2) {
        this.initializeScrollBarDrawable();
        this.mScrollCache.scrollBar.setVerticalTrackDrawable(drawable2);
    }

    @RemotableViewMethod
    public void setVisibility(int n) {
        this.setFlags(n, 12);
    }

    @Deprecated
    public void setWillNotCacheDrawing(boolean bl) {
        int n = bl ? 131072 : 0;
        this.setFlags(n, 131072);
    }

    public void setWillNotDraw(boolean bl) {
        int n = bl ? 128 : 0;
        this.setFlags(n, 128);
    }

    public void setWindowInsetsAnimationListener(WindowInsetsAnimationListener windowInsetsAnimationListener) {
        this.getListenerInfo().mWindowInsetsAnimationListener = windowInsetsAnimationListener;
    }

    public void setX(float f) {
        this.setTranslationX(f - (float)this.mLeft);
    }

    public void setY(float f) {
        this.setTranslationY(f - (float)this.mTop);
    }

    public void setZ(float f) {
        this.setTranslationZ(f - this.getElevation());
    }

    boolean shouldDrawRoundScrollbar() {
        boolean bl = this.mResources.getConfiguration().isScreenRound();
        boolean bl2 = false;
        if (bl && this.mAttachInfo != null) {
            View view = this.getRootView();
            WindowInsets windowInsets = this.getRootWindowInsets();
            int n = this.getHeight();
            int n2 = this.getWidth();
            int n3 = view.getHeight();
            int n4 = view.getWidth();
            if (n == n3 && n2 == n4) {
                this.getLocationInWindow(this.mAttachInfo.mTmpLocation);
                if (this.mAttachInfo.mTmpLocation[0] == windowInsets.getStableInsetLeft() && this.mAttachInfo.mTmpLocation[1] == windowInsets.getStableInsetTop()) {
                    bl2 = true;
                }
                return bl2;
            }
            return false;
        }
        return false;
    }

    public boolean showContextMenu() {
        return this.getParent().showContextMenuForChild(this);
    }

    public boolean showContextMenu(float f, float f2) {
        return this.getParent().showContextMenuForChild(this, f, f2);
    }

    public ActionMode startActionMode(ActionMode.Callback callback) {
        return this.startActionMode(callback, 0);
    }

    public ActionMode startActionMode(ActionMode.Callback callback, int n) {
        ViewParent viewParent = this.getParent();
        if (viewParent == null) {
            return null;
        }
        try {
            ActionMode actionMode = viewParent.startActionModeForChild(this, callback, n);
            return actionMode;
        }
        catch (AbstractMethodError abstractMethodError) {
            return viewParent.startActionModeForChild(this, callback);
        }
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public void startActivityForResult(Intent intent, int n) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("@android:view:");
        stringBuilder.append(System.identityHashCode(this));
        this.mStartActivityRequestWho = stringBuilder.toString();
        this.getContext().startActivityForResult(this.mStartActivityRequestWho, intent, n, null);
    }

    public void startAnimation(Animation animation) {
        animation.setStartTime(-1L);
        this.setAnimation(animation);
        this.invalidateParentCaches();
        this.invalidate(true);
    }

    @Deprecated
    public final boolean startDrag(ClipData clipData, DragShadowBuilder dragShadowBuilder, Object object, int n) {
        return this.startDragAndDrop(clipData, dragShadowBuilder, object, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final boolean startDragAndDrop(ClipData clipData, DragShadowBuilder object, Object object2, int n) {
        block28 : {
            block27 : {
                block29 : {
                    object3 = this.mAttachInfo;
                    if (object3 == null) {
                        Log.w("View", "startDragAndDrop called on a detached view.");
                        return false;
                    }
                    if (!object3.mViewRootImpl.mSurface.isValid()) {
                        Log.w("View", "startDragAndDrop called with an invalid surface.");
                        return false;
                    }
                    if (clipData != null) {
                        bl = (n & 256) != 0;
                        clipData.prepareToLeaveProcess(bl);
                    }
                    point2 = new Point();
                    point = new Point();
                    object.onProvideShadowMetrics(point2, point);
                    if (point2.x < 0) throw new IllegalStateException("Drag shadow dimensions must not be negative");
                    if (point2.y < 0) throw new IllegalStateException("Drag shadow dimensions must not be negative");
                    if (point.x < 0) throw new IllegalStateException("Drag shadow dimensions must not be negative");
                    if (point.y < 0) throw new IllegalStateException("Drag shadow dimensions must not be negative");
                    if (point2.x == 0 || point2.y == 0) {
                        if (View.sAcceptZeroSizeDragShadow == false) throw new IllegalStateException("Drag shadow dimensions must be positive");
                        point2.x = 1;
                        point2.y = 1;
                    }
                    viewRootImpl = this.mAttachInfo.mViewRootImpl;
                    surfaceSession = new SurfaceSession();
                    surfaceControl = new SurfaceControl.Builder(surfaceSession).setName("drag surface").setParent(viewRootImpl.getSurfaceControl()).setBufferSize(point2.x, point2.y).setFormat(-3).build();
                    object3 = new Surface();
                    object3.copyFrom(surfaceControl);
                    object5 = null;
                    var13_26 = null;
                    var14_27 = null;
                    object4 = object3.lockCanvas(null);
                    object4.drawColor(0, PorterDuff.Mode.CLEAR);
                    object.onDrawShadow((Canvas)object4);
                    object3.unlockCanvasAndPost((Canvas)object4);
                    viewRootImpl.getLastTouchPoint(point2);
                    object = this.mAttachInfo.mSession;
                    object4 = this.mAttachInfo.mWindow;
                    n2 = viewRootImpl.getLastTouchSource();
                    f = point2.x;
                    f2 = point2.y;
                    f3 = point.x;
                    n3 = point.y;
                    f4 = n3;
                    object5 = object3;
                    object = object.performDrag((IWindow)object4, n, surfaceControl, n2, f, f2, f3, f4, clipData);
                    if (object == null) break block27;
                    if (this.mAttachInfo.mDragSurface != null) {
                        this.mAttachInfo.mDragSurface.release();
                    }
                    this.mAttachInfo.mDragSurface = object5;
                    this.mAttachInfo.mDragToken = object;
                    try {
                        viewRootImpl.setLocalDragState(object2);
                        break block27;
                    }
                    catch (Throwable throwable) {
                        break block28;
                    }
                    catch (Exception exception) {
                        break block29;
                    }
                    catch (Throwable throwable) {
                        break block28;
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                object2 = object;
                ** GOTO lbl98
            }
            bl = object != null;
            if (object == null) {
                object5.destroy();
            }
            surfaceSession.kill();
            return bl;
            catch (Throwable throwable) {
                object = var13_26;
                break block28;
            }
            catch (Exception exception) {
                object2 = var14_27;
                ** GOTO lbl98
            }
            catch (Throwable throwable) {
                object = object5;
                try {
                    block30 : {
                        try {
                            object3.unlockCanvasAndPost((Canvas)object4);
                            object = object5;
                            throw throwable;
                        }
                        catch (Exception exception) {
                            object2 = var14_27;
                        }
                        break block30;
                        catch (Throwable throwable) {
                            object = var13_26;
                            break block28;
                        }
                        catch (Exception exception) {
                            object2 = var14_27;
                        }
                    }
                    object = object2;
                    Log.e("View", "Unable to initiate drag", (Throwable)var1_7);
                    if (object2 == null) {
                        object3.destroy();
                    }
                    surfaceSession.kill();
                    return false;
                }
                catch (Throwable throwable) {
                    // empty catch block
                }
            }
        }
        if (object == null) {
            object3.destroy();
        }
        surfaceSession.kill();
        throw var1_5;
    }

    public final boolean startMovingTask(float f, float f2) {
        try {
            boolean bl = this.mAttachInfo.mSession.startMovingTask(this.mAttachInfo.mWindow, f, f2);
            return bl;
        }
        catch (RemoteException remoteException) {
            Log.e(VIEW_LOG_TAG, "Unable to start moving", remoteException);
            return false;
        }
    }

    public boolean startNestedScroll(int n) {
        if (this.hasNestedScrollingParent()) {
            return true;
        }
        if (this.isNestedScrollingEnabled()) {
            View view = this;
            for (ViewParent viewParent = this.getParent(); viewParent != null; viewParent = viewParent.getParent()) {
                try {
                    if (viewParent.onStartNestedScroll(view, this, n)) {
                        this.mNestedScrollingParent = viewParent;
                        viewParent.onNestedScrollAccepted(view, this, n);
                        return true;
                    }
                }
                catch (AbstractMethodError abstractMethodError) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("ViewParent ");
                    stringBuilder.append(viewParent);
                    stringBuilder.append(" does not implement interface method onStartNestedScroll");
                    Log.e(VIEW_LOG_TAG, stringBuilder.toString(), abstractMethodError);
                }
                if (!(viewParent instanceof View)) continue;
                view = (View)((Object)viewParent);
            }
        }
        return false;
    }

    public void stopNestedScroll() {
        ViewParent viewParent = this.mNestedScrollingParent;
        if (viewParent != null) {
            viewParent.onStopNestedScroll(this);
            this.mNestedScrollingParent = null;
        }
    }

    @UnsupportedAppUsage
    public boolean toGlobalMotionEvent(MotionEvent motionEvent) {
        Object object = this.mAttachInfo;
        if (object == null) {
            return false;
        }
        object = ((AttachInfo)object).mTmpMatrix;
        ((Matrix)object).set(Matrix.IDENTITY_MATRIX);
        this.transformMatrixToGlobal((Matrix)object);
        motionEvent.transform((Matrix)object);
        return true;
    }

    @UnsupportedAppUsage
    public boolean toLocalMotionEvent(MotionEvent motionEvent) {
        Object object = this.mAttachInfo;
        if (object == null) {
            return false;
        }
        object = ((AttachInfo)object).mTmpMatrix;
        ((Matrix)object).set(Matrix.IDENTITY_MATRIX);
        this.transformMatrixToLocal((Matrix)object);
        motionEvent.transform((Matrix)object);
        return true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        StringBuilder stringBuilder;
        block18 : {
            stringBuilder = new StringBuilder(128);
            stringBuilder.append(this.getClass().getName());
            stringBuilder.append('{');
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(' ');
            int n = this.mViewFlags & 12;
            int n2 = 73;
            int n3 = 86;
            int n4 = 46;
            if (n != 0) {
                if (n != 4) {
                    if (n != 8) {
                        stringBuilder.append('.');
                    } else {
                        stringBuilder.append('G');
                    }
                } else {
                    stringBuilder.append('I');
                }
            } else {
                stringBuilder.append('V');
            }
            int n5 = this.mViewFlags;
            n = 70;
            int n6 = (n5 & 1) == 1 ? (n5 = 70) : (n5 = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mViewFlags & 32) == 0 ? (n5 = 69) : (n5 = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mViewFlags & 128) == 128 ? (n5 = 46) : (n5 = 68);
            stringBuilder.append((char)n6);
            int n7 = this.mViewFlags;
            n5 = 72;
            n6 = (n7 & 256) != 0 ? (n7 = 72) : (n7 = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mViewFlags & 512) != 0 ? n3 : (n3 = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mViewFlags & 16384) != 0 ? (n3 = 67) : (n3 = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mViewFlags & 2097152) != 0 ? (n3 = 76) : (n3 = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mViewFlags & 8388608) != 0 ? (n3 = 88) : (n3 = 46);
            stringBuilder.append((char)n6);
            stringBuilder.append(' ');
            n6 = (this.mPrivateFlags & 8) != 0 ? (n3 = 82) : (n3 = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mPrivateFlags & 2) != 0 ? n : (n = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mPrivateFlags & 4) != 0 ? (n = 83) : (n = 46);
            stringBuilder.append((char)n6);
            n = this.mPrivateFlags;
            if ((33554432 & n) != 0) {
                stringBuilder.append('p');
            } else {
                n6 = (n & 16384) != 0 ? (n = 80) : (n = 46);
                stringBuilder.append((char)n6);
            }
            n6 = (this.mPrivateFlags & 268435456) != 0 ? n5 : (n = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mPrivateFlags & 1073741824) != 0 ? (n = 65) : (n = 46);
            stringBuilder.append((char)n6);
            n6 = (this.mPrivateFlags & Integer.MIN_VALUE) != 0 ? n2 : (n2 = 46);
            stringBuilder.append((char)n6);
            n6 = n4;
            if ((this.mPrivateFlags & 2097152) != 0) {
                n6 = n4 = 68;
            }
            stringBuilder.append((char)n6);
            stringBuilder.append(' ');
            stringBuilder.append(this.mLeft);
            stringBuilder.append(',');
            stringBuilder.append(this.mTop);
            stringBuilder.append('-');
            stringBuilder.append(this.mRight);
            stringBuilder.append(',');
            stringBuilder.append(this.mBottom);
            n2 = this.getId();
            if (n2 != -1) {
                stringBuilder.append(" #");
                stringBuilder.append(Integer.toHexString(n2));
                Object object = this.mResources;
                if (n2 > 0 && Resources.resourceHasPackage(n2) && object != null) {
                    String string2;
                    block17 : {
                        n4 = -16777216 & n2;
                        if (n4 != 16777216) {
                            if (n4 != 2130706432) {
                                try {
                                    string2 = ((Resources)object).getResourcePackageName(n2);
                                    break block17;
                                }
                                catch (Resources.NotFoundException notFoundException) {
                                    break block18;
                                }
                            }
                            string2 = "app";
                        } else {
                            string2 = "android";
                        }
                    }
                    String string3 = ((Resources)object).getResourceTypeName(n2);
                    object = ((Resources)object).getResourceEntryName(n2);
                    stringBuilder.append(" ");
                    stringBuilder.append(string2);
                    stringBuilder.append(":");
                    stringBuilder.append(string3);
                    stringBuilder.append("/");
                    stringBuilder.append((String)object);
                }
            }
        }
        if (this.mAutofillId != null) {
            stringBuilder.append(" aid=");
            stringBuilder.append(this.mAutofillId);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void transformFromViewToWindowSpace(int[] arrn) {
        if (arrn != null && arrn.length >= 2) {
            Object object = this.mAttachInfo;
            if (object == null) {
                arrn[1] = 0;
                arrn[0] = 0;
                return;
            }
            float[] arrf = ((AttachInfo)object).mTmpTransformLocation;
            arrf[0] = arrn[0];
            arrf[1] = arrn[1];
            if (!this.hasIdentityMatrix()) {
                this.getMatrix().mapPoints(arrf);
            }
            arrf[0] = arrf[0] + (float)this.mLeft;
            arrf[1] = arrf[1] + (float)this.mTop;
            object = this.mParent;
            while (object instanceof View) {
                object = (View)object;
                arrf[0] = arrf[0] - (float)((View)object).mScrollX;
                arrf[1] = arrf[1] - (float)((View)object).mScrollY;
                if (!((View)object).hasIdentityMatrix()) {
                    ((View)object).getMatrix().mapPoints(arrf);
                }
                arrf[0] = arrf[0] + (float)((View)object).mLeft;
                arrf[1] = arrf[1] + (float)((View)object).mTop;
                object = ((View)object).mParent;
            }
            if (object instanceof ViewRootImpl) {
                object = (ViewRootImpl)object;
                arrf[1] = arrf[1] - (float)((ViewRootImpl)object).mCurScrollY;
            }
            arrn[0] = Math.round(arrf[0]);
            arrn[1] = Math.round(arrf[1]);
            return;
        }
        throw new IllegalArgumentException("inOutLocation must be an array of two integers");
    }

    public void transformMatrixToGlobal(Matrix matrix) {
        Object object = this.mParent;
        if (object instanceof View) {
            object = (View)object;
            ((View)object).transformMatrixToGlobal(matrix);
            matrix.preTranslate(-((View)object).mScrollX, -((View)object).mScrollY);
        } else if (object instanceof ViewRootImpl) {
            object = (ViewRootImpl)object;
            ((ViewRootImpl)object).transformMatrixToGlobal(matrix);
            matrix.preTranslate(0.0f, -((ViewRootImpl)object).mCurScrollY);
        }
        matrix.preTranslate(this.mLeft, this.mTop);
        if (!this.hasIdentityMatrix()) {
            matrix.preConcat(this.getMatrix());
        }
    }

    public void transformMatrixToLocal(Matrix matrix) {
        Object object = this.mParent;
        if (object instanceof View) {
            object = (View)object;
            ((View)object).transformMatrixToLocal(matrix);
            matrix.postTranslate(((View)object).mScrollX, ((View)object).mScrollY);
        } else if (object instanceof ViewRootImpl) {
            object = (ViewRootImpl)object;
            ((ViewRootImpl)object).transformMatrixToLocal(matrix);
            matrix.postTranslate(0.0f, ((ViewRootImpl)object).mCurScrollY);
        }
        matrix.postTranslate(-this.mLeft, -this.mTop);
        if (!this.hasIdentityMatrix()) {
            matrix.postConcat(this.getInverseMatrix());
        }
    }

    void unFocus(View view) {
        this.clearFocusInternal(view, false, false);
    }

    public void unscheduleDrawable(Drawable drawable2) {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null && drawable2 != null) {
            attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, null, drawable2);
        }
    }

    @Override
    public void unscheduleDrawable(Drawable drawable2, Runnable runnable) {
        if (this.verifyDrawable(drawable2) && runnable != null) {
            AttachInfo attachInfo = this.mAttachInfo;
            if (attachInfo != null) {
                attachInfo.mViewRootImpl.mChoreographer.removeCallbacks(1, runnable, drawable2);
            }
            this.getRunQueue().removeCallbacks(runnable);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    @UnsupportedAppUsage
    public RenderNode updateDisplayListIfDirty() {
        var1_1 = this.mRenderNode;
        if (!this.canHaveDisplayList()) {
            return var1_1;
        }
        if ((this.mPrivateFlags & 32768) != 0 && var1_1.hasDisplayList() && !this.mRecreateDisplayList) {
            this.mPrivateFlags |= 32800;
            this.mPrivateFlags &= -2097153;
            return var1_1;
        }
        if (var1_1.hasDisplayList() && !this.mRecreateDisplayList) {
            this.mPrivateFlags |= 32800;
            this.mPrivateFlags &= -2097153;
            this.dispatchGetDisplayList();
            return var1_1;
        }
        this.mRecreateDisplayList = true;
        var2_2 = this.mRight;
        var3_3 = this.mLeft;
        var4_4 = this.mBottom;
        var5_5 = this.mTop;
        var6_6 = this.getLayerType();
        var7_7 = var1_1.beginRecording(var2_2 - var3_3, var4_4 - var5_5);
        if (var6_6 != 1) ** GOTO lbl27
        try {
            this.buildDrawingCache(true);
            var8_9 = this.getDrawingCache(true);
            if (var8_9 == null) return var1_1;
            var7_7.drawBitmap(var8_9, 0.0f, 0.0f, this.mLayerPaint);
            return var1_1;
lbl27: // 1 sources:
            this.computeScroll();
            var7_7.translate(-this.mScrollX, -this.mScrollY);
            this.mPrivateFlags |= 32800;
            this.mPrivateFlags &= -2097153;
            if ((this.mPrivateFlags & 128) == 128) {
                this.dispatchDraw(var7_7);
                this.drawAutofilledHighlight(var7_7);
                if (this.mOverlay != null && !this.mOverlay.isEmpty()) {
                    this.mOverlay.getOverlayView().draw(var7_7);
                }
                if (this.debugDraw() == false) return var1_1;
                this.debugDrawFocus(var7_7);
                return var1_1;
            }
            this.draw(var7_7);
            return var1_1;
        }
        finally {
            var1_1.endRecording();
            this.setDisplayListProperties(var1_1);
        }
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final void updateDragShadow(DragShadowBuilder dragShadowBuilder) {
        Object object = this.mAttachInfo;
        if (object == null) {
            Log.w(VIEW_LOG_TAG, "updateDragShadow called on a detached view.");
            return;
        }
        if (((AttachInfo)object).mDragToken == null) {
            Log.e(VIEW_LOG_TAG, "No active drag");
            return;
        }
        try {
            object = this.mAttachInfo.mDragSurface.lockCanvas(null);
        }
        catch (Exception exception) {
            Log.e(VIEW_LOG_TAG, "Unable to update drag shadow", exception);
            return;
        }
        ((Canvas)object).drawColor(0, PorterDuff.Mode.CLEAR);
        dragShadowBuilder.onDrawShadow((Canvas)object);
        {
            catch (Throwable throwable) {
                this.mAttachInfo.mDragSurface.unlockCanvasAndPost((Canvas)object);
                throw throwable;
            }
        }
        this.mAttachInfo.mDragSurface.unlockCanvasAndPost((Canvas)object);
    }

    boolean updateLocalSystemUiVisibility(int n, int n2) {
        int n3 = this.mSystemUiVisibility;
        if ((n = n2 & n3 | n & n2) != n3) {
            this.setSystemUiVisibility(n);
            return true;
        }
        return false;
    }

    void updateSystemGestureExclusionRects() {
        AttachInfo attachInfo = this.mAttachInfo;
        if (attachInfo != null) {
            attachInfo.mViewRootImpl.updateSystemGestureExclusionRectsForView(this);
        }
    }

    protected boolean verifyDrawable(Drawable drawable2) {
        ForegroundInfo foregroundInfo;
        boolean bl = drawable2 == this.mBackground || (foregroundInfo = this.mForegroundInfo) != null && foregroundInfo.mDrawable == drawable2 || this.mDefaultFocusHighlight == drawable2;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    @Deprecated
    public boolean willNotCacheDrawing() {
        boolean bl = (this.mViewFlags & 131072) == 131072;
        return bl;
    }

    @ViewDebug.ExportedProperty(category="drawing")
    public boolean willNotDraw() {
        boolean bl = (this.mViewFlags & 128) == 128;
        return bl;
    }

    public static class AccessibilityDelegate {
        public void addExtraDataToAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo, String string2, Bundle bundle) {
            view.addExtraDataToAccessibilityNodeInfo(accessibilityNodeInfo, string2, bundle);
        }

        @UnsupportedAppUsage
        public AccessibilityNodeInfo createAccessibilityNodeInfo(View view) {
            return view.createAccessibilityNodeInfoInternal();
        }

        public boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            return view.dispatchPopulateAccessibilityEventInternal(accessibilityEvent);
        }

        public AccessibilityNodeProvider getAccessibilityNodeProvider(View view) {
            return null;
        }

        public void onInitializeAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            view.onInitializeAccessibilityEventInternal(accessibilityEvent);
        }

        public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
            view.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        }

        public void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            view.onPopulateAccessibilityEventInternal(accessibilityEvent);
        }

        public boolean onRequestSendAccessibilityEvent(ViewGroup viewGroup, View view, AccessibilityEvent accessibilityEvent) {
            return viewGroup.onRequestSendAccessibilityEventInternal(view, accessibilityEvent);
        }

        public boolean performAccessibilityAction(View view, int n, Bundle bundle) {
            return view.performAccessibilityActionInternal(n, bundle);
        }

        public void sendAccessibilityEvent(View view, int n) {
            view.sendAccessibilityEventInternal(n);
        }

        public void sendAccessibilityEventUnchecked(View view, AccessibilityEvent accessibilityEvent) {
            view.sendAccessibilityEventUncheckedInternal(accessibilityEvent);
        }
    }

    static final class AttachInfo {
        int mAccessibilityFetchFlags;
        Drawable mAccessibilityFocusDrawable;
        int mAccessibilityWindowId = -1;
        boolean mAlwaysConsumeSystemBars;
        @UnsupportedAppUsage
        float mApplicationScale;
        Drawable mAutofilledDrawable;
        Canvas mCanvas;
        @UnsupportedAppUsage
        final Rect mContentInsets = new Rect();
        boolean mDebugLayout = DisplayProperties.debug_layout().orElse(false);
        int mDisabledSystemUiVisibility;
        Display mDisplay;
        final DisplayCutout.ParcelableWrapper mDisplayCutout = new DisplayCutout.ParcelableWrapper(DisplayCutout.NO_CUTOUT);
        @UnsupportedAppUsage
        int mDisplayState = 0;
        public Surface mDragSurface;
        IBinder mDragToken;
        @UnsupportedAppUsage
        long mDrawingTime;
        boolean mForceReportNewAttributes;
        @UnsupportedAppUsage
        final ViewTreeObserver.InternalInsetsInfo mGivenInternalInsets = new ViewTreeObserver.InternalInsetsInfo();
        int mGlobalSystemUiVisibility = -1;
        @UnsupportedAppUsage
        final Handler mHandler;
        boolean mHandlingPointerEvent;
        boolean mHardwareAccelerated;
        boolean mHardwareAccelerationRequested;
        boolean mHasNonEmptyGivenInternalInsets;
        boolean mHasSystemUiListeners;
        @UnsupportedAppUsage
        boolean mHasWindowFocus;
        IWindowId mIWindowId;
        @UnsupportedAppUsage
        boolean mInTouchMode;
        final int[] mInvalidateChildLocation = new int[2];
        @UnsupportedAppUsage
        boolean mKeepScreenOn;
        @UnsupportedAppUsage
        final KeyEvent.DispatcherState mKeyDispatchState = new KeyEvent.DispatcherState();
        boolean mNeedsUpdateLightCenter;
        final Rect mOutsets = new Rect();
        final Rect mOverscanInsets = new Rect();
        boolean mOverscanRequested;
        IBinder mPanelParentWindowToken;
        List<RenderNode> mPendingAnimatingRenderNodes;
        final Point mPoint = new Point();
        @UnsupportedAppUsage
        boolean mRecomputeGlobalAttributes;
        final Callbacks mRootCallbacks;
        View mRootView;
        @UnsupportedAppUsage
        boolean mScalingRequired;
        @UnsupportedAppUsage
        final ArrayList<View> mScrollContainers = new ArrayList();
        @UnsupportedAppUsage
        final IWindowSession mSession;
        @UnsupportedAppUsage
        final Rect mStableInsets = new Rect();
        int mSystemUiVisibility;
        final ArrayList<View> mTempArrayList = new ArrayList(24);
        ThreadedRenderer mThreadedRenderer;
        final Rect mTmpInvalRect = new Rect();
        final int[] mTmpLocation = new int[2];
        final Matrix mTmpMatrix = new Matrix();
        final Outline mTmpOutline = new Outline();
        final List<RectF> mTmpRectList = new ArrayList<RectF>();
        final float[] mTmpTransformLocation = new float[2];
        final RectF mTmpTransformRect = new RectF();
        final RectF mTmpTransformRect1 = new RectF();
        final Transformation mTmpTransformation = new Transformation();
        View mTooltipHost;
        final int[] mTransparentLocation = new int[2];
        @UnsupportedAppUsage
        final ViewTreeObserver mTreeObserver;
        boolean mUnbufferedDispatchRequested;
        boolean mUse32BitDrawingCache;
        View mViewRequestingLayout;
        final ViewRootImpl mViewRootImpl;
        @UnsupportedAppUsage
        boolean mViewScrollChanged;
        @UnsupportedAppUsage
        boolean mViewVisibilityChanged;
        @UnsupportedAppUsage
        final Rect mVisibleInsets = new Rect();
        @UnsupportedAppUsage
        final IWindow mWindow;
        WindowId mWindowId;
        int mWindowLeft;
        final IBinder mWindowToken;
        int mWindowTop;
        int mWindowVisibility;

        AttachInfo(IWindowSession iWindowSession, IWindow iWindow, Display display, ViewRootImpl viewRootImpl, Handler handler, Callbacks callbacks, Context context) {
            this.mSession = iWindowSession;
            this.mWindow = iWindow;
            this.mWindowToken = iWindow.asBinder();
            this.mDisplay = display;
            this.mViewRootImpl = viewRootImpl;
            this.mHandler = handler;
            this.mRootCallbacks = callbacks;
            this.mTreeObserver = new ViewTreeObserver(context);
        }

        static interface Callbacks {
            public boolean performHapticFeedback(int var1, boolean var2);

            public void playSoundEffect(int var1);
        }

        static class InvalidateInfo {
            private static final int POOL_LIMIT = 10;
            private static final Pools.SynchronizedPool<InvalidateInfo> sPool = new Pools.SynchronizedPool(10);
            @UnsupportedAppUsage
            int bottom;
            @UnsupportedAppUsage
            int left;
            @UnsupportedAppUsage
            int right;
            @UnsupportedAppUsage
            View target;
            @UnsupportedAppUsage
            int top;

            InvalidateInfo() {
            }

            public static InvalidateInfo obtain() {
                InvalidateInfo invalidateInfo = sPool.acquire();
                if (invalidateInfo == null) {
                    invalidateInfo = new InvalidateInfo();
                }
                return invalidateInfo;
            }

            public void recycle() {
                this.target = null;
                sPool.release(this);
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AutofillFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AutofillImportance {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AutofillType {
    }

    public static class BaseSavedState
    extends AbsSavedState {
        static final int AUTOFILL_ID = 4;
        public static final Parcelable.Creator<BaseSavedState> CREATOR = new Parcelable.ClassLoaderCreator<BaseSavedState>(){

            @Override
            public BaseSavedState createFromParcel(Parcel parcel) {
                return new BaseSavedState(parcel);
            }

            @Override
            public BaseSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new BaseSavedState(parcel, classLoader);
            }

            public BaseSavedState[] newArray(int n) {
                return new BaseSavedState[n];
            }
        };
        static final int IS_AUTOFILLED = 2;
        static final int START_ACTIVITY_REQUESTED_WHO_SAVED = 1;
        int mAutofillViewId;
        boolean mIsAutofilled;
        int mSavedData;
        String mStartActivityRequestWhoSaved;

        public BaseSavedState(Parcel parcel) {
            this(parcel, null);
        }

        public BaseSavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.mSavedData = parcel.readInt();
            this.mStartActivityRequestWhoSaved = parcel.readString();
            this.mIsAutofilled = parcel.readBoolean();
            this.mAutofillViewId = parcel.readInt();
        }

        public BaseSavedState(Parcelable parcelable) {
            super(parcelable);
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeInt(this.mSavedData);
            parcel.writeString(this.mStartActivityRequestWhoSaved);
            parcel.writeBoolean(this.mIsAutofilled);
            parcel.writeInt(this.mAutofillViewId);
        }

    }

    private final class CheckForLongPress
    implements Runnable {
        private int mClassification;
        private boolean mOriginalPressedState;
        private int mOriginalWindowAttachCount;
        private float mX;
        private float mY;

        private CheckForLongPress() {
        }

        public void rememberPressedState() {
            this.mOriginalPressedState = View.this.isPressed();
        }

        public void rememberWindowAttachCount() {
            this.mOriginalWindowAttachCount = View.this.mWindowAttachCount;
        }

        @Override
        public void run() {
            if (this.mOriginalPressedState == View.this.isPressed() && View.this.mParent != null && this.mOriginalWindowAttachCount == View.this.mWindowAttachCount) {
                View.this.recordGestureClassification(this.mClassification);
                if (View.this.performLongClick(this.mX, this.mY)) {
                    View.this.mHasPerformedLongPress = true;
                }
            }
        }

        public void setAnchor(float f, float f2) {
            this.mX = f;
            this.mY = f2;
        }

        public void setClassification(int n) {
            this.mClassification = n;
        }
    }

    private final class CheckForTap
    implements Runnable {
        public float x;
        public float y;

        private CheckForTap() {
        }

        @Override
        public void run() {
            View view = View.this;
            view.mPrivateFlags &= -33554433;
            View.this.setPressed(true, this.x, this.y);
            long l = ViewConfiguration.getLongPressTimeout() - ViewConfiguration.getTapTimeout();
            View.this.checkForLongClick(l, this.x, this.y, 3);
        }
    }

    private static class DeclaredOnClickListener
    implements OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View view, String string2) {
            this.mHostView = view;
            this.mMethodName = string2;
        }

        private void resolveMethod(Context object, String object2) {
            while (object != null) {
                block7 : {
                    if (((Context)object).isRestricted()) break block7;
                    object2 = object.getClass().getMethod(this.mMethodName, View.class);
                    if (object2 == null) break block7;
                    try {
                        this.mResolvedMethod = object2;
                        this.mResolvedContext = object;
                        return;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        // empty catch block
                    }
                }
                if (object instanceof ContextWrapper) {
                    object = ((ContextWrapper)object).getBaseContext();
                    continue;
                }
                object = null;
            }
            int n = this.mHostView.getId();
            if (n == -1) {
                object = "";
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(" with id '");
                ((StringBuilder)object).append(this.mHostView.getContext().getResources().getResourceEntryName(n));
                ((StringBuilder)object).append("'");
                object = ((StringBuilder)object).toString();
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Could not find method ");
            ((StringBuilder)object2).append(this.mMethodName);
            ((StringBuilder)object2).append("(View) in a parent or ancestor Context for android:onClick attribute defined on view ");
            ((StringBuilder)object2).append(this.mHostView.getClass());
            ((StringBuilder)object2).append((String)object);
            throw new IllegalStateException(((StringBuilder)object2).toString());
        }

        @Override
        public void onClick(View view) {
            if (this.mResolvedMethod == null) {
                this.resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke(this.mResolvedContext, view);
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new IllegalStateException("Could not execute method for android:onClick", invocationTargetException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", illegalAccessException);
            }
        }
    }

    public static class DragShadowBuilder {
        @UnsupportedAppUsage
        private final WeakReference<View> mView;

        public DragShadowBuilder() {
            this.mView = new WeakReference<Object>(null);
        }

        public DragShadowBuilder(View view) {
            this.mView = new WeakReference<View>(view);
        }

        public final View getView() {
            return (View)this.mView.get();
        }

        public void onDrawShadow(Canvas canvas) {
            View view = (View)this.mView.get();
            if (view != null) {
                view.draw(canvas);
            } else {
                Log.e(View.VIEW_LOG_TAG, "Asked to draw drag shadow but no view");
            }
        }

        public void onProvideShadowMetrics(Point point, Point point2) {
            View view = (View)this.mView.get();
            if (view != null) {
                point.set(view.getWidth(), view.getHeight());
                point2.set(point.x / 2, point.y / 2);
            } else {
                Log.e(View.VIEW_LOG_TAG, "Asked for drag thumb metrics but no view");
            }
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DrawingCacheQuality {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FindViewFlags {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FocusDirection {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FocusRealDirection {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Focusable {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface FocusableMode {
    }

    private static class ForegroundInfo {
        private boolean mBoundsChanged = true;
        private Drawable mDrawable;
        private int mGravity = 119;
        private boolean mInsidePadding = true;
        private final Rect mOverlayBounds = new Rect();
        private final Rect mSelfBounds = new Rect();
        private TintInfo mTintInfo;

        private ForegroundInfo() {
        }

        static /* synthetic */ boolean access$102(ForegroundInfo foregroundInfo, boolean bl) {
            foregroundInfo.mInsidePadding = bl;
            return bl;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LayerType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LayoutDir {
    }

    static class ListenerInfo {
        OnApplyWindowInsetsListener mOnApplyWindowInsetsListener;
        private CopyOnWriteArrayList<OnAttachStateChangeListener> mOnAttachStateChangeListeners;
        OnCapturedPointerListener mOnCapturedPointerListener;
        @UnsupportedAppUsage
        public OnClickListener mOnClickListener;
        protected OnContextClickListener mOnContextClickListener;
        @UnsupportedAppUsage
        protected OnCreateContextMenuListener mOnCreateContextMenuListener;
        @UnsupportedAppUsage
        private OnDragListener mOnDragListener;
        @UnsupportedAppUsage
        protected OnFocusChangeListener mOnFocusChangeListener;
        @UnsupportedAppUsage
        private OnGenericMotionListener mOnGenericMotionListener;
        @UnsupportedAppUsage
        private OnHoverListener mOnHoverListener;
        @UnsupportedAppUsage
        private OnKeyListener mOnKeyListener;
        private ArrayList<OnLayoutChangeListener> mOnLayoutChangeListeners;
        @UnsupportedAppUsage
        protected OnLongClickListener mOnLongClickListener;
        protected OnScrollChangeListener mOnScrollChangeListener;
        private OnSystemUiVisibilityChangeListener mOnSystemUiVisibilityChangeListener;
        @UnsupportedAppUsage
        private OnTouchListener mOnTouchListener;
        public RenderNode.PositionUpdateListener mPositionUpdateListener;
        private List<Rect> mSystemGestureExclusionRects;
        private ArrayList<OnUnhandledKeyEventListener> mUnhandledKeyListeners;
        private WindowInsetsAnimationListener mWindowInsetsAnimationListener;

        ListenerInfo() {
        }
    }

    private static class MatchIdPredicate
    implements Predicate<View> {
        public int mId;

        private MatchIdPredicate() {
        }

        @Override
        public boolean test(View view) {
            boolean bl = view.mID == this.mId;
            return bl;
        }
    }

    private static class MatchLabelForPredicate
    implements Predicate<View> {
        private int mLabeledId;

        private MatchLabelForPredicate() {
        }

        @Override
        public boolean test(View view) {
            boolean bl = view.mLabelForId == this.mLabeledId;
            return bl;
        }
    }

    public static class MeasureSpec {
        public static final int AT_MOST = Integer.MIN_VALUE;
        public static final int EXACTLY = 1073741824;
        private static final int MODE_MASK = -1073741824;
        private static final int MODE_SHIFT = 30;
        public static final int UNSPECIFIED = 0;

        static int adjust(int n, int n2) {
            int n3;
            int n4 = MeasureSpec.getMode(n);
            int n5 = MeasureSpec.getSize(n);
            if (n4 == 0) {
                return MeasureSpec.makeMeasureSpec(n5, 0);
            }
            n5 = n3 = n5 + n2;
            if (n3 < 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("MeasureSpec.adjust: new size would be negative! (");
                stringBuilder.append(n3);
                stringBuilder.append(") spec: ");
                stringBuilder.append(MeasureSpec.toString(n));
                stringBuilder.append(" delta: ");
                stringBuilder.append(n2);
                Log.e(View.VIEW_LOG_TAG, stringBuilder.toString());
                n5 = 0;
            }
            return MeasureSpec.makeMeasureSpec(n5, n4);
        }

        public static int getMode(int n) {
            return -1073741824 & n;
        }

        public static int getSize(int n) {
            return 1073741823 & n;
        }

        public static int makeMeasureSpec(int n, int n2) {
            if (sUseBrokenMakeMeasureSpec) {
                return n + n2;
            }
            return 1073741823 & n | -1073741824 & n2;
        }

        @UnsupportedAppUsage
        public static int makeSafeMeasureSpec(int n, int n2) {
            if (sUseZeroUnspecifiedMeasureSpec && n2 == 0) {
                return 0;
            }
            return MeasureSpec.makeMeasureSpec(n, n2);
        }

        public static String toString(int n) {
            int n2 = MeasureSpec.getMode(n);
            n = MeasureSpec.getSize(n);
            StringBuilder stringBuilder = new StringBuilder("MeasureSpec: ");
            if (n2 == 0) {
                stringBuilder.append("UNSPECIFIED ");
            } else if (n2 == 1073741824) {
                stringBuilder.append("EXACTLY ");
            } else if (n2 == Integer.MIN_VALUE) {
                stringBuilder.append("AT_MOST ");
            } else {
                stringBuilder.append(n2);
                stringBuilder.append(" ");
            }
            stringBuilder.append(n);
            return stringBuilder.toString();
        }

        @Retention(value=RetentionPolicy.SOURCE)
        public static @interface MeasureSpecMode {
        }

    }

    public static interface OnApplyWindowInsetsListener {
        public WindowInsets onApplyWindowInsets(View var1, WindowInsets var2);
    }

    public static interface OnAttachStateChangeListener {
        public void onViewAttachedToWindow(View var1);

        public void onViewDetachedFromWindow(View var1);
    }

    public static interface OnCapturedPointerListener {
        public boolean onCapturedPointer(View var1, MotionEvent var2);
    }

    public static interface OnClickListener {
        public void onClick(View var1);
    }

    public static interface OnContextClickListener {
        public boolean onContextClick(View var1);
    }

    public static interface OnCreateContextMenuListener {
        public void onCreateContextMenu(ContextMenu var1, View var2, ContextMenu.ContextMenuInfo var3);
    }

    public static interface OnDragListener {
        public boolean onDrag(View var1, DragEvent var2);
    }

    public static interface OnFocusChangeListener {
        public void onFocusChange(View var1, boolean var2);
    }

    public static interface OnGenericMotionListener {
        public boolean onGenericMotion(View var1, MotionEvent var2);
    }

    public static interface OnHoverListener {
        public boolean onHover(View var1, MotionEvent var2);
    }

    public static interface OnKeyListener {
        public boolean onKey(View var1, int var2, KeyEvent var3);
    }

    public static interface OnLayoutChangeListener {
        public void onLayoutChange(View var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9);
    }

    public static interface OnLongClickListener {
        public boolean onLongClick(View var1);
    }

    public static interface OnScrollChangeListener {
        public void onScrollChange(View var1, int var2, int var3, int var4, int var5);
    }

    public static interface OnSystemUiVisibilityChangeListener {
        public void onSystemUiVisibilityChange(int var1);
    }

    public static interface OnTouchListener {
        public boolean onTouch(View var1, MotionEvent var2);
    }

    public static interface OnUnhandledKeyEventListener {
        public boolean onUnhandledKeyEvent(View var1, KeyEvent var2);
    }

    private final class PerformClick
    implements Runnable {
        private PerformClick() {
        }

        @Override
        public void run() {
            View.this.recordGestureClassification(1);
            View.this.performClickInternal();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ResolvedLayoutDir {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ScrollBarStyle {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ScrollIndicators {
    }

    private static class ScrollabilityCache
    implements Runnable {
        public static final int DRAGGING_HORIZONTAL_SCROLL_BAR = 2;
        public static final int DRAGGING_VERTICAL_SCROLL_BAR = 1;
        public static final int FADING = 2;
        public static final int NOT_DRAGGING = 0;
        public static final int OFF = 0;
        public static final int ON = 1;
        private static final float[] OPAQUE = new float[]{255.0f};
        private static final float[] TRANSPARENT = new float[]{0.0f};
        public boolean fadeScrollBars;
        public long fadeStartTime;
        public int fadingEdgeLength;
        @UnsupportedAppUsage
        public View host;
        public float[] interpolatorValues;
        private int mLastColor;
        public final Rect mScrollBarBounds = new Rect();
        public float mScrollBarDraggingPos = 0.0f;
        public int mScrollBarDraggingState = 0;
        public final Rect mScrollBarTouchBounds = new Rect();
        public final Matrix matrix;
        public final Paint paint;
        @UnsupportedAppUsage
        public ScrollBarDrawable scrollBar;
        public int scrollBarDefaultDelayBeforeFade;
        public int scrollBarFadeDuration;
        public final Interpolator scrollBarInterpolator = new Interpolator(1, 2);
        public int scrollBarMinTouchTarget;
        public int scrollBarSize;
        public Shader shader;
        @UnsupportedAppUsage
        public int state = 0;

        public ScrollabilityCache(ViewConfiguration viewConfiguration, View view) {
            this.fadingEdgeLength = viewConfiguration.getScaledFadingEdgeLength();
            this.scrollBarSize = viewConfiguration.getScaledScrollBarSize();
            this.scrollBarMinTouchTarget = viewConfiguration.getScaledMinScrollbarTouchTarget();
            this.scrollBarDefaultDelayBeforeFade = ViewConfiguration.getScrollDefaultDelay();
            this.scrollBarFadeDuration = ViewConfiguration.getScrollBarFadeDuration();
            this.paint = new Paint();
            this.matrix = new Matrix();
            this.shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
            this.paint.setShader(this.shader);
            this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
            this.host = view;
        }

        @Override
        public void run() {
            long l = AnimationUtils.currentAnimationTimeMillis();
            if (l >= this.fadeStartTime) {
                int n = (int)l;
                Interpolator interpolator2 = this.scrollBarInterpolator;
                interpolator2.setKeyFrame(0, n, OPAQUE);
                interpolator2.setKeyFrame(0 + 1, n + this.scrollBarFadeDuration, TRANSPARENT);
                this.state = 2;
                this.host.invalidate(true);
            }
        }

        public void setFadeColor(int n) {
            if (n != this.mLastColor) {
                this.mLastColor = n;
                if (n != 0) {
                    this.shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, n | -16777216, n & 16777215, Shader.TileMode.CLAMP);
                    this.paint.setShader(this.shader);
                    this.paint.setXfermode(null);
                } else {
                    this.shader = new LinearGradient(0.0f, 0.0f, 0.0f, 1.0f, -16777216, 0, Shader.TileMode.CLAMP);
                    this.paint.setShader(this.shader);
                    this.paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
                }
            }
        }
    }

    private class SendViewScrolledAccessibilityEvent
    implements Runnable {
        public int mDeltaX;
        public int mDeltaY;
        public volatile boolean mIsPending;

        private SendViewScrolledAccessibilityEvent() {
        }

        private void reset() {
            this.mIsPending = false;
            this.mDeltaX = 0;
            this.mDeltaY = 0;
        }

        public void post(int n, int n2) {
            this.mDeltaX += n;
            this.mDeltaY += n2;
            if (!this.mIsPending) {
                this.mIsPending = true;
                View.this.postDelayed(this, ViewConfiguration.getSendRecurringAccessibilityEventsInterval());
            }
        }

        @Override
        public void run() {
            if (AccessibilityManager.getInstance(View.this.mContext).isEnabled()) {
                AccessibilityEvent accessibilityEvent = AccessibilityEvent.obtain(4096);
                accessibilityEvent.setScrollDeltaX(this.mDeltaX);
                accessibilityEvent.setScrollDeltaY(this.mDeltaY);
                View.this.sendAccessibilityEventUnchecked(accessibilityEvent);
            }
            this.reset();
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TextAlignment {
    }

    static class TintInfo {
        BlendMode mBlendMode;
        boolean mHasTintList;
        boolean mHasTintMode;
        ColorStateList mTintList;

        TintInfo() {
        }
    }

    private static class TooltipInfo {
        int mAnchorX;
        int mAnchorY;
        Runnable mHideTooltipRunnable;
        int mHoverSlop;
        Runnable mShowTooltipRunnable;
        boolean mTooltipFromLongClick;
        TooltipPopup mTooltipPopup;
        CharSequence mTooltipText;

        private TooltipInfo() {
        }

        private void clearAnchorPos() {
            this.mAnchorX = Integer.MAX_VALUE;
            this.mAnchorY = Integer.MAX_VALUE;
        }

        private boolean updateAnchorPos(MotionEvent motionEvent) {
            int n = (int)motionEvent.getX();
            int n2 = (int)motionEvent.getY();
            if (Math.abs(n - this.mAnchorX) <= this.mHoverSlop && Math.abs(n2 - this.mAnchorY) <= this.mHoverSlop) {
                return false;
            }
            this.mAnchorX = n;
            this.mAnchorY = n2;
            return true;
        }
    }

    static class TransformationInfo {
        @ViewDebug.ExportedProperty
        private float mAlpha = 1.0f;
        private Matrix mInverseMatrix;
        private final Matrix mMatrix = new Matrix();
        float mTransitionAlpha = 1.0f;

        TransformationInfo() {
        }
    }

    private final class UnsetPressedState
    implements Runnable {
        private UnsetPressedState() {
        }

        @Override
        public void run() {
            View.this.setPressed(false);
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ViewStructureType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Visibility {
    }

    private static class VisibilityChangeForAutofillHandler
    extends Handler {
        private final AutofillManager mAfm;
        private final View mView;

        private VisibilityChangeForAutofillHandler(AutofillManager autofillManager, View view) {
            this.mAfm = autofillManager;
            this.mView = view;
        }

        @Override
        public void handleMessage(Message object) {
            AutofillManager autofillManager = this.mAfm;
            object = this.mView;
            autofillManager.notifyViewVisibilityChanged((View)object, ((View)object).isShown());
        }
    }

}

