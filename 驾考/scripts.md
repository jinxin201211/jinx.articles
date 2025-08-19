adb connect 192.168.124.19
adb connect 192.168.1.110

adb pair 192.168.124.19:41395

sh D:\\CUIT\\Java\\jinx.articles\\驾考\\JDHDYX.sh
sh D:\\CUIT\\Java\\jinx.articles\\驾考\\ADJOBS.sh
sh D:\\CUIT\\Java\\jinx.articles\\驾考\\JDZDDD.sh
sh D:\\CUIT\\Java\\jinx.articles\\驾考\\XJSKPJYG.sh

sh D:\\Repository\\jinx.articles\\驾考\\XJSKPJYG.sh

801  
591  
481  
361  
241  
124  
62

let gongji = 11688;
let qianggong = 1008;
let qiangpi = 1035 / 100.0;
let yanfaqiangshang = 185 / 100.0;
let hexin = 120 / 100.0;
let baoshang = 1613 / 100.0;
let yanfabaoshang = 72 / 100.0;

let bubaoji = 0.24 * (gongji + qianggong) * (1 + qiangpi + yanfaqiangshang) * (1 + hexin) * 0.9;
let baoji = bubaoji * (1 + baoshang + yanfabaoshang);

console.log(`不暴击伤害：${bubaoji}`);
console.log(`暴击伤害：${baoji}`);