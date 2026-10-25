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
import androidx.test.core.app.ApplicationProvider
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.PrivacyChoice
import br.com.paroquiasantacabrini.app.feature.prayer.ui.model.SavedRequest
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [35])
public class SharedPreferencesPrayerStoreTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    @Test
    public fun startsWithTheFirstNovenaDayAndNoRequest() {
        val store = SharedPreferencesPrayerStore(context)

        assertThat(store.novenaDay).isEqualTo(1)
        assertThat(store.savedRequest()).isNull()
    }

    @Test
    public fun novenaDayPersistsAcrossStoreInstances() {
        SharedPreferencesPrayerStore(context).novenaDay = 4

        assertThat(SharedPreferencesPrayerStore(context).novenaDay).isEqualTo(4)
    }

    @Test
    public fun outOfRangeNovenaDayIsClamped() {
        val store = SharedPreferencesPrayerStore(context)

        store.novenaDay = 99

        assertThat(store.novenaDay).isEqualTo(9)
    }

    @Test
    public fun requestRoundTripsAndCanBeCleared() {
        val store = SharedPreferencesPrayerStore(context)
        val request = SavedRequest(text = "Pela paz", privacy = PrivacyChoice.Community)

        store.saveRequest(request)
        assertThat(SharedPreferencesPrayerStore(context).savedRequest()).isEqualTo(request)

        store.clearRequest()
        assertThat(store.savedRequest()).isNull()
    }

    @Test
    public fun usesThePrivateFileThatIsExcludedFromBackups() {
        // The same name appears in app/src/main/res/xml/data_extraction_rules.xml and backup_rules.xml.
        assertThat(SharedPreferencesPrayerStore.FILE_NAME).isEqualTo("paroquia_prayer")
    }
}
