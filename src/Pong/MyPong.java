package Pong;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.File;




/************************************************************************
 Made by        PatrickSys
 Date           05/02/2021
 Package        Pong
 Description:
 ************************************************************************/


public class MyPong extends Application {


    /**
     * Attributes
     */
    public static Pane canvas;
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final int radius = 10;
    private final int paddleHeight = 100;
    private final int paddleWidth = 10;
    private final int paddle1PosX = 20;
    private final int paddle2PosX = 760;
    private Label player1Points;
    private Label player2Points;







    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(final Stage primaryStage) {

        //create scene, with pane
        canvas = new Pane();
        final Scene scene = new Scene(canvas, WIDTH, HEIGHT);

        //create players with it's paddles
        Player player1 = new Player(canvas, paddle1PosX, paddleHeight, paddleWidth);
        Player player2 = new Player(canvas,paddle2PosX, paddleHeight, paddleWidth);


        primaryStage.setTitle("P O N G");
        canvas.setStyle("-fx-background-color: BLACK");
        primaryStage.setScene(scene);
        primaryStage.show();
        (int)(canvas.getWidth() / 2  - font.getSize()*2
        //player1Points = new Label("0");
        //player2Points = new Label("0");
        player1.settlePoints((int)(canvas.getWidth() / 2  - player1.getFont().getSize())*2,player1Points);
        player2.settlePoints((int)(canvas.getWidth() / 2  + .getSize()*2);

        //create the ball
        Ball ball = new Ball(canvas, radius, Color.BLUE);
        ball.circle.relocate((WIDTH /2)- radius, (HEIGHT /2)- radius);



        //add shapes to pane
        canvas.getChildren().addAll(ball.circle);
        canvas.getChildren().addAll(player1.paddle, player2.paddle);
        canvas.getChildren().addAll(player1.playerPoints, player2.playerPoints);
        canvas.requestFocus();

        //case on paddle moves
        canvas.setOnKeyPressed(e -> {

            switch (e.getCode()) {
                case W -> player1.paddle.moveUp();
                case S -> player1.paddle.moveDown();
                case UP -> player2.paddle.moveUp();
                case DOWN -> player2.paddle.moveDown();
            }
        });


        //thread main is calling moveBall while checking collisions between paddles and the ball
        final Timeline loop = new Timeline(new KeyFrame(Duration.millis(12), new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {

                play(ball, player1, player2);
                
                }
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }


    /**
     * Main method play, moves the ball while checking if a goal is scored
     * checks collisions
     */
    private void play(Ball ball, Player player1, Player player2){

        //invoke moveBall then play around it
        int scored = ball.moveBall();
        checkGoal(scored, ball);
        checkCollision(ball, player1.paddle);
        checkCollision(ball, player2.paddle);

    }

    /**
     *Checks collision between paddle and ball
     *if top or bottom of the paddle are hit, it only reverses Y
     */
    private void checkCollision(Ball ball, Paddle paddle){


        //if bounds intersect means they're colliding
        if(ball.circle.getBoundsInParent().intersects(paddle.paddle.getBoundsInParent())){

            //plays bounce sound
            File bounce = new File("C:\\Users\\bitaz\\IdeaProjects\\JavaFX\\src\\PongSounds\\PaddleBounce.wav");
            PongSounds.Sounds.playSound(bounce);

            //check if the ball touches paddle bot or top then reverse Y
            if(ball.circle.getLayoutX()<paddle1PosX+paddleWidth+ball.circle.getRadius() || ball.circle.getLayoutX() > paddle2PosX - ball.circle.getRadius()){
                System.out.println("TOCA ");
                ball.deltaY *= -1;
            }

            else{
                ball.deltaX *= -1;

            }

            //prints it
            ball.circle.setLayoutX(ball.circle.getLayoutX() + ball.deltaX);

        }
    }



    /**
     *Checks goal for play method. Remember scored was a return value
     *from checkBallBounds method in ball class
     *where return depends on whose player scored
     */
    private void checkGoal(int scored, Ball ball){

        if(scored==1){
            //System.out.println( "player1 goal");
            //Scene p1goal = new Scene(canvas, WIDTH, HEIGHT);
            try {
                Thread.sleep(100);



                ball.circle.relocate(this.WIDTH/2,this.HEIGHT/2);

            }catch (Exception e){
                e.printStackTrace();
            }
        }

        if(scored == 2){
            try {
                Thread.sleep(100);


                ball.circle.relocate(this.WIDTH/2,this.HEIGHT/2);


            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }





}

