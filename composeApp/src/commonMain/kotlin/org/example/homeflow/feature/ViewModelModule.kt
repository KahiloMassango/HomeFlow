package org.example.homeflow.feature

import org.example.homeflow.feature.add_task.AddTaskViewModel
import org.example.homeflow.feature.authentication.LoginViewModel
import org.example.homeflow.feature.edit_task.EditTaskViewModel
import org.example.homeflow.feature.home.HomeViewModel
import org.example.homeflow.feature.tasks.TasksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::LoginViewModel)
    viewModelOf(::HomeViewModel)
    viewModelOf(::TasksViewModel)
    viewModelOf(::AddTaskViewModel)
    viewModelOf(::EditTaskViewModel)
}