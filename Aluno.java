import java.util.List;

// É uma classe mãe para criar as outras classes de aluno especial e aluno normal
public class Aluno {
    private String nome;
    private String matricula;
    private String curso;
    private String email;
    private List<Turma> turmasMatriculadas;

    // Perguntar se essa é uma maneira valida de fazer o método construtor
    public Aluno(String nome, String matricula, String curso, String email, List<Turma> turmasMatriculadas){
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.email =  email;
        this.turmasMatriculadas = turmasMatriculadas;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
}
