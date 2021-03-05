package com.fego.transaction.dto.integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Arun Balaji Rajasekaran
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractClausesDto {

    private ContractClauseDto contractClause;

    public ContractClauseDto getContractClause() {
        return contractClause;
    }

    public void setContractClause(ContractClauseDto contractClause) {
        this.contractClause = contractClause;
    }
}