FROM quay.io/projectquay/golang:1.20 AS builder

WORKDIR /go/src/app
COPY . .

# Define build arguments
ARG TARGETOS=linux
ARG TARGETARCH=amd64

# Pass the arguments as environment variables to be used in the make command.
ENV TARGETOS=${TARGETOS}
ENV TARGETARCH=${TARGETARCH}

RUN make build

FROM scratch
WORKDIR /
COPY --from=builder /go/src/app/kbot .
COPY --from=alpine:latest /etc/ssl/certs/ca-certificates.crt /etc/ssl/certs/
ENTRYPOINT ["./kbot", "start"]
