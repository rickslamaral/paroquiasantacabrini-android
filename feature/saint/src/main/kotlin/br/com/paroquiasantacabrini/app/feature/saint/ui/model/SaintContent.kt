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

package br.com.paroquiasantacabrini.app.feature.saint.ui.model

import androidx.annotation.StringRes
import br.com.paroquiasantacabrini.app.feature.saint.R
import java.text.Normalizer

internal data class CabriniFact(
    @get:StringRes internal val valueRes: Int,
    @get:StringRes internal val labelRes: Int,
)

internal data class TimelineEntry(
    @get:StringRes internal val titleRes: Int,
    @get:StringRes internal val textRes: Int,
)

internal data class SourceLink(
    internal val url: String,
    @get:StringRes internal val titleRes: Int,
    @get:StringRes internal val descriptionRes: Int,
)

internal val CabriniFacts: List<CabriniFact> = listOf(
    CabriniFact(R.string.feature_saint_fact_1_value, R.string.feature_saint_fact_1_label),
    CabriniFact(R.string.feature_saint_fact_2_value, R.string.feature_saint_fact_2_label),
    CabriniFact(R.string.feature_saint_fact_3_value, R.string.feature_saint_fact_3_label),
    CabriniFact(R.string.feature_saint_fact_4_value, R.string.feature_saint_fact_4_label),
)

internal val CabriniTimeline: List<TimelineEntry> = listOf(
    TimelineEntry(R.string.feature_saint_timeline_1_title, R.string.feature_saint_timeline_1_text),
    TimelineEntry(R.string.feature_saint_timeline_2_title, R.string.feature_saint_timeline_2_text),
    TimelineEntry(R.string.feature_saint_timeline_3_title, R.string.feature_saint_timeline_3_text),
    TimelineEntry(R.string.feature_saint_timeline_4_title, R.string.feature_saint_timeline_4_text),
)

/** Institutional sources for the biography, shown so readers can verify it. */
internal val CabriniSources: List<SourceLink> = listOf(
    SourceLink(
        url = "https://www.vatican.va/content/john-paul-ii/pt/speeches/2000/jul-sep/documents/" +
            "hf_jp-ii_spe_20000715_miss-sacro-cuore.html",
        titleRes = R.string.feature_saint_link_vatican_title,
        descriptionRes = R.string.feature_saint_link_vatican_description,
    ),
    SourceLink(
        url = "https://www.mothercabrini.org/who-we-are/our-history/",
        titleRes = R.string.feature_saint_link_congregation_title,
        descriptionRes = R.string.feature_saint_link_congregation_description,
    ),
    SourceLink(
        url = "https://cabrininationalshrine.org/history/mother-cabrinis-life-and-legacy/",
        titleRes = R.string.feature_saint_link_shrine_title,
        descriptionRes = R.string.feature_saint_link_shrine_description,
    ),
)

/** A saint in the demonstration directory. Names are proper nouns, so they are not translated. */
internal data class SaintEntry(
    internal val name: String,
    internal val isPatroness: Boolean = false,
)

internal val SaintDirectory: List<SaintEntry> = listOf(
    SaintEntry("Santa Francisca Xavier Cabrini", isPatroness = true),
    SaintEntry("Santo Antônio de Pádua"),
    SaintEntry("Santa Teresinha do Menino Jesus"),
    SaintEntry("São Francisco de Assis"),
    SaintEntry("São José"),
    SaintEntry("Santa Rita de Cássia"),
)

/** Case- and accent-insensitive name search, so "antonio" finds "Antônio". */
internal fun searchSaints(query: String, saints: List<SaintEntry> = SaintDirectory): List<SaintEntry> {
    val needle = query.trim().fold()
    return saints.filter { it.name.fold().contains(needle) }
}

private fun String.fold(): String =
    Normalizer.normalize(this, Normalizer.Form.NFD)
        .filter { it.category != CharCategory.NON_SPACING_MARK }
        .lowercase()
