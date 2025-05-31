package com.example.app_tareas

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.text.style.ForegroundColorSpan
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.prolificinteractive.materialcalendarview.CalendarDay

class TodayDecorator(private val today: CalendarDay) : DayViewDecorator {
    override fun shouldDecorate(day: CalendarDay): Boolean = day == today

    override fun decorate(view: DayViewFacade) {
        // Cambiar color del texto del día actual
        view.addSpan(ForegroundColorSpan(Color.WHITE))

        // Fondo circular personalizado para el día actual
        val drawable = GradientDrawable()
        drawable.shape = GradientDrawable.OVAL
        drawable.setColor(Color.parseColor("#44c686")) // Verde personalizado
        drawable.setSize(80, 80)

        view.setBackgroundDrawable(drawable)
    }
}
