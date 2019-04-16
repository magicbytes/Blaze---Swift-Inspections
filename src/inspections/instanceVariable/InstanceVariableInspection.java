package inspections.instanceVariable;

import com.intellij.codeInspection.ProblemsHolder;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.ui.DocumentAdapter;
import com.jetbrains.swift.codeinsight.highlighting.inspections.SwiftInspection;
import inspections.enumerations.EnumVisitor;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

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
    public PsiElementVisitor buildVisitor(@NotNull ProblemsHolder holder, boolean isOnTheFly) {
        return new InstanceVariableVisitor(holder);
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }
}
