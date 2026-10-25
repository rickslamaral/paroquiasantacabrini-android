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

package br.com.paroquiasantacabrini.app.core.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private fun style(
    size: Int,
    lineHeight: Int,
    weight: FontWeight,
    letterSpacing: Double = 0.0,
) = TextStyle(
    fontFamily = ParoquiaSansFontFamily,
    fontSize = size.sp,
    lineHeight = lineHeight.sp,
    fontWeight = weight,
    letterSpacing = letterSpacing.sp,
)

/**
 * Sizes follow the prototype's CSS (h1 27, page title 23, h2 19, card title 15, text 13-14, small 11,
 * eyebrow 10). They are `sp`, so they still scale with the reader's font size setting.
 */
val TemplateTypography = Typography(
    displayLarge = style(40, 44, FontWeight.ExtraBold, -1.5),
    displayMedium = style(34, 38, FontWeight.ExtraBold, -1.3),
    displaySmall = style(30, 34, FontWeight.ExtraBold, -1.2),
    headlineLarge = style(27, 30, FontWeight.ExtraBold, -1.2),
    headlineMedium = style(23, 26, FontWeight.ExtraBold, -1.0),
    headlineSmall = style(20, 24, FontWeight.Bold, -0.6),
    titleLarge = style(19, 24, FontWeight.Bold, -0.5),
    titleMedium = style(15, 20, FontWeight.Bold),
    titleSmall = style(14, 19, FontWeight.Bold),
    bodyLarge = style(14, 22, FontWeight.Normal),
    bodyMedium = style(13, 20, FontWeight.Normal),
    bodySmall = style(11, 16, FontWeight.Normal),
    labelLarge = style(13, 18, FontWeight.Bold),
    labelMedium = style(11, 16, FontWeight.SemiBold),
    labelSmall = style(10, 14, FontWeight.ExtraBold, 1.3),
)
