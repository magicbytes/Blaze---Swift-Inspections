package inspections.enumerations;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.jetbrains.swift.psi.SwiftEnumDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.NotNull;

public class EnumVisitor extends SwiftVisitor {

    private ProblemsHolder holder;

    public EnumVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitEnumDeclaration(@NotNull SwiftEnumDeclaration swiftEnumDeclaration) {
        super.visitEnumDeclaration(swiftEnumDeclaration);

        EnumSortedStatusChecker checker = new EnumSortedStatusChecker(swiftEnumDeclaration);
        if (!checker.isSorted()) {
            PsiElement nameIdentifier = swiftEnumDeclaration.getNameIdentifier();
            if (nameIdentifier != null) {
                holder.registerProblem(nameIdentifier, "Sort alphabetically", ProblemHighlightType.GENERIC_ERROR_OR_WARNING, new SortFix(swiftEnumDeclaration));
            }
        }
    }
}
