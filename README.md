
# react-native-device-wallpaper
Set device's wallpaper with react-native 
***Only Android is supported for now***
Based on **[react-native-wallpaper](https://github.com/thecodrr/react-native-wallpaper)** but with an improved API, and the image gets centered-cropped before its set as a wallpaper.

## Install
    yarn add https://github.com/juanamd/react-native-device-wallpaper.git

## Usage
```typescript
import DeviceWallpaper from "react-native-device-wallpaper";

const img = "http://i.imgur.com/DvpvklR.png";
const setSuccessfully = await DeviceWallpaper.setWallPaper(img);
console.log("Set successfully?: ", setSuccessfully);
```

## API
```typescript
DeviceWallpaper.setWallPaper(imgUri: string): Promise<boolean>;
DeviceWallpaper.setLockScreen(imgUri: string): Promise<boolean>;
DeviceWallpaper.setBoth(imgUri: string): Promise<boolean>;
```
