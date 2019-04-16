package inspections.instanceVariable;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.codeInspection.ProblemsHolder;
import com.jetbrains.swift.psi.SwiftInitializer;
import com.jetbrains.swift.psi.SwiftVariableDeclaration;
import com.jetbrains.swift.psi.SwiftVisitor;
import inspections.enumerations.SortFix;
import org.jetbrains.annotations.NotNull;

public class InstanceVariableVisitor extends SwiftVisitor {

    private ProblemsHolder holder;

    public InstanceVariableVisitor(ProblemsHolder holder) {
        this.holder = holder;
    }

    @Override
    public void visitVariableDeclaration(@NotNull SwiftVariableDeclaration swiftVariableDeclaration) {
        super.visitVariableDeclaration(swiftVariableDeclaration);

        boolean shouldRegister = false;

        if (swiftVariableDeclaration.getText().startsWith("private")) {
            SwiftInitializer initializer = swiftVariableDeclaration.getPatternInitializerList().get(0).getInitializer();
            if (initializer == null) {
                shouldRegister = true;
            }
        }

        if (shouldRegister) {
            holder.registerProblem(swiftVariableDeclaration, "Add to constructor", ProblemHighlightType.GENERIC_ERROR_OR_WARNING, new InstanceVariableFix());
        }

        int z = 10 + 1;
    }
}
