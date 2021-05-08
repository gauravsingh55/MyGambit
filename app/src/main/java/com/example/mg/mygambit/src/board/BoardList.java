package com.example.mg.mygambit.src.board;

import java.io.File;
import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

import java.text.SimpleDateFormat;

public class BoardList implements Serializable {

    private  final long serialVersionUID = 1L;

    List<Board> list = new ArrayList<Board>();
    //Time and Title
    String title;
    Date time;
    File file;

    public BoardList(){
    }

    public void addBoard(int Index, Board nB){
        list.add(Index, nB);
    }
    public Board getBoard(int index){
        return list.get(index);
    }

    public List<Board> getList(){
        return list;
    }
    public void deleteLast(int index){
        list.remove(index);
    }

    public Board getMostRecent(){
        return list.get(list.size()-1);
    }

    public List<Board> printBoard(){
        return list;
    }

    public void setTime(){
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        this.time = date;
    }

    public void setTitle(String newTitle){
        this.title = newTitle;
    }
    public String getTitle(){
        return title;
    }

    public void setFile(File f){
        this.file = f;
    }
    public File getFile(){
        return file;
    }

    public Date getDate(){
        return time;
    }
}


/*

private Board board;
    //Set Time and Title Here
    private String title;

    public BoardWrap(Board b) {
        this.board = b;
    }
    public Board getBoard(){
        return board;
    }
    public void copyBoard(Board copy){
        this.board = copy;
    }


*/