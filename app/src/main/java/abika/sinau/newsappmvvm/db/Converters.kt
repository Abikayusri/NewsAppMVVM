package abika.sinau.newsappmvvm.db

import abika.sinau.newsappmvvm.model.Source
import androidx.room.TypeConverter

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
class Converters {

    @TypeConverter
    fun fromSource(source: Source?): String? {
        return source?.name
    }

    @TypeConverter
    fun toSource(name: String): Source {
        return Source(name, name)
    }
}