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

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateBackground
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.massschedule.R
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.components.ContactSection
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.components.LabeledValue
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.components.ScheduleSection
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.model.MassScheduleUiState

@Composable
internal fun MassScheduleScreen(
    state: MassScheduleUiState,
    onActionClick: (uri: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TemplateBackground(modifier = modifier) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding(),
            contentAlignment = Alignment.TopCenter,
        ) {
            Column(
                modifier = Modifier
                    .widthIn(max = MAX_CONTENT_WIDTH)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(TemplateSpacing.medium),
                verticalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
            ) {
                Image(
                    painter = painterResource(R.drawable.feature_massschedule_church),
                    // Decorative: the heading below already names the screen.
                    contentDescription = null,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .height(ILLUSTRATION_HEIGHT),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.primary),
                )
                Text(
                    text = stringResource(R.string.feature_massschedule_title),
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { heading() },
                    style = MaterialTheme.typography.headlineMedium,
                )
                CelebrationsSection(state)
                ConfessionsSection()
                OfficeHoursSection(state)
                ContactSection(contact = state.contact, onActionClick = onActionClick)
            }
        }
    }
}

@Composable
private fun CelebrationsSection(state: MassScheduleUiState) {
    ScheduleSection(title = stringResource(R.string.feature_massschedule_celebrations_title)) {
        state.celebrations.forEach { celebration ->
            LabeledValue(
                label = stringResource(celebration.labelRes),
                value = celebration.times.joinToString(", "),
            )
        }
        LabeledValue(
            label = stringResource(R.string.feature_massschedule_adoration_label),
            value = state.adorationTimes.joinToString(", "),
        )
    }
}

@Composable
private fun ConfessionsSection() {
    ScheduleSection(title = stringResource(R.string.feature_massschedule_confessions_title)) {
        Text(
            text = stringResource(R.string.feature_massschedule_confessions_text),
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

@Composable
private fun OfficeHoursSection(state: MassScheduleUiState) {
    ScheduleSection(title = stringResource(R.string.feature_massschedule_office_title)) {
        state.officeHours.forEach { hours ->
            LabeledValue(
                label = stringResource(hours.labelRes),
                value = stringResource(
                    R.string.feature_massschedule_time_range,
                    hours.opens,
                    hours.closes,
                ),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MassScheduleScreenPreview() {
    ParoquiaTheme {
        MassScheduleScreen(
            state = MassScheduleUiState.forParish(),
            onActionClick = {},
        )
    }
}

private val MAX_CONTENT_WIDTH = 560.dp
private val ILLUSTRATION_HEIGHT = 120.dp
