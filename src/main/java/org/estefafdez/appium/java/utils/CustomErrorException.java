/**
 * The GNU GENERAL PUBLIC LICENSE (GPLv3)
 *  
 * Copyright (C) 2018  Francisco José Fernández González, Estefanía Fernández Muñoz
 *  
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package org.estefafdez.appium.java.utils;

/**
 * <p>
 * This class is to configure all the Test Configuration.
 * </p>
 * 
 * @author Francisco José Fernández González<br>
 * <a href="mailto:ffgonzalez1989@gmail.com">ffgonzalez1989@gmail.com</a><br>
 * <a href="https://github.com/FJFGonzalez">https://github.com/FJFGonzalez</a><br>
 * <br><br>
 * @author Estefanía Fernández Muñoz<br>
 * <a href="mailto:estefafdez@gmail.com">estefafdez@gmail.com</a><br>
 * <a href="https://github.com/estefafdez">https://github.com/estefafdez</a><br>
 *
 */
public class CustomErrorException extends Exception{

	private static final long serialVersionUID = -6762514709075847617L;
	
	/**
	 * Method to create a custom error exception with only a message. 
	 * @param message the message to display
	 */
	public CustomErrorException(String message) {
		super(message);
	}

	/**
	 * Method to create a custom error exception with a message and the cause. 
	 * @param message the message to display
	 * @param cause real cause of the error
	 */
	public CustomErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Method to create a custom error exception with only the cause. 
	 * @param cause real cause of the error
	 */
	public CustomErrorException(Throwable cause) {
		super(cause);
	}

}
