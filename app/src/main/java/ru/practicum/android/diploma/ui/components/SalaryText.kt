package ru.practicum.android.diploma.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import ru.practicum.android.diploma.R


@Composable
fun SalaryText(from: Int?, to: Int?, symbol: String?, style: TextStyle) {
    Text(
        text = getSalaryText(from, to, symbol),
        style = style,
        color = colorResource(R.color.text)
    )
}

@Composable
private fun getSalaryText(from: Int?, to: Int?, symbol: String?): String {
    val fromText = stringResource(R.string.from)
    val toText = stringResource(R.string.to)
    val noSalaryText = stringResource(R.string.no_salary)

    return when {
        from != null && to != null && symbol != null ->
            "$fromText $from $toText $to $symbol"

        from != null && symbol != null ->
            "$fromText $from $symbol"

        to != null && symbol != null ->
            "$toText $to $symbol"

        else -> noSalaryText
    }
}
