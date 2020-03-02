package com.baizhi.zwb.realm;

import com.baizhi.zwb.dao.AdminMapper;
import com.baizhi.zwb.entity.Admin;
import com.baizhi.zwb.entity.AdminExample;
import com.baizhi.zwb.entity.Permission;
import com.baizhi.zwb.entity.Role;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class MyRealm extends AuthorizingRealm {

    @Resource
    AdminMapper adminMapper;

    @Override
    //做授权
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("用户的授权");

        //获取主身份信息
        String username = (String)principalCollection.getPrimaryPrincipal();

        //根据用户查询角色和权限：5表查询
        Admin admin = adminMapper.queryRolesAndPermissionByUsername(username);

        //角色集合
        ArrayList<String> roleNames = new ArrayList<>();

        //权限集合
        ArrayList<String> permissionNames = new ArrayList<>();

        //根据用户信息获取角色集合
        List<Role> roles = admin.getRoleList();

        //遍历角色集合
        for (Role role : roles) {
            //获取角色名
            String roleName = role.getRoleName();

            System.out.println("角色名为："+roleName);
            //将角色名放入角色集合
            roleNames.add(roleName);

            //获取权限对象集合
            List<Permission> permissions = role.getPermissionList();

            //遍历权限集合
            for (Permission permission : permissions) {
                //获取权限名
                String permissionName = permission.getPermissionName();
                System.out.println("权限名为："+permissionName);
                //将权限名放到权限集合中
                permissionNames.add(permissionName);
            }
        }

        //创建授权对象
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        //给用户设置角色
       info.addRoles(roleNames);

        //给用户设置权限
        info.addStringPermissions(permissionNames);

        return info;
    }

    @Override
    //做认证
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("用户的认证");
        String username = (String)token.getPrincipal();

        //查询数据库：根据用户名进行查询
        AdminExample example = new AdminExample();
        example.createCriteria().andUsernameEqualTo(username);
        Admin admin = adminMapper.selectOneByExample(example);
        System.out.println("查询出来的admin为："+admin);

        AuthenticationInfo info=new SimpleAuthenticationInfo(admin.getUsername(),admin.getPassword(),ByteSource.Util.bytes(admin.getSalt()),this.getName());
        return info;
    }
}
