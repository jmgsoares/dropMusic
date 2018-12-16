package pt.onept.sd1819.dropmusic.web.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;

import java.util.Map;

public class EditorInterceptor implements Interceptor {
	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		if(((User)session.get("user")).getEditor()) return invocation.invoke();
		else return Action.LOGIN;
	}
}
