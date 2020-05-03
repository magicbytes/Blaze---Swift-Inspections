package inspections.instanceVariable;

import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.*;
import com.jetbrains.swift.psi.impl.SwiftPsiElementFactoryImpl;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class InstanceVariableFix implements LocalQuickFix {
//

    @Nls
    @NotNull
    @Override
    public String getName() {
        return "Add to constructor";
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "GeneralInspections";
    }

    @Override
    public void applyFix(@NotNull com.intellij.openapi.project.Project project, @NotNull com.intellij.codeInspection.ProblemDescriptor descriptor) {
        SwiftVariableDeclaration psiClass = (SwiftVariableDeclaration) descriptor.getPsiElement();

        SwiftClassDeclaration classParent = (SwiftClassDeclaration) psiClass.getContainingClass();

        SwiftPsiElementFactory instance = SwiftPsiElementFactoryImpl.getInstance(project);
        SwiftInitializerDeclaration initializerDeclaration = (SwiftInitializerDeclaration) instance.createStatement("init() {}");

        String[] split = psiClass.getPatternInitializerList().get(0).getText().split(":");

        List<SwiftParameter> parameterList = initializerDeclaration.getParameterClause().getParameterList();
        parameterList.add(instance.createParameter("_ " + split[0], split[1]));
//        instance.createParameterClause()

//        classParent.getStatementList().add(initializerDeclaration);

        int z = 10 + 1;

//        extractCurrentFields(psiClass);
//        sortFieldsAlphabetically();
//        saveResult(project, psiClass);
    }
//
//    private void extractCurrentFields(SwiftEnumDeclaration psiClass) {
//        enumFields.clear();
//
//        List<SwiftEnumCaseClause> allFields = psiClass.getEnumCaseClauseList();
//        for (SwiftEnumCaseClause field : allFields) {
//            enumFields.add(field.getText());
//        }
//    }
//
//    private void sortFieldsAlphabetically() {
//        enumFields.sort(String::compareTo);
//    }
//
//    private void saveResult(com.intellij.openapi.project.Project project, SwiftEnumDeclaration psiClass) {
//        List<SwiftEnumCaseClause> allFields = psiClass.getEnumCaseClauseList();
//
//        SwiftPsiElementFactory instance = SwiftPsiElementFactoryImpl.getInstance(project);
//        for (int i = 0; i < enumFields.size(); ++i) {
//            SwiftDeclarationSpecifier newField = instance.createDeclarationSpecifier(enumFields.get(i));
//            PsiElement replace = allFields.get(i).replace(newField);
//        }
//        int z = 10 + 1;
//    }
}
