package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.sql.Timestamp;

@Entity
@Table(name = "rulename")
public class RuleName {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "TINYINT")
    private Integer id ;

    @Column(name="name",length = 125)
    private String name ;

    @Column(name="description",length = 125)
    private String description ;

    @Column(name="json",length = 125)
    private String json ;

    @Column(name="template",length = 125)
    private String template ;

    @Column(name="sqlStr",length = 125)
    private String sqlStr ;

    @Column(name="sqlPart",length = 125)
    private String sqlPart ;

    public RuleName() {
    }

    public RuleName(String name, String description, String json, String template, String sqlStr, String sqlPart) {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getSqlStr() {
        return sqlStr;
    }

    public void setSqlStr(String sqlStr) {
        this.sqlStr = sqlStr;
    }

    public String getSqlPart() {
        return sqlPart;
    }

    public void setSqlPart(String sqlPart) {
        this.sqlPart = sqlPart;
    }
}
