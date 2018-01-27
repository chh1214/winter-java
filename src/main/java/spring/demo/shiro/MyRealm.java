package spring.demo.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm {

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		String username = token.getPrincipal().toString();
		String password = new String((char[]) token.getCredentials());
		// TODO 查询用户名密码判断是否正确
		if (!"chh".equals(username)) {
			throw new UnknownAccountException(); // 如果用户名错误
		}
		if (!"123".equals(password)) {
			throw new IncorrectCredentialsException(); // 如果密码错误
		}
		// 将查询到的用户账号和密码存放到 authenticationInfo用于后面的权限判断。第三个参数传入realName。
		AuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(username, password, "chh");
		return authenticationInfo;
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		String username = principalCollection.getPrimaryPrincipal().toString();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		// TODO 查询用户的身份和角色并添加到SimpleAuthorizationInfo对象与shiro.xml的chain配置相对应
		/*
		 * Set<String> roleName = userService.findRoles(username); Set<String>
		 * permissions = userService.findPermissions(username); info.setRoles(roleName);
		 * info.setStringPermissions(permissions);
		 */
		return info;
	}
}
