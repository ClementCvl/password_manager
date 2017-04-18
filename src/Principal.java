/**
 * Created by Clément on 20/03/2017.
 */
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Text;

import javax.xml.bind.JAXBElement;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Principal
{
    static User actualUser;
    public static void main(String[] args) {
        Connexion connexion = new Connexion("database/Database.db");
        connexion.connect();

        actualUser = connexion.connectUser("iClemich","azerty123");
        if(actualUser != null)
            actualUser.selectAllPasswordByUser();

        WordprocessingMLPackage template = getTemplate("template.docx");
        //replacePlaceholder(template, );
        connexion.close();
    }

    private WordprocessingMLPackage getTemplate(String name) throws Docx4JException, FileNotFoundException {
        WordprocessingMLPackage template = WordprocessingMLPackage.load(new FileInputStream(new File(name)));
        return template;
    }

    /**
     * Permet de récupérer tout les élèments du document.
     * @param obj
     * @param toSearch
     * @return Une liste d'objet
     */
    private static List<Object> getAllElementFromObject(Object obj, Class<?> toSearch) {
        List<Object> result = new ArrayList<Object>();
        if (obj instanceof JAXBElement) obj = ((JAXBElement<?>) obj).getValue();
        if (obj.getClass().equals(toSearch))
            result.add(obj);
        else if (obj instanceof ContentAccessor) {
            List<?> children = ((ContentAccessor) obj).getContent();
            for (Object child : children) {
                result.addAll(getAllElementFromObject(child, toSearch));
            }
        }
        return result;
    }


    /**
     * Remplace un champ texte du template
     * @param template
     * @param name = valeur de remplacement
     * @param placeholder = champ à remplacer
     */
    private void replacePlaceholder(WordprocessingMLPackage template, String name, String placeholder ) {
        List<Object> texts = getAllElementFromObject(template.getMainDocumentPart(), Text.class);

        for (Object text : texts) {
            Text textElement = (Text) text;
            if (textElement.getValue().equals(placeholder)) {
                textElement.setValue(name);
            }
        }
    }

    /**
     * Sauvegarde le nouveau document
     * @param template
     * @param target = nom du nouveau document
     * @throws IOException
     * @throws Docx4JException
     */
    private void writeDocxToStream(WordprocessingMLPackage template, String target) throws IOException, Docx4JException {
        File f = new File(target);
        template.save(f);
    }
}

