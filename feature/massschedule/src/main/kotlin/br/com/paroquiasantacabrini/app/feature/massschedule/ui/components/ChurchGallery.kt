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

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import br.com.paroquiasantacabrini.app.feature.massschedule.R

internal data class ChurchPhoto(
    @get:DrawableRes internal val imageRes: Int,
    @get:StringRes internal val descriptionRes: Int,
)

/** More photos of the parish church, in display order. The facade is shown at the top of the screen. */
internal val ChurchPhotos: List<ChurchPhoto> = listOf(
    ChurchPhoto(R.drawable.feature_massschedule_church_celebration, R.string.feature_massschedule_photo_celebration),
    ChurchPhoto(R.drawable.feature_massschedule_church_altar, R.string.feature_massschedule_photo_altar),
)

@Composable
internal fun ChurchGallery(
    modifier: Modifier = Modifier,
) {
    // Photos scroll sideways while the page scrolls down.
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        items(items = ChurchPhotos, key = { it.imageRes }) { photo ->
            Image(
                painter = painterResource(photo.imageRes),
                contentDescription = stringResource(photo.descriptionRes),
                modifier = Modifier
                    .width(PHOTO_WIDTH)
                    .height(PHOTO_HEIGHT)
                    .clip(RoundedCornerShape(18.dp)),
                contentScale = ContentScale.Crop,
            )
        }
    }
}

private val PHOTO_WIDTH = 220.dp
private val PHOTO_HEIGHT = 150.dp
