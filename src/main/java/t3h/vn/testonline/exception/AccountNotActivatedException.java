package t3h.vn.testonline.exception;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class AccountNotActivatedException extends AuthenticationException {
    public AccountNotActivatedException(String msg) {
        super(msg);
    }
    public AccountNotActivatedException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
