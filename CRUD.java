import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class CRUD {

    private static void addObject(File f) {
        int opc;
        try {
            if (f.exists()) { // the file already exists. the objects can be added.
                FileOutputStream fos = new FileOutputStream(f, true);
                MyObjectOutputStream moos = new MyObjectOutputStream(fos);
                do {
                    Object obj = new Object();
                    moos.writeObject(obj);
                    System.out.println("Add more objects? [y = 1, n = 2]");
                    opc = Util.readInt(1, 2);
                } while (opc == 1);
                moos.close();
                fos.close();
            }
        } catch (Exception e) {
             
        } 

        try {
            if (!f.exists()) { // create a new file to add the objects.
                FileOutputStream fos = new FileOutputStream(f);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                do {
                    Object obj = new Object();
                    oos.writeObject(obj);
                    System.out.println("Add more objects? [y = 1, n = 2]");
                    opc = Util.readInt(1, 2);
                } while (opc == 1);
                oos.close();
                fos.close();
            }
        } catch (Exception e) {
            
        }
    }

    public static void readFile(File f) {
        if (f.exists()) {
            FileInputStream fis = null;
            ObjectInputStream ois = null;
            try {

                fis = new FileInputStream(f);
                ois = new ObjectInputStream(fis);
                Object reader = new Object();
                while (reader != null) {
                    reader = ois.readObject();
                }

            } catch (EOFException e) {
                System.out.println("File has been fully read with no errors.");
            } catch (FileNotFoundException e) {
                System.out.println("File not found, fatal error, no encontrado el fero");
            } catch (ClassNotFoundException e) {
                System.out.println("Error en la lectura de datos");
            } catch (Exception e) {
                // Generic exception.
            }

            try {
                ois.close();
                fis.close();
            } catch (IOException e) {
                System.out.println("Error closing the file.");
            }

        } else { // There's no file to read.
            System.out.println("There are no objects to show.");
        }
    }

    public static void consulta(File f) {
        boolean found = false;
        if (!f.exists()) {
            System.out.println("There's no file bro.");
        } else {
            try {

                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
                Object reader = new Object();

                while (reader != null) {
                    reader = (Object) ois.readObject();
                    if (true) { // condition of the filer (if any)
                        found = true;
                        break;
                    }
                }
                ois.close();
            } catch (EOFException eofe) {
                System.out.println("The file has been fully read.");
            } catch (Exception e) {
                System.out.println("Fatal error 404 reading the file.");
            }
        }

        if (!found) {
            System.out.println("Nothing was found.");
        }
    }

    public static void update(File f) {
        if (!f.exists()) {
            System.out.println("No hay Objects disponibles");
        } else {
            boolean encontrado = false, cambios = false;

            FileInputStream fis = null;
            FileOutputStream fos = null;
            ObjectInputStream ois = null;
            ObjectOutputStream oos = null;

            File fAux = 
                new File("auxiliar.dat");

            try {
                // The auxiliar file is created, to store the objects.
                fos = new FileOutputStream(fAux);
                oos = new ObjectOutputStream(fos);
                // The file is opned to read the files.
                fis = 
                    new FileInputStream(f);
                ois =
                    new ObjectInputStream(fis);
                Object aux = (Object) ois.readObject();

                while (aux != null) {
                    if (true) { // Filter, condition to change/delete.
                        encontrado = true;
                    }
                    oos.writeObject(aux);
                    aux = (Object) ois.readObject();
                }
            } catch (FileNotFoundException e) {
                System.out.println("File not found.");
            } catch (IOException e) {
                System.out.println(" ");
            } catch (ClassNotFoundException e) {
                System.out.println("Error en la lectura de datos.");
            }
            try {
                ois.close();
                fis.close();
                oos.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("Error closing the fles.");
            }
            if (!encontrado)
                System.out.println("The object wasn't found.");
            if (cambios) {
                f.delete();
                fAux.renameTo(f);
            }
        }
    }
}
