package os.juanamd.devicewallpaper;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Promise;

public class RNDeviceWallpaperModule extends ReactContextBaseJavaModule {
    Context context;

    public RNDeviceWallpaperModule(ReactApplicationContext reactContext) {
        super(reactContext);
        context = reactContext.getApplicationContext();
    }

    @Override
    public String getName() {
        return "RNDeviceWallpaper";
    }

    @ReactMethod
    public void setWallPaper(final String imgUri, final String destination, final Promise promise) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Bitmap bitmap = getBitmapFromSource(imgUri);
                    if (bitmap != null) {
                        setWallpaperBitmap(getCenterCroppedBitmap(bitmap), destination);
                        promise.resolve(true);
                    } else {
                        promise.resolve(false);
                    }
                } catch (Exception e) {
                    promise.reject(e);
                }
            }
        });
        thread.start();
    }

    private Bitmap getBitmapFromSource(String source) throws IOException {
        Bitmap bitmap = null;
        if (source.startsWith("http://") || source.startsWith("https://")) {
            URL url = new URL(source);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bitmap = BitmapFactory.decodeStream(input);
        } else if (source.startsWith("file://")) {
            bitmap = BitmapFactory.decodeFile(source.replace("file://", ""));
        }
        return bitmap;
    }

    private Bitmap getCenterCroppedBitmap(Bitmap bitmap) {
        DisplayMetrics metrics = new DisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return ThumbnailUtils.extractThumbnail(bitmap, metrics.widthPixels, metrics.heightPixels);
    }

    private void setWallpaperBitmap(Bitmap bitmap, final String destination) throws IOException {
        WallpaperManager wpManager = WallpaperManager.getInstance(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            wpManager.setBitmap(bitmap, null, false, getWallpaperDestination(destination));
        } else {
            wpManager.setBitmap(bitmap);
        }
    }

    private int getWallpaperDestination(final String destination) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) return 0;
        switch (destination) {
            case "both":
                return WallpaperManager.FLAG_SYSTEM | WallpaperManager.FLAG_LOCK;
            case "system":
                return WallpaperManager.FLAG_SYSTEM;
            case "lock":
                return WallpaperManager.FLAG_LOCK;
            default:
                return 0;
        }
    }
}
