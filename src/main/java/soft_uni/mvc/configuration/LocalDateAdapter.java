package soft_uni.mvc.configuration;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {
    @Override
    public LocalDate unmarshal(String value) {
        return LocalDate.parse(value); // "2017-07-12" matches ISO format by default
    }

    @Override
    public String marshal(LocalDate value) {
        return value.toString();
    }
}
