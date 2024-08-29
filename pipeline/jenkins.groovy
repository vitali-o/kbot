pipeline {
    agent any
    
    environment {
        // Define environment variables
        REGISTRY = "vitaliio"
        DOCKER_TOKEN_CREDENTIALS_ID = 'dockerhub-token'
    }
    parameters {
        choice(name: 'OS', choices: ['linux', 'android', 'windows'], description: 'Pick OS')
        choice(name: 'ARCH', choices: ['386', 'arm', 'arm64', 'amd64'], description: 'Pick ARCH')
    }
    stages {
        stage('test') {
            steps {
                script {
                    echo "run test"
                    sh "make test"
                }
            }
        }
        stage('build') {
            steps { 
                script {
                    echo "Build image"
                    sh "make TARGETOS=${params.OS} TARGETARCH=${params.ARCH} image"
                }
            }
        }
        stage('list images') {
            steps { 
                script {
                    echo "Push image to dockerhub"
                    sh "docker images"
                }
            }
        }
        stage('push to dockerhub') {
            steps {
                script {
                    // Log in to Docker Hub (or another Docker registry) securely using an access token
                    withCredentials([string(credentialsId: "${DOCKER_TOKEN_CREDENTIALS_ID}", variable: 'DOCKER_TOKEN')]) {
                        sh "echo $DOCKER_TOKEN | docker login -u ${REGISTRY} --password-stdin"
                    }
                    echo "Push image to dockerhub"
                    sh "make push TARGETARCH=${params.ARCH}"
                }
            }
        }
    }
}
