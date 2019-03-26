package org.estefafdez.appium.java.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AndroidHomeConst {
	
	/*--------------------------------------------------------------------*
	|       PAGE ELEMENTS BLOCK                                                
	*---------------------------------------------------------------------*/
	
	/* HOME PAGE */
	public static final String HEADER_LABEL_TITLE = "header.label.title";
	public static final String BODY_LAYOUT = "body.layout";
	public static final String BODY_LABEL_SUBTITLE = "body.label.subtitle";
	public static final String BODY_INPUT_TEXTBOX = "body.input.textbox";
	public static final String BODY_BUTTON_BUTTON1 = "body.button.button1";
	public static final String BODY_BUTTON_BUTTON2 = "body.button.button2";
	public static final String BODY_BUTTON_BUTTON3 = "body.button.button3";
	public static final String BODY_BUTTON_BUTTON4 = "body.button.button4";
	
	/*--------------------------------------------------------------------*
	|       SET UP BLOCK                                                   
	*---------------------------------------------------------------------*/  

	public static final List<String> SET_UP_ANDROID_IS_READY = Collections.unmodifiableList(
			Arrays.asList(
					HEADER_LABEL_TITLE,
					BODY_LAYOUT, 
					BODY_LABEL_SUBTITLE, 
					BODY_INPUT_TEXTBOX, 
					BODY_BUTTON_BUTTON1, 
					BODY_BUTTON_BUTTON2, 
					BODY_BUTTON_BUTTON3, 
					BODY_BUTTON_BUTTON4
	 ));
	
	/*--------------------------------------------------------------------* 
	|		PRVATE CONSTRUCTOR												
	*---------------------------------------------------------------------*/
	
	private AndroidHomeConst() {}

}
