package com.example.mg.mygambit.src.board;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class GameList implements Serializable{
    private  final long serialVersionUID = 1;

    List<BoardList> gamelist = new ArrayList<BoardList>();

    public void addGame(int Index, BoardList nB){
        gamelist.add(Index, nB);
    }
    public BoardList getGame(int index){
        return gamelist.get(index);
    }
}
