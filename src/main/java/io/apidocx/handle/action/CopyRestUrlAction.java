package io.apidocx.handle.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiMethod;
import com.intellij.psi.util.PsiTreeUtil;
import io.apidocx.action.AbstractAction;
import io.apidocx.base.util.ClipboardUtils;
import io.apidocx.base.util.NotificationUtils;
import io.apidocx.config.ApidocxConfig;
import io.apidocx.config.ApidocxSettings;
import io.apidocx.model.Api;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static io.apidocx.base.util.NotificationUtils.notifyWarning;

/**
 * 复制URL路径
 */
public class CopyRestUrlAction extends AbstractAction {

    public static final String ACTION_TEXT = "Copy URL";

    public CopyRestUrlAction() {
        super(false);
    }

    /**
     * 检查前操作
     */
    public boolean before(AnActionEvent event, ApidocxConfig config) {
        config.setStrict(false);
        return true;
    }

    @Override
    public void handle(AnActionEvent event, ApidocxConfig config, List<Api> apis) {
        if (apis.size() != 1) {
            notifyWarning("Copy URL", "only support single api, please choose method in editor");
            return;
        }
        ApidocxSettings settings = ApidocxSettings.getInstance();
        String host = StringUtils.isNotEmpty(settings.getCurlHost()) ? settings.getCurlHost() : "";
        String url = apis.get(0).getPath();
        ClipboardUtils.setClipboard(url);
        // NotificationUtils.notifyInfo(ACTION_TEXT, url);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setText(ACTION_TEXT);
        e.getPresentation().setEnabledAndVisible(isSelectedMethod(e));
    }

    /**
     * 是否选中了方法
     */
    private boolean isSelectedMethod(@NotNull AnActionEvent e) {
        Editor editor = e.getDataContext().getData(CommonDataKeys.EDITOR);
        PsiFile editorFile = e.getDataContext().getData(CommonDataKeys.PSI_FILE);
        if (editor != null && editorFile != null) {

            PsiElement referenceAt = editorFile.findElementAt(editor.getCaretModel().getOffset());
            PsiClass selectClass = PsiTreeUtil.getContextOfType(referenceAt, PsiClass.class);
            if (selectClass != null) {
                PsiMethod method = PsiTreeUtil.getContextOfType(referenceAt, PsiMethod.class);
                return method != null;
            }
        }
        return true;
    }
}
