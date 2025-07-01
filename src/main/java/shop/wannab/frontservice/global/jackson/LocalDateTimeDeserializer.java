package shop.wannab.frontservice.global.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) throws IOException{
        List<Integer> list = parser.readValueAs(List.class);
        if(list != null && list.size()>=5){
            return LocalDateTime.of(
                    list.get(0),list.get(1),list.get(2),list.get(3),list.get(4)
            );
        }
        return null;
    }

}
