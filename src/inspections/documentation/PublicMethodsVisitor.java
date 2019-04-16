package inspections.documentation;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiComment;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.PsiWhiteSpace;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.jetbrains.swift.psi.SwiftClassDeclaration;
import com.jetbrains.swift.psi.SwiftDeclarationSpecifier;
import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import com.jetbrains.swift.search.supers.SwiftMethodAncestorsSearch;
import com.jetbrains.swift.symbols.SwiftCallableSymbol;
import com.jetbrains.swift.symbols.impl.SwiftFunctionSymbolImpl;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class PublicMethodsVisitor extends SwiftVisitor {

    private ProblemsHolder problemsHolder;

    PublicMethodsVisitor(ProblemsHolder problemsHolder) {
        this.problemsHolder = problemsHolder;
    }

    @Override
    public void visitFunctionDeclaration(@NotNull SwiftFunctionDeclaration functionDeclaration) {
        super.visitFunctionDeclaration(functionDeclaration);

        PsiElement nameIdentifier = functionDeclaration.getNameIdentifier();
        if (nameIdentifier == null) {
            return;
        }

        // Do not show problem if the method is private
        List<SwiftDeclarationSpecifier> declarationSpecifierList = functionDeclaration.getAttributes().getDeclarationSpecifierList();
        if (!declarationSpecifierList.isEmpty()) {
            for (SwiftDeclarationSpecifier specifier : declarationSpecifierList) {
                if (specifier.getText().equals("private")) {
                    return;
                }
            }
        }

        // Do not show problem if the method is overridden
        Collection<SwiftCallableSymbol> allAncestors = SwiftMethodAncestorsSearch.search(SwiftMethodAncestorsSearch.getParameters(functionDeclaration.getSwiftSymbol(), true)).findAll();
        if (!allAncestors.isEmpty()) {
            return;
        }

        checkElement(functionDeclaration.getPrevSibling(), nameIdentifier);
    }

    @Override
    public void visitClassDeclaration(@NotNull SwiftClassDeclaration classDeclaration) {
        super.visitClassDeclaration(classDeclaration);

        PsiElement nameIdentifier = classDeclaration.getNameIdentifier();
        if (nameIdentifier == null) {
            return;
        }
        checkElement(classDeclaration.getPrevSibling(), nameIdentifier);
    }

    private void checkElement(PsiElement prevSibling, @NotNull PsiElement nameIdentifier) {
        if (prevSibling instanceof PsiWhiteSpace) {
            String spaceAmount = prevSibling.getText();
            if (spaceAmount.contains("\n\n")) {
                problemsHolder.registerProblem(nameIdentifier, "Missing documentation", ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
            } else {
                PsiElement elementBeforeSpace = prevSibling.getPrevSibling();
                if (!(elementBeforeSpace instanceof PsiComment)) {
                    problemsHolder.registerProblem(nameIdentifier, "Missing documentation", ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                } else {
                    if (!elementBeforeSpace.getText().startsWith("///")) {
                        problemsHolder.registerProblem(nameIdentifier, "The documentation should start with ///", ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
                    }
                }
            }
        }
    }
}
