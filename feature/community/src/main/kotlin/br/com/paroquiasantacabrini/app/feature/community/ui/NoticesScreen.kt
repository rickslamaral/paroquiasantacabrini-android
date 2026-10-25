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

package br.com.paroquiasantacabrini.app.feature.community.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.RoundIcon
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.core.designsystem.theme.extraColors
import br.com.paroquiasantacabrini.app.feature.community.R

private data class Notice(
    internal val glyph: String,
    @get:StringRes internal val eyebrow: Int,
    @get:StringRes internal val title: Int,
    @get:StringRes internal val text: Int,
)

private val Notices = listOf(
    Notice(
        "☼",
        R.string.feature_community_notice_1_eyebrow,
        R.string.feature_community_notice_1_title,
        R.string.feature_community_notice_1_text,
    ),
    Notice(
        "⌂",
        R.string.feature_community_notice_2_eyebrow,
        R.string.feature_community_notice_2_title,
        R.string.feature_community_notice_2_text,
    ),
    Notice(
        "☩",
        R.string.feature_community_notice_3_eyebrow,
        R.string.feature_community_notice_3_title,
        R.string.feature_community_notice_3_text,
    ),
)

@Composable
internal fun NoticesScreen(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_community_notices_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_community_notices_eyebrow),
        onBackClick = onBackClick,
    ) {
        Text(
            text = stringResource(R.string.feature_community_notices_intro),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Notices.forEach { (glyph, eyebrowRes, titleRes, textRes) ->
            ParoquiaCard(modifier = Modifier.semantics(mergeDescendants = true) {}) {
                Row(horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.medium - 3.dp)) {
                    RoundIcon(glyph = glyph, background = MaterialTheme.extraColors.sand)
                    Column {
                        Text(
                            text = stringResource(eyebrowRes).uppercase(),
                            style = MaterialTheme.typography.labelMedium.copy(fontSize = 10.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                        Text(text = stringResource(titleRes), style = MaterialTheme.typography.titleSmall)
                        Text(
                            text = stringResource(textRes),
                            modifier = Modifier.padding(top = 4.dp),
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 12.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                }
            }
        }
        Callout(text = stringResource(R.string.feature_community_notices_note))
    }
}

@Preview(showBackground = true)
@Composable
private fun NoticesScreenPreview() {
    ParoquiaTheme { NoticesScreen(onBackClick = {}) }
}
