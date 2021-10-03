package com.example.debuggingchallenge

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener

class MainActivity : AppCompatActivity() {
    private lateinit var llMain: LinearLayout
    private lateinit var userName: EditText
    private lateinit var textLength: TextView
    private lateinit var noNumbers: TextView
    private lateinit var noSpacesOrSpecial: TextView

    private lateinit var password: EditText
    private lateinit var passwordLength: TextView
    private lateinit var hasUppercase: TextView
    private lateinit var hasNumber: TextView
    private lateinit var hasSpecial: TextView
    private lateinit var password2: EditText

    private lateinit var submitBtn: Button
    private lateinit var activeUsers: TextView

    private var users = arrayListOf(
        "Freddy",
        "Jason",
        "Ripley",
        "Poncho",
        "Saitama",
        "Archer",
        "Derek",
        "Pamela",
        "Simba",
        "Simon",
        "Retsy",
        "Peter",
        "Daria",
        "Smitty"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llMain = findViewById(R.id.llMain)
        userName = findViewById(R.id.etUsername)
        textLength = findViewById(R.id.tvTextLength)
        noNumbers = findViewById(R.id.tvNoNumbers)
        noSpacesOrSpecial = findViewById(R.id.tvNoSpacesOrSpecial)

        password = findViewById(R.id.etPassword)
        passwordLength = findViewById(R.id.tvPasswordLength)
        hasUppercase = findViewById(R.id.tvHasUppercase)
        hasNumber = findViewById(R.id.tvHasNumber)
        hasSpecial = findViewById(R.id.tvHasSpecial)
        password2 = findViewById(R.id.etConfirmPassword)

        submitBtn = findViewById(R.id.btSubmit)
        submitBtn.setOnClickListener {
            if(usernameAccepted(userName.text.toString()) && passwordAccepted(password.text.toString())){
                Toast.makeText(this, "User created!", Toast.LENGTH_LONG).show()
                users.add(userName.text.toString().capitalize())
                displayUsers()
            }
        }
        activeUsers = findViewById(R.id.tvActiveUsers)

        //Dynamically change the color of the required fields when the user TYPING
        userName.addTextChangedListener {
            val text = userName.text.toString()
            if(text.length in 5..15) textLength.setTextColor(Color.GREEN)
            else textLength.setTextColor(Color.RED)

            if(!hasNumber(text)) noNumbers.setTextColor(Color.GREEN)
            else noNumbers.setTextColor(Color.RED)

            if(!hasSpecial(text) && !text.contains(" ")) noSpacesOrSpecial.setTextColor(Color.GREEN)
            else noSpacesOrSpecial.setTextColor(Color.RED)
        }

        password.addTextChangedListener {
            val text = password.text.toString()
            if(text.length >= 8) passwordLength.setTextColor(Color.GREEN)
            else passwordLength.setTextColor(Color.RED)

            if(hasUpper(text)) hasUppercase.setTextColor(Color.GREEN)
            else hasUppercase.setTextColor(Color.RED)

            if(hasNumber(text)) hasNumber.setTextColor(Color.GREEN)
            else hasNumber.setTextColor(Color.RED)

            if(hasSpecial(text)) hasSpecial.setTextColor(Color.GREEN)
            else hasSpecial.setTextColor(Color.RED)
        }
        // --------------------------------------------------------------------------
    }

    private fun usernameAccepted(text: String): Boolean{
        if(text.capitalize() !in users){
            if(text.length in 5..15){
                if(!hasNumber(text)){
                    if(!hasSpecial(text) && !text.contains(" ")){
                        return true
                    }else Toast.makeText(this, "The username cannot contain special characters or spaces", Toast.LENGTH_LONG).show()
                }else Toast.makeText(this, "The username cannot contain numbers", Toast.LENGTH_LONG).show()
            }else Toast.makeText(this, "The username must be between 5 and 15 characters long", Toast.LENGTH_LONG).show()
        }else Toast.makeText(this, "The username is already taken", Toast.LENGTH_LONG).show()
        return false
    }

    private fun passwordAccepted(text: String): Boolean{
        if(text.length >= 8){
            if(hasUpper(text)){
                if(hasNumber(text)){
                    if(hasSpecial(text)){
                        if(AreTheyIdentical()){
                            return true
                        }else Toast.makeText(this, "The passwords must be identical", Toast.LENGTH_LONG).show()
                    }else Toast.makeText(this, "The password must contain a special character", Toast.LENGTH_LONG).show()
                }else Toast.makeText(this, "The password must contain a number", Toast.LENGTH_LONG).show()
            }else Toast.makeText(this, "The password must contain an uppercase letter", Toast.LENGTH_LONG).show()
        }else Toast.makeText(this, "The password must be at least 8 characters long", Toast.LENGTH_LONG).show()
        return false
    }

    private fun hasUpper(text: String): Boolean{
        for (l in text){
            if(l.isUpperCase()){
                return true
            }
        }
        return false
    }

    private fun hasNumber(text: String): Boolean{
        for(i in 0..9){
            if(text.contains(i.toString())){
                return true
            }
        }
        return false
    }

    private fun hasSpecial(text: String): Boolean{
        val specialCharacters = "!@#$%"
        for(special in specialCharacters){
            if(text.contains(special)){
                return true
            }
        }
        return false
    }

    private fun AreTheyIdentical(): Boolean{
        if(password.text.toString() == password2.text.toString()){
            return true
        }
        return false
    }

    private fun displayUsers(){
        var allUsers = "Active Users:\n\n"
        for(user in users){
            allUsers += user + "\n"
        }
        llMain.isVisible = false
        activeUsers.text = allUsers
        activeUsers.isVisible = true
    }
}