public class Frequencia {
    private int numeroTotalDeAulas;
    private int presencas;

    public Frequencia(int numeroTotalDeAulas, int presencas) {
        this.numeroTotalDeAulas = numeroTotalDeAulas;
        this.presencas = 0;
    }

    public void RegistrarPresenca(){
        this.presencas++;
    }

    public double calcularFrequencia() {
        return (double) presencas / numeroTotalDeAulas;
    }

    public boolean aprovadoPorFrequencia() {
        return calcularFrequencia() >= (75/100);
    }

    public int getNumeroTotalDeAulas() {
        return numeroTotalDeAulas;
    }

    public void setNumeroTotalDeAulas(int numeroTotalDeAulas) {
        this.numeroTotalDeAulas = numeroTotalDeAulas;
    }

    public int getPresencas() {
        return presencas;
    }

    public void setPresencas(int presencas) {
        this.presencas = presencas;
    }

    
}
