package com.baizhi.zwb.conf;

import com.baizhi.zwb.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

@Configuration
public class ShiroFilter {

    //将Shiro的过滤器工厂交给spring工厂管理
    @Bean
    public ShiroFilterFactoryBean getFilter(SecurityManager securityManager){
        //获取shiro的过滤器工厂
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();

        //将安全管理器交给shiro过滤器工厂
        shiroFilter.setSecurityManager(securityManager);

        //过滤器链
        HashMap<String, String> map = new HashMap<>();
        //配置过滤规则
        //资源路径,过滤规则
        /*
         * authc  认证过滤器    org.apache.shiro.web.filter.authc.FormAuthenticationFilter
         * 只有认证成功的资源才能被访问
         * anon   匿名过滤器   org.apache.shiro.web.filter.authc.AnonymousFilter
         * 不用认证都可以访问
         * */
        //map.put("/**","authc");
       // map.put("/login/login","anon");

        //设置过滤器链
        shiroFilter.setFilterChainDefinitionMap(map);

        //自定义登录页面
        shiroFilter.setLoginUrl("/login/login.jsp");

        return shiroFilter;
    }

    //将安全管理器交给spring工厂管理
    @Bean
    public SecurityManager getSecurityManager(MyRealm myRealm,CacheManager cacheManager){
        //创建安全管理器
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        //将自定义realm交给安全管理器
        securityManager.setRealm(myRealm);

        //设置缓存
        securityManager.setCacheManager(cacheManager);

        return securityManager;
    }

    //将自定义realm交给spring工厂管理
    @Bean
    public MyRealm getMyRealm(HashedCredentialsMatcher credentialsMatcher){
        //配置自定义realm
        MyRealm myRealm = new MyRealm();

        //将自定义凭证匹配器交给自定义Realm
        myRealm.setCredentialsMatcher(credentialsMatcher);

        return myRealm;
    }

    //将凭证匹配器交给spring工厂管理
    @Bean
    public HashedCredentialsMatcher getHashedCredentialsMatcher(){
        //创建凭证匹配器
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        credentialsMatcher.setHashAlgorithmName("MD5");  //加密算法
        credentialsMatcher.setHashIterations(1024); //散列次数
        return credentialsMatcher;
    }

    //将Shiro对象缓存交给spring工厂管理
    @Bean
    public CacheManager getCacheManager(){
        //创建缓存对象
        CacheManager cacheManager=new EhCacheManager();
        return cacheManager;
    }
}
