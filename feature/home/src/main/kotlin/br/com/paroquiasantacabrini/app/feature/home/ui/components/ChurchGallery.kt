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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.core.designsystem.theme.TemplateSpacing
import br.com.paroquiasantacabrini.app.feature.home.R

internal data class ChurchPhoto(
    @get:DrawableRes internal val imageRes: Int,
    @get:StringRes internal val descriptionRes: Int,
)

/** Photos of the parish church, in display order. */
internal val ChurchPhotos: List<ChurchPhoto> = listOf(
    ChurchPhoto(R.drawable.feature_home_church_facade, R.string.feature_home_photo_facade),
    ChurchPhoto(R.drawable.feature_home_church_celebration, R.string.feature_home_photo_celebration),
    ChurchPhoto(R.drawable.feature_home_church_altar, R.string.feature_home_photo_altar),
)

@Composable
internal fun ChurchGallery(
    modifier: Modifier = Modifier,
) {
    // Horizontal row inside the dashboard grid: photos scroll sideways, the page scrolls down.
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(TemplateSpacing.small),
    ) {
        items(items = ChurchPhotos, key = { it.imageRes }) { photo ->
            Card(modifier = Modifier.width(PHOTO_WIDTH).height(PHOTO_HEIGHT)) {
                Image(
                    painter = painterResource(photo.imageRes),
                    contentDescription = stringResource(photo.descriptionRes),
                    modifier = Modifier.width(PHOTO_WIDTH).height(PHOTO_HEIGHT),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

private val PHOTO_WIDTH = 160.dp
private val PHOTO_HEIGHT = 200.dp
