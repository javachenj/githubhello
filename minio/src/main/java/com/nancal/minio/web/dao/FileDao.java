package com.nancal.minio.web.dao;

import com.nancal.entity.uc.File;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileDao extends JpaRepository<File,Long>, QuerydslPredicateExecutor<File> {

   @Query(value = "select * from file f where f.file_status= :fileStatus")
    List<File> findByFileStatus(@Param("fileStatus") Boolean fileStatus);
}
