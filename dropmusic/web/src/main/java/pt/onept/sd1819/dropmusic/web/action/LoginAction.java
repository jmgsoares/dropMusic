package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.interceptor.SessionAware;
import pt.onept.sd1819.dropmusic.web.bean.DropBoxBean;

import java.util.Map;

public class LoginAction extends ActionSupport implements SessionAware {
	private static final long serialVersionUID = 4L;
	private Map<String, Object> session;
	private String username = null, password = null;


	public String loginUserPass() throws Exception{
		return SUCCESS;
	}

	public String loginDropBox() throws Exception{
		this.getDropBoxBean();
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public LoginAction setUsername(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public LoginAction setPassword(String password) {
		this.password = password;
		return this;
	}

	public DropBoxBean getDropBoxBean() {
		if (!session.containsKey("dropBoxBean"))
			this.setDropBoxBean(new DropBoxBean());
		return (DropBoxBean) session.get("dropBoxBean");
	}

	public void setDropBoxBean(DropBoxBean dropBoxBean) {
		this.session.put("dropBoxBean", dropBoxBean);
	}

	@Override
	public void setSession(Map<String, Object> session) { this.session = session; }
}
