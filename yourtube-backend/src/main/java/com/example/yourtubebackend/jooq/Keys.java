/*
 * This file is generated by jOOQ.
 */
package com.example.yourtubebackend.jooq;


import com.example.yourtubebackend.jooq.tables.SystemUser;
import com.example.yourtubebackend.jooq.tables.Video;
import com.example.yourtubebackend.jooq.tables.records.SystemUserRecord;
import com.example.yourtubebackend.jooq.tables.records.VideoRecord;

import org.jooq.ForeignKey;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.Internal;


/**
 * A class modelling foreign key relationships and constraints of tables in 
 * public.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

    // -------------------------------------------------------------------------
    // UNIQUE and PRIMARY KEY definitions
    // -------------------------------------------------------------------------

    public static final UniqueKey<SystemUserRecord> SYSTEM_USER_NAME_KEY = Internal.createUniqueKey(SystemUser.SYSTEM_USER, DSL.name("system_user_name_key"), new TableField[] { SystemUser.SYSTEM_USER.NAME }, true);
    public static final UniqueKey<SystemUserRecord> SYSTEM_USER_PKEY = Internal.createUniqueKey(SystemUser.SYSTEM_USER, DSL.name("system_user_pkey"), new TableField[] { SystemUser.SYSTEM_USER.ID }, true);
    public static final UniqueKey<VideoRecord> VIDEO_FILENAME_KEY = Internal.createUniqueKey(Video.VIDEO, DSL.name("video_filename_key"), new TableField[] { Video.VIDEO.FILENAME }, true);
    public static final UniqueKey<VideoRecord> VIDEO_PKEY = Internal.createUniqueKey(Video.VIDEO, DSL.name("video_pkey"), new TableField[] { Video.VIDEO.ID }, true);

    // -------------------------------------------------------------------------
    // FOREIGN KEY definitions
    // -------------------------------------------------------------------------

    public static final ForeignKey<VideoRecord, SystemUserRecord> VIDEO__VIDEO_SYSTEM_USER_ID_FKEY = Internal.createForeignKey(Video.VIDEO, DSL.name("video_system_user_id_fkey"), new TableField[] { Video.VIDEO.SYSTEM_USER_ID }, Keys.SYSTEM_USER_PKEY, new TableField[] { SystemUser.SYSTEM_USER.ID }, true);
}
