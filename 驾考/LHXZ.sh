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

j=30;
while ((j<=50))
do
  log "第 $j 轮";

  tap 534 2275;
  tap 534 2275;
  sleep 3;

  tap 891 2107;
  sleep 0.3;

  tap 891 2107;
  sleep 0.3;

  tap 175 2105;
  sleep 0.3;

  tap 175 2105;
  sleep 1;

  j=`expr $j + 1`;

done
