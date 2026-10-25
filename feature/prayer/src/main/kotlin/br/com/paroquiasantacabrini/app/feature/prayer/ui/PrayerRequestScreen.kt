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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.LiveRegionMode
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.liveRegion
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.prayer.R
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.PrayerRequestRules
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.PrivacyChoice
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.RequestError
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.SavedRequest

@Composable
internal fun PrayerRequestScreen(
    text: String,
    privacy: PrivacyChoice,
    acknowledged: Boolean,
    error: RequestError?,
    saved: SavedRequest?,
    feedbackRes: Int?,
    onTextChange: (String) -> Unit,
    onPrivacyChange: (PrivacyChoice) -> Unit,
    onAcknowledgedChange: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_prayer_request_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_prayer_request_eyebrow),
        onBackClick = onBackClick,
    ) {
        Text(
            text = stringResource(R.string.feature_prayer_request_intro),
            style = MaterialTheme.typography.bodyLarge,
        )
        OutlinedTextField(
            value = text,
            onValueChange = onTextChange,
            modifier = Modifier.fillMaxWidth(),
            label = { Text(stringResource(R.string.feature_prayer_request_field)) },
            placeholder = { Text(stringResource(R.string.feature_prayer_request_placeholder)) },
            supportingText = {
                Text(
                    when (error) {
                        RequestError.EmptyText -> stringResource(R.string.feature_prayer_request_error_empty)
                        else -> stringResource(
                            R.string.feature_prayer_request_counter,
                            text.length,
                            PrayerRequestRules.MAX_LENGTH,
                        )
                    },
                )
            },
            isError = error == RequestError.EmptyText,
            minLines = MIN_LINES,
        )
        Text(
            text = stringResource(R.string.feature_prayer_request_privacy_label),
            style = MaterialTheme.typography.titleMedium,
        )
        Row(horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small)) {
            FilterChip(
                selected = privacy == PrivacyChoice.Private,
                onClick = { onPrivacyChange(PrivacyChoice.Private) },
                label = { Text(stringResource(R.string.feature_prayer_request_private)) },
            )
            FilterChip(
                selected = privacy == PrivacyChoice.Community,
                onClick = { onPrivacyChange(PrivacyChoice.Community) },
                label = { Text(stringResource(R.string.feature_prayer_request_community)) },
            )
        }
        Text(
            text = stringResource(R.string.feature_prayer_request_privacy_note),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .toggleable(value = acknowledged, role = Role.Checkbox, onValueChange = onAcknowledgedChange),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Checkbox(checked = acknowledged, onCheckedChange = null)
            Text(
                text = stringResource(R.string.feature_prayer_request_ack),
                modifier = Modifier.padding(start = TemplateSpacing.small),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
        if (error == RequestError.NotAcknowledged) {
            Text(
                text = stringResource(R.string.feature_prayer_request_error_ack),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.error,
            )
        }
        Button(onClick = onSaveClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_prayer_request_save))
        }
        // Always composed so screen readers announce the message when it appears.
        Text(
            text = feedbackRes?.let { stringResource(it) }.orEmpty(),
            modifier = Modifier.semantics { liveRegion = LiveRegionMode.Polite },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        if (saved != null) {
            SavedCard(saved = saved, onDeleteClick = onDeleteClick)
        }
        Callout(text = stringResource(R.string.feature_prayer_request_saved_note))
    }
}

@Composable
private fun SavedCard(
    saved: SavedRequest,
    onDeleteClick: () -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
        ) {
            Text(
                text = stringResource(R.string.feature_prayer_request_saved_title),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            Text(text = saved.text, style = MaterialTheme.typography.bodyLarge)
            OutlinedButton(onClick = onDeleteClick) {
                Text(stringResource(R.string.feature_prayer_request_delete))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PrayerRequestScreenPreview() {
    ParoquiaTheme {
        PrayerRequestScreen(
            text = "",
            privacy = PrivacyChoice.Private,
            acknowledged = false,
            error = null,
            saved = null,
            feedbackRes = null,
            onTextChange = {},
            onPrivacyChange = {},
            onAcknowledgedChange = {},
            onSaveClick = {},
            onDeleteClick = {},
            onBackClick = {},
        )
    }
}

private const val MIN_LINES = 4
