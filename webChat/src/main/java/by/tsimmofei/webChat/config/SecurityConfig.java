package by.tsimmofei.webChat.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import by.tsimmofei.webChat.security.AuthProviderImpl;
import by.tsimmofei.webChat.services.PersonDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfiguration {
	
	private final AuthProviderImpl authProvider;
	private final PersonDetailsService personDetailsService;
	
	@Autowired
	public SecurityConfig(AuthProviderImpl authProvider, PersonDetailsService personDetailsService) {
		this.authProvider = authProvider;
		
		this.personDetailsService = personDetailsService;
	}
	
	//конфигурируем сам Spring Security и авторизацию
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/auth/login", "/auth/registration", "/error", "/js/**", "/css/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/chat", true)
                        .failureUrl("/auth/login?error"))
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/auth/login"));
               //.authenticationProvider(authProvider);
		return http.build();
	}
	
	public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/styles/css/**")
                .addResourceLocations("classpath:styles/css/");
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/");
    }
	//
	@Bean
	public DaoAuthenticationProvider authProvider() {
	    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
	    authProvider.setUserDetailsService(personDetailsService);
	    authProvider.setPasswordEncoder(encoder());
	    return authProvider;
	}
	//
    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
            .authenticationProvider(authProvider())
            .build();
    }
	
	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}
}
