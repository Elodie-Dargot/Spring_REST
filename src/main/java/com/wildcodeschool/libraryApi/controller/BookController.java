package com.wildcodeschool.libraryApi.controller;
import com.wildcodeschool.libraryApi.model.Book;
import com.wildcodeschool.libraryApi.repository.BookRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping(path = "/library")
public class BookController {
    private final BookRepository bookRepository;
    public BookController(BookRepository bookRepositoryInjected){
        this.bookRepository = bookRepositoryInjected;
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addNewBook(@RequestBody Book book){
        bookRepository.save(book);
        return "Saved";
    }

    @GetMapping(path = "/books")
    public @ResponseBody List<Book> getAllBooks(){
        return bookRepository.findAll();
    }

    @GetMapping("/books/{id}")
    public @ResponseBody Optional<Book> getBookById(@PathVariable Integer id){
            return bookRepository.findById(id);
    }

    @PutMapping(path = "/books/{id}")
    public @ResponseBody String updateBook(@PathVariable Integer id, @RequestBody Book book){
        Book bookToUpdate = bookRepository.findById(id).get();
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setDescription(book.getDescription());
        bookRepository.save(bookToUpdate);
        return "Update";
    }

    @DeleteMapping(path = "/books/{id}")
    public @ResponseBody String deleteBook(@PathVariable Integer id){
        bookRepository.deleteById(id);
        return "deleted";
    }

    @PostMapping(path = "/books/search")
    public @ResponseBody List<Book> searchBook(@RequestBody Map<String, String> body){
        String searchTerm = body.get("text");
        return bookRepository.findByTitleContainingOrDescriptionContaining(searchTerm, searchTerm);
    }
}
