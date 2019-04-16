package inspections.utils;

import com.jetbrains.swift.psi.SwiftAttribute;
import com.jetbrains.swift.psi.SwiftFunctionDeclaration;

import java.util.List;

public class FunctionUtils {
    public static boolean hasIbAction(SwiftFunctionDeclaration functionDeclaration) {
        List<SwiftAttribute> attributeList = functionDeclaration.getAttributes().getAttributeList();

        boolean result = false;
        for (SwiftAttribute attribute : attributeList) {
            String name = attribute.getName();
            if (name != null && name.equals("IBAction")) {
                result = true;
                break;
            }
        }

        return result;
    }
}
