package io.apidocx.handle.action;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiElement;
import io.apidocx.action.AbstracBaseAction;
import io.apidocx.parse.util.PsiClassUtils;
import org.jetbrains.annotations.NotNull;


import java.awt.datatransfer.StringSelection;

/**
 * @author Jfeng
 * @date 2024/12/26
 */
public class ConvertToJsonAction extends AbstracBaseAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        PsiClass psiClass = getPsiClass(psiElement);
        if (psiClass == null) {
            return;
        }
        String json = PsiClassUtils.create(psiClass).convertClassToJSON(myProject(e), true);
        CopyPasteManager.getInstance().setContents(new StringSelection(json));
    }

    protected PsiClass getPsiClass(PsiElement psiElement) {
        PsiClass psiClass = null;
        if (psiElement instanceof PsiClass) {
            psiClass = (PsiClass) psiElement;
        }
        // if (psiElement instanceof KtClassOrObject) {
        //     if (LightClassUtil.INSTANCE.canGenerateLightClass((KtClassOrObject) psiElement)) {
        //         psiClass = LightClassUtilsKt.toLightClass((KtClassOrObject) psiElement);
        //     }
        // }
        return psiClass;
    }

    public void update(AnActionEvent e) {
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);
        setActionPresentationVisible(e,  psiElement instanceof PsiClass);
    }
}
