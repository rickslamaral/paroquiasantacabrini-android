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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.core.designsystem.component.Callout
import br.com.paroquiasantacabrini.app.core.designsystem.component.NavCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.community.R

@Composable
internal fun CommunityHubScreen(
    onGroupsClick: () -> Unit,
    onCatechesisClick: () -> Unit,
    onConfirmationClick: () -> Unit,
    onContactClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_community_hub_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_community_hub_eyebrow),
        onBackClick = onBackClick,
    ) {
        Text(text = stringResource(R.string.feature_community_hub_intro), style = MaterialTheme.typography.bodyLarge)
        NavCard(
            title = stringResource(R.string.feature_community_hub_groups_title),
            description = stringResource(R.string.feature_community_hub_groups_text),
            emoji = "♧",
            onClick = onGroupsClick,
        )
        NavCard(
            title = stringResource(R.string.feature_community_hub_catechesis_title),
            description = stringResource(R.string.feature_community_hub_catechesis_text),
            emoji = "▤",
            onClick = onCatechesisClick,
        )
        NavCard(
            title = stringResource(R.string.feature_community_hub_confirmation_title),
            description = stringResource(R.string.feature_community_hub_confirmation_text),
            emoji = "✧",
            onClick = onConfirmationClick,
        )
        Callout(text = stringResource(R.string.feature_community_hub_note))
        Button(onClick = onContactClick, modifier = Modifier.fillMaxWidth()) {
            Text(stringResource(R.string.feature_community_hub_contact))
        }
    }
}

/** A short information page that sends the reader to the parish office instead of inventing details. */
@Composable
internal fun CommunityInfoPage(
    title: String,
    eyebrow: String,
    cardEyebrow: String,
    cardTitle: String,
    cardText: String,
    buttonLabel: String,
    note: String,
    onButtonClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ParoquiaPage(title = title, modifier = modifier, eyebrow = eyebrow, onBackClick = onBackClick) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        ) {
            Column(
                modifier = Modifier.padding(TemplateSpacing.medium),
                verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
            ) {
                Text(text = cardEyebrow, style = MaterialTheme.typography.labelMedium)
                Text(text = cardTitle, style = MaterialTheme.typography.titleLarge)
                Text(text = cardText, style = MaterialTheme.typography.bodyLarge)
                Button(onClick = onButtonClick, modifier = Modifier.fillMaxWidth()) { Text(buttonLabel) }
            }
        }
        Callout(text = note)
    }
}

@Preview(showBackground = true)
@Composable
private fun CommunityHubScreenPreview() {
    ParoquiaTheme {
        CommunityHubScreen(
            onGroupsClick = {},
            onCatechesisClick = {},
            onConfirmationClick = {},
            onContactClick = {},
            onBackClick = {},
        )
    }
}
