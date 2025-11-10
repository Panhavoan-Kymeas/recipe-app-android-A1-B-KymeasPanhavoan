package com.example.cooksy.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    var pageIndex by remember { mutableIntStateOf(0) }
    val currentPage = onBoardingPages[pageIndex]

    var navigateToHome by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(Modifier.height(16.dp))

        // 📸 Image
        Image(
            painter = painterResource(id = currentPage.image),
            contentDescription = null,
            modifier = Modifier
                .size(250.dp)
                .padding(16.dp)
        )

        // 📝 Title + Description
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = currentPage.title,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = currentPage.description,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 16.dp),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }

        // 🔘 Page Indicators
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(onBoardingPages.size) { index ->
                val color =
                    if (index == pageIndex) MaterialTheme.colorScheme.primary else Color.LightGray
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(color)
                        .padding(4.dp)
                )
                Spacer(Modifier.width(6.dp))
            }
        }

        // Button
        Button(
            onClick = {
                if (pageIndex < onBoardingPages.size - 1) {
                    pageIndex++
                } else {
                    onFinish()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (pageIndex == onBoardingPages.lastIndex) "Get Started" else "Next")
        }

    }
}
