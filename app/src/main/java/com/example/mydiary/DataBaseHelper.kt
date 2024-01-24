package com.example.mydiary
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


data class DataBaseHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "DBDiary"
        private const val DATABASE_VERSION = 10
        private const val TABLE_NAME = "table_name"

        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_SURNAME = "surname"
        private const val COLUMN_NICKNAME = "nickname"
        private const val COLUMN_BIRTHDAY = "birthday"
        private const val COLUMN_STUDY = "study"
        private const val COLUMN_ZODIAC = "zodiac"
        private const val COLUMN_HOBBI = "hobbi"
        private const val COLUMN_SOCIAL = "social"
        private const val COLUMN_BLOGGERS = "bloggers"
        private const val COLUMN_LOVETEXT = "lovetext"
        private const val COLUMN_DREAM = "dream"

        private const val DIARY_TABLE_NAME = "diary_table"
        private const val DIARY_COLUMN_ID = "diary_id"
        private const val DIARY_COLUMN_TITLE = "diary_title"
        private const val DIARY_COLUMN_CONTENT = "diary_content"
        private const val DIARY_COLUMN_DATE = "diary_date"

        private const val HEALTH_TABLE_NAME = "health_table"
        private const val HEALTH_COLUMN_ID = "health_id"
        private const val HEALTH_COLUMN_DATE = "health_date"
        private const val HEALTH_COLUMN_WATER = "health_water"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME " +
                "($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT, " +
                "$COLUMN_SURNAME TEXT, " +
                "$COLUMN_NICKNAME TEXT, " +
                "$COLUMN_BIRTHDAY TEXT, " +
                "$COLUMN_STUDY TEXT, " +
                "$COLUMN_ZODIAC TEXT, " +
                "$COLUMN_HOBBI TEXT, " +
                "$COLUMN_SOCIAL TEXT, " +
                "$COLUMN_BLOGGERS TEXT, " +
                "$COLUMN_LOVETEXT TEXT, " +
                "$COLUMN_DREAM TEXT)"

        db?.execSQL(createTableQuery)

        val createDiaryTableQuery = "CREATE TABLE $DIARY_TABLE_NAME " +
                "($DIARY_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$DIARY_COLUMN_TITLE TEXT, " +
                "$DIARY_COLUMN_CONTENT TEXT, " +
                "$DIARY_COLUMN_DATE TEXT)"

        db?.execSQL(createDiaryTableQuery)

        val createHealthTableQuery = "CREATE TABLE $HEALTH_TABLE_NAME " +
                "($HEALTH_COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$HEALTH_COLUMN_DATE TEXT, " +
                "$HEALTH_COLUMN_WATER TEXT)"

        db?.execSQL(createHealthTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $DIARY_TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $HEALTH_TABLE_NAME")
        onCreate(db)
    }

// сохранение данных для анкет
    fun insertData(profile: ProfileData): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_NAME, profile.name)
        contentValues.put(COLUMN_SURNAME, profile.surname)
        contentValues.put(COLUMN_NICKNAME, profile.nickname)
        contentValues.put(COLUMN_BIRTHDAY, profile.birthday)
        contentValues.put(COLUMN_STUDY, profile.study)
        contentValues.put(COLUMN_ZODIAC, profile.zodiac)
        contentValues.put(COLUMN_HOBBI, profile.hobbi)
        contentValues.put(COLUMN_SOCIAL, profile.social)
        contentValues.put(COLUMN_BLOGGERS, profile.loveBloggers)
        contentValues.put(COLUMN_LOVETEXT, profile.loveText)
        contentValues.put(COLUMN_DREAM, profile.dream)

        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()

        return result
    }
// №№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// получение информации из базы данных о всех заполненных полях
    @SuppressLint("Range")
    fun getAllProfiles(): List<ProfileData> {
        val profileList = mutableListOf<ProfileData>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val profile = ProfileData(
                    id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME)),
                    surname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME)),
                    nickname = cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME)),
                    birthday = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY)),
                    study = cursor.getString(cursor.getColumnIndex(COLUMN_STUDY)),
                    zodiac = cursor.getString(cursor.getColumnIndex(COLUMN_ZODIAC)),
                    hobbi = cursor.getString(cursor.getColumnIndex(COLUMN_HOBBI)),
                    social = cursor.getString(cursor.getColumnIndex(COLUMN_SOCIAL)),
                    loveBloggers = cursor.getString(cursor.getColumnIndex(COLUMN_BLOGGERS)),
                    loveText = cursor.getString(cursor.getColumnIndex(COLUMN_LOVETEXT)),
                    dream = cursor.getString(cursor.getColumnIndex(COLUMN_DREAM))
                )
                profileList.add(profile)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return profileList
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// получение только имени из анкеты
    @SuppressLint("Range")
    fun getAllProfileNames(): List<String> {
        val nameList = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT $COLUMN_NAME FROM $TABLE_NAME", null)

        if (cursor.moveToFirst()) {
            do {
                val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
                nameList.add(name)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()

        return nameList
    }
// №№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№3

// получение профиля по его id
    @SuppressLint("Range")
    fun getProfileById(profileId: String): ProfileData {
        val db = this.readableDatabase
        val cursor: Cursor
        try {
            cursor = db.query(
                TABLE_NAME,
                arrayOf(
                    COLUMN_ID,
                    COLUMN_NAME,
                    COLUMN_SURNAME,
                    COLUMN_NICKNAME,
                    COLUMN_BIRTHDAY,
                    COLUMN_STUDY,
                    COLUMN_ZODIAC,
                    COLUMN_HOBBI,
                    COLUMN_SOCIAL,
                    COLUMN_BLOGGERS,
                    COLUMN_LOVETEXT,
                    COLUMN_DREAM
                ),
                "$COLUMN_ID = ?",
                arrayOf(profileId),
                null,
                null,
                null,
                null
            )
        } catch (e: SQLiteException) {
            db.close()
            throw e
        }

        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndex(COLUMN_NAME))
            val surname = cursor.getString(cursor.getColumnIndex(COLUMN_SURNAME))
            val nickname = cursor.getString(cursor.getColumnIndex(COLUMN_NICKNAME))
            val birthday = cursor.getString(cursor.getColumnIndex(COLUMN_BIRTHDAY))
            val study = cursor.getString(cursor.getColumnIndex(COLUMN_STUDY))
            val zodiac = cursor.getString(cursor.getColumnIndex(COLUMN_ZODIAC))
            val hobbi = cursor.getString(cursor.getColumnIndex(COLUMN_HOBBI))
            val social = cursor.getString(cursor.getColumnIndex(COLUMN_SOCIAL))
            val bloggers = cursor.getString(cursor.getColumnIndex(COLUMN_BLOGGERS))
            val lovetext = cursor.getString(cursor.getColumnIndex(COLUMN_LOVETEXT))
            val dream = cursor.getString(cursor.getColumnIndex(COLUMN_DREAM))

            cursor.close()
            db.close()

            return ProfileData(id, name, surname, nickname, birthday, study, zodiac, hobbi, social, bloggers, lovetext, dream)
        } else {
            // Если профиль с таким ID не найден, возвращаем пустой профиль
            cursor.close()
            db.close()
            return ProfileData(0, "", "", "", "", "", "", "","", "","", "")
        }
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№3

// удаление анкеты из базы данных
    fun deleteProfile(profileId: String): Int {
        val db = this.writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(profileId))
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// обновление анкет
    fun updateData(profile: ProfileData): Int {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(COLUMN_NAME, profile.name)
        contentValues.put(COLUMN_SURNAME, profile.surname)
        contentValues.put(COLUMN_NICKNAME, profile.nickname)
        contentValues.put(COLUMN_BIRTHDAY, profile.birthday)
        contentValues.put(COLUMN_STUDY, profile.study)
        contentValues.put(COLUMN_ZODIAC, profile.zodiac)
        contentValues.put(COLUMN_HOBBI, profile.hobbi)
        contentValues.put(COLUMN_SOCIAL, profile.social)
        contentValues.put(COLUMN_BLOGGERS, profile.loveBloggers)
        contentValues.put(COLUMN_LOVETEXT, profile.loveText)
        contentValues.put(COLUMN_DREAM, profile.dream)

        val result = db.update(TABLE_NAME, contentValues, "$COLUMN_ID=?", arrayOf(profile.id.toString()))
        db.close()

        return result
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// сохранение данных для дневника
    fun insertDiaryEntry(title: String, content: String): Long {
    val currentTime = System.currentTimeMillis()
    val dateFormat = SimpleDateFormat("yyyy.MM.dd HH:mm:ss", Locale.getDefault())
    val currentDate = dateFormat.format(currentTime)

    val values = ContentValues().apply {
        put(DIARY_COLUMN_TITLE, title)
        put(DIARY_COLUMN_CONTENT, content)
        put(DIARY_COLUMN_DATE, currentDate)
    }

    val db = this.writableDatabase
    return db.insert(DIARY_TABLE_NAME, null, values)
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// получение записей дневника
    @SuppressLint("Range")
    fun getAllDiaryEntries(): List<DiaryData> {
        val entries = mutableListOf<DiaryData>()
        val query = "SELECT * FROM $DIARY_TABLE_NAME"
        val cursor = readableDatabase.rawQuery(query, null)

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DIARY_COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndex(DIARY_COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndex(DIARY_COLUMN_CONTENT))
            val date = cursor.getString(cursor.getColumnIndex(DIARY_COLUMN_DATE))

            entries.add(DiaryData(id, title, content, date))
        }

        cursor.close()
        return entries
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// получение записи дневника по id
    @SuppressLint("Range")
    fun getDiaryEntryById(profileId: String): DiaryData? {
        val db = this.readableDatabase
        var entry: DiaryData? = null

        val cursor = db.query(
            DIARY_TABLE_NAME,
            arrayOf(DIARY_COLUMN_ID, DIARY_COLUMN_TITLE, DIARY_COLUMN_CONTENT, DIARY_COLUMN_DATE),
            "$DIARY_COLUMN_ID = ?",
            arrayOf(profileId),
            null,
            null,
            null
        )

        cursor.use {
            if (it.moveToFirst()) {
                val entryId = it.getInt(it.getColumnIndex(DIARY_COLUMN_ID))
                val title = it.getString(it.getColumnIndex(DIARY_COLUMN_TITLE))
                val content = it.getString(it.getColumnIndex(DIARY_COLUMN_CONTENT))
                val date = it.getString(it.getColumnIndex(DIARY_COLUMN_DATE))

                entry = DiaryData(entryId, title, content, date)
            }
        }
        return entry
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// получение записи дневника по дате
    @SuppressLint("Range")
    fun getDiaryEntriesByDate(selectedDate: String): List<DiaryData> {
    val db = this.readableDatabase
        val entries = mutableListOf<DiaryData>()
        val query = "SELECT * FROM $DIARY_TABLE_NAME WHERE $DIARY_COLUMN_DATE = ?"
        val cursor = db.rawQuery(query, arrayOf(selectedDate))

        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(DIARY_COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndex(DIARY_COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndex(DIARY_COLUMN_CONTENT))
            val entryDate = cursor.getString(cursor.getColumnIndex(DIARY_COLUMN_DATE))

            val entry = DiaryData(id, title,content, entryDate)
            entries.add(entry)
        }
        cursor.close()
        db.close()
        return entries
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// удаление записи дневника
    fun deleteDiaryEntry(diaryEntryId: Int): Boolean {
        val db = writableDatabase
        val whereClause = "$DIARY_COLUMN_ID = ?"
        val whereArgs = arrayOf(diaryEntryId.toString())

        return try {
            db.delete(DIARY_TABLE_NAME, whereClause, whereArgs) > 0
        } catch (e: Exception) {
            // Обработка ошибки, если произошла
            false
        } finally {
            db.close()
        }
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// обновление данных дневника
    fun updateDiaryEntry(entryId: Int, newTitle: String, newContent: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(DIARY_COLUMN_TITLE, newTitle)
        contentValues.put(DIARY_COLUMN_CONTENT, newContent)

        val whereClause = "$DIARY_COLUMN_ID = ?"
        val whereArgs = arrayOf(entryId.toString())

        val rowsAffected = db.update(DIARY_TABLE_NAME, contentValues, whereClause, whereArgs)
        db.close()

        return rowsAffected > 0
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№33

// сохранение данных для здоровья
    fun insertHealthEntry(date: String, water: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(HEALTH_COLUMN_DATE, date)
        contentValues.put(HEALTH_COLUMN_WATER, water)

        val result = db.insert(HEALTH_TABLE_NAME, null, contentValues)
        db.close()

        return result
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№

// Метод для получения всех записей о здоровье из базы данных
    @SuppressLint("Range")
    fun getAllHealthEntries(): List<HealthData> {
        val entries = mutableListOf<HealthData>()
        val db = readableDatabase
        val cursor = db.query(
            HEALTH_TABLE_NAME,
            arrayOf(HEALTH_COLUMN_ID, HEALTH_COLUMN_DATE, HEALTH_COLUMN_WATER),
            null,
            null,
            null,
            null,
            null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndex(HEALTH_COLUMN_ID))
                val date = it.getString(it.getColumnIndex(HEALTH_COLUMN_DATE))
                val waterIntake = it.getString(it.getColumnIndex(HEALTH_COLUMN_WATER))

                entries.add(HealthData(id, date, waterIntake))
            }
        }
        return entries
    }
//№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№№
}