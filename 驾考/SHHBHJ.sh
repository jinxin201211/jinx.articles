function log() {
  time=$(date "+%Y-%m-%d %H:%M:%S");
  echo "$time --------> $1";
}

function tap() {
  adb shell input tap $1 $2;
}

function swipe() {
  adb shell input swipe 550 1783 534 1386 35000;
}

function backspace() {
  adb shell input keyevent 4;
}

j=1;
while ((j<=100))
do
  log "第 $j 轮";

  tap 575 1859;
  sleep 1;
  tap 782 1782;
  sleep 1;
  tap 575 1859;
  sleep 1;
  tap 346 1782;
  sleep 1;

  ((j++))

done
