package uz.app.pdptube.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.app.pdptube.filter.MyFilter;
import uz.app.pdptube.repository.UserRepository;

@Configuration
@RequiredArgsConstructor
public class MyConf {
    final UserRepository userRepository;
    final MyFilter myFilter;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .cors(c ->c.disable())
                .csrf(c ->c.disable())
                .authorizeHttpRequests(
                        auth->
                                auth
                                        .requestMatchers(new String[]{"/api/auth/**","swagger-ui/**","/v3/api-docs/**"})
                                        .permitAll()
                                        .requestMatchers(HttpMethod.GET,"/product/**")
                                        .permitAll()
                                        .requestMatchers(HttpMethod.DELETE,"/product/**")
                                        .hasRole("ADMIN")
                                        .anyRequest()
                                        .authenticated()
                )
                .addFilterBefore(myFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    @Bean
    public io.swagger.v3.oas.models.OpenAPI customOpenAPI() {
        return new io.swagger.v3.oas.models.OpenAPI()
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().addList("Bearer"))
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes("Bearer", new io.swagger.v3.oas.models.security.SecurityScheme()
                                .type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")));
    }


    @Bean
    UserDetailsService userDetailsService(){
        return (email)->{
            return userRepository.findByEmail(email).orElseThrow();
        };
    }
}
