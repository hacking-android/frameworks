/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.print;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentInfo;
import android.print.PrintJobId;
import android.print.PrintJobInfo;
import android.print.PrinterCapabilitiesInfo;
import android.print.PrinterId;
import android.print.PrinterInfo;
import com.android.internal.util.dump.DualDumpOutputStream;
import java.util.List;

public class DumpUtils {
    public static void writeMargins(DualDumpOutputStream dualDumpOutputStream, String string2, long l, PrintAttributes.Margins margins) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("top_mils", 1120986464257L, margins.getTopMils());
        dualDumpOutputStream.write("left_mils", 1120986464258L, margins.getLeftMils());
        dualDumpOutputStream.write("right_mils", 1120986464259L, margins.getRightMils());
        dualDumpOutputStream.write("bottom_mils", 1120986464260L, margins.getBottomMils());
        dualDumpOutputStream.end(l);
    }

    public static void writeMediaSize(Context context, DualDumpOutputStream dualDumpOutputStream, String string2, long l, PrintAttributes.MediaSize mediaSize) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("id", 1138166333441L, mediaSize.getId());
        dualDumpOutputStream.write("label", 1138166333442L, mediaSize.getLabel(context.getPackageManager()));
        dualDumpOutputStream.write("height_mils", 1120986464259L, mediaSize.getHeightMils());
        dualDumpOutputStream.write("width_mils", 1120986464260L, mediaSize.getWidthMils());
        dualDumpOutputStream.end(l);
    }

    public static void writePageRange(DualDumpOutputStream dualDumpOutputStream, String string2, long l, PageRange pageRange) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("start", 1120986464257L, pageRange.getStart());
        dualDumpOutputStream.write("end", 1120986464258L, pageRange.getEnd());
        dualDumpOutputStream.end(l);
    }

    public static void writePrintAttributes(Context object, DualDumpOutputStream dualDumpOutputStream, String object2, long l, PrintAttributes printAttributes) {
        l = dualDumpOutputStream.start((String)object2, l);
        object2 = printAttributes.getMediaSize();
        if (object2 != null) {
            DumpUtils.writeMediaSize((Context)object, dualDumpOutputStream, "media_size", 1146756268033L, (PrintAttributes.MediaSize)object2);
            dualDumpOutputStream.write("is_portrait", 1133871366146L, printAttributes.isPortrait());
        }
        if ((object = printAttributes.getResolution()) != null) {
            DumpUtils.writeResolution(dualDumpOutputStream, "resolution", 1146756268035L, (PrintAttributes.Resolution)object);
        }
        if ((object = printAttributes.getMinMargins()) != null) {
            DumpUtils.writeMargins(dualDumpOutputStream, "min_margings", 1146756268036L, (PrintAttributes.Margins)object);
        }
        dualDumpOutputStream.write("color_mode", 1159641169925L, printAttributes.getColorMode());
        dualDumpOutputStream.write("duplex_mode", 1159641169926L, printAttributes.getDuplexMode());
        dualDumpOutputStream.end(l);
    }

    public static void writePrintDocumentInfo(DualDumpOutputStream dualDumpOutputStream, String string2, long l, PrintDocumentInfo printDocumentInfo) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("name", 1138166333441L, printDocumentInfo.getName());
        int n = printDocumentInfo.getPageCount();
        if (n != -1) {
            dualDumpOutputStream.write("page_count", 1120986464258L, n);
        }
        dualDumpOutputStream.write("content_type", 1120986464259L, printDocumentInfo.getContentType());
        dualDumpOutputStream.write("data_size", 1112396529668L, printDocumentInfo.getDataSize());
        dualDumpOutputStream.end(l);
    }

    public static void writePrintJobInfo(Context object, DualDumpOutputStream dualDumpOutputStream, String arrpageRange, long l, PrintJobInfo printJobInfo) {
        l = dualDumpOutputStream.start((String)arrpageRange, l);
        dualDumpOutputStream.write("label", 1138166333441L, printJobInfo.getLabel());
        arrpageRange = printJobInfo.getId();
        if (arrpageRange != null) {
            dualDumpOutputStream.write("print_job_id", 1138166333442L, arrpageRange.flattenToString());
        }
        int n = printJobInfo.getState();
        boolean bl = true;
        if (n >= 1 && n <= 7) {
            dualDumpOutputStream.write("state", 1159641169923L, n);
        } else {
            dualDumpOutputStream.write("state", 1159641169923L, 0);
        }
        arrpageRange = printJobInfo.getPrinterId();
        if (arrpageRange != null) {
            DumpUtils.writePrinterId(dualDumpOutputStream, "printer", 1146756268036L, (PrinterId)arrpageRange);
        }
        if ((arrpageRange = printJobInfo.getTag()) != null) {
            dualDumpOutputStream.write("tag", 1138166333445L, (String)arrpageRange);
        }
        dualDumpOutputStream.write("creation_time", 1112396529670L, printJobInfo.getCreationTime());
        arrpageRange = printJobInfo.getAttributes();
        if (arrpageRange != null) {
            DumpUtils.writePrintAttributes((Context)object, dualDumpOutputStream, "attributes", 1146756268039L, (PrintAttributes)arrpageRange);
        }
        if ((arrpageRange = printJobInfo.getDocumentInfo()) != null) {
            DumpUtils.writePrintDocumentInfo(dualDumpOutputStream, "document_info", 1146756268040L, (PrintDocumentInfo)arrpageRange);
        }
        dualDumpOutputStream.write("is_canceling", 1133871366153L, printJobInfo.isCancelling());
        arrpageRange = printJobInfo.getPages();
        if (arrpageRange != null) {
            for (n = 0; n < arrpageRange.length; ++n) {
                DumpUtils.writePageRange(dualDumpOutputStream, "pages", 2246267895818L, arrpageRange[n]);
            }
        }
        if (printJobInfo.getAdvancedOptions() == null) {
            bl = false;
        }
        dualDumpOutputStream.write("has_advanced_options", 1133871366155L, bl);
        dualDumpOutputStream.write("progress", 1108101562380L, printJobInfo.getProgress());
        object = printJobInfo.getStatus(((Context)object).getPackageManager());
        if (object != null) {
            dualDumpOutputStream.write("status", 1138166333453L, object.toString());
        }
        dualDumpOutputStream.end(l);
    }

    public static void writePrinterCapabilities(Context context, DualDumpOutputStream dualDumpOutputStream, String string2, long l, PrinterCapabilitiesInfo printerCapabilitiesInfo) {
        int n;
        l = dualDumpOutputStream.start(string2, l);
        DumpUtils.writeMargins(dualDumpOutputStream, "min_margins", 1146756268033L, printerCapabilitiesInfo.getMinMargins());
        int n2 = printerCapabilitiesInfo.getMediaSizes().size();
        for (n = 0; n < n2; ++n) {
            DumpUtils.writeMediaSize(context, dualDumpOutputStream, "media_sizes", 2246267895810L, printerCapabilitiesInfo.getMediaSizes().get(n));
        }
        n2 = printerCapabilitiesInfo.getResolutions().size();
        for (n = 0; n < n2; ++n) {
            DumpUtils.writeResolution(dualDumpOutputStream, "resolutions", 2246267895811L, printerCapabilitiesInfo.getResolutions().get(n));
        }
        if ((printerCapabilitiesInfo.getColorModes() & 1) != 0) {
            dualDumpOutputStream.write("color_modes", 2259152797700L, 1);
        }
        if ((printerCapabilitiesInfo.getColorModes() & 2) != 0) {
            dualDumpOutputStream.write("color_modes", 2259152797700L, 2);
        }
        if ((printerCapabilitiesInfo.getDuplexModes() & 1) != 0) {
            dualDumpOutputStream.write("duplex_modes", 2259152797701L, 1);
        }
        if ((printerCapabilitiesInfo.getDuplexModes() & 2) != 0) {
            dualDumpOutputStream.write("duplex_modes", 2259152797701L, 2);
        }
        if ((printerCapabilitiesInfo.getDuplexModes() & 4) != 0) {
            dualDumpOutputStream.write("duplex_modes", 2259152797701L, 4);
        }
        dualDumpOutputStream.end(l);
    }

    public static void writePrinterId(DualDumpOutputStream dualDumpOutputStream, String string2, long l, PrinterId printerId) {
        l = dualDumpOutputStream.start(string2, l);
        com.android.internal.util.dump.DumpUtils.writeComponentName(dualDumpOutputStream, "service_name", 1146756268033L, printerId.getServiceName());
        dualDumpOutputStream.write("local_id", 1138166333442L, printerId.getLocalId());
        dualDumpOutputStream.end(l);
    }

    public static void writePrinterInfo(Context context, DualDumpOutputStream dualDumpOutputStream, String object, long l, PrinterInfo printerInfo) {
        l = dualDumpOutputStream.start((String)object, l);
        DumpUtils.writePrinterId(dualDumpOutputStream, "id", 1146756268033L, printerInfo.getId());
        dualDumpOutputStream.write("name", 1138166333442L, printerInfo.getName());
        dualDumpOutputStream.write("status", 1159641169923L, printerInfo.getStatus());
        dualDumpOutputStream.write("description", 1138166333444L, printerInfo.getDescription());
        object = printerInfo.getCapabilities();
        if (object != null) {
            DumpUtils.writePrinterCapabilities(context, dualDumpOutputStream, "capabilities", 1146756268037L, (PrinterCapabilitiesInfo)object);
        }
        dualDumpOutputStream.end(l);
    }

    public static void writeResolution(DualDumpOutputStream dualDumpOutputStream, String string2, long l, PrintAttributes.Resolution resolution) {
        l = dualDumpOutputStream.start(string2, l);
        dualDumpOutputStream.write("id", 1138166333441L, resolution.getId());
        dualDumpOutputStream.write("label", 1138166333442L, resolution.getLabel());
        dualDumpOutputStream.write("horizontal_DPI", 1120986464259L, resolution.getHorizontalDpi());
        dualDumpOutputStream.write("veritical_DPI", 1120986464260L, resolution.getVerticalDpi());
        dualDumpOutputStream.end(l);
    }
}

