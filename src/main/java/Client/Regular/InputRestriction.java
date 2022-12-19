package Client.Regular;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class InputRestriction
{
    private static final int MIN_LENGTH = 3;

    public static boolean textFieldIsValid(String text)
    {
         if(text.isEmpty()) return false;
         if(text.length() < MIN_LENGTH) return false;

         return true;
    }

    public static boolean textFieldIsNotEmpty(String text)
    {
        return !text.isEmpty();
    }

    public static boolean dateIsValid(String input)
    {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        try
        {
            sdf.parse(input);
        }
        catch (ParseException e)
        {
            return false;
        }
        return true;
    }

    public static boolean amountIsValid(int amount)
    {
        return amount >= 0;
    }
    public static boolean amountIsValid(int amount, int min, int max)
    {
        return amount >= min && amount <= max;
    }

    public static boolean amountIsNotNegative(float amount)
    {
        return amount > 0;
    }

    public static boolean amountIsValid(float amount)
    {
        return amount >= 0;
    }

    public static boolean dateIsValid(int year, int month, int day)
    {
        return year >= 2022 &&  month > 0 && month <= 12 && day > 0 && day < 31;
    }

}
