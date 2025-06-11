package com.example.springbootldapsecurity.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.ldap.authentication.BindAuthenticator;
import org.springframework.security.ldap.authentication.LdapAuthenticationProvider;
import org.springframework.security.ldap.search.FilterBasedLdapUserSearch;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

@Configuration
public class LdapConfig {

    @Value("${spring.ldap.urls}")
    private String ldapUrl;

    @Value("${spring.ldap.base}")
    private String ldapBase;

    @Value("${spring.ldap.userDnPattern}")
    private String userDnPattern;

    @Value("${spring.ldap.groupSearchBase}")
    private String groupSearchBase;

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public LdapAuthenticationProvider ldapAuthenticationProvider(
            AuthenticationManagerBuilder authManagerBuilder) throws Exception {
        
        FilterBasedLdapUserSearch userSearch = new FilterBasedLdapUserSearch(
            "", 
            userDnPattern, 
            null
        );

        BindAuthenticator authenticator = new BindAuthenticator(
            new org.springframework.security.ldap.DefaultSpringSecurityContextSource(ldapUrl)
        );
        authenticator.setUserSearch(userSearch);

        LdapAuthoritiesPopulator authoritiesPopulator = new DefaultLdapAuthoritiesPopulator(
            new org.springframework.security.ldap.DefaultSpringSecurityContextSource(ldapUrl),
            groupSearchBase
        );

        LdapAuthenticationProvider provider = new LdapAuthenticationProvider(authenticator, authoritiesPopulator);
        
        authManagerBuilder.authenticationProvider(provider);
        
        return provider;
    }
} 