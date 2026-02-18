package com.literalura.service;

import com.literalura.client.GutendexClient;
import com.literalura.model.Book;
import com.literalura.model.Datosrespuesta;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;
import com.literalura.model.Author;
import java.util.List;


@Component
public class AppRunner implements CommandLineRunner {

    private final BookService bookService;

    public AppRunner(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);
        int opcion = -1;

        while (opcion != 0) {

            System.out.println("""
                    
                    ===== LITERALURA =====
                    1 - Buscar libro por título
                    2 - Listar libros guardados
                    3 - Listar libros por idioma
                    4 - Listar autores
                    5 - Listar autores vivos en determinado año
                    6 - Mostrar cantidad de libros por idioma
                    0 - Salir
                    """);

            opcion = scanner.nextInt();
            scanner.nextLine(); // limpiar buffer

            switch (opcion) {

                case 1:
                    System.out.println("Escribe el nombre del libro:");
                    String titulo = scanner.nextLine();

                    String url = "https://gutendex.com/books/?search=" + titulo.replace(" ", "%20");

                    GutendexClient client = new GutendexClient();
                    String json = client.obtenerDatos(url);

                    ConvierteDatos conversor = new ConvierteDatos();
                    Datosrespuesta datos = conversor.obtenerDatos(json, Datosrespuesta.class);

                    bookService.guardarLibro(datos);
                    break;

                case 2:
                    List<Book> libros = bookService.obtenerTodosLosLibros();

                    if (libros.isEmpty()) {
                        System.out.println("No hay libros guardados.");
                    } else {
                        System.out.println("===== LIBROS GUARDADOS =====");
                        libros.forEach(libro -> {
                            System.out.println("Título: " + libro.getTitle());
                            System.out.println("Autor: " + libro.getAuthor());
                            System.out.println("----------------------------");
                        });
                    }
                    break;
                case 3:
                    System.out.println("Ingrese el idioma (ej: en, es, fr):");
                    String idioma = scanner.nextLine();

                    List<Book> librosPorIdioma = bookService.buscarPorIdioma(idioma);

                    if (librosPorIdioma.isEmpty()) {
                        System.out.println("No hay libros en ese idioma.");
                    } else {
                        librosPorIdioma.forEach(System.out::println);
                    }
                    break;
                case 4: {
                    System.out.println("Ingrese el año:");

                    try {
                        int anio = Integer.parseInt(scanner.nextLine());

                        if (anio < 0) {
                            System.out.println("El año no puede ser negativo.");
                            break;
                        }

                        var autores = bookService.obtenerAutoresVivosEnAnio(anio);

                        if (autores.isEmpty()) {
                            System.out.println("No hay autores vivos en ese año.");
                        } else {
                            autores.forEach(autor -> {
                                System.out.println("Autor: " + autor.getName());
                                System.out.println("Nacimiento: " + autor.getBirthYear());
                                System.out.println("Fallecimiento: " + autor.getDeathYear());
                                System.out.println("----------------------");
                            });
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Debe ingresar un número válido.");
                    }
                }

                case 5:
                    System.out.println("Ingrese el año:");
                    Integer anio = Integer.parseInt(scanner.nextLine());

                    List<Author> autoresVivos =
                            bookService.obtenerAutoresVivosEnAnio(anio);

                    if (autoresVivos.isEmpty()) {
                        System.out.println("No hay autores vivos en ese año.");
                    } else {
                        autoresVivos.forEach(System.out::println);
                    }
                    break;
                case 6:
                    System.out.println("""
            Ingrese el idioma:
            es - Español
            en - Inglés
            """);

                    String idiomaBusqueda = scanner.nextLine();

                    Long cantidad = bookService.contarLibrosPorIdioma(idiomaBusqueda);

                    System.out.println("Cantidad de libros en idioma " + idiomaBusqueda + ": " + cantidad);
                    break;



                case 0:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opción inválida.");
            }
        }
    }
}
