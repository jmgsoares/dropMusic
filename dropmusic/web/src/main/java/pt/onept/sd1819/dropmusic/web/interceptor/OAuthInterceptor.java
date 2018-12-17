package pt.onept.sd1819.dropmusic.web.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.util.Map;

/**
 * Interceptor to verify if an user has a dropBox account linked
 * @see com.opensymphony.xwork2.interceptor.Interceptor
 */
public class OAuthInterceptor implements Interceptor {

	@Override
	public void destroy() {	}

	@Override
	public void init() { }

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		if(((User)session.get("user")).getDropBoxToken()!=null) return invocation.invoke();
		else return Action.ERROR;
	}
}
