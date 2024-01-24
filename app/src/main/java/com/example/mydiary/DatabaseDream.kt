import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseDream(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "mydreams.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "mytable"
        const val COLUMN_ID = "_id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_SUBTITLE = "subtitle"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Создание таблицы
        db.execSQL("CREATE TABLE IF NOT EXISTS $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $COLUMN_TITLE TEXT," +
                " $COLUMN_SUBTITLE TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
         db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
         onCreate(db)
    }

    // Добавление новой записи
    fun addDream(title: String, subtitle: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, title)
            put(COLUMN_SUBTITLE, subtitle)
        }

        // Вставляем новую запись и возвращаем её ID
        return db.insert(TABLE_NAME, null, values)
    }

    // Получение записи по ID
    @SuppressLint("Range")
    fun getDreamById(dreamId: Long): Dream? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            arrayOf(COLUMN_ID, COLUMN_TITLE, COLUMN_SUBTITLE),
            "$COLUMN_ID = ?",
            arrayOf(dreamId.toString()),
            null,
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val id = cursor.getLong(cursor.getColumnIndex(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE))
            val subtitle = cursor.getString(cursor.getColumnIndex(COLUMN_SUBTITLE))
            Dream(id, title, subtitle)
        } else {
            null
        }
    }

    // Обновление записи
    fun updateDreams(dream: Dream): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, dream.title)
            put(COLUMN_SUBTITLE, dream.subtitle)
        }

        // Обновляем запись и возвращаем количество обновленных строк
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(dream.id.toString()))
    }

    data class Dream(val id: Long, val title: String, val subtitle: String)
}
