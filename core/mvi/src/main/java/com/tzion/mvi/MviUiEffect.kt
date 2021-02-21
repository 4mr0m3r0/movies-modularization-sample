package com.tzion.mvi

import com.tzion.mvi.events.MviEffect

interface MviUiEffect<in TUiEffect : MviEffect> {

    fun handleEffect(uiEffect: TUiEffect)

}