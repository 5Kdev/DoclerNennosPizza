package com.doclerholding.nenospizza.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat.animate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.repository.CartRepository
import com.doclerholding.nenospizza.data.repository.PizzaRepository
import com.doclerholding.nenospizza.ui.viewmodels.CartViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_cart.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var pizzaRepository: PizzaRepository
    @Inject
    lateinit var cartRepository: CartRepository

    lateinit var baseActivity: BaseActivity
    lateinit var dialog: AlertDialog

    lateinit var cartModel: CartViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartModel = ViewModelProviders
            .of(this, CartViewModel.ViewModelFactory())
            .get(CartViewModel::class.java)
    }

    fun cartOnClick(){
        val intent = Intent(this, CartActivity::class.java)
        startActivity(intent)
    }


    override fun onPause() {
        cartRepository.saveCartData(this.getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE).edit());
        super.onPause()
    }

    fun addToCartAnimation(v: View) {
        v.visibility = View.VISIBLE

        v.animate()
                .alpha(0f)
                .setDuration(3000)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        v.visibility = View.GONE
                    }
                })
    }

    fun showError(key: String?) {
        val messageToShow: String = key!!
        showSnackbar(messageToShow)
    }

    fun showError(resId: Int) {
        val messageToShow: String = getString(resId)
        showSnackbar(messageToShow)
    }

    fun showSnackbar(messageToShow: String) {
        val rootView: View = window.decorView.findViewById(R.id.content)
        Snackbar.make(rootView, messageToShow, Snackbar.LENGTH_LONG).show()
    }

    fun setToolBar(toolbarTitle: String, activity: MainActivity) {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = toolbarTitle
            elevation = 15F

            cartBadge.visibility = View.VISIBLE
            toolbarImageBtn.visibility = View.VISIBLE

            toolbarImageBtn.setImageResource(R.drawable.cart)
        }
    }

    fun setToolBar(toolbarTitle: String, activity: PizzaEditorActivity) {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = toolbarTitle
            elevation = 15F
            setDisplayHomeAsUpEnabled(true)

            cartBadge.visibility = View.GONE
            toolbarImageBtn.visibility = View.GONE

        }
    }

    fun setToolBar(toolbarTitle: String, activity: CartActivity) {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = toolbarTitle
            elevation = 15F
            setDisplayHomeAsUpEnabled(true)

            cartBadge.visibility = View.GONE
            toolbarImageBtn.visibility = View.VISIBLE

            toolbarImageBtn.setImageResource(R.drawable.drinks)
        }
    }

    fun setToolBar(toolbarTitle: String, activity: DrinksActivity) {
        setSupportActionBar(toolbar)

        supportActionBar?.apply {
            title = toolbarTitle
            elevation = 15F
            setDisplayHomeAsUpEnabled(true)

            cartBadge.visibility = View.GONE
            toolbarImageBtn.visibility = View.GONE
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        TODO("Not yet implemented")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}