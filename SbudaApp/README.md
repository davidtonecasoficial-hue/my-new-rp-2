# Sbuda App

App Android (Kotlin) com um botão gigante vermelho no centro. Ao clicar:
- Vibra levemente
- O botão faz um "salto" (efeito overshoot)
- Um card desliza para cima e a mensagem aparece letra a letra:
  **"i got you Sbuda, you are my son now"**

## Design
- Fundo escuro (#0D0D14) com glow radial vermelho atrás do botão
- Botão circular com gradiente e efeito de profundidade (borda clara no topo)
- Tipografia bold, letter-spacing para visual moderno
- Card de mensagem com cantos arredondados e borda sutil vermelha

## Como compilar
1. Abre a pasta `SbudaApp/` no Android Studio.
2. Deixa o Gradle sincronizar.
3. Corre num emulador ou telemóvel (Run ▶).

## Estrutura
```
SbudaApp/
├── app/
│   ├── build.gradle.kts
│   └── src/main/
│       ├── AndroidManifest.xml
│       ├── java/com/meliodas/sbudaapp/MainActivity.kt
│       └── res/
│           ├── layout/activity_main.xml
│           ├── drawable/ (botão, glow, card)
│           └── values/ (strings.xml, themes.xml)
├── build.gradle.kts
└── settings.gradle.kts
```
