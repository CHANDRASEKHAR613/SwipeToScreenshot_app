# 3-Finger Swipe Screenshot Android Application

## App Overview
This Android Studio project contains the implementation of the requested Swipe Screenshot application with the following UI & logic:
1. **Button 1**: `TURN ON Swipe screenshot`
2. **Button 2**: `TURN OFF Swipe screenshot`
3. **Toggle State**: When the feature is turned on, the 1st button shows active status, while the 2nd button allows turning it off (and when turned off, the 2nd button is greyed out and unclickable).
4. **Screenshot Engine**: Uses Android's `AccessibilityService` `GLOBAL_ACTION_TAKE_SCREENSHOT` (compatible with Android 9 / Pie and newer).

## How to Build the APK in Android Studio:
1. Open **Android Studio** (Hedgehog, Iguana, or newer).
2. Select **File > Open...** and select this extracted project folder.
3. Allow Gradle to sync.
4. Click **Build > Build Bundle(s) / APK(s) > Build APK(s)**.
5. Android Studio will generate the debug APK in `app/build/outputs/apk/debug/app-debug.apk`.
6. Transfer the APK to your Samsung Galaxy phone and install it.

## Samsung Built-In Alternatives (No APK Required!)
Samsung Galaxy phones running One UI have official ways to take 3-finger or gesture screenshots:
1. **Good Lock -> Home Up -> Multi-finger Gestures**:
   - Install Good Lock from the Galaxy Store.
   - Open Home Up -> Gestures -> Multi-finger Gestures.
   - Assign 3-finger swipe down to **Screenshot**.
2. **One Hand Operation +**:
   - Set diagonal downward swipe from the screen edge to take an instant screenshot with one thumb.
3. **Palm Swipe to Capture**:
   - Go to Settings > Advanced features > Motions and gestures > Palm swipe to capture.
