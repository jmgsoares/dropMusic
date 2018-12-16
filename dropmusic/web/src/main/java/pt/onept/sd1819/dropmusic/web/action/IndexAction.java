package pt.onept.sd1819.dropmusic.web.action;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionSupport;
import pt.onept.sd1819.dropmusic.web.rest.dropbox.DropBoxRestService;

public class IndexAction extends ActionSupport {

	@Override
	public String execute() throws Exception {
		return Action.SUCCESS;
	}

	public String getoAuthUrl() {
		return DropBoxRestService.getAuthorizationUrl();
	}
}
