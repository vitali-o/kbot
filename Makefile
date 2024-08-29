APP=$(shell basename $(shell git remote get-url origin) .git)
VERSION=$(shell git describe --tags --abbrev=0)-$(shell git rev-parse --short HEAD)
TARGETOS?=linux
TARGETARCH?=amd64
REGISTRY?=vitaliio

format:
	gofmt -s -w ./

lint:
	golint

test:
	go test -v

goget:
	go get

build: format goget
	CGO_ENABLED=0 GOOS=${TARGETOS} GOARCH=${TARGETARCH} go build -v -o kbot -ldflags "-X="github.com/vitali-o/kbot/cmd.appVersion=${VERSION}

image:
	docker build . -t ${REGISTRY}/${APP}:${VERSION}-${TARGETARCH}

push:
	docker push ${REGISTRY}/${APP}:${VERSION}-${TARGETARCH}

clean:
	docker rmi ${REGISTRY}/${APP}:${VERSION}-${TARGETARCH} && rm -rf kbot
