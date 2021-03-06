/*
 * This file is generated by jOOQ.
 */
package com.example.yourtubebackend.jooq;


import com.example.yourtubebackend.jooq.tables.SystemUser;
import com.example.yourtubebackend.jooq.tables.Video;

import java.util.Arrays;
import java.util.List;

import org.jooq.Catalog;
import org.jooq.Sequence;
import org.jooq.Table;
import org.jooq.impl.SchemaImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Public extends SchemaImpl {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public</code>
     */
    public static final Public PUBLIC = new Public();

    /**
     * The table <code>public.system_user</code>.
     */
    public final SystemUser SYSTEM_USER = SystemUser.SYSTEM_USER;

    /**
     * The table <code>public.video</code>.
     */
    public final Video VIDEO = Video.VIDEO;

    /**
     * No further instances allowed
     */
    private Public() {
        super("public", null);
    }


    @Override
    public Catalog getCatalog() {
        return DefaultCatalog.DEFAULT_CATALOG;
    }

    @Override
    public final List<Sequence<?>> getSequences() {
        return Arrays.<Sequence<?>>asList(
            Sequences.SYSTEM_USER_ID_SEQ,
            Sequences.VIDEO_ID_SEQ);
    }

    @Override
    public final List<Table<?>> getTables() {
        return Arrays.<Table<?>>asList(
            SystemUser.SYSTEM_USER,
            Video.VIDEO);
    }
}
