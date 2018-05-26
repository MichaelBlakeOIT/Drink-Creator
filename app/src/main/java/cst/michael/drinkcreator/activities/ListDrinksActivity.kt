package cst.michael.drinkcreator.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import cst.michael.drinkcreator.R

import kotlinx.android.synthetic.main.activity_list_drinks.*
import java.util.*
import com.firebase.ui.auth.ErrorCodes
import android.app.Activity
import android.view.Menu
import com.firebase.ui.auth.IdpResponse
import android.view.MenuItem
import android.app.AlertDialog


private val RC_SIGN_IN = 123

class ListDrinksActivity : AppCompatActivity() {
    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_drinks)

        title = "Drinks"

        tabs?.addTab(tabs.newTab().setText("Newest"))
        tabs?.addTab(tabs.newTab().setText("Top"))
        tabs?.addTab(tabs.newTab().setText("Liked"))

        auth = FirebaseAuth.getInstance()

        setAddDrinkListener()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            // Successfully signed in
            if (resultCode == Activity.RESULT_OK) {

                intent = Intent(this, CreateDrinkActivity::class.java)
            } else {
                // Sign in failed
                if (response == null) {
                    // User pressed back button

                    return
                }

                if (response.error!!.errorCode == ErrorCodes.NO_NETWORK) {

                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_screen_options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.sign_out -> {
                signOut()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun signOut() {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    setAddDrinkListener()
                }
    }

    private fun setAddDrinkListener() {
        fab.setOnClickListener {
            if (auth.currentUser == null) {
                promptLogin()
            }
            else {
                intent = Intent(this, CreateDrinkActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun promptLogin() {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Creating a drink requires logging in.")
                .setPositiveButton("Login", { dialog, id ->
                    leaveToLoginScreen()
                })
                .setNegativeButton("Cancel", { dialog, id ->
                    dialog.cancel()
                })
        builder.create().show()
    }

    private fun leaveToLoginScreen() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Arrays.asList(
                                AuthUI.IdpConfig.EmailBuilder().build(),
                                AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setTheme(R.style.AppTheme)
                        .build(),
                RC_SIGN_IN)
    }
}
