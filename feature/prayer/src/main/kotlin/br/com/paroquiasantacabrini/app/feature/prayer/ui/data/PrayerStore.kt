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

package br.com.paroquiasantacabrini.app.feature.prayer.ui.data

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.NovenaProgress
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.PrivacyChoice
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.SavedRequest

/** Device-local storage for prayer progress and the one saved intention. Nothing leaves the device. */
internal interface PrayerStore {
    public var novenaDay: Int

    public fun savedRequest(): SavedRequest?

    public fun saveRequest(request: SavedRequest)

    public fun clearRequest()
}

internal class SharedPreferencesPrayerStore internal constructor(
    context: Context,
) : PrayerStore {

    // The file name is excluded from backups in the app's backup rules; keep both in sync.
    private val preferences = context.applicationContext
        .getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)

    public override var novenaDay: Int
        get() = NovenaProgress.clamp(preferences.getInt(KEY_NOVENA_DAY, NovenaProgress.FIRST_DAY))
        set(value) {
            preferences.edit().putInt(KEY_NOVENA_DAY, NovenaProgress.clamp(value)).apply()
        }

    public override fun savedRequest(): SavedRequest? {
        val text = preferences.getString(KEY_REQUEST_TEXT, null) ?: return null
        val privacy = PrivacyChoice.entries.firstOrNull {
            it.name == preferences.getString(KEY_REQUEST_PRIVACY, null)
        } ?: PrivacyChoice.Private
        return SavedRequest(text = text, privacy = privacy)
    }

    public override fun saveRequest(request: SavedRequest) {
        preferences.edit()
            .putString(KEY_REQUEST_TEXT, request.text)
            .putString(KEY_REQUEST_PRIVACY, request.privacy.name)
            .apply()
    }

    public override fun clearRequest() {
        preferences.edit().remove(KEY_REQUEST_TEXT).remove(KEY_REQUEST_PRIVACY).apply()
    }

    internal companion object {
        internal const val FILE_NAME: String = "paroquia_prayer"
        private const val KEY_NOVENA_DAY = "novena_day"
        private const val KEY_REQUEST_TEXT = "request_text"
        private const val KEY_REQUEST_PRIVACY = "request_privacy"
    }
}

@Composable
internal fun rememberPrayerStore(): PrayerStore {
    val context = LocalContext.current
    return remember(context) { SharedPreferencesPrayerStore(context) }
}
