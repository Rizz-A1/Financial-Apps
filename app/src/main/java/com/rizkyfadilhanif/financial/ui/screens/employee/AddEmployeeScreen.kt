package com.rizkyfadilhanif.financial.ui.screens.employee

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.rizkyfadilhanif.financial.R
import com.rizkyfadilhanif.financial.ui.components.AppTopBar
import com.rizkyfadilhanif.financial.ui.components.GradientBackground
import com.rizkyfadilhanif.financial.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEmployeeScreen(
    viewModel: EmployeeViewModel,
    employeeId: Long? = null,
    onNavigateBack: () -> Unit
) {
    val formState by viewModel.formState.collectAsState()
    
    val title = if (employeeId != null && employeeId != 0L) {
        stringResource(R.string.edit_employee)
    } else {
        stringResource(R.string.add_employee)
    }
    
    LaunchedEffect(employeeId) {
        if (employeeId != null && employeeId != 0L) {
            viewModel.loadEmployee(employeeId)
        } else {
            viewModel.resetForm()
        }
    }
    
    LaunchedEffect(formState.isSuccess) {
        if (formState.isSuccess) {
            onNavigateBack()
        }
    }
    
    GradientBackground {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                AppTopBar(
                    title = title,
                    userName = "Rizky",
                    onNavigationClick = onNavigateBack,
                    navigationIcon = Icons.AutoMirrored.Filled.ArrowBack
                )
                
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            // Name field
                            OutlinedTextField(
                                value = formState.name,
                                onValueChange = { viewModel.updateName(it) },
                                label = { Text(stringResource(R.string.hint_employee_name)) },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Primary)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Primary,
                                    focusedLabelColor = Primary,
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = Color.White,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Position field
                            OutlinedTextField(
                                value = formState.position,
                                onValueChange = { viewModel.updatePosition(it) },
                                label = { Text(stringResource(R.string.hint_employee_position)) },
                                leadingIcon = {
                                    Icon(Icons.Default.Work, contentDescription = null, tint = Primary)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Primary,
                                    focusedLabelColor = Primary,
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = Color.White,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Salary field
                            OutlinedTextField(
                                value = formState.salary,
                                onValueChange = { viewModel.updateSalary(it) },
                                label = { Text(stringResource(R.string.hint_employee_salary)) },
                                leadingIcon = {
                                    Icon(Icons.Default.AttachMoney, contentDescription = null, tint = Primary)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Primary,
                                    focusedLabelColor = Primary,
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = Color.White,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Phone field
                            OutlinedTextField(
                                value = formState.phone,
                                onValueChange = { viewModel.updatePhone(it) },
                                label = { Text(stringResource(R.string.hint_employee_phone)) },
                                leadingIcon = {
                                    Icon(Icons.Default.Phone, contentDescription = null, tint = Primary)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Primary,
                                    focusedLabelColor = Primary,
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = Color.White,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Address field
                            OutlinedTextField(
                                value = formState.address,
                                onValueChange = { viewModel.updateAddress(it) },
                                label = { Text("Alamat") },
                                leadingIcon = {
                                    Icon(Icons.Default.Home, contentDescription = null, tint = Primary)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Primary,
                                    focusedLabelColor = Primary,
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = Color.White,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                            
                            Spacer(modifier = Modifier.height(16.dp))
                            
                            // Age field
                            OutlinedTextField(
                                value = formState.age,
                                onValueChange = { viewModel.updateAge(it) },
                                label = { Text("Umur") },
                                leadingIcon = {
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Primary)
                                },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true,
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                shape = RoundedCornerShape(12.dp),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = Primary,
                                    focusedLabelColor = Primary,
                                    unfocusedContainerColor = Color.White,
                                    focusedContainerColor = Color.White,
                                    focusedTextColor = Color.Black,
                                    unfocusedTextColor = Color.Black
                                )
                            )
                            
                            // Error message
                            formState.error?.let { error ->
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = error,
                                    color = Error,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            
                            Spacer(modifier = Modifier.height(32.dp))
                            
                            // Save button
                            Button(
                                onClick = { viewModel.saveEmployee() },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                enabled = !formState.isSaving,
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = Primary)
                            ) {
                                if (formState.isSaving) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.size(24.dp),
                                        color = Color.White
                                    )
                                } else {
                                    Text(
                                        text = stringResource(R.string.btn_save),
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
