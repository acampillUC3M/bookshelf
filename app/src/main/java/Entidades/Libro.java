package Entidades;
import android.net.Uri;
import android.text.Editable;

import com.example.bookshelf.Librerias;

/**
 * Clase Libro que establece las caracteristicas de los libros
 */
public class Libro {

    private String titulo;
    private String autor;
    private String ISBN;
    private Uri imagen;

    public Libro(String text, Editable autorLibroText, Editable isbnLibroText, int lineadefuego){

    }
    public Libro(){

    }
    /**
     *
     * @param titulo
     * @param autor
     * @param ISBN
     * @param imagen
     */
    public Libro(String titulo, String autor, String ISBN, Uri imagen) {
        this.titulo = titulo;
        this.autor = autor;
        this.ISBN = ISBN;
        this.imagen = imagen;
    }

    public Uri getImagen() {
        return imagen;
    }

    public void setImagen(Uri imagen) {
        this.imagen = imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}
