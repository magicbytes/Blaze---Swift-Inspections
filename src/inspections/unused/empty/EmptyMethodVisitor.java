package inspections.unused.empty;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElement;
import com.intellij.util.Processor;
import com.jetbrains.swift.psi.SwiftCodeBlock;
import com.jetbrains.swift.psi.SwiftFunctionDeclaration;
import com.jetbrains.swift.psi.SwiftStatement;
import com.jetbrains.swift.psi.SwiftVisitor;
import com.jetbrains.swift.symbols.SwiftSymbol;
import inspections.unused.methods.UnusedMethodFix;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class EmptyMethodVisitor extends SwiftVisitor {
    private ProblemsHolder holder;

    public EmptyMethodVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitFunctionDeclaration(@NotNull SwiftFunctionDeclaration functionDeclaration) {
        super.visitFunctionDeclaration(functionDeclaration);

        functionDeclaration.getSymbolsManager().getLocalSymbols(functionDeclaration.getContainingFile()).process(new Processor<SwiftSymbol>() {
            @Override
            public boolean process(SwiftSymbol swiftSymbol) {
                return false;
            }
        }, "whoKnow");

        SwiftCodeBlock codeBlock = functionDeclaration.getCodeBlock();
        PsiElement nameIdentifier = functionDeclaration.getNameIdentifier();
        if (codeBlock != null && nameIdentifier != null) {
            List<SwiftStatement> statementList = codeBlock.getStatements();
            if (statementList.isEmpty()) {
                holder.registerProblem(nameIdentifier, "Empty Method", ProblemHighlightType.LIKE_UNUSED_SYMBOL, new UnusedMethodFix(functionDeclaration, "Remove empty method"));
            }
        }
    }
}
