package com.amostra.comanda.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;
import android.widget.ImageView;

import com.amostra.comanda.App;
import com.amostra.comanda.R;

import java.io.File;


public class Utils {

    private static float density = 1;
    private static Point displaySize = new Point();
    public static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final String TAG = "Utils";

    static {
        density = App.getContext().getResources()
                .getDisplayMetrics().density;
        checkDisplaySize();
    }

    public static RoundedBitmapDrawable circularBitmap(Bitmap imageBitmap){
        RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(App.getContext().getResources(), imageBitmap);
        imageDrawable.setCircular(true);
        imageDrawable.setCornerRadius(Math.max(imageBitmap.getWidth(), imageBitmap.getHeight()) / 2.0f);
        return imageDrawable;
    }

    static int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int toolbarHeight = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();

        return toolbarHeight;
    }

    public static int dp(float value) {
        return (int) Math.ceil(density * value);
    }

    public static float dpf2(float value) {
        return density * value;
    }

    private static void checkDisplaySize() {
        try {
            WindowManager manager = (WindowManager) App.getContext()
                    .getSystemService(Context.WINDOW_SERVICE);
            if (manager != null) {
                Display display = manager.getDefaultDisplay();
                if (display != null) {
                    display.getSize(displaySize);
                    Log.e("tmessages", "display size = " + displaySize.x+ " " + displaySize.y);
                }
            }
        } catch (Exception e) {
            Log.e("tmessages", e.toString());
        }
    }

    @SuppressLint("NewApi")
    private static class HoneycombOrHigherUtils {
        static int getSizeInBytes(final Bitmap bitmap) {
            return bitmap.getByteCount();
        }
    }

    @SuppressLint("NewApi")
    private static class GingerbreadOrHigherUtils {
        static boolean isExternalStorageRemovable() {
            return Environment.isExternalStorageRemovable();
        }
    }

    public static int getSizeInBytes(final Bitmap bitmap) {
        return HoneycombOrHigherUtils.getSizeInBytes(bitmap);
    }

    public static String getContentPathFromUri(final Context context, final Uri uri) {
        Cursor cursor = null;
        String contentPath = null;
        try {
            final String[] proj = { MediaStore.Images.Media.DATA };
            final CursorLoader loader = new CursorLoader(context, uri, proj, null, null, null);
            cursor = loader.loadInBackground();
            final int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            contentPath = cursor.getString(columnIndex);
        } catch (final Exception e) {
            Log.w(TAG, "getContentPathFromURI(" + uri.toString() + "): " + e.getMessage());
        } finally {
            if (cursor != null)
                cursor.close();
        }

        return contentPath != null ? contentPath : "";

    }

    public static File getDiskCacheDir(final Context context, final String uniqueName) {
        // Check if media is mounted or storage is built-in, if so, try and use external cache dir
        // otherwise use internal cache dir
        final boolean externalCacheAvailable = (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) ||
                !Utils.isExternalStorageRemovable();

        String cachePath;

        if (externalCacheAvailable && Utils.getExternalCacheDir(context) != null) {
            cachePath = Utils.getExternalCacheDir(context).getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }

    public static int getPixels(final int dp) {
        // Get the screen's density scale
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int)((dp * scale) + 0.5f);
    }

    private static boolean isExternalStorageRemovable() {
        GingerbreadOrHigherUtils.isExternalStorageRemovable();
        return true;
    }

    private static File getExternalCacheDir(final Context context) {
        return context.getExternalCacheDir();
    }

    public static File getExternalFileDir(final Context context) {
        return context.getExternalFilesDir("");
    }

}
