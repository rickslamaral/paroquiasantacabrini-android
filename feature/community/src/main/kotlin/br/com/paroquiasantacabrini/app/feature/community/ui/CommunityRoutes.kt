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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import br.com.paroquiasantacabrini.app.feature.community.R
import br.com.paroquiasantacabrini.app.feature.community.ui.model.ParishContacts

@Composable
public fun CommunityHubRoute(
    onGroupsClick: () -> Unit,
    onCatechesisClick: () -> Unit,
    onConfirmationClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val openLink = rememberLinkOpener()
    CommunityHubScreen(
        onGroupsClick = onGroupsClick,
        onCatechesisClick = onCatechesisClick,
        onConfirmationClick = onConfirmationClick,
        onContactClick = { openLink(ParishContacts.WHATSAPP_URL) },
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
public fun GroupsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val openLink = rememberLinkOpener()
    CommunityInfoPage(
        title = stringResource(R.string.feature_community_groups_title),
        eyebrow = stringResource(R.string.feature_community_groups_eyebrow),
        cardEyebrow = stringResource(R.string.feature_community_groups_intro),
        cardTitle = stringResource(R.string.feature_community_groups_card_title),
        cardText = stringResource(R.string.feature_community_groups_card_text),
        buttonLabel = stringResource(R.string.feature_community_groups_button),
        note = stringResource(R.string.feature_community_groups_note),
        onButtonClick = { openLink(ParishContacts.WHATSAPP_URL) },
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
public fun CatechesisRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val openLink = rememberLinkOpener()
    CommunityInfoPage(
        title = stringResource(R.string.feature_community_catechesis_title),
        eyebrow = stringResource(R.string.feature_community_catechesis_eyebrow),
        cardEyebrow = stringResource(R.string.feature_community_catechesis_card_eyebrow),
        cardTitle = stringResource(R.string.feature_community_catechesis_card_title),
        cardText = stringResource(R.string.feature_community_catechesis_card_text),
        buttonLabel = stringResource(R.string.feature_community_ask_info),
        note = stringResource(R.string.feature_community_catechesis_note),
        onButtonClick = { openLink(ParishContacts.WHATSAPP_URL) },
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
public fun ConfirmationRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val openLink = rememberLinkOpener()
    CommunityInfoPage(
        title = stringResource(R.string.feature_community_confirmation_title),
        eyebrow = stringResource(R.string.feature_community_confirmation_eyebrow),
        cardEyebrow = stringResource(R.string.feature_community_confirmation_card_eyebrow),
        cardTitle = stringResource(R.string.feature_community_confirmation_card_title),
        cardText = stringResource(R.string.feature_community_confirmation_card_text),
        buttonLabel = stringResource(R.string.feature_community_ask_info),
        note = stringResource(R.string.feature_community_confirmation_note),
        onButtonClick = { openLink(ParishContacts.WHATSAPP_URL) },
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

/** Opens a link, ignoring devices without an app able to handle it. */
@Composable
internal fun rememberLinkOpener(): (String) -> Unit {
    val uriHandler = LocalUriHandler.current
    return { url -> runCatching { uriHandler.openUri(url) } }
}
