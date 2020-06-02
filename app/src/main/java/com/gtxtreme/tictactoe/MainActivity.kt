package com.gtxtreme.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var player1 = arrayListOf<Int>() //Set of all cells that player 1 selects
    var player2 = arrayListOf<Int>()
    var activePlayer = 1
    val cross=R.drawable.x
    val nut = R.drawable.nut
    var winner = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        currentUserTurnTxt.text=getString(R.string.user_info_text)
    }

    fun cellClick(view:View){
        val cellSelected = view as AppCompatImageView
        var cellId=0
        when(cellSelected.id){
            R.id.img1->{
                cellId=1
                img1.tag = "Checked"
            }
            R.id.img2->{
                img2.tag="Checked"
                cellId=2
            }
            R.id.img3->{
                img3.tag="Checked"
                cellId=3
            }
            R.id.img4->{
                img4.tag="Checked"
                cellId=4
            }
            R.id.img5->{
                img5.tag="Checked"
                cellId=5
            }
            R.id.img6->{
                img6.tag="Checked"
                cellId=6
            }
            R.id.img7->{
                cellId=7
                img7.tag="Checked"

            }
            R.id.img8->{
                img8.tag="Checked"
                cellId=8
            }
            R.id.img9->{
                img9.tag="Checked"
                cellId=9
            }
        }

        playGame(cellId,cellSelected)
    }

    fun isBoardFull():Boolean{
        return img1.tag=="Checked" && img2.tag=="Checked" && img3.tag=="Checked" && img4.tag=="Checked" && img5.tag=="Checked" && img6.tag=="Checked" && img7.tag=="Checked" && img8.tag=="Checked" && img9.tag=="Checked"
    }
    fun playGame(cellId:Int,cellSelected:AppCompatImageView){
        activePlayer = if(activePlayer == 1){
            cellSelected.setImageDrawable(getDrawable(cross))
            //cellSelected.setBackgroundColor(resources.getColor(R.color.colorAccent))
            player1.add(cellId)
            currentUserTurnTxt.text=getString(R.string.player2_turn_text)
            2
        } else{
            cellSelected.setImageDrawable(getDrawable(nut))
            //cellSelected.setBackgroundColor(resources.getColor(R.color.colorAccent))
            player2.add(cellId)
            currentUserTurnTxt.text=getString(R.string.player1_turn_text)
            1
        }
        cellSelected.isEnabled = false

        checkTheWinner()
    }

     fun checkTheWinner() {
         val player1wins =(player1.contains(1) && player1.contains(2) && player1.contains(3))||(player1.contains(4) && player1.contains(5) && player1.contains(6)) ||((player1.contains(7) && player1.contains(8) && player1.contains(9))) ||(player1.contains(1) && player1.contains(4) && player1.contains(7)) ||(player1.contains(2) && player1.contains(5) && player1.contains(8)) ||(player1.contains(3) && player1.contains(6) && player1.contains(9))||(player1.contains(1) && player1.contains(5) && player1.contains(9))||(player1.contains(3) && player1.contains(5) && player1.contains(7))
         val player2wins =(player2.contains(1) && player2.contains(2) && player2.contains(3))||(player2.contains(4) && player2.contains(5) && player2.contains(6))||((player2.contains(7) && player2.contains(8) && player2.contains(9))) ||(player2.contains(1) && player2.contains(4) && player2.contains(7)) ||(player2.contains(2) && player2.contains(5) && player2.contains(8)) ||(player2.contains(3) && player2.contains(6) && player2.contains(9))||(player2.contains(1) && player2.contains(5) && player1.contains(9))||(player2.contains(3) && player2.contains(5) && player2.contains(7))
        if(player1wins){
            currentUserTurnTxt.text=getString(R.string.player1wins)
            Log.d("TicTacToe","Player 1 wins")
        }
         else if (player2wins){
            currentUserTurnTxt.text=getString(R.string.player2wins)
            Log.d("TicTacToe","Player 2 wins")
        }
         else if(isBoardFull() && (!player1wins && !player2wins)) {
            currentUserTurnTxt.text=getString(R.string.draw)
            Log.d("TicTacToe","Draw")
        }
    }


}
