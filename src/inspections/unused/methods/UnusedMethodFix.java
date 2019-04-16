package inspections.unused.methods;

import com.intellij.codeInsight.FileModificationService;
import com.intellij.codeInspection.LocalQuickFixAndIntentionActionOnPsiElement;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.MessageType;
import com.intellij.openapi.ui.popup.Balloon;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.WindowManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.refactoring.safeDelete.SafeDeleteHandler;
import com.intellij.refactoring.safeDelete.SafeDeleteProcessor;
import com.intellij.ui.awt.RelativePoint;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO: 2019-01-28 Rename this class, as it's used from multiple places
public class UnusedMethodFix extends LocalQuickFixAndIntentionActionOnPsiElement {

    private String message = "Safe delete";

    public UnusedMethodFix(@Nullable PsiElement element) {
        super(element);
    }

    public UnusedMethodFix(PsiElement element, String text) {
        super(element);
        message = text;
    }

    @Override
    public @NotNull String getText() {
        return message;
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @Override
    public @NotNull String getFamilyName() {
        return "Safe delete";
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull PsiFile file, @Nullable("is null when called from inspection") Editor editor, @NotNull PsiElement startElement, @NotNull PsiElement endElement) {
        if (!FileModificationService.getInstance().prepareFileForWrite(file)) return;

        if (SafeDeleteProcessor.validElement(startElement)) {
            final PsiElement[] elements = {startElement};
            SafeDeleteHandler.invoke(project, elements, true);
        } else {
            StatusBar statusBar = WindowManager.getInstance()
                    .getStatusBar(project);
            JBPopupFactory.getInstance()
                    .createHtmlTextBalloonBuilder("Safe delete not available, removed just in this place", MessageType.INFO, null)
                    .setFadeoutTime(3500)
                    .createBalloon()
                    .show(RelativePoint.getCenterOf(statusBar.getComponent()),
                            Balloon.Position.atRight);
            startElement.delete();
        }
    }
}
