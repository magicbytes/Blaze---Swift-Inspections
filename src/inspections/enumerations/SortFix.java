package inspections.enumerations;

import com.intellij.codeInspection.LocalQuickFixAndIntentionActionOnPsiElement;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.jetbrains.swift.psi.SwiftEnumCaseClause;
import com.jetbrains.swift.psi.SwiftEnumDeclaration;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class SortFix extends LocalQuickFixAndIntentionActionOnPsiElement {

    private List<String> enumFields = new ArrayList<>();

    public SortFix(@Nullable PsiElement element) {
        super(element);
    }

    @Override
    public @NotNull String getText() {
        return "Sort alphabetically";
    }

    @Nls(capitalization = Nls.Capitalization.Sentence)
    @Override
    public @NotNull String getFamilyName() {
        return "Blaze - Swift Inspections";
    }

    @Override
    public void invoke(@NotNull Project project, @NotNull PsiFile file, @Nullable("is null when called from inspection") Editor editor, @NotNull PsiElement startElement, @NotNull PsiElement endElement) {
        SwiftEnumDeclaration psiClass = (SwiftEnumDeclaration) startElement;

        extractCurrentFields(psiClass);
        sortFieldsAlphabetically();
        saveResult(psiClass);
    }


    private void extractCurrentFields(SwiftEnumDeclaration psiClass) {
        enumFields.clear();

        List<SwiftEnumCaseClause> allFields = psiClass.getEnumCaseClauseList();
        for (SwiftEnumCaseClause field : allFields) {
            enumFields.add(field.getEnumCaseList().get(0).getName());
        }
    }

    private void sortFieldsAlphabetically() {
        enumFields.sort(String::compareTo);
    }

    private void saveResult(SwiftEnumDeclaration psiClass) {
        List<SwiftEnumCaseClause> allFields = psiClass.getEnumCaseClauseList();

        for (int i = 0; i < enumFields.size(); ++i) {
            allFields.get(i).getEnumCaseList().get(0).setName(enumFields.get(i));
        }
    }
}
