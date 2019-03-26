package org.estefafdez.appium.java.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AndroidHomeConst {
	
	/*--------------------------------------------------------------------*
	|       PAGE ELEMENTS BLOCK                                                
	*---------------------------------------------------------------------*/
	
	/* HOME PAGE */
	public static final String HEADER_LABEL_TITLE = "action_bar";
	public static final String BODY_LAYOUT = "content";
	public static final String BODY_LABEL_SUBTITLE = "texto";
	public static final String BODY_INPUT_TEXTBOX = "editText";
	public static final String BODY_BUTTON_BUTTON1 = "button1";
	public static final String BODY_BUTTON_BUTTON2 = "button2";
	public static final String BODY_BUTTON_BUTTON3 = "button3";
	public static final String BODY_BUTTON_BUTTON4 = "buttonActivity";
	
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
