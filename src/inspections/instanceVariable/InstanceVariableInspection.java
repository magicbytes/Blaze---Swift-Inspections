package inspections.instanceVariable;

import com.intellij.codeInspection.ProblemsHolder;
import com.jetbrains.swift.codeinsight.highlighting.inspections.SwiftInspection;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class InstanceVariableInspection extends SwiftInspection {

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Instance variable which are not initialised";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "SortingEnums";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "General";
    }

    @NotNull
    @Override
    public SwiftVisitor buildVisitor(@NotNull ProblemsHolder problemsHolder) {
        return new InstanceVariableVisitor(problemsHolder);
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }
}
