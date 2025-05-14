public class Matricula {
    private Turma turma;
    private Frequencia frequencia;
    // você pode adicionar mais: nota, data de matrícula, etc.

    public Matricula(Turma turma, int totalDeAulas) {
        this.turma = turma;
        this.frequencia = new Frequencia(totalDeAulas, 0);
    }

    public void registrarPresenca() {
        frequencia.RegistrarPresenca();
    }


    public boolean aprovadoPorFrequencia() {
        return frequencia.aprovadoPorFrequencia();
    }

    public Turma getTurma() {
        return turma;
    }

    public Frequencia getFrequencia() {
        return frequencia;
    }
}
