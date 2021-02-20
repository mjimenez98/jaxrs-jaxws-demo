package core;

// TO BE DELETED. THIS IS AN EXAMPLE
public class ResultData {
    double num1;
    double num2;
    double result;
    String servermessage;

    public ResultData() {};

    public ResultData(double num1, double num2) {
        this.num1 = num1;
        this.num2 = num2;
        this.result = num1 * num2;
        this.servermessage = "Multiplication successful!";
    }

    public ResultData(ResultData data) {
        this.num1 = data.num1;
        this.num2 = data.num2;
        this.result = data.result;
        this.servermessage = data.servermessage;
    }

    public double getNum1() {
        return num1;
    }

    public double getNum2() {
        return num2;
    }

    public double getResult() {
        return result;
    }

    public String getServermessage() {
        return servermessage;
    }

    public void setNum1(double num1) {
        this.num1 = num1;
    }

    public void setNum2(double num2) {
        this.num2 = num2;
    }

    public void setResult(double result) {
        this.result = result;
    }

    public void setServermessage(String servermessage) {
        this.servermessage = servermessage;
    }
}
