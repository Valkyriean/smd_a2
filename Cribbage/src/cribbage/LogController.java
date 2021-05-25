package cribbage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.stream.Collectors;

import ch.aplu.jcardgame.Card;
import cribbage.Cribbage.Rank;
import cribbage.Cribbage.Suit;


public class LogController {
    private static LogController instance = null;
    private static FileWriter fw;
    static {
        File log= new File ("cribbage.log");
        if (log.exists()) {
            try {
                fw = new FileWriter(log,false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                log.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                fw = new FileWriter(log);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static LogController getInstance(){
        if (instance == null){
            instance = new LogController();
        }
        return instance;
    }

    public void closeFile() throws IOException{
        fw.flush();
        fw.close();
    }

    public void logScore(int player, String event ,int score) {
        try {
            fw.write("score,P" + player + "," + score + "," + event);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void logScore(int player, String event, int score, ArrayList<Card> cards) { 
        try {
            String cardlist= "[" + cards.stream().map(this::canonical).collect(Collectors.joining(",")) + "]";
            fw.write("score,P" + player + "," + score + "," + event + cardlist);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void logSeed(int content){
        try {
            fw.write("seed," + content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String canonical(Card c) { return canonical((Rank) c.getRank()) + canonical((Suit) c.getSuit()); }
    
    String canonical(Suit s) { return s.toString().substring(0, 1); }

	String canonical(Rank r) {
		switch (r) {
			case ACE:case KING:case QUEEN:case JACK:case TEN:
				return r.toString().substring(0, 1);
			default:
				return String.valueOf(r.value);
		}
	}

}
