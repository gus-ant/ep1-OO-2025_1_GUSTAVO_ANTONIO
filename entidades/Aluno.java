package entidades;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// É uma classe mãe para criar as outras classes de aluno especial e aluno normal
public class Aluno {
    private String nome;
    private String matricula;
    private String curso;
    private String email;
    private List<Turma> turmasMatriculadas;
    private Avaliacao avaliacao;
    private double frequencia;   // Entre 0.0 e 1.0  
    private Map<Turma, Avaliacao> avaliacoes; // Mapa de turma para avaliação
    private Map<Turma, Double> frequencias;// Mapa de turma para frequência
    private List<String> turmasAprovadas;

    public Aluno(String nome, String matricula, String curso, String email){
        this.nome = nome;
        this.matricula = matricula;
        this.curso = curso;
        this.email =  email;
        this.turmasMatriculadas = new ArrayList<>(); 
        this.avaliacoes = new HashMap<>();
        this.frequencias = new HashMap<>();
        this.turmasAprovadas = new ArrayList<>();
        
    }

    

    // Verifica se o código da turma está presente na lista de turmas aprovadas do aluno.
    public boolean isAprovado(String codigoTurma) {
        return this.turmasAprovadas.contains(codigoTurma);
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

    public void RemoverTurmas(Turma turma){
        this.turmasMatriculadas.remove(turma);
    }


    public Map<Turma, Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public void setAvaliacoes(Map<Turma, Avaliacao> avaliacoes) {
        this.avaliacoes = avaliacoes;
    }

    public Map<Turma, Double> getFrequencias() {
        return frequencias;
    }

    public void setFrequencias(Map<Turma, Double> frequencias) {
        this.frequencias = frequencias;
    }

    public void adicionarAvaliacao(Turma turma, Avaliacao avaliacao) {
        this.avaliacoes.put(turma, avaliacao);
    }

    public void adicionarFrequencia(Turma turma, double frequencia) {
        this.frequencias.put(turma, frequencia);
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



    public List<String> getTurmasAprovadas() {
        return turmasAprovadas;
    }



    public void setTurmasAprovadas(List<String> turmasAprovadas) {
        this.turmasAprovadas = turmasAprovadas;
    }

}