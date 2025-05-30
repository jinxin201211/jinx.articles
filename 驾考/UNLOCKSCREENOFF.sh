"D:\Program Files\scrcpy-win64-v1.23\scrcpy.exe"  --turn-screen-off --stay-awake --show-touches --power-off-on-close &
sleep 5
echo "device ready"
# adb shell "input tap 600 1300; sleep 0.01; input tap 600 1300;"
adb shell input swipe 500 1500 500 500
adb shell input tap 259 862
adb shell input tap 546 862
adb shell input tap 846 862
adb shell input tap 259 1062
