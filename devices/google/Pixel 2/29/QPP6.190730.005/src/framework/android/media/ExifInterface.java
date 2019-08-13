/*
 * Decompiled with CFR 0.145.
 * 
 * Could not load the following classes:
 *  android.system.ErrnoException
 *  android.system.Os
 *  android.system.OsConstants
 *  libcore.io.IoUtils
 *  libcore.io.Streams
 */
package android.media;

import android.annotation.UnsupportedAppUsage;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaDataSource;
import android.media.MediaMetadataRetriever;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.util.Log;
import android.util.Pair;
import com.android.internal.util.ArrayUtils;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import libcore.io.IoUtils;
import libcore.io.Streams;

public class ExifInterface {
    private static final Charset ASCII;
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_1;
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_2;
    private static final int[] BITS_PER_SAMPLE_RGB;
    private static final short BYTE_ALIGN_II = 18761;
    private static final short BYTE_ALIGN_MM = 19789;
    private static final int DATA_DEFLATE_ZIP = 8;
    private static final int DATA_HUFFMAN_COMPRESSED = 2;
    private static final int DATA_JPEG = 6;
    private static final int DATA_JPEG_COMPRESSED = 7;
    private static final int DATA_LOSSY_JPEG = 34892;
    private static final int DATA_PACK_BITS_COMPRESSED = 32773;
    private static final int DATA_UNCOMPRESSED = 1;
    private static final boolean DEBUG;
    private static final byte[] EXIF_ASCII_PREFIX;
    private static final ExifTag[] EXIF_POINTER_TAGS;
    private static final ExifTag[][] EXIF_TAGS;
    private static final byte[] HEIF_BRAND_HEIC;
    private static final byte[] HEIF_BRAND_MIF1;
    private static final byte[] HEIF_TYPE_FTYP;
    private static final byte[] IDENTIFIER_EXIF_APP1;
    private static final byte[] IDENTIFIER_XMP_APP1;
    private static final ExifTag[] IFD_EXIF_TAGS;
    private static final int IFD_FORMAT_BYTE = 1;
    private static final int[] IFD_FORMAT_BYTES_PER_FORMAT;
    private static final int IFD_FORMAT_DOUBLE = 12;
    private static final int IFD_FORMAT_IFD = 13;
    private static final String[] IFD_FORMAT_NAMES;
    private static final int IFD_FORMAT_SBYTE = 6;
    private static final int IFD_FORMAT_SINGLE = 11;
    private static final int IFD_FORMAT_SLONG = 9;
    private static final int IFD_FORMAT_SRATIONAL = 10;
    private static final int IFD_FORMAT_SSHORT = 8;
    private static final int IFD_FORMAT_STRING = 2;
    private static final int IFD_FORMAT_ULONG = 4;
    private static final int IFD_FORMAT_UNDEFINED = 7;
    private static final int IFD_FORMAT_URATIONAL = 5;
    private static final int IFD_FORMAT_USHORT = 3;
    private static final ExifTag[] IFD_GPS_TAGS;
    private static final ExifTag[] IFD_INTEROPERABILITY_TAGS;
    private static final int IFD_OFFSET = 8;
    private static final ExifTag[] IFD_THUMBNAIL_TAGS;
    private static final ExifTag[] IFD_TIFF_TAGS;
    private static final int IFD_TYPE_EXIF = 1;
    private static final int IFD_TYPE_GPS = 2;
    private static final int IFD_TYPE_INTEROPERABILITY = 3;
    private static final int IFD_TYPE_ORF_CAMERA_SETTINGS = 7;
    private static final int IFD_TYPE_ORF_IMAGE_PROCESSING = 8;
    private static final int IFD_TYPE_ORF_MAKER_NOTE = 6;
    private static final int IFD_TYPE_PEF = 9;
    private static final int IFD_TYPE_PREVIEW = 5;
    private static final int IFD_TYPE_PRIMARY = 0;
    private static final int IFD_TYPE_THUMBNAIL = 4;
    private static final int IMAGE_TYPE_ARW = 1;
    private static final int IMAGE_TYPE_CR2 = 2;
    private static final int IMAGE_TYPE_DNG = 3;
    private static final int IMAGE_TYPE_HEIF = 12;
    private static final int IMAGE_TYPE_JPEG = 4;
    private static final int IMAGE_TYPE_NEF = 5;
    private static final int IMAGE_TYPE_NRW = 6;
    private static final int IMAGE_TYPE_ORF = 7;
    private static final int IMAGE_TYPE_PEF = 8;
    private static final int IMAGE_TYPE_RAF = 9;
    private static final int IMAGE_TYPE_RW2 = 10;
    private static final int IMAGE_TYPE_SRW = 11;
    private static final int IMAGE_TYPE_UNKNOWN = 0;
    private static final ExifTag JPEG_INTERCHANGE_FORMAT_LENGTH_TAG;
    private static final ExifTag JPEG_INTERCHANGE_FORMAT_TAG;
    private static final byte[] JPEG_SIGNATURE;
    private static final byte MARKER = -1;
    private static final byte MARKER_APP1 = -31;
    private static final byte MARKER_COM = -2;
    private static final byte MARKER_EOI = -39;
    private static final byte MARKER_SOF0 = -64;
    private static final byte MARKER_SOF1 = -63;
    private static final byte MARKER_SOF10 = -54;
    private static final byte MARKER_SOF11 = -53;
    private static final byte MARKER_SOF13 = -51;
    private static final byte MARKER_SOF14 = -50;
    private static final byte MARKER_SOF15 = -49;
    private static final byte MARKER_SOF2 = -62;
    private static final byte MARKER_SOF3 = -61;
    private static final byte MARKER_SOF5 = -59;
    private static final byte MARKER_SOF6 = -58;
    private static final byte MARKER_SOF7 = -57;
    private static final byte MARKER_SOF9 = -55;
    private static final byte MARKER_SOI = -40;
    private static final byte MARKER_SOS = -38;
    private static final int MAX_THUMBNAIL_SIZE = 512;
    private static final ExifTag[] ORF_CAMERA_SETTINGS_TAGS;
    private static final ExifTag[] ORF_IMAGE_PROCESSING_TAGS;
    private static final byte[] ORF_MAKER_NOTE_HEADER_1;
    private static final int ORF_MAKER_NOTE_HEADER_1_SIZE = 8;
    private static final byte[] ORF_MAKER_NOTE_HEADER_2;
    private static final int ORF_MAKER_NOTE_HEADER_2_SIZE = 12;
    private static final ExifTag[] ORF_MAKER_NOTE_TAGS;
    private static final short ORF_SIGNATURE_1 = 20306;
    private static final short ORF_SIGNATURE_2 = 21330;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    private static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final int PEF_MAKER_NOTE_SKIP_SIZE = 6;
    private static final String PEF_SIGNATURE = "PENTAX";
    private static final ExifTag[] PEF_TAGS;
    private static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    private static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    private static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    private static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final int RAF_INFO_SIZE = 160;
    private static final int RAF_JPEG_LENGTH_VALUE_SIZE = 4;
    private static final int RAF_OFFSET_TO_JPEG_IMAGE_OFFSET = 84;
    private static final String RAF_SIGNATURE = "FUJIFILMCCD-RAW";
    private static final int REDUCED_RESOLUTION_IMAGE = 1;
    private static final short RW2_SIGNATURE = 85;
    private static final int SIGNATURE_CHECK_SIZE = 5000;
    private static final byte START_CODE = 42;
    private static final String TAG = "ExifInterface";
    @Deprecated
    public static final String TAG_APERTURE = "FNumber";
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    private static final String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    private static final String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    private static final String TAG_HAS_THUMBNAIL = "HasThumbnail";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    private static final String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";
    @Deprecated
    public static final String TAG_ISO = "ISOSpeedRatings";
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_OFFSET_TIME = "OffsetTime";
    public static final String TAG_OFFSET_TIME_DIGITIZED = "OffsetTimeDigitized";
    public static final String TAG_OFFSET_TIME_ORIGINAL = "OffsetTimeOriginal";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER = "CameraSettingsIFDPointer";
    private static final String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    private static final ExifTag TAG_RAF_IMAGE_SIZE;
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIG = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIG = "SubSecTimeOriginal";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    private static final String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    private static final String TAG_THUMBNAIL_DATA = "ThumbnailData";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    private static final String TAG_THUMBNAIL_LENGTH = "ThumbnailLength";
    private static final String TAG_THUMBNAIL_OFFSET = "ThumbnailOffset";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_XMP = "Xmp";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    public static final int WHITEBALANCE_AUTO = 0;
    public static final int WHITEBALANCE_MANUAL = 1;
    private static final HashMap<Integer, Integer> sExifPointerTagMap;
    private static final HashMap[] sExifTagMapsForReading;
    private static final HashMap[] sExifTagMapsForWriting;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private static SimpleDateFormat sFormatter;
    private static SimpleDateFormat sFormatterTz;
    private static final Pattern sGpsTimestampPattern;
    private static final Pattern sNonZeroTimePattern;
    private static final HashSet<String> sTagSetForCompatibility;
    private AssetManager.AssetInputStream mAssetInputStream;
    @UnsupportedAppUsage
    private final HashMap[] mAttributes;
    private ByteOrder mExifByteOrder;
    private int mExifOffset;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private String mFilename;
    private Set<Integer> mHandledIfdOffsets;
    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    private boolean mHasThumbnail;
    private boolean mIsInputStream;
    private boolean mIsSupportedFile;
    private int mMimeType;
    private boolean mModified;
    private int mOrfMakerNoteOffset;
    private int mOrfThumbnailLength;
    private int mOrfThumbnailOffset;
    private int mRw2JpgFromRawOffset;
    private FileDescriptor mSeekableFileDescriptor;
    private byte[] mThumbnailBytes;
    private int mThumbnailCompression;
    private int mThumbnailLength;
    private int mThumbnailOffset;

    static {
        DEBUG = Log.isLoggable(TAG, 3);
        JPEG_SIGNATURE = new byte[]{-1, -40, -1};
        HEIF_TYPE_FTYP = new byte[]{102, 116, 121, 112};
        HEIF_BRAND_MIF1 = new byte[]{109, 105, 102, 49};
        HEIF_BRAND_HEIC = new byte[]{104, 101, 105, 99};
        ORF_MAKER_NOTE_HEADER_1 = new byte[]{79, 76, 89, 77, 80, 0};
        ORF_MAKER_NOTE_HEADER_2 = new byte[]{79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
        IFD_FORMAT_NAMES = new String[]{"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", "UNDEFINED", "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE"};
        IFD_FORMAT_BYTES_PER_FORMAT = new int[]{0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
        EXIF_ASCII_PREFIX = new byte[]{65, 83, 67, 73, 73, 0, 0, 0};
        BITS_PER_SAMPLE_RGB = new int[]{8, 8, 8};
        BITS_PER_SAMPLE_GREYSCALE_1 = new int[]{4};
        BITS_PER_SAMPLE_GREYSCALE_2 = new int[]{8};
        IFD_TIFF_TAGS = new ExifTag[]{new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, 3), new ExifTag(TAG_COMPRESSION, 259, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, 270, 2), new ExifTag(TAG_MAKE, 271, 2), new ExifTag(TAG_MODEL, 272, 2), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag(TAG_ORIENTATION, 274, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, 3), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag(TAG_X_RESOLUTION, 282, 5), new ExifTag(TAG_Y_RESOLUTION, 283, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, 301, 3), new ExifTag(TAG_SOFTWARE, 305, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, 315, 2), new ExifTag(TAG_WHITE_POINT, 318, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, 33432, 2), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_RW2_SENSOR_TOP_BORDER, 4, 4), new ExifTag(TAG_RW2_SENSOR_LEFT_BORDER, 5, 4), new ExifTag(TAG_RW2_SENSOR_BOTTOM_BORDER, 6, 4), new ExifTag(TAG_RW2_SENSOR_RIGHT_BORDER, 7, 4), new ExifTag(TAG_RW2_ISO, 23, 3), new ExifTag(TAG_RW2_JPG_FROM_RAW, 46, 7), new ExifTag(TAG_XMP, 700, 1)};
        IFD_EXIF_TAGS = new ExifTag[]{new ExifTag(TAG_EXPOSURE_TIME, 33434, 5), new ExifTag("FNumber", 33437, 5), new ExifTag(TAG_EXPOSURE_PROGRAM, 34850, 3), new ExifTag(TAG_SPECTRAL_SENSITIVITY, 34852, 2), new ExifTag("ISOSpeedRatings", 34855, 3), new ExifTag(TAG_OECF, 34856, 7), new ExifTag(TAG_EXIF_VERSION, 36864, 2), new ExifTag(TAG_DATETIME_ORIGINAL, 36867, 2), new ExifTag(TAG_DATETIME_DIGITIZED, 36868, 2), new ExifTag(TAG_OFFSET_TIME, 36880, 2), new ExifTag(TAG_OFFSET_TIME_ORIGINAL, 36881, 2), new ExifTag(TAG_OFFSET_TIME_DIGITIZED, 36882, 2), new ExifTag(TAG_COMPONENTS_CONFIGURATION, 37121, 7), new ExifTag(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, 5), new ExifTag(TAG_SHUTTER_SPEED_VALUE, 37377, 10), new ExifTag(TAG_APERTURE_VALUE, 37378, 5), new ExifTag(TAG_BRIGHTNESS_VALUE, 37379, 10), new ExifTag(TAG_EXPOSURE_BIAS_VALUE, 37380, 10), new ExifTag(TAG_MAX_APERTURE_VALUE, 37381, 5), new ExifTag(TAG_SUBJECT_DISTANCE, 37382, 5), new ExifTag(TAG_METERING_MODE, 37383, 3), new ExifTag(TAG_LIGHT_SOURCE, 37384, 3), new ExifTag(TAG_FLASH, 37385, 3), new ExifTag(TAG_FOCAL_LENGTH, 37386, 5), new ExifTag(TAG_SUBJECT_AREA, 37396, 3), new ExifTag(TAG_MAKER_NOTE, 37500, 7), new ExifTag(TAG_USER_COMMENT, 37510, 7), new ExifTag(TAG_SUBSEC_TIME, 37520, 2), new ExifTag("SubSecTimeOriginal", 37521, 2), new ExifTag("SubSecTimeDigitized", 37522, 2), new ExifTag(TAG_FLASHPIX_VERSION, 40960, 7), new ExifTag(TAG_COLOR_SPACE, 40961, 3), new ExifTag(TAG_PIXEL_X_DIMENSION, 40962, 3, 4), new ExifTag(TAG_PIXEL_Y_DIMENSION, 40963, 3, 4), new ExifTag(TAG_RELATED_SOUND_FILE, 40964, 2), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag(TAG_FLASH_ENERGY, 41483, 5), new ExifTag(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, 7), new ExifTag(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, 5), new ExifTag(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, 5), new ExifTag(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, 3), new ExifTag(TAG_SUBJECT_LOCATION, 41492, 3), new ExifTag(TAG_EXPOSURE_INDEX, 41493, 5), new ExifTag(TAG_SENSING_METHOD, 41495, 3), new ExifTag(TAG_FILE_SOURCE, 41728, 7), new ExifTag(TAG_SCENE_TYPE, 41729, 7), new ExifTag(TAG_CFA_PATTERN, 41730, 7), new ExifTag(TAG_CUSTOM_RENDERED, 41985, 3), new ExifTag(TAG_EXPOSURE_MODE, 41986, 3), new ExifTag(TAG_WHITE_BALANCE, 41987, 3), new ExifTag(TAG_DIGITAL_ZOOM_RATIO, 41988, 5), new ExifTag(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, 3), new ExifTag(TAG_SCENE_CAPTURE_TYPE, 41990, 3), new ExifTag(TAG_GAIN_CONTROL, 41991, 3), new ExifTag(TAG_CONTRAST, 41992, 3), new ExifTag(TAG_SATURATION, 41993, 3), new ExifTag(TAG_SHARPNESS, 41994, 3), new ExifTag(TAG_DEVICE_SETTING_DESCRIPTION, 41995, 7), new ExifTag(TAG_SUBJECT_DISTANCE_RANGE, 41996, 3), new ExifTag(TAG_IMAGE_UNIQUE_ID, 42016, 2), new ExifTag(TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        IFD_GPS_TAGS = new ExifTag[]{new ExifTag(TAG_GPS_VERSION_ID, 0, 1), new ExifTag(TAG_GPS_LATITUDE_REF, 1, 2), new ExifTag(TAG_GPS_LATITUDE, 2, 5), new ExifTag(TAG_GPS_LONGITUDE_REF, 3, 2), new ExifTag(TAG_GPS_LONGITUDE, 4, 5), new ExifTag(TAG_GPS_ALTITUDE_REF, 5, 1), new ExifTag(TAG_GPS_ALTITUDE, 6, 5), new ExifTag(TAG_GPS_TIMESTAMP, 7, 5), new ExifTag(TAG_GPS_SATELLITES, 8, 2), new ExifTag(TAG_GPS_STATUS, 9, 2), new ExifTag(TAG_GPS_MEASURE_MODE, 10, 2), new ExifTag(TAG_GPS_DOP, 11, 5), new ExifTag(TAG_GPS_SPEED_REF, 12, 2), new ExifTag(TAG_GPS_SPEED, 13, 5), new ExifTag(TAG_GPS_TRACK_REF, 14, 2), new ExifTag(TAG_GPS_TRACK, 15, 5), new ExifTag(TAG_GPS_IMG_DIRECTION_REF, 16, 2), new ExifTag(TAG_GPS_IMG_DIRECTION, 17, 5), new ExifTag(TAG_GPS_MAP_DATUM, 18, 2), new ExifTag(TAG_GPS_DEST_LATITUDE_REF, 19, 2), new ExifTag(TAG_GPS_DEST_LATITUDE, 20, 5), new ExifTag(TAG_GPS_DEST_LONGITUDE_REF, 21, 2), new ExifTag(TAG_GPS_DEST_LONGITUDE, 22, 5), new ExifTag(TAG_GPS_DEST_BEARING_REF, 23, 2), new ExifTag(TAG_GPS_DEST_BEARING, 24, 5), new ExifTag(TAG_GPS_DEST_DISTANCE_REF, 25, 2), new ExifTag(TAG_GPS_DEST_DISTANCE, 26, 5), new ExifTag(TAG_GPS_PROCESSING_METHOD, 27, 7), new ExifTag(TAG_GPS_AREA_INFORMATION, 28, 7), new ExifTag(TAG_GPS_DATESTAMP, 29, 2), new ExifTag(TAG_GPS_DIFFERENTIAL, 30, 3)};
        IFD_INTEROPERABILITY_TAGS = new ExifTag[]{new ExifTag(TAG_INTEROPERABILITY_INDEX, 1, 2)};
        IFD_THUMBNAIL_TAGS = new ExifTag[]{new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, 4), new ExifTag(TAG_SUBFILE_TYPE, 255, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, 3), new ExifTag(TAG_COMPRESSION, 259, 3), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, 3), new ExifTag(TAG_IMAGE_DESCRIPTION, 270, 2), new ExifTag(TAG_MAKE, 271, 2), new ExifTag(TAG_MODEL, 272, 2), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag(TAG_ORIENTATION, 274, 3), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, 3), new ExifTag(TAG_ROWS_PER_STRIP, 278, 3, 4), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, 3, 4), new ExifTag(TAG_X_RESOLUTION, 282, 5), new ExifTag(TAG_Y_RESOLUTION, 283, 5), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, 3), new ExifTag(TAG_RESOLUTION_UNIT, 296, 3), new ExifTag(TAG_TRANSFER_FUNCTION, 301, 3), new ExifTag(TAG_SOFTWARE, 305, 2), new ExifTag(TAG_DATETIME, 306, 2), new ExifTag(TAG_ARTIST, 315, 2), new ExifTag(TAG_WHITE_POINT, 318, 5), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, 5), new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, 5), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, 3), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, 3), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, 5), new ExifTag(TAG_COPYRIGHT, 33432, 2), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_DNG_VERSION, 50706, 1), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        TAG_RAF_IMAGE_SIZE = new ExifTag(TAG_STRIP_OFFSETS, 273, 3);
        ORF_MAKER_NOTE_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_THUMBNAIL_IMAGE, 256, 7), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 4), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, 8256, 4)};
        ORF_CAMERA_SETTINGS_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_PREVIEW_IMAGE_START, 257, 4), new ExifTag(TAG_ORF_PREVIEW_IMAGE_LENGTH, 258, 4)};
        ORF_IMAGE_PROCESSING_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_ASPECT_FRAME, 4371, 3)};
        PEF_TAGS = new ExifTag[]{new ExifTag(TAG_COLOR_SPACE, 55, 3)};
        ExifTag[] object2 = IFD_TIFF_TAGS;
        EXIF_TAGS = new ExifTag[][]{object2, IFD_EXIF_TAGS, IFD_GPS_TAGS, IFD_INTEROPERABILITY_TAGS, IFD_THUMBNAIL_TAGS, object2, ORF_MAKER_NOTE_TAGS, ORF_CAMERA_SETTINGS_TAGS, ORF_IMAGE_PROCESSING_TAGS, PEF_TAGS};
        EXIF_POINTER_TAGS = new ExifTag[]{new ExifTag(TAG_SUB_IFD_POINTER, 330, 4), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, 4), new ExifTag(TAG_GPS_INFO_IFD_POINTER, 34853, 4), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, 4), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, 1), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, 8256, 1)};
        JPEG_INTERCHANGE_FORMAT_TAG = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, 4);
        JPEG_INTERCHANGE_FORMAT_LENGTH_TAG = new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, 4);
        ExifTag[][] arrexifTag = EXIF_TAGS;
        sExifTagMapsForReading = new HashMap[arrexifTag.length];
        sExifTagMapsForWriting = new HashMap[arrexifTag.length];
        sTagSetForCompatibility = new HashSet<String>(Arrays.asList("FNumber", TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP));
        sExifPointerTagMap = new HashMap();
        ASCII = Charset.forName("US-ASCII");
        IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(ASCII);
        IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(ASCII);
        sFormatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
        sFormatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        sFormatterTz = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss XXX");
        sFormatterTz.setTimeZone(TimeZone.getTimeZone("UTC"));
        for (int i = 0; i < EXIF_TAGS.length; ++i) {
            ExifInterface.sExifTagMapsForReading[i] = new HashMap();
            ExifInterface.sExifTagMapsForWriting[i] = new HashMap();
            for (ExifTag exifTag : EXIF_TAGS[i]) {
                sExifTagMapsForReading[i].put(exifTag.number, exifTag);
                sExifTagMapsForWriting[i].put(exifTag.name, exifTag);
            }
        }
        sExifPointerTagMap.put(ExifInterface.EXIF_POINTER_TAGS[0].number, 5);
        sExifPointerTagMap.put(ExifInterface.EXIF_POINTER_TAGS[1].number, 1);
        sExifPointerTagMap.put(ExifInterface.EXIF_POINTER_TAGS[2].number, 2);
        sExifPointerTagMap.put(ExifInterface.EXIF_POINTER_TAGS[3].number, 3);
        sExifPointerTagMap.put(ExifInterface.EXIF_POINTER_TAGS[4].number, 7);
        sExifPointerTagMap.put(ExifInterface.EXIF_POINTER_TAGS[5].number, 8);
        sNonZeroTimePattern = Pattern.compile(".*[1-9].*");
        sGpsTimestampPattern = Pattern.compile("^([0-9][0-9]):([0-9][0-9]):([0-9][0-9])$");
    }

    public ExifInterface(File file) throws IOException {
        ExifTag[][] arrexifTag = EXIF_TAGS;
        this.mAttributes = new HashMap[arrexifTag.length];
        this.mHandledIfdOffsets = new HashSet<Integer>(arrexifTag.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (file != null) {
            this.initForFilename(file.getAbsolutePath());
            return;
        }
        throw new NullPointerException("file cannot be null");
    }

    public ExifInterface(FileDescriptor object) throws IOException {
        Object object2 = EXIF_TAGS;
        this.mAttributes = new HashMap[((ExifTag[][])object2).length];
        this.mHandledIfdOffsets = new HashSet<Integer>(((ExifTag[][])object2).length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (object != null) {
            this.mAssetInputStream = null;
            this.mFilename = null;
            boolean bl = false;
            if (ExifInterface.isSeekableFD((FileDescriptor)object)) {
                this.mSeekableFileDescriptor = object;
                try {
                    object2 = Os.dup((FileDescriptor)object);
                    bl = true;
                }
                catch (ErrnoException errnoException) {
                    throw errnoException.rethrowAsIOException();
                }
            } else {
                this.mSeekableFileDescriptor = null;
                object2 = object;
            }
            this.mIsInputStream = false;
            Object var4_6 = null;
            object = var4_6;
            object = var4_6;
            try {
                FileInputStream fileInputStream = new FileInputStream((FileDescriptor)object2, bl);
                object = object2 = fileInputStream;
            }
            catch (Throwable throwable) {
                IoUtils.closeQuietly((AutoCloseable)object);
                throw throwable;
            }
            this.loadAttributes((InputStream)object2);
            IoUtils.closeQuietly((AutoCloseable)object2);
            return;
        }
        throw new NullPointerException("fileDescriptor cannot be null");
    }

    public ExifInterface(InputStream inputStream) throws IOException {
        ExifTag[][] arrexifTag = EXIF_TAGS;
        this.mAttributes = new HashMap[arrexifTag.length];
        this.mHandledIfdOffsets = new HashSet<Integer>(arrexifTag.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (inputStream != null) {
            this.mFilename = null;
            if (inputStream instanceof AssetManager.AssetInputStream) {
                this.mAssetInputStream = (AssetManager.AssetInputStream)inputStream;
                this.mSeekableFileDescriptor = null;
            } else if (inputStream instanceof FileInputStream && ExifInterface.isSeekableFD(((FileInputStream)inputStream).getFD())) {
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = ((FileInputStream)inputStream).getFD();
            } else {
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = null;
            }
            this.mIsInputStream = true;
            this.loadAttributes(inputStream);
            return;
        }
        throw new NullPointerException("inputStream cannot be null");
    }

    public ExifInterface(String string2) throws IOException {
        ExifTag[][] arrexifTag = EXIF_TAGS;
        this.mAttributes = new HashMap[arrexifTag.length];
        this.mHandledIfdOffsets = new HashSet<Integer>(arrexifTag.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        if (string2 != null) {
            this.initForFilename(string2);
            return;
        }
        throw new NullPointerException("filename cannot be null");
    }

    static /* synthetic */ byte[] access$300() {
        return EXIF_ASCII_PREFIX;
    }

    private void addDefaultValuesForCompatibility() {
        String string2 = this.getAttribute(TAG_DATETIME_ORIGINAL);
        if (string2 != null && this.getAttribute(TAG_DATETIME) == null) {
            this.mAttributes[0].put(TAG_DATETIME, ExifAttribute.createString(string2));
        }
        if (this.getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.getAttribute(TAG_ORIENTATION) == null) {
            this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(0, this.mExifByteOrder));
        }
        if (this.getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.mAttributes[1].put(TAG_LIGHT_SOURCE, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
    }

    private boolean containsMatch(byte[] arrby, byte[] arrby2) {
        for (int i = 0; i < arrby.length - arrby2.length; ++i) {
            for (int j = 0; j < arrby2.length && arrby[i + j] == arrby2[j]; ++j) {
                if (j != arrby2.length - 1) continue;
                return true;
            }
        }
        return false;
    }

    @UnsupportedAppUsage(maxTargetSdk=28, trackingBug=115609023L)
    public static float convertRationalLatLonToFloat(String arrstring, String string2) {
        double d;
        block3 : {
            arrstring = arrstring.split(",");
            String[] arrstring2 = arrstring[0].split("/");
            double d2 = Double.parseDouble(arrstring2[0].trim()) / Double.parseDouble(arrstring2[1].trim());
            arrstring2 = arrstring[1].split("/");
            d = Double.parseDouble(arrstring2[0].trim()) / Double.parseDouble(arrstring2[1].trim());
            arrstring = arrstring[2].split("/");
            double d3 = Double.parseDouble(arrstring[0].trim()) / Double.parseDouble(arrstring[1].trim());
            d = d / 60.0 + d2 + d3 / 3600.0;
            try {
                boolean bl;
                if (string2.equals("S") || (bl = string2.equals("W"))) break block3;
            }
            catch (ArrayIndexOutOfBoundsException | NumberFormatException runtimeException) {
                throw new IllegalArgumentException();
            }
            return (float)d;
        }
        return (float)(-d);
    }

    private static long[] convertToLongArray(Object arrl) {
        if (arrl instanceof int[]) {
            int[] arrn = (int[])arrl;
            arrl = new long[arrn.length];
            for (int i = 0; i < arrn.length; ++i) {
                arrl[i] = arrn[i];
            }
            return arrl;
        }
        if (arrl instanceof long[]) {
            return arrl;
        }
        return null;
    }

    private ExifAttribute getExifAttribute(String string2) {
        if (string2 != null) {
            for (int i = 0; i < EXIF_TAGS.length; ++i) {
                Object v = this.mAttributes[i].get(string2);
                if (v == null) continue;
                return (ExifAttribute)v;
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void getHeifAttributes(ByteOrderedDataInputStream var1_1) throws IOException {
        block19 : {
            var2_6 = new MediaMetadataRetriever();
            var3_7 = new MediaDataSource((ByteOrderedDataInputStream)var1_1){
                long mPosition;
                final /* synthetic */ ByteOrderedDataInputStream val$in;
                {
                    this.val$in = byteOrderedDataInputStream;
                }

                @Override
                public void close() throws IOException {
                }

                @Override
                public long getSize() throws IOException {
                    return -1L;
                }

                @Override
                public int readAt(long l, byte[] arrby, int n, int n2) throws IOException {
                    block10 : {
                        block8 : {
                            block9 : {
                                if (n2 == 0) {
                                    return 0;
                                }
                                if (l < 0L) {
                                    return -1;
                                }
                                if (this.mPosition == l) break block8;
                                if (this.mPosition < 0L || l < this.mPosition + (long)this.val$in.available()) break block9;
                                return -1;
                            }
                            this.val$in.seek(l);
                            this.mPosition = l;
                        }
                        int n3 = n2;
                        if (n2 > this.val$in.available()) {
                            n3 = this.val$in.available();
                        }
                        if ((n = this.val$in.read(arrby, n, n3)) < 0) break block10;
                        try {
                            this.mPosition += (long)n;
                            return n;
                        }
                        catch (IOException iOException) {
                            // empty catch block
                        }
                    }
                    this.mPosition = -1L;
                    return -1;
                }
            };
            var2_6.setDataSource(var3_7);
            var4_8 = var2_6.extractMetadata(33);
            var5_9 = var2_6.extractMetadata(34);
            var6_10 = var2_6.extractMetadata(26);
            var7_11 = var2_6.extractMetadata(17);
            var3_7 = null;
            var8_12 = null;
            var9_13 = null;
            if ("yes".equals(var6_10)) {
                var3_7 = var2_6.extractMetadata(29);
                var8_12 = var2_6.extractMetadata(30);
                var9_13 = var2_6.extractMetadata(31);
            } else if ("yes".equals(var7_11)) {
                var3_7 = var2_6.extractMetadata(18);
                var8_12 = var2_6.extractMetadata(19);
                var9_13 = var2_6.extractMetadata(24);
            }
            if (var3_7 != null) {
                this.mAttributes[0].put("ImageWidth", ExifAttribute.createUShort(Integer.parseInt((String)var3_7), this.mExifByteOrder));
            }
            if (var8_12 != null) {
                this.mAttributes[0].put("ImageLength", ExifAttribute.createUShort(Integer.parseInt(var8_12), this.mExifByteOrder));
            }
            if (var9_13 != null) {
                var10_14 = 1;
                var11_15 = Integer.parseInt(var9_13);
                if (var11_15 != 90) {
                    if (var11_15 != 180) {
                        if (var11_15 == 270) {
                            var10_14 = 8;
                        }
                    } else {
                        var10_14 = 3;
                    }
                } else {
                    var10_14 = 6;
                }
                this.mAttributes[0].put("Orientation", ExifAttribute.createUShort(var10_14, this.mExifByteOrder));
            }
            if (var4_8 == null || var5_9 == null) ** GOTO lbl67
            var11_15 = Integer.parseInt(var4_8);
            var10_14 = Integer.parseInt((String)var5_9);
            if (var10_14 <= 6) ** GOTO lbl65
            var12_16 = var11_15;
            var1_1.seek(var12_16);
            var5_9 = new byte[6];
            if (var1_1.read(var5_9) != 6) ** GOTO lbl62
            var10_14 -= 6;
            if (!Arrays.equals(var5_9, ExifInterface.IDENTIFIER_EXIF_APP1)) ** GOTO lbl60
            var5_9 = new byte[var10_14];
            var11_15 = var1_1.read(var5_9);
            if (var11_15 != var10_14) ** GOTO lbl58
            {
                catch (Throwable var1_2) {}
            }
            try {
                block20 : {
                    this.readExifSegment(var5_9, 0);
                    break block20;
lbl58: // 1 sources:
                    var1_1 = new IOException("Can't read exif");
                    throw var1_1;
lbl60: // 1 sources:
                    var1_1 = new IOException("Invalid identifier");
                    throw var1_1;
lbl62: // 1 sources:
                    var1_1 = new IOException("Can't read identifier");
                    throw var1_1;
                    break block19;
lbl65: // 1 sources:
                    var1_1 = new IOException("Invalid exif length");
                    throw var1_1;
                }
                if (ExifInterface.DEBUG) {
                    var1_1 = new StringBuilder();
                    var1_1.append("Heif meta: ");
                    var1_1.append((String)var3_7);
                    var1_1.append("x");
                    var1_1.append(var8_12);
                    var1_1.append(", rotation ");
                    var1_1.append(var9_13);
                    Log.d("ExifInterface", var1_1.toString());
                }
                var2_6.release();
                return;
            }
            catch (Throwable var1_3) {}
            break block19;
            catch (Throwable var1_4) {
                // empty catch block
            }
        }
        var2_6.release();
        throw var1_5;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void getJpegAttributes(ByteOrderedDataInputStream var1_1, int var2_2, int var3_3) throws IOException {
        if (ExifInterface.DEBUG) {
            var4_4 = new StringBuilder();
            var4_4.append("getJpegAttributes starting with: ");
            var4_4.append(var1_1);
            Log.d("ExifInterface", var4_4.toString());
        }
        var1_1.setByteOrder(ByteOrder.BIG_ENDIAN);
        var1_1.seek(var2_2);
        var5_5 = var1_1.readByte();
        if (var5_5 != -1) {
            var1_1 = new StringBuilder();
            var1_1.append("Invalid marker: ");
            var1_1.append(Integer.toHexString(var5_5 & 255));
            throw new IOException(var1_1.toString());
        }
        if (var1_1.readByte() != -40) {
            var1_1 = new StringBuilder();
            var1_1.append("Invalid marker: ");
            var1_1.append(Integer.toHexString(var5_5 & 255));
            throw new IOException(var1_1.toString());
        }
        var2_2 = var2_2 + 1 + 1;
        do {
            block26 : {
                block27 : {
                    block25 : {
                        var5_5 = var3_3;
                        var6_6 = var1_1.readByte();
                        if (var6_6 != -1) {
                            var1_1 = new StringBuilder();
                            var1_1.append("Invalid marker:");
                            var1_1.append(Integer.toHexString(var6_6 & 255));
                            throw new IOException(var1_1.toString());
                        }
                        var7_7 = var1_1.readByte();
                        if (ExifInterface.DEBUG) {
                            var4_4 = new StringBuilder();
                            var4_4.append("Found JPEG segment indicator: ");
                            var4_4.append(Integer.toHexString(var7_7 & 255));
                            Log.d("ExifInterface", var4_4.toString());
                        }
                        if (var7_7 == -39 || var7_7 == -38) break;
                        var8_8 = var1_1.readUnsignedShort() - 2;
                        var6_6 = var2_2 + 1 + 1 + 2;
                        if (ExifInterface.DEBUG) {
                            var4_4 = new StringBuilder();
                            var4_4.append("JPEG segment: ");
                            var4_4.append(Integer.toHexString(var7_7 & 255));
                            var4_4.append(" (length: ");
                            var4_4.append(var8_8 + 2);
                            var4_4.append(")");
                            Log.d("ExifInterface", var4_4.toString());
                        }
                        if (var8_8 < 0) throw new IOException("Invalid length");
                        if (var7_7 == -31) break block25;
                        if (var7_7 != -2) {
                            block0 : switch (var7_7) {
                                default: {
                                    switch (var7_7) {
                                        default: {
                                            switch (var7_7) {
                                                default: {
                                                    switch (var7_7) {
                                                        default: {
                                                            var5_5 = var6_6;
                                                            var2_2 = var8_8;
                                                            break block0;
                                                        }
                                                        case -51: 
                                                        case -50: 
                                                        case -49: 
                                                    }
                                                }
                                                case -55: 
                                                case -54: 
                                                case -53: 
                                            }
                                        }
                                        case -59: 
                                        case -58: 
                                        case -57: 
                                    }
                                }
                                case -64: 
                                case -63: 
                                case -62: 
                                case -61: {
                                    if (var1_1.skipBytes(1) != 1) throw new IOException("Invalid SOFx");
                                    this.mAttributes[var5_5].put("ImageLength", ExifAttribute.createULong(var1_1.readUnsignedShort(), this.mExifByteOrder));
                                    this.mAttributes[var5_5].put("ImageWidth", ExifAttribute.createULong(var1_1.readUnsignedShort(), this.mExifByteOrder));
                                    var2_2 = var8_8 - 5;
                                    var5_5 = var6_6;
                                    break;
                                }
                            }
                        } else {
                            var4_4 = new byte[var8_8];
                            if (var1_1.read(var4_4) != var8_8) throw new IOException("Invalid exif");
                            var8_8 = 0;
                            var5_5 = var6_6;
                            var2_2 = var8_8;
                            if (this.getAttribute("UserComment") == null) {
                                this.mAttributes[1].put("UserComment", ExifAttribute.createString(new String(var4_4, ExifInterface.ASCII)));
                                var5_5 = var6_6;
                                var2_2 = var8_8;
                            }
                        }
                        break block26;
                    }
                    var4_4 = new byte[var8_8];
                    var1_1.readFully(var4_4);
                    var8_8 = var6_6 + var8_8;
                    var2_2 = 0;
                    if (!ArrayUtils.startsWith(var4_4, ExifInterface.IDENTIFIER_EXIF_APP1)) break block27;
                    var9_9 = ExifInterface.IDENTIFIER_EXIF_APP1;
                    var10_10 = var9_9.length + var6_6;
                    this.readExifSegment(Arrays.copyOfRange(var4_4, var9_9.length, var4_4.length), var5_5);
                    this.mExifOffset = (int)var10_10;
                    ** GOTO lbl-1000
                }
                if (ArrayUtils.startsWith(var4_4, ExifInterface.IDENTIFIER_XMP_APP1)) {
                    var9_9 = ExifInterface.IDENTIFIER_XMP_APP1;
                    var10_10 = var9_9.length + var6_6;
                    var4_4 = Arrays.copyOfRange(var4_4, var9_9.length, var4_4.length);
                    if (this.getAttribute("Xmp") == null) {
                        this.mAttributes[0].put("Xmp", new ExifAttribute(1, var4_4.length, var10_10, var4_4));
                    }
                    var5_5 = var8_8;
                } else lbl-1000: // 2 sources:
                {
                    var5_5 = var8_8;
                }
            }
            if (var2_2 < 0) throw new IOException("Invalid length");
            if (var1_1.skipBytes(var2_2) != var2_2) throw new IOException("Invalid JPEG segment");
            var2_2 = var5_5 + var2_2;
        } while (true);
        var1_1.setByteOrder(this.mExifByteOrder);
    }

    private int getMimeType(BufferedInputStream bufferedInputStream) throws IOException {
        bufferedInputStream.mark(5000);
        byte[] arrby = new byte[5000];
        bufferedInputStream.read(arrby);
        bufferedInputStream.reset();
        if (ExifInterface.isJpegFormat(arrby)) {
            return 4;
        }
        if (this.isRafFormat(arrby)) {
            return 9;
        }
        if (this.isHeifFormat(arrby)) {
            return 12;
        }
        if (this.isOrfFormat(arrby)) {
            return 7;
        }
        if (this.isRw2Format(arrby)) {
            return 10;
        }
        return 0;
    }

    private void getOrfAttributes(ByteOrderedDataInputStream object) throws IOException {
        this.getRawAttributes((ByteOrderedDataInputStream)object);
        object = (ExifAttribute)this.mAttributes[1].get(TAG_MAKER_NOTE);
        if (object != null) {
            object = new ByteOrderedDataInputStream(object.bytes);
            object.setByteOrder(this.mExifByteOrder);
            Object object2 = new byte[ORF_MAKER_NOTE_HEADER_1.length];
            object.readFully((byte[])object2);
            object.seek(0L);
            byte[] arrby = new byte[ORF_MAKER_NOTE_HEADER_2.length];
            object.readFully(arrby);
            if (Arrays.equals(object2, ORF_MAKER_NOTE_HEADER_1)) {
                object.seek(8L);
            } else if (Arrays.equals(arrby, ORF_MAKER_NOTE_HEADER_2)) {
                object.seek(12L);
            }
            this.readImageFileDirectory((ByteOrderedDataInputStream)object, 6);
            object = (ExifAttribute)this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            object2 = (ExifAttribute)this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (object != null && object2 != null) {
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT, object);
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, object2);
            }
            if ((object2 = (ExifAttribute)this.mAttributes[8].get(TAG_ORF_ASPECT_FRAME)) != null) {
                object = new int[4];
                object = (int[])((ExifAttribute)object2).getValue(this.mExifByteOrder);
                if (object[2] > object[0] && object[3] > object[1]) {
                    int n = object[2] - object[0] + 1;
                    int n2 = object[3] - object[1] + 1;
                    int n3 = n;
                    int n4 = n2;
                    if (n < n2) {
                        n3 = n + n2;
                        n4 = n3 - n2;
                        n3 -= n4;
                    }
                    object2 = ExifAttribute.createUShort(n3, this.mExifByteOrder);
                    object = ExifAttribute.createUShort(n4, this.mExifByteOrder);
                    this.mAttributes[0].put(TAG_IMAGE_WIDTH, object2);
                    this.mAttributes[0].put(TAG_IMAGE_LENGTH, object);
                }
            }
        }
    }

    private void getRafAttributes(ByteOrderedDataInputStream object) throws IOException {
        ((ByteOrderedDataInputStream)object).skipBytes(84);
        Object object2 = new byte[4];
        byte[] arrby = new byte[4];
        ((InputStream)object).read((byte[])object2);
        ((ByteOrderedDataInputStream)object).skipBytes(4);
        ((InputStream)object).read(arrby);
        int n = ByteBuffer.wrap((byte[])object2).getInt();
        int n2 = ByteBuffer.wrap(arrby).getInt();
        this.getJpegAttributes((ByteOrderedDataInputStream)object, n, 5);
        ((ByteOrderedDataInputStream)object).seek(n2);
        ((ByteOrderedDataInputStream)object).setByteOrder(ByteOrder.BIG_ENDIAN);
        n2 = ((ByteOrderedDataInputStream)object).readInt();
        if (DEBUG) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("numberOfDirectoryEntry: ");
            ((StringBuilder)object2).append(n2);
            Log.d(TAG, ((StringBuilder)object2).toString());
        }
        for (n = 0; n < n2; ++n) {
            int n3 = ((ByteOrderedDataInputStream)object).readUnsignedShort();
            int n4 = ((ByteOrderedDataInputStream)object).readUnsignedShort();
            if (n3 == ExifInterface.TAG_RAF_IMAGE_SIZE.number) {
                n = ((ByteOrderedDataInputStream)object).readShort();
                n2 = ((ByteOrderedDataInputStream)object).readShort();
                object2 = ExifAttribute.createUShort(n, this.mExifByteOrder);
                object = ExifAttribute.createUShort(n2, this.mExifByteOrder);
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, object2);
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, object);
                if (DEBUG) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Updated to length: ");
                    ((StringBuilder)object).append(n);
                    ((StringBuilder)object).append(", width: ");
                    ((StringBuilder)object).append(n2);
                    Log.d(TAG, ((StringBuilder)object).toString());
                }
                return;
            }
            ((ByteOrderedDataInputStream)object).skipBytes(n4);
        }
    }

    private void getRawAttributes(ByteOrderedDataInputStream object) throws IOException {
        this.parseTiffHeaders((ByteOrderedDataInputStream)object, ((ByteOrderedDataInputStream)object).available());
        this.readImageFileDirectory((ByteOrderedDataInputStream)object, 0);
        this.updateImageSizeValues((ByteOrderedDataInputStream)object, 0);
        this.updateImageSizeValues((ByteOrderedDataInputStream)object, 5);
        this.updateImageSizeValues((ByteOrderedDataInputStream)object, 4);
        this.validateImages((InputStream)object);
        if (this.mMimeType == 8 && (object = (ExifAttribute)this.mAttributes[1].get(TAG_MAKER_NOTE)) != null) {
            object = new ByteOrderedDataInputStream(((ExifAttribute)object).bytes);
            ((ByteOrderedDataInputStream)object).setByteOrder(this.mExifByteOrder);
            ((ByteOrderedDataInputStream)object).seek(6L);
            this.readImageFileDirectory((ByteOrderedDataInputStream)object, 9);
            object = (ExifAttribute)this.mAttributes[9].get(TAG_COLOR_SPACE);
            if (object != null) {
                this.mAttributes[1].put(TAG_COLOR_SPACE, object);
            }
        }
    }

    private void getRw2Attributes(ByteOrderedDataInputStream object) throws IOException {
        this.getRawAttributes((ByteOrderedDataInputStream)object);
        if ((ExifAttribute)this.mAttributes[0].get(TAG_RW2_JPG_FROM_RAW) != null) {
            this.getJpegAttributes((ByteOrderedDataInputStream)object, this.mRw2JpgFromRawOffset, 5);
        }
        object = (ExifAttribute)this.mAttributes[0].get(TAG_RW2_ISO);
        ExifAttribute exifAttribute = (ExifAttribute)this.mAttributes[1].get("ISOSpeedRatings");
        if (object != null && exifAttribute == null) {
            this.mAttributes[1].put("ISOSpeedRatings", object);
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Pair<Integer, Integer> guessDataFormat(String object) {
        Integer n;
        Integer n2;
        block19 : {
            void var0_2;
            boolean bl = ((String)object).contains(",");
            n = 2;
            n2 = -1;
            if (!bl) break block19;
            String[] arrstring = ((String)object).split(",");
            Pair<Integer, Integer> pair = ExifInterface.guessDataFormat(arrstring[0]);
            if ((Integer)pair.first == 2) {
                return pair;
            }
            int n3 = 1;
            while (n3 < arrstring.length) {
                int n4;
                int n5;
                block20 : {
                    block21 : {
                        Pair<Integer, Integer> pair2 = ExifInterface.guessDataFormat(arrstring[n3]);
                        n5 = -1;
                        int n6 = -1;
                        if (pair2.first == var0_2.first || pair2.second == var0_2.first) {
                            n5 = (Integer)var0_2.first;
                        }
                        n4 = n6;
                        if ((Integer)var0_2.second == -1) break block20;
                        if (pair2.first == var0_2.second) break block21;
                        n4 = n6;
                        if (pair2.second != var0_2.second) break block20;
                    }
                    n4 = (Integer)var0_2.second;
                }
                if (n5 == -1 && n4 == -1) {
                    return new Pair<Integer, Integer>(n, n2);
                }
                if (n5 == -1) {
                    Pair<Integer, Integer> pair3 = new Pair<Integer, Integer>(n4, n2);
                } else if (n4 == -1) {
                    Pair<Integer, Integer> pair4 = new Pair<Integer, Integer>(n5, n2);
                }
                ++n3;
            }
            return var0_2;
        }
        if (((String)object).contains("/")) {
            String[] arrstring = ((String)object).split("/");
            if (arrstring.length != 2) return new Pair<Integer, Integer>(n, n2);
            long l = (long)Double.parseDouble(arrstring[0]);
            long l2 = (long)Double.parseDouble(arrstring[1]);
            if (l < 0L) return new Pair<Integer, Integer>(10, n2);
            if (l2 < 0L) return new Pair<Integer, Integer>(10, n2);
            if (l > Integer.MAX_VALUE) return new Pair<Integer, Integer>(5, n2);
            if (l2 > Integer.MAX_VALUE) return new Pair<Integer, Integer>(5, n2);
            try {
                return new Pair<Integer, Integer>(10, 5);
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            return new Pair<Integer, Integer>(n, n2);
        }
        try {
            Long l = Long.parseLong((String)object);
            if (l >= 0L && l <= 65535L) {
                return new Pair<Integer, Integer>(3, 4);
            }
            if (l >= 0L) return new Pair<Integer, Integer>(4, n2);
            return new Pair<Integer, Integer>(9, n2);
        }
        catch (NumberFormatException numberFormatException) {
            try {
                Double.parseDouble((String)object);
                return new Pair<Integer, Integer>(12, n2);
            }
            catch (NumberFormatException numberFormatException2) {
                return new Pair<Integer, Integer>(n, n2);
            }
        }
    }

    private void handleThumbnailFromJfif(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap arrby) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute)arrby.get(TAG_JPEG_INTERCHANGE_FORMAT);
        arrby = (ExifAttribute)arrby.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (exifAttribute != null && arrby != null) {
            int n;
            int n2 = exifAttribute.getIntValue(this.mExifByteOrder);
            int n3 = Math.min(arrby.getIntValue(this.mExifByteOrder), byteOrderedDataInputStream.getLength() - n2);
            int n4 = this.mMimeType;
            if (n4 != 4 && n4 != 9 && n4 != 10) {
                n = n2;
                if (n4 == 7) {
                    n = n2 + this.mOrfMakerNoteOffset;
                }
            } else {
                n = n2 + this.mExifOffset;
            }
            if (DEBUG) {
                arrby = new StringBuilder();
                arrby.append("Setting thumbnail attributes with offset: ");
                arrby.append(n);
                arrby.append(", length: ");
                arrby.append(n3);
                Log.d(TAG, arrby.toString());
            }
            if (n > 0 && n3 > 0) {
                this.mHasThumbnail = true;
                this.mThumbnailOffset = n;
                this.mThumbnailLength = n3;
                this.mThumbnailCompression = 6;
                if (this.mFilename == null && this.mAssetInputStream == null && this.mSeekableFileDescriptor == null) {
                    arrby = new byte[n3];
                    byteOrderedDataInputStream.seek(n);
                    byteOrderedDataInputStream.readFully(arrby);
                    this.mThumbnailBytes = arrby;
                }
            }
        }
    }

    private void handleThumbnailFromStrips(ByteOrderedDataInputStream byteOrderedDataInputStream, HashMap object) throws IOException {
        block4 : {
            Object object2 = (ExifAttribute)((HashMap)object).get(TAG_STRIP_OFFSETS);
            object = (ExifAttribute)((HashMap)object).get(TAG_STRIP_BYTE_COUNTS);
            if (object2 == null || object == null) break block4;
            long[] arrl = ExifInterface.convertToLongArray(((ExifAttribute)object2).getValue(this.mExifByteOrder));
            long[] arrl2 = ExifInterface.convertToLongArray(((ExifAttribute)object).getValue(this.mExifByteOrder));
            if (arrl == null) {
                Log.w(TAG, "stripOffsets should not be null.");
                return;
            }
            if (arrl2 == null) {
                Log.w(TAG, "stripByteCounts should not be null.");
                return;
            }
            byte[] arrby = new byte[(int)Arrays.stream(arrl2).sum()];
            int n = 0;
            int n2 = 0;
            int n3 = 0;
            object = object2;
            do {
                object2 = byteOrderedDataInputStream;
                if (n3 >= arrl.length) break;
                int n4 = (int)arrl[n3];
                int n5 = (int)arrl2[n3];
                if ((n4 -= n) < 0) {
                    Log.d(TAG, "Invalid strip offset value");
                }
                ((ByteOrderedDataInputStream)object2).seek(n4);
                byte[] arrby2 = new byte[n5];
                ((InputStream)object2).read(arrby2);
                n = n + n4 + n5;
                System.arraycopy(arrby2, 0, arrby, n2, arrby2.length);
                n2 += arrby2.length;
                ++n3;
            } while (true);
            this.mHasThumbnail = true;
            this.mThumbnailBytes = arrby;
            this.mThumbnailLength = arrby.length;
        }
    }

    private void initForFilename(String object) throws IOException {
        Object object2;
        block8 : {
            block7 : {
                Object object3 = null;
                this.mAssetInputStream = null;
                this.mFilename = object;
                this.mIsInputStream = false;
                object2 = object3;
                object2 = object3;
                try {
                    FileInputStream fileInputStream = new FileInputStream((String)object);
                    object2 = object = fileInputStream;
                }
                catch (Throwable throwable) {
                    IoUtils.closeQuietly(object2);
                    throw throwable;
                }
                if (!ExifInterface.isSeekableFD(((FileInputStream)object).getFD())) break block7;
                object2 = object;
                this.mSeekableFileDescriptor = ((FileInputStream)object).getFD();
                break block8;
            }
            object2 = object;
            this.mSeekableFileDescriptor = null;
        }
        object2 = object;
        this.loadAttributes((InputStream)object);
        IoUtils.closeQuietly((AutoCloseable)object);
    }

    /*
     * Exception decompiling
     */
    private boolean isHeifFormat(byte[] var1_1) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[TRYBLOCK]], but top level block is 8[UNCONDITIONALDOLOOP]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static boolean isJpegFormat(byte[] arrby) throws IOException {
        byte[] arrby2;
        for (int i = 0; i < (arrby2 = JPEG_SIGNATURE).length; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return false;
        }
        return true;
    }

    private boolean isOrfFormat(byte[] object) throws IOException {
        object = new ByteOrderedDataInputStream((byte[])object);
        this.mExifByteOrder = this.readByteOrder((ByteOrderedDataInputStream)object);
        ((ByteOrderedDataInputStream)object).setByteOrder(this.mExifByteOrder);
        short s = ((ByteOrderedDataInputStream)object).readShort();
        return s == 20306 || s == 21330;
        {
        }
    }

    private boolean isRafFormat(byte[] arrby) throws IOException {
        byte[] arrby2 = RAF_SIGNATURE.getBytes();
        for (int i = 0; i < arrby2.length; ++i) {
            if (arrby[i] == arrby2[i]) continue;
            return false;
        }
        return true;
    }

    private boolean isRw2Format(byte[] object) throws IOException {
        object = new ByteOrderedDataInputStream((byte[])object);
        this.mExifByteOrder = this.readByteOrder((ByteOrderedDataInputStream)object);
        ((ByteOrderedDataInputStream)object).setByteOrder(this.mExifByteOrder);
        return ((ByteOrderedDataInputStream)object).readShort() == 85;
    }

    private static boolean isSeekableFD(FileDescriptor fileDescriptor) throws IOException {
        try {
            Os.lseek((FileDescriptor)fileDescriptor, (long)0L, (int)OsConstants.SEEK_CUR);
            return true;
        }
        catch (ErrnoException errnoException) {
            return false;
        }
    }

    private boolean isSupportedDataType(HashMap object) throws IOException {
        int[] arrn = (int[])((HashMap)object).get(TAG_BITS_PER_SAMPLE);
        if (arrn != null) {
            int n;
            if (Arrays.equals(BITS_PER_SAMPLE_RGB, arrn = (int[])((ExifAttribute)arrn).getValue(this.mExifByteOrder))) {
                return true;
            }
            if (this.mMimeType == 3 && (object = (ExifAttribute)((HashMap)object).get(TAG_PHOTOMETRIC_INTERPRETATION)) != null && ((n = ((ExifAttribute)object).getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(arrn, BITS_PER_SAMPLE_GREYSCALE_2) || n == 6 && Arrays.equals(arrn, BITS_PER_SAMPLE_RGB))) {
                return true;
            }
        }
        if (DEBUG) {
            Log.d(TAG, "Unsupported data type value");
        }
        return false;
    }

    private boolean isThumbnail(HashMap object) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute)((HashMap)object).get(TAG_IMAGE_LENGTH);
        object = (ExifAttribute)((HashMap)object).get(TAG_IMAGE_WIDTH);
        if (exifAttribute != null && object != null) {
            int n = exifAttribute.getIntValue(this.mExifByteOrder);
            int n2 = ((ExifAttribute)object).getIntValue(this.mExifByteOrder);
            if (n <= 512 && n2 <= 512) {
                return true;
            }
        }
        return false;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void loadAttributes(InputStream var1_1) throws IOException {
        if (var1_1 == null) throw new NullPointerException("inputstream shouldn't be null");
        for (var2_4 = 0; var2_4 < ExifInterface.EXIF_TAGS.length; ++var2_4) {
            this.mAttributes[var2_4] = new HashMap<K, V>();
        }
        var3_5 = new BufferedInputStream(var1_1, 5000);
        this.mMimeType = this.getMimeType(var3_5);
        var1_1 = new ByteOrderedDataInputStream(var3_5);
        switch (this.mMimeType) {
            default: {
                ** break;
            }
            case 12: {
                this.getHeifAttributes((ByteOrderedDataInputStream)var1_1);
                ** break;
            }
            case 10: {
                this.getRw2Attributes((ByteOrderedDataInputStream)var1_1);
                ** break;
            }
            case 9: {
                this.getRafAttributes((ByteOrderedDataInputStream)var1_1);
                ** break;
            }
            case 7: {
                this.getOrfAttributes((ByteOrderedDataInputStream)var1_1);
                ** break;
            }
            case 4: {
                this.getJpegAttributes((ByteOrderedDataInputStream)var1_1, 0, 0);
                ** break;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: 
            case 5: 
            case 6: 
            case 8: 
            case 11: 
        }
        this.getRawAttributes((ByteOrderedDataInputStream)var1_1);
lbl29: // 7 sources:
        this.setThumbnailData((ByteOrderedDataInputStream)var1_1);
        this.mIsSupportedFile = true;
        this.addDefaultValuesForCompatibility();
        if (ExifInterface.DEBUG == false) return;
        this.printAttributes();
        return;
        {
            catch (IOException | OutOfMemoryError var1_3) {}
            {
                this.mIsSupportedFile = false;
                Log.w("ExifInterface", "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.", var1_3);
                this.addDefaultValuesForCompatibility();
                if (ExifInterface.DEBUG == false) return;
            }
        }
        ** finally { 
lbl42: // 1 sources:
        this.addDefaultValuesForCompatibility();
        if (ExifInterface.DEBUG == false) throw var1_2;
        this.printAttributes();
        throw var1_2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static long parseDateTime(String string2, String string3, String object) {
        long l;
        Object object2;
        block9 : {
            if (string2 == null) return -1L;
            if (!sNonZeroTimePattern.matcher(string2).matches()) return -1L;
            object2 = new ParsePosition(0);
            object2 = sFormatter.parse(string2, (ParsePosition)object2);
            if (object == null) break block9;
            object2 = new StringBuilder();
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object2).append(" ");
            ((StringBuilder)object2).append((String)object);
            string2 = ((StringBuilder)object2).toString();
            object = new ParsePosition(0);
            object2 = sFormatterTz.parse(string2, (ParsePosition)object);
        }
        if (object2 == null) {
            return -1L;
        }
        long l2 = l = ((Date)object2).getTime();
        if (string3 == null) return l2;
        try {
            l2 = Long.parseLong(string3);
        }
        catch (NumberFormatException numberFormatException) {
            return l;
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return -1L;
        }
        while (l2 > 1000L) {
            l2 /= 10L;
        }
        return l + l2;
    }

    private void parseTiffHeaders(ByteOrderedDataInputStream object, int n) throws IOException {
        this.mExifByteOrder = this.readByteOrder((ByteOrderedDataInputStream)object);
        ((ByteOrderedDataInputStream)object).setByteOrder(this.mExifByteOrder);
        int n2 = ((ByteOrderedDataInputStream)object).readUnsignedShort();
        int n3 = this.mMimeType;
        if (n3 != 7 && n3 != 10 && n2 != 42) {
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid start code: ");
            ((StringBuilder)object).append(Integer.toHexString(n2));
            throw new IOException(((StringBuilder)object).toString());
        }
        n3 = ((ByteOrderedDataInputStream)object).readInt();
        if (n3 >= 8 && n3 < n) {
            n = n3 - 8;
            if (n > 0 && ((ByteOrderedDataInputStream)object).skipBytes(n) != n) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Couldn't jump to first Ifd: ");
                ((StringBuilder)object).append(n);
                throw new IOException(((StringBuilder)object).toString());
            }
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid first Ifd offset: ");
        ((StringBuilder)object).append(n3);
        throw new IOException(((StringBuilder)object).toString());
    }

    private void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; ++i) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("The size of tag group[");
            stringBuilder.append(i);
            stringBuilder.append("]: ");
            stringBuilder.append(this.mAttributes[i].size());
            Log.d(TAG, stringBuilder.toString());
            for (Map.Entry entry : this.mAttributes[i].entrySet()) {
                ExifAttribute exifAttribute = (ExifAttribute)entry.getValue();
                StringBuilder stringBuilder2 = new StringBuilder();
                stringBuilder2.append("tagName: ");
                stringBuilder2.append(entry.getKey());
                stringBuilder2.append(", tagType: ");
                stringBuilder2.append(exifAttribute.toString());
                stringBuilder2.append(", tagValue: '");
                stringBuilder2.append(exifAttribute.getStringValue(this.mExifByteOrder));
                stringBuilder2.append("'");
                Log.d(TAG, stringBuilder2.toString());
            }
        }
    }

    private ByteOrder readByteOrder(ByteOrderedDataInputStream object) throws IOException {
        short s = ((ByteOrderedDataInputStream)object).readShort();
        if (s != 18761) {
            if (s == 19789) {
                if (DEBUG) {
                    Log.d(TAG, "readExifSegment: Byte Align MM");
                }
                return ByteOrder.BIG_ENDIAN;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Invalid byte order: ");
            ((StringBuilder)object).append(Integer.toHexString(s));
            throw new IOException(((StringBuilder)object).toString());
        }
        if (DEBUG) {
            Log.d(TAG, "readExifSegment: Byte Align II");
        }
        return ByteOrder.LITTLE_ENDIAN;
    }

    private void readExifSegment(byte[] arrby, int n) throws IOException {
        ByteOrderedDataInputStream byteOrderedDataInputStream = new ByteOrderedDataInputStream(arrby);
        this.parseTiffHeaders(byteOrderedDataInputStream, arrby.length);
        this.readImageFileDirectory(byteOrderedDataInputStream, n);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void readImageFileDirectory(ByteOrderedDataInputStream var1_1, int var2_2) throws IOException {
        this.mHandledIfdOffsets.add(ByteOrderedDataInputStream.access$900((ByteOrderedDataInputStream)var1_1));
        if (ByteOrderedDataInputStream.access$900((ByteOrderedDataInputStream)var1_1) + 2 > ByteOrderedDataInputStream.access$1000((ByteOrderedDataInputStream)var1_1)) {
            return;
        }
        var3_3 = var1_1.readShort();
        if (ByteOrderedDataInputStream.access$900((ByteOrderedDataInputStream)var1_1) + var3_3 * 12 > ByteOrderedDataInputStream.access$1000((ByteOrderedDataInputStream)var1_1)) return;
        if (var3_3 <= 0) {
            return;
        }
        if (ExifInterface.DEBUG) {
            var4_4 = new StringBuilder();
            var4_4.append("numberOfDirectoryEntry: ");
            var4_4.append(var3_3);
            Log.d("ExifInterface", var4_4.toString());
        }
        var5_5 = 0;
        do {
            block48 : {
                block47 : {
                    block46 : {
                        var6_6 = var2_2;
                        if (var5_5 >= var3_3) break;
                        var7_7 = var1_1.readUnsignedShort();
                        var8_8 = var1_1.readUnsignedShort();
                        var9_9 = var1_1.readInt();
                        var10_10 = var1_1.peek() + 4;
                        var12_11 = (ExifTag)ExifInterface.sExifTagMapsForReading[var6_6].get(var7_7);
                        if (ExifInterface.DEBUG) {
                            var4_4 = var12_11 != null ? var12_11.name : null;
                            Log.d("ExifInterface", String.format("ifdType: %d, tagNumber: %d, tagName: %s, dataFormat: %d, numberOfComponents: %d", new Object[]{var2_2, var7_7, var4_4, var8_8, var9_9}));
                        }
                        if (var12_11 != null) break block46;
                        if (ExifInterface.DEBUG) {
                            var4_4 = new StringBuilder();
                            var4_4.append("Skip the tag entry since tag number is not defined: ");
                            var4_4.append(var7_7);
                            Log.d("ExifInterface", var4_4.toString());
                        }
                        ** GOTO lbl65
                    }
                    if (var8_8 > 0 && var8_8 < ((int[])(var4_4 = ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT)).length) {
                        var13_12 = var9_9;
                        var15_13 /* !! */  = var4_4[var8_8];
                        var16_14 = 0;
                        if ((var13_12 *= (long)var15_13 /* !! */ ) >= 0L && var13_12 <= Integer.MAX_VALUE) {
                            var16_14 = 1;
                        } else if (ExifInterface.DEBUG) {
                            var4_4 = new StringBuilder();
                            var4_4.append("Skip the tag entry since the number of components is invalid: ");
                            var4_4.append(var9_9);
                            Log.d("ExifInterface", var4_4.toString());
                        }
                    } else {
                        if (ExifInterface.DEBUG) {
                            var4_4 = new StringBuilder();
                            var4_4.append("Skip the tag entry since data format is invalid: ");
                            var4_4.append(var8_8);
                            Log.d("ExifInterface", var4_4.toString());
                        }
lbl65: // 4 sources:
                        var16_14 = 0;
                        var13_12 = 0L;
                    }
                    if (var16_14 != 0) break block47;
                    var1_1.seek(var10_10);
                    var16_14 = 2;
                    break block48;
                }
                if (var13_12 <= 4L) ** GOTO lbl114
                var15_13 /* !! */  = var1_1.readInt();
                if (ExifInterface.DEBUG) {
                    var4_4 = new StringBuilder();
                    var4_4.append("seek to data offset: ");
                    var4_4.append((int)var15_13 /* !! */ );
                    Log.d("ExifInterface", var4_4.toString());
                }
                if ((var16_14 = this.mMimeType) == 7) {
                    if (var12_11.name == "MakerNote") {
                        this.mOrfMakerNoteOffset = (int)var15_13 /* !! */ ;
                    } else if (var6_6 == 6 && var12_11.name == "ThumbnailImage") {
                        this.mOrfThumbnailOffset = (int)var15_13 /* !! */ ;
                        this.mOrfThumbnailLength = var9_9;
                        var17_15 = ExifAttribute.createUShort(6, this.mExifByteOrder);
                        var4_4 = ExifAttribute.createULong(this.mOrfThumbnailOffset, this.mExifByteOrder);
                        var18_16 = ExifAttribute.createULong(this.mOrfThumbnailLength, this.mExifByteOrder);
                        this.mAttributes[4].put("Compression", var17_15);
                        this.mAttributes[4].put("JPEGInterchangeFormat", var4_4);
                        this.mAttributes[4].put("JPEGInterchangeFormatLength", var18_16);
                    }
                } else if (var16_14 == 10 && var12_11.name == "JpgFromRaw") {
                    this.mRw2JpgFromRawOffset = (int)var15_13 /* !! */ ;
                }
                if ((long)var15_13 /* !! */  + var13_12 > (long)ByteOrderedDataInputStream.access$1000((ByteOrderedDataInputStream)var1_1)) {
                    if (ExifInterface.DEBUG) {
                        var4_4 = new StringBuilder();
                        var4_4.append("Skip the tag entry since data offset is invalid: ");
                        var4_4.append((int)var15_13 /* !! */ );
                        Log.d("ExifInterface", var4_4.toString());
                    }
                    var1_1.seek(var10_10);
                    var16_14 = 2;
                } else {
                    var1_1.seek((long)var15_13 /* !! */ );
lbl114: // 2 sources:
                    var4_4 = ExifInterface.sExifPointerTagMap.get(var7_7);
                    if (ExifInterface.DEBUG) {
                        var17_15 = new StringBuilder();
                        var17_15.append("nextIfdType: ");
                        var17_15.append(var4_4);
                        var17_15.append(" byteCount: ");
                        var17_15.append(var13_12);
                        Log.d("ExifInterface", var17_15.toString());
                    }
                    if (var4_4 != null) {
                        var13_12 = -1L;
                        if (var8_8 != 3) {
                            if (var8_8 != 4) {
                                if (var8_8 != 8) {
                                    if (var8_8 == 9 || var8_8 == 13) {
                                        var13_12 = var1_1.readInt();
                                    }
                                } else {
                                    var13_12 = var1_1.readShort();
                                }
                            } else {
                                var13_12 = var1_1.readUnsignedInt();
                            }
                        } else {
                            var13_12 = var1_1.readUnsignedShort();
                        }
                        if (ExifInterface.DEBUG) {
                            Log.d("ExifInterface", String.format("Offset: %d, tagName: %s", new Object[]{var13_12, var12_11.name}));
                        }
                        if (var13_12 > 0L && var13_12 < (long)ByteOrderedDataInputStream.access$1000((ByteOrderedDataInputStream)var1_1)) {
                            if (!this.mHandledIfdOffsets.contains((int)var13_12)) {
                                var1_1.seek(var13_12);
                                this.readImageFileDirectory((ByteOrderedDataInputStream)var1_1, var4_4.intValue());
                            } else if (ExifInterface.DEBUG) {
                                var12_11 = new StringBuilder();
                                var12_11.append("Skip jump into the IFD since it has already been read: IfdType ");
                                var12_11.append(var4_4);
                                var12_11.append(" (at ");
                                var12_11.append(var13_12);
                                var12_11.append(")");
                                Log.d("ExifInterface", var12_11.toString());
                            }
                        } else if (ExifInterface.DEBUG) {
                            var4_4 = new StringBuilder();
                            var4_4.append("Skip jump into the IFD since its offset is invalid: ");
                            var4_4.append(var13_12);
                            Log.d("ExifInterface", var4_4.toString());
                        }
                        var1_1.seek(var10_10);
                        var16_14 = 2;
                    } else {
                        var16_14 = var1_1.peek();
                        var4_4 = new byte[(int)var13_12];
                        var1_1.readFully((byte[])var4_4);
                        var13_12 = var16_14;
                        var6_6 = 2;
                        var4_4 = new ExifAttribute(var8_8, var9_9, var13_12, (byte[])var4_4);
                        this.mAttributes[var2_2].put(var12_11.name, var4_4);
                        if (var12_11.name == "DNGVersion") {
                            this.mMimeType = 3;
                        }
                        if ((var12_11.name == "Make" || var12_11.name == "Model") && var4_4.getStringValue(this.mExifByteOrder).contains("PENTAX") || var12_11.name == "Compression" && var4_4.getIntValue(this.mExifByteOrder) == 65535) {
                            this.mMimeType = 8;
                        }
                        var16_14 = var6_6;
                        if ((long)var1_1.peek() != var10_10) {
                            var1_1.seek(var10_10);
                            var16_14 = var6_6;
                        }
                    }
                }
            }
            var5_5 = (short)(var5_5 + 1);
        } while (true);
        if (var1_1.peek() + 4 > ByteOrderedDataInputStream.access$1000((ByteOrderedDataInputStream)var1_1)) return;
        var2_2 = var1_1.readInt();
        if (ExifInterface.DEBUG) {
            Log.d("ExifInterface", String.format("nextIfdOffset: %d", new Object[]{var2_2}));
        }
        if ((long)var2_2 > 0L && var2_2 < ByteOrderedDataInputStream.access$1000((ByteOrderedDataInputStream)var1_1)) {
            if (this.mHandledIfdOffsets.contains(var2_2)) {
                if (ExifInterface.DEBUG == false) return;
                var1_1 = new StringBuilder();
                var1_1.append("Stop reading file since re-reading an IFD may cause an infinite loop: ");
                var1_1.append(var2_2);
                Log.d("ExifInterface", var1_1.toString());
                return;
            }
            var1_1.seek(var2_2);
            if (this.mAttributes[4].isEmpty()) {
                this.readImageFileDirectory((ByteOrderedDataInputStream)var1_1, 4);
                return;
            }
            if (this.mAttributes[5].isEmpty() == false) return;
            this.readImageFileDirectory((ByteOrderedDataInputStream)var1_1, 5);
            return;
        }
        if (ExifInterface.DEBUG == false) return;
        var1_1 = new StringBuilder();
        var1_1.append("Stop reading file since a wrong offset may cause an infinite loop: ");
        var1_1.append(var2_2);
        Log.d("ExifInterface", var1_1.toString());
    }

    private void removeAttribute(String string2) {
        for (int i = 0; i < EXIF_TAGS.length; ++i) {
            this.mAttributes[i].remove(string2);
        }
    }

    private void retrieveJpegImageSize(ByteOrderedDataInputStream byteOrderedDataInputStream, int n) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute)this.mAttributes[n].get(TAG_IMAGE_LENGTH);
        ExifAttribute exifAttribute2 = (ExifAttribute)this.mAttributes[n].get(TAG_IMAGE_WIDTH);
        if ((exifAttribute == null || exifAttribute2 == null) && (exifAttribute2 = (ExifAttribute)this.mAttributes[n].get(TAG_JPEG_INTERCHANGE_FORMAT)) != null) {
            this.getJpegAttributes(byteOrderedDataInputStream, exifAttribute2.getIntValue(this.mExifByteOrder), n);
        }
    }

    private void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] arrby;
        if (DEBUG) {
            arrby = new StringBuilder();
            arrby.append("saveJpegAttributes starting with (inputStream: ");
            arrby.append(inputStream);
            arrby.append(", outputStream: ");
            arrby.append(outputStream);
            arrby.append(")");
            Log.d(TAG, arrby.toString());
        }
        inputStream = new DataInputStream(inputStream);
        outputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        if (((DataInputStream)inputStream).readByte() == -1) {
            ((ByteOrderedDataOutputStream)outputStream).writeByte(-1);
            if (((DataInputStream)inputStream).readByte() == -40) {
                ((ByteOrderedDataOutputStream)outputStream).writeByte(-40);
                ((ByteOrderedDataOutputStream)outputStream).writeByte(-1);
                ((ByteOrderedDataOutputStream)outputStream).writeByte(-31);
                this.writeExifSegment((ByteOrderedDataOutputStream)outputStream, 6);
                arrby = new byte[4096];
                while (((DataInputStream)inputStream).readByte() == -1) {
                    int n = ((DataInputStream)inputStream).readByte();
                    if (n != -39 && n != -38) {
                        int n2;
                        if (n != -31) {
                            ((ByteOrderedDataOutputStream)outputStream).writeByte(-1);
                            ((ByteOrderedDataOutputStream)outputStream).writeByte(n);
                            n = ((DataInputStream)inputStream).readUnsignedShort();
                            ((ByteOrderedDataOutputStream)outputStream).writeUnsignedShort(n);
                            if ((n -= 2) >= 0) {
                                while (n > 0 && (n2 = ((DataInputStream)inputStream).read(arrby, 0, Math.min(n, arrby.length))) >= 0) {
                                    ((ByteOrderedDataOutputStream)outputStream).write(arrby, 0, n2);
                                    n -= n2;
                                }
                                continue;
                            }
                            throw new IOException("Invalid length");
                        }
                        n2 = ((DataInputStream)inputStream).readUnsignedShort() - 2;
                        if (n2 >= 0) {
                            byte[] arrby2 = new byte[6];
                            if (n2 >= 6) {
                                if (((DataInputStream)inputStream).read(arrby2) == 6) {
                                    if (Arrays.equals(arrby2, IDENTIFIER_EXIF_APP1)) {
                                        if (((DataInputStream)inputStream).skipBytes(n2 - 6) == n2 - 6) continue;
                                        throw new IOException("Invalid length");
                                    }
                                } else {
                                    throw new IOException("Invalid exif");
                                }
                            }
                            ((ByteOrderedDataOutputStream)outputStream).writeByte(-1);
                            ((ByteOrderedDataOutputStream)outputStream).writeByte(n);
                            ((ByteOrderedDataOutputStream)outputStream).writeUnsignedShort(n2 + 2);
                            n = n2;
                            if (n2 >= 6) {
                                n = n2 - 6;
                                ((ByteOrderedDataOutputStream)outputStream).write(arrby2);
                            }
                            while (n > 0 && (n2 = ((DataInputStream)inputStream).read(arrby, 0, Math.min(n, arrby.length))) >= 0) {
                                ((ByteOrderedDataOutputStream)outputStream).write(arrby, 0, n2);
                                n -= n2;
                            }
                            continue;
                        }
                        throw new IOException("Invalid length");
                    }
                    ((ByteOrderedDataOutputStream)outputStream).writeByte(-1);
                    ((ByteOrderedDataOutputStream)outputStream).writeByte(n);
                    Streams.copy((InputStream)inputStream, (OutputStream)outputStream);
                    return;
                }
                throw new IOException("Invalid marker");
            }
            throw new IOException("Invalid marker");
        }
        throw new IOException("Invalid marker");
    }

    private void setThumbnailData(ByteOrderedDataInputStream byteOrderedDataInputStream) throws IOException {
        block4 : {
            HashMap hashMap;
            block1 : {
                block2 : {
                    block3 : {
                        hashMap = this.mAttributes[4];
                        ExifAttribute exifAttribute = (ExifAttribute)hashMap.get(TAG_COMPRESSION);
                        if (exifAttribute == null) break block1;
                        this.mThumbnailCompression = exifAttribute.getIntValue(this.mExifByteOrder);
                        int n = this.mThumbnailCompression;
                        if (n == 1) break block2;
                        if (n == 6) break block3;
                        if (n == 7) break block2;
                        break block4;
                    }
                    this.handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
                    break block4;
                }
                if (this.isSupportedDataType(hashMap)) {
                    this.handleThumbnailFromStrips(byteOrderedDataInputStream, hashMap);
                }
                break block4;
            }
            this.handleThumbnailFromJfif(byteOrderedDataInputStream, hashMap);
        }
    }

    private void swapBasedOnImageSize(int n, int n2) throws IOException {
        if (!this.mAttributes[n].isEmpty() && !this.mAttributes[n2].isEmpty()) {
            ExifAttribute exifAttribute = (ExifAttribute)this.mAttributes[n].get(TAG_IMAGE_LENGTH);
            Object object = (ExifAttribute)this.mAttributes[n].get(TAG_IMAGE_WIDTH);
            ExifAttribute exifAttribute2 = (ExifAttribute)this.mAttributes[n2].get(TAG_IMAGE_LENGTH);
            HashMap[] arrhashMap = (HashMap[])this.mAttributes[n2].get(TAG_IMAGE_WIDTH);
            if (exifAttribute != null && object != null) {
                if (exifAttribute2 != null && arrhashMap != null) {
                    int n3 = exifAttribute.getIntValue(this.mExifByteOrder);
                    int n4 = ((ExifAttribute)object).getIntValue(this.mExifByteOrder);
                    int n5 = exifAttribute2.getIntValue(this.mExifByteOrder);
                    int n6 = arrhashMap.getIntValue(this.mExifByteOrder);
                    if (n3 < n5 && n4 < n6) {
                        arrhashMap = this.mAttributes;
                        object = arrhashMap[n];
                        arrhashMap[n] = arrhashMap[n2];
                        arrhashMap[n2] = object;
                    }
                } else if (DEBUG) {
                    Log.d(TAG, "Second image does not contain valid size information");
                }
            } else if (DEBUG) {
                Log.d(TAG, "First image does not contain valid size information");
            }
            return;
        }
        if (DEBUG) {
            Log.d(TAG, "Cannot perform swap since only one image data exists");
        }
    }

    private boolean updateAttribute(String string2, ExifAttribute exifAttribute) {
        boolean bl = false;
        for (int i = 0; i < EXIF_TAGS.length; ++i) {
            if (!this.mAttributes[i].containsKey(string2)) continue;
            this.mAttributes[i].put(string2, exifAttribute);
            bl = true;
        }
        return bl;
    }

    private void updateImageSizeValues(ByteOrderedDataInputStream object, int n) throws IOException {
        ExifAttribute exifAttribute = (ExifAttribute)this.mAttributes[n].get(TAG_DEFAULT_CROP_SIZE);
        ExifAttribute exifAttribute2 = (ExifAttribute)this.mAttributes[n].get(TAG_RW2_SENSOR_TOP_BORDER);
        ExifAttribute exifAttribute3 = (ExifAttribute)this.mAttributes[n].get(TAG_RW2_SENSOR_LEFT_BORDER);
        ExifAttribute exifAttribute4 = (ExifAttribute)this.mAttributes[n].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        Object object2 = (Rational[])this.mAttributes[n].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (exifAttribute != null) {
            if (exifAttribute.format == 5) {
                object2 = (Rational[])exifAttribute.getValue(this.mExifByteOrder);
                object = ExifAttribute.createURational(object2[0], this.mExifByteOrder);
                object2 = ExifAttribute.createURational(object2[1], this.mExifByteOrder);
            } else {
                object2 = (int[])exifAttribute.getValue(this.mExifByteOrder);
                object = ExifAttribute.createUShort((int)object2[0], this.mExifByteOrder);
                object2 = ExifAttribute.createUShort((int)object2[1], this.mExifByteOrder);
            }
            this.mAttributes[n].put(TAG_IMAGE_WIDTH, object);
            this.mAttributes[n].put(TAG_IMAGE_LENGTH, object2);
        } else if (exifAttribute2 != null && exifAttribute3 != null && exifAttribute4 != null && object2 != null) {
            int n2 = exifAttribute2.getIntValue(this.mExifByteOrder);
            int n3 = exifAttribute4.getIntValue(this.mExifByteOrder);
            int n4 = object2.getIntValue(this.mExifByteOrder);
            int n5 = exifAttribute3.getIntValue(this.mExifByteOrder);
            if (n3 > n2 && n4 > n5) {
                object2 = ExifAttribute.createUShort(n3 - n2, this.mExifByteOrder);
                object = ExifAttribute.createUShort(n4 - n5, this.mExifByteOrder);
                this.mAttributes[n].put(TAG_IMAGE_LENGTH, object2);
                this.mAttributes[n].put(TAG_IMAGE_WIDTH, object);
            }
        } else {
            this.retrieveJpegImageSize((ByteOrderedDataInputStream)object, n);
        }
    }

    private void validateImages(InputStream arrhashMap) throws IOException {
        this.swapBasedOnImageSize(0, 5);
        this.swapBasedOnImageSize(0, 4);
        this.swapBasedOnImageSize(5, 4);
        ExifAttribute exifAttribute = (ExifAttribute)this.mAttributes[1].get(TAG_PIXEL_X_DIMENSION);
        arrhashMap = (ExifAttribute)this.mAttributes[1].get(TAG_PIXEL_Y_DIMENSION);
        if (exifAttribute != null && arrhashMap != null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, exifAttribute);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, arrhashMap);
        }
        if (this.mAttributes[4].isEmpty() && this.isThumbnail(this.mAttributes[5])) {
            arrhashMap = this.mAttributes;
            arrhashMap[4] = arrhashMap[5];
            arrhashMap[5] = new HashMap();
        }
        if (!this.isThumbnail(this.mAttributes[4])) {
            Log.d(TAG, "No image meets the size requirements of a thumbnail image.");
        }
    }

    private int writeExifSegment(ByteOrderedDataOutputStream byteOrderedDataOutputStream, int n) throws IOException {
        short s;
        int n2;
        int n3;
        int n4;
        Object object = EXIF_TAGS;
        int[] arrn = new int[((ExifTag[][])object).length];
        object = new int[((ExifTag[][])object).length];
        ExifTag[] object22 = EXIF_POINTER_TAGS;
        int n5 = object22.length;
        for (n4 = 0; n4 < n5; ++n4) {
            this.removeAttribute(object22[n4].name);
        }
        this.removeAttribute(ExifInterface.JPEG_INTERCHANGE_FORMAT_TAG.name);
        this.removeAttribute(ExifInterface.JPEG_INTERCHANGE_FORMAT_LENGTH_TAG.name);
        for (n4 = 0; n4 < EXIF_TAGS.length; ++n4) {
            Object[] arrobject = this.mAttributes[n4].entrySet().toArray();
            n3 = arrobject.length;
            for (n5 = 0; n5 < n3; ++n5) {
                Map.Entry entry = (Map.Entry)arrobject[n5];
                if (entry.getValue() != null) continue;
                this.mAttributes[n4].remove(entry.getKey());
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(ExifInterface.EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(ExifInterface.EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(ExifInterface.EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (this.mHasThumbnail) {
            this.mAttributes[4].put(ExifInterface.JPEG_INTERCHANGE_FORMAT_TAG.name, ExifAttribute.createULong(0L, this.mExifByteOrder));
            this.mAttributes[4].put(ExifInterface.JPEG_INTERCHANGE_FORMAT_LENGTH_TAG.name, ExifAttribute.createULong(this.mThumbnailLength, this.mExifByteOrder));
        }
        for (n4 = 0; n4 < EXIF_TAGS.length; ++n4) {
            n3 = 0;
            Iterator iterator = this.mAttributes[n4].entrySet().iterator();
            while (iterator.hasNext()) {
                n2 = ((ExifAttribute)iterator.next().getValue()).size();
                n5 = n3;
                if (n2 > 4) {
                    n5 = n3 + n2;
                }
                n3 = n5;
            }
            object[n4] = object[n4] + n3;
        }
        n4 = 8;
        for (n5 = 0; n5 < EXIF_TAGS.length; ++n5) {
            n3 = n4;
            if (!this.mAttributes[n5].isEmpty()) {
                arrn[n5] = n4;
                n3 = n4 + (this.mAttributes[n5].size() * 12 + 2 + 4 + object[n5]);
            }
            n4 = n3;
        }
        n5 = n4;
        if (this.mHasThumbnail) {
            this.mAttributes[4].put(ExifInterface.JPEG_INTERCHANGE_FORMAT_TAG.name, ExifAttribute.createULong(n4, this.mExifByteOrder));
            this.mThumbnailOffset = n + n4;
            n5 = n4 + this.mThumbnailLength;
        }
        n2 = n5 + 8;
        if (DEBUG) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("totalSize length: ");
            stringBuilder.append(n2);
            Log.d(TAG, stringBuilder.toString());
            for (n = 0; n < EXIF_TAGS.length; ++n) {
                Log.d(TAG, String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d", n, arrn[n], this.mAttributes[n].size(), (int)object[n]));
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(ExifInterface.EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(arrn[1], this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(ExifInterface.EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(arrn[2], this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(ExifInterface.EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(arrn[3], this.mExifByteOrder));
        }
        byteOrderedDataOutputStream.writeUnsignedShort(n2);
        byteOrderedDataOutputStream.write(IDENTIFIER_EXIF_APP1);
        if (this.mExifByteOrder == ByteOrder.BIG_ENDIAN) {
            n = 19789;
            s = n;
        } else {
            n = 18761;
            s = n;
        }
        byteOrderedDataOutputStream.writeShort(s);
        byteOrderedDataOutputStream.setByteOrder(this.mExifByteOrder);
        byteOrderedDataOutputStream.writeUnsignedShort(42);
        byteOrderedDataOutputStream.writeUnsignedInt(8L);
        for (n = 0; n < EXIF_TAGS.length; ++n) {
            if (this.mAttributes[n].isEmpty()) continue;
            byteOrderedDataOutputStream.writeUnsignedShort(this.mAttributes[n].size());
            n4 = arrn[n] + 2 + this.mAttributes[n].size() * 12 + 4;
            for (Map.Entry entry : this.mAttributes[n].entrySet()) {
                n5 = ((ExifTag)ExifInterface.sExifTagMapsForWriting[n].get(entry.getKey())).number;
                ExifAttribute exifAttribute = (ExifAttribute)entry.getValue();
                n3 = exifAttribute.size();
                byteOrderedDataOutputStream.writeUnsignedShort(n5);
                byteOrderedDataOutputStream.writeUnsignedShort(exifAttribute.format);
                byteOrderedDataOutputStream.writeInt(exifAttribute.numberOfComponents);
                if (n3 > 4) {
                    byteOrderedDataOutputStream.writeUnsignedInt(n4);
                    n5 = n4 + n3;
                } else {
                    byteOrderedDataOutputStream.write(exifAttribute.bytes);
                    n5 = n4;
                    if (n3 < 4) {
                        do {
                            n5 = n4;
                            if (n3 >= 4) break;
                            byteOrderedDataOutputStream.writeByte(0);
                            ++n3;
                        } while (true);
                    }
                }
                n4 = n5;
            }
            if (n == 0 && !this.mAttributes[4].isEmpty()) {
                byteOrderedDataOutputStream.writeUnsignedInt(arrn[4]);
            } else {
                byteOrderedDataOutputStream.writeUnsignedInt(0L);
            }
            Iterator iterator = this.mAttributes[n].entrySet().iterator();
            while (iterator.hasNext()) {
                object = (ExifAttribute)iterator.next().getValue();
                if (((ExifAttribute)object).bytes.length <= 4) continue;
                byteOrderedDataOutputStream.write(((ExifAttribute)object).bytes, 0, ((ExifAttribute)object).bytes.length);
            }
        }
        if (this.mHasThumbnail) {
            byteOrderedDataOutputStream.write(this.getThumbnailBytes());
        }
        byteOrderedDataOutputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        return n2;
    }

    public double getAltitude(double d) {
        double d2 = this.getAttributeDouble(TAG_GPS_ALTITUDE, -1.0);
        int n = -1;
        int n2 = this.getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (d2 >= 0.0 && n2 >= 0) {
            if (n2 != 1) {
                n = 1;
            }
            return (double)n * d2;
        }
        return d;
    }

    public String getAttribute(String arrrational) {
        if (arrrational != null) {
            ExifAttribute exifAttribute = this.getExifAttribute((String)arrrational);
            if (exifAttribute != null) {
                if (!sTagSetForCompatibility.contains(arrrational)) {
                    return exifAttribute.getStringValue(this.mExifByteOrder);
                }
                if (arrrational.equals(TAG_GPS_TIMESTAMP)) {
                    if (exifAttribute.format != 5 && exifAttribute.format != 10) {
                        return null;
                    }
                    arrrational = (Rational[])exifAttribute.getValue(this.mExifByteOrder);
                    if (arrrational.length != 3) {
                        return null;
                    }
                    return String.format("%02d:%02d:%02d", (int)((float)arrrational[0].numerator / (float)arrrational[0].denominator), (int)((float)arrrational[1].numerator / (float)arrrational[1].denominator), (int)((float)arrrational[2].numerator / (float)arrrational[2].denominator));
                }
                try {
                    arrrational = Double.toString(exifAttribute.getDoubleValue(this.mExifByteOrder));
                    return arrrational;
                }
                catch (NumberFormatException numberFormatException) {
                    return null;
                }
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    public byte[] getAttributeBytes(String object) {
        if (object != null) {
            if ((object = this.getExifAttribute((String)object)) != null) {
                return ((ExifAttribute)object).bytes;
            }
            return null;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    public double getAttributeDouble(String object, double d) {
        if (object != null) {
            if ((object = this.getExifAttribute((String)object)) == null) {
                return d;
            }
            try {
                double d2 = ((ExifAttribute)object).getDoubleValue(this.mExifByteOrder);
                return d2;
            }
            catch (NumberFormatException numberFormatException) {
                return d;
            }
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    public int getAttributeInt(String object, int n) {
        if (object != null) {
            if ((object = this.getExifAttribute((String)object)) == null) {
                return n;
            }
            try {
                int n2 = ((ExifAttribute)object).getIntValue(this.mExifByteOrder);
                return n2;
            }
            catch (NumberFormatException numberFormatException) {
                return n;
            }
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    public long[] getAttributeRange(String object) {
        if (object != null) {
            if (!this.mModified) {
                if ((object = this.getExifAttribute((String)object)) != null) {
                    return new long[]{((ExifAttribute)object).bytesOffset, ((ExifAttribute)object).bytes.length};
                }
                return null;
            }
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    @UnsupportedAppUsage
    public long getDateTime() {
        return ExifInterface.parseDateTime(this.getAttribute(TAG_DATETIME), this.getAttribute(TAG_SUBSEC_TIME), this.getAttribute(TAG_OFFSET_TIME));
    }

    public long getDateTimeDigitized() {
        return ExifInterface.parseDateTime(this.getAttribute(TAG_DATETIME_DIGITIZED), this.getAttribute("SubSecTimeDigitized"), this.getAttribute(TAG_OFFSET_TIME_DIGITIZED));
    }

    @UnsupportedAppUsage
    public long getDateTimeOriginal() {
        return ExifInterface.parseDateTime(this.getAttribute(TAG_DATETIME_ORIGINAL), this.getAttribute("SubSecTimeOriginal"), this.getAttribute(TAG_OFFSET_TIME_ORIGINAL));
    }

    @UnsupportedAppUsage
    public long getGpsDateTime() {
        String string2 = this.getAttribute(TAG_GPS_DATESTAMP);
        Object object = this.getAttribute(TAG_GPS_TIMESTAMP);
        if (string2 != null && object != null && (sNonZeroTimePattern.matcher(string2).matches() || sNonZeroTimePattern.matcher((CharSequence)object).matches())) {
            Object object2;
            block4 : {
                object2 = new StringBuilder();
                ((StringBuilder)object2).append(string2);
                ((StringBuilder)object2).append(' ');
                ((StringBuilder)object2).append((String)object);
                object2 = ((StringBuilder)object2).toString();
                object = new ParsePosition(0);
                try {
                    object2 = sFormatter.parse((String)object2, (ParsePosition)object);
                    if (object2 != null) break block4;
                    return -1L;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    return -1L;
                }
            }
            long l = ((Date)object2).getTime();
            return l;
        }
        return -1L;
    }

    public boolean getLatLong(float[] arrf) {
        String string2 = this.getAttribute(TAG_GPS_LATITUDE);
        String string3 = this.getAttribute(TAG_GPS_LATITUDE_REF);
        String string4 = this.getAttribute(TAG_GPS_LONGITUDE);
        String string5 = this.getAttribute(TAG_GPS_LONGITUDE_REF);
        if (string2 != null && string3 != null && string4 != null && string5 != null) {
            try {
                arrf[0] = ExifInterface.convertRationalLatLonToFloat(string2, string3);
                arrf[1] = ExifInterface.convertRationalLatLonToFloat(string4, string5);
                return true;
            }
            catch (IllegalArgumentException illegalArgumentException) {
                // empty catch block
            }
        }
        return false;
    }

    public byte[] getThumbnail() {
        int n = this.mThumbnailCompression;
        if (n != 6 && n != 7) {
            return null;
        }
        return this.getThumbnailBytes();
    }

    public Bitmap getThumbnailBitmap() {
        int n;
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes == null) {
            this.mThumbnailBytes = this.getThumbnailBytes();
        }
        if ((n = this.mThumbnailCompression) != 6 && n != 7) {
            if (n == 1) {
                Object object;
                int[] arrn = new int[this.mThumbnailBytes.length / 3];
                for (n = 0; n < arrn.length; ++n) {
                    object = this.mThumbnailBytes;
                    arrn[n] = (object[n * 3] << 16) + 0 + (object[n * 3 + 1] << 8) + object[n * 3 + 2];
                }
                ExifAttribute exifAttribute = (ExifAttribute)this.mAttributes[4].get(TAG_IMAGE_LENGTH);
                object = (ExifAttribute)this.mAttributes[4].get(TAG_IMAGE_WIDTH);
                if (exifAttribute != null && object != null) {
                    n = exifAttribute.getIntValue(this.mExifByteOrder);
                    return Bitmap.createBitmap(arrn, ((ExifAttribute)object).getIntValue(this.mExifByteOrder), n, Bitmap.Config.ARGB_8888);
                }
            }
            return null;
        }
        return BitmapFactory.decodeByteArray(this.mThumbnailBytes, 0, this.mThumbnailLength);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public byte[] getThumbnailBytes() {
        block13 : {
            block12 : {
                block11 : {
                    block14 : {
                        if (!this.mHasThumbnail) {
                            return null;
                        }
                        var1_1 = this.mThumbnailBytes;
                        if (var1_1 != null) {
                            return var1_1;
                        }
                        var2_4 /* !! */  = null;
                        var3_5 = null;
                        var1_1 = null;
                        var4_6 = var2_4 /* !! */ ;
                        var5_7 = var3_5;
                        if (this.mAssetInputStream == null) break block11;
                        var4_6 = var2_4 /* !! */ ;
                        var5_7 = var3_5;
                        var4_6 = var1_1 = this.mAssetInputStream;
                        var5_7 = var1_1;
                        if (!var1_1.markSupported()) break block14;
                        var4_6 = var1_1;
                        var5_7 = var1_1;
                        var1_1.reset();
                        ** GOTO lbl51
                    }
                    var4_6 = var1_1;
                    var5_7 = var1_1;
                    Log.d("ExifInterface", "Cannot read thumbnail from inputstream without mark/reset support");
                    IoUtils.closeQuietly((AutoCloseable)var1_1);
                    return null;
                }
                var4_6 = var2_4 /* !! */ ;
                var5_7 = var3_5;
                if (this.mFilename != null) {
                    var4_6 = var2_4 /* !! */ ;
                    var5_7 = var3_5;
                    var1_1 = new FileInputStream(this.mFilename);
                } else {
                    var4_6 = var2_4 /* !! */ ;
                    var5_7 = var3_5;
                    if (this.mSeekableFileDescriptor != null) {
                        var4_6 = var2_4 /* !! */ ;
                        var5_7 = var3_5;
                        var1_1 = Os.dup((FileDescriptor)this.mSeekableFileDescriptor);
                        var4_6 = var2_4 /* !! */ ;
                        var5_7 = var3_5;
                        Os.lseek((FileDescriptor)var1_1, (long)0L, (int)OsConstants.SEEK_SET);
                        var4_6 = var2_4 /* !! */ ;
                        var5_7 = var3_5;
                        var1_1 = new FileInputStream((FileDescriptor)var1_1, true);
                    }
                }
lbl51: // 5 sources:
                if (var1_1 == null) ** GOTO lbl89
                var4_6 = var1_1;
                var5_7 = var1_1;
                var6_8 = var1_1.skip(this.mThumbnailOffset);
                var4_6 = var1_1;
                var5_7 = var1_1;
                var8_9 = this.mThumbnailOffset;
                if (var6_8 != (long)var8_9) ** GOTO lbl81
                var4_6 = var1_1;
                var5_7 = var1_1;
                var2_4 /* !! */  = new byte[this.mThumbnailLength];
                var4_6 = var1_1;
                var5_7 = var1_1;
                if (var1_1.read(var2_4 /* !! */ ) != this.mThumbnailLength) break block12;
                var4_6 = var1_1;
                var5_7 = var1_1;
                this.mThumbnailBytes = var2_4 /* !! */ ;
                IoUtils.closeQuietly((AutoCloseable)var1_1);
                return var2_4 /* !! */ ;
            }
            var4_6 = var1_1;
            var5_7 = var1_1;
            try {
                var4_6 = var1_1;
                var5_7 = var1_1;
                var2_4 /* !! */  = new IOException("Corrupted image");
                var4_6 = var1_1;
                var5_7 = var1_1;
                throw var2_4 /* !! */ ;
lbl81: // 1 sources:
                var4_6 = var1_1;
                var5_7 = var1_1;
                var4_6 = var1_1;
                var5_7 = var1_1;
                var2_4 /* !! */  = new IOException("Corrupted image");
                var4_6 = var1_1;
                var5_7 = var1_1;
                throw var2_4 /* !! */ ;
lbl89: // 1 sources:
                var4_6 = var1_1;
                var5_7 = var1_1;
                var4_6 = var1_1;
                var5_7 = var1_1;
                var2_4 /* !! */  = new FileNotFoundException();
                var4_6 = var1_1;
                var5_7 = var1_1;
                throw var2_4 /* !! */ ;
            }
            catch (Throwable var1_2) {
                break block13;
            }
            catch (ErrnoException | IOException var1_3) {
                var4_6 = var5_7;
                Log.d("ExifInterface", "Encountered exception while getting thumbnail", var1_3);
                IoUtils.closeQuietly(var5_7);
                return null;
            }
        }
        IoUtils.closeQuietly(var4_6);
        throw var1_2;
    }

    public long[] getThumbnailRange() {
        if (!this.mModified) {
            if (this.mHasThumbnail) {
                return new long[]{this.mThumbnailOffset, this.mThumbnailLength};
            }
            return null;
        }
        throw new IllegalStateException("The underlying file has been modified since being parsed");
    }

    public boolean hasAttribute(String string2) {
        boolean bl = this.getExifAttribute(string2) != null;
        return bl;
    }

    public boolean hasThumbnail() {
        return this.mHasThumbnail;
    }

    public boolean isThumbnailCompressed() {
        if (!this.mHasThumbnail) {
            return false;
        }
        int n = this.mThumbnailCompression;
        return n == 6 || n == 7;
        {
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void saveAttributes() throws IOException {
        Throwable throwable22222;
        Object object4;
        Object object5;
        Object object6;
        Object object9;
        Object object3;
        Object object7;
        Object object8;
        Object object2;
        Object object;
        Throwable throwable3222;
        File file;
        StringBuilder stringBuilder;
        block16 : {
            if (!this.mIsSupportedFile) throw new IOException("ExifInterface only supports saving attributes on JPEG formats.");
            if (this.mMimeType != 4) throw new IOException("ExifInterface only supports saving attributes on JPEG formats.");
            if (this.mIsInputStream) throw new IOException("ExifInterface does not support saving attributes for the current input.");
            if (this.mSeekableFileDescriptor == null) {
                if (this.mFilename == null) throw new IOException("ExifInterface does not support saving attributes for the current input.");
            }
            this.mModified = true;
            this.mThumbnailBytes = this.getThumbnail();
            object8 = null;
            file = null;
            object7 = null;
            object3 = null;
            object6 = null;
            stringBuilder = null;
            object9 = null;
            object = object8;
            object5 = object3;
            object2 = file;
            object4 = object6;
            if (this.mFilename != null) {
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                Serializable serializable = new StringBuilder();
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                ((StringBuilder)serializable).append(this.mFilename);
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                ((StringBuilder)serializable).append(".tmp");
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                object9 = new File(((StringBuilder)serializable).toString());
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                serializable = new File(this.mFilename);
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                if (!((File)serializable).renameTo((File)object9)) {
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    stringBuilder = new StringBuilder();
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    stringBuilder.append("Could'nt rename to ");
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    stringBuilder.append(((File)object9).getAbsolutePath());
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    object7 = new IOException(stringBuilder.toString());
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    throw object7;
                }
            } else {
                object = object8;
                object5 = object3;
                object2 = file;
                object4 = object6;
                if (this.mSeekableFileDescriptor != null) {
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    object7 = File.createTempFile("temp", "jpg");
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    Os.lseek((FileDescriptor)this.mSeekableFileDescriptor, (long)0L, (int)OsConstants.SEEK_SET);
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    object = object8;
                    object5 = object3;
                    object2 = file;
                    object4 = object6;
                    object = object9 = new FileInputStream(this.mSeekableFileDescriptor);
                    object5 = object3;
                    object2 = object9;
                    object4 = object6;
                    object = object9;
                    object5 = object3;
                    object2 = object9;
                    object4 = object6;
                    object3 = object8 = new FileOutputStream((File)object7);
                    object = object9;
                    object5 = object3;
                    object2 = object9;
                    object4 = object3;
                    Streams.copy((InputStream)object9, (OutputStream)object3);
                    object6 = object7;
                    break block16;
                }
            }
            object6 = object9;
            object3 = stringBuilder;
            object9 = object7;
        }
        IoUtils.closeQuietly((AutoCloseable)object9);
        IoUtils.closeQuietly((AutoCloseable)object3);
        stringBuilder = null;
        object2 = null;
        object8 = null;
        file = null;
        object9 = null;
        object3 = object2;
        object = object8;
        object4 = stringBuilder;
        object5 = file;
        object3 = object2;
        object = object8;
        object4 = stringBuilder;
        object5 = file;
        object3 = object2 = (object7 = new FileInputStream((File)object6));
        object = object8;
        object4 = object2;
        object5 = file;
        if (this.mFilename != null) {
            object3 = object2;
            object = object8;
            object4 = object2;
            object5 = file;
            object3 = object2;
            object = object8;
            object4 = object2;
            object5 = file;
            object9 = new FileOutputStream(this.mFilename);
        } else {
            object3 = object2;
            object = object8;
            object4 = object2;
            object5 = file;
            if (this.mSeekableFileDescriptor != null) {
                object3 = object2;
                object = object8;
                object4 = object2;
                object5 = file;
                Os.lseek((FileDescriptor)this.mSeekableFileDescriptor, (long)0L, (int)OsConstants.SEEK_SET);
                object3 = object2;
                object = object8;
                object4 = object2;
                object5 = file;
                object9 = new FileOutputStream(this.mSeekableFileDescriptor);
            }
        }
        object3 = object2;
        object = object9;
        object4 = object2;
        object5 = object9;
        this.saveJpegAttributes((InputStream)object2, (OutputStream)object9);
        IoUtils.closeQuietly((AutoCloseable)object2);
        IoUtils.closeQuietly((AutoCloseable)object9);
        ((File)object6).delete();
        this.mThumbnailBytes = null;
        return;
        {
            catch (Throwable throwable22222) {
            }
            catch (ErrnoException errnoException) {}
            object3 = object4;
            object = object5;
            {
                throw errnoException.rethrowAsIOException();
            }
        }
        IoUtils.closeQuietly((AutoCloseable)object3);
        IoUtils.closeQuietly((AutoCloseable)object);
        ((File)object6).delete();
        throw throwable22222;
        {
            catch (Throwable throwable3222) {
            }
            catch (ErrnoException errnoException) {}
            object = object2;
            object5 = object4;
            {
                throw errnoException.rethrowAsIOException();
            }
        }
        IoUtils.closeQuietly(object);
        IoUtils.closeQuietly(object5);
        throw throwable3222;
    }

    public void setAttribute(String string2, String object) {
        block31 : {
            Object object2 = this;
            Object object3 = object;
            if (string2 == null) break block31;
            int n = 1;
            CharSequence charSequence = object3;
            if (object3 != null) {
                charSequence = object3;
                if (sTagSetForCompatibility.contains(string2)) {
                    if (string2.equals(TAG_GPS_TIMESTAMP)) {
                        object = sGpsTimestampPattern.matcher((CharSequence)object3);
                        if (!object.find()) {
                            object = new StringBuilder();
                            object.append("Invalid value for ");
                            object.append(string2);
                            object.append(" : ");
                            object.append((String)object3);
                            Log.w(TAG, object.toString());
                            return;
                        }
                        charSequence = new StringBuilder();
                        ((StringBuilder)charSequence).append(Integer.parseInt(object.group(1)));
                        ((StringBuilder)charSequence).append("/1,");
                        ((StringBuilder)charSequence).append(Integer.parseInt(object.group(2)));
                        ((StringBuilder)charSequence).append("/1,");
                        ((StringBuilder)charSequence).append(Integer.parseInt(object.group(3)));
                        ((StringBuilder)charSequence).append("/1");
                        charSequence = ((StringBuilder)charSequence).toString();
                    } else {
                        try {
                            double d = Double.parseDouble((String)object);
                            object = new StringBuilder();
                            object.append((long)(10000.0 * d));
                            object.append("/10000");
                            charSequence = object.toString();
                        }
                        catch (NumberFormatException numberFormatException) {
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Invalid value for ");
                            stringBuilder.append(string2);
                            stringBuilder.append(" : ");
                            stringBuilder.append((String)object3);
                            Log.w(TAG, stringBuilder.toString());
                            return;
                        }
                    }
                }
            }
            int n2 = 0;
            object = object2;
            while (n2 < EXIF_TAGS.length) {
                int n3;
                block33 : {
                    block34 : {
                        Object object4;
                        Object object5;
                        String[] arrstring;
                        block38 : {
                            block36 : {
                                block39 : {
                                    block37 : {
                                        block35 : {
                                            block32 : {
                                                if (n2 != 4 || object.mHasThumbnail) break block32;
                                                n3 = n;
                                                n = n2;
                                                object2 = object;
                                                n2 = n3;
                                                break block33;
                                            }
                                            object3 = sExifTagMapsForWriting[n2].get(string2);
                                            if (object3 == null) break block34;
                                            if (charSequence != null) break block35;
                                            object.mAttributes[n2].remove(string2);
                                            n3 = n;
                                            n = n2;
                                            object2 = object;
                                            n2 = n3;
                                            break block33;
                                        }
                                        object2 = (ExifTag)object3;
                                        arrstring = ExifInterface.guessDataFormat((String)charSequence);
                                        if (((ExifTag)object2).primaryFormat == (Integer)arrstring.first || ((ExifTag)object2).primaryFormat == (Integer)arrstring.second) break block36;
                                        if (((ExifTag)object2).secondaryFormat == -1 || ((ExifTag)object2).secondaryFormat != (Integer)arrstring.first && ((ExifTag)object2).secondaryFormat != (Integer)arrstring.second) break block37;
                                        n3 = ((ExifTag)object2).secondaryFormat;
                                        break block38;
                                    }
                                    if (((ExifTag)object2).primaryFormat == n || ((ExifTag)object2).primaryFormat == 7 || ((ExifTag)object2).primaryFormat == 2) break block39;
                                    if (DEBUG) {
                                        object4 = new StringBuilder();
                                        ((StringBuilder)object4).append("Given tag (");
                                        ((StringBuilder)object4).append(string2);
                                        ((StringBuilder)object4).append(") value didn't match with one of expected formats: ");
                                        ((StringBuilder)object4).append(IFD_FORMAT_NAMES[((ExifTag)object2).primaryFormat]);
                                        n3 = ((ExifTag)object2).secondaryFormat;
                                        object3 = "";
                                        if (n3 == -1) {
                                            object2 = "";
                                        } else {
                                            object5 = new StringBuilder();
                                            ((StringBuilder)object5).append(", ");
                                            ((StringBuilder)object5).append(IFD_FORMAT_NAMES[((ExifTag)object2).secondaryFormat]);
                                            object2 = ((StringBuilder)object5).toString();
                                        }
                                        ((StringBuilder)object4).append((String)object2);
                                        ((StringBuilder)object4).append(" (guess: ");
                                        ((StringBuilder)object4).append(IFD_FORMAT_NAMES[(Integer)arrstring.first]);
                                        if ((Integer)arrstring.second == -1) {
                                            object2 = object3;
                                        } else {
                                            object2 = new StringBuilder();
                                            ((StringBuilder)object2).append(", ");
                                            ((StringBuilder)object2).append(IFD_FORMAT_NAMES[(Integer)arrstring.second]);
                                            object2 = ((StringBuilder)object2).toString();
                                        }
                                        ((StringBuilder)object4).append((String)object2);
                                        ((StringBuilder)object4).append(")");
                                        Log.d(TAG, ((StringBuilder)object4).toString());
                                        n3 = n2;
                                        object2 = object;
                                        n2 = n;
                                        n = n3;
                                    } else {
                                        n3 = n;
                                        n = n2;
                                        object2 = object;
                                        n2 = n3;
                                    }
                                    break block33;
                                }
                                n3 = ((ExifTag)object2).primaryFormat;
                                break block38;
                            }
                            n3 = ((ExifTag)object2).primaryFormat;
                        }
                        switch (n3) {
                            default: {
                                int n4 = n;
                                int n5 = n2;
                                object2 = object;
                                n2 = n4;
                                n = n5;
                                if (DEBUG) {
                                    object2 = new StringBuilder();
                                    ((StringBuilder)object2).append("Data format isn't one of expected formats: ");
                                    ((StringBuilder)object2).append(n3);
                                    Log.d(TAG, ((StringBuilder)object2).toString());
                                    object2 = object;
                                    n2 = n4;
                                    n = n5;
                                    break;
                                }
                                break block33;
                            }
                            case 12: {
                                object3 = ((String)charSequence).split(",");
                                object2 = new double[((String[])object3).length];
                                for (n3 = 0; n3 < ((String[])object3).length; ++n3) {
                                    object2[n3] = (String)Double.parseDouble(object3[n3]);
                                }
                                object.mAttributes[n2].put(string2, ExifAttribute.createDouble((double[])object2, object.mExifByteOrder));
                                n3 = n2;
                                object2 = object;
                                n2 = n;
                                n = n3;
                                break;
                            }
                            case 10: {
                                object2 = ((String)charSequence).split(",");
                                object = new Rational[((String[])object2).length];
                                for (n3 = 0; n3 < ((String[])object2).length; ++n3) {
                                    object3 = object2[n3].split("/");
                                    object[n3] = new Rational((long)Double.parseDouble(object3[0]), (long)Double.parseDouble(object3[n]));
                                    n = 1;
                                }
                                n = n2;
                                object2 = this;
                                ((ExifInterface)object2).mAttributes[n].put(string2, ExifAttribute.createSRational(object, ((ExifInterface)object2).mExifByteOrder));
                                n2 = 1;
                                break;
                            }
                            case 9: {
                                n = n2;
                                object2 = ((String)charSequence).split(",");
                                object3 = new int[((String[])object2).length];
                                for (n2 = 0; n2 < ((String[])object2).length; ++n2) {
                                    object3[n2] = (String)Integer.parseInt(object2[n2]);
                                }
                                object.mAttributes[n].put(string2, ExifAttribute.createSLong((int[])object3, object.mExifByteOrder));
                                n2 = 1;
                                object2 = object;
                                break;
                            }
                            case 5: {
                                n = n2;
                                arrstring = ((String)charSequence).split(",");
                                object4 = new Rational[arrstring.length];
                                for (n2 = 0; n2 < arrstring.length; ++n2) {
                                    object5 = arrstring[n2].split("/");
                                    object4[n2] = new Rational((long)Double.parseDouble(object5[0]), (long)Double.parseDouble(object5[1]));
                                }
                                n2 = 1;
                                object.mAttributes[n].put(string2, ExifAttribute.createURational((Rational[])object4, object.mExifByteOrder));
                                object2 = object;
                                break;
                            }
                            case 4: {
                                n3 = n2;
                                object2 = ((String)charSequence).split(",");
                                object3 = new long[((String[])object2).length];
                                for (n2 = 0; n2 < ((String[])object2).length; ++n2) {
                                    object3[n2] = (String)Long.parseLong(object2[n2]);
                                }
                                object.mAttributes[n3].put(string2, ExifAttribute.createULong((long[])object3, object.mExifByteOrder));
                                object2 = object;
                                n2 = n;
                                n = n3;
                                break;
                            }
                            case 3: {
                                n3 = n2;
                                object3 = ((String)charSequence).split(",");
                                object2 = new int[((String[])object3).length];
                                for (n2 = 0; n2 < ((String[])object3).length; ++n2) {
                                    object2[n2] = Integer.parseInt(object3[n2]);
                                }
                                object.mAttributes[n3].put(string2, ExifAttribute.createUShort((int[])object2, object.mExifByteOrder));
                                object2 = object;
                                n2 = n;
                                n = n3;
                                break;
                            }
                            case 2: 
                            case 7: {
                                n3 = n;
                                n = n2;
                                object.mAttributes[n].put(string2, ExifAttribute.createString((String)charSequence));
                                object2 = object;
                                n2 = n3;
                                break;
                            }
                            case 1: {
                                n3 = n2;
                                object.mAttributes[n3].put(string2, ExifAttribute.createByte((String)charSequence));
                                object2 = object;
                                n2 = n;
                                n = n3;
                                break;
                            }
                        }
                        break block33;
                    }
                    n3 = n;
                    n = n2;
                    n2 = n3;
                    object2 = object;
                }
                n3 = n + 1;
                object = object2;
                n = n2;
                n2 = n3;
            }
            return;
        }
        throw new NullPointerException("tag shouldn't be null");
    }

    private static class ByteOrderedDataInputStream
    extends InputStream
    implements DataInput {
        private static final ByteOrder BIG_ENDIAN;
        private static final ByteOrder LITTLE_ENDIAN;
        private ByteOrder mByteOrder = ByteOrder.BIG_ENDIAN;
        private DataInputStream mDataInputStream;
        private InputStream mInputStream;
        private final int mLength;
        private int mPosition;

        static {
            LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
            BIG_ENDIAN = ByteOrder.BIG_ENDIAN;
        }

        public ByteOrderedDataInputStream(InputStream inputStream) throws IOException {
            this.mInputStream = inputStream;
            this.mDataInputStream = new DataInputStream(inputStream);
            this.mLength = this.mDataInputStream.available();
            this.mPosition = 0;
            this.mDataInputStream.mark(this.mLength);
        }

        public ByteOrderedDataInputStream(byte[] arrby) throws IOException {
            this(new ByteArrayInputStream(arrby));
        }

        static /* synthetic */ int access$1000(ByteOrderedDataInputStream byteOrderedDataInputStream) {
            return byteOrderedDataInputStream.mLength;
        }

        static /* synthetic */ int access$900(ByteOrderedDataInputStream byteOrderedDataInputStream) {
            return byteOrderedDataInputStream.mPosition;
        }

        @Override
        public int available() throws IOException {
            return this.mDataInputStream.available();
        }

        public int getLength() {
            return this.mLength;
        }

        public int peek() {
            return this.mPosition;
        }

        @Override
        public int read() throws IOException {
            ++this.mPosition;
            return this.mDataInputStream.read();
        }

        @Override
        public boolean readBoolean() throws IOException {
            ++this.mPosition;
            return this.mDataInputStream.readBoolean();
        }

        @Override
        public byte readByte() throws IOException {
            ++this.mPosition;
            if (this.mPosition <= this.mLength) {
                int n = this.mDataInputStream.read();
                if (n >= 0) {
                    return (byte)n;
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        @Override
        public char readChar() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        @Override
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(this.readLong());
        }

        @Override
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(this.readInt());
        }

        @Override
        public void readFully(byte[] arrby) throws IOException {
            this.mPosition += arrby.length;
            if (this.mPosition <= this.mLength) {
                if (this.mDataInputStream.read(arrby, 0, arrby.length) == arrby.length) {
                    return;
                }
                throw new IOException("Couldn't read up to the length of buffer");
            }
            throw new EOFException();
        }

        @Override
        public void readFully(byte[] arrby, int n, int n2) throws IOException {
            this.mPosition += n2;
            if (this.mPosition <= this.mLength) {
                if (this.mDataInputStream.read(arrby, n, n2) == n2) {
                    return;
                }
                throw new IOException("Couldn't read up to the length of buffer");
            }
            throw new EOFException();
        }

        @Override
        public int readInt() throws IOException {
            this.mPosition += 4;
            if (this.mPosition <= this.mLength) {
                int n;
                int n2;
                int n3;
                int n4 = this.mDataInputStream.read();
                if ((n4 | (n = this.mDataInputStream.read()) | (n2 = this.mDataInputStream.read()) | (n3 = this.mDataInputStream.read())) >= 0) {
                    Object object = this.mByteOrder;
                    if (object == LITTLE_ENDIAN) {
                        return (n3 << 24) + (n2 << 16) + (n << 8) + n4;
                    }
                    if (object == BIG_ENDIAN) {
                        return (n4 << 24) + (n << 16) + (n2 << 8) + n3;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid byte order: ");
                    ((StringBuilder)object).append(this.mByteOrder);
                    throw new IOException(((StringBuilder)object).toString());
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        @Override
        public String readLine() throws IOException {
            Log.d(ExifInterface.TAG, "Currently unsupported");
            return null;
        }

        @Override
        public long readLong() throws IOException {
            this.mPosition += 8;
            if (this.mPosition <= this.mLength) {
                int n;
                int n2;
                int n3;
                int n4;
                int n5;
                int n6;
                int n7;
                int n8 = this.mDataInputStream.read();
                if ((n8 | (n = this.mDataInputStream.read()) | (n5 = this.mDataInputStream.read()) | (n7 = this.mDataInputStream.read()) | (n3 = this.mDataInputStream.read()) | (n6 = this.mDataInputStream.read()) | (n4 = this.mDataInputStream.read()) | (n2 = this.mDataInputStream.read())) >= 0) {
                    Object object = this.mByteOrder;
                    if (object == LITTLE_ENDIAN) {
                        return ((long)n2 << 56) + ((long)n4 << 48) + ((long)n6 << 40) + ((long)n3 << 32) + ((long)n7 << 24) + ((long)n5 << 16) + ((long)n << 8) + (long)n8;
                    }
                    if (object == BIG_ENDIAN) {
                        return ((long)n8 << 56) + ((long)n << 48) + ((long)n5 << 40) + ((long)n7 << 32) + ((long)n3 << 24) + ((long)n6 << 16) + ((long)n4 << 8) + (long)n2;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid byte order: ");
                    ((StringBuilder)object).append(this.mByteOrder);
                    throw new IOException(((StringBuilder)object).toString());
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        @Override
        public short readShort() throws IOException {
            this.mPosition += 2;
            if (this.mPosition <= this.mLength) {
                int n;
                int n2 = this.mDataInputStream.read();
                if ((n2 | (n = this.mDataInputStream.read())) >= 0) {
                    Object object = this.mByteOrder;
                    if (object == LITTLE_ENDIAN) {
                        return (short)((n << 8) + n2);
                    }
                    if (object == BIG_ENDIAN) {
                        return (short)((n2 << 8) + n);
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid byte order: ");
                    ((StringBuilder)object).append(this.mByteOrder);
                    throw new IOException(((StringBuilder)object).toString());
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        @Override
        public String readUTF() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        @Override
        public int readUnsignedByte() throws IOException {
            ++this.mPosition;
            return this.mDataInputStream.readUnsignedByte();
        }

        public long readUnsignedInt() throws IOException {
            return (long)this.readInt() & 0xFFFFFFFFL;
        }

        @Override
        public int readUnsignedShort() throws IOException {
            this.mPosition += 2;
            if (this.mPosition <= this.mLength) {
                int n;
                int n2 = this.mDataInputStream.read();
                if ((n2 | (n = this.mDataInputStream.read())) >= 0) {
                    Object object = this.mByteOrder;
                    if (object == LITTLE_ENDIAN) {
                        return (n << 8) + n2;
                    }
                    if (object == BIG_ENDIAN) {
                        return (n2 << 8) + n;
                    }
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Invalid byte order: ");
                    ((StringBuilder)object).append(this.mByteOrder);
                    throw new IOException(((StringBuilder)object).toString());
                }
                throw new EOFException();
            }
            throw new EOFException();
        }

        public void seek(long l) throws IOException {
            int n = this.mPosition;
            if ((long)n > l) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
                this.mDataInputStream.mark(this.mLength);
            } else {
                l -= (long)n;
            }
            if (this.skipBytes((int)l) == (int)l) {
                return;
            }
            throw new IOException("Couldn't seek up to the byteCount");
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        @Override
        public int skipBytes(int n) throws IOException {
            int n2 = Math.min(n, this.mLength - this.mPosition);
            for (n = 0; n < n2; n += this.mDataInputStream.skipBytes((int)(n2 - n))) {
            }
            this.mPosition += n;
            return n;
        }
    }

    private static class ByteOrderedDataOutputStream
    extends FilterOutputStream {
        private ByteOrder mByteOrder;
        private final OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(OutputStream outputStream, ByteOrder byteOrder) {
            super(outputStream);
            this.mOutputStream = outputStream;
            this.mByteOrder = byteOrder;
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        @Override
        public void write(byte[] arrby) throws IOException {
            this.mOutputStream.write(arrby);
        }

        @Override
        public void write(byte[] arrby, int n, int n2) throws IOException {
            this.mOutputStream.write(arrby, n, n2);
        }

        public void writeByte(int n) throws IOException {
            this.mOutputStream.write(n);
        }

        public void writeInt(int n) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write(n >>> 0 & 255);
                this.mOutputStream.write(n >>> 8 & 255);
                this.mOutputStream.write(n >>> 16 & 255);
                this.mOutputStream.write(n >>> 24 & 255);
            } else if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write(n >>> 24 & 255);
                this.mOutputStream.write(n >>> 16 & 255);
                this.mOutputStream.write(n >>> 8 & 255);
                this.mOutputStream.write(n >>> 0 & 255);
            }
        }

        public void writeShort(short s) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write(s >>> 0 & 255);
                this.mOutputStream.write(s >>> 8 & 255);
            } else if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write(s >>> 8 & 255);
                this.mOutputStream.write(s >>> 0 & 255);
            }
        }

        public void writeUnsignedInt(long l) throws IOException {
            this.writeInt((int)l);
        }

        public void writeUnsignedShort(int n) throws IOException {
            this.writeShort((short)n);
        }
    }

    private static class ExifAttribute {
        public static final long BYTES_OFFSET_UNKNOWN = -1L;
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        private ExifAttribute(int n, int n2, long l, byte[] arrby) {
            this.format = n;
            this.numberOfComponents = n2;
            this.bytesOffset = l;
            this.bytes = arrby;
        }

        private ExifAttribute(int n, int n2, byte[] arrby) {
            this(n, n2, -1L, arrby);
        }

        public static ExifAttribute createByte(String arrby) {
            if (arrby.length() == 1 && arrby.charAt(0) >= '0' && arrby.charAt(0) <= '1') {
                byte[] arrby2 = new byte[]{(byte)(arrby.charAt(0) - 48)};
                return new ExifAttribute(1, arrby2.length, arrby2);
            }
            arrby = arrby.getBytes(ASCII);
            return new ExifAttribute(1, arrby.length, arrby);
        }

        public static ExifAttribute createDouble(double d, ByteOrder byteOrder) {
            return ExifAttribute.createDouble(new double[]{d}, byteOrder);
        }

        public static ExifAttribute createDouble(double[] arrd, ByteOrder byteOrder) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[12] * arrd.length]);
            byteBuffer.order(byteOrder);
            int n = arrd.length;
            for (int i = 0; i < n; ++i) {
                byteBuffer.putDouble(arrd[i]);
            }
            return new ExifAttribute(12, arrd.length, byteBuffer.array());
        }

        public static ExifAttribute createSLong(int n, ByteOrder byteOrder) {
            return ExifAttribute.createSLong(new int[]{n}, byteOrder);
        }

        public static ExifAttribute createSLong(int[] arrn, ByteOrder byteOrder) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[9] * arrn.length]);
            byteBuffer.order(byteOrder);
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                byteBuffer.putInt(arrn[i]);
            }
            return new ExifAttribute(9, arrn.length, byteBuffer.array());
        }

        public static ExifAttribute createSRational(Rational rational, ByteOrder byteOrder) {
            return ExifAttribute.createSRational(new Rational[]{rational}, byteOrder);
        }

        public static ExifAttribute createSRational(Rational[] arrrational, ByteOrder object) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[10] * arrrational.length]);
            byteBuffer.order((ByteOrder)object);
            int n = arrrational.length;
            for (int i = 0; i < n; ++i) {
                object = arrrational[i];
                byteBuffer.putInt((int)((Rational)object).numerator);
                byteBuffer.putInt((int)((Rational)object).denominator);
            }
            return new ExifAttribute(10, arrrational.length, byteBuffer.array());
        }

        public static ExifAttribute createString(String arrby) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append((String)arrby);
            stringBuilder.append('\u0000');
            arrby = stringBuilder.toString().getBytes(ASCII);
            return new ExifAttribute(2, arrby.length, arrby);
        }

        public static ExifAttribute createULong(long l, ByteOrder byteOrder) {
            return ExifAttribute.createULong(new long[]{l}, byteOrder);
        }

        public static ExifAttribute createULong(long[] arrl, ByteOrder byteOrder) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[4] * arrl.length]);
            byteBuffer.order(byteOrder);
            int n = arrl.length;
            for (int i = 0; i < n; ++i) {
                byteBuffer.putInt((int)arrl[i]);
            }
            return new ExifAttribute(4, arrl.length, byteBuffer.array());
        }

        public static ExifAttribute createURational(Rational rational, ByteOrder byteOrder) {
            return ExifAttribute.createURational(new Rational[]{rational}, byteOrder);
        }

        public static ExifAttribute createURational(Rational[] arrrational, ByteOrder object) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[5] * arrrational.length]);
            byteBuffer.order((ByteOrder)object);
            int n = arrrational.length;
            for (int i = 0; i < n; ++i) {
                object = arrrational[i];
                byteBuffer.putInt((int)((Rational)object).numerator);
                byteBuffer.putInt((int)((Rational)object).denominator);
            }
            return new ExifAttribute(5, arrrational.length, byteBuffer.array());
        }

        public static ExifAttribute createUShort(int n, ByteOrder byteOrder) {
            return ExifAttribute.createUShort(new int[]{n}, byteOrder);
        }

        public static ExifAttribute createUShort(int[] arrn, ByteOrder byteOrder) {
            ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[IFD_FORMAT_BYTES_PER_FORMAT[3] * arrn.length]);
            byteBuffer.order(byteOrder);
            int n = arrn.length;
            for (int i = 0; i < n; ++i) {
                byteBuffer.putShort((short)arrn[i]);
            }
            return new ExifAttribute(3, arrn.length, byteBuffer.array());
        }

        /*
         * Exception decompiling
         */
        private Object getValue(ByteOrder var1_1) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [23[CASE]], but top level block is 10[TRYBLOCK]
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
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

        public double getDoubleValue(ByteOrder arrobject) {
            if ((arrobject = this.getValue((ByteOrder)arrobject)) != null) {
                if (arrobject instanceof String) {
                    return Double.parseDouble((String)arrobject);
                }
                if (arrobject instanceof long[]) {
                    if ((arrobject = (long[])arrobject).length == 1) {
                        return arrobject[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                if (arrobject instanceof int[]) {
                    if ((arrobject = (int[])arrobject).length == 1) {
                        return arrobject[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                if (arrobject instanceof double[]) {
                    if ((arrobject = (double[])arrobject).length == 1) {
                        return arrobject[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                if (arrobject instanceof Rational[]) {
                    if ((arrobject = (Rational[])arrobject).length == 1) {
                        return arrobject[0].calculate();
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                throw new NumberFormatException("Couldn't find a double value");
            }
            throw new NumberFormatException("NULL can't be converted to a double value");
        }

        public int getIntValue(ByteOrder arrl) {
            if ((arrl = this.getValue((ByteOrder)arrl)) != null) {
                if (arrl instanceof String) {
                    return Integer.parseInt((String)arrl);
                }
                if (arrl instanceof long[]) {
                    if ((arrl = (long[])arrl).length == 1) {
                        return (int)arrl[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                if (arrl instanceof int[]) {
                    if ((arrl = (int[])arrl).length == 1) {
                        return (int)arrl[0];
                    }
                    throw new NumberFormatException("There are more than one component");
                }
                throw new NumberFormatException("Couldn't find a integer value");
            }
            throw new NumberFormatException("NULL can't be converted to a integer value");
        }

        public String getStringValue(ByteOrder object) {
            Object[] arrobject = this.getValue((ByteOrder)object);
            if (arrobject == null) {
                return null;
            }
            if (arrobject instanceof String) {
                return (String)arrobject;
            }
            object = new StringBuilder();
            if (arrobject instanceof long[]) {
                arrobject = arrobject;
                for (int i = 0; i < arrobject.length; ++i) {
                    ((StringBuilder)object).append(arrobject[i]);
                    if (i + 1 == arrobject.length) continue;
                    ((StringBuilder)object).append(",");
                }
                return ((StringBuilder)object).toString();
            }
            if (arrobject instanceof int[]) {
                arrobject = (int[])arrobject;
                for (int i = 0; i < arrobject.length; ++i) {
                    ((StringBuilder)object).append((int)arrobject[i]);
                    if (i + 1 == arrobject.length) continue;
                    ((StringBuilder)object).append(",");
                }
                return ((StringBuilder)object).toString();
            }
            if (arrobject instanceof double[]) {
                arrobject = arrobject;
                for (int i = 0; i < arrobject.length; ++i) {
                    ((StringBuilder)object).append((double)arrobject[i]);
                    if (i + 1 == arrobject.length) continue;
                    ((StringBuilder)object).append(",");
                }
                return ((StringBuilder)object).toString();
            }
            if (arrobject instanceof Rational[]) {
                arrobject = (Rational[])arrobject;
                for (int i = 0; i < arrobject.length; ++i) {
                    ((StringBuilder)object).append(arrobject[i].numerator);
                    ((StringBuilder)object).append('/');
                    ((StringBuilder)object).append(arrobject[i].denominator);
                    if (i + 1 == arrobject.length) continue;
                    ((StringBuilder)object).append(",");
                }
                return ((StringBuilder)object).toString();
            }
            return null;
        }

        public int size() {
            return IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("(");
            stringBuilder.append(IFD_FORMAT_NAMES[this.format]);
            stringBuilder.append(", data length:");
            stringBuilder.append(this.bytes.length);
            stringBuilder.append(")");
            return stringBuilder.toString();
        }
    }

    private static class ExifTag {
        public final String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        private ExifTag(String string2, int n, int n2) {
            this.name = string2;
            this.number = n;
            this.primaryFormat = n2;
            this.secondaryFormat = -1;
        }

        private ExifTag(String string2, int n, int n2, int n3) {
            this.name = string2;
            this.number = n;
            this.primaryFormat = n2;
            this.secondaryFormat = n3;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface IfdType {
    }

    private static class Rational {
        public final long denominator;
        public final long numerator;

        private Rational(long l, long l2) {
            if (l2 == 0L) {
                this.numerator = 0L;
                this.denominator = 1L;
                return;
            }
            this.numerator = l;
            this.denominator = l2;
        }

        public double calculate() {
            return (double)this.numerator / (double)this.denominator;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(this.numerator);
            stringBuilder.append("/");
            stringBuilder.append(this.denominator);
            return stringBuilder.toString();
        }
    }

}

