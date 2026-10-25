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

package br.com.paroquiasantacabrini.app.core.designsystem.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * An illustrated, tappable banner: a darkened photo with a left-to-right scrim and a short
 * eyebrow, title and line of text at the bottom (`feature-banner` in the prototype).
 */
@Composable
fun FeatureBanner(
    @DrawableRes imageRes: Int,
    eyebrow: String,
    title: String,
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(BANNER_HEIGHT)
            .clip(BannerShape)
            .clickable(role = Role.Button, onClick = onClick)
            .semantics(mergeDescendants = true) {},
    ) {
        // The text beside the photo already describes the banner, so the photo is decorative.
        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
            contentScale = ContentScale.Crop,
        )
        Box(modifier = Modifier.matchParentSize().background(Color.Black.copy(alpha = IMAGE_DIM)))
        Box(
            modifier = Modifier
                .matchParentSize()
                .background(Brush.horizontalGradient(0f to BANNER_SCRIM, SCRIM_END to Color.Transparent)),
        )
        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, end = 16.dp, bottom = 14.dp),
        ) {
            Text(
                text = eyebrow.uppercase(),
                style = MaterialTheme.typography.labelSmall,
                color = BANNER_EYEBROW,
            )
            Text(
                text = title,
                modifier = Modifier.padding(top = 5.dp, bottom = 3.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Normal,
                color = BANNER_TEXT,
            )
        }
    }
}

private val BannerShape = RoundedCornerShape(20.dp)
private val BANNER_HEIGHT = 154.dp
private const val IMAGE_DIM = 0.23f
private const val SCRIM_END = 0.95f
private val BANNER_SCRIM = Color(0xC91D3027)
private val BANNER_EYEBROW = Color(0xFFF1E6CE)
private val BANNER_TEXT = Color(0xFFF6F4ED)
