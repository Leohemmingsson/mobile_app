package com.ltu.m7019e.mobile_app.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ltu.m7019e.mobile_app.utils.countryMap
import com.ltu.m7019e.mobile_app.viewmodel.NewsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    onUpdateSuccess: () -> Unit,
    newsViewModel: NewsViewModel
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var newPassword by remember { mutableStateOf("") }
    var selectedCountryName by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }

    var errorMessage by remember { mutableStateOf("") } // State variable to hold the error message

    val countryNames = countryMap.keys.toList()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally // Center align content horizontally
    ) {

        Text(
            text = newsViewModel.loggedInUser,
            fontSize = 30.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Column(
            modifier = Modifier.padding(bottom = 36.dp)
        ) {
            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                    }
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            )

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                TextField(
                    value = selectedCountryName,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("New Country") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .menuAnchor()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    countryNames.forEach { countryName ->
                        DropdownMenuItem(
                            text = { Text(countryName) },
                            onClick = {
                                selectedCountryName = countryName
                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        // Display error message if password field is empty
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Button(
            onClick = {
                if (newPassword.isNotEmpty()) {
                    val countryCode = countryMap[selectedCountryName] ?: ""
                    CoroutineScope(Dispatchers.Main).launch {
                        newsViewModel.updateProfile(newPassword, countryCode)
                        onUpdateSuccess()
                    }
                } else {
                    // Set error message if password field is empty
                    errorMessage = "Please fill in all fields."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Text("Update")
        }
    }
}
