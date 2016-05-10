package uk.ac.napier.passitonapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.ByteArrayOutputStream;

/** The bitmap array utility **/
public class Util {
    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
        return os.toByteArray();
    }

    public static Bitmap byteArrayToBitmap(byte[] bytearray) {
        return BitmapFactory.decodeByteArray(bytearray, 0, bytearray.length);
    }
}
