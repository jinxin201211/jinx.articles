let init_power = 11688,
  soldider_levelup_needle = 70,
  soldider_levelup_coin = 7450,
  soldider_levelup_power = 9,
  soldider_count = 8,
  outfit_count = 6,
  outfit_levelup_coin = 11194,
  outfit_levelup_power = 10;

//        int coin = 1212384,
//                needle_sum = 4982;

let coin = 1212384,
  needle_sum = 4982;

let soldier_levelup = Math.floor(needle_sum / (soldider_levelup_needle * soldider_count));
coin = coin - soldier_levelup * soldider_levelup_coin * soldider_count;
let outfit_levelup = Math.floor(coin / (outfit_levelup_coin * outfit_count));
coin = coin - outfit_levelup * outfit_levelup_coin * outfit_count;
let final_power = init_power + soldider_levelup_power * soldier_levelup * soldider_count + outfit_levelup_power * outfit_levelup * outfit_count;

console.log(`最终战力：${final_power}，剩余金币：${coin}`);