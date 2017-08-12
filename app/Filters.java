import play.http.HttpFilters;
import play.api.mvc.EssentialFilter;
import play.filters.csrf.CSRFFilter;
import javax.inject.Inject;

/**
 * Created by chrisj on 7/29/17.
 */
public class Filters implements HttpFilters {

    final CSRFFilter csrfFilter;

    @Inject
    public Filters(CSRFFilter csrfFilter) {
        this.csrfFilter = csrfFilter;
    }

    @Override
    public EssentialFilter[] filters() {
        return new EssentialFilter[] { csrfFilter };
    }
}
