package com.ceva.cevatrack.repository;

import org.apache.deltaspike.data.api.AbstractFullEntityRepository;
import org.apache.deltaspike.data.api.Repository;
import com.ceva.cevatrack.domain.FileUploader;

@Repository(forEntity = FileUploader.class)
public abstract class FileUploaderRepository extends AbstractFullEntityRepository<FileUploader, Long> {

}
