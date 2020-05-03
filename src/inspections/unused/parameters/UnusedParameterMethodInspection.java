package inspections.unused.parameters;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.swift.codeinsight.highlighting.inspections.SwiftInspection;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class UnusedParameterMethodInspection extends SwiftInspection {
    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Unused parameter";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "UnusedParameterBlaze";
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
        return new UnusedParameterVisitor(problemsHolder);
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }

}
