# Sistema Acadêmico - FCTE

## Descrição do Projeto

Desenvolvimento de um sistema acadêmico para gerenciar alunos, disciplinas, professores, turmas, avaliações e frequência, utilizando os conceitos de orientação a objetos (herança, polimorfismo e encapsulamento) e persistência de dados em arquivos.

O enunciado do trabalho pode ser encontrado aqui:
- [Trabalho 1 - Sistema Acadêmico](https://github.com/lboaventura25/OO-T06_2025.1_UnB_FCTE/blob/main/trabalhos/ep1/README.md)

## Dados do Aluno

- **Nome completo:** Gustavo Antonio Rodrigues e Silva
- **Matrícula:** 242015380
- **Curso:** Engenharias
- **Turma:** 06

---

## Instruções para Compilação e Execução

1. **Compilação:**  
   Clonar repositorio na IDE, de preferencia VS Code com o comando: `git clone https://github.com/gus-ant/ep1-OO-2025_1_GUSTAVO_ANTONIO.git`

2. **Execução:**  
   Executar o arquivo Main.java no botão `run`

3. **Estrutura de Pastas:**  
```
   ep1-OO-2025_1_GUSTAVO_ANTONIO
   │
   ├── Main.java
   │   
   ├── entidades/              ← Classes de domínio
   │           ├── Aluno.java
   │           ├── AlunoEspecial.java
   │           ├── Avaliacao.java
   │           ├── Frequencia.java
   │           ├── Turma.java
   │           └── Disciplina.java
   | 
   ├─── persistencia/          ← Salvamento de dados
   |           ├─── matrícula_aluno.txt
   |           ├─── disciplinas.txt
   |           └─── turmas.txt     
   | 
   ├─── imagens/               ← Imagens do README.md
   |           ├─── Modo_aluno.png
   |           ├─── Modo_avaliacao.png
   |           └─── Pagina_inicial.png                       
   │
   └── README.md

```

3. **Versão do JAVA utilizada:**  
   java 21.0.6

---

## Vídeo de Demonstração

- [Inserir o link para o vídeo no YouTube/Drive aqui]

---

## Prints da Execução

1. Menu Principal:  
   ![Pagina_inicial](imagens/Pagina_inicial.png)

2. Cadastro de Aluno:  
   ![Modo_aluno](imagens/Modo_aluno.png)

3. Relatório de Frequência/Notas:  
   ![Modo_avaliacao](imagens/Modo_avaliacao.png)

---

## Principais Funcionalidades Implementadas

- [x] Cadastro, listagem, matrícula e trancamento de alunos (Normais e Especiais)
- [x] Cadastro de disciplinas e criação de turmas (presenciais e remotas)
- [x] Matrícula de alunos em turmas, respeitando vagas e pré-requisitos
- [x] Lançamento de notas e controle de presença
- [x] Cálculo de média final e verificação de aprovação/reprovação
- [x] Relatórios de desempenho acadêmico por aluno, turma e disciplina
- [x] Persistência de dados em arquivos (.txt ou .csv)
- [x] Tratamento de duplicidade de matrículas
- [x] Uso de herança, polimorfismo e encapsulamento

---

## Observações (Extras ou Dificuldades)

- [Espaço para o aluno comentar qualquer funcionalidade extra que implementou, dificuldades enfrentadas, ou considerações importantes.]

---

## Contato

- Email: gus.ant.rod.10@gmail.com