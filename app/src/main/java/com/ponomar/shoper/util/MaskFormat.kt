package com.ponomar.shoper.util

import android.widget.EditText
import android.widget.TextView
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.slots.Slot
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class MaskFormat {

    companion object{
        const val PHONE_MASK = "+7 (___)-___-__-__"

        fun installOn(textView: TextView,mask:String){
            val slots = UnderscoreDigitSlotsParser().parseSlots(mask)
            val formatWatcher = MaskFormatWatcher(MaskImpl.createTerminated(slots))
            formatWatcher.installOn(textView)
        }
    }


}