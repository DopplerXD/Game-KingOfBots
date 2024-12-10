package site.dopplerxd.backend.services.user.account;

import java.util.Map;

public interface LoginService {
    Map<String, String> getToken(String username, String password);
}
