package com.fego.transaction.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fego.transaction.common.base.BaseDto;
import com.fego.transaction.dto.integration.TierOneInvestmentTransactionDto;
import com.fego.transaction.dto.integration.TierOneSchemeTransactionDto;
import com.fego.transaction.dto.integration.TierTwoInvestmentTransactionDto;
import com.fego.transaction.dto.integration.TierTwoSchemeTransactionDto;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransactionDto extends BaseDto {
    private Long userId;
    private Long accountId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate endDate;
    private BigDecimal amount;
    private BigDecimal currentBalance;
    private BigDecimal balance;
    private String mode;
    private String narration;
    private String reference;
    private String transactionTimestamp;
    private String transactionId;
    private String type;
    private String valueDate;
    private String transactionDateTime;
    private String amc;
    private String registrar;
    private String schemeCode;
    private String schemePlan;
    private String schemeTypes;
    private String schemeCategory;
    private String isin;
    private String amfiCode;
    private String schemeOption;
    private String fundType;
    private String ucc;
    private BigDecimal closingUnits;
    private BigDecimal lienUnits;
    private String nav;
    private LocalDate navDate;
    private LocalDate orderDate;
    private LocalDate executionDate;
    @JsonProperty("lock-inDays")
    private String lockinDays;
    @JsonProperty("lock-inFlag")
    private String lockinFlag;
    private BigDecimal cost;
    private String premium;
    private BigDecimal premiumAllocationCharge;
    private BigDecimal otherCharges;
    private String units;
    private String fundName;
    private LocalDate transactionDate;
    private BigDecimal employeeDepositAmount;
    private BigDecimal employerDepositAmount;
    private BigDecimal employeeWithdrawalAmount;
    private BigDecimal employerWithdrawalAmount;
    private BigDecimal pensionFundAmount;
    private String transactionType;
    private LocalDate statementDate;
    private String mcc;
    private String maskedCardNumber;
    private String issuerName;
    private String symbol;
    private String exchange;
    private String transactionDescription;
    private BigDecimal price;
    private String unitsType;
    private String numUnitsTransacted;
    private String tradeValue;
    private BigDecimal totalCharge;
    private String nameOfAsset;
    private String investmentDateTime;
    private BigDecimal investmentValue;
    private LocalDate redemptionDate;
    private BigDecimal redemptionValue;
    private LocalDate fundFeesPaymentDate;
    private BigDecimal fundFeesPaymentAmount;
    private LocalDate reInvestmentDate;
    private BigDecimal reInvestmentValue;
    private LocalDate paymentInvestorDate;
    private BigDecimal paymentInvestorAmount;
    private BigDecimal taxPaidValue;
    private LocalDate taxPaidDate;
    private String dividendType;
    private String orderId;
    private String tradeId;
    private String companyName;
    private String rate;
    private String schemeName;
    private String dpId;
    private String schemeMinLotSize;
    private String faceValueofUnits;
    private String brokerCode;
    private String currency;
    private String otherTaxes;
    private String equityCategory;
    private String instrumentType;
    private String optionType;
    private BigDecimal strikePrice;
    private String shareHolderEquityType;
    private String stt;
    private String scheme;
    private String folioNo;
    private String firstLevelCategory;
    private String secondLevelCategory;
    private String thirdLevelCategory;
    private String fourthLevelCategory;
    private String remark;
    private String transactionNumber;
    private String fegoCategory;
    private String fegoSubCategory;
    private String merchant;
    private String fegoTransactionType;
    private Boolean isFixedTransaction;
    private Boolean isPartOfBudget;
    private Boolean isPartOfReport;
    private String accountType;
    private String location;
    private String bankName;
    private String maskedAccountNumber;
    private Boolean isUserUpdated;
    private TierOneSchemeTransactionDto tierOneSchemeTransactionDto;
    private TierTwoSchemeTransactionDto tierTwoSchemeTransactionDto;
    private TierOneInvestmentTransactionDto tierOneInvestmentTransactionDto;
    private TierTwoInvestmentTransactionDto tierTwoInvestmentTransactionDto;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getTransactionTimestamp() {
        return transactionTimestamp;
    }

    public void setTransactionTimestamp(String transactionTimestamp) {
        this.transactionTimestamp = transactionTimestamp;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getValueDate() {
        return valueDate;
    }

    public void setValueDate(String valueDate) {
        this.valueDate = valueDate;
    }

    public String getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public String getAmc() {
        return amc;
    }

    public void setAmc(String amc) {
        this.amc = amc;
    }

    public String getRegistrar() {
        return registrar;
    }

    public void setRegistrar(String registrar) {
        this.registrar = registrar;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getSchemePlan() {
        return schemePlan;
    }

    public void setSchemePlan(String schemePlan) {
        this.schemePlan = schemePlan;
    }

    public String getSchemeTypes() {
        return schemeTypes;
    }

    public void setSchemeTypes(String schemeTypes) {
        this.schemeTypes = schemeTypes;
    }

    public String getSchemeCategory() {
        return schemeCategory;
    }

    public void setSchemeCategory(String schemeCategory) {
        this.schemeCategory = schemeCategory;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public String getAmfiCode() {
        return amfiCode;
    }

    public void setAmfiCode(String amfiCode) {
        this.amfiCode = amfiCode;
    }

    public String getSchemeOption() {
        return schemeOption;
    }

    public void setSchemeOption(String schemeOption) {
        this.schemeOption = schemeOption;
    }

    public String getFundType() {
        return fundType;
    }

    public void setFundType(String fundType) {
        this.fundType = fundType;
    }

    public String getUcc() {
        return ucc;
    }

    public void setUcc(String ucc) {
        this.ucc = ucc;
    }

    public BigDecimal getClosingUnits() {
        return closingUnits;
    }

    public void setClosingUnits(BigDecimal closingUnits) {
        this.closingUnits = closingUnits;
    }

    public BigDecimal getLienUnits() {
        return lienUnits;
    }

    public void setLienUnits(BigDecimal lienUnits) {
        this.lienUnits = lienUnits;
    }

    public String getNav() {
        return nav;
    }

    public void setNav(String nav) {
        this.nav = nav;
    }

    public LocalDate getNavDate() {
        return navDate;
    }

    public void setNavDate(LocalDate navDate) {
        this.navDate = navDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getExecutionDate() {
        return executionDate;
    }

    public void setExecutionDate(LocalDate executionDate) {
        this.executionDate = executionDate;
    }

    public String getLockinDays() {
        return lockinDays;
    }

    public void setLockinDays(String lockinDays) {
        this.lockinDays = lockinDays;
    }

    public String getLockinFlag() {
        return lockinFlag;
    }

    public void setLockinFlag(String lockinFlag) {
        this.lockinFlag = lockinFlag;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public String getPremium() {
        return premium;
    }

    public void setPremium(String premium) {
        this.premium = premium;
    }

    public BigDecimal getPremiumAllocationCharge() {
        return premiumAllocationCharge;
    }

    public void setPremiumAllocationCharge(BigDecimal premiumAllocationCharge) {
        this.premiumAllocationCharge = premiumAllocationCharge;
    }

    public BigDecimal getOtherCharges() {
        return otherCharges;
    }

    public void setOtherCharges(BigDecimal otherCharges) {
        this.otherCharges = otherCharges;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public BigDecimal getEmployeeDepositAmount() {
        return employeeDepositAmount;
    }

    public void setEmployeeDepositAmount(BigDecimal employeeDepositAmount) {
        this.employeeDepositAmount = employeeDepositAmount;
    }

    public BigDecimal getEmployerDepositAmount() {
        return employerDepositAmount;
    }

    public void setEmployerDepositAmount(BigDecimal employerDepositAmount) {
        this.employerDepositAmount = employerDepositAmount;
    }

    public BigDecimal getEmployeeWithdrawalAmount() {
        return employeeWithdrawalAmount;
    }

    public void setEmployeeWithdrawalAmount(BigDecimal employeeWithdrawalAmount) {
        this.employeeWithdrawalAmount = employeeWithdrawalAmount;
    }

    public BigDecimal getEmployerWithdrawalAmount() {
        return employerWithdrawalAmount;
    }

    public void setEmployerWithdrawalAmount(BigDecimal employerWithdrawalAmount) {
        this.employerWithdrawalAmount = employerWithdrawalAmount;
    }

    public BigDecimal getPensionFundAmount() {
        return pensionFundAmount;
    }

    public void setPensionFundAmount(BigDecimal pensionFundAmount) {
        this.pensionFundAmount = pensionFundAmount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public LocalDate getStatementDate() {
        return statementDate;
    }

    public void setStatementDate(LocalDate statementDate) {
        this.statementDate = statementDate;
    }

    public String getMcc() {
        return mcc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    public String getMaskedCardNumber() {
        return maskedCardNumber;
    }

    public void setMaskedCardNumber(String maskedCardNumber) {
        this.maskedCardNumber = maskedCardNumber;
    }

    public String getIssuerName() {
        return issuerName;
    }

    public void setIssuerName(String issuerName) {
        this.issuerName = issuerName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getExchange() {
        return exchange;
    }

    public void setExchange(String exchange) {
        this.exchange = exchange;
    }

    public String getTransactionDescription() {
        return transactionDescription;
    }

    public void setTransactionDescription(String transactionDescription) {
        this.transactionDescription = transactionDescription;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getUnitsType() {
        return unitsType;
    }

    public void setUnitsType(String unitsType) {
        this.unitsType = unitsType;
    }

    public String getNumUnitsTransacted() {
        return numUnitsTransacted;
    }

    public void setNumUnitsTransacted(String numUnitsTransacted) {
        this.numUnitsTransacted = numUnitsTransacted;
    }

    public String getTradeValue() {
        return tradeValue;
    }

    public void setTradeValue(String tradeValue) {
        this.tradeValue = tradeValue;
    }

    public BigDecimal getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(BigDecimal totalCharge) {
        this.totalCharge = totalCharge;
    }

    public String getNameOfAsset() {
        return nameOfAsset;
    }

    public void setNameOfAsset(String nameOfAsset) {
        this.nameOfAsset = nameOfAsset;
    }

    public String getInvestmentDateTime() {
        return investmentDateTime;
    }

    public void setInvestmentDateTime(String investmentDateTime) {
        this.investmentDateTime = investmentDateTime;
    }

    public BigDecimal getInvestmentValue() {
        return investmentValue;
    }

    public void setInvestmentValue(BigDecimal investmentValue) {
        this.investmentValue = investmentValue;
    }

    public LocalDate getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(LocalDate redemptionDate) {
        this.redemptionDate = redemptionDate;
    }

    public BigDecimal getRedemptionValue() {
        return redemptionValue;
    }

    public void setRedemptionValue(BigDecimal redemptionValue) {
        this.redemptionValue = redemptionValue;
    }

    public LocalDate getFundFeesPaymentDate() {
        return fundFeesPaymentDate;
    }

    public void setFundFeesPaymentDate(LocalDate fundFeesPaymentDate) {
        this.fundFeesPaymentDate = fundFeesPaymentDate;
    }

    public BigDecimal getFundFeesPaymentAmount() {
        return fundFeesPaymentAmount;
    }

    public void setFundFeesPaymentAmount(BigDecimal fundFeesPaymentAmount) {
        this.fundFeesPaymentAmount = fundFeesPaymentAmount;
    }

    public LocalDate getReInvestmentDate() {
        return reInvestmentDate;
    }

    public void setReInvestmentDate(LocalDate reInvestmentDate) {
        this.reInvestmentDate = reInvestmentDate;
    }

    public BigDecimal getReInvestmentValue() {
        return reInvestmentValue;
    }

    public void setReInvestmentValue(BigDecimal reInvestmentValue) {
        this.reInvestmentValue = reInvestmentValue;
    }

    public LocalDate getPaymentInvestorDate() {
        return paymentInvestorDate;
    }

    public void setPaymentInvestorDate(LocalDate paymentInvestorDate) {
        this.paymentInvestorDate = paymentInvestorDate;
    }

    public BigDecimal getPaymentInvestorAmount() {
        return paymentInvestorAmount;
    }

    public void setPaymentInvestorAmount(BigDecimal paymentInvestorAmount) {
        this.paymentInvestorAmount = paymentInvestorAmount;
    }

    public BigDecimal getTaxPaidValue() {
        return taxPaidValue;
    }

    public void setTaxPaidValue(BigDecimal taxPaidValue) {
        this.taxPaidValue = taxPaidValue;
    }

    public LocalDate getTaxPaidDate() {
        return taxPaidDate;
    }

    public void setTaxPaidDate(LocalDate taxPaidDate) {
        this.taxPaidDate = taxPaidDate;
    }

    public String getDividendType() {
        return dividendType;
    }

    public void setDividendType(String dividendType) {
        this.dividendType = dividendType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTradeId() {
        return tradeId;
    }

    public void setTradeId(String tradeId) {
        this.tradeId = tradeId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getDpId() {
        return dpId;
    }

    public void setDpId(String dpId) {
        this.dpId = dpId;
    }

    public String getSchemeMinLotSize() {
        return schemeMinLotSize;
    }

    public void setSchemeMinLotSize(String schemeMinLotSize) {
        this.schemeMinLotSize = schemeMinLotSize;
    }

    public String getFaceValueofUnits() {
        return faceValueofUnits;
    }

    public void setFaceValueofUnits(String faceValueofUnits) {
        this.faceValueofUnits = faceValueofUnits;
    }

    public String getBrokerCode() {
        return brokerCode;
    }

    public void setBrokerCode(String brokerCode) {
        this.brokerCode = brokerCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOtherTaxes() {
        return otherTaxes;
    }

    public void setOtherTaxes(String otherTaxes) {
        this.otherTaxes = otherTaxes;
    }

    public String getEquityCategory() {
        return equityCategory;
    }

    public void setEquityCategory(String equityCategory) {
        this.equityCategory = equityCategory;
    }

    public String getInstrumentType() {
        return instrumentType;
    }

    public void setInstrumentType(String instrumentType) {
        this.instrumentType = instrumentType;
    }

    public String getOptionType() {
        return optionType;
    }

    public void setOptionType(String optionType) {
        this.optionType = optionType;
    }

    public BigDecimal getStrikePrice() {
        return strikePrice;
    }

    public void setStrikePrice(BigDecimal strikePrice) {
        this.strikePrice = strikePrice;
    }

    public String getShareHolderEquityType() {
        return shareHolderEquityType;
    }

    public void setShareHolderEquityType(String shareHolderEquityType) {
        this.shareHolderEquityType = shareHolderEquityType;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public String getFirstLevelCategory() {
        return firstLevelCategory;
    }

    public void setFirstLevelCategory(String firstLevelCategory) {
        this.firstLevelCategory = firstLevelCategory;
    }

    public String getSecondLevelCategory() {
        return secondLevelCategory;
    }

    public void setSecondLevelCategory(String secondLevelCategory) {
        this.secondLevelCategory = secondLevelCategory;
    }

    public String getThirdLevelCategory() {
        return thirdLevelCategory;
    }

    public void setThirdLevelCategory(String thirdLevelCategory) {
        this.thirdLevelCategory = thirdLevelCategory;
    }

    public String getFourthLevelCategory() {
        return fourthLevelCategory;
    }

    public void setFourthLevelCategory(String fourthLevelCategory) {
        this.fourthLevelCategory = fourthLevelCategory;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTransactionNumber() {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber) {
        this.transactionNumber = transactionNumber;
    }

    public TierOneSchemeTransactionDto getTier1SchemeTransactionDto() {
        return tierOneSchemeTransactionDto;
    }

    public void setTier1SchemeTransactionDto(TierOneSchemeTransactionDto tierOneSchemeTransactionDto) {
        this.tierOneSchemeTransactionDto = tierOneSchemeTransactionDto;
    }

    public TierTwoSchemeTransactionDto getTier2SchemeTransactionDto() {
        return tierTwoSchemeTransactionDto;
    }

    public void setTier2SchemeTransactionDto(TierTwoSchemeTransactionDto tierTwoSchemeTransactionDto) {
        this.tierTwoSchemeTransactionDto = tierTwoSchemeTransactionDto;
    }

    public TierOneInvestmentTransactionDto getTier1InvestmentTransactionDto() {
        return tierOneInvestmentTransactionDto;
    }

    public void setTier1InvestmentTransactionDto(TierOneInvestmentTransactionDto tierOneInvestmentTransactionDto) {
        this.tierOneInvestmentTransactionDto = tierOneInvestmentTransactionDto;
    }

    public TierTwoInvestmentTransactionDto getTier2InvestmentTransactionDto() {
        return tierTwoInvestmentTransactionDto;
    }

    public void setTier2InvestmentTransactionDto(TierTwoInvestmentTransactionDto tierTwoInvestmentTransactionDto) {
        this.tierTwoInvestmentTransactionDto = tierTwoInvestmentTransactionDto;
    }

    public String getFegoCategory() {
        return fegoCategory;
    }

    public void setFegoCategory(String fegoCategory) {
        this.fegoCategory = fegoCategory;
    }

    public String getFegoSubCategory() {
        return fegoSubCategory;
    }

    public void setFegoSubCategory(String fegoSubCategory) {
        this.fegoSubCategory = fegoSubCategory;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getFegoTransactionType() {
        return fegoTransactionType;
    }

    public void setFegoTransactionType(String fegoTransactionType) {
        this.fegoTransactionType = fegoTransactionType;
    }

    public Boolean getFixedTransaction() {
        return isFixedTransaction;
    }

    public void setFixedTransaction(Boolean fixedTransaction) {
        isFixedTransaction = fixedTransaction;
    }

    public Boolean getPartOfBudget() {
        return isPartOfBudget;
    }

    public void setPartOfBudget(Boolean partOfBudget) {
        isPartOfBudget = partOfBudget;
    }

    public Boolean getPartOfReport() {
        return isPartOfReport;
    }

    public void setPartOfReport(Boolean partOfReport) {
        isPartOfReport = partOfReport;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getMaskedAccountNumber() {
        return maskedAccountNumber;
    }

    public void setMaskedAccountNumber(String maskedAccountNumber) {
        this.maskedAccountNumber = maskedAccountNumber;
    }

    public Boolean getIsUserUpdated() {
        return isUserUpdated;
    }

    public void setIsUserUpdated(Boolean userUpdated) {
        isUserUpdated = userUpdated;
    }
}