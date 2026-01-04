package edu.watumull.presencify.core.design.systems.components

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.resources.Font
import presencify.core.design_systems.generated.resources.Res
import presencify.core.design_systems.generated.resources.poppins_bold
import presencify.core.design_systems.generated.resources.poppins_bold_italic
import presencify.core.design_systems.generated.resources.poppins_extra_bold
import presencify.core.design_systems.generated.resources.poppins_extra_bold_italic
import presencify.core.design_systems.generated.resources.poppins_extra_light
import presencify.core.design_systems.generated.resources.poppins_extra_light_italic
import presencify.core.design_systems.generated.resources.poppins_italic
import presencify.core.design_systems.generated.resources.poppins_light
import presencify.core.design_systems.generated.resources.poppins_light_italic
import presencify.core.design_systems.generated.resources.poppins_medium
import presencify.core.design_systems.generated.resources.poppins_medium_italic
import presencify.core.design_systems.generated.resources.poppins_regular
import presencify.core.design_systems.generated.resources.poppins_semi_bold
import presencify.core.design_systems.generated.resources.poppins_semi_bold_italic
import presencify.core.design_systems.generated.resources.poppins_thin
import presencify.core.design_systems.generated.resources.poppins_thin_italic

@Composable
fun fontFamily(): FontFamily = FontFamily(
    listOf(
        Font(Res.font.poppins_thin, FontWeight.Thin),
        Font(Res.font.poppins_thin_italic, FontWeight.Thin),

        Font(Res.font.poppins_extra_light, FontWeight.ExtraLight),
        Font(Res.font.poppins_extra_light_italic, FontWeight.ExtraLight),

        Font(Res.font.poppins_light, FontWeight.Light),
        Font(Res.font.poppins_light_italic, FontWeight.Light),

        Font(Res.font.poppins_regular, FontWeight.Normal),
        Font(Res.font.poppins_italic, FontWeight.Normal),

        Font(Res.font.poppins_medium, FontWeight.Medium),
        Font(Res.font.poppins_medium_italic, FontWeight.Medium),

        Font(Res.font.poppins_semi_bold, FontWeight.SemiBold),
        Font(Res.font.poppins_semi_bold_italic, FontWeight.SemiBold),

        Font(Res.font.poppins_bold, FontWeight.Bold),
        Font(Res.font.poppins_bold_italic, FontWeight.Bold),

        Font(Res.font.poppins_extra_bold, FontWeight.ExtraBold),
        Font(Res.font.poppins_extra_bold_italic, FontWeight.ExtraBold)
    )
)

@Composable
fun getTypography(): Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    displayLarge = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp
    ),
    displayMedium = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp
    ),
    displaySmall = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp
    ),
    labelLarge = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp
    ),
    titleLarge = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp
    ),
    titleMedium = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp
    ),
    titleSmall = TextStyle(
        fontFamily = fontFamily(),
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp
    ),
)