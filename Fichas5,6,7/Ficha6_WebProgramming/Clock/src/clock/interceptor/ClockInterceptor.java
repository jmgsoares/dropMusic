/**
 * Raul Barbosa 2014-11-07
 */
package clock.interceptor;

import java.util.Map;
import java.util.Calendar;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class ClockInterceptor implements Interceptor {
	private static final long serialVersionUID = 189237412378L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		Map<String, Object> session = invocation.getInvocationContext().getSession();
		
		// this method intercepts the execution of the action and we get access
		// to the session, to the action, and to the context of this invocation
		if((Calendar.getInstance().get(Calendar.MINUTE) & 1) == 0)
			return invocation.invoke();
		else
			return Action.INPUT; // the clock is broken every other minute

	}

	@Override
	public void init() { }
	
	@Override
	public void destroy() { }
}