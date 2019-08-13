/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  libcore.io.IoUtils
 */
package android.content;

import android.annotation.UnsupportedAppUsage;
import android.content.ClipDescription;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.os.StrictMode;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.URLSpan;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.ArrayUtils;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import libcore.io.IoUtils;

public class ClipData
implements Parcelable {
    public static final Parcelable.Creator<ClipData> CREATOR;
    static final String[] MIMETYPES_TEXT_HTML;
    static final String[] MIMETYPES_TEXT_INTENT;
    static final String[] MIMETYPES_TEXT_PLAIN;
    static final String[] MIMETYPES_TEXT_URILIST;
    static final int PARCEL_MAX_SIZE_BYTES = 819200;
    static final int PARCEL_TYPE_PFD = 1;
    static final int PARCEL_TYPE_STRING = 0;
    final ClipDescription mClipDescription;
    final Bitmap mIcon;
    final ArrayList<Item> mItems;

    static {
        MIMETYPES_TEXT_PLAIN = new String[]{"text/plain"};
        MIMETYPES_TEXT_HTML = new String[]{"text/html"};
        MIMETYPES_TEXT_URILIST = new String[]{"text/uri-list"};
        MIMETYPES_TEXT_INTENT = new String[]{"text/vnd.android.intent"};
        CREATOR = new Parcelable.Creator<ClipData>(){

            @Override
            public ClipData createFromParcel(Parcel parcel) {
                return new ClipData(parcel);
            }

            public ClipData[] newArray(int n) {
                return new ClipData[n];
            }
        };
    }

    public ClipData(ClipData clipData) {
        this.mClipDescription = clipData.mClipDescription;
        this.mIcon = clipData.mIcon;
        this.mItems = new ArrayList<Item>(clipData.mItems);
    }

    public ClipData(ClipDescription clipDescription, Item item) {
        this.mClipDescription = clipDescription;
        if (item != null) {
            this.mIcon = null;
            this.mItems = new ArrayList();
            this.mItems.add(item);
            return;
        }
        throw new NullPointerException("item is null");
    }

    public ClipData(ClipDescription clipDescription, ArrayList<Item> arrayList) {
        this.mClipDescription = clipDescription;
        if (arrayList != null) {
            this.mIcon = null;
            this.mItems = arrayList;
            return;
        }
        throw new NullPointerException("item is null");
    }

    ClipData(Parcel parcel) {
        this.mClipDescription = new ClipDescription(parcel);
        this.mIcon = parcel.readInt() != 0 ? Bitmap.CREATOR.createFromParcel(parcel) : null;
        this.mItems = new ArrayList();
        int n = parcel.readInt();
        for (int i = 0; i < n; ++i) {
            CharSequence charSequence = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            String string2 = ClipData.readHtmlTextFromParcel(parcel);
            Intent intent = parcel.readInt() != 0 ? Intent.CREATOR.createFromParcel(parcel) : null;
            Uri uri = parcel.readInt() != 0 ? Uri.CREATOR.createFromParcel(parcel) : null;
            this.mItems.add(new Item(charSequence, string2, intent, uri));
        }
    }

    public ClipData(CharSequence charSequence, String[] arrstring, Item item) {
        this.mClipDescription = new ClipDescription(charSequence, arrstring);
        if (item != null) {
            this.mIcon = null;
            this.mItems = new ArrayList();
            this.mItems.add(item);
            return;
        }
        throw new NullPointerException("item is null");
    }

    private static String[] getMimeTypes(ContentResolver arrstring, Uri uri) {
        String[] arrstring2 = null;
        if ("content".equals(uri.getScheme())) {
            String string2 = arrstring.getType(uri);
            arrstring2 = arrstring = arrstring.getStreamTypes(uri, "*/*");
            if (string2 != null) {
                if (arrstring == null) {
                    arrstring2 = new String[]{string2};
                } else {
                    arrstring2 = arrstring;
                    if (!ArrayUtils.contains(arrstring, string2)) {
                        arrstring2 = new String[arrstring.length + 1];
                        arrstring2[0] = string2;
                        System.arraycopy(arrstring, 0, arrstring2, 1, arrstring.length);
                    }
                }
            }
        }
        arrstring = arrstring2;
        if (arrstring2 == null) {
            arrstring = MIMETYPES_TEXT_URILIST;
        }
        return arrstring;
    }

    public static ClipData newHtmlText(CharSequence charSequence, CharSequence object, String string2) {
        object = new Item((CharSequence)object, string2);
        return new ClipData(charSequence, MIMETYPES_TEXT_HTML, (Item)object);
    }

    public static ClipData newIntent(CharSequence charSequence, Intent object) {
        object = new Item((Intent)object);
        return new ClipData(charSequence, MIMETYPES_TEXT_INTENT, (Item)object);
    }

    public static ClipData newPlainText(CharSequence charSequence, CharSequence object) {
        object = new Item((CharSequence)object);
        return new ClipData(charSequence, MIMETYPES_TEXT_PLAIN, (Item)object);
    }

    public static ClipData newRawUri(CharSequence charSequence, Uri object) {
        object = new Item((Uri)object);
        return new ClipData(charSequence, MIMETYPES_TEXT_URILIST, (Item)object);
    }

    public static ClipData newUri(ContentResolver contentResolver, CharSequence charSequence, Uri uri) {
        Item item = new Item(uri);
        return new ClipData(charSequence, ClipData.getMimeTypes(contentResolver, uri), item);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static String readHtmlTextFromParcel(Parcel object) {
        Throwable throwable2222;
        int n;
        if (((Parcel)object).readInt() == 0) {
            return ((Parcel)object).readString();
        }
        if ((object = (ParcelFileDescriptor)((Parcel)object).readParcelable(ParcelFileDescriptor.class.getClassLoader())) == null) throw new IllegalStateException("Error reading ParcelFileDescriptor from Parcel");
        object = new ParcelFileDescriptor.AutoCloseInputStream((ParcelFileDescriptor)object);
        Object object2 = new InputStreamReader((InputStream)object);
        CharSequence charSequence = new StringBuilder();
        char[] arrc = new char[4096];
        while ((n = ((Reader)object2).read(arrc)) != -1) {
            ((StringBuilder)charSequence).append(arrc, 0, n);
        }
        charSequence = ((StringBuilder)charSequence).toString();
        IoUtils.closeQuietly((AutoCloseable)object);
        return charSequence;
        {
            catch (Throwable throwable2222) {
            }
            catch (IOException iOException) {}
            {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("Error reading data from ParcelFileDescriptor: ");
                ((StringBuilder)charSequence).append(iOException.toString());
                object2 = new IllegalStateException(((StringBuilder)charSequence).toString());
                throw object2;
            }
        }
        IoUtils.closeQuietly((AutoCloseable)object);
        throw throwable2222;
    }

    private static void writeHtmlTextToParcel(String object, Parcel parcel, int n) {
        byte[] arrby = object != null ? ((String)object).getBytes() : new byte[0];
        if (arrby.length > 409600 && Build.VERSION.SDK_INT <= 29) {
            try {
                object = ParcelFileDescriptor.fromData(arrby, null);
                parcel.writeInt(1);
                parcel.writeParcelable((Parcelable)object, n);
            }
            catch (IOException iOException) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Error creating the shared memory area: ");
                ((StringBuilder)object).append(iOException.toString());
                throw new IllegalStateException(((StringBuilder)object).toString());
            }
        } else {
            parcel.writeInt(0);
            parcel.writeString((String)object);
        }
    }

    public void addItem(Item item) {
        if (item != null) {
            this.mItems.add(item);
            return;
        }
        throw new NullPointerException("item is null");
    }

    @Deprecated
    public void addItem(Item item, ContentResolver contentResolver) {
        this.addItem(contentResolver, item);
    }

    public void addItem(ContentResolver contentResolver, Item item) {
        this.addItem(item);
        if (item.getHtmlText() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_HTML);
        } else if (item.getText() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_PLAIN);
        }
        if (item.getIntent() != null) {
            this.mClipDescription.addMimeTypes(MIMETYPES_TEXT_INTENT);
        }
        if (item.getUri() != null) {
            this.mClipDescription.addMimeTypes(ClipData.getMimeTypes(contentResolver, item.getUri()));
        }
    }

    public void collectUris(List<Uri> list) {
        for (int i = 0; i < this.mItems.size(); ++i) {
            Object object = this.getItemAt(i);
            if (((Item)object).getUri() != null) {
                list.add(((Item)object).getUri());
            }
            if ((object = ((Item)object).getIntent()) == null) continue;
            if (((Intent)object).getData() != null) {
                list.add(((Intent)object).getData());
            }
            if (((Intent)object).getClipData() == null) continue;
            ((Intent)object).getClipData().collectUris(list);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void fixUris(int n) {
        int n2 = this.mItems.size();
        for (int i = 0; i < n2; ++i) {
            Item item = this.mItems.get(i);
            if (item.mIntent != null) {
                item.mIntent.fixUris(n);
            }
            if (item.mUri == null) continue;
            item.mUri = ContentProvider.maybeAddUserId(item.mUri, n);
        }
    }

    public void fixUrisLight(int n) {
        int n2 = this.mItems.size();
        for (int i = 0; i < n2; ++i) {
            Uri uri;
            Item item = this.mItems.get(i);
            if (item.mIntent != null && (uri = item.mIntent.getData()) != null) {
                item.mIntent.setData(ContentProvider.maybeAddUserId(uri, n));
            }
            if (item.mUri == null) continue;
            item.mUri = ContentProvider.maybeAddUserId(item.mUri, n);
        }
    }

    public ClipDescription getDescription() {
        return this.mClipDescription;
    }

    @UnsupportedAppUsage
    public Bitmap getIcon() {
        return this.mIcon;
    }

    public Item getItemAt(int n) {
        return this.mItems.get(n);
    }

    public int getItemCount() {
        return this.mItems.size();
    }

    public void prepareToEnterProcess() {
        int n = this.mItems.size();
        for (int i = 0; i < n; ++i) {
            Item item = this.mItems.get(i);
            if (item.mIntent == null) continue;
            item.mIntent.prepareToEnterProcess();
        }
    }

    public void prepareToLeaveProcess(boolean bl) {
        this.prepareToLeaveProcess(bl, 1);
    }

    public void prepareToLeaveProcess(boolean bl, int n) {
        int n2 = this.mItems.size();
        for (int i = 0; i < n2; ++i) {
            Item item = this.mItems.get(i);
            if (item.mIntent != null) {
                item.mIntent.prepareToLeaveProcess(bl);
            }
            if (item.mUri == null || !bl) continue;
            if (StrictMode.vmFileUriExposureEnabled()) {
                item.mUri.checkFileUriExposed("ClipData.Item.getUri()");
            }
            if (!StrictMode.vmContentUriWithoutPermissionEnabled()) continue;
            item.mUri.checkContentUriWithoutPermission("ClipData.Item.getUri()", n);
        }
    }

    public void setItemAt(int n, Item item) {
        this.mItems.set(n, item);
    }

    public void toShortString(StringBuilder stringBuilder) {
        ClipDescription clipDescription = this.mClipDescription;
        int n = clipDescription != null ? clipDescription.toShortString(stringBuilder) ^ true : 1;
        int n2 = n;
        if (this.mIcon != null) {
            if (n == 0) {
                stringBuilder.append(' ');
            }
            n2 = 0;
            stringBuilder.append("I:");
            stringBuilder.append(this.mIcon.getWidth());
            stringBuilder.append('x');
            stringBuilder.append(this.mIcon.getHeight());
        }
        for (n = 0; n < this.mItems.size(); ++n) {
            if (n2 == 0) {
                stringBuilder.append(' ');
            }
            n2 = 0;
            stringBuilder.append('{');
            this.mItems.get(n).toShortString(stringBuilder);
            stringBuilder.append('}');
        }
    }

    public void toShortStringShortItems(StringBuilder stringBuilder, boolean bl) {
        if (this.mItems.size() > 0) {
            if (!bl) {
                stringBuilder.append(' ');
            }
            this.mItems.get(0).toShortString(stringBuilder);
            if (this.mItems.size() > 1) {
                stringBuilder.append(" ...");
            }
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("ClipData { ");
        this.toShortString(stringBuilder);
        stringBuilder.append(" }");
        return stringBuilder.toString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        this.mClipDescription.writeToParcel(parcel, n);
        if (this.mIcon != null) {
            parcel.writeInt(1);
            this.mIcon.writeToParcel(parcel, n);
        } else {
            parcel.writeInt(0);
        }
        int n2 = this.mItems.size();
        parcel.writeInt(n2);
        for (int i = 0; i < n2; ++i) {
            Item item = this.mItems.get(i);
            TextUtils.writeToParcel(item.mText, parcel, n);
            ClipData.writeHtmlTextToParcel(item.mHtmlText, parcel, n);
            if (item.mIntent != null) {
                parcel.writeInt(1);
                item.mIntent.writeToParcel(parcel, n);
            } else {
                parcel.writeInt(0);
            }
            if (item.mUri != null) {
                parcel.writeInt(1);
                item.mUri.writeToParcel(parcel, n);
                continue;
            }
            parcel.writeInt(0);
        }
    }

    public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
        long l2 = protoOutputStream.start(l);
        ClipDescription clipDescription = this.mClipDescription;
        if (clipDescription != null) {
            clipDescription.writeToProto(protoOutputStream, 1146756268033L);
        }
        if (this.mIcon != null) {
            l = protoOutputStream.start(1146756268034L);
            protoOutputStream.write(1120986464257L, this.mIcon.getWidth());
            protoOutputStream.write(1120986464258L, this.mIcon.getHeight());
            protoOutputStream.end(l);
        }
        for (int i = 0; i < this.mItems.size(); ++i) {
            this.mItems.get(i).writeToProto(protoOutputStream, 2246267895811L);
        }
        protoOutputStream.end(l2);
    }

    public static class Item {
        final String mHtmlText;
        final Intent mIntent;
        final CharSequence mText;
        @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
        Uri mUri;

        public Item(Item item) {
            this.mText = item.mText;
            this.mHtmlText = item.mHtmlText;
            this.mIntent = item.mIntent;
            this.mUri = item.mUri;
        }

        public Item(Intent intent) {
            this.mText = null;
            this.mHtmlText = null;
            this.mIntent = intent;
            this.mUri = null;
        }

        public Item(Uri uri) {
            this.mText = null;
            this.mHtmlText = null;
            this.mIntent = null;
            this.mUri = uri;
        }

        public Item(CharSequence charSequence) {
            this.mText = charSequence;
            this.mHtmlText = null;
            this.mIntent = null;
            this.mUri = null;
        }

        public Item(CharSequence charSequence, Intent intent, Uri uri) {
            this.mText = charSequence;
            this.mHtmlText = null;
            this.mIntent = intent;
            this.mUri = uri;
        }

        public Item(CharSequence charSequence, String string2) {
            this.mText = charSequence;
            this.mHtmlText = string2;
            this.mIntent = null;
            this.mUri = null;
        }

        public Item(CharSequence charSequence, String string2, Intent intent, Uri uri) {
            if (string2 != null && charSequence == null) {
                throw new IllegalArgumentException("Plain text must be supplied if HTML text is supplied");
            }
            this.mText = charSequence;
            this.mHtmlText = string2;
            this.mIntent = intent;
            this.mUri = uri;
        }

        /*
         * Exception decompiling
         */
        private CharSequence coerceToHtmlOrStyledText(Context var1_1, boolean var2_14) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
            // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:778)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:886)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
            // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
            // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
            // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
            // org.benf.cfr.reader.Main.main(Main.java:48)
            throw new IllegalStateException("Decompilation failed");
        }

        private String uriToHtml(String string2) {
            StringBuilder stringBuilder = new StringBuilder(256);
            stringBuilder.append("<a href=\"");
            stringBuilder.append(Html.escapeHtml(string2));
            stringBuilder.append("\">");
            stringBuilder.append(Html.escapeHtml(string2));
            stringBuilder.append("</a>");
            return stringBuilder.toString();
        }

        private CharSequence uriToStyledText(String string2) {
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            spannableStringBuilder.append(string2);
            spannableStringBuilder.setSpan(new URLSpan(string2), 0, spannableStringBuilder.length(), 33);
            return spannableStringBuilder;
        }

        public String coerceToHtmlText(Context object) {
            CharSequence charSequence = this.getHtmlText();
            if (charSequence != null) {
                return charSequence;
            }
            charSequence = this.getText();
            if (charSequence != null) {
                if (charSequence instanceof Spanned) {
                    return Html.toHtml((Spanned)charSequence);
                }
                return Html.escapeHtml(charSequence);
            }
            object = (object = this.coerceToHtmlOrStyledText((Context)object, false)) != null ? object.toString() : null;
            return object;
        }

        public CharSequence coerceToStyledText(Context context) {
            CharSequence charSequence = this.getText();
            if (charSequence instanceof Spanned) {
                return charSequence;
            }
            CharSequence charSequence2 = this.getHtmlText();
            if (charSequence2 != null) {
                try {
                    charSequence2 = Html.fromHtml(charSequence2);
                    if (charSequence2 != null) {
                        return charSequence2;
                    }
                }
                catch (RuntimeException runtimeException) {
                    // empty catch block
                }
            }
            if (charSequence != null) {
                return charSequence;
            }
            return this.coerceToHtmlOrStyledText(context, true);
        }

        /*
         * Loose catch block
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public CharSequence coerceToText(Context object) {
            char[] arrc;
            Throwable throwable2;
            Object object2;
            block12 : {
                object2 = this.getText();
                if (object2 != null) {
                    return object2;
                }
                Uri uri = this.getUri();
                if (uri == null) {
                    object = this.getIntent();
                    if (object == null) return "";
                    return ((Intent)object).toUri(1);
                }
                char[] arrc2 = ((Context)object).getContentResolver();
                char[] arrc3 = null;
                char[] arrc4 = null;
                char[] arrc5 = null;
                char[] arrc6 = null;
                Object object3 = null;
                char[] arrc7 = null;
                arrc = arrc4;
                object = arrc6;
                object2 = arrc7;
                try {
                    arrc3 = arrc2 = arrc2.openTypedAssetFileDescriptor(uri, "text/*", null);
                }
                catch (FileNotFoundException | RuntimeException exception) {
                }
                catch (SecurityException securityException) {
                    arrc = arrc4;
                    object = arrc6;
                    object2 = arrc7;
                    Log.w("ClipData", "Failure opening stream", securityException);
                }
                if (arrc3 != null) {
                    arrc = arrc3;
                    object = arrc6;
                    object2 = arrc7;
                    arrc6 = arrc5;
                    arrc5 = object3;
                    try {
                        arrc4 = arrc3.createInputStream();
                        arrc = arrc3;
                        object = arrc4;
                        object2 = arrc7;
                        arrc6 = arrc4;
                        arrc5 = object3;
                        arrc = arrc3;
                        object = arrc4;
                        object2 = arrc7;
                        arrc6 = arrc4;
                        arrc5 = object3;
                        arrc7 = arrc2 = new InputStreamReader((InputStream)arrc4, "UTF-8");
                        arrc = arrc3;
                        object = arrc4;
                        object2 = arrc7;
                        arrc6 = arrc4;
                        arrc5 = arrc7;
                        arrc = arrc3;
                        object = arrc4;
                        object2 = arrc7;
                        arrc6 = arrc4;
                        arrc5 = arrc7;
                        object3 = new StringBuilder(128);
                        arrc = arrc3;
                        object = arrc4;
                        object2 = arrc7;
                        arrc6 = arrc4;
                        arrc5 = arrc7;
                        arrc2 = new char[8192];
                        do {
                            arrc = arrc3;
                            object = arrc4;
                            object2 = arrc7;
                            arrc6 = arrc4;
                            arrc5 = arrc7;
                            int n = arrc7.read(arrc2);
                            if (n <= 0) break;
                            arrc = arrc3;
                            object = arrc4;
                            object2 = arrc7;
                            arrc6 = arrc4;
                            arrc5 = arrc7;
                            object3.append(arrc2, 0, n);
                        } while (true);
                        arrc = arrc3;
                        object = arrc4;
                        object2 = arrc7;
                        arrc6 = arrc4;
                        arrc5 = arrc7;
                        object3 = object3.toString();
                    }
                    catch (IOException iOException) {
                        arrc = arrc3;
                        object = arrc6;
                        object2 = arrc5;
                        Log.w("ClipData", "Failure loading text", iOException);
                        arrc = arrc3;
                        object = arrc6;
                        object2 = arrc5;
                        String string2 = iOException.toString();
                        IoUtils.closeQuietly((AutoCloseable)arrc3);
                        IoUtils.closeQuietly((AutoCloseable)arrc6);
                        IoUtils.closeQuietly((AutoCloseable)arrc5);
                        return string2;
                    }
                    IoUtils.closeQuietly((AutoCloseable)arrc3);
                    IoUtils.closeQuietly((AutoCloseable)arrc4);
                    IoUtils.closeQuietly((AutoCloseable)arrc7);
                    return object3;
                }
                {
                    catch (Throwable throwable2) {
                        break block12;
                    }
                }
                IoUtils.closeQuietly(arrc3);
                IoUtils.closeQuietly(null);
                IoUtils.closeQuietly(null);
                object = uri.getScheme();
                if ("content".equals(object)) return "";
                if ("android.resource".equals(object)) return "";
                if (!"file".equals(object)) return uri.toString();
                return "";
            }
            IoUtils.closeQuietly(arrc);
            IoUtils.closeQuietly((AutoCloseable)object);
            IoUtils.closeQuietly((AutoCloseable)object2);
            throw throwable2;
        }

        public String getHtmlText() {
            return this.mHtmlText;
        }

        public Intent getIntent() {
            return this.mIntent;
        }

        public CharSequence getText() {
            return this.mText;
        }

        public Uri getUri() {
            return this.mUri;
        }

        public void toShortString(StringBuilder stringBuilder) {
            if (this.mHtmlText != null) {
                stringBuilder.append("H:");
                stringBuilder.append(this.mHtmlText);
            } else if (this.mText != null) {
                stringBuilder.append("T:");
                stringBuilder.append(this.mText);
            } else if (this.mUri != null) {
                stringBuilder.append("U:");
                stringBuilder.append(this.mUri);
            } else if (this.mIntent != null) {
                stringBuilder.append("I:");
                this.mIntent.toShortString(stringBuilder, true, true, true, true);
            } else {
                stringBuilder.append("NULL");
            }
        }

        public void toShortSummaryString(StringBuilder stringBuilder) {
            if (this.mHtmlText != null) {
                stringBuilder.append("HTML");
            } else if (this.mText != null) {
                stringBuilder.append("TEXT");
            } else if (this.mUri != null) {
                stringBuilder.append("U:");
                stringBuilder.append(this.mUri);
            } else if (this.mIntent != null) {
                stringBuilder.append("I:");
                this.mIntent.toShortString(stringBuilder, true, true, true, true);
            } else {
                stringBuilder.append("NULL");
            }
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("ClipData.Item { ");
            this.toShortString(stringBuilder);
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }

        public void writeToProto(ProtoOutputStream protoOutputStream, long l) {
            l = protoOutputStream.start(l);
            Object object = this.mHtmlText;
            if (object != null) {
                protoOutputStream.write(1138166333441L, (String)object);
            } else {
                object = this.mText;
                if (object != null) {
                    protoOutputStream.write(1138166333442L, object.toString());
                } else {
                    object = this.mUri;
                    if (object != null) {
                        protoOutputStream.write(1138166333443L, ((Uri)object).toString());
                    } else {
                        object = this.mIntent;
                        if (object != null) {
                            ((Intent)object).writeToProto(protoOutputStream, 1146756268036L, true, true, true, true);
                        } else {
                            protoOutputStream.write(1133871366149L, true);
                        }
                    }
                }
            }
            protoOutputStream.end(l);
        }
    }

}

