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

package br.com.paroquiasantacabrini.app.feature.saint.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.NavCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.saint.R
import br.com.paroquiasantacabrini.app.feature.saint.ui.model.SaintEntry
import br.com.paroquiasantacabrini.app.feature.saint.ui.model.searchSaints

@Composable
internal fun SaintsScreen(
    query: String,
    selectedSaint: SaintEntry?,
    onQueryChange: (String) -> Unit,
    onSaintClick: (SaintEntry) -> Unit,
    onSeeCabriniClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val results = searchSaints(query)
    ParoquiaPage(
        title = stringResource(R.string.feature_saint_saints_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_saint_saints_eyebrow),
        onBackClick = onBackClick,
    ) {
        TodayCard(onSeeCabriniClick = onSeeCabriniClick)
        SectionHeading(text = stringResource(R.string.feature_saint_directory_title))
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.feature_saint_search_label)) },
            placeholder = { Text(stringResource(R.string.feature_saint_search_hint)) },
            singleLine = true,
        )
        if (results.isEmpty()) {
            Text(
                text = stringResource(R.string.feature_saint_empty),
                style = MaterialTheme.typography.bodyLarge,
            )
        } else {
            results.forEach { saint ->
                NavCard(
                    title = saint.name,
                    description = stringResource(
                        if (saint.isPatroness) R.string.feature_saint_result_patroness else R.string.feature_saint_result_demo,
                    ),
                    emoji = if (saint.isPatroness) "✧" else "✝",
                    onClick = { onSaintClick(saint) },
                )
            }
        }
        if (selectedSaint != null && !selectedSaint.isPatroness) {
            DemoDetail(name = selectedSaint.name)
        }
        Callout(text = stringResource(R.string.feature_saint_directory_note))
    }
}

@Composable
private fun TodayCard(onSeeCabriniClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
        ) {
            Text(
                text = stringResource(R.string.feature_saint_today_eyebrow),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(R.string.feature_saint_today_title),
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = stringResource(R.string.feature_saint_today_text),
                style = MaterialTheme.typography.bodyMedium,
            )
            Button(onClick = onSeeCabriniClick) {
                Text(stringResource(R.string.feature_saint_see_cabrini))
            }
        }
    }
}

@Composable
private fun DemoDetail(name: String) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
        ) {
            Text(
                text = stringResource(R.string.feature_saint_detail_eyebrow),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(text = name, style = MaterialTheme.typography.titleMedium)
            Text(
                text = stringResource(R.string.feature_saint_detail_text),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SaintsScreenPreview() {
    ParoquiaTheme {
        SaintsScreen(
            query = "",
            selectedSaint = null,
            onQueryChange = {},
            onSaintClick = {},
            onSeeCabriniClick = {},
            onBackClick = {},
        )
    }
}
