package com.doclerholding.nenospizza.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.doclerholding.nenospizza.R
import com.doclerholding.nenospizza.data.api.CheckoutApiService
import com.doclerholding.nenospizza.data.api.NennosApiService
import com.doclerholding.nenospizza.data.repository.CartRepository
import com.doclerholding.nenospizza.data.repository.PizzaRepository
import com.doclerholding.nenospizza.viewmodels.CartViewModel
import com.doclerholding.nenospizza.viewmodels.PizzasViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.toolbar
import javax.inject.Inject

open class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var apiService: NennosApiService
    @Inject
    lateinit var checkoutApiService: CheckoutApiService
    @Inject
    lateinit var pizzaRepository: PizzaRepository
    @Inject
    lateinit var cartRepository: CartRepository

    lateinit var cartModel: CartViewModel
    lateinit var pizzaModel: PizzasViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        cartModel = ViewModelProviders
            .of(this, CartViewModel.ViewModelFactory(checkoutApiService))
            .get(CartViewModel::class.java)

        pizzaModel = ViewModelProviders.of(this, PizzasViewModel.ViewModelFactory(apiService)).get(
            PizzasViewModel::class.java)

        cartRepository.viewmodel = cartModel

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
                        v.alpha = 1f
                    }
                })
    }


    fun showError(messageToShow: String) {
        showAlertDialog(messageToShow)
    }

    fun showSnackbar(messageToShow: String) {
        val rootView: View = window.decorView.findViewById(android.R.id.content)
        Snackbar.make(rootView, messageToShow, Snackbar.LENGTH_LONG).show()
    }

    fun showAlertDialog(messageToShow: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.dialogTitle)
        builder.setMessage(messageToShow)
        builder.setPositiveButton("OK"){dialogInterface, which ->
            dialogInterface.cancel()
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
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


    fun showProgress(status: Boolean) {
        if (status) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
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