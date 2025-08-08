package com.jjsetech.literalura;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.jjsetech.literalura.principal.Principal;
import com.jjsetech.literalura.repository.AutoresRepository;
import com.jjsetech.literalura.repository.LivrosRepository;
import com.jjsetech.literalura.service.BookApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	@Autowired
	private AutoresRepository repositorio;
	@Autowired
	private LivrosRepository repositorioLivro;
	@Autowired
	private BookApiService bookApiService;
	@Autowired
	private LivrosRepository livroRepository;
	@Autowired
	private AutoresRepository autorRepository;

	public static void main(String[] args) {

		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception{

		Principal principal = new Principal(bookApiService, livroRepository, autorRepository);
		principal.exibeMenu();

	}

}