package com.example.tasks.db

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            """
                ALTER TABLE task_table
                ADD COLUMN complete INTEGER NOT NULL DEFAULT 0
            """
        )
    }
}

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {

        database.execSQL(
            """
            CREATE TABLE task_table_new (
                uid INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                description TEXT NOT NULL,
                priority INTEGER NOT NULL,
                deadline TEXT NOT NULL,
                completed INTEGER NOT NULL
            )
            """
        )

        database.execSQL(
            """
            INSERT INTO task_table_new (
                uid, title, description, priority, deadline, completed
            )
            SELECT
                uid,
                title,
                description,
                priority,
                deadline,
                `complete`
            FROM task_table
            """
        )

        database.execSQL("DROP TABLE task_table")

        database.execSQL(
            """
            ALTER TABLE task_table_new
            RENAME TO task_table
            """
        )
    }
}
