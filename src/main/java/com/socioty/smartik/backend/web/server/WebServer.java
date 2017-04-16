package com.socioty.smartik.backend.web.server;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.filter.session.NoSessionCreationFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableAutoConfiguration
@EnableMongoRepositories(basePackages = { "com.socioty.smartik.backend.repositories" })
@ComponentScan(basePackages = { "com.socioty.smartik.backend.web.resource" })
public class WebServer implements CommandLineRunner {

	public static void main(final String[] args) throws Exception {
		SpringApplication.run(WebServer.class, args);
	}
	
	@Override
	public void run(String... arg0) throws Exception {
	}
	
	@Bean(name = "shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(final SecurityManager manager) {
	    final ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
	    final Map<String, String> filterChainDefinitionMapping = new HashMap<String, String>();
	    filterChainDefinitionMapping.put("/rest/**", "noSessionCreation,authc");
	    shiroFilter.setFilterChainDefinitionMap(filterChainDefinitionMapping);
	    shiroFilter.setSecurityManager(manager);
	    final Map<String, Filter> filters = new HashMap<String, Filter>();
	    filters.put("authc", new BasicHttpAuthenticationFilter());
	    filters.put("noSessionCreation", new NoSessionCreationFilter());
	    shiroFilter.setFilters(filters);
	    return shiroFilter;
	}
	
	@Bean(name="securityManager")
	public DefaultWebSecurityManager securityManager(Realm realm) {
	    final DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(realm) ;
	    return securityManager;
	}

	@Bean
    public Realm realm() {
        final Realm realm = new AuthenticatingRealm() {
			@Override
			protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
				return new SimpleAuthenticationInfo(token.getPrincipal(), token.getCredentials(), "REALM");
			}
		};
	    return realm;
    }

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
	    return new LifecycleBeanPostProcessor();
	}

}