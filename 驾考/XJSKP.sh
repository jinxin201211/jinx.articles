echo "start ...";

waiting=20;

while ((waiting>0))
do
    echo "请在 $waiting s内打开主界面";
    waiting=`expr $waiting - 1`;
    sleep 1;
done

i=0;

while (true)
do
    echo "round $i";
    adb shell input tap 546 2011;
    sleep 1;
    adb shell input tap 546 2011;
    i=`expr $i + 1`;
    sleep 10;
done
    