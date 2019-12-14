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

package net.potm.web.jsf.user_profile;

import net.potm.business.api.UserManagementService;
import net.potm.business.util.EmailService;
import net.potm.persistence.model.Person;
import net.potm.web.jsf.user_session.UserSessionController;
import net.potm.web.jsf.util.HTTPUtil;
import net.potm.web.jsf.util.TextController;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.logging.Logger;

@Model
public class UserProfileController {
    static Logger log=Logger.getLogger(UserProfileController.class.getName());

    private static final int PASSWD_MIN_LEN = 6;
    private static final int NICK_MIN_LEN = 4;
    @Inject
    TextController textCtrl;

    @Inject
    UserSessionController usc;

    @Inject
    UserManagementService userManagementService;

    @Inject
    EmailService emailService;



    private String nickName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private String password2;
    private boolean showFeedbackMsg;

    @PostConstruct
    public void init(){
        if(usc.getAuthenticated()){
            nickName=usc.getUser().getNickName();
            email=usc.getUser().getEmail();
            firstName=usc.getUser().getFirstName();
            lastName=usc.getUser().getLastName();
        }
    }

    // Validators
    public void isNameValid(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String val = value.toString();

        if (val.length() < 2) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    textCtrl.getText("name_required"), textCtrl.getText("name_required")));
        }
    }

    public void isPasswordValid(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String val = value.toString();

        if (val.length() < PASSWD_MIN_LEN) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    String.format(textCtrl.getText("pwd_short"), PASSWD_MIN_LEN),
                    String.format(textCtrl.getText("pwd_short"), PASSWD_MIN_LEN)));
        }
    }

    public void isPasswordCheckValid(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String val = value.toString();

        if (!val.equals(password)) {
            log.info("Password checkh failed: " + password);
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    textCtrl.getText("pwd_check_fail"), textCtrl.getText("pwd_check_fail")));
        }
    }

    public void isNickValid(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String val = value.toString();

        if (val.length() < NICK_MIN_LEN) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    String.format(textCtrl.getText("minimum_x_chars"), NICK_MIN_LEN), String.format(textCtrl.getText("minimum_x_chars"), NICK_MIN_LEN)));
        }

        if (!val.matches("[A-Za-z0-9]+")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    textCtrl.getText("invalid_characters"), textCtrl.getText("invalid_characters")));
        }

        if (!isNickUnique(val)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    textCtrl.getText("nick_is_taken"), textCtrl.getText("nick_is_taken")));
        }
    }

    private boolean isNickUnique(String nick) {
        if(usc.getAuthenticated()&&usc.getUser().getNickName().equals(nick)) return true;
        return !userManagementService.isNickRegistered(nick);
    }

    private boolean isEmailUnique(String email) {
        if(usc.getAuthenticated()&&usc.getUser().getEmail().equals(email)) return true;
        return !userManagementService.isEmailRegistered(email);
    }

    public void isEmailValid(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String val = value.toString();

        if (!val.contains("@") || !val.contains(".")) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    textCtrl.getText("email_invalid"), textCtrl.getText("email_invalid")));
        }

        if (!isEmailUnique(val)) {
            throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    textCtrl.getText("email_is_taken"), textCtrl.getText("email_is_taken")));
        }
    }


    public String saveUser() {
        FacesContext context = FacesContext.getCurrentInstance();

        if (usc.getAuthenticated()) { //Update

            var person=usc.getUser();
            person.setFirstName(firstName);
            person.setLastName(lastName);
            person.setEmail(email);
            person.setNickName(nickName);
            person=userManagementService.updateUser(person);
            showFeedbackMsg =true;
            usc.setUser(person);
        } else {   //New user
            var person = userManagementService.signUp(email, nickName, firstName, lastName, password);
            sendActivationEmail(person);
            showFeedbackMsg =true;
            context.addMessage(null, new FacesMessage(textCtrl.getText("successful_registration")));
        }
        return "";
    }

    private void sendActivationEmail(Person user) {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        emailService.send(user.getEmail(), textCtrl.getText("account_activation_email_subject"), String.format(
                textCtrl.getText("account_activation_email"), user.getNickName(),
                HTTPUtil.getBaseUrl(req) + "/activation.jsf?email=" + user.getEmail() + "&code=" + user.getAuthCode()));
    }


    // Getters/Setters

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }

    public boolean getShowFeedbackMsg() {
        return showFeedbackMsg;
    }

    public void setShowFeedbackMsg(boolean showFeedbackMsg) {
        this.showFeedbackMsg = showFeedbackMsg;
    }
}
