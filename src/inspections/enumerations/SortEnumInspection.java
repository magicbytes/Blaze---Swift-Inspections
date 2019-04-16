package inspections.enumerations;

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

public class SortEnumInspection extends SwiftInspection {

    @Nls
    @NotNull
    @Override
    public String getDisplayName() {
        return "Unsorted Enums";
    }

    @NotNull
    @Override
    public String getShortName() {
        return "UnsortedEnum";
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
        return new EnumVisitor(holder);
    }

    @Override
    public boolean isEnabledByDefault() {
        return true;
    }
}
