import type { TurboModule } from 'react-native';
import { TurboModuleRegistry } from 'react-native';

export interface Spec extends TurboModule {
	/**
	 * `target` is one of `"home"`, `"lock"` or `"both"`.
	 * Rejects if the image can't be loaded or the wallpaper can't be set.
	 */
	setWallpaper(uri: string, target: string): Promise<void>;
}

export default TurboModuleRegistry.getEnforcing<Spec>('DeviceWallpaper');
