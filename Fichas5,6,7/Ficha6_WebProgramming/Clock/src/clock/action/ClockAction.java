/**
 * Raul Barbosa 2014-11-07
 */
package clock.action;

import com.opensymphony.xwork2.ActionSupport;
import clock.model.ClockBean;

public class ClockAction extends ActionSupport {
	private ClockBean clockBean;

	public String execute() throws Exception {
		setClockBean(new ClockBean());
		return SUCCESS;
	}

	public ClockBean getClockBean() {
		return clockBean;
	}

	public void setClockBean(ClockBean clockBean) {
		this.clockBean = clockBean;
	}
}
