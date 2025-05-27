import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import entidades.Aluno;
import entidades.AlunoEspecial;
import entidades.Avaliacao;
import entidades.Disciplina;
import entidades.Turma;


public class Main {
    int escolhaPagina;
    public static List<Disciplina> disciplinas = carregarDisciplinas();
    static ArrayList<Aluno> listaAlunos = new ArrayList<>();
    static ArrayList<AlunoEspecial> listaAlunosEspeciais = new ArrayList<>();
    static List<Turma> turmas = carregarTurmas(disciplinas, listaAlunos);

    

    public static void main(String[] args) throws IOException{
        carregarAlunos(turmas, listaAlunos, listaAlunosEspeciais);


        for (Aluno aluno : listaAlunos) {
            for (Turma turmaDoAluno : aluno.getTurmasMatriculadas()) {
                for (Turma turmaReal : turmas) {
                    if (turmaReal.getCodigoDaTurma().equals(turmaDoAluno.getCodigoDaTurma())
                        && turmaReal.getDisciplina().getCodigo().equals(turmaDoAluno.getDisciplina().getCodigo())) {
                        turmaReal.getAlunosMatriculados().add(aluno);
                    }
                }
            }
        }
        
        
        Scanner sc1 = new Scanner(System.in);
        System.out.println("\nBem vindo(a) ao sistema acadêmico da FCTE");

        paginaInicial(sc1);
    }

    public static void paginaInicial(Scanner sc){
        

        System.out.println("\n### Escolha a página que você quer entrar: \n");
    System.out.println("1️⃣  Opção 1 - Modo aluno");
    System.out.println("2️⃣  Opção 2 - Modo disciplina/turma");
    System.out.println("3️⃣  Opção 3 - Modo avaliação/frequência");
    System.out.println("4️⃣  Opção 4 - Fechar programa\n");
    System.out.print("Digite aqui sua opção: ");
    String es = sc.nextLine();

        if (es.isEmpty()) {
            System.out.println("❌ Entrada inválida. Por favor, digite um número.");
            paginaInicial(sc); 
            return;
        }

        try {
            int escolhaPagina = Integer.parseInt(es);

            switch (escolhaPagina) {
                case 1:
                    modoAluno(sc);
                    break;
                case 2:
                    modoDisciplina(sc, escolhaPagina);
                case 3:
                    modoAvaliacaoFrequencia(sc);
                    break;
                case 4: 
                    System.exit(0);
                    break;
            
                default:
                    System.out.println("❌ Desculpe, não existe essa opção, tente novamente. \n");
                    paginaInicial(sc);
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("❌ Entrada inválida. Por favor, digite apenas números.");
            paginaInicial(sc); 
        }



    }
    

    public static boolean verificarTurmaDuplicada(String codigo){
        for(Turma t : turmas){
            if (t.getCodigoDaTurma().equals(codigo)){ 
                return true;
            }
        }
        return false;
    }
    

    public static Aluno buscarAlunoPorMatricula(String matricula){

        for(Aluno aluno : listaAlunos){

            if(aluno.getMatricula().equals(matricula)){
                System.out.println(aluno.getMatricula());
                return aluno;
            }
            
        }

        for(AlunoEspecial alunoe : listaAlunosEspeciais){
            if(alunoe.getMatricula().equals(matricula)){
                return alunoe;
            }
        }
        return null;
        
    }


    public static List<Turma> carregarTurmas(List<Disciplina> listaDisciplinas, List<Aluno> listaAlunos) {
        List<Turma> turmasCarregadas = new ArrayList<>();
    
        File file = new File("persistencia/turmas.txt");


        if (!file.exists()) {
            try {
                file.createNewFile(); 
                System.out.println("Arquivo criado.");
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }        
        
        
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(";");
                if (partes.length < 8) {
                    System.out.println("Linha mal formatada: " + linha);
                    continue;
                }
    
                String codigoDisciplina = partes[0];
    
                Disciplina disciplinaEncontrada = null;
                for (Disciplina d : listaDisciplinas) {
                    if (d.getCodigo().equals(codigoDisciplina)) {
                        disciplinaEncontrada = d;
                        break;
                    }
                }
    
                if (disciplinaEncontrada == null) {
                    System.out.println("❌💾 Disciplina não encontrada para a linha: " + linha);
                    continue;
                }
    
                Turma turma = Turma.fromString(linha, disciplinaEncontrada, listaAlunos);
                turmasCarregadas.add(turma);
                disciplinaEncontrada.getTurmas().add(turma);
            }
        } catch (IOException e) {
            System.out.println("❌💾 Erro ao carregar Turmas: " + e.getMessage());
        }
    
        return turmasCarregadas;
    }
    
    

    public static void salvarDisciplinas(List<Disciplina> disciplinas) {


        try (BufferedWriter bw = new BufferedWriter(new FileWriter("persistencia/disciplinas.txt"))) {
            for (Disciplina d : disciplinas) {
                bw.write(d.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar disciplinas: " + e.getMessage());
        }
    }
    
    public static void salvarTurmas(List<Disciplina> disciplinas) {

        Set<String> turmasSalvas = new HashSet<>(); 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("persistencia/turmas.txt"))) {
            for (Disciplina d : disciplinas) {
                for (Turma t : d.getTurmas()) {
                    String linha = t.toString(d);
                    if (!turmasSalvas.contains(linha)) {
                        turmasSalvas.add(linha);      
                        bw.write(linha);              
                        bw.newLine();
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar turmas: " + e.getMessage());
        }

    }
    


    public static List<Disciplina> carregarDisciplinas() {
        List<Disciplina> disciplinas = new ArrayList<>();

        File file = new File("persistencia/disciplinas.txt");

        if (!file.exists()) {
            try {
                file.createNewFile(); 
                System.out.println("Arquivo criado.");
            } catch (IOException e) {
                System.err.println("Erro ao criar o arquivo: " + e.getMessage());
            }
        }
    
        try (BufferedReader br = new BufferedReader(new FileReader("persistencia/disciplinas.txt"))) {
            String linha;
    
            while ((linha = br.readLine()) != null) {
                Disciplina d = Disciplina.fromString(linha);
                disciplinas.add(d);
            }
    
        } catch (IOException e) {
            System.out.println("❌💾 Erro ao carregar disciplinas: " + e.getMessage());
        }
    
        return disciplinas;
    }

    public static void salvarAluno(Aluno aluno) {
        String pasta = "persistencia";
        new File(pasta).mkdirs();
    
        String caminho = pasta + "/" + aluno.getMatricula() + "_aluno.txt";
    
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho))) {
           
            String tipo = aluno instanceof AlunoEspecial ? "ESPECIAL" : "NORMAL";
    
            String turmasAprovadas = aluno.getTurmasAprovadas() == null
                ? "[]" : String.join(",", aluno.getTurmasAprovadas());
    
            writer.write(
                tipo + ";" +
                aluno.getNome() + ";" +
                aluno.getMatricula() + ";" +
                aluno.getCurso() + ";" +
                aluno.getEmail() + ";" +
                aluno.getFrequencia() + ";" +
                turmasAprovadas
            );
            writer.newLine();
    
            for (Turma turma : aluno.getTurmasMatriculadas()) {
                writer.write("TURMA:" + turma.getCodigoDaTurma());
                writer.newLine();
            }
    
            for (Map.Entry<Turma, Avaliacao> entry : aluno.getAvaliacoes().entrySet()) {
                writer.write("AVALIACAO:" + entry.getKey().getCodigoDaTurma() + ";" + entry.getValue().toString());
                writer.newLine();
            }
    
            for (Map.Entry<Turma, Double> entry : aluno.getFrequencias().entrySet()) {
                writer.write("FREQ:" + entry.getKey().getCodigoDaTurma() + ";" + entry.getValue());
                writer.newLine();
            }
    
        } catch (IOException e) {
            System.out.println("❌💾Erro ao salvar aluno " + aluno.getNome() + ": " + e.getMessage());
        }
    }
    

    public static void carregarAlunos(List<Turma> turmas, List<Aluno> alunos, List<AlunoEspecial> alunoEspeciais) {
        File pasta = new File("persistencia");
    
        if (pasta.exists() && pasta.isDirectory()) {
            File[] arquivos = pasta.listFiles((dir, nome) -> nome.endsWith("_aluno.txt"));
    
            if (arquivos != null) {
                for (File arq : arquivos) {
                    try (BufferedReader reader = new BufferedReader(new FileReader(arq))) {
                        String primeiraLinha = reader.readLine();
                        if (primeiraLinha == null) continue;
    
                        String[] partes = primeiraLinha.split(";", -1);
                        String tipo = partes[0];
                        String nome = partes[1];
                        String matricula = partes[2];
                        String curso = partes[3];
                        String email = partes[4];
                        double frequencia = Double.parseDouble(partes[5]);
                        List<String> turmasAprovadas = Arrays.asList(partes[6].split(","));
    
                        Aluno aluno;
                        if (tipo.equals("ESPECIAL")) {
                            aluno = new AlunoEspecial(nome, matricula, curso, email);
                            alunoEspeciais.add((AlunoEspecial) aluno);
                        } else {
                            aluno = new Aluno(nome, matricula, curso, email);
                            alunos.add(aluno);
                        }
    
                        aluno.setFrequencia(frequencia);
                        aluno.setTurmasAprovadas(new ArrayList<>(turmasAprovadas));
    
                        String linha;
                        while ((linha = reader.readLine()) != null) {
                            if (linha.startsWith("TURMA:")) {
                                String codigo = linha.substring(6);
                                for (Turma t : turmas) {
                                    if (t.getCodigoDaTurma().equals(codigo)) {
                                        aluno.getTurmasMatriculadas().add(t);
                                        break;
                                    }
                                }
                            } else if (linha.startsWith("AVALIACAO:")) {
                                String[] av = linha.substring(10).split(";");
                                String codigo = av[0];
                                Avaliacao avaliacao = Avaliacao.fromString(av[1]); 
                                for (Turma t : turmas) {
                                    if (t.getCodigoDaTurma().equals(codigo)) {
                                        aluno.getAvaliacoes().put(t, avaliacao);
                                        break;
                                    }
                                }
                            } else if (linha.startsWith("FREQ:")) {
                                String[] fr = linha.substring(5).split(";");
                                String codigo = fr[0];
                                double freq = Double.parseDouble(fr[1]);
                                for (Turma t : turmas) {
                                    if (t.getCodigoDaTurma().equals(codigo)) {
                                        aluno.getFrequencias().put(t, freq);
                                        break;
                                    }
                                }
                            }
                        }
    
                    } catch (IOException | NullPointerException e) {
                        System.out.println("❌💾 Erro ao carregar aluno do arquivo " + arq.getName() + ": " + e.getMessage());
                    }
                }
            }
        }
    }
    

    public static Turma acharTurmaPorCodigo(String codigo) {
        for (Turma t : turmas) {
            if (t.getCodigoDaTurma().equalsIgnoreCase(codigo)) {
                return t;
            }
        }
        return null;
    }
    
    

    public static void matricularAluno(Scanner sc) {
        System.out.println("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();
        
        Aluno aluno = buscarAlunoPorMatricula(matricula);
    
        if (aluno == null) {
            System.out.println("❌💾 Aluno não encontrado.");
            modoAluno(sc);
        }
    
        System.out.println("Digite o código da turma: ");
        String codigoTurma = sc.nextLine();
    
        Turma turmaSelecionada = null;
        for (Turma t : turmas) {
            if (t.getCodigoDaTurma().equals(codigoTurma)) {
                turmaSelecionada = t;
                break;
            }
        }
        
        if (turmaSelecionada == null) {
            System.out.println("❌💾 Turma não encontrada.");
            return;
        }


        Disciplina disciplinaDaTurma = turmaSelecionada.getDisciplina();
        String codigoDisciplina = disciplinaDaTurma.getCodigo();

        for (String codigoTurmaAprovada : aluno.getTurmasAprovadas()) {
            Turma turmaAprovada = buscarTurmaPorCodigo(codigoTurmaAprovada);
            if (turmaAprovada != null) {
                Disciplina disciplinaAprovada = turmaAprovada.getDisciplina();
                if (disciplinaAprovada.getCodigo().equals(codigoDisciplina)) {
                    System.out.println("❌ O aluno já foi aprovado na disciplina '" + disciplinaDaTurma.getNome() + "', Matrícula não permitida.");
                    return;
                }
            }
        }
        if (aluno instanceof AlunoEspecial) {
            if (aluno.getTurmasMatriculadas().size() >= 2) {
                System.out.println("❌ Alunos especiais só pode se matricular em até 2 turmas.");
                return;
            }
        }
        
        if (aluno.getTurmasMatriculadas().contains(turmaSelecionada)) {
            System.out.println("❌ Aluno já matriculado nesta turma.");
            return;
        }
    
        
        if (turmaSelecionada.getAlunosMatriculados().size() >= turmaSelecionada.getCapacidadeMaxima()) {
            System.out.println("❌ Turma sem vagas disponíveis.");
            return;
        }
    
        
        Disciplina disciplina = turmaSelecionada.getDisciplina();

        if (disciplina == null) {
            System.out.println("❌💾 Disciplina da turma não encontrada.");

            modoAluno(sc);
            return;
        }
        
        List<String> preRequisitos = disciplina.getPreRequisitos();
    
        boolean temTodosOsPreRequisitos = true;
    
        for (String cod : preRequisitos) {
            boolean encontrou = false;
            for (String codTurmaAprovada : aluno.getTurmasAprovadas()) {
                Turma turmaAprovada = acharTurmaPorCodigo(codTurmaAprovada); 
                if (turmaAprovada != null && turmaAprovada.getDisciplina().getCodigo().equalsIgnoreCase(cod)) {
                    encontrou = true;
                    break;
                }
            }
            if (!encontrou) {
                temTodosOsPreRequisitos = false;
                break;
            }
        }

        if (!temTodosOsPreRequisitos) {
            System.out.println("❌ Aluno não possui os pré-requisitos para esta disciplina.");
            return;
        }
    
        
        aluno.getTurmasMatriculadas().add(turmaSelecionada);
        turmaSelecionada.getAlunosMatriculados().add(aluno);

        salvarAluno(aluno);
    
        System.out.println("✅ Aluno matriculado com sucesso na turma: " + turmaSelecionada.getCodigoDaTurma() + " de " + turmaSelecionada.getDisciplina().getNome() + "!");
    }
    

    public static void modoAvaliacaoFrequencia(Scanner sc) {

        int escolhaPagina;
    
        System.out.println("---------------------\n");
        System.out.println("Bem-vindo(a) ao modo Avaliação/Frequência 📈\n"); 

        System.out.println("### Escolha o que você quer fazer:\n");
        System.out.println("1️⃣  Opção 1 - Lançar notas dos alunos");     
        System.out.println("2️⃣  Opção 2 - Exibir boletim de um aluno");  
        System.out.println("3️⃣  Opção 3 - Gerar relatórios");              
        System.out.println("4️⃣  Opção 4 - Voltar para a página inicial\n"); 

        System.out.print("Digite aqui sua opção: ");
        escolhaPagina = Integer.parseInt(sc.nextLine());

        
        try {
    
        switch (escolhaPagina) {
            case 1:
                lancarNotasEFrequencia(sc);
                break;
            case 2:
                exibirBoletimAluno(sc, listaAlunos);
                break;
            case 3:
                menuRelatorios(sc, turmas, disciplinas, listaAlunos);
                break;
            case 4:
                paginaInicial(sc);
                break;
            default:
                System.out.println("❌ Desculpe, não existe essa opção, tente novamente. \n");
                paginaInicial(sc);
                break;
        
            
        }

    } catch (NumberFormatException e) {
        System.out.println("❌ Entrada inválida. Por favor, digite apenas números.");
        paginaInicial(sc); 
    }
    

    }

    public static void menuRelatorios(Scanner sc, List<Turma> turmas, List<Disciplina> disciplinas, List<Aluno> alunos){
       

        System.out.println("\n### Página de Relatórios 📊 \n");
        System.out.println("1️⃣  Opção 1 - Relatório por Turma");      
        System.out.println("2️⃣  Opção 2 - Relatório por Disciplina"); 
        System.out.println("3️⃣  Opção 3 - Relatório por Professor");    
        System.out.println("4️⃣  Opção 4 - Voltar\n");               

        System.out.print("Digite aqui sua opção: ");
        int opcao = Integer.parseInt(sc.nextLine());

        try{
        switch (opcao) {
        case 1:
            relatorioPorTurma(sc, turmas, disciplinas, alunos);;
            break;
        case 2:
            relatorioPorDisciplina(sc, disciplinas, listaAlunos);
            break;
        case 3:
            relatorioPorProfessor(sc, turmas, disciplinas, alunos);
            break;
        case 4:
            paginaInicial(sc);
            break;
        default:
            System.out.println("Opção inválida.");
        }
    

} catch (NumberFormatException e) {
    System.out.println("❌ Entrada inválida. Por favor, digite apenas números.");
    paginaInicial(sc); 
}
    
    

    }

    public static void relatorioPorTurma(Scanner sc, List<Turma> turmas, List<Disciplina> disciplinas, List<Aluno> alunos) {
        System.out.print("Digite o código da turma: ");
        String codigo = sc.nextLine();
    
        Turma turmaEncontrada = null;
    
        for (Turma t : turmas) {
            if (t.getCodigoDaTurma().equalsIgnoreCase(codigo)) {
                turmaEncontrada = t;
                break;
            }
        }
    
        if (turmaEncontrada == null) {
            System.out.println("❌ Turma não encontrada com o código: " + codigo);
            menuRelatorios(sc, turmas, disciplinas, alunos);
            return;
        }
    
        System.out.println("\n📄 Relatório da turma " + turmaEncontrada.getCodigoDaTurma());
        System.out.println("📚 Disciplina: " + turmaEncontrada.getDisciplina().getNome());
        System.out.println("📅 Semestre: " + turmaEncontrada.getSemestre());
        System.out.println("👨‍🏫 Professor: " + turmaEncontrada.getProfessor());
        System.out.println("🏫 Modalidade: " + (turmaEncontrada.isPresencial() ? "Presencial" : "Remota"));
        
        if (turmaEncontrada.isPresencial()) {
            System.out.println("📍 Sala: " + turmaEncontrada.getSala());
        }
    
        System.out.println("⏰ Horário: " + turmaEncontrada.getHorario());
        System.out.println("👥 Capacidade máxima: " + turmaEncontrada.getCapacidadeMaxima());
        System.out.println("🧪 Forma de Avaliação: " + turmaEncontrada.getFormaAvaliacao());
    
        int totalAlunos = 0;
        int aprovados = 0;
    
        List<Aluno> alunosDaTurma = new ArrayList<>();
    
        for (Aluno aluno : alunos) {
            if (aluno.getTurmasAprovadas().contains(codigo)) {
                aprovados++;
                totalAlunos++;
                alunosDaTurma.add(aluno);
            } else if (aluno.getTurmasMatriculadas().contains(turmaEncontrada)) {
                totalAlunos++;
                alunosDaTurma.add(aluno);
            }
        }
    
        double taxaAprovacao = (totalAlunos > 0)
            ? ((double) aprovados / totalAlunos) * 100
            : 0;
    
        System.out.println("📋 Alunos da turma (" + totalAlunos + "):");
        if (alunosDaTurma.isEmpty()) {
            System.out.println("❌ Nenhum aluno encontrado para esta turma.");
        } else {
            for (Aluno aluno : alunosDaTurma) {
                boolean foiAprovado = aluno.getTurmasAprovadas().contains(codigo);
                System.out.println("- " + aluno.getNome() +
                                   " (Matrícula: " + aluno.getMatricula() + ")" +
                                   (foiAprovado ? " ✅ Aprovado" : " 🕒 Em andamento"));
            }
        }
    
        System.out.printf("📈 Taxa de Aprovação: %.2f%%\n", taxaAprovacao);
        System.out.println("---------------------------------------------\n");
    
        menuRelatorios(sc, turmas, disciplinas, alunos);
    }
    

    public static void relatorioPorDisciplina(Scanner sc, List<Disciplina> disciplinas, List<Aluno> listaAlunos) {
        System.out.print("Digite o código da disciplina: ");
        String cod = sc.nextLine();
    
        Disciplina disciplinaEncontrada = null;
        for (Disciplina disc : disciplinas) {
            if (disc.getCodigo().equalsIgnoreCase(cod)) {
                disciplinaEncontrada = disc;
                break;
            }
        }
    
        if (disciplinaEncontrada == null) {
            System.out.println("❌💾 Disciplina não encontrada.");
            return;
        }
    
        System.out.println("\n--- Relatório da Disciplina: " + disciplinaEncontrada.getNome() + "  ---");
        System.out.println("Total de Turmas Ofertadas: " + disciplinaEncontrada.getTurmas().size());
        System.out.println("------------------------------------------");
    
        int totalAlunosDisciplina = 0;
        int alunosAprovadosDisciplina = 0;
    
        if (disciplinaEncontrada.getTurmas().isEmpty()) {
            System.out.println("Nenhuma turma cadastrada para esta disciplina.");
        } else {
            for (Turma turma : disciplinaEncontrada.getTurmas()) {
                System.out.println("\n  Detalhes da Turma: " + turma.getCodigoDaTurma());
                System.out.println("👨‍🏫  Professor: " + turma.getProfessor());
                System.out.println("⏰  Horário: " + turma.getHorario());
                System.out.println("📅  Semestre: " + turma.getSemestre());
    
                int totalAlunosTurma = 0;
                int alunosAprovadosTurma = 0;
                String codigoTurma = turma.getCodigoDaTurma();
    
                for (Aluno aluno : listaAlunos) {
                    
                    if (aluno.getTurmasAprovadas().contains(codigoTurma)) {
                        alunosAprovadosTurma++;
                        totalAlunosTurma++;
                    }
                    
                    else if (aluno.getTurmasMatriculadas().contains(turma)) {
                        totalAlunosTurma++;
                    }
                }
    
                double taxaAprovacaoTurma = (totalAlunosTurma > 0)
                    ? ((double) alunosAprovadosTurma / totalAlunosTurma) * 100
                    : 0;
    
                System.out.printf("    Total de Alunos: %d | Aprovados: %d | Taxa de Aprovação da Turma: %.2f%%\n",
                    totalAlunosTurma, alunosAprovadosTurma, taxaAprovacaoTurma);
    
                totalAlunosDisciplina += totalAlunosTurma;
                alunosAprovadosDisciplina += alunosAprovadosTurma;
            }
    
            System.out.println("\n------------------------------------------");
            System.out.println("Resumo Geral da Disciplina:");
            System.out.printf("Total de Alunos em todas as turmas: %d\n", totalAlunosDisciplina);
            double taxaAprovacaoDisciplina = (totalAlunosDisciplina > 0)
                ? ((double) alunosAprovadosDisciplina / totalAlunosDisciplina) * 100
                : 0;
            System.out.printf("Taxa de Aprovação Geral da Disciplina: %.2f%%\n", taxaAprovacaoDisciplina);
        }
    
        menuRelatorios(sc, turmas, disciplinas, listaAlunos);
    }

    

 public static void relatorioPorProfessor(Scanner sc, List<Turma> turmas, List<Disciplina> disciplinas, List<Aluno> listaAlunos) {
    System.out.print("Digite o nome do professor para o relatório: ");
    String nome = sc.nextLine();

    System.out.println("\n📊 Relatório do professor " + nome.toUpperCase() + "\n");

    boolean encontrouProfessor = false;

    for (Turma turma : turmas) {
        if (turma.getProfessor().equalsIgnoreCase(nome)) {
            encontrouProfessor = true;

            System.out.println("--- Turma: " + turma.getCodigoDaTurma() +
                               " | Disciplina: " + turma.getDisciplina().getNome() +
                               " | Semestre: " + turma.getSemestre() + " ---");

            int totalAlunos = 0;
            int alunosAprovados = 0;
            String codigoTurma = turma.getCodigoDaTurma();

            for (Aluno aluno : listaAlunos) {
                
                if (aluno.getTurmasAprovadas().contains(codigoTurma)) {
                    alunosAprovados++;
                    totalAlunos++;
                }
                else if (aluno.getTurmasMatriculadas().contains(turma)) {
                    totalAlunos++;
                }
            }

            double taxaAprovacao = (totalAlunos > 0)
                ? ((double) alunosAprovados / totalAlunos) * 100
                : 0;

            System.out.printf("🧑‍🎓 Total de Alunos: %d | ✅ Aprovados: %d | 📈 Taxa de Aprovação: %.2f%%\n\n",
                              totalAlunos, alunosAprovados, taxaAprovacao);
        }
    }

    if (!encontrouProfessor) {
        System.out.println("❌ Professor '" + nome + "' não encontrado em nenhuma turma. Verifique o nome e tente novamente.\n");
    }

    menuRelatorios(sc, turmas, disciplinas, listaAlunos);
}

   
    
    public static Turma buscarTurmaPorCodigo(String codigoTurma, List<Turma> turmas) {
        for (Turma turma : turmas) {
            if (turma.getCodigoDaTurma().equalsIgnoreCase(codigoTurma)) {
                return turma;
            }
        }
        return null;
    }
        

        

    public static void exibirBoletimAluno(Scanner sc, List<Aluno> alunos){
        System.out.println("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();
        Aluno alunoEncontrado = null;
        String matriculaBuscada = matricula.trim();

        for (Aluno aluno : alunos) {
            if (aluno.getMatricula().trim().equalsIgnoreCase(matriculaBuscada)) {
                alunoEncontrado = aluno;
                break;
            }
        }

        if (alunoEncontrado == null) {
            for (AlunoEspecial aluno : listaAlunosEspeciais) {
                if (aluno.getMatricula().trim().equalsIgnoreCase(matriculaBuscada)) {
                    alunoEncontrado = aluno;
                    break;
                }
            }
        }

        if (alunoEncontrado == null) {
            System.out.println("❌💾 Aluno não encontrado.");
            modoAvaliacaoFrequencia(sc);
        }

        Map<String, List<String>> turmasPorSemestre = new HashMap<>();

        for (String codigoTurma : alunoEncontrado.getTurmasAprovadas()) {
            Turma turma = buscarTurmaPorCodigo(codigoTurma);
            if (turma == null) {
                System.out.println(" Turma com código " + codigoTurma + " não encontrada.");
                
            } 
            else{
            String[] partes = buscarTurmaPorCodigo(codigoTurma).getSemestre().split("-");
            if (partes.length >= 2) {
                String semestre = partes[0];
                turmasPorSemestre.putIfAbsent(semestre, new ArrayList<>());
                turmasPorSemestre.get(semestre).add(codigoTurma);
            } else {
            }
            
        }
    }
        System.out.println("\n✅ Disciplinas já concluídas pelo Aluno " + alunoEncontrado.getNome() + "\n" );

        for (String semestre : turmasPorSemestre.keySet()) {
            System.out.println("📆 Semestre: " + semestre);
    
            for (String codTurma : turmasPorSemestre.get(semestre)) {
                Turma turmaEncontrada = null;
    
                for (Turma turma : turmas) {
                    if (turma.getCodigoDaTurma().equals(codTurma)) {
                        turmaEncontrada = turma;
                        break;
                    }
                }
    
                if (turmaEncontrada != null) {
                    System.out.println(" Disciplina: " + turmaEncontrada.getDisciplina().getNome());
                    System.out.println(" Turma: " + turmaEncontrada.getCodigoDaTurma());
                    System.out.println(" Professor: " + turmaEncontrada.getProfessor());
                    System.out.println(" Carga Horária: " + turmaEncontrada.getDisciplina().getCargaHoraria() + "h");

                } else {
                    System.out.println("❌ Turma com código " + codTurma + " não encontrada.");
                }
                System.out.println(); 
            }
        }
    
        System.out.println(); 
        modoAvaliacaoFrequencia(sc);
    }


    public static Turma buscarTurmaPorCodigo(String codigo) {
        for (Turma t : turmas) {
            if (t.getCodigoDaTurma().equalsIgnoreCase(codigo)) {
                return t;
            }
        }
        return null;

    }

    
    public static void lancarNotasEFrequencia(Scanner sc){

        System.out.println("Escreva o código da turma que você quer lançar notas: ");

        String codigo = sc.nextLine();

        Turma turma = buscarTurmaPorCodigo(codigo);
        

        if(turma == null){
            System.err.println("\n❌ Essa turma não existe, tente cadastrar uma nova turma \n");
            modoDisciplina(sc, 0);
        }



        for (Aluno aluno : turma.getAlunosMatriculados()) {
            System.out.println("Aluno: " + aluno.getNome());
            
    
            Avaliacao avaliacao = new Avaliacao();
    
            String tipo = turma.getFormaAvaliacao();

            if(tipo.equals("1")){
                avaliacao.setTipoMedia(1);
            }
            else{
                avaliacao.setTipoMedia(0);
            }
    
            System.out.print("Nota P1: ");
            avaliacao.setP1(Double.parseDouble(sc.nextLine()));
    
            System.out.print("Nota P2: ");
            avaliacao.setP2(Double.parseDouble(sc.nextLine()));
    
            System.out.print("Nota P3: ");
            avaliacao.setP3(Double.parseDouble(sc.nextLine()));
    
            System.out.print("Nota das listas: ");
            avaliacao.setLista(Double.parseDouble(sc.nextLine()));
    
            System.out.print("Nota do seminário: ");
            avaliacao.setSeminario(Double.parseDouble(sc.nextLine()));
    
            aluno.setAvaliacao(avaliacao);
    
            System.out.print("Frequência do aluno (0% a 100%): ");
            aluno.setFrequencia(Double.parseDouble(sc.nextLine()));
    
            System.out.println("Média final: " + avaliacao.CalculoMedia());
            if (avaliacao.aprovado(aluno.getFrequencia())) {
                System.out.println("Aluno aprovado ✅");
                List<String> turmasAprovadas = aluno.getTurmasAprovadas();
                turmasAprovadas.add(codigo);

                aluno.RemoverTurmas(turma);

                
                salvarAluno(aluno);
            } else {
                System.out.println("Aluno reprovado ❌");
                aluno.RemoverTurmas(turma);

                salvarAluno(aluno);
                carregarAlunos(turmas, listaAlunos, listaAlunosEspeciais);

            }
            System.out.println("--------------------------");
        }

        

            paginaInicial(sc);;
    }
    
    public static boolean verificarDuplicacaoDeHorarios(String horario, Disciplina disciplina){
        
        for(Turma t : turmas){
            if(t.getHorario().equals(horario) && t.getDisciplina().equals(disciplina)){
                return false;
            }
        }
        return true;
    }
    

    public static void modoDisciplina(Scanner sc, int escolhaPagina){

        escolhaPagina = 0;
        
        System.out.println("---------------------\n");
        System.out.println("Bem-vindo(a) ao modo Disciplina e Turma 🏫\n");

        System.out.println("### Escolha o que você quer fazer:\n");
        System.out.println("1️⃣  Opção 1 - Cadastrar disciplinas");         
        System.out.println("2️⃣  Opção 2 - Criar turmas");                
        System.out.println("3️⃣  Opção 3 - Exibir todas as disciplinas");    
        System.out.println("4️⃣  Opção 4 - Exibir todas as turmas cadastradas"); 
        System.out.println("5️⃣  Opção 5 - Voltar para a página inicial"); 

        System.out.print("\nDigite aqui sua opção: ");

        escolhaPagina = Integer.parseInt(sc.nextLine());

        switch (escolhaPagina) {
            case 1:
                cadastrarDisciplina(sc);
                break;
            case 2:
                cadastrarTurma(sc);
                break;
            case 3:
                exibirDisciplinas(sc);
                break;
            case 4:
                exibirTurmas(sc);
                break;
            default:
                paginaInicial(sc);
                break;
        }

    }

    public static void cadastrarTurma(Scanner sc) {
        System.out.println("\n### Cadastro de Turma ###");
    
        System.out.print("Código da disciplina para esta turma: ");
        String codigoDisciplina = sc.nextLine();
    
        Disciplina disciplinaSelecionada = null;
        for (Disciplina d : disciplinas) {
            if (d.getCodigo().equalsIgnoreCase(codigoDisciplina)) {
                disciplinaSelecionada = d;
                break;
            }
        }
    
        if (disciplinaSelecionada == null) {
            System.out.println("\n❌💾 Disciplina não encontrada. Cadastre a disciplina antes\n");
            modoDisciplina(sc, 0);
        }

        System.out.print("Código da turma (ex: T01): ");
        String codigoDaTurma = sc.nextLine();

        if (verificarTurmaDuplicada(codigoDaTurma) == true){
            System.out.println("\n❌ Já exite uma turma com esse código, tente outro código\n");
            modoDisciplina(sc, 0);
            
        }

        System.out.print("Horário(formatado no padrão da documentação, ex: 35T34): ");
        String horario = sc.nextLine();

        if (verificarDuplicacaoDeHorarios(horario, disciplinaSelecionada) == false){
            System.out.println("❌ Já exite uma turma dessa Disciplina com esse horário, tente outro código\n");
            modoDisciplina(sc, 0);
            
        }
    
    
        System.out.print("Nome do professor: ");
        String professor = sc.nextLine();
    
        System.out.print("Semestre (Formatado, ex: 2025.1): ");
        String s = sc.nextLine();

        String semestre = s.concat("-").concat(codigoDaTurma);

    
        System.out.println("### Escolha a forma de avaliação:\n");
        System.out.println("1 - Média simples (P1 + P2 + P3 + L + S) / 5");
        System.out.println("2 - Média ponderada (P1 + P2*2 + P3*3 + L + S) / 8");
        String escolhaForma = sc.nextLine();
        String formaAvaliacao = escolhaForma.equals("1") ? "simples" : "ponderada";
    
        System.out.print("A turma é presencial? (s/n): ");
        boolean presencial = sc.nextLine().equalsIgnoreCase("s");
    
        
        
    
        System.out.print("Capacidade máxima de alunos: ");
        int capacidadeMaxima = Integer.parseInt(sc.nextLine());
    
        

        List<Aluno> alunosMatriculados = new ArrayList<>();

        if (presencial == true){
            System.out.print("Sala: ");
            String sala = sc.nextLine();

            Turma novaTurma = new Turma(professor, semestre, formaAvaliacao, presencial, horario,
                                        capacidadeMaxima, alunosMatriculados, codigoDaTurma, disciplinaSelecionada, sala);
            turmas.add(novaTurma);
            disciplinaSelecionada.getTurmas().add(novaTurma);
            salvarTurmas(disciplinas);
            salvarDisciplinas(disciplinas);
            System.out.println("\n✅ Turma cadastrada com sucesso na disciplina " + disciplinaSelecionada.getNome());
            modoDisciplina(sc, 0);

        } else {
            Turma novaTurma = new Turma(professor, semestre, formaAvaliacao, presencial, horario,
                                        capacidadeMaxima, alunosMatriculados, codigoDaTurma, disciplinaSelecionada);
            turmas.add(novaTurma);
            disciplinaSelecionada.getTurmas().add(novaTurma);
            salvarDisciplinas(disciplinas);
            salvarTurmas(disciplinas);
            System.out.println("\n✅ Turma cadastrada com sucesso na disciplina " + disciplinaSelecionada.getNome());
            modoDisciplina(sc, capacidadeMaxima);
        }
    }

    public static Disciplina acharDisciplinaPorString(String d){
        for(Disciplina disciplina: carregarDisciplinas()){
            if(disciplina.getCodigo().equals(d)){
                return disciplina;
            }
            else{
                
            }
        }
        return null;
    }   

    public static void exibirDisciplinas(Scanner sc){

        List<Disciplina> disciplinas = carregarDisciplinas();
        System.out.println("\n### Disciplinas disponíveis: ");
        
        for (Disciplina d : disciplinas){
            System.out.println("\nCódigo: " + d.getCodigo() + " | Nome: " + d.getNome() + " | Carga horária: " + d.getCargaHoraria());
            System.out.println("Pré-requisitos: ");
            for(String disciplina: d.getPreRequisitos()){
                System.out.println(acharDisciplinaPorString(disciplina).getNome());
            }
        }
        
        paginaInicial(sc);
        
    }

    
    public static void exibirTurmas(Scanner sc) {
        carregarDisciplinas(); 
        carregarTurmas(disciplinas, listaAlunos);
    
        System.out.println("\n### Turmas Cadastradas ");
        for (Turma t : turmas) {
            System.out.println(
                "Código: " + t.getCodigoDaTurma() +
                " | Professor: " + t.getProfessor() +
                " | Semestre: " + t.getSemestre() +
                " | Forma de Avaliação: " + t.getFormaAvaliacao() +
                " | Disciplina: " + t.getDisciplina().getNome() +
                " | Horário: " + t.getHorario()

            );
        }
    
        paginaInicial(sc);
    }
    
    public static boolean verificarExistenciaCodigo(String codigo, List<Disciplina> listaDisciplinas){
        for(Disciplina p1 : listaDisciplinas){
            
            if(p1.getCodigo().equals(codigo)){
                return true;
                
            }
        }
        return false;
    }
    
    public static void cadastrarDisciplina(Scanner sc) {
        System.out.println("\n### Cadastro de Disciplina ###");
    
        System.out.print("Nome da disciplina: ");
        String nome = sc.nextLine();
    
        System.out.print("Código da disciplina (ex: MAT101): ");
        String codigo = sc.nextLine();

        for(Disciplina p : disciplinas){
            if(p.getCodigo().equals(codigo)){
                System.out.println("❌ Uma disciplina com esse código já foi cadastrada ");
                modoDisciplina(sc, 0);
                
            }
        }

        System.out.print("Carga horária (em horas): ");
        String cargaHoraria = sc.nextLine();
    
        System.out.print("Quantos pré-requisitos essa disciplina possui? ");
        int qtdPre = Integer.parseInt(sc.nextLine());
    
        List<String> preRequisitos = new ArrayList<>();
    
        for (int i = 0; i < qtdPre; i++) {
            System.out.print("Código do pré-requisito " + (i + 1) + ": ");

            String pre = sc.nextLine();

            List<Disciplina> listaDisciplinas = carregarDisciplinas();

            if(verificarExistenciaCodigo(pre, listaDisciplinas)){
                preRequisitos.add(pre);
            }
            
            else{
                System.out.println("❌ Essa disciplina de pré-requisito não foi cadastrada ");
                modoDisciplina(sc, i);
            }
            
        }

        List<Turma> turmasVazias = new ArrayList<>();

        Disciplina nova = new Disciplina(nome, codigo, cargaHoraria, preRequisitos, turmasVazias);
        disciplinas.add(nova);
        nova.setPreRequisitos(preRequisitos);
        
        salvarDisciplinas(disciplinas);
        salvarTurmas(disciplinas);
        System.out.println("\n✅ Disciplina cadastrada com sucesso!");
        modoDisciplina(sc,0);
    
    }


    public static void modoAluno(Scanner sc){

        int escolhaPagina;

        System.out.println("---------------------\n");
        System.out.println("Bem-vindo(a) ao modo aluno 🧑‍🎓\n"); 

        System.out.println("### Escolha o que você quer fazer:\n");
        System.out.println("1️⃣  Opção 1 - Cadastrar aluno");          
        System.out.println("2️⃣  Opção 2 - Trancar disciplinas");          
        System.out.println("3️⃣  Opção 3 - Exibir todos os alunos");      
        System.out.println("4️⃣  Opção 4 - Matricular aluno em uma turma"); 
        System.out.println("5️⃣  Opção 5 - Voltar para a página Inicial\n");
        System.out.print("Digite aqui sua opção: ");

        escolhaPagina = Integer.parseInt(sc.nextLine());
        try{
        switch (escolhaPagina) {
            case 1:
                cadastrarAluno(sc);
                break;
            case 2:
                trancarDisciplina(sc);
                break;
            case 3:
                mostrarAlunos(sc);
                break;
            case 4:
                matricularAluno(sc);
            default:
                paginaInicial(sc);
                break;
        }
    

} catch (NumberFormatException e) {
    System.out.println("\n❌ Entrada inválida. Por favor, digite apenas números.");
    paginaInicial(sc); 
}
    }

    public static void trancarDisciplina(Scanner sc){
        System.out.println("Digite a matrícula do aluno: ");
        String matricula = sc.nextLine();

        Aluno aluno = buscarAlunoPorMatricula(matricula);

        if (aluno == null) {
            System.out.println("❌💾 Aluno não encontrado.");
            modoAluno(sc);
        }

        List<Turma> turmasMatriculadas = aluno.getTurmasMatriculadas();


        if (turmasMatriculadas.isEmpty()) {
            System.out.println("❌ Aluno não está matriculado em nenhuma turma ");
            modoAluno(sc);;
        }
        
        System.out.println("\n📚 Turmas matriculadas:");
        for (int i = 0; i < turmasMatriculadas.size(); i++) {
            Turma t = turmasMatriculadas.get(i);
            System.out.printf("%d - %s (%s)\n", i + 1, t.getDisciplina().getNome(), t.getCodigoDaTurma());
        }

        System.out.print("Digite o número da turma que deseja trancar: ");
        int escolha = Integer.parseInt(sc.nextLine());

        if (escolha < 1 || escolha > turmasMatriculadas.size()) {
            System.out.println("❌ Escolha inválida");
            modoAluno(sc);
        }

        Turma turmaParaTrancar = turmasMatriculadas.get(escolha - 1);

        aluno.getTurmasMatriculadas().remove(turmaParaTrancar);
        turmaParaTrancar.getAlunosMatriculados().remove(aluno);
        salvarAluno(aluno);

        System.out.println("✅ Trancamento realizado com sucesso!");

        modoAluno(sc);

    }

    public static void mostrarAlunos(Scanner sc){

        if(listaAlunos.size() == 0 && listaAlunosEspeciais.size()==0){
            System.out.println("\n❌ Não há alunos cadastrados no momento \n");
            modoAluno(sc);
            
        }

        System.out.println("\n-------------\n");
        System.out.println("Alunos Normais: ");


        for(Aluno aluno : listaAlunos){
            System.out.println("\n-------------\n");
            System.out.println("Nome: " + aluno.getNome() + " | Email: " + aluno.getEmail() + " | Matrícula: " 
            + aluno.getMatricula() + " | Curso: " + aluno.getCurso() );
        
            System.out.println("\n📝 Turmas que o aluno está cursando:\n");

            for(Turma turma: aluno.getTurmasMatriculadas()){
                System.out.println("Disciplina: " +turma.getDisciplina().getNome() + " | Código: " + turma.getCodigoDaTurma() + " | Professor: " + turma.getProfessor() + " | Horário: " + turma.getHorario() + " | Semestre: " + turma.getSemestre() );

            }

            System.out.println("\n✅ Turmas que o aluno foi aprovado:\n");


            for (String t : aluno.getTurmasAprovadas()) {
                Turma t1 = buscarTurmaPorCodigo(t);
                if (t1 != null) {
                    System.out.println("Disciplina: " + t1.getDisciplina().getNome() +" | Código da turma: " + t1.getCodigoDaTurma() + " | Professor: " + t1.getProfessor() + " | Semestre: " + t1.getSemestre());
                } else {
                }
            }
            
        }

        System.out.println("\n-------------\n");
        System.out.println("Alunos Especiais:");

        for(AlunoEspecial aluno : listaAlunosEspeciais){
            System.out.println("\n-------------\n");
            System.out.println("Nome: " + aluno.getNome() + " | Email: " + aluno.getEmail() + " | Matrícula: " 
            + aluno.getMatricula() + " | Curso: " + aluno.getCurso() );
        
            System.out.println("\n📝 Turmas que o aluno está cursando:\n");

            for(Turma turma: aluno.getTurmasMatriculadas()){
                System.out.println("Disciplina: " +turma.getDisciplina().getNome() +" | Código da turma: " + turma.getCodigoDaTurma() + " | Professor: " + turma.getProfessor() + " | Horário: " + turma.getHorario());

            }

            System.out.println("\n✅ Turmas que o aluno foi aprovado:\n");

            if (aluno.getTurmasAprovadas() != null) {
                for (String t : aluno.getTurmasAprovadas()) {
                    Turma t1 = buscarTurmaPorCodigo(t);
                    if (t1 != null) {
                        System.out.println("Disciplina: " + t1.getDisciplina().getNome() +" | Código da turma: " + t1.getCodigoDaTurma() + " | Professor: " + t1.getProfessor() + " | Horário: " + t1.getHorario());
                    } else {
                    }
                }
            } else {
                System.out.println(" Nenhuma turma aprovada registrada.");
            }
            
        }

        paginaInicial(sc);

        
    }


    public static void cadastrarAluno(Scanner sc){
        System.out.println("\n---------------------\n");
        System.out.println("Digite o nome do novo aluno: ");
        String nomeAluno = sc.nextLine();
        System.out.println("Digite a matrícula do novo aluno: ");
        String matricula = sc.nextLine();

        for(Aluno aluno : listaAlunos){
            if(aluno.getMatricula().equals(matricula)){
                System.out.println("❌ Já existe um aluno com essa matrícula, digite outra");
                cadastrarAluno(sc);
                break;
            }
        
        }

        for(AlunoEspecial alunoe : listaAlunosEspeciais){
            if(alunoe.getMatricula().equals(matricula)){
                System.out.println("❌ Já existe um aluno com essa matrícula, digite outra");
                cadastrarAluno(sc);
                break;
            }
        
        }

        System.out.println("Digite o curso do novo aluno: ");
        String curso = sc.nextLine();
        System.out.println("Digite o email do novo aluno: ");
        String email = sc.nextLine();
        System.out.println("Digite 1 se o aluno for especial e 0 se for normal");
        int especial = Integer.parseInt(sc.nextLine());



        if(especial == 1){
            
            AlunoEspecial novoAlunoEspecial = new AlunoEspecial(nomeAluno, matricula, curso, email);
            
            if (novoAlunoEspecial.verificarCadastro()) {
                listaAlunosEspeciais.add(novoAlunoEspecial);
                salvarAluno(novoAlunoEspecial);
                System.out.println("✅ Aluno Especial cadastrado com sucesso!");
                } 
                else {
                    
                System.out.println("❌ Todos os campos devem ser preenchidos corretamente.");
                } 
        }
        else{
            Aluno novoAluno = new Aluno(nomeAluno, matricula, curso, email);

            if (novoAluno.verificarCadastro()) {
                listaAlunos.add(novoAluno);
                salvarAluno(novoAluno);
                System.out.println("\n✅ Aluno cadastrado com sucesso!");
                } else {
                    
                System.out.println("\n❌ Todos os campos devem ser preenchidos corretamente.\n");
                } 
        }

        paginaInicial(sc);
    }

}  