package com.employee.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class EmployeeSecurity {

	@Bean
	static PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SecurityFilterChain securityFilterchain(HttpSecurity http) throws Exception {
		
		http.csrf((csrf)->csrf.disable())
			.authorizeHttpRequests(authorize->{
				authorize.requestMatchers(HttpMethod.POST,"/saveEmployee/**")
				.hasRole("ADMIN");
				
				authorize.requestMatchers(HttpMethod.PUT,"/updateEmployee/**")
		        .hasAnyRole("ADMIN","MANAGER");

		        authorize.requestMatchers(HttpMethod.DELETE,"/deleteEmployee/**")
		        .hasRole("ADMIN");

		        authorize.requestMatchers(HttpMethod.GET,"/getEmployees")
		        .hasAnyRole("ADMIN","MANAGER","USER");

		        authorize.requestMatchers(HttpMethod.GET,"/getEmployee/**")
		        .hasAnyRole("ADMIN","MANAGER","USER");
		        
		        authorize.anyRequest().authenticated();
			}).httpBasic(Customizer.withDefaults());
		
		return http.build();
	}
	
	@Bean
	UserDetailsService userDetailManager() {
		UserDetails admin = User.builder()
		        .username("admin")
		        .password(passwordEncoder().encode("admin123"))
		        .roles("ADMIN")
		        .build();

		UserDetails manager = User.builder()
		        .username("manager")
		        .password(passwordEncoder().encode("manager123"))
		        .roles("MANAGER")
		        .build();

		UserDetails employee = User.builder()
		        .username("employee")
		        .password(passwordEncoder().encode("employee123"))
		        .roles("USER")
		        .build(); 
		
		return new InMemoryUserDetailsManager(admin,manager,employee);
	}
}
