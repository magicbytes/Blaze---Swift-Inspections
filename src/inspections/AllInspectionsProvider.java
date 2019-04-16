package inspections;

import com.intellij.codeInspection.InspectionToolProvider;
import inspections.enumerations.SortEnumInspection;
import inspections.unused.empty.EmptyMethodInspection;
import inspections.unused.methods.SuperCallingMethodInspection;
import inspections.unused.methods.UnusedMethodInspection;
import inspections.unused.parameters.UnusedParameterMethodInspection;
import inspections.unused.unusedClass.UnusedClassInspection;
import inspections.unused.unusedInstance.UnusedInstanceInspection;
import org.jetbrains.annotations.NotNull;

public class AllInspectionsProvider implements InspectionToolProvider {
    @NotNull
    @Override
    public Class[] getInspectionClasses() {
//        return new Class[]{InstanceVariableInspection.class, UnusedMethodInspection.class, SortEnumInspection.class, UnusedParameterMethodInspection.class};
        return new Class[]{
                UnusedParameterMethodInspection.class,
                UnusedMethodInspection.class,
                SortEnumInspection.class,
                EmptyMethodInspection.class,
                SuperCallingMethodInspection.class,
                UnusedClassInspection.class,
                UnusedInstanceInspection.class
        };
    }
}
