package ru.skypro.homework.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login",
            "/register"
    };

//    @Bean
//    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//        UserDetails user =
//                User.builder()
//                        .username("user@gmail.com")
//                        .password("password")
//                        .passwordEncoder(passwordEncoder::encode)
//                        .roles(Role.USER.name())
//                        .build();
//        return new InMemoryUserDetailsManager(user);
//    }

    @Bean
    public JdbcUserDetailsManager userDetailsManager(DataSource dataSource, JdbcTemplate jdbcTemplate) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);
        manager.setJdbcTemplate(jdbcTemplate);
        manager.setCreateUserSql("insert into security (username, password, enabled) values (?,?,?)");
        manager.setDeleteUserSql("delete from security where username = ?");
        manager.setUpdateUserSql("update security set password = ?, enabled = ? where username = ?");
        manager.setUserExistsSql("select username from security where username = ?");
        manager.setChangePasswordSql("update security set password = ? where username = ?");
        manager.setUsersByUsernameQuery("select username, password, enabled from security where username = ?");
        return manager;
    }

//          private String createUserSql = "insert into users (username, password, enabled) values (?,?,?)";
//          private String deleteUserSql = "delete from users where username = ?";
//          private String updateUserSql = "update users set password = ?, enabled = ? where username = ?";
//    private String createAuthoritySql = "insert into authorities (username, authority) values (?,?)";
//    private String deleteUserAuthoritiesSql = "delete from authorities where username = ?";
//          private String userExistsSql = "select username from users where username = ?";
//          private String changePasswordSql = "update users set password = ? where username = ?";
//    private String findAllGroupsSql = "select group_name from groups";
//    private String findUsersInGroupSql = "select username from group_members gm, groups g where gm.group_id = g.id and g.group_name = ?";
//    private String insertGroupSql = "insert into groups (group_name) values (?)";
//    private String findGroupIdSql = "select id from groups where group_name = ?";
//    private String insertGroupAuthoritySql = "insert into group_authorities (group_id, authority) values (?,?)";
//    private String deleteGroupSql = "delete from groups where id = ?";
//    private String deleteGroupAuthoritiesSql = "delete from group_authorities where group_id = ?";
//    private String deleteGroupMembersSql = "delete from group_members where group_id = ?";
//    private String renameGroupSql = "update groups set group_name = ? where group_name = ?";
//    private String insertGroupMemberSql = "insert into group_members (group_id, username) values (?,?)";
//    private String deleteGroupMemberSql = "delete from group_members where group_id = ? and username = ?";
//    private String groupAuthoritiesSql = "select g.id, g.group_name, ga.authority from groups g, group_authorities ga where g.group_name = ? and g.id = ga.group_id ";
//    private String deleteGroupAuthoritySql = "delete from group_authorities where group_id = ? and authority = ?";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests(
                        authorization ->
                                authorization
                                        .mvcMatchers(AUTH_WHITELIST).permitAll()
                                        .mvcMatchers("/ads/**", "/users/**").authenticated())
                //.formLogin().loginPage("/login")
                //.and()
                .logout().permitAll()
                .and()
                .cors()
                .and()
                .httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
