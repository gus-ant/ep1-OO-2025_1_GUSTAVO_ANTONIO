import java.util.ArrayList;
import java.util.List;

// É uma classe mãe para criar as outras classes de aluno especial e aluno normal
public class Aluno {
    private String nome;
    private String matricula;
    private String curso;
    private String email;
    private List<Turma> turmasMatriculadas;
    private Avaliacao avaliacao;
    private double frequencia;   // Entre 0.0 e 1.0
    
    
    public Aluno(String nome, String matricula, String curso, String email){
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.email =  email;
        this.turmasMatriculadas = new ArrayList<>(); 
        // Deixar a parte de alunos em turmas para ser guardada na turma
        // Quando pegar os dados da turma, sobre quantos alunos tem e a frequencia de cada um, colocar 
        // Esse é o formato para criar um Array de objetos no método construtor segundo o professor
    }

    public boolean verificarCadastro() {
        return nome != null && !nome.trim().isEmpty() &&
               matricula != null && !matricula.trim().isEmpty() &&
               curso != null && !curso.trim().isEmpty() &&
               email != null && !email.trim().isEmpty();
               // .trim tira espaços em branco da string 
    }

    public boolean matricularEmTurma(Turma turma){
        if (!turmasMatriculadas.contains(turma)){
            turmasMatriculadas.add(turma);
            return true;
        }
        return false;
    }

    public void setNome(String nome){
        this.nome = nome;
    }
    
    public String getNome(){
        return nome;
    }
    
    public void setMatricula(String matricula){
        this.matricula = matricula;
    }
    
    public String getMatricula(){
        return matricula;
    }
    
    public void setCurso(String curso){
        this.curso = curso;
    }
    
    public String getCurso(){
        return curso;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setTurmasMatriculadas(List<Turma> turmasMatriculadas){
        this.turmasMatriculadas = turmasMatriculadas;
    }
    
    public List<Turma> getTurmasMatriculadas(){
        return turmasMatriculadas;
    }

    // O professor ensinou a usar add, OBS.: depois estude como usar add
    public void adicionarTurma(Turma turma){
        this.turmasMatriculadas.add(turma);
    }

    public List<Turma> mostrarTurmas(){
        return this.turmasMatriculadas;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public double getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(double frequencia) {
        this.frequencia = frequencia;
    }

}