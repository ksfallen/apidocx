package io.apidocx.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;


public abstract class AbstracBaseAction extends AnAction {

    protected Module myModule(AnActionEvent e) {
        return e.getData(DataKeys.MODULE);
    }

    protected Project myProject(AnActionEvent e) {
        return getEventProject(e);
    }

    protected void setActionPresentationVisible(AnActionEvent e, boolean visible) {
        e.getPresentation().setVisible(visible);
    }
}
