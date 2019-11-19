package net.potm.misc;



import net.potm.web.jsf.helper.HelperController;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Named("txt")
public class TextController {

	@Inject
	TextManager ls;
	
	@Inject
	HelperController helper;
	
	@Inject
	Logger log;
	
	public String getText(String key) {
		if(helper.getLocale()==null) {
			log.log(Level.WARNING,"No locale");
			return ls.getText(key, "en");
		}
		return ls.getText(key, helper.getLocale().getLanguage());
	}
}
