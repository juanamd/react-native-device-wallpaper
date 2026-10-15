package os.juanamd.devicewallpaper

import com.facebook.react.BaseReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.model.ReactModuleInfo
import com.facebook.react.module.model.ReactModuleInfoProvider

class DeviceWallpaperPackage : BaseReactPackage() {
	override fun getModule(name: String, reactContext: ReactApplicationContext): NativeModule? =
		if (name == DeviceWallpaperModule.NAME) DeviceWallpaperModule(reactContext) else null

	override fun getReactModuleInfoProvider() = ReactModuleInfoProvider {
		mapOf(
			DeviceWallpaperModule.NAME to ReactModuleInfo(
				DeviceWallpaperModule.NAME, // name
				DeviceWallpaperModule.NAME, // className
				false, // canOverrideExistingModule
				false, // needsEagerInit
				false, // isCxxModule
				true // isTurboModule
			)
		)
	}
}
