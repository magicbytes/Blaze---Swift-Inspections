package inspections.enumerations;

import com.jetbrains.swift.psi.SwiftEnumCaseClause;
import com.jetbrains.swift.psi.SwiftEnumDeclaration;

import java.util.ArrayList;
import java.util.List;

class EnumSortedStatusChecker {

    private SwiftEnumDeclaration enumDeclaration;
    private List<String> constants = new ArrayList<>();

    EnumSortedStatusChecker(SwiftEnumDeclaration enumDeclaration) {
        this.enumDeclaration = enumDeclaration;
    }

    boolean isSorted() {
        extractAllConstants();

        List<String> tmp = new ArrayList<>(constants);
        constants.sort(String::compareTo);

        return tmp.equals(constants);
    }

    private void extractAllConstants() {
        constants.clear();

        List<SwiftEnumCaseClause> allFields = enumDeclaration.getEnumCaseClauseList();
        for (SwiftEnumCaseClause field : allFields) {
            constants.add(field.getText().replace("case ", "").trim());
        }
    }
}
