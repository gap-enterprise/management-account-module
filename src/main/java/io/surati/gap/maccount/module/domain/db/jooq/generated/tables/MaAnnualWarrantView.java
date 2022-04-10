/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.maccount.module.domain.db.jooq.generated.tables;


import io.surati.gap.maccount.module.domain.db.jooq.generated.Public;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.records.MaAnnualWarrantViewRecord;

import java.time.LocalDate;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class MaAnnualWarrantView extends TableImpl<MaAnnualWarrantViewRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.ma_annual_warrant_view</code>
     */
    public static final MaAnnualWarrantView MA_ANNUAL_WARRANT_VIEW = new MaAnnualWarrantView();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MaAnnualWarrantViewRecord> getRecordType() {
        return MaAnnualWarrantViewRecord.class;
    }

    /**
     * The column <code>public.ma_annual_warrant_view.no</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Long> NO = createField(DSL.name("no"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.fiscal_year</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Short> FISCAL_YEAR = createField(DSL.name("fiscal_year"), SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.annual_amount_to_pay</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Double> ANNUAL_AMOUNT_TO_PAY = createField(DSL.name("annual_amount_to_pay"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.annual_amount_paid</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Double> ANNUAL_AMOUNT_PAID = createField(DSL.name("annual_amount_paid"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.annual_amount_left</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Double> ANNUAL_AMOUNT_LEFT = createField(DSL.name("annual_amount_left"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.sub_bundle_id</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Long> SUB_BUNDLE_ID = createField(DSL.name("sub_bundle_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.is_split</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Boolean> IS_SPLIT = createField(DSL.name("is_split"), SQLDataType.BOOLEAN, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.id</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Long> ID = createField(DSL.name("id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.type_id</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> TYPE_ID = createField(DSL.name("type_id"), SQLDataType.VARCHAR(25), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.date</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, LocalDate> DATE = createField(DSL.name("date"), SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.reference</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> REFERENCE = createField(DSL.name("reference"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.internal_reference</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> INTERNAL_REFERENCE = createField(DSL.name("internal_reference"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.object</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> OBJECT = createField(DSL.name("object"), SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.place</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> PLACE = createField(DSL.name("place"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.amount</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Double> AMOUNT = createField(DSL.name("amount"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.deposit_date</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, LocalDate> DEPOSIT_DATE = createField(DSL.name("deposit_date"), SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.entry_date</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, LocalDate> ENTRY_DATE = createField(DSL.name("entry_date"), SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.beneficiary_id</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Long> BENEFICIARY_ID = createField(DSL.name("beneficiary_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.step_id</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> STEP_ID = createField(DSL.name("step_id"), SQLDataType.VARCHAR(25), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.author_id</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Long> AUTHOR_ID = createField(DSL.name("author_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.worker_id</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Long> WORKER_ID = createField(DSL.name("worker_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.status_id</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> STATUS_ID = createField(DSL.name("status_id"), SQLDataType.CLOB, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.amount_paid</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Double> AMOUNT_PAID = createField(DSL.name("amount_paid"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.amount_left</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Double> AMOUNT_LEFT = createField(DSL.name("amount_left"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.beneficiary_name</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> BENEFICIARY_NAME = createField(DSL.name("beneficiary_name"), SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.beneficiary_abbreviated</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> BENEFICIARY_ABBREVIATED = createField(DSL.name("beneficiary_abbreviated"), SQLDataType.VARCHAR(100), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.beneficiary_code</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> BENEFICIARY_CODE = createField(DSL.name("beneficiary_code"), SQLDataType.VARCHAR(50), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.gross</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Double> GROSS = createField(DSL.name("gross"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.deduction</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, Double> DEDUCTION = createField(DSL.name("deduction"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.debit_account_pec</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> DEBIT_ACCOUNT_PEC = createField(DSL.name("debit_account_pec"), SQLDataType.VARCHAR(25), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.credit_account_pec</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> CREDIT_ACCOUNT_PEC = createField(DSL.name("credit_account_pec"), SQLDataType.VARCHAR(25), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.date_pec</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, LocalDate> DATE_PEC = createField(DSL.name("date_pec"), SQLDataType.LOCALDATE, this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.title</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> TITLE = createField(DSL.name("title"), SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.section</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> SECTION = createField(DSL.name("section"), SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.chapter</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> CHAPTER = createField(DSL.name("chapter"), SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.sub_chapter</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> SUB_CHAPTER = createField(DSL.name("sub_chapter"), SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.line</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> LINE = createField(DSL.name("line"), SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.bundle</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> BUNDLE = createField(DSL.name("bundle"), SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.region</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> REGION = createField(DSL.name("region"), SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>public.ma_annual_warrant_view.imputation</code>.
     */
    public final TableField<MaAnnualWarrantViewRecord, String> IMPUTATION = createField(DSL.name("imputation"), SQLDataType.VARCHAR(50), this, "");

    private MaAnnualWarrantView(Name alias, Table<MaAnnualWarrantViewRecord> aliased) {
        this(alias, aliased, null);
    }

    private MaAnnualWarrantView(Name alias, Table<MaAnnualWarrantViewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.view("create view \"ma_annual_warrant_view\" as  SELECT row_number() OVER (PARTITION BY awr.fiscal_year) AS no,\n    awr.fiscal_year,\n    awr.annual_amount_to_pay,\n    awr.annual_amount_paid,\n    awr.annual_amount_left,\n    awr.sub_bundle_id,\n    (NOT ((wr.amount = awr.annual_amount_to_pay) AND (awr.annual_amount_left = (0)::double precision))) AS is_split,\n    wr.id,\n    wr.type_id,\n    wr.date,\n    wr.reference,\n    wr.internal_reference,\n    wr.object,\n    wr.place,\n    wr.amount,\n    wr.deposit_date,\n    wr.entry_date,\n    wr.beneficiary_id,\n    wr.step_id,\n    wr.author_id,\n    wr.worker_id,\n    wr.status_id,\n    wr.amount_paid,\n    wr.amount_left,\n    wr.beneficiary_name,\n    wr.beneficiary_abbreviated,\n    wr.beneficiary_code,\n    wr.gross,\n    wr.deduction,\n    wr.debit_account_pec,\n    wr.credit_account_pec,\n    wr.date_pec,\n    wr.title,\n    wr.section,\n    wr.chapter,\n    wr.sub_chapter,\n    wr.line,\n    wr.bundle,\n    wr.region,\n    wr.imputation\n   FROM (ma_annual_warrant awr\n     LEFT JOIN gtp_warrant_view wr ON ((wr.id = awr.warrant_id)))\n  ORDER BY wr.id;"));
    }

    /**
     * Create an aliased <code>public.ma_annual_warrant_view</code> table reference
     */
    public MaAnnualWarrantView(String alias) {
        this(DSL.name(alias), MA_ANNUAL_WARRANT_VIEW);
    }

    /**
     * Create an aliased <code>public.ma_annual_warrant_view</code> table reference
     */
    public MaAnnualWarrantView(Name alias) {
        this(alias, MA_ANNUAL_WARRANT_VIEW);
    }

    /**
     * Create a <code>public.ma_annual_warrant_view</code> table reference
     */
    public MaAnnualWarrantView() {
        this(DSL.name("ma_annual_warrant_view"), null);
    }

    public <O extends Record> MaAnnualWarrantView(Table<O> child, ForeignKey<O, MaAnnualWarrantViewRecord> key) {
        super(child, key, MA_ANNUAL_WARRANT_VIEW);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public MaAnnualWarrantView as(String alias) {
        return new MaAnnualWarrantView(DSL.name(alias), this);
    }

    @Override
    public MaAnnualWarrantView as(Name alias) {
        return new MaAnnualWarrantView(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MaAnnualWarrantView rename(String name) {
        return new MaAnnualWarrantView(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MaAnnualWarrantView rename(Name name) {
        return new MaAnnualWarrantView(name, null);
    }
}
