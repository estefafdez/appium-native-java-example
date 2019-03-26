package org.estefafdez.appium.java.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IosHomeConst {
	
	/*--------------------------------------------------------------------*
	|       PAGE ELEMENTS BLOCK                                                
	*---------------------------------------------------------------------*/

	/* HOME PAGE */
	public static final String BODY_LABEL_ENTER_NAME = "enter_your_name_label";
	public static final String BODY_INPUT_ENTER_YOUR_NAME = "enter_your_name_textbox";
	public static final String BODY_BUTTON_CLICK = "click_button";
	public static final String BODY_LABEL_SWITCH_BUTTON_TEXT = "click_switch_button_text";
	public static final String BODY_BUTTON_SWITCH = "switch_button";
	public static final String BODY_LABEL_HEY_THERE = "hey_there_text";
	public static final String BODY_BUTTON_INCREASE_TEXT = "ui_slider_button";

	/*--------------------------------------------------------------------*
	|       SET UP BLOCK                                                   
	*---------------------------------------------------------------------*/  

	public static final List<String> SET_UP_IOS_IS_READY = Collections.unmodifiableList(
			Arrays.asList(
					BODY_LABEL_ENTER_NAME,
					BODY_INPUT_ENTER_YOUR_NAME, 
					BODY_BUTTON_CLICK, 
					//BODY_BUTTON_SWITCH, TODO: Back again when fix the visibility of the element is true
					BODY_LABEL_SWITCH_BUTTON_TEXT, 
					BODY_LABEL_HEY_THERE, 
					BODY_BUTTON_INCREASE_TEXT
	 ));
	
	/*--------------------------------------------------------------------* 
	|		PRVATE CONSTRUCTOR												
	*---------------------------------------------------------------------*/
	
	private IosHomeConst() {}
	
}
