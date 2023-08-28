package com.bera.techconferenceapp.presentation.events.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SortDropDown(
    viewModel: EventViewModel,
    state: EventListState,
    modifier: Modifier = Modifier,
    eventType: EventType
) {
    ExposedDropdownMenuBox(
        modifier = modifier,
        expanded = state.isDropDownExpanded,
        onExpandedChange = { (viewModel::onDropDownClick)(it, eventType) }
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {},
            enabled = false,
            readOnly = true,
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor()
        )
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = state.sortBy.name, style = MaterialTheme.typography.labelSmall)
            Icon(
                modifier = Modifier.width(14.dp),
                imageVector = if (state.isDropDownExpanded)
                    Icons.Default.KeyboardArrowDown
                else
                    Icons.Default.KeyboardArrowUp,
                contentDescription = null
            )
        }
        ExposedDropdownMenu(
            expanded = state.isDropDownExpanded,
            onDismissRequest = { (viewModel::onDropDownClick)(false, eventType) }) {
            SortBy.values().forEach {
                DropdownMenuItem(
                    text = { Text(text = it.name) },
                    onClick = {
                        (viewModel::onSortByChange)(it, eventType)
                        (viewModel::onDropDownClick)(false, eventType)
                    }
                )
            }
        }
    }
}