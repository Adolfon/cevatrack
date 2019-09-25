package com.ceva.cevatrack.transformer;

import javax.ejb.Singleton;
import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformerImpl;
import com.ceva.cevatrack.domain.FileUploader;
import com.ceva.cevatrack.vo.FileUploaderVO;

/**
 * File Uploader.
 * 
 * @author murty.koppaka
 *
 */
@Singleton
public class FileUploaderTransformerImpl extends AbstractEntityTransformerImpl<FileUploader>
        implements FileUploaderTransformer {

    @Override
    public FileUploader transformToFileUploader(FileUploaderVO fileUploaderVO) {
        if (fileUploaderVO == null) {
            return null;
        }
        FileUploader fileUploader = new FileUploader();
        fileUploader.setId(fileUploaderVO.getId());
        fileUploader.setTodoId(fileUploaderVO.getToDoId());
        fileUploader.setFileName(fileUploaderVO.getFileName());
        fileUploader.setContentType(fileUploaderVO.getContentType());
        return fileUploader;
    }

}
