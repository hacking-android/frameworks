/*
 * Decompiled with CFR 0.145.
 */
package android.net.wifi.rtt;

import android.annotation.SystemApi;
import android.location.Address;
import android.location.Location;
import android.net.MacAddress;
import android.net.Uri;
import android.net.wifi.rtt.CivicLocation;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.webkit.MimeTypeMap;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class ResponderLocation
implements Parcelable {
    public static final int ALTITUDE_FLOORS = 2;
    private static final int ALTITUDE_FRACTION_BITS = 8;
    public static final int ALTITUDE_METERS = 1;
    private static final int ALTITUDE_UNCERTAINTY_BASE = 21;
    public static final int ALTITUDE_UNDEFINED = 0;
    private static final int BYTES_IN_A_BSSID = 6;
    private static final int BYTE_MASK = 255;
    private static final int CIVIC_COUNTRY_CODE_INDEX = 0;
    private static final int CIVIC_TLV_LIST_INDEX = 2;
    public static final Parcelable.Creator<ResponderLocation> CREATOR;
    public static final int DATUM_NAD83_MLLW = 3;
    public static final int DATUM_NAD83_NAV88 = 2;
    public static final int DATUM_UNDEFINED = 0;
    public static final int DATUM_WGS84 = 1;
    private static final int LATLNG_FRACTION_BITS = 25;
    private static final int LATLNG_UNCERTAINTY_BASE = 8;
    private static final double LAT_ABS_LIMIT = 90.0;
    public static final int LCI_VERSION_1 = 1;
    private static final byte[] LEAD_LCI_ELEMENT_BYTES;
    private static final byte[] LEAD_LCR_ELEMENT_BYTES;
    private static final double LNG_ABS_LIMIT = 180.0;
    public static final int LOCATION_FIXED = 0;
    public static final int LOCATION_MOVEMENT_UNKNOWN = 2;
    private static final String LOCATION_PROVIDER = "WiFi Access Point";
    public static final int LOCATION_RESERVED = 3;
    public static final int LOCATION_VARIABLE = 1;
    private static final int LSB_IN_BYTE = 1;
    private static final int MAP_TYPE_URL_DEFINED = 0;
    private static final int MAX_BUFFER_SIZE = 256;
    private static final byte MEASUREMENT_REPORT_MODE = 0;
    private static final byte MEASUREMENT_TOKEN_AUTONOMOUS = 1;
    private static final byte MEASUREMENT_TYPE_LCI = 8;
    private static final byte MEASUREMENT_TYPE_LCR = 11;
    private static final int MIN_BUFFER_SIZE = 3;
    private static final int MSB_IN_BYTE = 128;
    private static final byte SUBELEMENT_BSSID_LIST = 7;
    private static final int SUBELEMENT_BSSID_LIST_INDEX = 1;
    private static final int SUBELEMENT_BSSID_LIST_MIN_BUFFER_LENGTH = 1;
    private static final int SUBELEMENT_BSSID_MAX_INDICATOR_INDEX = 0;
    private static final int SUBELEMENT_IMAGE_MAP_TYPE_INDEX = 0;
    private static final byte SUBELEMENT_LCI = 0;
    private static final int SUBELEMENT_LCI_ALT_INDEX = 6;
    private static final int SUBELEMENT_LCI_ALT_TYPE_INDEX = 4;
    private static final int SUBELEMENT_LCI_ALT_UNCERTAINTY_INDEX = 5;
    private static final int[] SUBELEMENT_LCI_BIT_FIELD_LENGTHS;
    private static final int SUBELEMENT_LCI_DATUM_INDEX = 7;
    private static final int SUBELEMENT_LCI_DEPENDENT_STA_INDEX = 10;
    private static final int SUBELEMENT_LCI_LAT_INDEX = 1;
    private static final int SUBELEMENT_LCI_LAT_UNCERTAINTY_INDEX = 0;
    private static final int SUBELEMENT_LCI_LENGTH = 16;
    private static final int SUBELEMENT_LCI_LNG_INDEX = 3;
    private static final int SUBELEMENT_LCI_LNG_UNCERTAINTY_INDEX = 2;
    private static final int SUBELEMENT_LCI_REGLOC_AGREEMENT_INDEX = 8;
    private static final int SUBELEMENT_LCI_REGLOC_DSE_INDEX = 9;
    private static final int SUBELEMENT_LCI_VERSION_INDEX = 11;
    private static final byte SUBELEMENT_LOCATION_CIVIC = 0;
    private static final int SUBELEMENT_LOCATION_CIVIC_MAX_LENGTH = 256;
    private static final int SUBELEMENT_LOCATION_CIVIC_MIN_LENGTH = 2;
    private static final byte SUBELEMENT_MAP_IMAGE = 5;
    private static final int SUBELEMENT_MAP_IMAGE_URL_MAX_LENGTH = 256;
    private static final byte SUBELEMENT_USAGE = 6;
    private static final int SUBELEMENT_USAGE_LENGTH1 = 1;
    private static final int SUBELEMENT_USAGE_LENGTH3 = 3;
    private static final int SUBELEMENT_USAGE_MASK_RETENTION_EXPIRES = 2;
    private static final int SUBELEMENT_USAGE_MASK_RETRANSMIT = 1;
    private static final int SUBELEMENT_USAGE_MASK_STA_LOCATION_POLICY = 4;
    private static final int SUBELEMENT_USAGE_PARAMS_INDEX = 0;
    private static final byte SUBELEMENT_Z = 4;
    private static final int[] SUBELEMENT_Z_BIT_FIELD_LENGTHS;
    private static final int SUBELEMENT_Z_FLOOR_NUMBER_INDEX = 1;
    private static final int SUBELEMENT_Z_HEIGHT_ABOVE_FLOOR_INDEX = 2;
    private static final int SUBELEMENT_Z_HEIGHT_ABOVE_FLOOR_UNCERTAINTY_INDEX = 3;
    private static final int SUBELEMENT_Z_LAT_EXPECTED_TO_MOVE_INDEX = 0;
    private static final int SUBELEMENT_Z_LENGTH = 6;
    private static final String[] SUPPORTED_IMAGE_FILE_EXTENSIONS;
    private static final int UNCERTAINTY_UNDEFINED = 0;
    private static final int Z_FLOOR_HEIGHT_FRACTION_BITS = 12;
    private static final int Z_FLOOR_NUMBER_FRACTION_BITS = 4;
    private static final int Z_MAX_HEIGHT_UNCERTAINTY_FACTOR = 25;
    private double mAltitude;
    private int mAltitudeType;
    private double mAltitudeUncertainty;
    private ArrayList<MacAddress> mBssidList;
    private CivicLocation mCivicLocation;
    private String mCivicLocationCountryCode;
    private String mCivicLocationString;
    private int mDatum;
    private int mExpectedToMove;
    private double mFloorNumber;
    private double mHeightAboveFloorMeters;
    private double mHeightAboveFloorUncertaintyMeters;
    private boolean mIsBssidListValid;
    private boolean mIsLciValid;
    private boolean mIsLocationCivicValid;
    private boolean mIsMapImageValid;
    private boolean mIsUsageValid;
    private final boolean mIsValid;
    private boolean mIsZValid;
    private double mLatitude;
    private double mLatitudeUncertainty;
    private boolean mLciDependentStation;
    private boolean mLciRegisteredLocationAgreement;
    private boolean mLciRegisteredLocationDse;
    private int mLciVersion;
    private double mLongitude;
    private double mLongitudeUncertainty;
    private int mMapImageType;
    private Uri mMapImageUri;
    private boolean mUsageExtraInfoOnAssociation;
    private boolean mUsageRetentionExpires;
    private boolean mUsageRetransmit;

    static {
        LEAD_LCI_ELEMENT_BYTES = new byte[]{1, 0, 8};
        SUBELEMENT_LCI_BIT_FIELD_LENGTHS = new int[]{6, 34, 6, 34, 4, 6, 30, 3, 1, 1, 1, 2};
        SUBELEMENT_Z_BIT_FIELD_LENGTHS = new int[]{2, 14, 24, 8};
        LEAD_LCR_ELEMENT_BYTES = new byte[]{1, 0, 11};
        SUPPORTED_IMAGE_FILE_EXTENSIONS = new String[]{"", "png", "gif", "jpg", "svg", "dxf", "dwg", "dwf", "cad", "tif", "gml", "kml", "bmp", "pgm", "ppm", "xbm", "xpm", "ico"};
        CREATOR = new Parcelable.Creator<ResponderLocation>(){

            @Override
            public ResponderLocation createFromParcel(Parcel parcel) {
                return new ResponderLocation(parcel);
            }

            public ResponderLocation[] newArray(int n) {
                return new ResponderLocation[n];
            }
        };
    }

    private ResponderLocation(Parcel object) {
        boolean bl = false;
        this.mIsLciValid = false;
        this.mIsZValid = false;
        this.mIsUsageValid = true;
        this.mIsBssidListValid = false;
        this.mIsLocationCivicValid = false;
        this.mIsMapImageValid = false;
        boolean bl2 = ((Parcel)object).readByte() != 0;
        this.mIsValid = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mIsLciValid = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mIsZValid = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mIsUsageValid = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mIsBssidListValid = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mIsLocationCivicValid = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mIsMapImageValid = bl2;
        this.mLatitudeUncertainty = ((Parcel)object).readDouble();
        this.mLatitude = ((Parcel)object).readDouble();
        this.mLongitudeUncertainty = ((Parcel)object).readDouble();
        this.mLongitude = ((Parcel)object).readDouble();
        this.mAltitudeType = ((Parcel)object).readInt();
        this.mAltitudeUncertainty = ((Parcel)object).readDouble();
        this.mAltitude = ((Parcel)object).readDouble();
        this.mDatum = ((Parcel)object).readInt();
        bl2 = ((Parcel)object).readByte() != 0;
        this.mLciRegisteredLocationAgreement = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mLciRegisteredLocationDse = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mLciDependentStation = bl2;
        this.mLciVersion = ((Parcel)object).readInt();
        this.mExpectedToMove = ((Parcel)object).readInt();
        this.mFloorNumber = ((Parcel)object).readDouble();
        this.mHeightAboveFloorMeters = ((Parcel)object).readDouble();
        this.mHeightAboveFloorUncertaintyMeters = ((Parcel)object).readDouble();
        bl2 = ((Parcel)object).readByte() != 0;
        this.mUsageRetransmit = bl2;
        bl2 = ((Parcel)object).readByte() != 0;
        this.mUsageRetentionExpires = bl2;
        bl2 = bl;
        if (((Parcel)object).readByte() != 0) {
            bl2 = true;
        }
        this.mUsageExtraInfoOnAssociation = bl2;
        this.mBssidList = ((Parcel)object).readArrayList(MacAddress.class.getClassLoader());
        this.mCivicLocationCountryCode = ((Parcel)object).readString();
        this.mCivicLocationString = ((Parcel)object).readString();
        this.mCivicLocation = (CivicLocation)((Parcel)object).readParcelable(this.getClass().getClassLoader());
        this.mMapImageType = ((Parcel)object).readInt();
        this.mMapImageUri = TextUtils.isEmpty((CharSequence)(object = ((Parcel)object).readString())) ? null : Uri.parse((String)object);
    }

    public ResponderLocation(byte[] arrby, byte[] arrby2) {
        boolean bl;
        block9 : {
            block8 : {
                int n;
                boolean bl2 = false;
                this.mIsLciValid = false;
                this.mIsZValid = false;
                this.mIsUsageValid = true;
                this.mIsBssidListValid = false;
                this.mIsLocationCivicValid = false;
                this.mIsMapImageValid = false;
                boolean bl3 = false;
                boolean bl4 = false;
                this.setLciSubelementDefaults();
                this.setZaxisSubelementDefaults();
                this.setUsageSubelementDefaults();
                this.setBssidListSubelementDefaults();
                this.setCivicLocationSubelementDefaults();
                this.setMapImageSubelementDefaults();
                bl = bl3;
                if (arrby != null) {
                    n = arrby.length;
                    byte[] arrby3 = LEAD_LCI_ELEMENT_BYTES;
                    bl = bl3;
                    if (n > arrby3.length) {
                        bl = this.parseInformationElementBuffer(8, arrby, arrby3);
                    }
                }
                bl3 = bl4;
                if (arrby2 != null) {
                    n = arrby2.length;
                    arrby = LEAD_LCR_ELEMENT_BYTES;
                    bl3 = bl4;
                    if (n > arrby.length) {
                        bl3 = this.parseInformationElementBuffer(11, arrby2, arrby);
                    }
                }
                n = bl && this.mIsUsageValid && (this.mIsLciValid || this.mIsZValid || this.mIsBssidListValid) ? 1 : 0;
                boolean bl5 = bl3 && this.mIsUsageValid && (this.mIsLocationCivicValid || this.mIsMapImageValid);
                if (n != 0) break block8;
                bl = bl2;
                if (!bl5) break block9;
            }
            bl = true;
        }
        this.mIsValid = bl;
        if (!this.mIsValid) {
            this.setLciSubelementDefaults();
            this.setZaxisSubelementDefaults();
            this.setCivicLocationSubelementDefaults();
            this.setMapImageSubelementDefaults();
        }
    }

    private double decodeLciAltUncertainty(long l) {
        return Math.pow(2.0, 21L - l);
    }

    private double decodeLciLatLng(long[] arrl, int[] arrn, int n, double d) {
        double d2;
        double d3 = (arrl[n] & (long)Math.pow(2.0, arrn[n] - 1)) != 0L ? Math.scalb((double)arrl[n] - Math.pow(2.0, arrn[n]), -25) : (double)Math.scalb(arrl[n], -25);
        if (d3 > d) {
            d2 = d;
        } else {
            d2 = d3;
            if (d3 < -d) {
                d2 = -d;
            }
        }
        return d2;
    }

    private double decodeLciLatLngUncertainty(long l) {
        return Math.pow(2.0, 8L - l);
    }

    private double decodeZUnsignedToSignedValue(long[] arrl, int[] arrn, int n, int n2) {
        int n3;
        int n4 = n3 = (int)arrl[n];
        if (n3 > (int)Math.pow(2.0, arrn[n] - 1) - 1) {
            n4 = (int)((double)n3 - Math.pow(2.0, arrn[n]));
        }
        return Math.scalb(n4, -n2);
    }

    private int getBitAtBitOffsetInByteArray(byte[] arrby, int n) {
        n = (arrby[n / 8] & 128 >> n % 8) == 0 ? 0 : 1;
        return n;
    }

    private long[] getFieldData(byte[] arrby, int[] arrn) {
        int n;
        int n2 = arrby.length;
        int n3 = 0;
        int n4 = arrn.length;
        for (n = 0; n < n4; ++n) {
            int n5 = arrn[n];
            if (n5 > 64) {
                return null;
            }
            n3 += n5;
        }
        if (n2 * 8 != n3) {
            return null;
        }
        long[] arrl = new long[arrn.length];
        n3 = 0;
        for (n = 0; n < arrn.length; ++n) {
            n4 = arrn[n];
            long l = 0L;
            for (n2 = 0; n2 < n4; ++n2) {
                l |= (long)this.getBitAtBitOffsetInByteArray(arrby, n3 + n2) << n2;
            }
            arrl[n] = l;
            n3 += n4;
        }
        return arrl;
    }

    private String imageTypeToMime(int n, String string2) {
        int n2 = SUPPORTED_IMAGE_FILE_EXTENSIONS.length;
        if (n == 0 && string2 == null || n > n2 - 1) {
            return null;
        }
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        if (n == 0) {
            return mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(string2));
        }
        return mimeTypeMap.getMimeTypeFromExtension(SUPPORTED_IMAGE_FILE_EXTENSIONS[n]);
    }

    private boolean parseInformationElementBuffer(int n, byte[] arrby, byte[] arrby2) {
        int n2 = arrby.length;
        if (n2 >= 3 && n2 <= 256) {
            if (!Arrays.equals(Arrays.copyOfRange(arrby, 0, arrby2.length), arrby2)) {
                return false;
            }
            int n3 = 0 + arrby2.length;
            while (n3 + 1 < n2) {
                int n4 = n3 + 1;
                n3 = arrby[n3];
                int n5 = n4 + 1;
                if (n5 + (n4 = arrby[n4]) <= n2 && n4 > 0) {
                    arrby2 = Arrays.copyOfRange(arrby, n5, n5 + n4);
                    if (n == 8) {
                        if (n3 != 0) {
                            if (n3 != 4) {
                                if (n3 != 6) {
                                    if (n3 == 7) {
                                        this.mIsBssidListValid = this.parseSubelementBssidList(arrby2);
                                        if (!this.mIsBssidListValid) {
                                            this.setBssidListSubelementDefaults();
                                        }
                                    }
                                } else {
                                    this.mIsUsageValid = this.parseSubelementUsage(arrby2);
                                }
                            } else {
                                this.mIsZValid = this.parseSubelementZ(arrby2);
                                if (!this.mIsZValid) {
                                    this.setZaxisSubelementDefaults();
                                }
                            }
                        } else {
                            this.mIsLciValid = this.parseSubelementLci(arrby2);
                            if (!this.mIsLciValid || this.mLciVersion != 1) {
                                this.setLciSubelementDefaults();
                            }
                        }
                    } else if (n == 11) {
                        if (n3 != 0) {
                            if (n3 == 5) {
                                this.mIsMapImageValid = this.parseSubelementMapImage(arrby2);
                                if (!this.mIsMapImageValid) {
                                    this.setMapImageSubelementDefaults();
                                }
                            }
                        } else {
                            this.mIsLocationCivicValid = this.parseSubelementLocationCivic(arrby2);
                            if (!this.mIsLocationCivicValid) {
                                this.setCivicLocationSubelementDefaults();
                            }
                        }
                    }
                    n3 = n5 + n4;
                    continue;
                }
                return false;
            }
            return true;
        }
        return false;
    }

    private boolean parseSubelementBssidList(byte[] arrby) {
        if (arrby.length < 1) {
            return false;
        }
        if ((arrby.length - 1) % 6 != 0) {
            return false;
        }
        int n = arrby[0];
        int n2 = (arrby.length - 1) / 6;
        if ((n & 255) != n2) {
            return false;
        }
        int n3 = 1;
        for (n = 0; n < n2; ++n) {
            MacAddress macAddress = MacAddress.fromBytes(Arrays.copyOfRange(arrby, n3, n3 + 6));
            this.mBssidList.add(macAddress);
            n3 += 6;
        }
        return true;
    }

    private boolean parseSubelementLci(byte[] arrby) {
        int n = arrby.length;
        boolean bl = false;
        if (n > 16) {
            return false;
        }
        this.swapEndianByteByByte(arrby);
        arrby = this.getFieldData(arrby, SUBELEMENT_LCI_BIT_FIELD_LENGTHS);
        if (arrby == null) {
            return false;
        }
        this.mLatitudeUncertainty = this.decodeLciLatLngUncertainty(arrby[0]);
        this.mLatitude = this.decodeLciLatLng(arrby, SUBELEMENT_LCI_BIT_FIELD_LENGTHS, 1, 90.0);
        this.mLongitudeUncertainty = this.decodeLciLatLngUncertainty(arrby[2]);
        this.mLongitude = this.decodeLciLatLng(arrby, SUBELEMENT_LCI_BIT_FIELD_LENGTHS, 3, 180.0);
        this.mAltitudeType = (int)arrby[4] & 255;
        this.mAltitudeUncertainty = this.decodeLciAltUncertainty(arrby[5]);
        this.mAltitude = Math.scalb(arrby[6], -8);
        this.mDatum = (int)arrby[7] & 255;
        boolean bl2 = arrby[8] == 1L;
        this.mLciRegisteredLocationAgreement = bl2;
        bl2 = arrby[9] == 1L;
        this.mLciRegisteredLocationDse = bl2;
        bl2 = bl;
        if (arrby[10] == 1L) {
            bl2 = true;
        }
        this.mLciDependentStation = bl2;
        this.mLciVersion = arrby[11];
        return true;
    }

    private boolean parseSubelementLocationCivic(byte[] object) {
        if (((byte[])object).length >= 2 && ((byte[])object).length <= 256) {
            this.mCivicLocationCountryCode = new String(Arrays.copyOfRange((byte[])object, 0, 2)).toUpperCase();
            if (!((CivicLocation)(object = new CivicLocation(Arrays.copyOfRange((byte[])object, 2, ((byte[])object).length), this.mCivicLocationCountryCode))).isValid()) {
                return false;
            }
            this.mCivicLocation = object;
            this.mCivicLocationString = ((CivicLocation)object).toString();
            return true;
        }
        return false;
    }

    private boolean parseSubelementMapImage(byte[] arrby) {
        if (arrby.length > 256) {
            return false;
        }
        int n = arrby[0];
        int n2 = SUPPORTED_IMAGE_FILE_EXTENSIONS.length;
        if (n >= 0 && n <= n2 - 1) {
            this.mMapImageType = n;
            this.mMapImageUri = Uri.parse(new String(Arrays.copyOfRange(arrby, 1, arrby.length), StandardCharsets.UTF_8));
            return true;
        }
        return false;
    }

    private boolean parseSubelementUsage(byte[] arrby) {
        int n = arrby.length;
        boolean bl = true;
        if (n != 1 && arrby.length != 3) {
            return false;
        }
        boolean bl2 = (arrby[0] & 1) != 0;
        this.mUsageRetransmit = bl2;
        bl2 = (arrby[0] & 2) != 0;
        this.mUsageRetentionExpires = bl2;
        bl2 = (arrby[0] & 4) != 0;
        this.mUsageExtraInfoOnAssociation = bl2;
        bl2 = this.mUsageRetransmit && !this.mUsageRetentionExpires ? bl : false;
        return bl2;
    }

    private boolean parseSubelementZ(byte[] arrby) {
        if (arrby.length != 6) {
            return false;
        }
        this.swapEndianByteByByte(arrby);
        arrby = this.getFieldData(arrby, SUBELEMENT_Z_BIT_FIELD_LENGTHS);
        if (arrby == null) {
            return false;
        }
        this.mExpectedToMove = (int)arrby[0] & 255;
        this.mFloorNumber = this.decodeZUnsignedToSignedValue(arrby, SUBELEMENT_Z_BIT_FIELD_LENGTHS, 1, 4);
        this.mHeightAboveFloorMeters = this.decodeZUnsignedToSignedValue(arrby, SUBELEMENT_Z_BIT_FIELD_LENGTHS, 2, 12);
        byte by = arrby[3];
        if (by > 0L && by < 25L) {
            this.mHeightAboveFloorUncertaintyMeters = Math.pow(2.0, 12L - by - 1L);
            return true;
        }
        return false;
    }

    private void setBssidListSubelementDefaults() {
        this.mIsBssidListValid = false;
        this.mBssidList = new ArrayList();
    }

    private void setLciSubelementDefaults() {
        this.mIsLciValid = false;
        this.mLatitudeUncertainty = 0.0;
        this.mLatitude = 0.0;
        this.mLongitudeUncertainty = 0.0;
        this.mLongitude = 0.0;
        this.mAltitudeType = 0;
        this.mAltitudeUncertainty = 0.0;
        this.mAltitude = 0.0;
        this.mDatum = 0;
        this.mLciRegisteredLocationAgreement = false;
        this.mLciRegisteredLocationDse = false;
        this.mLciDependentStation = false;
        this.mLciVersion = 0;
    }

    private void setMapImageSubelementDefaults() {
        this.mIsMapImageValid = false;
        this.mMapImageType = 0;
        this.mMapImageUri = null;
    }

    private void setUsageSubelementDefaults() {
        this.mUsageRetransmit = true;
        this.mUsageRetentionExpires = false;
        this.mUsageExtraInfoOnAssociation = false;
    }

    private void setZaxisSubelementDefaults() {
        this.mIsZValid = false;
        this.mExpectedToMove = 0;
        this.mFloorNumber = 0.0;
        this.mHeightAboveFloorMeters = 0.0;
        this.mHeightAboveFloorUncertaintyMeters = 0.0;
    }

    private void swapEndianByteByByte(byte[] arrby) {
        for (int i = 0; i < arrby.length; ++i) {
            byte by = arrby[i];
            int n = 0;
            byte by2 = 1;
            for (int j = 0; j < 8; ++j) {
                byte by3 = (byte)(n << 1);
                n = by3;
                if ((by & by2) != 0) {
                    n = (byte)(by3 | 1);
                }
                by2 = (byte)(by2 << 1);
            }
            arrby[i] = (byte)n;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (object != null && this.getClass() == object.getClass()) {
            object = (ResponderLocation)object;
            if (!(this.mIsValid == ((ResponderLocation)object).mIsValid && this.mIsLciValid == ((ResponderLocation)object).mIsLciValid && this.mIsZValid == ((ResponderLocation)object).mIsZValid && this.mIsUsageValid == ((ResponderLocation)object).mIsUsageValid && this.mIsBssidListValid == ((ResponderLocation)object).mIsBssidListValid && this.mIsLocationCivicValid == ((ResponderLocation)object).mIsLocationCivicValid && this.mIsMapImageValid == ((ResponderLocation)object).mIsMapImageValid && this.mLatitudeUncertainty == ((ResponderLocation)object).mLatitudeUncertainty && this.mLatitude == ((ResponderLocation)object).mLatitude && this.mLongitudeUncertainty == ((ResponderLocation)object).mLongitudeUncertainty && this.mLongitude == ((ResponderLocation)object).mLongitude && this.mAltitudeType == ((ResponderLocation)object).mAltitudeType && this.mAltitudeUncertainty == ((ResponderLocation)object).mAltitudeUncertainty && this.mAltitude == ((ResponderLocation)object).mAltitude && this.mDatum == ((ResponderLocation)object).mDatum && this.mLciRegisteredLocationAgreement == ((ResponderLocation)object).mLciRegisteredLocationAgreement && this.mLciRegisteredLocationDse == ((ResponderLocation)object).mLciRegisteredLocationDse && this.mLciDependentStation == ((ResponderLocation)object).mLciDependentStation && this.mLciVersion == ((ResponderLocation)object).mLciVersion && this.mExpectedToMove == ((ResponderLocation)object).mExpectedToMove && this.mFloorNumber == ((ResponderLocation)object).mFloorNumber && this.mHeightAboveFloorMeters == ((ResponderLocation)object).mHeightAboveFloorMeters && this.mHeightAboveFloorUncertaintyMeters == ((ResponderLocation)object).mHeightAboveFloorUncertaintyMeters && this.mUsageRetransmit == ((ResponderLocation)object).mUsageRetransmit && this.mUsageRetentionExpires == ((ResponderLocation)object).mUsageRetentionExpires && this.mUsageExtraInfoOnAssociation == ((ResponderLocation)object).mUsageExtraInfoOnAssociation && this.mBssidList.equals(((ResponderLocation)object).mBssidList) && this.mCivicLocationCountryCode.equals(((ResponderLocation)object).mCivicLocationCountryCode) && this.mCivicLocationString.equals(((ResponderLocation)object).mCivicLocationString) && Objects.equals(this.mCivicLocation, ((ResponderLocation)object).mCivicLocation) && this.mMapImageType == ((ResponderLocation)object).mMapImageType && Objects.equals(this.mMapImageUri, ((ResponderLocation)object).mMapImageUri))) {
                bl = false;
            }
            return bl;
        }
        return false;
    }

    public double getAltitude() {
        if (this.mIsLciValid) {
            return this.mAltitude;
        }
        throw new IllegalStateException("getAltitude(): invoked on an invalid result: mIsLciValid = false.");
    }

    public int getAltitudeType() {
        if (this.mIsLciValid) {
            return this.mAltitudeType;
        }
        throw new IllegalStateException("getLatitudeUncertainty(): invoked on an invalid result: mIsLciValid = false.");
    }

    public double getAltitudeUncertainty() {
        if (this.mIsLciValid) {
            return this.mAltitudeUncertainty;
        }
        throw new IllegalStateException("getLatitudeUncertainty(): invoked on an invalid result: mIsLciValid = false.");
    }

    public String getCivicLocationCountryCode() {
        return this.mCivicLocationCountryCode;
    }

    public String getCivicLocationElementValue(int n) {
        return this.mCivicLocation.getCivicElementValue(n);
    }

    public List<MacAddress> getColocatedBssids() {
        return Collections.unmodifiableList(this.mBssidList);
    }

    public int getDatum() {
        if (this.mIsLciValid) {
            return this.mDatum;
        }
        throw new IllegalStateException("getDatum(): invoked on an invalid result: mIsLciValid = false.");
    }

    public boolean getDependentStationIndication() {
        if (this.mIsLciValid) {
            return this.mLciDependentStation;
        }
        throw new IllegalStateException("getDependentStationIndication(): invoked on an invalid result: mIsLciValid = false.");
    }

    public int getExpectedToMove() {
        if (this.mIsZValid) {
            return this.mExpectedToMove;
        }
        throw new IllegalStateException("getExpectedToMove(): invoked on an invalid result: mIsZValid = false.");
    }

    @SystemApi
    public boolean getExtraInfoOnAssociationIndication() {
        return this.mUsageExtraInfoOnAssociation;
    }

    public double getFloorNumber() {
        if (this.mIsZValid) {
            return this.mFloorNumber;
        }
        throw new IllegalStateException("getFloorNumber(): invoked on an invalid result: mIsZValid = false)");
    }

    public double getHeightAboveFloorMeters() {
        if (this.mIsZValid) {
            return this.mHeightAboveFloorMeters;
        }
        throw new IllegalStateException("getHeightAboveFloorMeters(): invoked on an invalid result: mIsZValid = false)");
    }

    public double getHeightAboveFloorUncertaintyMeters() {
        if (this.mIsZValid) {
            return this.mHeightAboveFloorUncertaintyMeters;
        }
        throw new IllegalStateException("getHeightAboveFloorUncertaintyMeters():invoked on an invalid result: mIsZValid = false)");
    }

    public double getLatitude() {
        if (this.mIsLciValid) {
            return this.mLatitude;
        }
        throw new IllegalStateException("getLatitude(): invoked on an invalid result: mIsLciValid = false.");
    }

    public double getLatitudeUncertainty() {
        if (this.mIsLciValid) {
            return this.mLatitudeUncertainty;
        }
        throw new IllegalStateException("getLatitudeUncertainty(): invoked on an invalid result: mIsLciValid = false.");
    }

    public int getLciVersion() {
        if (this.mIsLciValid) {
            return this.mLciVersion;
        }
        throw new IllegalStateException("getLciVersion(): invoked on an invalid result: mIsLciValid = false.");
    }

    public double getLongitude() {
        if (this.mIsLciValid) {
            return this.mLongitude;
        }
        throw new IllegalStateException("getLatitudeUncertainty(): invoked on an invalid result: mIsLciValid = false.");
    }

    public double getLongitudeUncertainty() {
        if (this.mIsLciValid) {
            return this.mLongitudeUncertainty;
        }
        throw new IllegalStateException("getLongitudeUncertainty(): invoked on an invalid result: mIsLciValid = false.");
    }

    public String getMapImageMimeType() {
        Uri uri = this.mMapImageUri;
        if (uri == null) {
            return null;
        }
        return this.imageTypeToMime(this.mMapImageType, uri.toString());
    }

    public Uri getMapImageUri() {
        return this.mMapImageUri;
    }

    public boolean getRegisteredLocationAgreementIndication() {
        if (this.mIsLciValid) {
            return this.mLciRegisteredLocationAgreement;
        }
        throw new IllegalStateException("getRegisteredLocationAgreementIndication(): invoked on an invalid result: mIsLciValid = false.");
    }

    public boolean getRegisteredLocationDseIndication() {
        if (this.mIsLciValid) {
            return this.mLciRegisteredLocationDse;
        }
        throw new IllegalStateException("getRegisteredLocationDseIndication(): invoked on an invalid result: mIsLciValid = false.");
    }

    public boolean getRetentionExpiresIndication() {
        return this.mUsageRetentionExpires;
    }

    public boolean getRetransmitPolicyIndication() {
        return this.mUsageRetransmit;
    }

    public int hashCode() {
        return Objects.hash(this.mIsValid, this.mIsLciValid, this.mIsZValid, this.mIsUsageValid, this.mIsBssidListValid, this.mIsLocationCivicValid, this.mIsMapImageValid, this.mLatitudeUncertainty, this.mLatitude, this.mLongitudeUncertainty, this.mLongitude, this.mAltitudeType, this.mAltitudeUncertainty, this.mAltitude, this.mDatum, this.mLciRegisteredLocationAgreement, this.mLciRegisteredLocationDse, this.mLciDependentStation, this.mLciVersion, this.mExpectedToMove, this.mFloorNumber, this.mHeightAboveFloorMeters, this.mHeightAboveFloorUncertaintyMeters, this.mUsageRetransmit, this.mUsageRetentionExpires, this.mUsageExtraInfoOnAssociation, this.mBssidList, this.mCivicLocationCountryCode, this.mCivicLocationString, this.mCivicLocation, this.mMapImageType, this.mMapImageUri);
    }

    public boolean isLciSubelementValid() {
        return this.mIsLciValid;
    }

    public boolean isValid() {
        return this.mIsValid;
    }

    public boolean isZaxisSubelementValid() {
        return this.mIsZValid;
    }

    public void setCivicLocationSubelementDefaults() {
        this.mIsLocationCivicValid = false;
        this.mCivicLocationCountryCode = "";
        this.mCivicLocationString = "";
        this.mCivicLocation = null;
    }

    public Address toCivicLocationAddress() {
        CivicLocation civicLocation = this.mCivicLocation;
        if (civicLocation != null && civicLocation.isValid()) {
            return this.mCivicLocation.toAddress();
        }
        return null;
    }

    public SparseArray toCivicLocationSparseArray() {
        CivicLocation civicLocation = this.mCivicLocation;
        if (civicLocation != null && civicLocation.isValid()) {
            return this.mCivicLocation.toSparseArray();
        }
        return null;
    }

    public Location toLocation() {
        if (this.mIsLciValid) {
            Location location = new Location(LOCATION_PROVIDER);
            location.setLatitude(this.mLatitude);
            location.setLongitude(this.mLongitude);
            location.setAccuracy((float)(this.mLatitudeUncertainty + this.mLongitudeUncertainty) / 2.0f);
            location.setAltitude(this.mAltitude);
            location.setVerticalAccuracyMeters((float)this.mAltitudeUncertainty);
            location.setTime(System.currentTimeMillis());
            return location;
        }
        throw new IllegalStateException("toLocation(): invoked on an invalid result: mIsLciValid = false.");
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeByte((byte)(this.mIsValid ? 1 : 0));
        parcel.writeByte((byte)(this.mIsLciValid ? 1 : 0));
        parcel.writeByte((byte)(this.mIsZValid ? 1 : 0));
        parcel.writeByte((byte)(this.mIsUsageValid ? 1 : 0));
        parcel.writeByte((byte)(this.mIsBssidListValid ? 1 : 0));
        parcel.writeByte((byte)(this.mIsLocationCivicValid ? 1 : 0));
        parcel.writeByte((byte)(this.mIsMapImageValid ? 1 : 0));
        parcel.writeDouble(this.mLatitudeUncertainty);
        parcel.writeDouble(this.mLatitude);
        parcel.writeDouble(this.mLongitudeUncertainty);
        parcel.writeDouble(this.mLongitude);
        parcel.writeInt(this.mAltitudeType);
        parcel.writeDouble(this.mAltitudeUncertainty);
        parcel.writeDouble(this.mAltitude);
        parcel.writeInt(this.mDatum);
        parcel.writeByte((byte)(this.mLciRegisteredLocationAgreement ? 1 : 0));
        parcel.writeByte((byte)(this.mLciRegisteredLocationDse ? 1 : 0));
        parcel.writeByte((byte)(this.mLciDependentStation ? 1 : 0));
        parcel.writeInt(this.mLciVersion);
        parcel.writeInt(this.mExpectedToMove);
        parcel.writeDouble(this.mFloorNumber);
        parcel.writeDouble(this.mHeightAboveFloorMeters);
        parcel.writeDouble(this.mHeightAboveFloorUncertaintyMeters);
        parcel.writeByte((byte)(this.mUsageRetransmit ? 1 : 0));
        parcel.writeByte((byte)(this.mUsageRetentionExpires ? 1 : 0));
        parcel.writeByte((byte)(this.mUsageExtraInfoOnAssociation ? 1 : 0));
        parcel.writeList(this.mBssidList);
        parcel.writeString(this.mCivicLocationCountryCode);
        parcel.writeString(this.mCivicLocationString);
        parcel.writeParcelable(this.mCivicLocation, n);
        parcel.writeInt(this.mMapImageType);
        Uri uri = this.mMapImageUri;
        if (uri != null) {
            parcel.writeString(uri.toString());
        } else {
            parcel.writeString("");
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface AltitudeType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface DatumType {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface ExpectedToMoveType {
    }

}

