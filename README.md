## Aprendizagem Gamificada

### Integrantes
Enzo Murat Aires de Alencar - 212189  
Ricardo Leporo Holtz - 212064  
Thiago de Lima Santos - 223628

### Gestão de Branch
1. Branch principal protegida

- __main__ deve sempre conter a versão estável.

2. Criação de branch a partir da main

Cada colaborador deve criar sua própria branch a partir da main:

```
git checkout main
git pull origin main
git checkout -b feature/teste-cenario-login
```

3. Nomeação de branch

Use um padrão que identifique tipo de tarefa + contexto:

- __feature/teste-cenario-login__ → novos testes de login

- __feature/teste-cenario-reserva__ → novos testes de reserva

- __bugfix/corrigir-cenario-falha-login__ → correção de teste

- __chore/atualizar-dependencias-bdd__ → mudanças técnicas sem impacto funcional 

### Detalhes do Projeto

1. User Story

EU COMO estudante QUERO que ao concluir um curso com média final maior ou igual a 7,0, sejam automaticamente liberados 3 novos cursos adicionais PARA poder continuar avançando na plataforma.

2. BDDs

Dado um estudante "Thiago" com 0 tickets  
E um curso "DevOps" com um módulo avaliado em 7  
Quando o estudante finaliza o curso  
Então o estudante deve receber mais 3 tickets

Dado um estudante "Thiago" com 0 tickets  
E um curso "DevOps" com um módulo avaliado em 8 
Quando o estudante finaliza o curso  
Então o estudante deve receber mais 3 tickets

Dado um estudante "Thiago" com 0 tickets  
E um curso "DevOps" com um módulo avaliado em 5  
Quando o estudante finaliza o curso  
Então o estudante deve permanecer o mesmo número de tickets

