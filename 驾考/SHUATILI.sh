function log() {
  time=$(date "+%Y-%m-%d %H:%M:%S");
  echo "$time --------> $1";
}

function tap() {
  adb shell input tap $1 $2;
}

echo "start ...";

waiting=5;

while ((waiting>0))
do
  log "请在 $waiting s内打开主界面";
  ((waiting++))
  sleep 1;
done

i=0;

while [[ 1+1 ]]
do
  log "round $i";
  log "打开小程序";
  tap 922 927; #打开小程序
  sleep 1;
  log "等待小程序进入...";
  sleep 15;
  log "开始游戏";
  tap 540 1932; #开始游戏
  sleep 0.5;
  log "关闭小程序";
  tap 980 165; #关闭小程序
  sleep 5;
  ((i++))
  sleep 1;
done
    