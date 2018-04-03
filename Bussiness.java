package com.example.demo;

public class Bussiness {
	
    private int id;
    private String content;

    public void Greeting(int id, String content) {
        this.id = id;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
}
