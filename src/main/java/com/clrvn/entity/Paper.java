package com.clrvn.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "Paper")
@Data
public class Paper implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String paperName;

    private Integer paperType;

    private String paperPath;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String reportPath;

    private boolean isSystem;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    public Paper() {
        super();
    }

}
