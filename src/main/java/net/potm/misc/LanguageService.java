package net.potm.misc;

import net.potm.business.model.Text;
import net.potm.business.service.TextService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;


@ApplicationScoped
public class LanguageService {

	private static final String defaultLanguage="en";
	private Map<String,String>textDict=new HashMap<>(200);
	
	@Inject
	Logger log;
	
	@Inject
	TextService ts;
	
	private void reloadMap() {
		List<Text>textList=ts.getText();
		textDict.clear();
		textList.forEach(e->textDict.put(e.getId().getKey()+"_"+e.getId().getLanguage(), e.getText()));
	}
	
	@PostConstruct
	public void init() {
		reloadMap();
		log.info("Language service init. Text map size: "+textDict.size());
	}


	public String getText(String key,String language) {
		String text=textDict.get(key+"_"+language);
		if(text==null) {
			log.warning(String.format("Missing text key '%s' for language '%s'", key,language));
			reloadMap();
			text=textDict.get(key+"_"+language);
			if(text==null) {
				text=textDict.get(key+"_"+defaultLanguage);
				if(text==null) {
					text="???/???";
				}
			}
		}
		return text;
	}
}
