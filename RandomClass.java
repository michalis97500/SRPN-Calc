public class RandomClass {
    /*
    * I have found out that "r" in the SRPN given seems to be pseudorandom. It
    * appears there is an array doubles that are used for the random function.
    */
    public double randomNumber(int _int) {
        return randomArray[_int];
    }
    public double[] randomArray = {
        1804289383,
		846930886,
		1681692777,
		1714636915,
		1957747793,
		424238335,
		719885386,
		1649760492,
		596516649,
		1189641421,
		1025202362,
		1350490027,
		783368690,
		1102520059,
		2044897763,
		1967513926,
		1365180540,
		1540383426,
		304089172,
		1303455736,
		35005211,
		521595368
    };
}
