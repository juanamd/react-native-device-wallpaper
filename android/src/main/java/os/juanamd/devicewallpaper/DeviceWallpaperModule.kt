package os.juanamd.devicewallpaper

import android.app.WallpaperManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ThumbnailUtils
import android.net.Uri
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import java.io.IOException
import java.io.InputStream
import java.net.URL
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DeviceWallpaperModule(reactContext: ReactApplicationContext) :
	NativeDeviceWallpaperSpec(reactContext) {

	private val executor: ExecutorService = Executors.newSingleThreadExecutor()

	override fun getName() = NAME

	override fun setWallpaper(uri: String, target: String, promise: Promise) {
		val flags = when (target) {
			"home" -> WallpaperManager.FLAG_SYSTEM
			"lock" -> WallpaperManager.FLAG_LOCK
			"both" -> WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
			else -> {
				promise.reject("E_INVALID_TARGET", "Invalid wallpaper target: $target")
				return
			}
		}

		executor.execute {
			try {
				val source = decodeBitmap(uri)
				val cropped = centerCrop(source)
				try {
					WallpaperManager.getInstance(reactApplicationContext)
						.setBitmap(cropped, null, false, flags)
				} finally {
					if (cropped !== source) cropped.recycle()
					source.recycle()
				}
				promise.resolve(null)
			} catch (e: Exception) {
				promise.reject("E_SET_WALLPAPER_FAILED", e.message, e)
			}
		}
	}

	override fun invalidate() {
		executor.shutdownNow()
		super.invalidate()
	}

	private fun openStream(uri: String): InputStream {
		val parsed = Uri.parse(uri)
		return when (parsed.scheme) {
			"http", "https" -> URL(uri).openConnection().apply {
				connectTimeout = NETWORK_TIMEOUT_MS
				readTimeout = NETWORK_TIMEOUT_MS
			}.getInputStream()

			"file", "content" -> reactApplicationContext.contentResolver.openInputStream(parsed)
				?: throw IOException("Unable to open $uri")

			else -> throw IllegalArgumentException("Unsupported URI scheme: ${parsed.scheme}")
		}
	}

	private fun decodeBitmap(uri: String): Bitmap =
		openStream(uri).use { BitmapFactory.decodeStream(it) }
			?: throw IOException("Unable to decode image from $uri")

	private fun centerCrop(bitmap: Bitmap): Bitmap {
		val metrics = reactApplicationContext.resources.displayMetrics
		return ThumbnailUtils.extractThumbnail(bitmap, metrics.widthPixels, metrics.heightPixels)
	}

	companion object {
		const val NAME = "DeviceWallpaper"
		private const val NETWORK_TIMEOUT_MS = 15_000
	}
}
