/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.maccount.module.domain.db.jooq.generated;


import org.jooq.Sequence;
import org.jooq.impl.Internal;
import org.jooq.impl.SQLDataType;


/**
 * Convenience access to all sequences in public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Sequences {

    /**
     * The sequence <code>public.ma_sub_bundle_id_seq</code>
     */
    public static final Sequence<Long> MA_SUB_BUNDLE_ID_SEQ = Internal.createSequence("ma_sub_bundle_id_seq", Public.PUBLIC, SQLDataType.BIGINT.nullable(false), null, null, null, null, false, null);
}
