package org.estefafdez.appium.java.example.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * <p>
 * This class is to configure all the Test Configuration.
 * </p>
 * 
 * @author Francisco José Fernández González<br>
 *         <a href="mailto:ffgonzalez1989@gmail.com">ffgonzalez1989@gmail.com</a><br>
 *         <a href="https://github.com/FJFGonzalez">https://github.com/FJFGonzalez</a><br>
 *         <br>
 *         <br>
 * @author Estefanía Fernández Muñoz<br>
 *         <a href="mailto:estefafdez@gmail.com">estefafdez@gmail.com</a><br>
 *         <a href="https://github.com/estefafdez">https://github.com/estefafdez</a><br>
 *
 */
public abstract class BasePageObject {
	/** Logger class initialization. */
	private static final Logger LOGGER = LogManager.getLogger(BasePageObject.class);

	/** Driver instance. */
	protected final AppiumDriver<MobileElement> driver;
	
	/*--------------------------------------------------------------------* 
	|	CONSTRUCTOR
	*---------------------------------------------------------------------*/

	/**
	 * Construct to sets the Page's Driver. The Page structure is being used within
	 * this test in order to separate the page actions from the tests.
	 *
	 * @param driver the Appium Driver created.
	 */
	protected BasePageObject(AppiumDriver<MobileElement> driver) {
		this.driver = driver;
	}
}
