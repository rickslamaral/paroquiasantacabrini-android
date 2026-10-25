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

package br.com.paroquiasantacabrini.app.feature.massschedule.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.massschedule.R
import br.com.paroquiasantacabrini.app.feature.massschedule.ui.model.ParishContact

@Composable
internal fun ContactSection(
    contact: ParishContact,
    onActionClick: (uri: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    ScheduleSection(
        title = stringResource(R.string.feature_massschedule_contact_title),
        modifier = modifier,
    ) {
        LabeledValue(
            label = stringResource(R.string.feature_massschedule_address_label),
            value = contact.address + "\n" + contact.postalCode,
        )
        LabeledValue(
            label = stringResource(R.string.feature_massschedule_phone_label),
            value = contact.phone,
        )
        LabeledValue(
            label = stringResource(R.string.feature_massschedule_email_label),
            value = contact.email,
        )
        LabeledValue(
            label = stringResource(R.string.feature_massschedule_pastor_label),
            value = contact.pastor,
        )
        LabeledValue(
            label = stringResource(R.string.feature_massschedule_secretary_label),
            value = contact.secretary,
        )
        Column(verticalArrangement = Arrangement.spacedBy(TemplateSpacing.small)) {
            OutlinedButton(
                onClick = { onActionClick(contact.phoneUri) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.feature_massschedule_action_call))
            }
            OutlinedButton(
                onClick = { onActionClick(contact.emailUri) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.feature_massschedule_action_email))
            }
            OutlinedButton(
                onClick = { onActionClick(contact.mapUri) },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.feature_massschedule_action_map))
            }
        }
    }
}
