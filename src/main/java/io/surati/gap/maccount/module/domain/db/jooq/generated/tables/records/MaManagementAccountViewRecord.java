/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.maccount.module.domain.db.jooq.generated.tables.records;


import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.MaManagementAccountView;

import org.jooq.Field;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.TableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MaManagementAccountViewRecord extends TableRecordImpl<MaManagementAccountViewRecord> implements Record7<Long, Short, String, String, Double, Long, Double> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.ma_management_account_view.treasury_id</code>.
     */
    public void setTreasuryId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.ma_management_account_view.treasury_id</code>.
     */
    public Long getTreasuryId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.ma_management_account_view.fiscal_year</code>.
     */
    public void setFiscalYear(Short value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.ma_management_account_view.fiscal_year</code>.
     */
    public Short getFiscalYear() {
        return (Short) get(1);
    }

    /**
     * Setter for <code>public.ma_management_account_view.section_code</code>.
     */
    public void setSectionCode(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.ma_management_account_view.section_code</code>.
     */
    public String getSectionCode() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.ma_management_account_view.section_name</code>.
     */
    public void setSectionName(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.ma_management_account_view.section_name</code>.
     */
    public String getSectionName() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.ma_management_account_view.amount_in_digits</code>.
     */
    public void setAmountInDigits(Double value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.ma_management_account_view.amount_in_digits</code>.
     */
    public Double getAmountInDigits() {
        return (Double) get(4);
    }

    /**
     * Setter for <code>public.ma_management_account_view.number_of_bundles</code>.
     */
    public void setNumberOfBundles(Long value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.ma_management_account_view.number_of_bundles</code>.
     */
    public Long getNumberOfBundles() {
        return (Long) get(5);
    }

    /**
     * Setter for <code>public.ma_management_account_view.amount_on_parts</code>.
     */
    public void setAmountOnParts(Double value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.ma_management_account_view.amount_on_parts</code>.
     */
    public Double getAmountOnParts() {
        return (Double) get(6);
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Short, String, String, Double, Long, Double> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, Short, String, String, Double, Long, Double> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.TREASURY_ID;
    }

    @Override
    public Field<Short> field2() {
        return MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.FISCAL_YEAR;
    }

    @Override
    public Field<String> field3() {
        return MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.SECTION_CODE;
    }

    @Override
    public Field<String> field4() {
        return MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.SECTION_NAME;
    }

    @Override
    public Field<Double> field5() {
        return MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.AMOUNT_IN_DIGITS;
    }

    @Override
    public Field<Long> field6() {
        return MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.NUMBER_OF_BUNDLES;
    }

    @Override
    public Field<Double> field7() {
        return MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW.AMOUNT_ON_PARTS;
    }

    @Override
    public Long component1() {
        return getTreasuryId();
    }

    @Override
    public Short component2() {
        return getFiscalYear();
    }

    @Override
    public String component3() {
        return getSectionCode();
    }

    @Override
    public String component4() {
        return getSectionName();
    }

    @Override
    public Double component5() {
        return getAmountInDigits();
    }

    @Override
    public Long component6() {
        return getNumberOfBundles();
    }

    @Override
    public Double component7() {
        return getAmountOnParts();
    }

    @Override
    public Long value1() {
        return getTreasuryId();
    }

    @Override
    public Short value2() {
        return getFiscalYear();
    }

    @Override
    public String value3() {
        return getSectionCode();
    }

    @Override
    public String value4() {
        return getSectionName();
    }

    @Override
    public Double value5() {
        return getAmountInDigits();
    }

    @Override
    public Long value6() {
        return getNumberOfBundles();
    }

    @Override
    public Double value7() {
        return getAmountOnParts();
    }

    @Override
    public MaManagementAccountViewRecord value1(Long value) {
        setTreasuryId(value);
        return this;
    }

    @Override
    public MaManagementAccountViewRecord value2(Short value) {
        setFiscalYear(value);
        return this;
    }

    @Override
    public MaManagementAccountViewRecord value3(String value) {
        setSectionCode(value);
        return this;
    }

    @Override
    public MaManagementAccountViewRecord value4(String value) {
        setSectionName(value);
        return this;
    }

    @Override
    public MaManagementAccountViewRecord value5(Double value) {
        setAmountInDigits(value);
        return this;
    }

    @Override
    public MaManagementAccountViewRecord value6(Long value) {
        setNumberOfBundles(value);
        return this;
    }

    @Override
    public MaManagementAccountViewRecord value7(Double value) {
        setAmountOnParts(value);
        return this;
    }

    @Override
    public MaManagementAccountViewRecord values(Long value1, Short value2, String value3, String value4, Double value5, Long value6, Double value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached MaManagementAccountViewRecord
     */
    public MaManagementAccountViewRecord() {
        super(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW);
    }

    /**
     * Create a detached, initialised MaManagementAccountViewRecord
     */
    public MaManagementAccountViewRecord(Long treasuryId, Short fiscalYear, String sectionCode, String sectionName, Double amountInDigits, Long numberOfBundles, Double amountOnParts) {
        super(MaManagementAccountView.MA_MANAGEMENT_ACCOUNT_VIEW);

        setTreasuryId(treasuryId);
        setFiscalYear(fiscalYear);
        setSectionCode(sectionCode);
        setSectionName(sectionName);
        setAmountInDigits(amountInDigits);
        setNumberOfBundles(numberOfBundles);
        setAmountOnParts(amountOnParts);
    }
}
