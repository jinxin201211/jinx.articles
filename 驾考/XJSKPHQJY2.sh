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
  # if (( i % 100 == 0 )); then
  #    adb shell "input tap 86 2300;sleep 3s;input tap 86 2300;input tap 545 2000;input tap 86 2300;input tap 86 2300;input tap 990 2300;"
  # fi
  # adb shell "input tap 1058 1873;input tap 810 734;input tap 1013 549;"
  adb shell "input tap 909 1680;input tap 692 727;input tap 1013 549;"
  ((i++))
done