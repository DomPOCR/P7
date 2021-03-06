package com.nnk.springboot.domain;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "TINYINT")
    Integer tradeId;

    @Column(name = "account")
    @NotNull
    @NotBlank(message = "Trade is mandatory")
    private String account;

    @Column(name = "type")
    @NotNull
    @NotBlank(message = "Type is mandatory")
    private String type;

    @Column(name = "buyQuantity")
    @NotNull
    @Min(value = 1, message = "must be greater than or equal to 1")
    private Double buyQuantity;

    @Column(name = "sellQuantity")
    @NotNull
    @Min(value = 1, message = "must be greater than or equal to 1")
    private Double sellQuantity;

    @Column(name = "buyPrice")
    private Double buyPrice;
    @Column(name = "sellPrice")
    private Double sellPrice;
    @Column(name = "benchmark", length = 125)
    private String benchmark;
    @Column(name = "tradeDate")
    private Timestamp tradeDate;
    @Column(name = "security", length = 125)
    private String security;
    @Column(name = "status", length = 10)
    private String status;
    @Column(name = "trader", length = 125)
    private String trader;
    @Column(name = "book", length = 125)
    private String book;
    @Column(name = "creationName", length = 125)
    private String creationName;
    @Column(name = "creationDate")
    private Timestamp creationDate;
    @Column(name = "revisionName", length = 125)
    private String revisionName;
    @Column(name = "revisionDate")
    private Timestamp revisionDate;
    @Column(name = "dealName", length = 125)
    private String dealName;
    @Column(name = "dealType", length = 125)
    private String dealType;
    @Column(name = "sourceListId", length = 125)
    private String sourceListId;
    @Column(name = "side", length = 125)
    private String side;

    public Trade() {
    }

    public Trade(@NotNull @NotBlank(message = "Trade is mandatory") String account,
                 @NotNull @NotBlank(message = "Type is mandatory") String type,
                 @NotNull @Min(value = 1, message = "must be greater than or equal to 1") Double buyQuantity,
                 @NotNull @Min(value = 1, message = "must be greater than or equal to 1") Double sellQuantity) {
        this.account = account;
        this.type = type;
        this.buyQuantity = buyQuantity;
        this.sellQuantity = sellQuantity;
    }

    @Override
    public String toString() {
        return "Trade{" +
                "tradeId=" + tradeId +
                ", account='" + account + '\'' +
                ", buyQuantity=" + buyQuantity +
                ", sellQuantity=" + sellQuantity +
                '}';
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Double getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Double sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getTradeDate() {
        return new Timestamp(tradeDate.getTime());
    }

    public void setTradeDate(Timestamp tradeDate) {
        this.tradeDate = new Timestamp(tradeDate.getTime());
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
    }

    public Timestamp getCreationDate() {
        return new Timestamp(creationDate.getTime());
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = new Timestamp(creationDate.getTime());
    }

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        return new Timestamp(revisionDate.getTime());
    }

    public void setRevisionDate(Timestamp revisionDate) {
        this.revisionDate = new Timestamp(revisionDate.getTime());
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
