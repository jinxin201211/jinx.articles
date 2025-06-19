function log() {
  time=$(date "+%Y-%m-%d %H:%M:%S");
  echo "$time --------> $1";
}

function tap() {
  adb shell input tap $1 $2;
}

echo "start ...";

round=0;

while ((round<50))
do
  log "精英关第 $round 轮";
  log "点击向僵尸开炮游戏图标";
  tap 916 940;
  second=15;
  while ((second>0))
  do
    log "等待 $second 秒游戏界面加载完成...";
    ((second--))
    sleep 1;
  done
  
  log "点击开始游戏";
  tap 547 1924;
  sleep 0.2;
  
  log "关闭游戏";
  tap 1000 167;

  ((round++))

  if (( $round % 5 == 0 )); then
    log "清理后台...";
    adb shell input keyevent 187;
    adb shell input tap 528 2168;
  fi

  sleep 1;
done
