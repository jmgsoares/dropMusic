package pt.onept.sd1819.dropmusic.web.interceptor;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import pt.onept.sd1819.dropmusic.common.server.contract.type.User;
import pt.onept.sd1819.dropmusic.web.communication.CommunicationManager;

import java.util.Map;

public class AuthenticationInterceptor implements Interceptor {
	@Override
	public void destroy() {

	}

	@Override
	public void init() {

	}

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		if(session != null &&  session.containsKey("logged") && (boolean)session.get("logged")) {
			User user = (User)session.get("user");
			session.put("user", CommunicationManager.getServerInterface().user().read(user, user));
			return invocation.invoke();
		}
		else return Action.LOGIN;
	}
}

