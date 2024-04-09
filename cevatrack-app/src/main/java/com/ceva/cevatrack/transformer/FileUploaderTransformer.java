package com.ceva.cevatrack.transformer;

import com.ceva.framework.ejb.profile.transformer.AbstractEntityTransformer;
import com.ceva.cevatrack.domain.FileUploader;
import com.ceva.cevatrack.vo.FileUploaderVO;

public interface FileUploaderTransformer extends AbstractEntityTransformer<FileUploader> {
  /** converts vo to entity. */
  public FileUploader transformToFileUploader(FileUploaderVO fileUploader);
}
