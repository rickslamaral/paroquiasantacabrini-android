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

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import br.com.paroquiasantacabrini.app.feature.prayer.R
import br.com.paroquiasantacabrini.app.feature.prayer.ui.data.PrayerStore
import br.com.paroquiasantacabrini.app.feature.prayer.ui.data.rememberPrayerStore
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.PrayerRequestRules
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.PrivacyChoice
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.RequestError
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.SavedRequest

@Composable
public fun PrayerRequestRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    PrayerRequestRoute(store = rememberPrayerStore(), onBackClick = onBackClick, modifier = modifier)
}

@Composable
internal fun PrayerRequestRoute(
    store: PrayerStore,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var text by rememberSaveable { mutableStateOf("") }
    var privacy by rememberSaveable { mutableStateOf(PrivacyChoice.Private) }
    var acknowledged by rememberSaveable { mutableStateOf(false) }
    var error by remember { mutableStateOf<RequestError?>(null) }
    var feedbackRes by remember { mutableStateOf<Int?>(null) }
    var saved by remember(store) { mutableStateOf(store.savedRequest()) }

    PrayerRequestScreen(
        text = text,
        privacy = privacy,
        acknowledged = acknowledged,
        error = error,
        saved = saved,
        feedbackRes = feedbackRes,
        onTextChange = {
            text = PrayerRequestRules.limit(it)
            error = null
        },
        onPrivacyChange = { privacy = it },
        onAcknowledgedChange = {
            acknowledged = it
            error = null
        },
        onSaveClick = {
            val problem = PrayerRequestRules.validate(text, acknowledged)
            error = problem
            if (problem == null) {
                val request = SavedRequest(text = text.trim(), privacy = privacy)
                store.saveRequest(request)
                saved = request
                text = ""
                feedbackRes = R.string.feature_prayer_request_saved_feedback
            }
        },
        onDeleteClick = {
            store.clearRequest()
            saved = null
            feedbackRes = R.string.feature_prayer_request_deleted
        },
        onBackClick = onBackClick,
        modifier = modifier,
    )
}
