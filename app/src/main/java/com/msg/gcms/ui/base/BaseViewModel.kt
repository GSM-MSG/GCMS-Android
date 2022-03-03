package com.msg.gcms.ui.base

import androidx.lifecycle.ViewModel
import java.util.logging.ErrorManager
import javax.inject.Inject

class BaseViewModel : ViewModel() {
    @Inject
    lateinit var errorManager: ErrorManager

}