-- 如果键存在，则删除它并返回 true。 如果键不存在，则直接返回 false
if redis.call('exists',KEYS[1]) == 1 then
    redis.call('del', KEYS[1])
    return true
else
    return false
end