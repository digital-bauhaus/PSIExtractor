package serialization;

import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.DebugUtil;

public class Serializer {

    public void serialize(PsiFile psiFile) {
        String psiAsString = getPSIAsString(psiFile);
        System.out.println(psiAsString);
    }

    /**
     * https://github.com/cmf/psiviewer/blob/master/src/idea/plugin/psiviewer/controller/actions/PsiDump.java
     * @param file
     * @return
     */
    private String getPSIAsString(PsiFile file) {
        StringBuilder sb = new StringBuilder();
        sb.append(DebugUtil.psiToString(file, true, true)).toString();
        return sb.toString();
    }

}
