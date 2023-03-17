package bankService;

import enums.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class CurrencyRetrievalNBUService implements CurrencyRetrievalService {

    private static final String URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";
    @Override
    public List<CurrencyRateDto> getCurrencyRates() {
        try {
            String response = Jsoup.connect(URL).ignoreContentType(true).get().body().text();
            List<CurrencyRateNBUResponseDto> responseDtos = convertResponseToList(response);
            return responseDtos.stream()
                    .map(dto -> new CurrencyRateDto(BankName.NBU,dto.getR030(), dto.getRate()))
                    .filter(dto ->  Currency.UNKNOWN != dto.getCurrency() )
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private List<CurrencyRateNBUResponseDto> convertResponseToList(String response) {
        Type type = TypeToken.getParameterized(List.class, CurrencyRateNBUResponseDto.class).getType();
        Gson gson = new Gson();
        return gson.fromJson(response, type);
    }
}