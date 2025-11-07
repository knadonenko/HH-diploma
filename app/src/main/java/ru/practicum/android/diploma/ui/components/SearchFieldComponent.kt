package ru.practicum.android.diploma.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.blue
import ru.practicum.android.diploma.ui.theme.grey500
import ru.practicum.android.diploma.ui.theme.padding16
import ru.practicum.android.diploma.ui.theme.padding8
import ru.practicum.android.diploma.ui.theme.searchFieldCorner
import ru.practicum.android.diploma.ui.theme.size60
import ru.practicum.android.diploma.ui.theme.size70

@Composable
fun SearchField(
    searchQuery: String,
    onQueryChange: (String) -> Unit,
    placeHolder: String,
    onSearchClear: () -> Unit
) {
    Row {
        BasicTextField(
            value = searchQuery,
            onValueChange = onQueryChange,
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTypography.current.body16Medium,
            cursorBrush = SolidColor(blue),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = {}
            ),
            decorationBox = { innerTextField ->
                TextFieldDefaults.DecorationBox(
                    value = searchQuery,
                    innerTextField = innerTextField,
                    singleLine = true,
                    enabled = true,
                    shape = RoundedCornerShape(searchFieldCorner),
                    visualTransformation = VisualTransformation.None,
                    interactionSource = remember { MutableInteractionSource() },
                    placeholder = {
                        Text(
                            text = placeHolder,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = LocalTypography.current.body16Medium,
                        )
                    },
                    trailingIcon = {
                        when (searchQuery.isEmpty()) {
                            true -> Icon(
                                painter = painterResource(id = R.drawable.ic_search),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground
                            )

                            false -> Icon(
                                modifier = Modifier
                                    .clickable(onClick = onSearchClear),
                                painter = painterResource(id = R.drawable.ic_cross),
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    },
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = Transparent,
                        unfocusedIndicatorColor = Transparent,
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                    ),
                )
            }
        )
    }
}

private const val WEIGHT_1F = 1F

@Composable
fun MoneyField(
    salary: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(size60)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant,
                shape = RoundedCornerShape(searchFieldCorner)
            )
            .padding(start = padding16),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(WEIGHT_1F)) {
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.filter_expected_salary),
                style = LocalTypography.current.body12Regular,
                color = grey500,
                overflow = TextOverflow.Ellipsis,
            )
            BasicTextField(
                value = salary,
                onValueChange = { },
                modifier = Modifier.fillMaxWidth(),
                textStyle = LocalTypography.current.body16Medium,
                cursorBrush = SolidColor(blue),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {}
                ),
                decorationBox = { innerTextField ->
                    TextFieldDefaults.DecorationBox(
                        value = salary,
                        innerTextField = innerTextField,
                        singleLine = true,
                        enabled = true,
                        shape = RoundedCornerShape(searchFieldCorner),
                        visualTransformation = VisualTransformation.None,
                        contentPadding = PaddingValues(0.dp),
                        interactionSource = remember { MutableInteractionSource() },
                        placeholder = {
                            Text(
                                text = "Введите сумму",
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                style = LocalTypography.current.body16Regular,
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedIndicatorColor = Transparent,
                            unfocusedIndicatorColor = Transparent,
                            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                    )
                }
            )
        }
    }
}
