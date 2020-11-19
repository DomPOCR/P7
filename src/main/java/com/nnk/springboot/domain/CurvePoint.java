package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "curvepoint")
public class CurvePoint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "TINYINT")
    private Integer id ;

    @Column(name="CurveId",columnDefinition = "TINYINT")
    @NotNull(message = "must be not null")
    private Integer curveId ;

    @Column(name="asOfDate")
    private Timestamp asOfDate ;

    @Column(name="term")
    @NotNull(message = "must be not null")
    private Double term ;

    @Column(name = "value")
    @NotNull(message = "must be not null")
    @Min(value=1, message = "must be greater than or equal to 1")
    private Double value ;

    @Column(name="creationDate")
    private Timestamp creationDate ;

    public CurvePoint() {
    }

    public CurvePoint(@NotNull(message = "must be not null") Integer curveId,
                      @NotNull(message = "must be not null") Double term,
                      @NotNull(message = "must be not null")
                      @Min(value = 1, message = "must be greater than or equal to 1") Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    public CurvePoint(Integer id,
                      @NotNull(message = "must be not null") Integer curveId,
                      @NotNull(message = "must be not null") Double term,
                      @NotNull(message = "must be not null")
                      @Min(value = 1, message = "must be greater than or equal to 1") Double value) {
        this.id = id;
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }

    @Override
    public String toString() {
        return "CurvePoint{" +
                "id=" + id +
                ", curveId=" + curveId +
                ", term=" + term +
                ", value=" + value +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }


    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
