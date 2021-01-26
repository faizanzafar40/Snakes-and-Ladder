package com.example.user.snakesandladder;

//Android Libraries
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

//Java Libraries
import java.util.ArrayList;
import java.lang.String;

@SuppressWarnings("ALL")

//Main game activity
public class HomeActivity extends AppCompatActivity {

    private ArrayList<Game> Snakes = new ArrayList<>(); //Array of Snake Objects
    private ArrayList<Game> Ladders = new ArrayList<>(); //Array if Ladder Objects
    private int Player; //User Player
    private int AI; //Computer Player
    private int ActivePlayer; //Player currently playing the game

    private boolean GameOver = false; //To check status of game
    private Point SIZEOFBOARD=new Point(); //Single graphical point on the snakes and ladders board
    private ImageView d; //Image object for dice manipulation

    //Game class for Game objects
    private class Game {
        private int Head; //Head value of snake or ladder
        private int Tail; //Tail value of snake or ladder

        private Game(int h, int t) {
            this.Head = h; //Head of current Game object
            this.Tail = t; //Tail of current Game object
        }

        public int getHead() {
            return Head;
        }

        public int getTail() {
            return Tail;
        }

    }

    //OnCreate Method
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        d = (ImageView) findViewById(R.id.dice); //Stores current ImageView of dice in variable

        //Ladders Game object instantiated with tail and head values
        Ladders.add(new Game(28, 7));
        Ladders.add(new Game(79, 21));
        Ladders.add(new Game(68, 12));
        Ladders.add(new Game(46, 16));
        Ladders.add(new Game(64, 38));
        Ladders.add(new Game(85, 68));
        Ladders.add(new Game(97, 78));

        //Snakes Game object instantiated with head and tail values
        Snakes.add(new Game(27, 6));
        Snakes.add(new Game(42, 18));
        Snakes.add(new Game(45, 24));
        Snakes.add(new Game(52, 31));
        Snakes.add(new Game(67, 35));
        Snakes.add(new Game(76, 44));
        Snakes.add(new Game(82, 63));
        Snakes.add(new Game(94, 35));
        Snakes.add(new Game(82, 63));
        Snakes.add(new Game(96, 77));

        //Current position of all players at 1 before game starts
        Player = 1;
        ActivePlayer = 1;
        AI = 1;

        //Function for obtaining the height of the gameboard and storing it in a graphics point variable
        getWindowManager().getDefaultDisplay().getSize(SIZEOFBOARD);
        findViewById(R.id.gameboard).post(new Runnable() {
            @Override
            public void run() {
                SIZEOFBOARD.y=findViewById(R.id.gameboard).getHeight();
            }
        });
    }


    //Methid for obtaning current value of dice upon roll
    public int getDice() {
        int d = (int) (Math.random() * 6) + 1; //Dice values between 1 and 6
        switch (d) {
            case 1: //for 1
                this.d.setImageResource(R.drawable.d1);
                break;
            case 2: //for 2
                this.d.setImageResource(R.drawable.d2);
                break;
            case 3: //for 3
                this.d.setImageResource(R.drawable.d3);
                break;
            case 4://for 4
                this.d.setImageResource(R.drawable.d4);
                break;
            case 5: //for 5
                this.d.setImageResource(R.drawable.d5);
                break;
            case 6: //for 6
                this.d.setImageResource(R.drawable.d6);
                break;
        }
        return d; //returns the value upon roll
    }


    //Function for rolling dice and playing your turn on the gameboard
    public void playTurn(View V) {
        if (!GameOver) {

            int getDiceValue = getDice();

            if(ActivePlayer==1) {

                userTurn(getDiceValue);
            }
        }
    }

    //Turn of user
    public void userTurn(int d) {
        int CurrentPosition = Player; //Position of player is the current position

        Player += d; //Position of player incremented by value of dice

        if (Player == 100) { //Game over when position of player equals 100
            Toast.makeText(this, "Player wins", Toast.LENGTH_SHORT).show(); //Toast message notification
            GameOver = true;
        } else if (Player > 100) {
            Player -= d; //No change in position if position greater than 100

            AITurn(getDice()); //Turn of computer when player cannot play turn

        } else {
            for (Game Snake : Snakes) {
                if (Snake.getHead() == Player) {
                  Player = Snake.getTail(); //When player positions at head of snake, position changed to tail of snake
                }
            }
            for (Game Ladder : Ladders) {
                if (Ladder.getTail() == Player) {
                    Player = Ladder.getHead(); //When player positions at tail of ladder, position changed to head of ladder

                    userTurn(getDice()); //One more turn when computer climbs ladder
                }
            }
        }
        ((TextView) findViewById(R.id.playerposition)).setText("Player 1"); //User in action

        move(R.id.yellowpieceonboard,CurrentPosition,Player); //Move user player piece to number of steps on dice

        if(getDice()==6){
            userTurn(getDice()); //One more turn for the user when dice value equals 6

        }
    }

    //Turn of computer
    public void AITurn(int d) {
        int CurrentPosition = AI; //Position of computer is the current position

        AI += d; //Position of computer incremented by value of dice

        if (AI == 100) {
            Toast.makeText(this, "Computer wins", Toast.LENGTH_SHORT).show(); //Game over when position of player equals 100, toast message notification
            GameOver = true;
        } else if (AI > 100) {
            AI -= d; //No change in position if position greater than 100

            userTurn(getDice()); //Turn of user when computer cannot play turn


        } else {
            for (Game Snake : Snakes) {
                if (Snake.getHead() == AI) {
                    AI = Snake.getTail(); //When computer positions at head of snake, position changed to tail of snake

                }
            }
            for (Game Ladder : Ladders) {
                if (Ladder.getTail() == AI) {
                    AI = Ladder.getHead(); //When computer positions at tail of ladder, position changed to head of ladder

                    AITurn(getDice()); //One more turn when computer climbs ladder
                }
            }
        }
        ((TextView) findViewById(R.id.computerposition)).setText("Computer 1"); //Computer in action

        move(R.id.redpieceonboard,CurrentPosition,AI); //Move computer player piece to number of steps on dice

        if(getDice()==6){
            AITurn(getDice()); //One more turn for the computer when dice value equals 6

        }
    }


    //Function for obtaining the horizontal X position on gameboard
    private int getXPosition(int p){

        int column=p%10; //gameboard divided into 10 columns

        int row=p/10; //gameboard divided into 10 rows

        if((p%10)==0) {

            column=10;

        }
        else {
            return column;
        }

        if((p%10)==0) {

            row=row-1; //move up one row
        }
        else {
            return row;
        }

        if(row%2!=0){ //game occurs in one half of the gameboard

            column=10-column;
        }
        else

        {
            column=column-1; //move up one column
        }

        return (int) (column / 10.f * SIZEOFBOARD.x); //returns column X position on gameboard
    }

    //Function for obtaining the horizontal X position on gameboard

    private int getYPosition(int p) {

        int row=p/10; //gameboard divided into 10 rows

        if((p%10)==0){

            return row;
        }
        else{

            row=row+1; //move down one row
        }

        return SIZEOFBOARD.y -(int) (row/10.f * SIZEOFBOARD.x);  //returns row Y position on gameboard
    }

    //Function for moving player positions on gameboard

    private void move(int id, int currentPosition, int newPosition){
        TextView message;
        message= (TextView) findViewById((R.id.status));
        message.setText(String.valueOf(ActivePlayer)+"Rolling dice..."); //Current action on display

        int X1= getXPosition(currentPosition); //X coordinate of current position of player
        int Y1=getYPosition(currentPosition); //Y coordinate of current position of player
        int X2=getXPosition(newPosition); //X coordinate of new position of player
        int Y2=getYPosition(newPosition);//Y coordinate of new position of player

        //Animation function for translation along defined points
        TranslateAnimation animation=new TranslateAnimation(X1,X2,Y1,Y2);
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener(){

            @Override
            public void onAnimationStart(Animation animation) {
                TextView message;
                message= (TextView) findViewById((R.id.status));
                message.setText("Moving Player"+String.valueOf(ActivePlayer));
            }

           @Override
            public void onAnimationEnd(Animation animation){
                if(ActivePlayer==1){
                    ActivePlayer=2;
                    ((TextView) findViewById(R.id.status)).setText("Computer's turn");
                     AITurn(getDice()); //Turn of computer
                }

                else if (ActivePlayer==2) {
                    ((TextView) findViewById(R.id.status)).setText("Player's turn");
                }
                else if(GameOver){
                    ((TextView) findViewById(R.id.status)).setText("Game Over");
                }
            }
            //Repeat animation for every user and computer turn
            @Override
            public void onAnimationRepeat(Animation animation){

            }
        });
        animation.setFillAfter(true);

        findViewById(id).startAnimation(animation); //initiate animation
    }
}