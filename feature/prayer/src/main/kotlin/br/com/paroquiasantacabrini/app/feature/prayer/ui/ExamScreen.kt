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

package br.com.paroquiasantacabrini.app.feature.prayer.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.prayer.R

private val ExamItems = listOf(
    R.string.feature_prayer_exam_1_title to R.string.feature_prayer_exam_1_text,
    R.string.feature_prayer_exam_2_title to R.string.feature_prayer_exam_2_text,
    R.string.feature_prayer_exam_3_title to R.string.feature_prayer_exam_3_text,
)

@Composable
internal fun ExamScreen(
    isFinished: Boolean,
    onFinishClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_prayer_exam_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_prayer_exam_eyebrow),
        onBackClick = onBackClick,
    ) {
        Text(
            text = stringResource(R.string.feature_prayer_exam_intro),
            style = MaterialTheme.typography.bodyLarge,
        )
        SectionHeading(text = stringResource(R.string.feature_prayer_exam_reflect_title))
        ExamItems.forEachIndexed { index, (titleRes, textRes) ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
            ) {
                Column(
                    modifier = Modifier.padding(TemplateSpacing.medium),
                    verticalArrangement = Arrangement.spacedBy(TemplateSpacing.extraSmall),
                ) {
                    Text(
                        text = stringResource(R.string.feature_prayer_exam_item, index + 1, stringResource(titleRes)),
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                    )
                    Text(text = stringResource(textRes), style = MaterialTheme.typography.bodyLarge)
                }
            }
        }
        Callout(text = stringResource(R.string.feature_prayer_exam_note))
        Button(onClick = onFinishClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_prayer_exam_finish))
        }
        // Always composed so screen readers announce the message when it appears.
        Text(
            text = if (isFinished) stringResource(R.string.feature_prayer_exam_done) else "",
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun ExamScreenPreview() {
    ParoquiaTheme { ExamScreen(isFinished = false, onFinishClick = {}, onBackClick = {}) }
}
