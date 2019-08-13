/*
 * Decompiled with CFR 0.145.
 */
package android.provider;

import android.media.ExifInterface;
import android.os.BaseBundle;
import android.os.Bundle;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class MetadataReader {
    private static final String[] DEFAULT_EXIF_TAGS = new String[]{"FNumber", "Copyright", "DateTime", "ExposureTime", "FocalLength", "FNumber", "GPSLatitude", "GPSLatitudeRef", "GPSLongitude", "GPSLongitudeRef", "ImageLength", "ImageWidth", "ISOSpeedRatings", "Make", "Model", "Orientation", "ShutterSpeedValue"};
    private static final String JPEG_MIME_TYPE = "image/jpeg";
    private static final String JPG_MIME_TYPE = "image/jpg";
    private static final int TYPE_DOUBLE = 1;
    private static final int TYPE_INT = 0;
    private static final Map<String, Integer> TYPE_MAPPING = new HashMap<String, Integer>();
    private static final int TYPE_STRING = 2;

    static {
        Object object = TYPE_MAPPING;
        Integer n = 2;
        object.put((String)"Artist", (Integer)n);
        Map<String, Integer> map = TYPE_MAPPING;
        object = 0;
        map.put("BitsPerSample", (Integer)object);
        TYPE_MAPPING.put("Compression", (Integer)object);
        TYPE_MAPPING.put("Copyright", n);
        TYPE_MAPPING.put("DateTime", n);
        TYPE_MAPPING.put("ImageDescription", n);
        TYPE_MAPPING.put("ImageLength", (Integer)object);
        TYPE_MAPPING.put("ImageWidth", (Integer)object);
        TYPE_MAPPING.put("JPEGInterchangeFormat", (Integer)object);
        TYPE_MAPPING.put("JPEGInterchangeFormatLength", (Integer)object);
        TYPE_MAPPING.put("Make", n);
        TYPE_MAPPING.put("Model", n);
        TYPE_MAPPING.put("Orientation", (Integer)object);
        TYPE_MAPPING.put("PhotometricInterpretation", (Integer)object);
        TYPE_MAPPING.put("PlanarConfiguration", (Integer)object);
        map = TYPE_MAPPING;
        Integer n2 = 1;
        map.put("PrimaryChromaticities", n2);
        TYPE_MAPPING.put("ReferenceBlackWhite", n2);
        TYPE_MAPPING.put("ResolutionUnit", (Integer)object);
        TYPE_MAPPING.put("RowsPerStrip", (Integer)object);
        TYPE_MAPPING.put("SamplesPerPixel", (Integer)object);
        TYPE_MAPPING.put("Software", n);
        TYPE_MAPPING.put("StripByteCounts", (Integer)object);
        TYPE_MAPPING.put("StripOffsets", (Integer)object);
        TYPE_MAPPING.put("TransferFunction", (Integer)object);
        TYPE_MAPPING.put("WhitePoint", n2);
        TYPE_MAPPING.put("XResolution", n2);
        TYPE_MAPPING.put("YCbCrCoefficients", n2);
        TYPE_MAPPING.put("YCbCrPositioning", (Integer)object);
        TYPE_MAPPING.put("YCbCrSubSampling", (Integer)object);
        TYPE_MAPPING.put("YResolution", n2);
        TYPE_MAPPING.put("ApertureValue", n2);
        TYPE_MAPPING.put("BrightnessValue", n2);
        TYPE_MAPPING.put("CFAPattern", n);
        TYPE_MAPPING.put("ColorSpace", (Integer)object);
        TYPE_MAPPING.put("ComponentsConfiguration", n);
        TYPE_MAPPING.put("CompressedBitsPerPixel", n2);
        TYPE_MAPPING.put("Contrast", (Integer)object);
        TYPE_MAPPING.put("CustomRendered", (Integer)object);
        TYPE_MAPPING.put("DateTimeDigitized", n);
        TYPE_MAPPING.put("DateTimeOriginal", n);
        TYPE_MAPPING.put("DeviceSettingDescription", n);
        TYPE_MAPPING.put("DigitalZoomRatio", n2);
        TYPE_MAPPING.put("ExifVersion", n);
        TYPE_MAPPING.put("ExposureBiasValue", n2);
        TYPE_MAPPING.put("ExposureIndex", n2);
        TYPE_MAPPING.put("ExposureMode", (Integer)object);
        TYPE_MAPPING.put("ExposureProgram", (Integer)object);
        TYPE_MAPPING.put("ExposureTime", n2);
        TYPE_MAPPING.put("FNumber", n2);
        TYPE_MAPPING.put("FileSource", n);
        TYPE_MAPPING.put("Flash", (Integer)object);
        TYPE_MAPPING.put("FlashEnergy", n2);
        TYPE_MAPPING.put("FlashpixVersion", n);
        TYPE_MAPPING.put("FocalLength", n2);
        TYPE_MAPPING.put("FocalLengthIn35mmFilm", (Integer)object);
        TYPE_MAPPING.put("FocalPlaneResolutionUnit", (Integer)object);
        TYPE_MAPPING.put("FocalPlaneXResolution", n2);
        TYPE_MAPPING.put("FocalPlaneYResolution", n2);
        TYPE_MAPPING.put("GainControl", (Integer)object);
        TYPE_MAPPING.put("ISOSpeedRatings", (Integer)object);
        TYPE_MAPPING.put("ImageUniqueID", n);
        TYPE_MAPPING.put("LightSource", (Integer)object);
        TYPE_MAPPING.put("MakerNote", n);
        TYPE_MAPPING.put("MaxApertureValue", n2);
        TYPE_MAPPING.put("MeteringMode", (Integer)object);
        TYPE_MAPPING.put("NewSubfileType", (Integer)object);
        TYPE_MAPPING.put("OECF", n);
        TYPE_MAPPING.put("PixelXDimension", (Integer)object);
        TYPE_MAPPING.put("PixelYDimension", (Integer)object);
        TYPE_MAPPING.put("RelatedSoundFile", n);
        TYPE_MAPPING.put("Saturation", (Integer)object);
        TYPE_MAPPING.put("SceneCaptureType", (Integer)object);
        TYPE_MAPPING.put("SceneType", n);
        TYPE_MAPPING.put("SensingMethod", (Integer)object);
        TYPE_MAPPING.put("Sharpness", (Integer)object);
        TYPE_MAPPING.put("ShutterSpeedValue", n2);
        TYPE_MAPPING.put("SpatialFrequencyResponse", n);
        TYPE_MAPPING.put("SpectralSensitivity", n);
        TYPE_MAPPING.put("SubfileType", (Integer)object);
        TYPE_MAPPING.put("SubSecTime", n);
        TYPE_MAPPING.put("SubSecTimeDigitized", n);
        TYPE_MAPPING.put("SubSecTimeOriginal", n);
        TYPE_MAPPING.put("SubjectArea", (Integer)object);
        TYPE_MAPPING.put("SubjectDistance", n2);
        TYPE_MAPPING.put("SubjectDistanceRange", (Integer)object);
        TYPE_MAPPING.put("SubjectLocation", (Integer)object);
        TYPE_MAPPING.put("UserComment", n);
        TYPE_MAPPING.put("WhiteBalance", (Integer)object);
        TYPE_MAPPING.put("GPSAltitude", n2);
        TYPE_MAPPING.put("GPSAltitudeRef", (Integer)object);
        TYPE_MAPPING.put("GPSAreaInformation", n);
        TYPE_MAPPING.put("GPSDOP", n2);
        TYPE_MAPPING.put("GPSDateStamp", n);
        TYPE_MAPPING.put("GPSDestBearing", n2);
        TYPE_MAPPING.put("GPSDestBearingRef", n);
        TYPE_MAPPING.put("GPSDestDistance", n2);
        TYPE_MAPPING.put("GPSDestDistanceRef", n);
        TYPE_MAPPING.put("GPSDestLatitude", n2);
        TYPE_MAPPING.put("GPSDestLatitudeRef", n);
        TYPE_MAPPING.put("GPSDestLongitude", n2);
        TYPE_MAPPING.put("GPSDestLongitudeRef", n);
        TYPE_MAPPING.put("GPSDifferential", (Integer)object);
        TYPE_MAPPING.put("GPSImgDirection", n2);
        TYPE_MAPPING.put("GPSImgDirectionRef", n);
        TYPE_MAPPING.put("GPSLatitude", n);
        TYPE_MAPPING.put("GPSLatitudeRef", n);
        TYPE_MAPPING.put("GPSLongitude", n);
        TYPE_MAPPING.put("GPSLongitudeRef", n);
        TYPE_MAPPING.put("GPSMapDatum", n);
        TYPE_MAPPING.put("GPSMeasureMode", n);
        TYPE_MAPPING.put("GPSProcessingMethod", n);
        TYPE_MAPPING.put("GPSSatellites", n);
        TYPE_MAPPING.put("GPSSpeed", n2);
        TYPE_MAPPING.put("GPSSpeedRef", n);
        TYPE_MAPPING.put("GPSStatus", n);
        TYPE_MAPPING.put("GPSTimeStamp", n);
        TYPE_MAPPING.put("GPSTrack", n2);
        TYPE_MAPPING.put("GPSTrackRef", n);
        TYPE_MAPPING.put("GPSVersionID", n);
        TYPE_MAPPING.put("InteroperabilityIndex", n);
        TYPE_MAPPING.put("ThumbnailImageLength", (Integer)object);
        TYPE_MAPPING.put("ThumbnailImageWidth", (Integer)object);
        TYPE_MAPPING.put("DNGVersion", (Integer)object);
        TYPE_MAPPING.put("DefaultCropSize", (Integer)object);
        TYPE_MAPPING.put("PreviewImageStart", (Integer)object);
        TYPE_MAPPING.put("PreviewImageLength", (Integer)object);
        TYPE_MAPPING.put("AspectFrame", (Integer)object);
        TYPE_MAPPING.put("SensorBottomBorder", (Integer)object);
        TYPE_MAPPING.put("SensorLeftBorder", (Integer)object);
        TYPE_MAPPING.put("SensorRightBorder", (Integer)object);
        TYPE_MAPPING.put("SensorTopBorder", (Integer)object);
        TYPE_MAPPING.put("ISO", (Integer)object);
    }

    private MetadataReader() {
    }

    private static Bundle getExifData(InputStream object3, String[] object2) throws IOException {
        String string2;
        String[] arrstring = string2;
        if (string2 == null) {
            arrstring = DEFAULT_EXIF_TAGS;
        }
        ExifInterface exifInterface = new ExifInterface((InputStream)object3);
        Bundle bundle = new Bundle();
        for (String string3 : arrstring) {
            if (TYPE_MAPPING.get(string3).equals(0)) {
                int n = exifInterface.getAttributeInt(string3, Integer.MIN_VALUE);
                if (n == Integer.MIN_VALUE) continue;
                bundle.putInt(string3, n);
                continue;
            }
            if (TYPE_MAPPING.get(string3).equals(1)) {
                double d = exifInterface.getAttributeDouble(string3, Double.MIN_VALUE);
                if (d == Double.MIN_VALUE) continue;
                bundle.putDouble(string3, d);
                continue;
            }
            if (!TYPE_MAPPING.get(string3).equals(2) || (string2 = exifInterface.getAttribute(string3)) == null) continue;
            bundle.putString(string3, string2);
        }
        return bundle;
    }

    public static void getMetadata(Bundle bundle, InputStream object, String string2, String[] arrstring) throws IOException {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (MetadataReader.isSupportedMimeType(string2) && ((BaseBundle)(object = MetadataReader.getExifData((InputStream)object, arrstring))).size() > 0) {
            bundle.putBundle("android:documentExif", (Bundle)object);
            arrayList.add("android:documentExif");
        }
        bundle.putStringArray("android:documentMetadataTypes", arrayList.toArray(new String[arrayList.size()]));
    }

    public static boolean isSupportedMimeType(String string2) {
        boolean bl = JPG_MIME_TYPE.equals(string2) || JPEG_MIME_TYPE.equals(string2);
        return bl;
    }
}

