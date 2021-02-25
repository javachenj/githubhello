package com.nancal.entity.uc;

import com.nancal.entity.BaseEntity;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity(name = "user_test")
public class UserTest extends BaseEntity {
   @Column(name = "name")
    private String name;
   @Column(name = "age")
    private Integer age;
   @Column(name = "dept")
    private Integer dept;
   @Builder
    public UserTest(Long id, LocalDateTime createAt, LocalDateTime updateAt, String createUserId, String createBy, String updateUserId, String updateBy, boolean delFlag){
       super(id,createAt,updateAt,createUserId,createBy,updateUserId,updateBy,delFlag);
       this.name=name;
       this.age=age;
       this.dept=dept;
   }
}
