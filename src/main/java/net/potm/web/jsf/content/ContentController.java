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

package net.potm.web.jsf.content;

import net.potm.persistence.model.ContentBase;
import net.potm.persistence.service.ContentService;
import net.potm.web.jsf.user_session.UserSessionController;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.logging.Logger;

@Model
public class ContentController {

    @Inject
    UserSessionController usc;

    @Inject
    ContentService contentService;

    @Inject
    Logger log;

    ContentBase selectedContent;
    Boolean deleteButtonRendered;
    Boolean addButtonRendered;

    @PostConstruct
    public void init(){
        log.info("Init.");
        updateUI();
    }

    private void updateUI(){
        deleteButtonRendered = false;
        addButtonRendered = false;

        if (usc.getAuthenticated()) {
            addButtonRendered = true;
            if (selectedContent != null) {
                selectedContent = contentService.fetchOwner(selectedContent);
                if (selectedContent.getOwner().getId() == usc.getUser().getId()) {
                    deleteButtonRendered = true;
                }
            }
        }
    }

    public void selectionChanged() {
        updateUI();
    }

    public ContentBase getSelectedContent() {
        return selectedContent;
    }

    public void setSelectedContent(ContentBase selectedContent) {
        this.selectedContent = selectedContent;
    }

    public Boolean getDeleteButtonRendered() {
        return deleteButtonRendered;
    }

    public void setDeleteButtonRendered(Boolean deleteButtonRendered) {
        this.deleteButtonRendered = deleteButtonRendered;
    }

    public Boolean getAddButtonRendered() {
        return addButtonRendered;
    }

    public void setAddButtonRendered(Boolean addButtonRendered) {
        this.addButtonRendered = addButtonRendered;
    }
}
