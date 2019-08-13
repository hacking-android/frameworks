/*
 * Decompiled with CFR 0.145.
 */
package android.hardware.camera2.legacy;

import android.hardware.Camera;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.CaptureResult;
import android.hardware.camera2.impl.CameraMetadataNative;
import android.hardware.camera2.utils.ParamsUtils;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.util.Objects;

public class LegacyFocusStateMapper {
    private static final boolean DEBUG = false;
    private static String TAG = "LegacyFocusStateMapper";
    private String mAfModePrevious = null;
    private int mAfRun = 0;
    private int mAfState = 0;
    private int mAfStatePrevious = 0;
    private final Camera mCamera;
    private final Object mLock = new Object();

    public LegacyFocusStateMapper(Camera camera) {
        this.mCamera = Preconditions.checkNotNull(camera, "camera must not be null");
    }

    static /* synthetic */ Object access$000(LegacyFocusStateMapper legacyFocusStateMapper) {
        return legacyFocusStateMapper.mLock;
    }

    static /* synthetic */ int access$100(LegacyFocusStateMapper legacyFocusStateMapper) {
        return legacyFocusStateMapper.mAfRun;
    }

    static /* synthetic */ String access$200() {
        return TAG;
    }

    static /* synthetic */ int access$302(LegacyFocusStateMapper legacyFocusStateMapper, int n) {
        legacyFocusStateMapper.mAfState = n;
        return n;
    }

    private static String afStateToString(int n) {
        switch (n) {
            default: {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("UNKNOWN(");
                stringBuilder.append(n);
                stringBuilder.append(")");
                return stringBuilder.toString();
            }
            case 6: {
                return "PASSIVE_UNFOCUSED";
            }
            case 5: {
                return "NOT_FOCUSED_LOCKED";
            }
            case 4: {
                return "FOCUSED_LOCKED";
            }
            case 3: {
                return "ACTIVE_SCAN";
            }
            case 2: {
                return "PASSIVE_FOCUSED";
            }
            case 1: {
                return "PASSIVE_SCAN";
            }
            case 0: 
        }
        return "INACTIVE";
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void mapResultTriggers(CameraMetadataNative cameraMetadataNative) {
        int n;
        Preconditions.checkNotNull(cameraMetadataNative, "result must not be null");
        Object object = this.mLock;
        synchronized (object) {
            n = this.mAfState;
        }
        cameraMetadataNative.set(CaptureResult.CONTROL_AF_STATE, Integer.valueOf(n));
        this.mAfStatePrevious = n;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void processRequestTriggers(CaptureRequest var1_1, Camera.Parameters var2_2) {
        block34 : {
            block33 : {
                Preconditions.checkNotNull(var1_1, "captureRequest must not be null");
                var3_3 = CaptureRequest.CONTROL_AF_TRIGGER;
                var4_4 = 0;
                var5_5 = ParamsUtils.getOrDefault((CaptureRequest)var1_1, var3_3, 0);
                var1_1 = var2_2.getFocusMode();
                if (!Objects.equals(this.mAfModePrevious, var1_1)) {
                    var2_2 = this.mLock;
                    // MONITORENTER : var2_2
                    ++this.mAfRun;
                    this.mAfState = 0;
                    // MONITOREXIT : var2_2
                    this.mCamera.cancelAutoFocus();
                }
                this.mAfModePrevious = var1_1;
                var2_2 = this.mLock;
                // MONITORENTER : var2_2
                var6_6 = this.mAfRun;
                // MONITOREXIT : var2_2
                var2_2 = new Camera.AutoFocusMoveCallback((String)var1_1){
                    final /* synthetic */ String val$afMode;
                    {
                        this.val$afMode = string2;
                    }

                    /*
                     * Unable to fully structure code
                     * Enabled aggressive block sorting
                     * Enabled unnecessary exception pruning
                     * Enabled aggressive exception aggregation
                     * Converted monitor instructions to comments
                     * Lifted jumps to return sites
                     */
                    @Override
                    public void onAutoFocusMoving(boolean var1_1, Camera var2_2) {
                        block7 : {
                            block6 : {
                                var2_2 = LegacyFocusStateMapper.access$000(LegacyFocusStateMapper.this);
                                // MONITORENTER : var2_2
                                var3_3 = LegacyFocusStateMapper.access$100(LegacyFocusStateMapper.this);
                                if (var6_6 != var3_3) {
                                    var4_4 = LegacyFocusStateMapper.access$200();
                                    var5_6 = new StringBuilder();
                                    var5_6.append("onAutoFocusMoving - ignoring move callbacks from old af run");
                                    var5_6.append(var6_6);
                                    Log.d(var4_4, var5_6.toString());
                                    // MONITOREXIT : var2_2
                                    return;
                                }
                                var6_8 = var1_1 != false ? 1 : 2;
                                var5_7 = this.val$afMode;
                                var3_3 = var5_7.hashCode();
                                if (var3_3 == -194628547) break block6;
                                if (var3_3 != 910005312 || !var5_7.equals("continuous-picture")) ** GOTO lbl-1000
                                var3_3 = 0;
                                break block7;
                            }
                            if (var5_7.equals("continuous-video")) {
                                var3_3 = 1;
                            } else lbl-1000: // 2 sources:
                            {
                                var3_3 = -1;
                            }
                        }
                        if (var3_3 != 0 && var3_3 != 1) {
                            var5_7 = LegacyFocusStateMapper.access$200();
                            var4_5 = new StringBuilder();
                            var4_5.append("onAutoFocus - got unexpected onAutoFocus in mode ");
                            var4_5.append(this.val$afMode);
                            Log.w(var5_7, var4_5.toString());
                        }
                        LegacyFocusStateMapper.access$302(LegacyFocusStateMapper.this, var6_8);
                        // MONITOREXIT : var2_2
                    }
                };
                switch (var1_1.hashCode()) {
                    case 910005312: {
                        if (!var1_1.equals("continuous-picture")) break;
                        var6_6 = 2;
                        break block33;
                    }
                    case 103652300: {
                        if (!var1_1.equals("macro")) break;
                        var6_6 = 1;
                        break block33;
                    }
                    case 3005871: {
                        if (!var1_1.equals("auto")) break;
                        var6_6 = 0;
                        break block33;
                    }
                    case -194628547: {
                        if (!var1_1.equals("continuous-video")) break;
                        var6_6 = 3;
                        break block33;
                    }
                }
                ** break;
lbl38: // 1 sources:
                var6_6 = -1;
            }
            if (var6_6 == 0 || var6_6 == 1 || var6_6 == 2 || var6_6 == 3) {
                this.mCamera.setAutoFocusMoveCallback((Camera.AutoFocusMoveCallback)var2_2);
            }
            if (var5_5 == 0) return;
            if (var5_5 != 1) {
                if (var5_5 != 2) {
                    var2_2 = LegacyFocusStateMapper.TAG;
                    var1_1 = new StringBuilder();
                    var1_1.append("processRequestTriggers - ignoring unknown control.afTrigger = ");
                    var1_1.append(var5_5);
                    Log.w((String)var2_2, var1_1.toString());
                    return;
                }
                var1_1 = this.mLock;
                // MONITORENTER : var1_1
                var3_3 = this.mLock;
                // MONITORENTER : var3_3
                ++this.mAfRun;
                this.mAfState = 0;
                // MONITOREXIT : var3_3
                this.mCamera.cancelAutoFocus();
                // MONITOREXIT : var1_1
                return;
            }
            switch (var1_1.hashCode()) {
                case 910005312: {
                    if (!var1_1.equals("continuous-picture")) break;
                    var6_6 = 2;
                    break block34;
                }
                case 103652300: {
                    if (!var1_1.equals("macro")) break;
                    var6_6 = 1;
                    break block34;
                }
                case 3005871: {
                    if (!var1_1.equals("auto")) break;
                    var6_6 = var4_4;
                    break block34;
                }
                case -194628547: {
                    if (!var1_1.equals("continuous-video")) break;
                    var6_6 = 3;
                    break block34;
                }
            }
            ** break;
lbl82: // 1 sources:
            var6_6 = -1;
        }
        var6_6 = var6_6 != 0 && var6_6 != 1 ? (var6_6 != 2 && var6_6 != 3 ? 0 : 1) : 3;
        var2_2 = this.mLock;
        // MONITORENTER : var2_2
        this.mAfRun = var4_4 = this.mAfRun + 1;
        this.mAfState = var6_6;
        // MONITOREXIT : var2_2
        if (var6_6 == 0) {
            return;
        }
        this.mCamera.autoFocus(new Camera.AutoFocusCallback((String)var1_1){
            final /* synthetic */ String val$afMode;
            {
                this.val$afMode = string2;
            }

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Converted monitor instructions to comments
             * Lifted jumps to return sites
             */
            @Override
            public void onAutoFocus(boolean var1_1, Camera var2_2) {
                block10 : {
                    var2_2 = LegacyFocusStateMapper.access$000(LegacyFocusStateMapper.this);
                    // MONITORENTER : var2_2
                    var3_3 = LegacyFocusStateMapper.access$100(LegacyFocusStateMapper.this);
                    var4_4 = var4_4;
                    var5_5 = 0;
                    if (var3_3 != var4_4) {
                        Log.d(LegacyFocusStateMapper.access$200(), String.format("onAutoFocus - ignoring AF callback (old run %d, new run %d)", new Object[]{var4_4, var3_3}));
                        // MONITOREXIT : var2_2
                        return;
                    }
                    var3_3 = var1_1 != false ? 4 : 5;
                    var6_6 = this.val$afMode;
                    switch (var6_6.hashCode()) {
                        case 910005312: {
                            if (!var6_6.equals("continuous-picture")) break;
                            var5_5 = 1;
                            break block10;
                        }
                        case 103652300: {
                            if (!var6_6.equals("macro")) break;
                            var5_5 = 3;
                            break block10;
                        }
                        case 3005871: {
                            if (!var6_6.equals("auto")) break;
                            break block10;
                        }
                        case -194628547: {
                            if (!var6_6.equals("continuous-video")) break;
                            var5_5 = 2;
                            break block10;
                        }
                    }
                    ** break;
lbl30: // 1 sources:
                    var5_5 = -1;
                }
                if (var5_5 != 0 && var5_5 != 1 && var5_5 != 2 && var5_5 != 3) {
                    var7_7 = LegacyFocusStateMapper.access$200();
                    var6_6 = new StringBuilder();
                    var6_6.append("onAutoFocus - got unexpected onAutoFocus in mode ");
                    var6_6.append(this.val$afMode);
                    Log.w(var7_7, var6_6.toString());
                }
                LegacyFocusStateMapper.access$302(LegacyFocusStateMapper.this, var3_3);
                // MONITOREXIT : var2_2
            }
        });
    }

}

