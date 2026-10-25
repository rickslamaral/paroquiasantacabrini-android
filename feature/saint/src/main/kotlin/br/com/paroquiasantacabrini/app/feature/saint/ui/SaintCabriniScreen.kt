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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.InfoRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.PageBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.Pill
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors
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
            imageRes = ParoquiaImages.CabriniMission,
            contentDescription = stringResource(R.string.feature_saint_mission_banner_description),
            height = HERO_HEIGHT,
        )
        Pill(text = stringResource(R.string.feature_saint_memorial))
        SectionHeading(text = stringResource(R.string.feature_saint_cabrini_name))
        Text(
            text = stringResource(R.string.feature_saint_cabrini_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        FactsGrid()
        SectionHeading(text = stringResource(R.string.feature_saint_timeline_title))
        Column {
            CabriniTimeline.forEachIndexed { index, entry ->
                TimelineItem(
                    title = stringResource(entry.titleRes),
                    text = stringResource(entry.textRes),
                    isLast = index == CabriniTimeline.lastIndex,
                )
            }
        }
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
        LegacyCard()
        SectionHeading(text = stringResource(R.string.feature_saint_more_title))
        ParoquiaCard {
            Column {
                CabriniSources.forEachIndexed { index, source ->
                    InfoRow(
                        glyph = "↗",
                        title = stringResource(source.titleRes),
                        text = stringResource(source.descriptionRes),
                        modifier = Modifier.clickable(role = Role.Button) { onLinkClick(source.url) },
                        showDivider = index < CabriniSources.lastIndex,
                    )
                }
            }
        }
        TemplateButton(onClick = onVisitCommunityClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_saint_visit_community))
        }
        Callout(text = stringResource(R.string.feature_saint_demo_note))
    }
}

@Composable
private fun FactsGrid() {
    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        CabriniFacts.chunked(2).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
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
    ParoquiaCard(
        modifier = modifier,
        shape = MaterialTheme.shapes.medium,
        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 12.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(fact.valueRes),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(
                text = stringResource(fact.labelRes),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

/** A dot on a vertical line with a title and text (`timeline-item`). */
@Composable
private fun TimelineItem(
    title: String,
    text: String,
    isLast: Boolean,
) {
    val line = MaterialTheme.colorScheme.outlineVariant
    val dot = MaterialTheme.colorScheme.primary
    val halo = MaterialTheme.colorScheme.primaryContainer
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .semantics(mergeDescendants = true) {}
            .drawBehind {
                val x = DOT_RADIUS.toPx() + 1.dp.toPx()
                val top = DOT_TOP.toPx()
                if (!isLast) {
                    drawLine(line, Offset(x, top), Offset(x, size.height), strokeWidth = 2.dp.toPx())
                }
                drawCircle(halo, radius = DOT_RADIUS.toPx() + 3.dp.toPx(), center = Offset(x, top))
                drawCircle(dot, radius = DOT_RADIUS.toPx(), center = Offset(x, top))
            }
            .padding(start = TIMELINE_INDENT, bottom = 14.dp),
    ) {
        Text(text = title, style = MaterialTheme.typography.titleSmall)
        Text(
            text = text,
            modifier = Modifier.padding(top = 3.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun LegacyCard() {
    ParoquiaCard(containerColor = MaterialTheme.extraColors.cream, bordered = false) {
        Text(
            text = stringResource(R.string.feature_saint_legacy_eyebrow).uppercase(),
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(
            text = stringResource(R.string.feature_saint_legacy_text),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SaintCabriniScreenPreview() {
    ParoquiaTheme {
        SaintCabriniScreen(onLinkClick = {}, onVisitCommunityClick = {}, onBackClick = {})
    }
}

private val HERO_HEIGHT = 192.dp
private val DOT_RADIUS = 6.dp
private val DOT_TOP = 10.dp
private val TIMELINE_INDENT = 28.dp
