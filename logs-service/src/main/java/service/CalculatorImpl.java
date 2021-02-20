package service;
import core.ResultData;

import javax.jws.WebService;

// TO BE DELETED. THIS IS AN EXAMPLE
/**
 * This class holds the implementation of the methods of our SOAP web service
 */
@WebService(endpointInterface = "service.Calculator")
public class CalculatorImpl implements Calculator {

	/**
	 * Prints a simple message with input from the client.
	 * @param content
	 * @return
	 */
	@Override
	public String sayHelloWorld(String content) {
		return "Hello " + content + "!";
	}

	@Override
	public double add(double num1, double num2) { return num1 + num2; }

	@Override
	public double subtract(double num1, double num2) { return num1 - num2; }

	@Override
	public double multiply(double num1, double num2) { return num1 * num2; }

	@Override
	public ResultData multiplyV2(double num1, double num2) {
		return new ResultData(num1, num2);
	}
}