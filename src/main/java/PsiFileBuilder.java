import com.intellij.lang.java.JavaLanguage;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;

public class PsiFileBuilder {
    Project project;
    public PsiFileBuilder(Project project) {
        this.project = project;
    }

    public PsiFile getPsiFile(String javaFilePath) {
        String text = Utils.readFile(javaFilePath);
        PsiFile psiFile = PsiFileFactory.getInstance(this.project).createFileFromText(JavaLanguage.INSTANCE, text);
        return psiFile;
    }
}
