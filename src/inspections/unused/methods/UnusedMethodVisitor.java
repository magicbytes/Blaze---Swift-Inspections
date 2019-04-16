package inspections.unused.methods;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftTypeDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import com.jetbrains.swift.search.supers.SwiftMethodAncestorsSearch;
import com.jetbrains.swift.symbols.SwiftCallableSymbol;
import com.jetbrains.swift.symbols.SwiftClassSymbol;
import com.jetbrains.swift.symbols.SwiftFunctionSymbol;
import com.jetbrains.swift.symbols.SwiftTypeSymbol;
import com.jetbrains.swift.symbols.impl.SwiftClassSymbolUtil;
import com.jetbrains.swift.symbols.impl.SwiftFunctionSymbolImpl;
import inspections.utils.FunctionUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public class UnusedMethodVisitor extends SwiftVisitor {

    private ProblemsHolder holder;

    UnusedMethodVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitFunctionDeclaration(@NotNull SwiftFunctionDeclaration functionDeclaration) {
        super.visitFunctionDeclaration(functionDeclaration);

        boolean shouldRegister = false;

        boolean isTest = isTestClass(functionDeclaration);
        if (!isTest) {
            boolean hasIbAction = FunctionUtils.hasIbAction(functionDeclaration);
            if (!hasIbAction) {
                @Nullable PsiReference firstReference = ReferencesSearch.search(functionDeclaration).findFirst();
                SwiftFunctionSymbol swiftSymbol = functionDeclaration.getSwiftSymbol();
                if (firstReference == null && swiftSymbol != null) {
                    Collection<SwiftCallableSymbol> allAncestors = SwiftMethodAncestorsSearch.search(SwiftMethodAncestorsSearch.getParameters(swiftSymbol, true)).findAll();
                    if (allAncestors.isEmpty()) {
                        shouldRegister = true;
                    } else {
                        boolean hasAncestorWithReferences = hasAncestorsWithReferences(allAncestors);
                        shouldRegister = !hasAncestorWithReferences;
                    }
                }
            }
        }

        if (shouldRegister) {
            PsiElement nameIdentifier = functionDeclaration.getNameIdentifier();
            if (nameIdentifier != null) {
                holder.registerProblem(nameIdentifier, "Remove unused method", ProblemHighlightType.LIKE_UNUSED_SYMBOL, new UnusedMethodFix(functionDeclaration));
            }
        }
    }

    private boolean isTestClass(SwiftFunctionDeclaration functionDeclaration) {
        boolean result = false;

        SwiftTypeDeclaration containingClass = functionDeclaration.getContainingClass();
        if (containingClass != null) {
            SwiftTypeSymbol swiftSymbol = containingClass.getSwiftSymbol();
            if (swiftSymbol != null) {
                SwiftClassSymbol baseClass = SwiftClassSymbolUtil.getBaseClass(swiftSymbol);
                result = baseClass != null && baseClass.getName().startsWith("XCTest");
            }
        }

        return result;
    }

    private boolean hasAncestorsWithReferences(Collection<SwiftCallableSymbol> allAncestors) {
        return allAncestors.stream().anyMatch(swiftCallableSymbol -> {
            SwiftFunctionSymbolImpl functionSymbo = (SwiftFunctionSymbolImpl) swiftCallableSymbol;
            VirtualFile containingFile = functionSymbo.getContainingFile();
            boolean isReadOnly = !containingFile.isWritable();
            if (isReadOnly) {
                return true;
            }

            PsiElement element = functionSymbo.getElement();
            PsiReference first = ReferencesSearch.search(element).findFirst();
            return first != null;
        });
    }
}
