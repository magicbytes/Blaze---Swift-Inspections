package inspections.unused.methods;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SuperCallingMethodVisitor extends SwiftVisitor {

    private ProblemsHolder holder;

    SuperCallingMethodVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }


    @Override
    public void visitFunctionDeclaration(@NotNull SwiftFunctionDeclaration functionDeclaration) {
        super.visitFunctionDeclaration(functionDeclaration);

        boolean shouldRegister = false;

        SwiftCodeBlock codeBlock = functionDeclaration.getCodeBlock();
        if (codeBlock != null) {
            List<SwiftStatement> statementList = codeBlock.getStatements();
            if (statementList.size() == 1 && statementList.get(0) instanceof SwiftCallExpression) {
                shouldRegister = statementList.get(0).getText().startsWith("super.");
            }
        }

        if (shouldRegister) {
            PsiElement nameIdentifier = functionDeclaration.getNameIdentifier();
            if (nameIdentifier != null) {
                holder.registerProblem(nameIdentifier, "Method calling only supper", ProblemHighlightType.GENERIC_ERROR_OR_WARNING, new UnusedMethodFix(functionDeclaration));
            }
        }
    }
}
