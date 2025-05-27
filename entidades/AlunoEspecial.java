package entidades;
public class AlunoEspecial extends Aluno{
    private int quantidadeMaterias;

    public AlunoEspecial(String nome, String matricula, String curso, String email) {
        super(nome, matricula, curso, email);
        this.quantidadeMaterias = 0;
    }

    // Polimorfismo por sobrescrita 
    @Override 
    public boolean matricularEmTurma(Turma turma) {
        if(quantidadeMaterias >= 2){
            System.out.println("----->Aluno Especial só pode cursar até 2 disciplinas.");
            return false;
        }
        
        getTurmasMatriculadas().add(turma);
        System.out.println("----->Aluno matriculado com sucesso na turma: " + turma);
        return true;

    }
    
}
