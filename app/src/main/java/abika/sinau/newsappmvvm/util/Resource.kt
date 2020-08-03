package abika.sinau.newsappmvvm.util

/**
 * Created by Abika Chairul Yusri
 * on Sunday, 28 June 2020
 * Bismillahirrahmanirrahim
 */
// TODO 7-14: Untuk membungkus response network kita dan menjadi class generic dan sangat berguna
//  untuk membedakan antara success, error, dan loading. Google telah merekomendasikan sealed class
sealed class Resource <T>(
    val data: T ?= null,
    val message: String ?= null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String?, data: T? = null) : Resource<T>(data, message)
    class Loading<T> : Resource<T>()
}