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
 *     along with Foobar.  If not, see <https://www.gnu.org/licenses/>.
 */

package net.potm.web.jsf.user_session;

import net.potm.business.api.UserManagementService;
import net.potm.persistence.model.Person;
import net.potm.persistence.service.ContentService;
import net.potm.web.jsf.content.ContentController;
import net.potm.web.jsf.util.TextController;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.logging.Logger;

@SessionScoped
@Named("userSessionController")
public class UserSessionController implements Serializable {
    private Logger log = Logger.getLogger(this.getClass().getName());

    private Boolean authenticated = false;
    private Person user;

    private String username;
    private String password;

    @Inject
    TextController textController;

    @Inject
    UserManagementService userManagementService;

    @Inject
    ContentService contentService;

    @Inject
    ContentController contentController;

    @PostConstruct
    public void init() {
        log.info("UserSessionController has been initialized. "+this.hashCode());
    }

    public Boolean getAuthenticated() {
        return authenticated;
    }

    public void setAuthenticated(Boolean authenticated) {
        this.authenticated = authenticated;
    }

    public Person getUser() {
        return user;
    }

    public String login(){
        user=userManagementService.authenticate(username, password);
        var context = FacesContext.getCurrentInstance();
        if(user==null){
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, textController.getText("login_failed"),
                        textController.getText("wrong_username_or_pwd")));
        }
        else{
            context.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,textController.getText("login_ok"),
                            textController.getText("welcome")+" "+user.getNickName()));
            authenticated=true;
            contentController.updateUI();
        }

        return "";
    }

    public String logout() {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return "/index.xhtml?faces-redirect=true";
    }

    public void setUser(Person user) {
        this.user = user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
