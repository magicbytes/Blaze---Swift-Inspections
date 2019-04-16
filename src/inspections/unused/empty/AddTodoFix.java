package inspections.unused.empty;

import com.intellij.codeInspection.LocalQuickFix;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AddTodoFix implements LocalQuickFix {
    private List<String> enumFields = new ArrayList<>();

    @Nls
    @NotNull
    @Override
    public String getName() {
        return "Add TODO reminder";
    }

    @Nls
    @NotNull
    @Override
    public String getFamilyName() {
        return "Blaze - Swift Inspections";
    }

    @Override
    public void applyFix(@NotNull com.intellij.openapi.project.Project project, @NotNull com.intellij.codeInspection.ProblemDescriptor descriptor) {
//        SwiftFunctionDeclarationGenImpl functionDeclaration = (SwiftFunctionDeclarationGenImpl) descriptor.getPsiElement().getContext();
//
//        SwiftTokenType commentTokenType = new SwiftTokenType("EOL_COMMENT");
//
//        SwiftPsiElementFactory instance = SwiftPsiElementFactoryImpl.getInstance(project);
//        SwiftPsiElement element = instance.createElement("// TODO Add Implementation", functionDeclaration.getCodeBlock(), SwiftParserTypes.ANY_TYPE_ELEMENT);
//        SwiftPsiElement codeBlock = instance.createElement("", element, SwiftParserTypes.FUNCTION_DECLARATION);
//        codeBlock.add(element);
////        PsiCommentImpl psiComment = new PsiCommentImpl(commentTokenType, "// TODO Add Implementation");
////        functionDeclaration.getCodeBlock().add(psiComment);
////        functionDeclaration.add(element);
////        functionDeclaration.add(codeBlock);
//        SwiftStatement todo = instance.createStatement("TODO");
//        functionDeclaration.getCodeBlock().getStatements().add(todo);
//
//        int z = 10 + 1;
//
//
////        functionDeclaration.getCodeBlock().add(new PsiCommentImpl(IElementType.ARRAY_FACTORY.))

    }

}
