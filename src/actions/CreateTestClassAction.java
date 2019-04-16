package actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.psi.PsiFile;
import com.jetbrains.cidr.xcode.model.PbxGroupPsiElement;
import com.jetbrains.swift.psi.SwiftPsiElementFactory;
import com.jetbrains.swift.psi.impl.SwiftClassDeclarationGenImpl;
import com.jetbrains.swift.psi.impl.SwiftPsiElementFactoryImpl;
import org.jetbrains.annotations.NotNull;

public class CreateTestClassAction extends AnAction {
    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        PbxGroupPsiElement directory = (PbxGroupPsiElement) e.getData(CommonDataKeys.PSI_ELEMENT);

        Runnable runnable = () -> ApplicationManager.getApplication().runWriteAction(() -> {
            PsiFile exampleAdapter = directory.getDirectoryPsiElement().createFile("ExampleTest.swift");


            SwiftPsiElementFactory factory = SwiftPsiElementFactoryImpl.getInstance(e.getProject());
            SwiftClassDeclarationGenImpl statement = (SwiftClassDeclarationGenImpl) factory.createStatement("class Hello : XCTestCase {\n}", exampleAdapter);
            exampleAdapter.add(statement);
        });

        CommandProcessor.getInstance().executeCommand(e.getProject(), runnable, "Creating adapter", null);
    }
}
