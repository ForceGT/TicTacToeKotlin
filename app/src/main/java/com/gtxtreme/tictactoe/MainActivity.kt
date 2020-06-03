package com.gtxtreme.tictactoe

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.res.ResourcesCompat
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    var style: Int = 0
    var player1 = arrayListOf<Int>() //Set of all cells that player 1 selects
    var player2 = arrayListOf<Int>()
    var activePlayer = 1
    var cross: Drawable? = null
    var nut: Drawable? = null
    var userEmail: String? = null
    private var database = FirebaseDatabase.getInstance()
    private var myRef = database.reference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentUserTurnTxt.text = getString(R.string.user_info_text)
        val colorList = arrayListOf("color1", "color2", "color3", "color4", "color5")
        val rnds = (0..4).random()

        val b: Bundle? = intent.extras
        userEmail = b!!.getString("userEmail")
        var selectedColor = colorList[rnds]
        assignColortoSymbol(selectedColor)
        incomingCalls()

    }

    fun assignColortoSymbol(selectedColor: String?) {
        val theme = resources.newTheme()
        style = when (selectedColor) {
            "color1" -> R.style.ColorPickerStyle1
            "color2" -> R.style.ColorPickerStyle2
            "color3" -> R.style.ColorPickerStyle3
            "color4" -> R.style.ColorPickerStyle4
            "color5" -> R.style.ColorPickerStyle5
            else -> R.style.DefaultColorPickerStyle
        }
        theme.applyStyle(style, false)
        cross = ResourcesCompat.getDrawable(resources, R.drawable.x, theme)
        nut = ResourcesCompat.getDrawable(resources, R.drawable.nut, theme)
        myRef.child("sessions").child(sessionID).child("color").setValue(selectedColor)
    }

    fun cellClick(view: View) {
        val cellSelected = view as AppCompatImageView
        var cellId = 0
        when (cellSelected.id) {
            R.id.img1 -> {
                cellId = 1
                img1.tag = "Checked"
            }
            R.id.img2 -> {
                img2.tag = "Checked"
                cellId = 2
            }
            R.id.img3 -> {
                img3.tag = "Checked"
                cellId = 3
            }
            R.id.img4 -> {
                img4.tag = "Checked"
                cellId = 4
            }
            R.id.img5 -> {
                img5.tag = "Checked"
                cellId = 5
            }
            R.id.img6 -> {
                img6.tag = "Checked"
                cellId = 6
            }
            R.id.img7 -> {
                cellId = 7
                img7.tag = "Checked"

            }
            R.id.img8 -> {
                img8.tag = "Checked"
                cellId = 8
            }
            R.id.img9 -> {
                img9.tag = "Checked"
                cellId = 9
            }
        }

//        playGame(cellId, cellSelected)
        myRef.child("sessions").child(sessionID).child(cellId.toString()).setValue(userEmail)
    }

    fun isBoardFull(): Boolean {
        return img1.tag == "Checked" && img2.tag == "Checked" && img3.tag == "Checked" && img4.tag == "Checked" && img5.tag == "Checked" && img6.tag == "Checked" && img7.tag == "Checked" && img8.tag == "Checked" && img9.tag == "Checked"
    }

    fun autoPlay(cellId: Int) {
        var imgSelected: AppCompatImageView?
        when (cellId) {
            1 -> imgSelected = img1
            2 -> imgSelected = img2
            3 -> imgSelected = img3
            4 -> imgSelected = img4
            5 -> imgSelected = img5
            6 -> imgSelected = img6
            7 -> imgSelected = img7
            8 -> imgSelected = img8
            9 -> imgSelected = img9
            else -> {
                imgSelected = img1
            }
        }

        playGame(cellId, imgSelected)
    }

    fun playGame(cellId: Int, cellSelected: AppCompatImageView) {


        activePlayer = if (activePlayer == 1) {
            cellSelected.setImageDrawable(cross)
            player1.add(cellId)
            currentUserTurnTxt.text = getString(R.string.player2_turn_text)
            2
        } else {
            cellSelected.setImageDrawable(nut)
            player2.add(cellId)
            currentUserTurnTxt.text = getString(R.string.player1_turn_text)
            1
        }
        cellSelected.isEnabled = false
        checkTheWinner()
    }

    fun checkTheWinner() {
        val player1wins =
            (player1.contains(1) && player1.contains(2) && player1.contains(3)) || (player1.contains(
                4
            ) && player1.contains(5) && player1.contains(6)) || ((player1.contains(7) && player1.contains(
                8
            ) && player1.contains(9))) || (player1.contains(1) && player1.contains(4) && player1.contains(
                7
            )) || (player1.contains(2) && player1.contains(5) && player1.contains(8)) || (player1.contains(
                3
            ) && player1.contains(6) && player1.contains(9)) || (player1.contains(1) && player1.contains(
                5
            ) && player1.contains(9)) || (player1.contains(3) && player1.contains(5) && player1.contains(
                7
            ))
        val player2wins =
            (player2.contains(1) && player2.contains(2) && player2.contains(3)) || (player2.contains(
                4
            ) && player2.contains(5) && player2.contains(6)) || ((player2.contains(7) && player2.contains(
                8
            ) && player2.contains(9))) || (player2.contains(1) && player2.contains(4) && player2.contains(
                7
            )) || (player2.contains(2) && player2.contains(5) && player2.contains(8)) || (player2.contains(
                3
            ) && player2.contains(6) && player2.contains(9)) || (player2.contains(1) && player2.contains(
                5
            ) && player1.contains(9)) || (player2.contains(3) && player2.contains(5) && player2.contains(
                7
            ))
        if (player1wins) {
            currentUserTurnTxt.text = getString(R.string.player1wins)
            disableAllCells()
        } else if (player2wins) {
            currentUserTurnTxt.text = getString(R.string.player2wins)
            disableAllCells()
        } else if (isBoardFull() && (!player1wins && !player2wins)) {
            currentUserTurnTxt.text = getString(R.string.draw)
            Log.d("TicTacToe", "Draw")
        }
    }

    fun disableAllCells() {
        img9.isEnabled = false
        img8.isEnabled = false
        img7.isEnabled = false
        img6.isEnabled = false
        img5.isEnabled = false
        img4.isEnabled = false
        img3.isEnabled = false
        img2.isEnabled = false
        img1.isEnabled = false
        btnRestart.visibility = View.VISIBLE
    }

    fun restartGame(view: View) {

    }

    fun request(view: View) {
        var email = invite_edit_text.text.toString()
        myRef.child("Users").child(splitString(email)).child("Request").push().setValue(userEmail)
        sessions( splitString(email)+userEmail?.let { splitString(it) } )
        playerSymbol = "X"
    }

    fun incomingCalls() {
        myRef.child("Users").child(splitString(userEmail.toString())).child("Request").addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                var number = 0
                override fun onDataChange(p0: DataSnapshot) {
                    try {
                        val td = p0.value as HashMap<String, Any>
                        if (td != null) {
                            var value: String
                            for (key in td.keys) {
                                value = td[key] as String
                                // TODO Add to invitation
                                val notifyMe = Notifications()
                                notifyMe.Notfy(applicationContext,value + " wants to play TicTacToe",number)
                                number++
                                myRef.child("Users").child(splitString(userEmail!!)).child("Request")
                                    .setValue("Honored:Now playing with "+splitString(value))
                                break
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            }
        )
    }

    fun accept(view: View) {
        var email = invite_edit_text.text.toString()
        myRef.child("Users").child(splitString(email)).child("Request").push().setValue(userEmail)
        sessions(userEmail?.let { splitString(it) } + splitString(email) )
        playerSymbol = "O"
    }

    var sessionID: String = ""
    var playerSymbol: String? = null
    fun sessions(sessionID: String) {
        this.sessionID = sessionID
        myRef.child("sessions").child(sessionID).addValueEventListener(
            object : ValueEventListener {
                override fun onCancelled(p0: DatabaseError) {

                }

                override fun onDataChange(p0: DataSnapshot) {
                    try {
                        player1.clear()
                        player2.clear()
                        val td = p0.value as HashMap<String, Any>
                        if (td != null) {
                            var value: String
                            for (key in td.keys) {
                                value = td[key] as String
                                // TODO Add to invitation
                                invite_edit_text.setText(userEmail)
                                if (value != userEmail?.let { splitString(it) }) {
                                    activePlayer = if (playerSymbol === "X") 1 else 2
                                } else {
                                    activePlayer = if (playerSymbol === "O") 2 else 1
                                }
                                autoPlay(key.toInt())
                            }
                        }
                    } catch (e: Exception) {
                    }
                }
            })
    }

    fun splitString(email:String):String{
        return email.split("@")[0]
    }
}
