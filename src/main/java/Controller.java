/**
 * Created by Vitaly on 17.12.14.
 */
public class Controller {
    public static StringBuilder template(String url){
        if ("/private".equals(url)) {
            StringBuilder buf = new StringBuilder()
                    .append("<!DOCTYPE html>\r\n")
                    .append("<html><link rel=stylesheet href=http://getbootstrap.com/dist/css/bootstrap.min.css /><head><title>onPlay")
                    .append("</title></head><body>\r\n")
                    .append("<div class=\"jumbotron\">\n" +
                            "      <div class=\"container\">\n" +
                            "        <h1>Hello, world!</h1>\n" +
                            "        <p>Private room.</p>\n" +
                            "        <p><a class=\"btn btn-primary btn-lg\" href=\"#\" role=\"button\">Learn more »</a></p>\n" +
                            "      </div>\n" +
                            "    </div>");


            return buf.append("</body></html>\r\n");
        }
        else{
            StringBuilder buf = new StringBuilder()
                    .append("<!DOCTYPE html>\r\n")
                    .append("<html><link rel=stylesheet href=http://getbootstrap.com/dist/css/bootstrap.min.css /><head><title>onPlay")
                    .append("</title></head><body>\r\n")
                    .append("<div class=\"jumbotron\">\n" +
                            "      <div class=\"container\">\n" +
                            "        <h1>Hello, world!</h1>\n" +
                            "        <p>This is a template for a simple marketing or informational website. It includes a large callout called a jumbotron and three supporting pieces of content. Use it as a starting point to create something more unique.</p>\n" +
                            "        <p><a class=\"btn btn-primary btn-lg\" href=\"#\" role=\"button\">Learn more »</a></p>\n" +
                            "      </div>\n" +
                            "    </div>");




            return buf.append("</body></html>\r\n");
        }
    }
}
