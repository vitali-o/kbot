
##### How to build your own docker images with binary for different OS and architectures:  

* Prerequestions:
  * Create your own artifact registry in [Google Cloud] (https://cloud.google.com/artifact-registry)
  * Setup service account for access to registry and configure docker as described [here] (https://cloud.google.com/artifact-registry/docs/docker/authentication#linux-macos)
  * Set REGISTRY variable in Makefile to your registry
* How to build application for different OS and architectures:
  * run command "make TARGETOS=OS TARGETARCH=ARCH build". Values TARGETOS and TARGETARCH is [here] (https://go.dev/doc/install/source#environment). If vars is not set by default build will be for linux amd64
* How to build own docker image with binary and push it to own registry:
  * make TARGETOS=OS TARGETARCH=ARCH image
  * make push
* To remove builded image from local docker registry run "make TARGETOS=OS TARGETARCH=ARCH clean"



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
