/*
 * Designed and developed by 2026 ashtanko (Oleksii Shtanko)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package br.com.paroquiasantacabrini.app.feature.community.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.ScheduleRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateOutlinedButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.community.R
import br.com.paroquiasantacabrini.app.feature.community.ui.model.CalendarEvent
import br.com.paroquiasantacabrini.app.feature.community.ui.model.ParishCalendar
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale

private const val DAYS_IN_WEEK = 7

@Composable
internal fun CalendarScreen(
    month: YearMonth,
    selected: LocalDate,
    today: LocalDate,
    onDayClick: (LocalDate) -> Unit,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
    onAllMassTimesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val locale = LocalConfiguration.current.locales[0]
    ParoquiaPage(
        title = stringResource(R.string.feature_community_calendar_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_community_calendar_eyebrow),
    ) {
        Text(
            text = stringResource(R.string.feature_community_calendar_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        MonthCard(
            month = month,
            selected = selected,
            today = today,
            locale = locale,
            onDayClick = onDayClick,
            onPreviousMonthClick = onPreviousMonthClick,
            onNextMonthClick = onNextMonthClick,
        )
        SectionHeading(text = selectedDateLabel(selected, locale))
        DayEvents(events = ParishCalendar.eventsFor(selected))
        TemplateOutlinedButton(onClick = onAllMassTimesClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_community_calendar_all_times))
        }
        Callout(text = stringResource(R.string.feature_community_calendar_note))
    }
}

@Composable
private fun MonthCard(
    month: YearMonth,
    selected: LocalDate,
    today: LocalDate,
    locale: Locale,
    onDayClick: (LocalDate) -> Unit,
    onPreviousMonthClick: () -> Unit,
    onNextMonthClick: () -> Unit,
) {
    ParoquiaCard {
        Column(verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                MonthButton(
                    glyph = stringResource(R.string.feature_community_calendar_previous_glyph),
                    description = stringResource(R.string.feature_community_calendar_previous_month),
                    onClick = onPreviousMonthClick,
                )
                Text(
                    text = monthTitle(month, locale),
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.titleSmall,
                )
                MonthButton(
                    glyph = stringResource(R.string.feature_community_calendar_next_glyph),
                    description = stringResource(R.string.feature_community_calendar_next_month),
                    onClick = onNextMonthClick,
                )
            }
            WeekdayHeader(locale)
            ParishCalendar.monthCells(month).chunked(DAYS_IN_WEEK).forEach { week ->
                Row {
                    week.forEach { date ->
                        DayCell(
                            date = date,
                            isSelected = date == selected,
                            isToday = date == today,
                            locale = locale,
                            onClick = onDayClick,
                            modifier = Modifier.weight(1f),
                        )
                    }
                    repeat(DAYS_IN_WEEK - week.size) { Box(modifier = Modifier.weight(1f)) }
                }
            }
            Row(horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium)) {
                Text(
                    text = stringResource(R.string.feature_community_calendar_legend_events),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, letterSpacing = 0.sp, fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = stringResource(R.string.feature_community_calendar_legend_today),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, letterSpacing = 0.sp, fontWeight = FontWeight.Normal),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

@Composable
private fun WeekdayHeader(locale: Locale) {
    Row {
        SUNDAY_FIRST.forEach { day ->
            Text(
                text = day.getDisplayName(TextStyle.NARROW_STANDALONE, locale),
                modifier = Modifier
                    .weight(1f)
                    .clearAndSetSemantics {},
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 0.sp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@Composable
private fun DayCell(
    date: LocalDate?,
    isSelected: Boolean,
    isToday: Boolean,
    locale: Locale,
    onClick: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (date == null) {
        Box(modifier = modifier.aspectRatio(1f))
        return
    }
    val fullDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(locale).format(date)
    val description = if (ParishCalendar.hasEvents(date)) {
        stringResource(R.string.feature_community_calendar_day_with_events, fullDate)
    } else {
        fullDate
    }
    val scheme = MaterialTheme.colorScheme
    Box(
        modifier = modifier
            .aspectRatio(1f)
            .padding(DAY_PADDING)
            .clip(DaySelectionShape)
            .background(if (isSelected) scheme.primary else Color.Transparent)
            .then(if (isToday && !isSelected) Modifier.border(BorderStroke(1.dp, scheme.primary), DaySelectionShape) else Modifier)
            .clickable(role = Role.Button) { onClick(date) }
            .semantics {
                contentDescription = description
                this.selected = isSelected
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            modifier = Modifier.clearAndSetSemantics {},
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
            color = if (isSelected) scheme.onPrimary else scheme.onSurface,
        )
        if (ParishCalendar.hasEvents(date)) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = DOT_BOTTOM_PADDING)
                    .size(DOT_SIZE)
                    .clip(CircleShape)
                    .background(if (isSelected) scheme.surfaceContainer else scheme.tertiary),
            )
        }
    }
}

@Composable
private fun DayEvents(events: List<CalendarEvent>) {
    ParoquiaCard {
        Column {
            if (events.isEmpty()) {
                Text(
                    text = stringResource(R.string.feature_community_calendar_no_events),
                    modifier = Modifier.padding(vertical = TemplateSpacing.small),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            events.forEachIndexed { index, event ->
                ScheduleRow(
                    time = event.timeRes?.let { stringResource(it) } ?: event.time.orEmpty(),
                    title = stringResource(event.titleRes),
                    subtitle = stringResource(R.string.feature_community_calendar_event_place),
                    showDivider = index < events.lastIndex,
                    timeMinWidth = EVENT_TIME_WIDTH,
                )
            }
        }
    }
}

@Composable
private fun MonthButton(
    glyph: String,
    description: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(MONTH_BUTTON_SIZE)
            .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant), CircleShape)
            .clip(CircleShape)
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { contentDescription = description },
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = glyph,
            modifier = Modifier.clearAndSetSemantics {},
            style = MaterialTheme.typography.titleLarge,
        )
    }
}

@Composable
private fun monthTitle(month: YearMonth, locale: Locale): String {
    val name = month.month.getDisplayName(TextStyle.FULL_STANDALONE, locale)
    return stringResource(
        R.string.feature_community_calendar_month_format,
        name.replaceFirstChar { it.titlecase(locale) },
        month.year,
    )
}

@Composable
private fun selectedDateLabel(date: LocalDate, locale: Locale): String =
    DateTimeFormatter.ofPattern(stringResource(R.string.feature_community_calendar_date_pattern), locale).format(date)

private val SUNDAY_FIRST = listOf(
    DayOfWeek.SUNDAY,
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY,
)
private val DAY_PADDING = 2.dp
private val DaySelectionShape = RoundedCornerShape(12.dp)
private val MONTH_BUTTON_SIZE = 36.dp
private val EVENT_TIME_WIDTH = 104.dp
private val DOT_BOTTOM_PADDING = 4.dp
private val DOT_SIZE = 4.dp

@Preview(showBackground = true)
@Composable
private fun CalendarScreenPreview() {
    val today = LocalDate.of(2026, 10, 25)
    ParoquiaTheme {
        CalendarScreen(
            month = YearMonth.from(today),
            selected = today,
            today = today,
            onDayClick = {},
            onPreviousMonthClick = {},
            onNextMonthClick = {},
            onAllMassTimesClick = {},
        )
    }
}
