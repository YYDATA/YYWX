package com.yy.domain.entity;

import java.math.BigDecimal;
import java.util.Date;

public class LoanOrder {
    private String loanordercode;

    private String productcode;

    private String loancontractcode;

    private String loantitle;

    private Long customerid;

    private Long salerid;

    private Float loanamount;

    private Float guarantyfee;

    private String currency;

    private Date startdate;

    private Date enddate;

    private Integer loanterm;

    private String loantermtype;

    private Integer graceterm;

    private String gracetermtype;

    private String guarantyorgcode;

    private String orderstatus;

    private String channelcode;

    private BigDecimal interestrate;

    private Float fee;

    private String risklevel;

    private Integer repayday;

    private Date orderdate;

    private BigDecimal overdueinterestrate;

    private Long recorder;

    private String loanremark;

    private String summary;
    
    private String cellphone;

    public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}
    public String getLoanordercode() {
        return loanordercode;
    }

    public void setLoanordercode(String loanordercode) {
        this.loanordercode = loanordercode == null ? null : loanordercode.trim();
    }

    public String getProductcode() {
        return productcode;
    }

    public void setProductcode(String productcode) {
        this.productcode = productcode == null ? null : productcode.trim();
    }

    public String getLoancontractcode() {
        return loancontractcode;
    }

    public void setLoancontractcode(String loancontractcode) {
        this.loancontractcode = loancontractcode == null ? null : loancontractcode.trim();
    }

    public String getLoantitle() {
        return loantitle;
    }

    public void setLoantitle(String loantitle) {
        this.loantitle = loantitle == null ? null : loantitle.trim();
    }

    public Long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(Long customerid) {
        this.customerid = customerid;
    }

    public Long getSalerid() {
        return salerid;
    }

    public void setSalerid(Long salerid) {
        this.salerid = salerid;
    }

    public Float getLoanamount() {
        return loanamount;
    }

    public void setLoanamount(Float loanamount) {
        this.loanamount = loanamount;
    }

    public Float getGuarantyfee() {
        return guarantyfee;
    }

    public void setGuarantyfee(Float guarantyfee) {
        this.guarantyfee = guarantyfee;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency == null ? null : currency.trim();
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Integer getLoanterm() {
        return loanterm;
    }

    public void setLoanterm(Integer loanterm) {
        this.loanterm = loanterm;
    }

    public String getLoantermtype() {
        return loantermtype;
    }

    public void setLoantermtype(String loantermtype) {
        this.loantermtype = loantermtype == null ? null : loantermtype.trim();
    }

    public Integer getGraceterm() {
        return graceterm;
    }

    public void setGraceterm(Integer graceterm) {
        this.graceterm = graceterm;
    }

    public String getGracetermtype() {
        return gracetermtype;
    }

    public void setGracetermtype(String gracetermtype) {
        this.gracetermtype = gracetermtype == null ? null : gracetermtype.trim();
    }

    public String getGuarantyorgcode() {
        return guarantyorgcode;
    }

    public void setGuarantyorgcode(String guarantyorgcode) {
        this.guarantyorgcode = guarantyorgcode == null ? null : guarantyorgcode.trim();
    }

    public String getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(String orderstatus) {
        this.orderstatus = orderstatus == null ? null : orderstatus.trim();
    }

    public String getChannelcode() {
        return channelcode;
    }

    public void setChannelcode(String channelcode) {
        this.channelcode = channelcode == null ? null : channelcode.trim();
    }

    public BigDecimal getInterestrate() {
        return interestrate;
    }

    public void setInterestrate(BigDecimal interestrate) {
        this.interestrate = interestrate;
    }

    public Float getFee() {
        return fee;
    }

    public void setFee(Float fee) {
        this.fee = fee;
    }

    public String getRisklevel() {
        return risklevel;
    }

    public void setRisklevel(String risklevel) {
        this.risklevel = risklevel == null ? null : risklevel.trim();
    }

    public Integer getRepayday() {
        return repayday;
    }

    public void setRepayday(Integer repayday) {
        this.repayday = repayday;
    }

    public Date getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(Date orderdate) {
        this.orderdate = orderdate;
    }

    public BigDecimal getOverdueinterestrate() {
        return overdueinterestrate;
    }

    public void setOverdueinterestrate(BigDecimal overdueinterestrate) {
        this.overdueinterestrate = overdueinterestrate;
    }

    public Long getRecorder() {
        return recorder;
    }

    public void setRecorder(Long recorder) {
        this.recorder = recorder;
    }

    public String getLoanremark() {
        return loanremark;
    }

    public void setLoanremark(String loanremark) {
        this.loanremark = loanremark == null ? null : loanremark.trim();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary == null ? null : summary.trim();
    }
}