package inspections.unused.unusedInstance;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.jetbrains.swift.psi.SwiftIdentifierPattern;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import com.jetbrains.swift.psi.impl.SwiftPatternInitializerGenImpl;
import inspections.unused.methods.UnusedMethodFix;
import org.jetbrains.annotations.NotNull;

public class UnusedInstanceVisitor extends SwiftVisitor {
    private ProblemsHolder holder;

    UnusedInstanceVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitVariableDeclaration(@NotNull SwiftVariableDeclaration variableDeclaration) {
        super.visitVariableDeclaration(variableDeclaration);

        SwiftPatternInitializerGenImpl initializerGen = (SwiftPatternInitializerGenImpl) variableDeclaration.getPatternInitializerList().get(0);
        SwiftIdentifierPattern swiftIdentifierPattern = initializerGen.getVariables().get(0);

        PsiElement nameIdentifier = variableDeclaration.getVariables().get(0).getNameIdentifier();
        if (nameIdentifier != null) {
            PsiReference first = ReferencesSearch.search(swiftIdentifierPattern).findFirst();
            if (first == null) {
                holder.registerProblem(nameIdentifier, "Unused variable", ProblemHighlightType.LIKE_UNUSED_SYMBOL, new UnusedMethodFix(variableDeclaration));
            }
        }
    }
}
