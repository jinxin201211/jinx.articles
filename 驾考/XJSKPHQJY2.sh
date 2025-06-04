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
  adb shell "input tap 86 2300;input tap 86 2300;input tap 990 2300;input tap 1058 1873;input tap 810 734;input tap 1013 549;"
  i=`expr $i + 1`;
done