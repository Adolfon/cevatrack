package com.ceva.cevatrack.filenavigator;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.ceva.cjf.base.web.jsf.CJFBaseBean;
import com.ceva.framework.ejb.service.property.ConfigProperty;
import com.ceva.cevatrack.vo.FileVO;

/**
 * FileNavigatorView.
 * 
 * @author varsha.anumolu
 *
 */
@Named
@ViewScoped
public class FileNavigatorBean extends CJFBaseBean implements Serializable {

    private static final long serialVersionUID = 1L;
    private TreeNode root;

    private TreeNode selectedFile;

    private static final Logger LOGGER = LoggerFactory.getLogger(FileNavigatorBean.class);

    @Inject
    @ConfigProperty(key = "file.navigator.directories")
    private String fileNavigatorDirectory;

    @PostConstruct
    public void init() {
        root = createFileTree();
    }

    /**
     * Creates TreeTable with the list of files of the given Directories.
     * 
     */
    public TreeNode createFileTree() {
        TreeNode root = new DefaultTreeNode(new FileVO("Files", "", "Folder", ""), null);
        String[] directories = fileNavigatorDirectory.split(";");
        DateTimeFormatter newPattern = DateTimeFormatter.ofPattern(getUserProfileDateFormat());
        LocalDate today = LocalDate.now();
        for (String directory : directories) {

            String folderName = StringUtils.substringAfterLast(directory, File.separator);
            TreeNode tree = new DefaultTreeNode(new FileVO(folderName, "", "", ""), root);
            File folder = new File(directory);

            for (File file : folder.listFiles()) {

                if (file.isDirectory()) {

                    TreeNode fileTree = new DefaultTreeNode(new FileVO(file.getName(), "", "", file.getAbsolutePath()),
                            tree);

                    File subFolder = new File(file.getPath());
                    for (File fileOfSubFolder : subFolder.listFiles()) {
                        new DefaultTreeNode("FileVO",
                                new FileVO(fileOfSubFolder.getName(), today.format(newPattern),
                                        String.valueOf(fileOfSubFolder.length()), fileOfSubFolder.getAbsolutePath()),
                                fileTree);
                    }
                } else {
                    new DefaultTreeNode("FileVO", new FileVO(file.getName(), today.format(newPattern),
                            String.valueOf(file.length()), file.getAbsolutePath()), tree);
                }
            }
        }
        return root;
    }

    /**
     * Displays entire file content without tailing functionality.
     */
    public void displayFileContent(String filePath) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

        Path path = Paths.get(filePath);
        try {
            List<String> contents = Files.readAllLines(path);
            contents.stream().forEach(content -> {
                try {
                    response.getWriter().println(content);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            facesContext.responseComplete();
        } catch (IOException e) {
            e.printStackTrace();
            LOGGER.error("Could not load the file", e.getMessage());
        }

    }

    /**
     * Gets the selectedFile in the FileNavigator.
     * 
     * @return
     */
    public String getFilePath(String filePath) {
        String encodedFilePath = null;
        try {
            encodedFilePath = URLEncoder.encode(
                    Base64.getEncoder().encodeToString(filePath.replace(File.separator, "_").getBytes()), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return "file?faces-redirect=true&filePath=" + encodedFilePath;
    }

    public TreeNode getRoot() {
        return root;
    }

    public TreeNode getSelectedFile() {
        return selectedFile;
    }

    public void setSelectedFile(TreeNode selectedFile) {
        this.selectedFile = selectedFile;
    }

    public String getFileNavigatorDirectory() {
        return fileNavigatorDirectory;
    }

    public void setFileNavigatorDirectory(String fileNavigatorDirectory) {
        this.fileNavigatorDirectory = fileNavigatorDirectory;
    }
}
