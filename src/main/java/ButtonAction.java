import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class ButtonAction extends AnAction {
    private void iterateOverFilesInProject(Project project) {
        // try to add files at given path to project to be parsed
        // TODO: do we have to add real files to the project? can be enough to just use the PSIfile
        //       However, we will need a way to serialize them to let the python ML do its magic
        //       To sum up: we need serializer and deserializer for the tree
        createVirtualFilefromPath(project);
        // iterate over all files in the given project
        iterateOverRealFiles(project);
    }

    private void createVirtualFilefromPath(Project project) {
        //TODO: change to corpus path
        String filePath = "/home/andre/Documents/parseFolderTest/TestClass.java";
        PsiFileBuilder psiFileBuilder = new PsiFileBuilder(project);
        PsiFile file = psiFileBuilder.getPsiFile(filePath);
    }

    private void iterateOverRealFiles(Project project) {
        ProjectFileIndex.SERVICE.getInstance(project).iterateContent(virtualFile -> {
            if(virtualFile.getFileType().getName().equals("JAVA")) {
                PsiFile psiFile = PsiManager.getInstance(project).findFile(virtualFile);
                System.out.println(psiFile);
                FileASTNode fileASTNode = psiFile.getNode();
                ASTNode node = (ASTNode) fileASTNode;
                // TODO: we need a way to serialize this fileASTNode
            }
            return true;
        });
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        iterateOverFilesInProject(project);
    }
}
