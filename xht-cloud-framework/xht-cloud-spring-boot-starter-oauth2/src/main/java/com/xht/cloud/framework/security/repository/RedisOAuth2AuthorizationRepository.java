package com.xht.cloud.framework.security.repository;

import com.xht.cloud.framework.security.domain.redis.RedisOAuth2Authorization;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 描述 ：Redis认证 Repository
 *
 * @author 小糊涂
 **/
@Repository
public interface RedisOAuth2AuthorizationRepository extends CrudRepository<RedisOAuth2Authorization, String> {

    Optional<RedisOAuth2Authorization> findByState(String token);

    Optional<RedisOAuth2Authorization> findByAuthorizationCodeValue(String token);

    Optional<RedisOAuth2Authorization> findByAccessTokenValue(String token);

    Optional<RedisOAuth2Authorization> findByOidcIdTokenValue(String token);

    Optional<RedisOAuth2Authorization> findByRefreshTokenValue(String token);

    Optional<RedisOAuth2Authorization> findByUserCodeValue(String token);

    Optional<RedisOAuth2Authorization> findByDeviceCodeValue(String token);
}
