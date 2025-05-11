package jp.co.yumemi.ui.weather

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import jp.co.yumemi.ui.R
import jp.co.yumemi.ui.design.WeatherTheme

@Composable
fun ActionButtons(
    modifier: Modifier = Modifier,
    onReload: () -> Unit,
    onNext: () -> Unit
) {
    Row(modifier = modifier) {
        Button(
            onClick = onReload,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = stringResource(id = R.string.RELOAD))
        }
        Button(
            onClick = onNext,
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = stringResource(id = R.string.NEXT))
        }
    }
}

@Preview
@Composable
private fun PreviewActionButtons() {
    WeatherTheme {
        ActionButtons(
            onReload = {},
            onNext = {}
        )
    }
}
