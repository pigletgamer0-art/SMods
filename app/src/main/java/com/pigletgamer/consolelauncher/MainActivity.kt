package com.pigletgamer.consolelauncher

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SportsEsports
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Background = Color(0xFF101820)
private val SurfaceColor = Color(0xFF1D2B38)
private val Muted = Color(0xFF9FB2C2)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { ConsoleLauncherApp() }
    }
}

private enum class Section(val label: String) {
    HOME("Inicio"), GAMES("Juegos"), APPS("Aplicaciones"), SETTINGS("Configuración")
}

@Composable
private fun ConsoleLauncherApp() {
    var section by remember { mutableStateOf(Section.HOME) }
    var guideOpen by remember { mutableStateOf(false) }
    var accent by remember { mutableStateOf(Color(0xFF55A7E8)) }

    MaterialTheme(colorScheme = darkColorScheme(
        primary = accent,
        background = Background,
        surface = SurfaceColor,
        onBackground = Color.White,
        onSurface = Color.White
    )) {
        Box(
            Modifier.fillMaxSize()
                .background(Background)
                .onPreviewKeyEvent { event ->
                    if (event.type == KeyEventType.KeyDown && event.key == Key.Home) {
                        guideOpen = !guideOpen
                        true
                    } else false
                }
        ) {
            Column(Modifier.fillMaxSize().padding(horizontal = 22.dp, vertical = 18.dp)) {
                Header()
                Spacer(Modifier.height(22.dp))
                Navigation(section) { section = it }
                Spacer(Modifier.height(22.dp))
                when (section) {
                    Section.HOME -> HomeScreen(accent) { guideOpen = true }
                    Section.GAMES -> GamesScreen()
                    Section.APPS -> AppsScreen()
                    Section.SETTINGS -> SettingsScreen(accent) { accent = it }
                }
            }
            if (guideOpen) {
                QuickGuide(
                    onClose = { guideOpen = false },
                    onSelect = { section = it; guideOpen = false }
                )
            }
        }
    }
}

@Composable
private fun Header() {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
        Column {
            Text("CONSOLE", fontSize = 24.sp, fontWeight = FontWeight.Black, letterSpacing = 2.sp)
            Text("LAUNCHER", color = Muted, fontSize = 11.sp, letterSpacing = 4.sp)
        }
        Text("ANDROID  •  GAME MODE", color = Muted, fontSize = 10.sp)
    }
}

@Composable
private fun Navigation(selected: Section, onSelect: (Section) -> Unit) {
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Section.entries.forEach { item ->
            Surface(
                color = if (selected == item) Color(0xFF29445A) else SurfaceColor,
                shape = RoundedCornerShape(13.dp),
                modifier = Modifier.clickable { onSelect(item) }
            ) {
                Text(item.label, Modifier.padding(horizontal = 13.dp, vertical = 11.dp),
                    color = Color.White, fontWeight = if (selected == item) FontWeight.Bold else FontWeight.Medium,
                    fontSize = 12.sp)
            }
        }
    }
}

@Composable
private fun HomeScreen(accent: Color, openGuide: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(18.dp)) {
        Surface(color = SurfaceColor, shape = RoundedCornerShape(22.dp), modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.padding(22.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(9.dp)) {
                    Text("LISTO PARA JUGAR", color = accent, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
                    Text("Tu biblioteca.\nTodo en un lugar.", fontSize = 28.sp, lineHeight = 32.sp, fontWeight = FontWeight.Bold)
                    Text("Selecciona un juego para abrirlo.", color = Muted, fontSize = 13.sp)
                    Button(onClick = openGuide) { Text("ABRIR GUÍA RÁPIDA") }
                }
                Icon(Icons.Default.SportsEsports, contentDescription = null, tint = accent, modifier = Modifier.size(76.dp))
            }
        }
        Text("TUS SECCIONES", color = Muted, fontSize = 11.sp, fontWeight = FontWeight.Bold, letterSpacing = 2.sp)
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Tile("Juegos", "Biblioteca", Modifier.weight(1f))
            Tile("Aplicaciones", "Android", Modifier.weight(1f))
        }
        Tile("Recientes", "Tus últimos juegos aparecerán aquí", Modifier.fillMaxWidth())
        Text("Primera versión en desarrollo", color = Muted, fontSize = 11.sp)
    }
}

@Composable
private fun Tile(title: String, subtitle: String, modifier: Modifier = Modifier) {
    Surface(modifier, color = SurfaceColor, shape = RoundedCornerShape(15.dp)) {
        Column(Modifier.padding(15.dp), verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(title, fontWeight = FontWeight.Bold)
            Text(subtitle, color = Muted, fontSize = 11.sp)
        }
    }
}

@Composable
private fun GamesScreen() {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("BIBLIOTECA DE JUEGOS", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        Text("Aquí reuniremos juegos Android y accesos configurados para emuladores.", color = Muted, fontSize = 13.sp)
        Tile("Todavía no hay juegos", "La importación de plataformas y carátulas llegará en una siguiente fase.", Modifier.fillMaxWidth())
    }
}

private data class InstalledApp(val label: String, val packageName: String)

@Composable
private fun AppsScreen() {
    val context = LocalContext.current
    var apps by remember { mutableStateOf(emptyList<InstalledApp>()) }
    LaunchedEffect(Unit) {
        val launcherIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        @Suppress("DEPRECATION")
        val resolved = context.packageManager.queryIntentActivities(launcherIntent, 0)
        apps = resolved.map {
            InstalledApp(it.loadLabel(context.packageManager).toString(), it.activityInfo.packageName)
        }.distinctBy { it.packageName }.sortedBy { it.label.lowercase() }
    }
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Text("APLICACIONES", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        Text("${apps.size} aplicaciones visibles para iniciar", color = Muted, fontSize = 12.sp)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(7.dp)) {
            items(apps, key = { it.packageName }) { app ->
                Surface(color = SurfaceColor, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().clickable {
                    val launch = context.packageManager.getLaunchIntentForPackage(app.packageName)
                    if (launch != null) context.startActivity(launch)
                }) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Apps, contentDescription = null, tint = Color(0xFF8FC7F2))
                        Spacer(Modifier.width(12.dp))
                        Text(app.label, Modifier.weight(1f), fontWeight = FontWeight.Medium)
                        Text("ABRIR", color = Muted, fontSize = 10.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}

@Composable
private fun SettingsScreen(accent: Color, onAccent: (Color) -> Unit) {
    val colors = listOf(
        Color(0xFF55A7E8), Color(0xFF70C98B), Color(0xFFB58AE8),
        Color(0xFFE5A45B), Color(0xFFE47791), Color(0xFF54C7C0)
    )
    Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
        Text("CONFIGURACIÓN", fontSize = 21.sp, fontWeight = FontWeight.Bold)
        Text("Color de acento", color = Muted)
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            colors.forEach { color ->
                Box(Modifier.size(38.dp).background(color, RoundedCornerShape(50))
                    .clickable { onAccent(color) }) {
                    if (accent == color) Text("✓", Modifier.align(Alignment.Center), color = Color.Black, fontWeight = FontWeight.Bold)
                }
            }
        }
        Tile("Dispositivos compatibles", "Retroid Pocket Flip 2 · AYN Thor", Modifier.fillMaxWidth())
        Text("Más opciones de personalización se añadirán durante el desarrollo.", color = Muted, fontSize = 12.sp)
    }
}

@Composable
private fun QuickGuide(onClose: () -> Unit, onSelect: (Section) -> Unit) {
    Row(Modifier.fillMaxSize().background(Color(0xAA000000))) {
        Surface(
            color = Color(0xFF182633),
            shape = RoundedCornerShape(topEnd = 20.dp, bottomEnd = 20.dp),
            modifier = Modifier.fillMaxHeight().widthIn(min = 260.dp, max = 320.dp)
        ) {
            Column(Modifier.fillMaxSize().padding(20.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("GUÍA RÁPIDA", fontSize = 20.sp, fontWeight = FontWeight.Black)
                Text("Accesos del sistema", color = Muted, fontSize = 12.sp)
                HorizontalDivider(color = Color(0xFF3B4B59))
                GuideButton("Inicio") { onSelect(Section.HOME) }
                GuideButton("Juegos") { onSelect(Section.GAMES) }
                GuideButton("Aplicaciones") { onSelect(Section.APPS) }
                GuideButton("Configuración") { onSelect(Section.SETTINGS) }
                Spacer(Modifier.weight(1f))
                GuideButton("Cerrar") { onClose() }
            }
        }
        Spacer(Modifier.weight(1f).fillMaxHeight().clickable { onClose() })
    }
}

@Composable
private fun GuideButton(label: String, onClick: () -> Unit) {
    Surface(color = SurfaceColor, shape = RoundedCornerShape(12.dp), modifier = Modifier.fillMaxWidth().clickable { onClick() }) {
        Text(label, Modifier.padding(15.dp), fontWeight = FontWeight.Medium)
    }
}
