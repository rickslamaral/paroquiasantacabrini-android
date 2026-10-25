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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors

/**
 * A square-ish shortcut with a round icon above a short label (`quick` in the prototype).
 * [position] is the 1-based place in the grid and picks the icon tint like the prototype's CSS.
 */
@Composable
fun QuickTile(
    glyph: String,
    label: String,
    position: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val extra = MaterialTheme.extraColors
    val tint: Color = when {
        position % 3 == 0 -> extra.rose
        position % 2 == 0 -> extra.blue
        else -> extra.cream
    }
    ParoquiaCard(
        modifier = modifier.heightIn(min = TILE_MIN_HEIGHT),
        onClick = onClick,
        shape = MaterialTheme.shapes.medium,
        contentPadding = TILE_PADDING,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(7.dp),
        ) {
            RoundIcon(glyph = glyph, background = tint, size = TILE_ICON_SIZE)
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(fontSize = TILE_LABEL_SIZE),
                textAlign = TextAlign.Center,
            )
        }
    }
}

/** Lays [count] items out in rows of [columns] equal cells, leaving the last row's gaps empty. */
@Composable
fun <T> FixedGrid(
    items: List<T>,
    columns: Int,
    modifier: Modifier = Modifier,
    spacing: Dp = 8.dp,
    cell: @Composable (index: Int, item: T) -> Unit,
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(spacing)) {
        items.chunked(columns).forEachIndexed { rowIndex, row ->
            Row(horizontalArrangement = Arrangement.spacedBy(spacing)) {
                row.forEachIndexed { column, item ->
                    Column(modifier = Modifier.weight(1f)) { cell(rowIndex * columns + column, item) }
                }
                repeat(columns - row.size) { Column(modifier = Modifier.weight(1f)) {} }
            }
        }
    }
}

/** An official parish channel shown in the "social" row. The names are brands, so not translated. */
data class ParishChannel(val name: String, val url: String)

/** The parish's official channels, in the order the prototype shows them. */
object ParishChannels {
    val All: List<ParishChannel> = listOf(
        ParishChannel("Instagram", "https://www.instagram.com/stacabrini/"),
        ParishChannel("Facebook", "https://www.facebook.com/santafranciscaxaviercabrini"),
        ParishChannel(
            "Diocese",
            "https://diocesedesantoamaro.org.br/paroquias-da-diocese-de-santo-amaro/" +
                "paroquia-santa-francisca-xavier-cabrini/",
        ),
    )
}

/** Three equal outlined buttons for the official channels (`social` in the prototype). */
@Composable
fun SocialLinksRow(
    onLinkClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        ParishChannels.All.forEach { channel ->
            ParoquiaCard(
                modifier = Modifier.weight(1f),
                onClick = { onLinkClick(channel.url) },
                shape = MaterialTheme.shapes.extraSmall,
                contentPadding = SOCIAL_PADDING,
            ) {
                Text(
                    text = channel.name,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 1.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

private val TILE_MIN_HEIGHT = 83.dp
private val TILE_PADDING = PaddingValues(start = 5.dp, end = 5.dp, top = 12.dp, bottom = 10.dp)
private val TILE_ICON_SIZE = 34.dp
private val TILE_LABEL_SIZE = 10.sp
private val SOCIAL_PADDING = PaddingValues(horizontal = 5.dp, vertical = 11.dp)
