package com.company;

import java.io.IOException;

/**
 * Gity !
 * this is simple version of git
 *
 * Network Project
 *
 *
 * @author Seyed Nami Modarressi
 * @version 0.01
 *
 * Spring 2021
 */
public class Main {

    public static void main(String[] args) throws IOException {
	    FileHandler p = new FileHandler();
	    System.out.println(p.login("nami","SNiMod137"));
	    p.register("nami","salam");
	    p.register("sdfsdfsdf","Sdfsdfsdfsdf");
		System.out.println(p.login("1","1"));
		p.register("3","3");
		System.out.println(p.login("1","1"));
    }
}
