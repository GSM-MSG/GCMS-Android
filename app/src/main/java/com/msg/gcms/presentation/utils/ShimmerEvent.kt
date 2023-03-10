package com.msg.gcms.presentation.utils

import androidx.core.view.isVisible
import com.facebook.shimmer.ShimmerFrameLayout

fun ShimmerFrameLayout.start() {
    startShimmer()
    isVisible = true
}

fun ShimmerFrameLayout.stop() {
    stopShimmer()
    isVisible = false
}