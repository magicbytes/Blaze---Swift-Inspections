package inspections.unused.unusedClass;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.jetbrains.swift.psi.*;
import inspections.unused.methods.UnusedMethodFix;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class UnusedClassVisitor extends SwiftVisitor {

    private ProblemsHolder holder;

    UnusedClassVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitProtocolDeclaration(@NotNull SwiftProtocolDeclaration protocolDeclaration) {
        super.visitProtocolDeclaration(protocolDeclaration);

        PsiElement nameIdentifier = protocolDeclaration.getNameIdentifier();
        if (nameIdentifier != null) {
            PsiReference first = ReferencesSearch.search(protocolDeclaration).findFirst();
            if (first == null) {
                holder.registerProblem(nameIdentifier, "Unused protocol", ProblemHighlightType.LIKE_UNUSED_SYMBOL, new UnusedMethodFix(protocolDeclaration));
            }
        }
    }

    @Override
    public void visitClassDeclaration(@NotNull SwiftClassDeclaration classDeclaration) {
        super.visitClassDeclaration(classDeclaration);

        if (classDeclaration.getAttributes().hasAttribute("UIApplicationMain")) {
            return;
        }

        PsiElement nameIdentifier = classDeclaration.getNameIdentifier();
        if (nameIdentifier != null) {
            PsiReference first = ReferencesSearch.search(classDeclaration).findFirst();
            if (first == null) {
                boolean shouldRegister = true;

                SwiftTypeInheritanceClause inheritanceClause = classDeclaration.getTypeInheritanceClause();
                if (inheritanceClause != null) {
                    List<SwiftTypeElement> typeElementList = inheritanceClause.getTypeElementList();
                    if (!typeElementList.isEmpty()) {
                        String nameClass = ((SwiftReferenceTypeElement) typeElementList.get(0)).getName();
                        if (nameClass.startsWith("XCTest")) {
                            shouldRegister = false;
                        }
                    }
                }

                if (shouldRegister) {
                    holder.registerProblem(nameIdentifier, "Unused class", ProblemHighlightType.LIKE_UNUSED_SYMBOL, new UnusedMethodFix(classDeclaration));
                }
            }
        }
    }
}
