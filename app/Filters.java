/**
 * Created by chrisj on 7/29/17.
 */
import play.http.HttpFilters;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
import javax.inject.Inject;

public class Filters implements HttpFilters {

    @Inject CSRFFilter csrfFilter;

    @Override
    public EssentialFilter[] filters() {
        return new EssentialFilter[] { csrfFilter };
    }
}
