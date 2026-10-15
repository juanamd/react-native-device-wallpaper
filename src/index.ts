import NativeDeviceWallpaper from './NativeDeviceWallpaper';

/** Which screen(s) the wallpaper should be applied to. */
export type WallpaperTarget = 'home' | 'lock' | 'both';

/**
 * Sets the device wallpaper. The image is center-cropped to the screen size.
 *
 * @param uri Image location. Supports `http(s)://`, `file://` and `content://` URIs.
 * @param target Where to apply the wallpaper. Defaults to `'both'`.
 * @throws If the image can't be loaded or the wallpaper can't be set.
 */
export function setWallpaper(
	uri: string,
	target: WallpaperTarget = 'both'
): Promise<void> {
	return NativeDeviceWallpaper.setWallpaper(uri, target);
}
