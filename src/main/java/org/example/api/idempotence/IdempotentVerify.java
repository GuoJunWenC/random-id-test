package org.example.api.idempotence;

public interface IdempotentVerify {
    /**
     * 保存
     *
     * @param value        令牌的值
     * @param idempotentId 唯一标识
     */
    void save(String idempotentId, String value);

    /**
     * 删除
     *
     * @param idempotentId 唯一标识
     */
    boolean delete(String idempotentId);

    /**
     * 验证令牌
     *
     * @param token 令牌
     * @param value 令牌的值
     * @return 令牌是否有效
     */
    boolean validToken(String token, String value);

}
