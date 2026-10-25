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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.NavCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.PageBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.saint.R
import br.com.paroquiasantacabrini.app.feature.saint.ui.model.CabriniFact
import br.com.paroquiasantacabrini.app.feature.saint.ui.model.CabriniFacts
import br.com.paroquiasantacabrini.app.feature.saint.ui.model.CabriniSources
import br.com.paroquiasantacabrini.app.feature.saint.ui.model.CabriniTimeline

@Composable
internal fun SaintCabriniScreen(
    onLinkClick: (String) -> Unit,
    onVisitCommunityClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_saint_cabrini_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_saint_cabrini_eyebrow),
        onBackClick = onBackClick,
    ) {
        PageBanner(
            imageRes = ParoquiaImages.SantaCabrini,
            contentDescription = stringResource(R.string.feature_saint_cabrini_banner_description),
            height = HERO_HEIGHT,
        )
        Text(
            text = stringResource(R.string.feature_saint_illustrative_note),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            color = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ) {
            Text(
                text = stringResource(R.string.feature_saint_memorial),
                modifier = Modifier.padding(horizontal = TemplateSpacing.medium, vertical = TemplateSpacing.small),
                style = MaterialTheme.typography.labelLarge,
            )
        }
        SectionHeading(text = stringResource(R.string.feature_saint_cabrini_name))
        Text(
            text = stringResource(R.string.feature_saint_cabrini_intro),
            style = MaterialTheme.typography.bodyLarge,
        )
        FactsGrid()
        SectionHeading(text = stringResource(R.string.feature_saint_timeline_title))
        PageBanner(
            imageRes = ParoquiaImages.CabriniMission,
            contentDescription = stringResource(R.string.feature_saint_mission_banner_description),
        )
        CabriniTimeline.forEach { entry ->
            Column(verticalArrangement = Arrangement.spacedBy(TemplateSpacing.extraSmall)) {
                Text(
                    text = stringResource(entry.titleRes),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
                Text(text = stringResource(entry.textRes), style = MaterialTheme.typography.bodyLarge)
            }
        }
        LegacyCard()
        SectionHeading(text = stringResource(R.string.feature_saint_more_title))
        CabriniSources.forEach { source ->
            NavCard(
                title = stringResource(source.titleRes),
                description = stringResource(source.descriptionRes),
                emoji = "↗",
                onClick = { onLinkClick(source.url) },
            )
        }
        Button(onClick = onVisitCommunityClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_saint_visit_community))
        }
        Callout(text = stringResource(R.string.feature_saint_demo_note))
    }
}

@Composable
private fun FactsGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small)) {
        CabriniFacts.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small)) {
                row.forEach { fact -> FactCard(fact = fact, modifier = Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun FactCard(
    fact: CabriniFact,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
    ) {
        Column(modifier = Modifier.padding(TemplateSpacing.medium)) {
            Text(text = stringResource(fact.valueRes), style = MaterialTheme.typography.titleLarge)
            Text(text = stringResource(fact.labelRes), style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
private fun LegacyCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
        ) {
            Text(
                text = stringResource(R.string.feature_saint_legacy_eyebrow),
                style = MaterialTheme.typography.labelMedium,
            )
            Text(
                text = stringResource(R.string.feature_saint_legacy_text),
                style = MaterialTheme.typography.titleMedium,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SaintCabriniScreenPreview() {
    ParoquiaTheme {
        SaintCabriniScreen(onLinkClick = {}, onVisitCommunityClick = {}, onBackClick = {})
    }
}

private val HERO_HEIGHT = 200.dp
