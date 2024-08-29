pipeline {
    agent any
    
    environment {
        // Define environment variables
        REGISTRY = "vitaliio"
        DOCKER_CREDENTIALS_ID = "dockerhub-credentials"
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
                        // Log in to Docker Hub (or another Docker registry)
                        withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                        sh "echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin ${REGISTRY}"
                    }
                    echo "Push image to dockerhub"
                    sh "make push"
                }
            }
        }
    }
}
