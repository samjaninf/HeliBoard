package helium314.keyboard.latin.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.SharedPreferences
import androidx.activity.ComponentActivity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.withLink

// generic extension functions

// adapted from Kotlin source: https://github.com/JetBrains/kotlin/blob/7a7d392b3470b38d42f80c896b7270678d0f95c3/libraries/stdlib/common/src/generated/_Collections.kt#L3004
inline fun <T> Iterable<T>.sumOf(selector: (T) -> Float): Float {
    var sum = 0f
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun CharSequence.getStringResourceOrName(prefix: String, context: Context): String {
    val resId = context.resources.getIdentifier(prefix + this, "string", context.packageName)
    return if (resId == 0) this.toString() else context.getString(resId)
}

/**
 *  Splits the collection into a pair of lists on the first match of [condition], discarding the element first matching the condition.
 *  If [condition] is not met, all elements are in the first list.
 */
fun <T> Collection<T>.splitAt(condition: (T) -> Boolean): Pair<MutableList<T>, MutableList<T>> {
    var conditionMet = false
    val first = mutableListOf<T>()
    val second = mutableListOf<T>()
    forEach {
        if (conditionMet) {
            second.add(it)
        } else {
            conditionMet = condition(it)
            if (!conditionMet)
                first.add(it)
        }
    }
    return first to second
}

// like plus, but for nullable collections
fun <T> addCollections(a: Collection<T>?, b: Collection<T>?): Collection<T>? {
    if (a.isNullOrEmpty()) return b
    if (b.isNullOrEmpty()) return a
    return a + b
}

fun <T> MutableList<T>.removeFirst(predicate: (T) -> Boolean) {
    val i = indexOfFirst(predicate)
    if (i >= 0) removeAt(i)
}

fun <T> MutableList<T>.replaceFirst(predicate: (T) -> Boolean, with: (T) -> T) {
    val i = indexOfFirst(predicate)
    if (i >= 0) this[i] = with(this[i])
}

fun Context.getActivity(): ComponentActivity? {
    val componentActivity = when (this) {
        is ComponentActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }
    return componentActivity
}

/** SharedPreferences from deviceProtectedContext, which are accessible even without unlocking.
 *  They should not be used to store sensitive data! */
fun Context.prefs(): SharedPreferences = DeviceProtectedUtils.getSharedPreferences(this)

/** The "default" preferences that are only accessible after the device has been unlocked. */
fun Context.protectedPrefs(): SharedPreferences = getSharedPreferences("${packageName}_preferences", Context.MODE_PRIVATE)

@Composable
fun AnnotatedString.Builder.appendLink(text: String, url: String) =
    withLink(
        LinkAnnotation.Url(
        url,
        styles = TextLinkStyles(style = SpanStyle(color = MaterialTheme.colorScheme.primary))
    )) {
        append(text)
    }
