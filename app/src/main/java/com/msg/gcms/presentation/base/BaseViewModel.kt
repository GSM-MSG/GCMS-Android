package com.msg.gcms.presentation.base

import androidx.lifecycle.ViewModel
import java.util.logging.ErrorManager
import javax.inject.Inject

abstract class BaseViewModel : ViewModel() {
    @Inject
    lateinit var errorManager: ErrorManager
}
