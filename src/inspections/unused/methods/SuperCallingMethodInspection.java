package inspections.unused.methods;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.swift.codeinsight.highlighting.inspections.SwiftInspection;
import com.jetbrains.swift.psi.SwiftVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class SuperCallingMethodInspection extends SwiftInspection {
    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Super calling methods";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "SuperCallingMethod";
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
        return new SuperCallingMethodVisitor(problemsHolder);
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }
}
