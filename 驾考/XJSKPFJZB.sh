function log() {
  time=$(date "+%Y-%m-%d %H:%M:%S");
  echo "$time --------> $1";
}

function tap() {
  adb shell input tap $1 $2;
}

echo "start ...";

waiting=10;

while ((waiting>0))
do
  log "请在 $waiting s内打开分解界面";
  ((waiting--))
  sleep 1;
done

i=0;

while [[ 1+1 ]]
do
  log "round $i";
  tap 122 1400;
  tap 280 1400;
  tap 440 1400;
  tap 610 1400;
  tap 780 1400;
  tap 940 1400;
  tap 122 1536;
  tap 280 1536;
  tap 440 1536;
  tap 610 1536;
  tap 780 1536;
  tap 940 1536;
  tap 550 2115;
  sleep 1;
  tap 588 2294;
  ((i++))
  # sleep 1;
done
    