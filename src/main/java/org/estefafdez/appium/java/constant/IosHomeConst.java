package org.estefafdez.appium.java.constant;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class IosHomeConst {
	
	/*--------------------------------------------------------------------*
	|       PAGE ELEMENTS BLOCK                                                
	*---------------------------------------------------------------------*/

	/* HOME PAGE */
	public static final String BODY_LABEL_ENTER_NAME = "body.label.enter.name";
	public static final String BODY_INPUT_ENTER_YOUR_NAME = "body.input.enter.your.name";
	public static final String BODY_BUTTON_CLICK = "body.button.click";
	public static final String BODY_LABEL_SWITCH_BUTTON_TEXT = "body.label.switch.button.text";
	public static final String BODY_BUTTON_SWITCH = "body.button.switch";
	public static final String BODY_LABEL_HEY_THERE = "body.label.hey.there";
	public static final String BODY_BUTTON_INCREASE_TEXT = "body.button.increase.text";
	
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
