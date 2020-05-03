package inspections.unused.empty;

import com.intellij.codeInspection.ProblemsHolder;
import com.jetbrains.swift.codeinsight.highlighting.inspections.SwiftInspection;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class EmptyMethodInspection extends SwiftInspection {
    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Empty method declaration";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "EmptyMethodDecl";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "Blaze - Swift Inspections";
    }

    @NotNull
    @Override
    public SwiftVisitor buildVisitor(@NotNull ProblemsHolder problemsHolder) {
        return new EmptyMethodVisitor(problemsHolder);
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }
}
