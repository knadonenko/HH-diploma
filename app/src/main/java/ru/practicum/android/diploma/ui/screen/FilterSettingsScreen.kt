package ru.practicum.android.diploma.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.presentation.filters.viewmodel.FilterSettingsViewModel
import ru.practicum.android.diploma.ui.components.FilterItem
import ru.practicum.android.diploma.ui.components.topbars.FilterTopBar
import ru.practicum.android.diploma.ui.theme.LocalCustomColors
import ru.practicum.android.diploma.ui.theme.LocalTypography
import ru.practicum.android.diploma.ui.theme.blue
import ru.practicum.android.diploma.ui.theme.cornerRadius
import ru.practicum.android.diploma.ui.theme.padding0
import ru.practicum.android.diploma.ui.theme.padding16
import ru.practicum.android.diploma.ui.theme.paddingBase
import ru.practicum.android.diploma.ui.theme.red
import ru.practicum.android.diploma.ui.theme.searchFieldCorner
import ru.practicum.android.diploma.ui.theme.size18
import ru.practicum.android.diploma.ui.theme.size2
import ru.practicum.android.diploma.ui.theme.size60
import ru.practicum.android.diploma.ui.theme.size8
import ru.practicum.android.diploma.ui.theme.white

@Composable
fun FilterSettingsScreen(
    modifier: Modifier,
    onBackClick: (Boolean) -> Unit,
    toFilterWorkPlace: () -> Unit,
    toFilterIndustry: () -> Unit,
    viewModel: FilterSettingsViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) { viewModel.loadFilterSettings() }

    Scaffold(
        modifier = modifier,
        topBar = {
            FilterTopBar(
                stringResource(id = R.string.top_bar_label_filter_settings)
            ) { onBackClick(false) }
        }
    ) { padding ->
        Column(
            modifier = modifier
                .padding(padding)
                .fillMaxSize()
                .padding(paddingBase),
        ) {
            val areaName = viewModel.areaName.collectAsStateWithLifecycle().value
            val industryName = viewModel.industryName.collectAsStateWithLifecycle().value
            val salary = viewModel.salary.collectAsStateWithLifecycle().value
            val onlyWithSalary = viewModel.onlyWithSalary.collectAsStateWithLifecycle().value
            val hasSettings = viewModel.hasSettings.collectAsStateWithLifecycle().value
            val hasSettingsChange = viewModel.hasSettingsChange.collectAsStateWithLifecycle().value

            val isAreaSelected = !areaName.isNullOrEmpty()
            val isIndustrySelected = !industryName.isNullOrEmpty()

            FilterItem(
                modifier = Modifier.clickable(onClick = toFilterWorkPlace),
                stringResource(R.string.filter_work_place_label),
                composableElement = {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                onClick = if (isAreaSelected) {
                                    viewModel::onClearArea
                                } else {
                                    toFilterWorkPlace
                                }
                            )
                            .height(size18),
                        imageVector = if (isAreaSelected) {
                            Icons.Filled.Clear
                        } else {
                            Icons.AutoMirrored.Filled.ArrowForwardIos
                        },
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                        contentDescription = "Arrow Forward"
                    )
                },
                color = LocalCustomColors.current.text.secondaryTextColors.textColor
            )
            FilterItem(
                modifier = Modifier.clickable(onClick = toFilterIndustry),
                stringResource(R.string.filter_industry_label),
                composableElement = {
                    Icon(
                        modifier = Modifier
                            .clickable(
                                onClick = if (isIndustrySelected) {
                                    viewModel::onClearIndustry
                                } else {
                                    toFilterIndustry
                                }
                            )
                            .height(size18),
                        imageVector = if (isIndustrySelected) {
                            Icons.Filled.Clear
                        } else {
                            Icons.AutoMirrored.Filled.ArrowForwardIos
                        },
                        tint = LocalCustomColors.current.icons.defaultIconColors,
                        contentDescription = "Arrow Forward"
                    )
                },
                color = LocalCustomColors.current.text.secondaryTextColors.textColor
            )
            Spacer(modifier = Modifier.height(paddingBase))
            MoneyField(
                salary ?: "",
                viewModel::onChangeSalary
            ) { viewModel.onChangeSalary(null) }
            val checked = onlyWithSalary
            FilterItem(
                modifier = Modifier.padding(top = padding16),
                stringResource(R.string.filter_without_salary),
                composableElement = {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = { value ->
                            viewModel.onChangeOnlyWithSalary(value)
                        },
                        colors = CheckboxDefaults.colors(
                            uncheckedColor = blue,
                            checkedColor = blue,
                            checkmarkColor = white
                        )
                    )
                },
                color = LocalCustomColors.current.text.primaryTextColors.textColor
            )

            Spacer(modifier = Modifier.weight(WEIGHT_1F))

            if (hasSettingsChange) {
                Button(
                    modifier = Modifier
                        .height(size60)
                        .padding(horizontal = padding16)
                        .fillMaxSize(),
                    shape = RoundedCornerShape(cornerRadius),
                    onClick = { onBackClick(true) },
                    content = {
                        Text(
                            text = stringResource(R.string.filter_apply_button),
                            style = LocalTypography.current.body16Medium
                        )
                    }
                )
            } else {
                Spacer(modifier = Modifier.height(size60))
            }

            Spacer(modifier = Modifier.height(size8))

            if (hasSettings) {
                Button(
                    modifier = Modifier
                        .height(size60)
                        .padding(horizontal = padding16)
                        .fillMaxSize(),
                    shape = RoundedCornerShape(cornerRadius),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = red,
                        containerColor = Transparent
                    ),
                    onClick = viewModel::onClearAll,
                    content = {
                        Text(
                            text = stringResource(R.string.filter_reset_button),
                            style = LocalTypography.current.body16Medium
                        )
                    }
                )
            }
        }
    }
}

private const val WEIGHT_1F = 1F

@Composable
fun MoneyField(
    salary: String,
    onChangeSalary: (String) -> Unit,
    onSalaryClear: () -> Unit
) {
    var isFocused by remember { mutableStateOf(false) }

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
            Spacer(modifier = Modifier.height(size2))
            Text(
                text = stringResource(R.string.filter_expected_salary),
                style = LocalTypography.current.body12Regular,
                color = if (isFocused) {
                    blue
                } else {
                    MaterialTheme.colorScheme.onSurfaceVariant
                },
                overflow = TextOverflow.Ellipsis,
            )
            BasicTextField(
                value = salary,
                onValueChange = { newText ->
                    onChangeSalary(
                        newText
                            .filter { it.isDigit() }
                            .removePrefix("0")
                            .ifEmpty { "" }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged { isFocused = it.isFocused },
                textStyle = LocalTypography.current.body16Medium,
                cursorBrush = SolidColor(blue),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done, keyboardType = KeyboardType.Number),
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
                        contentPadding = PaddingValues(padding0),
                        interactionSource = remember { MutableInteractionSource() },
                        placeholder = {
                            Text(
                                text = stringResource(R.string.filter_placeholder),
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
        if (!salary.isEmpty()) {
            Icon(
                modifier = Modifier
                    .padding(end = 20.dp)
                    .clickable(onClick = onSalaryClear),
                painter = painterResource(id = R.drawable.ic_cross),
                contentDescription = "Cross",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}
