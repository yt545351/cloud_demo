package com.example.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author yt
 * @since 2022-05-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Student对象", description="")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄")
    private Integer age;

    @ApiModelProperty(value = "性别")
    private Integer sex;

    @ApiModelProperty(value = "家庭地址")
    private String address;

    @ApiModelProperty(value = "联系电话")
    private String tell;

    @ApiModelProperty(value = "年级")
    private Integer grade;

    @ApiModelProperty(value = "班级")
    private Integer class_name;

    @ApiModelProperty(value = "班主任")
    private String class_teacher_name;

    @ApiModelProperty(value = "班主任电话")
    private String class_teacher_tell;

    @ApiModelProperty(value = "父亲")
    private String fa_name;

    @ApiModelProperty(value = "父亲电话")
    private String fa_tell;

    @ApiModelProperty(value = "母亲")
    private String mo_name;

    @ApiModelProperty(value = "母亲电话")
    private String mo_tell;


}
