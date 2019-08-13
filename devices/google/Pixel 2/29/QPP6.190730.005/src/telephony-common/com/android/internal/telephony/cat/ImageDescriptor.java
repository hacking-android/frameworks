/*
 * Decompiled with CFR 0.145.
 */
package com.android.internal.telephony.cat;

import com.android.internal.telephony.cat.CatLog;

public class ImageDescriptor {
    static final int CODING_SCHEME_BASIC = 17;
    static final int CODING_SCHEME_COLOUR = 33;
    int mCodingScheme = 0;
    int mHeight = 0;
    int mHighOffset = 0;
    int mImageId = 0;
    int mLength = 0;
    int mLowOffset = 0;
    int mWidth = 0;

    ImageDescriptor() {
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static ImageDescriptor parse(byte[] object, int n) {
        block11 : {
            int n3;
            int n2;
            ImageDescriptor imageDescriptor = new ImageDescriptor();
            Object n4 = n2 = n + 1;
            imageDescriptor.mWidth = object[n] & 255;
            n = n3 = n2 + 1;
            imageDescriptor.mHeight = object[n2] & 255;
            n4 = n2 = n3 + 1;
            imageDescriptor.mCodingScheme = object[n3] & 255;
            n = n4 = n2 + 1;
            imageDescriptor.mImageId = (object[n2] & 255) << 8;
            n = n4;
            n2 = imageDescriptor.mImageId;
            n = n4 + 1;
            try {
                imageDescriptor.mImageId = object[n4] & 255 | n2;
                n4 = n2 = n + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
            imageDescriptor.mHighOffset = object[n] & 255;
            n = n4 = n2 + 1;
            try {
                imageDescriptor.mLowOffset = object[n2] & 255;
                n = n4 + 1;
            }
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {}
            n4 = object[n4];
            imageDescriptor.mLength = (n4 & 255) << 8 | object[n] & 255;
            object = new StringBuilder();
            ((StringBuilder)object).append("parse; Descriptor : ");
            ((StringBuilder)object).append(imageDescriptor.mWidth);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(imageDescriptor.mHeight);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(imageDescriptor.mCodingScheme);
            ((StringBuilder)object).append(", 0x");
            ((StringBuilder)object).append(Integer.toHexString(imageDescriptor.mImageId));
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(imageDescriptor.mHighOffset);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(imageDescriptor.mLowOffset);
            ((StringBuilder)object).append(", ");
            ((StringBuilder)object).append(imageDescriptor.mLength);
            CatLog.d("ImageDescriptor", ((StringBuilder)object).toString());
            return imageDescriptor;
            break block11;
            catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                // empty catch block
            }
        }
        CatLog.d("ImageDescriptor", "parse; failed parsing image descriptor");
        return null;
    }
}

