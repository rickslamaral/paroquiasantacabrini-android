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

package br.com.paroquiasantacabrini.app

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import br.com.paroquiasantacabrini.app.home.navigation.Screen
import com.android.tools.screenshot.PreviewTest

@PreviewTest
@Preview(name = "liturgy", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppLiturgyPreview() {
    AppAt(Screen.DailyLiturgy)
}

@PreviewTest
@Preview(name = "notices", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppNoticesPreview() {
    AppAt(Screen.Notices)
}

@PreviewTest
@Preview(name = "prayer-request", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppPrayerRequestPreview() {
    AppAt(Screen.PrayerRequest)
}

@PreviewTest
@Preview(name = "rosary", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppRosaryPreview() {
    AppAt(Screen.Rosary)
}

@PreviewTest
@Preview(name = "novenas", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppNovenasPreview() {
    AppAt(Screen.Novenas)
}

@PreviewTest
@Preview(name = "exam", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppExamPreview() {
    AppAt(Screen.Exam)
}

@PreviewTest
@Preview(name = "saints", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppSaintsPreview() {
    AppAt(Screen.Saints)
}

@PreviewTest
@Preview(name = "community", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppCommunityPreview() {
    AppAt(Screen.Community)
}

@PreviewTest
@Preview(name = "contact", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppContactPreview() {
    AppAt(Screen.Contact)
}

@PreviewTest
@Preview(name = "groups", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppGroupsPreview() {
    AppAt(Screen.Groups)
}

@PreviewTest
@Preview(name = "catechesis", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppCatechesisPreview() {
    AppAt(Screen.Catechesis)
}

@PreviewTest
@Preview(name = "confirmation", widthDp = PHONE_WIDTH, heightDp = PHONE_HEIGHT, locale = "pt-rBR")
@Composable
public fun AppConfirmationPreview() {
    AppAt(Screen.Confirmation)
}
