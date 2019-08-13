/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.view;

import android.annotation.UnsupportedAppUsage;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.inputmethod.CompletionInfo;
import android.view.inputmethod.CorrectionInfo;
import android.view.inputmethod.ExtractedText;
import android.view.inputmethod.ExtractedTextRequest;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionInspector;
import android.view.inputmethod.InputContentInfo;
import com.android.internal.annotations.GuardedBy;
import com.android.internal.os.SomeArgs;
import com.android.internal.view.IInputContext;
import com.android.internal.view.IInputContextCallback;

public abstract class IInputConnectionWrapper
extends IInputContext.Stub {
    private static final boolean DEBUG = false;
    private static final int DO_BEGIN_BATCH_EDIT = 90;
    private static final int DO_CLEAR_META_KEY_STATES = 130;
    private static final int DO_CLOSE_CONNECTION = 150;
    private static final int DO_COMMIT_COMPLETION = 55;
    private static final int DO_COMMIT_CONTENT = 160;
    private static final int DO_COMMIT_CORRECTION = 56;
    private static final int DO_COMMIT_TEXT = 50;
    private static final int DO_DELETE_SURROUNDING_TEXT = 80;
    private static final int DO_DELETE_SURROUNDING_TEXT_IN_CODE_POINTS = 81;
    private static final int DO_END_BATCH_EDIT = 95;
    private static final int DO_FINISH_COMPOSING_TEXT = 65;
    private static final int DO_GET_CURSOR_CAPS_MODE = 30;
    private static final int DO_GET_EXTRACTED_TEXT = 40;
    private static final int DO_GET_SELECTED_TEXT = 25;
    private static final int DO_GET_TEXT_AFTER_CURSOR = 10;
    private static final int DO_GET_TEXT_BEFORE_CURSOR = 20;
    private static final int DO_PERFORM_CONTEXT_MENU_ACTION = 59;
    private static final int DO_PERFORM_EDITOR_ACTION = 58;
    private static final int DO_PERFORM_PRIVATE_COMMAND = 120;
    private static final int DO_REQUEST_UPDATE_CURSOR_ANCHOR_INFO = 140;
    private static final int DO_SEND_KEY_EVENT = 70;
    private static final int DO_SET_COMPOSING_REGION = 63;
    private static final int DO_SET_COMPOSING_TEXT = 60;
    private static final int DO_SET_SELECTION = 57;
    private static final String TAG = "IInputConnectionWrapper";
    @GuardedBy(value={"mLock"})
    private boolean mFinished = false;
    private Handler mH;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    @GuardedBy(value={"mLock"})
    private InputConnection mInputConnection;
    @UnsupportedAppUsage
    private Object mLock = new Object();
    private Looper mMainLooper;

    public IInputConnectionWrapper(Looper looper, InputConnection inputConnection) {
        this.mInputConnection = inputConnection;
        this.mMainLooper = looper;
        this.mH = new MyHandler(this.mMainLooper);
    }

    @Override
    public void beginBatchEdit() {
        this.dispatchMessage(this.obtainMessage(90));
    }

    @Override
    public void clearMetaKeyStates(int n) {
        this.dispatchMessage(this.obtainMessageII(130, n, 0));
    }

    public void closeConnection() {
        this.dispatchMessage(this.obtainMessage(150));
    }

    @Override
    public void commitCompletion(CompletionInfo completionInfo) {
        this.dispatchMessage(this.obtainMessageO(55, completionInfo));
    }

    @Override
    public void commitContent(InputContentInfo inputContentInfo, int n, Bundle bundle, int n2, IInputContextCallback iInputContextCallback) {
        this.dispatchMessage(this.obtainMessageIOOSC(160, n, inputContentInfo, bundle, n2, iInputContextCallback));
    }

    @Override
    public void commitCorrection(CorrectionInfo correctionInfo) {
        this.dispatchMessage(this.obtainMessageO(56, correctionInfo));
    }

    @Override
    public void commitText(CharSequence charSequence, int n) {
        this.dispatchMessage(this.obtainMessageIO(50, n, charSequence));
    }

    @Override
    public void deleteSurroundingText(int n, int n2) {
        this.dispatchMessage(this.obtainMessageII(80, n, n2));
    }

    @Override
    public void deleteSurroundingTextInCodePoints(int n, int n2) {
        this.dispatchMessage(this.obtainMessageII(81, n, n2));
    }

    void dispatchMessage(Message message) {
        if (Looper.myLooper() == this.mMainLooper) {
            this.executeMessage(message);
            message.recycle();
            return;
        }
        this.mH.sendMessage(message);
    }

    @Override
    public void endBatchEdit() {
        this.dispatchMessage(this.obtainMessage(95));
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void executeMessage(Message var1_1) {
        block108 : {
            block107 : {
                block101 : {
                    block106 : {
                        block100 : {
                            block105 : {
                                block99 : {
                                    block104 : {
                                        block98 : {
                                            block103 : {
                                                block97 : {
                                                    block102 : {
                                                        block96 : {
                                                            block109 : {
                                                                block110 : {
                                                                    var2_14 = var1_1.what;
                                                                    if (var2_14 == 80) break block109;
                                                                    if (var2_14 == 81) break block110;
                                                                    switch (var2_14) {
                                                                        default: {
                                                                            switch (var2_14) {
                                                                                default: {
                                                                                    var3_15 = new StringBuilder();
                                                                                    var3_15.append("Unhandled message code: ");
                                                                                    var3_15.append(var1_1.what);
                                                                                    Log.w("IInputConnectionWrapper", var3_15.toString());
                                                                                    return;
                                                                                }
                                                                                case 60: {
                                                                                    var3_16 = this.getInputConnection();
                                                                                    if (var3_16 != null && this.isActive()) {
                                                                                        var3_16.setComposingText((CharSequence)var1_1.obj, var1_1.arg1);
                                                                                        return;
                                                                                    }
                                                                                    Log.w("IInputConnectionWrapper", "setComposingText on inactive InputConnection");
                                                                                    return;
                                                                                }
                                                                                case 59: {
                                                                                    var3_17 = this.getInputConnection();
                                                                                    if (var3_17 != null && this.isActive()) {
                                                                                        var3_17.performContextMenuAction(var1_1.arg1);
                                                                                        return;
                                                                                    }
                                                                                    Log.w("IInputConnectionWrapper", "performContextMenuAction on inactive InputConnection");
                                                                                    return;
                                                                                }
                                                                                case 58: {
                                                                                    var3_18 = this.getInputConnection();
                                                                                    if (var3_18 != null && this.isActive()) {
                                                                                        var3_18.performEditorAction(var1_1.arg1);
                                                                                        return;
                                                                                    }
                                                                                    Log.w("IInputConnectionWrapper", "performEditorAction on inactive InputConnection");
                                                                                    return;
                                                                                }
                                                                                case 57: {
                                                                                    var3_19 = this.getInputConnection();
                                                                                    if (var3_19 != null && this.isActive()) {
                                                                                        var3_19.setSelection(var1_1.arg1, var1_1.arg2);
                                                                                        return;
                                                                                    }
                                                                                    Log.w("IInputConnectionWrapper", "setSelection on inactive InputConnection");
                                                                                    return;
                                                                                }
                                                                                case 56: {
                                                                                    var3_20 = this.getInputConnection();
                                                                                    if (var3_20 != null && this.isActive()) {
                                                                                        var3_20.commitCorrection((CorrectionInfo)var1_1.obj);
                                                                                        return;
                                                                                    }
                                                                                    Log.w("IInputConnectionWrapper", "commitCorrection on inactive InputConnection");
                                                                                    return;
                                                                                }
                                                                                case 55: 
                                                                            }
                                                                            var3_21 = this.getInputConnection();
                                                                            if (var3_21 != null && this.isActive()) {
                                                                                var3_21.commitCompletion((CompletionInfo)var1_1.obj);
                                                                                return;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "commitCompletion on inactive InputConnection");
                                                                            return;
                                                                        }
                                                                        case 160: {
                                                                            var4_41 = var1_1.arg1;
                                                                            var1_1 = (SomeArgs)var1_1.obj;
                                                                            var3_22 = (IInputContextCallback)var1_1.arg6;
                                                                            var2_14 = var1_1.argi6;
                                                                            var5_42 = this.getInputConnection();
                                                                            if (var5_42 != null && this.isActive()) {
                                                                                var6_50 = (InputContentInfo)var1_1.arg1;
                                                                                if (var6_50 != null && var6_50.validate()) {
                                                                                    var3_22.setCommitContentResult(var5_42.commitContent(var6_50, var4_41, (Bundle)var1_1.arg2), var2_14);
                                                                                    break block96;
                                                                                }
                                                                                var5_42 = new StringBuilder();
                                                                                var5_42.append("commitContent with invalid inputContentInfo=");
                                                                                var5_42.append(var6_50);
                                                                                Log.w("IInputConnectionWrapper", var5_42.toString());
                                                                                var3_22.setCommitContentResult(false, var2_14);
                                                                                var1_1.recycle();
                                                                                return;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "commitContent on inactive InputConnection");
                                                                            var3_22.setCommitContentResult(false, var2_14);
                                                                            var1_1.recycle();
                                                                            return;
                                                                        }
                                                                        case 150: {
                                                                            if (this.isFinished()) {
                                                                                return;
                                                                            }
                                                                            var1_1 = this.getInputConnection();
                                                                            if (var1_1 != null) ** GOTO lbl112
                                                                            var1_1 = this.mLock;
                                                                            this.mInputConnection = null;
                                                                            this.mFinished = true;
                                                                            // MONITOREXIT : var1_1
                                                                            return;
lbl112: // 2 sources:
                                                                            if ((InputConnectionInspector.getMissingMethodFlags((InputConnection)var1_1) & 64) != 0) return;
                                                                            var1_1.closeConnection();
                                                                            return;
                                                                            finally {
                                                                                var3_25 = this.mLock;
                                                                                // MONITORENTER : var3_25
                                                                                this.mInputConnection = null;
                                                                                this.mFinished = true;
                                                                                // MONITOREXIT : var3_25
                                                                            }
                                                                        }
                                                                        case 140: {
                                                                            var3_27 = (SomeArgs)var1_1.obj;
                                                                            var5_43 = (IInputContextCallback)var3_27.arg6;
                                                                            var2_14 = var3_27.argi6;
                                                                            var6_51 = this.getInputConnection();
                                                                            if (var6_51 != null && this.isActive()) {
                                                                                var5_43.setRequestUpdateCursorAnchorInfoResult(var6_51.requestCursorUpdates(var1_1.arg1), var2_14);
                                                                                break block97;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "requestCursorAnchorInfo on inactive InputConnection");
                                                                            var5_43.setRequestUpdateCursorAnchorInfoResult(false, var2_14);
                                                                            var3_27.recycle();
                                                                            return;
                                                                        }
                                                                        case 130: {
                                                                            var3_28 = this.getInputConnection();
                                                                            if (var3_28 != null && this.isActive()) {
                                                                                var3_28.clearMetaKeyStates(var1_1.arg1);
                                                                                return;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "clearMetaKeyStates on inactive InputConnection");
                                                                            return;
                                                                        }
                                                                        case 120: {
                                                                            var1_1 = (SomeArgs)var1_1.obj;
                                                                            try {
                                                                                var3_29 = (String)var1_1.arg1;
                                                                                var5_44 = (Bundle)var1_1.arg2;
                                                                                var6_52 = this.getInputConnection();
                                                                                if (var6_52 != null && this.isActive()) {
                                                                                    var6_52.performPrivateCommand(var3_29, var5_44);
                                                                                    return;
                                                                                }
                                                                                Log.w("IInputConnectionWrapper", "performPrivateCommand on inactive InputConnection");
                                                                                return;
                                                                            }
                                                                            finally {
                                                                                var1_1.recycle();
                                                                            }
                                                                        }
                                                                        case 95: {
                                                                            var1_1 = this.getInputConnection();
                                                                            if (var1_1 != null && this.isActive()) {
                                                                                var1_1.endBatchEdit();
                                                                                return;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "endBatchEdit on inactive InputConnection");
                                                                            return;
                                                                        }
                                                                        case 90: {
                                                                            var1_1 = this.getInputConnection();
                                                                            if (var1_1 != null && this.isActive()) {
                                                                                var1_1.beginBatchEdit();
                                                                                return;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "beginBatchEdit on inactive InputConnection");
                                                                            return;
                                                                        }
                                                                        case 70: {
                                                                            var3_31 = this.getInputConnection();
                                                                            if (var3_31 != null && this.isActive()) {
                                                                                var3_31.sendKeyEvent((KeyEvent)var1_1.obj);
                                                                                return;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "sendKeyEvent on inactive InputConnection");
                                                                            return;
                                                                        }
                                                                        case 65: {
                                                                            if (this.isFinished()) {
                                                                                return;
                                                                            }
                                                                            var1_1 = this.getInputConnection();
                                                                            if (var1_1 == null) {
                                                                                Log.w("IInputConnectionWrapper", "finishComposingText on inactive InputConnection");
                                                                                return;
                                                                            }
                                                                            var1_1.finishComposingText();
                                                                            return;
                                                                        }
                                                                        case 63: {
                                                                            var3_32 = this.getInputConnection();
                                                                            if (var3_32 != null && this.isActive()) {
                                                                                var3_32.setComposingRegion(var1_1.arg1, var1_1.arg2);
                                                                                return;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "setComposingRegion on inactive InputConnection");
                                                                            return;
                                                                        }
                                                                        case 50: {
                                                                            var3_33 = this.getInputConnection();
                                                                            if (var3_33 != null && this.isActive()) {
                                                                                var3_33.commitText((CharSequence)var1_1.obj, var1_1.arg1);
                                                                                return;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "commitText on inactive InputConnection");
                                                                            return;
                                                                        }
                                                                        case 40: {
                                                                            var3_34 = (SomeArgs)var1_1.obj;
                                                                            var6_53 = (IInputContextCallback)var3_34.arg6;
                                                                            var2_14 = var3_34.argi6;
                                                                            var5_45 = this.getInputConnection();
                                                                            if (var5_45 != null && this.isActive()) {
                                                                                var6_53.setExtractedText(var5_45.getExtractedText((ExtractedTextRequest)var3_34.arg1, var1_1.arg1), var2_14);
                                                                                break block98;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "getExtractedText on inactive InputConnection");
                                                                            var6_53.setExtractedText(null, var2_14);
                                                                            var3_34.recycle();
                                                                            return;
                                                                        }
                                                                        case 30: {
                                                                            var3_35 = (SomeArgs)var1_1.obj;
                                                                            var5_46 = (IInputContextCallback)var3_35.arg6;
                                                                            var2_14 = var3_35.argi6;
                                                                            var6_54 = this.getInputConnection();
                                                                            if (var6_54 != null && this.isActive()) {
                                                                                var5_46.setCursorCapsMode(var6_54.getCursorCapsMode(var1_1.arg1), var2_14);
                                                                                break block99;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "getCursorCapsMode on inactive InputConnection");
                                                                            var5_46.setCursorCapsMode(0, var2_14);
                                                                            var3_35.recycle();
                                                                            return;
                                                                        }
                                                                        case 25: {
                                                                            var3_36 = (SomeArgs)var1_1.obj;
                                                                            var5_47 = (IInputContextCallback)var3_36.arg6;
                                                                            var2_14 = var3_36.argi6;
                                                                            var6_55 = this.getInputConnection();
                                                                            if (var6_55 != null && this.isActive()) {
                                                                                var5_47.setSelectedText(var6_55.getSelectedText(var1_1.arg1), var2_14);
                                                                                break block100;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "getSelectedText on inactive InputConnection");
                                                                            var5_47.setSelectedText(null, var2_14);
                                                                            var3_36.recycle();
                                                                            return;
                                                                        }
                                                                        case 20: {
                                                                            var3_37 = (SomeArgs)var1_1.obj;
                                                                            var5_48 = (IInputContextCallback)var3_37.arg6;
                                                                            var2_14 = var3_37.argi6;
                                                                            var6_56 = this.getInputConnection();
                                                                            if (var6_56 != null && this.isActive()) {
                                                                                var5_48.setTextBeforeCursor(var6_56.getTextBeforeCursor(var1_1.arg1, var1_1.arg2), var2_14);
                                                                                break block101;
                                                                            }
                                                                            Log.w("IInputConnectionWrapper", "getTextBeforeCursor on inactive InputConnection");
                                                                            var5_48.setTextBeforeCursor(null, var2_14);
                                                                            var3_37.recycle();
                                                                            return;
                                                                        }
                                                                        case 10: {
                                                                            var3_38 = (SomeArgs)var1_1.obj;
                                                                            var5_49 = (IInputContextCallback)var3_38.arg6;
                                                                            var2_14 = var3_38.argi6;
                                                                            var6_57 = this.getInputConnection();
                                                                            if (var6_57 == null || !this.isActive()) {
                                                                                Log.w("IInputConnectionWrapper", "getTextAfterCursor on inactive InputConnection");
                                                                                var5_49.setTextAfterCursor(null, var2_14);
                                                                                var3_38.recycle();
                                                                                return;
                                                                            }
                                                                            var5_49.setTextAfterCursor(var6_57.getTextAfterCursor(var1_1.arg1, var1_1.arg2), var2_14);
                                                                        }
                                                                    }
                                                                }
                                                                var3_39 = this.getInputConnection();
                                                                if (var3_39 != null && this.isActive()) {
                                                                    var3_39.deleteSurroundingTextInCodePoints(var1_1.arg1, var1_1.arg2);
                                                                    return;
                                                                }
                                                                Log.w("IInputConnectionWrapper", "deleteSurroundingTextInCodePoints on inactive InputConnection");
                                                                return;
                                                            }
                                                            var3_40 = this.getInputConnection();
                                                            if (var3_40 != null && this.isActive()) {
                                                                var3_40.deleteSurroundingText(var1_1.arg1, var1_1.arg2);
                                                                return;
                                                            }
                                                            Log.w("IInputConnectionWrapper", "deleteSurroundingText on inactive InputConnection");
                                                            return;
                                                            {
                                                                catch (Throwable var3_23) {
                                                                    break block102;
                                                                }
                                                                catch (RemoteException var3_24) {}
                                                                {
                                                                    Log.w("IInputConnectionWrapper", "Got RemoteException calling commitContent", var3_24);
                                                                }
                                                            }
                                                        }
                                                        var1_1.recycle();
                                                        return;
                                                    }
                                                    var1_1.recycle();
                                                    throw var3_23;
                                                    {
                                                        catch (Throwable var1_2) {
                                                            break block103;
                                                        }
                                                        catch (RemoteException var1_3) {}
                                                        {
                                                            Log.w("IInputConnectionWrapper", "Got RemoteException calling requestCursorAnchorInfo", var1_3);
                                                        }
                                                    }
                                                }
                                                var3_27.recycle();
                                                return;
                                            }
                                            var3_27.recycle();
                                            throw var1_2;
                                            {
                                                catch (Throwable var1_4) {
                                                    break block104;
                                                }
                                                catch (RemoteException var1_5) {}
                                                {
                                                    Log.w("IInputConnectionWrapper", "Got RemoteException calling setExtractedText", var1_5);
                                                }
                                            }
                                        }
                                        var3_34.recycle();
                                        return;
                                    }
                                    var3_34.recycle();
                                    throw var1_4;
                                    {
                                        catch (Throwable var1_6) {
                                            break block105;
                                        }
                                        catch (RemoteException var1_7) {}
                                        {
                                            Log.w("IInputConnectionWrapper", "Got RemoteException calling setCursorCapsMode", var1_7);
                                        }
                                    }
                                }
                                var3_35.recycle();
                                return;
                            }
                            var3_35.recycle();
                            throw var1_6;
                            {
                                catch (Throwable var1_8) {
                                    break block106;
                                }
                                catch (RemoteException var1_9) {}
                                {
                                    Log.w("IInputConnectionWrapper", "Got RemoteException calling setSelectedText", var1_9);
                                }
                            }
                        }
                        var3_36.recycle();
                        return;
                    }
                    var3_36.recycle();
                    throw var1_8;
                    {
                        catch (Throwable var1_10) {
                            break block107;
                        }
                        catch (RemoteException var1_11) {}
                        {
                            Log.w("IInputConnectionWrapper", "Got RemoteException calling setTextBeforeCursor", var1_11);
                        }
                    }
                }
                var3_37.recycle();
                return;
            }
            var3_37.recycle();
            throw var1_10;
            {
                catch (Throwable var1_12) {
                    break block108;
                }
                catch (RemoteException var1_13) {}
                {
                    Log.w("IInputConnectionWrapper", "Got RemoteException calling setTextAfterCursor", var1_13);
                }
            }
            var3_38.recycle();
            return;
        }
        var3_38.recycle();
        throw var1_12;
    }

    @Override
    public void finishComposingText() {
        this.dispatchMessage(this.obtainMessage(65));
    }

    @Override
    public void getCursorCapsMode(int n, int n2, IInputContextCallback iInputContextCallback) {
        this.dispatchMessage(this.obtainMessageISC(30, n, n2, iInputContextCallback));
    }

    @Override
    public void getExtractedText(ExtractedTextRequest extractedTextRequest, int n, int n2, IInputContextCallback iInputContextCallback) {
        this.dispatchMessage(this.obtainMessageIOSC(40, n, extractedTextRequest, n2, iInputContextCallback));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public InputConnection getInputConnection() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mInputConnection;
        }
    }

    @Override
    public void getSelectedText(int n, int n2, IInputContextCallback iInputContextCallback) {
        this.dispatchMessage(this.obtainMessageISC(25, n, n2, iInputContextCallback));
    }

    @Override
    public void getTextAfterCursor(int n, int n2, int n3, IInputContextCallback iInputContextCallback) {
        this.dispatchMessage(this.obtainMessageIISC(10, n, n2, n3, iInputContextCallback));
    }

    @Override
    public void getTextBeforeCursor(int n, int n2, int n3, IInputContextCallback iInputContextCallback) {
        this.dispatchMessage(this.obtainMessageIISC(20, n, n2, n3, iInputContextCallback));
    }

    protected abstract boolean isActive();

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected boolean isFinished() {
        Object object = this.mLock;
        synchronized (object) {
            return this.mFinished;
        }
    }

    Message obtainMessage(int n) {
        return this.mH.obtainMessage(n);
    }

    Message obtainMessageII(int n, int n2, int n3) {
        return this.mH.obtainMessage(n, n2, n3);
    }

    Message obtainMessageIISC(int n, int n2, int n3, int n4, IInputContextCallback iInputContextCallback) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg6 = iInputContextCallback;
        someArgs.argi6 = n4;
        return this.mH.obtainMessage(n, n2, n3, someArgs);
    }

    Message obtainMessageIO(int n, int n2, Object object) {
        return this.mH.obtainMessage(n, n2, 0, object);
    }

    Message obtainMessageIOOSC(int n, int n2, Object object, Object object2, int n3, IInputContextCallback iInputContextCallback) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        someArgs.arg6 = iInputContextCallback;
        someArgs.argi6 = n3;
        return this.mH.obtainMessage(n, n2, 0, someArgs);
    }

    Message obtainMessageIOSC(int n, int n2, Object object, int n3, IInputContextCallback iInputContextCallback) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg6 = iInputContextCallback;
        someArgs.argi6 = n3;
        return this.mH.obtainMessage(n, n2, 0, someArgs);
    }

    Message obtainMessageISC(int n, int n2, int n3, IInputContextCallback iInputContextCallback) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg6 = iInputContextCallback;
        someArgs.argi6 = n3;
        return this.mH.obtainMessage(n, n2, 0, someArgs);
    }

    Message obtainMessageO(int n, Object object) {
        return this.mH.obtainMessage(n, 0, 0, object);
    }

    Message obtainMessageOO(int n, Object object, Object object2) {
        SomeArgs someArgs = SomeArgs.obtain();
        someArgs.arg1 = object;
        someArgs.arg2 = object2;
        return this.mH.obtainMessage(n, 0, 0, someArgs);
    }

    @Override
    public void performContextMenuAction(int n) {
        this.dispatchMessage(this.obtainMessageII(59, n, 0));
    }

    @Override
    public void performEditorAction(int n) {
        this.dispatchMessage(this.obtainMessageII(58, n, 0));
    }

    @Override
    public void performPrivateCommand(String string2, Bundle bundle) {
        this.dispatchMessage(this.obtainMessageOO(120, string2, bundle));
    }

    @Override
    public void requestUpdateCursorAnchorInfo(int n, int n2, IInputContextCallback iInputContextCallback) {
        this.dispatchMessage(this.obtainMessageISC(140, n, n2, iInputContextCallback));
    }

    @Override
    public void sendKeyEvent(KeyEvent keyEvent) {
        this.dispatchMessage(this.obtainMessageO(70, keyEvent));
    }

    @Override
    public void setComposingRegion(int n, int n2) {
        this.dispatchMessage(this.obtainMessageII(63, n, n2));
    }

    @Override
    public void setComposingText(CharSequence charSequence, int n) {
        this.dispatchMessage(this.obtainMessageIO(60, n, charSequence));
    }

    @Override
    public void setSelection(int n, int n2) {
        this.dispatchMessage(this.obtainMessageII(57, n, n2));
    }

    class MyHandler
    extends Handler {
        MyHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message message) {
            IInputConnectionWrapper.this.executeMessage(message);
        }
    }

}

