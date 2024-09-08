//@file:kotlin.OptIn(ExperimentalMaterial3Api::class)

package com.example.florecer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.OptIn
import androidx.annotation.StringRes
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.florecer.model.Consejo
import com.example.florecer.model.Consejos
import com.example.florecer.ui.theme.FlorecerTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlorecerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
                    FlorecerApp()
                }
            }
        }
    }
}

@Composable
fun FlorecerApp(){
    Scaffold(
        topBar = {
            FlorecerTopAppBar()
        }
    ) { paddingValues ->
        LazyColumn(contentPadding = paddingValues) {
            val consejos = Consejos.consejos
            items(consejos.indices.toList()) { index ->
                val advice = consejos[index]
                AdviceItem(
                    dayNumber = index+1,
                    advice = advice,
                    modifier = Modifier
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        }

    }
}


@kotlin.OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlorecerTopAppBar(modifier: Modifier = Modifier) {
    CenterAlignedTopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    style = MaterialTheme.typography.displayLarge
                )
                Image(
                    // https://www.freepik.es/icono/flor_3025706#fromView=search&page=1&position=49&uuid=caf09bfe-dfea-444e-9fc3-b00f219f7813
                    painter = painterResource(id = R.drawable.flower_3025706),
                    contentDescription = null,
                    modifier = Modifier
                        .size(dimensionResource(id = R.dimen.image_size))
                        .padding(dimensionResource(id = R.dimen.padding_small))
                )
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceBright
        ),
        modifier = modifier
    )
}

@Composable
fun AdviceItem(
    dayNumber: Int,
    advice: Consejo,
    modifier: Modifier = Modifier
){
    var expanded by remember {
        mutableStateOf(false)
    }
    val color by animateColorAsState(
        targetValue = if (expanded) MaterialTheme.colorScheme.tertiaryContainer
        else MaterialTheme.colorScheme.secondaryContainer,
        label = "AdviceItemBackgroundColor"
    )
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .animateContentSize(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioNoBouncy,
                        stiffness = Spring.StiffnessMedium
                    )
                )
                .background(color = color)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(id = R.dimen.padding_small))
            ) {
                AdviceIcon(adviceIcon = advice.imagenId)
                AdviceDescription(
                    dia = "Día $dayNumber",
                    advice = advice.tituloId,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(0.2f)
                )
                Spacer(modifier = Modifier.weight(1f))
                AdviceButton(
                    expanded = expanded,
                    onClick = { expanded = !expanded },
                    modifier = Modifier.wrapContentWidth()
                )
            }
            if (expanded) {
                AdviceMoreInformation(
                    detail = advice.descripcionId,
                    modifier = Modifier.padding(
                        start = dimensionResource(id = R.dimen.padding_medium),
                        top = dimensionResource(id = R.dimen.padding_small),
                        end = dimensionResource(id = R.dimen.padding_medium),
                        bottom = dimensionResource(id = R.dimen.padding_medium)
                    )
                )
            }
        }
    }
}


@Composable
fun AdviceIcon(
    @DrawableRes adviceIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        painter = painterResource(adviceIcon),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(dimensionResource(id = R.dimen.image_size))
            .padding(dimensionResource(id = R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small)
    )
}

@Composable
fun AdviceButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            contentDescription = stringResource(id = R.string.boton_expandido),
            tint = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
fun AdviceDescription(
    dia: String,
    @StringRes advice: Int,
    modifier: Modifier = Modifier
){
    Column {
        Text(
            text = dia,
            style = MaterialTheme.typography.displayMedium,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = stringResource(id = advice),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
fun AdviceMoreInformation(
    @StringRes detail: Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(detail),
            style = MaterialTheme.typography.bodyLarge
        )

    }
}


@Preview(showBackground = true)
@Composable
fun FlorecerLightThemePreview() {
    FlorecerTheme(darkTheme = false) {
        FlorecerApp()
    }
}

@Preview
@Composable
fun FlorecerDarkThemePreview() {
    FlorecerTheme(darkTheme = true) {
        FlorecerApp()
    }
}