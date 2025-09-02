## Aprendizagem Gamificada

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

### Integrantes
Enzo Murat Aires de Alencar - 212189
Ricardo Leporo Holtz
Thiago de Lima Santos - 223628