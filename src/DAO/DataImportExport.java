package DAO;

import java.io.IOException;
import java.util.List;

 //Interface générique pour l'importation et l'exportation des données.

public interface DataImportExport<T> {
    void importData(String fileName) throws IOException;
    void exportData(String fileName, List<T> data) throws IOException;
}
