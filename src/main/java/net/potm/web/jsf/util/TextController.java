/*
 *     (C) 2019 by Zoltan Bakcsa (zoltan@bakcsa.hu)
 *     This file is part of "putonthemap".
 *
 *     putonthemap is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     putonthemap is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with putonthemap.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.potm.web.jsf.util;



import net.potm.business.util.PropertiesService;
import net.potm.business.util.TextService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequestScoped
@Named("txt")
public class TextController {
	static Logger log=Logger.getLogger(PropertiesService.class.getName());
	@Inject
	TextService ls;
	
	@Inject
	HelperController helper;
	
	public String getText(String key) {
		if(helper.getLocale()==null) {
			log.log(Level.WARNING,"No locale");
			return ls.getText(key, "en");
		}
		return ls.getText(key, helper.getLocale().getLanguage());
	}
}
