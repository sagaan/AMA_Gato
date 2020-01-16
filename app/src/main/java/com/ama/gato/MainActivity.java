package com.ama.gato;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //P1 = false, P2 = true
    private boolean turno = true;
    private int finish;

    private ImageButton[][] buttons;
    private int imgEmpty, imgO, imgX;
    private TextView txtP1;
    private TextView txtP2;
    private Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        finish = 0;

        buttons = new ImageButton[3][3];
        imgEmpty = R.drawable.empty;
        imgO = R.drawable.o;
        imgX = R.drawable.x;

        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                String btnId = "btn"+x+y;
                int id = getResources().getIdentifier(btnId, "id", getPackageName());
                buttons[x][y] = findViewById(id);
                buttons[x][y].setOnClickListener(this);
                buttons[x][y].setImageResource(imgEmpty);
                buttons[x][y].setTag(imgEmpty);
            }
        }

        btnReset = findViewById(R.id.btnReset);
        btnReset.setOnClickListener(this);

        txtP1 = findViewById(R.id.txtP1);
        txtP2 = findViewById(R.id.txtP2);

        txtP1.setBackgroundColor(getResources().getColor(R.color.BGP1));
        txtP1.setTextColor(getResources().getColor(R.color.BGWhite));
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btnReset){
            ((Activity)MainActivity.this).recreate();
        }else {
            ImageButton boton = (ImageButton) v;
            if ((int) boton.getTag() == imgEmpty) {
                if (turno) {
                    boton.setImageResource(imgX);
                    boton.setTag(imgX);
                } else {
                    boton.setImageResource(imgO);
                    boton.setTag(imgO);
                }

                int winner = win();
                if(winner == -1) {
                    turnChanger(turno);
                    turno = !turno;
                    finish++;
                    if (finish == 9) {
                        btnReset.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "Se acabaron las casillas, empate", Toast.LENGTH_SHORT).show();
                    }//end if
                }else{
                    if(winner==imgX){
                        Toast.makeText(MainActivity.this, "Gano el jugador 1", Toast.LENGTH_LONG).show();
                        disable();
                    }else if(winner == imgO){
                        Toast.makeText(MainActivity.this, "Gano el jugador 2", Toast.LENGTH_LONG).show();
                        disable();
                    }//end else-if
                }//end else-if
            } else
                Toast.makeText(this, "Elige otra casilla", Toast.LENGTH_LONG).show();
        }//end else-if
    }//end onClick()

    public void turnChanger(boolean turno){
        if(turno){
            txtP1.setTextColor(getResources().getColor(R.color.BGttt));
            txtP1.setBackgroundColor(getResources().getColor(R.color.BGWhite));
            txtP2.setBackgroundColor(getResources().getColor(R.color.BGP2));
            txtP2.setTextColor(getResources().getColor(R.color.BGWhite));
        }else{
            txtP1.setBackgroundColor(getResources().getColor(R.color.BGP1));
            txtP1.setTextColor(getResources().getColor(R.color.BGWhite));
            txtP2.setTextColor(getResources().getColor(R.color.BGttt));
            txtP2.setBackgroundColor(getResources().getColor(R.color.BGWhite));
        }//end else-if
    }//end turnChanger()

    public int win(){
        //GANADORES
        //-1 = Nadie
        //R.drawable.x = P1
        //R.drawable.o = P2

        for(int x=0; x<3; x++){
            if((int)buttons[x][0].getTag() == (int)buttons[x][1].getTag()
            && (int)buttons[x][0].getTag() == (int)buttons[x][2].getTag()
            && (int)buttons[x][0].getTag() != imgEmpty){
                disable();
                paint(buttons[x][0], buttons[x][1], buttons[x][2]);
                return (int)buttons[x][0].getTag();
            }
        }//end for(x) renglones

        for(int y=0; y<3; y++){
            if((int)buttons[0][y].getTag() == (int)buttons[1][y].getTag()
                    && (int)buttons[0][y].getTag() == (int)buttons[2][y].getTag()
                    && (int)buttons[0][y].getTag() != imgEmpty){
                disable();
                paint(buttons[0][y], buttons[1][y], buttons[2][y]);
                return (int)buttons[0][y].getTag();
            }
        }//end for(y) columnas

        if((int) buttons[0][0].getTag() == (int) buttons[1][1].getTag()
                && (int) buttons[0][0].getTag() == (int) buttons[2][2].getTag()
                && (int) buttons[0][0].getTag() != imgEmpty){
            disable();
            paint(buttons[0][0], buttons[1][1], buttons[2][2]);
            return (int)buttons[0][0].getTag();
        }//end if diagonal

        if((int)buttons[0][2].getTag() == (int)buttons[1][1].getTag()
                && (int)buttons[0][2].getTag() == (int)buttons[2][0].getTag()
                && (int) buttons[0][2].getTag() != imgEmpty){
            disable();
            paint(buttons[0][2], buttons[1][1], buttons[2][0]);
            return (int)buttons[0][2].getTag();
        }//end if diagonal invertida

        return -1;
    }//end win()

    private void disable(){
        btnReset.setVisibility(View.VISIBLE);
        for(int x=0; x<3; x++){
            for(int y=0; y<3; y++){
                buttons[x][y].setEnabled(false);
            }//end for(y)
        }//end for(x)
    }//end disable

    private void paint(ImageButton btn1, ImageButton btn2, ImageButton btn3){
        btn1.setBackgroundColor(getResources().getColor(R.color.BGWinner));
        btn2.setBackgroundColor(getResources().getColor(R.color.BGWinner));
        btn3.setBackgroundColor(getResources().getColor(R.color.BGWinner));
    }//end paint()

}
