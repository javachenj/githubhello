package com.nancal.entity.uc;

import com.nancal.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper =true)
@Entity
@Table(name = "file")
public class File extends BaseEntity implements Serializable {
    @Column(name = "uuid")
    private String uuid;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "file_status")
    private Boolean fileStatus;
    @Column(name = "file_suffix")
    private String fileSuffix;
    @Builder
    public File(Long id, LocalDateTime createAt, LocalDateTime updateAt, String createUserId, String createBy, String updateUserId, String updateBy, boolean delFlag, String uuid, String fileName, Boolean fileStatus) {
        super(id, createAt, updateAt, createUserId, createBy, updateUserId, updateBy, delFlag);
        this.uuid = uuid;
        this.fileName= fileName;
        this.fileStatus=fileStatus;
    }
}
