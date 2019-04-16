package inspections.unused.parameters;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiReference;
import com.intellij.psi.search.searches.ReferencesSearch;
import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftParameter;
import com.jetbrains.swift.psi.SwiftVisitor;
import com.jetbrains.swift.search.supers.SwiftMethodAncestorsSearch;
import com.jetbrains.swift.symbols.SwiftCallableSymbol;
import com.jetbrains.swift.symbols.SwiftFunctionSymbol;
import com.jetbrains.swift.symbols.impl.SwiftFunctionSymbolImpl;
import inspections.unused.methods.UnusedMethodFix;
import inspections.utils.FunctionUtils;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public class UnusedParameterVisitor extends SwiftVisitor {

    private ProblemsHolder holder;

    UnusedParameterVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitParameter(@NotNull SwiftParameter parameter) {
        super.visitParameter(parameter);

        PsiReference first = ReferencesSearch.search(parameter.getNameIdentifier()).findFirst();
        if (first == null) {
            boolean isFromAncestors = false;
            boolean isInAttributeList = false;

            PsiElement context = parameter.getContext();
            if (context != null) {
                SwiftFunctionDeclaration functionDeclaration = (SwiftFunctionDeclaration) context.getContext();
                if (functionDeclaration != null) {
                    isInAttributeList = FunctionUtils.hasIbAction(functionDeclaration);
                    isFromAncestors = isFunctionFromAncestors(functionDeclaration);
                }
            }

            boolean shouldRegister = !isFromAncestors && !isInAttributeList;

            if (shouldRegister) {
                holder.registerProblem(parameter.getNameIdentifier(), "Unused parameter", ProblemHighlightType.LIKE_UNUSED_SYMBOL, new UnusedMethodFix(parameter));
            }
        }
    }


    private boolean isFunctionFromAncestors(SwiftFunctionDeclaration functionDeclaration) {
        SwiftFunctionSymbol swiftSymbol = functionDeclaration.getSwiftSymbol();
        if (swiftSymbol == null) {
            return false;
        }

        Collection<SwiftCallableSymbol> allAncestors = SwiftMethodAncestorsSearch.search(SwiftMethodAncestorsSearch.getParameters(swiftSymbol, true)).findAll();
        return allAncestors.stream().anyMatch(swiftCallableSymbol -> {
            SwiftFunctionSymbolImpl functionSymbo = (SwiftFunctionSymbolImpl) swiftCallableSymbol;
            VirtualFile containingFile = functionSymbo.getContainingFile();
            return containingFile != null && !containingFile.isWritable();
        });
    }
}
