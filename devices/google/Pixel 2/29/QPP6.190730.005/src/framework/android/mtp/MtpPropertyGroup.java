/*
 * Decompiled with CFR 0.145.
 */
package android.mtp;

import android.content.ContentProviderClient;
import android.database.Cursor;
import android.mtp.MtpDatabase;
import android.mtp.MtpPropertyList;
import android.mtp.MtpStorageManager;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.RemoteException;
import android.util.Log;
import java.nio.file.Path;
import java.util.ArrayList;

class MtpPropertyGroup {
    private static final String PATH_WHERE = "_data=?";
    private static final String TAG = MtpPropertyGroup.class.getSimpleName();
    private String[] mColumns;
    private final Property[] mProperties;

    public MtpPropertyGroup(int[] arrn) {
        int n;
        int n2 = arrn.length;
        ArrayList<String> arrayList = new ArrayList<String>(n2);
        arrayList.add("_id");
        this.mProperties = new Property[n2];
        for (n = 0; n < n2; ++n) {
            this.mProperties[n] = this.createProperty(arrn[n], arrayList);
        }
        n2 = arrayList.size();
        this.mColumns = new String[n2];
        for (n = 0; n < n2; ++n) {
            this.mColumns[n] = arrayList.get(n);
        }
    }

    private Property createProperty(int n, ArrayList<String> arrayList) {
        int n2;
        String string2 = null;
        switch (n) {
            default: {
                n2 = 0;
                String string3 = TAG;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("unsupported property ");
                stringBuilder.append(n);
                Log.e(string3, stringBuilder.toString());
                break;
            }
            case 56979: 
            case 56985: 
            case 56986: {
                n2 = 6;
                break;
            }
            case 56978: 
            case 56980: {
                n2 = 4;
                break;
            }
            case 56544: {
                n2 = 65535;
                break;
            }
            case 56475: {
                string2 = "album_artist";
                n2 = 65535;
                break;
            }
            case 56474: {
                n2 = 65535;
                break;
            }
            case 56473: {
                string2 = "year";
                n2 = 65535;
                break;
            }
            case 56470: {
                string2 = "composer";
                n2 = 65535;
                break;
            }
            case 56459: {
                string2 = "track";
                n2 = 4;
                break;
            }
            case 56457: {
                string2 = "duration";
                n2 = 6;
                break;
            }
            case 56398: {
                n2 = 65535;
                break;
            }
            case 56392: {
                string2 = "description";
                n2 = 65535;
                break;
            }
            case 56390: {
                n2 = 65535;
                break;
            }
            case 56388: {
                n2 = 65535;
                break;
            }
            case 56385: {
                n2 = 10;
                break;
            }
            case 56331: {
                n2 = 6;
                break;
            }
            case 56329: {
                n2 = 65535;
                break;
            }
            case 56327: {
                n2 = 65535;
                break;
            }
            case 56324: {
                n2 = 8;
                break;
            }
            case 56323: {
                n2 = 4;
                break;
            }
            case 56322: {
                n2 = 4;
                break;
            }
            case 56321: {
                n2 = 6;
            }
        }
        if (string2 != null) {
            arrayList.add(string2);
            return new Property(n, n2, arrayList.size() - 1);
        }
        return new Property(n, n2, -1);
    }

    private native String format_date_time(long var1);

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public int getPropertyList(ContentProviderClient contentProviderClient, String string2, MtpStorageManager.MtpObject mtpObject, MtpPropertyList mtpPropertyList) {
        n = mtpObject.getId();
        string3 = mtpObject.getPath().toString();
        arrproperty = this.mProperties;
        n2 = arrproperty.length;
        object = null;
        i = 0;
        do {
            block33 : {
                block31 : {
                    if (i >= n2) {
                        if (object == null) return 8193;
                        object.close();
                        return 8193;
                    }
                    property = arrproperty[i];
                    if (property.column != -1 && object == null) {
                        block32 : {
                            n3 = mtpObject.getFormat();
                            try {
                                cursor = contentProviderClient.query(MtpDatabase.getObjectPropertiesUri(n3, string2), this.mColumns, "_data=?", new String[]{string3}, null, null);
                                object2 = cursor;
                                if (cursor != null) {
                                    object2 = cursor;
                                    object = cursor;
                                    if (!cursor.moveToNext()) {
                                        object = cursor;
                                        cursor.close();
                                        object2 = null;
                                    }
                                }
                                object = object2;
                                break block31;
                            }
                            catch (RemoteException remoteException) {
                                break block32;
                            }
                            catch (IllegalArgumentException illegalArgumentException) {
                                return 43009;
                            }
                            catch (RemoteException remoteException) {
                                // empty catch block
                            }
                        }
                        Log.e(MtpPropertyGroup.TAG, "Mediaprovider lookup failed");
                        break block31;
                        catch (IllegalArgumentException illegalArgumentException) {
                            // empty catch block
                        }
                        return 43009;
                    }
                }
                switch (property.code) {
                    default: {
                        n3 = property.type;
                        ** break;
                    }
                    case 56979: 
                    case 56985: 
                    case 56986: {
                        mtpPropertyList.append(n, property.code, 6, 0L);
                        break;
                    }
                    case 56978: 
                    case 56980: {
                        mtpPropertyList.append(n, property.code, 4, 0L);
                        break;
                    }
                    case 56473: {
                        n3 = 0;
                        if (object != null) {
                            n3 = object.getInt(property.column);
                        }
                        object2 = new StringBuilder();
                        object2.append(Integer.toString(n3));
                        object2.append("0101T000000");
                        object2 = object2.toString();
                        mtpPropertyList.append(n, property.code, (String)object2);
                        break;
                    }
                    case 56459: {
                        n3 = object != null ? object.getInt(property.column) : 0;
                        mtpPropertyList.append(n, property.code, 4, n3 % 1000);
                        break;
                    }
                    case 56385: {
                        l2 = mtpObject.getPath().toString().hashCode() << 32;
                        l = mtpObject.getModifiedTime();
                        mtpPropertyList.append(n, property.code, property.type, l2 + l);
                        break;
                    }
                    case 56331: {
                        n4 = property.code;
                        n3 = property.type;
                        l = mtpObject.getParent().isRoot() != false ? 0L : (long)mtpObject.getParent().getId();
                        mtpPropertyList.append(n, n4, n3, l);
                        break;
                    }
                    case 56329: 
                    case 56398: {
                        mtpPropertyList.append(n, property.code, this.format_date_time(mtpObject.getModifiedTime()));
                        break;
                    }
                    case 56327: 
                    case 56388: 
                    case 56544: {
                        mtpPropertyList.append(n, property.code, mtpObject.getName());
                        break;
                    }
                    case 56324: {
                        mtpPropertyList.append(n, property.code, property.type, mtpObject.getSize());
                        break;
                    }
                    case 56323: {
                        mtpPropertyList.append(n, property.code, property.type, 0L);
                        break;
                    }
                    case 56322: {
                        mtpPropertyList.append(n, property.code, property.type, mtpObject.getFormat());
                        break;
                    }
                    case 56321: {
                        mtpPropertyList.append(n, property.code, property.type, mtpObject.getStorageId());
                        break;
                    }
                }
                break block33;
lbl98: // 1 sources:
                if (n3 != 0) {
                    if (n3 != 65535) {
                        l = object != null ? object.getLong(property.column) : 0L;
                        mtpPropertyList.append(n, property.code, property.type, l);
                    } else {
                        object2 = "";
                        if (object != null) {
                            object2 = object.getString(property.column);
                        }
                        mtpPropertyList.append(n, property.code, (String)object2);
                    }
                } else {
                    mtpPropertyList.append(n, property.code, property.type, 0L);
                }
            }
            ++i;
        } while (true);
    }

    private class Property {
        int code;
        int column;
        int type;

        Property(int n, int n2, int n3) {
            this.code = n;
            this.type = n2;
            this.column = n3;
        }
    }

}

