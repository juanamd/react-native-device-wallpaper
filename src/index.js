//@flow
// $FlowFixMe
import { NativeModules } from "react-native";

const { RNDeviceWallpaper } = NativeModules;

class DeviceWallpaper {
	/**
	 * If device is running Android version below N (api 24) it will set both wallpaper and lock screen
	*/
	static async setWallPaper(imgUri: string): Promise<boolean> {
		return await RNDeviceWallpaper.setWallPaper(imgUri, "system");
	}

	/**
	 * If device is running Android version below N (api 24) it will set both wallpaper and lock screen
	*/
	static async setLockScreen(imgUri: string): Promise<boolean> {
		return await RNDeviceWallpaper.setWallPaper(imgUri, "lock");
	}

	static async setBoth(imgUri: string): Promise<boolean> {
		return await RNDeviceWallpaper.setWallPaper(imgUri, "both");
	}
}

export default DeviceWallpaper;
