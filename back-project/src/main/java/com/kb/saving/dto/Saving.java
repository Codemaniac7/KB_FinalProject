    package com.kb.saving.dto;


    import com.kb.bank.dto.Bank;
    import lombok.AllArgsConstructor;
    import lombok.Builder;
    import lombok.Data;
    import lombok.NoArgsConstructor;

    import java.util.Date;
    import java.util.List;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public class Saving {
        private long savingId;
        private int rank;
        private String bankId;
        private String financialProductCode;
        private String savingName;
        private Date disclosureStartDay;
        private int joinDeny;
        private String joinMember;
        private int financialCategoryId;
        private String joinWay;
        private String maturityInterest;
        private String note;
        private double maxPrimeRate;

        private Bank bank;
        private List<SavingInterestRate> interestRateList;
        private List<SavingPrimeRate> primeRatesList;
        private List<String> joinWays;
    }
