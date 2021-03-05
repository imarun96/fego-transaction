package com.fego.transaction.common.constants;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collector;

public final class Constants {

    // Symbols
    public static final String HYPHEN = "-";
    public static final String COLON = ":";
    public static final String COMMA = ",";
    public static final String NEW_LINE = "\n";
    // Common
    public static final String EMPTY = "";
    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";
    public static final String OK = "OK";
    public static final String PROFILE_ACTIVE_PRODUCTION = "prod";
    public static final String SCHEMA = "PUBLIC";
    public static final String BUCKET_NAME = "bucketName";
    public static final String BUCKET_BASE_URL = "bucket-base-URL";
    public static final String DEFAULT_IMAGE = "DEFAULT.png";
    public static final String ACTIVE_ACCOUNT_STATUS = "ACTIVE";
    public static final String BASE_URL = "baseURL";
    public static final BigDecimal ONE = new BigDecimal(1);
    public static final BigDecimal TWO = new BigDecimal(2);
    public static final BigDecimal THREE = new BigDecimal(3);
    public static final BigDecimal FIVE = new BigDecimal(5);
    public static final BigDecimal SEVEN = new BigDecimal(7);
    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);
    public static final BigDecimal THOUSAND = new BigDecimal(1000);
    public static final BigDecimal THIRTY = new BigDecimal(30);
    public static final BigDecimal FIFTY = new BigDecimal(50);
    public static final BigDecimal EIGHTY = new BigDecimal(80);
    public static final Collector<BigDecimal, BigDecimal[], BigDecimal> avgCollector = Collector.of(
            () -> new BigDecimal[]{BigDecimal.ZERO, BigDecimal.ZERO},
            (pair, val) -> {
                pair[0] = pair[0].add(val);
                pair[1] = pair[1].add(BigDecimal.ONE);
            },
            (pair1, pair2) -> new BigDecimal[]{pair1[0].add(pair2[0]), pair1[1].add(pair2[1])},
            pair -> pair[0].divide(pair[1], 2, RoundingMode.HALF_UP)
    );
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.ENGLISH)
            .withZone(ZoneId.systemDefault());
    public static final String CREDIT_CARD = "credit_card";
    public static final String SAVINGS = "SAVINGS";
    public static final String CURRENT = "CURRENT";
    public static final String MONTH = "MONTH";
    public static final String WEEK = "WEEK";
    public static final String INCOME = "Income";
    public static final String EXPENSE = "Expense";
    public static final String RETIREMENT_INCOME = "Retirement Income";
    public static final String SALARY = "Salary";
    public static final String RECORD_NOT_FOUND = "Record not found";
    public static final String CONSENT_ID = "consentId";
    public static final String IS_ACCOUNT_LINKED = "isAccountLinked";
    public static final String USER_ID = "userId";
    public static final String IS_COMPLETED = "isCompleted";
    public static final String RULES = "rules";
    public static final String RULE_ID = "ruleId";
    public static final String GOAL_ID = "goalId";
    public static final String VALUE_DATE = "valueDate";
    public static final String TRANSACTION_DATE = "transactionDate";
    public static final List<String> ACCOUNT_TYPES = List.of(CREDIT_CARD, CURRENT, SAVINGS);
    public static final List<String> FEGO_TRANSACTION_TYPES = List.of(INCOME, EXPENSE);
    public static final String SORT_BY = "sortBy";
    public static final String PAGE_NUMBER = "pageNumber";
    public static final String PAGE_SIZE = "pageSize";
    public static final String DIRECTION = "direction";
    public static final String JUST_START_SAVING = "Just Start Saving";
    public static final String START_OF_DAY = "T00:00:00.000Z";
    public static final String END_OF_DAY = "T23:59:59.999Z";

    private Constants() {

    }
}