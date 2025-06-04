function log() {
  time=$(date "+%Y-%m-%d %H:%M:%S");
  echo "$time --------> $1";
}

function tap() {
  adb shell input tap $1 $2;
}

echo "start ...";

waiting=20;

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
  tap 546 2011; #开始游戏、返回
  sleep 1;
  tap 540 1249; #选择词条
  sleep 1;
  tap 611 1542; #重新连接
  sleep 1;
  tap 594 2068; #继续战斗
  sleep 1;
  tap 546 2011; #开始游戏、返回
  ((i++))
  sleep 6;
done
    