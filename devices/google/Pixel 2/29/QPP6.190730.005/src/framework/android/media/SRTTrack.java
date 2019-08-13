/*
 * Decompiled with CFR 0.145.
 */
package android.media;

import android.media.MediaFormat;
import android.media.SubtitleData;
import android.media.SubtitleTrack;
import android.media.TextTrackCue;
import android.media.TextTrackCueSpan;
import android.media.WebVttRenderingWidget;
import android.media.WebVttTrack;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.util.Log;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

class SRTTrack
extends WebVttTrack {
    private static final int KEY_LOCAL_SETTING = 102;
    private static final int KEY_START_TIME = 7;
    private static final int KEY_STRUCT_TEXT = 16;
    private static final int MEDIA_TIMED_TEXT = 99;
    private static final String TAG = "SRTTrack";
    private final Handler mEventHandler;

    SRTTrack(WebVttRenderingWidget webVttRenderingWidget, MediaFormat mediaFormat) {
        super(webVttRenderingWidget, mediaFormat);
        this.mEventHandler = null;
    }

    SRTTrack(Handler handler, MediaFormat mediaFormat) {
        super(null, mediaFormat);
        this.mEventHandler = handler;
    }

    private static long parseMs(String string2) {
        return Long.parseLong(string2.split(":")[0].trim()) * 60L * 60L * 1000L + 60L * Long.parseLong(string2.split(":")[1].trim()) * 1000L + 1000L * Long.parseLong(string2.split(":")[2].split(",")[0].trim()) + Long.parseLong(string2.split(":")[2].split(",")[1].trim());
    }

    @Override
    protected void onData(SubtitleData arrstring) {
        TextTrackCue textTrackCue = new TextTrackCue();
        textTrackCue.mStartTimeMs = arrstring.getStartTimeUs() / 1000L;
        textTrackCue.mEndTimeMs = (arrstring.getStartTimeUs() + arrstring.getDurationUs()) / 1000L;
        String string2 = new String(arrstring.getData(), "UTF-8");
        arrstring = string2.split("\\r?\\n");
        textTrackCue.mLines = new TextTrackCueSpan[arrstring.length][];
        int n = arrstring.length;
        int n2 = 0;
        int n3 = 0;
        while (n3 < n) {
            string2 = arrstring[n3];
            TextTrackCueSpan textTrackCueSpan = new TextTrackCueSpan(string2, -1L);
            textTrackCue.mLines[n2] = new TextTrackCueSpan[]{textTrackCueSpan};
            ++n3;
            ++n2;
        }
        try {
            this.addCue(textTrackCue);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            arrstring = new StringBuilder();
            arrstring.append("subtitle data is not UTF-8 encoded: ");
            arrstring.append(unsupportedEncodingException);
            Log.w(TAG, arrstring.toString());
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
    @Override
    public void onData(byte[] object, boolean bl, long l) {
        void var1_9;
        Object object2;
        block14 : {
            void var1_7;
            block13 : {
                Object object3;
                try {
                    object3 = new ByteArrayInputStream((byte[])object);
                    object2 = new InputStreamReader((InputStream)object3, "UTF-8");
                    object = new BufferedReader((Reader)object2);
                }
                catch (IOException iOException) {
                    // empty catch block
                    break block13;
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    // empty catch block
                    break block14;
                }
                while (((BufferedReader)object).readLine() != null) {
                    String string2;
                    object3 = ((BufferedReader)object).readLine();
                    if (object3 == null) {
                        return;
                    }
                    object2 = new TextTrackCue();
                    object3 = ((String)object3).split("-->");
                    ((TextTrackCue)object2).mStartTimeMs = SRTTrack.parseMs(object3[0]);
                    ((TextTrackCue)object2).mEndTimeMs = SRTTrack.parseMs(object3[1]);
                    ((TextTrackCue)object2).mRunID = l;
                    object3 = new ArrayList();
                    while ((string2 = ((BufferedReader)object).readLine()) != null && !string2.trim().equals("")) {
                        object3.add(string2);
                    }
                    int n = 0;
                    ((TextTrackCue)object2).mLines = new TextTrackCueSpan[object3.size()][];
                    ((TextTrackCue)object2).mStrings = object3.toArray(new String[0]);
                    object3 = object3.iterator();
                    while (object3.hasNext()) {
                        string2 = (String)object3.next();
                        TextTrackCueSpan textTrackCueSpan = new TextTrackCueSpan(string2, -1L);
                        object2.mStrings[n] = string2;
                        object2.mLines[n] = new TextTrackCueSpan[]{textTrackCueSpan};
                        ++n;
                    }
                    try {
                        this.addCue((SubtitleTrack.Cue)object2);
                    }
                    catch (IOException iOException) {
                        break block13;
                    }
                    catch (UnsupportedEncodingException unsupportedEncodingException) {
                        break block14;
                    }
                }
                return;
                catch (IOException iOException) {
                }
                catch (UnsupportedEncodingException unsupportedEncodingException) {
                    break block14;
                }
            }
            Log.e(TAG, var1_7.getMessage(), (Throwable)var1_7);
            return;
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("subtitle data is not UTF-8 encoded: ");
        ((StringBuilder)object2).append(var1_9);
        Log.w(TAG, ((StringBuilder)object2).toString());
    }

    @Override
    public void updateView(Vector<SubtitleTrack.Cue> vector) {
        if (this.getRenderingWidget() != null) {
            super.updateView(vector);
            return;
        }
        if (this.mEventHandler == null) {
            return;
        }
        for (SubtitleTrack.Cue cue : vector) {
            Object[] arrobject = (String[])cue;
            Object object = Parcel.obtain();
            ((Parcel)object).writeInt(102);
            ((Parcel)object).writeInt(7);
            ((Parcel)object).writeInt((int)cue.mStartTimeMs);
            ((Parcel)object).writeInt(16);
            StringBuilder object2 = new StringBuilder();
            arrobject = arrobject.mStrings;
            int n = arrobject.length;
            for (int i = 0; i < n; ++i) {
                object2.append(arrobject[i]);
                object2.append('\n');
            }
            arrobject = object2.toString().getBytes();
            ((Parcel)object).writeInt(arrobject.length);
            ((Parcel)object).writeByteArray((byte[])arrobject);
            object = this.mEventHandler.obtainMessage(99, 0, 0, object);
            this.mEventHandler.sendMessage((Message)object);
        }
        vector.clear();
    }
}

