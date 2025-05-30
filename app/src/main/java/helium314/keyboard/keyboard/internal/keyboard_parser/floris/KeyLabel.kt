package helium314.keyboard.keyboard.internal.keyboard_parser.floris

import helium314.keyboard.keyboard.internal.KeyboardParams
import helium314.keyboard.latin.utils.ToolbarKey
import helium314.keyboard.latin.utils.toolbarKeyStrings

/** labels for functional / special keys */
object KeyLabel {
    const val COM = "com"
    const val LANGUAGE_SWITCH = "language_switch"
    const val ACTION = "action"
    const val DELETE = "delete"
    const val SHIFT = "shift"
    const val NUMPAD = "numpad"
    const val SYMBOL = "symbol"
    const val ALPHA = "alpha"
    const val SYMBOL_ALPHA = "symbol_alpha"
    const val PERIOD = "period"
    const val COMMA = "comma"
    const val SPACE = "space"
    const val ZWNJ = "zwnj"
    const val CURRENCY = "$$$"
    const val CURRENCY1 = "$$$1"
    const val CURRENCY2 = "$$$2"
    const val CURRENCY3 = "$$$3"
    const val CURRENCY4 = "$$$4"
    const val CURRENCY5 = "$$$5"
    const val CTRL = "ctrl"
    const val ALT = "alt"
    const val FN = "fn"
    const val META = "meta"
    const val TAB = "tab"
    const val ESCAPE = "esc"
    const val TIMESTAMP = "timestamp"

    /** to make sure a FlorisBoard label works when reading a JSON layout */
    // resulting special labels should be names of FunctionalKey enum, case insensitive
    fun String.convertFlorisLabel(): String = when (this) {
        "view_characters" -> ALPHA
        "view_symbols" -> SYMBOL
        "view_numeric_advanced" -> NUMPAD
        "view_phone" -> ALPHA // phone keyboard is treated like alphabet, just with different layout
        "view_phone2" -> SYMBOL // phone symbols
        "ime_ui_mode_media" -> toolbarKeyStrings[ToolbarKey.EMOJI]!!
        "ime_ui_mode_clipboard" -> toolbarKeyStrings[ToolbarKey.CLIPBOARD]!!
        "ime_ui_mode_text" -> ALPHA
        "currency_slot_1" -> CURRENCY
        "currency_slot_2" -> CURRENCY1
        "currency_slot_3" -> CURRENCY2
        "currency_slot_4" -> CURRENCY3
        "currency_slot_5" -> CURRENCY4
        "currency_slot_6" -> CURRENCY5
        "enter" -> ACTION
        "half_space" -> ZWNJ
        else -> this
    }

    fun String.rtlLabel(params: KeyboardParams): String {
        if (!params.mId.mSubtype.isRtlSubtype || params.mId.isNumberLayout) return this
        return when (this) {
            "{" -> "{|}"
            "}" -> "}|{"
            "(" -> "(|)"
            ")" -> ")|("
            "[" -> "[|]"
            "]" -> "]|["
            "<" -> "<|>"
            ">" -> ">|<"
            "≤" -> "≤|≥"
            "≥" -> "≥|≤"
            "«" -> "«|»"
            "»" -> "»|«"
            "‹" -> "‹|›"
            "›" -> "›|‹"
            "﴾" -> "﴾|﴿"
            "﴿" -> "﴿|﴾"
            else -> this
        }
    }

}
