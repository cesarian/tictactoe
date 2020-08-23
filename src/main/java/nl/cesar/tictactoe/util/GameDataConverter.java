package nl.cesar.tictactoe.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.cesar.tictactoe.domain.GameData;

@Converter(autoApply = true)
public class GameDataConverter implements AttributeConverter<GameData, String> {

    @Override
    public String convertToDatabaseColumn(GameData gameData) {
        if( gameData == null )
            return null;

        ObjectMapper mapper = new ObjectMapper();
        try {
			return mapper.writeValueAsString(gameData);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;
    }

    @Override
    public GameData convertToEntityAttribute(String databaseValue) {
        if( databaseValue == null )
            return null;

        ObjectMapper mapper = new ObjectMapper();

        try {
			return mapper.readValue(databaseValue, GameData.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return null;

    }
}
