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
  waiting=`expr $waiting - 1`;
  sleep 1;
done

i=0;

while [[ 1+1 ]]
do
  log "round $i";
  tap 546 2011;
  sleep 1;
  tap 546 2011;
  i=`expr $i + 1`;
  sleep 10;
done
    