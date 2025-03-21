package t3h.vn.testonline.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import t3h.vn.testonline.exception.AccountNotActivatedException;

import java.io.IOException;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable()
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/login", "/register", "/reactivate").anonymous()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/profile/**", "/doExam/**").hasAnyRole("ADMIN","USER")
						.anyRequest().permitAll())
							.formLogin((form) -> form.loginPage("/login")
									.usernameParameter("username")
									.passwordParameter("password")
									.loginProcessingUrl("/doLogin")
									.defaultSuccessUrl("/home")
									.failureHandler(authenticationFailureHandler())

									.permitAll())
									.logout((logout) -> logout.logoutSuccessUrl("/home?logout")
															  .permitAll());
		return http.build();
	}

	@Bean
	public AuthenticationFailureHandler authenticationFailureHandler(){
		return new SimpleUrlAuthenticationFailureHandler(){
			@Override
			public void onAuthenticationFailure(HttpServletRequest request,
												HttpServletResponse response,
												AuthenticationException exception)
					throws IOException, ServletException {
				request.getSession().setAttribute("error", exception.getMessage());
				response.sendRedirect("/login?error=true");
			}
		};
	}

//	@Bean
//	public AuthenticationSuccessHandler authenticationSuccessHandler(){
//		return new SimpleUrlAuthenticationSuccessHandler(){
//			public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//												UserDetails user) throws IOException{
//				if ()
//			}
//		};
//	}
}
