package com.jjsetech.literalura.principal;
import com.jjsetech.literalura.model.Livro;
import com.jjsetech.literalura.repository.AutoresRepository;
import com.jjsetech.literalura.repository.LivrosRepository;
import com.jjsetech.literalura.service.BookApiService;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class Principal {

    private final LivrosRepository livroRepository;
    private final AutoresRepository autorRepository;
    private final BookApiService bookApiService;
    private final Scanner leitura = new Scanner(System.in);
    private final List<String> possibleQueryLanguages;

    public Principal(BookApiService bookApiService,
                     LivrosRepository livroRepository,
                     AutoresRepository autorRepository
    ) {

        this.bookApiService = bookApiService;
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;

        possibleQueryLanguages = new ArrayList<>();
        possibleQueryLanguages.add("pt");
        possibleQueryLanguages.add("en");
        possibleQueryLanguages.add("es");
        possibleQueryLanguages.add("fr");
    }

    public void exibeMenu(){

        var opcao = -1;

        while (opcao !=0) {

            var menu = """
                    ******************************************
                    
                    1 - Buscar livro pelo título
                    2 - Listar livros registrados
                    3 - Lista autores
                    4 - Listar autores em determinado ano
                    5 - Listar livros em determinado idioma
                    6 - Listar os 10 livros mais baixados
                    
                    0 - Sair
                    
                    ******************************************
                    """;

            System.out.println(menu);
            opcao = leitura.nextInt();
            leitura.nextLine();

            switch (opcao) {

                case 1:
                    buscarLivropeloTitulo();
                    break;
                case 2:
                    listarLivrosRegistrados();
                    break;
                case 3:
                    listarAutores();
                    break;
                case 4:
                    listarAutoresnoAnoDeterminado();
                    break;
                case 5:
                    listarLivrosnoIdiomaSelecionado();
                    break;
                case 6:
                    listarLivrosMaisBaixados();
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("\n[!] - Opção inválida!");
            }
        }

    }

    private void buscarLivropeloTitulo() {

        System.out.println("\nBUSCAR POR TÍTULO ********************************************");
        System.out.print("Digite o título do livro: ");
        var titulo = leitura.nextLine();

        System.out.println("\nPesquisando...\n");
        var bookData = bookApiService.search(titulo).stream().findFirst();

        if (bookData.isEmpty()) {
            System.out.println(" *** Nenhum livro encontrado ***\n");
        } else {
            var livro = Livro.fromBookData(bookData.get());

            var autorNome = livro.getAutor().getNome();
            var autorExistente = autorRepository.findByNome(autorNome);

            if (autorExistente.isPresent()) {
                livro.setAutor(autorExistente.get());
            } else {
                autorRepository.save(livro.getAutor());
            }

            // Verifica se o livro já existe
            var livroExistente = livroRepository.findByTituloAndAutorNome(livro.getTitulo(), autorNome);

            if (livroExistente.isPresent()) {
                System.out.println("Este livro já está cadastrado:\n" + livroExistente.get() + "\n");
            } else {
                livroRepository.save(livro);
                System.out.println("Livro salvo com sucesso:\n" + livro + "\n");
            }
        }
    }

    private void listarLivrosRegistrados() {
        System.out.println("\n*** LISTANDO LIVROS CADASTRADOS ***");
        var livros = livroRepository.findAll();
        if (livros.isEmpty())
            System.out.println("*** Nenhum livro encontrado ***\n");
        livros.forEach(l -> System.out.println(l + "\n"));
    }

    private void listarAutores() {
        System.out.println("\n*** LISTAGEM DE AUTORES ***");
        var autores = autorRepository.findAll();
        autores.forEach(System.out::println);
        if (autores.isEmpty())
            System.out.println("*** Nenhum autor encontrado ***\n");

    }

    private void listarAutoresnoAnoDeterminado() {
        System.out.println("\n*** BUSCAR AUTORES VIVOS EM DETERMINADO ANO ***");
        System.out.print("Digite o ano desejado: ");
        var year = leitura.nextInt();

        System.out.println("\nPesquisando...\n");
        var autores = autorRepository
                .findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(year, year);

        if (autores.isEmpty())
            System.out.println("*** Nenhum autor encontrado ***\n");
        autores.forEach(System.out::println);
        System.out.println(" ");
    }

    private void listarLivrosnoIdiomaSelecionado() {

        System.out.println("\n*** LISTAR LIVROS DE UM IDIOMA ESPECÍFICO ***");

        boolean validLanguage = false;
        String idioma = "";
        while (!validLanguage) {
            System.out.println(" pt => Português");
            System.out.println(" en => Inglês");
            System.out.println(" es => Espanhol");
            System.out.println(" fr => Francês");
            System.out.print("Escolha o idioma: ");

            var inputUsuario = leitura.nextLine();
            validLanguage = possibleQueryLanguages.stream()
                    .anyMatch(l -> l.equalsIgnoreCase(inputUsuario));

            if (!validLanguage) System.out.println("\n*** Idioma inválido ***\n");
            idioma = inputUsuario;
        }
        var livros = livroRepository.findByIdioma(idioma);
        var quantidade = livroRepository.countByIdioma(idioma);

        System.out.println("\nLISTANDO " + quantidade + " LIVRO"
                + (quantidade != 1 ? "S" : ""));
        livros.forEach(l -> System.out.println(l + "\n"));
        if (livros.isEmpty())
            System.out.println("*** Nenhum livro encontrado ***\n");
    }

    private void listarLivrosMaisBaixados() {
        System.out.println("\nCarregando...");
        var maisBaixados = livroRepository.findTop10ByOrderByDownloadsDesc();

        System.out.println("\n*** TOP 10 LIVROS MAIS BAIXADOS ***");
        maisBaixados.forEach(l -> System.out.println(l + "\n"));
        if (maisBaixados.isEmpty())
            System.out.println("*** Nenhum livro encontrado ***\n");
    }
}
