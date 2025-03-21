package t3h.vn.testonline.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import t3h.vn.testonline.entities.UserEntity;
import t3h.vn.testonline.exception.AccountNotActivatedException;
import t3h.vn.testonline.repository.UserRepo;

import java.util.Collection;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserRepo userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserEntity user = userRepo.findFirstByUsername(username);
		if (user == null) throw new UsernameNotFoundException("Không tồn tại tài khoản");
		if (user.getStatus() == 0) throw new AccountNotActivatedException("Tài khoản chưa được kích hoạt, " +
				                                                          "vui kích hoạt tài khoản của bạn trước khi đăng nhập");
		return new UserDetailImpl(user);
	}

	public static class UserDetailImpl implements UserDetails {
		@Getter
		UserEntity user;

		public UserDetailImpl(UserEntity user) {
			this.user = user;
		}

		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			return List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
		}

		@Override
		public String getPassword() {
			return user.getPassword();
		}

		@Override
		public String getUsername() {
			return user.getUsername();
		}

		@Override
		public boolean isAccountNonExpired() {
			return true;
		}

		@Override
		public boolean isAccountNonLocked() {
			return true;
		}

		@Override
		public boolean isCredentialsNonExpired() {
			return true;
		}

		@Override
		public boolean isEnabled() {
			return user.getStatus() == 1;
		}
	}

}
