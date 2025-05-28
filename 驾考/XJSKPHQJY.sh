function log() {
  time=$(date "+%Y-%m-%d %H:%M:%S");
  echo "$time --------> $1";
}

function tap() {
  adb shell input tap $1 $2;
}

echo "start ...";

i=0;

while [[ 1+1 ]]
do
  log "round $i";

  log "点击副本邀请";
  tap 1058 1873;
  # tap 1058 1667;
  sleep 0.01;
  log "接受组队邀请";
  tap 810 734;
  sleep 0.01;
  log "接受失败时关闭组队邀请窗口";
  tap 1013 549;
  sleep 0.01;
  i=`expr $i + 1`;
  # sleep 0.2;
done