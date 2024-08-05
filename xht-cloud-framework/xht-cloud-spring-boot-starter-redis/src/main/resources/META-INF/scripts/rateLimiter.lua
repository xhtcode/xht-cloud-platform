--获取KEY
local key = KEYS[1]
-- 获取第一个参数
local limit = tonumber(ARGV[1])
-- 获取redis里面的值
local curentLimit = tonumber(redis.call('get', key) or "0")

if curentLimit + 1 > limit
    then return 0
else
    -- 自增长 1
    redis.call('INCRBY', key, 1)
    -- 设置过期时间
    redis.call('EXPIRE', key, ARGV[2])
    return curentLimit + 1
end
