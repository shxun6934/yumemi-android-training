package jp.co.yumemi.ui.weather

import androidx.compose.foundation.layout.Row
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import jp.co.yumemi.ui.R

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
