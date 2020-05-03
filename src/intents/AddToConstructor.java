package intents;

import com.intellij.codeInsight.intention.PsiElementBaseIntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AddToConstructor extends PsiElementBaseIntentionAction {
    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return getText();
    }

    @NotNull
    @Override
    public String getText() {
        return "Add to constructor";
    }

    @Override
    public void invoke(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) throws IncorrectOperationException {
        SwiftVariableDeclaration variable = getDeclaration(psiElement);
        if (variable == null) {
            return;
        }

        SwiftInitializerDeclaration constructor = null;
        SwiftClassDeclaration containingClass = (SwiftClassDeclaration) variable.getContainingClass();
        List<SwiftMemberDeclaration> declarations = containingClass.getDeclarations();
        for (SwiftMemberDeclaration statement : declarations) {
            if (statement instanceof SwiftInitializerDeclaration) {
                constructor = (SwiftInitializerDeclaration) statement;
                break;
            }
        }

        if (constructor == null) {
            return;
        }


        SwiftPsiElementFactory instance = SwiftPsiElementFactory.getInstance(project);
        String variableName = "_ " + variable.getVariables().get(0).getNameIdentifier().getText();
        String variableType = variable.getVariables().get(0).getSwiftType().getPresentableText();
        SwiftParameter parameter = instance.createParameter(variableName, variableType);

        List<SwiftParameter> parameterList = constructor.getParameterClause().getParameterList();
        StringBuilder buffer = new StringBuilder();
        boolean hasParameterAlready = false;
        for (SwiftParameter existingParameter : parameterList) {
            if (hasParameterAlready) {
                buffer.append(", ");
            }
            buffer.append(existingParameter.getText());
            hasParameterAlready = true;
        }

        if (hasParameterAlready) {
            buffer.append(", ");
        }
        buffer.append(parameter.getText());

        String result = "init(" + buffer.toString() + ") {}";
        SwiftInitializerDeclaration statement = (SwiftInitializerDeclaration) instance.createStatement(result);
        constructor.getParameterClause().replace(statement.getParameterClause());

        SwiftStatement statement1 = instance.createStatement("data = hello");
        constructor.getCodeBlock().getStatements().add(statement1);

        int z = 10 + 1;
    }

    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, @NotNull PsiElement psiElement) {
        boolean result = false;

        SwiftVariableDeclaration variable = getDeclaration(psiElement);

        if (variable != null) {
            List<SwiftPatternInitializer> initializers = variable.getPatternInitializerList();
            if (!initializers.isEmpty()) {
                SwiftInitializer initializer = initializers.get(0).getInitializer();
                result = initializer == null;
            }
        }

        return result;
    }

    private SwiftVariableDeclaration getDeclaration(PsiElement psiElement) {
        SwiftVariableDeclaration declaration = null;

        PsiElement variableElement = psiElement;
        do {
            variableElement = variableElement.getContext();
            if (variableElement instanceof SwiftVariableDeclaration) {
                declaration = (SwiftVariableDeclaration) variableElement;
                break;
            }
        } while (variableElement != null);

        System.out.println("Checking for variable");

        return declaration;
    }
}
