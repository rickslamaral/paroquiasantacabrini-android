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
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.home.R

internal data class ParishLink(
    internal val url: String,
    @get:StringRes internal val titleRes: Int,
    @get:StringRes internal val descriptionRes: Int,
)

/** Official parish channels, shown in the order they appear on the dashboard. */
internal val ParishLinks: List<ParishLink> = listOf(
    ParishLink(
        url = "https://www.instagram.com/stacabrini/",
        titleRes = R.string.feature_home_link_instagram_title,
        descriptionRes = R.string.feature_home_link_instagram_description,
    ),
    ParishLink(
        url = "https://www.facebook.com/santafranciscaxaviercabrini",
        titleRes = R.string.feature_home_link_facebook_title,
        descriptionRes = R.string.feature_home_link_facebook_description,
    ),
    ParishLink(
        url = "https://diocesedesantoamaro.org.br/paroquias-da-diocese-de-santo-amaro/" +
            "paroquia-santa-francisca-xavier-cabrini/",
        titleRes = R.string.feature_home_link_diocese_title,
        descriptionRes = R.string.feature_home_link_diocese_description,
    ),
)

@Composable
internal fun ParishLinkCard(
    link: ParishLink,
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
        Column(
            modifier = Modifier.padding(TemplateSpacing.medium),
            verticalArrangement = Arrangement.spacedBy(TemplateSpacing.extraSmall),
        ) {
            Text(
                text = stringResource(link.titleRes),
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                text = stringResource(link.descriptionRes),
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}
