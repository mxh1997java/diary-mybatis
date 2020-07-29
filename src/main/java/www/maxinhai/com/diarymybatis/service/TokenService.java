package www.maxinhai.com.diarymybatis.service;

public interface TokenService {

    int addToken(String key, Object token) throws Exception;

    int delToken(String key) throws Exception;

    boolean checkToken(String key) throws Exception;

}
