package net.potm.web.jsf.activation;

import net.potm.business.api.UserManagementService;
import net.potm.misc.TextController;
import net.potm.web.jsf.helper.HTTPUtil;

import javax.enterprise.inject.Model;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.logging.Logger;

@Model
public class ActivationController {
    String activationEmail;
    String activationCode;

    String activationText;

    @Inject
    UserManagementService userManagementService;

    @Inject
    TextController textCtrl;

    @Inject
    Logger log;

    private void redirectToIndex() {
        HttpServletRequest req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();
        String url = req.getContextPath();
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect(url + "/index.jsf?faces-redirect=true");
        } catch (IOException e) {
            log.severe("Redirect failed. " + e.getMessage());

        }
    }

    public void preRender() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
                .getRequest();

        if (activationEmail == null || activationEmail.isEmpty()) {
            log.warning("Invalid activation request - Missing user parameter.");
            redirectToIndex();
            return;
        }

        if (activationCode == null || activationCode.isEmpty()) {
            log.warning(String.format(
                    "Invalid activation request - Missing activation code parameter.",
                    HTTPUtil.getClientIP(request)));
            redirectToIndex();
            return;
        }

        userManagementService.activateUser(activationEmail, activationCode);
    }

    public String getActivationEmail() {
        return activationEmail;
    }

    public void setActivationEmail(String activationEmail) {
        log.info("Set act email: " + activationEmail);
        this.activationEmail = activationEmail;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getActivationText() {
        return activationText;
    }

    public void setActivationText(String activationText) {
        this.activationText = activationText;
    }

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }

}
