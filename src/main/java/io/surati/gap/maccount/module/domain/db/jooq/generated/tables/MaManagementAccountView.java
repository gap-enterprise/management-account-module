/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.maccount.module.domain.db.jooq.generated.tables;


import io.surati.gap.maccount.module.domain.db.jooq.generated.Public;
import io.surati.gap.maccount.module.domain.db.jooq.generated.tables.records.MaManagementAccountViewRecord;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row7;
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
public class MaManagementAccountView extends TableImpl<MaManagementAccountViewRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.ma_management_account_view</code>
     */
    public static final MaManagementAccountView MA_MANAGEMENT_ACCOUNT_VIEW = new MaManagementAccountView();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<MaManagementAccountViewRecord> getRecordType() {
        return MaManagementAccountViewRecord.class;
    }

    /**
     * The column <code>public.ma_management_account_view.treasury_id</code>.
     */
    public final TableField<MaManagementAccountViewRecord, Long> TREASURY_ID = createField(DSL.name("treasury_id"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.ma_management_account_view.fiscal_year</code>.
     */
    public final TableField<MaManagementAccountViewRecord, Short> FISCAL_YEAR = createField(DSL.name("fiscal_year"), SQLDataType.SMALLINT, this, "");

    /**
     * The column <code>public.ma_management_account_view.section_code</code>.
     */
    public final TableField<MaManagementAccountViewRecord, String> SECTION_CODE = createField(DSL.name("section_code"), SQLDataType.VARCHAR(10), this, "");

    /**
     * The column <code>public.ma_management_account_view.section_name</code>.
     */
    public final TableField<MaManagementAccountViewRecord, String> SECTION_NAME = createField(DSL.name("section_name"), SQLDataType.VARCHAR(200), this, "");

    /**
     * The column <code>public.ma_management_account_view.amount_in_digits</code>.
     */
    public final TableField<MaManagementAccountViewRecord, Double> AMOUNT_IN_DIGITS = createField(DSL.name("amount_in_digits"), SQLDataType.DOUBLE, this, "");

    /**
     * The column <code>public.ma_management_account_view.number_of_bundles</code>.
     */
    public final TableField<MaManagementAccountViewRecord, Long> NUMBER_OF_BUNDLES = createField(DSL.name("number_of_bundles"), SQLDataType.BIGINT, this, "");

    /**
     * The column <code>public.ma_management_account_view.amount_on_parts</code>.
     */
    public final TableField<MaManagementAccountViewRecord, Double> AMOUNT_ON_PARTS = createField(DSL.name("amount_on_parts"), SQLDataType.DOUBLE, this, "");

    private MaManagementAccountView(Name alias, Table<MaManagementAccountViewRecord> aliased) {
        this(alias, aliased, null);
    }

    private MaManagementAccountView(Name alias, Table<MaManagementAccountViewRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.view("create view \"ma_management_account_view\" as  SELECT awr.treasury_id,\n    awr.fiscal_year,\n    sec.code AS section_code,\n    sec.name AS section_name,\n    sum(awr.annual_amount_paid) AS amount_in_digits,\n    count(awrb.id) AS number_of_bundles,\n    sum(COALESCE(awrb.annual_amount_paid, (0)::double precision)) AS amount_on_parts\n   FROM ((gtp_annual_warrant_view awr\n     LEFT JOIN gtp_section sec ON (((sec.code)::text = (awr.section)::text)))\n     LEFT JOIN ma_warrant_bundled_view awrb ON (((awrb.id = awr.id) AND (awrb.fiscal_year = awr.fiscal_year))))\n  GROUP BY awr.treasury_id, awr.fiscal_year, sec.code, sec.name\n  ORDER BY awr.treasury_id, awr.fiscal_year, sec.code;"));
    }

    /**
     * Create an aliased <code>public.ma_management_account_view</code> table reference
     */
    public MaManagementAccountView(String alias) {
        this(DSL.name(alias), MA_MANAGEMENT_ACCOUNT_VIEW);
    }

    /**
     * Create an aliased <code>public.ma_management_account_view</code> table reference
     */
    public MaManagementAccountView(Name alias) {
        this(alias, MA_MANAGEMENT_ACCOUNT_VIEW);
    }

    /**
     * Create a <code>public.ma_management_account_view</code> table reference
     */
    public MaManagementAccountView() {
        this(DSL.name("ma_management_account_view"), null);
    }

    public <O extends Record> MaManagementAccountView(Table<O> child, ForeignKey<O, MaManagementAccountViewRecord> key) {
        super(child, key, MA_MANAGEMENT_ACCOUNT_VIEW);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public MaManagementAccountView as(String alias) {
        return new MaManagementAccountView(DSL.name(alias), this);
    }

    @Override
    public MaManagementAccountView as(Name alias) {
        return new MaManagementAccountView(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public MaManagementAccountView rename(String name) {
        return new MaManagementAccountView(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public MaManagementAccountView rename(Name name) {
        return new MaManagementAccountView(name, null);
    }

    // -------------------------------------------------------------------------
    // Row7 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, Short, String, String, Double, Long, Double> fieldsRow() {
        return (Row7) super.fieldsRow();
    }
}
