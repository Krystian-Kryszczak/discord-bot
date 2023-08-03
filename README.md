## Discord AI-Bot
![CodeQL](https://github.com/Krystian-Kryszczak/discord-bot/workflows/CodeQL/badge.svg)
> This is a bot for Discord that you can talk to.<br>
This process is done sequentially through speech recognition using the Whisper model,<br>
then the result is sent to the GPT language model from OpenAI.<br>
The resulting response is converted into sound, via an API from ElevenLabs,<br>
after which it is sent back to the Discord user in the form of sound (voice).
#### Used technologies
- Discord Bot Library » [JDA (Java Discord API)](https://github.com/discord-jda/JDA)
- Speech recognition » [Whisper (OpenAI)](https://platform.openai.com/docs/models/whisper)
- Language model  » [GPT-3.5](https://platform.openai.com/docs/models/gpt-3-5)
- Text to speech » [ElevenLabs](https://elevenlabs.io/)
- Testing » [JUnit](https://junit.org/)
