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

zdddx=868;
zdddy=1815;
# zdddx=840;
# zdddy=1713;

i=100;
while ((i<=15))
do
  log "第一行第 $i 轮";

  log "点击我的";
  tap 977 2285;
  sleep 1;

  log "点击种豆得豆";
  tap $zdddx $zdddy;
  sleep 2;

  log "点击赚收获值";
  tap 970 1566;
  sleep 0.2;

  log "点击去逛逛";
  tap 909 1370;
  sleep 10;

  log "点击返回";
  backspace;
  sleep 0.2;
  tap 741 1344;
  log "点击返回";
  backspace;
  sleep 0.2;
  log "点击返回";
  backspace;
  sleep 0.2;

  i=`expr $i + 1`;

done

j=100;
while ((j<=5))
do
  log "第二行第 $j 轮";

  log "点击我的";
  tap 977 2285;
  sleep 1;

  log "点击种豆得豆";
  tap $zdddx $zdddy;
  sleep 2;

  log "点击赚收获值";
  tap 970 1566;
  sleep 0.2;

  log "点击去逛逛";
  tap 909 1601;
  sleep 10;

  log "点击返回";
  backspace;
  sleep 0.2;
  tap 741 1344;
  log "点击返回";
  backspace;
  sleep 0.2;
  log "点击返回";
  backspace;
  sleep 0.2;
  
  j=`expr $j + 1`;

done

#关注频道
log "点击我的";
tap 977 2285;
sleep 1;
log "点击种豆得豆";
tap $zdddx $zdddy;
sleep 2;
log "点击赚收获值";
tap 970 1566;
sleep 0.2;
log "点击去逛逛";
tap 909 1370;
sleep 5;
log "关注第一个频道";
tap 210 854;
sleep 3;
backspace;
# log "点击返回";
# backspace;
# sleep 0.2;
# tap 741 1344;
# log "点击返回";
# backspace;
# sleep 0.2;
# log "点击返回";
# backspace;
# sleep 0.2;

# log "点击我的";
# tap 977 2285;
# sleep 1;
# log "点击种豆得豆";
# tap $zdddx $zdddy;
# sleep 2;
# log "点击赚收获值";
# tap 970 1566;
# sleep 0.2;
# log "点击去逛逛";
# tap 909 1370;
# sleep 5;
log "关注第二个频道";
tap 517 854;
sleep 3;
backspace;
# log "点击返回";
# backspace;
# sleep 0.2;
# tap 741 1344;
# log "点击返回";
# backspace;
# sleep 0.2;
# log "点击返回";
# backspace;
# sleep 0.2;

# log "点击我的";
# tap 977 2285;
# sleep 1;
# log "点击种豆得豆";
# tap $zdddx $zdddy;
# sleep 2;
# log "点击赚收获值";
# tap 970 1566;
# sleep 0.2;
# log "点击去逛逛";
# tap 909 1370;
# sleep 5;
log "关注第三个频道";
tap 850 854;
sleep 3;
backspace;
log "点击返回";
backspace;
sleep 0.2;
tap 741 1344;
log "点击返回";
backspace;
sleep 0.2;
log "点击返回";
backspace;
sleep 0.2;


#关注店铺
log "点击我的";
tap 977 2285;
sleep 1;
log "点击种豆得豆";
tap $zdddx $zdddy;
sleep 2;
log "点击赚收获值";
tap 970 1566;
sleep 0.2;
log "点击去逛逛";
tap 909 1370;
sleep 5;

log "关注第一个店铺";
tap 193 772;
sleep 3;
backspace;
# log "点击返回";
# backspace;
# sleep 0.2;
# tap 741 1344;
# log "点击返回";
# backspace;
# sleep 0.2;
# log "点击返回";
# backspace;
# sleep 0.2;

# log "点击我的";
# tap 977 2285;
# sleep 1;
# log "点击种豆得豆";
# tap $zdddx $zdddy;
# sleep 2;
# log "点击赚收获值";
# tap 970 1566;
# sleep 0.2;
# log "点击去逛逛";
# tap 909 1370;
# sleep 5;

log "关注第二个店铺";
tap 540 772;
sleep 3;
backspace;
# log "点击返回";
# backspace;
# sleep 0.2;
# tap 741 1344;
# log "点击返回";
# backspace;
# sleep 0.2;
# log "点击返回";
# backspace;
# sleep 0.2;

# log "点击我的";
# tap 977 2285;
# sleep 1;
# log "点击种豆得豆";
# tap $zdddx $zdddy;
# sleep 2;
# log "点击赚收获值";
# tap 970 1566;
# sleep 0.2;
# log "点击去逛逛";
# tap 909 1370;
# sleep 5;
log "关注第三个店铺";
tap 873 772;
sleep 3;
backspace;
log "点击返回";
backspace;
sleep 0.2;
tap 741 1344;
log "点击返回";
backspace;
sleep 0.2;
log "点击返回";
backspace;
sleep 0.2;

#收取
log "点击我的";
tap 977 2285;
sleep 1;
log "点击种豆得豆";
tap $zdddx $zdddy;
sleep 2;
k=0;
while ((k<=20))
do

  log "收取第 $k 次 上";
  tap 629 521;
  sleep 1;

  log "关闭可能弹出的中奖券";
  tap 1008 650;
  sleep 0.2;

  log "收取第 $k 次 下";
  tap 191 600;
  sleep 1;

  log "关闭可能弹出的中奖券";
  tap 1008 650;
  sleep 0.2;

  k=`expr $k + 1`;

done

