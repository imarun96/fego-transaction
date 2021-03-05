package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryDto {

    private BigDecimal currentBalance;
    private String currency;
    private String branch;
    private String balanceDateTime;
    @JsonProperty("currentODLimit")
    private BigDecimal currentOdLimit;
    private BigDecimal drawingLimit;
    @JsonProperty("exchgeRate")
    private String exchangeRate;
    private String facility;
    private String ifscCode;
    private String micrCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate openingDate;
    private String status;
    private String type;
    @JsonProperty("Pending")
    private PendingDto pending;
    private String ifsc;
    private String accountType;
    private BigDecimal maturityAmount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate maturityDate;
    private String description;
    private String interestPayout;
    private String interestRate;
    private BigDecimal principalAmount;
    private Integer tenureDays;
    private Integer tenureMonths;
    private Integer tenureYears;
    private String interestComputation;
    private String compoundingFrequency;
    private BigDecimal interestPeriodicPayoutAmount;
    private String interestOnMaturity;
    private String currentValue;
    private BigDecimal recurringAmount;
    private String recurringDepositDay;
    private String investmentValue;
    @JsonProperty("Investment")
    private InvestmentDto investment;
    private BigDecimal premiumAmount;
    private Integer premiumPaymentMonths;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate policyStartDate;
    private String productName;
    private String sumAssured;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate policyMaturityDate;
    private String premiumFrequency;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate nextPremiumDueDate;
    private String productDescription;
    private String premiumPaymentYears;
    @JsonProperty("Holdings")
    private HoldingsDto holdings;
    private String establishmentId;
    private String establishmentName;
    private BigDecimal employerBalance;
    private BigDecimal employeeBalance;
    private BigDecimal totalBalance;
    private BigDecimal currentDue;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastStatementDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private BigDecimal previousDueAmount;
    private BigDecimal totalDueAmount;
    private BigDecimal minDueAmount;
    private BigDecimal creditLimit;
    private BigDecimal cashLimit;
    private BigDecimal availableCredit;
    private String loyaltyPoints;
    private BigDecimal financeCharges;
    private String policyName;
    private String policyNumber;
    private String eiaNumber;
    private BigDecimal coverAmount;
    private String policyType;
    private String coverType;
    private String maturityBenefit;
    @JsonProperty("MoneyBacks")
    private MoneyBacksDto moneyBacks;
    @JsonProperty("ContractClauses")
    private ContractClausesDto contractClauses;
    @JsonProperty("Covers")
    private CoversDto covers;
    private String totalNumUnitsIssued;
    private String folioNo;
    @JsonProperty("Investments")
    private InvestmentsDto investments;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @JsonProperty("tier1NAVDate")
    private LocalDate tierOneNavDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate tierTwoNavDate;
    private String debtAssetValue;
    private String equityAssetValue;
    private String otherAssetValue;
    private String tierOneStatus;
    private String tierTwoStatus;
    @JsonProperty("SchemeChoices")
    private SchemeChoicesDto schemeChoices;

    public BigDecimal getCurrentDue() {
        return currentDue;
    }

    public void setCurrentDue(BigDecimal currentDue) {
        this.currentDue = currentDue;
    }

    public LocalDate getLastStatementDate() {
        return lastStatementDate;
    }

    public void setLastStatementDate(LocalDate lastStatementDate) {
        this.lastStatementDate = lastStatementDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public BigDecimal getPreviousDueAmount() {
        return previousDueAmount;
    }

    public void setPreviousDueAmount(BigDecimal previousDueAmount) {
        this.previousDueAmount = previousDueAmount;
    }

    public BigDecimal getTotalDueAmount() {
        return totalDueAmount;
    }

    public void setTotalDueAmount(BigDecimal totalDueAmount) {
        this.totalDueAmount = totalDueAmount;
    }

    public BigDecimal getMinDueAmount() {
        return minDueAmount;
    }

    public void setMinDueAmount(BigDecimal minDueAmount) {
        this.minDueAmount = minDueAmount;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getCashLimit() {
        return cashLimit;
    }

    public void setCashLimit(BigDecimal cashLimit) {
        this.cashLimit = cashLimit;
    }

    public BigDecimal getAvailableCredit() {
        return availableCredit;
    }

    public void setAvailableCredit(BigDecimal availableCredit) {
        this.availableCredit = availableCredit;
    }

    public String getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(String loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public BigDecimal getFinanceCharges() {
        return financeCharges;
    }

    public void setFinanceCharges(BigDecimal financeCharges) {
        this.financeCharges = financeCharges;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public String getPolicyNumber() {
        return policyNumber;
    }

    public void setPolicyNumber(String policyNumber) {
        this.policyNumber = policyNumber;
    }

    public String getEiaNumber() {
        return eiaNumber;
    }

    public void setEiaNumber(String eiaNumber) {
        this.eiaNumber = eiaNumber;
    }

    public BigDecimal getCoverAmount() {
        return coverAmount;
    }

    public void setCoverAmount(BigDecimal coverAmount) {
        this.coverAmount = coverAmount;
    }

    public String getPolicyType() {
        return policyType;
    }

    public void setPolicyType(String policyType) {
        this.policyType = policyType;
    }

    public String getCoverType() {
        return coverType;
    }

    public void setCoverType(String coverType) {
        this.coverType = coverType;
    }

    public String getMaturityBenefit() {
        return maturityBenefit;
    }

    public void setMaturityBenefit(String maturityBenefit) {
        this.maturityBenefit = maturityBenefit;
    }

    public MoneyBacksDto getMoneyBacks() {
        return moneyBacks;
    }

    public void setMoneyBacks(MoneyBacksDto moneyBacks) {
        this.moneyBacks = moneyBacks;
    }

    public ContractClausesDto getContractClauses() {
        return contractClauses;
    }

    public void setContractClauses(ContractClausesDto contractClauses) {
        this.contractClauses = contractClauses;
    }

    public CoversDto getCovers() {
        return covers;
    }

    public void setCovers(CoversDto covers) {
        this.covers = covers;
    }

    public String getTotalNumUnitsIssued() {
        return totalNumUnitsIssued;
    }

    public void setTotalNumUnitsIssued(String totalNumUnitsIssued) {
        this.totalNumUnitsIssued = totalNumUnitsIssued;
    }

    public String getFolioNo() {
        return folioNo;
    }

    public void setFolioNo(String folioNo) {
        this.folioNo = folioNo;
    }

    public InvestmentsDto getInvestments() {
        return investments;
    }

    public void setInvestments(InvestmentsDto investments) {
        this.investments = investments;
    }

    public LocalDate getTierOneNavDate() {
        return tierOneNavDate;
    }

    public void setTierOneNavDate(LocalDate tierOneNavDate) {
        this.tierOneNavDate = tierOneNavDate;
    }

    public LocalDate getTierTwoNavDate() {
        return tierTwoNavDate;
    }

    public void setTierTwoNavDate(LocalDate tierTwoNavDate) {
        this.tierTwoNavDate = tierTwoNavDate;
    }

    public String getDebtAssetValue() {
        return debtAssetValue;
    }

    public void setDebtAssetValue(String debtAssetValue) {
        this.debtAssetValue = debtAssetValue;
    }

    public String getEquityAssetValue() {
        return equityAssetValue;
    }

    public void setEquityAssetValue(String equityAssetValue) {
        this.equityAssetValue = equityAssetValue;
    }

    public String getOtherAssetValue() {
        return otherAssetValue;
    }

    public void setOtherAssetValue(String otherAssetValue) {
        this.otherAssetValue = otherAssetValue;
    }

    public String getTierOneStatus() {
        return tierOneStatus;
    }

    public void setTierOneStatus(String tierOneStatus) {
        this.tierOneStatus = tierOneStatus;
    }

    public String getTierTwoStatus() {
        return tierTwoStatus;
    }

    public void setTierTwoStatus(String tierTwoStatus) {
        this.tierTwoStatus = tierTwoStatus;
    }

    public SchemeChoicesDto getSchemeChoices() {
        return schemeChoices;
    }

    public void setSchemeChoices(SchemeChoicesDto schemeChoices) {
        this.schemeChoices = schemeChoices;
    }

    public HoldingsDto getHoldings() {
        return holdings;
    }

    public void setHoldings(HoldingsDto holdings) {
        this.holdings = holdings;
    }

    public String getEstablishmentId() {
        return establishmentId;
    }

    public void setEstablishmentId(String establishmentId) {
        this.establishmentId = establishmentId;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public BigDecimal getEmployerBalance() {
        return employerBalance;
    }

    public void setEmployerBalance(BigDecimal employerBalance) {
        this.employerBalance = employerBalance;
    }

    public BigDecimal getEmployeeBalance() {
        return employeeBalance;
    }

    public void setEmployeeBalance(BigDecimal employeeBalance) {
        this.employeeBalance = employeeBalance;
    }

    public BigDecimal getTotalBalance() {
        return totalBalance;
    }

    public void setTotalBalance(BigDecimal totalBalance) {
        this.totalBalance = totalBalance;
    }

    public BigDecimal getPremiumAmount() {
        return premiumAmount;
    }

    public void setPremiumAmount(BigDecimal premiumAmount) {
        this.premiumAmount = premiumAmount;
    }

    public Integer getPremiumPaymentMonths() {
        return premiumPaymentMonths;
    }

    public void setPremiumPaymentMonths(Integer premiumPaymentMonths) {
        this.premiumPaymentMonths = premiumPaymentMonths;
    }

    public LocalDate getPolicyStartDate() {
        return policyStartDate;
    }

    public void setPolicyStartDate(LocalDate policyStartDate) {
        this.policyStartDate = policyStartDate;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSumAssured() {
        return sumAssured;
    }

    public void setSumAssured(String sumAssured) {
        this.sumAssured = sumAssured;
    }

    public LocalDate getPolicyMaturityDate() {
        return policyMaturityDate;
    }

    public void setPolicyMaturityDate(LocalDate policyMaturityDate) {
        this.policyMaturityDate = policyMaturityDate;
    }

    public String getPremiumFrequency() {
        return premiumFrequency;
    }

    public void setPremiumFrequency(String premiumFrequency) {
        this.premiumFrequency = premiumFrequency;
    }

    public LocalDate getNextPremiumDueDate() {
        return nextPremiumDueDate;
    }

    public void setNextPremiumDueDate(LocalDate nextPremiumDueDate) {
        this.nextPremiumDueDate = nextPremiumDueDate;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public String getPremiumPaymentYears() {
        return premiumPaymentYears;
    }

    public void setPremiumPaymentYears(String premiumPaymentYears) {
        this.premiumPaymentYears = premiumPaymentYears;
    }

    public BigDecimal getRecurringAmount() {
        return recurringAmount;
    }

    public void setRecurringAmount(BigDecimal recurringAmount) {
        this.recurringAmount = recurringAmount;
    }

    public String getRecurringDepositDay() {
        return recurringDepositDay;
    }

    public void setRecurringDepositDay(String recurringDepositDay) {
        this.recurringDepositDay = recurringDepositDay;
    }

    public String getInvestmentValue() {
        return investmentValue;
    }

    public void setInvestmentValue(String investmentValue) {
        this.investmentValue = investmentValue;
    }

    public InvestmentDto getInvestment() {
        return investment;
    }

    public void setInvestment(InvestmentDto investment) {
        this.investment = investment;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getBalanceDateTime() {
        return balanceDateTime;
    }

    public void setBalanceDateTime(String balanceDateTime) {
        this.balanceDateTime = balanceDateTime;
    }

    public BigDecimal getCurrentOdLimit() {
        return currentOdLimit;
    }

    public void setCurrentOdLimit(BigDecimal currentOdLimit) {
        this.currentOdLimit = currentOdLimit;
    }

    public BigDecimal getDrawingLimit() {
        return drawingLimit;
    }

    public void setDrawingLimit(BigDecimal drawingLimit) {
        this.drawingLimit = drawingLimit;
    }

    public String getExchangeRate() {
        return exchangeRate;
    }

    public void setExchangeRate(String exchangeRate) {
        this.exchangeRate = exchangeRate;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    public String getMicrCode() {
        return micrCode;
    }

    public void setMicrCode(String micrCode) {
        this.micrCode = micrCode;
    }

    public LocalDate getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public PendingDto getPending() {
        return pending;
    }

    public void setPending(PendingDto pending) {
        this.pending = pending;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public BigDecimal getMaturityAmount() {
        return maturityAmount;
    }

    public void setMaturityAmount(BigDecimal maturityAmount) {
        this.maturityAmount = maturityAmount;
    }

    public LocalDate getMaturityDate() {
        return maturityDate;
    }

    public void setMaturityDate(LocalDate maturityDate) {
        this.maturityDate = maturityDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInterestPayout() {
        return interestPayout;
    }

    public void setInterestPayout(String interestPayout) {
        this.interestPayout = interestPayout;
    }

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public BigDecimal getPrincipalAmount() {
        return principalAmount;
    }

    public void setPrincipalAmount(BigDecimal principalAmount) {
        this.principalAmount = principalAmount;
    }

    public Integer getTenureDays() {
        return tenureDays;
    }

    public void setTenureDays(Integer tenureDays) {
        this.tenureDays = tenureDays;
    }

    public Integer getTenureMonths() {
        return tenureMonths;
    }

    public void setTenureMonths(Integer tenureMonths) {
        this.tenureMonths = tenureMonths;
    }

    public Integer getTenureYears() {
        return tenureYears;
    }

    public void setTenureYears(Integer tenureYears) {
        this.tenureYears = tenureYears;
    }

    public String getInterestComputation() {
        return interestComputation;
    }

    public void setInterestComputation(String interestComputation) {
        this.interestComputation = interestComputation;
    }

    public String getCompoundingFrequency() {
        return compoundingFrequency;
    }

    public void setCompoundingFrequency(String compoundingFrequency) {
        this.compoundingFrequency = compoundingFrequency;
    }

    public BigDecimal getInterestPeriodicPayoutAmount() {
        return interestPeriodicPayoutAmount;
    }

    public void setInterestPeriodicPayoutAmount(BigDecimal interestPeriodicPayoutAmount) {
        this.interestPeriodicPayoutAmount = interestPeriodicPayoutAmount;
    }

    public String getInterestOnMaturity() {
        return interestOnMaturity;
    }

    public void setInterestOnMaturity(String interestOnMaturity) {
        this.interestOnMaturity = interestOnMaturity;
    }

    public String getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(String currentValue) {
        this.currentValue = currentValue;
    }
}