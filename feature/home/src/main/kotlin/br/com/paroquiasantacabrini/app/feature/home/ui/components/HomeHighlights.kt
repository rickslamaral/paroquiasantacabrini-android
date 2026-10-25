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

package br.com.paroquiasantacabrini.app.feature.home.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.home.R

/** The "come celebrate" card that opens the Mass times. */
@Composable
internal fun MassCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Row(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
        ) {
            Text(
                text = "✝",
                modifier = Modifier.clearAndSetSemantics {},
                style = MaterialTheme.typography.headlineMedium,
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.feature_home_mass_card_eyebrow),
                    style = MaterialTheme.typography.labelMedium,
                )
                Text(
                    text = stringResource(R.string.feature_home_mass_card_title),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(R.string.feature_home_mass_card_text),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

/** An illustrated banner that opens the guided Mass Mode. White text sits on a dark scrim. */
@Composable
internal fun MassModeBanner(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(BANNER_HEIGHT)
            .clip(MaterialTheme.shapes.large)
            .clickable(role = Role.Button, onClick = onClick)
            .semantics(mergeDescendants = true) {},
    ) {
        Image(
            painter = painterResource(ParoquiaImages.MassBanner),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Brush.verticalGradient(listOf(Color.Transparent, SCRIM_COLOR))),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(TemplateSpacing.medium),
        ) {
            Text(
                text = stringResource(R.string.feature_home_massmode_banner_eyebrow),
                style = MaterialTheme.typography.labelMedium,
                color = Color.White,
            )
            Text(
                text = stringResource(R.string.feature_home_massmode_banner_title),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
            )
            Text(
                text = stringResource(R.string.feature_home_massmode_banner_text),
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
            )
        }
    }
}

/** The patroness card with her illustrated portrait, opening her page. */
@Composable
internal fun PatronessCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
    ) {
        Row(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
        ) {
            Image(
                painter = painterResource(ParoquiaImages.SantaCabrini),
                contentDescription = stringResource(R.string.feature_home_patroness_image),
                modifier = Modifier
                    .size(PORTRAIT_SIZE)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.feature_home_patroness_name),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(R.string.feature_home_patroness_text),
                    style = MaterialTheme.typography.bodyMedium,
                )
            }
        }
    }
}

/** A section heading with an optional action on the right, e.g. "See all". */
@Composable
internal fun SectionTitleWithAction(
    text: String,
    actionLabel: String,
    onActionClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = text,
            modifier = Modifier
                .weight(1f)
                .semantics { heading() },
            style = MaterialTheme.typography.titleLarge,
        )
        TextButton(onClick = onActionClick) { Text(actionLabel) }
    }
}

private val BANNER_HEIGHT = 150.dp
private val PORTRAIT_SIZE = 72.dp
private val SCRIM_COLOR = Color(0xCC000000)
