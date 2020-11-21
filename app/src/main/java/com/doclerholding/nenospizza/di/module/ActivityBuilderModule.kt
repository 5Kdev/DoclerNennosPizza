package com.doclerholding.nenospizza.di.module

import com.doclerholding.nenospizza.ui.*
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilderModule {

    @ContributesAndroidInjector
    fun contributeBaseActivity(): BaseActivity

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    fun contributePizzaEditorActivity(): PizzaEditorActivity

    @ContributesAndroidInjector
    fun contributeCartActivity(): CartActivity

    @ContributesAndroidInjector
    fun contributeDrinksActivity(): DrinksActivity
}