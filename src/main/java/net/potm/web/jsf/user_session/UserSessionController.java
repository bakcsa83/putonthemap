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

import net.potm.persistence.model.Person;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
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


    @PostConstruct
    public void init(){
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

    public void setUser(Person user) {
        this.user = user;
    }
}
