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

function advertise() {
  log "等待广告加载...";
  sleep 3;

  log "滑动广告页面30s...";
  swipe; #TODO

  log "关闭广告页面";
  tap 983 177; #TODO
  sleep 1;

  log "等待广告奖励加载...";
  sleep 3;

  log "关闭广告奖励";
  tap 588 2074; #TODO
  sleep 1;
}

function patrol() {
  log "点击战斗";
  tap 499 2303;
  sleep 1;
  log "点击巡逻车";
  tap 117 1929;
  sleep 1;
  #快速巡逻
  quickPatrol=4;
  while ((quickPatrol<=3))
  do
    log "快速巡逻第 $quickPatrol 轮"
    log "点击快速巡逻";
    tap 356 1810;
    sleep 1;
    log "等待巡逻奖励加载...";
    sleep 3;
    log "关闭巡逻奖励";
    tap 588 2074; #TODO
    sleep 1;
    ((quickPatrol++))
  done

  log "关闭巡逻车";
  tap 588 2074;
  sleep 1;

  log "点击巡逻车";
  tap 117 1929;
  sleep 1;

  #广告巡逻
  adPatrol=1;
  while ((adPatrol<=5))
  do
    log "广告巡逻第 $adPatrol 轮";
    log "点击观看广告";
    tap 356 1810;
    advertise;
    ((adPatrol++))
  done

  log "关闭巡逻车";
  tap 588 2074;
  sleep 1;
}

#体力奖励
function chickenLeg(){
  log "点击鸡腿";
  tap 699 253;
  sleep 1;
  log "点击鸡腿免费";
  tap 365 1221;
  sleep 1;
  advertise;
  log "关闭鸡腿窗口";
  tap 588 2074;
  sleep 1;
}

#商城奖励
function mall() {
  log "点击商城";
  tap 108 2293;
  sleep 1;
  log "点击普通宝箱免费";
  tap 114 1017;
  sleep 1;
  advertise;
  log "点击璀璨宝箱免费";
  tap 109 1606;
  sleep 1;
  advertise;
  log "滑动页面到底";
  adb shell input swipe 546 2114 546 0;
  sleep 1;
  adb shell input swipe 546 2114 546 0;
  sleep 1;
  log "点击金币免费";
  tap 202 1655;
  sleep 1;
  advertise;
}

#观影宝藏
function videoTreasure() {
  log "点击战斗";
  tap 499 2303;
  sleep 1;
  log "左侧栏滑动到底";
  adb shell input swipe 65 1230 65 0;
  sleep 1;
  adb shell input swipe 65 1230 65 0;
  sleep 1;
  #再向上滑动一些
  adb shell input swipe 72 859 72 900;
  # adb shell input swipe 72 859 72 960;
  # adb shell input swipe 72 859 72 1010;
  # adb shell input swipe 72 859 72 1030;
  # adb shell input swipe 72 859 72 1060;
  # adb shell input swipe 72 859 72 1090;
  sleep 1;
  log "点击观影宝藏";
  tap 75 635;
  sleep 1;

  #观影
  adVideoTreasure=1;
  while ((adVideoTreasure<=5))
  do
    log "观影第 $adVideoTreasure 轮";
    log "点击5/5";
    tap 545 1987;
    advertise;
    ((adVideoTreasure++))
  done

  log "退出观影宝藏";
  tap 97 2308;
  sleep 1;
}

#观影宝藏
function videoTreasure2() {
  log "点击战斗";
  tap 499 2303;
  sleep 1;
  log "左侧栏滑动到底";
  adb shell input swipe 65 1230 65 0;
  sleep 1;
  adb shell input swipe 65 1230 65 0;
  sleep 1;
  log 再向上滑动一些
  adb shell input swipe 72 859 72 880;
  # adb shell input swipe 72 859 72 900;
  sleep 1;
  log "点击观影宝藏";
  tap 75 635;
  # tap 75 700;
  sleep 1;

  #观影
  adVideoTreasure=1;
  while ((adVideoTreasure<=5))
  do
    log "观影第 $adVideoTreasure 轮";
    log "点击5/5";
    tap 545 1987;
    advertise;
    ((adVideoTreasure++))
  done

  log "退出观影宝藏";
  tap 97 2308;
  sleep 1;
}

#活动奖励
function activity() {
  log "点击战斗";
  tap 499 2303;
  sleep 1;
  log "左侧栏滑动到底";
  adb shell input swipe 65 1230 65 0;
  sleep 1;
  adb shell input swipe 65 1230 65 0;
  sleep 1;
  log "点击活动";
  tap 62 1165;
  sleep 1;
  log "点击特惠礼包";
  tap 968 2299;
  sleep 1;
  log "点击体力免费";
  tap 891 849;
  sleep 1;
  advertise;
  log "关闭活动页面";
  tap 120 2311;
  sleep 1;
}

#军团任务
function legion() {
  log "点击军团";
  tap 845 2263;
  sleep 1;
  log "点击军团捐献";
  tap 905 1582;
  sleep 1;
  log "点击1/1";
  tap 371 1687;
  sleep 1;
  advertise;
  log "关闭军团捐献";
  tap 614 1932;
  sleep 1;
}

function switch332() {
  log "点击头像";
  tap 105 291;
  sleep 1;
  log "点击选服";
  tap 745 1874;
  sleep 1;
  log "选择332服";
  tap 518 684;
  log "等待服务器切换...";
  sleep 10;
}

function switch114() {
  log "点击头像";
  tap 105 291;
  sleep 1;
  log "点击选服";
  tap 745 1874;
  sleep 1;
  log "选择114服";
  tap 527 811;
  log "等待服务器切换...";
  sleep 10;
}

# switch114;
patrol;
# videoTreasure;
# videoTreasure2;
chickenLeg;
mall;
activity;
legion;
# log "114任务完成";

# switch332
# patrol;
# videoTreasure2;
# chickenLeg;
# mall;
# activity;
# log "332任务完成";

log "所有任务完成";