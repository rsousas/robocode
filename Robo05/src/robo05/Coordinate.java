/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robo05;

/**
 *
 * @author Renato Sousa
 */
public class Coordinate {
    	public double x;
	public double y;
	
	public Coordinate()
	{
	}

	public Coordinate(double px, double py)
	{
		this.x = px;
		this.y = py;
	}

	public void set(double px, double py)
	{
		this.x = px;
		this.y = py;
	}    
}
