## Discord AI-Bot
[![CodeQL](https://github.com/Krystian-Kryszczak/discord-bot/actions/workflows/codeql.yml/badge.svg)](https://github.com/Krystian-Kryszczak/discord-bot/actions/workflows/codeql.yml)
[![Java CI](https://github.com/Krystian-Kryszczak/discord-bot/actions/workflows/gradle.yml/badge.svg)](https://github.com/Krystian-Kryszczak/discord-bot/actions/workflows/gradle.yml)
> It is a bot for discord that can be spoken to.\
This process is done sequentially through speech recognition using the Whisper model,\
then the result is sent to the GPT language model from OpenAI.\
The resulting response is converted into sound, via an API from ElevenLabs,\
after which it is sent back to the Discord user in the form of sound (voice).
#### Used technologies
- Discord Bot Library » [JDA (Java Discord API)](https://github.com/discord-jda/JDA)
- Speech recognition » [Whisper (OpenAI)](https://platform.openai.com/docs/models/whisper)
- Language model » [GPT](https://openai.com/chatgpt)
- Text to speech » [ElevenLabs](https://elevenlabs.io/)
- Testing » [JUnit](https://junit.org/), [Mockito](https://site.mockito.org/)
