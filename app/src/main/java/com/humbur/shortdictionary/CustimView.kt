package com.humbur.shortdictionary

import android.content.Context
import android.util.LongSparseArray
import android.view.View
import android.view.translation.ViewTranslationResponse

class CustimView(context: Context) : View(context) {
    override fun onVirtualViewTranslationResponses(response: LongSparseArray<ViewTranslationResponse>) {
        super.onVirtualViewTranslationResponses(response)
    }
}