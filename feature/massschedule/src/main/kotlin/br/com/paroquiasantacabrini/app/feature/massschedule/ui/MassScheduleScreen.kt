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

package br.com.paroquiasantacabrini.app.feature.massschedule.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.ChoiceChip
import br.com.paroquiasantacabrini.app.core.designsystem.component.InfoRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.ScheduleRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.feature.massschedule.R
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.model.MassScheduleUiState

@Composable
internal fun MassScheduleScreen(
    state: MassScheduleUiState,
    initialDayIndex: Int,
    onDirectionsClick: () -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(initialDayIndex) }
    val selected = state.celebrations[selectedIndex.coerceIn(0, state.celebrations.lastIndex)]

    ParoquiaPage(
        title = stringResource(R.string.feature_massschedule_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_massschedule_eyebrow),
        onBackClick = onBackClick,
    ) {
        Text(
            text = stringResource(R.string.feature_massschedule_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            state.celebrations.forEachIndexed { index, celebration ->
                ChoiceChip(
                    label = stringResource(celebration.labelRes),
                    selected = index == selectedIndex,
                    onClick = { selectedIndex = index },
                )
            }
        }
        ParoquiaCard {
            Column {
                Text(
                    text = stringResource(selected.labelRes),
                    modifier = Modifier.padding(top = 13.dp, bottom = 7.dp),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
                selected.masses.forEachIndexed { index, mass ->
                    ScheduleRow(
                        time = mass.time,
                        title = stringResource(R.string.feature_massschedule_holy_mass),
                        subtitle = stringResource(
                            R.string.feature_massschedule_mass_subtitle,
                            stringResource(mass.weekdayRes),
                        ),
                        showDivider = index < selected.masses.lastIndex,
                    )
                }
            }
        }
        SectionHeading(text = stringResource(R.string.feature_massschedule_other_services))
        ParoquiaCard {
            Column {
                InfoRow(
                    glyph = "☼",
                    title = stringResource(R.string.feature_massschedule_adoration_title),
                    text = stringResource(R.string.feature_massschedule_adoration_text),
                )
                InfoRow(
                    glyph = "☩",
                    title = stringResource(R.string.feature_massschedule_confessions_title),
                    text = stringResource(R.string.feature_massschedule_confessions_text),
                )
                InfoRow(
                    glyph = "⌂",
                    title = stringResource(R.string.feature_massschedule_office_title),
                    text = stringResource(R.string.feature_massschedule_office_text),
                    showDivider = false,
                )
            }
        }
        TemplateButton(onClick = onDirectionsClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_massschedule_directions_button))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MassScheduleScreenPreview() {
    ParoquiaTheme {
        MassScheduleScreen(
            state = MassScheduleUiState.forParish(),
            initialDayIndex = 2,
            onDirectionsClick = {},
            onBackClick = {},
        )
    }
}
