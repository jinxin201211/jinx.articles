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

log "点击我的";
tap 977 2285;
sleep 1;

log "点击互动游戏";
tap 853 1259;
sleep 3;

log "点击签到";
tap 925 775;
sleep 5;

i=1;
while ((i<=5))
do
  log "点击奖票赚不停 $i";
  tap 179 1359;
  sleep 10;
  log "点击返回";
  backspace;
  sleep 3;
  i=`expr $i + 1`;
done

log "点击领取";
tap 967 1200;
sleep 10;

log "点击东东农场";
tap 224 1864;
sleep 10;
log "点击返回";
backspace;
sleep 3;
log "领取东东农场";
tap 235 1770;
sleep 10;

log "点击种豆得豆";
tap 539 1864;
sleep 10;
log "点击返回";
backspace;
sleep 3;
log "领取种豆得豆";
tap 567 1770;
sleep 10;

log "点击赚红包";
tap 861 1864;
sleep 10;
log "点击返回";
backspace;
sleep 3;
log "领取赚红包";
tap 892 1770;
sleep 10;

log "点击返回";
backspace;
sleep 3;