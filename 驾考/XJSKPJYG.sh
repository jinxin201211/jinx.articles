function log() {
  time=$(date "+%Y-%m-%d %H:%M:%S");
  echo "$time --------> $1";
}

function tap() {
  adb shell input tap $1 $2;
}

echo "start ...";

round=0;

# while ((round<100))
while [[ 1+1 ]]
do
  log "精英关第 $round 轮";
  log "点击向僵尸开炮游戏图标";
  # tap 942 627;
  tap 942 332;
  second=18;
  while ((second>0))
  do
    log "等待 $second 秒游戏界面加载完成...";
    ((second--))
    sleep 1;
  done

  if (( $round % 10 == 0  && $round != 0)); then
    log "收取邮件...";
    adb shell input tap 1016 477;
    sleep 0.2;
    adb shell input tap 890 611;
    sleep 0.2;
    adb shell input tap 556 1863;
    sleep 3;
    adb shell input tap 542 2133;
    sleep 0.2;
    adb shell input tap 972 563;
  fi
  
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
