import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ProjectZipper {

    public static void main(String[] args) {
        String projectPath = "../WordDetective"; // Replace this with your project directory
        String zipFileName = "WordDetective.zip"; // Name for the output zip file

        zipProject(projectPath, zipFileName);
    }

    public static void zipProject(String sourceDirPath, String zipFilePath) {
        try {
            FileOutputStream fos = new FileOutputStream(zipFilePath);
            ZipOutputStream zos = new ZipOutputStream(fos);
            File sourceDir = new File(sourceDirPath);
            addDirToZip(sourceDir, sourceDir, zos);
            zos.close();
            fos.close();
            System.out.println("Zip file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void addDirToZip(File rootPath, File dirObj, ZipOutputStream zos) throws IOException {
        File[] files = dirObj.listFiles();
        byte[] buffer = new byte[1024];
        int bytesRead;

        for (File file : files) {
            if (file.isDirectory()) {
                addDirToZip(rootPath, file, zos);
            } else {
                FileInputStream fis = new FileInputStream(file);
                String entryPath = file.getCanonicalPath().substring(rootPath.getCanonicalPath().length() + 1);
                ZipEntry zipEntry = new ZipEntry(entryPath);
                zos.putNextEntry(zipEntry);
                while ((bytesRead = fis.read(buffer)) > 0) {
                    zos.write(buffer, 0, bytesRead);
                }
                fis.close();
            }
        }
    }
}