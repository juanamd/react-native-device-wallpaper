# react-native-device-wallpaper

Set the device's wallpaper and/or lock screen from React Native.

- **Android only**
- **New Architecture only** (TurboModule). Requires React Native `>= 0.77`.
- The image is center-cropped to the screen size before being applied.

> Looking for the old-architecture / Flow version (`1.x`)? See the legacy branch.

## Install

```sh
yarn add https://github.com/juanamd/react-native-device-wallpaper.git
```

The `SET_WALLPAPER` and `INTERNET` permissions are added automatically through the library manifest.

## Usage

```ts
import { setWallpaper } from 'react-native-device-wallpaper';

try {
  await setWallpaper('https://i.imgur.com/DvpvklR.png', 'both');
} catch (e) {
  console.warn('Could not set wallpaper', e);
}
```

## API

```ts
type WallpaperTarget = 'home' | 'lock' | 'both';

setWallpaper(uri: string, target?: WallpaperTarget): Promise<void>; // target defaults to 'both'
```

- `uri` can be an `http(s)://`, `file://` or `content://` URI. Plain `http://` URLs require your app to allow cleartext traffic.
- The promise rejects if the image can't be loaded/decoded or the wallpaper can't be set (previously it resolved with `false`).

## Migrating from 1.x

| 1.x                                  | 2.x                              |
| ------------------------------------ | -------------------------------- |
| `DeviceWallpaper.setWallPaper(uri)`  | `setWallpaper(uri, 'home')`      |
| `DeviceWallpaper.setLockScreen(uri)` | `setWallpaper(uri, 'lock')`      |
| `DeviceWallpaper.setBoth(uri)`       | `setWallpaper(uri, 'both')`      |
| Resolved `boolean`                   | Resolves `void`, rejects on error |

## Development

```sh
yarn install
yarn typecheck
```

There is no build step: the package ships its TypeScript source and Metro compiles it in the consuming app.
