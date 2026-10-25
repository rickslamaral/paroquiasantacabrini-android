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

package br.com.paroquiasantacabrini.app.feature.prayer.ui.model

import androidx.annotation.StringRes
import br.com.paroquiasantacabrini.app.feature.prayer.R

internal data class RosaryStep(
    @get:StringRes internal val titleRes: Int,
    @get:StringRes internal val textRes: Int,
)

/** Opening steps of the rosary, with illustrative text. */
internal val RosarySteps: List<RosaryStep> = listOf(
    RosaryStep(R.string.feature_prayer_rosary_step_1_title, R.string.feature_prayer_rosary_step_1_text),
    RosaryStep(R.string.feature_prayer_rosary_step_2_title, R.string.feature_prayer_rosary_step_2_text),
    RosaryStep(R.string.feature_prayer_rosary_step_3_title, R.string.feature_prayer_rosary_step_3_text),
    RosaryStep(R.string.feature_prayer_rosary_step_4_title, R.string.feature_prayer_rosary_step_4_text),
    RosaryStep(R.string.feature_prayer_rosary_step_5_title, R.string.feature_prayer_rosary_step_5_text),
    RosaryStep(R.string.feature_prayer_rosary_step_6_title, R.string.feature_prayer_rosary_step_6_text),
)

internal val GloriousMysteries: List<Int> = listOf(
    R.string.feature_prayer_rosary_mystery_1,
    R.string.feature_prayer_rosary_mystery_2,
    R.string.feature_prayer_rosary_mystery_3,
    R.string.feature_prayer_rosary_mystery_4,
    R.string.feature_prayer_rosary_mystery_5,
)

internal object RosaryProgress {
    internal fun clamp(step: Int): Int = step.coerceIn(0, RosarySteps.lastIndex)
}

internal object NovenaProgress {
    internal const val FIRST_DAY: Int = 1
    internal const val LAST_DAY: Int = 9

    internal fun clamp(day: Int): Int = day.coerceIn(FIRST_DAY, LAST_DAY)

    /** The next day of the novena, or the first day again once the ninth is complete. */
    internal fun advance(day: Int): Int = if (day >= LAST_DAY) FIRST_DAY else day + 1
}

internal enum class PrivacyChoice {
    Private,
    Community,
}

internal data class SavedRequest(
    internal val text: String,
    internal val privacy: PrivacyChoice,
)

internal enum class RequestError {
    EmptyText,
    NotAcknowledged,
}

internal object PrayerRequestRules {
    internal const val MAX_LENGTH: Int = 400

    /** Returns why the request cannot be saved, or `null` when it can. */
    internal fun validate(text: String, acknowledged: Boolean): RequestError? = when {
        text.isBlank() -> RequestError.EmptyText
        !acknowledged -> RequestError.NotAcknowledged
        else -> null
    }

    internal fun limit(text: String): String = text.take(MAX_LENGTH)
}
