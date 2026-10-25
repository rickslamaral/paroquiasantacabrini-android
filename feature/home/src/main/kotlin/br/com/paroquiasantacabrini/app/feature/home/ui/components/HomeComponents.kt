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

import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.FeatureBanner
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.QuickTile
import br.com.paroquiasantacabrini.app.core.designsystem.component.RoundIcon
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaSerifFontFamily
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors
import br.com.paroquiasantacabrini.app.feature.home.R
import br.com.paroquiasantacabrini.app.feature.home.ui.model.AnnouncementUiModel
import br.com.paroquiasantacabrini.app.feature.home.ui.model.HomeDestination

/** The facade photo, full width, with the welcome headline over a dark gradient (`hero`). */
@Composable
internal fun HomeHero(modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxWidth().height(HERO_HEIGHT)) {
        Image(
            painter = painterResource(ParoquiaImages.Facade),
            contentDescription = stringResource(R.string.feature_home_hero_image),
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
            alignment = BiasAlignment(0f, HERO_FOCUS),
        )
        Box(modifier = Modifier.matchParentSize().background(Color.Black.copy(alpha = HERO_DIM)))
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color.Transparent,
                        HERO_GRADIENT_START to Color.Transparent,
                        1f to HERO_SCRIM,
                    ),
                ),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 20.dp, end = 20.dp, bottom = 15.dp),
        ) {
            Text(
                text = stringResource(R.string.feature_home_hero_eyebrow).uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = HERO_EYEBROW,
            )
            Text(
                text = stringResource(R.string.feature_home_hero_title),
                modifier = Modifier.padding(vertical = 5.dp).semantics { heading() },
                fontFamily = ParoquiaSerifFontFamily,
                fontWeight = FontWeight.Medium,
                fontSize = 25.sp,
                lineHeight = 27.sp,
                letterSpacing = (-1.1).sp,
                color = Color.White,
            )
            Text(
                text = stringResource(R.string.feature_home_hero_subtitle),
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp),
                color = HERO_TEXT,
            )
        }
    }
}

/** The sage "come celebrate" card that opens the Mass times (`mass-card`). */
@Composable
internal fun MassCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaCard(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        bordered = false,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium - 3.dp),
        ) {
            RoundIcon(
                glyph = stringResource(R.string.feature_home_mass_card_glyph),
                background = Color.White.copy(alpha = ICON_WHITE),
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.feature_home_mass_card_eyebrow),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = stringResource(R.string.feature_home_mass_card_title),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(R.string.feature_home_mass_card_text),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Chevron()
        }
    }
}

/** An illustrated banner that opens the guided Mass Mode. */
@Composable
internal fun MassModeBanner(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    FeatureBanner(
        imageRes = ParoquiaImages.MassBanner,
        eyebrow = stringResource(R.string.feature_home_massmode_banner_eyebrow),
        title = stringResource(R.string.feature_home_massmode_banner_title),
        text = stringResource(R.string.feature_home_massmode_banner_text),
        onClick = onClick,
        modifier = modifier,
    )
}

internal data class QuickAccessItem(
    internal val destination: HomeDestination,
    internal val glyph: String,
    @get:StringRes internal val labelRes: Int,
)

/** The four shortcuts, in the prototype's order. */
internal val QuickAccessItems: List<QuickAccessItem> = listOf(
    QuickAccessItem(HomeDestination.MassSchedule, "◷", R.string.feature_home_quick_times),
    QuickAccessItem(HomeDestination.DailyLiturgy, "▤", R.string.feature_home_quick_liturgy),
    QuickAccessItem(HomeDestination.PrayerRequests, "♡", R.string.feature_home_quick_prayer),
    QuickAccessItem(HomeDestination.TithesAndOfferings, "＋", R.string.feature_home_quick_tithes),
)

@Composable
internal fun QuickAccessTile(
    item: QuickAccessItem,
    position: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    QuickTile(
        glyph = item.glyph,
        label = stringResource(item.labelRes),
        position = position,
        onClick = onClick,
        modifier = modifier,
    )
}

/** A notice with a sand icon, a small label, a title and its text (`notice`). */
@Composable
internal fun NoticeCard(
    announcement: AnnouncementUiModel,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaCard(modifier = modifier, onClick = onClick) {
        Row(horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium - 3.dp)) {
            RoundIcon(glyph = "✦", background = MaterialTheme.extraColors.sand)
            Column {
                Text(
                    text = announcement.dateLabel.uppercase(),
                    style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(text = announcement.title, style = MaterialTheme.typography.titleSmall)
                Text(
                    text = announcement.message,
                    modifier = Modifier.padding(top = 4.dp),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

/** The patroness card: round portrait, name, memorial and a chevron (`split-card`). */
@Composable
internal fun PatronessCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaCard(modifier = modifier, onClick = onClick) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium - 3.dp),
        ) {
            Image(
                painter = painterResource(ParoquiaImages.SantaCabrini),
                contentDescription = stringResource(R.string.feature_home_patroness_image),
                modifier = Modifier.size(PORTRAIT_SIZE).clip(CircleShape),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.feature_home_patroness_name),
                    style = MaterialTheme.typography.titleSmall,
                )
                Text(
                    text = stringResource(R.string.feature_home_patroness_text),
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
            Chevron()
        }
    }
}

@Composable
private fun Chevron() {
    Text(
        text = "›",
        modifier = Modifier.clearAndSetSemantics {},
        style = MaterialTheme.typography.headlineSmall,
        color = MaterialTheme.colorScheme.primary,
    )
}

private val HERO_HEIGHT = 190.dp
private val PORTRAIT_SIZE = 72.dp
private const val HERO_FOCUS = 0.12f
private const val HERO_DIM = 0.24f
private const val HERO_GRADIENT_START = 0.22f
private const val ICON_WHITE = 0.7f
private val HERO_SCRIM = Color(0xDB15281E)
private val HERO_EYEBROW = Color(0xFFF1E6CE)
private val HERO_TEXT = Color(0xFFF6F4ED)
