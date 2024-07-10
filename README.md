Sample bot can be accessed [here] (https://t.me/k8course_bot)

##### How to use:

* Clone repo localy or create fork
* Go to [BotFather] (https://t.me/BotFather)
  * register new bot
  * give username to bot
  * copy provided API key
* Set local variable TELE_TOKEN with API key value:
  * read -s TELE_TOKEN
  * export TELE_TOKEN
* Build and run your application:
  * go build -ldflags "-X="kbot/cmd.appVersion={version}  #where {version} is any preferrable build number
  * ./kbot start
* Open your bot in telegram
  * print /start hello or /start boo to see bot answer
  * view client messages in terminal
