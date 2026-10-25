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
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.R
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors

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
                    .padding(horizontal = PAGE_PADDING, vertical = TemplateSpacing.small),
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = HEADER_TOP_PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
    ) {
        if (onBackClick != null) {
            BackButton(onClick = onBackClick)
        }
        Column(modifier = Modifier.weight(1f)) {
            if (eyebrow != null) {
                Text(
                    text = eyebrow.uppercase(),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.secondary,
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

/** The prototype's round outlined back button, inside a 48 dp touch target. */
@Composable
fun BackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val backDescription = stringResource(R.string.core_designsystem_back)
    Box(
        modifier = modifier
            .size(TOUCH_TARGET)
            .clip(CircleShape)
            .clickable(role = Role.Button, onClick = onClick)
            .semantics { contentDescription = backDescription },
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier.size(BACK_BUTTON_SIZE),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.surfaceContainerLowest,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
            Box(contentAlignment = Alignment.Center) {
                Text(
                    text = "‹",
                    modifier = Modifier.clearAndSetSemantics {},
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}

/** The prototype's white card with a 1 dp warm border. Pass [onClick] to make it tappable. */
@Composable
fun ParoquiaCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainerLowest,
    bordered: Boolean = true,
    shape: Shape = MaterialTheme.shapes.large,
    contentPadding: PaddingValues = PaddingValues(CARD_PADDING),
    content: @Composable ColumnScope.() -> Unit,
) {
    val border = if (bordered) BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant) else null
    val colors = CardDefaults.cardColors(containerColor = containerColor)
    val inner: @Composable ColumnScope.() -> Unit = {
        Column(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
            content = content,
        )
    }
    if (onClick != null) {
        Card(
            onClick = onClick,
            modifier = modifier.fillMaxWidth(),
            shape = shape,
            colors = colors,
            border = border,
            content = inner,
        )
    } else {
        Card(
            modifier = modifier.fillMaxWidth(),
            shape = shape,
            colors = colors,
            border = border,
            content = inner,
        )
    }
}

/** A rounded tile holding a decorative glyph, as in the prototype's `round-icon`. */
@Composable
fun RoundIcon(
    glyph: String,
    modifier: Modifier = Modifier,
    background: Color = MaterialTheme.extraColors.cream,
    size: Dp = ROUND_ICON_SIZE,
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(MaterialTheme.shapes.small)
            .background(background),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = glyph,
            modifier = Modifier.clearAndSetSemantics {},
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface,
        )
    }
}

/** A tappable card with a round icon, a title, a description and a chevron (`split-card`). */
@Composable
fun NavCard(
    title: String,
    description: String,
    emoji: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconBackground: Color = MaterialTheme.extraColors.blue,
) {
    ParoquiaCard(modifier = modifier, onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium),
        ) {
            RoundIcon(glyph = emoji, background = iconBackground)
            Column(modifier = Modifier.weight(1f)) {
                Text(text = title, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = SMALL_TEXT),
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
}

/** A soft note for disclaimers and demo-content warnings (`callout`). */
@Composable
fun Callout(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.extraColors.cream,
        contentColor = MaterialTheme.extraColors.calloutText,
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = CALLOUT_HORIZONTAL, vertical = CALLOUT_VERTICAL),
            style = MaterialTheme.typography.bodyMedium.copy(fontSize = SMALL_TEXT, lineHeight = SMALL_LINE),
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
            .clip(MaterialTheme.shapes.medium),
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

/** A section heading with an optional green text action on the right (`section-head`). */
@Composable
fun SectionHead(
    text: String,
    modifier: Modifier = Modifier,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SectionHeading(text = text, modifier = Modifier.weight(1f))
        if (actionLabel != null && onActionClick != null) {
            TextButton(onClick = onActionClick) {
                Text(text = actionLabel, style = MaterialTheme.typography.labelLarge.copy(fontSize = SMALL_TEXT))
            }
        }
    }
}

/** A small white pill, e.g. the memorial date (`pill`). */
@Composable
fun Pill(
    text: String,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surfaceContainerLowest,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(horizontal = PILL_HORIZONTAL, vertical = PILL_VERTICAL),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

/** The prototype's slim progress bar. [progress] is between 0 and 1. */
@Composable
fun ParoquiaProgress(
    progress: Float,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(PROGRESS_HEIGHT)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.outlineVariant),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress.coerceIn(0f, 1f))
                .height(PROGRESS_HEIGHT)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary),
        )
    }
}

/** An icon, a bold title and a muted line of text, separated by a hairline (`info-row`). */
@Composable
fun InfoRow(
    glyph: String,
    title: String,
    text: String,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
) {
    Column(modifier = modifier.fillMaxWidth().semantics(mergeDescendants = true) {}) {
        Row(
            modifier = Modifier.padding(vertical = INFO_ROW_VERTICAL),
            horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium - 3.dp),
        ) {
            RoundIcon(glyph = glyph, size = INFO_ICON_SIZE)
            Column {
                Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontSize = 12.sp))
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant),
            )
        }
    }
}

/** A time in green and a bold title with a muted subtitle (`schedule-row`). */
@Composable
fun ScheduleRow(
    time: String,
    title: String,
    subtitle: String?,
    modifier: Modifier = Modifier,
    showDivider: Boolean = true,
    timeMinWidth: Dp = TIME_MIN_WIDTH,
) {
    Column(modifier = modifier.fillMaxWidth().semantics(mergeDescendants = true) {}) {
        Row(
            modifier = Modifier.padding(vertical = SCHEDULE_ROW_VERTICAL),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium - 4.dp),
        ) {
            Text(
                text = time,
                modifier = Modifier.widthIn(min = timeMinWidth),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Column {
                Text(text = title, style = MaterialTheme.typography.titleSmall.copy(fontSize = 13.sp))
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                    )
                }
            }
        }
        if (showDivider) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(MaterialTheme.colorScheme.outlineVariant),
            )
        }
    }
}

private val PAGE_MAX_WIDTH = 560.dp
private val PAGE_PADDING = 17.dp
private val HEADER_TOP_PADDING = 14.dp
private val TOUCH_TARGET = 48.dp
private val BACK_BUTTON_SIZE = 36.dp
private val CARD_PADDING = 15.dp
private val ROUND_ICON_SIZE = 42.dp
private val INFO_ICON_SIZE = 34.dp
private val BANNER_HEIGHT = 150.dp
private val CALLOUT_HORIZONTAL = 14.dp
private val CALLOUT_VERTICAL = 13.dp
private val PILL_HORIZONTAL = 9.dp
private val PILL_VERTICAL = 5.dp
private val PROGRESS_HEIGHT = 7.dp
private val INFO_ROW_VERTICAL = 12.dp
private val SCHEDULE_ROW_VERTICAL = 12.dp
private val TIME_MIN_WIDTH = 56.dp
private val SMALL_TEXT = 12.sp
private val SMALL_LINE = 18.sp
