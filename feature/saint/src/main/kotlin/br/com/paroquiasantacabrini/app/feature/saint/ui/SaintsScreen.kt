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

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.FeatureCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaTonalButton
import br.com.paroquiasantacabrini.app.core.designsystem.component.RoundIcon
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors
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
        Text(
            text = stringResource(R.string.feature_saint_search_label),
            style = MaterialTheme.typography.titleSmall,
        )
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.small,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceContainerLowest,
                unfocusedBorderColor = MaterialTheme.colorScheme.outlineVariant,
            ),
            placeholder = { Text(stringResource(R.string.feature_saint_search_hint)) },
            singleLine = true,
        )
        if (results.isEmpty()) {
            Text(
                text = stringResource(R.string.feature_saint_empty),
                style = MaterialTheme.typography.bodyMedium,
            )
        } else {
            ParoquiaCard(contentPadding = PaddingValues(vertical = 6.dp, horizontal = 12.dp)) {
                results.forEach { saint ->
                    SaintResultRow(
                        name = saint.name,
                        description = stringResource(
                            if (saint.isPatroness) R.string.feature_saint_result_patroness else R.string.feature_saint_result_demo,
                        ),
                        glyph = if (saint.isPatroness) "✧" else "✝",
                        onClick = { onSaintClick(saint) },
                    )
                }
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
    FeatureCard(
        eyebrow = stringResource(R.string.feature_saint_today_eyebrow),
        title = stringResource(R.string.feature_saint_today_title),
        text = stringResource(R.string.feature_saint_today_text),
        containerColor = MaterialTheme.extraColors.cream,
    ) {
        ParoquiaTonalButton(onClick = onSeeCabriniClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_saint_see_cabrini))
        }
    }
}

/** A saint in the directory: round icon, name, short description and a chevron (`saint-result`). */
@Composable
private fun SaintResultRow(
    name: String,
    description: String,
    glyph: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        RoundIcon(glyph = glyph)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = name, style = MaterialTheme.typography.titleSmall)
            Text(
                text = description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Text(
            text = "›",
            modifier = Modifier.clearAndSetSemantics {},
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Composable
private fun DemoDetail(name: String) {
    ParoquiaCard {
        Text(
            text = stringResource(R.string.feature_saint_detail_eyebrow).uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(text = name, style = MaterialTheme.typography.titleMedium)
        Text(
            text = stringResource(R.string.feature_saint_detail_text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
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
