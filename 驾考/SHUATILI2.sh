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
  ((waiting--))
  sleep 1;
done

i=0;

while [[ 1+1 ]]
do
  log "round $i";
  adb shell "input tap 544 1487;input tap 544 2000;"
  ((i++));
  sleep 1;
done
    