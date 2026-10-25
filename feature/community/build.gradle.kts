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
plugins {
    alias(libs.plugins.app.android.feature)
    alias(libs.plugins.app.android.compose.screenshot)
    alias(libs.plugins.app.android.library.jacoco)
    alias(libs.plugins.app.android.junit5)
    alias(libs.plugins.app.kotlin.explicit.visibility)
}

android {
    namespace = "br.com.paroquiasantacabrini.app.feature.community"
}

dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.foundation.layout)

    screenshotTestImplementation(project(":core:testing"))

    testImplementation(libs.truth)
}
