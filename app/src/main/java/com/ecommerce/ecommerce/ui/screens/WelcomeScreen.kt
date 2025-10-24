package com.ecommerce.ecommerce.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ecommerce.ecommerce.ui.theme.Success
import com.ecommerce.ecommerce.ui.theme.SuccessDark
import com.ecommerce.ecommerce.ui.theme.SuccessMedium
import com.ecommerce.ecommerce.ui.theme.TextSecondary
import com.ecommerce.ecommerce.utils.SessionManager
import com.ecommerce.ecommerce.viewmodels.WelcomeViewModel


@Composable
fun WelcomeScreen(
    sessionManager: SessionManager,
    onLogout: () -> Unit,
    viewModel: WelcomeViewModel = viewModel { WelcomeViewModel(sessionManager) }
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFE8F5E8),
                        Color(0xFFF1F8E9)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Icono de éxito
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = null,
                modifier = Modifier.size(120.dp),
                tint = Success
            )


            Spacer(modifier = Modifier.height(32.dp))


            // Mensaje de bienvenida
            Text(
                text = if (viewModel.isAdmin) "¡Bienvenido Admin!" else "¡Bienvenido!",
                style = MaterialTheme.typography.headlineMedium,
                color = SuccessDark,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )


            Spacer(modifier = Modifier.height(16.dp))


            // Nombre del usuario
            Text(
                text = viewModel.userName,
                style = MaterialTheme.typography.titleLarge,
                color = SuccessMedium,
                textAlign = TextAlign.Center
            )


            Spacer(modifier = Modifier.height(24.dp))


            // Tipo de usuario
            Text(
                text = if (viewModel.isAdmin) "👑 Administrador" else "👤 Usuario Regular",
                style = MaterialTheme.typography.titleMedium,
                color = if (viewModel.isAdmin) Color(0xFFFF9800) else TextSecondary,
                textAlign = TextAlign.Center
            )


            Spacer(modifier = Modifier.height(32.dp))


            // Card con información de sesión
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Estado de la sesión",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )


                    Spacer(modifier = Modifier.height(8.dp))


                    Text(
                        text = "✓ Sesión iniciada correctamente",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Success
                    )


                    Spacer(modifier = Modifier.height(4.dp))


                    Text(
                        text = viewModel.loginTime,
                        style = MaterialTheme.typography.bodyMedium,
                        color = TextSecondary
                    )
                }
            }


            Spacer(modifier = Modifier.height(32.dp))


            // Botón Cerrar Sesión
            OutlinedButton(
                onClick = {
                    viewModel.logout()
                    onLogout()
                },
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(
                    text = "Cerrar Sesión",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
