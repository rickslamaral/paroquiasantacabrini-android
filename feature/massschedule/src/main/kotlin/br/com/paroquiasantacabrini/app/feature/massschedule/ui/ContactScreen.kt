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

package br.com.paroquiasantacabrini.app.feature.massschedule.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.component.InfoRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaCard
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaImages
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaPage
import br.com.paroquiasantacabrini.app.core.designsystem.component.ParoquiaTonalButton
import br.com.paroquiasantacabrini.app.core.designsystem.component.SectionHeading
import br.com.paroquiasantacabrini.app.core.designsystem.component.SocialLinksRow
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateButton
import br.com.paroquiasantacabrini.app.core.designsystem.component.TemplateOutlinedButton
import br.com.paroquiasantacabrini.app.core.designsystem.theme.ParoquiaTheme
import br.com.paroquiasantacabrini.app.feature.massschedule.R
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.components.ChurchGallery
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.model.MassScheduleUiState
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.model.ParishContact

@Composable
internal fun ContactScreen(
    contact: ParishContact,
    onActionClick: (uri: String) -> Unit,
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
) {
    ParoquiaPage(
        title = stringResource(R.string.feature_massschedule_contact_title),
        modifier = modifier,
        eyebrow = stringResource(R.string.feature_massschedule_contact_eyebrow),
        onBackClick = onBackClick,
    ) {
        Image(
            painter = painterResource(ParoquiaImages.Facade),
            contentDescription = stringResource(R.string.feature_massschedule_photo_facade),
            modifier = Modifier
                .fillMaxWidth()
                .height(PHOTO_HEIGHT)
                .clip(RoundedCornerShape(18.dp)),
            contentScale = ContentScale.Crop,
        )
        ParoquiaCard {
            Column {
                InfoRow(
                    glyph = "⌖",
                    title = stringResource(R.string.feature_massschedule_parish_name),
                    text = stringResource(
                        R.string.feature_massschedule_contact_address_format,
                        contact.street,
                        contact.locality,
                        contact.postalCode,
                    ),
                )
                InfoRow(
                    glyph = "☎",
                    title = contact.phone,
                    text = stringResource(R.string.feature_massschedule_phone_note),
                )
                InfoRow(
                    glyph = "✉",
                    title = stringResource(R.string.feature_massschedule_email_label),
                    text = contact.email,
                )
                InfoRow(
                    glyph = "♧",
                    title = stringResource(R.string.feature_massschedule_pastor_label),
                    text = contact.pastor,
                )
                InfoRow(
                    glyph = "☷",
                    title = stringResource(R.string.feature_massschedule_secretary_label),
                    text = contact.secretary,
                    showDivider = false,
                )
            }
        }
        TemplateButton(
            onClick = { onActionClick(contact.mapUri) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.feature_massschedule_action_map))
        }
        Row(horizontalArrangement = Arrangement.spacedBy(9.dp)) {
            TemplateOutlinedButton(
                onClick = { onActionClick(contact.phoneUri) },
                modifier = Modifier.weight(1f),
            ) {
                Text(stringResource(R.string.feature_massschedule_action_call))
            }
            ParoquiaTonalButton(
                onClick = { onActionClick(contact.whatsappUri) },
                modifier = Modifier.weight(1f),
            ) {
                Text(stringResource(R.string.feature_massschedule_action_whatsapp))
            }
        }
        TemplateOutlinedButton(
            onClick = { onActionClick(contact.emailUri) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.feature_massschedule_action_email))
        }
        SocialLinksRow(onLinkClick = onActionClick)
        SectionHeading(
            text = stringResource(R.string.feature_massschedule_gallery_title),
            modifier = Modifier.padding(top = 6.dp),
        )
        ChurchGallery()
    }
}

@Preview(showBackground = true)
@Composable
private fun ContactScreenPreview() {
    ParoquiaTheme {
        ContactScreen(
            contact = MassScheduleUiState.forParish().contact,
            onActionClick = {},
            onBackClick = {},
        )
    }
}

private val PHOTO_HEIGHT = 170.dp
