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

package br.com.paroquiasantacabrini.app.home.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.R
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaSerifFontFamily
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors

/** The prototype's fixed green header: brand mark, parish name, subtitle and a notices button. */
@Composable
internal fun ParoquiaTopBar(
    onNoticesClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val extra = MaterialTheme.extraColors
    Surface(modifier = modifier.fillMaxWidth(), color = extra.header, contentColor = Color.White) {
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(start = 20.dp, end = 20.dp, top = 8.dp, bottom = 18.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(MaterialTheme.shapes.small)
                    .background(Color.White.copy(alpha = 0.125f))
                    .clearAndSetSemantics {},
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = stringResource(R.string.brand_mark),
                    fontFamily = ParoquiaSerifFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 19.sp,
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = stringResource(R.string.top_bar_subtitle),
                    modifier = Modifier.padding(top = 3.dp),
                    style = MaterialTheme.typography.labelMedium,
                    color = extra.onHeaderMuted,
                )
            }
            val noticesDescription = stringResource(R.string.top_bar_notices)
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .clickable(role = Role.Button, onClick = onNoticesClick)
                    .semantics { contentDescription = noticesDescription },
                contentAlignment = Alignment.Center,
            ) {
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.094f)),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = stringResource(R.string.top_bar_notices_glyph),
                        modifier = Modifier.clearAndSetSemantics {},
                        fontSize = 18.sp,
                    )
                }
            }
        }
    }
}

/** The prototype's bottom bar: four labelled items, the selected one with a pale pill behind its icon. */
@Composable
internal fun ParoquiaBottomBar(
    selected: MainTab,
    onTabClick: (MainTab) -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    Surface(modifier = modifier.fillMaxWidth(), color = colors.background) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colors.outlineVariant),
            )
            Row(
                modifier = Modifier
                    .navigationBarsPadding()
                    .height(67.dp)
                    .padding(horizontal = 6.dp, vertical = 6.dp),
            ) {
                MainTab.entries.forEach { tab ->
                    BottomBarItem(
                        tab = tab,
                        isSelected = tab == selected,
                        onClick = { onTabClick(tab) },
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun BottomBarItem(
    tab: MainTab,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .selectable(selected = isSelected, role = Role.Tab, onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(width = 38.dp, height = 28.dp)
                .clip(CircleShape)
                .background(if (isSelected) colors.primaryContainer else Color.Transparent)
                .clearAndSetSemantics {},
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = tab.glyph,
                fontSize = 19.sp,
                color = if (isSelected) colors.primary else colors.onSurfaceVariant,
            )
        }
        Text(
            text = stringResource(tab.labelRes),
            modifier = Modifier.padding(top = 3.dp),
            style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
            color = if (isSelected) colors.primary else colors.onSurfaceVariant,
        )
    }
}
