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

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.community.R

private val Notices = listOf(
    Triple(
        R.string.feature_community_notice_1_eyebrow,
        R.string.feature_community_notice_1_title,
        R.string.feature_community_notice_1_text,
    ),
    Triple(
        R.string.feature_community_notice_2_eyebrow,
        R.string.feature_community_notice_2_title,
        R.string.feature_community_notice_2_text,
    ),
    Triple(
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
        Text(text = stringResource(R.string.feature_community_notices_intro), style = MaterialTheme.typography.bodyLarge)
        Notices.forEach { (eyebrowRes, titleRes, textRes) ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .semantics(mergeDescendants = true) {},
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
            ) {
                Column(
                    modifier = Modifier.padding(TemplateSpacing.medium),
                    verticalArrangement = Arrangement.spacedBy(TemplateSpacing.extraSmall),
                ) {
                    Text(
                        text = stringResource(eyebrowRes),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.secondary,
                    )
                    Text(text = stringResource(titleRes), style = MaterialTheme.typography.titleMedium)
                    Text(text = stringResource(textRes), style = MaterialTheme.typography.bodyLarge)
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
