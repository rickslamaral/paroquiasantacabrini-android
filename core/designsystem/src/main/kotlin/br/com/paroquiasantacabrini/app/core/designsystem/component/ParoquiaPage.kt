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

package br.com.paroquiasantacabrini.app.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.R
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing

/**
 * A scrolling screen with an optional back button, an eyebrow, a heading and header [actions].
 * Content is centered with a readable maximum width and kept clear of the system bars.
 */
@Composable
fun ParoquiaPage(
    title: String,
    modifier: Modifier = Modifier,
    eyebrow: String? = null,
    onBackClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable ColumnScope.() -> Unit,
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
                    .widthIn(max = PAGE_MAX_WIDTH)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = TemplateSpacing.medium, vertical = TemplateSpacing.small),
                verticalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
            ) {
                PageHeader(title = title, eyebrow = eyebrow, onBackClick = onBackClick, actions = actions)
                content()
            }
        }
    }
}

@Composable
private fun PageHeader(
    title: String,
    eyebrow: String?,
    onBackClick: (() -> Unit)?,
    actions: @Composable RowScope.() -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
    ) {
        if (onBackClick != null) {
            val backDescription = stringResource(R.string.core_designsystem_back)
            IconButton(
                onClick = onBackClick,
                modifier = Modifier.semantics { contentDescription = backDescription },
            ) {
                Text(
                    text = "‹",
                    modifier = Modifier.clearAndSetSemantics {},
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
        Column(modifier = Modifier.weight(1f)) {
            if (eyebrow != null) {
                Text(
                    text = eyebrow,
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
            Text(
                text = title,
                modifier = Modifier.semantics { heading() },
                style = MaterialTheme.typography.headlineMedium,
            )
        }
        actions()
    }
}

/** A tappable card with a decorative emoji, a title, a description and a chevron. */
@Composable
fun NavCard(
    title: String,
    description: String,
    emoji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
        ),
    ) {
        Row(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
        ) {
            Surface(
                modifier = Modifier.size(EMOJI_BOX_SIZE),
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.primaryContainer,
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = emoji,
                        modifier = Modifier.clearAndSetSemantics {},
                        style = MaterialTheme.typography.titleLarge,
                    )
                }
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(text = description, style = MaterialTheme.typography.bodyMedium)
            }
            Text(
                text = "›",
                modifier = Modifier.clearAndSetSemantics {},
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
            )
        }
    }
}

/** A soft note for disclaimers and demo-content warnings. */
@Composable
fun Callout(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.tertiaryContainer,
        contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(TemplateSpacing.medium),
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}

/** A rounded, cropped banner image. Pass `null` for [contentDescription] when decorative. */
@Composable
fun PageBanner(
    @DrawableRes imageRes: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    height: Dp = BANNER_HEIGHT,
) {
    Image(
        painter = painterResource(imageRes),
        contentDescription = contentDescription,
        modifier = modifier
            .fillMaxWidth()
            .height(height)
            .clip(MaterialTheme.shapes.large),
        contentScale = ContentScale.Crop,
    )
}

/** A section heading used inside pages. */
@Composable
fun SectionHeading(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        modifier = modifier.semantics { heading() },
        style = MaterialTheme.typography.titleLarge,
    )
}

private val PAGE_MAX_WIDTH = 560.dp
private val EMOJI_BOX_SIZE = 48.dp
private val BANNER_HEIGHT = 150.dp
