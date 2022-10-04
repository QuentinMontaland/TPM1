package fr.icom.info.m1.balleauprisonnier_mvn;


import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

/**
 * Classe gerant le terrain de jeu.
 * 
 */
public class Field extends Canvas {
	
	/** Joueurs */
	Player [] Team1 = new Player[1];
	Player [] Team2 = new Player[2];
	/** Couleurs possibles */
	String[] colorMap = new String[] {"blue", "green", "orange", "purple", "yellow"};
	/** Tableau traçant les evenements */
    ArrayList<String> input = new ArrayList<String>();
    

    final GraphicsContext gc;
    final int width;
    final int height;
    
    /**
     * Canvas dans lequel on va dessiner le jeu.
     * 
     * @param scene Scene principale du jeu a laquelle on va ajouter notre Canvas
     * @param w largeur du canvas
     * @param h hauteur du canvas
     */
	public Field(Scene scene, int w, int h) 
	{
		super(w, h); 
		width = w;
		height = h;
		
		/** permet de capturer le focus et donc les evenements clavier et souris */
		this.setFocusTraversable(true);
		
        gc = this.getGraphicsContext2D();
        
        /** On initialise le terrain de jeu */
    	Team1[0] = new Player(gc, colorMap[0], w/2, h-50, "bottom");
    	Team1[0].display();

    	Team2[0] = new Player(gc, colorMap[1], w/4, 20, "top");
    	Team2[0].display();
    	
    	Team2[1] = new Player(gc, colorMap[1], 3*w/4, 20, "top");
    	Team2[1].display();


	    /** 
	     * Event Listener du clavier 
	     * quand une touche est pressee on la rajoute a la liste d'input
	     *   
	     */
	    this.setOnKeyPressed(
	    		new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            // only add once... prevent duplicates
	    	            if ( !input.contains(code) )
	    	                input.add( code );
	    	        }
	    	    });

	    /** 
	     * Event Listener du clavier 
	     * quand une touche est relachee on l'enleve de la liste d'input
	     *   
	     */
	    this.setOnKeyReleased(
	    	    new EventHandler<KeyEvent>()
	    	    {
	    	        public void handle(KeyEvent e)
	    	        {
	    	            String code = e.getCode().toString();
	    	            input.remove( code );
	    	        }
	    	    });
	    
	    /** 
	     * 
	     * Boucle principale du jeu
	     * 
	     * handle() est appelee a chaque rafraichissement de frame
	     * soit environ 60 fois par seconde.
	     * 
	     */
	    new AnimationTimer() 
	    {
	        public void handle(long currentNanoTime)
	        {	 
	            // On nettoie le canvas a chaque frame
	            gc.setFill( Color.LIGHTGRAY);
	            gc.fillRect(0, 0, width, height);
	        	
	            // Deplacement et affichage des joueurs
	        	for (int i = 0; i < Team1.length; i++) 
	    	    {
	        		if (i==0 && input.contains("LEFT"))
	        		{
	        			Team1[i].moveLeft();
	        		} 
	        		if (i==0 && input.contains("RIGHT")) 
	        		{
	        			Team1[i].moveRight();	        			
	        		}
	        		if (i==0 && input.contains("UP"))
	        		{
	        			Team1[i].turnLeft();
	        		} 
	        		if (i==0 && input.contains("DOWN")) 
	        		{
	        			Team1[i].turnRight();	        			
	        		}
	        		if (i==0 && input.contains("ENTER")){
	        			Team1[i].shoot();
					}
	        		if (i==1 && input.contains("Q"))
	        		{
	        			Team1[i].moveLeft();
	        		} 
	        		if (i==1 && input.contains("D")) 
	        		{
	        			Team1[i].moveRight();	        			
	        		}
	        		if (i==1 && input.contains("Z"))
	        		{
	        			Team1[i].turnLeft();
	        		} 
	        		if (i==1 && input.contains("S")) 
	        		{
	        			Team1[i].turnRight();	        			
	        		}
	        		if (i==1 && input.contains("SPACE")){
	        			Team1[i].shoot();
					}

	        		
	        		Team1[i].display();
	    	    }
	        	
	        	
	        	for (int i = 0; i < Team2.length; i++) 
	    	    {
	        		Team2[i].display();
	    	    }
	    	}
	     }.start(); // On lance la boucle de rafraichissement 
	     
	}

	public Player[] getTeam1() {
		return Team1;
	}
	
	public Player[] getTeam2() {
		return Team2;
	}
}
