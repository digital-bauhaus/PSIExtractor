import com.intellij.lang.ASTNode;
import com.intellij.lang.FileASTNode;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import org.jetbrains.annotations.NotNull;

public class ButtonAction extends AnAction {
    private void iterateOverFilesInProject(AnActionEvent event) {
        // try to add files at given path to project to be parsed
        // TODO: do we have to add real files to the project? can be enough to just use the PSIfile
        //       However, we will need a way to serialize them to let the python ML do its magic
        //       To sum up: we need serializer and deserializer for the tree
        createVirtualFilefromPath(event);
        // iterate over all files in the given project
        iterateOverRealFiles(event.getProject());
    }

    private void createVirtualFilefromPath(AnActionEvent event) {
        //TODO: change to corpus path
        String filePath = "/home/andre/Documents/parseFolderTest/TestClass.java";
        PsiFileBuilder psiFileBuilder = new PsiFileBuilder(event.getProject());
        PsiFile file = psiFileBuilder.getPsiFile(filePath);
        test(event, file);
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

    private void test(AnActionEvent e, PsiFile f) {
        PsiDirectory directory = LangDataKeys.IDE_VIEW.getData(e.getDataContext()).getOrChooseDirectory();
        // Writes file - but as foo.bar ... TODO
        directory.add(f);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent anActionEvent) {
        iterateOverFilesInProject(anActionEvent);
    }
}
