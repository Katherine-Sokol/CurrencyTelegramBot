package botAPI;

import bankService.*;
import enums.BankName;
import enums.DigitsAfterDecimalPoint;
import settings.SettingsUserDto;

import java.util.List;
import java.util.stream.Collectors;

public class MessageUserInfo {
    private static String RESPONSE_TEMPLATE = "Курс в банку: cur1/UAH\n Покупка: покуп\n Продажа: прод\n";
    private static List<CurrencyRateDto> currencyRateDtoList;
    public static String showInfo(SettingsUserDto settingsUserDto) {
        String res;
        String bankName = formatBankName(settingsUserDto.getBankName());
        String formatDecimalPoint = formatDecimalPoint(settingsUserDto.getDecimalPoint());
        res = currencyRateDtoList.stream()
                .filter(item -> settingsUserDto.getCurrency().contains(item.getCurrency()))
                .map(item -> RESPONSE_TEMPLATE
                        .replace("cur1", item.getCurrency().toString())
                        .replace("покуп", String.format(formatDecimalPoint,item.getBuyRate()))
                        .replace("прод", String.format(formatDecimalPoint,item.getSellRate()))
                        .replace("банку", bankName))
                .collect(Collectors.joining());
        return res;
    }

    private static String formatDecimalPoint(DigitsAfterDecimalPoint digitsAfterDecimalPoint){
        switch (digitsAfterDecimalPoint){
            case TWO:
                return "%.2f";
            case THREE:
                return "%.3f";
            case FOUR:
            default:
                return "%.4f";
        }
    }

    private static String formatBankName(BankName bankName){
        switch (String.valueOf(bankName)) {
            case "NBU":
                currencyRateDtoList = HourCurrencyRatesUpdate.currencyRateDtoNBUList;
                return "НБУ";
            case "MONOBANK":
                currencyRateDtoList = HourCurrencyRatesUpdate.currencyRateDtoMonoList;
                return "MonoBank";
            case "PRIVATBANK":
            default:
                currencyRateDtoList = HourCurrencyRatesUpdate.currencyRateDtoPrivatList;
                return "PrivatBank";
        }
    }
}
