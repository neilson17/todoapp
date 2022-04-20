package com.ubaya.s160419037todoapp.util

import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ubaya.s160419037todoapp.model.TodoDatabase

val DB_NAME ="newtododb"

fun buildDb (context:Context) = Room.databaseBuilder(context, TodoDatabase::class.java, DB_NAME)
    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_1_3)
    .build()

val MIGRATION_1_2 = object: Migration (1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 NOT NULL")
    }
}

val MIGRATION_2_3 = object: Migration (2, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        // Menggunakan INTEGER karena di dalam SQLite tidak menyediakan tipe penyimpanan BOOLEAN.
        // Nilai tipe penyimpanan BOOLEAN disimpan sebagai INTEGER dengan nilai 0 (false) dan 1 (true).
        // Sumber: https://www.sqlite.org/datatype3.html

        // Hal ini juga serupa pada MySQL dimana untuk menyimpan tipe data boolean dapat menggunakan tipe data TINYINT atau INT dengan nilai 0 (false) dan 1 (true).
        // Meskipun di PhpMyAdmin ketika membuat kolom baru terdapat tipe data boolean, sebenarnya tipe data tersebut hanya menggantikan TINYINT dengan nilai 0 dan 1.

        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL")
    }
}

val MIGRATION_1_3 = object: Migration (1, 3) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL("ALTER TABLE todo ADD COLUMN priority INTEGER DEFAULT 3 NOT NULL")
        database.execSQL("ALTER TABLE todo ADD COLUMN is_done INTEGER DEFAULT 0 NOT NULL")
    }
}