package airdrop.backend.service;

public interface RedisService {

    public void set(String key, String value);

    public void setWithExpired(String key, String value, int seconds);

    public String get(String key);

    public long del(String key);

}
