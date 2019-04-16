package inspections.documentation;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.jetbrains.swift.codeinsight.highlighting.inspections.SwiftInspection;
import inspections.enumerations.EnumVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

public class MissingDocumentationInspection extends SwiftInspection {
    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Missing Documentation";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "NoDocumentation";
    }

    @Nls
    @NotNull
    @Override
    public String getGroupDisplayName() {
        return "Blaze - Swift Inspections";
    }

    @NotNull
    @Override
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new PublicMethodsVisitor(holder);
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }
}
